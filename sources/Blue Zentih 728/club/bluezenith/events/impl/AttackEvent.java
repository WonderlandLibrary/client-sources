package club.bluezenith.events.impl;

import club.bluezenith.events.EventType;
import club.bluezenith.events.MultiTypeEvent;
import net.minecraft.entity.Entity;

@SuppressWarnings("unused")
public class AttackEvent extends MultiTypeEvent {

    public final Entity target;
    public EventType type;

    public AttackEvent(Entity target, EventType type) {
        this.target = target;
        this.type = type;
    }
}
