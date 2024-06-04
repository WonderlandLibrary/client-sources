package org.silvertunnel_ng.netlib.layer.tls;

import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetLayerFactory;
import org.silvertunnel_ng.netlib.api.NetLayerIDs;
import org.silvertunnel_ng.netlib.layer.logger.LoggingNetLayer;
import org.silvertunnel_ng.netlib.layer.tcpip.TcpipNetLayer;


















































public class TLSNetLayerFactory
  implements NetLayerFactory
{
  private NetLayer netLayer;
  
  public TLSNetLayerFactory() {}
  
  public synchronized NetLayer getNetLayerById(NetLayerIDs netLayerId)
  {
    if (netLayerId == NetLayerIDs.TLS_OVER_TCPIP)
    {
      if (netLayer == null)
      {

        NetLayer tcpipNetLayer = new TcpipNetLayer();
        NetLayer loggingTcpipNetLayer = new LoggingNetLayer(tcpipNetLayer, "upper tcpip under tls/ssl ");
        

        TLSNetLayer tlsNetLayer = new TLSNetLayer(loggingTcpipNetLayer);
        
        NetLayer loggingTlsNetLayer = new LoggingNetLayer(tlsNetLayer, "upper tls/ssl             ");
        

        netLayer = loggingTlsNetLayer;
      }
      return netLayer;
    }
    

    return null;
  }
}
