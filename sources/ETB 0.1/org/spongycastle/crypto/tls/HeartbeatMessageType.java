package org.spongycastle.crypto.tls;


public class HeartbeatMessageType
{
  public static final short heartbeat_request = 1;
  public static final short heartbeat_response = 2;
  
  public HeartbeatMessageType() {}
  
  public static boolean isValid(short heartbeatMessageType)
  {
    return (heartbeatMessageType >= 1) && (heartbeatMessageType <= 2);
  }
}
