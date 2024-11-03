package net.silentclient.client.utils;

import net.silentclient.client.gui.notification.Notification;
import net.silentclient.client.gui.notification.NotificationManager;

public class NotificationUtils {	
	public static void showNotification(String title, String message) {
		Notification notification = new Notification();
	
		notification.setNotification(title, message);
		NotificationManager.show(notification);
	}
}
