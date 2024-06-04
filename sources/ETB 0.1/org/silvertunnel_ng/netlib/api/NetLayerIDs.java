package org.silvertunnel_ng.netlib.api;












































public enum NetLayerIDs
{
  NOP("nop"), 
  

  TCPIP("tcpip"), 
  

  TLS_OVER_TCPIP("tls_over_tcpip"), 
  

  SOCKS_OVER_TCPIP("socks_over_tcpip"), 
  

  TOR_OVER_TLS_OVER_TCPIP("tor_over_tls_over_tcpip"), 
  

  TOR(TOR_OVER_TLS_OVER_TCPIP.getValue()), 
  

  SOCKS_OVER_TOR_OVER_TLS_OVER_TCPIP("socks_over_tor_over_tls_over_tcpip"), 
  

  SOCKS_OVER_TOR(SOCKS_OVER_TOR_OVER_TLS_OVER_TCPIP.getValue()), 
  

  MOCK("mock"), 
  

  MODIFY_OVER_TCPIP("modify_over_tcpip"), 
  

  MODIFY_OVER_ECHO("modify_over_echo"), 
  

  UNKNOWN("unknown");
  

  private String internalValue;
  
  private NetLayerIDs(String value)
  {
    internalValue = value;
  }
  


  public String getValue()
  {
    return internalValue;
  }
  




  public static NetLayerIDs getByValue(String value)
  {
    for (NetLayerIDs netLayer : )
    {
      if (value.equals(netLayer.getValue()))
      {
        return netLayer;
      }
    }
    return UNKNOWN;
  }
}
