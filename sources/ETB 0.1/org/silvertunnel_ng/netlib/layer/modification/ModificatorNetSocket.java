package org.silvertunnel_ng.netlib.layer.modification;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.silvertunnel_ng.netlib.api.NetSocket;


























public class ModificatorNetSocket
  implements NetSocket
{
  private final NetSocket lowerLayerSocket;
  private ByteModificatorInputStream in;
  private ByteModificatorOutputStream out;
  private final ByteModificator inByteModificator;
  private final ByteModificator outByteModificator;
  
  public ModificatorNetSocket(NetSocket lowerLayerSocket, ByteModificator inByteModificator, ByteModificator outByteModificator)
  {
    this.lowerLayerSocket = lowerLayerSocket;
    this.inByteModificator = inByteModificator;
    this.outByteModificator = outByteModificator;
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

      in = new ByteModificatorInputStream(lowerLayerSocket.getInputStream(), inByteModificator);
    }
    return in;
  }
  
  public OutputStream getOutputStream()
    throws IOException
  {
    if (out == null)
    {

      out = new ByteModificatorOutputStream(lowerLayerSocket.getOutputStream(), outByteModificator);
    }
    return out;
  }
}
