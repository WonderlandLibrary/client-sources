package org.silvertunnel_ng.netlib.layer.control;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;





























public class ControlOutputStream
  extends OutputStream
{
  private final OutputStream lowerLevelOutputStream;
  private final ControlNetSocket timeControlNetSocket;
  
  protected ControlOutputStream(OutputStream lowerLevelOutputStream, ControlNetSocket timeControlNetSocket)
  {
    this.lowerLevelOutputStream = lowerLevelOutputStream;
    this.timeControlNetSocket = timeControlNetSocket;
  }
  






  public void write(int b)
    throws IOException, InterruptedIOException
  {
    timeControlNetSocket.setLastActivity();
    try
    {
      timeControlNetSocket.throwInterruptedIOExceptionIfNecessary();
      lowerLevelOutputStream.write(b);
      timeControlNetSocket.throwInterruptedIOExceptionIfNecessary();
      


      timeControlNetSocket.setLastActivity(); } finally { timeControlNetSocket.setLastActivity();
    }
  }
  
  public void write(byte[] b)
    throws IOException, InterruptedIOException
  {
    timeControlNetSocket.setLastActivity();
    try
    {
      timeControlNetSocket.throwInterruptedIOExceptionIfNecessary();
      lowerLevelOutputStream.write(b);
      timeControlNetSocket.throwInterruptedIOExceptionIfNecessary();
      


      timeControlNetSocket.setLastActivity(); } finally { timeControlNetSocket.setLastActivity();
    }
  }
  

  public void write(byte[] b, int off, int len)
    throws IOException, InterruptedIOException
  {
    timeControlNetSocket.setLastActivity();
    try
    {
      timeControlNetSocket.throwInterruptedIOExceptionIfNecessary();
      lowerLevelOutputStream.write(b, off, len);
      timeControlNetSocket.throwInterruptedIOExceptionIfNecessary();
    }
    finally
    {
      timeControlNetSocket.setLastActivity();
    }
  }
  
  public void flush()
    throws IOException, InterruptedIOException
  {
    timeControlNetSocket.setLastActivity();
    try
    {
      timeControlNetSocket.throwInterruptedIOExceptionIfNecessary();
      lowerLevelOutputStream.flush();
      timeControlNetSocket.throwInterruptedIOExceptionIfNecessary();
      


      timeControlNetSocket.setLastActivity(); } finally { timeControlNetSocket.setLastActivity();
    }
  }
  
  public void close()
    throws IOException
  {
    timeControlNetSocket.setLastActivity();
    try
    {
      lowerLevelOutputStream.close();
      


      timeControlNetSocket.setLastActivity(); } finally { timeControlNetSocket.setLastActivity();
    }
  }
}
