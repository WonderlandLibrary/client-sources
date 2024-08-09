/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeContainer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.optifine.ChunkDataOF;
import net.optifine.ChunkSectionDataOF;

public class ChunkOF
extends Chunk {
    private ChunkDataOF chunkDataOF;
    private boolean hasEntitiesOF;
    private boolean loadedOF;

    public ChunkOF(World world, ChunkPos chunkPos, BiomeContainer biomeContainer) {
        super(world, chunkPos, biomeContainer);
    }

    public ChunkDataOF getChunkDataOF() {
        return this.chunkDataOF;
    }

    public void setChunkDataOF(ChunkDataOF chunkDataOF) {
        this.chunkDataOF = chunkDataOF;
    }

    public static ChunkDataOF makeChunkDataOF(Chunk chunk) {
        ChunkSectionDataOF[] chunkSectionDataOFArray = null;
        ChunkSection chunkSection = chunk.getLastExtendedBlockStorage();
        if (chunkSection != null) {
            int n = (chunkSection.getYLocation() >> 4) + 1;
            chunkSectionDataOFArray = new ChunkSectionDataOF[n];
            ChunkSection[] chunkSectionArray = chunk.getSections();
            for (int i = 0; i < n; ++i) {
                ChunkSection chunkSection2 = chunkSectionArray[i];
                if (chunkSection2 == null) continue;
                short s = chunkSection2.getBlockRefCount();
                short s2 = chunkSection2.getTickRefCount();
                short s3 = chunkSection2.getFluidRefCount();
                chunkSectionDataOFArray[i] = new ChunkSectionDataOF(s, s2, s3);
            }
        }
        return new ChunkDataOF(chunkSectionDataOFArray);
    }

    @Override
    public void addEntity(Entity entity2) {
        this.hasEntitiesOF = true;
        super.addEntity(entity2);
    }

    @Override
    public void setHasEntities(boolean bl) {
        this.hasEntitiesOF = bl;
        super.setHasEntities(bl);
    }

    public boolean hasEntities() {
        return this.hasEntitiesOF;
    }

    @Override
    public void setLoaded(boolean bl) {
        this.loadedOF = bl;
        super.setLoaded(bl);
    }

    public boolean isLoaded() {
        return this.loadedOF;
    }
}

