package lunadevs.luna.option;

public class TimerUtil
{
  private long prevMS;
  
  public TimerUtil()
  {
    this.prevMS = 0L;
  }
  
  public boolean hasDelay(double d)
  {
    return getTime() - this.prevMS >= 1000.0D / d;
  }
  
  public void reset()
  {
    this.prevMS = getTime();
  }
  
  public long getTime()
  {
    return System.nanoTime() / 1000000L;
  }
  
  private long currentMs = 0L;
  private long lastMs = -1L;
  private long previousTime;
  
  public boolean a(long milliseconds)
  {
    return getCurrentMS() - this.prevMS >= milliseconds;
  }
  
  public void updateMs()
  {
    this.currentMs = System.currentTimeMillis();
  }
  
  public void setLastMs()
  {
    this.lastMs = System.currentTimeMillis();
  }
  
  public boolean hasPassed(long Ms)
  {
    return this.currentMs > this.lastMs + Ms;
  }
  
  public long getCurrentMS()
  {
    return System.nanoTime() / 1000000L;
  }
  
  public long getLastMS()
  {
    return this.lastMs;
  }
  
  public static long getCurrentTime()
  {
    return System.nanoTime() / 1000000L;
  }
  
  public boolean hasTimePassedM(long MS)
  {
    return this.currentMs >= this.lastMs + MS;
  }
  
  public boolean hasReached(long milliseconds)
  {
    return getCurrentMS() - this.lastMs >= milliseconds;
  }
  
  public boolean check(float milliseconds)
  {
    return (float)(getCurrentTime() - this.previousTime) >= milliseconds;
  }
  
  public boolean hasPassed(double milli)
  {
    return getTime() - this.previousTime >= milli;
  }
  
  public boolean isDelayComplete(long delay)
  {
    if (System.currentTimeMillis() - this.lastMs >= delay) {
      return true;
    }
    return false;
  }
  
  public int convertToMS(int perSecond)
  {
    return 1000 / perSecond;
  }
}
