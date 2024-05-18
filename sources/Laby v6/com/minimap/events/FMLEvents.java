package com.minimap.events;

import net.minecraft.client.settings.*;
import java.util.*;
import de.labystudio.modapi.events.*;
import com.minimap.*;
import com.minimap.minimap.*;
import net.minecraft.client.*;
import de.labystudio.modapi.*;

public class FMLEvents implements Listener
{
    public ArrayList<KeyEvent> keyEvents;
    public ArrayList<KeyEvent> oldKeyEvents;
    
    public FMLEvents() {
        this.keyEvents = new ArrayList<KeyEvent>();
        this.oldKeyEvents = new ArrayList<KeyEvent>();
    }
    
    private boolean eventExists(final KeyBinding kb) {
        for (final KeyEvent o : this.keyEvents) {
            if (o.kb == kb) {
                return true;
            }
        }
        return this.oldEventExists(kb);
    }
    
    private boolean oldEventExists(final KeyBinding kb) {
        for (final KeyEvent o : this.oldKeyEvents) {
            if (o.kb == kb) {
                return true;
            }
        }
        return false;
    }
    
    @EventHandler
    public void onGameTick(final GameTickEvent e) {
        if (XaeroMinimap.getSettings() != null && (XaeroMinimap.getSettings().getDeathpoints() || XaeroMinimap.getSettings().getShowWaypoints() || XaeroMinimap.getSettings().getShowIngameWaypoints())) {
            Minimap.updateWaypoints();
        }
        else if (Minimap.waypoints != null) {
            Minimap.waypoints = null;
        }
        final Minecraft mc = XaeroMinimap.mc;
        for (int i = 0; i < this.keyEvents.size(); ++i) {
            final KeyEvent ke = this.keyEvents.get(i);
            if (mc.currentScreen == null) {
                XaeroMinimap.ch.keyDown(ke.kb, ke.tickEnd, ke.isRepeat);
            }
            if (!ke.isRepeat) {
                if (!this.oldEventExists(ke.kb)) {
                    this.oldKeyEvents.add(ke);
                }
                this.keyEvents.remove(i);
                --i;
            }
            else if (!ControlsHandler.isDown(ke.kb)) {
                XaeroMinimap.ch.keyUp(ke.kb, ke.tickEnd);
                this.keyEvents.remove(i);
                --i;
            }
        }
        for (int i = 0; i < this.oldKeyEvents.size(); ++i) {
            final KeyEvent ke = this.oldKeyEvents.get(i);
            if (!ControlsHandler.isDown(ke.kb)) {
                XaeroMinimap.ch.keyUp(ke.kb, ke.tickEnd);
                this.oldKeyEvents.remove(i);
                --i;
            }
        }
    }
}
