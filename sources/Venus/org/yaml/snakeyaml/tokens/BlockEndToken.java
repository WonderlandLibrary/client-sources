/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.tokens;

import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.tokens.Token;

public final class BlockEndToken
extends Token {
    public BlockEndToken(Mark mark, Mark mark2) {
        super(mark, mark2);
    }

    @Override
    public Token.ID getTokenId() {
        return Token.ID.BlockEnd;
    }
}

