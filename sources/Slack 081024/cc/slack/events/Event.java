package cc.slack.events;

import cc.slack.start.Slack;

public class Event {
    private boolean cancel;

    public Event call() {
        Slack.getInstance().getEventBus().publish(this);
        return this;
    }

    public boolean isCanceled() {
        return cancel;
    }

    public void cancel() {
        this.cancel = true;
    }
}
