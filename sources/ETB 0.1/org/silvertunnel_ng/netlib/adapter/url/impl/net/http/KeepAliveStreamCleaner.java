package org.silvertunnel_ng.netlib.adapter.url.impl.net.http;

import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;








































public class KeepAliveStreamCleaner
  extends LinkedBlockingQueue<MyKeepAliveCleanerEntry>
  implements Runnable
{
  private static final Logger LOG = LoggerFactory.getLogger(KeepAliveStreamCleaner.class);
  
  protected static int MAX_DATA_REMAINING = 512;
  

  protected static int MAX_CAPACITY = 10;
  

  protected static final int TIMEOUT = 5000;
  
  private static final int MAX_RETRIES = 5;
  

  static
  {
    String maxDataKey = "http.KeepAlive.remainingData";
    








    int maxData = ((Integer)AccessController.doPrivileged(new PrivilegedAction()
    {

      public Integer run()
      {

        return NetProperties.getInteger("http.KeepAlive.remainingData", KeepAliveStreamCleaner.MAX_DATA_REMAINING);
      }
      
    })).intValue() * 1024;
    MAX_DATA_REMAINING = maxData;
    
    String maxCapacityKey = "http.KeepAlive.queuedConnections";
    








    int maxCapacity = ((Integer)AccessController.doPrivileged(new PrivilegedAction()
    {

      public Integer run()
      {

        return NetProperties.getInteger("http.KeepAlive.queuedConnections", KeepAliveStreamCleaner.MAX_CAPACITY);
      }
      
    })).intValue();
    MAX_CAPACITY = maxCapacity;
  }
  

  public KeepAliveStreamCleaner()
  {
    super(MAX_CAPACITY);
  }
  
  public KeepAliveStreamCleaner(int capacity)
  {
    super(capacity);
  }
  

  public void run()
  {
    MyKeepAliveCleanerEntry kace = null;
    
    do
    {
      try
      {
        kace = (MyKeepAliveCleanerEntry)poll(5000L, TimeUnit.MILLISECONDS);
        if (kace == null) {
          break;
        }
        

        KeepAliveStream kas = kace.getKeepAliveStream();
        
        if (kas != null)
        {
          synchronized (kas)
          {
            HttpClient hc = kace.getHttpClient();
            try
            {
              if ((hc != null) && (!hc.isInKeepAliveCache()))
              {
                int oldTimeout = hc.setTimeout(5000);
                long remainingToRead = kas.remainingToRead();
                if (remainingToRead > 0L)
                {
                  long n = 0L;
                  int retries = 0;
                  while ((n < remainingToRead) && (retries < 5))
                  {

                    remainingToRead -= n;
                    n = kas.skip(remainingToRead);
                    if (n == 0L)
                    {
                      retries++;
                    }
                  }
                  remainingToRead -= n;
                }
                if (remainingToRead == 0L)
                {
                  hc.setTimeout(oldTimeout);
                  hc.finished();
                }
                else
                {
                  hc.closeServer();
                }
              }
            }
            catch (IOException ioe)
            {
              hc.closeServer();
              LOG.debug("got IOException : {}", ioe.getMessage(), ioe);
            }
            finally
            {
              kas.setClosed();
            }
          }
        }
      }
      catch (InterruptedException ie)
      {
        LOG.debug("got IterruptedException : {}", ie.getMessage(), ie);
      }
      
    } while (kace != null);
  }
}
