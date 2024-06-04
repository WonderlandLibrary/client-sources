package org.silvertunnel_ng.netlib.layer.tor.circuit;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;
import org.silvertunnel_ng.netlib.layer.tor.api.Router;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.Cell;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.CellDestroy;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.CellRelay;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



























































class TLSDispatcherThread
  extends Thread
{
  private static final Logger LOG = LoggerFactory.getLogger(TLSDispatcherThread.class);
  private final DataInputStream sin;
  private final TLSConnection tls;
  private boolean stopped;
  
  TLSDispatcherThread(TLSConnection tls, DataInputStream sin)
  {
    this.tls = tls;
    this.sin = sin;
    setName("TLSDispatcher for " + tls.getRouter().getNickname());
    start();
  }
  
  public void close() {
    stopped = true;
    interrupt();
  }
  
  public void run()
  {
    boolean dispatched = false;
    while (!stopped)
    {

      Cell cell = null;
      try {
        cell = new Cell(sin);
      } catch (IOException e) {
        if ((e instanceof SocketTimeoutException)) {
          LOG.debug("TLSDispatcher.run: {} connection error: socket timeout", getName(), e);
          continue;
        }
        LOG.info("TLSDispatcher.run: connection error: " + e.getMessage(), e);
        
        stopped = true;
        break;
      }
      
      if (cell.isTypePadding()) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("TLSDispatcher.run: padding cell from {}", tls.getRouter().getNickname());
        }
      } else {
        dispatched = false;
        int cellCircId = cell.getCircuitId();
        
        Circuit circ = tls.getCircuit(Integer.valueOf(cellCircId));
        if (circ != null)
        {
          if (cell.isTypeRelay()) {
            CellRelay relay = null;
            
            try
            {
              relay = new CellRelay(circ, cell);
              if (LOG.isDebugEnabled()) {
                LOG.debug("relay.getRelayCommandAsString()=" + relay
                  .getRelayCommandAsString());
              }
              

              int streamId = relay.getStreamId();
              if (streamId != 0) {
                Stream stream = (Stream)circ.getStreams().get(Integer.valueOf(streamId));
                if (LOG.isDebugEnabled()) {
                  LOG.debug("dispatch to stream with streamId=" + streamId + ", stream=" + stream);
                }
                
                if (stream != null) {
                  dispatched = true;
                  if (LOG.isDebugEnabled()) {
                    LOG.debug("TLSDispatcher.run: data from " + tls
                      .getRouter().getNickname() + " dispatched to circuit " + circ
                      
                      .getId() + "/stream " + streamId);
                  }
                  

                  stream.processCell(relay);
                }
                else if ((circ.isUsedByHiddenServiceToConnectToRendezvousPoint()) && 
                  (relay.isTypeBegin()))
                {


                  circ.handleHiddenServiceStreamBegin(relay, streamId);


                }
                else if (LOG.isDebugEnabled()) {
                  LOG.debug("else: circ.isUsedByHiddenServiceToConnectToRendezvousPoint()=" + circ
                    .isUsedByHiddenServiceToConnectToRendezvousPoint() + ", relay.getRelayCommand()=" + relay
                    
                    .getRelayCommand());

                }
                

              }
              else if (relay.isTypeIntroduce2()) {
                if (circ.isUsedByHiddenServiceToConnectToIntroductionPoint()) {
                  if (LOG.isDebugEnabled()) {
                    LOG.debug("TLSDispatcher.run: introduce2 from " + tls
                      .getRouter()
                      .getNickname() + " dispatched to circuit " + circ
                      
                      .getId() + " (stream ID=0)");
                  }
                  try
                  {
                    dispatched = circ.handleIntroduce2(relay);
                  } catch (IOException e) {
                    LOG.info("TLSDispatcher.run: error handling intro2-cell: " + e
                      .getMessage());
                  }
                  
                }
                else if (LOG.isDebugEnabled()) {
                  LOG.debug("else isTypeIntroduce2: from " + tls
                    .getRouter()
                    .getNickname() + " dispatched to circuit " + circ
                    
                    .getId() + " (stream ID=0)");
                }
              }
              else
              {
                if (LOG.isDebugEnabled()) {
                  LOG.debug("TLSDispatcher.run: data from " + tls
                    .getRouter().getNickname() + " dispatched to circuit " + circ
                    
                    .getId() + " (stream ID=0)");
                }
                
                dispatched = true;
                circ.processCell(relay);
              }
            }
            catch (TorException e) {
              LOG.warn("TLSDispatcher.run: TorException " + e
                .getMessage() + " during dispatching cell");
            }
            catch (Exception e) {
              LOG.warn("TLSDispatcher.run: Exception " + e
              
                .getMessage() + " during dispatching cell", e);
            }
            

          }
          else if (cell.isTypeDestroy()) {
            if (LOG.isDebugEnabled()) {
              try {
                LOG.debug("TLSDispatcher.run: received DESTROY-cell from " + tls
                  .getRouter().getNickname() + " for circuit " + circ
                  
                  .getId() + " reason : " + ((CellDestroy)cell)
                  
                  .getReason());
              } catch (ClassCastException exception) {
                LOG.debug("TLSDispatcher.run: received DESTROY-cell from " + tls
                  .getRouter().getNickname() + " for circuit " + circ
                  
                  .getId() + " reason : " + 
                  CellDestroy.getReason(cell.getPayload()[0]));
              }
            }
            if (cell.getPayload()[0] == 1) {
              LOG.warn("got a DestroyCell with Reason protocol violation from " + circ);
            }
            dispatched = true;
            circ.close(true);
          } else {
            if (LOG.isDebugEnabled()) {
              LOG.debug("TLSDispatcher.run: data from " + tls
                .getRouter().getNickname() + " dispatched to circuit " + circ
                
                .getId());
            }
            dispatched = true;
            try {
              circ.processCell(cell);
            } catch (TorException exception) {
              LOG.warn("got Exception while processing cell", exception);
            }
          }
        }
        else {
          LOG.info("TLSDispatcher.run: received cell for circuit " + cellCircId + " from " + tls
          
            .getRouter().getNickname() + ". But no such circuit exists.");
        }
      }
      
      if (!dispatched)
      {

        if (LOG.isDebugEnabled()) {
          LOG.debug("TLSDispatcher.run: data from " + tls
            .getRouter().getNickname() + " could not get dispatched");
          
          LOG.debug("TLSDispatcher.run: " + cell.toString());
        }
      }
    }
  }
}
