/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import net.java.games.input.Component;
import net.java.games.input.Event;
import net.java.games.input.WinTabComponent;
import net.java.games.input.WinTabContext;
import net.java.games.input.WinTabPacket;

public class WinTabCursorComponent
extends WinTabComponent {
    private int index;

    protected WinTabCursorComponent(WinTabContext winTabContext, int n, String string, Component.Identifier identifier, int n2) {
        super(winTabContext, n, string, identifier);
        this.index = n2;
    }

    public Event processPacket(WinTabPacket winTabPacket) {
        Event event = null;
        if (winTabPacket.PK_CURSOR == this.index && this.lastKnownValue == 0.0f) {
            this.lastKnownValue = 1.0f;
            event = new Event();
            event.set(this, this.lastKnownValue, winTabPacket.PK_TIME * 1000L);
        } else if (winTabPacket.PK_CURSOR != this.index && this.lastKnownValue == 1.0f) {
            this.lastKnownValue = 0.0f;
            event = new Event();
            event.set(this, this.lastKnownValue, winTabPacket.PK_TIME * 1000L);
        }
        return event;
    }
}

