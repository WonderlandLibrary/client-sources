// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.event;

import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

import me.kaktuswasser.client.utilities.Logger;

import java.util.Iterator;
import java.util.List;

public class EventManager
{
    private List<Listener> listeners;
    private boolean cancelled;
    
    public void hook(final Event event) {
        if (!this.cancelled) {
            if (this.listeners == null || this.listeners.size() == 0) {
                return;
            }
            for (final Listener listener : this.listeners) {
                listener.onEvent(event);
            }
        }
    }
    
    public void addListener(final Listener listener) {
        if (!this.listeners.contains(listener) && !this.cancelled) {
            this.listeners.add(listener);
        }
    }
    
    public void removeListener(final Listener listener) {
        if (this.listeners.contains(listener) && !this.cancelled) {
            this.listeners.remove(listener);
        }
    }
    
    public void setupListeners() {
        this.listeners = new CopyOnWriteArrayList<Listener>();
    }
    
    public void organizeListeners() {
        Logger.writeConsole("Started loading up event listeners.");
        Collections.sort(this.listeners, new Comparator<Listener>() {
            @Override
            public int compare(final Listener listener1, final Listener listener2) {
                return listener1.toString().compareTo(listener2.toString());
            }
        });
        Logger.writeConsole("Succesfully loaded up " + this.listeners.size() + " listeners.");
    }
    
    public List<Listener> getListeners() {
        return this.listeners;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
}
