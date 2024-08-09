/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.movement;

import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.mixininterface.IVec3d;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.PlayerUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.math.Vec3d;

public final class VulcanBhop extends Module {

    public VulcanBhop() {
        super(Categories.Combat, "Vulcan-bhop", "Bhop but for vulcan.");
    }

    @EventHandler
    public void onMove(final PlayerMoveEvent event) {

        // setting the bps on some random number above 11 and under 12 bc like rounded numbers get flagged "vulcan is an advaneced anticheat" JOKES
        Vec3d vel = PlayerUtils.getHorizontalVelocity(11.6345);

        // getting velocity X and Z
        double velX = vel.getX();
        double velZ = vel.getZ();

        // returing if the player isn't on ground else it'll flag for strafe or something
        if (!mc.player.isOnGround()) return;

        // setting the X and Z movement
        ((IVec3d) event.movement).set(velX, event.movement.y, velZ);
    }

    @EventHandler
    private void onTick(final TickEvent.Pre event) {
        if (mc.player.isOnGround()) {
            // jumping when the player is on ground else it'll flag for speed :)
            mc.player.jump();
        }
    }
}
