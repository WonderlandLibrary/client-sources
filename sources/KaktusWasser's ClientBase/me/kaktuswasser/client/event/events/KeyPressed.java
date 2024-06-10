// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.event.events;

import java.util.Iterator;

import me.kaktuswasser.client.Client;
import me.kaktuswasser.client.event.Event;
import me.kaktuswasser.client.module.Module;

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
        if (Client.getEventManager().isCancelled()) {
            return;
        }
        for (final Module module : Client.getModuleManager().getModules()) {
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
