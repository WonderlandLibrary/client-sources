package org.silvertunnel_ng.netlib.layer.tor.circuit.cells;

import org.silvertunnel_ng.netlib.layer.tor.circuit.Circuit;




















































public class CellRelayRendezvous1
  extends CellRelay
{
  public CellRelayRendezvous1(Circuit circuit, byte[] cookie, byte[] dhY, byte[] keyHandshake)
  {
    super(circuit, 36);
    
    System.arraycopy(cookie, 0, data, 0, cookie.length);
    System.arraycopy(dhY, 0, data, cookie.length, dhY.length);
    System.arraycopy(keyHandshake, 0, data, cookie.length + dhY.length, keyHandshake.length);
    setLength(cookie.length + dhY.length + keyHandshake.length);
  }
}
