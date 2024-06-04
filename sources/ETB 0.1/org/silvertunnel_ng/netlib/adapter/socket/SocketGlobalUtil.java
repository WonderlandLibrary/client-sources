package org.silvertunnel_ng.netlib.adapter.socket;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.Socket;
import java.net.SocketImpl;
import org.silvertunnel_ng.netlib.api.NetFactory;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetLayerIDs;
import org.silvertunnel_ng.netlib.api.util.JavaVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

















































public class SocketGlobalUtil
{
  private static final Logger LOG = LoggerFactory.getLogger(SocketGlobalUtil.class);
  



  private static NetlibSocketImplFactory netlibSocketImplFactory;
  




  public SocketGlobalUtil() {}
  



  public static synchronized void initSocketImplFactory()
  {
    if (netlibSocketImplFactory == null) {
      try
      {
        NetLayer defaultNetLayer = NetFactory.getInstance().getNetLayerById(NetLayerIDs.NOP);
        netlibSocketImplFactory = new NetlibSocketImplFactory(defaultNetLayer);
        
        Socket.setSocketImplFactory(netlibSocketImplFactory);
      } catch (IOException e) {
        LOG.warn("Socket.setSocketImplFactory() was already called before, but not from SocketUtil, i.e. maybe the wrong factory is set");
      }
    }
  }
  









  public static synchronized void setNetLayerUsedBySocketImplFactory(NetLayer netLayer)
    throws IllegalStateException
  {
    if (netlibSocketImplFactory == null) {
      throw new IllegalStateException("initSocketImplFactory() must be called first (but was not)");
    }
    


    netlibSocketImplFactory.setNetLayer(netLayer);
  }
  









  public static ExtendedSocket createOriginalSocket()
    throws RuntimeException
  {
    LOG.debug("createOriginalSocket() called here:", new Throwable("stacktrace for debugging - not an error"));
    

    if (netlibSocketImplFactory == null)
    {

      return new ExtendedSocket();
    }
    





    try
    {
      Class clazz = Class.forName("java.net.SocksSocketImpl");
      
      Constructor constructor = clazz.getDeclaredConstructor(new Class[0]);
      
      constructor.setAccessible(true);
      SocketImpl impl = (SocketImpl)constructor.newInstance(new Object[0]);
      return new ExtendedSocket(impl);
    }
    catch (Throwable t1)
    {
      String msg = "createOriginalSocket()#1st attempt: could not create a Socket for Java Version: " + JavaVersion.getJavaVersion();
      LOG.error(msg, t1);
      


      try
      {
        return new ExtendedSocket(new PatchedProxy());
      }
      catch (Throwable t2)
      {
        msg = "createOriginalSocket()#2nd attempt: could not create a Socket for Java Version: " + JavaVersion.getJavaVersion();
        LOG.error(msg + ", " + t2, t2);
        
        throw new RuntimeException(msg + ", " + t2);
      }
    }
  }
}
