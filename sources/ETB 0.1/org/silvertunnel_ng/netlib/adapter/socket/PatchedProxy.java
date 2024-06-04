package org.silvertunnel_ng.netlib.adapter.socket;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.SocketAddress;





















class PatchedProxy
  extends Proxy
{
  private static final int SOCKS_DEFAULT_PORT = 1080;
  private static final String SOCKS_SOCKET_IMPL = "java.net.SocksSocketImpl";
  
  public PatchedProxy()
  {
    super(Proxy.Type.SOCKS, new InetSocketAddress((InetAddress)null, 1080));
  }
  










  public SocketAddress address()
  {
    StackTraceElement[] elements = new Exception().getStackTrace();
    String callingClassFullName = elements[1].getClassName();
    

    if (!"java.net.SocksSocketImpl".equals(callingClassFullName))
    {


      return super.address();
    }
    



    return new InvalidSocketAddress();
  }
}
