package org.silvertunnel_ng.netlib.layer.tor.circuit.cells;

import org.silvertunnel_ng.netlib.layer.tor.circuit.Stream;






























public class CellRelayBeginDir
  extends CellRelay
{
  public CellRelayBeginDir(Stream stream)
  {
    super(stream, 13);
  }
}
