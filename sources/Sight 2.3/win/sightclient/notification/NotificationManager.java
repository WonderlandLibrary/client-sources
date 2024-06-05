package win.sightclient.notification;

import java.util.ArrayList;

public class NotificationManager {

	private ArrayList<Notification> notifsToRender = new ArrayList<Notification>();
	private NotificationRender nr;
	
	public NotificationManager() {
		nr = new NotificationRender();
	}
	
	public void send(Notification notif) {
		notifsToRender.add(notif);
	}
	
	public void delete(int index) {
		notifsToRender.remove(index);
	}
	
	public void render() {
		this.nr.onRender();
	}
	
	public ArrayList<Notification> getNotifications() {
		return this.notifsToRender;
	}
}
