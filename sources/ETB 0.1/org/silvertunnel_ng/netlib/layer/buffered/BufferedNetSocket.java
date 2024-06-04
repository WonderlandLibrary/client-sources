package org.silvertunnel_ng.netlib.layer.buffered;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.silvertunnel_ng.netlib.api.NetSocket;



























public class BufferedNetSocket
  implements NetSocket
{
  private final NetSocket lowerLayerSocket;
  private BufferedInputStream in;
  private BufferedOutputStream out;
  
  public BufferedNetSocket(NetSocket lowerLayerSocket)
  {
    this.lowerLayerSocket = lowerLayerSocket;
  }
  
  public void close()
    throws IOException
  {
    lowerLayerSocket.close();
  }
  
  public InputStream getInputStream()
    throws IOException
  {
    if (in == null)
    {
      in = new BufferedInputStream(lowerLayerSocket.getInputStream());
    }
    return in;
  }
  
  public OutputStream getOutputStream()
    throws IOException
  {
    if (out == null)
    {
      out = new BufferedOutputStream(lowerLayerSocket.getOutputStream());
    }
    return out;
  }
}
