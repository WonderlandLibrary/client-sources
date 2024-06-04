package org.silvertunnel_ng.netlib.layer.tor.circuit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.silvertunnel_ng.netlib.api.util.TcpipNetAddress;
import org.silvertunnel_ng.netlib.layer.tor.api.Fingerprint;
import org.silvertunnel_ng.netlib.layer.tor.api.Router;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.Cell;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.CellCreate;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.CellCreateFast;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.CellDestroy;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.CellPadding;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.CellRelay;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.CellRelayData;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.CellRelayExtend;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.CellRelayRendezvous1;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.CellRelaySendme;
import org.silvertunnel_ng.netlib.layer.tor.common.TCPStreamProperties;
import org.silvertunnel_ng.netlib.layer.tor.common.TorConfig;
import org.silvertunnel_ng.netlib.layer.tor.common.TorEvent;
import org.silvertunnel_ng.netlib.layer.tor.common.TorEventService;
import org.silvertunnel_ng.netlib.layer.tor.directory.Directory;
import org.silvertunnel_ng.netlib.layer.tor.directory.GuardList;
import org.silvertunnel_ng.netlib.layer.tor.directory.RendezvousServiceDescriptor;
import org.silvertunnel_ng.netlib.layer.tor.hiddenservice.HiddenServiceProperties;
import org.silvertunnel_ng.netlib.layer.tor.util.Encoding;
import org.silvertunnel_ng.netlib.layer.tor.util.Encryption;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;
import org.silvertunnel_ng.netlib.layer.tor.util.TorNoAnswerException;
import org.silvertunnel_ng.netlib.layer.tor.util.TorServerNotFoundException;
import org.silvertunnel_ng.netlib.util.ByteArrayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;













































public final class Circuit
{
  private static final Logger LOG = LoggerFactory.getLogger(Circuit.class);
  




  private static final int CIRCUIT_LEVEL_FLOW_RECV = 1000;
  




  private int circuitFlowRecv = 1000;
  






  private int circuitFlowSend = 1000;
  


  private static final int CIRCUIT_LEVEL_FLOW_RECV_INC = 100;
  

  public static volatile int numberOfCircuitsInConstructor = 0;
  


  private transient TLSConnection tls;
  


  private Node[] routeNodes;
  


  private int routeEstablished;
  


  private Queue queue;
  


  private boolean unused = true;
  


  private int relayEarlyCellsRemaining = 8;
  




  private final transient Map<Integer, Stream> streams = Collections.synchronizedMap(new HashMap());
  



  private final transient Set<Object> streamHistory = new HashSet();
  


  private int establishedStreams = 0;
  



  private RendezvousServiceDescriptor serviceDescriptor;
  



  private transient int circuitId;
  



  private boolean established;
  



  private boolean closed;
  



  private boolean destruct;
  



  private long createdTime;
  



  private long lastAction;
  



  private long lastCell;
  



  private int setupDurationMs;
  



  private int ranking;
  



  private int sumStreamsSetupDelays;
  



  private int streamCounter;
  



  private int streamFails;
  



  private Directory directory;
  



  private TLSConnectionAdmin tlsConnectionAdmin;
  



  private TorEventService torEventService;
  


  private boolean closeCircuitIfLastStreamIsClosed;
  


  private HiddenServiceInstance hiddenServiceInstanceForIntroduction;
  


  private HiddenServiceInstance hiddenServiceInstanceForRendezvous;
  


  private TCPStreamProperties streamProperties;
  


  private CircuitHistory circuitHistory;
  



  private Circuit() {}
  



