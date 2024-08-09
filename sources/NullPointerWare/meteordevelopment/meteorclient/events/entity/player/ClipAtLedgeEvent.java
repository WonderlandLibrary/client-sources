/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.events.entity.player;

public class ClipAtLedgeEvent {
    private static final ClipAtLedgeEvent INSTANCE = new ClipAtLedgeEvent();

    public boolean clip;

    public static ClipAtLedgeEvent get(boolean clip) {
        INSTANCE.clip = clip;
        return INSTANCE;
    }
}
