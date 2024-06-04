package org.silvertunnel_ng.netlib.layer.tor.circuit.cells;

import org.silvertunnel_ng.netlib.layer.tor.circuit.Circuit;
import org.silvertunnel_ng.netlib.layer.tor.circuit.Node;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;








































public class CellCreateFast
  extends Cell
{
  private static final Logger LOG = LoggerFactory.getLogger(CellCreateFast.class);
  





  public CellCreateFast(Circuit circuit)
    throws TorException
  {
    super(circuit, 5);
    System.arraycopy(circuit.getRouteNodes()[0].getDhXBytes(), 0, payload, 0, 20);
  }
}
