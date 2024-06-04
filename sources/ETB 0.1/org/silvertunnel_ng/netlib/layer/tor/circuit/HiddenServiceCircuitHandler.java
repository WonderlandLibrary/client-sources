package org.silvertunnel_ng.netlib.layer.tor.circuit;

public abstract interface HiddenServiceCircuitHandler
{
  public abstract boolean accept(Circuit paramCircuit, int paramInt1, int paramInt2);
}
