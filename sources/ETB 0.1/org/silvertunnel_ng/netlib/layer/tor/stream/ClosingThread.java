package org.silvertunnel_ng.netlib.layer.tor.stream;

import org.silvertunnel_ng.netlib.layer.tor.circuit.Queue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

























public class ClosingThread
  extends Thread
{
  private static final Logger LOG = LoggerFactory.getLogger(ClosingThread.class);
  
  private final StreamThread[] threads;
  private final int chosenOne;
  
  public ClosingThread(StreamThread[] threads, int chosenOne)
  {
    this.threads = threads;
    this.chosenOne = chosenOne;
    start();
  }
  


  public void run()
  {
    for (int i = 0; i < threads.length; i++)
    {
      if (i != chosenOne)
      {
        if (threads[i].getStream() != null)
        {
          try
          {
            threads[i].getStream().setClosed(true);
            threads[i].getStream().queue.close();
          }
          catch (Exception e)
          {
            LOG.warn("Tor.ClosingThread.run(): " + e.getMessage(), e);
          }
          try
          {
            threads[i].getStream().close();
          }
          catch (Exception e)
          {
            LOG.warn("Tor.ClosingThread.run(): " + e.getMessage(), e);
          }
        }
        try
        {
          threads[i].join();
        }
        catch (Exception e)
        {
          LOG.warn("Tor.ClosingThread.run(): " + e.getMessage(), e);
        }
      }
    }
  }
}
