package org.silvertunnel_ng.netlib.layer.tor.circuit.cells;

import org.silvertunnel_ng.netlib.layer.tor.circuit.Circuit;

















































public class CellRelayEarly
  extends CellRelay
{
  CellRelayEarly(Circuit c, int relayCommand)
  {
    super(c, 9, relayCommand);
    c.decrementRelayEarlyCellsRemaining();
  }
}
