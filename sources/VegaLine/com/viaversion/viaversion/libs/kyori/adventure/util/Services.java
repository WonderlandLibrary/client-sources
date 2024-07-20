/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.viaversion.viaversion.libs.kyori.adventure.util;

import com.viaversion.viaversion.libs.kyori.adventure.internal.properties.AdventureProperties;
import com.viaversion.viaversion.libs.kyori.adventure.util.Services0;
import java.util.Iterator;
import java.util.Optional;
import java.util.ServiceLoader;
import org.jetbrains.annotations.NotNull;

public final class Services {
    private static final boolean SERVICE_LOAD_FAILURES_ARE_FATAL = Boolean.TRUE.equals(AdventureProperties.SERVICE_LOAD_FAILURES_ARE_FATAL.value());

    private Services() {
    }

    @NotNull
    public static <P> Optional<P> service(@NotNull Class<P> type2) {
        ServiceLoader<P> loader = Services0.loader(type2);
        Iterator<P> it = loader.iterator();
        while (it.hasNext()) {
            P instance;
            try {
                instance = it.next();
            } catch (Throwable t) {
                if (!SERVICE_LOAD_FAILURES_ARE_FATAL) continue;
                throw new IllegalStateException("Encountered an exception loading service " + type2, t);
            }
            if (it.hasNext()) {
                throw new IllegalStateException("Expected to find one service " + type2 + ", found multiple");
            }
            return Optional.of(instance);
        }
        return Optional.empty();
    }

    @NotNull
    public static <P> Optional<P> serviceWithFallback(@NotNull Class<P> type2) {
        ServiceLoader<P> loader = Services0.loader(type2);
        Iterator<P> it = loader.iterator();
        Object firstFallback = null;
        while (it.hasNext()) {
            P instance;
            try {
                instance = it.next();
            } catch (Throwable t) {
                if (!SERVICE_LOAD_FAILURES_ARE_FATAL) continue;
                throw new IllegalStateException("Encountered an exception loading service " + type2, t);
            }
            if (instance instanceof Fallback) {
                if (firstFallback != null) continue;
                firstFallback = instance;
                continue;
            }
            return Optional.of(instance);
        }
        return Optional.ofNullable(firstFallback);
    }

    public static interface Fallback {
    }
}

