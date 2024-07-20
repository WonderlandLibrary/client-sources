/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.tokens;

import java.util.Objects;
import org.yaml.snakeyaml.comments.CommentType;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.tokens.Token;

public final class CommentToken
extends Token {
    private final CommentType type;
    private final String value;

    public CommentToken(CommentType type2, String value, Mark startMark, Mark endMark) {
        super(startMark, endMark);
        Objects.requireNonNull(type2);
        this.type = type2;
        Objects.requireNonNull(value);
        this.value = value;
    }

    public CommentType getCommentType() {
        return this.type;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public Token.ID getTokenId() {
        return Token.ID.Comment;
    }
}

