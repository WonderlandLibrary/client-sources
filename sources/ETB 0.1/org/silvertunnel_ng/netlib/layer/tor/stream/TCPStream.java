package org.silvertunnel_ng.netlib.layer.tor.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.util.Arrays;
import org.silvertunnel_ng.netlib.api.NetSocket;
import org.silvertunnel_ng.netlib.layer.tor.api.Router;
import org.silvertunnel_ng.netlib.layer.tor.circuit.Circuit;
import org.silvertunnel_ng.netlib.layer.tor.circuit.Node;
import org.silvertunnel_ng.netlib.layer.tor.circuit.Queue;
import org.silvertunnel_ng.netlib.layer.tor.circuit.Stream;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.Cell;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.CellRelay;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.CellRelayBegin;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.CellRelayBeginDir;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.CellRelayConnected;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.CellRelayData;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.CellRelayDrop;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.CellRelayEnd;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.CellRelaySendme;
import org.silvertunnel_ng.netlib.layer.tor.common.TCPStreamProperties;
import org.silvertunnel_ng.netlib.layer.tor.common.TorConfig;
import org.silvertunnel_ng.netlib.layer.tor.common.TorEvent;
import org.silvertunnel_ng.netlib.layer.tor.common.TorEventService;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;
import org.silvertunnel_ng.netlib.layer.tor.util.TorNoAnswerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





































