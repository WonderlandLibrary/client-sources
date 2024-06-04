package org.silvertunnel_ng.netlib.adapter.socket;

import java.net.InetSocketAddress;

























class PatchedSocketAddress
  extends InetSocketAddress
{
  private static final long serialVersionUID = 1L;
  
  public PatchedSocketAddress()
  {
    super("localhost", 0);
  }
}
