package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.OutputLengthException;













/**
 * @deprecated
 */
public class PaddedBlockCipher
  extends BufferedBlockCipher
{
  public PaddedBlockCipher(BlockCipher cipher)
  {
    this.cipher = cipher;
    
    buf = new byte[cipher.getBlockSize()];
    bufOff = 0;
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
      return total - buf.length;
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
          throw new OutputLengthException("output buffer too short");
        }
        
        resultLen = cipher.processBlock(buf, 0, out, outOff);
        bufOff = 0;
      }
      



      byte code = (byte)(blockSize - bufOff);
      
      while (bufOff < blockSize)
      {
        buf[bufOff] = code;
        bufOff += 1;
      }
      
      resultLen += cipher.processBlock(buf, 0, out, outOff + resultLen);
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
        throw new DataLengthException("last block incomplete in decryption");
      }
      



      int count = buf[(blockSize - 1)] & 0xFF;
      
      if ((count < 0) || (count > blockSize))
      {
        throw new InvalidCipherTextException("pad block corrupted");
      }
      
      resultLen -= count;
      
      System.arraycopy(buf, 0, out, outOff, resultLen);
    }
    
    reset();
    
    return resultLen;
  }
}