public class TCPStream
  implements Stream, NetSocket
{
  private static final Logger LOG = LoggerFactory.getLogger(TCPStream.class);
  


  private static final int STREAM_LEVEL_FLOW_WINDOW = 500;
  


  private static final int STREAM_LEVEL_FLOW_INCREMENT = 50;
  

  private int streamLevelFlowControlRecv = 500;
  




  private int streamLevelFlowControlSend = 500;
  
  private final int queueTimeout = TorConfig.queueTimeoutStreamBuildup;
  

  public static final int QUEUE_TIMEOUNT2 = 20;
  

  protected transient Circuit circuit;
  

  protected int streamId;
  

  protected Queue queue;
  

  private InetAddress resolvedAddress;
  

  private boolean established;
  

  private boolean closed;
  

  private int closedForReason;
  

  private QueueTor2JavaHandler qhT2J;
  

  private TCPStreamOutputStream outputStream;
  

  private long created;
  

  private long lastAction;
  

  private long lastCellSentDate;
  


  public TCPStream(Circuit circuit, TCPStreamProperties sp)
    throws IOException, TorException, TorNoAnswerException
  {
    established = false;
    created = System.currentTimeMillis();
    lastAction = created;
    lastCellSentDate = created;
    




    this.circuit = circuit;
    circuit.assignStreamId(this);
    queue = new Queue(queueTimeout);
    closed = false;
    closedForReason = 0;
    if (LOG.isDebugEnabled()) {
      LOG.debug("TCPStream: building new stream " + toString());
    }
    
    long startSetupTime = System.currentTimeMillis();
    if (sp.isConnectToDirServer())
    {
      sendCell(new CellRelayBeginDir(this));
    }
    else {
      sendCell(new CellRelayBegin(this, sp));
    }
    
    CellRelay relay = null;
    try {
      LOG.debug("TCPStream: Waiting for Relay-Connected Cell...");
      relay = queue.receiveRelayCell(4);
      LOG.debug("TCPStream: Got Relay-Connected Cell");
    } catch (TorException e) {
      if (!closed)
      {
        LOG.warn("TCPStream: Closed: " + toString() + " due to TorException:" + e
          .getMessage());
      }
      closed = true;
      







      circuit.reportStreamFailure(this);
      
      throw e;
    } catch (IOException e) {
      closed = true;
      LOG.warn("TCPStream: Closed:" + toString() + " due to IOException:" + e
        .getMessage());
      throw e;
    }
    
    int setupDuration = (int)(System.currentTimeMillis() - startSetupTime);
    

    switch (relay.getLength())
    {
    case 8: 
      byte[] ip = new byte[4];
      System.arraycopy(relay.getData(), 0, ip, 0, ip.length);
      try {
        resolvedAddress = InetAddress.getByAddress(ip);
        sp.setAddr(resolvedAddress);
        sp.setAddrResolved(true);
        if (LOG.isDebugEnabled()) {
          LOG.debug("TCPStream: storing resolved IP " + resolvedAddress
            .toString());
        }
      } catch (IOException e) {
        LOG.info("unexpected for resolved ip={}", Arrays.toString(ip), e);
      }
    


    case 25: 
      LOG.warn("IPv6 not implemented yet");
      break;
    default: 
      LOG.error("this should not happen, unexpected length received : {}", Integer.valueOf(relay.getLength()));
    }
    
    



    qhT2J = new QueueTor2JavaHandler(this);
    queue.addHandler(qhT2J);
    outputStream = new TCPStreamOutputStream(this);
    
    if (LOG.isDebugEnabled()) {
      LOG.debug("TCPStream: build stream " + toString() + " within " + setupDuration + " ms");
    }
    
    circuit.registerStream(sp, setupDuration);
    established = true;
    
    circuit.getTorEventService().fireEvent(new TorEvent(20, this, "Stream build: " + 
    
      toString()));
  }
  














  public TCPStream(Circuit circuit, int streamId)
    throws IOException, TorException, TorNoAnswerException
  {
    established = false;
    created = System.currentTimeMillis();
    lastAction = created;
    lastCellSentDate = created;
    




    this.circuit = circuit;
    circuit.assignStreamId(this, streamId);
    queue = new Queue(20);
    closed = false;
    closedForReason = 0;
    if (LOG.isDebugEnabled()) {
      LOG.debug("TCPStream(2): building new stream " + toString());
    }
    
    long startSetupTime = System.currentTimeMillis();
    














    sendCell(new CellRelayConnected(this));
    
    int setupDuration = (int)(System.currentTimeMillis() - startSetupTime);
    



    qhT2J = new QueueTor2JavaHandler(this);
    queue.addHandler(qhT2J);
    outputStream = new TCPStreamOutputStream(this);
    
    LOG.info("TCPStream: build stream " + toString() + " within " + setupDuration + " ms");
    

    TCPStreamProperties sp = new TCPStreamProperties();
    circuit.registerStream(sp, setupDuration);
    established = true;
    
    circuit.getTorEventService().fireEvent(new TorEvent(20, this, "Stream build: " + 
    
      toString()));
  }
  
  protected TCPStream(Circuit circuit)
  {
    this.circuit = circuit;
  }
  

  private final transient Object waitForSendme = new Object();
  
  public void sendCell(Cell cell)
    throws TorException
  {
    lastCellSentDate = System.currentTimeMillis();
    if (!cell.isTypePadding()) {
      lastAction = lastCellSentDate;
      if ((cell.isTypeRelay()) && ((cell instanceof CellRelayData))) {
        streamLevelFlowControlSend -= 1;
        LOG.debug("STREAM_FLOW_CONTROL_SEND = {}", Integer.valueOf(streamLevelFlowControlSend));
        if (streamLevelFlowControlSend == 0) {
          LOG.debug("waiting for SENDME cell");
          try {
            waitForSendme.wait();
          } catch (InterruptedException exception) {
            throw new TorException("interrupted while trying to wait for SENDME cell", exception);
          }
        }
      }
    }
    try
    {
      circuit.sendCell(cell);
    }
    catch (IOException e) {
      circuit.reportStreamFailure(this);
      close(false);
      throw new TorException(e);
    }
  }
  
  public void sendKeepAlive()
  {
    try {
      sendCell(new CellRelayDrop(this));
    } catch (TorException e) {
      LOG.debug("got TorException while trying to send a keep alive", e);
    }
  }
  


  public void close()
  {
    close(false);
    
    if (LOG.isDebugEnabled()) {
      LOG.debug("TCPStream.close(): removing stream " + toString());
    }
    circuit.removeStream(Integer.valueOf(streamId));
  }
  







  public void close(boolean force)
  {
    if (LOG.isDebugEnabled()) {
      LOG.debug("TCPStream.close(): closing stream " + toString());
    }
    circuit.getTorEventService().fireEvent(new TorEvent(21, this, "Stream closed: " + 
    
      toString()));
    

    if ((!closed) && (!force)) {
      try {
        sendCell(new CellRelayEnd(this, (byte)6));
      }
      catch (TorException e) {
        LOG.debug("got TorException while trying to close the stream", e);
      }
    }
    
    closed = true;
    



    if (outputStream != null) {
      try {
        outputStream.close();
      } catch (Exception e) {
        LOG.debug("got Exception : {}", e.getMessage(), e);
      }
    }
    
    queue.close();
    
    circuit.removeStream(Integer.valueOf(streamId));
  }
  





  public InputStream getInputStream()
  {
    return qhT2J.getInputStream();
  }
  





  public OutputStream getOutputStream()
  {
    return outputStream;
  }
  
  public String getRoute()
  {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < circuit.getRouteEstablished(); i++) {
      Router r = circuit.getRouteNodes()[i].getRouter();
      sb.append(", ");
      sb.append(r.getNickname() + " (" + r.getCountryCode() + ")");
    }
    return sb.toString();
  }
  









  public String toString()
  {
    if (closed) {
      return streamId + " on circuit " + circuit.toString() + " to ??? (closed)";
    }
    return streamId + " on circuit " + circuit.toString() + " to ???";
  }
  





  public void setId(int id)
  {
    if (streamId == 0)
    {
      streamId = id;
    }
    else {
      streamId = id;
      LOG.warn("replaced TCPStream.ID " + streamId + " by " + id);
    }
  }
  
  public int getId()
  {
    return streamId;
  }
  
  public long getLastCellSentDate()
  {
    return lastCellSentDate;
  }
  
  public boolean isClosed()
  {
    return closed;
  }
  
  void setClosed(boolean closed) {
    this.closed = closed;
  }
  
  public Circuit getCircuit()
  {
    return circuit;
  }
  
  public void processCell(Cell cell) throws TorException
  {
    if ((cell.isTypeRelay()) && ((cell instanceof CellRelay))) {
      CellRelay relay = (CellRelay)cell;
      if (relay.isTypeData()) {
        streamLevelFlowControlRecv -= 1;
        LOG.debug("STREAM_FLOW_CONTROL_RECV = {}", Integer.valueOf(streamLevelFlowControlRecv));
        circuit.reduceCircWindowRecv();
        if (streamLevelFlowControlRecv <= 450) {
          try
          {
            sendCell(new CellRelaySendme(this));
            streamLevelFlowControlRecv += 50;
          } catch (TorException exception) {
            LOG.warn("problems with sending RELAY_SENDME for stream {}", Integer.valueOf(getId()), exception);
            throw new TorException("problems with sending RELAY_SENDME for stream " + getId(), exception);
          }
        }
      } else if (relay.isTypeSendme()) {
        streamLevelFlowControlSend += 50;
        waitForSendme.notifyAll();
        LOG.debug("got RELAY_SENDME cell, increasing stream {} flow send window to {}", Integer.valueOf(getId()), Integer.valueOf(streamLevelFlowControlRecv));
      }
    }
    queue.add(cell);
  }
  
  public int getQueueTimeout() {
    return queueTimeout;
  }
  
  public InetAddress getResolvedAddress() {
    return resolvedAddress;
  }
  



  public boolean isEstablished()
  {
    return established;
  }
  
  public int getClosedForReason() {
    return closedForReason;
  }
  
  public void setClosedForReason(int closedForReason) {
    this.closedForReason = closedForReason;
  }
  


  public long getCreated()
  {
    return created;
  }
  
  public long getLastAction() {
    return lastAction;
  }
}
