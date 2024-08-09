/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.movement.speed.modes;

import meteordevelopment.meteorclient.systems.modules.movement.speed.SpeedMode;
import meteordevelopment.meteorclient.systems.modules.movement.speed.SpeedModes;
import meteordevelopment.meteorclient.utils.player.MoveUtils;

public final class MineMenClub extends SpeedMode {

    public MineMenClub() {
        super(SpeedModes.MineMenClub);
    }

    @Override
    public void onTick() {
        mc.options.jumpKey.setPressed(false);
        if (mc.player.hurtTime <= 6) {
            MoveUtils.strafe();
        }
        if (mc.player.isOnGround()) {
            mc.player.jump();
        }
    }
}
