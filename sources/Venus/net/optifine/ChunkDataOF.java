/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import net.optifine.ChunkSectionDataOF;

public class ChunkDataOF {
    private ChunkSectionDataOF[] chunkSectionDatas;

    public ChunkDataOF(ChunkSectionDataOF[] chunkSectionDataOFArray) {
        this.chunkSectionDatas = chunkSectionDataOFArray;
    }

    public ChunkSectionDataOF[] getChunkSectionDatas() {
        return this.chunkSectionDatas;
    }

    public void setChunkSectionDatas(ChunkSectionDataOF[] chunkSectionDataOFArray) {
        this.chunkSectionDatas = chunkSectionDataOFArray;
    }
}

