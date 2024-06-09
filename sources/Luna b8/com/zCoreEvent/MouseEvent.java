// 
// Decompiled by Procyon v0.5.30
// 

package com.zCoreEvent;

public class MouseEvent extends Event
{
    private int key;
    
    public MouseEvent(final int key) {
        this.key = key;
    }
    
    public int getKey() {
        return this.key;
    }
    
    public void setKey(final int key) {
        this.key = key;
    }
}
