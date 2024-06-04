package org.silvertunnel_ng.netlib.adapter.url.impl.net.http;

import java.io.IOException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.HashMap;
import org.silvertunnel_ng.netlib.util.DatatypeConverter;


































class NegotiateAuthentication
  extends AuthenticationInfo
{
  private static final long serialVersionUID = 100L;
  private String scheme = null;
  


  static final char NEGOTIATE_AUTH = 'S';
  

  static final char KERBEROS_AUTH = 'K';
  

  static HashMap<String, Boolean> supported = null;
  static HashMap<String, Negotiator> cache = null;
  

  private Negotiator negotiator = null;
  






  public NegotiateAuthentication(boolean isProxy, URL url, PasswordAuthentication pw, String scheme)
  {
    super(isProxy ? 'p' : 's', 'S', url, "");
    
    this.scheme = scheme;
  }
  




  public NegotiateAuthentication(boolean isProxy, String host, int port, PasswordAuthentication pw, String scheme)
  {
    super(isProxy ? 'p' : 's', 'S', host, port, "");
    
    this.scheme = scheme;
  }
  




  boolean supportsPreemptiveAuthorization()
  {
    return false;
  }
  
















  public static synchronized boolean isSupported(String hostname, String scheme)
  {
    if (supported == null)
    {
      supported = new HashMap();
      cache = new HashMap();
    }
    
    hostname = hostname.toLowerCase();
    if (supported.containsKey(hostname))
    {
      return ((Boolean)supported.get(hostname)).booleanValue();
    }
    
    try
    {
      Negotiator neg = Negotiator.getSupported(hostname, scheme);
      supported.put(hostname, Boolean.valueOf(true));
      

      cache.put(hostname, neg);
      return true;
    }
    catch (Exception e)
    {
      supported.put(hostname, Boolean.valueOf(false)); }
    return false;
  }
  





  String getHeaderName()
  {
    if (type == 's')
    {
      return "Authorization";
    }
    

    return "Proxy-Authorization";
  }
  





  String getHeaderValue(URL url, String method)
  {
    throw new RuntimeException("getHeaderValue not supported");
  }
  








  boolean isAuthorizationStale(String header)
  {
    return false;
  }
  

















  synchronized boolean setHeaders(HttpURLConnection conn, HeaderParser p, String raw)
  {
    try
    {
      byte[] incoming = null;
      String[] parts = raw.split("\\s+");
      if (parts.length > 1)
      {
        incoming = DatatypeConverter.parseBase64Binary(parts[1]);
      }
      

      byte[] token = incoming == null ? firstToken() : nextToken(incoming);
      String encodedToken = DatatypeConverter.printBase64Binary(token);
      encodedToken = encodedToken != null ? encodedToken : "INVALID_TOKEN";
      

      String response = scheme + " " + encodedToken;
      
      conn.setAuthenticationProperty(getHeaderName(), response);
      return true;
    }
    catch (IOException e) {}
    
    return false;
  }
  








  private byte[] firstToken()
    throws IOException
  {
    negotiator = null;
    if (cache != null)
    {
      synchronized (cache)
      {
        negotiator = ((Negotiator)cache.get(getHost()));
        if (negotiator != null)
        {
          cache.remove(getHost());
        }
      }
    }
    if (negotiator == null)
    {
      try
      {
        negotiator = Negotiator.getSupported(getHost(), scheme);
      }
      catch (Exception e)
      {
        IOException ioe = new IOException("Cannot initialize Negotiator");
        
        ioe.initCause(e);
        throw ioe;
      }
    }
    
    return negotiator.firstToken();
  }
  









  private byte[] nextToken(byte[] token)
    throws IOException
  {
    return negotiator.nextToken(token);
  }
  
  public void checkResponse(String header, String method, URL url)
    throws IOException
  {}
}
