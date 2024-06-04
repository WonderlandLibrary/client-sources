package org.silvertunnel_ng.netlib.layer.tor.common;

import com.maxmind.geoip.LookupService;
import java.net.InetAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


























public class LookupServiceUtil
{
  private static final Logger LOG = LoggerFactory.getLogger(LookupServiceUtil.class);
  
  private static LookupService lookupService;
  
  static
  {
    try
    {
      lookupService = new LookupService(LookupServiceUtil.class.getResourceAsStream("/com/maxmind/geoip/GeoIP.dat"), 2000000);

    }
    catch (Exception e)
    {
      LOG.error("LookupService could not be initialized", e);
    }
  }
  






  public static String getCountryCodeOfIpAddress(InetAddress address)
  {
    String countryCode = null;
    if (lookupService != null)
    {
      countryCode = lookupService.getCountry(address.getAddress());
    }
    
    if ((countryCode == null) || (countryCode.length() < 1))
    {
      return "??";
    }
    

    return countryCode;
  }
  
  public LookupServiceUtil() {}
}
