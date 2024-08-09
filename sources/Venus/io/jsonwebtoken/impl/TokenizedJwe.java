/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.impl.TokenizedJwt;

public interface TokenizedJwe
extends TokenizedJwt {
    public CharSequence getEncryptedKey();

    public CharSequence getIv();
}

