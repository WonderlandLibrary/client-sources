package org.silvertunnel_ng.netlib.api.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;























class SocketTimeoutInputStreamThread
  extends Thread
{
  private static final Logger LOG = LoggerFactory.getLogger(SocketTimeoutInputStreamThread.class);
  
  private final SocketTimeoutInputStream stis;
  
  private final InputStream wrappedInputStream;
  
  private static final long WAIT_TIMEOUT_MS = 60000L;
  private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  

  SocketTimeoutInputStreamThread(SocketTimeoutInputStream socketTimeoutInputStream, InputStream wrappedInputStream)
  {
    super(createThreadName());
    stis = socketTimeoutInputStream;
    this.wrappedInputStream = wrappedInputStream;
    setDaemon(true);
  }
  


  private static synchronized String createThreadName()
  {
    return Thread.currentThread().getName() + " - SocketTimeoutInputStreamThread (created=" + DF.format(new Date()) + ")";
  }
  




  public void run()
  {
    try
    {
      copyBytesFromInputStreamToBuffer();
    }
    catch (IOException e)
    {
      synchronized (stis)
      {
        stis.lastPendingIOException = e;
      }
    }
    finally
    {
      waitUntilClosed();
      try
      {
        wrappedInputStream.close();
      }
      catch (IOException e)
      {
        synchronized (stis)
        {
          stis.lastPendingIOException = e;
        }
      }
      finally
      {
        synchronized (stis)
        {
          stis.notify();
        }
      }
    }
  }
  



  private void waitUntilClosed()
  {
    synchronized (stis)
    {
      stis.waitingForClose = true;
      stis.notify();
    }
  }
  

  private void copyBytesFromInputStreamToBuffer()
    throws IOException
  {
    for (;;)
    {
      int offset;
      
      int len;
      synchronized (stis)
      {

        if (stis.bufferLen == stis.buffer.length)
        {

          if (stis.closeRequestedByServiceUser)
          {

            return;
          }
          waitForRead(); continue;
        }
        offset = (stis.bufferHead + stis.bufferLen) % stis.buffer.length;
        len = (stis.bufferHead > offset ? stis.bufferHead : stis.buffer.length) - offset;
      }
      
      int count;
      try
      {
        int count = wrappedInputStream.read(stis.buffer, offset, len);
        if (count == -1)
        {

          return;
        }
      }
      catch (InterruptedIOException e)
      {
        count = bytesTransferred;
      }
      synchronized (stis)
      {
        stis.bufferLen += count;
        stis.notify();
      }
    }
  }
  



  private void waitForRead()
  {
    synchronized (stis)
    {
      try
      {
        stis.wait(60000L);
      }
      catch (InterruptedException e)
      {
        stis.closeRequestedByServiceUser = true;
      }
    }
  }
}
