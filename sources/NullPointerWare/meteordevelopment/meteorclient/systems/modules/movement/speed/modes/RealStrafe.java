/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.movement.speed.modes;

import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;
import meteordevelopment.meteorclient.systems.modules.movement.speed.SpeedMode;
import meteordevelopment.meteorclient.systems.modules.movement.speed.SpeedModes;
import meteordevelopment.meteorclient.utils.player.MoveUtils;

public final class RealStrafe extends SpeedMode {

    public RealStrafe() {
        super(SpeedModes.RealStrafe);
    }

    @Override
    public void onMove(final PlayerMoveEvent event) {
        if (mc.player != null && MoveUtils.isMoving())
            MoveUtils.strafe();
    }
}
