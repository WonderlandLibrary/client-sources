package me.protocol_client.utils;

public class TimerUtil
{
  private long prevMS;
  
  public TimerUtil()
  {
    this.prevMS = 0L;
  }
  
  public boolean delay(double d)
  {
    if (getTime() - getPrevMS() >= d)
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
    return this.prevMS;
  }

private long previousTime;
  
  public void Timer()
  {
    this.previousTime = -1L;
  }
  
  public boolean hasReach(float milliseconds)
  {
    return (float)(getCurrentTime() - this.previousTime) >= milliseconds;
  }
  
  public void reset()
  {
    this.previousTime = getCurrentTime();
  }
  
  public long get()
  {
    return this.previousTime;
  }
  
  public static long getCurrentTime()
  {
    return System.nanoTime() / 1000000L;
  }
  public boolean check(float milliseconds)
  {
    return (float)(getCurrentTime() - this.previousTime) >= milliseconds;
  }
  public static short convert(float perSecond)
  {
    return (short)(int)(1000.0F / perSecond);
  }
private long lastMS;
  
  public long getCurrentMS()
  {
    return System.nanoTime() / 1000000L;
  }
  
  public long getLastMS()
  {
    return this.lastMS;
  }
  
  public boolean hasReached(long milliseconds)
  {
    return getCurrentMS() - this.lastMS >= milliseconds;
  }
  
  public void reset1()
  {
    this.lastMS = getCurrentMS();
  }
  
  public void setLastMS(long currentMS)
  {
    this.lastMS = currentMS;
  }

}
