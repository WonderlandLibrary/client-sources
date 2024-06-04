package org.spongycastle.crypto.macs;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.ParametersWithIV;







class MacCFBBlockCipher
{
  private byte[] IV;
  private byte[] cfbV;
  private byte[] cfbOutV;
  private int blockSize;
  private BlockCipher cipher = null;
  









  public MacCFBBlockCipher(BlockCipher cipher, int bitBlockSize)
  {
    this.cipher = cipher;
    blockSize = (bitBlockSize / 8);
    
    IV = new byte[cipher.getBlockSize()];
    cfbV = new byte[cipher.getBlockSize()];
    cfbOutV = new byte[cipher.getBlockSize()];
  }
  










  public void init(CipherParameters params)
    throws IllegalArgumentException
  {
    if ((params instanceof ParametersWithIV))
    {
      ParametersWithIV ivParam = (ParametersWithIV)params;
      byte[] iv = ivParam.getIV();
      
      if (iv.length < IV.length)
      {
        System.arraycopy(iv, 0, IV, IV.length - iv.length, iv.length);
      }
      else
      {
        System.arraycopy(iv, 0, IV, 0, IV.length);
      }
      
      reset();
      
      cipher.init(true, ivParam.getParameters());
    }
    else
    {
      reset();
      
      cipher.init(true, params);
    }
  }
  






  public String getAlgorithmName()
  {
    return cipher.getAlgorithmName() + "/CFB" + blockSize * 8;
  }
  





  public int getBlockSize()
  {
    return blockSize;
  }
  

















  public int processBlock(byte[] in, int inOff, byte[] out, int outOff)
    throws DataLengthException, IllegalStateException
  {
    if (inOff + blockSize > in.length)
    {
      throw new DataLengthException("input buffer too short");
    }
    
    if (outOff + blockSize > out.length)
    {
      throw new OutputLengthException("output buffer too short");
    }
    
    cipher.processBlock(cfbV, 0, cfbOutV, 0);
    



    for (int i = 0; i < blockSize; i++)
    {
      out[(outOff + i)] = ((byte)(cfbOutV[i] ^ in[(inOff + i)]));
    }
    



    System.arraycopy(cfbV, blockSize, cfbV, 0, cfbV.length - blockSize);
    System.arraycopy(out, outOff, cfbV, cfbV.length - blockSize, blockSize);
    
    return blockSize;
  }
  




  public void reset()
  {
    System.arraycopy(IV, 0, cfbV, 0, IV.length);
    
    cipher.reset();
  }
  

  void getMacBlock(byte[] mac)
  {
    cipher.processBlock(cfbV, 0, mac, 0);
  }
}
