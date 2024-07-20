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

    public DimensionDataImpl(int minY, int height) {
        this.minY = minY;
        this.height = height;
    }

    public DimensionDataImpl(CompoundTag dimensionData) {
        Object height = dimensionData.get("height");
        if (!(height instanceof IntTag)) {
            throw new IllegalArgumentException("height missing in dimension data: " + dimensionData);
        }
        this.height = ((NumberTag)height).asInt();
        Object minY = dimensionData.get("min_y");
        if (!(minY instanceof IntTag)) {
            throw new IllegalArgumentException("min_y missing in dimension data: " + dimensionData);
        }
        this.minY = ((NumberTag)minY).asInt();
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

