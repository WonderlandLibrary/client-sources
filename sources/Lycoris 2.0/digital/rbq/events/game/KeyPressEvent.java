/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.events.game;

import digital.rbq.events.Event;

public final class KeyPressEvent
implements Event {
    private final int keyCode;

    public KeyPressEvent(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return this.keyCode;
    }
}

