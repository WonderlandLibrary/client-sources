package org.silvertunnel_ng.netlib.layer.tor.common;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;
import org.apache.http.conn.util.InetAddressUtils;
import org.silvertunnel_ng.netlib.api.util.TcpipNetAddress;
import org.silvertunnel_ng.netlib.layer.tor.api.Fingerprint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;












































public final class TCPStreamProperties
{
  private static final Logger LOG = LoggerFactory.getLogger(TCPStreamProperties.class);
  
  private String hostname;
  
  private InetAddress addr;
  
  private boolean addrResolved;
  
  private boolean untrustedExitAllowed = true;
  
  private boolean nonGuardEntryAllowed = TorConfig.allowNonGuardEntry;
  




  private boolean exitPolicyRequired = true;
  
  private boolean fastRoute = true;
  
  private boolean stableRoute = false;
  
  private int port;
  
  private int routeMinLength;
  
  private int routeMaxLength;
  
  private int connectRetries;
  private boolean connectToDirServer = false;
  


  private float rankingInfluenceIndex;
  


  private Fingerprint[] route;
  


  private Fingerprint customExitpoint;
  



  public TCPStreamProperties(String host, int port)
  {
    hostname = host;
    this.port = port;
    addrResolved = false;
    
    init();
  }
  
  public TCPStreamProperties(InetAddress addr, int port)
  {
    hostname = addr.getHostAddress();
    this.addr = addr;
    this.port = port;
    addrResolved = true;
    
    init();
  }
  
  public TCPStreamProperties(TcpipNetAddress address)
  {
    if (address.getIpaddress() != null)
    {

      hostname = null;
      try
      {
        addr = InetAddress.getByAddress(address.getIpaddress());
      }
      catch (UnknownHostException e)
      {
        LOG.warn("invalid address=" + address, e);
      }
      
      port = address.getPort();
      addrResolved = true;

    }
    else
    {
      hostname = address.getHostname();
      addr = null;
      port = address.getPort();
      addrResolved = false;
    }
    
    init();
  }
  
  public TCPStreamProperties()
  {
    hostname = null;
    addr = null;
    port = 0;
    addrResolved = false;
    
    init();
  }
  

  private void init()
  {
    routeMinLength = TorConfig.getRouteMinLength();
    routeMaxLength = TorConfig.getRouteMaxLength();
    rankingInfluenceIndex = 1.0F;
    connectRetries = TorConfig.retriesStreamBuildup;
  }
  






  public void setCustomRoute(Fingerprint[] route)
  {
    this.route = route;
  }
  






  public Fingerprint getCustomExitpoint()
  {
    return customExitpoint;
  }
  





  public void setCustomExitpoint(Fingerprint node)
  {
    customExitpoint = node;
    if (route == null)
    {
      routeMinLength = routeMaxLength;
      route = new Fingerprint[routeMaxLength];
    }
    route[(route.length - 1)] = node;
  }
  




  public Fingerprint[] getProposedRouteFingerprints()
  {
    return route;
  }
  



  public String getDestination()
  {
    if (hostname.length() > 0)
    {
      return hostname;
    }
    return addr.getHostAddress();
  }
  









  public float getRankingInfluenceIndex()
  {
    return rankingInfluenceIndex;
  }
  





  public void setRankingInfluenceIndex(float rankingInfluenceIndex)
  {
    this.rankingInfluenceIndex = rankingInfluenceIndex;
  }
  






  public void setMinRouteLength(int min)
  {
    if (min >= 0)
    {
      routeMinLength = min;
    }
  }
  






  public void setMaxRouteLength(int max)
  {
    if (max >= 0)
    {
      routeMaxLength = max;
    }
  }
  





  public int getMinRouteLength()
  {
    return routeMinLength;
  }
  





  public int getMaxRouteLength()
  {
    return routeMaxLength;
  }
  
  public String getHostname()
  {
    if ((hostname == null) && (addr != null))
    {
      return addr.getHostAddress();
    }
    return hostname;
  }
  
  public void setAddr(InetAddress addr)
  {
    this.addr = addr;
  }
  
  public InetAddress getAddr()
  {
    if ((addr == null) && (hostname != null) && (!hostname.isEmpty()) && (InetAddressUtils.isIPv4Address(hostname)))
    {
      try
      {
        String[] octets = hostname.split("\\.");
        byte[] ip = new byte[4];
        ip[0] = ((byte)Integer.parseInt(octets[0]));
        ip[1] = ((byte)Integer.parseInt(octets[1]));
        ip[2] = ((byte)Integer.parseInt(octets[2]));
        ip[3] = ((byte)Integer.parseInt(octets[3]));
        return InetAddress.getByAddress(ip);
      }
      catch (UnknownHostException e)
      {
        return addr;
      }
    }
    return addr;
  }
  
  public boolean isAddrResolved()
  {
    return addrResolved;
  }
  
  public void setAddrResolved(boolean addrResolved)
  {
    this.addrResolved = addrResolved;
  }
  




  public boolean isUntrustedExitAllowed()
  {
    return untrustedExitAllowed;
  }
  
  public void setUntrustedExitAllowed(boolean untrustedExitAllowed)
  {
    this.untrustedExitAllowed = untrustedExitAllowed;
  }
  
  public boolean isNonGuardEntryAllowed()
  {
    return nonGuardEntryAllowed;
  }
  
  public void setNonGuardEntryAllowed(boolean nonGuardEntryAllowed)
  {
    this.nonGuardEntryAllowed = nonGuardEntryAllowed;
  }
  
  public boolean isExitPolicyRequired()
  {
    return exitPolicyRequired;
  }
  
  public void setExitPolicyRequired(boolean exitPolicyRequired)
  {
    this.exitPolicyRequired = exitPolicyRequired;
  }
  




  public int getPort()
  {
    return port;
  }
  




  public void setPort(int port)
  {
    this.port = port;
  }
  
  public int getConnectRetries()
  {
    return connectRetries;
  }
  
  public void setConnectRetries(int connectRetries)
  {
    this.connectRetries = connectRetries;
  }
  
  public Fingerprint[] getRouteFingerprints()
  {
    return route;
  }
  



  public boolean isFastRoute()
  {
    return fastRoute;
  }
  



  public void setFastRoute(boolean fastRoute)
  {
    this.fastRoute = fastRoute;
  }
  



  public boolean isStableRoute()
  {
    if (stableRoute)
    {
      return true;
    }
    if (getPort() > 0)
    {
      if (TorConfig.getLongLivedPorts().contains(Integer.valueOf(getPort())))
      {
        return true;
      }
    }
    return false;
  }
  



  public void setStableRoute(boolean stableRoute)
  {
    this.stableRoute = stableRoute;
  }
  



  public boolean isConnectToDirServer()
  {
    return connectToDirServer;
  }
  



  public void setConnectToDirServer(boolean connectToDirServer)
  {
    this.connectToDirServer = connectToDirServer;
  }
}
