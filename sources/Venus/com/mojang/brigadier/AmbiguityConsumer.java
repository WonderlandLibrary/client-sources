/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.brigadier;

import com.mojang.brigadier.tree.CommandNode;
import java.util.Collection;

@FunctionalInterface
public interface AmbiguityConsumer<S> {
    public void ambiguous(CommandNode<S> var1, CommandNode<S> var2, CommandNode<S> var3, Collection<String> var4);
}

