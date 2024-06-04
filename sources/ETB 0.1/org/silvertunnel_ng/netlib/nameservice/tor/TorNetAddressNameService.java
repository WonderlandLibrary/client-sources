package org.silvertunnel_ng.netlib.nameservice.tor;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import org.silvertunnel_ng.netlib.api.NetAddress;
import org.silvertunnel_ng.netlib.api.NetAddressNameService;
import org.silvertunnel_ng.netlib.api.util.IpNetAddress;
import org.silvertunnel_ng.netlib.layer.tor.clientimpl.Tor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




























public class TorNetAddressNameService
  implements NetAddressNameService
{
  private static final Logger LOG = LoggerFactory.getLogger(TorNetAddressNameService.class);
  





  private final Tor tor;
  





  protected TorNetAddressNameService(Tor tor)
  {
    if (tor == null)
    {
      throw new NullPointerException("invalid argument tor=null");
    }
    
    this.tor = tor;
  }
  

  public NetAddress[] getAddressesByName(String hostname)
    throws UnknownHostException
  {
    try
    {
      checkNetlibTorLoop();
      

      List<NetAddress> result = tor.resolveAll(hostname);
      return (NetAddress[])result.toArray(new NetAddress[result.size()]);

    }
    catch (UnknownHostException e)
    {
      throw e;

    }
    catch (IOException e)
    {
      UnknownHostException e2 = new UnknownHostException("Error with hostname=" + hostname);
      e2.initCause(e);
      throw e2;
    }
    catch (Throwable throwable) {
      LOG.warn("got Exception", throwable); }
    return null;
  }
  


  public String[] getNamesByAddress(NetAddress netAddress)
    throws UnknownHostException
  {
    try
    {
      checkNetlibTorLoop();
      
      if (netAddress == null)
      {
        throw new UnknownHostException("Invalid netAddress=null");
      }
      if ((netAddress instanceof IpNetAddress))
      {

        IpNetAddress ipNetAddress = (IpNetAddress)netAddress;
        String result = tor.resolve(ipNetAddress);
        return new String[] { result };
      }
      



      throw new UnknownHostException("Invalid type of netAddress=" + netAddress);

    }
    catch (UnknownHostException e)
    {

      throw e;

    }
    catch (IOException e)
    {
      UnknownHostException e2 = new UnknownHostException("Error with netAddress=" + netAddress);
      e2.initCause(e);
      throw e2;
    }
    catch (Throwable throwable)
    {
      LOG.warn("got Exception", throwable); }
    return null;
  }
  








  private void checkNetlibTorLoop()
    throws UnknownHostException
  {
    UnknownHostException e = new UnknownHostException("Netlib Tor call cycle / dead lock prevented");
    for (StackTraceElement ste : e.getStackTrace())
    {
      if (ste.getClassName().startsWith("org.silvertunnel_ng.netlib.layer.tor."))
      {


        if (LOG.isDebugEnabled())
        {
          LOG.debug("Netlib Tor call cycle / dead lock prevented - right now", e);
        }
        else
        {
          LOG.info("Netlib Tor call cycle / dead lock prevented - right now; use FINE logging to see the call stack");
        }
        throw e;
      }
    }
  }
}
