/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.data.entity;

import com.viaversion.viaversion.api.data.entity.DimensionData;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;

public final class DimensionDataImpl
implements DimensionData {
    private final int minY;
    private final int height;

    public DimensionDataImpl(int n, int n2) {
        this.minY = n;
        this.height = n2;
    }

    public DimensionDataImpl(CompoundTag compoundTag) {
        Object t = compoundTag.get("height");
        if (!(t instanceof IntTag)) {
            throw new IllegalArgumentException("height missing in dimension data: " + compoundTag);
        }
        this.height = ((NumberTag)t).asInt();
        Object t2 = compoundTag.get("min_y");
        if (!(t2 instanceof IntTag)) {
            throw new IllegalArgumentException("min_y missing in dimension data: " + compoundTag);
        }
        this.minY = ((NumberTag)t2).asInt();
    }

    @Override
    public int minY() {
        return this.minY;
    }

    @Override
    public int height() {
        return this.height;
    }

    public String toString() {
        return "DimensionData{minY=" + this.minY + ", height=" + this.height + '}';
    }
}

