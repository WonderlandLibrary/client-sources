package ez.h.event.events;

import ez.h.event.*;

public class EventPostAttack extends Event
{
    public vg attackedEntity;
    
    public EventPostAttack(final vg attackedEntity) {
        this.attackedEntity = attackedEntity;
    }
}
