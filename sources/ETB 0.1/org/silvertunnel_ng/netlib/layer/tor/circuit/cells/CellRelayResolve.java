package org.silvertunnel_ng.netlib.layer.tor.circuit.cells;

import org.silvertunnel_ng.netlib.layer.tor.circuit.Stream;






















public class CellRelayResolve
  extends CellRelay
{
  public CellRelayResolve(Stream s, String hostname)
  {
    super(s, 11);
    
    byte[] host = hostname.getBytes();
    System.arraycopy(host, 0, data, 0, host.length);
    
    setLength(host.length);
  }
}
