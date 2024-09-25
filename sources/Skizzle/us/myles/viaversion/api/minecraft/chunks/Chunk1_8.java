/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.api.minecraft.chunks;

import java.util.ArrayList;
import java.util.List;
import us.myles.ViaVersion.api.minecraft.chunks.BaseChunk;
import us.myles.ViaVersion.api.minecraft.chunks.ChunkSection;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;

public class Chunk1_8
extends BaseChunk {
    private boolean unloadPacket;

    public Chunk1_8(int x, int z, boolean groundUp, int bitmask, ChunkSection[] sections, int[] biomeData, List<CompoundTag> blockEntities) {
        super(x, z, groundUp, false, bitmask, sections, biomeData, blockEntities);
    }

    public Chunk1_8(int x, int z) {
        this(x, z, true, 0, new ChunkSection[16], null, new ArrayList<CompoundTag>());
        this.unloadPacket = true;
    }

    public boolean hasBiomeData() {
        return this.biomeData != null && this.fullChunk;
    }

    public boolean isUnloadPacket() {
        return this.unloadPacket;
    }
}

