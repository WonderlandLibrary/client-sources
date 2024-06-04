package org.spongycastle.crypto.paddings;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.ParametersWithRandom;
















public class PaddedBufferedBlockCipher
  extends BufferedBlockCipher
{
  BlockCipherPadding padding;
  
  public PaddedBufferedBlockCipher(BlockCipher cipher, BlockCipherPadding padding)
  {
    this.cipher = cipher;
    this.padding = padding;
    
    buf = new byte[cipher.getBlockSize()];
    bufOff = 0;
  }
  






  public PaddedBufferedBlockCipher(BlockCipher cipher)
  {
    this(cipher, new PKCS7Padding());
  }
  











  public void init(boolean forEncryption, CipherParameters params)
    throws IllegalArgumentException
  {
    this.forEncryption = forEncryption;
    
    reset();
    
    if ((params instanceof ParametersWithRandom))
    {
      ParametersWithRandom p = (ParametersWithRandom)params;
      
      padding.init(p.getRandom());
      
      cipher.init(forEncryption, p.getParameters());
    }
    else
    {
      padding.init(null);
      
      cipher.init(forEncryption, params);
    }
  }
  









  public int getOutputSize(int len)
  {
    int total = len + bufOff;
    int leftOver = total % buf.length;
    
    if (leftOver == 0)
    {
      if (forEncryption)
      {
        return total + buf.length;
      }
      
      return total;
    }
    
    return total - leftOver + buf.length;
  }
  









  public int getUpdateOutputSize(int len)
  {
    int total = len + bufOff;
    int leftOver = total % buf.length;
    
    if (leftOver == 0)
    {
      return Math.max(0, total - buf.length);
    }
    
    return total - leftOver;
  }
  













  public int processByte(byte in, byte[] out, int outOff)
    throws DataLengthException, IllegalStateException
  {
    int resultLen = 0;
    
    if (bufOff == buf.length)
    {
      resultLen = cipher.processBlock(buf, 0, out, outOff);
      bufOff = 0;
    }
    
    buf[(bufOff++)] = in;
    
    return resultLen;
  }
  

















  public int processBytes(byte[] in, int inOff, int len, byte[] out, int outOff)
    throws DataLengthException, IllegalStateException
  {
    if (len < 0)
    {
      throw new IllegalArgumentException("Can't have a negative input length!");
    }
    
    int blockSize = getBlockSize();
    int length = getUpdateOutputSize(len);
    
    if (length > 0)
    {
      if (outOff + length > out.length)
      {
        throw new OutputLengthException("output buffer too short");
      }
    }
    
    int resultLen = 0;
    int gapLen = buf.length - bufOff;
    
    if (len > gapLen)
    {
      System.arraycopy(in, inOff, buf, bufOff, gapLen);
      
      resultLen += cipher.processBlock(buf, 0, out, outOff);
      
      bufOff = 0;
      len -= gapLen;
      inOff += gapLen;
      
      while (len > buf.length)
      {
        resultLen += cipher.processBlock(in, inOff, out, outOff + resultLen);
        
        len -= blockSize;
        inOff += blockSize;
      }
    }
    
    System.arraycopy(in, inOff, buf, bufOff, len);
    
    bufOff += len;
    
    return resultLen;
  }
  
















  public int doFinal(byte[] out, int outOff)
    throws DataLengthException, IllegalStateException, InvalidCipherTextException
  {
    int blockSize = cipher.getBlockSize();
    int resultLen = 0;
    
    if (forEncryption)
    {
      if (bufOff == blockSize)
      {
        if (outOff + 2 * blockSize > out.length)
        {
          reset();
          
          throw new OutputLengthException("output buffer too short");
        }
        
        resultLen = cipher.processBlock(buf, 0, out, outOff);
        bufOff = 0;
      }
      
      padding.addPadding(buf, bufOff);
      
      resultLen += cipher.processBlock(buf, 0, out, outOff + resultLen);
      
      reset();
    }
    else
    {
      if (bufOff == blockSize)
      {
        resultLen = cipher.processBlock(buf, 0, buf, 0);
        bufOff = 0;
      }
      else
      {
        reset();
        
        throw new DataLengthException("last block incomplete in decryption");
      }
      
      try
      {
        resultLen -= padding.padCount(buf);
        
        System.arraycopy(buf, 0, out, outOff, resultLen);
      }
      finally
      {
        reset();
      }
    }
    
    return resultLen;
  }
}
