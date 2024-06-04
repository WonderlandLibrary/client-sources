package org.silvertunnel_ng.netlib.layer.tor.directory;

import java.util.concurrent.Callable;
import org.silvertunnel_ng.netlib.layer.tor.api.Router;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

























public final class RouterParserCallable
  implements Callable<Router>
{
  private static final Logger LOG = LoggerFactory.getLogger(RouterParserCallable.class);
  


  private final transient String descriptor;
  


  public RouterParserCallable(String descriptor)
  {
    this.descriptor = descriptor;
  }
  

  public Router call()
    throws TorException
  {
    try
    {
      return new RouterImpl(descriptor);
    }
    catch (TorException e)
    {
      LOG.info("got TorException while parsing RouterDescriptor", e);
    }
    return null;
  }
}
