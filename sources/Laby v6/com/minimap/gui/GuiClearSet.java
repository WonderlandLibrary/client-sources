package com.minimap.gui;

import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;
import com.minimap.minimap.*;
import com.minimap.*;
import java.io.*;

public class GuiClearSet extends GuiYesNo
{
    private String key;
    private String name;
    private GuiScreen parentScreen;
    
    public GuiClearSet(final String setName, final String key, final String name, final GuiScreen parent) {
        super(null, I18n.format("gui.xaero_clear_set_message", new Object[0]) + ": " + setName + "?", I18n.format("gui.xaero_clear_set_message2", new Object[0]), 0);
        this.parentScreen = parent;
        this.key = key;
        this.name = name;
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
        switch (button.id) {
            case 0: {
                final WaypointSet set = Minimap.waypointMap.get(this.key).sets.get(this.name);
                if (set != null) {
                    set.list.clear();
                }
                XaeroMinimap.getSettings().saveWaypoints();
            }
            case 1: {
                this.mc.displayGuiScreen(this.parentScreen);
                break;
            }
        }
    }
}
