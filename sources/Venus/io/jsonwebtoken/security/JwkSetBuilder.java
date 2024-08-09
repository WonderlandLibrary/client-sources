/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.lang.MapMutator;
import io.jsonwebtoken.security.Jwk;
import io.jsonwebtoken.security.JwkSet;
import io.jsonwebtoken.security.KeyOperationPolicied;
import io.jsonwebtoken.security.SecurityBuilder;
import java.util.Collection;

public interface JwkSetBuilder
extends MapMutator<String, Object, JwkSetBuilder>,
SecurityBuilder<JwkSet, JwkSetBuilder>,
KeyOperationPolicied<JwkSetBuilder> {
    public JwkSetBuilder add(Jwk<?> var1);

    public JwkSetBuilder add(Collection<Jwk<?>> var1);

    public JwkSetBuilder keys(Collection<Jwk<?>> var1);
}

