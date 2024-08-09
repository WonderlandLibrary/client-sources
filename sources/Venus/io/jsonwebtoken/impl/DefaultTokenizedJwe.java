/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.impl.DefaultJweHeader;
import io.jsonwebtoken.impl.DefaultTokenizedJwt;
import io.jsonwebtoken.impl.TokenizedJwe;
import java.util.Map;

class DefaultTokenizedJwe
extends DefaultTokenizedJwt
implements TokenizedJwe {
    private final CharSequence encryptedKey;
    private final CharSequence iv;

    DefaultTokenizedJwe(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, CharSequence charSequence5) {
        super(charSequence, charSequence2, charSequence3);
        this.encryptedKey = charSequence4;
        this.iv = charSequence5;
    }

    @Override
    public CharSequence getEncryptedKey() {
        return this.encryptedKey;
    }

    @Override
    public CharSequence getIv() {
        return this.iv;
    }

    @Override
    public Header createHeader(Map<String, ?> map) {
        return new DefaultJweHeader(map);
    }
}

