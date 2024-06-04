package org.silvertunnel_ng.netlib.api.impl;






public class DataNetSocketPair
{
  private DataNetSocket socket;
  



  private DataNetSocket invertedSocked;
  




  public DataNetSocketPair() {}
  




  public DataNetSocket getSocket()
  {
    return socket;
  }
  
  public void setSocket(DataNetSocket socket)
  {
    this.socket = socket;
  }
  
  public DataNetSocket getInvertedSocked()
  {
    return invertedSocked;
  }
  
  public void setInvertedSocked(DataNetSocket invertedSocked)
  {
    this.invertedSocked = invertedSocked;
  }
}
