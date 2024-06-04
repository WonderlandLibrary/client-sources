package org.silvertunnel_ng.netlib.layer.tor.circuit.cells;

import org.silvertunnel_ng.netlib.layer.tor.circuit.Circuit;
import org.silvertunnel_ng.netlib.layer.tor.circuit.Node;
import org.silvertunnel_ng.netlib.layer.tor.hiddenservice.HiddenServiceProperties;
import org.silvertunnel_ng.netlib.layer.tor.util.Encoding;
import org.silvertunnel_ng.netlib.layer.tor.util.Encryption;








































public class CellRelayEstablishIntro
  extends CellRelay
{
  private static final byte[] CELL_MAGIC = { 73, 78, 84, 82, 79, 68, 85, 67, 69 };
  
  public CellRelayEstablishIntro(Circuit circuit, HiddenServiceProperties service) {
    super(circuit, 32);
    
    byte[] hsInput = new byte[29];
    System.arraycopy(circuit.getLastRouteNode().getKeyHandshake(), 0, hsInput, 0, 20);
    System.arraycopy("INTRODUCE".getBytes(), 0, hsInput, 20, 9);
    byte[] hs = Encryption.getDigest(hsInput);
    
    byte[] pk = Encryption.getPKCS1EncodingFromRSAPublicKey(service.getPublicKey());
    byte[] kl = Encoding.intToNByteArray(pk.length, 2);
    byte[] input = new byte[pk.length + kl.length + hs.length];
    System.arraycopy(kl, 0, input, 0, 2);
    System.arraycopy(pk, 0, input, 2, pk.length);
    System.arraycopy(hs, 0, input, 2 + pk.length, hs.length);
    
    byte[] signature = Encryption.signData(input, service.getPrivateKey());
    
    System.arraycopy(input, 0, data, 0, input.length);
    System.arraycopy(signature, 0, data, input.length, signature.length);
    setLength(input.length + signature.length);
  }
}
