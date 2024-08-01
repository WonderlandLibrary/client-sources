package wtf.diablo.client.event.api;

import best.azura.eventbus.events.CancellableEvent;
import wtf.diablo.client.core.impl.Diablo;

public abstract class AbstractEvent extends CancellableEvent {
    private EventTypeEnum eventType;

    public AbstractEvent() {
        this.eventType = EventTypeEnum.PRE;
    }

    public EventTypeEnum getEventType() {
        return this.eventType;
    }

    public void setEventType(final EventTypeEnum eventType) {
        this.eventType = eventType;
    }

    public void call() {
        Diablo.getInstance().getEventBus().call(this);
    }
}
