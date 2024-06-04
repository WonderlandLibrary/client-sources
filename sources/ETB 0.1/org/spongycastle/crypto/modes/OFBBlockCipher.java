package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.StreamBlockCipher;
import org.spongycastle.crypto.params.ParametersWithIV;














public class OFBBlockCipher
  extends StreamBlockCipher
{
  private int byteCount;
  private byte[] IV;
  private byte[] ofbV;
  private byte[] ofbOutV;
  private final int blockSize;
  private final BlockCipher cipher;
  
  public OFBBlockCipher(BlockCipher cipher, int blockSize)
  {
    super(cipher);
    
    this.cipher = cipher;
    this.blockSize = (blockSize / 8);
    
    IV = new byte[cipher.getBlockSize()];
    ofbV = new byte[cipher.getBlockSize()];
    ofbOutV = new byte[cipher.getBlockSize()];
  }
  













  public void init(boolean encrypting, CipherParameters params)
    throws IllegalArgumentException
  {
    if ((params instanceof ParametersWithIV))
    {
      ParametersWithIV ivParam = (ParametersWithIV)params;
      byte[] iv = ivParam.getIV();
      
      if (iv.length < IV.length)
      {

        System.arraycopy(iv, 0, IV, IV.length - iv.length, iv.length);
        for (int i = 0; i < IV.length - iv.length; i++)
        {
          IV[i] = 0;
        }
      }
      else
      {
        System.arraycopy(iv, 0, IV, 0, IV.length);
      }
      
      reset();
      

      if (ivParam.getParameters() != null)
      {
        cipher.init(true, ivParam.getParameters());
      }
    }
    else
    {
      reset();
      

      if (params != null)
      {
        cipher.init(true, params);
      }
    }
  }
  






  public String getAlgorithmName()
  {
    return cipher.getAlgorithmName() + "/OFB" + blockSize * 8;
  }
  






  public int getBlockSize()
  {
    return blockSize;
  }
  

















  public int processBlock(byte[] in, int inOff, byte[] out, int outOff)
    throws DataLengthException, IllegalStateException
  {
    processBytes(in, inOff, blockSize, out, outOff);
    
    return blockSize;
  }
  




  public void reset()
  {
    System.arraycopy(IV, 0, ofbV, 0, IV.length);
    byteCount = 0;
    
    cipher.reset();
  }
  
  protected byte calculateByte(byte in)
    throws DataLengthException, IllegalStateException
  {
    if (byteCount == 0)
    {
      cipher.processBlock(ofbV, 0, ofbOutV, 0);
    }
    
    byte rv = (byte)(ofbOutV[(byteCount++)] ^ in);
    
    if (byteCount == blockSize)
    {
      byteCount = 0;
      
      System.arraycopy(ofbV, blockSize, ofbV, 0, ofbV.length - blockSize);
      System.arraycopy(ofbOutV, 0, ofbV, ofbV.length - blockSize, blockSize);
    }
    
    return rv;
  }
}
