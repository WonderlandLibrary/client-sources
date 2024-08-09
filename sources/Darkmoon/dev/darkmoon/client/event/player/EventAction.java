package dev.darkmoon.client.event.player;

import com.darkmagician6.eventapi.events.Event;

public class EventAction implements Event {
    private boolean sprintState;

    public EventAction(boolean sprintState) {
        this.sprintState = sprintState;
    }

    public void setSprintState(boolean sprintState) {
        this.sprintState = sprintState;
    }

    public boolean getSprintState() {
        return this.sprintState;
    }
}
