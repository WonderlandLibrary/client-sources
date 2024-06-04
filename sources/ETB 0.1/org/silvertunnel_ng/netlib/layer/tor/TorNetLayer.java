package org.silvertunnel_ng.netlib.layer.tor;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.silvertunnel_ng.netlib.api.NetAddress;
import org.silvertunnel_ng.netlib.api.NetAddressNameService;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetLayerStatus;
import org.silvertunnel_ng.netlib.api.NetServerSocket;
import org.silvertunnel_ng.netlib.api.NetSocket;
import org.silvertunnel_ng.netlib.api.util.TcpipNetAddress;
import org.silvertunnel_ng.netlib.layer.control.ControlNetLayer;
import org.silvertunnel_ng.netlib.layer.control.ControlParameters;
import org.silvertunnel_ng.netlib.layer.tor.api.Router;
import org.silvertunnel_ng.netlib.layer.tor.circuit.Circuit;
import org.silvertunnel_ng.netlib.layer.tor.clientimpl.Tor;
import org.silvertunnel_ng.netlib.layer.tor.common.TCPStreamProperties;
import org.silvertunnel_ng.netlib.layer.tor.common.TorConfig;
import org.silvertunnel_ng.netlib.layer.tor.directory.Directory;
import org.silvertunnel_ng.netlib.layer.tor.directory.FingerprintImpl;
import org.silvertunnel_ng.netlib.layer.tor.hiddenservice.HiddenServiceProperties;
import org.silvertunnel_ng.netlib.layer.tor.stream.TCPStream;
import org.silvertunnel_ng.netlib.nameservice.cache.CachingNetAddressNameService;
import org.silvertunnel_ng.netlib.nameservice.tor.TorNetAddressNameService;
import org.silvertunnel_ng.netlib.util.DatatypeConverter;
import org.silvertunnel_ng.netlib.util.StringStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





































