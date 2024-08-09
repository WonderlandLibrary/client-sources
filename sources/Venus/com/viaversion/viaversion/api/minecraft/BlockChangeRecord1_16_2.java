/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;

public class BlockChangeRecord1_16_2
implements BlockChangeRecord {
    private final byte sectionX;
    private final byte sectionY;
    private final byte sectionZ;
    private int blockId;

    public BlockChangeRecord1_16_2(byte by, byte by2, byte by3, int n) {
        this.sectionX = by;
        this.sectionY = by2;
        this.sectionZ = by3;
        this.blockId = n;
    }

    public BlockChangeRecord1_16_2(int n, int n2, int n3, int n4) {
        this((byte)n, (byte)n2, (byte)n3, n4);
    }

    @Override
    public byte getSectionX() {
        return this.sectionX;
    }

    @Override
    public byte getSectionY() {
        return this.sectionY;
    }

    @Override
    public byte getSectionZ() {
        return this.sectionZ;
    }

    @Override
    public short getY(int n) {
        Preconditions.checkArgument(n >= 0, "Invalid chunkSectionY: " + n);
        return (short)((n << 4) + this.sectionY);
    }

    @Override
    public int getBlockId() {
        return this.blockId;
    }

    @Override
    public void setBlockId(int n) {
        this.blockId = n;
    }
}

