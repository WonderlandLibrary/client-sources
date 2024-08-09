/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.config;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.http.config.Registry;
import org.apache.http.util.Args;

public final class RegistryBuilder<I> {
    private final Map<String, I> items = new HashMap<String, I>();

    public static <I> RegistryBuilder<I> create() {
        return new RegistryBuilder<I>();
    }

    RegistryBuilder() {
    }

    public RegistryBuilder<I> register(String string, I i) {
        Args.notEmpty(string, "ID");
        Args.notNull(i, "Item");
        this.items.put(string.toLowerCase(Locale.ROOT), i);
        return this;
    }

    public Registry<I> build() {
        return new Registry<I>(this.items);
    }

    public String toString() {
        return this.items.toString();
    }
}

