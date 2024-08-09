/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.events.game;

public class MouseUpdateEvent {

    private static final MouseUpdateEvent INSTANCE = new MouseUpdateEvent();

    public static MouseUpdateEvent get() {
        return INSTANCE;
    }

}
