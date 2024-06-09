// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.event.events;

import java.util.Iterator;
import net.andrewsnetwork.icarus.module.Module;
import net.andrewsnetwork.icarus.Icarus;
import net.andrewsnetwork.icarus.event.Event;

public class KeyPressed extends Event
{
    private int key;
    
    public KeyPressed(final int key) {
        this.key = key;
    }
    
    public void setKey(final int key) {
        this.key = key;
    }
    
    public void checkKeyboard() {
        if (Icarus.getEventManager().isCancelled()) {
            return;
        }
        for (final Module module : Icarus.getModuleManager().getModules()) {
            if (module.getKey() == 0) {
                continue;
            }
            if (this.key != module.getKey()) {
                continue;
            }
            module.toggle();
        }
    }
    
    public int getKey() {
        return this.key;
    }
}
