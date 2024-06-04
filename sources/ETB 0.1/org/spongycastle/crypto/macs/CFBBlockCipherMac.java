package org.spongycastle.crypto.macs;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.paddings.BlockCipherPadding;



































































































































































public class CFBBlockCipherMac
  implements Mac
{
  private byte[] mac;
  private byte[] buf;
  private int bufOff;
  private MacCFBBlockCipher cipher;
  private BlockCipherPadding padding = null;
  




  private int macSize;
  





  public CFBBlockCipherMac(BlockCipher cipher)
  {
    this(cipher, 8, cipher.getBlockSize() * 8 / 2, null);
  }
  










  public CFBBlockCipherMac(BlockCipher cipher, BlockCipherPadding padding)
  {
    this(cipher, 8, cipher.getBlockSize() * 8 / 2, padding);
  }
  

















  public CFBBlockCipherMac(BlockCipher cipher, int cfbBitSize, int macSizeInBits)
  {
    this(cipher, cfbBitSize, macSizeInBits, null);
  }
  



















  public CFBBlockCipherMac(BlockCipher cipher, int cfbBitSize, int macSizeInBits, BlockCipherPadding padding)
  {
    if (macSizeInBits % 8 != 0)
    {
      throw new IllegalArgumentException("MAC size must be multiple of 8");
    }
    
    mac = new byte[cipher.getBlockSize()];
    
    this.cipher = new MacCFBBlockCipher(cipher, cfbBitSize);
    this.padding = padding;
    macSize = (macSizeInBits / 8);
    
    buf = new byte[this.cipher.getBlockSize()];
    bufOff = 0;
  }
  
  public String getAlgorithmName()
  {
    return cipher.getAlgorithmName();
  }
  

  public void init(CipherParameters params)
  {
    reset();
    
    cipher.init(params);
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
    



    if (padding == null)
    {
      while (bufOff < blockSize)
      {
        buf[bufOff] = 0;
        bufOff += 1;
      }
    }
    

    padding.addPadding(buf, bufOff);
    

    cipher.processBlock(buf, 0, mac, 0);
    
    cipher.getMacBlock(mac);
    
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
