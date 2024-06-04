package org.silvertunnel_ng.netlib.util;

import java.io.IOException;
import java.io.InputStream;
import org.silvertunnel_ng.netlib.tool.DynByteBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

























public final class HttpUtilResponseReceiverThread
  extends Thread
{
  private static final Logger LOG = LoggerFactory.getLogger(HttpUtilResponseReceiverThread.class);
  

  private static final int DEFAULT_CHUNK_SIZE = 100000;
  

  private volatile boolean stopThread;
  

  private volatile boolean finished;
  
  private final DynByteBuffer tempResultBuffer;
  
  private final InputStream is;
  

  public HttpUtilResponseReceiverThread(InputStream is)
  {
    this(is, 100000);
  }
  







  public HttpUtilResponseReceiverThread(InputStream is, int maxResultSize)
  {
    this.is = is;
    tempResultBuffer = new DynByteBuffer(maxResultSize);
    

    setDaemon(true);
  }
  


  public void run()
  {
    byte[] buffer = new byte[50000];
    try
    {
      while (!stopThread)
      {
        int lastLen = is.read(buffer, 0, buffer.length);
        if (lastLen <= 0) {
          break;
        }
        
        tempResultBuffer.append(buffer, 0, lastLen);
      }
    }
    catch (IOException e)
    {
      LOG.error("receiving data interupted by exception", e);
    }
    
    finished = true;
  }
  



  public boolean isFinished()
  {
    return finished;
  }
  






  public byte[] readCurrentResultAndStopThread()
  {
    stopThread = true;
    finished = true;
    
    return tempResultBuffer.toArray();
  }
}
