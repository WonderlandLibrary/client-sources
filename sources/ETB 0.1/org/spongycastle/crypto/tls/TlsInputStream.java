package org.spongycastle.crypto.tls;

import java.io.IOException;
import java.io.InputStream;




class TlsInputStream
  extends InputStream
{
  private byte[] buf = new byte[1];
  private TlsProtocol handler = null;
  
  TlsInputStream(TlsProtocol handler)
  {
    this.handler = handler;
  }
  
  public int available()
    throws IOException
  {
    return handler.applicationDataAvailable();
  }
  
  public int read(byte[] buf, int offset, int len)
    throws IOException
  {
    return handler.readApplicationData(buf, offset, len);
  }
  
  public int read()
    throws IOException
  {
    if (read(buf) < 0)
    {
      return -1;
    }
    return buf[0] & 0xFF;
  }
  
  public void close()
    throws IOException
  {
    handler.close();
  }
}
