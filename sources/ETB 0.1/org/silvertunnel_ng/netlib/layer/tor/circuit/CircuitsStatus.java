package org.silvertunnel_ng.netlib.layer.tor.circuit;

























public class CircuitsStatus
{
  private int circuitsTotal = 0;
  
  private int circuitsAlive = 0;
  
  private int circuitsEstablished = 0;
  
  private int circuitsClosed = 0;
  

  public CircuitsStatus() {}
  

  public int getCircuitsTotal()
  {
    return circuitsTotal;
  }
  
  public void setCircuitsTotal(int circuitsTotal)
  {
    this.circuitsTotal = circuitsTotal;
  }
  
  public int getCircuitsAlive()
  {
    return circuitsAlive;
  }
  
  public void setCircuitsAlive(int circuitsAlive)
  {
    this.circuitsAlive = circuitsAlive;
  }
  
  public int getCircuitsEstablished()
  {
    return circuitsEstablished;
  }
  
  public void setCircuitsEstablished(int circuitsEstablished)
  {
    this.circuitsEstablished = circuitsEstablished;
  }
  
  public int getCircuitsClosed()
  {
    return circuitsClosed;
  }
  
  public void setCircuitsClosed(int circuitsClosed)
  {
    this.circuitsClosed = circuitsClosed;
  }
}
