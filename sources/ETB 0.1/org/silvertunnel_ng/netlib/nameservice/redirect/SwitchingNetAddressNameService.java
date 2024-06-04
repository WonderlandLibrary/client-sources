package org.silvertunnel_ng.netlib.nameservice.redirect;

import java.net.UnknownHostException;
import java.util.Map;
import org.silvertunnel_ng.netlib.api.NetAddress;
import org.silvertunnel_ng.netlib.api.NetAddressNameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


























public class SwitchingNetAddressNameService
  implements NetAddressNameService
{
  private static final Logger LOG = LoggerFactory.getLogger(SwitchingNetAddressNameService.class);
  







  private volatile NetAddressNameService lowerNetAddressNameService;
  








  public SwitchingNetAddressNameService(Map<String, NetAddress> name2AddressMapping, Map<NetAddress, String> address2NameMapping) {}
  







  public SwitchingNetAddressNameService(NetAddressNameService lowerNetAddressNameService)
  {
    this.lowerNetAddressNameService = lowerNetAddressNameService;
  }
  






  public void setLowerNetAddressNameService(NetAddressNameService lowerNetAddressNameService)
  {
    this.lowerNetAddressNameService = lowerNetAddressNameService;
  }
  



  public NetAddress[] getAddressesByName(String name)
    throws UnknownHostException
  {
    return lowerNetAddressNameService.getAddressesByName(name);
  }
  



  public String[] getNamesByAddress(NetAddress address)
    throws UnknownHostException
  {
    return lowerNetAddressNameService.getNamesByAddress(address);
  }
  


  public String getLowerNetAddressNameServiceClass()
  {
    if (lowerNetAddressNameService == null)
    {
      return "null";
    }
    return lowerNetAddressNameService.getClass().getCanonicalName();
  }
}
