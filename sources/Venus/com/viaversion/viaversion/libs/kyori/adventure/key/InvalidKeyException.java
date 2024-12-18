/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.key;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class InvalidKeyException
extends RuntimeException {
    private static final long serialVersionUID = -5413304087321449434L;
    private final String keyNamespace;
    private final String keyValue;

    InvalidKeyException(@NotNull String string, @NotNull String string2, @Nullable String string3) {
        super(string3);
        this.keyNamespace = string;
        this.keyValue = string2;
    }

    @NotNull
    public final String keyNamespace() {
        return this.keyNamespace;
    }

    @NotNull
    public final String keyValue() {
        return this.keyValue;
    }
}

