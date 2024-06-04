package org.silvertunnel_ng.netlib.adapter.url;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import org.silvertunnel_ng.netlib.adapter.url.impl.net.http.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;












































public class URLUtil
{
  private static final Logger LOG = LoggerFactory.getLogger(URLUtil.class);
  






  public URLUtil() {}
  





  public static URLConnection openConnection(URLStreamHandlerFactory factory, URL url)
    throws IOException
  {
    LOG.info("openConnection start with url={}", url);
    URLStreamHandler handler = factory.createURLStreamHandler(url.getProtocol());
    URLConnection result = ((HttpHandler)handler).openConnection(url, null);
    LOG.info("openConnection end with result={}", result);
    return result;
  }
}
