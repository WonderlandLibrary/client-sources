package org.silvertunnel_ng.netlib.adapter.nameservice;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import org.silvertunnel_ng.netlib.api.NetAddress;
import org.silvertunnel_ng.netlib.api.NetAddressNameService;
import org.silvertunnel_ng.netlib.api.util.IpNetAddress;
import org.silvertunnel_ng.netlib.nameservice.redirect.SwitchingNetAddressNameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;













































class NameServiceNetlibAdapter
  implements NameServiceNetlibGenericAdapter
{
  private static final Logger LOG = LoggerFactory.getLogger(NameServiceNetlibAdapter.class);
  



  private final NetAddressNameService netAddressNameService;
  




  NameServiceNetlibAdapter(NetAddressNameService netAddressNameService)
  {
    this.netAddressNameService = netAddressNameService;
  }
  



  public String getHostByAddr(byte[] ip)
    throws UnknownHostException
  {
    LOG.info("getHostByAddr(ip={})", Arrays.toString(ip));
    

    String[] result = netAddressNameService.getNamesByAddress(new IpNetAddress(ip));
    

    return result[0];
  }
  





  public InetAddress[] lookupAllHostAddrJava6(String name)
    throws UnknownHostException
  {
    String netAddressNS = "unknown";
    if (netAddressNameService.getClass().equals(SwitchingNetAddressNameService.class))
    {
      netAddressNS = ((SwitchingNetAddressNameService)netAddressNameService).getLowerNetAddressNameServiceClass();
    }
    LOG.info("InetAddress[] lookupAllHostAddrJava6(name={} netAddressNameService={})", name, netAddressNS);
    

    NetAddress[] result = netAddressNameService.getAddressesByName(name);
    

    InetAddress[] resultFinal = new InetAddress[result.length];
    for (int i = 0; i < result.length; i++)
    {
      resultFinal[i] = ((IpNetAddress)result[i]).getIpaddressAsInetAddress();
    }
    
    return resultFinal;
  }
}
