package org.silvertunnel_ng.netlib.adapter.url;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.HashMap;
import java.util.Map;
import org.silvertunnel_ng.netlib.adapter.url.impl.net.http.HttpHandler;
import org.silvertunnel_ng.netlib.adapter.url.impl.net.https.HttpsHandler;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.layer.tls.TLSNetLayer;










































public class NetlibURLStreamHandlerFactory
  implements URLStreamHandlerFactory
{
  private static final String PROTOCOL_HTTP = "http";
  private static final String PROTOCOL_HTTPS = "https";
  private Map<String, URLStreamHandler> handlers;
  private final boolean prohibitAccessForOtherProtocols;
  
  public NetlibURLStreamHandlerFactory()
  {
    this(false);
  }
  







  public NetlibURLStreamHandlerFactory(boolean prohibitAccessForOtherProtocols)
  {
    this(new HashMap(), prohibitAccessForOtherProtocols);
  }
  







  public NetlibURLStreamHandlerFactory(Map<String, URLStreamHandler> handlers, boolean prohibitAccessForOtherProtocols)
  {
    this.handlers = handlers;
    this.prohibitAccessForOtherProtocols = prohibitAccessForOtherProtocols;
  }
  















  public NetlibURLStreamHandlerFactory(NetLayer tcpipNetLayer, NetLayer tlsNetLayer, boolean prohibitAccessForOtherProtocols)
  {
    setNetLayerForHttpHttpsFtp(tcpipNetLayer, tlsNetLayer);
    this.prohibitAccessForOtherProtocols = prohibitAccessForOtherProtocols;
  }
  












  public NetlibURLStreamHandlerFactory(NetLayer tcpipNetLayer, boolean prohibitAccessForOtherProtocols)
  {
    setNetLayerForHttpHttpsFtp(tcpipNetLayer);
    this.prohibitAccessForOtherProtocols = prohibitAccessForOtherProtocols;
  }
  











  public final synchronized void setNetLayerForHttpHttpsFtp(NetLayer tcpipNetLayer, NetLayer tlsNetLayer)
  {
    if (handlers == null)
    {

      handlers = new HashMap();
    }
    

    URLStreamHandler handler = (URLStreamHandler)handlers.get("http");
    if ((handler != null) && ((handler instanceof HttpHandler)))
    {

      ((HttpHandler)handler).setNetLayer(tcpipNetLayer);

    }
    else
    {
      handlers.put("http", new HttpHandler(tcpipNetLayer));
    }
    
    handler = (URLStreamHandler)handlers.get("https");
    if ((handler != null) && ((handler instanceof HttpHandler)))
    {

      ((HttpsHandler)handler).setNetLayer(tlsNetLayer);

    }
    else
    {
      handlers.put("https", new HttpsHandler(tlsNetLayer));
    }
  }
  













  public void setNetLayerForHttpHttpsFtp(NetLayer tcpipNetLayer)
  {
    setNetLayerForHttpHttpsFtp(tcpipNetLayer, new TLSNetLayer(tcpipNetLayer));
  }
  




  public URLStreamHandler createURLStreamHandler(String protocol)
  {
    URLStreamHandler result;
    



    synchronized (this)
    {
      result = (URLStreamHandler)handlers.get(protocol);
    }
    if (result != null)
    {
      return result;
    }
    

    if (prohibitAccessForOtherProtocols)
    {

      return new InvalidURLStreamHandler();
    }
    



    return null;
  }
}
