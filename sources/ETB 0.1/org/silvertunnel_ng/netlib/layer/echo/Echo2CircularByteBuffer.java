package org.silvertunnel_ng.netlib.layer.echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;














































































public class Echo2CircularByteBuffer
{
  private static final int DEFAULT_SIZE = 1024;
  public static final int INFINITE_SIZE = -1;
  protected byte[] buffer;
  protected volatile int readPosition = 0;
  




  protected volatile int writePosition = 0;
  




  protected volatile int markPosition = 0;
  





  protected volatile int markSize = 0;
  




  protected volatile boolean infinite = false;
  





  protected boolean blockingWrite = true;
  




  protected InputStream in = new CircularByteBufferInputStream();
  




  protected boolean inputStreamClosed = false;
  




  protected OutputStream out = new CircularByteBufferOutputStream();
  




  protected boolean outputStreamClosed = false;
  







  public void clear()
  {
    synchronized (this)
    {
      readPosition = 0;
      writePosition = 0;
      markPosition = 0;
      outputStreamClosed = false;
      inputStreamClosed = false;
    }
  }
  













  public OutputStream getOutputStream()
  {
    return out;
  }
  









  public InputStream getInputStream()
  {
    return in;
  }
  











  public int getAvailable()
  {
    synchronized (this)
    {
      return available();
    }
  }
  











  public int getSpaceLeft()
  {
    synchronized (this)
    {
      return spaceLeft();
    }
  }
  











  public int getSize()
  {
    synchronized (this)
    {
      return buffer.length;
    }
  }
  





  private void resize()
  {
    byte[] newBuffer = new byte[buffer.length * 2];
    int marked = marked();
    int available = available();
    if (markPosition <= writePosition)
    {



      int length = writePosition - markPosition;
      System.arraycopy(buffer, markPosition, newBuffer, 0, length);
    }
    else
    {
      int length1 = buffer.length - markPosition;
      System.arraycopy(buffer, markPosition, newBuffer, 0, length1);
      int length2 = writePosition;
      System.arraycopy(buffer, 0, newBuffer, length1, length2);
    }
    buffer = newBuffer;
    markPosition = 0;
    readPosition = marked;
    writePosition = (marked + available);
  }
  





  private int spaceLeft()
  {
    if (writePosition < markPosition)
    {




      return markPosition - writePosition - 1;
    }
    
    return buffer.length - 1 - (writePosition - markPosition);
  }
  





  private int available()
  {
    if (readPosition <= writePosition)
    {




      return writePosition - readPosition;
    }
    
    return buffer.length - (readPosition - writePosition);
  }
  





  private int marked()
  {
    if (markPosition <= readPosition)
    {




      return readPosition - markPosition;
    }
    
    return buffer.length - (markPosition - readPosition);
  }
  






  private void ensureMark()
  {
    if (marked() >= markSize)
    {
      markPosition = readPosition;
      markSize = 0;
    }
  }
  






  public Echo2CircularByteBuffer()
  {
    this(1024, true);
  }
  
















  public Echo2CircularByteBuffer(int size)
  {
    this(size, true);
  }
  









  public Echo2CircularByteBuffer(boolean blockingWrite)
  {
    this(1024, blockingWrite);
  }
  


















  public Echo2CircularByteBuffer(int size, boolean blockingWrite)
  {
    if (size == -1)
    {
      buffer = new byte['Ѐ'];
      infinite = true;
    }
    else
    {
      buffer = new byte[size];
      infinite = false;
    }
    this.blockingWrite = blockingWrite;
  }
  








