package cc.slack.utils;

import cc.slack.start.Slack;
import cc.slack.events.Event;

public final class EventUtil {
    public static void register(Object o) {
        Slack.getInstance().getEventBus().subscribe(o);
    }

    public static void unRegister(Object o) {
        Slack.getInstance().getEventBus().unsubscribe(o);
    }

    public static void call(Event e) {
        Slack.getInstance().getEventBus().publish(e);
    }
}
