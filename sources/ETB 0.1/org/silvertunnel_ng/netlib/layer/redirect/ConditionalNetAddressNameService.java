package org.silvertunnel_ng.netlib.layer.redirect;

import java.net.UnknownHostException;
import org.silvertunnel_ng.netlib.api.NetAddress;
import org.silvertunnel_ng.netlib.api.NetAddressNameService;
import org.silvertunnel_ng.netlib.api.NetLayer;























public class ConditionalNetAddressNameService
  implements NetAddressNameService
{
  private final ConditionalNetLayer conditionalNetLayer;
  
  protected ConditionalNetAddressNameService(ConditionalNetLayer conditionalNetLayer)
  {
    this.conditionalNetLayer = conditionalNetLayer;
  }
  














  public NetAddress[] getAddressesByName(String name)
    throws UnknownHostException
  {
    return conditionalNetLayer.getMatchingNetLayer(name).getNetAddressNameService().getAddressesByName(name);
  }
  













  public String[] getNamesByAddress(NetAddress address)
    throws UnknownHostException
  {
    return conditionalNetLayer.getMatchingNetLayer(address).getNetAddressNameService().getNamesByAddress(address);
  }
}
