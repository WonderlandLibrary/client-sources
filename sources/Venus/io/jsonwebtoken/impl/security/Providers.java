/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.lang.Classes;
import java.security.Provider;
import java.security.Security;
import java.util.concurrent.atomic.AtomicReference;

final class Providers {
    private static final String BC_PROVIDER_CLASS_NAME = "org.bouncycastle.jce.provider.BouncyCastleProvider";
    static final boolean BOUNCY_CASTLE_AVAILABLE = Classes.isAvailable("org.bouncycastle.jce.provider.BouncyCastleProvider");
    private static final AtomicReference<Provider> BC_PROVIDER = new AtomicReference();

    private Providers() {
    }

    public static Provider findBouncyCastle() {
        if (!BOUNCY_CASTLE_AVAILABLE) {
            return null;
        }
        Provider provider = BC_PROVIDER.get();
        if (provider == null) {
            Provider[] providerArray;
            Class clazz = Classes.forName(BC_PROVIDER_CLASS_NAME);
            for (Provider provider2 : providerArray = Security.getProviders()) {
                if (!clazz.isInstance(provider2)) continue;
                BC_PROVIDER.set(provider2);
                return provider2;
            }
            provider = (Provider)Classes.newInstance(clazz);
            BC_PROVIDER.set(provider);
        }
        return provider;
    }
}

