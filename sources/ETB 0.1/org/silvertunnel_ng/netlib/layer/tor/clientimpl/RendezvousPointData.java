package org.silvertunnel_ng.netlib.layer.tor.clientimpl;

import org.silvertunnel_ng.netlib.layer.tor.api.Router;
import org.silvertunnel_ng.netlib.layer.tor.circuit.Circuit;






















class RendezvousPointData
{
  private byte[] rendezvousCookie = new byte[20];
  private Router rendezvousPointRouter = null;
  private Circuit myRendezvousCirc = null;
  
  public RendezvousPointData(byte[] rendezvousCookie, Router rendezvousPointRouter, Circuit myRendezvousCirc)
  {
    this.rendezvousCookie = rendezvousCookie;
    this.rendezvousPointRouter = rendezvousPointRouter;
    this.myRendezvousCirc = myRendezvousCirc;
  }
  



  public byte[] getRendezvousCookie()
  {
    return rendezvousCookie;
  }
  
  public Router getRendezvousPointRouter() {
    return rendezvousPointRouter;
  }
  
  public Circuit getMyRendezvousCirc() {
    return myRendezvousCirc;
  }
}
