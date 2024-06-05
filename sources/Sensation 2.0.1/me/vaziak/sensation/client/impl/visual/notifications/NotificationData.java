package me.vaziak.sensation.client.impl.visual.notifications;

public class NotificationData {
    private String title, info;
    private long currTime;
    private NotificationType type;
    
    public enum NotificationType {
    	INFO, WARNING, ERROR
    }
    
    public NotificationData(String title, String info, long currTime,NotificationType notificationType) {
        this.title = title;
        this.info = info;
        this.currTime = currTime;
        this.type = notificationType;
    }
    
    public int currTime() {
    	return (int) currTime;
    }
    
    public String info() {
    	return info;
    }

    public String getTitle() {
        return title;
    }

    public NotificationType type() {
		return type;
    }
}
