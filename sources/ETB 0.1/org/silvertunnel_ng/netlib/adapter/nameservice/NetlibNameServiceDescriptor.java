package org.silvertunnel_ng.netlib.adapter.nameservice;

import java.lang.reflect.Constructor;
import org.silvertunnel_ng.netlib.api.NetAddressNameService;
import org.silvertunnel_ng.netlib.nameservice.mock.NopNetAddressNameService;
import org.silvertunnel_ng.netlib.nameservice.redirect.SwitchingNetAddressNameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.spi.nameservice.NameService;
import sun.net.spi.nameservice.NameServiceDescriptor;


















































public class NetlibNameServiceDescriptor
  implements NameServiceDescriptor
{
  private static final Logger LOG = LoggerFactory.getLogger(NetlibNameServiceDescriptor.class);
  

  public static final String DNS_PROVIDER_NAME = "NetlibNameService";
  

  private static NameService nameService;
  

  private static SwitchingNetAddressNameService switchingNetAddressNameService;
  

  static
  {
    try
    {
      LOG.info("NetlibNameServiceDescriptor#static called");
      
      NetAddressNameService firstNetAddressNameService = null;
      String firstNetAddressNameServiceName = System.getProperty("org.silvertunnel_ng.netlib.nameservice");
      if (firstNetAddressNameServiceName != null)
      {

        try
        {

          firstNetAddressNameService = (NetAddressNameService)Class.forName(firstNetAddressNameServiceName).getConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (Exception e)
        {
          LOG.warn("could not instantiate org.silvertunnel_ng.netlib.nameservice={}", firstNetAddressNameServiceName, e);
        }
      }
      

      if (firstNetAddressNameService == null)
      {

        firstNetAddressNameService = new NopNetAddressNameService();
      }
      


      switchingNetAddressNameService = new SwitchingNetAddressNameService(firstNetAddressNameService);
      







      nameService = new NameServiceNetlibJava6(new NameServiceNetlibAdapter(switchingNetAddressNameService));

    }
    catch (Throwable t)
    {

      LOG.error("NetlibNameServiceDescriptor initialization failed", t);
    }
  }
  






  public String getType()
  {
    LOG.debug("NetlibNameServiceDescriptor.getType() called");
    return "dns";
  }
  






  public String getProviderName()
  {
    LOG.debug("NetlibNameServiceDescriptor.getProviderName() called");
    return "NetlibNameService";
  }
  







  public NameService createNameService()
  {
    LOG.debug("NetlibNameServiceDescriptor.createNameService() called");
    return nameService;
  }
  




  public static SwitchingNetAddressNameService getSwitchingNetAddressNameService()
  {
    return switchingNetAddressNameService;
  }
  
  public NetlibNameServiceDescriptor() {}
}
