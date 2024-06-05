package win.sightclient.script.values.classes;

public class TimerUtilsMC {

	private long lastMS = System.currentTimeMillis();
	
	public void reset() {
		this.lastMS = System.currentTimeMillis();
	}
	
	public boolean hasReached(double d) {
		return System.currentTimeMillis() - this.lastMS >= d;
	}
}
