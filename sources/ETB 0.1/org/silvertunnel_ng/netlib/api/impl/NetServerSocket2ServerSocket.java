package org.silvertunnel_ng.netlib.api.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.ServerSocketChannel;
import org.silvertunnel_ng.netlib.api.NetServerSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




















public class NetServerSocket2ServerSocket
  extends ServerSocket
{
  private static final Logger LOG = LoggerFactory.getLogger(NetServerSocket2ServerSocket.class);
  
  private final NetServerSocket netServerSocket;
  
  public NetServerSocket2ServerSocket(NetServerSocket netServerSocket)
    throws IOException
  {
    this.netServerSocket = netServerSocket;
  }
  
  public void bind(SocketAddress endpoint) throws IOException
  {
    throw new SocketException("Already bound");
  }
  
  public void bind(SocketAddress endpoint, int backlog) throws IOException
  {
    throw new SocketException("Already bound");
  }
  
  public InetAddress getInetAddress()
  {
    LOG.warn("method empty/not implemented");
    return null;
  }
  
  public int getLocalPort()
  {
    LOG.warn("method empty/not implemented");
    return -1;
  }
  
  public SocketAddress getLocalSocketAddress()
  {
    LOG.warn("method empty/not implemented");
    return null;
  }
  
  public Socket accept() throws IOException
  {
    return new NetSocket2Socket(netServerSocket.accept());
  }
  
  public void close() throws IOException
  {
    netServerSocket.close();
  }
  
  public ServerSocketChannel getChannel()
  {
    LOG.warn("method empty/not implemented");
    return null;
  }
  
  public boolean isBound()
  {
    return true;
  }
  
  public boolean isClosed()
  {
    LOG.warn("method empty/not implemented");
    return false;
  }
  
  public synchronized void setSoTimeout(int timeout) throws SocketException
  {
    LOG.warn("method empty/not implemented");
  }
  
  public synchronized int getSoTimeout() throws IOException
  {
    LOG.warn("method empty/not implemented");
    return -1;
  }
  
  public void setReuseAddress(boolean on) throws SocketException
  {
    LOG.warn("method empty/not implemented");
  }
  
  public synchronized void setReceiveBufferSize(int size)
    throws SocketException
  {
    LOG.warn("method empty/not implemented");
  }
  
  public synchronized int getReceiveBufferSize() throws SocketException
  {
    LOG.warn("method empty/not implemented");
    return -1;
  }
  

  public void setPerformancePreferences(int connectionTime, int latency, int bandwidth)
  {
    LOG.warn("method empty/not implemented");
  }
}
