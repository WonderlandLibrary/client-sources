package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.OutputLengthException;





















public class NISTCTSBlockCipher
  extends BufferedBlockCipher
{
  public static final int CS1 = 1;
  public static final int CS2 = 2;
  public static final int CS3 = 3;
  private final int type;
  private final int blockSize;
  
  public NISTCTSBlockCipher(int type, BlockCipher cipher)
  {
    this.type = type;
    this.cipher = new CBCBlockCipher(cipher);
    
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
      if (bufOff < blockSize)
      {
        throw new DataLengthException("need at least one block of input for NISTCTS");
      }
      
      if (bufOff > blockSize)
      {
        byte[] lastBlock = new byte[blockSize];
        
        if ((type == 2) || (type == 3))
        {
          cipher.processBlock(buf, 0, block, 0);
          
          System.arraycopy(buf, blockSize, lastBlock, 0, len);
          
          cipher.processBlock(lastBlock, 0, lastBlock, 0);
          
          if ((type == 2) && (len == blockSize))
          {
            System.arraycopy(block, 0, out, outOff, blockSize);
            
            System.arraycopy(lastBlock, 0, out, outOff + blockSize, len);
          }
          else
          {
            System.arraycopy(lastBlock, 0, out, outOff, blockSize);
            
            System.arraycopy(block, 0, out, outOff + blockSize, len);
          }
        }
        else
        {
          System.arraycopy(buf, 0, block, 0, blockSize);
          cipher.processBlock(block, 0, block, 0);
          System.arraycopy(block, 0, out, outOff, len);
          
          System.arraycopy(buf, bufOff - len, lastBlock, 0, len);
          cipher.processBlock(lastBlock, 0, lastBlock, 0);
          System.arraycopy(lastBlock, 0, out, outOff + len, blockSize);
        }
      }
      else
      {
        cipher.processBlock(buf, 0, block, 0);
        
        System.arraycopy(block, 0, out, outOff, blockSize);
      }
    }
    else
    {
      if (bufOff < blockSize)
      {
        throw new DataLengthException("need at least one block of input for CTS");
      }
      
      byte[] lastBlock = new byte[blockSize];
      
      if (bufOff > blockSize)
      {
        if ((type == 3) || ((type == 2) && ((buf.length - bufOff) % blockSize != 0)))
        {
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
        else
        {
          BlockCipher c = ((CBCBlockCipher)cipher).getUnderlyingCipher();
          
          c.processBlock(buf, bufOff - blockSize, lastBlock, 0);
          
          System.arraycopy(buf, 0, block, 0, blockSize);
          
          if (len != blockSize)
          {
            System.arraycopy(lastBlock, len, block, len, blockSize - len);
          }
          
          cipher.processBlock(block, 0, block, 0);
          
          System.arraycopy(block, 0, out, outOff, blockSize);
          
          for (int i = 0; i != len; i++)
          {
            int tmp635_633 = i; byte[] tmp635_631 = lastBlock;tmp635_631[tmp635_633] = ((byte)(tmp635_631[tmp635_633] ^ buf[i]));
          }
          
          System.arraycopy(lastBlock, 0, out, outOff + blockSize, len);
        }
      }
      else
      {
        cipher.processBlock(buf, 0, block, 0);
        
        System.arraycopy(block, 0, out, outOff, blockSize);
      }
    }
    
    int offset = bufOff;
    
    reset();
    
    return offset;
  }
}
