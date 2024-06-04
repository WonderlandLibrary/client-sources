package org.silvertunnel_ng.netlib.layer.tls.android;

import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketImpl;
import java.util.UUID;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





















public class LocalProxySocket
  extends Socket
{
  private LocalServerSocket localServerSocket;
  private LocalSocket localSocketSend;
  private LocalSocket localSocketRecv;
  private Socket originalSocket;
  private static final Logger LOG = LoggerFactory.getLogger(LocalProxySocket.class);
  
  public LocalProxySocket(Socket original) throws TorException
  {
    try
    {
      localSocketSend = new LocalSocket();
      
      String socketName = "local" + UUID.randomUUID();
      localServerSocket = new LocalServerSocket(socketName);
      localSocketSend.connect(new LocalSocketAddress(socketName));
      localSocketRecv = localServerSocket.accept();
      originalSocket = original;
      
      LocalProxyWorker lpw1 = new LocalProxyWorker(localSocketRecv.getInputStream(), originalSocket.getOutputStream(), "to");
      LocalProxyWorker lpw2 = new LocalProxyWorker(originalSocket.getInputStream(), localSocketRecv.getOutputStream(), "from");
      Thread t1 = new Thread(lpw1);
      Thread t2 = new Thread(lpw2);
      t1.start();
      t2.start();
      
      FileDescriptor fd = localSocketSend.getFileDescriptor();
      SocketImpl socketImpl = (SocketImpl)Class.forName("java.net.PlainSocketImpl").getConstructor(new Class[] { FileDescriptor.class }).newInstance(new Object[] { fd });
      Field implField = getClass().getSuperclass().getDeclaredField("impl");
      implField.setAccessible(true);
      implField.set(this, socketImpl);
    } catch (Exception e) {
      LOG.debug("Got Exception while trying to create LocalProxySocket", e);
      throw new TorException("could not create LocalProxySocket", e);
    }
  }
  
  private class LocalProxyWorker implements Runnable {
    private InputStream inputStream;
    private OutputStream outputStream;
    private String direction;
    
    public LocalProxyWorker(InputStream inputStream, OutputStream outputStream, String direction) {
      this.inputStream = inputStream;
      this.outputStream = outputStream;
      this.direction = direction;
    }
    
    public void run()
    {
      boolean error = false;
      while (!error) {
        try {
          if (inputStream.available() > 0) {
            copyStream(inputStream, outputStream);
          }
        } catch (IOException e) {
          LocalProxySocket.LOG.debug("got Exception during copy direction : {}", direction, e);
          error = true;
          try {
            inputStream.close();
          } catch (IOException e1) {
            if ((!(e1 instanceof SocketException)) || (!e1.getMessage().contains("closed"))) {
              LocalProxySocket.LOG.debug("got exception during close of inputStream direction : {}", direction, e1);
            }
          }
          try {
            outputStream.close();
          } catch (IOException e1) {
            if ((!(e1 instanceof SocketException)) || (!e1.getMessage().contains("closed"))) {
              LocalProxySocket.LOG.debug("got exception during close of outputStream direction : {}", direction, e1);
            }
          }
        }
      }
    }
    
    void copyStream(InputStream input, OutputStream output) throws IOException
    {
      byte[] buffer = new byte['Ѐ'];
      int bytesRead;
      while ((bytesRead = input.read(buffer)) != -1) {
        output.write(buffer, 0, bytesRead);
      }
    }
  }
  




  public synchronized void close()
    throws IOException
  {
    super.close();
    originalSocket.close();
    localSocketRecv.close();
    LOG.debug("close() called", new Throwable());
  }
  



  public InetAddress getInetAddress()
  {
    return originalSocket.getInetAddress();
  }
  







  public InputStream getInputStream()
    throws IOException
  {
    return super.getInputStream();
  }
  

  public boolean getKeepAlive()
    throws SocketException
  {
    return originalSocket.getKeepAlive();
  }
  



  public InetAddress getLocalAddress()
  {
    return originalSocket.getLocalAddress();
  }
  



  public int getLocalPort()
  {
    return originalSocket.getLocalPort();
  }
  







  public OutputStream getOutputStream()
    throws IOException
  {
    return super.getOutputStream();
  }
  



  public int getPort()
  {
    return originalSocket.getPort();
  }
  


  public int getSoLinger()
    throws SocketException
  {
    return originalSocket.getSoLinger();
  }
  

  public synchronized int getReceiveBufferSize()
    throws SocketException
  {
    return originalSocket.getReceiveBufferSize();
  }
  

  public synchronized int getSendBufferSize()
    throws SocketException
  {
    return originalSocket.getSendBufferSize();
  }
  

  public synchronized int getSoTimeout()
    throws SocketException
  {
    return originalSocket.getSoTimeout();
  }
  

  public boolean getTcpNoDelay()
    throws SocketException
  {
    return originalSocket.getTcpNoDelay();
  }
  

  public void setKeepAlive(boolean keepAlive)
    throws SocketException
  {
    originalSocket.setKeepAlive(keepAlive);
  }
  

  public synchronized void setSendBufferSize(int size)
    throws SocketException
  {
    originalSocket.setSendBufferSize(size);
  }
  

  public synchronized void setReceiveBufferSize(int size)
    throws SocketException
  {
    originalSocket.setReceiveBufferSize(size);
  }
  


  public void setSoLinger(boolean on, int timeout)
    throws SocketException
  {
    originalSocket.setSoLinger(on, timeout);
  }
  



  public synchronized void setSoTimeout(int timeout)
    throws SocketException
  {
    originalSocket.setSoTimeout(timeout);
  }
  

  public void setTcpNoDelay(boolean on)
    throws SocketException
  {
    originalSocket.setTcpNoDelay(on);
  }
  







  public String toString()
  {
    return "LocalProxySocket : " + super.toString() + " - " + originalSocket.toString() + " - " + localSocketSend.toString();
  }
  






  public void shutdownInput()
    throws IOException
  {
    originalSocket.shutdownInput();
    localSocketRecv.shutdownInput();
  }
  






  public void shutdownOutput()
    throws IOException
  {
    originalSocket.shutdownOutput();
    localSocketRecv.shutdownOutput();
  }
  





  public SocketAddress getLocalSocketAddress()
  {
    return originalSocket.getLocalSocketAddress();
  }
  





  public SocketAddress getRemoteSocketAddress()
  {
    return originalSocket.getRemoteSocketAddress();
  }
  





  public boolean isBound()
  {
    return originalSocket.isBound();
  }
  




  public boolean isConnected()
  {
    return originalSocket.isConnected();
  }
  





  public boolean isClosed()
  {
    return originalSocket.isClosed();
  }
  









  public void bind(SocketAddress localAddr)
    throws IOException
  {
    originalSocket.bind(localAddr);
  }
  







  public void connect(SocketAddress remoteAddr)
    throws IOException
  {
    originalSocket.connect(remoteAddr);
  }
  












  public void connect(SocketAddress remoteAddr, int timeout)
    throws IOException
  {
    originalSocket.connect(remoteAddr, timeout);
  }
  






  public boolean isInputShutdown()
  {
    return (originalSocket.isInputShutdown()) || (localSocketRecv.isInputShutdown());
  }
  






  public boolean isOutputShutdown()
  {
    return (originalSocket.isOutputShutdown()) || (localSocketRecv.isOutputShutdown());
  }
}
