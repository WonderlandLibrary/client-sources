package org.silvertunnel_ng.netlib.layer.tor.circuit;

import java.net.BindException;
import java.util.HashMap;
import java.util.Map;
import org.silvertunnel_ng.netlib.layer.tor.hiddenservice.HiddenServiceProperties;

























public class HiddenServiceInstance
{
  private final Map<Integer, HiddenServicePortInstance> listenPortsOfThisHiddenService = new HashMap();
  
  private final HiddenServiceProperties hiddenServiceProperties;
  
  public HiddenServiceInstance(HiddenServiceProperties hiddenServiceProperties)
  {
    this.hiddenServiceProperties = hiddenServiceProperties;
  }
  



  public HiddenServiceProperties getHiddenServiceProperties()
  {
    return hiddenServiceProperties;
  }
  









  public synchronized void putHiddenServicePortInstance(HiddenServicePortInstance instance)
    throws BindException
  {
    int port = instance.getPort();
    HiddenServicePortInstance old = getHiddenServicePortInstance(port);
    if ((old != null) && (old.isOpen()))
    {

      throw new BindException("port=" + port + " is already in use - instance=" + instance + " cannot be bound to");
    }
    



    listenPortsOfThisHiddenService.put(Integer.valueOf(port), instance);
    instance.setHiddenServiceInstance(this);
  }
  








  public synchronized HiddenServicePortInstance getHiddenServicePortInstance(int port)
  {
    return (HiddenServicePortInstance)listenPortsOfThisHiddenService.get(Integer.valueOf(port));
  }
}
