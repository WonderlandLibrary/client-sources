package org.silvertunnel_ng.netlib.layer.redirect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.silvertunnel_ng.netlib.api.NetAddress;
import org.silvertunnel_ng.netlib.api.NetAddressNameService;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetLayerStatus;
import org.silvertunnel_ng.netlib.api.NetServerSocket;
import org.silvertunnel_ng.netlib.api.NetSocket;
import org.silvertunnel_ng.netlib.api.util.IpNetAddress;
import org.silvertunnel_ng.netlib.api.util.TcpipNetAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



























public class ConditionalNetLayer
  implements NetLayer
{
  private static final Logger LOG = LoggerFactory.getLogger(ConditionalNetLayer.class);
  

  private List<Condition> conditions = new ArrayList();
  




  private final NetLayer defaultLowerNetLayer;
  




  public ConditionalNetLayer(List<Condition> conditions, NetLayer defaultLowerNetLayer)
  {
    this.conditions = Collections.synchronizedList(conditions);
    this.defaultLowerNetLayer = defaultLowerNetLayer;
  }
  







  protected NetLayer getMatchingNetLayer(NetAddress netAddress)
  {
    NetLayer result = getMatchingNetLayerOrNull(netAddress);
    if (result == null)
    {
      result = defaultLowerNetLayer;
    }
    if (LOG.isDebugEnabled())
    {
      LOG.debug("netAddress={} matches with lowerNetLayer={}", netAddress, result);
    }
    return result;
  }
  










  private NetLayer getMatchingNetLayerOrNull(NetAddress netAddress)
  {
    synchronized (conditions)
    {
      if (netAddress == null)
      {
        return defaultLowerNetLayer;
      }
      if ((netAddress instanceof TcpipNetAddress))
      {
        TcpipNetAddress tcpipNetAddress = (TcpipNetAddress)netAddress;
        

        String s = tcpipNetAddress.getHostnameAndPort();
        NetLayer result = getMatchingNetLayerOrNull(s);
        if (result != null)
        {
          return result;
        }
        
        s = tcpipNetAddress.getIpaddressAndPort();
        result = getMatchingNetLayerOrNull(s);
        if (result != null)
        {
          return result;
        }
      }
      else if ((netAddress instanceof IpNetAddress))
      {
        IpNetAddress ipNetAddress = (IpNetAddress)netAddress;
        

        String s = ipNetAddress.getIpaddressAsString();
        NetLayer result = getMatchingNetLayerOrNull(s);
        if (result != null)
        {
          return result;
        }
        
      }
      else
      {
        String s = netAddress.toString();
        NetLayer result = getMatchingNetLayerOrNull(s);
        if (result != null)
        {
          return result;
        }
      }
    }
    

    return defaultLowerNetLayer;
  }
  







  protected NetLayer getMatchingNetLayer(String addressName)
  {
    NetLayer result = getMatchingNetLayerOrNull(addressName);
    if (result == null)
    {
      result = defaultLowerNetLayer;
    }
    if (LOG.isDebugEnabled())
    {
      LOG.debug("addressName={} matches with lowerNetLayer={}", addressName, result);
    }
    return result;
  }
  






  private NetLayer getMatchingNetLayerOrNull(String s)
  {
    synchronized (conditions)
    {
      for (Condition condition : conditions)
      {
        if (condition.getPattern().matcher(s).matches())
        {

          return condition.getNetLayer();
        }
      }
    }
    
    return null;
  }
  




  private Set<NetLayer> getAllLowerNetLayers()
  {
    Set<NetLayer> result = new HashSet(conditions.size() + 1);
    synchronized (conditions)
    {
      for (Condition condition : conditions)
      {
        result.add(condition.getNetLayer());
      }
    }
    result.add(defaultLowerNetLayer);
    return result;
  }
  














  public NetSocket createNetSocket(Map<String, Object> localProperties, NetAddress localAddress, NetAddress remoteAddress)
    throws IOException
  {
    NetLayer lowerNetLayer = getMatchingNetLayer(remoteAddress);
    return lowerNetLayer.createNetSocket(localProperties, localAddress, remoteAddress);
  }
  
















  public NetServerSocket createNetServerSocket(Map<String, Object> properties, NetAddress localListenAddress)
    throws IOException
  {
    NetLayer lowerNetLayer = getMatchingNetLayer(localListenAddress);
    return lowerNetLayer.createNetServerSocket(properties, localListenAddress);
  }
  








  public NetLayerStatus getStatus()
  {
    Collection<NetLayerStatus> statuses = new ArrayList();
    for (NetLayer lowerNetLayer : getAllLowerNetLayers())
    {
      statuses.add(lowerNetLayer.getStatus());
    }
    

    return (NetLayerStatus)Collections.max(statuses);
  }
  






  public void waitUntilReady()
  {
    for (NetLayer lowerNetLayer : getAllLowerNetLayers())
    {
      lowerNetLayer.waitUntilReady();
    }
  }
  





  public void clear()
    throws IOException
  {
    for (NetLayer lowerNetLayer : getAllLowerNetLayers())
    {
      lowerNetLayer.clear();
    }
  }
  


  public NetAddressNameService getNetAddressNameService()
  {
    return new ConditionalNetAddressNameService(this);
  }
}
