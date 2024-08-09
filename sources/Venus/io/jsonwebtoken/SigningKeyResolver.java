/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import java.security.Key;

@Deprecated
public interface SigningKeyResolver {
    public Key resolveSigningKey(JwsHeader var1, Claims var2);

    public Key resolveSigningKey(JwsHeader var1, byte[] var2);
}

