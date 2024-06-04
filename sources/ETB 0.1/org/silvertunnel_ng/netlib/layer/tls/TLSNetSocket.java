package org.silvertunnel_ng.netlib.layer.tls;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.net.ssl.SSLSession;
import org.silvertunnel_ng.netlib.api.NetSocket;
































public class TLSNetSocket
  implements NetSocket
{
  protected NetSocket innerNetSocket;
  protected String lowerLayerSocketInfoMsg;
  protected SSLSession sslSession;
  
  protected TLSNetSocket(NetSocket innerNetSocket, SSLSession sslSession, String lowerLayerSocketInfoMsg)
  {
    this.innerNetSocket = innerNetSocket;
    this.sslSession = sslSession;
    this.lowerLayerSocketInfoMsg = lowerLayerSocketInfoMsg;
  }
  
  public SSLSession getSSLSession()
  {
    return sslSession;
  }
  

  public String toString()
  {
    return "TLSNetSocket(" + lowerLayerSocketInfoMsg + ")";
  }
  




  public void close()
    throws IOException
  {
    innerNetSocket.close();
  }
  
  public InputStream getInputStream()
    throws IOException
  {
    return innerNetSocket.getInputStream();
  }
  
  public OutputStream getOutputStream()
    throws IOException
  {
    return innerNetSocket.getOutputStream();
  }
}
