package org.silvertunnel_ng.netlib.layer.tor.stream;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import org.silvertunnel_ng.netlib.layer.tor.circuit.Queue;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.Cell;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.CellRelay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

























class TCPStreamThreadTor2JavaThread
  extends Thread
{
  private static final Logger LOG = LoggerFactory.getLogger(TCPStreamThreadTor2JavaThread.class);
  
  private final TCPStream stream;
  
  private PipedInputStream sin;
  
  private PipedOutputStream fromtor;
  private boolean stopped;
  
  TCPStreamThreadTor2JavaThread(TCPStream stream)
  {
    this.stream = stream;
    try {
      sin = new SafePipedInputStream();
      fromtor = new PipedOutputStream(sin);
    } catch (IOException e) {
      LOG.error("TCPStreamThreadTor2Java: caught IOException " + e
        .getMessage());
    }
    start();
  }
  
  public void close() {
    stopped = true;
    interrupt();
  }
  
  public void run()
  {
    while ((!stream.isClosed()) && (!stopped)) {
      Cell cell = stream.queue.get();
      if (cell != null) {
        if (!cell.isTypeRelay()) {
          LOG.error("TCPStreamThreadTor2Java.run(): stream " + stream
            .getId() + " received NON-RELAY cell:\n" + cell
            .toString());
        } else {
          CellRelay relay = (CellRelay)cell;
          if (relay.isTypeData()) {
            LOG.debug("TCPStreamThreadTor2Java.run(): stream {} received data", Integer.valueOf(stream.getId()));
            try {
              fromtor.write(relay.getData(), 0, relay.getLength());
            } catch (IOException e) {
              LOG.error("TCPStreamThreadTor2Java.run(): caught IOException " + e
                .getMessage());
            }
          } else if (relay.isTypeEnd()) {
            if (LOG.isDebugEnabled()) {
              LOG.debug("TCPStreamThreadTor2Java.run(): stream " + stream
                .getId() + " is closed: " + relay
                .getReasonForClosing());
            }
            stream.setClosedForReason(relay.getPayload()[0] & 0xFF);
            stream.setClosed(true);
            stream.close(true);
          } else {
            LOG.error("TCPStreamThreadTor2Java.run(): stream " + stream
              .getId() + " received strange cell:\n" + relay
              .toString());
          }
        }
      }
    }
  }
}
