/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.events.CollectionStartEvent;
import org.yaml.snakeyaml.events.Event;

public final class SequenceStartEvent
extends CollectionStartEvent {
    public SequenceStartEvent(String string, String string2, boolean bl, Mark mark, Mark mark2, DumperOptions.FlowStyle flowStyle) {
        super(string, string2, bl, mark, mark2, flowStyle);
    }

    @Override
    public Event.ID getEventId() {
        return Event.ID.SequenceStart;
    }
}

