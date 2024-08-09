/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.brigadier.tree;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.CommandContextBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.CommandNode;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

public class RootCommandNode<S>
extends CommandNode<S> {
    public RootCommandNode() {
        super(null, RootCommandNode::lambda$new$0, null, RootCommandNode::lambda$new$1, false);
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getUsageText() {
        return "";
    }

    @Override
    public void parse(StringReader stringReader, CommandContextBuilder<S> commandContextBuilder) throws CommandSyntaxException {
    }

    @Override
    public CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
        return Suggestions.empty();
    }

    @Override
    public boolean isValidInput(String string) {
        return true;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof RootCommandNode)) {
            return true;
        }
        return super.equals(object);
    }

    @Override
    public ArgumentBuilder<S, ?> createBuilder() {
        throw new IllegalStateException("Cannot convert root into a builder");
    }

    @Override
    protected String getSortedKey() {
        return "";
    }

    @Override
    public Collection<String> getExamples() {
        return Collections.emptyList();
    }

    public String toString() {
        return "<root>";
    }

    private static Collection lambda$new$1(CommandContext commandContext) throws CommandSyntaxException {
        return Collections.singleton(commandContext.getSource());
    }

    private static boolean lambda$new$0(Object object) {
        return false;
    }
}

