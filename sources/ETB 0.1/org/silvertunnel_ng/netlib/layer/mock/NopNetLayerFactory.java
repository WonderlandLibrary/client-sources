package org.silvertunnel_ng.netlib.layer.mock;

import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetLayerFactory;
import org.silvertunnel_ng.netlib.api.NetLayerIDs;


















































public class NopNetLayerFactory
  implements NetLayerFactory
{
  private NetLayer netLayer;
  
  public NopNetLayerFactory() {}
  
  public synchronized NetLayer getNetLayerById(NetLayerIDs netLayerId)
  {
    if (netLayerId == NetLayerIDs.NOP)
    {
      if (netLayer == null)
      {

        netLayer = new NopNetLayer();
      }
      return netLayer;
    }
    

    return null;
  }
}
