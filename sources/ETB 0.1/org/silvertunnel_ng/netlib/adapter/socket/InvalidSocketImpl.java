package org.silvertunnel_ng.netlib.adapter.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketImpl;




















class InvalidSocketImpl
  extends SocketImpl
{
  InvalidSocketImpl() {}
  
  private void notImplemented()
  {
    throw new UnsupportedOperationException();
  }
  
  protected void accept(SocketImpl s) throws IOException
  {
    notImplemented();
  }
  
  protected int available() throws IOException
  {
    notImplemented();
    return 0;
  }
  
  protected void bind(InetAddress host, int port) throws IOException
  {
    notImplemented();
  }
  
  protected void close() throws IOException
  {
    notImplemented();
  }
  
  protected void connect(String host, int port) throws IOException
  {
    notImplemented();
  }
  
  protected void connect(InetAddress address, int port) throws IOException
  {
    notImplemented();
  }
  
  protected void connect(SocketAddress address, int timeout) throws IOException
  {
    notImplemented();
  }
  
  protected void create(boolean stream) throws IOException
  {
    notImplemented();
  }
  
  protected InputStream getInputStream() throws IOException
  {
    notImplemented();
    return null;
  }
  
  protected OutputStream getOutputStream() throws IOException
  {
    notImplemented();
    return null;
  }
  
  protected void listen(int backlog) throws IOException
  {
    notImplemented();
  }
  
  protected void sendUrgentData(int data) throws IOException
  {
    notImplemented();
  }
  
  public Object getOption(int arg0) throws SocketException
  {
    notImplemented();
    return null;
  }
  
  public void setOption(int arg0, Object arg1) throws SocketException
  {
    notImplemented();
  }
}
