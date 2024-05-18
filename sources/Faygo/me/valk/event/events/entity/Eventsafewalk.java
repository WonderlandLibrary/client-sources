package me.valk.event.events.entity;

import me.valk.event.Event;

public class Eventsafewalk extends Event
{
    private boolean shouldWalkSafely;
    
    public Eventsafewalk(final boolean shouldWalkSafely) {
        this.shouldWalkSafely = shouldWalkSafely;
    }
    
    public boolean getShouldWalkSafely() {
        return this.shouldWalkSafely;
    }
    
    public void setShouldWalkSafely(final boolean shouldWalkSafely) {
        this.shouldWalkSafely = shouldWalkSafely;
    }
}
