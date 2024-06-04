package org.silvertunnel_ng.netlib.layer.tor.circuit.cells;

import org.silvertunnel_ng.netlib.layer.tor.circuit.Circuit;










































public class CellDestroy
  extends Cell
{
  private static final String[] REASON_TO_STRING = { "none", "end-circ tor-protocol", "internal", "requested", "hibernating", "resourcelimit", "connectfailed", "or_identity", "or_conn_closed", "finished", "timeout", "destroyed", "nosuchservice", "tor protocol violation" };
  
  public static final byte REASON_NONE = 0;
  
  public static final byte REASON_END_CIRC_TOR_PROTOCOL = 1;
  
  public static final byte REASON_INTERNAL = 2;
  
  public static final byte REASON_REQUESTED = 3;
  
  public static final byte REASON_HIBERNATING = 4;
  
  public static final byte REASON_RESOURCELIMIT = 5;
  
  public static final byte REASON_CONNECTFAILED = 6;
  
  public static final byte REASON_OR_IDENTITY = 7;
  
  public static final byte REASON_OR_CONN_CLOSED = 8;
  public static final byte REASON_FINISHED = 9;
  public static final byte REASON_TIMEOUT = 10;
  public static final byte REASON_DESTROYED = 11;
  public static final byte REASON_NO_SUCH_SERVICE = 12;
  public static final byte REASON_TOR_PROTOCOL = 13;
  
  public CellDestroy(Circuit c)
  {
    super(c, 4);
  }
  





  public static String getReason(int reason)
  {
    if ((reason < 0) || (reason >= REASON_TO_STRING.length))
    {
      return "[" + reason + "]";
    }
    return REASON_TO_STRING[reason];
  }
  



  public String getReason()
  {
    return getReason(payload[0]);
  }
}
