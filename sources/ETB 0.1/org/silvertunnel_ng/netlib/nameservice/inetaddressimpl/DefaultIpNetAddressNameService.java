package org.silvertunnel_ng.netlib.nameservice.inetaddressimpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import org.silvertunnel_ng.netlib.api.NetAddress;
import org.silvertunnel_ng.netlib.api.NetAddressNameService;
import org.silvertunnel_ng.netlib.api.util.IpNetAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



























public class DefaultIpNetAddressNameService
  implements NetAddressNameService
{
  private static final Logger LOG = LoggerFactory.getLogger(DefaultIpNetAddressNameService.class);
  
  private Object inetAddressImpl;
  
  private Method lookupAllHostAddrMethod;
  
  private Method getHostByAddrMethod;
  
  private static final String TEST_HOSTNAME = "localhost";
  
  private static final IpNetAddress TEST_IP = new IpNetAddress("127.0.0.1");
  


  private static DefaultIpNetAddressNameService instance;
  



  public DefaultIpNetAddressNameService()
    throws UnsupportedOperationException
  {
    try
    {
      String inetAddressImplClassName;
      


      String inetAddressImplClassName;
      


      if (isIPv6Supported())
      {
        inetAddressImplClassName = "java.net.Inet6AddressImpl";
      }
      else
      {
        inetAddressImplClassName = "java.net.Inet4AddressImpl";
      }
      







      try
      {
        NetworkInterface.getByName(null);
      }
      catch (NullPointerException localNullPointerException) {}
      






      Class clazz = Class.forName(inetAddressImplClassName);
      
      Constructor constructor = clazz.getDeclaredConstructor(new Class[0]);
      constructor.setAccessible(true);
      inetAddressImpl = constructor.newInstance(new Object[0]);
      lookupAllHostAddrMethod = clazz.getDeclaredMethod("lookupAllHostAddr", new Class[] { String.class });
      lookupAllHostAddrMethod.setAccessible(true);
      getHostByAddrMethod = clazz.getDeclaredMethod("getHostByAddr", new Class[] { new byte[0].getClass() });
      getHostByAddrMethod.setAccessible(true);
      

      checkThatReflectionWorks();

    }
    catch (UnsupportedOperationException e)
    {
      LOG.error("error during initialization (1)", e);
      throw e;

    }
    catch (Throwable e)
    {
      LOG.error("error during initialization (2)", e);
      throw new UnsupportedOperationException("error during initialization (2)", e);
    }
  }
  










  private void checkThatReflectionWorks()
    throws UnsupportedOperationException
  {
    try
    {
      getAddressesByName("localhost");
      getNamesByAddress(TEST_IP);


    }
    catch (UnknownHostException e)
    {

      LOG.debug("got UnknownHostException", e);
    }
  }
  



  private static boolean isIPv6Supported()
  {
    return false;
  }
  















  public NetAddress[] getAddressesByName(String hostname)
    throws UnknownHostException, UnsupportedOperationException
  {
    if (hostname == null)
    {
      throw new UnknownHostException("hostname=null");
    }
    

    try
    {
      Object inetAddressesObj = lookupAllHostAddrMethod.invoke(inetAddressImpl, new Object[] { hostname });
      if (inetAddressesObj == null)
      {

        throw new UnknownHostException("hostname=" + hostname + " could not be resolved");
      }
      
      if ((inetAddressesObj instanceof byte[][]))
      {


        byte[][] inetAddresses = (byte[][])inetAddressesObj;
        NetAddress[] result = new NetAddress[inetAddresses.length];
        for (int i = 0; i < inetAddresses.length; i++)
        {
          result[i] = new IpNetAddress(inetAddresses[i]);
        }
        return result;
      }
      




      InetAddress[] inetAddresses = (InetAddress[])inetAddressesObj;
      NetAddress[] result = new NetAddress[inetAddresses.length];
      for (int i = 0; i < inetAddresses.length; i++)
      {
        result[i] = new IpNetAddress(inetAddresses[i]);
      }
      return result;

    }
    catch (UnknownHostException e)
    {

      throw e;

    }
    catch (InvocationTargetException e)
    {
      if ((e.getTargetException() instanceof UnknownHostException))
      {

        throw ((UnknownHostException)e.getTargetException());
      }
      throw new UnsupportedOperationException("resolution failed (1) for hostname=" + hostname, e);

    }
    catch (Exception e)
    {

      throw new UnsupportedOperationException("resolution failed (2) for hostname=" + hostname, e);
    }
  }
  














  public String[] getNamesByAddress(NetAddress ipaddress)
    throws UnknownHostException, UnsupportedOperationException
  {
    if (ipaddress == null)
    {
      throw new UnknownHostException("ipaddress=null");
    }
    if (!(ipaddress instanceof IpNetAddress))
    {
      throw new UnknownHostException("ipaddress is not of type IpNetAddress: " + ipaddress);
    }
    
    IpNetAddress ipNetAddress = (IpNetAddress)ipaddress;
    

    try
    {
      String hostname = (String)getHostByAddrMethod.invoke(inetAddressImpl, new Object[] {ipNetAddress
        .getIpaddress() });
      if (hostname == null)
      {
        throw new UnknownHostException("ipaddress=" + ipNetAddress + " could not be resolved");
      }
      



      return new String[] { hostname };

    }
    catch (UnknownHostException e)
    {

      throw e;

    }
    catch (InvocationTargetException e)
    {
      if ((e.getTargetException() instanceof UnknownHostException))
      {



        throw ((UnknownHostException)e.getTargetException());
      }
      throw new UnsupportedOperationException("resolution failed (1) ipaddress=" + ipaddress, e);

    }
    catch (Exception e)
    {

      throw new UnsupportedOperationException("resolution failed (2) ipaddress=" + ipaddress, e);
    }
  }
  




  public static synchronized DefaultIpNetAddressNameService getInstance()
  {
    if (instance == null)
    {
      instance = new DefaultIpNetAddressNameService();
    }
    
    return instance;
  }
}
