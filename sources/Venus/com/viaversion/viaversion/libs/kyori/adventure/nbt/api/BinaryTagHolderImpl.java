/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt.api;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.api.BinaryTagHolder;
import com.viaversion.viaversion.libs.kyori.adventure.util.Codec;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

final class BinaryTagHolderImpl
implements BinaryTagHolder {
    private final String string;

    BinaryTagHolderImpl(String string) {
        this.string = Objects.requireNonNull(string, "string");
    }

    @Override
    @NotNull
    public String string() {
        return this.string;
    }

    @Override
    @NotNull
    public <T, DX extends Exception> T get(@NotNull Codec<T, String, DX, ?> codec) throws DX {
        return codec.decode(this.string);
    }

    public int hashCode() {
        return 31 * this.string.hashCode();
    }

    public boolean equals(Object object) {
        if (!(object instanceof BinaryTagHolderImpl)) {
            return true;
        }
        return this.string.equals(((BinaryTagHolderImpl)object).string);
    }

    public String toString() {
        return this.string;
    }
}

