package org.silvertunnel_ng.netlib.adapter.url.impl.net.http;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;























public class HttpHandler
  extends Handler
{
  private static final Logger LOG = LoggerFactory.getLogger(HttpHandler.class);
  





  protected NetLayer netLayer;
  






  public HttpHandler(NetLayer netLayer)
  {
    this.netLayer = netLayer;
  }
  
  public URLConnection openConnection(URL u, Proxy p)
    throws IOException
  {
    return new HttpURLConnection(netLayer, u, p, this);
  }
  
  public void setNetLayer(NetLayer netLayer)
  {
    this.netLayer = netLayer;
  }
  









  protected synchronized InetAddress getHostAddress(URL u)
  {
    LOG.info("HttpHandler.getHostAddress(): do not determine correct address for security reasons - return null");
    


    return null;
  }
  






















  protected boolean hostsEqual(URL url1, URL url2)
  {
    if ((url1.getHost() != null) && (url2.getHost() != null))
    {
      return url1.getHost().equalsIgnoreCase(url2.getHost());
    }
    

    return (url1.getHost() == null) && (url2.getHost() == null);
  }
}
