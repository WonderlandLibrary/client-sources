package org.silvertunnel_ng.netlib.layer.mock;

import java.io.IOException;
import java.util.Map;
import org.silvertunnel_ng.netlib.api.NetAddress;
import org.silvertunnel_ng.netlib.api.NetSocket;


























public class MockNetSession
{
  private final Map<String, Object> providedLocalProperties;
  private final NetAddress providedRemoteAddress;
  private final MockNetSocket preparedHigherLayerSocket;
  private boolean isHigherLayerSocketCreated = false;
  


  public MockNetSession(Map<String, Object> localProperties, NetAddress localAddress, NetAddress remoteAddress, MockNetSocket preparedHigherLayerSocket)
  {
    providedLocalProperties = localProperties;
    providedRemoteAddress = remoteAddress;
    this.preparedHigherLayerSocket = preparedHigherLayerSocket;
  }
  
  public NetSocket createHigherLayerNetSocket() throws IOException
  {
    if (isHigherLayerSocketCreated)
    {
      throw new IllegalStateException("cannot create multiple sockets for one session");
    }
    

    isHigherLayerSocketCreated = true;
    return preparedHigherLayerSocket;
  }
  




  public MockNetSocket getHigherLayerNetSocket()
  {
    if (isHigherLayerSocketCreated)
    {
      return preparedHigherLayerSocket;
    }
    

    return null;
  }
  





  public Map<String, Object> getProvidedLocalProperties()
  {
    return providedLocalProperties;
  }
  
  public NetAddress getProvidedRemoteAddress()
  {
    return providedRemoteAddress;
  }
}
