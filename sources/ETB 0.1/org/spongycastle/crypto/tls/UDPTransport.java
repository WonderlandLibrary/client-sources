package org.spongycastle.crypto.tls;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPTransport
  implements DatagramTransport
{
  protected static final int MIN_IP_OVERHEAD = 20;
  protected static final int MAX_IP_OVERHEAD = 84;
  protected static final int UDP_OVERHEAD = 8;
  protected final DatagramSocket socket;
  protected final int receiveLimit;
  protected final int sendLimit;
  
  public UDPTransport(DatagramSocket socket, int mtu)
    throws IOException
  {
    if ((!socket.isBound()) || (!socket.isConnected()))
    {
      throw new IllegalArgumentException("'socket' must be bound and connected");
    }
    
    this.socket = socket;
    


    receiveLimit = (mtu - 20 - 8);
    sendLimit = (mtu - 84 - 8);
  }
  
  public int getReceiveLimit()
  {
    return receiveLimit;
  }
  

  public int getSendLimit()
  {
    return sendLimit;
  }
  
  public int receive(byte[] buf, int off, int len, int waitMillis)
    throws IOException
  {
    socket.setSoTimeout(waitMillis);
    DatagramPacket packet = new DatagramPacket(buf, off, len);
    socket.receive(packet);
    return packet.getLength();
  }
  
  public void send(byte[] buf, int off, int len)
    throws IOException
  {
    if (len > getSendLimit())
    {





      throw new TlsFatalAlert((short)80);
    }
    
    DatagramPacket packet = new DatagramPacket(buf, off, len);
    socket.send(packet);
  }
  
  public void close()
    throws IOException
  {
    socket.close();
  }
}
