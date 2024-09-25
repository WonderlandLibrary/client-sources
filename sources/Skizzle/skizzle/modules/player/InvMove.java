/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.player;

import skizzle.events.Event;
import skizzle.events.listeners.EventUpdate;
import skizzle.modules.Module;

public class InvMove
extends Module {
    public InvMove() {
        super(Qprot0.0("\u6d11\u71c5\u565a\ua7e1\u4cc1\ue896\u8c20\u0167\u571b\ue7c5\u6282\uaf03\uefb8\u7248"), 23, Module.Category.PLAYER);
        InvMove Nigga;
    }

    public static {
        throw throwable;
    }

    @Override
    public void onEvent(Event Nigga) {
        if (Nigga instanceof EventUpdate) {
            Nigga.isPre();
        }
    }
}

