package org.silvertunnel_ng.netlib.layer.tor.circuit;

import java.io.IOException;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;

public abstract interface HiddenServicePortInstance
{
  public abstract boolean isOpen();
  
  public abstract int getPort();
  
  public abstract void createStream(Circuit paramCircuit, int paramInt)
    throws TorException, IOException;
  
  public abstract HiddenServiceInstance getHiddenServiceInstance();
  
  public abstract void setHiddenServiceInstance(HiddenServiceInstance paramHiddenServiceInstance);
}
