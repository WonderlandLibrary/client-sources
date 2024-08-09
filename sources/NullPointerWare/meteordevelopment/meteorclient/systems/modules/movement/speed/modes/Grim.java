/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.movement.speed.modes;

import meteordevelopment.meteorclient.systems.modules.movement.speed.SpeedMode;
import meteordevelopment.meteorclient.systems.modules.movement.speed.SpeedModes;
import meteordevelopment.meteorclient.utils.entity.fakeplayer.FakePlayerEntity;
import meteordevelopment.meteorclient.utils.player.MoveUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;

public final class Grim extends SpeedMode {

    public Grim() {
        super(SpeedModes.Grim);
    }

    @Override
    public void onTick() {
        for (Entity entity : mc.world.getEntities()) {
            if (Math.sqrt(mc.player.squaredDistanceTo(entity)) <= settings.grimDist.get() && isSpeedable(entity)) {
                if (!mc.player.isOnGround()) {
                    double boost = settings.grimBoost.get();
                    double velocityX = mc.player.getVelocity().x * 1.08 * boost;
                    double velocityZ = mc.player.getVelocity().z * 1.08 * boost;
                    mc.player.setVelocity(velocityX, mc.player.getVelocity().y, velocityZ);

                } else {
                    if (settings.grimGround.get()) {
                        double boost = settings.grimGroundAmount.get();
                        double velocityX = mc.player.getVelocity().x * 1.08 * boost;
                        double velocityZ = mc.player.getVelocity().z * 1.08 * boost;
                        mc.player.setVelocity(velocityX, mc.player.getVelocity().y, velocityZ);
                    }
                }
                // Doesnt stack velocity from different players
                break;
            }
        }
    }

    private boolean isSpeedable(Entity entity) {
        if (entity == mc.player) return false;
        if (!(entity instanceof LivingEntity)) return false;
        if (entity instanceof ArmorStandEntity) return false;
        if (entity instanceof FakePlayerEntity) return false;
        return true;
    }
}


