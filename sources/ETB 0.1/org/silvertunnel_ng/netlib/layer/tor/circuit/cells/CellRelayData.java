package org.silvertunnel_ng.netlib.layer.tor.circuit.cells;

import org.silvertunnel_ng.netlib.layer.tor.circuit.Stream;





























public class CellRelayData
  extends CellRelay
{
  public CellRelayData(Stream s)
  {
    super(s, 2);
  }
}
