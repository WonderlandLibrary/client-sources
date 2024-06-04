package org.silvertunnel_ng.netlib.layer.tor.directory;

import java.io.IOException;
import org.silvertunnel_ng.netlib.layer.tor.api.RouterExitPolicy;
import org.silvertunnel_ng.netlib.layer.tor.util.Encoding;
import org.silvertunnel_ng.netlib.tool.ConvenientStreamReader;
import org.silvertunnel_ng.netlib.tool.ConvenientStreamWriter;

















































public final class RouterExitPolicyImpl
  implements RouterExitPolicy, Cloneable
{
  private final boolean accept;
  private final long ip;
  private final long netmask;
  private final int loPort;
  private final int hiPort;
  
  public RouterExitPolicyImpl(boolean accept, long ip, long netmask, int loPort, int hiPort)
  {
    this.accept = accept;
    this.ip = ip;
    this.netmask = netmask;
    this.loPort = loPort;
    this.hiPort = hiPort;
  }
  



  public RouterExitPolicy cloneReliable()
    throws RuntimeException
  {
    try
    {
      return (RouterExitPolicy)clone();
    }
    catch (CloneNotSupportedException e)
    {
      throw new RuntimeException(e);
    }
  }
  


  public String toString()
  {
    return accept + " " + Encoding.toHex(ip) + "/" + Encoding.toHex(netmask) + ":" + loPort + "-" + hiPort;
  }
  





  public boolean isAccept()
  {
    return accept;
  }
  

  public long getIp()
  {
    return ip;
  }
  

  public long getNetmask()
  {
    return netmask;
  }
  

  public int getLoPort()
  {
    return loPort;
  }
  

  public int getHiPort()
  {
    return hiPort;
  }
  




  public int hashCode()
  {
    int prime = 31;
    int result = 1;
    result = 31 * result + (accept ? 1231 : 1237);
    result = 31 * result + hiPort;
    result = 31 * result + (int)(ip ^ ip >>> 32);
    result = 31 * result + loPort;
    result = 31 * result + (int)(netmask ^ netmask >>> 32);
    return result;
  }
  




  public boolean equals(Object obj)
  {
    if (this == obj)
    {
      return true;
    }
    if (obj == null)
    {
      return false;
    }
    if (!(obj instanceof RouterExitPolicyImpl))
    {
      return false;
    }
    RouterExitPolicyImpl other = (RouterExitPolicyImpl)obj;
    if (accept != accept)
    {
      return false;
    }
    if (hiPort != hiPort)
    {
      return false;
    }
    if (ip != ip)
    {
      return false;
    }
    if (loPort != loPort)
    {
      return false;
    }
    if (netmask != netmask)
    {
      return false;
    }
    return true;
  }
  






  protected static RouterExitPolicyImpl parseFrom(ConvenientStreamReader data)
    throws IOException
  {
    RouterExitPolicyImpl result = new RouterExitPolicyImpl(data.readBoolean(), data.readLong(), data.readLong(), data.readInt(), data.readInt());
    return result;
  }
  
  public void save(ConvenientStreamWriter convenientStreamWriter)
    throws IOException
  {
    convenientStreamWriter.writeBoolean(accept);
    convenientStreamWriter.writeLong(ip);
    convenientStreamWriter.writeLong(netmask);
    convenientStreamWriter.writeInt(loPort);
    convenientStreamWriter.writeInt(hiPort);
  }
}
