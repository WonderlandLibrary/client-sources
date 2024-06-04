package org.silvertunnel_ng.netlib.adapter.url.impl.net.http;

import java.io.IOException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



















public class Handler
  extends URLStreamHandler
{
  private static final Logger LOG = LoggerFactory.getLogger(Handler.class);
  
  protected String proxy;
  
  protected int proxyPort;
  
  protected int getDefaultPort()
  {
    return 80;
  }
  
  public Handler()
  {
    proxy = null;
    proxyPort = -1;
  }
  
  public Handler(String proxy, int port)
  {
    this.proxy = proxy;
    proxyPort = port;
  }
  
  protected URLConnection openConnection(URL u)
    throws IOException
  {
    return openConnection(u, null);
  }
  

  protected URLConnection openConnection(URL u, Proxy p)
    throws IOException
  {
    LOG.warn("Handler.openConnection(URL u, Proxy p): not implemented - must be overwritten");
    throw new UnsupportedOperationException("Method not implemented.");
  }
}
