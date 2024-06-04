package org.silvertunnel_ng.netlib.layer.tor.circuit.cells;

import org.silvertunnel_ng.netlib.layer.tor.circuit.Circuit;
import org.silvertunnel_ng.netlib.layer.tor.circuit.Stream;













































public class CellRelaySendme
  extends CellRelay
{
  public CellRelaySendme(Stream stream)
  {
    super(stream, 5);
  }
  










  public CellRelaySendme(Circuit circuit, int router)
  {
    super(circuit, 5);
    setAddressedRouter(router);
  }
}
