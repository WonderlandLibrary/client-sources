/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.events.NodeEvent;

public final class AliasEvent
extends NodeEvent {
    public AliasEvent(String string, Mark mark, Mark mark2) {
        super(string, mark, mark2);
        if (string == null) {
            throw new NullPointerException("anchor is not specified for alias");
        }
    }

    @Override
    public Event.ID getEventId() {
        return Event.ID.Alias;
    }
}

