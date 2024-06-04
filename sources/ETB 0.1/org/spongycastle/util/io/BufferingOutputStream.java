package org.spongycastle.util.io;

import java.io.IOException;
import java.io.OutputStream;
import org.spongycastle.util.Arrays;














public class BufferingOutputStream
  extends OutputStream
{
  private final OutputStream other;
  private final byte[] buf;
  private int bufOff;
  
  public BufferingOutputStream(OutputStream other)
  {
    this.other = other;
    buf = new byte['က'];
  }
  






  public BufferingOutputStream(OutputStream other, int bufferSize)
  {
    this.other = other;
    buf = new byte[bufferSize];
  }
  
  public void write(byte[] bytes, int offset, int len)
    throws IOException
  {
    if (len < buf.length - bufOff)
    {
      System.arraycopy(bytes, offset, buf, bufOff, len);
      bufOff += len;
    }
    else
    {
      int gap = buf.length - bufOff;
      
      System.arraycopy(bytes, offset, buf, bufOff, gap);
      bufOff += gap;
      
      flush();
      
      offset += gap;
      len -= gap;
      while (len >= buf.length)
      {
        other.write(bytes, offset, buf.length);
        offset += buf.length;
        len -= buf.length;
      }
      
      if (len > 0)
      {
        System.arraycopy(bytes, offset, buf, bufOff, len);
        bufOff += len;
      }
    }
  }
  
  public void write(int b)
    throws IOException
  {
    buf[(bufOff++)] = ((byte)b);
    if (bufOff == buf.length)
    {
      flush();
    }
  }
  





  public void flush()
    throws IOException
  {
    other.write(buf, 0, bufOff);
    bufOff = 0;
    Arrays.fill(buf, (byte)0);
  }
  
  public void close()
    throws IOException
  {
    flush();
    other.close();
  }
}
