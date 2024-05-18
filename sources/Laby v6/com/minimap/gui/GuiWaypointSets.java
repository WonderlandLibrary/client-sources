package com.minimap.gui;

import com.minimap.minimap.*;
import net.minecraft.client.resources.*;

public class GuiWaypointSets
{
    public int currentSet;
    String[] options;
    
    public GuiWaypointSets(final String c, final String worldID, final boolean canCreate) {
        final WaypointWorld w = Minimap.waypointMap.get(worldID);
        final int size = w.sets.size() + (canCreate ? 1 : 0);
        this.options = new String[size];
        final Object[] keys = w.sets.keySet().toArray();
        for (int i = 0; i < keys.length; ++i) {
            this.options[i] = (String)keys[i];
            if (this.options[i].equals(c)) {
                this.currentSet = i;
            }
        }
        if (canCreate) {
            this.options[this.options.length - 1] = "§8" + I18n.format("gui.xaero_create_set", new Object[0]);
        }
    }
}
