/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.events.entity.player;

import meteordevelopment.meteorclient.events.Cancellable;

public class UpdateEvent extends Cancellable {
    private static final UpdateEvent INSTANCE = new UpdateEvent();

    public static UpdateEvent get() {
        INSTANCE.setCancelled(false);
        return INSTANCE;
    }
}
