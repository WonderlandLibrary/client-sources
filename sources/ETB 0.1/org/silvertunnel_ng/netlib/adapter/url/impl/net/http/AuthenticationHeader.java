package org.silvertunnel_ng.netlib.adapter.url.impl.net.http;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Iterator;
























































public class AuthenticationHeader
{
  MessageHeader rsp;
  HeaderParser preferred;
  String preferredRaw;
  String host = null;
  
  static String authPref = null;
  String hdrname;
  HashMap<String, SchemeMapValue> schemes;
  
  public String toString() {
    return "AuthenticationHeader: prefer " + preferredRaw;
  }
  

  static
  {
    authPref = (String)AccessController.doPrivileged(new PrivilegedAction()
    {

      public Object run()
      {
        return System.getProperty("http.auth.preference");
      }
    });
    






    if (authPref != null)
    {
      authPref = authPref.toLowerCase();
      if ((authPref.equals("spnego")) || (authPref.equals("kerberos")))
      {
        authPref = "negotiate";
      }
    }
  }
  






  public AuthenticationHeader(String hdrname, MessageHeader response)
  {
    rsp = response;
    this.hdrname = hdrname;
    schemes = new HashMap();
    parse();
  }
  




  public AuthenticationHeader(String hdrname, MessageHeader response, String host)
  {
    this.host = host;
    rsp = response;
    this.hdrname = hdrname;
    schemes = new HashMap();
    parse();
  }
  
  static class SchemeMapValue {
    String raw;
    HeaderParser parser;
    
    SchemeMapValue(HeaderParser headerParser, String rawValue) {
      raw = rawValue;
      parser = headerParser;
    }
  }
  










  private void parse()
  {
    Iterator<String> iter = rsp.multiValueIterator(hdrname);
    while (iter.hasNext())
    {
      String raw = (String)iter.next();
      HeaderParser headerParser = new HeaderParser(raw);
      Iterator<Object> keys = headerParser.keys();
      
      int i = 0; for (int lastSchemeIndex = -1; keys.hasNext(); i++)
      {
        keys.next();
        if (headerParser.findValue(i) == null)
        {
          if (lastSchemeIndex != -1)
          {
            HeaderParser hpn = headerParser.subsequence(lastSchemeIndex, i);
            
            String scheme = hpn.findKey(0);
            schemes.put(scheme, new SchemeMapValue(hpn, raw));
          }
          lastSchemeIndex = i;
        }
      }
      if (i > lastSchemeIndex)
      {
        HeaderParser hpn = headerParser.subsequence(lastSchemeIndex, i);
        String scheme = hpn.findKey(0);
        schemes.put(scheme, new SchemeMapValue(hpn, raw));
      }
    }
    




    SchemeMapValue schemeMapValue = null;
    if ((authPref == null) || ((schemeMapValue = (SchemeMapValue)schemes.get(authPref)) == null))
    {

      if (schemeMapValue == null)
      {
        SchemeMapValue tmp = (SchemeMapValue)schemes.get("negotiate");
        if (tmp != null)
        {
          if ((host == null) || 
            (!NegotiateAuthentication.isSupported(host, "Negotiate")))
          {

            tmp = null;
          }
          schemeMapValue = tmp;
        }
      }
      
      if (schemeMapValue == null)
      {
        SchemeMapValue tmp = (SchemeMapValue)schemes.get("kerberos");
        if (tmp != null)
        {












          if ((host == null) || 
            (!NegotiateAuthentication.isSupported(host, "Kerberos")))
          {

            tmp = null;
          }
          schemeMapValue = tmp;
        }
      }
      
      if (schemeMapValue == null)
      {
        if ((schemeMapValue = (SchemeMapValue)schemes.get("digest")) == null)
        {
          if ((schemeMapValue = (SchemeMapValue)schemes.get("ntlm")) == null)
          {
            schemeMapValue = (SchemeMapValue)schemes.get("basic");
          }
        }
      }
    }
    if (schemeMapValue != null)
    {
      preferred = parser;
      preferredRaw = raw;
    }
  }
  






  public HeaderParser headerParser()
  {
    return preferred;
  }
  



  public String scheme()
  {
    if (preferred != null)
    {
      return preferred.findKey(0);
    }
    

    return null;
  }
  



  public String raw()
  {
    return preferredRaw;
  }
  



  public boolean isPresent()
  {
    return preferred != null;
  }
}
