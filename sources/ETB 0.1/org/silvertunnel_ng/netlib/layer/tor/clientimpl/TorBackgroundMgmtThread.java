package org.silvertunnel_ng.netlib.layer.tor.clientimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import org.silvertunnel_ng.netlib.api.NetLayerStatus;
import org.silvertunnel_ng.netlib.layer.tor.api.TorNetLayerStatus;
import org.silvertunnel_ng.netlib.layer.tor.circuit.Circuit;
import org.silvertunnel_ng.netlib.layer.tor.circuit.CircuitsStatus;
import org.silvertunnel_ng.netlib.layer.tor.circuit.Stream;
import org.silvertunnel_ng.netlib.layer.tor.circuit.TLSConnection;
import org.silvertunnel_ng.netlib.layer.tor.circuit.TLSConnectionAdmin;
import org.silvertunnel_ng.netlib.layer.tor.common.TCPStreamProperties;
import org.silvertunnel_ng.netlib.layer.tor.common.TorConfig;
import org.silvertunnel_ng.netlib.layer.tor.directory.Directory;
import org.silvertunnel_ng.netlib.layer.tor.directory.DirectoryManagerThread;
import org.silvertunnel_ng.netlib.layer.tor.stream.TCPStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;







































class TorBackgroundMgmtThread
  extends Thread
{
  private static final Logger LOG = LoggerFactory.getLogger(TorBackgroundMgmtThread.class);
  


  private static final int MILLISEC = 1000;
  


  private static final int INITIAL_INTERVAL_S = 3;
  


  protected static final int INTERVAL_S = 3;
  


  private static final int CIRCUITS_KEEP_ALIVE_INTERVAL_S = 30;
  


  private static final int STREAMS_KEEP_ALIVE_INTERVAL_S = 30;
  

  private static long idleThreadCounter = 0L;
  



  private final Tor tor;
  


  private long currentTimeMillis;
  


  private final List<Thread> backgroundThreads;
  


  private boolean stopped = false;
  

  private final DirectoryManagerThread directoryManagerThread;
  


  TorBackgroundMgmtThread(Tor tor)
  {
    backgroundThreads = new ArrayList(TorConfig.getMinimumIdleCircuits());
    this.tor = tor;
    currentTimeMillis = System.currentTimeMillis();
    spawnIdleCircuits(TorConfig.getMinimumIdleCircuits());
    directoryManagerThread = new DirectoryManagerThread(tor.getDirectory());
    setName(getClass().getName());
    setDaemon(true);
    start();
  }
  




  private void spawnIdleCircuits(int amount)
  {
    if (tor.getDirectory().isDirectoryReady()) {
      if (amount > 0) {
        LOG.info("TorBackgroundMgmtThread.spawnIdleCircuits: Spawn {} new circuits", Integer.valueOf(amount));
      }
    } else {
      LOG.debug("Not yet spawning circuits (too few routers known until now)");
      return;
    }
    

    ListIterator<Thread> brtIterator = backgroundThreads.listIterator();
    while (brtIterator.hasNext()) {
      Thread brt = (Thread)brtIterator.next();
      if (!brt.isAlive()) {
        brtIterator.remove();
      }
    }
    

    if (amount > 0) {
      tor.updateStatus(TorNetLayerStatus.INITIAL_CIRCUITES_ESTABLISHING);
    }
    for (int i = 0; i < amount; i++) {
      Thread brt = new Thread()
      {
        public void run()
        {
          try
          {
            TCPStreamProperties sp = new TCPStreamProperties();
            sp.setFastRoute(true);
            sp.setPort(80);
            new Circuit(tor.getTlsConnectionAdmin(), 
              tor.getDirectory(), sp, 
              tor.getTorEventService(), null);
          } catch (Exception e) {
            TorBackgroundMgmtThread.LOG.debug("TorBackgroundMgmtThread.spawnIdleCircuits got Exception: {}", e.getMessage(), e);
          }
        }
      };
      LOG.debug("TorBackgroundMgmtThread.spawnIdleCircuits: Circuit-Spawning thread started.");
      brt.setName("Idle Thread " + idleThreadCounter++);
      brt.start();
      backgroundThreads.add(brt);
    }
  }
  


  private void sendKeepAlivePackets()
  {
    for (TLSConnection tls : tor.getTlsConnectionAdmin().getConnections()) {
      for (Circuit c : tls.getCircuits())
      {
        if ((c.isEstablished()) && (currentTimeMillis - c.getLastCell() > 30000L)) {
          if (LOG.isDebugEnabled()) {
            LOG.debug("TorBackgroundMgmtThread.sendKeepAlivePackets(): Circuit " + c.toString());
          }
          c.sendKeepAlive();
        }
        
        for (Stream streamX : c.getStreams().values()) {
          TCPStream stream = (TCPStream)streamX;
          if ((stream.isEstablished()) && 
            (!stream.isClosed()))
          {
            if (currentTimeMillis - stream.getLastCellSentDate() > 30000L)
            {
              if (LOG.isDebugEnabled()) {
                LOG.debug("TorBackgroundMgmt.sendKeepAlivePackets(): Stream " + stream
                  .toString());
              }
              stream.sendKeepAlive();
            }
          }
        }
      }
    }
  }
  


  private void manageIdleCircuits()
  {
    CircuitsStatus circuitsStatus = tor.getCircuitsStatus();
    
    if (LOG.isDebugEnabled()) {
      LOG.debug("TorBackgroundMgmt.manageIdleCircuits(): circuit counts: " + (circuitsStatus
        .getCircuitsAlive() - circuitsStatus
        .getCircuitsEstablished()) + " building, " + circuitsStatus
        .getCircuitsEstablished() + " established + " + circuitsStatus
        .getCircuitsClosed() + " closed = " + circuitsStatus
        .getCircuitsTotal());
    }
    
    if (circuitsStatus.getCircuitsAlive() + Circuit.numberOfCircuitsInConstructor < TorConfig.getMinimumIdleCircuits()) {
      spawnIdleCircuits((TorConfig.getMinimumIdleCircuits() - circuitsStatus.getCircuitsAlive()) * 3 / 2);
    } else if (circuitsStatus.getCircuitsEstablished() > TorConfig.getMinimumIdleCircuits() + TorConfig.circuitsMaximumNumber)
    {
      if (LOG.isDebugEnabled()) {
        LOG.debug("TorBackgroundMgmtThread.manageIdleCircuits(): kill " + (
          TorConfig.getMinimumIdleCircuits() + TorConfig.circuitsMaximumNumber - circuitsStatus
          .getCircuitsAlive()) + "new circuits (FIXME)");
      }
    }
  }
  



  private void tearDownClosedCircuits()
  {
    for (Iterator localIterator1 = tor.getTlsConnectionAdmin().getConnections().iterator(); localIterator1.hasNext();) { tls = (TLSConnection)localIterator1.next();
      if (tls.isClosed()) {
        LOG.debug("remove tls={}", tls);
        tor.getTlsConnectionAdmin().removeConnection(tls);
      }
      for (Circuit c : tls.getCircuitMap().values())
      {

        for (Stream streamX : c.getStreams().values()) {
          TCPStream s = (TCPStream)streamX;
          long diff = (currentTimeMillis - s.getLastAction()) / 1000L;
          if ((!s.isEstablished()) || (s.isClosed())) {
            if (diff > 2 * TorConfig.queueTimeoutStreamBuildup)
            {
              LOG.debug("TorBackgroundMgmtThread.tearDownClosedCircuits(): closing stream (too long building) " + s
                .toString());
              s.close(true);
            } else {
              LOG.debug("Checked {} {}", Long.valueOf(diff), s.getRoute());
            }
          } else {
            LOG.debug("OK {} {}", Long.valueOf(diff), s.getRoute());
          }
        }
        

        if ((!c.isEstablished()) && (!c.isClosed()) && 
          ((currentTimeMillis - c.getLastAction()) / 1000L > 2 * TorConfig.queueTimeoutCircuit)) {
          LOG.debug("TorBackgroundMgmtThread.tearDownClosedCircuits(): closing (too long building) " + c
            .toString());
          c.close(false);
        }
        

        if (c.getEstablishedStreams() > TorConfig.getStreamsPerCircuit()) {
          LOG.debug("TorBackgroundMgmtThread.tearDownClosedCircuits(): closing (maximum streams) " + c
            .toString());
          c.close(false);
        }
        

        if (c.isClosed()) {
          c.close(false);
        }
        
        if (c.isDestruct()) {
          LOG.debug("TorBackgroundMgmtThread.tearDownClosedCircuits(): destructing circuit " + c.toString());
          tls.removeCircuit(Integer.valueOf(c.getId()));
        }
      }
    }
    TLSConnection tls;
  }
  
  public void close() {
    directoryManagerThread.setStopped();
    directoryManagerThread.interrupt();
    
    stopped = true;
    interrupt();
  }
  
  public void cleanup() {
    ListIterator<Thread> brtIterator = backgroundThreads.listIterator();
    while (brtIterator.hasNext()) {
      Thread brt = (Thread)brtIterator.next();
      if (brt.isAlive()) {
        brt.interrupt();
      }
      brtIterator.remove();
    }
  }
  
  public void run()
  {
    try {
      sleep(3000L);
    } catch (InterruptedException e) {
      LOG.debug("got IterruptedException : {}", e.getMessage(), e);
    }
    for (;;)
    {
      if (!stopped) {
        try {
          currentTimeMillis = System.currentTimeMillis();
          
          manageIdleCircuits();
          tearDownClosedCircuits();
          sendKeepAlivePackets();
          
          if (tor.getCircuitsStatus().getCircuitsEstablished() >= TorConfig.getMinimumIdleCircuits()) {
            tor.updateStatus(NetLayerStatus.READY);
          }
          
          sleep(3000L);
        } catch (InterruptedException e) {
          LOG.error("stop thread1", e);
        }
        catch (Exception e) {
          LOG.error("stop thread2", e);
        }
      }
    }
    cleanup();
  }
}
