package me.valk.manager.managers;

import javax.vecmath.*;

import me.valk.utils.waypoint.WayPoint;

import java.util.*;

public class WaypointManager {
	
	public static ArrayList<WayPoint> points = new ArrayList<WayPoint>();

	public static void addWayPoint(final String name, final int x, final int y, final int z) {
		WaypointManager.points.add(new WayPoint(new Vector3f((float) x, (float) y, (float) z), name));
	}

	public static void removeall() {
		WaypointManager.points.clear();
	}

	public static void removeWayPoint(final String name) {
		for (final WayPoint wp : WaypointManager.points) {
			if (wp.name.equalsIgnoreCase(name) && WaypointManager.points.contains(wp)) {
				WaypointManager.points.remove(wp);
			}
		}
	}
}
