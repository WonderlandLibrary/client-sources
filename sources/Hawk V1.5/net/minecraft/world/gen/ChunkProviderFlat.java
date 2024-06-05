package net.minecraft.world.gen;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.StructureOceanMonument;

public class ChunkProviderFlat implements IChunkProvider {
   private final List structureGenerators = Lists.newArrayList();
   private WorldGenLakes waterLakeGenerator;
   private final FlatGeneratorInfo flatWorldGenInfo;
   private WorldGenLakes lavaLakeGenerator;
   private World worldObj;
   private Random random;
   private static final String __OBFID = "CL_00000391";
   private final boolean hasDungeons;
   private final boolean hasDecoration;
   private final IBlockState[] cachedBlockIDs = new IBlockState[256];

   public String makeString() {
      return "FlatLevelSource";
   }

   public void populate(IChunkProvider var1, int var2, int var3) {
      int var4 = var2 * 16;
      int var5 = var3 * 16;
      BlockPos var6 = new BlockPos(var4, 0, var5);
      BiomeGenBase var7 = this.worldObj.getBiomeGenForCoords(new BlockPos(var4 + 16, 0, var5 + 16));
      boolean var8 = false;
      this.random.setSeed(this.worldObj.getSeed());
      long var9 = this.random.nextLong() / 2L * 2L + 1L;
      long var11 = this.random.nextLong() / 2L * 2L + 1L;
      this.random.setSeed((long)var2 * var9 + (long)var3 * var11 ^ this.worldObj.getSeed());
      ChunkCoordIntPair var13 = new ChunkCoordIntPair(var2, var3);
      Iterator var14 = this.structureGenerators.iterator();

      while(var14.hasNext()) {
         MapGenStructure var15 = (MapGenStructure)var14.next();
         boolean var16 = var15.func_175794_a(this.worldObj, this.random, var13);
         if (var15 instanceof MapGenVillage) {
            var8 |= var16;
         }
      }

      if (this.waterLakeGenerator != null && !var8 && this.random.nextInt(4) == 0) {
         this.waterLakeGenerator.generate(this.worldObj, this.random, var6.add(this.random.nextInt(16) + 8, this.random.nextInt(256), this.random.nextInt(16) + 8));
      }

      if (this.lavaLakeGenerator != null && !var8 && this.random.nextInt(8) == 0) {
         BlockPos var17 = var6.add(this.random.nextInt(16) + 8, this.random.nextInt(this.random.nextInt(248) + 8), this.random.nextInt(16) + 8);
         if (var17.getY() < 63 || this.random.nextInt(10) == 0) {
            this.lavaLakeGenerator.generate(this.worldObj, this.random, var17);
         }
      }

      if (this.hasDungeons) {
         for(int var18 = 0; var18 < 8; ++var18) {
            (new WorldGenDungeons()).generate(this.worldObj, this.random, var6.add(this.random.nextInt(16) + 8, this.random.nextInt(256), this.random.nextInt(16) + 8));
         }
      }

      if (this.hasDecoration) {
         var7.func_180624_a(this.worldObj, this.random, new BlockPos(var4, 0, var5));
      }

   }

   public Chunk provideChunk(int var1, int var2) {
      ChunkPrimer var3 = new ChunkPrimer();

      int var4;
      for(int var5 = 0; var5 < this.cachedBlockIDs.length; ++var5) {
         IBlockState var6 = this.cachedBlockIDs[var5];
         if (var6 != null) {
            for(int var7 = 0; var7 < 16; ++var7) {
               for(var4 = 0; var4 < 16; ++var4) {
                  var3.setBlockState(var7, var5, var4, var6);
               }
            }
         }
      }

      Iterator var9 = this.structureGenerators.iterator();

      while(var9.hasNext()) {
         MapGenBase var10 = (MapGenBase)var9.next();
         var10.func_175792_a(this, this.worldObj, var1, var2, var3);
      }

      Chunk var11 = new Chunk(this.worldObj, var3, var1, var2);
      BiomeGenBase[] var12 = this.worldObj.getWorldChunkManager().loadBlockGeneratorData((BiomeGenBase[])null, var1 * 16, var2 * 16, 16, 16);
      byte[] var8 = var11.getBiomeArray();

      for(var4 = 0; var4 < var8.length; ++var4) {
         var8[var4] = (byte)var12[var4].biomeID;
      }

      var11.generateSkylightMap();
      return var11;
   }

