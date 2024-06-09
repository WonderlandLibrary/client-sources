// 
// Decompiled by Procyon v0.5.30
// 

package com.zCoreEvent;

import java.util.Iterator;

import net.minecraft.network.Packet;

import java.lang.reflect.InvocationTargetException;

public abstract class Event
{
	 protected Packet packet;
    private boolean cancelled;
    
    public Event call() {
        this.cancelled = false;
        call(this);
        return this;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public Packet getPacket() {
        return this.packet;
    }
    
    public void setCancelled(final boolean state) {
        this.cancelled = state;
    }
    
    private static final void call(final Event event) {
        final FlexibleArray<MethodData> dataList = EventManager.get(event.getClass());
        if (dataList != null) {
            for (final MethodData data : dataList) {
                try {
                    data.target.invoke(data.source, event);
                }
                catch (IllegalAccessException e2) {
                    System.out.println("Can't invoke '" + data.target.getName() + "' because it's not accessible.");
                }
                catch (IllegalArgumentException e3) {
                    System.out.println("Can't invoke '" + data.target.getName() + "' because the parameter/s don't match.");
                }
                catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public enum State
    {
        PRE("PRE", 0), 
        POST("POST", 1);
        
        private State(final String s, final int n) {
        }
    }
}
