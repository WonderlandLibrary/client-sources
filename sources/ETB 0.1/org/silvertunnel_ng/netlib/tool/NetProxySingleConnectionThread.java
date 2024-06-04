package org.silvertunnel_ng.netlib.tool;

import org.silvertunnel_ng.netlib.api.NetAddress;
import org.silvertunnel_ng.netlib.api.NetFactory;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetLayerIDs;
import org.silvertunnel_ng.netlib.api.NetSocket;
import org.silvertunnel_ng.netlib.api.impl.InterconnectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;








































class NetProxySingleConnectionThread
  extends Thread
{
  private static final Logger LOG = LoggerFactory.getLogger(NetProxySingleConnectionThread.class);
  


  private final NetSocket upperLayerNetSocket;
  


  private final NetLayerIDs lowerNetLayerId;
  


  private static long id;
  


  public NetProxySingleConnectionThread(NetSocket upperLayerNetSocket, NetLayerIDs lowerNetLayerId)
  {
    super(createUniqueThreadName());
    this.upperLayerNetSocket = upperLayerNetSocket;
    this.lowerNetLayerId = lowerNetLayerId;
  }
  


  public void run()
  {
    try
    {
      NetAddress remoteAddress = null;
      

      NetSocket lowerLayerNetSocket = NetFactory.getInstance().getNetLayerById(lowerNetLayerId).createNetSocket(null, null, remoteAddress);
      

      InterconnectUtil.relay(upperLayerNetSocket, lowerLayerNetSocket);
    }
    catch (Exception e)
    {
      LOG.warn("connection abborted", e);
    }
  }
  



  protected static synchronized String createUniqueThreadName()
  {
    id += 1L;
    return NetProxySingleConnectionThread.class.getName() + id;
  }
}
