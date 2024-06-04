package org.spongycastle.crypto.agreement.kdf;

import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.DerivationFunction;
import org.spongycastle.crypto.DerivationParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KDFParameters;








public class ConcatenationKDFGenerator
  implements DerivationFunction
{
  private Digest digest;
  private byte[] shared;
  private byte[] otherInfo;
  private int hLen;
  
  public ConcatenationKDFGenerator(Digest digest)
  {
    this.digest = digest;
    hLen = digest.getDigestSize();
  }
  

  public void init(DerivationParameters param)
  {
    if ((param instanceof KDFParameters))
    {
      KDFParameters p = (KDFParameters)param;
      
      shared = p.getSharedSecret();
      otherInfo = p.getIV();
    }
    else
    {
      throw new IllegalArgumentException("KDF parameters required for generator");
    }
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
    int counter = 1;
    int outputLen = 0;
    
    digest.reset();
    
    if (len > hLen)
    {
      do
      {
        ItoOSP(counter, C);
        
        digest.update(C, 0, C.length);
        digest.update(shared, 0, shared.length);
        digest.update(otherInfo, 0, otherInfo.length);
        
        digest.doFinal(hashBuf, 0);
        
        System.arraycopy(hashBuf, 0, out, outOff + outputLen, hLen);
        outputLen += hLen;
      }
      while (counter++ < len / hLen);
    }
    
    if (outputLen < len)
    {
      ItoOSP(counter, C);
      
      digest.update(C, 0, C.length);
      digest.update(shared, 0, shared.length);
      digest.update(otherInfo, 0, otherInfo.length);
      
      digest.doFinal(hashBuf, 0);
      
      System.arraycopy(hashBuf, 0, out, outOff + outputLen, len - outputLen);
    }
    
    return len;
  }
}
