package org.silvertunnel_ng.netlib.layer.tor.api;

import java.io.IOException;
import org.silvertunnel_ng.netlib.tool.ConvenientStreamWriter;

public abstract interface RouterExitPolicy
{
  public abstract RouterExitPolicy cloneReliable()
    throws RuntimeException;
  
  public abstract String toString();
  
  public abstract boolean isAccept();
  
  public abstract long getIp();
  
  public abstract long getNetmask();
  
  public abstract int getLoPort();
  
  public abstract int getHiPort();
  
  public abstract void save(ConvenientStreamWriter paramConvenientStreamWriter)
    throws IOException;
}
