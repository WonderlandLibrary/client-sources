package org.silvertunnel_ng.netlib.api;

import java.io.IOException;
import java.util.Map;

public abstract interface NetLayer
{
  public abstract NetSocket createNetSocket(Map<String, Object> paramMap, NetAddress paramNetAddress1, NetAddress paramNetAddress2)
    throws IOException;
  
  public abstract NetServerSocket createNetServerSocket(Map<String, Object> paramMap, NetAddress paramNetAddress)
    throws IOException;
  
  public abstract NetLayerStatus getStatus();
  
  public abstract void waitUntilReady();
  
  public abstract void clear()
    throws IOException;
  
  public abstract NetAddressNameService getNetAddressNameService();
}
