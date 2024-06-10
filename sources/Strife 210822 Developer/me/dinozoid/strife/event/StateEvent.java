package me.dinozoid.strife.event;

import me.dinozoid.strife.alpine.event.EventState;

public class StateEvent extends Event {

    private EventState state;

    public StateEvent(EventState state) {
        this.state = state;
    }

    public EventState state() {
        return state;
    }

    public void state(EventState state) {
        this.state = state;
    }
}
