package org.silvertunnel_ng.netlib.layer.tor.api;

import org.silvertunnel_ng.netlib.api.NetLayerStatus;
























public class TorNetLayerStatus
  extends NetLayerStatus
{
  public static final TorNetLayerStatus CONSENSUS_LOADING = new TorNetLayerStatus("Consensus document (Tor router overview) will be loaded", 0.1D);
  
  public static final TorNetLayerStatus ROUTER_DESCRIPTORS_LOADING = new TorNetLayerStatus("Router descriptors (Tor router details) will be loaded", 0.3D);
  
  public static final TorNetLayerStatus INITIAL_CIRCUITES_ESTABLISHING = new TorNetLayerStatus("Initial circuits to Tor exit nodes will be established", 0.6D);
  









  protected TorNetLayerStatus(String name, double readyIndicator)
  {
    super(name, readyIndicator);
  }
}
