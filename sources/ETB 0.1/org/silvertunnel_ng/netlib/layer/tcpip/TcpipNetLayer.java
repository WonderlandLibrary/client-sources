package org.silvertunnel_ng.netlib.layer.tcpip;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;
import org.silvertunnel_ng.netlib.adapter.socket.SocketGlobalUtil;
import org.silvertunnel_ng.netlib.api.NetAddress;
import org.silvertunnel_ng.netlib.api.NetAddressNameService;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetLayerStatus;
import org.silvertunnel_ng.netlib.api.NetServerSocket;
import org.silvertunnel_ng.netlib.api.NetSocket;
import org.silvertunnel_ng.netlib.api.impl.ServerSocket2NetServerSocket;
import org.silvertunnel_ng.netlib.api.impl.Socket2NetSocket;
import org.silvertunnel_ng.netlib.api.service.NetlibVersion;
import org.silvertunnel_ng.netlib.api.util.TcpipNetAddress;
import org.silvertunnel_ng.netlib.nameservice.cache.CachingNetAddressNameService;
import org.silvertunnel_ng.netlib.nameservice.inetaddressimpl.DefaultIpNetAddressNameService;
import org.silvertunnel_ng.netlib.util.PropertiesUtil;





















public class TcpipNetLayer
  implements NetLayer
{
  public static final String BACKLOG = "TcpipNetLayer.backlog";
  public static final String TIMEOUT_IN_MS = "TcpipNetLayer.timeoutInMs";
  private static final int DEFAULT_TIMEOUT = 5000;
  private NetAddressNameService netAddressNameService;
  
  static
  {
    NetlibVersion.getInstance();
  }
  














  public NetSocket createNetSocket(Map<String, Object> localProperties, NetAddress localAddress, NetAddress remoteAddress)
    throws IOException
  {
    TcpipNetAddress r = (TcpipNetAddress)remoteAddress;
    

    Integer timeoutInMs = PropertiesUtil.getAsInteger(localProperties, "TcpipNetLayer.timeoutInMs", Integer.valueOf(5000));
    

    Socket socket = SocketGlobalUtil.createOriginalSocket();
    if (r.getIpaddress() != null)
    {
      InetAddress remoteInetAddress = InetAddress.getByAddress(r.getIpaddress());
      InetSocketAddress remoteInetSocketAddress = new InetSocketAddress(remoteInetAddress, r.getPort());
      socket.connect(remoteInetSocketAddress, timeoutInMs.intValue());
    }
    else
    {
      InetSocketAddress remoteInetSocketAddress = new InetSocketAddress(r.getHostname(), r.getPort());
      if (remoteInetSocketAddress.getAddress() == null) {
        throw new UnknownHostException("hostlookup didnt worked. for Hostname : " + r.getHostname());
      }
      socket.connect(remoteInetSocketAddress, timeoutInMs.intValue());
    }
    

    return new Socket2NetSocket(socket);
  }
  




  public NetSocket createNetSocket(NetAddress localAddress, NetAddress remoteAddress)
    throws IOException
  {
    TcpipNetAddress r = (TcpipNetAddress)remoteAddress;
    
    Socket socket;
    Socket socket;
    if (r.getIpaddress() != null)
    {
      InetAddress inetAddress = InetAddress.getByAddress(r.getIpaddress());
      socket = new Socket(inetAddress, r.getPort());
    }
    else {
      socket = new Socket(r.getHostname(), r.getPort());
    }
    

    return new Socket2NetSocket(socket);
  }
  




  public NetServerSocket createNetServerSocket(Map<String, Object> properties, NetAddress localListenAddress)
    throws IOException
  {
    TcpipNetAddress l = (TcpipNetAddress)localListenAddress;
    

    Long backlogL = PropertiesUtil.getAsLong(properties, "TcpipNetLayer.backlog", null);
    int backlog = backlogL == null ? 0 : backlogL.intValue();
    

    ServerSocket serverSocket = new ServerSocket(l.getPort(), backlog);
    

    return new ServerSocket2NetServerSocket(serverSocket);
  }
  



  public NetLayerStatus getStatus()
  {
    return NetLayerStatus.READY;
  }
  



















  public NetAddressNameService getNetAddressNameService()
  {
    if (netAddressNameService == null)
    {
      netAddressNameService = new CachingNetAddressNameService(new DefaultIpNetAddressNameService());
    }
    
    return netAddressNameService;
  }
  
  public TcpipNetLayer() {}
  
  public void waitUntilReady() {}
  
  public void clear()
    throws IOException
  {}
}
