package net.shoreline.client.impl.event.entity;

import net.minecraft.util.Hand;
import net.shoreline.client.api.event.Cancelable;
import net.shoreline.client.api.event.Event;

@Cancelable
public class SwingEvent extends Event {
    private final Hand hand;

    public SwingEvent(Hand hand) {
        this.hand = hand;
    }

    public Hand getHand() {
        return hand;
    }
}
