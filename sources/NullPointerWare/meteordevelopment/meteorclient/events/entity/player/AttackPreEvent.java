/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.events.entity.player;

import meteordevelopment.meteorclient.events.Cancellable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.HitResult;


public class AttackPreEvent extends Cancellable {
    private static final AttackPreEvent INSTANCE = new AttackPreEvent();

    public PlayerEntity player;
    public HitResult hitResult;

    public AttackPreEvent get(PlayerEntity player, HitResult hitResult) {
        INSTANCE.setCancelled(false);
        INSTANCE.player = player;
        INSTANCE.hitResult = hitResult;
        return INSTANCE;
    }

    public HitResult getHitResult() {
        return hitResult;
    }
}
