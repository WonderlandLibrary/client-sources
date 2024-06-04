package org.silvertunnel_ng.netlib.layer.redirect;

import java.io.IOException;
import java.util.Map;
import org.silvertunnel_ng.netlib.api.NetAddress;
import org.silvertunnel_ng.netlib.api.NetAddressNameService;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetLayerStatus;
import org.silvertunnel_ng.netlib.api.NetServerSocket;
import org.silvertunnel_ng.netlib.api.NetSocket;


































public class ForwardingNetLayer
  implements NetLayer
{
  private final NetLayer lowerNetLayer;
  private final Map<String, Object> lowerNetLayerLocalProperties;
  private final NetAddress lowerNetLayerLocalAddress;
  private final NetAddress lowerNetLayerRemoteAddress;
  
  public ForwardingNetLayer(NetLayer lowerNetLayer, Map<String, Object> lowerNetLayerLocalProperties, NetAddress lowerNetLayerLocalAddress, NetAddress lowerNetLayerRemoteAddress)
  {
    this.lowerNetLayer = lowerNetLayer;
    this.lowerNetLayerLocalProperties = lowerNetLayerLocalProperties;
    this.lowerNetLayerLocalAddress = lowerNetLayerLocalAddress;
    this.lowerNetLayerRemoteAddress = lowerNetLayerRemoteAddress;
  }
  















  public NetSocket createNetSocket(Map<String, Object> localProperties, NetAddress localAddress, NetAddress remoteAddress)
    throws IOException
  {
    return lowerNetLayer.createNetSocket(lowerNetLayerLocalProperties, lowerNetLayerLocalAddress, lowerNetLayerRemoteAddress);
  }
  










  public NetServerSocket createNetServerSocket(Map<String, Object> properties, NetAddress localListenAddress)
  {
    throw new UnsupportedOperationException();
  }
  


  public NetLayerStatus getStatus()
  {
    return NetLayerStatus.READY;
  }
  




  public void waitUntilReady() {}
  



  public void clear()
    throws IOException
  {}
  



  public NetAddressNameService getNetAddressNameService()
  {
    throw new UnsupportedOperationException();
  }
}
