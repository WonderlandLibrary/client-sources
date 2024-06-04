package org.silvertunnel_ng.netlib.layer.tor.util;

import org.silvertunnel_ng.netlib.api.NetLayerStatus;

public abstract interface NetLayerStatusAdmin
{
  public abstract void setStatus(NetLayerStatus paramNetLayerStatus);
  
  public abstract void updateStatus(NetLayerStatus paramNetLayerStatus);
  
  public abstract NetLayerStatus getStatus();
}
