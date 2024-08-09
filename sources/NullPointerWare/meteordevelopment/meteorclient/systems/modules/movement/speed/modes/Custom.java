/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.movement.speed.modes;

import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;
import meteordevelopment.meteorclient.systems.modules.movement.speed.SpeedMode;
import meteordevelopment.meteorclient.systems.modules.movement.speed.SpeedModes;
import meteordevelopment.meteorclient.utils.player.PlayerUtils;

public final class Custom extends SpeedMode {

    public Custom() {
        super(SpeedModes.Custom);
    }

    @Override
    public void onMove(final PlayerMoveEvent event) {
        //Auto Jump
        if (settings.autojump.get() && mc.player.isOnGround()) {
            if (!settings.MoveOnly.get()) {
                mc.player.jump();
            }
            if (settings.MoveOnly.get() && PlayerUtils.isMoving()) {
                mc.player.jump();
            }
        }
        //Y-Motion
        if (mc.player.isOnGround() && PlayerUtils.isMoving() && settings.ymotiontoggle.get()) {
            mc.player.setVelocity(mc.player.getVelocity().getX(), settings.ymotion.get(), mc.player.getVelocity().getZ());
        }
    }
}
