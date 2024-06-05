package win.sightclient.notification;

public class Notification {

	private long startTime;
	private long duration;
	
	private String title;
	private String description;
	
	public Notification(String title, String description) {
		this.description = description;
		this.title = title;
		this.startTime = System.currentTimeMillis();
		this.duration = 3000;
	}
	
	public Notification(String title, String description, long duration) {
		this.description = description;
		this.title = title;
		this.startTime = System.currentTimeMillis();
		this.duration = duration;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public boolean isFinished() {
		return System.currentTimeMillis() - this.startTime > this.duration;
	}
	
	public long getLifeTime() {
		return System.currentTimeMillis() - this.startTime;
	}
	
	public long getDuration() {
		return this.duration;
	}
}
