package org.silvertunnel_ng.netlib.adapter.httpclient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetSocket;
import org.silvertunnel_ng.netlib.api.impl.NetSocket2Socket;
import org.silvertunnel_ng.netlib.api.util.TcpipNetAddress;
import org.silvertunnel_ng.netlib.layer.mock.MockNetSocket;






































public class NetlibSocketFactory
  implements SocketFactory
{
  private final NetLayer lowerNetLayer;
  private static final int WAIT_ENDLESS = -1;
  
  public NetlibSocketFactory(NetLayer lowerNetLayer)
  {
    this.lowerNetLayer = lowerNetLayer;
  }
  



  public Socket createSocket()
    throws IOException
  {
    return new NetSocket2Socket(new MockNetSocket(new byte[0], -1L));
  }
  











  public Socket connectSocket(Socket sock, String host, int port, InetAddress localAddress, int localPort, HttpParams params)
    throws IOException
  {
    if (host == null)
    {
      throw new IllegalArgumentException("Target host may not be null.");
    }
    if (params == null)
    {
      throw new IllegalArgumentException("Parameters may not be null.");
    }
    
    TcpipNetAddress localNetAddress = null;
    if ((localAddress != null) || (localPort > 0))
    {

      if (localPort < 0)
      {
        localPort = 0;
      }
      
      localNetAddress = new TcpipNetAddress(localAddress, localPort);
    }
    

    int timeoutInMs = HttpConnectionParams.getConnectionTimeout(params);
    

    Map<String, Object> localProperties = new HashMap();
    localProperties.put("TcpipNetLayer.timeoutInMs", Integer.valueOf(timeoutInMs));
    TcpipNetAddress remoteNetAddress = new TcpipNetAddress(host, port);
    
    try
    {
      NetSocket netSocket = lowerNetLayer.createNetSocket(localProperties, localNetAddress, remoteNetAddress);
      
      if ((sock != null) && ((sock instanceof NetSocket2Socket)))
      {

        NetSocket2Socket netSocket2Socket = (NetSocket2Socket)sock;
        netSocket2Socket.setNetSocket(netSocket);
        return netSocket2Socket;
      }
      


      return new NetSocket2Socket(netSocket);

    }
    catch (SocketTimeoutException ex)
    {

      throw new ConnectTimeoutException("Connect to " + remoteNetAddress + " timed out");
    }
  }
  














  public final boolean isSecure(Socket sock)
    throws IllegalArgumentException
  {
    if (sock == null)
    {
      throw new IllegalArgumentException("Socket may not be null.");
    }
    

    if (sock.isClosed())
    {
      throw new IllegalArgumentException("Socket is closed.");
    }
    return false;
  }
}
