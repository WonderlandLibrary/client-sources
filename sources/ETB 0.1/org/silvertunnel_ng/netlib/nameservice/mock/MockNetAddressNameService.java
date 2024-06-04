package org.silvertunnel_ng.netlib.nameservice.mock;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.silvertunnel_ng.netlib.api.NetAddress;
import org.silvertunnel_ng.netlib.api.NetAddressNameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


























public class MockNetAddressNameService
  implements NetAddressNameService
{
  private static final Logger LOG = LoggerFactory.getLogger(MockNetAddressNameService.class);
  




  private final Map<String, NetAddress[]> name2AddressesMapping;
  




  private final Map<NetAddress, String[]> address2NamesMapping;
  




  public MockNetAddressNameService(Map<String, NetAddress> name2AddressMapping, Map<NetAddress, String> address2NameMapping)
  {
    if (name2AddressMapping == null)
    {
      throw new NullPointerException("invalid name2AddressMapping=null");
    }
    if (address2NameMapping == null)
    {
      throw new NullPointerException("invalid address2NameMapping=null");
    }
    


    name2AddressesMapping = new HashMap(name2AddressMapping.size());
    for (Map.Entry<String, NetAddress> name2Address : name2AddressMapping
      .entrySet())
    {
      name2AddressesMapping.put(name2Address.getKey(), new NetAddress[] {
        (NetAddress)name2Address.getValue() });
    }
    


    address2NamesMapping = new HashMap(address2NameMapping.size());
    for (Map.Entry<NetAddress, String> address2Name : address2NameMapping
      .entrySet())
    {
      address2NamesMapping.put(address2Name.getKey(), new String[] {
        (String)address2Name.getValue() });
    }
  }
  
















  public MockNetAddressNameService(Map<String, NetAddress[]> name2AddressesMapping, Map<NetAddress, String[]> address2NamesMapping, boolean details)
  {
    if (name2AddressesMapping == null)
    {
      throw new NullPointerException("invalid name2AddressesMapping=null");
    }
    if (address2NamesMapping == null)
    {
      throw new NullPointerException("invalid address2NamesMapping=null");
    }
    

    this.name2AddressesMapping = name2AddressesMapping;
    this.address2NamesMapping = address2NamesMapping;
  }
  


  public NetAddress[] getAddressesByName(String name)
    throws UnknownHostException
  {
    NetAddress[] result = (NetAddress[])name2AddressesMapping.get(name);
    if ((result != null) && (result.length > 0))
    {
      return result;
    }
    

    throw new UnknownHostException("name=" + name + " could not be resolved");
  }
  




  public String[] getNamesByAddress(NetAddress address)
    throws UnknownHostException
  {
    String[] result = (String[])address2NamesMapping.get(address);
    if ((result != null) && (result.length > 0))
    {
      return result;
    }
    

    throw new UnknownHostException("address=" + address + " could not be resolved");
  }
}