  protected class CircularByteBufferInputStream
    extends InputStream
  {
    protected CircularByteBufferInputStream() {}
    








    public int available()
      throws IOException
    {
      synchronized (Echo2CircularByteBuffer.this)
      {
        if (inputStreamClosed)
        {
          throw new IOException("InputStream has been closed, it is not ready.");
        }
        
        return Echo2CircularByteBuffer.this.available();
      }
    }
    











    public void close()
      throws IOException
    {
      synchronized (Echo2CircularByteBuffer.this)
      {
        inputStreamClosed = true;
      }
    }
    















    public void mark(int readAheadLimit)
    {
      synchronized (Echo2CircularByteBuffer.this)
      {


        if (buffer.length - 1 > readAheadLimit)
        {
          markSize = readAheadLimit;
          markPosition = readPosition;
        }
      }
    }
    








    public boolean markSupported()
    {
      return true;
    }
    











    public int read()
      throws IOException
    {
      for (;;)
      {
        synchronized (Echo2CircularByteBuffer.this)
        {
          if (inputStreamClosed)
          {
            throw new IOException("InputStream has been closed; cannot read from a closed InputStream.");
          }
          

          int available = Echo2CircularByteBuffer.this.available();
          if (available > 0)
          {
            int result = buffer[readPosition] & 0xFF;
            readPosition += 1;
            if (readPosition == buffer.length)
            {
              readPosition = 0;
            }
            Echo2CircularByteBuffer.this.ensureMark();
            return result;
          }
          if (outputStreamClosed)
          {
            return -1;
          }
        }
        try
        {
          Thread.sleep(100L);
        }
        catch (Exception x)
        {
          throw new IOException("Blocking read operation interrupted.");
        }
      }
    }
    














    public int read(byte[] cbuf)
      throws IOException
    {
      return read(cbuf, 0, cbuf.length);
    }
    


















    public int read(byte[] cbuf, int off, int len)
      throws IOException
    {
      for (;;)
      {
        synchronized (Echo2CircularByteBuffer.this)
        {
          if (inputStreamClosed)
          {
            throw new IOException("InputStream has been closed; cannot read from a closed InputStream.");
          }
          

          int available = Echo2CircularByteBuffer.this.available();
          if (available > 0)
          {
            int length = Math.min(len, available);
            int firstLen = Math.min(length, buffer.length - readPosition);
            
            int secondLen = length - firstLen;
            System.arraycopy(buffer, readPosition, cbuf, off, firstLen);
            
            if (secondLen > 0)
            {
              System.arraycopy(buffer, 0, cbuf, off + firstLen, secondLen);
              
              readPosition = secondLen;
            }
            else
            {
              readPosition += length;
            }
            if (readPosition == buffer.length)
            {
              readPosition = 0;
            }
            Echo2CircularByteBuffer.this.ensureMark();
            return length;
          }
          if (outputStreamClosed)
          {
            return -1;
          }
        }
        try
        {
          Thread.sleep(100L);
        }
        catch (Exception x)
        {
          throw new IOException("Blocking read operation interrupted.");
        }
      }
    }
    












    public void reset()
      throws IOException
    {
      synchronized (Echo2CircularByteBuffer.this)
      {
        if (inputStreamClosed)
        {
          throw new IOException("InputStream has been closed; cannot reset a closed InputStream.");
        }
        
        readPosition = markPosition;
      }
    }
    














    public long skip(long n)
      throws IOException, IllegalArgumentException
    {
      for (;;)
      {
        synchronized (Echo2CircularByteBuffer.this)
        {
          if (inputStreamClosed)
          {
            throw new IOException("InputStream has been closed; cannot skip bytes on a closed InputStream.");
          }
          

          int available = Echo2CircularByteBuffer.this.available();
          if (available > 0)
          {
            int length = Math.min((int)n, available);
            int firstLen = Math.min(length, buffer.length - readPosition);
            
            int secondLen = length - firstLen;
            if (secondLen > 0)
            {
              readPosition = secondLen;
            }
            else
            {
              readPosition += length;
            }
            if (readPosition == buffer.length)
            {
              readPosition = 0;
            }
            Echo2CircularByteBuffer.this.ensureMark();
            return length;
          }
          if (outputStreamClosed)
          {
            return 0L;
          }
        }
        try
        {
          Thread.sleep(100L);
        }
        catch (Exception x)
        {
          throw new IOException("Blocking read operation interrupted.");
        }
      }
    }
  }
  









