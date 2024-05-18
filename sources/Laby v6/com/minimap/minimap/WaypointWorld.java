package com.minimap.minimap;

import java.util.*;

public class WaypointWorld
{
    public HashMap<String, WaypointSet> sets;
    public String current;
    
    public WaypointWorld() {
        this.current = "gui.xaero_default";
        (this.sets = new HashMap<String, WaypointSet>()).put("gui.xaero_default", new WaypointSet(this));
    }
    
    public WaypointSet getCurrentSet() {
        return this.sets.get(this.current);
    }
}
