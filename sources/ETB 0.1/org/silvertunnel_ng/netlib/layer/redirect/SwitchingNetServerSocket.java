package org.silvertunnel_ng.netlib.layer.redirect;

import java.io.IOException;
import org.silvertunnel_ng.netlib.api.NetServerSocket;
import org.silvertunnel_ng.netlib.api.NetSocket;




























public class SwitchingNetServerSocket
  implements NetServerSocket
{
  private final SwitchingNetLayer switchingNetLayer;
  private final NetServerSocket lowerNetServerSocket;
  private boolean closed = false;
  

  protected SwitchingNetServerSocket(SwitchingNetLayer switchingNetLayer, NetServerSocket lowerNetServerSocket)
  {
    this.switchingNetLayer = switchingNetLayer;
    this.lowerNetServerSocket = lowerNetServerSocket;
  }
  
  public synchronized NetSocket accept()
    throws IOException
  {
    if (!closed)
    {

      SwitchingNetSocket result = new SwitchingNetSocket(switchingNetLayer, lowerNetServerSocket.accept());
      switchingNetLayer.addToLayer(result);
      return result;
    }
    

    throw new IOException("accept(): SwitchingNetServerSocket already closed");
  }
  



  public synchronized void close()
    throws IOException
  {
    switchingNetLayer.removeFromLayer(this);
    
    closeLowerLayer();
  }
  
  protected synchronized void closeLowerLayer() throws IOException
  {
    closed = true;
    lowerNetServerSocket.close();
  }
}
