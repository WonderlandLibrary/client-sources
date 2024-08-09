/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.platform.providers;

import com.viaversion.viaversion.api.platform.providers.Provider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ViaProviders {
    private final Map<Class<? extends Provider>, Provider> providers = new HashMap<Class<? extends Provider>, Provider>();
    private final List<Class<? extends Provider>> lonelyProviders = new ArrayList<Class<? extends Provider>>();

    public void require(Class<? extends Provider> clazz) {
        this.lonelyProviders.add(clazz);
    }

    public <T extends Provider> void register(Class<T> clazz, T t) {
        this.providers.put(clazz, t);
    }

    public <T extends Provider> void use(Class<T> clazz, T t) {
        this.lonelyProviders.remove(clazz);
        this.providers.put(clazz, t);
    }

    public <T extends Provider> @Nullable T get(Class<T> clazz) {
        Provider provider = this.providers.get(clazz);
        if (provider != null) {
            return (T)provider;
        }
        if (this.lonelyProviders.contains(clazz)) {
            throw new IllegalStateException("There was no provider for " + clazz + ", one is required!");
        }
        return null;
    }
}

