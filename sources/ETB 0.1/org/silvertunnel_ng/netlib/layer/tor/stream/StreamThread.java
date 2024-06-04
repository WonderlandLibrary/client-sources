package org.silvertunnel_ng.netlib.layer.tor.stream;

import org.silvertunnel_ng.netlib.layer.tor.circuit.Circuit;
import org.silvertunnel_ng.netlib.layer.tor.circuit.Queue;
import org.silvertunnel_ng.netlib.layer.tor.common.TCPStreamProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;























public class StreamThread
  extends Thread
{
  private static final Logger LOG = LoggerFactory.getLogger(StreamThread.class);
  
  private TCPStream stream;
  
  private final Circuit cs;
  
  private final TCPStreamProperties sp;
  

  public StreamThread(Circuit cs, TCPStreamProperties sp)
  {
    this.cs = cs;
    this.sp = sp;
    start();
  }
  





  public void run()
  {
    try
    {
      stream = new TCPStream(cs, sp);

    }
    catch (Exception e)
    {
      if ((stream != null) && (stream.queue != null) && 
        (!stream.queue.isClosed()))
      {
        LOG.warn("Tor.StreamThread.run(): " + e.getMessage());
      }
      stream = null;
    }
  }
  

  public TCPStream getStream()
  {
    return stream;
  }
}
