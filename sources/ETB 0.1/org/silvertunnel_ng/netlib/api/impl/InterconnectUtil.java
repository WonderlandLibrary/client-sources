package org.silvertunnel_ng.netlib.api.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.silvertunnel_ng.netlib.api.NetSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


























public class InterconnectUtil
{
  private static final Logger LOG = LoggerFactory.getLogger(InterconnectUtil.class);
  

  private static final int SLEEP_ON_INACTIVITY_MS = 10;
  
  private static final int DEFAULT_BUFFER_SIZE = 2048;
  
  private static long id;
  

  public InterconnectUtil() {}
  

  public static void relay(NetSocket netSocket1, NetSocket netSocket2)
    throws IOException
  {
    relay(netSocket1.getInputStream(), netSocket2.getOutputStream(), netSocket2
      .getInputStream(), netSocket1.getOutputStream(), 2048);
  }
  


















  public static void relay(InputStream in1, OutputStream out1, InputStream in2, OutputStream out2, int bufferSize)
  {
    relayInTwoThreads(in1, out1, in2, out2, bufferSize);
  }
  


























  public static void relayNonBlocking(final InputStream in1, final OutputStream out1, final InputStream in2, final OutputStream out2, final int bufferSize)
  {
    new Thread(createUniqueThreadName())
    {
      public void run()
      {
        InterconnectUtil.relayInTwoThreads(in1, out1, in2, out2, bufferSize);
      }
    }.start();
  }
  

















  public static void relayInOneThread(InputStream in1, OutputStream out1, InputStream in2, OutputStream out2, int bufferSize)
  {
    long byteCounterForLog1 = 0L;
    long byteCounterForLog2 = 0L;
    try
    {
      byte[] buffer = new byte[bufferSize];
      boolean tryToClose = false;
      for (;;)
      {
        boolean action = false;
        

        try
        {
          if (in1.available() > 0)
          {
            int cc = in1.read(buffer);
            byteCounterForLog1 += cc;
            LOG.info(" > " + cc + " bytes (" + byteCounterForLog1 + " bytes total)");
            
            out1.write(buffer, 0, cc);
            out1.flush();
            action = true;
          }
        }
        catch (IOException e)
        {
          LOG.debug("relay1: {}", e.toString(), e);
          tryToClose = true;
        }
        

        try
        {
          if (in2.available() > 0)
          {
            int cc = in2.read(buffer);
            byteCounterForLog2 += cc;
            LOG.info(" < " + cc + " bytes (" + byteCounterForLog2 + " bytes total)");
            
            out2.write(buffer, 0, cc);
            out2.flush();
            action = true;
          }
        }
        catch (IOException e)
        {
          LOG.debug("relay2: {}", e.toString(), e);
          tryToClose = true;
        }
        

        if ((!action) && (tryToClose))
        {

          in1.close();
          in2.close();
          out1.close();
          out2.close();
          break;
        }
        

        if (!action)
        {
          Thread.sleep(10L);
        }
      }
    }
    catch (Exception e)
    {
      LOG.warn("connection interrupted", e);
    }
  }
  





















  public static void relayInTwoThreads(final InputStream in1, final OutputStream out1, InputStream in2, OutputStream out2, final int bufferSize)
  {
    final BooleanHolder tryToClose = new BooleanHolder();
    value = false;
    

    new Thread(createUniqueThreadName())
    {

      public void run()
      {
        InterconnectUtil.relayOneDirection2(" >1> ", in1, out1, bufferSize, tryToClose);
      }
      

    }.start();
    relayOneDirection2(" <2< ", in2, out2, bufferSize, tryToClose);
  }
  















  static void relayOneDirection1(String logMsg, InputStream in, OutputStream out, int bufferSize, BooleanHolder tryToClose)
  {
    long byteCounterForLog = 0L;
    try
    {
      byte[] buffer = new byte[bufferSize];
      for (;;)
      {
        boolean action = false;
        

        try
        {
          if (in.available() > 0)
          {
            int cc = in.read(buffer);
            byteCounterForLog += cc;
            if (logMsg != null)
            {
              LOG.info(logMsg + " " + cc + " bytes (" + byteCounterForLog + " bytes total)");
            }
            
            out.write(buffer, 0, cc);
            out.flush();
            action = true;
          }
        }
        catch (IOException e)
        {
          LOG.debug("relay: {}", e.toString(), e);
          value = true;
        }
        

        if ((!action) && (value))
        {

          in.close();
          out.close();
          break;
        }
        

        if (!action)
        {
          Thread.sleep(10L);
        }
      }
    }
    catch (Exception e)
    {
      LOG.warn("connection interrupted", e);
    }
  }
  















  static void relayOneDirection2(String logMsg, InputStream in, OutputStream out, int bufferSize, BooleanHolder tryToClose)
  {
    long byteCounterForLog = 0L;
    try
    {
      byte[] buffer = new byte[bufferSize];
      try
      {
        for (;;)
        {
          int cc = in.read(buffer);
          if (cc <= 0)
          {

            LOG.info(logMsg + " input stream closed - close the rest");
            
            break;
          }
          

          byteCounterForLog += cc;
          if (logMsg != null)
          {
            LOG.info(logMsg + " " + cc + " bytes (" + byteCounterForLog + " bytes total)");
          }
          
          out.write(buffer, 0, cc);
          out.flush();
        }
        

      }
      catch (IOException e)
      {
        LOG.info(logMsg + " close all because of " + e.toString());
      }
      

      value = true;
      try
      {
        in.close();

      }
      catch (IOException e)
      {
        LOG.debug("got IOException : {}", e.getMessage(), e);
      }
      try
      {
        out.close();

      }
      catch (IOException e)
      {
        LOG.debug("got IOException : {}", e.getMessage(), e);
      }
    }
    catch (Exception e)
    {
      LOG.warn("connection interrupted", e);
    }
  }
  



  protected static synchronized String createUniqueThreadName()
  {
    id += 1L;
    
    return InterconnectUtil.class.getName() + id + "-" + Thread.currentThread().getName();
  }
}
