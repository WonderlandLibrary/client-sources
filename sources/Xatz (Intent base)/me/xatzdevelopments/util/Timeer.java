package me.xatzdevelopments.util;

public class Timeer {
	
	public long lastMS = System.currentTimeMillis();;
	
	public void reset() {
		lastMS = System.currentTimeMillis();
	}
	
	public boolean hasTimeElapsed(long time, boolean reset) {
		
		if (System.currentTimeMillis()-lastMS > time) {
			
			if (reset)
				reset();
			
			return true;
				
			
		}else {
			return false;
		}
		
	}
	
}
