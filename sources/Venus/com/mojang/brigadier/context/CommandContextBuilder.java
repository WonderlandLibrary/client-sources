/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.brigadier.context;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.RedirectModifier;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.ParsedArgument;
import com.mojang.brigadier.context.ParsedCommandNode;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.context.SuggestionContext;
import com.mojang.brigadier.tree.CommandNode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CommandContextBuilder<S> {
    private final Map<String, ParsedArgument<S, ?>> arguments = new LinkedHashMap();
    private final CommandNode<S> rootNode;
    private final List<ParsedCommandNode<S>> nodes = new ArrayList<ParsedCommandNode<S>>();
    private final CommandDispatcher<S> dispatcher;
    private S source;
    private Command<S> command;
    private CommandContextBuilder<S> child;
    private StringRange range;
    private RedirectModifier<S> modifier = null;
    private boolean forks;

    public CommandContextBuilder(CommandDispatcher<S> commandDispatcher, S s, CommandNode<S> commandNode, int n) {
        this.rootNode = commandNode;
        this.dispatcher = commandDispatcher;
        this.source = s;
        this.range = StringRange.at(n);
    }

    public CommandContextBuilder<S> withSource(S s) {
        this.source = s;
        return this;
    }

    public S getSource() {
        return this.source;
    }

    public CommandNode<S> getRootNode() {
        return this.rootNode;
    }

    public CommandContextBuilder<S> withArgument(String string, ParsedArgument<S, ?> parsedArgument) {
        this.arguments.put(string, parsedArgument);
        return this;
    }

    public Map<String, ParsedArgument<S, ?>> getArguments() {
        return this.arguments;
    }

    public CommandContextBuilder<S> withCommand(Command<S> command) {
        this.command = command;
        return this;
    }

    public CommandContextBuilder<S> withNode(CommandNode<S> commandNode, StringRange stringRange) {
        this.nodes.add(new ParsedCommandNode<S>(commandNode, stringRange));
        this.range = StringRange.encompassing(this.range, stringRange);
        this.modifier = commandNode.getRedirectModifier();
        this.forks = commandNode.isFork();
        return this;
    }

    public CommandContextBuilder<S> copy() {
        CommandContextBuilder<S> commandContextBuilder = new CommandContextBuilder<S>(this.dispatcher, this.source, this.rootNode, this.range.getStart());
        commandContextBuilder.command = this.command;
        commandContextBuilder.arguments.putAll(this.arguments);
        commandContextBuilder.nodes.addAll(this.nodes);
        commandContextBuilder.child = this.child;
        commandContextBuilder.range = this.range;
        commandContextBuilder.forks = this.forks;
        return commandContextBuilder;
    }

    public CommandContextBuilder<S> withChild(CommandContextBuilder<S> commandContextBuilder) {
        this.child = commandContextBuilder;
        return this;
    }

    public CommandContextBuilder<S> getChild() {
        return this.child;
    }

    public CommandContextBuilder<S> getLastChild() {
        CommandContextBuilder<S> commandContextBuilder = this;
        while (commandContextBuilder.getChild() != null) {
            commandContextBuilder = commandContextBuilder.getChild();
        }
        return commandContextBuilder;
    }

    public Command<S> getCommand() {
        return this.command;
    }

    public List<ParsedCommandNode<S>> getNodes() {
        return this.nodes;
    }

    public CommandContext<S> build(String string) {
        return new CommandContext<S>(this.source, string, this.arguments, this.command, this.rootNode, this.nodes, this.range, this.child == null ? null : this.child.build(string), this.modifier, this.forks);
    }

    public CommandDispatcher<S> getDispatcher() {
        return this.dispatcher;
    }

    public StringRange getRange() {
        return this.range;
    }

    public SuggestionContext<S> findSuggestionContext(int n) {
        if (this.range.getStart() <= n) {
            if (this.range.getEnd() < n) {
                if (this.child != null) {
                    return this.child.findSuggestionContext(n);
                }
                if (!this.nodes.isEmpty()) {
                    ParsedCommandNode<S> parsedCommandNode = this.nodes.get(this.nodes.size() - 1);
                    return new SuggestionContext<S>(parsedCommandNode.getNode(), parsedCommandNode.getRange().getEnd() + 1);
                }
                return new SuggestionContext<S>(this.rootNode, this.range.getStart());
            }
            CommandNode<S> commandNode = this.rootNode;
            for (ParsedCommandNode<S> parsedCommandNode : this.nodes) {
                StringRange stringRange = parsedCommandNode.getRange();
                if (stringRange.getStart() <= n && n <= stringRange.getEnd()) {
                    return new SuggestionContext<S>(commandNode, stringRange.getStart());
                }
                commandNode = parsedCommandNode.getNode();
            }
            if (commandNode == null) {
                throw new IllegalStateException("Can't find node before cursor");
            }
            return new SuggestionContext<S>(commandNode, this.range.getStart());
        }
        throw new IllegalStateException("Can't find node before cursor");
    }
}

