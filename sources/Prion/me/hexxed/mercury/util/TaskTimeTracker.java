package me.hexxed.mercury.util;

public class TaskTimeTracker
{
  private long startTimeMS;
  
  public TaskTimeTracker() {
    startTimeMS = (System.nanoTime() / 1000000L);
  }
  
  public boolean sleep(long ms)
  {
    long now = System.nanoTime() / 1000000L;
    if (startTimeMS == 0L) {
      return false;
    }
    if (now - startTimeMS >= ms)
    {
      startTimeMS = (System.nanoTime() / 1000000L);
      return true;
    }
    return false;
  }
}
