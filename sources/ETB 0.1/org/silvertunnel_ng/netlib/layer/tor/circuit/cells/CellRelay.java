package org.silvertunnel_ng.netlib.layer.tor.circuit.cells;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import org.silvertunnel_ng.netlib.layer.tor.api.Router;
import org.silvertunnel_ng.netlib.layer.tor.circuit.Circuit;
import org.silvertunnel_ng.netlib.layer.tor.circuit.Node;
import org.silvertunnel_ng.netlib.layer.tor.circuit.Stream;
import org.silvertunnel_ng.netlib.layer.tor.util.Encoding;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;
import org.silvertunnel_ng.netlib.util.ByteArrayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;










































public class CellRelay
  extends Cell
{
  private static final Logger LOG = LoggerFactory.getLogger(CellRelay.class);
  
  public static final int RELAY_BEGIN = 1;
  
  public static final int RELAY_DATA = 2;
  
  public static final int RELAY_END = 3;
  
  public static final int RELAY_CONNECTED = 4;
  public static final int RELAY_SENDME = 5;
  public static final int RELAY_EXTEND = 6;
  public static final int RELAY_EXTENDED = 7;
  public static final int RELAY_TRUNCATE = 8;
  public static final int RELAY_TRUNCATED = 9;
  public static final int RELAY_DROP = 10;
  public static final int RELAY_RESOLVE = 11;
  public static final int RELAY_RESOLVED = 12;
  public static final int RELAY_BEGIN_DIR = 13;
  public static final int RELAY_ESTABLISH_INTRO = 32;
  public static final int RELAY_ESTABLISH_RENDEZVOUS = 33;
  public static final int RELAY_INTRODUCE1 = 34;
  public static final int RELAY_INTRODUCE2 = 35;
  public static final int RELAY_RENDEZVOUS1 = 36;
  public static final int RELAY_RENDEZVOUS2 = 37;
  public static final int RELAY_INTRO_ESTABLISHED = 38;
  public static final int RELAY_RENDEZVOUS_ESTABLISHED = 39;
  public static final int RELAY_COMMAND_INTRODUCE_ACK = 40;
  public static final int RELAY_COMMAND_SIZE = 1;
  public static final int RELAY_RECOGNIZED_SIZE = 2;
  public static final int RELAY_STREAMID_SIZE = 2;
  public static final int RELAY_DIGEST_SIZE = 4;
  public static final int RELAY_LENGTH_SIZE = 2;
  public static final int RELAY_DATA_SIZE = 498;
  public static final int RELAY_TOTAL_SIZE = 509;
  public static final int RELAY_COMMAND_POS = 0;
  public static final int RELAY_RECOGNIZED_POS = 1;
  public static final int RELAY_STREAMID_POS = 3;
  public static final int RELAY_DIGEST_POS = 5;
  public static final int RELAY_LENGTH_POS = 9;
  public static final int RELAY_DATA_POS = 11;
  private static final String[] COMMAND_TO_STRING = { "zero", "begin", "data", "end", "connected", "sendme", "extend", "extended", "truncate", "truncated", "drop", "resolv", "resolved", "RELAY_BEGIN_DIR", "[14]", "[15]", "[16]", "[17]", "[18]", "[19]", "[20]", "[21]", "[22]", "[23]", "[24]", "[25]", "[26]", "[27]", "[28]", "[29]", "[30]", "[31]", "RELAY_COMMAND_ESTABLISH_INTRO", "RELAY_COMMAND_ESTABLISH_RENDEZVOUS", "RELAY_COMMAND_INTRODUCE1", "RELAY_COMMAND_INTRODUCE2", "RELAY_COMMAND_RENDEZVOUS1", "RELAY_COMMAND_RENDEZVOUS2", "RELAY_COMMAND_INTRO_ESTABLISHED", "RELAY_COMMAND_RENDEZVOUS_ESTABLISHED", "RELAY_COMMAND_INTRODUCE_ACK" };
  















  private static final String[] REASON_TO_STRING = { "none", "misc", "resolve failed", "connect refused", "exit policy", "destroy", "done", "timeout", "(unallocated - see spec)", "hibernating", "internal", "resource limit", "connection reset", "tor protocol violation" };
  





  private static final String[] TRUNCATED_REASON_TO_STRING = { "none", "protocol", "internal", "requested", "hibernating", "resourcelimit", "connectfailed", "or_identity", "or_conn_closed", "finished", "timeout", "destroyed", "nosuchservice" };
  

  private byte relayCommand;
  

  private int streamId;
  
  private byte[] digest = new byte[4];
  
  private int length;
  protected byte[] data = new byte['ǲ'];
  



  private int addressedRouterInCircuit = -1;
  


  CellRelay(Circuit c, int relayCommand)
  {
    super(c, 3);
    this.relayCommand = ((byte)relayCommand);
  }
  


  CellRelay(Circuit c, int cellType, int relayCommand)
  {
    super(c, cellType);
    this.relayCommand = ((byte)relayCommand);
  }
  


  CellRelay(Stream s, int relayCommand)
  {
    super(s.getCircuit(), 3);
    streamId = s.getId();
    this.relayCommand = ((byte)relayCommand);
  }
  


  CellRelay(Stream s, int cellType, int relayCommand)
  {
    super(s.getCircuit(), cellType);
    streamId = s.getId();
    this.relayCommand = ((byte)relayCommand);
  }
  

  CellRelay(byte[] data)
    throws TorException
  {
    super(data);
    initFromData();
  }
  

  public CellRelay(Circuit circ, Cell cell)
    throws TorException
  {
    super(cell.toByteArray());
    
    outCircuit = circ;
    initFromData();
  }
  

  CellRelay(InputStream in)
    throws IOException, TorException
  {
    super(in);
    initFromData();
  }
  


  void initFromData()
    throws TorException
  {
    if (LOG.isDebugEnabled()) {
      LOG.debug("CellRelay.initFromData() for " + outCircuit
        .getRouteEstablished() + " layers");
    }
    

    boolean digestVerified = false;
    if (outCircuit.getRouteEstablished() == 0) {
      LOG.warn("CellRelay.initFromData() for zero layers on " + outCircuit
        .toString());
    }
    for (int encryptingRouter = 0; 
        encryptingRouter <= outCircuit.getRouteEstablished(); encryptingRouter++)
    {
      if (encryptingRouter == outCircuit.getRouteEstablished())
      {

        throw new TorException("relay cell not recognized, possibly due to decryption errors? on " + outCircuit.toString());
      }
      
      outCircuit.getRouteNodes()[encryptingRouter].symDecrypt(payload);
      if (LOG.isDebugEnabled()) {
        LOG.info("CellRelay.initFromDate with encryptingRouter=" + encryptingRouter + " has decrypted payload=" + 
        
          ByteArrayUtil.showAsStringDetails(payload));
      }
      

      if ((payload[1] == 0) && (payload[2] == 0))
      {



        System.arraycopy(payload, 5, digest, 0, 4);
        
        payload[5] = 0;
        payload[6] = 0;
        payload[7] = 0;
        payload[8] = 0;
        
        byte[] digestCalc = outCircuit.getRouteNodes()[encryptingRouter].calcBackwardDigest(payload);
        
        System.arraycopy(digest, 0, payload, 5, 4);
        
        if ((digest[0] == digestCalc[0]) && (digest[1] == digestCalc[1]) && (digest[2] == digestCalc[2]) && (digest[3] == digestCalc[3]))
        {


          if (LOG.isDebugEnabled()) {
            LOG.debug("CellRelay.initFromData(): backward digest from " + outCircuit
              .getRouteNodes()[encryptingRouter]
              .getRouter().getNickname() + " is OK");
          }
          digestVerified = true;
          break;
        }
        if (LOG.isDebugEnabled()) {
          LOG.debug("didn't verified digest=" + 
            Encoding.toHexString(digest) + ", digestCalc=" + 
            
            Encoding.toHexString(digestCalc));
        }
      }
    }
    

    if (!digestVerified) {
      LOG.warn("CellRelay.initFromData(): Received " + 
        Encoding.toHexString(digest) + " as backward digest but couldn't verify");
      
      throw new TorException("wrong digest");
    }
    

    relayCommand = payload[0];
    streamId = Encoding.byteArrayToInt(payload, 3, 2);
    length = Encoding.byteArrayToInt(payload, 9, 2);
    System.arraycopy(payload, 11, data, 0, 498);
    
    if (LOG.isDebugEnabled()) {
      LOG.debug("CellRelay.initFromData(): " + toString());
    }
  }
  



  boolean setAddressedRouter(int router)
  {
    if ((router > -1) && (router < outCircuit.getRouteEstablished())) {
      addressedRouterInCircuit = router;
      return true;
    }
    return false;
  }
  






  public byte[] toByteArray()
  {
    if (LOG.isDebugEnabled()) {
      LOG.debug("CellRelay.toByteArray() for " + outCircuit.getRouteEstablished() + " layers");
    }
    
    payload[0] = relayCommand;
    System.arraycopy(Encoding.intToNByteArray(streamId, 2), 0, payload, 3, 2);
    



    System.arraycopy(Encoding.intToNByteArray(length, 2), 0, payload, 9, 2);
    



    System.arraycopy(data, 0, payload, 11, 498);
    
    int i0 = outCircuit.getRouteEstablished() - 1;
    if (addressedRouterInCircuit >= 0) {
      i0 = addressedRouterInCircuit;
    }
    digest = outCircuit.getRouteNodes()[i0].calcForwardDigest(payload);
    System.arraycopy(digest, 0, payload, 5, 4);
    
    if (LOG.isDebugEnabled()) {
      LOG.debug("CellRelay.toByteArray(): " + toString());
    }
    
    for (int i = i0; i >= 0; i--) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("CellRelay.toByteArray with encryptingRouter=" + i + " has unencrypted payload=" + 
        
          ByteArrayUtil.showAsStringDetails(payload));
      }
      outCircuit.getRouteNodes()[i].symEncrypt(payload);
    }
    
    return super.toByteArray();
  }
  


  private static String getReasonForClosing(int reason)
  {
    if ((reason < 0) || (reason >= REASON_TO_STRING.length)) {
      return "[" + reason + "]";
    }
    return REASON_TO_STRING[reason];
  }
  
  public String getReasonForClosing() {
    return getReasonForClosing(data[0]);
  }
  


  private static String getReasonForTruncated(int reason)
  {
    if ((reason < 0) || (reason >= TRUNCATED_REASON_TO_STRING.length)) {
      return "[" + reason + "]";
    }
    return TRUNCATED_REASON_TO_STRING[reason];
  }
  
  public String getReasonForTruncated() {
    return getReasonForTruncated(data[0]);
  }
  


  public String getRelayCommandAsString()
  {
    return getRelayCommandAsString(relayCommand);
  }
  






  public static String getRelayCommandAsString(int cmd)
  {
    if ((cmd < COMMAND_TO_STRING.length) && (cmd >= 0)) {
      return COMMAND_TO_STRING[cmd];
    }
    return "[" + cmd + "]";
  }
  




  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    

    sb.append("Relay cell for circuit " + getCircuitId() + "/stream " + streamId + " with command " + 
      getRelayCommandAsString() + ".\n");
    

    if (Encoding.byteArrayToInt(payload, 1, 2) != 0) {
      sb.append("  Recognized    " + Encoding.toHexString(payload, 100, 1, 2) + "\n");
      sb.append("  DigestID      " + Encoding.toHexString(digest) + "\n");
    }
    
    if (isTypeBegin()) {
      byte[] host = new byte[length - 1];
      System.arraycopy(data, 0, host, 0, length - 1);
      sb.append("  Connecting to: " + new String(host) + "\n");
    }
    else if (isTypeEnd())
    {
      sb.append("  End reason: " + getReasonForClosing() + "\n");
    }
    else if ((isTypeConnected()) && (length >= 4))
    {
      byte[] ip = new byte[4];
      System.arraycopy(data, 0, ip, 0, 4);
      try {
        sb.append("  Connected to: " + InetAddress.getByAddress(ip).toString() + "\n");
      } catch (UnknownHostException e) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("got UnknownHostException : ip = " + 
            Arrays.toString(ip) + " exception : " + e + " " + e
            
            .getMessage(), e);
        }
      }
    }
    else if ((length > 0) && (relayCommand != 6) && (relayCommand != 7))
    {
      sb.append("  Data (" + length + " bytes)\n" + Encoding.toHexString(data, 100, 0, length) + "\n");
    }
    
    return sb.toString();
  }
  



  public boolean isTypeBegin()
  {
    return relayCommand == 1;
  }
  
  public boolean isTypeData() {
    return relayCommand == 2;
  }
  
  public boolean isTypeEnd() {
    return relayCommand == 3;
  }
  
  boolean isTypeConnected() {
    return relayCommand == 4;
  }
  


  public final boolean isTypeSendme()
  {
    return relayCommand == 5;
  }
  
  boolean isTypeExtend() {
    return relayCommand == 6;
  }
  
  boolean isTypeExtended() {
    return relayCommand == 7;
  }
  
  boolean isTypeTruncate() {
    return relayCommand == 8;
  }
  
  public boolean isTypeTruncated() {
    return relayCommand == 9;
  }
  
  boolean isTypeDrop() {
    return relayCommand == 10;
  }
  
  boolean isTypeResolve() {
    return relayCommand == 12;
  }
  
  boolean isTypeResolved() {
    return relayCommand == 12;
  }
  
  boolean isTypeEstablishedRendezvous() {
    return relayCommand == 39;
  }
  
  boolean isTypeIntroduceACK() {
    return relayCommand == 40;
  }
  
  boolean isTypeRendezvous2() {
    return relayCommand == 37;
  }
  
  public boolean isTypeIntroduce2() {
    return relayCommand == 35;
  }
  
  public byte getRelayCommand() {
    return relayCommand;
  }
  
  public void setRelayCommand(byte relayCommand) {
    this.relayCommand = relayCommand;
  }
  
  public int getStreamId() {
    return streamId;
  }
  
  public void setStreamId(int streamId) {
    this.streamId = streamId;
  }
  
  public byte[] getDigest() {
    return digest;
  }
  
  public void setDigest(byte[] digest) {
    this.digest = digest;
  }
  


  public int getLength()
  {
    return length;
  }
  
  public void setLength(int length) {
    this.length = length;
  }
  
  public byte[] getData() {
    return data;
  }
  
  public void setData(byte[] data) {
    this.data = data;
  }
}
