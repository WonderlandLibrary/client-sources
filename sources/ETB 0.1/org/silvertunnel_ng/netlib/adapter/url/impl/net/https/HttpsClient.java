package org.silvertunnel_ng.netlib.adapter.url.impl.net.https;

import java.io.IOException;
import java.net.URL;
import java.security.Principal;
import java.security.cert.Certificate;
import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import org.silvertunnel_ng.netlib.adapter.url.impl.net.http.HttpClient;
import org.silvertunnel_ng.netlib.adapter.url.impl.net.http.KeepAliveCache;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.layer.tls.TLSNetSocket;



















































































final class HttpsClient
  extends HttpClient
  implements HandshakeCompletedListener
{
  private HostnameVerifier hv;
  private SSLSession session;
  protected static final int DEFAULT_HTTPS_PORT = 443;
  
  protected int getDefaultPort()
  {
    return 443;
  }
  



















































  HttpsClient(NetLayer lowerNetLayer, URL url, int connectTimeout)
    throws IOException
  {
    super(lowerNetLayer, url, connectTimeout);
    
    if ((serverSocket instanceof TLSNetSocket))
    {
      session = ((TLSNetSocket)serverSocket).getSSLSession();
    }
  }
  



  static HttpClient New(NetLayer lowerNetLayer, URL url, HostnameVerifier hv)
    throws IOException
  {
    return New(lowerNetLayer, url, hv, true);
  }
  
  static HttpClient New(NetLayer lowerNetLayer, URL url, HostnameVerifier hv, boolean useCache)
    throws IOException
  {
    return New(lowerNetLayer, url, hv, useCache, -1);
  }
  
  static HttpClient New(NetLayer lowerNetLayer, URL url, HostnameVerifier hv, boolean useCache, int connectTimeout)
    throws IOException
  {
    HttpsClient ret = null;
    if (useCache)
    {

      ret = (HttpsClient)KAC.get(url, lowerNetLayer);
      if (ret != null)
      {
        cachedHttpClient = true;
      }
    }
    if (ret == null)
    {
      ret = new HttpsClient(lowerNetLayer, url, connectTimeout);
    }
    else
    {
      SecurityManager security = System.getSecurityManager();
      if (security != null)
      {
        security.checkConnect(url.getHost(), url.getPort());
      }
      url = url;
    }
    ret.setHostnameVerifier(hv);
    
    return ret;
  }
  

  void setHostnameVerifier(HostnameVerifier hv)
  {
    this.hv = hv;
  }
  


























































































  public void closeIdleConnection()
  {
    HttpClient http = KAC.get(url, lowerNetLayer);
    if (http != null)
    {
      http.closeServer();
    }
  }
  



  String getCipherSuite()
  {
    return session.getCipherSuite();
  }
  




  public Certificate[] getLocalCertificates()
  {
    return session.getLocalCertificates();
  }
  




  Certificate[] getServerCertificates()
    throws SSLPeerUnverifiedException
  {
    return session.getPeerCertificates();
  }
  




  javax.security.cert.X509Certificate[] getServerCertificateChain()
    throws SSLPeerUnverifiedException
  {
    return session.getPeerCertificateChain();
  }
  


  Principal getPeerPrincipal()
    throws SSLPeerUnverifiedException
  {
    Principal principal;
    
    try
    {
      principal = session.getPeerPrincipal();
    }
    catch (AbstractMethodError e)
    {
      Principal principal;
      

      Certificate[] certs = session.getPeerCertificates();
      principal = ((java.security.cert.X509Certificate)certs[0]).getSubjectX500Principal();
    }
    return principal;
  }
  


  Principal getLocalPrincipal()
  {
    Principal principal;
    

    try
    {
      principal = session.getLocalPrincipal();
    }
    catch (AbstractMethodError e) {
      Principal principal;
      principal = null;
      


      Certificate[] certs = session.getLocalCertificates();
      if (certs != null)
      {

        principal = ((java.security.cert.X509Certificate)certs[0]).getSubjectX500Principal();
      }
    }
    return principal;
  }
  








  public void handshakeCompleted(HandshakeCompletedEvent event)
  {
    session = event.getSession();
  }
}
