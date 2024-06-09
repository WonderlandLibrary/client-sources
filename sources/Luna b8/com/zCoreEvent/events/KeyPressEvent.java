// 
// 
package com.zCoreEvent.events;

import com.zCoreEvent.Event;

public class KeyPressEvent extends Event
{
    private int key;
    
    public KeyPressEvent(final int key) {
        this.key = key;
    }
    
    public int getKey() {
        return this.key;
    }
    
    
    public void setKey(final int key) {
        this.key = key;
    }
}
