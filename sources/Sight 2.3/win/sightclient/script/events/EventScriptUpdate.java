package win.sightclient.script.events;

public class EventScriptUpdate extends ScriptEvent {

	private float yaw;
	private float pitch;
	private double y;
	
	public EventScriptUpdate(float yaw, float pitch, double y) {
		super();
		this.yaw = yaw;
		this.pitch = pitch;
		this.y = y;
	}
	
	public float getYaw() {
		return yaw;
	}
	
	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
	
	public float getPitch() {
		return pitch;
	}
	
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	
	public double getY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}
}
