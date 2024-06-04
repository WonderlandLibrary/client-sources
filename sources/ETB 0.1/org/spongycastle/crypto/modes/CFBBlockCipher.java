package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.StreamBlockCipher;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.util.Arrays;





public class CFBBlockCipher
  extends StreamBlockCipher
{
  private byte[] IV;
  private byte[] cfbV;
  private byte[] cfbOutV;
  private byte[] inBuf;
  private int blockSize;
  private BlockCipher cipher = null;
  


  private boolean encrypting;
  


  private int byteCount;
  



  public CFBBlockCipher(BlockCipher cipher, int bitBlockSize)
  {
    super(cipher);
    
    this.cipher = cipher;
    blockSize = (bitBlockSize / 8);
    
    IV = new byte[cipher.getBlockSize()];
    cfbV = new byte[cipher.getBlockSize()];
    cfbOutV = new byte[cipher.getBlockSize()];
    inBuf = new byte[blockSize];
  }
  













  public void init(boolean encrypting, CipherParameters params)
    throws IllegalArgumentException
  {
    this.encrypting = encrypting;
    
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
    return cipher.getAlgorithmName() + "/CFB" + blockSize * 8;
  }
  
  protected byte calculateByte(byte in)
    throws DataLengthException, IllegalStateException
  {
    return encrypting ? encryptByte(in) : decryptByte(in);
  }
  
  private byte encryptByte(byte in)
  {
    if (byteCount == 0)
    {
      cipher.processBlock(cfbV, 0, cfbOutV, 0);
    }
    
    byte rv = (byte)(cfbOutV[byteCount] ^ in);
    inBuf[(byteCount++)] = rv;
    
    if (byteCount == blockSize)
    {
      byteCount = 0;
      
      System.arraycopy(cfbV, blockSize, cfbV, 0, cfbV.length - blockSize);
      System.arraycopy(inBuf, 0, cfbV, cfbV.length - blockSize, blockSize);
    }
    
    return rv;
  }
  
  private byte decryptByte(byte in)
  {
    if (byteCount == 0)
    {
      cipher.processBlock(cfbV, 0, cfbOutV, 0);
    }
    
    inBuf[byteCount] = in;
    byte rv = (byte)(cfbOutV[(byteCount++)] ^ in);
    
    if (byteCount == blockSize)
    {
      byteCount = 0;
      
      System.arraycopy(cfbV, blockSize, cfbV, 0, cfbV.length - blockSize);
      System.arraycopy(inBuf, 0, cfbV, cfbV.length - blockSize, blockSize);
    }
    
    return rv;
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
  
















  public int encryptBlock(byte[] in, int inOff, byte[] out, int outOff)
    throws DataLengthException, IllegalStateException
  {
    processBytes(in, inOff, blockSize, out, outOff);
    
    return blockSize;
  }
  
















  public int decryptBlock(byte[] in, int inOff, byte[] out, int outOff)
    throws DataLengthException, IllegalStateException
  {
    processBytes(in, inOff, blockSize, out, outOff);
    
    return blockSize;
  }
  





  public byte[] getCurrentIV()
  {
    return Arrays.clone(cfbV);
  }
  




  public void reset()
  {
    System.arraycopy(IV, 0, cfbV, 0, IV.length);
    Arrays.fill(inBuf, (byte)0);
    byteCount = 0;
    
    cipher.reset();
  }
}
