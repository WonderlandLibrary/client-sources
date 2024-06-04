package org.silvertunnel_ng.netlib.layer.mock;

import java.io.ByteArrayInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


























public class MockByteArrayInputStream
  extends ByteArrayInputStream
{
  private static final Logger LOG = LoggerFactory.getLogger(MockByteArrayInputStream.class);
  

  private static final long ENDLESS_MS = Long.MAX_VALUE;
  

  private long waitAtTheEndMs;
  

  private Thread sleepingThread;
  


  public MockByteArrayInputStream(byte[] response, long waitAtTheEndMs)
  {
    super(response);
    this.waitAtTheEndMs = waitAtTheEndMs;
  }
  





  private void waitAtTheEnd()
  {
    if (waitAtTheEndMs == 0L)
    {
      return;
    }
    
    try
    {
      sleepingThread = Thread.currentThread();
      Thread.sleep(waitAtTheEndMs < 0L ? Long.MAX_VALUE : waitAtTheEndMs);
    }
    catch (InterruptedException e)
    {
      LOG.debug("got IterruptedException : {}", e.getMessage(), e);
    }
    sleepingThread = null;
    

    waitAtTheEndMs = 0L;
  }
  





  public int read()
  {
    int result = super.read();
    if (result < 0)
    {
      waitAtTheEnd();
    }
    return result;
  }
  

  public int read(byte[] b, int off, int len)
  {
    int result = super.read(b, off, len);
    if (result < 0)
    {
      waitAtTheEnd();
    }
    return result;
  }
  

  public void close()
  {
    waitAtTheEndMs = 0L;
    

    super.skip(super.available());
    
    Thread sThread = sleepingThread;
    if (sThread != null)
    {

      sThread.interrupt();
    }
  }
}
