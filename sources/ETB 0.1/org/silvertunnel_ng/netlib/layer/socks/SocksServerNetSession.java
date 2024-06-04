package org.silvertunnel_ng.netlib.layer.socks;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.silvertunnel_ng.netlib.api.NetAddress;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetSocket;
import org.silvertunnel_ng.netlib.api.impl.DataNetSocket;
import org.silvertunnel_ng.netlib.api.impl.DataNetSocketPair;
import org.silvertunnel_ng.netlib.api.impl.DataNetSocketUtil;
import org.silvertunnel_ng.netlib.api.impl.DataNetSocketWrapper;
import org.silvertunnel_ng.netlib.api.impl.InterconnectUtil;
import org.silvertunnel_ng.netlib.api.util.TcpipNetAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





























public class SocksServerNetSession
  implements Runnable
{
  private static final Logger LOG = LoggerFactory.getLogger(SocksServerNetSession.class);
  

  private final NetLayer lowerNetLayer;
  
  private DataNetSocket higherLayerSocketExported;
  
  private DataNetSocket higherLayerSocketInternallyUsed;
  
  private DataNetSocket lowerLayerSocket;
  
  private DataInputStream socksIn;
  
  private DataOutputStream socksOut;
  
  private DataInputStream lowerIn;
  
  private DataOutputStream lowerOut;
  
  static final int BUFFER_SIZE = 498;
  
  private static long id;
  

  public SocksServerNetSession(NetLayer lowerNetLayer, Map<String, Object> localProperties, NetAddress localAddress, NetAddress remoteAddress)
  {
    this.lowerNetLayer = lowerNetLayer;
  }
  
  public NetSocket createHigherLayerNetSocket() throws IOException
  {
    if (higherLayerSocketExported != null)
    {
      throw new IllegalStateException("cannot create multiple sockets for one session");
    }
    

    DataNetSocketPair dataNetSocketPair = DataNetSocketUtil.createDataNetSocketPair();
    higherLayerSocketExported = dataNetSocketPair.getSocket();
    higherLayerSocketInternallyUsed = dataNetSocketPair.getInvertedSocked();
    

    socksIn = higherLayerSocketInternallyUsed.getDataInputStream();
    socksOut = higherLayerSocketInternallyUsed.getDataOutputStream();
    

    new Thread(this, createUniqueThreadName()).start();
    
    return higherLayerSocketExported;
  }
  





  public void run()
  {
    try
    {
      byte[] version = new byte[1];
      socksIn.read(version, 0, 1);
      
      if (version[0] == 4)
      {
        processSocks4Connection();
      }
      else if (version[0] == 5)
      {
        processSocks5Connection();

      }
      else
      {
        byte[] answer = new byte[2];
        answer[0] = 0;
        answer[1] = 91;
        socksOut.write(answer);
        socksOut.flush();
        throw new Exception("only support for Socks-4(a)/5");
      }
    }
    catch (Exception e)
    {
      LOG.warn("got Exception", e);
    }
    finally
    {
      LOG.debug("{} closing down", Long.valueOf(id));
    }
  }
  



  public void close() {}
  


  private void processSocks5Connection()
  {
    LOG.debug("processSocks5Connection(): start");
    

    byte[] command = new byte[8];
    byte[] answer = new byte[2];
    
    try
    {
      answer[0] = 5;
      
      socksIn.read(command, 0, 1);
      if (command[0] <= 0)
      {

        answer[1] = -1;
        socksOut.write(answer);
        socksOut.flush();
        throw new Exception("number of supported methods must be >0");
      }
      byte[] methods = new byte[command[0]];
      socksIn.readFully(methods);
      
      boolean foundAnonymous = false;
      for (int i = 0; i < methods.length; i++)
      {
        foundAnonymous = (foundAnonymous) || (methods[i] == 0);
      }
      if (!foundAnonymous)
      {

        answer[1] = -1;
        socksOut.write(answer);
        socksOut.flush();
        throw new Exception("no accepted method listed by client");
      }
      
      answer[1] = 0;
      socksOut.write(answer);
      socksOut.flush();
      
      command = new byte[4];
      socksIn.readFully(command);
      if (command[0] != 5)
      {
        throw new Exception("why the f*** does the client change its version number?");
      }
      if (command[1] != 1)
      {
        throw new Exception("only CONNECT supported");
      }
      if (command[2] != 0)
      {
        throw new Exception("do not play around with reserved fields");
      }
      if ((command[3] != 1) && (command[3] != 3))
      {
        throw new Exception("only IPv4 and HOSTNAME supported");
      }
      
      String hostname = null;
      byte[] address;
      if (command[3] == 1)
      {

        byte[] address = new byte[4];
        socksIn.readFully(address);

      }
      else
      {
        byte[] lenInfo = new byte[1];
        socksIn.readFully(lenInfo);
        address = new byte[256 + lenInfo[0] & 0xFF];
        socksIn.readFully(address);
        hostname = new String(address);
      }
      
      byte[] port = new byte[2];
      socksIn.readFully(port);
      int intPort = ((port[0] & 0xFF) << 8) + (port[1] & 0xFF);
      
      NetAddress remoteAddress;
      NetAddress remoteAddress;
      if (hostname != null)
      {
        remoteAddress = new TcpipNetAddress(hostname, intPort);
      }
      else
      {
        remoteAddress = new TcpipNetAddress(address, intPort);
      }
      

      List<Byte> answerL = new ArrayList();
      
      answerL.add(Byte.valueOf((byte)5));
      answerL.add(Byte.valueOf((byte)0));
      answerL.add(Byte.valueOf((byte)0));
      answerL.add(Byte.valueOf(command[3]));
      if (hostname != null)
      {
        answerL.add(Byte.valueOf((byte)address.length));
      }
      for (int i = 0; i < address.length; i++)
      {
        answerL.add(Byte.valueOf(address[i]));
      }
      answerL.add(Byte.valueOf(port[0]));
      answerL.add(Byte.valueOf(port[1]));
      
      answer = new byte[answerL.size()];
      for (int i = 0; i < answer.length; i++)
      {
        answer[i] = ((Byte)answerL.get(i)).byteValue();
      }
      socksOut.write(answer);
      socksOut.flush();
      

      lowerLayerSocket = new DataNetSocketWrapper(lowerNetLayer.createNetSocket(null, null, remoteAddress));
      lowerIn = lowerLayerSocket.getDataInputStream();
      lowerOut = lowerLayerSocket.getDataOutputStream();
      

      InterconnectUtil.relay(socksIn, lowerOut, lowerIn, socksOut, 498);
      
      LOG.debug("processSocks5Connection(): end");
    }
    catch (Exception e) {
      e = 
      





        e;LOG.error("unexpected end", e);
    } finally {}
  }
  
  private void processSocks4Connection() {
    throw new UnsupportedOperationException("socks4 is currently not supported");
  }
  





































  protected static synchronized String createUniqueThreadName()
  {
    id += 1L;
    return SocksServerNetSession.class.getName() + id + "-" + Thread.currentThread().getName();
  }
}
