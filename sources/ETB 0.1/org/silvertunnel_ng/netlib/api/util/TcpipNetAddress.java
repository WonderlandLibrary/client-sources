package org.silvertunnel_ng.netlib.api.util;

import java.io.Serializable;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.silvertunnel_ng.netlib.api.NetAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;













































public class TcpipNetAddress
  implements NetAddress, Serializable
{
  private static final Logger LOG = LoggerFactory.getLogger(TcpipNetAddress.class);
  

  private String hostname;
  

  private byte[] ipaddress;
  

  private int port;
  

  private static final String DEFAULT_HOSTNAME = "0.0.0.0";
  

  private static final int MIN_PORT = 0;
  

  private static final int MAX_PORT = 65535;
  

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
  





  public TcpipNetAddress(String hostnameOrIpaddressAndTcpPort)
    throws IllegalArgumentException
  {
    String portStr;
    




    String portStr;
    




    if (hostnameOrIpaddressAndTcpPort.contains(":"))
    {


      int idx = hostnameOrIpaddressAndTcpPort.lastIndexOf(':');
      hostname = hostnameOrIpaddressAndTcpPort.substring(0, idx);
      if (hostname.length() == 0)
      {
        hostname = "0.0.0.0";
      }
      

      try
      {
        Matcher m = ip4Pattern.matcher(hostname);
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
        throw new IllegalArgumentException("could not parse IPv4 address=" + hostname);
      }
      

      portStr = hostnameOrIpaddressAndTcpPort.substring(idx + 1);

    }
    else
    {
      hostname = "0.0.0.0";
      portStr = hostnameOrIpaddressAndTcpPort;
    }
    

    try
    {
      port = Integer.parseInt(portStr);
    }
    catch (NumberFormatException e)
    {
      throw new IllegalArgumentException("port could not be parsed of nameOrIpAddressAndTcpPort=" + hostnameOrIpaddressAndTcpPort);
    }
    checkThis();
  }
  






  public TcpipNetAddress(String hostname, int port)
  {
    this.hostname = hostname;
    this.port = port;
  }
  








  public TcpipNetAddress(byte[] ipaddress, int port)
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
    this.port = port;
  }
  








  public TcpipNetAddress(IpNetAddress ipaddress, int port)
    throws IllegalArgumentException
  {
    this(ipaddress.getIpaddress(), port);
  }
  









  public TcpipNetAddress(String hostname, byte[] ipaddress, int port)
    throws IllegalArgumentException
  {
    this.hostname = hostname;
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
    this.port = port;
  }
  








  public TcpipNetAddress(String hostname, InetAddress inetAddress, int port)
    throws IllegalArgumentException
  {
    this.hostname = hostname;
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
    this.port = port;
  }
  








  public TcpipNetAddress(InetAddress inetAddress, int port)
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
    this.port = port;
  }
  




  private void checkThis()
    throws IllegalArgumentException
  {
    if ((port < 0) || (port > 65535))
    {
      throw new IllegalArgumentException("port=" + port + " is out of range");
    }
  }
  



  public String getHostname()
  {
    return hostname;
  }
  



  public String getHostnameAndPort()
  {
    return (hostname == null ? "" : hostname) + ":" + port;
  }
  




  public byte[] getIpaddress()
  {
    return ipaddress;
  }
  



  public IpNetAddress getIpNetAddress()
  {
    return new IpNetAddress(ipaddress);
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
    


    StringBuffer result = new StringBuffer();
    
    return ":IPv6:" + ipaddress;
  }
  

  private int getByteAsNonnegativeInt(byte signedByte)
  {
    if (signedByte >= 0)
    {
      return signedByte;
    }
    

    return 256 + signedByte;
  }
  




  public String getIpaddressAndPort()
  {
    String ipaddressStr = getIpaddressAsString();
    return (ipaddressStr == null ? "" : ipaddressStr) + ":" + port;
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
  






  public String getHostnameOrIpaddress()
  {
    if (hostname != null)
    {
      return hostname;
    }
    

    return getIpaddressAsString();
  }
  




  public int getPort()
  {
    return port;
  }
  



  protected String getId()
  {
    return "TcpipNetAddress(hostname=" + hostname + ",ipaddress=" + getIpaddressAsString() + ",port=" + port + ")";
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
    if ((obj == null) || (!(obj instanceof TcpipNetAddress)))
    {
      return false;
    }
    
    TcpipNetAddress other = (TcpipNetAddress)obj;
    if (hostname == null)
    {
      if (hostname != null)
      {
        return false;
      }
    }
    else
    {
      if (hostname == null)
      {
        return false;
      }
      if (!hostname.equals(hostname))
      {
        return false;
      }
    }
    if (port != port)
    {
      return false;
    }
    if (ipaddress == null)
    {
      if (ipaddress != null)
      {
        return false;
      }
    }
    else
    {
      if (ipaddress == null)
      {
        return false;
      }
      if (!Arrays.equals(ipaddress, ipaddress))
      {
        return false;
      }
    }
    return true;
  }
}
