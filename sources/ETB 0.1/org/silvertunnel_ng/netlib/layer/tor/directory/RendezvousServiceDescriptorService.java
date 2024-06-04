package org.silvertunnel_ng.netlib.layer.tor.directory;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetSocket;
import org.silvertunnel_ng.netlib.api.util.TcpipNetAddress;
import org.silvertunnel_ng.netlib.layer.tor.api.Fingerprint;
import org.silvertunnel_ng.netlib.layer.tor.api.Router;
import org.silvertunnel_ng.netlib.layer.tor.common.TorConfig;
import org.silvertunnel_ng.netlib.layer.tor.hiddenservice.HiddenServiceProperties;
import org.silvertunnel_ng.netlib.layer.tor.util.Encoding;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;
import org.silvertunnel_ng.netlib.tool.SimpleHttpClient;
import org.silvertunnel_ng.netlib.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





































































public class RendezvousServiceDescriptorService
{
  private static final Logger LOG = LoggerFactory.getLogger(RendezvousServiceDescriptorService.class);
  
  private static RendezvousServiceDescriptorService instance = new RendezvousServiceDescriptorService();
  



  private final HttpUtil httpUtil = HttpUtil.getInstance();
  
  private static final int RENDEZVOUS_NUMBER_OF_NON_CONSECUTIVE_REPLICAS = 2;
  

  public RendezvousServiceDescriptorService() {}
  
  public static RendezvousServiceDescriptorService getInstance()
  {
    return instance;
  }
  









  public RendezvousServiceDescriptor loadRendezvousServiceDescriptorFromDirectory(String z, Directory directory, NetLayer torNetLayer)
    throws IOException
  {
    String hiddenServicePermanentIdBase32 = z;
    Long now = Long.valueOf(System.currentTimeMillis());
    String PRE = "loadRendezvousServiceDescriptorFromDirectory(): ";
    
    int attempts = TorConfig.getRetriesConnect();
    while (attempts > 0) {
      for (int replica = 0; replica < 2; replica++)
      {
        byte[] descriptorId = RendezvousServiceDescriptorUtil.getRendezvousDescriptorId(hiddenServicePermanentIdBase32, replica, now).getDescriptorId();
        String descriptorIdBase32 = Encoding.toBase32(descriptorId);
        String descriptorIdHex = Encoding.toHexStringNoColon(descriptorId);
        Fingerprint descriptorIdAsFingerprint = new FingerprintImpl(descriptorId);
        


        Collection<Router> routers = directory.getThreeHiddenDirectoryServersWithFingerprintGreaterThan(descriptorIdAsFingerprint);
        for (Router r : routers) {
          TcpipNetAddress dirAddress = r.getDirAddress();
          dirAddress = new TcpipNetAddress(dirAddress.getHostnameOrIpaddress() + ":" + dirAddress.getPort());
          LOG.info("loadRendezvousServiceDescriptorFromDirectory(): try fetching service descriptor for " + z + " with descriptorID base32/hex=" + descriptorIdBase32 + "/" + descriptorIdHex + " (with replica=" + replica + ") from " + r);
          



          String response = null;
          try {
            response = retrieveServiceDescriptor(torNetLayer, dirAddress, descriptorIdBase32);
          } catch (Exception e) {
            LOG.warn("unable to connect to or to load data from directory server " + r + "(" + e.getMessage() + ")", e); }
          continue;
          


          if (LOG.isDebugEnabled()) {
            LOG.debug("loadRendezvousServiceDescriptorFromDirectory(): found descriptorIdBase32=" + descriptorIdBase32 + " with result(plain)=" + response);
          }
          try {
            return new RendezvousServiceDescriptor(response, Long.valueOf(System.currentTimeMillis()));
          } catch (TorException e) {
            LOG.info("loadRendezvousServiceDescriptorFromDirectory(): problem parsing Service Descriptor for " + z, e);
          }
        }
        
        attempts--;
      }
    }
    LOG.warn("loadRendezvousServiceDescriptorFromDirectory(): unable to fetch service descriptor for " + z);
    throw new IOException("unable to fetch service descriptor for " + z);
  }
  












