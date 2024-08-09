/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.security.ProviderKey;
import java.security.PrivateKey;
import java.security.Provider;

public final class ProviderPrivateKey
extends ProviderKey<PrivateKey>
implements PrivateKey {
    ProviderPrivateKey(Provider provider, PrivateKey privateKey) {
        super(provider, privateKey);
    }
}

