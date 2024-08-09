package ez.h.event.events;

import ez.h.event.*;

public class SafeWalkEvent extends Event
{
    private boolean isSafe;
    
    public void setSafe(final boolean isSafe) {
        this.isSafe = isSafe;
    }
    
    public boolean isSafe() {
        return this.isSafe;
    }
}
