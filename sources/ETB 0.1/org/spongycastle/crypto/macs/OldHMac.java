package org.spongycastle.crypto.macs;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.params.KeyParameter;








public class OldHMac
  implements Mac
{
  private static final int BLOCK_LENGTH = 64;
  private static final byte IPAD = 54;
  private static final byte OPAD = 92;
  private Digest digest;
  private int digestSize;
  private byte[] inputPad = new byte[64];
  private byte[] outputPad = new byte[64];
  

  /**
   * @deprecated
   */
  public OldHMac(Digest digest)
  {
    this.digest = digest;
    digestSize = digest.getDigestSize();
  }
  
  public String getAlgorithmName()
  {
    return digest.getAlgorithmName() + "/HMAC";
  }
  
  public Digest getUnderlyingDigest()
  {
    return digest;
  }
  

  public void init(CipherParameters params)
  {
    digest.reset();
    
    byte[] key = ((KeyParameter)params).getKey();
    
    if (key.length > 64)
    {
      digest.update(key, 0, key.length);
      digest.doFinal(inputPad, 0);
      for (int i = digestSize; i < inputPad.length; i++)
      {
        inputPad[i] = 0;
      }
    }
    else
    {
      System.arraycopy(key, 0, inputPad, 0, key.length);
      for (int i = key.length; i < inputPad.length; i++)
      {
        inputPad[i] = 0;
      }
    }
    
    outputPad = new byte[inputPad.length];
    System.arraycopy(inputPad, 0, outputPad, 0, inputPad.length);
    
    for (int i = 0; i < inputPad.length; i++)
    {
      int tmp164_163 = i; byte[] tmp164_160 = inputPad;tmp164_160[tmp164_163] = ((byte)(tmp164_160[tmp164_163] ^ 0x36));
    }
    
    for (int i = 0; i < outputPad.length; i++)
    {
      int tmp193_192 = i; byte[] tmp193_189 = outputPad;tmp193_189[tmp193_192] = ((byte)(tmp193_189[tmp193_192] ^ 0x5C));
    }
    
    digest.update(inputPad, 0, inputPad.length);
  }
  
  public int getMacSize()
  {
    return digestSize;
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
    byte[] tmp = new byte[digestSize];
    digest.doFinal(tmp, 0);
    
    digest.update(outputPad, 0, outputPad.length);
    digest.update(tmp, 0, tmp.length);
    
    int len = digest.doFinal(out, outOff);
    
    reset();
    
    return len;
  }
  






  public void reset()
  {
    digest.reset();
    



    digest.update(inputPad, 0, inputPad.length);
  }
}
