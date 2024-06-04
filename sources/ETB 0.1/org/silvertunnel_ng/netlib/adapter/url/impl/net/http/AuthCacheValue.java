package org.silvertunnel_ng.netlib.adapter.url.impl.net.http;

import java.io.Serializable;
import java.net.PasswordAuthentication;




































public abstract class AuthCacheValue
  implements Serializable
{
  private static final long serialVersionUID = -8021676947097690390L;
  
  public static enum Type
  {
    Proxy,  Server;
    

    private Type() {}
  }
  
  protected static AuthCache cache = new AuthCacheImpl();
  
  public static void setAuthCache(AuthCache map)
  {
    cache = map;
  }
  
  AuthCacheValue() {}
  
  abstract Type getAuthType();
  
  abstract String getHost();
  
  abstract int getPort();
  
  abstract String getRealm();
  
  abstract String getPath();
  
  abstract String getProtocolScheme();
  
  abstract PasswordAuthentication credentials();
}
