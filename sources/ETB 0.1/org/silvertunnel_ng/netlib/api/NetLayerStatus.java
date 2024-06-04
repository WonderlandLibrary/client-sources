package org.silvertunnel_ng.netlib.api;


























public class NetLayerStatus
  implements Comparable<NetLayerStatus>
{
  public static final NetLayerStatus NEW = new NetLayerStatus("New or clean", 0.0D);
  
  public static final NetLayerStatus READY = new NetLayerStatus("Ready for use", 1.0D);
  




  private final String name;
  



  private final double readyIndicator;
  




  protected NetLayerStatus(String name, double readyIndicator)
  {
    this.name = name;
    this.readyIndicator = readyIndicator;
  }
  

  public String toString()
  {
    return name + " (" + (int)(100.0D * readyIndicator) + "%)";
  }
  

  public int hashCode()
  {
    return (int)(readyIndicator * 2.147483647E9D);
  }
  


  public boolean equals(Object o)
  {
    if ((o == null) || (!(o instanceof NetLayerStatus)))
    {
      return false;
    }
    
    NetLayerStatus obj = (NetLayerStatus)o;
    return (name.equals(name)) && (readyIndicator == readyIndicator);
  }
  


  public int compareTo(NetLayerStatus other)
  {
    return new Double(readyIndicator).compareTo(new Double(other
      .getReadyIndicator()));
  }
  







  public String getName()
  {
    return name;
  }
  



  public double getReadyIndicator()
  {
    return readyIndicator;
  }
}
