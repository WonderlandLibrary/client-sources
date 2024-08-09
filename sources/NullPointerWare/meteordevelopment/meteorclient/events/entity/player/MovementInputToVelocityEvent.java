/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.events.entity.player;

public class MovementInputToVelocityEvent {
    private static final MovementInputToVelocityEvent INSTANCE = new MovementInputToVelocityEvent();

    public float yaw;

    public static MovementInputToVelocityEvent get(float yaw) {
        INSTANCE.yaw = yaw;
        return INSTANCE;
    }
}
