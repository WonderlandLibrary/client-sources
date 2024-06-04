package org.spongycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;








public class ByteQueue
{
  private static final int DEFAULT_CAPACITY = 1024;
  private byte[] databuf;
  
  public static int nextTwoPow(int i)
  {
    i |= i >> 1;
    i |= i >> 2;
    i |= i >> 4;
    i |= i >> 8;
    i |= i >> 16;
    return i + 1;
  }
  













  private int skipped = 0;
  



  private int available = 0;
  
  private boolean readOnlyBuf = false;
  
  public ByteQueue()
  {
    this(1024);
  }
  
  public ByteQueue(int capacity)
  {
    databuf = (capacity == 0 ? TlsUtils.EMPTY_BYTES : new byte[capacity]);
  }
  
  public ByteQueue(byte[] buf, int off, int len)
  {
    databuf = buf;
    skipped = off;
    available = len;
    readOnlyBuf = true;
  }
  







  public void addData(byte[] buf, int off, int len)
  {
    if (readOnlyBuf)
    {
      throw new IllegalStateException("Cannot add data to read-only buffer");
    }
    
    if (skipped + available + len > databuf.length)
    {
      int desiredSize = nextTwoPow(available + len);
      if (desiredSize > databuf.length)
      {
        byte[] tmp = new byte[desiredSize];
        System.arraycopy(databuf, skipped, tmp, 0, available);
        databuf = tmp;
      }
      else
      {
        System.arraycopy(databuf, skipped, databuf, 0, available);
      }
      skipped = 0;
    }
    
    System.arraycopy(buf, off, databuf, skipped + available, len);
    available += len;
  }
  



  public int available()
  {
    return available;
  }
  





  public void copyTo(OutputStream output, int length)
    throws IOException
  {
    if (length > available)
    {
      throw new IllegalStateException("Cannot copy " + length + " bytes, only got " + available);
    }
    
    output.write(databuf, skipped, length);
  }
  








  public void read(byte[] buf, int offset, int len, int skip)
  {
    if (buf.length - offset < len)
    {
      throw new IllegalArgumentException("Buffer size of " + buf.length + " is too small for a read of " + len + " bytes");
    }
    
    if (available - skip < len)
    {
      throw new IllegalStateException("Not enough data to read");
    }
    System.arraycopy(databuf, skipped + skip, buf, offset, len);
  }
  





  public ByteArrayInputStream readFrom(int length)
  {
    if (length > available)
    {
      throw new IllegalStateException("Cannot read " + length + " bytes, only got " + available);
    }
    
    int position = skipped;
    
    available -= length;
    skipped += length;
    
    return new ByteArrayInputStream(databuf, position, length);
  }
  





  public void removeData(int i)
  {
    if (i > available)
    {
      throw new IllegalStateException("Cannot remove " + i + " bytes, only got " + available);
    }
    



    available -= i;
    skipped += i;
  }
  








  public void removeData(byte[] buf, int off, int len, int skip)
  {
    read(buf, off, len, skip);
    removeData(skip + len);
  }
  
  public byte[] removeData(int len, int skip)
  {
    byte[] buf = new byte[len];
    removeData(buf, 0, len, skip);
    return buf;
  }
  
  public void shrink()
  {
    if (available == 0)
    {
      databuf = TlsUtils.EMPTY_BYTES;
      skipped = 0;
    }
    else
    {
      int desiredSize = nextTwoPow(available);
      if (desiredSize < databuf.length)
      {
        byte[] tmp = new byte[desiredSize];
        System.arraycopy(databuf, skipped, tmp, 0, available);
        databuf = tmp;
        skipped = 0;
      }
    }
  }
}
