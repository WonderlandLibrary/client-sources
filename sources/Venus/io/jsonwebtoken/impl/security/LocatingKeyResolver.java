/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Locator;
import io.jsonwebtoken.SigningKeyResolver;
import io.jsonwebtoken.lang.Assert;
import java.security.Key;

public class LocatingKeyResolver
implements SigningKeyResolver {
    private final Locator<? extends Key> locator;

    public LocatingKeyResolver(Locator<? extends Key> locator) {
        this.locator = Assert.notNull(locator, "Locator cannot be null.");
    }

    @Override
    public Key resolveSigningKey(JwsHeader jwsHeader, Claims claims) {
        return this.locator.locate(jwsHeader);
    }

    @Override
    public Key resolveSigningKey(JwsHeader jwsHeader, byte[] byArray) {
        return this.locator.locate(jwsHeader);
    }
}

