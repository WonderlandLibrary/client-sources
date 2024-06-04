package org.spongycastle.crypto.macs;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.modes.CBCBlockCipher;








public class BlockCipherMac
  implements Mac
{
  private byte[] mac;
  private byte[] buf;
  private int bufOff;
  private BlockCipher cipher;
  private int macSize;
  
  /**
   * @deprecated
   */
  public BlockCipherMac(BlockCipher cipher)
  {
    this(cipher, cipher.getBlockSize() * 8 / 2);
  }
  











  /**
   * @deprecated
   */
  public BlockCipherMac(BlockCipher cipher, int macSizeInBits)
  {
    if (macSizeInBits % 8 != 0)
    {
      throw new IllegalArgumentException("MAC size must be multiple of 8");
    }
    
    this.cipher = new CBCBlockCipher(cipher);
    macSize = (macSizeInBits / 8);
    
    mac = new byte[cipher.getBlockSize()];
    
    buf = new byte[cipher.getBlockSize()];
    bufOff = 0;
  }
  
  public String getAlgorithmName()
  {
    return cipher.getAlgorithmName();
  }
  

  public void init(CipherParameters params)
  {
    reset();
    
    cipher.init(true, params);
  }
  
  public int getMacSize()
  {
    return macSize;
  }
  

  public void update(byte in)
  {
    if (bufOff == buf.length)
    {
      cipher.processBlock(buf, 0, mac, 0);
      bufOff = 0;
    }
    
    buf[(bufOff++)] = in;
  }
  



  public void update(byte[] in, int inOff, int len)
  {
    if (len < 0)
    {
      throw new IllegalArgumentException("Can't have a negative input length!");
    }
    
    int blockSize = cipher.getBlockSize();
    int resultLen = 0;
    int gapLen = blockSize - bufOff;
    
    if (len > gapLen)
    {
      System.arraycopy(in, inOff, buf, bufOff, gapLen);
      
      resultLen += cipher.processBlock(buf, 0, mac, 0);
      
      bufOff = 0;
      len -= gapLen;
      inOff += gapLen;
      
      while (len > blockSize)
      {
        resultLen += cipher.processBlock(in, inOff, mac, 0);
        
        len -= blockSize;
        inOff += blockSize;
      }
    }
    
    System.arraycopy(in, inOff, buf, bufOff, len);
    
    bufOff += len;
  }
  


  public int doFinal(byte[] out, int outOff)
  {
    int blockSize = cipher.getBlockSize();
    



    while (bufOff < blockSize)
    {
      buf[bufOff] = 0;
      bufOff += 1;
    }
    
    cipher.processBlock(buf, 0, mac, 0);
    
    System.arraycopy(mac, 0, out, outOff, macSize);
    
    reset();
    
    return macSize;
  }
  






  public void reset()
  {
    for (int i = 0; i < buf.length; i++)
    {
      buf[i] = 0;
    }
    
    bufOff = 0;
    



    cipher.reset();
  }
}
