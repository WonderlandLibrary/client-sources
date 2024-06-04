package org.silvertunnel_ng.netlib.api.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.silvertunnel_ng.netlib.api.NetSocket;



















public class Socket2NetSocket
  implements NetSocket
{
  private final Socket socket;
  
  public Socket2NetSocket(Socket socket)
    throws IOException
  {
    this.socket = socket;
  }
  
  public InputStream getInputStream()
    throws IOException
  {
    return socket.getInputStream();
  }
  
  public OutputStream getOutputStream()
    throws IOException
  {
    return socket.getOutputStream();
  }
  
  public void close()
    throws IOException
  {
    socket.close();
  }
  

  public String toString()
  {
    return "Socket2NetSocket(" + socket + ")";
  }
}
