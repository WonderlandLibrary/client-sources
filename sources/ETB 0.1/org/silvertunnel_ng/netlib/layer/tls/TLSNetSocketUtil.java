package org.silvertunnel_ng.netlib.layer.tls;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import org.silvertunnel_ng.netlib.api.NetSocket;
import org.silvertunnel_ng.netlib.api.impl.NetSocket2Socket;
import org.silvertunnel_ng.netlib.api.impl.Socket2NetSocket;
import org.silvertunnel_ng.netlib.api.util.JavaVersion;
import org.silvertunnel_ng.netlib.api.util.TcpipNetAddress;
import org.silvertunnel_ng.netlib.layer.tls.android.LocalProxySocket;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





















public class TLSNetSocketUtil
{
  private static final Logger LOG = LoggerFactory.getLogger(TLSNetSocketUtil.class);
  







  public TLSNetSocketUtil() {}
  







  public static NetSocket createTLSSocket(NetSocket lowerLayerNetSocket, TcpipNetAddress remoteAddress, boolean autoClose, String[] enabledCipherSuites, KeyManager[] keyManagers, TrustManager[] trustManagers)
    throws IOException
  {
    Socket lowerLayerSocket = new NetSocket2Socket(lowerLayerNetSocket);
    

    if (JavaVersion.getJavaVersion() == JavaVersion.ANDROID) {
      try {
        lowerLayerSocket = new LocalProxySocket(lowerLayerSocket);
      } catch (TorException e) {
        LOG.debug("Could not create LocalProxySocket which is needed for Android!", e);
      }
    }
    
    SSLContext context = null;
    try {
      context = SSLContext.getInstance("TLS");
      context.init(keyManagers, trustManagers, null);
    } catch (NoSuchAlgorithmException e) {
      IOException ioe = new IOException();
      ioe.initCause(e);
      LOG.debug("Got Exception during SSLContext init", e);
      throw ioe;
    } catch (KeyManagementException e) {
      IOException ioe = new IOException();
      ioe.initCause(e);
      LOG.debug("Got Exception during SSLContext init", e);
      throw ioe;
    }
    
    SSLSocketFactory f = context.getSocketFactory();
    


    String hostname = remoteAddress != null ? remoteAddress.getHostname() : null;
    int port = remoteAddress != null ? remoteAddress.getPort() : 0;
    SSLSocket resultSocket = (SSLSocket)f.createSocket(lowerLayerSocket, hostname, port, autoClose);
    


    if (LOG.isDebugEnabled()) {
      LOG.debug("default enabledCipherSuites=" + 
        Arrays.toString(resultSocket.getEnabledCipherSuites()));
    }
    if (enabledCipherSuites != null) {
      resultSocket.setEnabledCipherSuites(enabledCipherSuites);
      if (LOG.isDebugEnabled()) {
        LOG.debug("set enabledCipherSuites=" + 
          Arrays.toString(enabledCipherSuites));
      }
    }
    
    if (!resultSocket.isConnected()) {
      resultSocket.connect(new InetSocketAddress(hostname, port));
    }
    try {
      resultSocket.startHandshake();
    } catch (Exception e) {
      LOG.error("Handshake : ", e);
    }
    
    return new TLSNetSocket(new Socket2NetSocket(resultSocket), resultSocket.getSession(), "" + lowerLayerNetSocket);
  }
}
