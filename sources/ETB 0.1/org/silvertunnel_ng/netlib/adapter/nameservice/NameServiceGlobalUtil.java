package org.silvertunnel_ng.netlib.adapter.nameservice;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.silvertunnel_ng.netlib.api.NetAddressNameService;
import org.silvertunnel_ng.netlib.api.util.IpNetAddress;
import org.silvertunnel_ng.netlib.nameservice.inetaddressimpl.DefaultIpNetAddressNameService;
import org.silvertunnel_ng.netlib.nameservice.mock.NopNetAddressNameService;
import org.silvertunnel_ng.netlib.nameservice.redirect.SwitchingNetAddressNameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.InetAddressCachePolicy;
import sun.net.spi.nameservice.NameService;
















































public class NameServiceGlobalUtil
{
  private static final Logger LOG = LoggerFactory.getLogger(NameServiceGlobalUtil.class);
  
  private static boolean initialized = false;
  private static boolean initializedWithSuccess = false;
  









  private static final long CACHE_TIMEOUT_MILLIS = 11000L;
  








  private static List<NameService> oldNameServices;
  









  public NameServiceGlobalUtil() {}
  









  public static synchronized void initNameService()
    throws IllegalStateException
  {
    if (initialized)
    {

      if (!isNopNetAddressNameServiceInstalled())
      {
        setIpNetAddressNameService(NopNetAddressNameService.getInstance());
      }
      LOG.debug("initialized");


    }
    else
    {


      System.setProperty("sun.net.spi.nameservice.provider.1", "dns,NetlibNameService");
      



      System.setProperty("sun.net.inetaddr.ttl", "0");
      System.setProperty("sun.net.inetaddr.negative.ttl", "0");
      
























      System.setProperty("org.silvertunnel_ng.netlib.nameservice", "org.silvertunnel_ng.netlib.nameservice.mock.NopNetAddressNameService");
      


      initialized = true;
    }
    


    initializedWithSuccess = isNopNetAddressNameServiceInstalled();
    if (initializedWithSuccess)
    {

      LOG.info("Installation of NameService adapter with NopNetAddressNameService was successful");

    }
    else
    {
      initNameServiceHardway();
      initializedWithSuccess = isNopNetAddressNameServiceInstalled();
      if (initializedWithSuccess)
      {

        LOG.info("Installation of NameService adapter with NopNetAddressNameService was successful (hard way)");

      }
      else
      {
        String msg = "Installation of NameService adapter with NopNetAddressNameService failed: probably the method NameServiceGlobalUtil.initNameService() is called too late, i.e. after first usage of java.net.InetAddress";
        

        LOG.error("Installation of NameService adapter with NopNetAddressNameService failed: probably the method NameServiceGlobalUtil.initNameService() is called too late, i.e. after first usage of java.net.InetAddress");
        throw new IllegalStateException("Installation of NameService adapter with NopNetAddressNameService failed: probably the method NameServiceGlobalUtil.initNameService() is called too late, i.e. after first usage of java.net.InetAddress");
      }
    }
  }
  




  private static void initNameServiceHardway()
  {
    try
    {
      Field field = InetAddress.class.getDeclaredField("nameServices");
      field.setAccessible(true);
      oldNameServices = (List)field.get(null);
      

      String provider = null;
      String propPrefix = "sun.net.spi.nameservice.provider.";
      int n = 1;
      List<NameService> nameServices = new ArrayList();
      provider = System.getProperty(propPrefix + n);
      while (provider != null)
      {
        Method m = InetAddress.class.getDeclaredMethod("createNSProvider", new Class[] { String.class });
        m.setAccessible(true);
        NameService ns = (NameService)m.invoke(null, new Object[] { provider });
        if (ns != null)
        {
          nameServices.add(ns);
        }
        
        n++;
        provider = System.getProperty(propPrefix + n);
      }
      


      if (nameServices.size() == 0)
      {
        Method m = InetAddress.class.getDeclaredMethod("createNSProvider", new Class[] { String.class });
        m.setAccessible(true);
        NameService ns = (NameService)m.invoke(null, new Object[] { "default" });
        nameServices.add(ns);
      }
      field.set(null, nameServices);
      

      Field fieldCache = InetAddressCachePolicy.class.getDeclaredField("cachePolicy");
      fieldCache.setAccessible(true);
      fieldCache.set(null, Integer.valueOf(0));
      

      Field fieldCacheNeg = InetAddressCachePolicy.class.getDeclaredField("negativeCachePolicy");
      fieldCacheNeg.setAccessible(true);
      fieldCacheNeg.set(null, Integer.valueOf(0));

    }
    catch (Exception exception)
    {
      LOG.debug("Hardway init doesnt work. got Exception : {}", exception, exception);
    }
  }
  


  public static void resetInetAddress()
  {
    if (oldNameServices != null)
    {
      try
      {
        Field field = InetAddress.class.getDeclaredField("nameServices");
        field.setAccessible(true);
        field.set(null, oldNameServices);
      }
      catch (Exception exception)
      {
        LOG.warn("Could not reset InetAddress due to exception", exception);
      }
    }
  }
  






  public static boolean isNopNetAddressNameServiceInstalled()
  {
    try
    {
      InetAddress[] address = InetAddress.getAllByName("dnstest.silvertunnel-ng.org");
      return false;

    }
    catch (UnknownHostException localUnknownHostException)
    {

      try
      {

        InetAddress[] address = InetAddress.getAllByName("checker.mock.dnstest.silvertunnel.org");
        

        if (address == null)
        {
          LOG.error("InetAddress.getAllByName() returned null as address (but this is wrong)");
          return false;
        }
        if (address.length != 1)
        {
          LOG.error("InetAddress.getAllByName() returned array of wrong size={}", Integer.valueOf(address.length));
          return false;
        }
        if (Arrays.equals(address[0].getAddress(), NopNetAddressNameService.CHECKER_IP[0].getIpaddress()))
        {

          return true;
        }
        

        LOG.error("InetAddress.getAllByName() returned wrong IP address={}", Arrays.toString(address[0].getAddress()));
        return false;

      }
      catch (Exception e)
      {
        LOG.error("InetAddress.getAllByName() throwed unexpected excpetion={}", e, e); } }
    return false;
  }
  














  public static synchronized void setIpNetAddressNameService(NetAddressNameService lowerNetAddressNameService)
    throws IllegalStateException
  {
    if (!initialized)
    {
      throw new IllegalStateException("initNameService() must be called first (but was not)");
    }
    



    NetlibNameServiceDescriptor.getSwitchingNetAddressNameService().setLowerNetAddressNameService(lowerNetAddressNameService);
  }
  




  public static long getCacheTimeoutMillis()
  {
    return 11000L;
  }
  




  public static boolean isDefaultIpNetAddressNameServiceActive()
  {
    return NetlibNameServiceDescriptor.getSwitchingNetAddressNameService().getLowerNetAddressNameServiceClass().equals(DefaultIpNetAddressNameService.class.getCanonicalName());
  }
  


  public static void activateDefaultIpNetAddressNameService()
  {
    if (!initialized)
    {
      initNameService();
    }
    setIpNetAddressNameService(DefaultIpNetAddressNameService.getInstance());
  }
}
