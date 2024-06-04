package org.silvertunnel_ng.netlib.layer.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;





























public class ControlInputStream
  extends InputStream
{
  private final InputStream lowerLevelInputStream;
  private final ControlNetSocket timeControlNetSocket;
  
  protected ControlInputStream(InputStream lowerLevelInputStream, ControlNetSocket timeControlNetSocket)
  {
    this.lowerLevelInputStream = lowerLevelInputStream;
    this.timeControlNetSocket = timeControlNetSocket;
  }
  






  public int read()
    throws IOException, InterruptedIOException
  {
    timeControlNetSocket.setLastActivity();
    try
    {
      timeControlNetSocket.throwInterruptedIOExceptionIfNecessary();
      int result = lowerLevelInputStream.read();
      timeControlNetSocket.addInputBytes(1);
      timeControlNetSocket.throwInterruptedIOExceptionIfNecessary();
      return result;
    }
    finally
    {
      timeControlNetSocket.setLastActivity();
    }
  }
  
  public int read(byte[] b)
    throws IOException, InterruptedIOException
  {
    timeControlNetSocket.setLastActivity();
    try
    {
      timeControlNetSocket.throwInterruptedIOExceptionIfNecessary();
      int result = lowerLevelInputStream.read(b);
      timeControlNetSocket.addInputBytes(result);
      timeControlNetSocket.throwInterruptedIOExceptionIfNecessary();
      return result;
    }
    finally
    {
      timeControlNetSocket.setLastActivity();
    }
  }
  

  public int read(byte[] b, int off, int len)
    throws IOException, InterruptedIOException
  {
    timeControlNetSocket.setLastActivity();
    try
    {
      timeControlNetSocket.throwInterruptedIOExceptionIfNecessary();
      int result = lowerLevelInputStream.read(b, off, len);
      timeControlNetSocket.addInputBytes(result);
      timeControlNetSocket.throwInterruptedIOExceptionIfNecessary();
      return result;
    }
    finally
    {
      timeControlNetSocket.setLastActivity();
    }
  }
  
  public long skip(long n)
    throws IOException, InterruptedIOException
  {
    timeControlNetSocket.setLastActivity();
    try
    {
      timeControlNetSocket.throwInterruptedIOExceptionIfNecessary();
      long result = lowerLevelInputStream.skip(n);
      timeControlNetSocket.throwInterruptedIOExceptionIfNecessary();
      return result;
    }
    finally
    {
      timeControlNetSocket.setLastActivity();
    }
  }
  
  public int available()
    throws IOException, InterruptedIOException
  {
    timeControlNetSocket.setLastActivity();
    try
    {
      timeControlNetSocket.throwInterruptedIOExceptionIfNecessary();
      int result = lowerLevelInputStream.available();
      timeControlNetSocket.throwInterruptedIOExceptionIfNecessary();
      return result;
    }
    finally
    {
      timeControlNetSocket.setLastActivity();
    }
  }
  
  public void close()
    throws IOException
  {
    timeControlNetSocket.setLastActivity();
    try
    {
      lowerLevelInputStream.close();
      


      timeControlNetSocket.setLastActivity(); } finally { timeControlNetSocket.setLastActivity();
    }
  }
  

  public synchronized void mark(int readlimit)
  {
    timeControlNetSocket.setLastActivity();
    try
    {
      lowerLevelInputStream.mark(readlimit);
      


      timeControlNetSocket.setLastActivity(); } finally { timeControlNetSocket.setLastActivity();
    }
  }
  
  public synchronized void reset()
    throws IOException, InterruptedIOException
  {
    timeControlNetSocket.setLastActivity();
    try
    {
      timeControlNetSocket.throwInterruptedIOExceptionIfNecessary();
      lowerLevelInputStream.reset();
      timeControlNetSocket.throwInterruptedIOExceptionIfNecessary();
      


      timeControlNetSocket.setLastActivity(); } finally { timeControlNetSocket.setLastActivity();
    }
  }
  

  public boolean markSupported()
  {
    timeControlNetSocket.setLastActivity();
    try
    {
      return lowerLevelInputStream.markSupported();
    }
    finally
    {
      timeControlNetSocket.setLastActivity();
    }
  }
}