  public Circuit(TLSConnectionAdmin fnh, Directory dir, TCPStreamProperties sp, TorEventService torEventService, CircuitHistory circuitHistory)
    throws IOException, TorException, InterruptedException
  {
    numberOfCircuitsInConstructor += 1;
    boolean successful = false;
    try
    {
      directory = dir;
      tlsConnectionAdmin = fnh;
      this.torEventService = torEventService;
      this.circuitHistory = circuitHistory;
      streamProperties = sp;
      closed = false;
      established = false;
      destruct = false;
      sumStreamsSetupDelays = 0;
      streamCounter = 0;
      streamFails = 0;
      ranking = -1;
      createdTime = System.currentTimeMillis();
      lastAction = createdTime;
      lastCell = createdTime;
      

      Thread currentThread = Thread.currentThread();
      String originalThreadName = currentThread.getName();
      

      Router[] routeServers = CircuitAdmin.createNewRoute(dir, sp);
      if ((routeServers == null) || (routeServers.length < 1)) {
        throw new TorException("Circuit: could not build route");
      }
      
      long startSetupTime = System.currentTimeMillis();
      for (int misses = 1;; misses++) {
        long currentSetupDuration = System.currentTimeMillis() - startSetupTime;
        if (currentSetupDuration >= TorConfig.maxAllowedSetupDurationMs)
        {
          String msg = "Circuit: close-during-create " + toString() + ", because current duration of " + currentSetupDuration + " ms is already too long";
          
          LOG.info(msg);
          throw new IOException(msg);
        }
        

        if ((originalThreadName != null) && (originalThreadName.startsWith("Idle Thread"))) {
          currentThread.setName(originalThreadName + " - Circuit to " + routeServers[(routeServers.length - 1)].getNickname());
        }
        
        if (Thread.interrupted()) {
          throw new InterruptedException();
        }
        Router lastTarget = null;
        try
        {
          lastTarget = routeServers[0];
          routeEstablished = 0;
          if (LOG.isDebugEnabled()) {
            LOG.debug("Circuit: connecting to " + routeServers[0].getNickname() + " (" + routeServers[0].getCountryCode() + ")" + " [" + routeServers[0]
              .getPlatform() + "] over tls");
          }
          tls = fnh.getConnection(routeServers[0]);
          queue = new Queue(TorConfig.queueTimeoutCircuit);
          

          circuitId = tls.assignCircuitId(this);
          if (LOG.isDebugEnabled()) {
            LOG.debug("Circuit: assigned to tls " + routeServers[0].getNickname() + " (" + routeServers[0].getCountryCode() + ")" + " [" + routeServers[0]
              .getPlatform() + "]");
          }
          
          if (LOG.isDebugEnabled()) {
            LOG.debug("Circuit: sending create cell to " + routeServers[0].getNickname());
          }
          routeNodes = new Node[routeServers.length];
          if (TorConfig.useCreateFastCells()) {
            createFast(routeServers[0]);
          } else {
            create(routeServers[0]);
          }
          if (LOG.isDebugEnabled()) {
            LOG.debug("Circuit: connected to entry point " + routeServers[0].getNickname() + " (" + routeServers[0].getCountryCode() + ")" + " [" + routeServers[0]
              .getPlatform() + "]");
          }
          routeEstablished = 1;
          dir.getGuardList().successful(routeServers[0].getFingerprint());
          
          for (int i = 1; i < routeServers.length; i++) {
            lastTarget = routeServers[i];
            extend(i, routeServers[i]);
            routeEstablished += 1;
          }
          if (LOG.isDebugEnabled()) {
            LOG.debug("Circuit: " + toString() + " successfully established");
          }
          
        }
        catch (Exception e)
        {
          if (routeEstablished == 0)
          {
            dir.getGuardList().unsuccessful(routeServers[0].getFingerprint());
          }
          
          if (LOG.isDebugEnabled()) {
            LOG.debug("Circuit: " + toString() + " Exception " + misses + " :" + e, e);
          }
          if ((lastTarget != null) && 
            (LOG.isDebugEnabled())) {
            LOG.debug("Circuit: " + toString() + "\nlastTarget\n" + lastTarget.toLongString());
          }
          

          if (circuitId != 0) {
            tls.removeCircuit(Integer.valueOf(circuitId));
          }
          
          if (closed) {
            throw new IOException("Circuit: " + toString() + " closing during buildup");
          }
          if (misses >= TorConfig.getReconnectCircuit())
          {
            if ((e instanceof IOException)) {
              throw ((IOException)e);
            }
            throw new TorException(e);
          }
          


          if (LOG.isDebugEnabled()) {
            LOG.debug("Circuit: " + toString() + " build a new route over the hosts that are known to be working, punish failing host");
          }
          routeServers = CircuitAdmin.restoreCircuit(dir, sp, routeServers, routeEstablished);
        }
      }
      setupDurationMs = ((int)(System.currentTimeMillis() - startSetupTime));
      if (setupDurationMs < TorConfig.maxAllowedSetupDurationMs) {
        established = true;
        if (LOG.isDebugEnabled()) {
          LOG.debug("Circuit: " + toString() + " established within " + setupDurationMs + " ms - OK");
        }
        
        torEventService.fireEvent(new TorEvent(10, this, "Circuit build " + toString()));
        successful = true;
      } else {
        if (LOG.isInfoEnabled()) {
          LOG.info("Circuit: close-after-create " + toString() + ", because established within " + setupDurationMs + " ms was too long");
        }
        close(true);
      }
    } catch (TorServerNotFoundException exception) {
      throw exception;
    } catch (Exception exception) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("got Exception while constructing circuit : " + exception, exception);
      }
    } finally {
      numberOfCircuitsInConstructor -= 1;
      if (!successful) {
        this.circuitHistory = null;
        close(true);
      }
    }
  }
  










  boolean handleIntroduce2(CellRelay cell)
    throws TorException, IOException
  {
    LOG.debug("Circuit.handleIntroduce2: received Intro2-Cell of length=" + cell.getLength());
    
    if (cell.getLength() < 20) {
      throw new TorException("Circuit.handleIntroduce2: cannot parse content, cell is too short");
    }
    byte[] identifier = new byte[20];
    System.arraycopy(cell.getData(), 0, identifier, 0, 20);
    HiddenServiceProperties introProps = getHiddenServiceInstanceForIntroduction().getHiddenServiceProperties();
    if (!Arrays.equals(identifier, introProps.getPubKeyHash())) {
      throw new TorException("Circuit.handleIntroduce2: onion is for unknown key-pair");
    }
    byte[] onionData = new byte[cell.getLength() - 20];
    System.arraycopy(cell.getData(), 20, onionData, 0, cell.getLength() - 20);
    
    byte[] plainIntro2 = Encryption.asymDecrypt(introProps.getPrivateKey(), onionData);
    



    LOG.debug("   Intro2-Cell with plainIntro of lenght=" + plainIntro2.length);
    


    byte[] version = new byte[1];
    byte[] rendezvousPointAddress = new byte[4];
    

    byte[] rendezvousPointIdentityID = new byte[20];
    


    final byte[] cookie = new byte[20];
    final byte[] dhX = new byte[''];
    
    int i = 0;
    System.arraycopy(plainIntro2, i, version, 0, version.length);
    i += version.length;
    

    LOG.debug("version=" + version[0]);
    
    System.arraycopy(plainIntro2, i, rendezvousPointAddress, 0, rendezvousPointAddress.length);
    i += rendezvousPointAddress.length;
    int rendezvousPointPort = Encoding.byteArrayToInt(plainIntro2, i, 2);
    i += 2;
    System.arraycopy(plainIntro2, i, rendezvousPointIdentityID, 0, rendezvousPointIdentityID.length);
    i += rendezvousPointIdentityID.length;
    int rendezvousPointOnionKeyLength = Encoding.byteArrayToInt(plainIntro2, i, 2);
    i += 2;
    byte[] rendezvousPointOnionKey = new byte[rendezvousPointOnionKeyLength];
    System.arraycopy(plainIntro2, i, rendezvousPointOnionKey, 0, rendezvousPointOnionKey.length);
    i += rendezvousPointOnionKey.length;
    System.arraycopy(plainIntro2, i, cookie, 0, cookie.length);
    i += cookie.length;
    System.arraycopy(plainIntro2, i, dhX, 0, dhX.length);
    i += dhX.length;
    


    TcpipNetAddress rendezvousPointTcpipNetAddress1 = new TcpipNetAddress(rendezvousPointAddress, rendezvousPointPort);
    Router rendezvousServer1 = directory.getValidRouterByIpAddressAndOnionPort(rendezvousPointTcpipNetAddress1.getIpNetAddress(), rendezvousPointTcpipNetAddress1
      .getPort());
    

    LOG.debug("rendezvousServer1=" + rendezvousServer1);
    

    byte[] rendezvousPointAddress2 = new byte[4];
    rendezvousPointAddress2[0] = rendezvousPointAddress[3];
    rendezvousPointAddress2[1] = rendezvousPointAddress[2];
    rendezvousPointAddress2[2] = rendezvousPointAddress[1];
    rendezvousPointAddress2[3] = rendezvousPointAddress[0];
    TcpipNetAddress rendezvousPointTcpipNetAddress2 = new TcpipNetAddress(rendezvousPointAddress2, rendezvousPointPort);
    Router rendezvousServer2 = directory.getValidRouterByIpAddressAndOnionPort(rendezvousPointTcpipNetAddress2.getIpNetAddress(), rendezvousPointTcpipNetAddress2
      .getPort());
    

    LOG.debug("rendezvousServer2=" + rendezvousServer2);
    

    final Router rendezvousServer = rendezvousServer1 != null ? rendezvousServer1 : rendezvousServer2;
    

    LOG.debug("rendezvousServer=" + rendezvousServer);
    




    LOG.debug("received Introduce2 cell with rendevouz point server=" + rendezvousServer);
    
    if (version[0] != 2)
    {

      LOG.warn("Intro2-Cell not supported with version=" + version[0]);
      
      return false;
    }
    






































    new Thread()
    {
      public void run()
      {
        TCPStreamProperties sp = new TCPStreamProperties();
        sp.setExitPolicyRequired(false);
        sp.setCustomExitpoint(rendezvousServer.getFingerprint());
        

        for (int j = 0; j < sp.getConnectRetries(); j++) {
          try {
            Circuit c2rendezvous = CircuitAdmin.provideSuitableNewCircuit(tlsConnectionAdmin, directory, sp, torEventService);
            if (c2rendezvous != null)
            {


              Node virtualNode = new Node(rendezvousServer, dhX);
              c2rendezvous.sendCell(new CellRelayRendezvous1(c2rendezvous, cookie, virtualNode.getDhYBytes(), virtualNode.getKeyHandshake()));
              

              Circuit.LOG.debug("Circuit.handleIntroduce2: connected to rendezvous '" + rendezvousServer + "' over " + c2rendezvous.toString());
              



              c2rendezvous.addNode(virtualNode);
              

              c2rendezvous.setHiddenServiceInstanceForRendezvous(hiddenServiceInstanceForIntroduction);
            }
          }
          catch (Throwable e)
          {
            Circuit.LOG.warn("Exception in handleIntroduce2", e);
          }
          
        }
        
      }
      

    }.start();
    return false;
  }
  





  void handleHiddenServiceStreamBegin(CellRelay cell, int streamId)
    throws TorException, IOException
  {
    LOG.info("new stream requested on a circuit that was already established to the rendezvous point");
    

    byte[] cellData = cell.getData();
    

    LOG.debug("handleHiddenServiceStreamBegin with data=" + ByteArrayUtil.showAsStringDetails(cellData));
    
    int DEFAULT_PORT = -1;
    int MAX_PORTSTR_LEN = 5;
    int port = -1;
    if (cellData[0] == 58)
    {
      int startIndex = 1;
      int portNum = 0;
      for (int i = 0; i < 5; i++) {
        char c = (char)cellData[(1 + i)];
        if (!Character.isDigit(c)) {
          break;
        }
        portNum = 10 * portNum + (c - '0');
      }
      port = portNum;
    }
    

    LOG.debug("new stream on port=" + port);
    


    HiddenServiceInstance hiddenServiceInstance = getHiddenServiceInstanceForRendezvous();
    HiddenServicePortInstance hiddenServicePortInstance = hiddenServiceInstance.getHiddenServicePortInstance(port);
    if (hiddenServicePortInstance != null)
    {
      hiddenServicePortInstance.createStream(this, streamId);
      

      LOG.debug("added new TCPStream to NetServerSocket/hiddenServicePortInstance=" + hiddenServicePortInstance);

    }
    else
    {

      LOG.debug("rejected stream because nobody is listen on port=" + port + " of hiddenServiceInstance=" + hiddenServiceInstance);
    }
  }
  










  public void sendCell(Cell cell)
    throws IOException
  {
    lastCell = System.currentTimeMillis();
    if (!cell.isTypePadding()) {
      lastAction = lastCell;
      if ((cell.isTypeRelay()) && ((cell instanceof CellRelayData))) {
        circuitFlowSend -= 1;
        LOG.debug("CIRCUIT_FLOW_CONTROL_SEND = {}", Integer.valueOf(circuitFlowRecv));
        
        if (circuitFlowSend == 0) {
          LOG.debug("waiting for SENDME cell");
          try {
            waitForSendMe.wait();
          } catch (InterruptedException exception) {
            LOG.warn("got Exception while waiting for SENDME cell.", exception);
          }
        }
      }
    }
    try
    {
      tls.sendCell(cell);
    } catch (IOException e) {
      LOG.debug("error in tls.sendCell Exception : {}", e, e);
      


      if (!closed) {
        close(false);
      }
      throw e;
    }
  }
  



  private final transient Object waitForSendMe = new Object();
  

  public void sendKeepAlive()
  {
    try
    {
      sendCell(new CellPadding(this));
    } catch (IOException e) {
      LOG.debug("got IOException : {}" + e.getMessage(), e);
    }
  }
  



  private void create(Router init)
    throws IOException, TorException
  {
    routeNodes[0] = new Node(init);
    
    sendCell(new CellCreate(this));
    
    Cell created = queue.receiveCell(2);
    
    routeNodes[0].finishDh(created.getPayload());
  }
  


  private void createFast(Router init)
    throws IOException, TorException
  {
    LOG.debug("preparing CREATE-FAST cell");
    
    routeNodes[0] = new Node(init, true);
    
    LOG.debug("Sending CREATE-FAST cell");
    sendCell(new CellCreateFast(this));
    
    Cell createdFast = queue.receiveCell(6);
    
    routeNodes[0].finishDh(createdFast.getPayload());
  }
  

  private void extend(int i, Router next)
    throws IOException, TorException
  {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Circuit: " + toString() + " extending to " + next.getNickname() + " (" + next
        .getCountryCode() + ")" + " [" + next.getPlatform() + "]");
    }
    
    routeNodes[i] = new Node(next);
    
    sendCell(new CellRelayExtend(this, routeNodes[i]));
    
    CellRelay relay = queue.receiveRelayCell(7);
    
    routeNodes[i].finishDh(relay.getData());
    if (LOG.isDebugEnabled()) {
      LOG.debug("Circuit: " + toString() + " successfully extended to " + next.getNickname() + " (" + next
        .getCountryCode() + ")" + " [" + next.getPlatform() + "]");
    }
  }
  






  public void extend(Fingerprint routerFingerprint)
    throws TorException, IOException
  {
    if (LOG.isDebugEnabled()) {
      LOG.debug("extending Circuit with id {} to {}", new Object[] { Integer.valueOf(getId()), routerFingerprint });
    }
    
    for (Node node : routeNodes) {
      if (node.getRouter().getFingerprint() == routerFingerprint) {
        throw new TorException("Circuit cant be extended to given fingerprint as this router is already a node");
      }
    }
    Router router = (Router)directory.getValidRoutersByFingerprint().get(routerFingerprint);
    if (router == null) {
      throw new TorException("Router with fingerprint " + routerFingerprint + " not found.");
    }
    
    Node[] newRoute = new Node[routeEstablished + 1];
    System.arraycopy(routeNodes, 0, newRoute, 0, routeEstablished);
    
    routeNodes = newRoute;
    extend(routeEstablished, router);
  }
  





  public void addNode(Node n)
  {
    Node[] newRoute = new Node[routeEstablished + 1];
    if (routeNodes != null) {
      System.arraycopy(routeNodes, 0, newRoute, 0, routeEstablished);
    }
    
    newRoute[routeEstablished] = n;
    routeEstablished += 1;
    
    routeNodes = newRoute;
  }
  





  public void reportStreamFailure(Stream stream)
  {
    streamFails += 1;
    
    if ((streamFails > TorConfig.getCircuitClosesOnFailures()) && (streamFails > streamCounter * 3 / 2)) {
      if (!closed) {
        LOG.info("Circuit.reportStreamFailure: closing due to failures {}", toString());
      }
      close(false);
    }
    
    updateRanking();
  }
  

  private synchronized int getFreeStreamID()
    throws TorException
  {
    for (int nr = 1; nr < 65536; nr++) {
      int newId = nr + streamCounter & 0xFFFF;
      if ((newId != 0) && 
        (!streams.containsKey(Integer.valueOf(newId)))) {
        return newId;
      }
    }
    
    throw new TorException("Circuit.getFreeStreamID: " + toString() + " has no free stream-IDs");
  }
  






  public int assignStreamId(Stream stream)
    throws TorException
  {
    int streamId = getFreeStreamID();
    if (!assignStreamId(stream, streamId)) {
      throw new TorException("streamId=" + streamId + " could not be set");
    }
    return streamId;
  }
  






  public boolean assignStreamId(Stream stream, int streamId)
    throws TorException
  {
    if (closed) {
      throw new TorException("Circuit.assignStreamId: " + toString() + " is closed");
    }
    
    stream.setId(streamId);
    Stream oldStream = (Stream)streams.put(Integer.valueOf(streamId), stream);
    if (oldStream == null)
    {
      return true;
    }
    
    streams.put(Integer.valueOf(streamId), oldStream);
    return false;
  }
  



  void registerStream(TCPStreamProperties sp)
    throws TorException
  {
    establishedStreams += 1;
    if (sp.getAddr() != null) {
      streamHistory.add(sp.getAddr());
    }
    if (sp.getHostname() != null) {
      streamHistory.add(sp.getHostname());
    }
  }
  



  public void registerStream(TCPStreamProperties sp, long streamSetupDuration)
    throws TorException
  {
    sumStreamsSetupDelays = ((int)(sumStreamsSetupDelays + streamSetupDuration));
    streamCounter += 1;
    updateRanking();
    registerStream(sp);
  }
  








  private void updateRanking()
  {
    ranking = ((5 * setupDurationMs + sumStreamsSetupDelays) / (streamCounter + 5));
    




    ranking = ((int)(ranking * Math.exp(streamFails)));
  }
  


  public boolean close(boolean force)
  {
    int i;
    
    if (!closed) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Circuit.close(): closing " + toString());
      }
      
      for (i = 0; i < routeEstablished; i++) {
        Fingerprint f = routeNodes[i].getRouter().getFingerprint();
        Integer numberOfNodeOccurances = CircuitAdmin.getCurrentlyUsedNode(f);
        if (numberOfNodeOccurances != null)
        {
          CircuitAdmin.putCurrentlyUsedNodeNumber(f, Integer.valueOf(Math.max(0, (numberOfNodeOccurances = Integer.valueOf(numberOfNodeOccurances.intValue() - 1)).intValue())));
        }
      }
    }
    torEventService.fireEvent(new TorEvent(11, this, "Circuit: closing " + toString()));
    


    closed = true;
    established = false;
    
    for (Stream stream : new ArrayList(streams.values())) {
      try
      {
        if (!stream.isClosed()) {
          if (force) {
            stream.close(force);

          }
          else if (System.currentTimeMillis() - stream.getLastCellSentDate() > 10 * TorConfig.queueTimeoutStreamBuildup * 1000)
          {
            LOG.info("Circuit.close(): forcing timeout on stream");
            stream.close(true);

          }
          else if (LOG.isDebugEnabled()) {
            LOG.debug("Circuit.close(): can't close due to " + stream.toString());
          }
        }
        

        if (stream.isClosed()) {
          streams.remove(Integer.valueOf(stream.getId()));
        }
      } catch (Exception e) {
        LOG.warn("unexpected " + e, e);
      }
    }
    
    if ((!force) && (!streams.isEmpty())) {
      return false;
    }
    
    if ((!force) && 
      (routeEstablished > 0))
    {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Circuit.close(): destroying " + toString());
      }
      routeEstablished = 1;
      try {
        sendCell(new CellDestroy(this));
      } catch (IOException e) {
        LOG.debug("Exception while destroying circuit: {}", e, e);
      }
    }
    


    if (LOG.isDebugEnabled()) {
      LOG.debug("Circuit.close(): close queue? " + toString());
    }
    if (queue != null) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Circuit.close(): close queue! " + toString());
      }
      queue.close();
    }
    

    destruct = true;
    if (LOG.isDebugEnabled()) {
      LOG.debug("Circuit.close(): remove from tls? " + toString());
    }
    if (tls != null) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Circuit.close(): remove from tls! " + toString());
      }
      tls.removeCircuit(Integer.valueOf(getId()));
    }
    

    if (LOG.isDebugEnabled()) {
      LOG.debug("Circuit.close(): done " + toString());
    }
    return true;
  }
  



  public Router[] getRoute()
  {
    Router[] s = new Router[routeEstablished];
    for (int i = 0; i < routeEstablished; i++) {
      s[i] = routeNodes[i].getRouter();
    }
    return s;
  }
  



  public String toString()
  {
    if ((tls != null) && (tls.getRouter() != null)) {
      Router r1 = tls.getRouter();
      
      StringBuffer sb = new StringBuffer(circuitId + " [" + r1.getNickname() + "/" + r1.getFingerprint() + " (" + r1.getCountryCode() + ") (" + r1.getPlatform() + ")" + (isFast() ? "[fast]" : "") + (isStable() ? "[stable]" : ""));
      for (int i = 1; i < routeEstablished; i++) {
        Router r = routeNodes[i].getRouter();
        sb.append(" " + r.getNickname() + "/" + r.getHostname() + ":" + r.getOrPort() + "/" + r.getFingerprint() + " (" + r.getCountryCode() + ") (" + r
          .getPlatform() + ")");
      }
      sb.append("]");
      if (closed) {
        sb.append(" (closed)");
      }
      else if (!established) {
        sb.append(" (establishing)");
      }
      
      return sb.toString();
    }
    return "<empty>";
  }
  




  public boolean removeStream(Integer streamId)
  {
    synchronized (streams) {
      boolean result = streams.remove(streamId) != null;
      if ((closeCircuitIfLastStreamIsClosed) && (streams.size() == 0)) {
        close(true);
      }
      return result;
    }
  }
  



  public void setHiddenServiceInstanceForIntroduction(HiddenServiceInstance hiddenServiceInstanceForIntroduction)
  {
    this.hiddenServiceInstanceForIntroduction = hiddenServiceInstanceForIntroduction;
  }
  
  HiddenServiceInstance getHiddenServiceInstanceForIntroduction() {
    return hiddenServiceInstanceForIntroduction;
  }
  
  public boolean isUsedByHiddenServiceToConnectToIntroductionPoint() {
    return hiddenServiceInstanceForIntroduction != null;
  }
  
  private void setHiddenServiceInstanceForRendezvous(HiddenServiceInstance hiddenServiceInstanceForRendezvous) {
    this.hiddenServiceInstanceForRendezvous = hiddenServiceInstanceForRendezvous;
  }
  
  HiddenServiceInstance getHiddenServiceInstanceForRendezvous() {
    return hiddenServiceInstanceForRendezvous;
  }
  
  boolean isUsedByHiddenServiceToConnectToRendezvousPoint() {
    return hiddenServiceInstanceForRendezvous != null;
  }
  
  public TorEventService getTorEventService() {
    return torEventService;
  }
  




  public Node getLastRouteNode()
  {
    return routeNodes == null ? null : routeNodes[(routeNodes.length - 1)];
  }
  




  public Node[] getRouteNodes()
  {
    return routeNodes;
  }
  
  public void setRouteNodes(Node[] routeNodes) {
    this.routeNodes = routeNodes;
  }
  



  public int getRouteEstablished()
  {
    return routeEstablished;
  }
  
  public void setRouteEstablished(int routeEstablished) {
    this.routeEstablished = routeEstablished;
  }
  
  public Map<Integer, Stream> getStreams() {
    synchronized (streams) {
      return new HashMap(streams);
    }
  }
  







  public CellRelay receiveRelayCell(int type)
    throws TorNoAnswerException, IOException, TorException
  {
    return queue.receiveRelayCell(type);
  }
  





  public void processCell(Cell cell)
    throws TorException
  {
    if ((cell.isTypeRelay()) && ((cell instanceof CellRelay))) {
      CellRelay relay = (CellRelay)cell;
      if (relay.isTypeData()) {
        reduceCircWindowRecv();
      } else if (relay.isTypeSendme()) {
        circuitFlowSend += 100;
        waitForSendMe.notifyAll();
        LOG.debug("got RELAY_SENDME cell, increasing circuit flow send window to {}", Integer.valueOf(circuitFlowSend));
      }
    }
    queue.add(cell);
  }
  




  public synchronized void reduceCircWindowRecv()
    throws TorException
  {
    circuitFlowRecv -= 1;
    LOG.debug("CIRCUIT_FLOW_CONTROL_RECV = {}", Integer.valueOf(circuitFlowRecv));
    if (circuitFlowRecv <= 900) {
      try
      {
        if (LOG.isDebugEnabled()) {
          LOG.debug("sending RELAY_SENDME cell to router {}", getRoute()[(getRouteEstablished() - 1)]);
        }
        sendCell(new CellRelaySendme(this, getRouteEstablished() - 1));
        circuitFlowRecv += 100;
      } catch (IOException exception) {
        LOG.warn("problems with sending RELAY_SENDME cell to router {}", getRoute()[(getRouteEstablished() - 1)], exception);
        throw new TorException("problems with sending RELAY_SENDME cell to router " + getRoute()[(getRouteEstablished() - 1)], exception);
      }
    }
  }
  





  public Set<Object> getStreamHistory()
  {
    return streamHistory;
  }
  
  public int getEstablishedStreams() {
    return establishedStreams;
  }
  
  public void setEstablishedStreams(int establishedStreams) {
    this.establishedStreams = establishedStreams;
  }
  
  public int getId() {
    return circuitId;
  }
  
  public boolean isEstablished() {
    return established;
  }
  
  public void setEstablished(boolean established) {
    this.established = established;
  }
  
  public boolean isClosed() {
    return closed;
  }
  
  public boolean isDestruct() {
    return destruct;
  }
  
  public long getCreated() {
    return createdTime;
  }
  
  public void setCreated(long created) {
    createdTime = created;
  }
  
  public long getLastAction() {
    return lastAction;
  }
  
  public void setLastAction(long lastAction) {
    this.lastAction = lastAction;
  }
  
  public long getLastCell() {
    return lastCell;
  }
  
  public void setLastCell(long lastCell) {
    this.lastCell = lastCell;
  }
  
  public int getSetupDurationMs() {
    return setupDurationMs;
  }
  
  public void setSetupDurationMs(int setupDurationMs) {
    this.setupDurationMs = setupDurationMs;
  }
  
  public int getRanking() {
    return ranking;
  }
  
  public void setRanking(int ranking) {
    this.ranking = ranking;
  }
  
  public int getSumStreamsSetupDelays() {
    return sumStreamsSetupDelays;
  }
  
  public void setSumStreamsSetupDelays(int sumStreamsSetupDelays) {
    this.sumStreamsSetupDelays = sumStreamsSetupDelays;
  }
  
  public int getStreamCounter() {
    return streamCounter;
  }
  
  public void setStreamCounter(int streamCounter) {
    this.streamCounter = streamCounter;
  }
  
  public int getStreamFails() {
    return streamFails;
  }
  
  public void setStreamFails(int streamFails) {
    this.streamFails = streamFails;
  }
  
  public Directory getDirectory() {
    return directory;
  }
  
  public void setDirectory(Directory directory) {
    this.directory = directory;
  }
  
  public TLSConnectionAdmin getTlsConnectionAdmin() {
    return tlsConnectionAdmin;
  }
  
  public void setTlsConnectionAdmin(TLSConnectionAdmin tlsConnectionAdmin) {
    this.tlsConnectionAdmin = tlsConnectionAdmin;
  }
  
  public RendezvousServiceDescriptor getServiceDescriptor() {
    return serviceDescriptor;
  }
  
  public void setServiceDescriptor(RendezvousServiceDescriptor serviceDescriptor) {
    this.serviceDescriptor = serviceDescriptor;
  }
  


  public boolean isCloseCircuitIfLastStreamIsClosed()
  {
    return closeCircuitIfLastStreamIsClosed;
  }
  




  public void setCloseCircuitIfLastStreamIsClosed(boolean closeCircuitIfLastStreamIsClosed)
  {
    this.closeCircuitIfLastStreamIsClosed = closeCircuitIfLastStreamIsClosed;
  }
  



  private Boolean isFast = null;
  


  public synchronized boolean isFast()
  {
    boolean circuitComplete = true;
    boolean tmpValue = true;
    if (isFast == null) {
      if (routeNodes == null) {
        return false;
      }
      for (Node routerNode : routeNodes) {
        if (routerNode == null) {
          circuitComplete = false;
          return false;
        }
        if (!routerNode.getRouter().isDirv2Fast()) {
          tmpValue = false;
        }
      }
      
      if (circuitComplete) {
        isFast = Boolean.valueOf(tmpValue);
      }
    }
    return isFast.booleanValue();
  }
  



  private Boolean isStable = null;
  


  public synchronized boolean isStable()
  {
    boolean circuitComplete = true;
    boolean tmpValue = true;
    if (isStable == null) {
      if (routeNodes == null) {
        return false;
      }
      for (Node routerNode : routeNodes) {
        if (routerNode == null) {
          circuitComplete = false;
          return false;
        }
        if (!routerNode.getRouter().isDirv2Stable()) {
          tmpValue = false;
        }
      }
      
      if (circuitComplete) {
        isStable = Boolean.valueOf(tmpValue);
      }
    }
    return isStable.booleanValue();
  }
  


  public int getRelayEarlyCellsRemaining()
  {
    return relayEarlyCellsRemaining;
  }
  


  public void decrementRelayEarlyCellsRemaining()
  {
    relayEarlyCellsRemaining -= 1;
  }
  




  public TCPStreamProperties getTcpStreamProperties()
  {
    return streamProperties;
  }
  


  public boolean isUnused()
  {
    if ((unused) && (establishedStreams == 0)) {}
    

    return (!isUsedByHiddenServiceToConnectToIntroductionPoint()) && (!isUsedByHiddenServiceToConnectToRendezvousPoint());
  }
  


  public void setUnused(boolean unused)
  {
    this.unused = unused;
  }
}
