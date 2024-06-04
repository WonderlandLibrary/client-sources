package org.silvertunnel_ng.netlib.api.impl;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import org.silvertunnel_ng.netlib.api.NetSocket;
import org.silvertunnel_ng.netlib.api.util.JavaVersion;























public class NetSocket2Socket
  extends Socket
{
  private final NetSocket2SocketImpl netSocket2SocketImpl;
  
  public NetSocket2Socket(NetSocket netSocket)
    throws IOException
  {
    this(new NetSocket2SocketImpl(netSocket));
    if (JavaVersion.getJavaVersion() != JavaVersion.ANDROID) {
      int IP4SIZE = 4;
      InetAddress dummyInetAddress = InetAddress.getByAddress("NetSocket-dummy-host", new byte[4]);
      
      int dummyPort = 0;
      connect(new InetSocketAddress(dummyInetAddress, 0));
    } else {
      try {
        Field fIsConnected = getClass().getSuperclass().getDeclaredField("isConnected");
        fIsConnected.setAccessible(true);
        fIsConnected.setBoolean(this, true);
      } catch (Exception e) {
        throw new IOException("cannot set internal state to connected", e);
      }
    }
  }
  
  private NetSocket2Socket(NetSocket2SocketImpl netSocket2SocketImpl) throws IOException {
    super(netSocket2SocketImpl);
    this.netSocket2SocketImpl = netSocket2SocketImpl;
  }
  




  public void setNetSocket(NetSocket newNetSocket)
  {
    netSocket2SocketImpl.setNetSocket(newNetSocket);
  }
}
