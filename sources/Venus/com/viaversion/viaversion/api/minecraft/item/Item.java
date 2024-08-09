/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft.item;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface Item {
    public int identifier();

    public void setIdentifier(int var1);

    public int amount();

    public void setAmount(int var1);

    default public short data() {
        return 1;
    }

    default public void setData(short s) {
        throw new UnsupportedOperationException();
    }

    public @Nullable CompoundTag tag();

    public void setTag(@Nullable CompoundTag var1);

    public Item copy();
}

