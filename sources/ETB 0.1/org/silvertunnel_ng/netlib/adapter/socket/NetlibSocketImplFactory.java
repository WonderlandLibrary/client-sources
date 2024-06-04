package org.silvertunnel_ng.netlib.adapter.socket;

import java.net.SocketImpl;
import java.net.SocketImplFactory;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.impl.NetSocket2SocketImpl;































class NetlibSocketImplFactory
  implements SocketImplFactory
{
  private NetLayer netLayer;
  
  public NetlibSocketImplFactory(NetLayer netLayer)
  {
    this.netLayer = netLayer;
  }
  







  public synchronized void setNetLayer(NetLayer netLayer)
  {
    this.netLayer = netLayer;
  }
  

  public synchronized SocketImpl createSocketImpl()
  {
    if (netLayer != null)
    {
      return new NetSocket2SocketImpl(netLayer);
    }
    


    return new InvalidSocketImpl();
  }
}
