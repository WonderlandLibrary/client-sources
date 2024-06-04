package org.silvertunnel_ng.netlib.adapter.nameservice;

import java.net.InetAddress;
import java.net.UnknownHostException;

abstract interface NameServiceNetlibGenericAdapter
{
  public abstract String getHostByAddr(byte[] paramArrayOfByte)
    throws UnknownHostException;
  
  public abstract InetAddress[] lookupAllHostAddrJava6(String paramString)
    throws UnknownHostException;
}