  protected class CircularByteBufferOutputStream
    extends OutputStream
  {
    protected CircularByteBufferOutputStream() {}
    









    public void close()
      throws IOException
    {
      synchronized (Echo2CircularByteBuffer.this)
      {
        if (!outputStreamClosed)
        {
          flush();
        }
        outputStreamClosed = true;
      }
    }
    















    public void flush()
      throws IOException
    {}
    















    public void write(byte[] cbuf)
      throws IOException
    {
      write(cbuf, 0, cbuf.length);
    }
    




















    public void write(byte[] cbuf, int off, int len)
      throws IOException
    {
      while (len > 0)
      {
        synchronized (Echo2CircularByteBuffer.this)
        {
          if (outputStreamClosed)
          {
            throw new IOException("OutputStream has been closed; cannot write to a closed OutputStream.");
          }
          
          if (inputStreamClosed)
          {
            throw new IOException("Buffer closed by InputStream; cannot write to a closed buffer.");
          }
          
          int spaceLeft = Echo2CircularByteBuffer.this.spaceLeft();
          while ((infinite) && (spaceLeft < len))
          {
            Echo2CircularByteBuffer.this.resize();
            spaceLeft = Echo2CircularByteBuffer.this.spaceLeft();
          }
          if ((!blockingWrite) && (spaceLeft < len))
          {
            throw new IOException("CircularByteBuffer is full; cannot write " + len + " bytes");
          }
          

          int realLen = Math.min(len, spaceLeft);
          int firstLen = Math.min(realLen, buffer.length - writePosition);
          
          int secondLen = Math.min(realLen - firstLen, buffer.length - markPosition - 1);
          
          int written = firstLen + secondLen;
          if (firstLen > 0)
          {
            System.arraycopy(cbuf, off, buffer, writePosition, firstLen);
          }
          
          if (secondLen > 0)
          {
            System.arraycopy(cbuf, off + firstLen, buffer, 0, secondLen);
            
            writePosition = secondLen;
          }
          else
          {
            writePosition += written;
          }
          if (writePosition == buffer.length)
          {
            writePosition = 0;
          }
          off += written;
          len -= written;
        }
        if (len > 0)
        {
          try
          {
            Thread.sleep(100L);
          }
          catch (Exception x)
          {
            throw new IOException("Waiting for available space in buffer interrupted.");
          }
        }
      }
    }
    

















    public void write(int c)
      throws IOException
    {
      boolean written = false;
      while (!written)
      {
        synchronized (Echo2CircularByteBuffer.this)
        {
          if (outputStreamClosed)
          {
            throw new IOException("OutputStream has been closed; cannot write to a closed OutputStream.");
          }
          
          if (inputStreamClosed)
          {
            throw new IOException("Buffer closed by InputStream; cannot write to a closed buffer.");
          }
          
          int spaceLeft = Echo2CircularByteBuffer.this.spaceLeft();
          while ((infinite) && (spaceLeft < 1))
          {
            Echo2CircularByteBuffer.this.resize();
            spaceLeft = Echo2CircularByteBuffer.this.spaceLeft();
          }
          if ((!blockingWrite) && (spaceLeft < 1))
          {
            throw new IOException("CircularByteBuffer is full; cannot write 1 byte");
          }
          
          if (spaceLeft > 0)
          {
            buffer[writePosition] = ((byte)(c & 0xFF));
            writePosition += 1;
            if (writePosition == buffer.length)
            {
              writePosition = 0;
            }
            written = true;
          }
        }
        if (!written)
        {
          try
          {
            Thread.sleep(100L);
          }
          catch (Exception x)
          {
            throw new IOException("Waiting for available space in buffer interrupted.");
          }
        }
      }
    }
  }
}
