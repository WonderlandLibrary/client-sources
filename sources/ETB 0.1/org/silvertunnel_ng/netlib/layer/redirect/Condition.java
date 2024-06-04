package org.silvertunnel_ng.netlib.layer.redirect;

import java.util.regex.Pattern;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.util.IpNetAddress;
import org.silvertunnel_ng.netlib.api.util.TcpipNetAddress;
































public class Condition
{
  private final Pattern pattern;
  private final NetLayer netLayer;
  
  public Condition(IpNetAddress ipNetAddress, NetLayer netLayer)
  {
    this(ipNetAddress.getIpaddressAsString(), netLayer);
  }
  







  public Condition(String hostnameOrIpAddress, NetLayer netLayer)
  {
    this(Pattern.compile("^" + Pattern.quote(hostnameOrIpAddress) + ":(\\d{1,5})$"), netLayer);
  }
  








  public Condition(TcpipNetAddress tcpipNetAddress, NetLayer netLayer)
  {
    this(Pattern.compile("^((" + 
      Pattern.quote(tcpipNetAddress.getIpaddressAndPort()) + ")|" + " (" + 
      Pattern.quote(tcpipNetAddress.getHostnameAndPort()) + "))" + "$"), netLayer);
  }
  





  public Condition(Pattern pattern, NetLayer netLayer)
  {
    this.pattern = pattern;
    this.netLayer = netLayer;
  }
  

  public String toString()
  {
    return pattern + "-" + netLayer;
  }
  




  public Pattern getPattern()
  {
    return pattern;
  }
  
  public NetLayer getNetLayer()
  {
    return netLayer;
  }
}
