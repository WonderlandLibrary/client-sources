package org.silvertunnel_ng.netlib.layer.tor.clientimpl;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.HashMap;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.layer.tor.api.Fingerprint;
import org.silvertunnel_ng.netlib.layer.tor.api.Router;
import org.silvertunnel_ng.netlib.layer.tor.circuit.Circuit;
import org.silvertunnel_ng.netlib.layer.tor.circuit.CircuitAdmin;
import org.silvertunnel_ng.netlib.layer.tor.circuit.Node;
import org.silvertunnel_ng.netlib.layer.tor.circuit.TLSConnectionAdmin;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.CellRelay;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.CellRelayEstablishRendezvous;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.CellRelayIntroduce1;
import org.silvertunnel_ng.netlib.layer.tor.common.TCPStreamProperties;
import org.silvertunnel_ng.netlib.layer.tor.common.TorEventService;
import org.silvertunnel_ng.netlib.layer.tor.directory.Directory;
import org.silvertunnel_ng.netlib.layer.tor.directory.RendezvousServiceDescriptor;
import org.silvertunnel_ng.netlib.layer.tor.directory.RendezvousServiceDescriptorService;
import org.silvertunnel_ng.netlib.layer.tor.directory.RouterImpl;
import org.silvertunnel_ng.netlib.layer.tor.directory.SDIntroductionPoint;
import org.silvertunnel_ng.netlib.layer.tor.hiddenservice.HiddenServiceDescriptorCache;
import org.silvertunnel_ng.netlib.layer.tor.stream.TCPStream;
import org.silvertunnel_ng.netlib.layer.tor.util.Encoding;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;













































public final class HiddenServiceClient
{
  private static final Logger LOG = LoggerFactory.getLogger(HiddenServiceClient.class);
  

  private static RendezvousServiceDescriptorService rendezvousServiceDescriptorService = RendezvousServiceDescriptorService.getInstance();
  









  public HiddenServiceClient() {}
  









  static TCPStream connectToHiddenService(Directory directory, TorEventService torEventService, TLSConnectionAdmin tlsConnectionAdmin, NetLayer torNetLayer, TCPStreamProperties spo)
    throws Throwable
  {
    String z = (String)Encoding.parseHiddenAddress(spo.getHostname()).get("z");
    



    RendezvousServiceDescriptor sd = HiddenServiceDescriptorCache.getInstance().get(z);
    if ((sd == null) || (!sd.isPublicationTimeValid()))
    {

      sd = rendezvousServiceDescriptorService.loadRendezvousServiceDescriptorFromDirectory(z, directory, torNetLayer);
      
      HiddenServiceDescriptorCache.getInstance().put(z, sd);
    }
    if (sd == null)
    {
      throw new IOException("connectToHiddenService(): couldn't retrieve RendezvousServiceDescriptor for z=" + z);
    }
    LOG.info("connectToHiddenService(): use RendezvousServiceDescriptor=" + sd);
    



    boolean establishedRendezvousPoint = false;
    boolean connectedToIntroPoint = false;
    boolean didRendezvous = false;
    for (int attempts = 0; attempts < spo.getConnectRetries(); attempts++)
    {
      Circuit rendezvousPointCircuit = null;
      



      try
      {
        rendezvousPointData = null;
        rendezvousPointData = createRendezvousPoint(directory, torEventService, tlsConnectionAdmin, z);
        rendezvousPointCircuit = rendezvousPointData.getMyRendezvousCirc();
        rendezvousPointCircuit.setServiceDescriptor(sd);
        establishedRendezvousPoint = true;
        LOG.info("connectToHiddenService(): use circuit to rendezvous point=" + rendezvousPointData.getMyRendezvousCirc());
        




        for (SDIntroductionPoint introPoint : sd.getIntroductionPoints())
        {
          try
          {
            Node introPointServicePublicKeyNode = sendIntroduction1Cell(directory, torEventService, tlsConnectionAdmin, rendezvousPointData, introPoint, z);
            




            connectedToIntroPoint = true;
            




            doRendezvous(rendezvousPointCircuit, introPointServicePublicKeyNode, z);
            didRendezvous = true;
            






            String hiddenServiceExternalAddress = "";
            TCPStreamProperties tcpProps = new TCPStreamProperties("", spo.getPort());
            return new TCPStream(rendezvousPointCircuit, tcpProps);
          }
          catch (TorException exception)
          {
            LOG.debug("got Exception while rendezvous", exception);
          }
        }
      }
      catch (Exception e) {
        RendezvousPointData rendezvousPointData;
        LOG.info("got Exception", e);
        
        if (rendezvousPointCircuit != null)
        {
          rendezvousPointCircuit.close(true);
          rendezvousPointCircuit = null;
        }
        
      }
      finally
      {
        if (rendezvousPointCircuit != null)
        {
          rendezvousPointCircuit.setCloseCircuitIfLastStreamIsClosed(true);
        }
      }
    }
    
    String msg;
    
    String msg;
    
    if (!establishedRendezvousPoint)
    {
      msg = "connectToHiddenService(): coudn't establishing rendezvous point for " + z;
    } else { String msg;
      if (!connectedToIntroPoint)
      {
        msg = "connectToHiddenService(): couldn't connect to an introduction point of " + z;
      } else { String msg;
        if (!didRendezvous)
        {
          msg = "connectToHiddenService(): coudn't make a rendezvous for " + z;
        }
        else
        {
          msg = "connectToHiddenService(): couldn't connect to remote server of " + z; }
      } }
    LOG.warn(msg);
    throw new IOException(msg);
  }
  














