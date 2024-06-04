package org.silvertunnel_ng.netlib.layer.tor.clientimpl;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.util.TcpipNetAddress;
import org.silvertunnel_ng.netlib.layer.tor.api.Fingerprint;
import org.silvertunnel_ng.netlib.layer.tor.api.Router;
import org.silvertunnel_ng.netlib.layer.tor.circuit.Circuit;
import org.silvertunnel_ng.netlib.layer.tor.circuit.CircuitAdmin;
import org.silvertunnel_ng.netlib.layer.tor.circuit.HiddenServiceInstance;
import org.silvertunnel_ng.netlib.layer.tor.circuit.HiddenServicePortInstance;
import org.silvertunnel_ng.netlib.layer.tor.circuit.Node;
import org.silvertunnel_ng.netlib.layer.tor.circuit.TLSConnectionAdmin;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.CellRelayEstablishIntro;
import org.silvertunnel_ng.netlib.layer.tor.common.TCPStreamProperties;
import org.silvertunnel_ng.netlib.layer.tor.common.TorEventService;
import org.silvertunnel_ng.netlib.layer.tor.directory.Directory;
import org.silvertunnel_ng.netlib.layer.tor.directory.RendezvousServiceDescriptorService;
import org.silvertunnel_ng.netlib.layer.tor.directory.RendezvousServiceDescriptorUtil;
import org.silvertunnel_ng.netlib.layer.tor.directory.SDIntroductionPoint;
import org.silvertunnel_ng.netlib.layer.tor.hiddenservice.HiddenServiceProperties;
import org.silvertunnel_ng.netlib.layer.tor.util.Encoding;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;























public class HiddenServiceServer
{
  private static final Logger LOG = LoggerFactory.getLogger(HiddenServiceServer.class);
  




  private static Map<String, HiddenServiceInstance> allHiddenServices = new HashMap();
  
  private static HiddenServiceServer instance = new HiddenServiceServer();
  
  public HiddenServiceServer() {}
  
  public static HiddenServiceServer getInstance() { return instance; }
  

















