package org.silvertunnel_ng.netlib.layer.tor;

import org.silvertunnel_ng.netlib.api.NetFactory;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetLayerFactory;
import org.silvertunnel_ng.netlib.api.NetLayerIDs;
import org.silvertunnel_ng.netlib.layer.socks.SocksServerNetLayer;
import org.silvertunnel_ng.netlib.util.TempfileStringStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;














































public class TorNetLayerFactory
  implements NetLayerFactory
{
  private static final Logger LOG = LoggerFactory.getLogger(TorNetLayerFactory.class);
  

  private NetLayer torNetLayer;
  

  private NetLayer socksOverTorNetLayer;
  


  public TorNetLayerFactory() {}
  

  public NetLayer getNetLayerById(NetLayerIDs netLayerId)
  {
    try
    {
      if ((netLayerId == NetLayerIDs.TOR_OVER_TLS_OVER_TCPIP) || (netLayerId == NetLayerIDs.TOR))
      {
        if (torNetLayer == null)
        {


          NetLayer tcpipNetLayer = NetFactory.getInstance().getNetLayerById(NetLayerIDs.TCPIP);
          
          NetLayer tlsNetLayer = NetFactory.getInstance().getNetLayerById(NetLayerIDs.TLS_OVER_TCPIP);
          

          torNetLayer = new TorNetLayer(tlsNetLayer, tcpipNetLayer, TempfileStringStorage.getInstance());
        }
        return torNetLayer;
      }
      
      if ((netLayerId == NetLayerIDs.SOCKS_OVER_TOR_OVER_TLS_OVER_TCPIP) || (netLayerId == NetLayerIDs.SOCKS_OVER_TOR))
      {
        if (socksOverTorNetLayer == null)
        {

          if (torNetLayer == null)
          {

            torNetLayer = getNetLayerById(NetLayerIDs.TOR_OVER_TLS_OVER_TCPIP);
          }
          
          socksOverTorNetLayer = new SocksServerNetLayer(torNetLayer);
        }
        return socksOverTorNetLayer;
      }
      

      return null;

    }
    catch (Exception e)
    {
      LOG.error("could not create " + netLayerId, e); }
    return null;
  }
}
