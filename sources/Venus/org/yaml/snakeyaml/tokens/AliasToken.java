/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.tokens;

import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.tokens.Token;

public final class AliasToken
extends Token {
    private final String value;

    public AliasToken(String string, Mark mark, Mark mark2) {
        super(mark, mark2);
        if (string == null) {
            throw new NullPointerException("alias is expected");
        }
        this.value = string;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public Token.ID getTokenId() {
        return Token.ID.Alias;
    }
}

