package org.silvertunnel_ng.netlib.api.impl;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
























public class SocketTimeoutInputStream
  extends FilterInputStream
{
  private static final Logger LOG = LoggerFactory.getLogger(SocketTimeoutInputStream.class);
  

  private long timeout;
  

  volatile boolean closeRequestedByServiceUser;
  

  volatile boolean waitingForClose;
  

  private final SocketTimeoutInputStreamThread thread;
  
  private static final int BUFFER_SIZE = 4096;
  
  final transient byte[] buffer = new byte['က'];
  
  transient int bufferHead = 0;
  
  transient int bufferLen = 0;
  





  volatile IOException lastPendingIOException;
  





  public SocketTimeoutInputStream(InputStream in, long timeout)
  {
    super(in);
    this.timeout = timeout;
    
    thread = new SocketTimeoutInputStreamThread(this, in);
    thread.start();
  }
  







  public synchronized void setTimeout(long timeout)
  {
    this.timeout = timeout;
  }
  










  public void close()
    throws SocketTimeoutException, IOException
  {
    super.close();
    Thread oldThread; synchronized (this)
    {
      if (thread == null)
      {
        return;
      }
      
      oldThread = thread;
      closeRequestedByServiceUser = true;
      thread.interrupt();
      throwLastPendingIOException();
    }
    if (timeout == -1L)
    {
      return;
    }
    try
    {
      oldThread.join(timeout);

    }
    catch (InterruptedException e)
    {
      Thread.currentThread().interrupt();
    }
    synchronized (this)
    {
      throwLastPendingIOException();
      if (thread != null)
      {
        throw new SocketTimeoutException();
      }
    }
  }
  




  public synchronized int available()
    throws IOException
  {
    if (bufferLen == 0)
    {
      throwLastPendingIOException();
    }
    return bufferLen > 0 ? bufferLen : 0;
  }
  
  public synchronized int read()
    throws IOException
  {
    int ONE = 1;
    byte[] buffer1 = new byte[1];
    
    int len = read(buffer1, 0, 1);
    if (len < 1)
    {

      return -1;
    }
    


    return buffer1[0] & 0xFF;
  }
  


  public synchronized int read(byte[] buf, int off, int len)
    throws IOException
  {
    if (!waitUntilBufferIsFilled())
    {

      return -1;
    }
    int pos = off;
    if (len > bufferLen)
    {
      len = bufferLen;
    }
    while (len-- > 0)
    {
      buf[(pos++)] = buffer[(bufferHead++)];
      if (bufferHead == buffer.length)
      {
        bufferHead = 0;
      }
      bufferLen -= 1;
    }
    notify();
    return pos - off;
  }
  
  public synchronized long skip(long count)
    throws IOException
  {
    long amount = 0L;
    try
    {
      do
      {
        if (!waitUntilBufferIsFilled()) {
          break;
        }
        

        int skip = (int)Math.min(count - amount, bufferLen);
        bufferHead = ((bufferHead + skip) % buffer.length);
        bufferLen -= skip;
        amount += skip;
      }
      while (amount < count);
    }
    catch (SocketTimeoutException e)
    {
      bytesTransferred = ((int)amount);
      throw e;
    }
    notify();
    return amount;
  }
  




  public boolean markSupported()
  {
    return false;
  }
  












  private boolean waitUntilBufferIsFilled()
    throws IOException, SocketTimeoutException
  {
    if (bufferLen != 0)
    {

      return true;
    }
    

    throwLastPendingIOException();
    if (waitingForClose)
    {
      return false;
    }
    notify();
    try
    {
      wait(timeout);
    }
    catch (InterruptedException e)
    {
      LOG.debug("got InterruptedException : {}", e, e);
      
      Thread.currentThread().interrupt();
    }
    throwLastPendingIOException();
    if (bufferLen != 0)
    {

      return true;
    }
    if (waitingForClose)
    {

      return false;
    }
    throw new SocketTimeoutException();
  }
  



  private void throwLastPendingIOException()
    throws IOException
  {
    if (lastPendingIOException != null)
    {
      IOException e = lastPendingIOException;
      lastPendingIOException = null;
      throw e;
    }
  }
}
