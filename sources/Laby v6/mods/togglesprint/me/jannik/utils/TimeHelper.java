package mods.togglesprint.me.jannik.utils;

public class TimeHelper {
	
	private long lastMS;
	
	public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }
	
    public boolean hasReached(float milliseconds) {
        return this.getCurrentMS() - this.lastMS >= milliseconds;
    }
    
    public void reset() {
        this.lastMS = this.getCurrentMS();
    }
}
