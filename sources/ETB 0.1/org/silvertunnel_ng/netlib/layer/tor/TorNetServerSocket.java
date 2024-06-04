package org.silvertunnel_ng.netlib.layer.tor;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.silvertunnel_ng.netlib.api.NetServerSocket;
import org.silvertunnel_ng.netlib.api.NetSocket;
import org.silvertunnel_ng.netlib.layer.tor.circuit.Circuit;
import org.silvertunnel_ng.netlib.layer.tor.circuit.HiddenServiceInstance;
import org.silvertunnel_ng.netlib.layer.tor.circuit.HiddenServicePortInstance;
import org.silvertunnel_ng.netlib.layer.tor.stream.TCPStream;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


























public class TorNetServerSocket
  implements NetServerSocket, HiddenServicePortInstance
{
  private static final Logger LOG = LoggerFactory.getLogger(TorNetServerSocket.class);
  
  private static final int SERVER_QUEUE_MAX_SIZE = 10;
  private final BlockingQueue<TCPStream> streams = new ArrayBlockingQueue(10, false);
  
  private final String info;
  
  private final int port;
  private boolean closed = false;
  



  private HiddenServiceInstance hiddenServiceInstance;
  




  public TorNetServerSocket(String info, int port)
  {
    this.info = info;
    this.port = port;
  }
  

  public String toString()
  {
    return "TorNetServerSocket(info=" + info + ", port=" + port + ")";
  }
  




  public NetSocket accept()
    throws IOException
  {
    LOG.info("accept() called");
    
    TCPStream nextStream = null;
    try
    {
      nextStream = (TCPStream)streams.take();
    }
    catch (InterruptedException e)
    {
      LOG.warn("waiting interrupted", e);
    }
    LOG.info("accept() got stream from queue nextStream=" + nextStream);
    
    return new TorNetSocket(nextStream, "TorNetLayer accepted server connection");
  }
  

  public void close()
    throws IOException
  {
    closed = true;
  }
  





  public int getPort()
  {
    return port;
  }
  

  public boolean isOpen()
  {
    return !closed;
  }
  







  public void createStream(Circuit circuit, int streamId)
    throws TorException, IOException
  {
    LOG.debug("addStream() called");
    TCPStream newStream = new TCPStream(circuit, streamId);
    try
    {
      streams.put(newStream);
    }
    catch (InterruptedException e)
    {
      LOG.warn("waiting interrupted", e);
    }
  }
  

  public HiddenServiceInstance getHiddenServiceInstance()
  {
    return hiddenServiceInstance;
  }
  


  public void setHiddenServiceInstance(HiddenServiceInstance hiddenServiceInstance)
  {
    this.hiddenServiceInstance = hiddenServiceInstance;
  }
}
