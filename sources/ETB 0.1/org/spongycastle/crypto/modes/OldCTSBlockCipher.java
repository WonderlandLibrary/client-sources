package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.OutputLengthException;















public class OldCTSBlockCipher
  extends BufferedBlockCipher
{
  private int blockSize;
  
  public OldCTSBlockCipher(BlockCipher cipher)
  {
    if (((cipher instanceof OFBBlockCipher)) || ((cipher instanceof CFBBlockCipher)))
    {
      throw new IllegalArgumentException("CTSBlockCipher can only accept ECB, or CBC ciphers");
    }
    
    this.cipher = cipher;
    
    blockSize = cipher.getBlockSize();
    
    buf = new byte[blockSize * 2];
    bufOff = 0;
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
  









  public int getOutputSize(int len)
  {
    return len + bufOff;
  }
  













  public int processByte(byte in, byte[] out, int outOff)
    throws DataLengthException, IllegalStateException
  {
    int resultLen = 0;
    
    if (bufOff == buf.length)
    {
      resultLen = cipher.processBlock(buf, 0, out, outOff);
      System.arraycopy(buf, blockSize, buf, 0, blockSize);
      
      bufOff = blockSize;
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
      System.arraycopy(buf, blockSize, buf, 0, blockSize);
      
      bufOff = blockSize;
      
      len -= gapLen;
      inOff += gapLen;
      
      while (len > blockSize)
      {
        System.arraycopy(in, inOff, buf, bufOff, blockSize);
        resultLen += cipher.processBlock(buf, 0, out, outOff + resultLen);
        System.arraycopy(buf, blockSize, buf, 0, blockSize);
        
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
    if (bufOff + outOff > out.length)
    {
      throw new OutputLengthException("output buffer to small in doFinal");
    }
    
    int blockSize = cipher.getBlockSize();
    int len = bufOff - blockSize;
    byte[] block = new byte[blockSize];
    
    if (forEncryption)
    {
      cipher.processBlock(buf, 0, block, 0);
      
      if (bufOff < blockSize)
      {
        throw new DataLengthException("need at least one block of input for CTS");
      }
      
      for (int i = bufOff; i != buf.length; i++)
      {
        buf[i] = block[(i - blockSize)];
      }
      
      for (int i = blockSize; i != bufOff; i++)
      {
        int tmp141_139 = i; byte[] tmp141_136 = buf;tmp141_136[tmp141_139] = ((byte)(tmp141_136[tmp141_139] ^ block[(i - blockSize)]));
      }
      
      if ((cipher instanceof CBCBlockCipher))
      {
        BlockCipher c = ((CBCBlockCipher)cipher).getUnderlyingCipher();
        
        c.processBlock(buf, blockSize, out, outOff);
      }
      else
      {
        cipher.processBlock(buf, blockSize, out, outOff);
      }
      
      System.arraycopy(block, 0, out, outOff + blockSize, len);
    }
    else
    {
      byte[] lastBlock = new byte[blockSize];
      
      if ((cipher instanceof CBCBlockCipher))
      {
        BlockCipher c = ((CBCBlockCipher)cipher).getUnderlyingCipher();
        
        c.processBlock(buf, 0, block, 0);
      }
      else
      {
        cipher.processBlock(buf, 0, block, 0);
      }
      
      for (int i = blockSize; i != bufOff; i++)
      {
        lastBlock[(i - blockSize)] = ((byte)(block[(i - blockSize)] ^ buf[i]));
      }
      
      System.arraycopy(buf, blockSize, block, 0, len);
      
      cipher.processBlock(block, 0, out, outOff);
      System.arraycopy(lastBlock, 0, out, outOff + blockSize, len);
    }
    
    int offset = bufOff;
    
    reset();
    
    return offset;
  }
}