  public void provideHiddenService(final Directory directory, final TorEventService torEventService, final TLSConnectionAdmin tlsConnectionAdmin, NetLayer torNetLayerToConnectToDirectoryService, final HiddenServiceProperties hiddenServiceProps, HiddenServicePortInstance hiddenServicePortInstance)
    throws IOException, TorException
  {
    HiddenServiceInstance hiddenServiceInstance = null;
    String hiddenServicePermanentIdBase32 = RendezvousServiceDescriptorUtil.calculateZFromPublicKey(hiddenServiceProps.getPublicKey());
    synchronized (allHiddenServices)
    {
      hiddenServiceInstance = (HiddenServiceInstance)allHiddenServices.get(hiddenServicePermanentIdBase32);
      if (hiddenServiceInstance == null)
      {

        hiddenServiceInstance = new HiddenServiceInstance(hiddenServiceProps);
        allHiddenServices.put(hiddenServicePermanentIdBase32, hiddenServiceInstance);
        
        hiddenServiceInstance.putHiddenServicePortInstance(hiddenServicePortInstance);

      }
      else
      {

        hiddenServiceInstance.putHiddenServicePortInstance(hiddenServicePortInstance);
      }
    }
    






    ExecutorService executor = Executors.newCachedThreadPool();
    while (hiddenServiceProps.getNumberOfIntroPoints() < hiddenServiceProps.getMinimumNumberOfIntroPoints())
    {
      LOG.debug("establish circuits to (randomly chosen) introduction points for {}", hiddenServicePortInstance);
      

      Object allTasks = new ArrayList();
      int TRY_MORE_NUMBER_OF_INTRO_POINTS = 2;
      for (int i = hiddenServiceProps.getNumberOfIntroPoints(); i < hiddenServiceProps.getMinimumNumberOfIntroPoints() + 2; 
          i++)
      {
        final HiddenServiceInstance hiddenServiceInstanceFinal = hiddenServiceInstance;
        Callable<Circuit> callable = new Callable()
        {

          public Circuit call()
            throws Exception
          {
            HiddenServiceServer.LOG.debug("Callable Started..");
            TCPStreamProperties spIntro = new TCPStreamProperties();
            spIntro.setExitPolicyRequired(false);
            





            Circuit result = null;
            try {
              result = HiddenServiceServer.this.establishIntroductionPoint(directory, torEventService, tlsConnectionAdmin, hiddenServiceProps, spIntro, hiddenServiceInstanceFinal);


            }
            catch (Throwable throwable)
            {

              HiddenServiceServer.LOG.warn("got Exception", throwable);
            }
            HiddenServiceServer.LOG.debug("Callable Finished!");
            return result;
          }
        };
        ((Collection)allTasks).add(callable);
      }
      

      LOG.debug("start to execute the tasks in parallel");
      int TIMEOUT_SECONDS = 120;
      Collection<Future<Circuit>> allTaskResults = null;
      try
      {
        allTaskResults = executor.invokeAll((Collection)allTasks, 120L, TimeUnit.SECONDS);
      }
      catch (Exception e)
      {
        LOG.info("Exception in background task", e);
      }
      

      for (Future<Circuit> taskResult : allTaskResults)
      {
        try
        {
          LOG.debug("analyse taskResult={}", taskResult);
          Circuit c = (Circuit)taskResult.get();
          if (c != null)
          {
            Router introPointRouter = c.getRouteNodes()[(c.getRouteEstablished() - 1)].getRouter();
            LOG.info("Tor.provideHiddenService: establish introduction point at " + introPointRouter.getNickname());
            hiddenServiceProps.addIntroPoint(new SDIntroductionPoint(Encoding.toBase32(introPointRouter.getFingerprint().getBytes()), new TcpipNetAddress(introPointRouter
              .getAddress().getAddress(), introPointRouter
              .getOrPort()), introPointRouter
              .getOnionKey(), hiddenServiceProps
              .getPublicKey()));
          }
          
        }
        catch (InterruptedException e)
        {
          LOG.debug("task interruped");
        }
        catch (Exception e)
        {
          LOG.info("in background task", e);
        }
      }
      LOG.info("(server side) circuit(s) to hidden service introduction point(s)==" + hiddenServiceProps.getIntroPoints() + " established for " + hiddenServicePortInstance);
    }
    
    executor.shutdown();
    LOG.debug("establish circuits finished introduction points for {}", hiddenServicePortInstance);
    




    RendezvousServiceDescriptorService.getInstance().putRendezvousServiceDescriptorToDirectory(directory, torNetLayerToConnectToDirectoryService, hiddenServiceProps);
  }
  













  private Circuit establishIntroductionPoint(Directory directory, TorEventService torEventService, TLSConnectionAdmin tlsConnectionAdmin, HiddenServiceProperties service, TCPStreamProperties spIntro, HiddenServiceInstance hiddenServiceInstance)
    throws Throwable
  {
    Circuit circuit = null;
    for (int i = 0; i < spIntro.getConnectRetries(); i++)
    {
      try
      {

        circuit = CircuitAdmin.provideSuitableExclusiveCircuit(tlsConnectionAdmin, directory, spIntro, torEventService);
        if ((circuit == null) || (!circuit.isEstablished()))
        {
          LOG.warn("could not establish Circuit to introduction point with spIntro=" + spIntro);
          Thread.sleep(5000L);

        }
        else
        {
          circuit.setHiddenServiceInstanceForIntroduction(hiddenServiceInstance);
          
          LOG.debug("Tor.provideHiddenService: send relay_establish_intro-Cell over {}", circuit.toString());
          circuit.sendCell(new CellRelayEstablishIntro(circuit, service));
          circuit.receiveRelayCell(38);
          return circuit;
        }
      }
      catch (Exception e)
      {
        LOG.warn("Tor.provideHiddenService: " + e.getMessage(), e);
        if (circuit != null)
        {
          circuit.close(true);
        }
      }
    }
    return null;
  }
}
