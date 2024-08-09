/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft.blockentity;

import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;

public final class BlockEntityImpl
implements BlockEntity {
    private final byte packedXZ;
    private final short y;
    private final int typeId;
    private final CompoundTag tag;

    public BlockEntityImpl(byte by, short s, int n, CompoundTag compoundTag) {
        this.packedXZ = by;
        this.y = s;
        this.typeId = n;
        this.tag = compoundTag;
    }

    @Override
    public byte packedXZ() {
        return this.packedXZ;
    }

    @Override
    public short y() {
        return this.y;
    }

    @Override
    public int typeId() {
        return this.typeId;
    }

    @Override
    public CompoundTag tag() {
        return this.tag;
    }

    @Override
    public BlockEntity withTypeId(int n) {
        return new BlockEntityImpl(this.packedXZ, this.y, n, this.tag);
    }
}

