package org.silvertunnel_ng.netlib.adapter.url.impl.net.http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.CacheRequest;
import java.net.CookieHandler;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
















public class HttpClient
  extends NetworkClient
{
  private static final Logger LOG;
  protected boolean cachedHttpClient = false;
  


  private boolean inCache;
  


  protected CookieHandler cookieHandler;
  

  MessageHeader requests;
  

  PosterOutputStream poster = null;
  



  boolean failedOnce = false;
  
  private static final int HTTP_CONTINUE = 100;
  
  protected static final int DEFAULT_HTTP_PORT = 80;
  
  private String host;
  
  private int port;
  
  protected static final KeepAliveCache KAC;
  private static boolean keepAliveProp;
  private static boolean retryPostProp;
  
  protected int getDefaultPort()
  {
    return 80;
  }
  



















  private volatile boolean keepingAlive = false;
  


  private int keepAliveConnections = -1;
  








  private int keepAliveTimeout = 0;
  



  private CacheRequest cacheRequest = null;
  


  protected URL url;
  


  public boolean reuse = false;
  








  int getKeepAliveTimeout()
  {
    return keepAliveTimeout;
  }
  
  static
  {
    LOG = LoggerFactory.getLogger(HttpClient.class);
    















































    KAC = new KeepAliveCache();
    
    keepAliveProp = true;
    



    retryPostProp = true;
    














































    String keepAlive = System.getProperty("http.keepAlive");
    
    String retryPost = System.getProperty("sun.net.http.retryPost");
    
    if (keepAlive != null) {
      keepAliveProp = Boolean.valueOf(keepAlive).booleanValue();
    } else {
      keepAliveProp = true;
    }
    
    if (retryPost != null) {
      retryPostProp = Boolean.valueOf(retryPost).booleanValue();
    } else {
      retryPostProp = true;
    }
  }
  




  public boolean getHttpKeepAliveSet()
  {
    return keepAliveProp;
  }
  





  protected HttpClient(NetLayer lowerNetLayer, URL url, int to)
    throws IOException
  {
    super(lowerNetLayer);
    proxy = Proxy.NO_PROXY;
    host = url.getHost();
    this.url = url;
    port = url.getPort();
    if (port == -1) {
      port = getDefaultPort();
    }
    setConnectTimeout(to);
    


    cookieHandler = ((CookieHandler)AccessController.doPrivileged(new PrivilegedAction()
    {
      public CookieHandler run()
      {
        return CookieHandler.getDefault();
      }
      
    }));
    openServer();
  }
  











  public static HttpClient New(NetLayer lowerNetLayer, URL url)
    throws IOException
  {
    return New(lowerNetLayer, url, -1, true);
  }
  





  public static HttpClient New(NetLayer lowerNetLayer, URL url, boolean useCache)
    throws IOException
  {
    return New(lowerNetLayer, url, -1, useCache);
  }
  






  public static HttpClient New(NetLayer lowerNetLayer, URL url, int to, boolean useCache)
    throws IOException
  {
    Proxy p = Proxy.NO_PROXY;
    
    HttpClient ret = null;
    
    if (useCache) {
      ret = KAC.get(url, null);
      if (ret != null) {
        if (((proxy != null) && (proxy.equals(p))) || ((proxy == null) && (p == null)))
        {
          synchronized (ret) {
            cachedHttpClient = true;
            assert (inCache);
            inCache = false;
          }
          

        }
        else
        {

          inCache = false;
          ret.closeServer();
          ret = null;
        }
      }
    }
    if (ret == null) {
      ret = new HttpClient(lowerNetLayer, url, to);
    } else {
      SecurityManager security = System.getSecurityManager();
      if (security != null) {
        security.checkConnect(url.getHost(), url.getPort());
      }
      url = url;
    }
    return ret;
  }
  








  public void finished()
  {
    if (reuse)
    {
      return;
    }
    keepAliveConnections -= 1;
    poster = null;
    if ((keepAliveConnections > 0) && (isKeepingAlive()) && 
      (!serverOutput.checkError()))
    {



      putInKeepAliveCache();
    } else {
      closeServer();
    }
  }
  
  protected synchronized void putInKeepAliveCache() {
    if (inCache) {
      if (!$assertionsDisabled) throw new AssertionError("Duplicate put to keep alive cache");
      return;
    }
    inCache = true;
    KAC.put(url, null, this);
  }
  
  protected boolean isInKeepAliveCache() {
    return inCache;
  }
  


  public void closeIdleConnection()
  {
    HttpClient http = KAC.get(url, null);
    if (http != null) {
      http.closeServer();
    }
  }
  




  public void openServer(String server, int port)
    throws IOException
  {
    serverSocket = doConnect(server, port);
    try
    {
      serverOutput = new PrintStream(new BufferedOutputStream(serverSocket.getOutputStream()), false, encoding);
    } catch (UnsupportedEncodingException e) {
      throw new InternalError(encoding + " encoding not found");
    }
  }
  




  public boolean needsTunneling()
  {
    return false;
  }
  


  public boolean isCachedConnection()
  {
    return cachedHttpClient;
  }
  












  protected synchronized void openServer()
    throws IOException
  {
    SecurityManager security = System.getSecurityManager();
    if (security != null) {
      security.checkConnect(host, port);
    }
    
    if (keepingAlive) {
      return;
    }
    
    if ((url.getProtocol().equals("http")) || 
      (url.getProtocol().equals("https")))
    {

      openServer(host, port);
      return;
    }
    





    super.openServer(host, port);
  }
  

  public String getURLFile()
    throws IOException
  {
    String fileName = url.getFile();
    if ((fileName == null) || (fileName.length() == 0)) {
      fileName = "/";
    }
    
    if (fileName.indexOf('\n') == -1) {
      return fileName;
    }
    throw new MalformedURLException("Illegal character in URL");
  }
  



  @Deprecated
  public void writeRequests(MessageHeader head)
  {
    requests = head;
    requests.print(serverOutput);
    serverOutput.flush();
  }
  
  public void writeRequests(MessageHeader head, PosterOutputStream pos) throws IOException {
    requests = head;
    requests.print(serverOutput);
    poster = pos;
    if (poster != null) {
      poster.writeTo(serverOutput);
    }
    serverOutput.flush();
  }
  














  public boolean parseHTTP(MessageHeader responses, ProgressSource pi, HttpURLConnection httpuc)
    throws IOException
  {
    try
    {
      serverInput = serverSocket.getInputStream();
      serverInput = new BufferedInputStream(serverInput);
      return parseHTTPHeader(responses, pi, httpuc);
    }
    catch (SocketTimeoutException stex) {
      closeServer();
      throw stex;
    } catch (IOException e) {
      closeServer();
      cachedHttpClient = false;
      if ((!failedOnce) && (requests != null)) {
        if ((httpuc.getRequestMethod().equals("POST")) && (!retryPostProp))
        {
          LOG.debug("do not retry request");
        }
        else {
          failedOnce = true;
          openServer();
          if (needsTunneling()) {
            httpuc.doTunneling();
          }
          afterConnect();
          writeRequests(requests, poster);
          return parseHTTP(responses, pi, httpuc);
        }
      }
      throw e;
    }
  }
  


  public int setTimeout(int timeout)
    throws SocketException
  {
    return -1;
  }
  










  private boolean parseHTTPHeader(MessageHeader responses, ProgressSource pi, HttpURLConnection httpuc)
    throws IOException
  {
    keepAliveConnections = -1;
    keepAliveTimeout = 0;
    
    boolean ret = false;
    byte[] b = new byte[8];
    try
    {
      int nread = 0;
      serverInput.mark(10);
      while (nread < 8) {
        int r = serverInput.read(b, nread, 8 - nread);
        if (r < 0) {
          break;
        }
        nread += r;
      }
      String keep = null;
      ret = (b[0] == 72) && (b[1] == 84) && (b[2] == 84) && (b[3] == 80) && (b[4] == 47) && (b[5] == 49) && (b[6] == 46);
      
      serverInput.reset();
      if (ret) {
        responses.parseHeader(serverInput);
        if (LOG.isDebugEnabled()) {
          LOG.debug("response header : {}", responses.toString());
        }
        

        if (cookieHandler != null) {
          URI uri = ParseUtil.toURI(url);
          



          if (uri != null) {
            cookieHandler.put(uri, responses.getHeaders());
          }
        }
        







        if (keep == null) {
          keep = responses.findValue("Connection");
        }
        if ((keep != null) && (keep.equalsIgnoreCase("keep-alive")))
        {




          HeaderParser p = new HeaderParser(responses.findValue("Keep-Alive"));
          if (p != null)
          {
            boolean usingProxy = false;
            keepAliveConnections = p.findInt("max", 5);
            keepAliveTimeout = p.findInt("timeout", 5);
          }
        } else if (b[7] != 48)
        {



          if (keep != null)
          {




            keepAliveConnections = 1;
          } else
            keepAliveConnections = 5;
        }
      } else {
        if (nread != 8) {
          if ((!failedOnce) && (requests != null)) {
            if ((httpuc.getRequestMethod().equals("POST")) && (!retryPostProp))
            {

              LOG.debug("do not retry request");
            } else {
              failedOnce = true;
              closeServer();
              cachedHttpClient = false;
              openServer();
              if (needsTunneling()) {
                httpuc.doTunneling();
              }
              afterConnect();
              writeRequests(requests, poster);
              return parseHTTP(responses, pi, httpuc);
            }
          }
          throw new SocketException("Unexpected end of file from server");
        }
        
        responses.set("Content-type", "unknown/unknown");
      }
    } catch (IOException e) {
      throw e;
    }
    
    int code = -1;
    try
    {
      String resp = responses.getValue(0);
      




      int ind = resp.indexOf(' ');
      while (resp.charAt(ind) == ' ') {
        ind++;
      }
      code = Integer.parseInt(resp.substring(ind, ind + 3));
    } catch (Exception e) {
      LOG.debug("got Exception while trying to extract the status code : {}", e, e);
    }
    
    if (code == 100) {
      responses.reset();
      return parseHTTPHeader(responses, pi, httpuc);
    }
    
    long cl = -1L;
    





    String te = null;
    try {
      te = responses.findValue("Transfer-Encoding");
    } catch (Exception e) {
      LOG.debug("got Exception while retrieving Transfer-Encoding value : {}", e, e);
    }
    if ((te != null) && (te.equalsIgnoreCase("chunked"))) {
      serverInput = new ChunkedInputStream(serverInput, this, responses);
      




      if (keepAliveConnections <= 1) {
        keepAliveConnections = 1;
        keepingAlive = false;
      } else {
        keepingAlive = true;
      }
      failedOnce = false;


    }
    else
    {

      try
      {

        cl = Long.parseLong(responses.findValue("content-length"));
      } catch (Exception e) {
        cl = -1L;
      }
      

      String requestLine = requests.getKey(0);
      
      if (((requestLine != null) && (requestLine.startsWith("HEAD"))) || (code == 304) || (code == 204))
      {

        cl = 0L;
      }
      
      if ((keepAliveConnections > 1) && ((cl >= 0L) || (code == 304) || (code == 204)))
      {

        keepingAlive = true;
        failedOnce = false;
      } else if (keepingAlive)
      {




        keepingAlive = false;
      }
    }
    


    if (cl > 0L)
    {


      if (pi != null)
      {
        pi.setContentType(responses.findValue("content-type"));
      }
      
      if (isKeepingAlive())
      {
        serverInput = new KeepAliveStream(serverInput, pi, cl, this);
        failedOnce = false;
      } else {
        serverInput = new MeteredStream(serverInput, pi, cl);
      }
    } else if (cl == -1L)
    {



      if (pi != null)
      {

        pi.setContentType(responses.findValue("content-type"));
        


        serverInput = new MeteredStream(serverInput, pi, cl);



      }
      



    }
    else if (pi != null) {
      pi.finishTracking();
    }
    

    return ret;
  }
  
  public synchronized InputStream getInputStream() {
    return serverInput;
  }
  
  public OutputStream getOutputStream() {
    return serverOutput;
  }
  
  public String toString()
  {
    return getClass().getName() + "(" + url + ")";
  }
  
  public final boolean isKeepingAlive() {
    return (getHttpKeepAliveSet()) && (keepingAlive);
  }
  
  public void setCacheRequest(CacheRequest cacheRequest) {
    this.cacheRequest = cacheRequest;
  }
  
  CacheRequest getCacheRequest() {
    return cacheRequest;
  }
  






  public void setDoNotRetry(boolean value)
  {
    failedOnce = value;
  }
  
  public void closeServer()
  {
    try
    {
      keepingAlive = false;
      serverSocket.close();
    } catch (Exception e) {
      LOG.debug("got Exception trying to close the socket : {}", e, e);
    }
  }
  



  public String getProxyHostUsed()
  {
    return null;
  }
  



  public int getProxyPortUsed()
  {
    return -1;
  }
  
  @Deprecated
  public static synchronized void resetProperties() {}
  
  public void afterConnect()
    throws IOException, UnknownHostException
  {}
  
  protected void finalize()
    throws Throwable
  {}
}