   public BlockPos func_180513_a(World var1, String var2, BlockPos var3) {
      if ("Stronghold".equals(var2)) {
         Iterator var4 = this.structureGenerators.iterator();

         while(var4.hasNext()) {
            MapGenStructure var5 = (MapGenStructure)var4.next();
            if (var5 instanceof MapGenStronghold) {
               return var5.func_180706_b(var1, var3);
            }
         }
      }

      return null;
   }

   public boolean chunkExists(int var1, int var2) {
      return true;
   }

   public Chunk func_177459_a(BlockPos var1) {
      return this.provideChunk(var1.getX() >> 4, var1.getZ() >> 4);
   }

   public int getLoadedChunkCount() {
      return 0;
   }

   public void saveExtraData() {
   }

   public List func_177458_a(EnumCreatureType var1, BlockPos var2) {
      BiomeGenBase var3 = this.worldObj.getBiomeGenForCoords(var2);
      return var3.getSpawnableList(var1);
   }

   public boolean saveChunks(boolean var1, IProgressUpdate var2) {
      return true;
   }

   public boolean unloadQueuedChunks() {
      return false;
   }

   public ChunkProviderFlat(World var1, long var2, boolean var4, String var5) {
      this.worldObj = var1;
      this.random = new Random(var2);
      this.flatWorldGenInfo = FlatGeneratorInfo.createFlatGeneratorFromString(var5);
      if (var4) {
         Map var6 = this.flatWorldGenInfo.getWorldFeatures();
         if (var6.containsKey("village")) {
            Map var7 = (Map)var6.get("village");
            if (!var7.containsKey("size")) {
               var7.put("size", "1");
            }

            this.structureGenerators.add(new MapGenVillage(var7));
         }

         if (var6.containsKey("biome_1")) {
            this.structureGenerators.add(new MapGenScatteredFeature((Map)var6.get("biome_1")));
         }

         if (var6.containsKey("mineshaft")) {
            this.structureGenerators.add(new MapGenMineshaft((Map)var6.get("mineshaft")));
         }

         if (var6.containsKey("stronghold")) {
            this.structureGenerators.add(new MapGenStronghold((Map)var6.get("stronghold")));
         }

         if (var6.containsKey("oceanmonument")) {
            this.structureGenerators.add(new StructureOceanMonument((Map)var6.get("oceanmonument")));
         }
      }

      if (this.flatWorldGenInfo.getWorldFeatures().containsKey("lake")) {
         this.waterLakeGenerator = new WorldGenLakes(Blocks.water);
      }

      if (this.flatWorldGenInfo.getWorldFeatures().containsKey("lava_lake")) {
         this.lavaLakeGenerator = new WorldGenLakes(Blocks.lava);
      }

      this.hasDungeons = this.flatWorldGenInfo.getWorldFeatures().containsKey("dungeon");
      boolean var11 = true;
      Iterator var12 = this.flatWorldGenInfo.getFlatLayers().iterator();

      while(var12.hasNext()) {
         FlatLayerInfo var8 = (FlatLayerInfo)var12.next();

         for(int var9 = var8.getMinY(); var9 < var8.getMinY() + var8.getLayerCount(); ++var9) {
            IBlockState var10 = var8.func_175900_c();
            if (var10.getBlock() != Blocks.air) {
               var11 = false;
               this.cachedBlockIDs[var9] = var10;
            }
         }
      }

      this.hasDecoration = var11 ? false : this.flatWorldGenInfo.getWorldFeatures().containsKey("decoration");
   }

   public boolean func_177460_a(IChunkProvider var1, Chunk var2, int var3, int var4) {
      return false;
   }

   public boolean canSave() {
      return true;
   }

   public void func_180514_a(Chunk var1, int var2, int var3) {
      Iterator var4 = this.structureGenerators.iterator();

      while(var4.hasNext()) {
         MapGenStructure var5 = (MapGenStructure)var4.next();
         var5.func_175792_a(this, this.worldObj, var2, var3, (ChunkPrimer)null);
      }

   }
}
