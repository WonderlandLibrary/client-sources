package org.silvertunnel_ng.netlib.adapter.url.impl.net.http;

import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Authenticator.RequestorType;
import java.net.CacheRequest;
import java.net.CacheResponse;
import java.net.CookieHandler;
import java.net.HttpRetryException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.ResponseCache;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpURLConnection extends java.net.HttpURLConnection
{
  private static final Logger LOG = LoggerFactory.getLogger("sun.net.www.protocol.http.HttpURLConnection");
  

















































  private static boolean enableESBuffer = false;
  



  private static int timeout4ESBuffer = 0;
  



  private static int bufSize4ES = 0;
  

  static final String version = "";
  public static final String userAgent = "";
  static final int maxRedirects = 3;
  static final boolean validateProxy = false;
  static final boolean validateServer = false;
  








































  private static final String[] EXCLUDE_HEADERS = { "Proxy-Authorization", "Authorization" };
  














  protected PrintStream ps = null;
  



  private InputStream errorStream = null;
  



  private boolean setUserCookies = true;
  private String userCookies = null;
  

















  AuthenticationInfo currentProxyCredentials = null;
  AuthenticationInfo currentServerCredentials = null;
  boolean needToCheck = true;
  private boolean doingNTLM2ndStage = false;
  


  private boolean doingNTLMp2ndStage = false;
  























  private InputStream inputStream = null;
  


  private PosterOutputStream poster = null;
  



  private boolean setRequests = false;
  



  private boolean failedOnce = false;
  




  private Exception rememberedException = null;
  



  private HttpClient reuseClient = null;
  





  static enum TunnelState
  {
    NONE, 
    



    SETUP, 
    



    TUNNELING;
    
    private TunnelState() {} }
  private TunnelState tunnelState = TunnelState.NONE;
  




  private int connectTimeout = -1;
  private int readTimeout = -1;
  






  protected long fixedContentLengthLong = -1L;
  















  private static PasswordAuthentication privilegedRequestPasswordAuthentication(String host, final InetAddress addr, final int port, final String protocol, final String prompt, final String scheme, final URL url, final Authenticator.RequestorType authType)
  {
    (PasswordAuthentication)AccessController.doPrivileged(new PrivilegedAction()
    {
      public PasswordAuthentication run() {
        return java.net.Authenticator.requestPasswordAuthentication(val$host, addr, port, protocol, prompt, scheme, url, authType);
      }
    });
  }
  










  private void checkMessageHeader(String key, String value)
  {
    int index = key.indexOf('\n');
    if (index != -1) {
      throw new IllegalArgumentException("Illegal character(s) in message header field: " + key);
    }
    if (value == null) {
      return;
    }
    
    index = value.indexOf('\n');
    while (index != -1) {
      index++;
      if (index < value.length()) {
        char c = value.charAt(index);
        if ((c == ' ') || (c == '\t'))
        {
          index = value.indexOf('\n', index);
          continue;
        }
      }
      throw new IllegalArgumentException("Illegal character(s) in message header value: " + value);
    }
  }
  







  private void writeRequests()
    throws IOException
  {
    if (!setRequests)
    {









      if (!failedOnce) {
        requests.prepend(method + " " + http.getURLFile() + " " + "HTTP/1.1", null);
      }
      if (!getUseCaches()) {
        requests.setIfNotSet("Cache-Control", "no-cache");
        requests.setIfNotSet("Pragma", "no-cache");
      }
      requests.setIfNotSet("User-Agent", userAgent);
      int port = url.getPort();
      String host = url.getHost();
      if ((port != -1) && (port != url.getDefaultPort())) {
        host = host + ":" + String.valueOf(port);
      }
      requests.setIfNotSet("Host", host);
      requests.setIfNotSet("Accept", "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2");
      








      if ((!failedOnce) && (http.getHttpKeepAliveSet())) {
        requests.setIfNotSet("Connection", "keep-alive");


      }
      else
      {

        requests.setIfNotSet("Connection", "close");
      }
      
      long modTime = getIfModifiedSince();
      if (modTime != 0L) {
        Date date = new Date(modTime);
        

        SimpleDateFormat fo = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", java.util.Locale.US);
        fo.setTimeZone(java.util.TimeZone.getTimeZone("GMT"));
        requests.setIfNotSet("If-Modified-Since", fo.format(date));
      }
      
      AuthenticationInfo sauth = AuthenticationInfo.getServerAuth(url);
      if ((sauth != null) && (sauth.supportsPreemptiveAuthorization()))
      {
        requests.setIfNotSet(sauth.getHeaderName(), sauth.getHeaderValue(url, method));
        currentServerCredentials = sauth;
      }
      
      if ((!method.equals("PUT")) && ((poster != null) || (streaming()))) {
        requests.setIfNotSet("Content-type", "application/x-www-form-urlencoded");
      }
      
      if (streaming()) {
        if (chunkLength != -1) {
          requests.set("Transfer-Encoding", "chunked");
        }
        else if (fixedContentLengthLong != -1L) {
          requests.set("Content-Length", String.valueOf(fixedContentLengthLong));
        } else if (fixedContentLength != -1) {
          requests.set("Content-Length", String.valueOf(fixedContentLength));
        }
      }
      else if (poster != null)
      {
        synchronized (poster)
        {
          poster.close();
          requests.set("Content-Length", String.valueOf(poster.size()));
        }
      }
      


      setCookieHeader();
      
      setRequests = true;
    }
    if (LOG.isDebugEnabled()) {
      LOG.debug(requests.toString());
    }
    http.writeRequests(requests, poster);
    if (ps.checkError()) {
      String proxyHost = http.getProxyHostUsed();
      int proxyPort = http.getProxyPortUsed();
      disconnectInternal();
      if (failedOnce) {
        throw new IOException("Error writing to server");
      }
      failedOnce = true;
      if (proxyHost != null) {
        setProxiedClient(url, proxyHost, proxyPort);
      } else {
        setNewClient(url);
      }
      ps = ((PrintStream)http.getOutputStream());
      connected = true;
      responses = new MessageHeader();
      setRequests = false;
      writeRequests();
    }
  }
  





  protected void setNewClient(URL url)
    throws IOException
  {
    setNewClient(url, false);
  }
  




  protected void setNewClient(URL url, boolean useCache)
    throws IOException
  {
    http = HttpClient.New(lowerNetLayer, url, useCache);
    http.setReadTimeout(readTimeout);
  }
  







  protected void setProxiedClient(URL url, String proxyHost, int proxyPort)
    throws IOException
  {
    setProxiedClient(url, proxyHost, proxyPort, false);
  }
  








  protected void setProxiedClient(URL url, String proxyHost, int proxyPort, boolean useCache)
    throws IOException
  {
    proxiedConnect(url, proxyHost, proxyPort, useCache);
  }
  
  protected void proxiedConnect(URL url, String proxyHost, int proxyPort, boolean useCache) throws IOException {
    http = HttpClient.New(lowerNetLayer, url, useCache);
    http.setReadTimeout(readTimeout);
  }
  
  protected HttpURLConnection(NetLayer lowerNetLayer, URL u, Handler handler)
    throws IOException
  {
    this(lowerNetLayer, u, null, handler);
  }
  
  public HttpURLConnection(NetLayer lowerNetLayer, URL u, String host, int port) {
    this(lowerNetLayer, u, new Proxy(java.net.Proxy.Type.HTTP, 
      java.net.InetSocketAddress.createUnresolved(host, port)));
  }
  



  public HttpURLConnection(NetLayer lowerNetLayer, URL u, Proxy p)
  {
    this(lowerNetLayer, u, p, new Handler());
  }
  
  protected HttpURLConnection(NetLayer lowerNetLayer, URL u, Proxy p, Handler handler) {
    super(u);
    this.lowerNetLayer = lowerNetLayer;
    requests = new MessageHeader();
    responses = new MessageHeader();
    this.handler = handler;
    instProxy = p;
  }
  











  public static InputStream openConnectionCheckRedirects(URLConnection c)
    throws IOException
  {
    int redirects = 0;
    InputStream in = null;
    boolean redir;
    do {
      if ((c instanceof HttpURLConnection)) {
        ((HttpURLConnection)c).setInstanceFollowRedirects(false);
      }
      



      in = c.getInputStream();
      redir = false;
      
      if ((c instanceof HttpURLConnection)) {
        HttpURLConnection http = (HttpURLConnection)c;
        int stat = http.getResponseCode();
        if ((stat >= 300) && (stat <= 307) && (stat != 306) && (stat != 304))
        {
          URL base = http.getURL();
          String loc = http.getHeaderField("Location");
          URL target = null;
          if (loc != null) {
            target = new URL(base, loc);
          }
          http.disconnect();
          if ((target == null) || 
            (!base.getProtocol().equals(target.getProtocol())) || 
            (base.getPort() != target.getPort()) || 
            (!hostsEqual(base, target)) || (redirects >= 5)) {
            throw new SecurityException("illegal URL redirect");
          }
          redir = true;
          c = target.openConnection();
          redirects++;
        }
        
      }
    } while (redir);
    return in;
  }
  


  private static boolean hostsEqual(URL url1, URL url2)
  {
    String h1 = url1.getHost();
    final String h2 = url2.getHost();
    
    if (h1 == null)
      return h2 == null;
    if (h2 == null)
      return false;
    if (h1.equalsIgnoreCase(h2)) {
      return true;
    }
    

    final boolean[] result = { false };
    

    AccessController.doPrivileged(new PrivilegedAction()
    {
      public Void run()
      {
        try {
          InetAddress a1 = InetAddress.getByName(val$h1);
          InetAddress a2 = InetAddress.getByName(h2);
          result[0] = a1.equals(a2);
        } catch (UnknownHostException e) {
          HttpURLConnection.LOG.debug("got UnknownHostException : {}", e.getMessage(), e);
        } catch (SecurityException e) {
          HttpURLConnection.LOG.debug("got SecurityException : {}", e.getMessage(), e);
        }
        return null;
      }
      
    });
    return result[0];
  }
  

  public void connect()
    throws IOException
  {
    plainConnect();
  }
  
  private boolean checkReuseConnection() {
    if (connected) {
      return true;
    }
    if (reuseClient != null) {
      http = reuseClient;
      http.setReadTimeout(getReadTimeout());
      http.reuse = false;
      reuseClient = null;
      connected = true;
      return true;
    }
    return false;
  }
  
  protected void plainConnect() throws IOException {
    if (connected) {
      return;
    }
    
    if ((cacheHandler != null) && (getUseCaches())) {
      try {
        URI uri = ParseUtil.toURI(url);
        if (uri != null) {
          cachedResponse = cacheHandler.get(uri, getRequestMethod(), requests
            .getHeaders(EXCLUDE_HEADERS));
          if (("https".equalsIgnoreCase(uri.getScheme())) && (!(cachedResponse instanceof java.net.SecureCacheResponse)))
          {
            cachedResponse = null;
          }
          if (cachedResponse != null) {
            cachedHeaders = mapToMessageHeader(cachedResponse
              .getHeaders());
            cachedInputStream = cachedResponse.getBody();
          }
        }
      }
      catch (IOException ioex) {
        LOG.debug("got IOException : {}", ioex.getMessage(), ioex);
      }
      if ((cachedHeaders != null) && (cachedInputStream != null)) {
        connected = true;
        return;
      }
      cachedResponse = null;
    }
    






    try
    {
      if (instProxy == null)
      {



        ProxySelector sel = (ProxySelector)AccessController.doPrivileged(new PrivilegedAction()
        {
          public ProxySelector run() {
            return ProxySelector.getDefault();
          }
        });
        Proxy p = null;
        if (sel != null) {
          URI uri = ParseUtil.toURI(url);
          Iterator<Proxy> it = sel.select(uri).iterator();
          while (it.hasNext()) {
            p = (Proxy)it.next();
            try {
              if (!failedOnce) {
                http = getNewHttpClient(url, p, connectTimeout);
                http.setReadTimeout(readTimeout);

              }
              else
              {
                http = getNewHttpClient(url, p, connectTimeout, false);
                http.setReadTimeout(readTimeout);
              }
            }
            catch (IOException ioex) {
              if (p != Proxy.NO_PROXY) {
                sel.connectFailed(uri, p.address(), ioex);
                if (!it.hasNext())
                {
                  http = getNewHttpClient(url, null, connectTimeout, false);
                  http.setReadTimeout(readTimeout);
                  break;
                }
              } else {
                throw ioex;
              }
              
            }
            
          }
        }
        else if (!failedOnce) {
          http = getNewHttpClient(url, null, connectTimeout);
          http.setReadTimeout(readTimeout);
        }
        else
        {
          http = getNewHttpClient(url, null, connectTimeout, false);
          http.setReadTimeout(readTimeout);
        }
        
      }
      else if (!failedOnce) {
        http = getNewHttpClient(url, instProxy, connectTimeout);
        http.setReadTimeout(readTimeout);
      }
      else
      {
        http = getNewHttpClient(url, instProxy, connectTimeout, false);
        http.setReadTimeout(readTimeout);
      }
      

      ps = ((PrintStream)http.getOutputStream());
    } catch (IOException e) {
      throw e;
    }
    
    connected = true;
  }
  
  protected HttpClient getNewHttpClient(URL url, Proxy p, int connectTimeout) throws IOException
  {
    return HttpClient.New(lowerNetLayer, url, connectTimeout, true);
  }
  
  protected HttpClient getNewHttpClient(URL url, Proxy p, int connectTimeout, boolean useCache) throws IOException
  {
    return HttpClient.New(lowerNetLayer, url, connectTimeout, useCache);
  }
  






  public synchronized OutputStream getOutputStream()
    throws IOException
  {
    try
    {
      if (!doOutput) {
        throw new ProtocolException("cannot write to a URLConnection if doOutput=false - call setDoOutput(true)");
      }
      

      if (method.equals("GET")) {
        method = "POST";
      }
      if ((!"POST".equals(method)) && (!"PUT".equals(method)) && 
        ("http".equals(url.getProtocol()))) {
        throw new ProtocolException("HTTP method " + method + " doesn't support output");
      }
      


      if (inputStream != null) {
        throw new ProtocolException("Cannot write output after reading input.");
      }
      
      if (!checkReuseConnection()) {
        connect();
      }
      






      if ((streaming()) && (strOutputStream == null)) {
        writeRequests();
      }
      ps = ((PrintStream)http.getOutputStream());
      if (streaming()) {
        if (strOutputStream == null) {
          if (chunkLength != -1)
          {




            strOutputStream = new StreamingOutputStream(ps, -1L);
          } else {
            long length = 0L;
            if (fixedContentLengthLong != -1L) {
              length = fixedContentLengthLong;
            } else if (fixedContentLength != -1) {
              length = fixedContentLength;
            }
            strOutputStream = new StreamingOutputStream(ps, length);
          }
        }
        return strOutputStream;
      }
      if (poster == null) {
        poster = new PosterOutputStream();
      }
      return poster;
    }
    catch (RuntimeException e) {
      disconnectInternal();
      throw e;
    } catch (IOException e) {
      disconnectInternal();
      throw e;
    }
  }
  
  private boolean streaming() {
    return (fixedContentLength != -1) || (fixedContentLengthLong != -1L) || (chunkLength != -1);
  }
  


  private void setCookieHeader()
    throws IOException
  {
    if (cookieHandler != null)
    {


      if (setUserCookies) {
        int k = requests.getKeyPos("Cookie");
        if (k != -1) {
          userCookies = requests.getValue(k);
        }
        setUserCookies = false;
      }
      

      requests.remove("Cookie");
      
      URI uri = ParseUtil.toURI(url);
      if (uri != null) {
        Map<String, List<String>> cookies = cookieHandler.get(uri, requests
          .getHeaders(EXCLUDE_HEADERS));
        if (!cookies.isEmpty())
          for (Map.Entry<String, List<String>> entry : cookies
            .entrySet()) {
            String key = (String)entry.getKey();
            

            if (("Cookie".equalsIgnoreCase(key)) || 
              ("Cookie2".equalsIgnoreCase(key)))
            {

              List<String> l = (List)entry.getValue();
              if ((l != null) && (!l.isEmpty())) {
                StringBuilder cookieValue = new StringBuilder();
                for (String value : l) {
                  cookieValue.append(value).append("; ");
                }
                try
                {
                  requests.add(key, cookieValue
                  
                    .substring(0, cookieValue
                    .length() - 2));
                }
                catch (StringIndexOutOfBoundsException ignored) {
                  LOG.debug("got StringIndexOutOfBoundsException : {}", ignored
                    .getMessage());
                }
              }
            }
          }
      }
      if (userCookies != null) {
        int k;
        if ((k = requests.getKeyPos("Cookie")) != -1) {
          requests.set("Cookie", requests.getValue(k) + ";" + userCookies);
        }
        else {
          requests.set("Cookie", userCookies);
        }
      }
    }
  }
  
  public synchronized InputStream getInputStream()
    throws IOException
  {
    if (!doInput) {
      throw new ProtocolException("Cannot read from URLConnection if doInput=false (call setDoInput(true))");
    }
    

    if (rememberedException != null) {
      if ((rememberedException instanceof RuntimeException)) {
        throw new RuntimeException(rememberedException);
      }
      throw getChainedException((IOException)rememberedException);
    }
    

    if (inputStream != null) {
      return inputStream;
    }
    
    if (streaming()) {
      if (strOutputStream == null) {
        getOutputStream();
      }
      
      strOutputStream.close();
      if (!strOutputStream.writtenOK()) {
        throw new IOException("Incomplete output stream");
      }
    }
    
    int redirects = 0;
    int respCode = 0;
    long cl = -1L;
    AuthenticationInfo serverAuthentication = null;
    AuthenticationInfo proxyAuthentication = null;
    AuthenticationHeader srvHdr = null;
    

















    boolean inNegotiate = false;
    boolean inNegotiateProxy = false;
    

    isUserServerAuth = (requests.getKeyPos("Authorization") != -1);
    isUserProxyAuth = (requests.getKeyPos("Proxy-Authorization") != -1);
    try
    {
      do {
        if (!checkReuseConnection()) {
          connect();
        }
        
        if (cachedInputStream != null) {
          return cachedInputStream;
        }
        














        ps = ((PrintStream)http.getOutputStream());
        
        if (!streaming()) {
          writeRequests();
        }
        http.parseHTTP(responses, pi, this);
        if (LOG.isDebugEnabled()) {
          LOG.debug(responses.toString());
        }
        inputStream = http.getInputStream();
        
        respCode = getResponseCode();
        if (respCode == 407) {
          if (streaming()) {
            disconnectInternal();
            throw new HttpRetryException("cannot retry due to proxy authentication, in streaming mode", 407);
          }
          


          boolean dontUseNegotiate = false;
          Iterator iter = responses.multiValueIterator("Proxy-Authenticate");
          while (iter.hasNext()) {
            String value = ((String)iter.next()).trim();
            if ((value.equalsIgnoreCase("Negotiate")) || 
              (value.equalsIgnoreCase("Kerberos"))) {
              if (!inNegotiateProxy) {
                inNegotiateProxy = true; break;
              }
              dontUseNegotiate = true;
              doingNTLMp2ndStage = false;
              proxyAuthentication = null;
              
              break;
            }
          }
          








          AuthenticationHeader authhdr = new AuthenticationHeader("Proxy-Authenticate", responses, http.getProxyHostUsed());
          

          if (!doingNTLMp2ndStage) {
            proxyAuthentication = resetProxyAuthentication(proxyAuthentication, authhdr);
            if (proxyAuthentication != null) {
              redirects++;
              disconnectInternal();
              continue;
            }
          }
          else {
            String raw = responses.findValue("Proxy-Authenticate");
            reset();
            if (!proxyAuthentication.setHeaders(this, authhdr.headerParser(), raw)) {
              disconnectInternal();
              throw new IOException("Authentication failure");
            }
            if ((serverAuthentication != null) && (srvHdr != null))
            {
              if (!serverAuthentication.setHeaders(this, srvHdr
                .headerParser(), raw)) {
                disconnectInternal();
                throw new IOException("Authentication failure");
              } }
            authObj = null;
            doingNTLMp2ndStage = false;
            continue;
          }
        }
        

        if (proxyAuthentication != null)
        {
          proxyAuthentication.addToCache();
        }
        
        if (respCode == 401) {
          if (streaming()) {
            disconnectInternal();
            throw new HttpRetryException("cannot retry due to server authentication, in streaming mode", 401);
          }
          


          boolean dontUseNegotiate = false;
          Iterator iter = responses.multiValueIterator("WWW-Authenticate");
          while (iter.hasNext()) {
            String value = ((String)iter.next()).trim();
            if ((value.equalsIgnoreCase("Negotiate")) || 
              (value.equalsIgnoreCase("Kerberos"))) {
              if (!inNegotiate) {
                inNegotiate = true; break;
              }
              dontUseNegotiate = true;
              doingNTLM2ndStage = false;
              serverAuthentication = null;
              
              break;
            }
          }
          

          srvHdr = new AuthenticationHeader("WWW-Authenticate", responses, url.getHost().toLowerCase());
          




          String raw = srvHdr.raw();
          if (!doingNTLM2ndStage) {
            if ((serverAuthentication != null) && (!(serverAuthentication instanceof NTLMAuthentication)))
            {
              if (serverAuthentication.isAuthorizationStale(raw))
              {
                disconnectInternal();
                redirects++;
                requests.set(serverAuthentication
                  .getHeaderName(), serverAuthentication
                  .getHeaderValue(url, method));
                currentServerCredentials = serverAuthentication;
                setCookieHeader();
                continue;
              }
              serverAuthentication.removeFromCache();
            }
            
            serverAuthentication = getServerAuthentication(srvHdr);
            currentServerCredentials = serverAuthentication;
            
            if (serverAuthentication != null) {
              disconnectInternal();
              redirects++;
              setCookieHeader();
              continue;
            }
          } else {
            reset();
            
            if (!serverAuthentication.setHeaders(this, null, raw)) {
              disconnectInternal();
              throw new IOException("Authentication failure");
            }
            doingNTLM2ndStage = false;
            authObj = null;
            setCookieHeader();
            continue;
          }
        }
        
        if (serverAuthentication != null)
        {
          if ((!(serverAuthentication instanceof DigestAuthentication)) || (domain == null)) {
            if ((serverAuthentication instanceof BasicAuthentication))
            {

              String npath = AuthenticationInfo.reducePath(url.getPath());
              String opath = path;
              if ((!opath.startsWith(npath)) || (npath.length() >= opath.length()))
              {
                npath = BasicAuthentication.getRootPath(opath, npath);
              }
              
              BasicAuthentication a = (BasicAuthentication)serverAuthentication.clone();
              serverAuthentication.removeFromCache();
              path = npath;
              serverAuthentication = a;
            }
            serverAuthentication.addToCache();
          }
          else
          {
            DigestAuthentication srv = (DigestAuthentication)serverAuthentication;
            StringTokenizer tok = new StringTokenizer(domain, " ");
            String realm = realm;
            PasswordAuthentication pw = pw;
            digestparams = params;
            while (tok.hasMoreTokens()) {
              String path = tok.nextToken();
              


              try
              {
                URL u = new URL(url, path);
                DigestAuthentication d = new DigestAuthentication(false, u, realm, "Digest", pw, digestparams);
                
                d.addToCache();
              } catch (Exception e) {
                LOG.debug("got Exception : {}", e, e);
              }
            }
          }
        }
        



        inNegotiate = false;
        inNegotiateProxy = false;
        

        doingNTLMp2ndStage = false;
        doingNTLM2ndStage = false;
        if (!isUserServerAuth) {
          requests.remove("Authorization");
        }
        if (!isUserProxyAuth) {
          requests.remove("Proxy-Authorization");
        }
        
        if (respCode == 200) {
          checkResponseCredentials(false);
        } else {
          needToCheck = false;
        }
        

        needToCheck = true;
        
        if (followRedirect())
        {




          redirects++;
          


          setCookieHeader();
        }
        else
        {
          try
          {
            cl = Long.parseLong(responses.findValue("content-length"));
          }
          catch (Exception localException2) {}
          


          if ((method.equals("HEAD")) || (cl == 0L) || (respCode == 304) || (respCode == 204))
          {


            if (pi != null) {
              pi.finishTracking();
              pi = null;
            }
            http.finished();
            http = null;
            inputStream = new EmptyInputStream();
            connected = false;
          }
          Object uri;
          if ((respCode == 200) || (respCode == 203) || (respCode == 206) || (respCode == 300) || (respCode == 301) || (respCode == 410))
          {

            if (cacheHandler != null)
            {
              uri = ParseUtil.toURI(url);
              if (uri != null) {
                URLConnection uconn = this;
                if ("https".equalsIgnoreCase(((URI)uri).getScheme()))
                {

                  try
                  {


                    uconn = (URLConnection)getClass().getField("httpsURLConnection").get(this);
                  }
                  catch (IllegalAccessException iae) {
                    LOG.debug("got IllegalAccessException : {}", iae
                      .getMessage(), iae);
                  }
                  catch (NoSuchFieldException nsfe)
                  {
                    LOG.debug("got NoSuchFieldException : {}", nsfe
                      .getMessage(), nsfe);
                  }
                }
                
                CacheRequest cacheRequest = cacheHandler.put((URI)uri, uconn);
                
                if ((cacheRequest != null) && (http != null)) {
                  http.setCacheRequest(cacheRequest);
                  inputStream = new HttpInputStream(inputStream, cacheRequest);
                }
              }
            }
          }
          

          if (!(inputStream instanceof HttpInputStream)) {
            inputStream = new HttpInputStream(inputStream);
          }
          
          if (respCode >= 400) {
            if ((respCode == 404) || (respCode == 410)) {
              throw new java.io.FileNotFoundException(url.toString());
            }
            

            throw new IOException("Server returned HTTP response code: " + respCode + " for URL: " + url.toString());
          }
          
          poster = null;
          strOutputStream = null;
          return inputStream;
        }
      } while (redirects < maxRedirects);
      
      throw new ProtocolException("Server redirected too many  times (" + redirects + ")");
    }
    catch (RuntimeException e) {
      disconnectInternal();
      rememberedException = e;
      throw e;
    } catch (IOException e) {
      rememberedException = e;
      


      String te = responses.findValue("Transfer-Encoding");
      if ((http != null) && 
        (http.isKeepingAlive()) && (enableESBuffer)) if (cl <= 0L) { if (te != null)
          {
            if (!te.equalsIgnoreCase("chunked")) {} }
        } else { errorStream = ErrorStream.getErrorStream(inputStream, cl, http);
        }
      throw e;
    } finally {
      if ((respCode == 407) && (proxyAuthentication != null)) {
        proxyAuthentication.endAuthRequest();
      } else if ((respCode == 401) && (serverAuthentication != null))
      {
        serverAuthentication.endAuthRequest();
      }
    }
  }
  




  private IOException getChainedException(final IOException rememberedException)
  {
    try
    {
      final Object[] args = { rememberedException.getMessage() };
      
      IOException chainedException = (IOException)AccessController.doPrivileged(new PrivilegedExceptionAction()
      {


        public IOException run()
          throws Exception
        {

          return (IOException)rememberedException.getClass().getConstructor(new Class[] { String.class }).newInstance(args);
        }
      });
      chainedException.initCause(rememberedException);
      return chainedException;
    } catch (Exception ignored) {}
    return rememberedException;
  }
  

  public InputStream getErrorStream()
  {
    if ((connected) && (responseCode >= 400))
    {
      if (errorStream != null)
        return errorStream;
      if (inputStream != null) {
        return inputStream;
      }
    }
    return null;
  }
  





  private AuthenticationInfo resetProxyAuthentication(AuthenticationInfo proxyAuthentication, AuthenticationHeader auth)
  {
    if ((proxyAuthentication != null) && (!(proxyAuthentication instanceof NTLMAuthentication))) {
      String raw = auth.raw();
      if (proxyAuthentication.isAuthorizationStale(raw)) {
        String value;
        String value;
        if ((tunnelState() == TunnelState.SETUP) && ((proxyAuthentication instanceof DigestAuthentication)))
        {

          value = ((DigestAuthentication)proxyAuthentication).getHeaderValue(connectRequestURI(url), "CONNECT");
        }
        else {
          value = proxyAuthentication.getHeaderValue(url, method);
        }
        requests.set(proxyAuthentication.getHeaderName(), value);
        currentProxyCredentials = proxyAuthentication;
        return proxyAuthentication;
      }
      proxyAuthentication.removeFromCache();
    }
    
    proxyAuthentication = getHttpProxyAuthentication(auth);
    currentProxyCredentials = proxyAuthentication;
    return proxyAuthentication;
  }
  




  TunnelState tunnelState()
  {
    return tunnelState;
  }
  




  void setTunnelState(TunnelState tunnelState)
  {
    this.tunnelState = tunnelState;
  }
  

  public synchronized void doTunneling()
    throws IOException
  {
    int retryTunnel = 0;
    String statusLine = "";
    int respCode = 0;
    AuthenticationInfo proxyAuthentication = null;
    String proxyHost = null;
    int proxyPort = -1;
    


    MessageHeader savedRequests = requests;
    requests = new MessageHeader();
    

    boolean inNegotiateProxy = false;
    
    try
    {
      setTunnelState(TunnelState.SETUP);
      do
      {
        if (!checkReuseConnection()) {
          proxiedConnect(url, proxyHost, proxyPort, false);
        }
        

        sendCONNECTRequest();
        responses.reset();
        


        http.parseHTTP(responses, null, this);
        

        LOG.debug(responses.toString());
        
        statusLine = responses.getValue(0);
        StringTokenizer st = new StringTokenizer(statusLine);
        st.nextToken();
        respCode = Integer.parseInt(st.nextToken().trim());
        if (respCode == 407)
        {
          boolean dontUseNegotiate = false;
          
          Iterator iter = responses.multiValueIterator("Proxy-Authenticate");
          while (iter.hasNext()) {
            String value = ((String)iter.next()).trim();
            if ((value.equalsIgnoreCase("Negotiate")) || 
              (value.equalsIgnoreCase("Kerberos"))) {
              if (!inNegotiateProxy) {
                inNegotiateProxy = true; break;
              }
              dontUseNegotiate = true;
              doingNTLMp2ndStage = false;
              proxyAuthentication = null;
              
              break;
            }
          }
          


          AuthenticationHeader authhdr = new AuthenticationHeader("Proxy-Authenticate", responses, http.getProxyHostUsed());
          
          if (!doingNTLMp2ndStage) {
            proxyAuthentication = resetProxyAuthentication(proxyAuthentication, authhdr);
            
            if (proxyAuthentication != null) {
              proxyHost = http.getProxyHostUsed();
              proxyPort = http.getProxyPortUsed();
              disconnectInternal();
              retryTunnel++;
              continue;
            }
          }
          else {
            String raw = responses.findValue("Proxy-Authenticate");
            reset();
            if (!proxyAuthentication.setHeaders(this, authhdr
              .headerParser(), raw)) {
              proxyHost = http.getProxyHostUsed();
              proxyPort = http.getProxyPortUsed();
              disconnectInternal();
              throw new IOException("Authentication failure");
            }
            authObj = null;
            doingNTLMp2ndStage = false;
            continue;
          }
        }
        
        if (proxyAuthentication != null)
        {
          proxyAuthentication.addToCache();
        }
        
        if (respCode == 200) {
          setTunnelState(TunnelState.TUNNELING);
          break;
        }
        

        disconnectInternal();
        setTunnelState(TunnelState.NONE);
        break;
      }
      while (retryTunnel < maxRedirects);
      
      if ((retryTunnel >= maxRedirects) || (respCode != 200)) {
        throw new IOException("Unable to tunnel through proxy. Proxy returns \"" + statusLine + "\"");
      }
    }
    finally {
      if ((respCode == 407) && (proxyAuthentication != null)) {
        proxyAuthentication.endAuthRequest();
      }
    }
    

    requests = savedRequests;
    

    responses.reset();
  }
  
  static String connectRequestURI(URL url) {
    String host = url.getHost();
    int port = url.getPort();
    port = port != -1 ? port : url.getDefaultPort();
    
    return host + ":" + port;
  }
  

  private void sendCONNECTRequest()
    throws IOException
  {
    int port = url.getPort();
    





    if (setRequests) {
      requests.set(0, null, null);
    }
    
    requests.prepend("CONNECT " + connectRequestURI(url) + " " + "HTTP/1.1", null);
    
    requests.setIfNotSet("User-Agent", userAgent);
    
    String host = url.getHost();
    if ((port != -1) && (port != url.getDefaultPort())) {
      host = host + ":" + String.valueOf(port);
    }
    requests.setIfNotSet("Host", host);
    

    requests.setIfNotSet("Accept", "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2");
    
    setPreemptiveProxyAuthentication(requests);
    

    LOG.debug(requests.toString());
    
    http.writeRequests(requests, null);
    
    requests.set(0, null, null);
  }
  


  private void setPreemptiveProxyAuthentication(MessageHeader requests)
  {
    AuthenticationInfo pauth = AuthenticationInfo.getProxyAuth(http.getProxyHostUsed(), http.getProxyPortUsed());
    if ((pauth != null) && (pauth.supportsPreemptiveAuthorization())) { String value;
      String value;
      if ((tunnelState() == TunnelState.SETUP) && ((pauth instanceof DigestAuthentication))) {
        value = ((DigestAuthentication)pauth).getHeaderValue(connectRequestURI(url), "CONNECT");
      } else {
        value = pauth.getHeaderValue(url, method);
      }
      

      requests.set(pauth.getHeaderName(), value);
      currentProxyCredentials = pauth;
    }
  }
  




  private AuthenticationInfo getHttpProxyAuthentication(AuthenticationHeader authhdr)
  {
    AuthenticationInfo ret = null;
    String raw = authhdr.raw();
    String host = http.getProxyHostUsed();
    int port = http.getProxyPortUsed();
    if ((host != null) && (authhdr.isPresent())) {
      HeaderParser p = authhdr.headerParser();
      String realm = p.findValue("realm");
      String scheme = authhdr.scheme();
      char schemeID;
      char schemeID; if ("basic".equalsIgnoreCase(scheme)) {
        schemeID = 'B'; } else { char schemeID;
        if ("digest".equalsIgnoreCase(scheme)) {
          schemeID = 'D';


        }
        else if ("Kerberos".equalsIgnoreCase(scheme)) {
          char schemeID = 'K';
          doingNTLMp2ndStage = true;
        } else if ("Negotiate".equalsIgnoreCase(scheme)) {
          char schemeID = 'S';
          doingNTLMp2ndStage = true;
        } else {
          schemeID = '\000';
        } }
      if (realm == null) {
        realm = "";
      }
      ret = AuthenticationInfo.getProxyAuth(host, port, realm, schemeID);
      if (ret == null) {
        if (schemeID == 'B') {
          InetAddress addr = null;
          try {
            final String finalHost = host;
            
            addr = (InetAddress)AccessController.doPrivileged(new PrivilegedExceptionAction()
            {
              public InetAddress run()
                throws UnknownHostException
              {
                return InetAddress.getByName(finalHost);
              }
            });
          }
          catch (PrivilegedActionException ignored) {
            LOG.debug("got PrivilegedActionException : {}", ignored.getMessage(), ignored);
          }
          PasswordAuthentication a = privilegedRequestPasswordAuthentication(host, addr, port, "http", realm, scheme, url, Authenticator.RequestorType.PROXY);
          

          if (a != null) {
            ret = new BasicAuthentication(true, host, port, realm, a);
          }
        }
        else if (schemeID == 'D') {
          PasswordAuthentication a = privilegedRequestPasswordAuthentication(host, null, port, url
            .getProtocol(), realm, scheme, url, Authenticator.RequestorType.PROXY);
          
          if (a != null) {
            DigestAuthentication.Parameters params = new DigestAuthentication.Parameters();
            ret = new DigestAuthentication(true, host, port, realm, scheme, a, params);









          }
          









        }
        else if (schemeID == 'S') {
          ret = new NegotiateAuthentication(true, host, port, null, "Negotiate");
        }
        else if (schemeID == 'K') {
          ret = new NegotiateAuthentication(true, host, port, null, "Kerberos");
        }
      }
      
      if ((ret != null) && 
        (!ret.setHeaders(this, p, raw))) {
        ret = null;
      }
    }
    
    return ret;
  }
  







  private AuthenticationInfo getServerAuthentication(AuthenticationHeader authenticationHeader)
  {
    AuthenticationInfo ret = null;
    String raw = authenticationHeader.raw();
    
    if (authenticationHeader.isPresent()) {
      HeaderParser p = authenticationHeader.headerParser();
      String realm = p.findValue("realm");
      String scheme = authenticationHeader.scheme();
      char schemeID;
      char schemeID; if ("basic".equalsIgnoreCase(scheme)) {
        schemeID = 'B'; } else { char schemeID;
        if ("digest".equalsIgnoreCase(scheme)) {
          schemeID = 'D';


        }
        else if ("Kerberos".equalsIgnoreCase(scheme)) {
          char schemeID = 'K';
          doingNTLM2ndStage = true;
        } else if ("Negotiate".equalsIgnoreCase(scheme)) {
          char schemeID = 'S';
          doingNTLM2ndStage = true;
        } else {
          schemeID = '\000';
        } }
      domain = p.findValue("domain");
      if (realm == null) {
        realm = "";
      }
      ret = AuthenticationInfo.getServerAuth(url, realm, schemeID);
      InetAddress addr = null;
      if (ret == null) {
        try {
          addr = InetAddress.getByName(url.getHost());
        }
        catch (UnknownHostException ignored) {
          LOG.debug("got UnknownHostException : {}", ignored.getMessage(), ignored);
        }
      }
      
      int port = url.getPort();
      if (port == -1) {
        port = url.getDefaultPort();
      }
      if (ret == null) {
        if (schemeID == 'K') {
          URL url1;
          try {
            url1 = new URL(url, "/");
          } catch (Exception e) { URL url1;
            url1 = url;
          }
          ret = new NegotiateAuthentication(false, url1, null, "Kerberos");
        }
        
        if (schemeID == 'S') {
          URL url1;
          try {
            url1 = new URL(url, "/");
          } catch (Exception e) { URL url1;
            url1 = url;
          }
          ret = new NegotiateAuthentication(false, url1, null, "Negotiate");
        }
        
        if (schemeID == 'B') {
          PasswordAuthentication a = privilegedRequestPasswordAuthentication(url
            .getHost(), addr, port, url.getProtocol(), realm, scheme, url, Authenticator.RequestorType.SERVER);
          
          if (a != null) {
            ret = new BasicAuthentication(false, url, realm, a);
          }
        }
        
        if (schemeID == 'D') {
          PasswordAuthentication a = privilegedRequestPasswordAuthentication(url
            .getHost(), addr, port, url.getProtocol(), realm, scheme, url, Authenticator.RequestorType.SERVER);
          
          if (a != null) {
            digestparams = new DigestAuthentication.Parameters();
            ret = new DigestAuthentication(false, url, realm, scheme, a, digestparams);
          }
        }
      }
      





























      if ((ret != null) && 
        (!ret.setHeaders(this, p, raw))) {
        ret = null;
      }
    }
    
    return ret;
  }
  



  private void checkResponseCredentials(boolean inClose)
    throws IOException
  {
    try
    {
      if (!needToCheck) {
        return;
      }
      if ((validateProxy) && (currentProxyCredentials != null)) {
        String raw = responses.findValue("Proxy-Authentication-Info");
        if ((inClose) || (raw != null)) {
          currentProxyCredentials.checkResponse(raw, method, url);
          currentProxyCredentials = null;
        }
      }
      if ((validateServer) && (currentServerCredentials != null)) {
        String raw = responses.findValue("Authentication-Info");
        if ((inClose) || (raw != null)) {
          currentServerCredentials.checkResponse(raw, method, url);
          currentServerCredentials = null;
        }
      }
      if ((currentServerCredentials == null) && (currentProxyCredentials == null)) {
        needToCheck = false;
      }
    } catch (IOException e) {
      disconnectInternal();
      connected = false;
      throw e;
    }
  }
  



  private boolean followRedirect()
    throws IOException
  {
    if (!getInstanceFollowRedirects()) {
      return false;
    }
    
    int stat = getResponseCode();
    if ((stat < 300) || (stat > 307) || (stat == 306) || (stat == 304))
    {
      return false;
    }
    String loc = getHeaderField("Location");
    if (loc == null)
    {



      return false;
    }
    URL locUrl;
    try {
      URL locUrl = new URL(loc);
      if (!url.getProtocol().equalsIgnoreCase(locUrl.getProtocol())) {
        return false;
      }
    }
    catch (MalformedURLException mue)
    {
      locUrl = new URL(url, loc);
    }
    disconnectInternal();
    if (streaming()) {
      throw new HttpRetryException("cannot retry due to redirection, in streaming mode", stat, loc);
    }
    

    responses = new MessageHeader();
    if (stat == 305)
    {







      String proxyHost = locUrl.getHost();
      int proxyPort = locUrl.getPort();
      
      SecurityManager security = System.getSecurityManager();
      if (security != null) {
        security.checkConnect(proxyHost, proxyPort);
      }
      
      setProxiedClient(url, proxyHost, proxyPort);
      requests.set(0, method + " " + http.getURLFile() + " " + "HTTP/1.1", null);
      
      connected = true;
    }
    else
    {
      url = locUrl;
      if ((method.equals("POST")) && 
        (!Boolean.getBoolean("http.strictPostRedirect")) && (stat != 307))
      {
















        requests = new MessageHeader();
        setRequests = false;
        setRequestMethod("GET");
        poster = null;
        if (!checkReuseConnection()) {
          connect();
        }
      } else {
        if (!checkReuseConnection()) {
          connect();
        }
        










        if (http != null) {
          requests.set(0, method + " " + http.getURLFile() + " " + "HTTP/1.1", null);
          
          int port = url.getPort();
          String host = url.getHost();
          if ((port != -1) && (port != url.getDefaultPort())) {
            host = host + ":" + String.valueOf(port);
          }
          requests.set("Host", host);
        }
      }
    }
    return true;
  }
  



  private byte[] cdata = new byte[''];
  protected static final String HTTP_CONNECT = "CONNECT";
  static final int defaultmaxRedirects = 20;
  private StreamingOutputStream strOutputStream;
  
  private void reset() throws IOException
  {
    http.reuse = true;
    
    reuseClient = http;
    InputStream is = http.getInputStream();
    if (!method.equals("HEAD"))
    {


      try
      {

        if (((is instanceof ChunkedInputStream)) || ((is instanceof MeteredStream))) {}
        
        while (is.read(cdata) > 0)
        {





          continue;int cl = 0;n = 0;
          try {
            cl = Integer.parseInt(responses
              .findValue("Content-Length"));
          } catch (Exception e) {
            LOG.debug("got Exception while parsing content-length : {}", e, e);
          }
          for (i = 0; (i < cl) && 
                ((n = is.read(cdata)) != -1);)
          {

            i += n; }
        }
      } catch (IOException e) {
        int n;
        int i;
        http.reuse = false;
        reuseClient = null;
        disconnectInternal();
        return;
      }
      try {
        if ((is instanceof MeteredStream)) {
          is.close();
        }
      } catch (IOException e) {
        LOG.debug("got IOException : {}", e.getMessage(), e);
      }
    }
    responseCode = -1;
    responses = new MessageHeader();
    connected = false;
  }
  


  private void disconnectInternal()
  {
    responseCode = -1;
    inputStream = null;
    if (pi != null) {
      pi.finishTracking();
      pi = null;
    }
    if (http != null) {
      http.closeServer();
      http = null;
      connected = false;
    }
  }
  




  public void disconnect()
  {
    responseCode = -1;
    if (pi != null) {
      pi.finishTracking();
      pi = null;
    }
    
    if (http != null)
    {






















      if (inputStream != null) {
        HttpClient hc = http;
        

        boolean ka = hc.isKeepingAlive();
        try
        {
          inputStream.close();
        } catch (IOException ioe) {
          LOG.debug("got IOException : {}", ioe.getMessage(), ioe);
        }
        





        if (ka) {
          hc.closeIdleConnection();
        }
        

      }
      else
      {
        http.setDoNotRetry(true);
        
        http.closeServer();
      }
      

      http = null;
      connected = false;
    }
    cachedInputStream = null;
    if (cachedHeaders != null) {
      cachedHeaders.reset();
    }
  }
  
  public boolean usingProxy()
  {
    if (http != null) {
      return http.getProxyHostUsed() != null;
    }
    return false;
  }
  




  public String getHeaderField(String name)
  {
    try
    {
      getInputStream();
    } catch (IOException e) {
      LOG.debug("got IOException : {}", e.getMessage(), e);
    }
    
    if (cachedHeaders != null) {
      return cachedHeaders.findValue(name);
    }
    
    return responses.findValue(name);
  }
  








  public Map<String, List<String>> getHeaderFields()
  {
    try
    {
      getInputStream();
    } catch (IOException e) {
      LOG.debug("got IOException : {}", e.getMessage(), e);
    }
    
    if (cachedHeaders != null) {
      return cachedHeaders.getHeaders();
    }
    
    return responses.getHeaders();
  }
  




  public String getHeaderField(int n)
  {
    try
    {
      getInputStream();
    } catch (IOException e) {
      LOG.debug("got IOException : {}", e.getMessage(), e);
    }
    
    if (cachedHeaders != null) {
      return cachedHeaders.getValue(n);
    }
    return responses.getValue(n);
  }
  




  public String getHeaderFieldKey(int n)
  {
    try
    {
      getInputStream();
    } catch (IOException e) {
      LOG.debug("got IOException : {}", e.getMessage(), e);
    }
    
    if (cachedHeaders != null) {
      return cachedHeaders.getKey(n);
    }
    
    return responses.getKey(n);
  }
  






  public void setRequestProperty(String key, String value)
  {
    if (connected) {
      throw new IllegalStateException("Already connected");
    }
    if (key == null) {
      throw new NullPointerException("key is null");
    }
    
    checkMessageHeader(key, value);
    requests.set(key, value);
  }
  









  public void addRequestProperty(String key, String value)
  {
    if (connected) {
      throw new IllegalStateException("Already connected");
    }
    if (key == null) {
      throw new NullPointerException("key is null");
    }
    
    checkMessageHeader(key, value);
    requests.add(key, value);
  }
  



  void setAuthenticationProperty(String key, String value)
  {
    checkMessageHeader(key, value);
    requests.set(key, value);
  }
  

  public String getRequestProperty(String key)
  {
    if (key != null) {
      for (int i = 0; i < EXCLUDE_HEADERS.length; i++) {
        if (key.equalsIgnoreCase(EXCLUDE_HEADERS[i])) {
          return null;
        }
      }
    }
    return requests.findValue(key);
  }
  


  private static final String RETRY_MSG1 = "cannot retry due to proxy authentication, in streaming mode";
  

  private static final String RETRY_MSG2 = "cannot retry due to server authentication, in streaming mode";
  

  private static final String RETRY_MSG3 = "cannot retry due to redirection, in streaming mode";
  
  public Map<String, List<String>> getRequestProperties()
  {
    if (connected) {
      throw new IllegalStateException("Already connected");
    }
    

    return requests.getHeaders(EXCLUDE_HEADERS);
  }
  
  public void setConnectTimeout(int timeout)
  {
    if (timeout < 0) {
      throw new IllegalArgumentException("timeouts can't be negative");
    }
    connectTimeout = timeout;
  }
  

  static final String HTTP_VERSION = "HTTP/1.1";
  
  static final String ACCEPT_STRING = "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2";
  
  protected HttpClient http;
  
  protected Handler handler;
  protected Proxy instProxy;
  private CookieHandler cookieHandler;
  private ResponseCache cacheHandler;
  public int getConnectTimeout()
  {
    return connectTimeout < 0 ? 0 : connectTimeout;
  }
  

  protected CacheResponse cachedResponse;
  
  private MessageHeader cachedHeaders;
  
  private InputStream cachedInputStream;
  
  private MessageHeader requests;
  String domain;
  DigestAuthentication.Parameters digestparams;
  Object authObj;
  boolean isUserServerAuth;
  boolean isUserProxyAuth;
  protected ProgressSource pi;
  private MessageHeader responses;
  protected NetLayer lowerNetLayer;
  private static final char LINE_FEED = '\n';
  public void setReadTimeout(int timeout)
  {
    if (timeout < 0) {
      throw new IllegalArgumentException("timeouts can't be negative");
    }
    readTimeout = timeout;
  }
  










  public int getReadTimeout()
  {
    return readTimeout < 0 ? 0 : readTimeout;
  }
  


  protected void finalize() {}
  

  String getMethod()
  {
    return method;
  }
  
  private MessageHeader mapToMessageHeader(Map<String, List<String>> map) {
    MessageHeader headers = new MessageHeader();
    if ((map == null) || (map.isEmpty())) {
      return headers;
    }
    for (Map.Entry<String, List<String>> entry : map.entrySet()) {
      key = (String)entry.getKey();
      List<String> values = (List)entry.getValue();
      for (String value : values) {
        if (key == null) {
          headers.prepend(key, value);
        } else
          headers.add(key, value);
      }
    }
    String key;
    return headers;
  }
  

  class HttpInputStream
    extends FilterInputStream
  {
    private CacheRequest cacheRequest;
    
    private OutputStream outputStream;
    private boolean marked = false;
    private int inCache = 0;
    private int markCount = 0;
    
    public HttpInputStream(InputStream is) {
      super();
      cacheRequest = null;
      outputStream = null;
    }
    
    public HttpInputStream(InputStream is, CacheRequest cacheRequest) {
      super();
      this.cacheRequest = cacheRequest;
      try {
        outputStream = cacheRequest.getBody();
      } catch (IOException ioex) {
        this.cacheRequest.abort();
        this.cacheRequest = null;
        outputStream = null;
      }
    }
    















    public synchronized void mark(int readlimit)
    {
      super.mark(readlimit);
      if (cacheRequest != null) {
        marked = true;
        markCount = 0;
      }
    }
    


















    public synchronized void reset()
      throws IOException
    {
      super.reset();
      if (cacheRequest != null) {
        marked = false;
        inCache += markCount;
      }
    }
    
    public int read() throws IOException
    {
      try {
        byte[] b = new byte[1];
        int ret = read(b);
        return ret == -1 ? ret : b[0] & 0xFF;
      } catch (IOException ioex) {
        if (cacheRequest != null) {
          cacheRequest.abort();
        }
        throw ioex;
      }
    }
    
    public int read(byte[] b) throws IOException
    {
      return read(b, 0, b.length);
    }
    
    public int read(byte[] b, int off, int len) throws IOException
    {
      try {
        int newLen = super.read(b, off, len);
        
        int nWrite;
        if (inCache > 0) { int nWrite;
          if (inCache >= newLen) {
            inCache -= newLen;
            nWrite = 0;
          } else {
            int nWrite = newLen - inCache;
            inCache = 0;
          }
        } else {
          nWrite = newLen;
        }
        if ((nWrite > 0) && (outputStream != null)) {
          outputStream.write(b, off + (newLen - nWrite), nWrite);
        }
        if (marked) {
          markCount += newLen;
        }
        return newLen;
      } catch (IOException ioex) {
        if (cacheRequest != null) {
          cacheRequest.abort();
        }
        throw ioex;
      }
    }
    
    public void close() throws IOException
    {
      try {
        if (outputStream != null) {
          if (read() != -1) {
            cacheRequest.abort();
          } else {
            outputStream.close();
          }
        }
        super.close();
      } catch (IOException ioex) {
        if (cacheRequest != null) {
          cacheRequest.abort();
        }
        throw ioex;
      } finally {
        http = null;
        HttpURLConnection.this.checkResponseCredentials(true);
      }
    }
  }
  

  class StreamingOutputStream
    extends FilterOutputStream
  {
    long expected;
    
    long written;
    
    boolean closed;
    boolean error;
    IOException errorExcp;
    
    StreamingOutputStream(OutputStream os, long expectedLength)
    {
      super();
      expected = expectedLength;
      written = 0L;
      closed = false;
      error = false;
    }
    
    public void write(int b) throws IOException
    {
      checkError();
      written += 1L;
      if ((expected != -1L) && (written > expected)) {
        throw new IOException("too many bytes written");
      }
      out.write(b);
    }
    
    public void write(byte[] b) throws IOException
    {
      write(b, 0, b.length);
    }
    
    public void write(byte[] b, int off, int len) throws IOException
    {
      checkError();
      written += len;
      if ((expected != -1L) && (written > expected)) {
        out.close();
        throw new IOException("too many bytes written");
      }
      out.write(b, off, len);
    }
    
    void checkError() throws IOException {
      if (closed) {
        throw new IOException("Stream is closed");
      }
      if (error) {
        throw errorExcp;
      }
      if (((PrintStream)out).checkError()) {
        throw new IOException("Error writing request body to server");
      }
    }
    



    boolean writtenOK()
    {
      return (closed) && (!error);
    }
    
    public void close() throws IOException
    {
      if (closed) {
        return;
      }
      closed = true;
      if (expected != -1L)
      {
        if (written != expected) {
          error = true;
          errorExcp = new IOException("insufficient data written");
          out.close();
          throw errorExcp;
        }
        super.flush();
      }
      else {
        super.close();
        
        OutputStream o = http.getOutputStream();
        o.write(13);
        o.write(10);
        o.flush();
      }
    }
  }
  
  static class ErrorStream extends InputStream {
    ByteBuffer buffer;
    InputStream is;
    
    private ErrorStream(ByteBuffer buf) {
      buffer = buf;
      is = null;
    }
    
    private ErrorStream(ByteBuffer buf, InputStream is) {
      buffer = buf;
      this.is = is;
    }
    



    public static InputStream getErrorStream(InputStream is, long cl, HttpClient http)
    {
      if (cl == 0L) {
        return null;
      }
      

      try
      {
        int oldTimeout = http.setTimeout(HttpURLConnection.timeout4ESBuffer / 5);
        
        long expected = 0L;
        boolean isChunked = false;
        
        if (cl < 0L) {
          expected = HttpURLConnection.bufSize4ES;
          isChunked = true;
        } else {
          expected = cl;
        }
        if (expected <= HttpURLConnection.bufSize4ES) {
          int exp = (int)expected;
          byte[] buffer = new byte[exp];
          int count = 0;int time = 0;int len = 0;
          do {
            try {
              len = is.read(buffer, count, buffer.length - count);
              if (len < 0) {
                if (isChunked) {
                  break;
                }
                



                throw new IOException("the server closes before sending " + cl + " bytes of data");
              }
              

              count += len;
            } catch (SocketTimeoutException ex) {
              time += HttpURLConnection.timeout4ESBuffer / 5;
            }
            
          } while ((count < exp) && (time < HttpURLConnection.timeout4ESBuffer));
          

          http.setTimeout(oldTimeout);
          


          if (count == 0)
          {


            return null; }
          if (((count == expected) && (!isChunked)) || ((isChunked) && (len < 0)))
          {


            is.close();
            
            return new ErrorStream(ByteBuffer.wrap(buffer, 0, count));
          }
          

          return new ErrorStream(ByteBuffer.wrap(buffer, 0, count), is);
        }
        
        return null;
      }
      catch (IOException ioex) {}
      return null;
    }
    
    public int available()
      throws IOException
    {
      if (is == null) {
        return buffer.remaining();
      }
      return buffer.remaining() + is.available();
    }
    
    public int read()
      throws IOException
    {
      byte[] b = new byte[1];
      int ret = read(b);
      return ret == -1 ? ret : b[0] & 0xFF;
    }
    
    public int read(byte[] b) throws IOException
    {
      return read(b, 0, b.length);
    }
    
    public int read(byte[] b, int off, int len) throws IOException
    {
      int rem = buffer.remaining();
      if (rem > 0) {
        int ret = rem < len ? rem : len;
        buffer.get(b, off, ret);
        return ret;
      }
      if (is == null) {
        return -1;
      }
      return is.read(b, off, len);
    }
    

    public void close()
      throws IOException
    {
      buffer = null;
      if (is != null) {
        is.close();
      }
    }
  }
}
