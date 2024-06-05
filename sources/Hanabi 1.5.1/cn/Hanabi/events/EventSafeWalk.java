package cn.Hanabi.events;

import com.darkmagician6.eventapi.events.callables.*;

public class EventSafeWalk extends EventCancellable
{
    public boolean safe;
    
    public EventSafeWalk(final boolean safe) {
        this.safe = safe;
    }
    
    public void setSafe(final boolean safe) {
        this.safe = safe;
    }
    
    public boolean getSafe() {
        return this.safe;
    }
}
