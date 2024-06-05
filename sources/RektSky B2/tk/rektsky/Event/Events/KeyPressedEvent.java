package tk.rektsky.Event.Events;

import tk.rektsky.Event.*;

public class KeyPressedEvent extends Event
{
    private int key;
    
    public KeyPressedEvent(final int key) {
        this.key = key;
    }
    
    public int getKey() {
        return this.key;
    }
}
