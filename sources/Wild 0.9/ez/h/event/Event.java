package ez.h.event;

import ez.h.*;
import java.util.*;
import java.lang.reflect.*;

public abstract class Event
{
    private boolean cancelled;
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
    
    private static void call(final Event event) throws InvocationTargetException, IllegalAccessException {
        final ArrayHelper<Data> value = Main.instance.eventManager.get(event.getClass());
        if (value != null) {
            for (final Data data : value) {
                data.target.invoke(data.source, event);
            }
        }
    }
    
    public Event call() {
        try {
            this.cancelled = false;
            call(this);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return this;
    }
}
