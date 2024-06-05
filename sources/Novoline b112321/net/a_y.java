package net;

import net.BQ;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.storage.ChunkLoader$AnvilConverterData;

public class a_y {
   public static ChunkLoader$AnvilConverterData a(NBTTagCompound var0) {
      return BQ.a(var0);
   }

   public static void a(ChunkLoader$AnvilConverterData var0, NBTTagCompound var1, WorldChunkManager var2) {
      BQ.a(var0, var1, var2);
   }
}
