package net.SliceClient.Utils;

public final class Timer
{
  private long previousTime;
  
  public static short convert(float perSecond)
  {
    return (short)(int)(1000.0F / perSecond);
  }
  
  public static long getCurrentTime()
  {
    return System.nanoTime() / 1000000L;
  }
  
  public Timer()
  {
    previousTime = -1L;
  }
  
  public long get()
  {
    return previousTime;
  }
  
  public boolean check(float milliseconds)
  {
    return (float)(getCurrentTime() - previousTime) >= milliseconds;
  }
  
  public void reset()
  {
    previousTime = getCurrentTime();
  }
}
