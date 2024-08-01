package wtf.diablo.irc.event;

import wtf.diablo.client.event.api.AbstractEvent;

public final class IrcQueryOnlineEvent extends AbstractEvent {
    private final int count;

    public IrcQueryOnlineEvent(final int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
