package org.silvertunnel_ng.netlib.api.impl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.silvertunnel_ng.netlib.api.NetSocket;




















public class DataNetSocketWrapper
  implements DataNetSocket
{
  NetSocket netSocket;
  private final DataInputStream dis;
  private final DataOutputStream dos;
  
  public DataNetSocketWrapper(NetSocket netSocket)
    throws IOException
  {
    InputStream is = netSocket.getInputStream();
    OutputStream os = netSocket.getOutputStream();
    
    dis = ((is instanceof DataInputStream) ? (DataInputStream)is : new DataInputStream(is));
    dos = ((os instanceof DataOutputStream) ? (DataOutputStream)os : new DataOutputStream(os));
    this.netSocket = netSocket;
  }
  
  public DataInputStream getDataInputStream()
    throws IOException
  {
    return dis;
  }
  
  public DataOutputStream getDataOutputStream()
    throws IOException
  {
    return dos;
  }
  
  public DataInputStream getInputStream()
    throws IOException
  {
    return dis;
  }
  
  public DataOutputStream getOutputStream()
    throws IOException
  {
    return dos;
  }
  
  public void close()
    throws IOException
  {
    netSocket.close();
  }
}
