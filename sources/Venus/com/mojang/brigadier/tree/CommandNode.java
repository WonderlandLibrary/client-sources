/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.brigadier.tree;

import com.mojang.brigadier.AmbiguityConsumer;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.RedirectModifier;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.CommandContextBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class CommandNode<S>
implements Comparable<CommandNode<S>> {
    private Map<String, CommandNode<S>> children = new LinkedHashMap<String, CommandNode<S>>();
    private Map<String, LiteralCommandNode<S>> literals = new LinkedHashMap<String, LiteralCommandNode<S>>();
    private Map<String, ArgumentCommandNode<S, ?>> arguments = new LinkedHashMap();
    private final Predicate<S> requirement;
    private final CommandNode<S> redirect;
    private final RedirectModifier<S> modifier;
    private final boolean forks;
    private Command<S> command;

    protected CommandNode(Command<S> command, Predicate<S> predicate, CommandNode<S> commandNode, RedirectModifier<S> redirectModifier, boolean bl) {
        this.command = command;
        this.requirement = predicate;
        this.redirect = commandNode;
        this.modifier = redirectModifier;
        this.forks = bl;
    }

    public Command<S> getCommand() {
        return this.command;
    }

    public Collection<CommandNode<S>> getChildren() {
        return this.children.values();
    }

    public CommandNode<S> getChild(String string) {
        return this.children.get(string);
    }

    public CommandNode<S> getRedirect() {
        return this.redirect;
    }

    public RedirectModifier<S> getRedirectModifier() {
        return this.modifier;
    }

    public boolean canUse(S s) {
        return this.requirement.test(s);
    }

    public void addChild(CommandNode<S> commandNode) {
        if (commandNode instanceof RootCommandNode) {
            throw new UnsupportedOperationException("Cannot add a RootCommandNode as a child to any other CommandNode");
        }
        CommandNode<S> commandNode2 = this.children.get(commandNode.getName());
        if (commandNode2 != null) {
            if (commandNode.getCommand() != null) {
                commandNode2.command = commandNode.getCommand();
            }
            for (CommandNode<S> commandNode3 : commandNode.getChildren()) {
                commandNode2.addChild(commandNode3);
            }
        } else {
            this.children.put(commandNode.getName(), commandNode);
            if (commandNode instanceof LiteralCommandNode) {
                this.literals.put(commandNode.getName(), (LiteralCommandNode)commandNode);
            } else if (commandNode instanceof ArgumentCommandNode) {
                this.arguments.put(commandNode.getName(), (ArgumentCommandNode)commandNode);
            }
        }
        this.children = this.children.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, CommandNode::lambda$addChild$0, LinkedHashMap::new));
    }

    public void findAmbiguities(AmbiguityConsumer<S> ambiguityConsumer) {
        HashSet<String> hashSet = new HashSet<String>();
        for (CommandNode<S> commandNode : this.children.values()) {
            for (CommandNode<S> commandNode2 : this.children.values()) {
                if (commandNode == commandNode2) continue;
                for (String string : commandNode.getExamples()) {
                    if (!commandNode2.isValidInput(string)) continue;
                    hashSet.add(string);
                }
                if (hashSet.size() <= 0) continue;
                ambiguityConsumer.ambiguous(this, commandNode, commandNode2, hashSet);
                hashSet = new HashSet();
            }
            commandNode.findAmbiguities(ambiguityConsumer);
        }
    }

    protected abstract boolean isValidInput(String var1);

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof CommandNode)) {
            return true;
        }
        CommandNode commandNode = (CommandNode)object;
        if (!this.children.equals(commandNode.children)) {
            return true;
        }
        return this.command != null ? !this.command.equals(commandNode.command) : commandNode.command != null;
    }

    public int hashCode() {
        return 31 * this.children.hashCode() + (this.command != null ? this.command.hashCode() : 0);
    }

    public Predicate<S> getRequirement() {
        return this.requirement;
    }

    public abstract String getName();

    public abstract String getUsageText();

    public abstract void parse(StringReader var1, CommandContextBuilder<S> var2) throws CommandSyntaxException;

    public abstract CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) throws CommandSyntaxException;

    public abstract ArgumentBuilder<S, ?> createBuilder();

    protected abstract String getSortedKey();

    public Collection<? extends CommandNode<S>> getRelevantNodes(StringReader stringReader) {
        if (this.literals.size() > 0) {
            int n = stringReader.getCursor();
            while (stringReader.canRead() && stringReader.peek() != ' ') {
                stringReader.skip();
            }
            String string = stringReader.getString().substring(n, stringReader.getCursor());
            stringReader.setCursor(n);
            LiteralCommandNode<S> literalCommandNode = this.literals.get(string);
            if (literalCommandNode != null) {
                return Collections.singleton(literalCommandNode);
            }
            return this.arguments.values();
        }
        return this.arguments.values();
    }

    @Override
    public int compareTo(CommandNode<S> commandNode) {
        if (this instanceof LiteralCommandNode == commandNode instanceof LiteralCommandNode) {
            return this.getSortedKey().compareTo(commandNode.getSortedKey());
        }
        return commandNode instanceof LiteralCommandNode ? 1 : -1;
    }

    public boolean isFork() {
        return this.forks;
    }

    public abstract Collection<String> getExamples();

    @Override
    public int compareTo(Object object) {
        return this.compareTo((CommandNode)object);
    }

    private static CommandNode lambda$addChild$0(CommandNode commandNode, CommandNode commandNode2) {
        return commandNode;
    }
}

