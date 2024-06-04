package org.spongycastle.crypto.generators;

import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.DerivationParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.DigestDerivationFunction;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.ISO18033KDFParameters;
import org.spongycastle.crypto.params.KDFParameters;
import org.spongycastle.util.Pack;















public class BaseKDFBytesGenerator
  implements DigestDerivationFunction
{
  private int counterStart;
  private Digest digest;
  private byte[] shared;
  private byte[] iv;
  
  protected BaseKDFBytesGenerator(int counterStart, Digest digest)
  {
    this.counterStart = counterStart;
    this.digest = digest;
  }
  
  public void init(DerivationParameters param)
  {
    if ((param instanceof KDFParameters))
    {
      KDFParameters p = (KDFParameters)param;
      
      shared = p.getSharedSecret();
      iv = p.getIV();
    }
    else if ((param instanceof ISO18033KDFParameters))
    {
      ISO18033KDFParameters p = (ISO18033KDFParameters)param;
      
      shared = p.getSeed();
      iv = null;
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
  









  public int generateBytes(byte[] out, int outOff, int len)
    throws DataLengthException, IllegalArgumentException
  {
    if (out.length - len < outOff)
    {
      throw new OutputLengthException("output buffer too small");
    }
    
    long oBytes = len;
    int outLen = digest.getDigestSize();
    






    if (oBytes > 8589934591L)
    {
      throw new IllegalArgumentException("Output length too large");
    }
    
    int cThreshold = (int)((oBytes + outLen - 1L) / outLen);
    
    byte[] dig = new byte[digest.getDigestSize()];
    
    byte[] C = new byte[4];
    Pack.intToBigEndian(counterStart, C, 0);
    
    int counterBase = counterStart & 0xFF00;
    
    for (int i = 0; i < cThreshold; tmp229_228++)
    {
      digest.update(shared, 0, shared.length);
      digest.update(C, 0, C.length);
      
      if (iv != null)
      {
        digest.update(iv, 0, iv.length);
      }
      
      digest.doFinal(dig, 0);
      
      if (len > outLen)
      {
        System.arraycopy(dig, 0, out, outOff, outLen);
        outOff += outLen;
        len -= outLen;
      }
      else
      {
        System.arraycopy(dig, 0, out, outOff, len);
      }
      
      int tmp229_228 = 3;C;
      



































































































































      if ((tmp229_226[tmp229_228] = (byte)(tmp229_226[tmp229_228] + 1)) == 0)
      {
        counterBase += 256;
        Pack.intToBigEndian(counterBase, C, 0);
      }
    }
    
    digest.reset();
    
    return (int)oBytes;
  }
}
