package org.silvertunnel_ng.netlib.layer.tor.circuit;

import java.util.Map;
import org.silvertunnel_ng.netlib.layer.tor.api.Router;
import org.silvertunnel_ng.netlib.layer.tor.common.TCPStreamProperties;
import org.silvertunnel_ng.netlib.layer.tor.directory.Directory;
import org.silvertunnel_ng.netlib.layer.tor.directory.RendezvousServiceDescriptor;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;
































public class DirectoryService
{
  public DirectoryService() {}
  
  public static boolean isCompatible(Directory directory, Circuit circ, TCPStreamProperties sp, boolean forHiddenService)
    throws TorException
  {
    Router[] routeCopy = new Router[circ.getRouteNodes().length];
    for (int i = 0; i < circ.getRouteNodes().length; i++)
    {
      routeCopy[i] = circ.getRouteNodes()[i].getRouter();
    }
    if (forHiddenService)
    {


      if (circ.getStreamHistory() == null)
      {
        if ((circ.getStreams() == null) || (
          (circ.getStreams().size() == 1) && 
          (circ.getServiceDescriptor() != null) && 
          (sp.getHostname().contains(circ.getServiceDescriptor().getURL()))))
        {
          return directory.isCompatible(routeCopy, sp, forHiddenService);
        }
        
      }
      

    }
    else if ((circ.getServiceDescriptor() == null) && (!circ.isUsedByHiddenServiceToConnectToIntroductionPoint()))
    {
      return directory.isCompatible(routeCopy, sp, forHiddenService);
    }
    
    return false;
  }
}
