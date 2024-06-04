package org.silvertunnel_ng.netlib.api;

import java.io.IOException;

public abstract interface NetServerSocket
{
  public abstract NetSocket accept()
    throws IOException;
  
  public abstract void close()
    throws IOException;
}
