package org.silvertunnel_ng.netlib.adapter.url;

import java.net.URL;
import org.silvertunnel_ng.netlib.adapter.url.impl.net.http.HttpHandler;
import org.silvertunnel_ng.netlib.api.NetFactory;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetLayerIDs;
import org.silvertunnel_ng.netlib.layer.tls.TLSNetLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

















































public class URLGlobalUtil
{
  private static final Logger LOG = LoggerFactory.getLogger(URLGlobalUtil.class);
  




  private static NetlibURLStreamHandlerFactory netlibURLStreamHandlerFactory;
  




  public URLGlobalUtil() {}
  




  public static synchronized void initURLStreamHandlerFactory()
  {
    NetLayer tcpipNetLayer = NetFactory.getInstance().getNetLayerById(NetLayerIDs.NOP);
    NetLayer tlsNetLayer = NetFactory.getInstance().getNetLayerById(NetLayerIDs.NOP);
    NetlibURLStreamHandlerFactory factory = new NetlibURLStreamHandlerFactory(tcpipNetLayer, tlsNetLayer, false);
    
    initURLStreamHandlerFactory(factory);
  }
  


























  public static synchronized void initURLStreamHandlerFactory(NetlibURLStreamHandlerFactory factory)
  {
    try
    {
      new HttpHandler(null).openConnection(null, null);
    }
    catch (Exception e)
    {
      LOG.debug("Can be ignored be ignored", e);
    }
    



    if (netlibURLStreamHandlerFactory == null)
    {
      try
      {
        netlibURLStreamHandlerFactory = factory;
        URL.setURLStreamHandlerFactory(factory);

      }
      catch (Throwable e)
      {
        String msg = "URL.setURLStreamHandlerFactory() was already called before, but not from UrlUtil, i.e. maybe the wrong factory is set";
        
        LOG.warn("URL.setURLStreamHandlerFactory() was already called before, but not from UrlUtil, i.e. maybe the wrong factory is set", e);
      }
    }
  }
  
















  public static synchronized void setNetLayerUsedByURLStreamHandlerFactory(NetLayer tcpipNetLayer, NetLayer tlsNetLayer)
    throws IllegalStateException
  {
    if (netlibURLStreamHandlerFactory == null)
    {
      throw new IllegalStateException("initURLStreamHandlerFactory() must be called first (but was not)");
    }
    

    netlibURLStreamHandlerFactory.setNetLayerForHttpHttpsFtp(tcpipNetLayer, tlsNetLayer);
  }
  











  public static synchronized void setNetLayerUsedByURLStreamHandlerFactory(NetLayer tcpipNetLayer)
    throws IllegalStateException
  {
    setNetLayerUsedByURLStreamHandlerFactory(tcpipNetLayer, new TLSNetLayer(tcpipNetLayer));
  }
}
