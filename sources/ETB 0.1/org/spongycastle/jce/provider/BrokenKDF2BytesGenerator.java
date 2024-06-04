package org.spongycastle.jce.provider;

import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.DerivationFunction;
import org.spongycastle.crypto.DerivationParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KDFParameters;




















public class BrokenKDF2BytesGenerator
  implements DerivationFunction
{
  private Digest digest;
  private byte[] shared;
  private byte[] iv;
  
  public BrokenKDF2BytesGenerator(Digest digest)
  {
    this.digest = digest;
  }
  

  public void init(DerivationParameters param)
  {
    if (!(param instanceof KDFParameters))
    {
      throw new IllegalArgumentException("KDF parameters required for generator");
    }
    
    KDFParameters p = (KDFParameters)param;
    
    shared = p.getSharedSecret();
    iv = p.getIV();
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
    
    long oBits = len * 8L;
    






    if (oBits > digest.getDigestSize() * 8L * 2147483648L)
    {
      new IllegalArgumentException("Output length to large");
    }
    
    int cThreshold = (int)(oBits / digest.getDigestSize());
    
    byte[] dig = null;
    
    dig = new byte[digest.getDigestSize()];
    
    for (int counter = 1; counter <= cThreshold; counter++)
    {
      digest.update(shared, 0, shared.length);
      
      digest.update((byte)(counter & 0xFF));
      digest.update((byte)(counter >> 8 & 0xFF));
      digest.update((byte)(counter >> 16 & 0xFF));
      digest.update((byte)(counter >> 24 & 0xFF));
      
      digest.update(iv, 0, iv.length);
      
      digest.doFinal(dig, 0);
      
      if (len - outOff > dig.length)
      {
        System.arraycopy(dig, 0, out, outOff, dig.length);
        outOff += dig.length;
      }
      else
      {
        System.arraycopy(dig, 0, out, outOff, len - outOff);
      }
    }
    
    digest.reset();
    
    return len;
  }
}
