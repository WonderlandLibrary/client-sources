/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.brigadier.context;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.RedirectModifier;
import com.mojang.brigadier.context.ParsedArgument;
import com.mojang.brigadier.context.ParsedCommandNode;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.tree.CommandNode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandContext<S> {
    private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER = new HashMap();
    private final S source;
    private final String input;
    private final Command<S> command;
    private final Map<String, ParsedArgument<S, ?>> arguments;
    private final CommandNode<S> rootNode;
    private final List<ParsedCommandNode<S>> nodes;
    private final StringRange range;
    private final CommandContext<S> child;
    private final RedirectModifier<S> modifier;
    private final boolean forks;

    public CommandContext(S s, String string, Map<String, ParsedArgument<S, ?>> map, Command<S> command, CommandNode<S> commandNode, List<ParsedCommandNode<S>> list, StringRange stringRange, CommandContext<S> commandContext, RedirectModifier<S> redirectModifier, boolean bl) {
        this.source = s;
        this.input = string;
        this.arguments = map;
        this.command = command;
        this.rootNode = commandNode;
        this.nodes = list;
        this.range = stringRange;
        this.child = commandContext;
        this.modifier = redirectModifier;
        this.forks = bl;
    }

    public CommandContext<S> copyFor(S s) {
        if (this.source == s) {
            return this;
        }
        return new CommandContext<S>(s, this.input, this.arguments, this.command, this.rootNode, this.nodes, this.range, this.child, this.modifier, this.forks);
    }

    public CommandContext<S> getChild() {
        return this.child;
    }

    public CommandContext<S> getLastChild() {
        CommandContext<S> commandContext = this;
        while (commandContext.getChild() != null) {
            commandContext = commandContext.getChild();
        }
        return commandContext;
    }

    public Command<S> getCommand() {
        return this.command;
    }

    public S getSource() {
        return this.source;
    }

    public <V> V getArgument(String string, Class<V> clazz) {
        ParsedArgument<S, ?> parsedArgument = this.arguments.get(string);
        if (parsedArgument == null) {
            throw new IllegalArgumentException("No such argument '" + string + "' exists on this command");
        }
        Object obj = parsedArgument.getResult();
        if (PRIMITIVE_TO_WRAPPER.getOrDefault(clazz, clazz).isAssignableFrom(obj.getClass())) {
            return (V)obj;
        }
        throw new IllegalArgumentException("Argument '" + string + "' is defined as " + obj.getClass().getSimpleName() + ", not " + clazz);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof CommandContext)) {
            return true;
        }
        CommandContext commandContext = (CommandContext)object;
        if (!this.arguments.equals(commandContext.arguments)) {
            return true;
        }
        if (!this.rootNode.equals(commandContext.rootNode)) {
            return true;
        }
        if (this.nodes.size() != commandContext.nodes.size() || !this.nodes.equals(commandContext.nodes)) {
            return true;
        }
        if (this.command != null ? !this.command.equals(commandContext.command) : commandContext.command != null) {
            return true;
        }
        if (!this.source.equals(commandContext.source)) {
            return true;
        }
        return this.child != null ? !this.child.equals(commandContext.child) : commandContext.child != null;
    }

    public int hashCode() {
        int n = this.source.hashCode();
        n = 31 * n + this.arguments.hashCode();
        n = 31 * n + (this.command != null ? this.command.hashCode() : 0);
        n = 31 * n + this.rootNode.hashCode();
        n = 31 * n + this.nodes.hashCode();
        n = 31 * n + (this.child != null ? this.child.hashCode() : 0);
        return n;
    }

    public RedirectModifier<S> getRedirectModifier() {
        return this.modifier;
    }

    public StringRange getRange() {
        return this.range;
    }

    public String getInput() {
        return this.input;
    }

    public CommandNode<S> getRootNode() {
        return this.rootNode;
    }

    public List<ParsedCommandNode<S>> getNodes() {
        return this.nodes;
    }

    public boolean hasNodes() {
        return !this.nodes.isEmpty();
    }

    public boolean isForked() {
        return this.forks;
    }

    static {
        PRIMITIVE_TO_WRAPPER.put(Boolean.TYPE, Boolean.class);
        PRIMITIVE_TO_WRAPPER.put(Byte.TYPE, Byte.class);
        PRIMITIVE_TO_WRAPPER.put(Short.TYPE, Short.class);
        PRIMITIVE_TO_WRAPPER.put(Character.TYPE, Character.class);
        PRIMITIVE_TO_WRAPPER.put(Integer.TYPE, Integer.class);
        PRIMITIVE_TO_WRAPPER.put(Long.TYPE, Long.class);
        PRIMITIVE_TO_WRAPPER.put(Float.TYPE, Float.class);
        PRIMITIVE_TO_WRAPPER.put(Double.TYPE, Double.class);
    }
}

