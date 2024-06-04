package org.silvertunnel_ng.netlib.layer.tor.circuit.cells;

import java.io.IOException;
import java.net.InetAddress;
import org.silvertunnel_ng.netlib.layer.tor.api.Fingerprint;
import org.silvertunnel_ng.netlib.layer.tor.api.Router;
import org.silvertunnel_ng.netlib.layer.tor.circuit.Circuit;
import org.silvertunnel_ng.netlib.layer.tor.circuit.Node;
import org.silvertunnel_ng.netlib.layer.tor.util.Encoding;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




















public class CellRelayExtend
  extends CellRelayEarly
{
  private static final Logger LOG = LoggerFactory.getLogger(CellRelayExtend.class);
  












  public CellRelayExtend(Circuit cell, Node nextNode)
    throws IOException, TorException
  {
    super(cell, 6);
    

    byte[] address = nextNode.getRouter().getAddress().getAddress();
    
    byte[] orPort = Encoding.intTo2ByteArray(nextNode.getRouter().getOrPort());
    
    byte[] onionSkin = nextNode.asymEncrypt(nextNode.getDhXBytes());
    

    byte[] keyHash = nextNode.getRouter().getFingerprint().getBytes();
    

    setLength(address.length + orPort.length + onionSkin.length + keyHash.length);
    System.arraycopy(address, 0, data, 0, address.length);
    System.arraycopy(orPort, 0, data, 4, orPort.length);
    System.arraycopy(onionSkin, 0, data, 6, onionSkin.length);
    System.arraycopy(keyHash, 0, data, 192, keyHash.length);
    if (LOG.isDebugEnabled()) {
      LOG.debug("CellRelayExtend Router :\n" + nextNode.getRouter().toLongString());
      LOG.debug("CellRelayExtend address :\n" + Encoding.toHexString(address, 100));
      LOG.debug("CellRelayExtend orPort :\n" + Encoding.toHexString(orPort, 100));
      LOG.debug("CellRelayExtend onionSkin :\n" + Encoding.toHexString(onionSkin, 100));
      LOG.debug("CellRelayExtend keyhash :\n" + Encoding.toHexString(keyHash, 100));
      LOG.debug("CellRelayExtend data :\n" + Encoding.toHexString(data, 100));
    }
  }
}
