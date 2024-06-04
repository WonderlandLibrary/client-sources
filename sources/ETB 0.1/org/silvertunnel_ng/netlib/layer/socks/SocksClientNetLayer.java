package org.silvertunnel_ng.netlib.layer.socks;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import org.silvertunnel_ng.netlib.api.NetAddress;
import org.silvertunnel_ng.netlib.api.NetAddressNameService;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetLayerStatus;
import org.silvertunnel_ng.netlib.api.NetServerSocket;
import org.silvertunnel_ng.netlib.api.NetSocket;
import org.silvertunnel_ng.netlib.api.impl.DataNetSocket;
import org.silvertunnel_ng.netlib.api.impl.DataNetSocketPair;
import org.silvertunnel_ng.netlib.api.impl.DataNetSocketUtil;
import org.silvertunnel_ng.netlib.api.impl.DataNetSocketWrapper;
import org.silvertunnel_ng.netlib.api.impl.InterconnectUtil;
import org.silvertunnel_ng.netlib.api.util.TcpipNetAddress;
import org.silvertunnel_ng.netlib.util.ByteArrayUtil;

































public class SocksClientNetLayer
  implements NetLayer
{
  private static final int BUFFER_SIZE = 1024;
  private final NetLayer lowerNetLayer;
  
  public SocksClientNetLayer(NetLayer lowerNetLayer)
  {
    this.lowerNetLayer = lowerNetLayer;
  }
  












  public NetSocket createNetSocket(Map<String, Object> localProperties, NetAddress localAddress, NetAddress remoteAddress)
    throws IOException
  {
    DataNetSocket socksServerSocket = new DataNetSocketWrapper(lowerNetLayer.createNetSocket((Map)null, (NetAddress)null, (NetAddress)null));
    
    DataOutputStream socksOut = socksServerSocket.getDataOutputStream();
    DataInputStream socksIn = socksServerSocket.getDataInputStream();
    



    byte[] request1 = ByteArrayUtil.getByteArray(new int[] { 5, 1, 0 });
    byte[] expectedResponse1 = ByteArrayUtil.getByteArray(new int[] { 5, 0 });
    socksOut.write(request1);
    socksOut.flush();
    byte[] response1 = new byte[expectedResponse1.length];
    socksIn.readFully(response1);
    if (!Arrays.equals(expectedResponse1, response1))
    {
      throw new IOException("could not create connection: socks negotiation failed");
    }
    



    if (remoteAddress == null)
    {
      throw new IllegalArgumentException("invalid remoteAddress=null");
    }
    if (!(remoteAddress instanceof TcpipNetAddress))
    {
      throw new IllegalArgumentException("not of type TcpipNetAddress: remoteAddress=" + remoteAddress);
    }
    



    TcpipNetAddress ra = (TcpipNetAddress)remoteAddress;
    int i = 0;
    byte[] request2; int j; if (ra.getIpaddress() != null)
    {

      int addressLen = ra.getIpaddress().length;
      request2 = new byte[4 + addressLen + 2];
      request2[(i++)] = 5;
      request2[(i++)] = 1;
      request2[(i++)] = 0;
      request2[(i++)] = ((byte)(addressLen == 4 ? 1 : 4));
      for (j = 0; j < addressLen;)
      {
        request2[(i++)] = ra.getIpaddress()[(j++)]; } } else { byte[] request2;
      char[] name;
      int j;
      if (ra.getHostname() != null)
      {

        int nameLen = ra.getHostname().length();
        if (nameLen > 255)
        {
          throw new IllegalArgumentException("name too long in remoteAddress=" + remoteAddress);
        }
        request2 = new byte[5 + nameLen + 2];
        request2[(i++)] = 5;
        request2[(i++)] = 1;
        request2[(i++)] = 0;
        request2[(i++)] = 3;
        request2[(i++)] = ((byte)nameLen);
        name = ra.getHostname().toCharArray();
        for (j = 0; j < nameLen;)
        {
          request2[(i++)] = ((byte)name[(j++)]);
        }
      }
      else
      {
        throw new IllegalArgumentException("invalid remoteAddress=" + remoteAddress);
      } }
    byte[] request2;
    request2[(i++)] = ((byte)(ra.getPort() / 256));
    request2[(i++)] = ((byte)(ra.getPort() % 256));
    
    byte[] expectedResponse2 = new byte[request2.length];
    System.arraycopy(request2, 0, expectedResponse2, 0, request2.length);
    expectedResponse2[1] = 0;
    
    socksOut.write(request2);
    socksOut.flush();
    
    byte[] response2 = new byte[5];
    socksIn.readFully(response2);
    if (response2[1] != 0)
    {
      throw new IOException("could not create connection: socks connection setup failed with response=" + response2[1] + " for remoteAddress=" + remoteAddress);
    }
    
    int remainingByteLen;
    
    int remainingByteLen;
    int remainingByteLen;
    switch (response2[3])
    {
    case 1: 
      remainingByteLen = 5;
      break;
    case 4: 
      remainingByteLen = 17;
      break;
    case 3: 
      remainingByteLen = 1 + response2[4] + 2 - 1;
      break;
    case 2: default: 
      throw new IOException("could not create connection: socks connection setup failed with response address type=" + response2[3] + " for remoteAddress=" + remoteAddress);
    }
    
    
    int remainingByteLen;
    
    socksIn.readFully(new byte[remainingByteLen]);
    













    DataNetSocketPair dataNetSocketPair = DataNetSocketUtil.createDataNetSocketPair();
    DataNetSocket higherLayerSocketExported = dataNetSocketPair.getSocket();
    DataNetSocket higherLayerSocketInternallyUsed = dataNetSocketPair.getInvertedSocked();
    DataInputStream higherIn = higherLayerSocketInternallyUsed.getDataInputStream();
    DataOutputStream higherOut = higherLayerSocketInternallyUsed.getDataOutputStream();
    InterconnectUtil.relayNonBlocking(higherIn, socksOut, socksIn, higherOut, 1024);
    

    return higherLayerSocketExported;
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
    throw new UnsupportedOperationException();
  }
}
