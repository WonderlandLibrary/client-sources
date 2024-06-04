package org.spongycastle.crypto.macs;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.engines.DESEngine;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.paddings.BlockCipherPadding;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;


















public class ISO9797Alg3Mac
  implements Mac
{
  private byte[] mac;
  private byte[] buf;
  private int bufOff;
  private BlockCipher cipher;
  private BlockCipherPadding padding;
  private int macSize;
  private KeyParameter lastKey2;
  private KeyParameter lastKey3;
  
  public ISO9797Alg3Mac(BlockCipher cipher)
  {
    this(cipher, cipher.getBlockSize() * 8, null);
  }
  









  public ISO9797Alg3Mac(BlockCipher cipher, BlockCipherPadding padding)
  {
    this(cipher, cipher.getBlockSize() * 8, padding);
  }
  















  public ISO9797Alg3Mac(BlockCipher cipher, int macSizeInBits)
  {
    this(cipher, macSizeInBits, null);
  }
  


















  public ISO9797Alg3Mac(BlockCipher cipher, int macSizeInBits, BlockCipherPadding padding)
  {
    if (macSizeInBits % 8 != 0)
    {
      throw new IllegalArgumentException("MAC size must be multiple of 8");
    }
    
    if (!(cipher instanceof DESEngine))
    {
      throw new IllegalArgumentException("cipher must be instance of DESEngine");
    }
    
    this.cipher = new CBCBlockCipher(cipher);
    this.padding = padding;
    macSize = (macSizeInBits / 8);
    
    mac = new byte[cipher.getBlockSize()];
    
    buf = new byte[cipher.getBlockSize()];
    bufOff = 0;
  }
  
  public String getAlgorithmName()
  {
    return "ISO9797Alg3";
  }
  
  public void init(CipherParameters params)
  {
    reset();
    
    if ((!(params instanceof KeyParameter)) && (!(params instanceof ParametersWithIV)))
    {
      throw new IllegalArgumentException("params must be an instance of KeyParameter or ParametersWithIV");
    }
    

    KeyParameter kp;
    

    KeyParameter kp;
    

    if ((params instanceof KeyParameter))
    {
      kp = (KeyParameter)params;
    }
    else
    {
      kp = (KeyParameter)((ParametersWithIV)params).getParameters();
    }
    

    byte[] keyvalue = kp.getKey();
    
    if (keyvalue.length == 16)
    {
      KeyParameter key1 = new KeyParameter(keyvalue, 0, 8);
      lastKey2 = new KeyParameter(keyvalue, 8, 8);
      lastKey3 = key1;
    }
    else if (keyvalue.length == 24)
    {
      KeyParameter key1 = new KeyParameter(keyvalue, 0, 8);
      lastKey2 = new KeyParameter(keyvalue, 8, 8);
      lastKey3 = new KeyParameter(keyvalue, 16, 8);
    }
    else
    {
      throw new IllegalArgumentException("Key must be either 112 or 168 bit long");
    }
    
    KeyParameter key1;
    if ((params instanceof ParametersWithIV))
    {
      cipher.init(true, new ParametersWithIV(key1, ((ParametersWithIV)params).getIV()));
    }
    else
    {
      cipher.init(true, key1);
    }
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
    

    if (bufOff == blockSize)
    {
      cipher.processBlock(buf, 0, mac, 0);
      bufOff = 0;
    }
    
    padding.addPadding(buf, bufOff);
    

    cipher.processBlock(buf, 0, mac, 0);
    

    DESEngine deseng = new DESEngine();
    
    deseng.init(false, lastKey2);
    deseng.processBlock(mac, 0, mac, 0);
    
    deseng.init(true, lastKey3);
    deseng.processBlock(mac, 0, mac, 0);
    

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
