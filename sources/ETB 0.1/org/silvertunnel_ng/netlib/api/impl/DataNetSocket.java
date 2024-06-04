package org.silvertunnel_ng.netlib.api.impl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.silvertunnel_ng.netlib.api.NetSocket;

public abstract interface DataNetSocket
  extends NetSocket
{
  public abstract DataInputStream getDataInputStream()
    throws IOException;
  
  public abstract DataOutputStream getDataOutputStream()
    throws IOException;
  
  public abstract void close()
    throws IOException;
}
