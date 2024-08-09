/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.KeySupplier;
import java.security.Key;
import java.security.Provider;

public class ProviderKey<T extends Key>
implements Key,
KeySupplier<T> {
    private final T key;
    private final Provider provider;

    public static Provider getProvider(Key key, Provider provider) {
        if (key instanceof ProviderKey) {
            ProviderKey providerKey = (ProviderKey)key;
            return Assert.stateNotNull(providerKey.getProvider(), "ProviderKey provider can never be null.");
        }
        return provider;
    }

    public static <K extends Key> K getKey(K k) {
        return (K)(k instanceof ProviderKey ? ((ProviderKey)k).getKey() : k);
    }

    ProviderKey(Provider provider, T t) {
        this.provider = Assert.notNull(provider, "Provider cannot be null.");
        this.key = (Key)Assert.notNull(t, "Key argument cannot be null.");
        if (t instanceof ProviderKey) {
            String string = "Nesting not permitted.";
            throw new IllegalArgumentException(string);
        }
    }

    @Override
    public T getKey() {
        return this.key;
    }

    @Override
    public String getAlgorithm() {
        return this.key.getAlgorithm();
    }

    @Override
    public String getFormat() {
        return this.key.getFormat();
    }

    @Override
    public byte[] getEncoded() {
        return this.key.getEncoded();
    }

    public final Provider getProvider() {
        return this.provider;
    }
}

