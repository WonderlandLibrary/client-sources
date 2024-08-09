package ez.h.event.events;

import ez.h.event.*;

public class MouseKeyEvent extends Event
{
    public int key;
    
    public MouseKeyEvent(final int key) {
        this.key = key;
    }
}
