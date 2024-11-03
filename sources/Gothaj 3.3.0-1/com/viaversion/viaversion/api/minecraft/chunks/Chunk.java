package com.viaversion.viaversion.api.minecraft.chunks;

import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import java.util.BitSet;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface Chunk {
   int getX();

   int getZ();

   boolean isBiomeData();

   boolean isFullChunk();

   boolean isIgnoreOldLightData();

   void setIgnoreOldLightData(boolean var1);

   int getBitmask();

   void setBitmask(int var1);

   @Nullable
   BitSet getChunkMask();

   void setChunkMask(BitSet var1);

   ChunkSection[] getSections();

   void setSections(ChunkSection[] var1);

   @Nullable
   int[] getBiomeData();

   void setBiomeData(@Nullable int[] var1);

   @Nullable
   CompoundTag getHeightMap();

   void setHeightMap(@Nullable CompoundTag var1);

   List<CompoundTag> getBlockEntities();

   List<BlockEntity> blockEntities();
}
