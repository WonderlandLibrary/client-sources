package org.silvertunnel_ng.netlib.adapter.java;

import org.silvertunnel_ng.netlib.adapter.nameservice.NameServiceGlobalUtil;
import org.silvertunnel_ng.netlib.adapter.socket.SocketGlobalUtil;
import org.silvertunnel_ng.netlib.adapter.url.URLGlobalUtil;
import org.silvertunnel_ng.netlib.api.NetAddressNameService;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
















































public final class JvmGlobalUtil
{
  private static final Logger LOG = LoggerFactory.getLogger(JvmGlobalUtil.class);
  














  private JvmGlobalUtil() {}
  













  public static synchronized void init()
    throws IllegalStateException
  {
    IllegalStateException firstException = null;
    try
    {
      NameServiceGlobalUtil.initNameService();
    }
    catch (IllegalStateException e)
    {
      firstException = e;
      LOG.error("initialization (1/3) failed", e);
    }
    
    try
    {
      SocketGlobalUtil.initSocketImplFactory();
    }
    catch (IllegalStateException e)
    {
      if (firstException == null)
      {
        firstException = e;
      }
      LOG.error("initialization (2/3) failed", e);
    }
    try
    {
      URLGlobalUtil.initURLStreamHandlerFactory();
    }
    catch (IllegalStateException e)
    {
      if (firstException == null)
      {
        firstException = e;
      }
      LOG.error("initialization (3/3) failed", e);
    }
    LOG.info("init() ongoing");
    














    if (firstException == null)
    {

      LOG.info("init() end");

    }
    else
    {
      LOG.info("init() end with exception");
      

      throw firstException;
    }
  }
  














  public static synchronized void setNetLayerAndNetAddressNameService(NetLayer nextNetLayer, NetAddressNameService nextNetAddressNameService, boolean waitUntilReady)
    throws IllegalStateException
  {
    LOG.info("setNetLayerAndNetAddressNameService(nextNetLayer={}, nextNetAddressNameService={})", nextNetLayer, nextNetAddressNameService);
    


    if (nextNetAddressNameService != null)
    {
      NameServiceGlobalUtil.initNameService();
      NameServiceGlobalUtil.setIpNetAddressNameService(nextNetAddressNameService);
    }
    long time1 = System.currentTimeMillis();
    if (nextNetLayer != null)
    {
      SocketGlobalUtil.setNetLayerUsedBySocketImplFactory(nextNetLayer);
      URLGlobalUtil.setNetLayerUsedByURLStreamHandlerFactory(nextNetLayer);
    }
    

    if (waitUntilReady)
    {

      nextNetLayer.waitUntilReady();
      

      long time2 = System.currentTimeMillis();
      try
      {
        Thread.sleep(Math.max(0L, 
        
          NameServiceGlobalUtil.getCacheTimeoutMillis() - (time2 - time1)));

      }
      catch (InterruptedException e)
      {
        LOG.debug("got InterruptedException : {}", e.getMessage(), e);
      }
    }
  }
  












  public static synchronized void setNetLayerAndNetAddressNameService(NetLayer nextNetLayer, boolean waitUntilReady)
    throws IllegalStateException
  {
    LOG.info("setNetLayerAndNetAddressNameService(nextNetLayer={})", nextNetLayer);
    
    if (nextNetLayer != null)
    {
      setNetLayerAndNetAddressNameService(nextNetLayer, nextNetLayer.getNetAddressNameService(), waitUntilReady);
    }
  }
}
