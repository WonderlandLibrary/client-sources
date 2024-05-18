package net.SliceClient.Utils;

public class TimeHelper
{
  private long lastMS = 0L;
  
  public TimeHelper() {}
  
  public boolean isDelayComplete(long delay) { if (System.currentTimeMillis() - lastMS >= delay) {
      return true;
    }
    return false;
  }
  
  public boolean hasReached(long milliseconds)
  {
    return getCurrentMS() - lastMS >= milliseconds;
  }
  
  public void setLastMS(long lastMS)
  {
    this.lastMS = lastMS;
  }
  
  public void setLastMS()
  {
    lastMS = System.currentTimeMillis();
  }
  
  public int convertToMS(int perSecond)
  {
    return 1000 / perSecond;
  }
  
  public long getCurrentMS()
  {
    return System.nanoTime() / 1000000L;
  }
  
  public long getLastMS()
  {
    return lastMS;
  }
  
  public void reset()
  {
    lastMS = getCurrentMS();
  }
}
