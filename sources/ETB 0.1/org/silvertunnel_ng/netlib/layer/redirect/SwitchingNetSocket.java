package org.silvertunnel_ng.netlib.layer.redirect;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.silvertunnel_ng.netlib.api.NetSocket;



























public class SwitchingNetSocket
  implements NetSocket
{
  private final SwitchingNetLayer switchingNetLayer;
  private final NetSocket lowerNetSocket;
  private boolean closed = false;
  

  protected SwitchingNetSocket(SwitchingNetLayer switchingNetLayer, NetSocket lowerNetSocket)
  {
    this.switchingNetLayer = switchingNetLayer;
    this.lowerNetSocket = lowerNetSocket;
  }
  
  public synchronized InputStream getInputStream()
    throws IOException
  {
    if (!closed)
    {
      return lowerNetSocket.getInputStream();
    }
    

    throw new IOException("getInputStream(): SwitchingNetSocket already closed");
  }
  


  public synchronized OutputStream getOutputStream()
    throws IOException
  {
    if (!closed)
    {
      return lowerNetSocket.getOutputStream();
    }
    

    throw new IOException("getOutputStream(): SwitchingNetSocket already closed");
  }
  



  public synchronized void close()
    throws IOException
  {
    switchingNetLayer.removeFromLayer(this);
    
    closeLowerLayer();
  }
  

  public String toString()
  {
    return "SwitchingNetSocket(" + lowerNetSocket + ")";
  }
  
  protected synchronized void closeLowerLayer() throws IOException
  {
    closed = true;
    lowerNetSocket.close();
  }
}
