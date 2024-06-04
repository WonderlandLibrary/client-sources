package org.silvertunnel_ng.netlib.adapter.url.impl.net.http;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;








































abstract class AuthenticationInfo
  extends AuthCacheValue
  implements Cloneable
{
  private static final Logger LOG = LoggerFactory.getLogger(AuthCacheValue.class);
  



  private static final long serialVersionUID = 1L;
  



  protected static final char SERVER_AUTHENTICATION = 's';
  


  protected static final char PROXY_AUTHENTICATION = 'p';
  


  static boolean serializeAuth = "true".equalsIgnoreCase(
    System.getProperty("http.auth.serializeRequests"));
  


  protected transient PasswordAuthentication pw;
  


  public PasswordAuthentication credentials()
  {
    return pw;
  }
  

  public AuthCacheValue.Type getAuthType()
  {
    return type == 's' ? AuthCacheValue.Type.Server : AuthCacheValue.Type.Proxy;
  }
  


  public String getHost()
  {
    return host;
  }
  

  public int getPort()
  {
    return port;
  }
  

  public String getRealm()
  {
    return realm;
  }
  

  public String getPath()
  {
    return path;
  }
  

  public String getProtocolScheme()
  {
    return protocol;
  }
  






  private static HashMap<String, Thread> requests = new HashMap();
  
  char type;
  char authType;
  String protocol;
  String host;
  
  private static boolean requestIsInProgress(String key)
  {
    if (!serializeAuth)
    {
      return false;
    }
    synchronized (requests)
    {

      Thread c = Thread.currentThread();
      Thread t; if ((t = (Thread)requests.get(key)) == null)
      {
        requests.put(key, c);
        return false;
      }
      if (t == c)
      {
        return false;
      }
      while (requests.containsKey(key))
      {
        try
        {
          requests.wait();
        }
        catch (InterruptedException e)
        {
          LOG.debug("got IterruptedException : ", e.getMessage(), e);
        }
      }
    }
    
    return true;
  }
  




  private static void requestCompleted(String key)
  {
    synchronized (requests)
    {
      boolean waspresent = requests.remove(key) != null;
      assert (waspresent);
      requests.notifyAll();
    }
  }
  






  int port;
  





  String realm;
  





  String path;
  





  String s1;
  




  String s2;
  




  AuthenticationInfo(char type, char authType, String host, int port, String realm)
  {
    this.type = type;
    this.authType = authType;
    protocol = "";
    this.host = host.toLowerCase();
    this.port = port;
    this.realm = realm;
    path = null;
  }
  

  public Object clone()
  {
    try
    {
      return super.clone();
    }
    catch (CloneNotSupportedException e) {}
    

    return null;
  }
  





  AuthenticationInfo(char type, char authType, URL url, String realm)
  {
    this.type = type;
    this.authType = authType;
    protocol = url.getProtocol().toLowerCase();
    host = url.getHost().toLowerCase();
    port = url.getPort();
    if (port == -1)
    {
      port = url.getDefaultPort();
    }
    this.realm = realm;
    
    String urlPath = url.getPath();
    if (urlPath.length() == 0)
    {
      path = urlPath;
    }
    else
    {
      path = reducePath(urlPath);
    }
  }
  






  static String reducePath(String urlPath)
  {
    int sepIndex = urlPath.lastIndexOf('/');
    int targetSuffixIndex = urlPath.lastIndexOf('.');
    if (sepIndex != -1)
    {
      if (sepIndex < targetSuffixIndex)
      {
        return urlPath.substring(0, sepIndex + 1);
      }
      

      return urlPath;
    }
    


    return urlPath;
  }
  





  static AuthenticationInfo getServerAuth(URL url)
  {
    int port = url.getPort();
    if (port == -1)
    {
      port = url.getDefaultPort();
    }
    

    String key = "s:" + url.getProtocol().toLowerCase() + ":" + url.getHost().toLowerCase() + ":" + port;
    return getAuth(key, url);
  }
  






  static AuthenticationInfo getServerAuth(URL url, String realm, char atype)
  {
    int port = url.getPort();
    if (port == -1)
    {
      port = url.getDefaultPort();
    }
    

    String key = "s:" + atype + ":" + url.getProtocol().toLowerCase() + ":" + url.getHost().toLowerCase() + ":" + port + ":" + realm;
    AuthenticationInfo cached = getAuth(key, null);
    if ((cached == null) && (requestIsInProgress(key)))
    {

      cached = getAuth(key, null);
    }
    return cached;
  }
  




  static AuthenticationInfo getAuth(String key, URL url)
  {
    if (url == null)
    {
      return (AuthenticationInfo)cache.get(key, null);
    }
    

    return (AuthenticationInfo)cache.get(key, url.getPath());
  }
  






  static AuthenticationInfo getProxyAuth(String host, int port)
  {
    String key = "p::" + host.toLowerCase() + ":" + port;
    
    AuthenticationInfo result = (AuthenticationInfo)cache.get(key, null);
    
    return result;
  }
  







  static AuthenticationInfo getProxyAuth(String host, int port, String realm, char atype)
  {
    String key = "p:" + atype + "::" + host.toLowerCase() + ":" + port + ":" + realm;
    AuthenticationInfo cached = (AuthenticationInfo)cache.get(key, null);
    if ((cached == null) && (requestIsInProgress(key)))
    {

      cached = (AuthenticationInfo)cache.get(key, null);
    }
    return cached;
  }
  



  void addToCache()
  {
    cache.put(cacheKey(true), this);
    if (supportsPreemptiveAuthorization())
    {
      cache.put(cacheKey(false), this);
    }
    endAuthRequest();
  }
  
  void endAuthRequest()
  {
    if (!serializeAuth)
    {
      return;
    }
    synchronized (requests)
    {
      requestCompleted(cacheKey(true));
    }
  }
  



  void removeFromCache()
  {
    cache.remove(cacheKey(true), this);
    if (supportsPreemptiveAuthorization())
    {
      cache.remove(cacheKey(false), this);
    }
  }
  








  abstract boolean supportsPreemptiveAuthorization();
  








  abstract String getHeaderName();
  








  abstract String getHeaderValue(URL paramURL, String paramString);
  







  abstract boolean setHeaders(HttpURLConnection paramHttpURLConnection, HeaderParser paramHeaderParser, String paramString);
  







  abstract boolean isAuthorizationStale(String paramString);
  







  abstract void checkResponse(String paramString1, String paramString2, URL paramURL)
    throws IOException;
  







  String cacheKey(boolean includeRealm)
  {
    if (includeRealm)
    {
      return type + ":" + authType + ":" + protocol + ":" + host + ":" + port + ":" + realm;
    }
    


    return type + ":" + protocol + ":" + host + ":" + port;
  }
  



  private void readObject(ObjectInputStream s)
    throws IOException, ClassNotFoundException
  {
    s.defaultReadObject();
    pw = new PasswordAuthentication(s1, s2.toCharArray());
    s1 = null;
    s2 = null;
  }
  
  private synchronized void writeObject(ObjectOutputStream s)
    throws IOException
  {
    s1 = pw.getUserName();
    s2 = new String(pw.getPassword());
    s.defaultWriteObject();
  }
}
