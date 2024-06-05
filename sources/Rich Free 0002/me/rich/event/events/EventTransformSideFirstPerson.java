package me.rich.event.events;

import me.rich.event.Event;
import net.minecraft.util.EnumHandSide;

public class EventTransformSideFirstPerson
extends Event {
    private final EnumHandSide enumHandSide;

    public EventTransformSideFirstPerson(EnumHandSide enumHandSide) {
        this.enumHandSide = enumHandSide;
    }

    public EnumHandSide getEnumHandSide() {
        return this.enumHandSide;
    }
}