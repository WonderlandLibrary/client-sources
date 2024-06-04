package org.spongycastle.crypto.digests;

import org.spongycastle.crypto.ExtendedDigest;
import org.spongycastle.util.Memoable;
import org.spongycastle.util.Pack;






public abstract class GeneralDigest
  implements ExtendedDigest, Memoable
{
  private static final int BYTE_LENGTH = 64;
  private final byte[] xBuf = new byte[4];
  

  private int xBufOff;
  
  private long byteCount;
  

  protected GeneralDigest()
  {
    xBufOff = 0;
  }
  





  protected GeneralDigest(GeneralDigest t)
  {
    copyIn(t);
  }
  
  protected GeneralDigest(byte[] encodedState)
  {
    System.arraycopy(encodedState, 0, xBuf, 0, xBuf.length);
    xBufOff = Pack.bigEndianToInt(encodedState, 4);
    byteCount = Pack.bigEndianToLong(encodedState, 8);
  }
  
  protected void copyIn(GeneralDigest t)
  {
    System.arraycopy(xBuf, 0, xBuf, 0, xBuf.length);
    
    xBufOff = xBufOff;
    byteCount = byteCount;
  }
  

  public void update(byte in)
  {
    xBuf[(xBufOff++)] = in;
    
    if (xBufOff == xBuf.length)
    {
      processWord(xBuf, 0);
      xBufOff = 0;
    }
    
    byteCount += 1L;
  }
  



  public void update(byte[] in, int inOff, int len)
  {
    len = Math.max(0, len);
    



    int i = 0;
    if (xBufOff != 0)
    {
      while (i < len)
      {
        xBuf[(xBufOff++)] = in[(inOff + i++)];
        if (xBufOff == 4)
        {
          processWord(xBuf, 0);
          xBufOff = 0;
        }
      }
    }
    




    int limit = (len - i & 0xFFFFFFFC) + i;
    for (; i < limit; i += 4)
    {
      processWord(in, inOff + i);
    }
    



    while (i < len)
    {
      xBuf[(xBufOff++)] = in[(inOff + i++)];
    }
    
    byteCount += len;
  }
  
  public void finish()
  {
    long bitLength = byteCount << 3;
    



    update((byte)Byte.MIN_VALUE);
    
    while (xBufOff != 0)
    {
      update((byte)0);
    }
    
    processLength(bitLength);
    
    processBlock();
  }
  
  public void reset()
  {
    byteCount = 0L;
    
    xBufOff = 0;
    for (int i = 0; i < xBuf.length; i++)
    {
      xBuf[i] = 0;
    }
  }
  
  protected void populateState(byte[] state)
  {
    System.arraycopy(xBuf, 0, state, 0, xBufOff);
    Pack.intToBigEndian(xBufOff, state, 4);
    Pack.longToBigEndian(byteCount, state, 8);
  }
  
  public int getByteLength()
  {
    return 64;
  }
  
  protected abstract void processWord(byte[] paramArrayOfByte, int paramInt);
  
  protected abstract void processLength(long paramLong);
  
  protected abstract void processBlock();
}
