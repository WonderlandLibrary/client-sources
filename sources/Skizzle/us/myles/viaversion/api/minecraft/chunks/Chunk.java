/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.api.minecraft.chunks;

import java.util.List;
import us.myles.ViaVersion.api.minecraft.chunks.ChunkSection;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;

public interface Chunk {
    public int getX();

    public int getZ();

    public boolean isBiomeData();

    public boolean isFullChunk();

    @Deprecated
    default public boolean isGroundUp() {
        return this.isFullChunk();
    }

    public boolean isIgnoreOldLightData();

    public void setIgnoreOldLightData(boolean var1);

    public int getBitmask();

    public ChunkSection[] getSections();

    public int[] getBiomeData();

    public void setBiomeData(int[] var1);

    public CompoundTag getHeightMap();

    public void setHeightMap(CompoundTag var1);

    public List<CompoundTag> getBlockEntities();
}

