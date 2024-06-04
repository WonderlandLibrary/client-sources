package org.silvertunnel_ng.netlib.tool;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.zip.DataFormatException;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;
import org.silvertunnel_ng.netlib.adapter.url.NetlibURLStreamHandlerFactory;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.util.TcpipNetAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;














































public final class SimpleHttpClientCompressed
{
  private static final Logger LOG = LoggerFactory.getLogger(SimpleHttpClientCompressed.class);
  
  private static SimpleHttpClientCompressed instance = new SimpleHttpClientCompressed();
  private static final String PROTOCOL_HTTP = "http";
  private static final int BUFFER_SIZE = 512000;
  
  public SimpleHttpClientCompressed() {}
  
  public static SimpleHttpClientCompressed getInstance() { return instance; }
  





















  public String get(NetLayer netLayer, TcpipNetAddress hostAndPort, String path)
    throws IOException, DataFormatException
  {
    String urlStr = null;
    InputStream in = null;
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
      path = path + ".z";
      urlStr = "http://" + hostAndPort.getHostnameOrIpaddress() + ":" + hostAndPort.getPort() + path;
      URLStreamHandler handler = factory.createURLStreamHandler("http");
      URL context = null;
      URL url = new URL(context, urlStr, handler);
      

      URLConnection conn = url.openConnection();
      conn.setDoOutput(false);
      conn.setDoInput(true);
      conn.connect();
      
      if ((conn instanceof HttpURLConnection)) {
        HttpURLConnection httpConnection = (HttpURLConnection)conn;
        int code = httpConnection.getResponseCode();
        

        if ((code < 200) || (code >= 300))
        {
          throw new IOException("http transfer was not successful for url=" + urlStr);
        }
      }
      else {
        throw new IOException("http response code could not be determined for url=" + urlStr);
      }
      in = getInputStream(conn.getInputStream());
      DynByteBuffer byteBuffer = new DynByteBuffer(512000L);
      byte[] buffer = new byte[512000];
      int count;
      while ((count = in.read(buffer)) > 0) {
        byteBuffer.append(buffer, 0, count);
      }
      long timeReceived = System.currentTimeMillis();
      
      String response = new String(byteBuffer.toArray(), "UTF-8");
      
      if (LOG.isDebugEnabled()) {
        LOG.debug("end download with hostAndPort=" + hostAndPort + " and path=" + path + " finished with result of length=" + response
          .length() + " timeReceived : " + (timeReceived - startTime) + " ms");
      }
      return response;
    }
    catch (IOException e) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("end download with hostAndPort=" + hostAndPort + " and path=" + path + " with " + e, e);
      }
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
  
  private InputStream getInputStream(InputStream inputStream) throws IOException {
    PushbackInputStream pushbackInputStream = new PushbackInputStream(inputStream, 2);
    byte[] signature = new byte[2];
    pushbackInputStream.read(signature);
    pushbackInputStream.unread(signature);
    if (isGzipCompressed(signature))
      return new GZIPInputStream(pushbackInputStream);
    if (isZlibCompressed(signature)) {
      return new InflaterInputStream(pushbackInputStream);
    }
    return pushbackInputStream;
  }
  








  private boolean isGzipCompressed(byte[] bytes)
    throws IOException
  {
    if ((bytes == null) || (bytes.length < 2)) {
      return false;
    }
    return (bytes[0] == 31) && (bytes[1] == -117);
  }
  








  private boolean isZlibCompressed(byte[] bytes)
    throws IOException
  {
    if ((bytes == null) || (bytes.length < 1)) {
      return false;
    }
    return bytes[0] == 120;
  }
}
