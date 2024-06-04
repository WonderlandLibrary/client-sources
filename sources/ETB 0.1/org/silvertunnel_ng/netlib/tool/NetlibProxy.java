package org.silvertunnel_ng.netlib.tool;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.silvertunnel_ng.netlib.api.NetFactory;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetLayerIDs;
import org.silvertunnel_ng.netlib.api.NetServerSocket;
import org.silvertunnel_ng.netlib.api.NetSocket;
import org.silvertunnel_ng.netlib.api.util.TcpipNetAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


























































public class NetlibProxy
{
  private static final Logger LOG = LoggerFactory.getLogger(NetlibProxy.class);
  
  private static boolean startedFromCommandLine = true;
  private static volatile boolean started = false;
  private static volatile boolean stopped = false;
  
  private static NetServerSocket netServerSocket;
  

  public NetlibProxy() {}
  

  public static void start(String[] argv)
  {
    startedFromCommandLine = false;
    main(argv);
  }
  





  public static void main(String[] argv)
  {
    stopped = false;
    started = false;
    if (argv.length < 1)
    {
      LOG.error("NetProxy: insufficient number of command line arguments: you must specify [listen_port] and [net_layer_id] at least");
      System.exit(1);
      return;
    }
    


    try
    {
      String listenAddressPortArg = argv[0];
      TcpipNetAddress localListenAddress = new TcpipNetAddress(listenAddressPortArg);
      




      netServerSocket = NetFactory.getInstance().getNetLayerById(NetLayerIDs.TCPIP).createNetServerSocket(null, localListenAddress);

    }
    catch (Exception e)
    {
      LOG.error("NetlibProxy: could not open server port", e);
      if (startedFromCommandLine)
      {
        LOG.error("System.exit(2)");
        System.exit(2);
      }
      return;
    }
    started = true;
    

    NetLayerIDs lowerLayerNetLayerId = NetLayerIDs.getByValue(argv[1]);
    

    try
    {
      while (!stopped)
      {
        NetSocket upperLayerNetSocket = netServerSocket.accept();
        new NetProxySingleConnectionThread(upperLayerNetSocket, lowerLayerNetLayerId)
          .start();
      }
    }
    catch (Exception e)
    {
      started = false;
      String msg = "NetlibProxy: server-wide problem while running";
      if (stopped)
      {
        LOG.info("NetlibProxy: server-wide problem while running");
      }
      else
      {
        LOG.error("NetlibProxy: server-wide problem while running", e);
      }
      if (startedFromCommandLine)
      {
        LOG.error("System.exit(3)");
        System.exit(3);
      }
      return;
    }
  }
  



  public static void stop()
  {
    LOG.info("NetlibProxy: will be stopped now");
    stopped = true;
    started = false;
    

    try
    {
      netServerSocket.close();
    }
    catch (IOException e)
    {
      LOG.warn("Exception while closing the server socket", e);
    }
  }
  






  public static boolean isStarted()
  {
    return started;
  }
  



  public static void testConnection()
    throws Exception
  {
    LOG.info("(client) connect client to server");
    TcpipNetAddress remoteAddress = new TcpipNetAddress("www.google.de", 80);
    


    NetSocket client = NetFactory.getInstance().getNetLayerById(NetLayerIDs.TCPIP).createNetSocket(null, null, remoteAddress);
    
    LOG.info("(client) send data client->server");
    client.getOutputStream().write("GET /\n\n".getBytes());
    client.getOutputStream().flush();
    
    LOG.info("(client) read data from server");
    byte[] dataReceivedByClient = readDataFromInputStream(100, client
      .getInputStream());
    
    LOG.info("(client) finish connection");
    client.close();
  }
  
  public static byte[] readDataFromInputStream(int maxResultSize, InputStream is)
    throws IOException
  {
    byte[] tempResultBuffer = new byte[maxResultSize];
    
    int len = 0;
    

    while (len < tempResultBuffer.length)
    {



      int lastLen = is.read(tempResultBuffer, len, tempResultBuffer.length - len);
      
      if (lastLen < 0) {
        break;
      }
      

      len += lastLen;
    }
    


    byte[] result = new byte[len];
    System.arraycopy(tempResultBuffer, 0, result, 0, len);
    
    return result;
  }
}
