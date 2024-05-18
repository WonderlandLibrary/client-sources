package host.kix.uzi.events;

import com.darkmagician6.eventapi.events.callables.EventCancellable;

/**
 * Created by k1x on 4/23/17.
 */
public class CullingEvent extends EventCancellable {

    public CullingEvent(boolean cancelled){
        setCancelled(cancelled);
    }

}
