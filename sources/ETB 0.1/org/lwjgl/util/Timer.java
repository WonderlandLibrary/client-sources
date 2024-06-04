package org.lwjgl.util;

import org.lwjgl.Sys;












































public class Timer
{
  private static long resolution = ;
  

  private static final int QUERY_INTERVAL = 50;
  

  private static int queryCount;
  
  private static long currentTime;
  
  private long startTime;
  
  private long lastTime;
  
  private boolean paused;
  

  static
  {
    tick();
  }
  


  public Timer()
  {
    reset();
    resume();
  }
  


  public float getTime()
  {
    if (!paused) {
      lastTime = (currentTime - startTime);
    }
    
    return (float)(lastTime / resolution);
  }
  

  public boolean isPaused()
  {
    return paused;
  }
  





  public void pause()
  {
    paused = true;
  }
  



  public void reset()
  {
    set(0.0F);
  }
  



  public void resume()
  {
    paused = false;
    startTime = (currentTime - lastTime);
  }
  



  public void set(float newTime)
  {
    long newTimeInTicks = (newTime * resolution);
    startTime = (currentTime - newTimeInTicks);
    lastTime = newTimeInTicks;
  }
  




  public static void tick()
  {
    currentTime = Sys.getTime();
    

    queryCount += 1;
    if (queryCount > 50) {
      queryCount = 0;
      resolution = Sys.getTimerResolution();
    }
  }
  


  public String toString()
  {
    return "Timer[Time=" + getTime() + ", Paused=" + paused + "]";
  }
}
