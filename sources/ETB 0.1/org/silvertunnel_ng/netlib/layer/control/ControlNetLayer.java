package org.silvertunnel_ng.netlib.layer.control;

import java.io.IOException;
import java.util.Map;
import org.silvertunnel_ng.netlib.api.NetAddress;
import org.silvertunnel_ng.netlib.api.NetAddressNameService;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetLayerStatus;
import org.silvertunnel_ng.netlib.api.NetServerSocket;
import org.silvertunnel_ng.netlib.api.NetSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


























public class ControlNetLayer
  implements NetLayer
{
  private static final Logger LOG = LoggerFactory.getLogger(ControlNetLayer.class);
  


  private final NetLayer lowerNetLayer;
  


  private final ControlParameters controlParameters;
  



  public ControlNetLayer(NetLayer lowerNetLayer, ControlParameters controlParameters)
  {
    this.lowerNetLayer = lowerNetLayer;
    this.controlParameters = controlParameters;
  }
  



  public NetSocket createNetSocket(Map<String, Object> localProperties, NetAddress localAddress, NetAddress remoteAddress)
    throws IOException
  {
    return new ControlNetSocket(lowerNetLayer.createNetSocket(localProperties, localAddress, remoteAddress), controlParameters);
  }
  





  public NetServerSocket createNetServerSocket(Map<String, Object> properties, NetAddress localListenAddress)
    throws IOException
  {
    throw new UnsupportedOperationException();
  }
  
  public void clear()
    throws IOException
  {
    lowerNetLayer.clear();
  }
  

  public NetAddressNameService getNetAddressNameService()
  {
    return lowerNetLayer.getNetAddressNameService();
  }
  

  public NetLayerStatus getStatus()
  {
    return lowerNetLayer.getStatus();
  }
  

  public void waitUntilReady()
  {
    lowerNetLayer.waitUntilReady();
  }
}
