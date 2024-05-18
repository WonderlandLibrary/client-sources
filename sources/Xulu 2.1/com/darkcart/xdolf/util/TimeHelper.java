package com.darkcart.xdolf.util;

public class TimeHelper
{
  private long lastMS = 0L;
  
  public boolean isDelayComplete(long delay)
  {
    if (System.currentTimeMillis() - this.lastMS >= delay) {
      return true;
    }
    return false;
  }
  
  public void setLastMS(long lastMS)
  {
    this.lastMS = lastMS;
  }
  
  public void setLastMS()
  {
    this.lastMS = System.currentTimeMillis();
  }
  
  public int convertToMS(int perSecond)
  {
    return 1000 / perSecond;
  }
  
  public long getCurrentMS()
  {
    return System.nanoTime() / 1000000L;
  }
  
  public boolean hasReached(float f)
  {
    return (float)(getCurrentMS() - this.lastMS) >= f;
  }
  
  public void reset()
  {
    this.lastMS = getCurrentMS();
  }
}
