package org.silvertunnel_ng.netlib.adapter.url.impl.net.http;

import java.io.UnsupportedEncodingException;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import org.silvertunnel_ng.netlib.util.DatatypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

































class BasicAuthentication
  extends AuthenticationInfo
{
  private static final Logger LOG = LoggerFactory.getLogger(BasicAuthentication.class);
  


  private static final long serialVersionUID = 100L;
  


  static final char BASIC_AUTH = 'B';
  

  String auth;
  


  public BasicAuthentication(boolean isProxy, String host, int port, String realm, PasswordAuthentication pw)
  {
    super(isProxy ? 'p' : 's', 'B', host, port, realm);
    
    String plain = pw.getUserName() + ":";
    byte[] nameBytes = null;
    try
    {
      nameBytes = plain.getBytes("ISO-8859-1");
    }
    catch (UnsupportedEncodingException uee)
    {
      if (!$assertionsDisabled) { throw new AssertionError();
      }
    }
    
    char[] passwd = pw.getPassword();
    byte[] passwdBytes = new byte[passwd.length];
    for (int i = 0; i < passwd.length; i++)
    {
      passwdBytes[i] = ((byte)passwd[i]);
    }
    

    byte[] concat = new byte[nameBytes.length + passwdBytes.length];
    System.arraycopy(nameBytes, 0, concat, 0, nameBytes.length);
    System.arraycopy(passwdBytes, 0, concat, nameBytes.length, passwdBytes.length);
    
    auth = ("Basic " + DatatypeConverter.printBase64Binary(concat));
    this.pw = pw;
  }
  




  public BasicAuthentication(boolean isProxy, String host, int port, String realm, String auth)
  {
    super(isProxy ? 'p' : 's', 'B', host, port, realm);
    
    this.auth = ("Basic " + auth);
  }
  




  public BasicAuthentication(boolean isProxy, URL url, String realm, PasswordAuthentication pw)
  {
    super(isProxy ? 'p' : 's', 'B', url, realm);
    
    String plain = pw.getUserName() + ":";
    byte[] nameBytes = null;
    try
    {
      nameBytes = plain.getBytes("ISO-8859-1");
    }
    catch (UnsupportedEncodingException uee)
    {
      if (!$assertionsDisabled) { throw new AssertionError();
      }
    }
    
    char[] passwd = pw.getPassword();
    byte[] passwdBytes = new byte[passwd.length];
    for (int i = 0; i < passwd.length; i++)
    {
      passwdBytes[i] = ((byte)passwd[i]);
    }
    

    byte[] concat = new byte[nameBytes.length + passwdBytes.length];
    System.arraycopy(nameBytes, 0, concat, 0, nameBytes.length);
    System.arraycopy(passwdBytes, 0, concat, nameBytes.length, passwdBytes.length);
    
    auth = ("Basic " + DatatypeConverter.printBase64Binary(concat));
    this.pw = pw;
  }
  




  public BasicAuthentication(boolean isProxy, URL url, String realm, String auth)
  {
    super(isProxy ? 'p' : 's', 'B', url, realm);
    
    this.auth = ("Basic " + auth);
  }
  




  boolean supportsPreemptiveAuthorization()
  {
    return true;
  }
  




  String getHeaderName()
  {
    if (type == 's')
    {
      return "Authorization";
    }
    

    return "Proxy-authorization";
  }
  















  boolean setHeaders(HttpURLConnection conn, HeaderParser p, String raw)
  {
    conn.setAuthenticationProperty(getHeaderName(), 
      getHeaderValue(null, null));
    return true;
  }
  








  String getHeaderValue(URL url, String method)
  {
    return auth;
  }
  






  boolean isAuthorizationStale(String header)
  {
    return false;
  }
  







  void checkResponse(String header, String method, URL url) {}
  






  static String getRootPath(String npath, String opath)
  {
    int index = 0;
    


    try
    {
      npath = new URI(npath).normalize().getPath();
      opath = new URI(opath).normalize().getPath();

    }
    catch (URISyntaxException e)
    {
      LOG.debug("got URISyntaxException : ", e.getMessage(), e);
    }
    
    while (index < opath.length())
    {
      int toindex = opath.indexOf('/', index + 1);
      if ((toindex != -1) && (opath.regionMatches(0, npath, 0, toindex + 1)))
      {
        index = toindex;
      }
      else
      {
        return opath.substring(0, index + 1);
      }
    }
    
    return npath;
  }
}
