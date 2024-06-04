package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.SkippingStreamCipher;
import org.spongycastle.crypto.StreamBlockCipher;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Pack;











public class SICBlockCipher
  extends StreamBlockCipher
  implements SkippingStreamCipher
{
  private final BlockCipher cipher;
  private final int blockSize;
  private byte[] IV;
  private byte[] counter;
  private byte[] counterOut;
  private int byteCount;
  
  public SICBlockCipher(BlockCipher c)
  {
    super(c);
    
    cipher = c;
    blockSize = cipher.getBlockSize();
    IV = new byte[blockSize];
    counter = new byte[blockSize];
    counterOut = new byte[blockSize];
    byteCount = 0;
  }
  


  public void init(boolean forEncryption, CipherParameters params)
    throws IllegalArgumentException
  {
    if ((params instanceof ParametersWithIV))
    {
      ParametersWithIV ivParam = (ParametersWithIV)params;
      IV = Arrays.clone(ivParam.getIV());
      
      if (blockSize < IV.length)
      {
        throw new IllegalArgumentException("CTR/SIC mode requires IV no greater than: " + blockSize + " bytes.");
      }
      
      int maxCounterSize = 8 > blockSize / 2 ? blockSize / 2 : 8;
      
      if (blockSize - IV.length > maxCounterSize)
      {
        throw new IllegalArgumentException("CTR/SIC mode requires IV of at least: " + (blockSize - maxCounterSize) + " bytes.");
      }
      

      if (ivParam.getParameters() != null)
      {
        cipher.init(true, ivParam.getParameters());
      }
      
      reset();
    }
    else
    {
      throw new IllegalArgumentException("CTR/SIC mode requires ParametersWithIV");
    }
  }
  
  public String getAlgorithmName()
  {
    return cipher.getAlgorithmName() + "/SIC";
  }
  
  public int getBlockSize()
  {
    return cipher.getBlockSize();
  }
  
  public int processBlock(byte[] in, int inOff, byte[] out, int outOff)
    throws DataLengthException, IllegalStateException
  {
    processBytes(in, inOff, blockSize, out, outOff);
    
    return blockSize;
  }
  
  protected byte calculateByte(byte in)
    throws DataLengthException, IllegalStateException
  {
    if (byteCount == 0)
    {
      cipher.processBlock(counter, 0, counterOut, 0);
      
      return (byte)(counterOut[(byteCount++)] ^ in);
    }
    
    byte rv = (byte)(counterOut[(byteCount++)] ^ in);
    
    if (byteCount == counter.length)
    {
      byteCount = 0;
      
      incrementCounterAt(0);
      
      checkCounter();
    }
    
    return rv;
  }
  

  private void checkCounter()
  {
    if (IV.length < blockSize)
    {
      for (int i = 0; i != IV.length; i++)
      {
        if (counter[i] != IV[i])
        {
          throw new IllegalStateException("Counter in CTR/SIC mode out of range.");
        }
      }
    }
  }
  
  private void incrementCounterAt(int pos)
  {
    int i = counter.length - pos;
    for (;;) { i--; if (i >= 0)
      {
        int tmp20_19 = i;counter;
        













































































































































        if ((tmp20_16[tmp20_19] = (byte)(tmp20_16[tmp20_19] + 1)) != 0) {
          break;
        }
      }
    }
  }
  
  private void incrementCounter(int offSet)
  {
    byte old = counter[(counter.length - 1)]; int 
    
      tmp24_23 = (counter.length - 1); byte[] tmp24_14 = counter;tmp24_14[tmp24_23] = ((byte)(tmp24_14[tmp24_23] + offSet));
    
    if ((old != 0) && (counter[(counter.length - 1)] < old))
    {
      incrementCounterAt(1);
    }
  }
  
  private void decrementCounterAt(int pos)
  {
    int i = counter.length - pos;
    do { i--; if (i < 0)
        break;
    } while ((tmp20_16[tmp20_19] = (byte)(tmp20_16[tmp20_19] - 1)) == -1);
    
    return;
  }
  


  private void adjustCounter(long n)
  {
    if (n >= 0L)
    {
      long numBlocks = (n + byteCount) / blockSize;
      
      long rem = numBlocks;
      if (rem > 255L)
      {
        for (int i = 5; i >= 1; i--)
        {
          long diff = 1L << 8 * i;
          while (rem >= diff)
          {
            incrementCounterAt(i);
            rem -= diff;
          }
        }
      }
      
      incrementCounter((int)rem);
      
      byteCount = ((int)(n + byteCount - blockSize * numBlocks));
    }
    else
    {
      long numBlocks = (-n - byteCount) / blockSize;
      
      long rem = numBlocks;
      if (rem > 255L)
      {
        for (int i = 5; i >= 1; i--)
        {
          long diff = 1L << 8 * i;
          while (rem > diff)
          {
            decrementCounterAt(i);
            rem -= diff;
          }
        }
      }
      
      for (long i = 0L; i != rem; i += 1L)
      {
        decrementCounterAt(0);
      }
      
      int gap = (int)(byteCount + n + blockSize * numBlocks);
      
      if (gap >= 0)
      {
        byteCount = 0;
      }
      else
      {
        decrementCounterAt(0);
        byteCount = (blockSize + gap);
      }
    }
  }
  
  public void reset()
  {
    Arrays.fill(counter, (byte)0);
    System.arraycopy(IV, 0, counter, 0, IV.length);
    cipher.reset();
    byteCount = 0;
  }
  
  public long skip(long numberOfBytes)
  {
    adjustCounter(numberOfBytes);
    
    checkCounter();
    
    cipher.processBlock(counter, 0, counterOut, 0);
    
    return numberOfBytes;
  }
  
  public long seekTo(long position)
  {
    reset();
    
    return skip(position);
  }
  
  public long getPosition()
  {
    byte[] res = new byte[counter.length];
    
    System.arraycopy(counter, 0, res, 0, res.length);
    
    for (int i = res.length - 1; i >= 1; i--) {
      int v;
      int v;
      if (i < IV.length)
      {
        v = (res[i] & 0xFF) - (IV[i] & 0xFF);
      }
      else
      {
        v = res[i] & 0xFF;
      }
      
      if (v < 0)
      {
        int tmp77_76 = (i - 1); byte[] tmp77_73 = res;tmp77_73[tmp77_76] = ((byte)(tmp77_73[tmp77_76] - 1));
        v += 256;
      }
      
      res[i] = ((byte)v);
    }
    
    return Pack.bigEndianToLong(res, res.length - 8) * blockSize + byteCount;
  }
}
