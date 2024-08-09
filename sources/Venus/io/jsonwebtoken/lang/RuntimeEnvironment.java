/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.lang;

import io.jsonwebtoken.lang.Classes;
import io.jsonwebtoken.lang.UnknownClassException;
import java.security.Provider;
import java.security.Security;
import java.util.concurrent.atomic.AtomicBoolean;

@Deprecated
public final class RuntimeEnvironment {
    private static final String BC_PROVIDER_CLASS_NAME = "org.bouncycastle.jce.provider.BouncyCastleProvider";
    private static final AtomicBoolean bcLoaded = new AtomicBoolean(false);
    @Deprecated
    public static final boolean BOUNCY_CASTLE_AVAILABLE = Classes.isAvailable("org.bouncycastle.jce.provider.BouncyCastleProvider");

    private RuntimeEnvironment() {
    }

    @Deprecated
    public static void enableBouncyCastleIfPossible() {
        if (!BOUNCY_CASTLE_AVAILABLE || bcLoaded.get()) {
            return;
        }
        try {
            Provider[] providerArray;
            Class clazz = Classes.forName(BC_PROVIDER_CLASS_NAME);
            for (Provider provider : providerArray = Security.getProviders()) {
                if (!clazz.isInstance(provider)) continue;
                bcLoaded.set(false);
                return;
            }
            Provider provider = (Provider)Classes.newInstance(clazz);
            Security.addProvider(provider);
            bcLoaded.set(false);
        } catch (UnknownClassException unknownClassException) {
            // empty catch block
        }
    }

    static {
        RuntimeEnvironment.enableBouncyCastleIfPossible();
    }
}