  public void putRendezvousServiceDescriptorToDirectory(Directory directory, final NetLayer torNetLayerToConnectToDirectoryService, HiddenServiceProperties hiddenServiceProps)
    throws IOException, TorException
  {
    final String hiddenServicePermanentIdBase32 = RendezvousServiceDescriptorUtil.calculateZFromPublicKey(hiddenServiceProps.getPublicKey());
    Long now = Long.valueOf(System.currentTimeMillis());
    String PRE = "putRendezvousServiceDescriptorToDirectory(): ";
    

    final AtomicInteger advertiseSuccess = new AtomicInteger(0);
    for (int replica = 0; replica < 2; replica++)
    {

      try
      {


        sd = new RendezvousServiceDescriptor(hiddenServicePermanentIdBase32, replica, now.longValue(), hiddenServiceProps.getPublicKey(), hiddenServiceProps.getPrivateKey(), hiddenServiceProps.getIntroPoints());
        byte[] descriptorId = sd.getDescriptorId();
        descriptorIdBase32 = Encoding.toBase32(descriptorId);
        descriptorIdHex = Encoding.toHexStringNoColon(descriptorId);
        Fingerprint descriptorIdAsFingerprint = new FingerprintImpl(descriptorId);
        replicaFinal = replica;
        



        Collection<Router> routers = directory.getThreeHiddenDirectoryServersWithFingerprintGreaterThan(descriptorIdAsFingerprint);
        for (Router ro : routers) {
          final Router r = ro;
          new Thread()
          {
            public void run() {
              TcpipNetAddress dirAddress = r.getDirAddress();
              dirAddress = new TcpipNetAddress(dirAddress.getHostnameOrIpaddress() + ":" + dirAddress.getPort());
              RendezvousServiceDescriptorService.LOG.info("putRendezvousServiceDescriptorToDirectory(): try putting service descriptor for " + hiddenServicePermanentIdBase32 + " with descriptorID base32/hex=" + descriptorIdBase32 + "/" + descriptorIdHex + " (with replica=" + replicaFinal + ") from " + r);
              


              for (int attempts = 0; attempts < TorConfig.getRetriesConnect(); attempts++)
                try {
                  RendezvousServiceDescriptorService.this.postServiceDescriptor(torNetLayerToConnectToDirectoryService, dirAddress, sd);
                  advertiseSuccess.addAndGet(1);
                  
                  return;
                } catch (Exception e) {
                  RendezvousServiceDescriptorService.LOG.warn("putRendezvousServiceDescriptorToDirectory(): unable to connect to directory server " + dirAddress + "(" + e.getMessage() + ")");
                }
            }
          }.start();
        } } catch (TorException e1) { final RendezvousServiceDescriptor sd;
        final String descriptorIdBase32;
        final String descriptorIdHex;
        final int replicaFinal;
        LOG.warn("unexpected exception", e1);
      }
    }
    

    int TIMEOUT_SECONDS = 120;
    int MIN_NUMBER_OF_ADVERTISEMENTS = 1;
    for (int seconds = 0; (seconds < 120) && (advertiseSuccess.get() < 1); seconds++) {
      try
      {
        Thread.sleep(1000L);
      } catch (InterruptedException e) {
        LOG.debug("got IterruptedException : {}", e.getMessage(), e);
      }
    }
    

    if (advertiseSuccess.get() < 1) {
      throw new TorException("RendezvousServiceDescriptorService: no successful hidden service descriptor advertisement");
    }
  }
  










  private String retrieveServiceDescriptor(NetLayer torNetLayer, TcpipNetAddress dirNetAddress, String descriptorIdBase32)
    throws Exception
  {
    try
    {
      LOG.debug("retrieveServiceDescriptor() from {}", dirNetAddress);
      LOG.debug("descriptorId : {}", descriptorIdBase32);
      String path = "/tor/rendezvous2/" + descriptorIdBase32;
      
      return SimpleHttpClient.getInstance().get(torNetLayer, dirNetAddress, path);
    } catch (Exception e) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("retrieveServiceDescriptor() from {} failed", dirNetAddress, e);
      }
      throw e;
    }
  }
  











  private void postServiceDescriptor(NetLayer torNetLayerToConnectToDirectoryService, TcpipNetAddress dirNetAddress, RendezvousServiceDescriptor sd)
    throws IOException, TorException
  {
    String pathOnHttpServer = "/tor/rendezvous2/publish";
    long timeoutInMs = 60000L;
    

    NetSocket netSocket = torNetLayerToConnectToDirectoryService.createNetSocket(null, null, dirNetAddress);
    httpUtil.post(netSocket, dirNetAddress, "/tor/rendezvous2/publish", sd.toByteArray(), 60000L);
  }
}
