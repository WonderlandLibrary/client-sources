package org.silvertunnel_ng.netlib.api.impl;

import java.io.IOException;
import java.net.ServerSocket;
import org.silvertunnel_ng.netlib.api.NetServerSocket;
import org.silvertunnel_ng.netlib.api.NetSocket;

























public class ServerSocket2NetServerSocket
  implements NetServerSocket
{
  private final ServerSocket serverSocket;
  
  public ServerSocket2NetServerSocket(ServerSocket serverSocket)
    throws IOException
  {
    this.serverSocket = serverSocket;
  }
  
  public NetSocket accept()
    throws IOException
  {
    return new Socket2NetSocket(serverSocket.accept());
  }
  
  public void close()
    throws IOException
  {
    serverSocket.close();
  }
}
