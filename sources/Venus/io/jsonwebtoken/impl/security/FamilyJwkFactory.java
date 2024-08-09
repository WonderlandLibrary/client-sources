/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.Identifiable;
import io.jsonwebtoken.impl.security.JwkContext;
import io.jsonwebtoken.impl.security.JwkFactory;
import io.jsonwebtoken.security.Jwk;
import java.security.Key;

public interface FamilyJwkFactory<K extends Key, J extends Jwk<K>>
extends JwkFactory<K, J>,
Identifiable {
    public boolean supports(Key var1);

    public boolean supports(JwkContext<?> var1);
}

