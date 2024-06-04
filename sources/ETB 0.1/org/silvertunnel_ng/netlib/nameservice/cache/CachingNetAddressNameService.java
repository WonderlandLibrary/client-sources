package org.silvertunnel_ng.netlib.nameservice.cache;

import java.net.UnknownHostException;
import org.silvertunnel_ng.netlib.api.NetAddress;
import org.silvertunnel_ng.netlib.api.NetAddressNameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


























public class CachingNetAddressNameService
  implements NetAddressNameService
{
  private static final Logger LOG = LoggerFactory.getLogger(CachingNetAddressNameService.class);
  

  private static final int DEFAULT_MAX_ELEMENTS_IN_CACHE = 1000;
  

  private static final boolean DEFAULT_IS_NAME_CASE_SENSITIVE = false;
  

  private static final int DEFAULT_CACHE_TTL_SECONDS = 60;
  

  private static final int DEFAULT_CACHE_NEGATIVE_TTL_SECONDS = 60;
  

  private final NetAddressNameService lowerNetAddressNameService;
  

  private final boolean isNameCaseSensitive;
  

  private final Cache<String, NetAddress[]> name2AddressesMappingPositive;
  

  private final Cache<String, Boolean> name2AddressesMappingNegative;
  

  private final Cache<NetAddress, String[]> address2NamesMappingPositive;
  
  private final Cache<NetAddress, Boolean> address2NamesMappingNegative;
  

  public CachingNetAddressNameService(NetAddressNameService lowerNetAddressNameService)
  {
    this(lowerNetAddressNameService, 1000, false, 60, 60);
  }
  


























  public CachingNetAddressNameService(NetAddressNameService lowerNetAddressNameService, int maxElementsInCache, boolean isNameCaseSensitive, int cacheTtlSeconds, int cacheNegativeTtlSeconds)
  {
    this.lowerNetAddressNameService = lowerNetAddressNameService;
    this.isNameCaseSensitive = isNameCaseSensitive;
    

    name2AddressesMappingPositive = new Cache(maxElementsInCache, cacheTtlSeconds);
    
    name2AddressesMappingNegative = new Cache(maxElementsInCache, cacheNegativeTtlSeconds);
    
    address2NamesMappingPositive = new Cache(maxElementsInCache, cacheTtlSeconds);
    
    address2NamesMappingNegative = new Cache(maxElementsInCache, cacheNegativeTtlSeconds);
  }
  




  public NetAddress[] getAddressesByName(String name)
    throws UnknownHostException
  {
    if (name == null)
    {
      throw new UnknownHostException("name=null");
    }
    

    if (!isNameCaseSensitive)
    {
      name = name.toLowerCase();
    }
    

    NetAddress[] result = (NetAddress[])name2AddressesMappingPositive.get(name);
    if (result != null)
    {
      return result;
    }
    Boolean negativeResult = (Boolean)name2AddressesMappingNegative.get(name);
    if (Boolean.TRUE == negativeResult)
    {
      throw new UnknownHostException("name=\"" + name + "\" could be resolved in cache as negative result");
    }
    


    try
    {
      result = lowerNetAddressNameService.getAddressesByName(name);
      if (result != null)
      {

        name2AddressesMappingPositive.put(name, result);
      }
      return result;

    }
    catch (UnknownHostException e)
    {
      name2AddressesMappingNegative.put(name, Boolean.TRUE);
      throw e;
    }
  }
  



  public String[] getNamesByAddress(NetAddress address)
    throws UnknownHostException
  {
    if (address == null)
    {
      throw new UnknownHostException("address=null");
    }
    

    String[] result = (String[])address2NamesMappingPositive.get(address);
    if (result != null)
    {
      return result;
    }
    
    Boolean negativeResult = (Boolean)address2NamesMappingNegative.get(address);
    if (Boolean.TRUE == negativeResult)
    {

      throw new UnknownHostException("address=\"" + address + "\" could be resolved in cache as negative result");
    }
    


    try
    {
      result = lowerNetAddressNameService.getNamesByAddress(address);
      if (result != null)
      {

        address2NamesMappingPositive.put(address, result);
      }
      return result;

    }
    catch (UnknownHostException e)
    {
      address2NamesMappingNegative.put(address, Boolean.TRUE);
      throw e;
    }
  }
}
