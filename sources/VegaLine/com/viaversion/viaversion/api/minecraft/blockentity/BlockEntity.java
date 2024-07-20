/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft.blockentity;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface BlockEntity {
    public static byte pack(int sectionX, int sectionZ) {
        return (byte)((sectionX & 0xF) << 4 | sectionZ & 0xF);
    }

    default public byte sectionX() {
        return (byte)(this.packedXZ() >> 4 & 0xF);
    }

    default public byte sectionZ() {
        return (byte)(this.packedXZ() & 0xF);
    }

    public byte packedXZ();

    public short y();

    public int typeId();

    public @Nullable CompoundTag tag();

    public BlockEntity withTypeId(int var1);
}

