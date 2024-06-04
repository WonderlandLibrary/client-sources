package org.silvertunnel_ng.netlib.adapter.url.impl.net.https;

import java.io.IOException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import org.silvertunnel_ng.netlib.adapter.url.impl.net.http.HttpHandler;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;























public class HttpsHandler
  extends HttpHandler
{
  private static final Logger LOG = LoggerFactory.getLogger(HttpsHandler.class);
  







  public HttpsHandler(NetLayer netLayer)
  {
    super(netLayer);
  }
  

  public URLConnection openConnection(URL u, Proxy p)
    throws IOException
  {
    return new HttpsURLConnection(netLayer, u, this);
  }
}
