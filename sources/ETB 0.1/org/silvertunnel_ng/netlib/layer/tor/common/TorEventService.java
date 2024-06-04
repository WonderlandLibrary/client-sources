package org.silvertunnel_ng.netlib.layer.tor.common;

import java.util.ArrayList;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

























public class TorEventService
{
  private static final Logger LOG = LoggerFactory.getLogger(TorEventService.class);
  
  private final Collection<TorEventHandler> eventHandlers = new ArrayList();
  
  public TorEventService() {}
  
  public void registerEventHandler(TorEventHandler eventHandler) { eventHandlers.add(eventHandler); }
  

  public boolean removeEventHandler(TorEventHandler eventHandler)
  {
    return eventHandlers.remove(eventHandler);
  }
  





  public void fireEvent(TorEvent event)
  {
    for (TorEventHandler eventHandler : eventHandlers)
    {
      try
      {
        eventHandler.fireEvent(event);
      }
      catch (Exception e)
      {
        LOG.warn("TorEventService.fireEvent()", e);
      }
    }
  }
}
