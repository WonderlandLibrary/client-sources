package org.silvertunnel_ng.netlib.api.impl;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketImpl;
import java.util.HashMap;
import java.util.Map;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetSocket;
import org.silvertunnel_ng.netlib.api.util.TcpipNetAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




















public class NetSocket2SocketImpl
  extends SocketImpl
{
  private static final Logger LOG = LoggerFactory.getLogger(NetSocket2SocketImpl.class);
  


  public static final String TCPIP_NET_LAYER_TIMEOUT_IN_MS = "TcpipNetLayer.timeoutInMs";
  


  private NetSocket netSocket;
  

  private NetLayer netLayer;
  

  private static final int DEFAULT_INPUTSTREAM_TIMEOUT = 60000;
  

  private int inputStreamTimeout = 60000;
  private static final Boolean DEFAULT_TCP_NODELAY = Boolean.valueOf(true);
  private Boolean tcpNodelay = DEFAULT_TCP_NODELAY;
  private static final Integer DEFAULT_SO_LINGER = Integer.valueOf(0);
  private Integer soLinger = DEFAULT_SO_LINGER;
  

  private SocketTimeoutInputStream inputStream;
  

  public NetSocket2SocketImpl(NetSocket netSocket)
  {
    FileDescriptor fd = new FileDescriptor();
    this.fd = fd;
    this.netSocket = netSocket;
  }
  


  public NetSocket2SocketImpl(NetLayer netLayer)
  {
    FileDescriptor fd = new FileDescriptor();
    this.fd = fd;
    this.netLayer = netLayer;
  }
  




  public void setNetSocket(NetSocket netSocket)
  {
    this.netSocket = netSocket;
  }
  
  protected void accept(SocketImpl arg0) throws IOException
  {
    LOG.warn("method empty/not implemented", new Throwable("method empty/not implemented"));
  }
  
  protected int available() throws IOException
  {
    LOG.warn("method empty/not implemented", new Throwable("method empty/not implemented"));
    return 0;
  }
  

  protected void bind(InetAddress arg0, int arg1)
    throws IOException
  {}
  
  protected void close()
    throws IOException
  {
    if (netSocket != null) {
      netSocket.close();
    } else {
      LOG.info("close() with netSocket=null", new Throwable("Just to dump a trace"));
    }
  }
  
  protected void connect(String remoteHost, int port)
    throws IOException
  {
    connect(new InetSocketAddress(remoteHost, port), 0);
  }
  
  protected void connect(InetAddress remoteAddress, int port)
    throws IOException
  {
    connect(new InetSocketAddress(remoteAddress, port), 0);
  }
  









  protected void connect(SocketAddress remoteAddress, int timeoutInMs)
    throws IOException
  {
    if (netSocket != null)
    {
      return;
    }
    





    if (netLayer == null) {
      throw new IllegalStateException("netLayer not set");
    }
    LOG.debug("method empty implemented");
    if ((remoteAddress == null) || (!(remoteAddress instanceof InetSocketAddress)))
    {
      throw new IllegalArgumentException("Unsupported address type");
    }
    

    InetSocketAddress remoteInetSocketAddress = (InetSocketAddress)remoteAddress;
    TcpipNetAddress remoteNetAddress;
    TcpipNetAddress remoteNetAddress; if (remoteInetSocketAddress.isUnresolved())
    {

      remoteNetAddress = new TcpipNetAddress(remoteInetSocketAddress.getHostName(), remoteInetSocketAddress.getPort());
    }
    else
    {
      remoteNetAddress = new TcpipNetAddress(remoteInetSocketAddress.getAddress().getHostAddress(), remoteInetSocketAddress.getPort());
    }
    

    Map<String, Object> localProperties = new HashMap();
    localProperties.put("TcpipNetLayer.timeoutInMs", Integer.valueOf(timeoutInMs));
    TcpipNetAddress localNetAddress = null;
    netSocket = netLayer.createNetSocket(localProperties, localNetAddress, remoteNetAddress);
  }
  


  protected void create(boolean arg0)
    throws IOException
  {}
  

  protected synchronized InputStream getInputStream()
    throws IOException
  {
    if (inputStream == null)
    {

      inputStream = new SocketTimeoutInputStream(netSocket.getInputStream(), inputStreamTimeout);
    }
    return inputStream;
  }
  
  protected OutputStream getOutputStream() throws IOException
  {
    return netSocket.getOutputStream();
  }
  
  protected void listen(int arg0) throws IOException
  {
    LOG.warn("method empty/not implemented", new Throwable("method empty/not implemented"));
  }
  
  protected void sendUrgentData(int arg0) throws IOException
  {
    LOG.warn("method empty/not implemented", new Throwable("method empty/not implemented"));
  }
  
  public Object getOption(int key) throws SocketException
  {
    if (key == 4102)
    {
      return Integer.valueOf(inputStreamTimeout);
    }
    if (key == 1) {
      return tcpNodelay;
    }
    if (key == 128)
    {
      return soLinger;
    }
    

    String msg = "no implementation for getOption(" + key + "). List of all options in java.net.SocketOptions.";
    
    if (LOG.isDebugEnabled()) {
      LOG.debug(msg, new Throwable("method not completely implemented"));
    } else {
      LOG.info(msg + " - Log with level=debug to get call hierarchy.");
    }
    return null;
  }
  
  public synchronized void setOption(int key, Object value)
    throws SocketException
  {
    LOG.debug("setOption(key={},value={})", Integer.valueOf(key), value);
    if (key == 4102)
    {
      if ((value instanceof Integer)) {
        inputStreamTimeout = ((Integer)value).intValue();
        if (inputStream != null) {
          inputStream.setTimeout(inputStreamTimeout);
        }
      } else {
        LOG.warn("ignored value of wrong type of setOption(key={},value={}). List of all options in java.net.SocketOptions.", 
        
          Integer.valueOf(key), value);
      }
      return;
    }
    if (key == 1)
    {
      if ((value instanceof Boolean)) {
        tcpNodelay = ((Boolean)value);
      } else {
        LOG.warn("ignored value of wrong type of setOption(key={},value={}). List of all options in java.net.SocketOptions.", 
          Integer.valueOf(key), value);
      }
      return;
    }
    if (key == 4102)
    {
      if ((value instanceof Integer)) {
        soLinger = ((Integer)value);
      } else {
        LOG.warn("ignored value of wrong type of setOption(key={},value={}). List of all options in java.net.SocketOptions.", 
          Integer.valueOf(key), value);
      }
      return;
    }
    

    String msg = "no implementation for setOption(key=" + key + ",value=" + value + "). List of all options in java.net.SocketOptions.";
    

    if (LOG.isDebugEnabled()) {
      LOG.debug(msg, new Throwable("method not completely implemented"));
    } else {
      LOG.warn(msg + " Log with level=debug to get call hierarchy.");
    }
  }
}
