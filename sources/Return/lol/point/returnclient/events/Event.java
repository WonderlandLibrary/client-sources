package lol.point.returnclient.events;

import lombok.Getter;
import lombok.Setter;
import me.zero.alpine.event.CancellableEvent;
import me.zero.alpine.event.EventPhase;

@Getter
@Setter
public abstract class Event extends CancellableEvent {

    private EventPhase eventPhase;
    private EventFlow eventFlow;

    public boolean isPre() {
        if (eventPhase == null)
            return false;
        return eventPhase == EventPhase.PRE;
    }

    public boolean isON() {
        if (eventPhase == null) return false;
        return eventPhase == EventPhase.ON;
    }

    public boolean isPost() {
        if (eventPhase == null) return false;
        return eventPhase == EventPhase.POST;
    }

    public boolean isInbound() {
        if (eventFlow == null) return false;
        return eventFlow == EventFlow.INBOUND;
    }

    public boolean isOutbound() {
        if (eventFlow == null) return false;
        return eventFlow == EventFlow.OUTBOUND;
    }
}