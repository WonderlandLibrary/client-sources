package org.silvertunnel_ng.netlib.layer.tor.common;

public abstract interface TorEventHandler
{
  public abstract void fireEvent(TorEvent paramTorEvent);
}
