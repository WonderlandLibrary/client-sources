package org.silvertunnel_ng.netlib.layer.tls;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.KeyManager;
import javax.net.ssl.TrustManager;
import org.silvertunnel_ng.netlib.api.NetAddress;
import org.silvertunnel_ng.netlib.api.NetAddressNameService;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetLayerStatus;
import org.silvertunnel_ng.netlib.api.NetServerSocket;
import org.silvertunnel_ng.netlib.api.NetSocket;
import org.silvertunnel_ng.netlib.api.util.TcpipNetAddress;
import org.silvertunnel_ng.netlib.util.PropertiesUtil;






























public class TLSNetLayer
  implements NetLayer
{
  public static final String ENABLES_CIPHER_SUITES = "TLSNetLayer.enabledCipherSuites";
  public static final String KEY_MANAGERS = "TLSNetLayer.KEYManagers";
  public static final String TRUST_MANAGERS = "TLSNetLayer.TrustManagers";
  private final NetLayer lowerNetLayer;
  
  public TLSNetLayer(NetLayer lowerNetLayer)
  {
    this.lowerNetLayer = lowerNetLayer;
  }
  





  public NetSocket createNetSocket(Map<String, Object> localProperties, NetAddress localAddress, NetAddress remoteAddress)
    throws IOException
  {
    Map<String, Object> lowerLayerProperties = null;
    if (localProperties != null)
    {
      lowerLayerProperties = new HashMap(localProperties);
      lowerLayerProperties.remove("TLSNetLayer.enabledCipherSuites");
      lowerLayerProperties.remove("TLSNetLayer.KEYManagers");
      lowerLayerProperties.remove("TLSNetLayer.TrustManagers");
    }
    NetSocket lowerLayerSocket = lowerNetLayer.createNetSocket(lowerLayerProperties, localAddress, remoteAddress);
    


    String[] enabledCipherSuites = PropertiesUtil.getAsStringArray(localProperties, "TLSNetLayer.enabledCipherSuites", null);
    
    Object keyManagersObj = PropertiesUtil.getAsObject(localProperties, "TLSNetLayer.KEYManagers", null);
    KeyManager[] keyManagers = null;
    if ((keyManagersObj != null) && ((keyManagersObj instanceof KeyManager[])))
    {
      keyManagers = (KeyManager[])keyManagersObj;
    }
    
    Object trustManagersObj = PropertiesUtil.getAsObject(localProperties, "TLSNetLayer.TrustManagers", null);
    TrustManager[] trustManagers = null;
    if ((trustManagersObj != null) && ((trustManagersObj instanceof TrustManager[])))
    {
      trustManagers = (TrustManager[])trustManagersObj;
    }
    

    TcpipNetAddress tcpidRemoteAddress = null;
    if ((remoteAddress != null) && ((remoteAddress instanceof TcpipNetAddress)))
    {
      tcpidRemoteAddress = (TcpipNetAddress)remoteAddress;
    }
    NetSocket higherLayerSocket = TLSNetSocketUtil.createTLSSocket(lowerLayerSocket, tcpidRemoteAddress, true, enabledCipherSuites, keyManagers, trustManagers);
    






    return higherLayerSocket;
  }
  


  public NetServerSocket createNetServerSocket(Map<String, Object> properties, NetAddress localListenAddress)
  {
    throw new UnsupportedOperationException();
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
}
