/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.brigadier.tree;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.RedirectModifier;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.CommandContextBuilder;
import com.mojang.brigadier.context.ParsedArgument;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.CommandNode;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

public class ArgumentCommandNode<S, T>
extends CommandNode<S> {
    private static final String USAGE_ARGUMENT_OPEN = "<";
    private static final String USAGE_ARGUMENT_CLOSE = ">";
    private final String name;
    private final ArgumentType<T> type;
    private final SuggestionProvider<S> customSuggestions;

    public ArgumentCommandNode(String string, ArgumentType<T> argumentType, Command<S> command, Predicate<S> predicate, CommandNode<S> commandNode, RedirectModifier<S> redirectModifier, boolean bl, SuggestionProvider<S> suggestionProvider) {
        super(command, predicate, commandNode, redirectModifier, bl);
        this.name = string;
        this.type = argumentType;
        this.customSuggestions = suggestionProvider;
    }

    public ArgumentType<T> getType() {
        return this.type;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getUsageText() {
        return USAGE_ARGUMENT_OPEN + this.name + USAGE_ARGUMENT_CLOSE;
    }

    public SuggestionProvider<S> getCustomSuggestions() {
        return this.customSuggestions;
    }

    @Override
    public void parse(StringReader stringReader, CommandContextBuilder<S> commandContextBuilder) throws CommandSyntaxException {
        int n = stringReader.getCursor();
        T t = this.type.parse(stringReader);
        ParsedArgument parsedArgument = new ParsedArgument(n, stringReader.getCursor(), t);
        commandContextBuilder.withArgument(this.name, parsedArgument);
        commandContextBuilder.withNode(this, parsedArgument.getRange());
    }

    @Override
    public CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) throws CommandSyntaxException {
        if (this.customSuggestions == null) {
            return this.type.listSuggestions(commandContext, suggestionsBuilder);
        }
        return this.customSuggestions.getSuggestions(commandContext, suggestionsBuilder);
    }

    @Override
    public RequiredArgumentBuilder<S, T> createBuilder() {
        RequiredArgumentBuilder requiredArgumentBuilder = RequiredArgumentBuilder.argument(this.name, this.type);
        requiredArgumentBuilder.requires(this.getRequirement());
        requiredArgumentBuilder.forward(this.getRedirect(), this.getRedirectModifier(), this.isFork());
        requiredArgumentBuilder.suggests(this.customSuggestions);
        if (this.getCommand() != null) {
            requiredArgumentBuilder.executes(this.getCommand());
        }
        return requiredArgumentBuilder;
    }

    @Override
    public boolean isValidInput(String string) {
        try {
            StringReader stringReader = new StringReader(string);
            this.type.parse(stringReader);
            return !stringReader.canRead() || stringReader.peek() == ' ';
        } catch (CommandSyntaxException commandSyntaxException) {
            return true;
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof ArgumentCommandNode)) {
            return true;
        }
        ArgumentCommandNode argumentCommandNode = (ArgumentCommandNode)object;
        if (!this.name.equals(argumentCommandNode.name)) {
            return true;
        }
        if (!this.type.equals(argumentCommandNode.type)) {
            return true;
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int n = this.name.hashCode();
        n = 31 * n + this.type.hashCode();
        return n;
    }

    @Override
    protected String getSortedKey() {
        return this.name;
    }

    @Override
    public Collection<String> getExamples() {
        return this.type.getExamples();
    }

    public String toString() {
        return "<argument " + this.name + ":" + this.type + USAGE_ARGUMENT_CLOSE;
    }

    @Override
    public ArgumentBuilder createBuilder() {
        return this.createBuilder();
    }
}

