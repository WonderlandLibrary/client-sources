package com.kilo.manager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.kilo.Kilo;
import com.kilo.ui.inter.slotlist.part.Activity;
import com.kilo.ui.inter.slotlist.part.Friend;
import com.kilo.util.ActivityType;
import com.kilo.util.ServerUtil;

public class ActivityManager {

	private static List<Activity> latestActivities = new CopyOnWriteArrayList<Activity>();
	
	public static void addActivity(String id, ActivityType t, String i, String f, String ign, String ip, String msg) {
		latestActivities.add(new Activity(id, t, i, f, ign, ip, msg));
	}

	public static void loadActivities() {
		new Thread() {
			@Override
			public void run() {
				latestActivities = ServerUtil.getLatestActivity(Kilo.kilo().client.clientID);
			}
		}.start();
	}
	
	public static List<Activity> getList() {
		return latestActivities;
	}
	
	public static void addActivity(Activity a) {
		latestActivities.add(a);
	}
	
	public static void addActivity(int index, Activity a) {
		latestActivities.add(index, a);
	}
	
	public static void removeActivity(Activity a) {
		latestActivities.remove(a);
	}
	
	public static void removeActivity(int index) {
		latestActivities.remove(latestActivities.get(index));
	}
	
	public static Activity getActivity(int index) {
		if (latestActivities.size() == 0 || index >= latestActivities.size()) {
			return null;
		}
		return latestActivities.get(index);
	}
	
	public static int getIndex(Activity a) {
		return latestActivities.indexOf(a);
	}
	
	public static int getSize() {
		return getList().size();
	}
}
