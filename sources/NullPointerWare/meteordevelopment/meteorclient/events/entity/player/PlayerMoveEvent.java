/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.events.entity.player;

import meteordevelopment.meteorclient.events.Cancellable;
import meteordevelopment.meteorclient.mixininterface.IVec3d;
import net.minecraft.entity.MovementType;
import net.minecraft.util.math.Vec3d;

public class PlayerMoveEvent extends Cancellable {
    private static final PlayerMoveEvent INSTANCE = new PlayerMoveEvent();

    public MovementType type;
    public Vec3d movement;

    public static PlayerMoveEvent get(MovementType type, Vec3d movement) {
        INSTANCE.type = type;
        INSTANCE.movement = movement;
        return INSTANCE;
    }

    public void setY(final double motionY) {
        ((IVec3d) movement).setY(motionY);
    }

    public double getX() {
        return movement.getX();
    }
    public double getZ() {
        return movement.getZ();
    }



    public void setX(final double speedX) {
        ((IVec3d) movement).setX(speedX);
    }

    public void setZ(final double speedZ) {
        ((IVec3d) movement).setZ(speedZ);
    }

}
