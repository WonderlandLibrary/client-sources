package org.silvertunnel_ng.netlib.layer.tor.clientimpl;

import java.io.IOException;
import java.security.Security;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.silvertunnel_ng.netlib.api.NetAddress;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetLayerStatus;
import org.silvertunnel_ng.netlib.api.util.Hostname;
import org.silvertunnel_ng.netlib.api.util.IpNetAddress;
import org.silvertunnel_ng.netlib.layer.tor.api.Router;
import org.silvertunnel_ng.netlib.layer.tor.api.TorNetLayerStatus;
import org.silvertunnel_ng.netlib.layer.tor.circuit.Circuit;
import org.silvertunnel_ng.netlib.layer.tor.circuit.CircuitAdmin;
import org.silvertunnel_ng.netlib.layer.tor.circuit.CircuitsStatus;
import org.silvertunnel_ng.netlib.layer.tor.circuit.HiddenServicePortInstance;
import org.silvertunnel_ng.netlib.layer.tor.circuit.TLSConnection;
import org.silvertunnel_ng.netlib.layer.tor.circuit.TLSConnectionAdmin;
import org.silvertunnel_ng.netlib.layer.tor.common.TCPStreamProperties;
import org.silvertunnel_ng.netlib.layer.tor.common.TorConfig;
import org.silvertunnel_ng.netlib.layer.tor.common.TorEventService;
import org.silvertunnel_ng.netlib.layer.tor.directory.Directory;
import org.silvertunnel_ng.netlib.layer.tor.hiddenservice.HiddenServiceProperties;
import org.silvertunnel_ng.netlib.layer.tor.stream.ClosingThread;
import org.silvertunnel_ng.netlib.layer.tor.stream.ResolveStream;
import org.silvertunnel_ng.netlib.layer.tor.stream.StreamThread;
import org.silvertunnel_ng.netlib.layer.tor.stream.TCPStream;
import org.silvertunnel_ng.netlib.layer.tor.util.NetLayerStatusAdmin;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;
import org.silvertunnel_ng.netlib.layer.tor.util.TorNoAnswerException;
import org.silvertunnel_ng.netlib.util.StringStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.jce.provider.BouncyCastleProvider;

































































