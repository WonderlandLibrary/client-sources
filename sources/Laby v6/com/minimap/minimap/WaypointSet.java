package com.minimap.minimap;

import java.util.*;

public class WaypointSet
{
    public WaypointWorld world;
    public ArrayList<Waypoint> list;
    
    public WaypointSet(final WaypointWorld world) {
        this.world = world;
        this.list = new ArrayList<Waypoint>();
    }
}
