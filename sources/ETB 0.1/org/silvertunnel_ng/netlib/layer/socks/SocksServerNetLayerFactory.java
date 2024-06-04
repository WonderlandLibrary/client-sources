package org.silvertunnel_ng.netlib.layer.socks;

import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetLayerFactory;
import org.silvertunnel_ng.netlib.api.NetLayerIDs;
import org.silvertunnel_ng.netlib.layer.logger.LoggingNetLayer;
import org.silvertunnel_ng.netlib.layer.tcpip.TcpipNetLayer;


















































public class SocksServerNetLayerFactory
  implements NetLayerFactory
{
  private NetLayer netLayer;
  
  public SocksServerNetLayerFactory() {}
  
  public synchronized NetLayer getNetLayerById(NetLayerIDs netLayerId)
  {
    if (netLayerId == NetLayerIDs.SOCKS_OVER_TCPIP)
    {
      if (netLayer == null)
      {

        NetLayer tcpipNetLayer = new TcpipNetLayer();
        NetLayer loggingTcpipNetLayer = new LoggingNetLayer(tcpipNetLayer, "upper tcpip  ");
        
        NetLayer socksProxyNetLayer = new SocksServerNetLayer(loggingTcpipNetLayer);
        
        netLayer = socksProxyNetLayer;
      }
      return netLayer;
    }
    

    return null;
  }
}
