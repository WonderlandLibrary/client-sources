package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.StreamBlockCipher;
import org.spongycastle.crypto.params.ParametersWithIV;






public class GOFBBlockCipher
  extends StreamBlockCipher
{
  private byte[] IV;
  private byte[] ofbV;
  private byte[] ofbOutV;
  private int byteCount;
  private final int blockSize;
  private final BlockCipher cipher;
  boolean firstStep = true;
  

  int N3;
  

  int N4;
  

  static final int C1 = 16843012;
  
  static final int C2 = 16843009;
  

  public GOFBBlockCipher(BlockCipher cipher)
  {
    super(cipher);
    
    this.cipher = cipher;
    blockSize = cipher.getBlockSize();
    
    if (blockSize != 8)
    {
      throw new IllegalArgumentException("GCTR only for 64 bit block ciphers");
    }
    
    IV = new byte[cipher.getBlockSize()];
    ofbV = new byte[cipher.getBlockSize()];
    ofbOutV = new byte[cipher.getBlockSize()];
  }
  













  public void init(boolean encrypting, CipherParameters params)
    throws IllegalArgumentException
  {
    firstStep = true;
    N3 = 0;
    N4 = 0;
    
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
    return cipher.getAlgorithmName() + "/GCTR";
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
    firstStep = true;
    N3 = 0;
    N4 = 0;
    System.arraycopy(IV, 0, ofbV, 0, IV.length);
    byteCount = 0;
    cipher.reset();
  }
  



  private int bytesToint(byte[] in, int inOff)
  {
    return (in[(inOff + 3)] << 24 & 0xFF000000) + (in[(inOff + 2)] << 16 & 0xFF0000) + (in[(inOff + 1)] << 8 & 0xFF00) + (in[inOff] & 0xFF);
  }
  





  private void intTobytes(int num, byte[] out, int outOff)
  {
    out[(outOff + 3)] = ((byte)(num >>> 24));
    out[(outOff + 2)] = ((byte)(num >>> 16));
    out[(outOff + 1)] = ((byte)(num >>> 8));
    out[outOff] = ((byte)num);
  }
  
  protected byte calculateByte(byte b)
  {
    if (byteCount == 0)
    {
      if (firstStep)
      {
        firstStep = false;
        cipher.processBlock(ofbV, 0, ofbOutV, 0);
        N3 = bytesToint(ofbOutV, 0);
        N4 = bytesToint(ofbOutV, 4);
      }
      N3 += 16843009;
      N4 += 16843012;
      if (N4 < 16843012)
      {
        if (N4 > 0)
        {
          N4 += 1;
        }
      }
      intTobytes(N3, ofbV, 0);
      intTobytes(N4, ofbV, 4);
      
      cipher.processBlock(ofbV, 0, ofbOutV, 0);
    }
    
    byte rv = (byte)(ofbOutV[(byteCount++)] ^ b);
    
    if (byteCount == blockSize)
    {
      byteCount = 0;
      



      System.arraycopy(ofbV, blockSize, ofbV, 0, ofbV.length - blockSize);
      System.arraycopy(ofbOutV, 0, ofbV, ofbV.length - blockSize, blockSize);
    }
    
    return rv;
  }
}
