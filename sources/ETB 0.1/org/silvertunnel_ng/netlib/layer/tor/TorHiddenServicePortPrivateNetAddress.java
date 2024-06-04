package org.silvertunnel_ng.netlib.layer.tor;

import org.silvertunnel_ng.netlib.api.NetAddress;
import org.silvertunnel_ng.netlib.api.util.TcpipNetAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





























public class TorHiddenServicePortPrivateNetAddress
  implements NetAddress
{
  private static final Logger LOG = LoggerFactory.getLogger(TorHiddenServicePortPrivateNetAddress.class);
  


  private final TorHiddenServicePrivateNetAddress torHiddenServicePrivateNetAddress;
  

  private final int port;
  

  private static final int MIN_PORT = 0;
  

  private static final int MAX_PORT = 65535;
  


  public TorHiddenServicePortPrivateNetAddress(TorHiddenServicePrivateNetAddress torHiddenServicePrivateNetAddress, int port)
  {
    this.torHiddenServicePrivateNetAddress = torHiddenServicePrivateNetAddress;
    this.port = port;
    
    checkThis();
  }
  




  private void checkThis()
    throws IllegalArgumentException
  {
    if ((port < 0) || (port > 65535))
    {
      throw new IllegalArgumentException("port=" + port + " is out of range");
    }
  }
  



  public byte[] getPublicKeyHash()
  {
    return torHiddenServicePrivateNetAddress.getPublicKeyHash();
  }
  




  public String getPublicOnionHostname()
  {
    return torHiddenServicePrivateNetAddress.getPublicOnionHostname();
  }
  



  public TcpipNetAddress getPublicTcpipNetAddress()
  {
    return new TcpipNetAddress(getPublicOnionHostname(), getPort());
  }
  




  protected String getId()
  {
    return "TorHiddenServicePrivateNetAddress(hostname=" + getPublicOnionHostname() + ",port=" + port + ")";
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
    if ((obj == null) || (!(obj instanceof TorHiddenServicePortPrivateNetAddress)))
    {
      return false;
    }
    
    TorHiddenServicePortPrivateNetAddress other = (TorHiddenServicePortPrivateNetAddress)obj;
    return getId().equals(other.getId());
  }
  




  public TorHiddenServicePrivateNetAddress getTorHiddenServicePrivateNetAddress()
  {
    return torHiddenServicePrivateNetAddress;
  }
  
  public int getPort()
  {
    return port;
  }
}
