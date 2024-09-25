package eze.events;

public enum EventDirection
{
    INCOMING("INCOMING", 0), 
    OUTGOING("OUTGOING", 1);
    
    private EventDirection(final String name, final int ordinal) {
    }
}
