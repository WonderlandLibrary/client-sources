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
import net.minecraft.entity.effect.StatusEffects;

public final class VulcantGround extends SpeedMode {

    public VulcantGround() {
        super(SpeedModes.VulcantGround);
    }

    double y = 0;
    double timer = 1;

    @Override
    public void onDeactivate() {
        y = 0;
        timer = 1;
    }

    @Override
    public void onTick() {
        Modules.get().get(Timer.class).setOverride(timer);
    }

    @Override
    public void onMove(PlayerMoveEvent event) {
        if (mc.player.isOnGround()) {
            timer = 1.05;
            y = 0.01;
            MoveUtils.setYVelocity(0.01);
            MoveUtils.strafe(0.4175 + MoveUtils.getSpeedBoost(1.53));
        } else {
            timer = 1;
            if (y == 0.01) {
                MoveUtils.strafe(mc.player.hasStatusEffect(StatusEffects.SPEED) ? MoveUtils.getBaseMoveSpeed() * 1.15 : MoveUtils.getBaseMoveSpeed() * 1.04);
                y = 0;
            }

            if (mc.player.age % 10 == 0) {

                mc.player.setVelocity(mc.player.getVelocity().x * 1.00575, mc.player.getVelocity().y, mc.player.getVelocity().z * 1.00575);

                // MoveUtils.strafe(mc.player.hasStatusEffect(StatusEffects.SPEED) ? MoveUtils.getSpeedSQRT() * 1.08 : MoveUtils.getSpeedSQRT());

                timer = 1.18;
            }
        }
    }
}
