package org.silvertunnel_ng.netlib.tool;

import java.io.PrintStream;
import org.silvertunnel_ng.netlib.adapter.socket.ExtendedSocket;
import org.silvertunnel_ng.netlib.adapter.socket.SocketGlobalUtil;
import org.silvertunnel_ng.netlib.api.NetFactory;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetLayerIDs;
import org.silvertunnel_ng.netlib.api.NetSocket;
import org.silvertunnel_ng.netlib.api.util.JavaVersion;
import org.silvertunnel_ng.netlib.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

























public class CheckNetConnectivity
{
  private static final Logger LOG = LoggerFactory.getLogger(CheckNetConnectivity.class);
  

  private static final long timeoutInMs = 5000L;
  

  public CheckNetConnectivity() {}
  

  public static void main(String[] argv)
  {
    if (argv.length > 0)
    {
      LOG.error("CheckNetConnectivity: no command line arguments are supported");
      System.exit(1);
      return;
    }
    

    executeCheck(true);
  }
  











  public static boolean executeCheck(boolean checkWithSocketGlobalUtil)
  {
    LOG.info("CheckNetConnectivity.executeCheck()");
    

    boolean test1Result = false;
    try
    {
      ExtendedSocket socket = new ExtendedSocket("httptest.silvertunnel-ng.org", 80);
      

      test1Result = HttpUtil.getInstance().executeSmallTest(socket, "test1", 5000L);

    }
    catch (Exception e)
    {
      LOG.warn("Exception while test1", e);
    }
    

    boolean test2Result = false;
    


    try
    {
      NetSocket netSocket = NetFactory.getInstance().getNetLayerById(NetLayerIDs.TCPIP).createNetSocket(null, null, HttpUtil.HTTPTEST_SERVER_NETADDRESS);
      
      test2Result = HttpUtil.getInstance().executeSmallTest(netSocket, "test2", 5000L);

    }
    catch (Exception e)
    {
      LOG.warn("Exception while test2", e);
    }
    
    boolean test3Result;
    boolean test4Result;
    if (checkWithSocketGlobalUtil)
    {

      SocketGlobalUtil.initSocketImplFactory();
      NetLayer netLayer = NetFactory.getInstance().getNetLayerById(NetLayerIDs.TCPIP);
      
      SocketGlobalUtil.setNetLayerUsedBySocketImplFactory(netLayer);
      

      boolean test3Result = false;
      try
      {
        ExtendedSocket socket = new ExtendedSocket("httptest.silvertunnel-ng.org", 80);
        

        test3Result = HttpUtil.getInstance().executeSmallTest(socket, "test3", 5000L);

      }
      catch (Exception e)
      {
        LOG.warn("Exception while test3", e);
      }
      

      boolean test4Result = false;
      


      try
      {
        NetSocket netSocket = NetFactory.getInstance().getNetLayerById(NetLayerIDs.TCPIP).createNetSocket(null, null, HttpUtil.HTTPTEST_SERVER_NETADDRESS);
        
        test4Result = HttpUtil.getInstance().executeSmallTest(netSocket, "test4", 5000L);

      }
      catch (Exception e)
      {
        LOG.warn("Exception while test4", e);
      }
      
    }
    else
    {
      test3Result = true;
      test4Result = true;
    }
    

    print("===================================================");
    print("=== Test Results ==================================");
    print("===================================================");
    print("JavaVersion: " + JavaVersion.getJavaVersion());
    print("test1 (Socket    before initSocketImplFactory): " + (test1Result ? "OK" : "FAILED"));
    
    print("test2 (NetSocket before initSocketImplFactory): " + (test2Result ? "OK" : "FAILED"));
    
    if (checkWithSocketGlobalUtil)
    {
      print("test3 (Socket    after initSocketImplFactory):  " + (test3Result ? "OK" : "FAILED"));
      
      print("test4 (NetSocket after initSocketImplFactory):  " + (test4Result ? "OK" : "FAILED"));
    }
    


    return (test1Result) && (test2Result) && (test3Result) && (test4Result);
  }
  
  private static void print(String s)
  {
    System.out.println(s);
  }
}