public class TorNetLayer
  implements NetLayer
{
  private static final Logger LOG = LoggerFactory.getLogger(TorNetLayer.class);
  


  private final transient Tor tor;
  


  private transient NetAddressNameService netAddressNameService;
  

  private static final String EXIT = "exit";
  

  private static final Pattern EXIT_PATTERN = Pattern.compile("(.*)\\.([^\\.]+)\\.exit");
  






  private final NetLayer thisTorNetLayerWithTimeoutControl;
  






  public TorNetLayer(NetLayer lowerTlsConnectionNetLayer, NetLayer lowerDirConnectionNetLayer, StringStorage stringStorage)
    throws IOException
  {
    this(new Tor(lowerTlsConnectionNetLayer, lowerDirConnectionNetLayer, stringStorage));
  }
  
  public TorNetLayer(Tor tor) throws IOException {
    this.tor = tor;
    


    ControlParameters cp = ControlParameters.createTypicalFileTransferParameters();
    cp.setConnectTimeoutMillis(TorConfig.getDirConnectTimeout());
    cp.setOverallTimeoutMillis(TorConfig.getDirOverallTimeout());
    cp.setInputMaxBytes(52428800L);
    cp.setThroughputTimeframeMinBytes(15360L);
    cp.setThroughputTimeframeMillis(15000L);
    thisTorNetLayerWithTimeoutControl = new ControlNetLayer(this, cp);
  }
  






  public NetSocket createNetSocket(Map<String, Object> localProperties, NetAddress localAddress, NetAddress remoteAddress)
    throws IOException
  {
    TcpipNetAddress ra = (TcpipNetAddress)remoteAddress;
    

    TCPStreamProperties sp = convertTcpipNetAddress2TCPStreamProperties(ra);
    


    if (tor.getDirectory().isDirServer(sp)) {
      sp.setExitPolicyRequired(false);
      

      sp.setConnectToDirServer(true);
      
      String[] octets = sp.getHostname().split("\\.");
      byte[] ip = new byte[4];
      ip[0] = ((byte)Integer.parseInt(octets[0]));
      ip[1] = ((byte)Integer.parseInt(octets[1]));
      ip[2] = ((byte)Integer.parseInt(octets[2]));
      ip[3] = ((byte)Integer.parseInt(octets[3]));
      sp.setMinRouteLength(1);
      sp.setMaxRouteLength(1);
      sp.setCustomExitpoint(tor.getDirectory().getValidRouterByIpAddressAndDirPort(new TcpipNetAddress(ip, sp.getPort()))
        .getFingerprint());
      sp.setCustomRoute(new FingerprintImpl[] { (FingerprintImpl)sp.getCustomExitpoint() });
    }
    try {
      TCPStream remote = tor.connect(sp, thisTorNetLayerWithTimeoutControl);
      return new TorNetSocket(remote, "TorNetLayer connection to " + ra);
    } catch (Throwable throwable) {
      throw new IOException(throwable);
    }
  }
  
  private TCPStreamProperties convertTcpipNetAddress2TCPStreamProperties(TcpipNetAddress ra) {
    TCPStreamProperties sp = new TCPStreamProperties(ra);
    






    String hostname = ra.getHostname();
    if (hostname != null) {
      hostname = hostname.toLowerCase();
      Matcher m = EXIT_PATTERN.matcher(hostname);
      if (m.find())
      {

        if (LOG.isDebugEnabled()) {
          LOG.debug("hostname with .exit pattern={}", hostname);
        }
        String originalHostname = m.group(1);
        String exitNodeNameOrDigest = m.group(2);
        if (LOG.isDebugEnabled()) {
          LOG.debug("originalHostname=" + originalHostname);
          LOG.debug("exitNodeNameOrDigest=" + exitNodeNameOrDigest);
        }
        

        TcpipNetAddress raNew = new TcpipNetAddress(originalHostname, ra.getPort());
        sp = new TCPStreamProperties(raNew);
        

        sp.setCustomExitpoint(new FingerprintImpl(DatatypeConverter.parseHexBinary(exitNodeNameOrDigest)));
      }
    }
    
    return sp;
  }
  

  public NetServerSocket createNetServerSocket(Map<String, Object> properties, NetAddress localListenAddress)
    throws IOException
  {
    try
    {
      TorHiddenServicePortPrivateNetAddress netAddress = (TorHiddenServicePortPrivateNetAddress)localListenAddress;
      TorNetServerSocket torNetServerSocket = new TorNetServerSocket(netAddress.getPublicOnionHostname(), netAddress.getPort());
      
      NetLayer torNetLayerToConnectToDirectoryService = this;
      
      HiddenServiceProperties hiddenServiceProps = new HiddenServiceProperties(netAddress.getPort(), netAddress.getTorHiddenServicePrivateNetAddress().getKeyPair());
      tor.provideHiddenService(torNetLayerToConnectToDirectoryService, hiddenServiceProps, torNetServerSocket);
      
      return torNetServerSocket;
    }
    catch (Exception e) {
      String msg = "could not create NetServerSocket for localListenAddress=" + localListenAddress;
      LOG.error("could not create NetServerSocket", e);
      throw new IOException(msg);
    }
  }
  



  public NetLayerStatus getStatus()
  {
    return tor.getStatus();
  }
  




  public void waitUntilReady()
  {
    tor.checkStartup();
  }
  


  public void clear()
    throws IOException
  {
    LOG.info("clear() started");
    tor.clear();
    LOG.info("clear() finished");
  }
  



  public NetAddressNameService getNetAddressNameService()
  {
    if (netAddressNameService == null)
    {
      netAddressNameService = new CachingNetAddressNameService(new TorNetAddressNameService(tor) {});
    }
    



    return netAddressNameService;
  }
  






  public Collection<Router> getValidTorRouters()
  {
    waitUntilReady();
    return tor.getValidTorRouters();
  }
  




  public final void changeIdentity()
  {
    Iterator<Circuit> itCircuits = tor.getCurrentCircuits().iterator();
    while (itCircuits.hasNext()) {
      Circuit circuit = (Circuit)itCircuits.next();
      if ((circuit != null) && (!circuit.isUnused()) && (!circuit.isClosed())) {
        circuit.close(false);
      }
    }
  }
}
