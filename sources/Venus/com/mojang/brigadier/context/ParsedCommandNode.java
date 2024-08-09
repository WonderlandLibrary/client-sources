/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.brigadier.context;

import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.tree.CommandNode;
import java.util.Objects;

public class ParsedCommandNode<S> {
    private final CommandNode<S> node;
    private final StringRange range;

    public ParsedCommandNode(CommandNode<S> commandNode, StringRange stringRange) {
        this.node = commandNode;
        this.range = stringRange;
    }

    public CommandNode<S> getNode() {
        return this.node;
    }

    public StringRange getRange() {
        return this.range;
    }

    public String toString() {
        return this.node + "@" + this.range;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        ParsedCommandNode parsedCommandNode = (ParsedCommandNode)object;
        return Objects.equals(this.node, parsedCommandNode.node) && Objects.equals(this.range, parsedCommandNode.range);
    }

    public int hashCode() {
        return Objects.hash(this.node, this.range);
    }
}

