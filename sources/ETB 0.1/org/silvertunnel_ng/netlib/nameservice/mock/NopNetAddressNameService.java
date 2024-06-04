package org.silvertunnel_ng.netlib.nameservice.mock;

import java.net.UnknownHostException;
import org.silvertunnel_ng.netlib.api.NetAddress;
import org.silvertunnel_ng.netlib.api.NetAddressNameService;
import org.silvertunnel_ng.netlib.api.util.IpNetAddress;


























public class NopNetAddressNameService
  implements NetAddressNameService
{
  public static final String CHECKER_NAME = "checker.mock.dnstest.silvertunnel.org";
  public static final IpNetAddress[] CHECKER_IP = { new IpNetAddress("0.0.0.1") };
  
  private static NopNetAddressNameService instance;
  

  public NopNetAddressNameService() {}
  

  public NetAddress[] getAddressesByName(String name)
    throws UnknownHostException
  {
    if ("checker.mock.dnstest.silvertunnel.org".equals(name))
    {

      return CHECKER_IP;
    }
    

    throw new UnknownHostException("NopNetAddressNameService.getAddressesByName() always throws this IOException");
  }
  




  public String[] getNamesByAddress(NetAddress address)
    throws UnknownHostException
  {
    throw new UnknownHostException("NopNetAddressNameService.getNamesByAddress() always throws this IOException");
  }
  




  public static synchronized NopNetAddressNameService getInstance()
  {
    if (instance == null)
    {
      instance = new NopNetAddressNameService();
    }
    
    return instance;
  }
}
