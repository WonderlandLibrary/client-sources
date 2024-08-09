/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.brigadier.builder;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.RedirectModifier;
import com.mojang.brigadier.SingleRedirectModifier;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;

public abstract class ArgumentBuilder<S, T extends ArgumentBuilder<S, T>> {
    private final RootCommandNode<S> arguments = new RootCommandNode();
    private Command<S> command;
    private Predicate<S> requirement = ArgumentBuilder::lambda$new$0;
    private CommandNode<S> target;
    private RedirectModifier<S> modifier = null;
    private boolean forks;

    protected abstract T getThis();

    public T then(ArgumentBuilder<S, ?> argumentBuilder) {
        if (this.target != null) {
            throw new IllegalStateException("Cannot add children to a redirected node");
        }
        this.arguments.addChild(argumentBuilder.build());
        return this.getThis();
    }

    public T then(CommandNode<S> commandNode) {
        if (this.target != null) {
            throw new IllegalStateException("Cannot add children to a redirected node");
        }
        this.arguments.addChild(commandNode);
        return this.getThis();
    }

    public Collection<CommandNode<S>> getArguments() {
        return this.arguments.getChildren();
    }

    public T executes(Command<S> command) {
        this.command = command;
        return this.getThis();
    }

    public Command<S> getCommand() {
        return this.command;
    }

    public T requires(Predicate<S> predicate) {
        this.requirement = predicate;
        return this.getThis();
    }

    public Predicate<S> getRequirement() {
        return this.requirement;
    }

    public T redirect(CommandNode<S> commandNode) {
        return this.forward(commandNode, null, true);
    }

    public T redirect(CommandNode<S> commandNode, SingleRedirectModifier<S> singleRedirectModifier) {
        return this.forward(commandNode, singleRedirectModifier == null ? null : arg_0 -> ArgumentBuilder.lambda$redirect$1(singleRedirectModifier, arg_0), true);
    }

    public T fork(CommandNode<S> commandNode, RedirectModifier<S> redirectModifier) {
        return this.forward(commandNode, redirectModifier, false);
    }

    public T forward(CommandNode<S> commandNode, RedirectModifier<S> redirectModifier, boolean bl) {
        if (!this.arguments.getChildren().isEmpty()) {
            throw new IllegalStateException("Cannot forward a node with children");
        }
        this.target = commandNode;
        this.modifier = redirectModifier;
        this.forks = bl;
        return this.getThis();
    }

    public CommandNode<S> getRedirect() {
        return this.target;
    }

    public RedirectModifier<S> getRedirectModifier() {
        return this.modifier;
    }

    public boolean isFork() {
        return this.forks;
    }

    public abstract CommandNode<S> build();

    private static Collection lambda$redirect$1(SingleRedirectModifier singleRedirectModifier, CommandContext commandContext) throws CommandSyntaxException {
        return Collections.singleton(singleRedirectModifier.apply(commandContext));
    }

    private static boolean lambda$new$0(Object object) {
        return false;
    }
}

