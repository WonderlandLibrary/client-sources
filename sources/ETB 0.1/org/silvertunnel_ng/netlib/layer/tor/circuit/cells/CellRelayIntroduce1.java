package org.silvertunnel_ng.netlib.layer.tor.circuit.cells;

import org.silvertunnel_ng.netlib.api.util.TcpipNetAddress;
import org.silvertunnel_ng.netlib.layer.tor.api.Fingerprint;
import org.silvertunnel_ng.netlib.layer.tor.api.Router;
import org.silvertunnel_ng.netlib.layer.tor.circuit.Circuit;
import org.silvertunnel_ng.netlib.layer.tor.circuit.Node;
import org.silvertunnel_ng.netlib.layer.tor.directory.SDIntroductionPoint;
import org.silvertunnel_ng.netlib.layer.tor.util.Encoding;
import org.silvertunnel_ng.netlib.layer.tor.util.Encryption;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;
import org.silvertunnel_ng.netlib.util.ByteArrayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;








































public class CellRelayIntroduce1
  extends CellRelay
{
  private static final Logger LOG = LoggerFactory.getLogger(CellRelayIntroduce1.class);
  















  public CellRelayIntroduce1(Circuit circuit, byte[] rendezvousCookie, SDIntroductionPoint introPoint, Node introPointServicePublicKeyNode, Router rendezvousPointRouter)
    throws TorException
  {
    super(circuit, 34);
    





    byte[] clearText = Encryption.getDigest(
      Encryption.getPKCS1EncodingFromRSAPublicKey(introPoint
      .getServicePublicKey()));
    System.arraycopy(clearText, 0, data, 0, clearText.length);
    




    byte[] rendezvousPointRouterOnionKey = Encryption.getPKCS1EncodingFromRSAPublicKey(rendezvousPointRouter.getOnionKey());
    byte[] unencryptedData = ByteArrayUtil.concatByteArrays(new byte[][] { { 2 }, rendezvousPointRouter
    
















      .getOrAddress().getIpaddress(), 
      
      Encoding.intTo2ByteArray(rendezvousPointRouter.getOrAddress().getPort()), rendezvousPointRouter
      
      .getFingerprint().getBytes(), 
      
      Encoding.intTo2ByteArray(rendezvousPointRouterOnionKey.length), rendezvousPointRouterOnionKey, rendezvousCookie, introPointServicePublicKeyNode
      




      .getDhXBytes() });
    byte[] encryptedData = introPointServicePublicKeyNode.asymEncrypt(unencryptedData);
    if (LOG.isDebugEnabled()) {
      LOG.debug("CellRelayIntroduce1: unencryptedData=" + Encoding.toHexString(unencryptedData));
      LOG.debug("CellRelayIntroduce1: encryptedData=" + Encoding.toHexString(encryptedData));
    }
    

    System.arraycopy(encryptedData, 0, data, clearText.length, encryptedData.length);
    setLength(clearText.length + encryptedData.length);
    
    if (LOG.isDebugEnabled()) {
      LOG.debug("CellRelayIntroduce1: cell=" + toString());
    }
  }
}
