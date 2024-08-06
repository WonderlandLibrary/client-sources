package com.shroomclient.shroomclientnextgen.events;

import com.shroomclient.shroomclientnextgen.util.C;

public class Event {

    private boolean cancelled = false;

    public void cancel() {
        setCancelled(true);
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean flag) {
        if (!this.getClass().isAnnotationPresent(Cancellable.class)) {
            C.logger.error("Tried to cancel a non-cancellable event");
            return;
        }

        cancelled = flag;
    }
}
