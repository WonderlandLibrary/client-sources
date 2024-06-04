package org.silvertunnel_ng.netlib.adapter.socket;

import java.net.InetAddress;
import java.net.InetSocketAddress;





















class PatchedInetSocketAddress
  extends InetSocketAddress
{
  private static final long serialVersionUID = 1L;
  
  public PatchedInetSocketAddress(InetAddress addr, int port)
  {
    super(addr, port);
  }
}
