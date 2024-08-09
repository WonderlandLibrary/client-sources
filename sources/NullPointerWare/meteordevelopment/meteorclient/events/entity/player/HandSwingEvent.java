/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.events.entity.player;

import meteordevelopment.meteorclient.events.Cancellable;
import net.minecraft.util.Hand;

public final class HandSwingEvent extends Cancellable {

    private static final HandSwingEvent INSTANCE = new HandSwingEvent();

    public Hand hand;

    public static HandSwingEvent get(Hand hand) {
        INSTANCE.setCancelled(false);
        INSTANCE.hand = hand;
        return INSTANCE;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }
}
