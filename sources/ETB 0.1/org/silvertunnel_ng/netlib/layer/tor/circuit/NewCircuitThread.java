package org.silvertunnel_ng.netlib.layer.tor.circuit;

import org.silvertunnel_ng.netlib.layer.tor.common.TCPStreamProperties;
import org.silvertunnel_ng.netlib.layer.tor.common.TorEventService;
import org.silvertunnel_ng.netlib.layer.tor.directory.Directory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
























public class NewCircuitThread
  extends Thread
{
  private static final Logger LOG = LoggerFactory.getLogger(NewCircuitThread.class);
  



  private final TLSConnectionAdmin fnh;
  


  private final Directory dir;
  


  private final TCPStreamProperties spFinal;
  


  private final TorEventService torEventService;
  



  public NewCircuitThread(TLSConnectionAdmin fnh, Directory dir, TCPStreamProperties spFinal, TorEventService torEventService)
  {
    this.fnh = fnh;
    this.dir = dir;
    this.spFinal = spFinal;
    this.torEventService = torEventService;
  }
  

  public final void run()
  {
    try
    {
      new Circuit(fnh, dir, spFinal, torEventService, null);
    }
    catch (Exception e)
    {
      LOG.warn("unexpected", e);
    }
  }
}
