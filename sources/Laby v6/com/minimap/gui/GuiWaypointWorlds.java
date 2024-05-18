package com.minimap.gui;

import com.minimap.minimap.*;

public class GuiWaypointWorlds
{
    public int currentWorld;
    public int autoWorld;
    public Object[] keys;
    String[] options;
    public static final String[] dimensions;
    
    public GuiWaypointWorlds(final String c, final String a) {
        this.keys = Minimap.waypointMap.keySet().toArray();
        this.options = new String[this.keys.length];
        this.currentWorld = -1;
        this.autoWorld = -1;
        for (int i = 0; i < this.options.length; ++i) {
            try {
                this.options[i] = "Error";
                final String worldID = (String)this.keys[i];
                if (this.currentWorld == -1 && worldID.equals(c)) {
                    this.currentWorld = i;
                }
                if (this.autoWorld == -1 && worldID.equals(a)) {
                    this.autoWorld = i;
                }
                final String[] details = worldID.split("_");
                String dimension = "";
                if (details[details.length - 1].startsWith("DIM")) {
                    final String dim = details[details.length - 1].substring(3);
                    try {
                        final int id = Integer.parseInt(dim) + 1;
                        if (id < GuiWaypointWorlds.dimensions.length && id >= 0) {
                            dimension = " " + GuiWaypointWorlds.dimensions[id];
                        }
                        else {
                            dimension = " Dimension " + id;
                        }
                    }
                    catch (NumberFormatException e2) {
                        dimension = " " + dim.substring(1);
                    }
                }
                this.options[i] = details[details.length - 2] + dimension;
                if (this.autoWorld == i) {
                    final StringBuilder sb = new StringBuilder();
                    final String[] options = this.options;
                    final int n = i;
                    options[n] = sb.append(options[n]).append(" (auto)").toString();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    static {
        dimensions = new String[] { "Nether", "Overworld", "The End" };
    }
}
