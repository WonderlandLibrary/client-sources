/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.nodes;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.Tag;

public class ScalarNode
extends Node {
    private final DumperOptions.ScalarStyle style;
    private final String value;

    public ScalarNode(Tag tag, String string, Mark mark, Mark mark2, DumperOptions.ScalarStyle scalarStyle) {
        this(tag, true, string, mark, mark2, scalarStyle);
    }

    public ScalarNode(Tag tag, boolean bl, String string, Mark mark, Mark mark2, DumperOptions.ScalarStyle scalarStyle) {
        super(tag, mark, mark2);
        if (string == null) {
            throw new NullPointerException("value in a Node is required.");
        }
        this.value = string;
        if (scalarStyle == null) {
            throw new NullPointerException("Scalar style must be provided.");
        }
        this.style = scalarStyle;
        this.resolved = bl;
    }

    public DumperOptions.ScalarStyle getScalarStyle() {
        return this.style;
    }

    @Override
    public NodeId getNodeId() {
        return NodeId.scalar;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        return "<" + this.getClass().getName() + " (tag=" + this.getTag() + ", value=" + this.getValue() + ")>";
    }

    public boolean isPlain() {
        return this.style == DumperOptions.ScalarStyle.PLAIN;
    }
}

