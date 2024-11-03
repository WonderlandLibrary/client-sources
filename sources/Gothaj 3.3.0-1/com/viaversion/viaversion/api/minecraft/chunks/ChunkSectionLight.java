package com.viaversion.viaversion.api.minecraft.chunks;

import io.netty.buffer.ByteBuf;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface ChunkSectionLight {
   int LIGHT_LENGTH = 2048;

   boolean hasSkyLight();

   boolean hasBlockLight();

   @Nullable
   byte[] getSkyLight();

   @Nullable
   byte[] getBlockLight();

   void setSkyLight(byte[] var1);

   void setBlockLight(byte[] var1);

   @Nullable
   NibbleArray getSkyLightNibbleArray();

   @Nullable
   NibbleArray getBlockLightNibbleArray();

   void readSkyLight(ByteBuf var1);

   void readBlockLight(ByteBuf var1);

   void writeSkyLight(ByteBuf var1);

   void writeBlockLight(ByteBuf var1);
}
