package org.silvertunnel_ng.netlib.layer.tor.circuit.cells;

import org.silvertunnel_ng.netlib.layer.tor.circuit.Circuit;
import org.silvertunnel_ng.netlib.layer.tor.circuit.Node;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;






















public class CellCreate
  extends Cell
{
  private static final Logger LOG = LoggerFactory.getLogger(CellCreate.class);
  





  public CellCreate(Circuit circuit)
    throws TorException
  {
    super(circuit, 1);
    
    byte[] data = new byte[''];
    


    System.arraycopy(circuit.getRouteNodes()[0].getSymmetricKeyForCreate(), 0, data, 0, 16);
    


    System.arraycopy(circuit.getRouteNodes()[0].getDhXBytes(), 0, data, 16, 128);
    

    byte[] temp = circuit.getRouteNodes()[0].asymEncrypt(data);
    
    if (payload.length < temp.length)
    {
      LOG.warn("encrypted data longer than max payload length.\n possible unwanted truncation.");
      System.arraycopy(temp, 0, payload, 0, payload.length);
    }
    else
    {
      System.arraycopy(temp, 0, payload, 0, temp.length);
    }
  }
}
