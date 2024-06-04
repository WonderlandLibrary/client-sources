package org.silvertunnel_ng.netlib.adapter.nameservice;

import java.net.InetAddress;
import java.net.UnknownHostException;
import sun.net.spi.nameservice.NameService;





































public class NameServiceNetlibJava6
  implements NameService
{
  private final NameServiceNetlibGenericAdapter nameServiceNetlibGenericAdapter;
  
  public NameServiceNetlibJava6(NameServiceNetlibGenericAdapter nameServiceNetlibGenericAdapter)
  {
    this.nameServiceNetlibGenericAdapter = nameServiceNetlibGenericAdapter;
  }
  



  public String getHostByAddr(byte[] ip)
    throws UnknownHostException
  {
    return nameServiceNetlibGenericAdapter.getHostByAddr(ip);
  }
  





  public InetAddress[] lookupAllHostAddr(String name)
    throws UnknownHostException
  {
    return nameServiceNetlibGenericAdapter.lookupAllHostAddrJava6(name);
  }
}
