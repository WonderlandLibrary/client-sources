package org.silvertunnel_ng.netlib.layer.redirect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import org.silvertunnel_ng.netlib.api.NetAddress;
import org.silvertunnel_ng.netlib.api.NetAddressNameService;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetLayerStatus;
import org.silvertunnel_ng.netlib.api.NetServerSocket;
import org.silvertunnel_ng.netlib.api.NetSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


























public class SwitchingNetLayer
  implements NetLayer
{
  private static final Logger LOG = LoggerFactory.getLogger(SwitchingNetLayer.class);
  

  private static int OPEN_SOCKETS_WARN_THRESHOLD = 100;
  



  private volatile NetLayer lowerNetLayer;
  


  private final Collection<SwitchingNetSocket> switchingNetSockets = new ArrayList();
  




  private final Collection<SwitchingNetServerSocket> switchingNetServerSockets = new ArrayList();
  






  public SwitchingNetLayer(NetLayer lowerNetLayer)
  {
    this.lowerNetLayer = lowerNetLayer;
  }
  











  public synchronized void setLowerNetLayer(NetLayer lowerNetLayer, boolean closeAllOpenConnectionsImmediately)
  {
    for (SwitchingNetServerSocket serverSocket : switchingNetServerSockets)
    {
      try
      {
        serverSocket.closeLowerLayer();
      }
      catch (Exception e)
      {
        LOG.info("setLowerNetLayer(): exception while closing lower server socket: " + e);
      }
    }
    
    switchingNetServerSockets.clear();
    

    if (closeAllOpenConnectionsImmediately)
    {
      for (SwitchingNetSocket socket : switchingNetSockets)
      {
        try
        {
          socket.closeLowerLayer();
        }
        catch (Exception e)
        {
          LOG.info("setLowerNetLayer(): exception while closing lower socket: " + e);
        }
      }
    }
    
    switchingNetSockets.clear();
    

    this.lowerNetLayer = lowerNetLayer;
  }
  















  public NetSocket createNetSocket(Map<String, Object> localProperties, NetAddress localAddress, NetAddress remoteAddress)
    throws IOException
  {
    SwitchingNetSocket result = new SwitchingNetSocket(this, lowerNetLayer.createNetSocket(localProperties, localAddress, remoteAddress));
    
    addToLayer(result);
    
    return result;
  }
  
















  public NetServerSocket createNetServerSocket(Map<String, Object> properties, NetAddress localListenAddress)
    throws IOException
  {
    SwitchingNetServerSocket result = new SwitchingNetServerSocket(this, lowerNetLayer.createNetServerSocket(properties, localListenAddress));
    
    addToLayer(result);
    
    return result;
  }
  


  public NetLayerStatus getStatus()
  {
    return lowerNetLayer.getStatus();
  }
  


  public void waitUntilReady()
  {
    lowerNetLayer.waitUntilReady();
  }
  

  public void clear()
    throws IOException
  {
    lowerNetLayer.clear();
  }
  


  public NetAddressNameService getNetAddressNameService()
  {
    return lowerNetLayer.getNetAddressNameService();
  }
  




  protected synchronized void addToLayer(SwitchingNetSocket switchingNetSocket)
  {
    switchingNetSockets.add(switchingNetSocket);
    

    if (switchingNetSockets.size() >= OPEN_SOCKETS_WARN_THRESHOLD)
    {

      String msg = "SwitchingNetLayer: " + switchingNetSockets.size() + " open sockets - this could be a resource and memory leak";
      
      if (switchingNetSockets.size() == OPEN_SOCKETS_WARN_THRESHOLD)
      {

        LOG.warn(msg, new Throwable("use thread dump to localize potential resource and memory leak"));


      }
      else
      {

        LOG.warn(msg);
      }
    }
  }
  

  private synchronized void addToLayer(SwitchingNetServerSocket switchingNetServerSocket)
  {
    switchingNetServerSockets.add(switchingNetServerSocket);
    

    if (switchingNetServerSockets.size() >= OPEN_SOCKETS_WARN_THRESHOLD)
    {

      String msg = "SwitchingNetLayer: " + switchingNetServerSockets.size() + " open server sockets - this could be a resource and memory leak";
      
      if (switchingNetServerSockets.size() == OPEN_SOCKETS_WARN_THRESHOLD)
      {

        LOG.warn(msg, new Throwable("use thread dump to localize potential resource and memory leak"));


      }
      else
      {


        LOG.warn(msg);
      }
    }
  }
  

  protected synchronized void removeFromLayer(SwitchingNetSocket switchingNetSocket)
  {
    switchingNetSockets.remove(switchingNetSocket);
  }
  

  protected synchronized void removeFromLayer(SwitchingNetServerSocket switchingNetServerSocket)
  {
    switchingNetServerSockets.remove(switchingNetServerSocket);
  }
}
