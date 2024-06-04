package org.silvertunnel_ng.netlib.layer.tor.stream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.silvertunnel_ng.netlib.api.NetAddress;
import org.silvertunnel_ng.netlib.api.util.Hostname;
import org.silvertunnel_ng.netlib.api.util.IpNetAddress;
import org.silvertunnel_ng.netlib.layer.tor.circuit.Circuit;
import org.silvertunnel_ng.netlib.layer.tor.circuit.Queue;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.CellRelay;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.CellRelayResolve;
import org.silvertunnel_ng.netlib.layer.tor.common.TorConfig;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;
import org.silvertunnel_ng.netlib.tool.DynByteBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;






















public final class ResolveStream
  extends TCPStream
{
  private static final Logger LOG = LoggerFactory.getLogger(ResolveStream.class);
  



  private final int queueTimeoutS = TorConfig.queueTimeoutResolve;
  
  public ResolveStream(Circuit c) {
    super(c);
  }
  






  public List<NetAddress> resolve(String hostname)
    throws TorException, IOException
  {
    circuit.assignStreamId(this);
    
    circuit.getStreamHistory().add(hostname);
    queue = new Queue(queueTimeoutS);
    setClosed(false);
    if (LOG.isDebugEnabled()) {
      LOG.debug("resolving hostname " + hostname + " on stream " + toString());
    }
    
    sendCell(new CellRelayResolve(this, hostname));
    
    CellRelay relay = queue.receiveRelayCell(12);
    DynByteBuffer buffer = new DynByteBuffer(relay.getData());
    List<NetAddress> result = new ArrayList();
    
    byte type = buffer.getNextByte();
    int len = buffer.getNextByteAsInt();
    
    byte[] value = buffer.getNextByteArray(len);
    buffer.getNextInt();
    

    if (type == -16) {
      throw new TorException("transient error: " + new String(value));
    }
    if (type == -15) {
      throw new TorException("non transient error: " + new String(value));
    }
    
    if ((type != 0) && (type != 4) && (type != 6)) {
      throw new TorException("can't handle answers of type " + type);
    }
    
    while (len > 0)
    {
      if (type == 0) {
        result.add(new Hostname(value));
      } else {
        result.add(new IpNetAddress(value));
      }
      type = buffer.getNextByte();
      len = buffer.getNextByteAsInt();
      
      value = buffer.getNextByteArray(len);
      buffer.getNextInt();
    }
    return result;
  }
}
