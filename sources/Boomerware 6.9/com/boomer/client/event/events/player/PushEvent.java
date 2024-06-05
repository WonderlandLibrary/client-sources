package com.boomer.client.event.events.player;

import com.boomer.client.event.cancelable.CancelableEvent;

public class PushEvent extends CancelableEvent {
   private boolean pre;
    public PushEvent(boolean pre) {
        this.pre = pre;
    }
    public boolean isPre() {
        return pre;
    }
}
