package xyz.vergoclient.util.main;

public class TimerUtil {
	
	public long lastMS = System.currentTimeMillis();;
	
	public void reset() {
		lastMS = System.currentTimeMillis();
	}
	
	public boolean hasTimeElapsed(long time, boolean reset) {
		
		if (lastMS > System.currentTimeMillis()) {
			lastMS = System.currentTimeMillis();
		}
		
		if (System.currentTimeMillis() - lastMS > time) {
			
			if (reset)
				reset();
			
			return true;
				
			
		}else {
			return false;
		}
		
	}

}
