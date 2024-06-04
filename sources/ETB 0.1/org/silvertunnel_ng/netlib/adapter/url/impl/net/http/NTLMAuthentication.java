package org.silvertunnel_ng.netlib.adapter.url.impl.net.http;

import java.net.URL;























final class NTLMAuthentication
  extends BasicAuthentication
{
  private static final long serialVersionUID = 1L;
  
  private NTLMAuthentication()
  {
    super(false, (URL)null, (String)null, (String)null);
  }
  
  static boolean supportsTransparentAuth()
  {
    return false;
  }
}
