/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.events.game;

import digital.rbq.events.Cancellable;
import digital.rbq.events.Event;

public final class SendMessageEvent
extends Cancellable
implements Event {
    private final String message;

    public SendMessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}

