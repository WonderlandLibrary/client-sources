package org.silvertunnel_ng.netlib.layer.tor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.silvertunnel_ng.netlib.api.NetSocket;
import org.silvertunnel_ng.netlib.layer.tor.stream.TCPStream;
























public class TorNetSocket
  implements NetSocket
{
  private final TCPStream tcpStream;
  private final String socketInfoMsg;
  
  public TorNetSocket(TCPStream tcpStream, String socketInfoMsg)
  {
    this.tcpStream = tcpStream;
    this.socketInfoMsg = socketInfoMsg;
  }
  
  public void close()
    throws IOException
  {
    tcpStream.close();
  }
  
  public InputStream getInputStream()
    throws IOException
  {
    return tcpStream.getInputStream();
  }
  
  public OutputStream getOutputStream()
    throws IOException
  {
    return tcpStream.getOutputStream();
  }
  

  public String toString()
  {
    return "ControlNetSocket(" + socketInfoMsg + ")";
  }
}
