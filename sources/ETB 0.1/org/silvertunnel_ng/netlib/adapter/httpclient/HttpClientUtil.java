package org.silvertunnel_ng.netlib.adapter.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpVersion;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetSocket;
import org.silvertunnel_ng.netlib.api.util.TcpipNetAddress;
import org.silvertunnel_ng.netlib.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

















































public class HttpClientUtil
{
  private static final Logger LOG = LoggerFactory.getLogger(HttpClientUtil.class);
  
  private static SchemeRegistry supportedSchemes;
  
  private static NetLayer lowerNetLayer;
  

  public HttpClientUtil() {}
  
  static void init(NetLayer lowerNetLayer)
  {
    try
    {
      lowerNetLayer = lowerNetLayer;
      Scheme http = new Scheme("http", new NetlibSocketFactory(lowerNetLayer), 80);
      
      supportedSchemes = new SchemeRegistry();
      supportedSchemes.register(http);
      

      HttpParams httpParams = new BasicHttpParams();
      HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
      HttpProtocolParams.setContentCharset(httpParams, "UTF-8");
      HttpProtocolParams.setUseExpectContinue(httpParams, true);


    }
    catch (Exception e)
    {

      LOG.error("error during class init", e);
    }
  }
  
  public static InputStream simpleAction(URL url) throws IOException
  {
    int port = url.getPort() < 0 ? 80 : url.getPort();
    TcpipNetAddress httpServerNetAddress = new TcpipNetAddress(url.getHost(), port);
    Map<String, Object> localProperties = new HashMap();
    NetSocket lowerLayerNetSocket = lowerNetLayer.createNetSocket(localProperties, null, httpServerNetAddress);
    String pathOnHttpServer = url.getPath();
    if ((pathOnHttpServer == null) || (pathOnHttpServer.length() < 1))
    {
      pathOnHttpServer = "/";
    }
    long timeoutInMs = 10000L;
    
    return HttpUtil.getInstance().getReponseBodyInputStream(lowerLayerNetSocket, httpServerNetAddress, pathOnHttpServer, 10000L);
  }
  
  public static byte[] simpleBytesAction(URL url) throws IOException
  {
    int port = url.getPort() < 0 ? 80 : url.getPort();
    TcpipNetAddress httpServerNetAddress = new TcpipNetAddress(url.getHost(), port);
    Map<String, Object> localProperties = new HashMap();
    NetSocket lowerLayerNetSocket = lowerNetLayer.createNetSocket(localProperties, null, httpServerNetAddress);
    String pathOnHttpServer = url.getPath();
    if ((pathOnHttpServer == null) || (pathOnHttpServer.length() < 1))
    {
      pathOnHttpServer = "/";
    }
    long timeoutInMs = 10000L;
    
    HttpUtil.getInstance();
    return HttpUtil.get(lowerLayerNetSocket, httpServerNetAddress, pathOnHttpServer, 10000L);
  }
}
