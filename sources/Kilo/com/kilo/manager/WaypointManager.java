package com.kilo.manager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.kilo.Kilo;
import com.kilo.ui.inter.slotlist.part.Waypoint;
import com.kilo.util.ServerUtil;

public class WaypointManager {

	private static List<Waypoint> waypoints = new CopyOnWriteArrayList<Waypoint>();
	
	public static List<Waypoint> getList() {
		return waypoints;
	}
	
	public static void addWaypoint(Waypoint f) {
		waypoints.add(f);
	}
	
	public static void addWaypoint(int index, Waypoint w) {
		waypoints.add(index, w);
	}
	
	public static void removeWaypoint(Waypoint w) {
		waypoints.remove(w);
	}
	
	public static void removeWaypoint(int index) {
		waypoints.remove(waypoints.get(index));
	}
	
	public static Waypoint getWaypoint(int index) {
		if (waypoints.size() == 0) {
			return null;
		}
		return waypoints.get(index);
	}
	
	public static Waypoint getWaypoint(String n) {
		for(Waypoint w : waypoints) {
			if (w.name.equalsIgnoreCase(n)) {
				return w;
			}
		}
		return null;
	}
	
	public static int getIndex(Waypoint w) {
		return waypoints.indexOf(w);
	}
	
	public static int getSize() {
		return getList().size();
	}
}
