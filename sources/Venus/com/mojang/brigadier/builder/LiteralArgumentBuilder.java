/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.brigadier.builder;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;

public class LiteralArgumentBuilder<S>
extends ArgumentBuilder<S, LiteralArgumentBuilder<S>> {
    private final String literal;

    protected LiteralArgumentBuilder(String string) {
        this.literal = string;
    }

    public static <S> LiteralArgumentBuilder<S> literal(String string) {
        return new LiteralArgumentBuilder<S>(string);
    }

    @Override
    protected LiteralArgumentBuilder<S> getThis() {
        return this;
    }

    public String getLiteral() {
        return this.literal;
    }

    @Override
    public LiteralCommandNode<S> build() {
        LiteralCommandNode literalCommandNode = new LiteralCommandNode(this.getLiteral(), this.getCommand(), this.getRequirement(), this.getRedirect(), this.getRedirectModifier(), this.isFork());
        for (CommandNode commandNode : this.getArguments()) {
            literalCommandNode.addChild(commandNode);
        }
        return literalCommandNode;
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

