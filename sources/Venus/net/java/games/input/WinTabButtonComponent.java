/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import net.java.games.input.Component;
import net.java.games.input.Event;
import net.java.games.input.WinTabComponent;
import net.java.games.input.WinTabContext;
import net.java.games.input.WinTabPacket;

public class WinTabButtonComponent
extends WinTabComponent {
    private int index;

    protected WinTabButtonComponent(WinTabContext winTabContext, int n, String string, Component.Identifier identifier, int n2) {
        super(winTabContext, n, string, identifier);
        this.index = n2;
    }

    public Event processPacket(WinTabPacket winTabPacket) {
        float f;
        Event event = null;
        float f2 = f = (winTabPacket.PK_BUTTONS & (int)Math.pow(2.0, this.index)) > 0 ? 1.0f : 0.0f;
        if (f != this.getPollData()) {
            this.lastKnownValue = f;
            event = new Event();
            event.set(this, f, winTabPacket.PK_TIME * 1000L);
            return event;
        }
        return event;
    }
}

