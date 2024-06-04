package org.silvertunnel_ng.netlib.layer.control;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.WeakHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;










































public class ControlNetSocketThread
  extends Thread
{
  private static final Logger LOG = LoggerFactory.getLogger(ControlNetSocketThread.class);
  

  private static ControlNetSocketThread instance;
  

  private final Map<ControlNetSocket, ControlParameters> sockets = Collections.synchronizedMap(new WeakHashMap());
  

  static
  {
    try
    {
      instance = new ControlNetSocketThread();
      instance.setName("ControlNetSocketThread");
      instance.setDaemon(true);
      instance.start();
      LOG.info("ControlNetSocketThread instance started");

    }
    catch (Throwable t)
    {
      LOG.error("could not construct class ControlNetSocketThread", t);
    }
  }
  







  public static void startControlingControlNetSocket(ControlNetSocket socket, ControlParameters parameters)
  {
    synchronized (instancesockets)
    {
      instancesockets.put(socket, parameters);
    }
  }
  





  public static void stopControlingControlNetSocket(ControlNetSocket socket)
  {
    synchronized (instancesockets)
    {
      instancesockets.remove(socket);
    }
  }
  


  public void run()
  {
    for (;;)
    {
      Map<ControlNetSocket, String> socketsToRemoveFromChecklist = new HashMap();
      
      synchronized (sockets)
      {
        for (Map.Entry<ControlNetSocket, ControlParameters> e : sockets.entrySet())
        {

          String timeoutText = checkSingleSocketOnce((ControlNetSocket)e.getKey(), (ControlParameters)e.getValue());
          if (timeoutText != null)
          {
            socketsToRemoveFromChecklist.put(e.getKey(), timeoutText);
          }
        }
        

        for (Map.Entry<ControlNetSocket, String> e : socketsToRemoveFromChecklist.entrySet())
        {
          sockets.remove(e.getKey());
        }
      }
      

      for (??? = socketsToRemoveFromChecklist.entrySet().iterator(); ???.hasNext();) { Object e = (Map.Entry)???.next();
        
        sendTimeoutToSingleSocket((ControlNetSocket)((Map.Entry)e).getKey(), (String)((Map.Entry)e).getValue());
      }
      

      try
      {
        Thread.sleep(100L);
      }
      catch (InterruptedException e)
      {
        LOG.debug("got IterruptedException : {}", e.getMessage(), e);
      }
    }
  }
  









  private String checkSingleSocketOnce(ControlNetSocket socket, ControlParameters parameters)
  {
    if (parameters.getOverallTimeoutMillis() > 0L)
    {
      if (socket.getOverallMillis() > parameters.getOverallTimeoutMillis())
      {
        return "overall timeout reached"; }
    }
    if (parameters.getThroughputTimeframeMillis() > 0L)
    {
      if (socket.getCurrentTimeframeMillis() >= parameters.getThroughputTimeframeMillis())
      {


        long bytes = socket.getCurrentTimeframeStartInputOutputBytesAndStartNewTimeframe();
        if ((parameters.getThroughputTimeframeMinBytes() > 0L) && 
          (bytes < parameters.getThroughputTimeframeMinBytes()))
        {

          return "throughput is too low";
        }
      }
    }
    
    return null;
  }
  
  private void sendTimeoutToSingleSocket(ControlNetSocket socket, String msg)
  {
    LOG.info("send timeout to " + socket + ": " + msg);
    try
    {
      InterruptedIOException exceptionToBeThrownBySockets = new InterruptedIOException("Stream of ControlNetLayer closed because of: " + msg);
      
      socket.setInterruptedIOException(exceptionToBeThrownBySockets);
      socket.close();
    }
    catch (IOException e)
    {
      LOG.debug("IOException while calling close() (want to close because of: {})", msg, e);
    }
    catch (Exception e)
    {
      LOG.info("Exception while calling close() (want to close because of: {})", msg, e);
    }
  }
  
  public ControlNetSocketThread() {}
}
