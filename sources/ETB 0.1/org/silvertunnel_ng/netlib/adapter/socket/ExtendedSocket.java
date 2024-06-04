package org.silvertunnel_ng.netlib.adapter.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketImpl;
import org.silvertunnel_ng.netlib.api.NetSocket;






















































public class ExtendedSocket
  extends Socket
  implements NetSocket
{
  public ExtendedSocket() {}
  
  public ExtendedSocket(Proxy proxy)
  {
    super(proxy);
  }
  
  public ExtendedSocket(String host, int port) throws IOException
  {
    super(host, port);
  }
  
  public ExtendedSocket(InetAddress address, int port) throws IOException
  {
    super(address, port);
  }
  
  public ExtendedSocket(String host, int port, InetAddress localAddr, int localPort)
    throws IOException
  {
    super(host, port, localAddr, localPort);
  }
  
  public ExtendedSocket(InetAddress address, int port, InetAddress localAddr, int localPort)
    throws IOException
  {
    super(address, port, localAddr, localPort);
  }
  







  public ExtendedSocket(SocketImpl socketImpl)
    throws SocketException
  {
    super(socketImpl);
  }
}
