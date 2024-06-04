package org.silvertunnel_ng.netlib.adapter.url.impl.net.https;

import java.io.IOException;
import java.net.Proxy;
import java.net.SecureCacheResponse;
import java.net.URL;
import java.security.Principal;
import java.security.cert.Certificate;
import java.util.List;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.security.cert.X509Certificate;
import org.silvertunnel_ng.netlib.adapter.url.impl.net.http.Handler;
import org.silvertunnel_ng.netlib.adapter.url.impl.net.http.HttpClient;
import org.silvertunnel_ng.netlib.adapter.url.impl.net.http.HttpURLConnection;
import org.silvertunnel_ng.netlib.api.NetLayer;
































public class HttpsURLConnection
  extends HttpURLConnection
{
  protected HttpsURLConnection(NetLayer lowerNetLayer, URL url, Handler handler)
    throws IOException
  {
    this(lowerNetLayer, url, null, handler);
  }
  
  protected HttpsURLConnection(NetLayer lowerNetLayer, URL url, Proxy p, Handler handler)
    throws IOException
  {
    super(lowerNetLayer, url, p, handler);
  }
  
















  public void setNewClient(URL url)
    throws IOException
  {
    setNewClient(url, false);
  }
  












  public void setNewClient(URL url, boolean useCache)
    throws IOException
  {
    http = HttpsClient.New(lowerNetLayer, url, null, useCache);
  }
  



















  public void setProxiedClient(URL url, String proxyHost, int proxyPort)
    throws IOException
  {
    setProxiedClient(url, proxyHost, proxyPort, false);
  }
  



  public boolean isConnected()
  {
    return connected;
  }
  



  public void setConnected(boolean conn)
  {
    connected = conn;
  }
  




  public void connect()
    throws IOException
  {
    if (connected)
    {
      return;
    }
    plainConnect();
    if (cachedResponse != null) {}
  }
  





  protected HttpClient getNewHttpClient(URL url, Proxy p, int connectTimeout)
    throws IOException
  {
    return HttpsClient.New(lowerNetLayer, url, (HostnameVerifier)null, true, connectTimeout);
  }
  


  protected HttpClient getNewHttpClient(URL url, Proxy p, int connectTimeout, boolean useCache)
    throws IOException
  {
    return HttpsClient.New(lowerNetLayer, url, (HostnameVerifier)null, useCache, connectTimeout);
  }
  




  public String getCipherSuite()
  {
    if (cachedResponse != null)
    {
      return ((SecureCacheResponse)cachedResponse).getCipherSuite();
    }
    if (http == null)
    {
      throw new IllegalStateException("connection not yet open");
    }
    

    return ((HttpsClient)http).getCipherSuite();
  }
  





  public Certificate[] getLocalCertificates()
  {
    if (cachedResponse != null)
    {

      List<Certificate> l = ((SecureCacheResponse)cachedResponse).getLocalCertificateChain();
      if (l == null)
      {
        return null;
      }
      

      return (Certificate[])l.toArray();
    }
    
    if (http == null)
    {
      throw new IllegalStateException("connection not yet open");
    }
    

    return ((HttpsClient)http).getLocalCertificates();
  }
  





  public Certificate[] getServerCertificates()
    throws SSLPeerUnverifiedException
  {
    if (cachedResponse != null)
    {

      List<Certificate> l = ((SecureCacheResponse)cachedResponse).getServerCertificateChain();
      if (l == null)
      {
        return null;
      }
      

      return (Certificate[])l.toArray();
    }
    

    if (http == null)
    {
      throw new IllegalStateException("connection not yet open");
    }
    

    return ((HttpsClient)http).getServerCertificates();
  }
  





  public X509Certificate[] getServerCertificateChain()
    throws SSLPeerUnverifiedException
  {
    if (cachedResponse != null)
    {
      throw new UnsupportedOperationException("this method is not supported when using cache");
    }
    
    if (http == null)
    {
      throw new IllegalStateException("connection not yet open");
    }
    

    return ((HttpsClient)http).getServerCertificateChain();
  }
  




  Principal getPeerPrincipal()
    throws SSLPeerUnverifiedException
  {
    if (cachedResponse != null)
    {
      return ((SecureCacheResponse)cachedResponse).getPeerPrincipal();
    }
    
    if (http == null)
    {
      throw new IllegalStateException("connection not yet open");
    }
    

    return ((HttpsClient)http).getPeerPrincipal();
  }
  





  Principal getLocalPrincipal()
  {
    if (cachedResponse != null)
    {
      return ((SecureCacheResponse)cachedResponse).getLocalPrincipal();
    }
    
    if (http == null)
    {
      throw new IllegalStateException("connection not yet open");
    }
    

    return ((HttpsClient)http).getLocalPrincipal();
  }
}
