package org.silvertunnel_ng.netlib.layer.tor.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import org.silvertunnel_ng.netlib.layer.tor.circuit.QueueHandler;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.Cell;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.CellRelay;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;






















class QueueTor2JavaHandler
  implements QueueHandler
{
  private static final Logger LOG = LoggerFactory.getLogger(QueueTor2JavaHandler.class);
  
  private final TCPStream stream;
  
  private PipedInputStream sin;
  
  private PipedOutputStream fromtor;
  private boolean stopped;
  
  QueueTor2JavaHandler(TCPStream stream)
  {
    this.stream = stream;
    try {
      sin = new SafePipedInputStream();
      fromtor = new PipedOutputStream(sin);
    } catch (IOException e) {
      LOG.error("QueueTor2JavaHandler: caught IOException " + e.getMessage(), e);
    }
  }
  
  public void close()
  {
    stopped = true;
    
    try
    {
      fromtor.close();
    } catch (Exception e) {
      LOG.debug("got Exception : {}", e.getMessage(), e);
    }
  }
  
  public boolean handleCell(Cell cell)
    throws TorException
  {
    if ((stream.isClosed()) || (stopped)) {
      return false;
    }
    if (cell == null) {
      return false;
    }
    if (!cell.isTypeRelay()) {
      return false;
    }
    
    CellRelay relay = (CellRelay)cell;
    if (relay.isTypeData()) {
      LOG.debug("QueueTor2JavaHandler.handleCell(): stream {} received data", Integer.valueOf(stream.getId()));
      try {
        fromtor.write(relay.getData(), 0, relay.getLength());
      } catch (IOException e) {
        LOG.error("QueueTor2JavaHandler.handleCell(): caught IOException " + e.getMessage(), e);
      }
      return true; }
    if (relay.isTypeEnd()) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("QueueTor2JavaHandler.handleCell(): stream " + stream
          .getId() + " is closed: " + relay
          .getReasonForClosing());
      }
      stream.setClosedForReason(relay.getPayload()[0] & 0xFF);
      stream.setClosed(true);
      stream.close(true);
      stopped = true;
      return true;
    }
    return false;
  }
  
  public InputStream getInputStream() {
    return sin;
  }
}
