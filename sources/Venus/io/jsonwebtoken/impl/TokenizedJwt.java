/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.Header;
import java.util.Map;

public interface TokenizedJwt {
    public CharSequence getProtected();

    public CharSequence getPayload();

    public CharSequence getDigest();

    public Header createHeader(Map<String, ?> var1);
}

