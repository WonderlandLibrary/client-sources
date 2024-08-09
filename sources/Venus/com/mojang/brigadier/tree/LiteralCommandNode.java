/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.brigadier.tree;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.RedirectModifier;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.CommandContextBuilder;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.CommandNode;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

public class LiteralCommandNode<S>
extends CommandNode<S> {
    private final String literal;

    public LiteralCommandNode(String string, Command<S> command, Predicate<S> predicate, CommandNode<S> commandNode, RedirectModifier<S> redirectModifier, boolean bl) {
        super(command, predicate, commandNode, redirectModifier, bl);
        this.literal = string;
    }

    public String getLiteral() {
        return this.literal;
    }

    @Override
    public String getName() {
        return this.literal;
    }

    @Override
    public void parse(StringReader stringReader, CommandContextBuilder<S> commandContextBuilder) throws CommandSyntaxException {
        int n = stringReader.getCursor();
        int n2 = this.parse(stringReader);
        if (n2 > -1) {
            commandContextBuilder.withNode(this, StringRange.between(n, n2));
            return;
        }
        throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.literalIncorrect().createWithContext(stringReader, this.literal);
    }

    private int parse(StringReader stringReader) {
        int n = stringReader.getCursor();
        if (stringReader.canRead(this.literal.length())) {
            int n2 = n + this.literal.length();
            if (stringReader.getString().substring(n, n2).equals(this.literal)) {
                stringReader.setCursor(n2);
                if (!stringReader.canRead() || stringReader.peek() == ' ') {
                    return n2;
                }
                stringReader.setCursor(n);
            }
        }
        return 1;
    }

    @Override
    public CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
        if (this.literal.toLowerCase().startsWith(suggestionsBuilder.getRemaining().toLowerCase())) {
            return suggestionsBuilder.suggest(this.literal).buildFuture();
        }
        return Suggestions.empty();
    }

    @Override
    public boolean isValidInput(String string) {
        return this.parse(new StringReader(string)) > -1;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof LiteralCommandNode)) {
            return true;
        }
        LiteralCommandNode literalCommandNode = (LiteralCommandNode)object;
        if (!this.literal.equals(literalCommandNode.literal)) {
            return true;
        }
        return super.equals(object);
    }

    @Override
    public String getUsageText() {
        return this.literal;
    }

    @Override
    public int hashCode() {
        int n = this.literal.hashCode();
        n = 31 * n + super.hashCode();
        return n;
    }

    public LiteralArgumentBuilder<S> createBuilder() {
        LiteralArgumentBuilder literalArgumentBuilder = LiteralArgumentBuilder.literal(this.literal);
        literalArgumentBuilder.requires(this.getRequirement());
        literalArgumentBuilder.forward(this.getRedirect(), this.getRedirectModifier(), this.isFork());
        if (this.getCommand() != null) {
            literalArgumentBuilder.executes(this.getCommand());
        }
        return literalArgumentBuilder;
    }

    @Override
    protected String getSortedKey() {
        return this.literal;
    }

    @Override
    public Collection<String> getExamples() {
        return Collections.singleton(this.literal);
    }

    public String toString() {
        return "<literal " + this.literal + ">";
    }

    @Override
    public ArgumentBuilder createBuilder() {
        return this.createBuilder();
    }
}