public class Tor
  implements NetLayerStatusAdmin
{
  private static final Logger LOG = LoggerFactory.getLogger(Tor.class);
  

  private static final int TOR_CONNECT_MAX_RETRIES = 10;
  

  private static final long TOR_CONNECT_MILLISECONDS_BETWEEN_RETRIES = 10L;
  
  private Directory directory;
  
  private TLSConnectionAdmin tlsConnectionAdmin;
  
  private TorBackgroundMgmtThread torBackgroundMgmtThread;
  
  private long startupPhaseWithoutConnects;
  
  private final NetLayer lowerTlsConnectionNetLayer;
  
  private final NetLayer lowerDirConnectionNetLayer;
  
  private final StringStorage stringStorage;
  
  private final TorEventService torEventService = new TorEventService();
  
  private boolean gaveMessage = false;
  private boolean startUpInProgress = true;
  
  private NetLayerStatus status = TorNetLayerStatus.NEW;
  




  public Tor(NetLayer lowerTlsConnectionNetLayer, NetLayer lowerDirConnectionNetLayer, StringStorage stringStorage)
    throws IOException
  {
    this.lowerTlsConnectionNetLayer = lowerTlsConnectionNetLayer;
    this.lowerDirConnectionNetLayer = lowerDirConnectionNetLayer;
    this.stringStorage = stringStorage;
    initLocalSystem(false);
    initDirectory();
    initRemoteAccess();
  }
  
  private void initLocalSystem(boolean noLocalFileSystemAccess) throws IOException
  {
    if (Security.getProvider("SC") == null) {
      Security.addProvider(new BouncyCastleProvider());
    }
    


    LOG.info("Tor implementation of silvertunnel-ng.org is starting up");
    
    startupPhaseWithoutConnects = (System.currentTimeMillis() + TorConfig.getStartupDelay() * 1000L);
  }
  
  private void initDirectory() throws IOException
  {
    directory = new Directory(stringStorage, lowerDirConnectionNetLayer, this);
  }
  
  private void initRemoteAccess() throws IOException
  {
    tlsConnectionAdmin = new TLSConnectionAdmin(lowerTlsConnectionNetLayer);
    
    torBackgroundMgmtThread = new TorBackgroundMgmtThread(this);
  }
  


  public Collection<Router> getValidTorRouters()
  {
    Collection<Router> resultBase = directory.getValidRoutersByFingerprint().values();
    Collection<Router> result = new ArrayList(resultBase.size());
    

    for (Router r : resultBase) {
      result.add(r.cloneReliable());
    }
    
    return result;
  }
  






  public TCPStream connect(TCPStreamProperties sp, NetLayer torNetLayer)
    throws Throwable
  {
    if ((sp.getHostname() == null) && (sp.getAddr() == null)) {
      throw new IOException("Tor: no hostname and no address provided");
    }
    

    checkStartup();
    

    if ((sp.getHostname() != null) && (sp.getHostname().endsWith(".onion")))
    {
      return HiddenServiceClient.connectToHiddenService(directory, torEventService, tlsConnectionAdmin, torNetLayer, sp);
    }
    

    int retry = 0;
    String hostnameAddress = null;
    int minIdleCircuits = Math.min(2, TorConfig.getMinimumIdleCircuits());
    for (; retry <= 10; retry++)
    {
      waitForIdleCircuits(minIdleCircuits);
      

      Circuit[] circuits = CircuitAdmin.provideSuitableCircuits(tlsConnectionAdmin, directory, sp, torEventService, false);
      
      if ((circuits == null) || (circuits.length < 1)) {
        LOG.debug("no valid circuit found: wait for new one created by the TorBackgroundMgmtThread");
        try {
          Thread.sleep(3000L);
        } catch (InterruptedException e) {
          LOG.debug("got IterruptedException : {}", e.getMessage(), e);
        }
      }
      else {
        if (TorConfig.isVeryAggressiveStreamBuilding())
        {
          for (int j = 0; j < circuits.length; j++) {
            try
            {
              StreamThread[] streamThreads = new StreamThread[circuits.length];
              for (int i = 0; i < circuits.length; i++) {
                streamThreads[i] = new StreamThread(circuits[i], sp);
              }
              
              int chosenStream = -1;
              int waitingCounter = TorConfig.queueTimeoutStreamBuildup * 1000 / 10;
              while ((chosenStream < 0) && (waitingCounter >= 0)) {
                boolean atLeastOneAlive = false;
                for (int i = 0; (i < circuits.length) && (chosenStream < 0); i++) {
                  if (!streamThreads[i].isAlive()) {
                    if ((streamThreads[i].getStream() != null) && (streamThreads[i].getStream().isEstablished())) {
                      chosenStream = i;
                    }
                  } else {
                    atLeastOneAlive = true;
                  }
                }
                if (!atLeastOneAlive) {
                  break;
                }
                
                long SLEEPING_MS = 10L;
                try {
                  Thread.sleep(10L);
                } catch (InterruptedException e) {
                  LOG.debug("got IterruptedException : {}", e.getMessage(), e);
                }
                
                waitingCounter--;
              }
              
              if (chosenStream >= 0) {
                TCPStream returnValue = streamThreads[chosenStream].getStream();
                new ClosingThread(streamThreads, chosenStream);
                return returnValue;
              }
            } catch (Exception e) {
              LOG.warn("Tor.connect(): " + e.getMessage());
              return null;
            }
            
          }
          
        } else {
          for (int i = 0; i < circuits.length; i++) {
            try {
              return new TCPStream(circuits[i], sp);
            } catch (TorNoAnswerException e) {
              LOG.warn("Tor.connect: Timeout on circuit:" + e.getMessage());
            } catch (TorException e) {
              LOG.warn("Tor.connect: TorException trying to reuse existing circuit:" + e.getMessage(), e);
            } catch (IOException e) {
              LOG.warn("Tor.connect: IOException " + e.getMessage());
            }
          }
        }
        
        hostnameAddress = sp.getAddr() != null ? "" + sp.getAddr() : sp.getHostname();
        LOG.info("Tor.connect: not (yet) connected to " + hostnameAddress + ":" + sp.getPort() + ", full retry count=" + retry);
        try
        {
          Thread.sleep(10L);
        } catch (InterruptedException e) {
          LOG.debug("got IterruptedException : {}", e.getMessage(), e);
        }
      } }
    hostnameAddress = sp.getAddr() != null ? "" + sp.getAddr() : sp.getHostname();
    
    throw new IOException("Tor.connect: unable to connect to " + hostnameAddress + ":" + sp.getPort() + " after " + retry + " full retries with " + sp.getConnectRetries() + " sub retries");
  }
  







  public void provideHiddenService(NetLayer torNetLayerToConnectToDirectoryService, HiddenServiceProperties service, HiddenServicePortInstance hiddenServicePortInstance)
    throws IOException, TorException
  {
    checkStartup();
    

    HiddenServiceServer.getInstance().provideHiddenService(directory, torEventService, tlsConnectionAdmin, torNetLayerToConnectToDirectoryService, service, hiddenServicePortInstance);
  }
  






  public void close(boolean force)
  {
    LOG.info("TorJava ist closing down");
    
    torBackgroundMgmtThread.close();
    
    tlsConnectionAdmin.close(force);
    
    directory.close();
    


    LOG.info("Tor.close(): CLOSED");
  }
  
  public void close()
  {
    close(false);
  }
  





  public List<NetAddress> resolveAll(String hostname)
    throws Throwable
  {
    return resolveInternal(hostname);
  }
  





  public IpNetAddress resolve(String hostname)
    throws Throwable
  {
    return (IpNetAddress)resolveInternal(hostname).get(0);
  }
  






  public String resolve(IpNetAddress addr)
    throws Throwable
  {
    byte[] a = addr.getIpaddress();
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < 4; i++) {
      sb.append(a[(3 - i)] & 0xFF);
      sb.append('.');
    }
    sb.append("in-addr.arpa");
    
    List<NetAddress> o = resolveInternal(sb.toString());
    if ((o.get(0) instanceof Hostname)) {
      return ((Hostname)o.get(0)).getHostname();
    }
    return null;
  }
  






  private List<NetAddress> resolveInternal(String query)
    throws Throwable
  {
    try
    {
      checkStartup();
      

      for (TLSConnection tls : tlsConnectionAdmin.getConnections())
      {
        for (Circuit circuit : tls.getCircuits()) {
          try {
            if (circuit.isEstablished())
            {
              ResolveStream rs = new ResolveStream(circuit);
              List<NetAddress> o = rs.resolve(query);
              rs.close();
              return o;
            }
          }
          catch (Exception e)
          {
            LOG.debug("got Exception : {}", e.getMessage(), e);
          }
        }
      }
      




      TCPStreamProperties streamProperties = new TCPStreamProperties();
      Circuit[] rsCircuit = CircuitAdmin.provideSuitableCircuits(tlsConnectionAdmin, directory, streamProperties, torEventService, false);
      
      ResolveStream rs = new ResolveStream(rsCircuit[0]);
      List<NetAddress> o = rs.resolve(query);
      rs.close();
      return o;
    } catch (TorException e) {
      throw new IOException("Error in Tor: " + e.getMessage());
    }
  }
  
  public void setStatus(NetLayerStatus newStatus)
  {
    LOG.debug("TorNetLayer old status: {}", status);
    status = newStatus;
    LOG.info("TorNetLayer new status: {}", status);
  }
  





  public void updateStatus(NetLayerStatus newStatus)
  {
    if (getStatus().getReadyIndicator() < newStatus.getReadyIndicator()) {
      setStatus(newStatus);
    }
  }
  
  public NetLayerStatus getStatus()
  {
    return status;
  }
  



  public void checkStartup()
  {
    if (!startUpInProgress) {
      return;
    }
    

    long now = System.currentTimeMillis();
    if (now >= startupPhaseWithoutConnects) {
      startUpInProgress = false;
      return;
    }
    

    long sleep = startupPhaseWithoutConnects - System.currentTimeMillis();
    if (!gaveMessage) {
      gaveMessage = true;
      LOG.debug("Tor.checkStartup(): Tor is still in startup phase, sleeping for max. {} seconds", Long.valueOf(sleep / 1000L));
      LOG.debug("Tor not yet started - wait until torServers available");
    }
    



    waitForIdleCircuits(TorConfig.getMinimumIdleCircuits());
    try {
      Thread.sleep(500L);
    } catch (Exception e) {
      LOG.debug("got Exception : {}", e.getMessage(), e);
    }
    LOG.info("Tor start completed!!!");
    startUpInProgress = false;
  }
  






  private void waitForIdleCircuits(int minExpectedIdleCircuits)
  {
    while ((!directory.isDirectoryReady()) || (getCircuitsStatus().getCircuitsEstablished() < minExpectedIdleCircuits)) {
      try {
        Thread.sleep(100L);
      } catch (Exception e) {
        LOG.debug("got Exception : {}", e.getMessage(), e);
      }
    }
  }
  





  public HashSet<Circuit> getCurrentCircuits()
  {
    HashSet<Circuit> allCircs = new HashSet();
    for (TLSConnection tls : tlsConnectionAdmin.getConnections()) {
      for (Circuit circuit : tls.getCircuits())
      {
        allCircs.add(circuit);
      }
    }
    
    return allCircs;
  }
  



  public CircuitsStatus getCircuitsStatus()
  {
    int circuitsTotal = 0;
    int circuitsAlive = 0;
    int circuitsEstablished = 0;
    int circuitsClosed = 0;
    
    for (TLSConnection tls : tlsConnectionAdmin.getConnections()) {
      for (Circuit c : tls.getCircuits()) {
        String flag = "";
        circuitsTotal++;
        if (c.isClosed()) {
          flag = "C";
          circuitsClosed++;
        } else {
          flag = "B";
          circuitsAlive++;
          if (c.isEstablished()) {
            flag = "E";
            circuitsEstablished++;
          }
        }
      }
    }
    






    CircuitsStatus result = new CircuitsStatus();
    result.setCircuitsTotal(circuitsTotal);
    result.setCircuitsAlive(circuitsAlive);
    result.setCircuitsEstablished(circuitsEstablished);
    result.setCircuitsClosed(circuitsClosed);
    
    return result;
  }
  


  public void clear()
  {
    CircuitAdmin.clear(tlsConnectionAdmin);
  }
  



  public TorEventService getTorEventService()
  {
    return torEventService;
  }
  


  public Directory getDirectory()
  {
    return directory;
  }
  
  public TLSConnectionAdmin getTlsConnectionAdmin() {
    return tlsConnectionAdmin;
  }
  
  public NetLayer getLowerTlsConnectionNetLayer() {
    return lowerTlsConnectionNetLayer;
  }
  
  public NetLayer getLowerDirConnectionNetLayer() {
    return lowerDirConnectionNetLayer;
  }
}
