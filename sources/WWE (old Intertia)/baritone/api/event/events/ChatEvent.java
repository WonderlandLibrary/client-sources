package baritone.api.event.events;

import baritone.api.event.events.type.*;

public final class ChatEvent extends Cancellable
{
    private final String message;
    
    public ChatEvent(final String message) {
        super();
        this.message = message;
    }
    
    public final String getMessage() {
        return this.message;
    }
}
