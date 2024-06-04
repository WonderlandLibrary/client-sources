package org.silvertunnel_ng.netlib.layer.mock;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.silvertunnel_ng.netlib.api.NetSocket;































public class MockNetSocket
  implements NetSocket
{
  private final ByteArrayInputStream in;
  private final ByteArrayOutputStream out;
  
  public MockNetSocket(byte[] response, long waitAtEndOfResponseBeforeClosingMs)
  {
    in = new MockByteArrayInputStream(response, waitAtEndOfResponseBeforeClosingMs);
    
    out = new ByteArrayOutputStream();
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
  
  public ByteArrayInputStream getByteArrayInputStream() throws IOException
  {
    return in;
  }
  
  public ByteArrayOutputStream getByteArrayOutputStream() throws IOException
  {
    return out;
  }
}
