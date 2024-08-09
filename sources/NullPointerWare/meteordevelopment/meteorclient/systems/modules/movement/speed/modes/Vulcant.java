/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.movement.speed.modes;

import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.movement.speed.SpeedMode;
import meteordevelopment.meteorclient.systems.modules.movement.speed.SpeedModes;
import meteordevelopment.meteorclient.systems.modules.world.Timer;
import meteordevelopment.meteorclient.utils.player.MoveUtils;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public final class Vulcant extends SpeedMode {
    public Vulcant() {
        super(SpeedModes.Vulcant);
    }

    int ticks;

    double timer = 1;
    double y = 0;

    @Override
    public void onActivate() {
        y = 0;
        timer = 1;
    }

    @Override
    public void onTick() {
        Modules.get().get(Timer.class).setOverride(timer);
    }

    @Override
    public void onMove(final PlayerMoveEvent event) {
        if (mc.player.isOnGround()) {
            ticks = 0;

            timer = 1.05;
            y = 0.01;
            MoveUtils.setYVelocity(0.01);
            MoveUtils.strafe(0.4175 + MoveUtils.getBaseMoveSpeed());
        } else {
            ticks++;


            timer = 1;
            if (y == 0.01) {
                MoveUtils.strafe(hasSpeed() ? MoveUtils.getBaseMoveEventSpeed() * 1.15 : MoveUtils.getBaseMoveSpeed() * 1.04);
                y = 0;
            }

            if (mc.player.age % 10 == 0) {
                mc.player.setVelocity(mc.player.getVelocity().x * 1.00575, mc.player.getVelocity().y, mc.player.getVelocity().z * 1.00575);
                //MoveUtils.strafe(hasSpeed() ? MoveUtils.speed() * 1.08 : MoveUtils.speed());
                timer = 1.18;
            }
        }
    }

    private boolean hasSpeed() {
        for (StatusEffectInstance statusEffectInstance : mc.player.getStatusEffects()) {
            if (statusEffectInstance.getEffectType() == StatusEffects.SPEED) return true;
        }
        return false;
    }

}
