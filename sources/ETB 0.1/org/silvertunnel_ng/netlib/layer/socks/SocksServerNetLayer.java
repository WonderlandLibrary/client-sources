package org.silvertunnel_ng.netlib.layer.socks;

import java.io.IOException;
import java.util.Map;
import org.silvertunnel_ng.netlib.api.NetAddress;
import org.silvertunnel_ng.netlib.api.NetAddressNameService;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetLayerStatus;
import org.silvertunnel_ng.netlib.api.NetServerSocket;
import org.silvertunnel_ng.netlib.api.NetSocket;
































public class SocksServerNetLayer
  implements NetLayer
{
  private final NetLayer lowerNetLayer;
  
  public SocksServerNetLayer(NetLayer lowerNetLayer)
  {
    this.lowerNetLayer = lowerNetLayer;
  }
  

















  public NetSocket createNetSocket(Map<String, Object> localProperties, NetAddress localAddress, NetAddress remoteAddress)
    throws IOException
  {
    return new SocksServerNetSession(lowerNetLayer, localProperties, localAddress, remoteAddress).createHigherLayerNetSocket();
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