  private static RendezvousPointData createRendezvousPoint(Directory directory, TorEventService torEventService, TLSConnectionAdmin tlsConnectionAdmin, String z)
    throws Throwable
  {
    Circuit myRendezvousCirc = null;
    try
    {
      TCPStreamProperties streamProperties = new TCPStreamProperties();
      streamProperties.setFastRoute(true);
      streamProperties.setStableRoute(true);
      streamProperties.setExitPolicyRequired(false);
      myRendezvousCirc = CircuitAdmin.provideSuitableExclusiveCircuit(tlsConnectionAdmin, directory, streamProperties, torEventService);
      if ((myRendezvousCirc == null) || (!myRendezvousCirc.isEstablished()))
      {
        throw new TorException("getNewRendezvousPoint(): couldnt establish rendezvous point for " + z + " - at the moment");
      }
      Router rendezvousPointRouter = myRendezvousCirc.getRouteNodes()[(myRendezvousCirc.getRouteEstablished() - 1)].getRouter();
      
      LOG.info("getNewRendezvousPoint(): establishing rendezvous point for " + z + " at " + rendezvousPointRouter);
      SecureRandom rnd = new SecureRandom();
      byte[] rendezvousCookie = new byte[20];
      rnd.nextBytes(rendezvousCookie);
      
      myRendezvousCirc.sendCell(new CellRelayEstablishRendezvous(myRendezvousCirc, rendezvousCookie));
      



      CellRelay rendezvousACK = myRendezvousCirc.receiveRelayCell(39);
      if (rendezvousACK.getLength() > 0)
      {
        throw new TorException("connectToHiddenService(): Got NACK from RENDEZVOUS Point");
      }
      

      LOG.debug("getNewRendezvousPoint(): establishing rendezvous point for " + z + " at " + rendezvousPointRouter);
      return new RendezvousPointData(rendezvousCookie, rendezvousPointRouter, myRendezvousCirc);
    }
    catch (IOException e)
    {
      if (myRendezvousCirc != null)
      {
        myRendezvousCirc.close(true);
      }
      throw e;
    }
    catch (TorException e)
    {
      if (myRendezvousCirc != null)
      {
        myRendezvousCirc.close(true);
      }
      throw e;
    }
  }
  





















  private static Node sendIntroduction1Cell(Directory directory, TorEventService torEventService, TLSConnectionAdmin tlsConnectionAdmin, RendezvousPointData rendezvousPointData, SDIntroductionPoint introPoint, String z)
    throws Throwable
  {
    Fingerprint introPointFingerprint = introPoint.getIdentifierAsFingerprint();
    LOG.debug("sendIntroduction1Cell(): contacting introduction point=" + introPointFingerprint + " for " + z);
    

    TCPStreamProperties spIntro = new TCPStreamProperties();
    spIntro.setExitPolicyRequired(false);
    spIntro.setCustomExitpoint(introPointFingerprint);
    Circuit myIntroCirc = null;
    
    try
    {
      myIntroCirc = CircuitAdmin.provideSuitableExclusiveCircuit(tlsConnectionAdmin, directory, spIntro, torEventService);
      
      if (!myIntroCirc.isEstablished())
      {
        LOG.debug("Circuit to Introductionpoint not successful.");
        throw new TorException("Circuit to Introductionpoint " + introPointFingerprint + " not successful.");
      }
      LOG.debug("sendIntroduction1Cell(): use Circuit to introduction point=" + myIntroCirc);
      

      Router introPointServicePublicKey = new RouterImpl(introPoint.getServicePublicKey());
      Node introPointServicePublicKeyNode = new Node(introPointServicePublicKey);
      myIntroCirc.sendCell(new CellRelayIntroduce1(myIntroCirc, rendezvousPointData
        .getRendezvousCookie(), introPoint, introPointServicePublicKeyNode, rendezvousPointData
        

        .getRendezvousPointRouter()));
      

      CellRelay introACK = myIntroCirc.receiveRelayCell(40);
      if (introACK.getLength() > 0)
      {
        throw new TorException("sendIntroduction1Cell(): Got NACK from Introduction Point introACK=" + introACK);
      }
      
      LOG.debug("sendIntroduction1Cell(): Got ACK from Intro Point");
      
      return introPointServicePublicKeyNode;

    }
    finally
    {
      if (myIntroCirc != null)
      {
        myIntroCirc.close(true);
      }
    }
  }
  

















  private static void doRendezvous(Circuit myRendezvousCircuit, Node introPointServicePublicKeyNode, String z)
    throws TorException, IOException
  {
    CellRelay r2Relay = myRendezvousCircuit.receiveRelayCell(37);
    
    byte[] dhGy = new byte[''];
    System.arraycopy(r2Relay.getData(), 0, dhGy, 0, 148);
    introPointServicePublicKeyNode.finishDh(dhGy);
    
    myRendezvousCircuit.addNode(introPointServicePublicKeyNode);
    
    LOG.debug("doRendezvous(): succesfully established rendezvous with " + z);
  }
}
