package ru.FecuritySQ.event.imp;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import ru.FecuritySQ.event.Event;

public class EventAttack extends Event {
    private Entity target;
    private PlayerEntity player;
    public EventAttack(Entity entity, PlayerEntity player1){
        this.target = entity;
        player = player1;

    }

    public Entity getTarget() {
        return target;
    }

    public PlayerEntity getPlayer() {
        return player;
    }
}