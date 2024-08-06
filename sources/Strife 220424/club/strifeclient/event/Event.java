package club.strifeclient.event;

import best.azura.eventbus.events.CancellableEvent;

public class Event extends CancellableEvent {
    public EventState eventState;

    public boolean isPre() {
        return eventState == EventState.PRE;
    }
    public boolean isPost() {
        return eventState == EventState.POST;
    }
    public boolean isUpdate() {
        return eventState == EventState.UPDATE;
    }
}
