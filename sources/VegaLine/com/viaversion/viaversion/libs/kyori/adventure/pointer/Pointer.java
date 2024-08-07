/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.viaversion.viaversion.libs.kyori.adventure.pointer;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.pointer.PointerImpl;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

public interface Pointer<V>
extends Examinable {
    @NotNull
    public static <V> Pointer<V> pointer(@NotNull Class<V> type2, @NotNull Key key) {
        return new PointerImpl<V>(type2, key);
    }

    @NotNull
    public Class<V> type();

    @NotNull
    public Key key();

    @Override
    @NotNull
    default public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("type", this.type()), ExaminableProperty.of("key", this.key()));
    }
}

