package me.zeroeightsix.kami.event.events;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.event.KamiEvent;
import me.zeroeightsix.kami.util.Wrapper;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.SPacketEntityStatus;

public class TotemPopEvent extends KamiEvent {

    private Entity entity;

    public TotemPopEvent(Entity entity) {
        super();
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

}