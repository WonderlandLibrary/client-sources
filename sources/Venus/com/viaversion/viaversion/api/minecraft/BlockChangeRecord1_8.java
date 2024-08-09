/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft;

import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;

public class BlockChangeRecord1_8
implements BlockChangeRecord {
    private final byte sectionX;
    private final short y;
    private final byte sectionZ;
    private int blockId;

    public BlockChangeRecord1_8(byte by, short s, byte by2, int n) {
        this.sectionX = by;
        this.y = s;
        this.sectionZ = by2;
        this.blockId = n;
    }

    public BlockChangeRecord1_8(int n, int n2, int n3, int n4) {
        this((byte)n, (short)n2, (byte)n3, n4);
    }

    @Override
    public byte getSectionX() {
        return this.sectionX;
    }

    @Override
    public byte getSectionY() {
        return (byte)(this.y & 0xF);
    }

    @Override
    public short getY(int n) {
        return this.y;
    }

    @Override
    public byte getSectionZ() {
        return this.sectionZ;
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

