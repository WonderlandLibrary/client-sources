package org.silvertunnel_ng.netlib.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import org.silvertunnel_ng.netlib.adapter.url.NetlibURLStreamHandlerFactory;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.util.TcpipNetAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;















































public final class SimpleHttpClient
{
  private static final Logger LOG = LoggerFactory.getLogger(SimpleHttpClient.class);
  
  private static SimpleHttpClient instance = new SimpleHttpClient();
  private static final String PROTOCOL_HTTP = "http";
  private static final String NL = "\n";
  
  public SimpleHttpClient() {}
  
  public static SimpleHttpClient getInstance() { return instance; }
  





















  public String get(NetLayer netLayer, TcpipNetAddress hostAndPort, String path)
    throws IOException
  {
    String urlStr = null;
    BufferedReader in = null;
    long startTime = System.currentTimeMillis();
    try
    {
      if (LOG.isDebugEnabled()) {
        LOG.debug("start download with hostAndPort=" + hostAndPort + " and path=" + path);
      }
      


      NetlibURLStreamHandlerFactory factory = new NetlibURLStreamHandlerFactory(false);
      factory.setNetLayerForHttpHttpsFtp(netLayer);
      

      if ((path != null) && (!path.startsWith("/"))) {
        path = "/" + path;
      }
      

      urlStr = "http://" + hostAndPort.getHostnameOrIpaddress() + ":" + hostAndPort.getPort() + path;
      URLStreamHandler handler = factory.createURLStreamHandler("http");
      URL context = null;
      URL url = new URL(context, urlStr, handler);
      

      URLConnection conn = url.openConnection();
      conn.setDoOutput(false);
      conn.setDoInput(true);
      conn.connect();
      in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
      
      StringBuffer response = new StringBuffer();
      String inputLine; while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
        response.append("\n");
      }
      
      HttpURLConnection httpConnection;
      if ((conn instanceof HttpURLConnection)) {
        httpConnection = (HttpURLConnection)conn;
        int code = httpConnection.getResponseCode();
        

        if ((code < 200) || (code >= 300)) {
          throw new IOException("http transfer was not successful for url=" + urlStr);
        }
      }
      else
      {
        throw new IOException("http response code could not be determined for url=" + urlStr);
      }
      



      if (LOG.isDebugEnabled()) {
        LOG.debug("end download with hostAndPort=" + hostAndPort + " and path=" + path + " finished with result of length=" + response
        

          .length() + " time : " + (
          System.currentTimeMillis() - startTime) + " ms");
      }
      return response.toString();
    }
    catch (IOException e) {
      LOG.debug("end download with hostAndPort=" + hostAndPort + " and path=" + path + " with " + e);
      
      throw e;
    }
    finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
          LOG.warn("Exception while closing InputStream from url=" + urlStr);
        }
      }
    }
  }
}
