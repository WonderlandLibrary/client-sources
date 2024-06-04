package org.silvertunnel_ng.netlib.layer.tcpip;

import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetLayerFactory;
import org.silvertunnel_ng.netlib.api.NetLayerIDs;
import org.silvertunnel_ng.netlib.layer.logger.LoggingNetLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;













































public final class TcpipNetLayerFactory
  implements NetLayerFactory
{
  private static final Logger LOG = LoggerFactory.getLogger(TcpipNetLayerFactory.class);
  


  private NetLayer netLayer;
  


  public TcpipNetLayerFactory() {}
  


  public synchronized NetLayer getNetLayerById(NetLayerIDs netLayerId)
  {
    if (netLayerId == NetLayerIDs.TCPIP)
    {
      if (netLayer == null)
      {

        NetLayer tcpipNetLayer = new TcpipNetLayer();
        NetLayer loggingTcpipNetLayer = new LoggingNetLayer(tcpipNetLayer, "upper tcpip  ");
        netLayer = loggingTcpipNetLayer;
      }
      return netLayer;
    }
    LOG.debug("NetLayer {} is not supported.", netLayerId);
    
    return null;
  }
}
