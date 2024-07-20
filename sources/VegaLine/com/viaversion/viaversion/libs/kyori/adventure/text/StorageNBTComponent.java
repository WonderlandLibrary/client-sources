/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Contract
 *  org.jetbrains.annotations.NotNull
 */
package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponentBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.ScopedComponent;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.stream.Stream;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface StorageNBTComponent
extends NBTComponent<StorageNBTComponent, Builder>,
ScopedComponent<StorageNBTComponent> {
    @NotNull
    public Key storage();

    @Contract(pure=true)
    @NotNull
    public StorageNBTComponent storage(@NotNull Key var1);

    @Override
    @NotNull
    default public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.concat(Stream.of(ExaminableProperty.of("storage", this.storage())), NBTComponent.super.examinableProperties());
    }

    public static interface Builder
    extends NBTComponentBuilder<StorageNBTComponent, Builder> {
        @Contract(value="_ -> this")
        @NotNull
        public Builder storage(@NotNull Key var1);
    }
}

