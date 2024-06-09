package com.kilo.util;

import java.util.concurrent.TimeUnit;

public class TimerUtil {
	    private long previousTime;
		private long currentMS = 0L;
		protected long lastMS = -1L;
	    
	    public long getPreviousTime() {
	        return this.previousTime;
	    }
	    private long prevMS;
	    
	    public TimerUtil() {
	        this.prevMS = 0L;
	    }
	    
	    public boolean delay(final float milliSec) {
	        return this.getTime() - this.prevMS >= milliSec;
	    }
	    
		public final void update() {
			currentMS = System.currentTimeMillis();
		}
		
	    public boolean isDelayComplete(final long delay) {
	        return System.currentTimeMillis() - this.lastMS >= delay;
	    }
	    
		public long systemTime() {
			return System.currentTimeMillis();
		}
	    
		public float currentTime() {
			return systemTime()-previousTime;
		}
	    
		public boolean isTime(float time) {
			return currentTime() >= time*1000L;
		}
		
	    
	    public void setLastMS(final long lastMS) {
	        this.lastMS = lastMS;
	    }
	    
	    public void setLastMS() {
	        this.lastMS = System.currentTimeMillis();
	    }
	    
	    public void reset() {
	        this.prevMS = this.getTime();
	    }
	    
	    public long getTime() {
	        return System.nanoTime() / 1000000L;
	    }
	    
		public final boolean hasPassed(long MS) {
			return currentMS >= lastMS + MS;
		}
	    
	    public long getDifference() {
	        return this.getTime() - this.prevMS;
	    }
	    
	    public boolean hasReach(final long a, TimeUnit milliseconds) {
	        return this.hasReach(a, TimeUnit.MILLISECONDS);
	    }
	    
	    
	}
