package org.silvertunnel_ng.netlib.layer.tor.circuit.cells;

import org.silvertunnel_ng.netlib.layer.tor.circuit.Stream;





























public class CellRelayConnected
  extends CellRelay
{
  public CellRelayConnected(Stream s)
  {
    super(s, 4);
    

    setLength(0);
  }
}
