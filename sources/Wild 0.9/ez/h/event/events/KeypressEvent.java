package ez.h.event.events;

import ez.h.event.*;

public class KeypressEvent extends Event
{
    public int key;
    
    public KeypressEvent(final int key) {
        this.key = key;
    }
}
