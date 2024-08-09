/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.brigadier;

import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.context.CommandContextBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import java.util.Collections;
import java.util.Map;

public class ParseResults<S> {
    private final CommandContextBuilder<S> context;
    private final Map<CommandNode<S>, CommandSyntaxException> exceptions;
    private final ImmutableStringReader reader;

    public ParseResults(CommandContextBuilder<S> commandContextBuilder, ImmutableStringReader immutableStringReader, Map<CommandNode<S>, CommandSyntaxException> map) {
        this.context = commandContextBuilder;
        this.reader = immutableStringReader;
        this.exceptions = map;
    }

    public ParseResults(CommandContextBuilder<S> commandContextBuilder) {
        this(commandContextBuilder, new StringReader(""), Collections.emptyMap());
    }

    public CommandContextBuilder<S> getContext() {
        return this.context;
    }

    public ImmutableStringReader getReader() {
        return this.reader;
    }

    public Map<CommandNode<S>, CommandSyntaxException> getExceptions() {
        return this.exceptions;
    }
}

