package yung.purity.utils;



public class TimerUtil
{
  private long lastMS;
  


  public TimerUtil() {}
  


  private long getCurrentMS()
  {
    return System.nanoTime() / 1000000L;
  }
  
  public boolean hasReached(double milliseconds)
  {
    return getCurrentMS() - lastMS >= milliseconds;
  }
  
  public void reset()
  {
    lastMS = getCurrentMS();
  }
}
