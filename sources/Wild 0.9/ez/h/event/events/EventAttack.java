package ez.h.event.events;

import ez.h.event.*;

public class EventAttack extends Event
{
    public vg attackEntity;
    
    public EventAttack(final vg attackEntity) {
        this.attackEntity = attackEntity;
    }
}
