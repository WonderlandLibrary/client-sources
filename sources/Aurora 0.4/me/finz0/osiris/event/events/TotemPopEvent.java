package me.finz0.osiris.event.events;

import me.finz0.osiris.event.OsirisEvent;

import me.finz0.osiris.util.OsirisTessellator;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.SPacketEntityStatus;


public class TotemPopEvent extends OsirisEvent {

    private Entity entity;

    public TotemPopEvent(Entity entity) {
        super();
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

}