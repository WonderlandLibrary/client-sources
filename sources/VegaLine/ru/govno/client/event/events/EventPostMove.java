/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.event.events;

import ru.govno.client.event.Event;

public class EventPostMove
extends Event {
    private double horizontalMove;

    public EventPostMove(double horizontalMove) {
        this.horizontalMove = horizontalMove;
    }

    public double getHorizontalMove() {
        return this.horizontalMove;
    }
}

