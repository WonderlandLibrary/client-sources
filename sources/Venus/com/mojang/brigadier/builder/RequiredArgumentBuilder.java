/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.brigadier.builder;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;

public class RequiredArgumentBuilder<S, T>
extends ArgumentBuilder<S, RequiredArgumentBuilder<S, T>> {
    private final String name;
    private final ArgumentType<T> type;
    private SuggestionProvider<S> suggestionsProvider = null;

    private RequiredArgumentBuilder(String string, ArgumentType<T> argumentType) {
        this.name = string;
        this.type = argumentType;
    }

    public static <S, T> RequiredArgumentBuilder<S, T> argument(String string, ArgumentType<T> argumentType) {
        return new RequiredArgumentBuilder<S, T>(string, argumentType);
    }

    public RequiredArgumentBuilder<S, T> suggests(SuggestionProvider<S> suggestionProvider) {
        this.suggestionsProvider = suggestionProvider;
        return this.getThis();
    }

    public SuggestionProvider<S> getSuggestionsProvider() {
        return this.suggestionsProvider;
    }

    @Override
    protected RequiredArgumentBuilder<S, T> getThis() {
        return this;
    }

    public ArgumentType<T> getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public ArgumentCommandNode<S, T> build() {
        ArgumentCommandNode argumentCommandNode = new ArgumentCommandNode(this.getName(), this.getType(), this.getCommand(), this.getRequirement(), this.getRedirect(), this.getRedirectModifier(), this.isFork(), this.getSuggestionsProvider());
        for (CommandNode commandNode : this.getArguments()) {
            argumentCommandNode.addChild(commandNode);
        }
        return argumentCommandNode;
    }

    @Override
    public CommandNode build() {
        return this.build();
    }

    @Override
    protected ArgumentBuilder getThis() {
        return this.getThis();
    }
}

