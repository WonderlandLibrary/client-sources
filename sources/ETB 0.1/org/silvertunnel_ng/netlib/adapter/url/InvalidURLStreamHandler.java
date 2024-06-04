package org.silvertunnel_ng.netlib.adapter.url;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;





















public final class InvalidURLStreamHandler
  extends URLStreamHandler
{
  public InvalidURLStreamHandler() {}
  
  protected URLConnection openConnection(URL url)
    throws IOException
  {
    throw new UnsupportedOperationException("openConnection(): not allowed, url=" + url);
  }
  

  protected InetAddress getHostAddress(URL url)
  {
    throw new UnsupportedOperationException("getHostAddress(): not allowed, url=" + url);
  }
}
