package org.silvertunnel_ng.netlib.layer.tor.circuit.cells;

import org.silvertunnel_ng.netlib.layer.tor.circuit.Stream;





























public class CellRelayDrop
  extends CellRelay
{
  public CellRelayDrop(Stream stream)
  {
    super(stream, 10);
  }
}
