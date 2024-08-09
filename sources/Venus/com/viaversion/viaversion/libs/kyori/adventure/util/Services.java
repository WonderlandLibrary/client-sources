/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
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
    public static <P> Optional<P> service(@NotNull Class<P> clazz) {
        ServiceLoader<P> serviceLoader = Services0.loader(clazz);
        Iterator<P> iterator2 = serviceLoader.iterator();
        while (iterator2.hasNext()) {
            P p;
            try {
                p = iterator2.next();
            } catch (Throwable throwable) {
                if (!SERVICE_LOAD_FAILURES_ARE_FATAL) continue;
                throw new IllegalStateException("Encountered an exception loading service " + clazz, throwable);
            }
            if (iterator2.hasNext()) {
                throw new IllegalStateException("Expected to find one service " + clazz + ", found multiple");
            }
            return Optional.of(p);
        }
        return Optional.empty();
    }
}

