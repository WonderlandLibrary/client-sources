package dev.luvbeeq.baritone.api.event.events;

import dev.luvbeeq.baritone.api.event.events.type.Cancellable;

/**
 * @author LoganDark
 */
public final class TabCompleteEvent extends Cancellable {

    public final String prefix;
    public String[] completions;

    public TabCompleteEvent(String prefix) {
        this.prefix = prefix;
        this.completions = null;
    }
}
