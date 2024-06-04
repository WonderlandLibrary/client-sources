package org.silvertunnel_ng.netlib.adapter.url.impl.net.http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.net.UnknownHostException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetSocket;
import org.silvertunnel_ng.netlib.api.util.TcpipNetAddress;































public class NetworkClient
{
  protected Proxy proxy = Proxy.NO_PROXY;
  
  protected NetSocket serverSocket = null;
  

  public PrintStream serverOutput;
  
  public InputStream serverInput;
  
  protected static int defaultSoTimeout;
  
  protected static int defaultConnectTimeout;
  
  protected int readTimeout = -1;
  protected int connectTimeout = -1;
  
  protected static String encoding;
  
  protected NetLayer lowerNetLayer;
  

  static
  {
    int[] vals = { 0, 0 };
    final String[] encs = { null };
    
    AccessController.doPrivileged(new PrivilegedAction()
    {

      public Void run()
      {

        val$vals[0] = Integer.getInteger("sun.net.client.defaultReadTimeout", 0).intValue();
        val$vals[1] = Integer.getInteger("sun.net.client.defaultConnectTimeout", 0)
          .intValue();
        encs[0] = System.getProperty("file.encoding", "ISO8859_1");
        return null;
      }
    });
    if (vals[0] == 0)
    {
      defaultSoTimeout = -1;
    }
    else
    {
      defaultSoTimeout = vals[0];
    }
    
    if (vals[1] == 0)
    {
      defaultConnectTimeout = -1;
    }
    else
    {
      defaultConnectTimeout = vals[1];
    }
    
    encoding = encs[0];
    try
    {
      if (!isASCIISuperset(encoding))
      {
        encoding = "ISO8859_1";
      }
    }
    catch (Exception e)
    {
      encoding = "ISO8859_1";
    }
  }
  
















  private static boolean isASCIISuperset(String encoding)
    throws Exception
  {
    String chkS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-_.!~*'();/?:@&=+$,";
    


    byte[] chkB = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 45, 95, 46, 33, 126, 42, 39, 40, 41, 59, 47, 63, 58, 64, 38, 61, 43, 36, 44 };
    





    byte[] b = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-_.!~*'();/?:@&=+$,".getBytes(encoding);
    return Arrays.equals(b, chkB);
  }
  

  public void openServer(String server, int port)
    throws IOException, UnknownHostException
  {
    if (serverSocket != null)
    {
      closeServer();
    }
    serverSocket = doConnect(server, port);
    
    try
    {
      serverOutput = new PrintStream(new BufferedOutputStream(serverSocket.getOutputStream()), true, encoding);
    }
    catch (UnsupportedEncodingException e)
    {
      throw new InternalError(encoding + "encoding not found");
    }
    serverInput = new BufferedInputStream(serverSocket.getInputStream());
  }
  





  protected NetSocket doConnect(String server, int port)
    throws IOException, UnknownHostException
  {
    NetSocket s = lowerNetLayer.createNetSocket(null, null, new TcpipNetAddress(server, port));
    




















    return s;
  }
  






  public void closeServer()
    throws IOException
  {
    if (!serverIsOpen())
    {
      return;
    }
    serverSocket.close();
    serverSocket = null;
    serverInput = null;
    serverOutput = null;
  }
  

  public boolean serverIsOpen()
  {
    return serverSocket != null;
  }
  

  public NetworkClient(NetLayer lowerNetLayer, String host, int port)
    throws IOException
  {
    this.lowerNetLayer = lowerNetLayer;
    openServer(host, port);
  }
  
  public NetworkClient(NetLayer lowerNetLayer)
  {
    this.lowerNetLayer = lowerNetLayer;
  }
  
  public void setConnectTimeout(int timeout)
  {
    connectTimeout = timeout;
  }
  
  public int getConnectTimeout()
  {
    return connectTimeout;
  }
  
  public void setReadTimeout(int timeout)
  {
    if ((serverSocket != null) && (timeout >= 0)) {}
    






    readTimeout = timeout;
  }
  
  public int getReadTimeout()
  {
    return readTimeout;
  }
}
