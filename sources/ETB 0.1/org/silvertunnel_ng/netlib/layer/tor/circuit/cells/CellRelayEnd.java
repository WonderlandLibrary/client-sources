package org.silvertunnel_ng.netlib.layer.tor.circuit.cells;

import org.silvertunnel_ng.netlib.layer.tor.circuit.Stream;































public class CellRelayEnd
  extends CellRelay
{
  public CellRelayEnd(Stream s, byte reason)
  {
    super(s, 3);
    

    setLength(1);
    data[0] = reason;
  }
}
