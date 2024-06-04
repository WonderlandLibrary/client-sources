package org.spongycastle.crypto.generators;

import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.DerivationFunction;
import org.spongycastle.crypto.DerivationParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.MGFParameters;








public class MGF1BytesGenerator
  implements DerivationFunction
{
  private Digest digest;
  private byte[] seed;
  private int hLen;
  
  public MGF1BytesGenerator(Digest digest)
  {
    this.digest = digest;
    hLen = digest.getDigestSize();
  }
  

  public void init(DerivationParameters param)
  {
    if (!(param instanceof MGFParameters))
    {
      throw new IllegalArgumentException("MGF parameters required for MGF1Generator");
    }
    
    MGFParameters p = (MGFParameters)param;
    
    seed = p.getSeed();
  }
  



  public Digest getDigest()
  {
    return digest;
  }
  





  private void ItoOSP(int i, byte[] sp)
  {
    sp[0] = ((byte)(i >>> 24));
    sp[1] = ((byte)(i >>> 16));
    sp[2] = ((byte)(i >>> 8));
    sp[3] = ((byte)(i >>> 0));
  }
  









  public int generateBytes(byte[] out, int outOff, int len)
    throws DataLengthException, IllegalArgumentException
  {
    if (out.length - len < outOff)
    {
      throw new OutputLengthException("output buffer too small");
    }
    
    byte[] hashBuf = new byte[hLen];
    byte[] C = new byte[4];
    int counter = 0;
    
    digest.reset();
    
    if (len > hLen)
    {
      do
      {
        ItoOSP(counter, C);
        
        digest.update(seed, 0, seed.length);
        digest.update(C, 0, C.length);
        digest.doFinal(hashBuf, 0);
        
        System.arraycopy(hashBuf, 0, out, outOff + counter * hLen, hLen);
        
        counter++; } while (counter < len / hLen);
    }
    
    if (counter * hLen < len)
    {
      ItoOSP(counter, C);
      
      digest.update(seed, 0, seed.length);
      digest.update(C, 0, C.length);
      digest.doFinal(hashBuf, 0);
      
      System.arraycopy(hashBuf, 0, out, outOff + counter * hLen, len - counter * hLen);
    }
    
    return len;
  }
}
