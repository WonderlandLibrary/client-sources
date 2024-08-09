/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

public class ChunkSectionDataOF {
    private short blockRefCount;
    private short tickRefCount;
    private short fluidRefCount;

    public ChunkSectionDataOF(short s, short s2, short s3) {
        this.blockRefCount = s;
        this.tickRefCount = s2;
        this.fluidRefCount = s3;
    }

    public short getBlockRefCount() {
        return this.blockRefCount;
    }

    public short getTickRefCount() {
        return this.tickRefCount;
    }

    public short getFluidRefCount() {
        return this.fluidRefCount;
    }
}

