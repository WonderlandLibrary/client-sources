package org.silvertunnel_ng.netlib.api;

import java.net.UnknownHostException;

public abstract interface NetAddressNameService
{
  public abstract NetAddress[] getAddressesByName(String paramString)
    throws UnknownHostException;
  
  public abstract String[] getNamesByAddress(NetAddress paramNetAddress)
    throws UnknownHostException;
}
