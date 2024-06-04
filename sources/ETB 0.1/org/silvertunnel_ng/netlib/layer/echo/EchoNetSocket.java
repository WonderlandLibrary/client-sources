package org.silvertunnel_ng.netlib.layer.echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import org.silvertunnel_ng.netlib.api.NetSocket;

























public class EchoNetSocket
  implements NetSocket
{
  final PipedInputStream in;
  final PipedOutputStream out;
  
  public EchoNetSocket()
    throws IOException
  {
    in = new PipedInputStream();
    out = new PipedOutputStream(in);
  }
  
  public void close()
    throws IOException
  {
    in.close();
    out.close();
  }
  
  public InputStream getInputStream()
    throws IOException
  {
    return in;
  }
  
  public OutputStream getOutputStream()
    throws IOException
  {
    return out;
  }
}
