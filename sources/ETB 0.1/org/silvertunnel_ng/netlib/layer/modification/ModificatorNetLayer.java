package org.silvertunnel_ng.netlib.layer.modification;

import java.io.IOException;
import java.util.Map;
import org.silvertunnel_ng.netlib.api.NetAddress;
import org.silvertunnel_ng.netlib.api.NetAddressNameService;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetLayerStatus;
import org.silvertunnel_ng.netlib.api.NetServerSocket;
import org.silvertunnel_ng.netlib.api.NetSocket;


























public class ModificatorNetLayer
  implements NetLayer
{
  private final NetLayer lowerNetLayer;
  private final ByteModificator inByteModificator;
  private final ByteModificator outByteModificator;
  
  public ModificatorNetLayer(NetLayer lowerNetLayer, ByteModificator inByteModificator, ByteModificator outByteModificator)
  {
    this.lowerNetLayer = lowerNetLayer;
    this.inByteModificator = inByteModificator;
    this.outByteModificator = outByteModificator;
  }
  



  public NetSocket createNetSocket(Map<String, Object> localProperties, NetAddress localAddress, NetAddress remoteAddress)
    throws IOException
  {
    NetSocket lowerLayerSocket = lowerNetLayer.createNetSocket(localProperties, localAddress, remoteAddress);
    
    return new ModificatorNetSocket(lowerLayerSocket, inByteModificator, outByteModificator);
  }
  




  public NetServerSocket createNetServerSocket(Map<String, Object> properties, NetAddress localListenAddress)
  {
    throw new UnsupportedOperationException();
  }
  


  public NetLayerStatus getStatus()
  {
    return lowerNetLayer.getStatus();
  }
  


  public void waitUntilReady()
  {
    lowerNetLayer.waitUntilReady();
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
}
