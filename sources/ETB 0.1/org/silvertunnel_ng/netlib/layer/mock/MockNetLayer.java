package org.silvertunnel_ng.netlib.layer.mock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.silvertunnel_ng.netlib.api.NetAddress;
import org.silvertunnel_ng.netlib.api.NetAddressNameService;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetLayerStatus;
import org.silvertunnel_ng.netlib.api.NetServerSocket;
import org.silvertunnel_ng.netlib.api.NetSocket;
import org.silvertunnel_ng.netlib.nameservice.mock.NopNetAddressNameService;
























public class MockNetLayer
  implements NetLayer
{
  private final List<MockNetSession> sessionHistory = new ArrayList();
  



  private byte[] response;
  



  private boolean allowMultipleSessions;
  



  private List<byte[]> responses;
  


  private Map<NetAddress, byte[]> responsePerDestinationAddress;
  


  private boolean allowMultipleSessionsForOneAddress;
  


  private final long waitAtEndOfResponseBeforeClosingMs;
  



  public MockNetLayer(byte[] response, boolean allowMultipleSessions, long waitAtEndOfResponseBeforeClosingMs)
  {
    this.response = response;
    this.allowMultipleSessions = allowMultipleSessions;
    this.waitAtEndOfResponseBeforeClosingMs = waitAtEndOfResponseBeforeClosingMs;
  }
  











  public MockNetLayer(List<byte[]> responses, long waitAtEndOfResponseBeforeClosingMs)
  {
    this.responses = responses;
    this.waitAtEndOfResponseBeforeClosingMs = waitAtEndOfResponseBeforeClosingMs;
  }
  

















  public MockNetLayer(Map<NetAddress, byte[]> responsePerDestinationAddress, boolean allowMultipleSessionsForOneAddress, long waitAtEndOfResponseBeforeClosingMs)
  {
    this.responsePerDestinationAddress = responsePerDestinationAddress;
    this.allowMultipleSessionsForOneAddress = allowMultipleSessionsForOneAddress;
    this.waitAtEndOfResponseBeforeClosingMs = waitAtEndOfResponseBeforeClosingMs;
  }
  






  public List<MockNetSession> getSessionHistory()
  {
    return sessionHistory;
  }
  






  public MockNetSession getFirstSessionHistory()
  {
    if (sessionHistory.size() == 0)
    {
      return null;
    }
    

    return (MockNetSession)sessionHistory.get(0);
  }
  





  public synchronized NetSocket createNetSocket(Map<String, Object> localProperties, NetAddress localAddress, NetAddress remoteAddress)
    throws IOException
  {
    if (this.response != null)
    {

      MockNetSocket preparedHigherLayerSocket = new MockNetSocket(this.response, waitAtEndOfResponseBeforeClosingMs);
      
      if (!allowMultipleSessions)
      {

        this.response = null;
      }
      
    }
    else if ((responses != null) && (responses.size() >= 1))
    {

      MockNetSocket preparedHigherLayerSocket = new MockNetSocket((byte[])responses.get(0), waitAtEndOfResponseBeforeClosingMs);
      

      responses.remove(0);
    } else {
      MockNetSocket preparedHigherLayerSocket;
      if ((responsePerDestinationAddress != null) && 
        (responsePerDestinationAddress.size() >= 1))
      {


        byte[] response = (byte[])responsePerDestinationAddress.get(remoteAddress);
        if (response == null)
        {
          throw new IOException("connection could not be established to remoteAddress=" + remoteAddress);
        }
        

        if (!allowMultipleSessionsForOneAddress)
        {

          responsePerDestinationAddress.remove(remoteAddress);
        }
        preparedHigherLayerSocket = new MockNetSocket(response, waitAtEndOfResponseBeforeClosingMs);

      }
      else
      {

        throw new IOException("no more Sockets allowed");
      }
    }
    MockNetSocket preparedHigherLayerSocket;
    MockNetSession session = new MockNetSession(localProperties, localAddress, remoteAddress, preparedHigherLayerSocket);
    
    sessionHistory.add(session);
    return session.createHigherLayerNetSocket();
  }
  



  public NetServerSocket createNetServerSocket(Map<String, Object> properties, NetAddress localListenAddress)
  {
    throw new UnsupportedOperationException();
  }
  


  public NetLayerStatus getStatus()
  {
    return NetLayerStatus.READY;
  }
  




  public void waitUntilReady() {}
  



  public void clear()
    throws IOException
  {}
  



  public NetAddressNameService getNetAddressNameService()
  {
    return NopNetAddressNameService.getInstance();
  }
}
