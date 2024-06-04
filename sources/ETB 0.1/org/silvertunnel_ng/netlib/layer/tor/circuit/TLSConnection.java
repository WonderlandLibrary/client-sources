package org.silvertunnel_ng.netlib.layer.tor.circuit;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.TrustManager;
import org.silvertunnel_ng.netlib.api.NetAddress;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetSocket;
import org.silvertunnel_ng.netlib.api.util.TcpipNetAddress;
import org.silvertunnel_ng.netlib.layer.tor.api.Router;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.Cell;
import org.silvertunnel_ng.netlib.layer.tor.common.TorX509TrustManager;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;








































public class TLSConnection
{
  private static final Logger LOG = LoggerFactory.getLogger(TLSConnection.class);
  

  private static final String enabledSuitesStr = "SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA,TLS_DHE_RSA_WITH_AES_128_CBC_SHA";
  

  private Router router;
  

  private final NetSocket tls;
  

  private boolean closed = false;
  
  private final TLSDispatcherThread dispatcher;
  
  private final DataOutputStream sout;
  
  private final Map<Integer, Circuit> circuitMap = Collections.synchronizedMap(new HashMap());
  










  TLSConnection(Router server, NetLayer lowerNetLayer)
    throws IOException, SSLPeerUnverifiedException, SSLException
  {
    if (server == null) {
      throw new IOException("TLSConnection: server variable is NULL");
    }
    router = server;
    









    TrustManager[] tms = { new TorX509TrustManager() };
    

    Map<String, Object> props = new HashMap();
    props.put("TLSNetLayer.enabledCipherSuites", "SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA,TLS_DHE_RSA_WITH_AES_128_CBC_SHA");
    props.put("TLSNetLayer.TrustManagers", tms);
    NetAddress remoteAddress = new TcpipNetAddress(server.getHostname(), server.getOrPort());
    NetAddress localAddress = null;
    tls = lowerNetLayer.createNetSocket(props, localAddress, remoteAddress);
    





















    sout = new DataOutputStream(tls.getOutputStream());
    
    dispatcher = new TLSDispatcherThread(this, new DataInputStream(tls.getInputStream()));
  }
  





  synchronized void sendCell(Cell cell)
    throws IOException
  {
    try
    {
      sout.write(cell.toByteArray());
    } catch (IOException exception) {
      LOG.debug("error while sending data Exception : {}", exception, exception);
      
      close(true);
      
      throw exception;
    }
  }
  










  synchronized int assignCircuitId(Circuit circuit)
    throws TorException
  {
    if (closed)
    {

      throw new TorException("TLSConnection.assignCircuitId(): Connection to " + router.getNickname() + " is closed for new circuits");
    }
    

    int newId = 0;
    int j = 0;
    while (newId == 0) {
      j++; if (j > 1000) {
        throw new TorException("TLSConnection.assignCircuitId(): no more free IDs");
      }
      








      newId = TLSConnectionAdmin.RANDOM.nextInt() & 0xFFFF;
      
      if (circuitMap.containsKey(Integer.valueOf(newId))) {
        newId = 0;
      }
    }
    

    circuitMap.put(Integer.valueOf(newId), circuit);
    return newId;
  }
  






  void close(boolean force)
  {
    LOG.debug("Closing TLS to {}", router.getNickname());
    
    closed = true;
    

    Collection<Circuit> circuits;
    

    synchronized (circuitMap) {
      circuits = new ArrayList(circuitMap.values());
    }
    for (??? = circuits.iterator(); ???.hasNext();) { Circuit circuit = (Circuit)???.next();
      if (circuit.close(force)) {
        removeCircuit(Integer.valueOf(circuit.getId()));
      }
    }
    
    LOG.debug("Fast exit while closing TLS to {}?", router.getNickname());
    if ((!force) && (!circuitMap.isEmpty())) {
      LOG.debug("Fast exit while closing TLS to {}!", router.getNickname());
      return;
    }
    

    LOG.debug("Closing dispatcher of TLS to {}", router.getNickname());
    dispatcher.close();
    

    LOG.debug("Closing TLS connection to {}", router.getNickname());
    try {
      sout.close();
      tls.close();
    } catch (IOException e) {
      LOG.debug("got IOException : {}", e.getMessage(), e);
    }
    LOG.debug("Closing TLS to {} done", router.getNickname());
  }
  
  public String toString()
  {
    return "TLS to " + router.getNickname();
  }
  



  public Router getRouter()
  {
    return router;
  }
  
  public void setRouter(Router router) {
    this.router = router;
  }
  
  public Collection<Circuit> getCircuits() {
    synchronized (circuitMap) {
      return new ArrayList(circuitMap.values());
    }
  }
  
  public Map<Integer, Circuit> getCircuitMap() {
    synchronized (circuitMap) {
      return new HashMap(circuitMap);
    }
  }
  
  public Circuit getCircuit(Integer circuitId) {
    synchronized (circuitMap) {
      return (Circuit)circuitMap.get(circuitId);
    }
  }
  





  public boolean removeCircuit(Integer circuitId)
  {
    LOG.debug("remove circuit with circuitId={} from {}", circuitId, toString());
    
    boolean result;
    
    boolean doClose;
    synchronized (circuitMap) {
      result = circuitMap.remove(circuitId) != null;
      doClose = circuitMap.size() == 0;
    }
    

    if (doClose)
    {
      if (LOG.isDebugEnabled()) {
        LOG.debug("close TLSConnection from {} because last Circuit is removed", toString());
      }
      close(true);
    }
    else {
      synchronized (circuitMap) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("cannot close TLSConnection from " + toString() + " because of additional circuits: " + circuitMap);
        }
      }
    }
    
    if (LOG.isDebugEnabled()) {
      LOG.debug("remove circuit from " + toString() + " done with result=" + result);
    }
    
    return result;
  }
  
  public boolean isClosed() {
    return closed;
  }
}
