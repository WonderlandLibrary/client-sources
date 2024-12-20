/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.events.Event;

public abstract class NodeEvent
extends Event {
    private final String anchor;

    public NodeEvent(String string, Mark mark, Mark mark2) {
        super(mark, mark2);
        this.anchor = string;
    }

    public String getAnchor() {
        return this.anchor;
    }

    @Override
    protected String getArguments() {
        return "anchor=" + this.anchor;
    }
}

