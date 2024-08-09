/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.impl.DefaultHeader;
import io.jsonwebtoken.impl.DefaultJwsHeader;
import io.jsonwebtoken.impl.TokenizedJwt;
import io.jsonwebtoken.lang.Strings;
import java.util.Map;

class DefaultTokenizedJwt
implements TokenizedJwt {
    private final CharSequence protectedHeader;
    private final CharSequence payload;
    private final CharSequence digest;

    DefaultTokenizedJwt(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        this.protectedHeader = charSequence;
        this.payload = charSequence2;
        this.digest = charSequence3;
    }

    @Override
    public CharSequence getProtected() {
        return this.protectedHeader;
    }

    @Override
    public CharSequence getPayload() {
        return this.payload;
    }

    @Override
    public CharSequence getDigest() {
        return this.digest;
    }

    @Override
    public Header createHeader(Map<String, ?> map) {
        if (Strings.hasText(this.getDigest())) {
            return new DefaultJwsHeader(map);
        }
        return new DefaultHeader(map);
    }
}

