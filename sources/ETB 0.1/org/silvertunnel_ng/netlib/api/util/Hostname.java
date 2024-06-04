package org.silvertunnel_ng.netlib.api.util;

import java.io.UnsupportedEncodingException;
import org.silvertunnel_ng.netlib.api.NetAddress;





















public class Hostname
  implements NetAddress
{
  private String hostname;
  
  public Hostname(String hostname)
  {
    this.hostname = hostname;
  }
  
  public Hostname(byte[] array) throws UnsupportedEncodingException {
    hostname = new String(array, "UTF-8");
  }
  
  public String getHostname() {
    return hostname;
  }
  
  public void setHostname(String hostname) {
    this.hostname = hostname;
  }
}
