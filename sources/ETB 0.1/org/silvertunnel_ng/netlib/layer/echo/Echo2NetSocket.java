package org.silvertunnel_ng.netlib.layer.echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.silvertunnel_ng.netlib.api.NetSocket;

























public class Echo2NetSocket
  implements NetSocket
{
  Echo2CircularByteBuffer cbb;
  
  public Echo2NetSocket()
    throws IOException
  {
    cbb = new Echo2CircularByteBuffer();
  }
  
  public void close()
    throws IOException
  {
    getInputStream().close();
    getOutputStream().close();
  }
  
  public InputStream getInputStream()
    throws IOException
  {
    return cbb.getInputStream();
  }
  
  public OutputStream getOutputStream()
    throws IOException
  {
    return cbb.getOutputStream();
  }
}
