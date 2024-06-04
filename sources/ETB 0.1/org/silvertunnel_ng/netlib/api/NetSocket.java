package org.silvertunnel_ng.netlib.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract interface NetSocket
{
  public abstract InputStream getInputStream()
    throws IOException;
  
  public abstract OutputStream getOutputStream()
    throws IOException;
  
  public abstract void close()
    throws IOException;
}
