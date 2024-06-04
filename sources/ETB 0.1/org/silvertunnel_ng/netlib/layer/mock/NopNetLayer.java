package org.silvertunnel_ng.netlib.layer.mock;

import java.io.IOException;
import java.util.Map;
import org.silvertunnel_ng.netlib.api.NetAddress;
import org.silvertunnel_ng.netlib.api.NetAddressNameService;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetLayerStatus;
import org.silvertunnel_ng.netlib.api.NetServerSocket;
import org.silvertunnel_ng.netlib.api.NetSocket;
import org.silvertunnel_ng.netlib.nameservice.mock.NopNetAddressNameService;

























public class NopNetLayer
  implements NetLayer
{
  public NopNetLayer() {}
  
  public synchronized NetSocket createNetSocket(Map<String, Object> localProperties, NetAddress localAddress, NetAddress remoteAddress)
    throws IOException
  {
    throw new IOException("NopNetLayer.createNetSocket() always throws this IOException");
  }
  




  public NetServerSocket createNetServerSocket(Map<String, Object> properties, NetAddress localListenAddress)
    throws IOException
  {
    throw new IOException("NopNetLayer.createNetServerSocket() always throws this IOException");
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
    return NopNetAddressNameService.getInstance();
  }
}
