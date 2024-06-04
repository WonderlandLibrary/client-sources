package org.silvertunnel_ng.netlib.layer.echo;

import java.io.IOException;
import java.util.Map;
import org.silvertunnel_ng.netlib.api.NetAddress;
import org.silvertunnel_ng.netlib.api.NetAddressNameService;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetLayerStatus;
import org.silvertunnel_ng.netlib.api.NetServerSocket;
import org.silvertunnel_ng.netlib.api.NetSocket;































public class Echo2NetLayer
  implements NetLayer
{
  public Echo2NetLayer() {}
  
  public NetSocket createNetSocket(Map<String, Object> localProperties, NetAddress localAddress, NetAddress remoteAddress)
    throws IOException
  {
    return new Echo2NetSocket();
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
