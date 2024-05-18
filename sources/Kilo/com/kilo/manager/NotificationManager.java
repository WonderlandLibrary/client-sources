package com.kilo.manager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class NotificationManager {

	public static List<Notification> list = new CopyOnWriteArrayList<Notification>();
	
	public static void update() {
		if (list.size() > 0) {
			list.get(0).update();
			if (list.size() > 1) {
				for(int i = 1; i < list.size(); i++) {
					list.get(i).timer.reset();
				}
			}
		}
	}
	
	public static void render(float opacity) {
		if (list.size() > 0) {
			list.get(0).render(opacity);
		}
	}
}
