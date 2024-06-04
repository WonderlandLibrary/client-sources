package org.silvertunnel_ng.netlib.api.util;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.silvertunnel_ng.netlib.api.NetAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



























public class IpNetAddress
  implements NetAddress
{
  private static final Logger LOG = LoggerFactory.getLogger(IpNetAddress.class);
  


  private byte[] ipaddress;
  

  private static Pattern ip4Pattern;
  


  static
  {
    try
    {
      ip4Pattern = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)\\.(\\d+)", 35);

    }
    catch (Exception e)
    {

      LOG.error("could not initialze class AuthorityKeyCertificate", e);
    }
  }
  











  public IpNetAddress(String ipAddressStr)
    throws IllegalArgumentException
  {
    try
    {
      Matcher m = ip4Pattern.matcher(ipAddressStr);
      if (m.find())
      {
        ipaddress = new byte[4];
        for (int i = 0; i < 4; i++)
        {
          ipaddress[i] = ((byte)Integer.parseInt(m.group(i + 1)));
        }
      }
    }
    catch (Exception e)
    {
      throw new IllegalArgumentException("could not parse IPv4 address=" + ipAddressStr);
    }
  }
  








  public IpNetAddress(byte[] ipaddress)
    throws IllegalArgumentException
  {
    if (ipaddress != null)
    {
      if (ipaddress.length == 4)
      {

        this.ipaddress = ipaddress;
      }
      else if (ipaddress.length == 16)
      {

        this.ipaddress = ipaddress;
      }
      else
      {
        throw new IllegalArgumentException("invalid IP address length (" + ipaddress.length + " bytes )");
      }
    }
  }
  









  public IpNetAddress(InetAddress inetAddress)
    throws IllegalArgumentException
  {
    if (inetAddress != null)
    {
      if ((inetAddress instanceof Inet4Address))
      {

        ipaddress = ((Inet4Address)inetAddress).getAddress();
      }
      else if (ipaddress.length == 16)
      {

        ipaddress = ((Inet6Address)inetAddress).getAddress();
      }
      else
      {
        throw new IllegalArgumentException("invalid inet address=" + inetAddress);
      }
    }
  }
  
  public byte[] getIpaddress()
  {
    return ipaddress;
  }
  
  public String getIpaddressAsString()
  {
    if (ipaddress == null)
    {
      return null;
    }
    if (ipaddress.length == 4)
    {



      return getByteAsNonnegativeInt(ipaddress[0]) + "." + getByteAsNonnegativeInt(ipaddress[1]) + "." + getByteAsNonnegativeInt(ipaddress[2]) + "." + getByteAsNonnegativeInt(ipaddress[3]);
    }
    




    return ":IPv6:" + ipaddress;
  }
  

  private int getByteAsNonnegativeInt(byte b)
  {
    if (b >= 0)
    {
      return b;
    }
    

    return 256 + b;
  }
  

  public InetAddress getIpaddressAsInetAddress()
  {
    if (ipaddress == null)
    {

      return null;
    }
    


    try
    {
      return InetAddress.getByAddress(ipaddress);
    }
    catch (UnknownHostException e)
    {
      LOG.warn("could not convert into InetAddress: {}", toString(), e); }
    return null;
  }
  





  protected String getId()
  {
    return "IpNetAddress(ipaddress=" + getIpaddressAsString() + ")";
  }
  

  public String toString()
  {
    return getId();
  }
  

  public int hashCode()
  {
    return getId().hashCode();
  }
  

  public boolean equals(Object obj)
  {
    if ((obj == null) || (!(obj instanceof IpNetAddress)))
    {
      return false;
    }
    
    IpNetAddress other = (IpNetAddress)obj;
    return getId().equals(other.getId());
  }
}
