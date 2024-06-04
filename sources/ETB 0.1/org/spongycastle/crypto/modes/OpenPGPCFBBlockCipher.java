package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;


















public class OpenPGPCFBBlockCipher
  implements BlockCipher
{
  private byte[] IV;
  private byte[] FR;
  private byte[] FRE;
  private BlockCipher cipher;
  private int count;
  private int blockSize;
  private boolean forEncryption;
  
  public OpenPGPCFBBlockCipher(BlockCipher cipher)
  {
    this.cipher = cipher;
    
    blockSize = cipher.getBlockSize();
    IV = new byte[blockSize];
    FR = new byte[blockSize];
    FRE = new byte[blockSize];
  }
  





  public BlockCipher getUnderlyingCipher()
  {
    return cipher;
  }
  






  public String getAlgorithmName()
  {
    return cipher.getAlgorithmName() + "/OpenPGPCFB";
  }
  





  public int getBlockSize()
  {
    return cipher.getBlockSize();
  }
  

















  public int processBlock(byte[] in, int inOff, byte[] out, int outOff)
    throws DataLengthException, IllegalStateException
  {
    return forEncryption ? encryptBlock(in, inOff, out, outOff) : decryptBlock(in, inOff, out, outOff);
  }
  




  public void reset()
  {
    count = 0;
    
    System.arraycopy(IV, 0, FR, 0, FR.length);
    
    cipher.reset();
  }
  













  public void init(boolean forEncryption, CipherParameters params)
    throws IllegalArgumentException
  {
    this.forEncryption = forEncryption;
    
    reset();
    
    cipher.init(true, params);
  }
  






  private byte encryptByte(byte data, int blockOff)
  {
    return (byte)(FRE[blockOff] ^ data);
  }
  
















  private int encryptBlock(byte[] in, int inOff, byte[] out, int outOff)
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
    
    if (count > blockSize)
    {
      byte tmp80_77 = encryptByte(in[inOff], blockSize - 2);out[outOff] = tmp80_77;FR[(blockSize - 2)] = tmp80_77; byte 
        tmp113_110 = encryptByte(in[(inOff + 1)], blockSize - 1);out[(outOff + 1)] = tmp113_110;FR[(blockSize - 1)] = tmp113_110;
      
      cipher.processBlock(FR, 0, FRE, 0);
      
      for (int n = 2; n < blockSize; n++)
      {
        byte tmp176_173 = encryptByte(in[(inOff + n)], n - 2);out[(outOff + n)] = tmp176_173;FR[(n - 2)] = tmp176_173;
      }
    }
    else if (count == 0)
    {
      cipher.processBlock(FR, 0, FRE, 0);
      
      for (int n = 0; n < blockSize; n++)
      {
        byte tmp251_248 = encryptByte(in[(inOff + n)], n);out[(outOff + n)] = tmp251_248;FR[n] = tmp251_248;
      }
      
      count += blockSize;
    }
    else if (count == blockSize)
    {
      cipher.processBlock(FR, 0, FRE, 0);
      
      out[outOff] = encryptByte(in[inOff], 0);
      out[(outOff + 1)] = encryptByte(in[(inOff + 1)], 1);
      



      System.arraycopy(FR, 2, FR, 0, blockSize - 2);
      System.arraycopy(out, outOff, FR, blockSize - 2, 2);
      
      cipher.processBlock(FR, 0, FRE, 0);
      
      for (int n = 2; n < blockSize; n++)
      {
        byte tmp431_428 = encryptByte(in[(inOff + n)], n - 2);out[(outOff + n)] = tmp431_428;FR[(n - 2)] = tmp431_428;
      }
      
      count += blockSize;
    }
    
    return blockSize;
  }
  
















  private int decryptBlock(byte[] in, int inOff, byte[] out, int outOff)
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
    
    if (count > blockSize)
    {
      byte inVal = in[inOff];
      FR[(blockSize - 2)] = inVal;
      out[outOff] = encryptByte(inVal, blockSize - 2);
      
      inVal = in[(inOff + 1)];
      FR[(blockSize - 1)] = inVal;
      out[(outOff + 1)] = encryptByte(inVal, blockSize - 1);
      
      cipher.processBlock(FR, 0, FRE, 0);
      
      for (int n = 2; n < blockSize; n++)
      {
        inVal = in[(inOff + n)];
        FR[(n - 2)] = inVal;
        out[(outOff + n)] = encryptByte(inVal, n - 2);
      }
    }
    else if (count == 0)
    {
      cipher.processBlock(FR, 0, FRE, 0);
      
      for (int n = 0; n < blockSize; n++)
      {
        FR[n] = in[(inOff + n)];
        out[n] = encryptByte(in[(inOff + n)], n);
      }
      
      count += blockSize;
    }
    else if (count == blockSize)
    {
      cipher.processBlock(FR, 0, FRE, 0);
      
      byte inVal1 = in[inOff];
      byte inVal2 = in[(inOff + 1)];
      out[outOff] = encryptByte(inVal1, 0);
      out[(outOff + 1)] = encryptByte(inVal2, 1);
      
      System.arraycopy(FR, 2, FR, 0, blockSize - 2);
      
      FR[(blockSize - 2)] = inVal1;
      FR[(blockSize - 1)] = inVal2;
      
      cipher.processBlock(FR, 0, FRE, 0);
      
      for (int n = 2; n < blockSize; n++)
      {
        byte inVal = in[(inOff + n)];
        FR[(n - 2)] = inVal;
        out[(outOff + n)] = encryptByte(inVal, n - 2);
      }
      
      count += blockSize;
    }
    
    return blockSize;
  }
}
