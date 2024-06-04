package org.silvertunnel_ng.netlib.layer.tor.circuit.cells;

import org.silvertunnel_ng.netlib.layer.tor.circuit.Circuit;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;






















public class CellRelayEstablishRendezvous
  extends CellRelay
{
  public CellRelayEstablishRendezvous(Circuit c, byte[] cookie)
    throws TorException
  {
    super(c, 33);
    

    if (cookie.length < 20)
    {
      throw new TorException("CellRelayEstablishRendezvous: rendevouz-cookie is too small");
    }
    
    if (cookie.length > data.length)
    {
      throw new TorException("CellRelayEstablishRendezvous: rendevouz-cookie is too large");
    }
    


    System.arraycopy(cookie, 0, data, 0, cookie.length);
    setLength(cookie.length);
  }
}
