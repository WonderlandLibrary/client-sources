package org.silvertunnel_ng.netlib.layer.tor.circuit.cells;

import java.net.InetAddress;
import org.silvertunnel_ng.netlib.layer.tor.circuit.Stream;
import org.silvertunnel_ng.netlib.layer.tor.common.TCPStreamProperties;






























public class CellRelayBegin
  extends CellRelay
{
  public CellRelayBegin(Stream s, TCPStreamProperties sp)
  {
    super(s, 1);
    
    byte[] host;
    byte[] host;
    if (sp.isAddrResolved())
    {

      StringBuffer sb = new StringBuffer();
      byte[] a = sp.getAddr().getAddress();
      for (int i = 0; i < 4; i++)
      {
        if (i > 0)
        {
          sb.append('.');
        }
        sb.append(a[i] & 0xFF);
      }
      
      host = sb.toString().getBytes();

    }
    else
    {
      host = sp.getHostname().getBytes();
    }
    
    System.arraycopy(host, 0, data, 0, host.length);
    
    getData()[host.length] = 58;
    
    byte[] port = Integer.valueOf(sp.getPort()).toString().getBytes();
    System.arraycopy(port, 0, getData(), host.length + 1, port.length);
    
    setLength(host.length + 1 + port.length + 1);
  }
}
