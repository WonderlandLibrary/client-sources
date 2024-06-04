package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.ParametersWithIV;
















public class PGPCFBBlockCipher
  implements BlockCipher
{
  private byte[] IV;
  private byte[] FR;
  private byte[] FRE;
  private byte[] tmp;
  private BlockCipher cipher;
  private int count;
  private int blockSize;
  private boolean forEncryption;
  private boolean inlineIv;
  
  public PGPCFBBlockCipher(BlockCipher cipher, boolean inlineIv)
  {
    this.cipher = cipher;
    this.inlineIv = inlineIv;
    
    blockSize = cipher.getBlockSize();
    IV = new byte[blockSize];
    FR = new byte[blockSize];
    FRE = new byte[blockSize];
    tmp = new byte[blockSize];
  }
  





  public BlockCipher getUnderlyingCipher()
  {
    return cipher;
  }
  






  public String getAlgorithmName()
  {
    if (inlineIv)
    {
      return cipher.getAlgorithmName() + "/PGPCFBwithIV";
    }
    

    return cipher.getAlgorithmName() + "/PGPCFB";
  }
  






  public int getBlockSize()
  {
    return cipher.getBlockSize();
  }
  

















  public int processBlock(byte[] in, int inOff, byte[] out, int outOff)
    throws DataLengthException, IllegalStateException
  {
    if (inlineIv)
    {
      return forEncryption ? encryptBlockWithIV(in, inOff, out, outOff) : decryptBlockWithIV(in, inOff, out, outOff);
    }
    

    return forEncryption ? encryptBlock(in, inOff, out, outOff) : decryptBlock(in, inOff, out, outOff);
  }
  





  public void reset()
  {
    count = 0;
    
    for (int i = 0; i != FR.length; i++)
    {
      if (inlineIv)
      {
        FR[i] = 0;
      }
      else
      {
        FR[i] = IV[i];
      }
    }
    
    cipher.reset();
  }
  













  public void init(boolean forEncryption, CipherParameters params)
    throws IllegalArgumentException
  {
    this.forEncryption = forEncryption;
    
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
      
      cipher.init(true, ivParam.getParameters());
    }
    else
    {
      reset();
      
      cipher.init(true, params);
    }
  }
  






  private byte encryptByte(byte data, int blockOff)
  {
    return (byte)(FRE[blockOff] ^ data);
  }
  
















  private int encryptBlockWithIV(byte[] in, int inOff, byte[] out, int outOff)
    throws DataLengthException, IllegalStateException
  {
    if (inOff + blockSize > in.length)
    {
      throw new DataLengthException("input buffer too short");
    }
    
    if (count == 0)
    {
      if (outOff + 2 * blockSize + 2 > out.length)
      {
        throw new OutputLengthException("output buffer too short");
      }
      
      cipher.processBlock(FR, 0, FRE, 0);
      
      for (int n = 0; n < blockSize; n++)
      {
        out[(outOff + n)] = encryptByte(IV[n], n);
      }
      
      System.arraycopy(out, outOff, FR, 0, blockSize);
      
      cipher.processBlock(FR, 0, FRE, 0);
      
      out[(outOff + blockSize)] = encryptByte(IV[(blockSize - 2)], 0);
      out[(outOff + blockSize + 1)] = encryptByte(IV[(blockSize - 1)], 1);
      
      System.arraycopy(out, outOff + 2, FR, 0, blockSize);
      
      cipher.processBlock(FR, 0, FRE, 0);
      
      for (int n = 0; n < blockSize; n++)
      {
        out[(outOff + blockSize + 2 + n)] = encryptByte(in[(inOff + n)], n);
      }
      
      System.arraycopy(out, outOff + blockSize + 2, FR, 0, blockSize);
      
      count += 2 * blockSize + 2;
      
      return 2 * blockSize + 2;
    }
    if (count >= blockSize + 2)
    {
      if (outOff + blockSize > out.length)
      {
        throw new OutputLengthException("output buffer too short");
      }
      
      cipher.processBlock(FR, 0, FRE, 0);
      
      for (int n = 0; n < blockSize; n++)
      {
        out[(outOff + n)] = encryptByte(in[(inOff + n)], n);
      }
      
      System.arraycopy(out, outOff, FR, 0, blockSize);
    }
    
    return blockSize;
  }
  
















  private int decryptBlockWithIV(byte[] in, int inOff, byte[] out, int outOff)
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
    
    if (count == 0)
    {
      for (int n = 0; n < blockSize; n++)
      {
        FR[n] = in[(inOff + n)];
      }
      
      cipher.processBlock(FR, 0, FRE, 0);
      
      count += blockSize;
      
      return 0;
    }
    if (count == blockSize)
    {

      System.arraycopy(in, inOff, tmp, 0, blockSize);
      
      System.arraycopy(FR, 2, FR, 0, blockSize - 2);
      
      FR[(blockSize - 2)] = tmp[0];
      FR[(blockSize - 1)] = tmp[1];
      
      cipher.processBlock(FR, 0, FRE, 0);
      
      for (int n = 0; n < blockSize - 2; n++)
      {
        out[(outOff + n)] = encryptByte(tmp[(n + 2)], n);
      }
      
      System.arraycopy(tmp, 2, FR, 0, blockSize - 2);
      
      count += 2;
      
      return blockSize - 2;
    }
    if (count >= blockSize + 2)
    {

      System.arraycopy(in, inOff, tmp, 0, blockSize);
      
      out[(outOff + 0)] = encryptByte(tmp[0], blockSize - 2);
      out[(outOff + 1)] = encryptByte(tmp[1], blockSize - 1);
      
      System.arraycopy(tmp, 0, FR, blockSize - 2, 2);
      
      cipher.processBlock(FR, 0, FRE, 0);
      
      for (int n = 0; n < blockSize - 2; n++)
      {
        out[(outOff + n + 2)] = encryptByte(tmp[(n + 2)], n);
      }
      
      System.arraycopy(tmp, 2, FR, 0, blockSize - 2);
    }
    

    return blockSize;
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
    
    cipher.processBlock(FR, 0, FRE, 0);
    for (int n = 0; n < blockSize; n++)
    {
      out[(outOff + n)] = encryptByte(in[(inOff + n)], n);
    }
    
    for (int n = 0; n < blockSize; n++)
    {
      FR[n] = out[(outOff + n)];
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
    
    cipher.processBlock(FR, 0, FRE, 0);
    for (int n = 0; n < blockSize; n++)
    {
      out[(outOff + n)] = encryptByte(in[(inOff + n)], n);
    }
    
    for (int n = 0; n < blockSize; n++)
    {
      FR[n] = in[(inOff + n)];
    }
    
    return blockSize;
  }
}
