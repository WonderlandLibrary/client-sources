package net.futureclient.client.events;

public class EventRender extends Event
{
    private float D;
    private EventType type;
    
    public EventRender(final EventType type, final float d) {
        super();
        this.type = type;
        this.D = d;
    }
    
    public EventType getType() {
        return this.type;
    }
    
    public float M() {
        return this.D;
    }
}
