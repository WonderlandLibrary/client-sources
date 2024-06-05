package net.minecraft.world.chunk.storage;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.NibbleArray;

public class ChunkLoader {
   private static final String __OBFID = "CL_00000379";

   public static void convertToAnvilFormat(ChunkLoader.AnvilConverterData var0, NBTTagCompound var1, WorldChunkManager var2) {
      var1.setInteger("xPos", var0.x);
      var1.setInteger("zPos", var0.z);
      var1.setLong("LastUpdate", var0.lastUpdated);
      int[] var3 = new int[var0.heightmap.length];

      for(int var4 = 0; var4 < var0.heightmap.length; ++var4) {
         var3[var4] = var0.heightmap[var4];
      }

      var1.setIntArray("HeightMap", var3);
      var1.setBoolean("TerrainPopulated", var0.terrainPopulated);
      NBTTagList var17 = new NBTTagList();

      int var5;
      for(int var6 = 0; var6 < 8; ++var6) {
         boolean var7 = true;

         for(var5 = 0; var5 < 16 && var7; ++var5) {
            for(int var8 = 0; var8 < 16 && var7; ++var8) {
               for(int var9 = 0; var9 < 16; ++var9) {
                  int var10 = var5 << 11 | var9 << 7 | var8 + (var6 << 4);
                  byte var11 = var0.blocks[var10];
                  if (var11 != 0) {
                     var7 = false;
                     break;
                  }
               }
            }
         }

         if (!var7) {
            byte[] var20 = new byte[4096];
            NibbleArray var21 = new NibbleArray();
            NibbleArray var22 = new NibbleArray();
            NibbleArray var23 = new NibbleArray();

            for(int var12 = 0; var12 < 16; ++var12) {
               for(int var13 = 0; var13 < 16; ++var13) {
                  for(int var14 = 0; var14 < 16; ++var14) {
                     int var15 = var12 << 11 | var14 << 7 | var13 + (var6 << 4);
                     byte var16 = var0.blocks[var15];
                     var20[var13 << 8 | var14 << 4 | var12] = (byte)(var16 & 255);
                     var21.set(var12, var13, var14, var0.data.get(var12, var13 + (var6 << 4), var14));
                     var22.set(var12, var13, var14, var0.skyLight.get(var12, var13 + (var6 << 4), var14));
                     var23.set(var12, var13, var14, var0.blockLight.get(var12, var13 + (var6 << 4), var14));
                  }
               }
            }

            NBTTagCompound var24 = new NBTTagCompound();
            var24.setByte("Y", (byte)(var6 & 255));
            var24.setByteArray("Blocks", var20);
            var24.setByteArray("Data", var21.getData());
            var24.setByteArray("SkyLight", var22.getData());
            var24.setByteArray("BlockLight", var23.getData());
            var17.appendTag(var24);
         }
      }

      var1.setTag("Sections", var17);
      byte[] var18 = new byte[256];

      for(int var19 = 0; var19 < 16; ++var19) {
         for(var5 = 0; var5 < 16; ++var5) {
            var18[var5 << 4 | var19] = (byte)(var2.func_180300_a(new BlockPos(var0.x << 4 | var19, 0, var0.z << 4 | var5), BiomeGenBase.field_180279_ad).biomeID & 255);
         }
      }

      var1.setByteArray("Biomes", var18);
      var1.setTag("Entities", var0.entities);
      var1.setTag("TileEntities", var0.tileEntities);
      if (var0.tileTicks != null) {
         var1.setTag("TileTicks", var0.tileTicks);
      }

   }

   public static ChunkLoader.AnvilConverterData load(NBTTagCompound var0) {
      int var1 = var0.getInteger("xPos");
      int var2 = var0.getInteger("zPos");
      ChunkLoader.AnvilConverterData var3 = new ChunkLoader.AnvilConverterData(var1, var2);
      var3.blocks = var0.getByteArray("Blocks");
      var3.data = new NibbleArrayReader(var0.getByteArray("Data"), 7);
      var3.skyLight = new NibbleArrayReader(var0.getByteArray("SkyLight"), 7);
      var3.blockLight = new NibbleArrayReader(var0.getByteArray("BlockLight"), 7);
      var3.heightmap = var0.getByteArray("HeightMap");
      var3.terrainPopulated = var0.getBoolean("TerrainPopulated");
      var3.entities = var0.getTagList("Entities", 10);
      var3.tileEntities = var0.getTagList("TileEntities", 10);
      var3.tileTicks = var0.getTagList("TileTicks", 10);

      try {
         var3.lastUpdated = var0.getLong("LastUpdate");
      } catch (ClassCastException var5) {
         var3.lastUpdated = (long)var0.getInteger("LastUpdate");
      }

      return var3;
   }

   public static class AnvilConverterData {
      public NBTTagList tileTicks;
      public boolean terrainPopulated;
      public NibbleArrayReader data;
      public long lastUpdated;
      public NBTTagList tileEntities;
      public NibbleArrayReader skyLight;
      public NibbleArrayReader blockLight;
      private static final String __OBFID = "CL_00000380";
      public final int z;
      public byte[] blocks;
      public byte[] heightmap;
      public NBTTagList entities;
      public final int x;

      public AnvilConverterData(int var1, int var2) {
         this.x = var1;
         this.z = var2;
      }
   }
}
