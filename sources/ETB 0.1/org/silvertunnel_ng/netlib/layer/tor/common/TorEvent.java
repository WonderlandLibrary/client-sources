package org.silvertunnel_ng.netlib.layer.tor.common;




public class TorEvent
{
  public static final int GENERAL = 0;
  


  public static final int CIRCUIT_BUILD = 10;
  


  public static final int CIRCUIT_CLOSED = 11;
  


  public static final int STREAM_BUILD = 20;
  

  public static final int STREAM_CLOSED = 21;
  

  private final String description;
  

  private final int type;
  

  private final Object cause;
  


  public TorEvent(int type, Object o, String description)
  {
    this.description = description;
    cause = o;
    this.type = type;
  }
  
  public String getDescription()
  {
    return description;
  }
  
  public int getType()
  {
    return type;
  }
  
  public Object getObject()
  {
    return cause;
  }
}
