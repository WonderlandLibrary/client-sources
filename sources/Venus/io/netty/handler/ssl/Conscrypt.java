/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.handler.ssl.ConscryptAlpnSslEngine;
import io.netty.util.internal.PlatformDependent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.net.ssl.SSLEngine;

final class Conscrypt {
    private static final Method IS_CONSCRYPT_SSLENGINE = Conscrypt.loadIsConscryptEngine();

    private static Method loadIsConscryptEngine() {
        try {
            Class<?> clazz = Class.forName("org.conscrypt.Conscrypt", true, ConscryptAlpnSslEngine.class.getClassLoader());
            return clazz.getMethod("isConscrypt", SSLEngine.class);
        } catch (Throwable throwable) {
            return null;
        }
    }

    static boolean isAvailable() {
        return IS_CONSCRYPT_SSLENGINE != null && PlatformDependent.javaVersion() >= 8;
    }

    static boolean isEngineSupported(SSLEngine sSLEngine) {
        return Conscrypt.isAvailable() && Conscrypt.isConscryptEngine(sSLEngine);
    }

    private static boolean isConscryptEngine(SSLEngine sSLEngine) {
        try {
            return (Boolean)IS_CONSCRYPT_SSLENGINE.invoke(null, sSLEngine);
        } catch (IllegalAccessException illegalAccessException) {
            return true;
        } catch (InvocationTargetException invocationTargetException) {
            throw new RuntimeException(invocationTargetException);
        }
    }

    private Conscrypt() {
    }
}

