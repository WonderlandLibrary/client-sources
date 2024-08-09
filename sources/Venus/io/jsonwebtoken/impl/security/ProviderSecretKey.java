/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.security.ProviderKey;
import java.security.Provider;
import javax.crypto.SecretKey;

public final class ProviderSecretKey
extends ProviderKey<SecretKey>
implements SecretKey {
    ProviderSecretKey(Provider provider, SecretKey secretKey) {
        super(provider, secretKey);
    }
}

