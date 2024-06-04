package org.silvertunnel_ng.netlib.layer.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import org.silvertunnel_ng.netlib.api.NetSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


























public class ControlNetSocket
  implements NetSocket
{
  private static final Logger LOG = LoggerFactory.getLogger(ControlNetSocket.class);
  
  private final NetSocket lowerLayerSocket;
  
  private InputStream in;
  
  private OutputStream out;
  
  private long lastActivity;
  
  private final long startDate;
  
  private long connectDate;
  
  private long currentTimeframeStartDate;
  
  private long currentTimeframeStartInputOutputBytes;
  
  private long inputBytes;
  
  private long outputBytes;
  private InterruptedIOException interruptedIOException;
  
  public ControlNetSocket(NetSocket lowerLayerSocket, ControlParameters parameters)
  {
    startDate = System.currentTimeMillis();
    currentTimeframeStartDate = startDate;
    currentTimeframeStartInputOutputBytes = 0L;
    this.lowerLayerSocket = lowerLayerSocket;
    setLastActivity();
    
    ControlNetSocketThread.startControlingControlNetSocket(this, parameters);
  }
  
  protected synchronized void addInputBytes(int bytes)
  {
    inputBytes += bytes;
  }
  
  protected synchronized void addOutputBytes(int bytes)
  {
    outputBytes += bytes;
  }
  
  protected void setLastActivity(long lastActivity)
  {
    this.lastActivity = lastActivity;
  }
  
  protected void setLastActivity()
  {
    setLastActivity(System.currentTimeMillis());
  }
  



  public long getCurrentTimeframeMillis()
  {
    return System.currentTimeMillis() - currentTimeframeStartDate;
  }
  



  public long getOverallMillis()
  {
    return System.currentTimeMillis() - startDate;
  }
  








  protected synchronized long getCurrentTimeframeStartInputOutputBytesAndStartNewTimeframe()
  {
    long result = getInputOutputBytes() - currentTimeframeStartInputOutputBytes;
    


    currentTimeframeStartInputOutputBytes = getInputOutputBytes();
    
    currentTimeframeStartDate = System.currentTimeMillis();
    return result;
  }
  
  public void close()
    throws IOException
  {
    lowerLayerSocket.close();
    ControlNetSocketThread.stopControlingControlNetSocket(this);
    setLastActivity();
  }
  
  public InputStream getInputStream()
    throws IOException
  {
    if (in == null)
    {
      in = new ControlInputStream(lowerLayerSocket.getInputStream(), this);
    }
    setLastActivity();
    return in;
  }
  
  public OutputStream getOutputStream()
    throws IOException
  {
    if (out == null)
    {
      out = new ControlOutputStream(lowerLayerSocket.getOutputStream(), this);
    }
    
    setLastActivity();
    return out;
  }
  




  public int hashCode()
  {
    return super.hashCode();
  }
  




  public boolean equals(Object obj)
  {
    return super.equals(obj);
  }
  

  public String toString()
  {
    return "ControlNetSocket(" + lowerLayerSocket + ")";
  }
  






  protected void setInterruptedIOException(InterruptedIOException interruptedIOException)
  {
    this.interruptedIOException = interruptedIOException;
  }
  






  protected void throwInterruptedIOExceptionIfNecessary()
    throws InterruptedIOException
  {
    if (interruptedIOException != null)
    {
      throw interruptedIOException;
    }
  }
  




  public long getStartDate()
  {
    return startDate;
  }
  
  public long getConnectDate()
  {
    return connectDate;
  }
  
  public long getCurrentTimeframeStartDate()
  {
    return currentTimeframeStartDate;
  }
  
  public long getCurrentTimeframeStartInputOutputBytes()
  {
    return currentTimeframeStartInputOutputBytes;
  }
  
  public long getInputBytes()
  {
    return inputBytes;
  }
  
  public long getOutputBytes()
  {
    return outputBytes;
  }
  
  public long getInputOutputBytes()
  {
    return inputBytes + outputBytes;
  }
  
  public long getLastActivity()
  {
    return lastActivity;
  }
}
