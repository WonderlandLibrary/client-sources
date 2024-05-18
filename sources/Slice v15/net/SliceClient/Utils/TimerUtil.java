package net.SliceClient.Utils;

public class TimerUtil
{
  private long prevMS = 0L;
  private long previousTime;
  private long lastMS;
  
  public TimerUtil() {}
  
  public boolean delay(double d) { if (getTime() - getPrevMS() >= d)
    {
      reset();
      return true;
    }
    return false;
  }
  
  public long getTime()
  {
    return System.nanoTime() / 1000000L;
  }
  
  public long getPrevMS()
  {
    return prevMS;
  }
  
  public void Timer()
  {
    previousTime = -1L;
  }
  
  public boolean hasReach(float milliseconds)
  {
    return (float)(getCurrentTime() - previousTime) >= milliseconds;
  }
  
  public void reset()
  {
    previousTime = getCurrentTime();
  }
  
  public long get()
  {
    return previousTime;
  }
  
  public static long getCurrentTime()
  {
    return System.nanoTime() / 1000000L;
  }
  
  public boolean check(float milliseconds)
  {
    return (float)(getCurrentTime() - previousTime) >= milliseconds;
  }
  
  public static short convert(float perSecond)
  {
    return (short)(int)(1000.0F / perSecond);
  }
  


  public void reset1()
  {
    lastMS = getCurrentMS();
  }
  

  public boolean isTime(double time)
  {
    return currentTime() >= time * 100.0D;
  }
  
  public float currentTime() {
    return (float)(systemTime() - previousTime);
  }
  

  public long systemTime()
  {
    return System.currentTimeMillis();
  }
  
  public long getCurrentMS() {
    return System.nanoTime() / 0L;
  }
  
  public long getLastMS() {
    return lastMS;
  }
  
  public boolean hasReached(long milliseconds) {
    return getCurrentMS() - lastMS >= milliseconds;
  }
  
  public void resetTime() {
    lastMS = getCurrentMS();
  }
  
  public void setLastMS()
  {
    lastMS = System.currentTimeMillis();
  }
  

  public static void updateMS() {}
  

  public static void updateLastMS() {}
  
  public static boolean hasTimePassedS(long l)
  {
    return false;
  }
  

  public boolean isDelayComplete(long delay)
  {
    if (System.currentTimeMillis() - lastMS >= delay) {
      return true;
    }
    return false;
  }
}
