package org.spongycastle.crypto.tls;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.Arrays;









public class SSL3Mac
  implements Mac
{
  private static final byte IPAD_BYTE = 54;
  private static final byte OPAD_BYTE = 92;
  static final byte[] IPAD = genPad(, 48);
  static final byte[] OPAD = genPad((byte)92, 48);
  

  private Digest digest;
  

  private int padLength;
  

  private byte[] secret;
  


  public SSL3Mac(Digest digest)
  {
    this.digest = digest;
    
    if (digest.getDigestSize() == 20)
    {
      padLength = 40;
    }
    else
    {
      padLength = 48;
    }
  }
  
  public String getAlgorithmName()
  {
    return digest.getAlgorithmName() + "/SSL3MAC";
  }
  
  public Digest getUnderlyingDigest()
  {
    return digest;
  }
  
  public void init(CipherParameters params)
  {
    secret = Arrays.clone(((KeyParameter)params).getKey());
    
    reset();
  }
  
  public int getMacSize()
  {
    return digest.getDigestSize();
  }
  
  public void update(byte in)
  {
    digest.update(in);
  }
  
  public void update(byte[] in, int inOff, int len)
  {
    digest.update(in, inOff, len);
  }
  
  public int doFinal(byte[] out, int outOff)
  {
    byte[] tmp = new byte[digest.getDigestSize()];
    digest.doFinal(tmp, 0);
    
    digest.update(secret, 0, secret.length);
    digest.update(OPAD, 0, padLength);
    digest.update(tmp, 0, tmp.length);
    
    int len = digest.doFinal(out, outOff);
    
    reset();
    
    return len;
  }
  



  public void reset()
  {
    digest.reset();
    digest.update(secret, 0, secret.length);
    digest.update(IPAD, 0, padLength);
  }
  
  private static byte[] genPad(byte b, int count)
  {
    byte[] padding = new byte[count];
    Arrays.fill(padding, b);
    return padding;
  }
}
