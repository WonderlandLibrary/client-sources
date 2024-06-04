package org.silvertunnel_ng.netlib.adapter.url.impl.net.http;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;































public class MeteredStream
  extends FilterInputStream
{
  protected boolean closed = false;
  protected long expected;
  protected long count = 0L;
  protected long markedCount = 0L;
  protected int markLimit = -1;
  protected ProgressSource pi;
  
  public MeteredStream(InputStream is, ProgressSource pi, long expected)
  {
    super(is);
    
    this.pi = pi;
    this.expected = expected;
    
    if (pi != null)
    {
      pi.updateProgress(0L, (int)expected);
    }
  }
  
  private final void justRead(long n) throws IOException
  {
    if (n == -1L)
    {





      if (!isMarked())
      {
        close();
      }
      return;
    }
    
    count += n;
    



    if (count - markedCount > markLimit)
    {
      markLimit = -1;
    }
    
    if (pi != null)
    {
      pi.updateProgress((int)count, (int)expected);
    }
    
    if (isMarked())
    {
      return;
    }
    


    if (expected > 0L)
    {
      if (count >= expected)
      {
        close();
      }
    }
  }
  




  private boolean isMarked()
  {
    if (markLimit < 0)
    {
      return false;
    }
    

    if (count - markedCount > markLimit)
    {
      return false;
    }
    

    return true;
  }
  
  public synchronized int read()
    throws IOException
  {
    if (closed)
    {
      return -1;
    }
    int c = in.read();
    if (c != -1)
    {
      justRead(1L);
    }
    else
    {
      justRead(c);
    }
    return c;
  }
  

  public synchronized int read(byte[] b, int off, int len)
    throws IOException
  {
    if (closed)
    {
      return -1;
    }
    int n = in.read(b, off, len);
    justRead(n);
    return n;
  }
  


  public synchronized long skip(long n)
    throws IOException
  {
    if (closed)
    {
      return 0L;
    }
    
    if ((in instanceof ChunkedInputStream))
    {
      n = in.skip(n);

    }
    else
    {
      long min = n > expected - count ? expected - count : n;
      n = in.skip(min);
    }
    justRead(n);
    return n;
  }
  
  public void close()
    throws IOException
  {
    if (closed)
    {
      return;
    }
    if (pi != null)
    {
      pi.finishTracking();
    }
    
    closed = true;
    in.close();
  }
  
  public synchronized int available()
    throws IOException
  {
    return closed ? 0 : in.available();
  }
  

  public synchronized void mark(int readLimit)
  {
    if (closed)
    {
      return;
    }
    super.mark(readLimit);
    



    markedCount = count;
    markLimit = readLimit;
  }
  
  public synchronized void reset()
    throws IOException
  {
    if (closed)
    {
      return;
    }
    
    if (!isMarked())
    {
      throw new IOException("Resetting to an invalid mark");
    }
    
    count = markedCount;
    super.reset();
  }
  

  public boolean markSupported()
  {
    if (closed)
    {
      return false;
    }
    return super.markSupported();
  }
  
  protected void finalize()
    throws Throwable
  {
    try
    {
      close();
      if (pi != null)
      {
        pi.close();
      }
      



      super.finalize(); } finally { super.finalize();
    }
  }
}
