package lunadevs.luna.utils.faithsminiutils;

import java.util.concurrent.TimeUnit;

public class Timer {

	 private long time;
		private double lastMSdouble;
	  private long prevMS;

	  public long currentMS = 0L;
	  public long lastMS = -1L;
	  public Timer()
	  {
	    this.time = (System.nanoTime() / 1000000L);
	    this.prevMS = 0L;
	  }
	  
	    private static long previousTime;

	    public static boolean hasReach(long time) {
	        return hasReach(time, TimeUnit.MILLISECONDS);
	    }

	    public static boolean hasReach(long time, TimeUnit timeUnit) {
	        final long convert = timeUnit.convert(System.nanoTime() - previousTime, TimeUnit.NANOSECONDS);
	        if (convert >= time) {
	            reset();
	        }
	        return convert >= time;
	    }
	  public final void update()
	  {
	    this.currentMS = System.currentTimeMillis();
	  }
	  
	  public static void reset() {
	       previousTime = System.nanoTime();
	    }
	  public long getCurrentMS() {
			return System.nanoTime() / 1000000L;
		}
	  public boolean hasReached(Double double1) {
			return getCurrentMSDouble() - this.lastMSdouble >= double1;
		}
		public double getCurrentMSDouble() {
			return System.nanoTime() / 1000000L;
		}

		public void resetDouble() {
			this.lastMSdouble = getCurrentMS();
		}
	  public void resetT() {
			this.lastMS = getCurrentMS();
		}
	  public final boolean hasPassed(long MS)
	  {
	    return this.currentMS >= this.lastMS + MS;
	  }
	  
	  public boolean hasTimeElapsed(long time, boolean reset)
	  {
	    if (time() >= time)
	    {
	      if (reset) {
	        reset();
	      }
	      return true;
	    }
	    return false;
	  }
	  
	  public boolean delay(float milliSec)
	  {
	    return (float)(time() - this.prevMS) >= milliSec;
	  }
	  
	  public long time()
	  {
	    return System.nanoTime() / 1000000L - this.time;
	  }
	  
	  public void reset2()
	  {
	    this.time = (System.nanoTime() / 1000000L);
	  }
	  
	  public long getDifference()
	  {
	    return time() - this.prevMS;
	  }
	
}
