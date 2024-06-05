package net.minecraft.world.gen;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.StructureOceanMonument;

public class ChunkProviderGenerate implements IChunkProvider {
   private MapGenBase ravineGenerator;
   private StructureOceanMonument field_177474_A;
   private Block field_177476_s;
   private ChunkProviderSettings field_177477_r;
   private final boolean mapFeaturesEnabled;
   double[] field_147426_g;
   private double[] stoneNoise;
   private static final String __OBFID = "CL_00000396";
   private MapGenMineshaft mineshaftGenerator;
   private Random rand;
   private MapGenBase caveGenerator;
   public NoiseGeneratorOctaves noiseGen6;
   private final float[] parabolicField;
   private World worldObj;
   double[] field_147427_d;
   private WorldType field_177475_o;
   private NoiseGeneratorPerlin field_147430_m;
   private BiomeGenBase[] biomesForGeneration;
   private NoiseGeneratorOctaves field_147432_k;
   private final double[] field_147434_q;
   private NoiseGeneratorOctaves field_147431_j;
   private MapGenScatteredFeature scatteredFeatureGenerator;
   public NoiseGeneratorOctaves mobSpawnerNoise;
   public NoiseGeneratorOctaves noiseGen5;
   double[] field_147428_e;
   private NoiseGeneratorOctaves field_147429_l;
   double[] field_147425_f;
   private MapGenVillage villageGenerator;
   private MapGenStronghold strongholdGenerator;

   public boolean saveChunks(boolean var1, IProgressUpdate var2) {
      return true;
   }

   public boolean func_177460_a(IChunkProvider var1, Chunk var2, int var3, int var4) {
      boolean var5 = false;
      if (this.field_177477_r.field_177852_y && this.mapFeaturesEnabled && var2.getInhabitedTime() < 3600L) {
         var5 |= this.field_177474_A.func_175794_a(this.worldObj, this.rand, new ChunkCoordIntPair(var3, var4));
      }

      return var5;
   }

   public ChunkProviderGenerate(World var1, long var2, boolean var4, String var5) {
      this.field_177476_s = Blocks.water;
      this.stoneNoise = new double[256];
      this.caveGenerator = new MapGenCaves();
      this.strongholdGenerator = new MapGenStronghold();
      this.villageGenerator = new MapGenVillage();
      this.mineshaftGenerator = new MapGenMineshaft();
      this.scatteredFeatureGenerator = new MapGenScatteredFeature();
      this.ravineGenerator = new MapGenRavine();
      this.field_177474_A = new StructureOceanMonument();
      this.worldObj = var1;
      this.mapFeaturesEnabled = var4;
      this.field_177475_o = var1.getWorldInfo().getTerrainType();
      this.rand = new Random(var2);
      this.field_147431_j = new NoiseGeneratorOctaves(this.rand, 16);
      this.field_147432_k = new NoiseGeneratorOctaves(this.rand, 16);
      this.field_147429_l = new NoiseGeneratorOctaves(this.rand, 8);
      this.field_147430_m = new NoiseGeneratorPerlin(this.rand, 4);
      this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
      this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
      this.mobSpawnerNoise = new NoiseGeneratorOctaves(this.rand, 8);
      this.field_147434_q = new double[825];
      this.parabolicField = new float[25];

      for(int var6 = -2; var6 <= 2; ++var6) {
         for(int var7 = -2; var7 <= 2; ++var7) {
            float var8 = 10.0F / MathHelper.sqrt_float((float)(var6 * var6 + var7 * var7) + 0.2F);
            this.parabolicField[var6 + 2 + (var7 + 2) * 5] = var8;
         }
      }

      if (var5 != null) {
         this.field_177477_r = ChunkProviderSettings.Factory.func_177865_a(var5).func_177864_b();
         this.field_177476_s = this.field_177477_r.field_177778_E ? Blocks.lava : Blocks.water;
      }

   }

   public List func_177458_a(EnumCreatureType var1, BlockPos var2) {
      BiomeGenBase var3 = this.worldObj.getBiomeGenForCoords(var2);
      if (this.mapFeaturesEnabled) {
         if (var1 == EnumCreatureType.MONSTER && this.scatteredFeatureGenerator.func_175798_a(var2)) {
            return this.scatteredFeatureGenerator.getScatteredFeatureSpawnList();
         }

         if (var1 == EnumCreatureType.MONSTER && this.field_177477_r.field_177852_y && this.field_177474_A.func_175796_a(this.worldObj, var2)) {
            return this.field_177474_A.func_175799_b();
         }
      }

      return var3.getSpawnableList(var1);
   }

   public void func_180514_a(Chunk var1, int var2, int var3) {
      if (this.field_177477_r.field_177829_w && this.mapFeaturesEnabled) {
         this.mineshaftGenerator.func_175792_a(this, this.worldObj, var2, var3, (ChunkPrimer)null);
      }

      if (this.field_177477_r.field_177831_v && this.mapFeaturesEnabled) {
         this.villageGenerator.func_175792_a(this, this.worldObj, var2, var3, (ChunkPrimer)null);
      }

      if (this.field_177477_r.field_177833_u && this.mapFeaturesEnabled) {
         this.strongholdGenerator.func_175792_a(this, this.worldObj, var2, var3, (ChunkPrimer)null);
      }

      if (this.field_177477_r.field_177854_x && this.mapFeaturesEnabled) {
         this.scatteredFeatureGenerator.func_175792_a(this, this.worldObj, var2, var3, (ChunkPrimer)null);
      }

      if (this.field_177477_r.field_177852_y && this.mapFeaturesEnabled) {
         this.field_177474_A.func_175792_a(this, this.worldObj, var2, var3, (ChunkPrimer)null);
      }

   }

   public BlockPos func_180513_a(World var1, String var2, BlockPos var3) {
      return "Stronghold".equals(var2) && this.strongholdGenerator != null ? this.strongholdGenerator.func_180706_b(var1, var3) : null;
   }

   public String makeString() {
      return "RandomLevelSource";
   }

   public void func_180518_a(int var1, int var2, ChunkPrimer var3) {
      this.biomesForGeneration = this.worldObj.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, var1 * 4 - 2, var2 * 4 - 2, 10, 10);
      this.func_147423_a(var1 * 4, 0, var2 * 4);

      for(int var4 = 0; var4 < 4; ++var4) {
         int var5 = var4 * 5;
         int var6 = (var4 + 1) * 5;

         for(int var7 = 0; var7 < 4; ++var7) {
            int var8 = (var5 + var7) * 33;
            int var9 = (var5 + var7 + 1) * 33;
            int var10 = (var6 + var7) * 33;
            int var11 = (var6 + var7 + 1) * 33;

            for(int var12 = 0; var12 < 32; ++var12) {
               double var13 = 0.125D;
               double var15 = this.field_147434_q[var8 + var12];
               double var17 = this.field_147434_q[var9 + var12];
               double var19 = this.field_147434_q[var10 + var12];
               double var21 = this.field_147434_q[var11 + var12];
               double var23 = (this.field_147434_q[var8 + var12 + 1] - var15) * var13;
               double var25 = (this.field_147434_q[var9 + var12 + 1] - var17) * var13;
               double var27 = (this.field_147434_q[var10 + var12 + 1] - var19) * var13;
               double var29 = (this.field_147434_q[var11 + var12 + 1] - var21) * var13;

               for(int var31 = 0; var31 < 8; ++var31) {
                  double var32 = 0.25D;
                  double var34 = var15;
                  double var36 = var17;
                  double var38 = (var19 - var15) * var32;
                  double var40 = (var21 - var17) * var32;

                  for(int var42 = 0; var42 < 4; ++var42) {
                     double var43 = 0.25D;
                     double var45 = (var36 - var34) * var43;
                     double var47 = var34 - var45;

                     for(int var49 = 0; var49 < 4; ++var49) {
                        if ((var47 += var45) > 0.0D) {
                           var3.setBlockState(var4 * 4 + var42, var12 * 8 + var31, var7 * 4 + var49, Blocks.stone.getDefaultState());
                        } else if (var12 * 8 + var31 < this.field_177477_r.field_177841_q) {
                           var3.setBlockState(var4 * 4 + var42, var12 * 8 + var31, var7 * 4 + var49, this.field_177476_s.getDefaultState());
                        }
                     }

                     var34 += var38;
                     var36 += var40;
                  }

                  var15 += var23;
                  var17 += var25;
                  var19 += var27;
                  var21 += var29;
               }
            }
         }
      }

   }

   public int getLoadedChunkCount() {
      return 0;
   }

   public void func_180517_a(int var1, int var2, ChunkPrimer var3, BiomeGenBase[] var4) {
      double var5 = 0.03125D;
      this.stoneNoise = this.field_147430_m.func_151599_a(this.stoneNoise, (double)(var1 * 16), (double)(var2 * 16), 16, 16, var5 * 2.0D, var5 * 2.0D, 1.0D);

      for(int var7 = 0; var7 < 16; ++var7) {
         for(int var8 = 0; var8 < 16; ++var8) {
            BiomeGenBase var9 = var4[var8 + var7 * 16];
            var9.genTerrainBlocks(this.worldObj, this.rand, var3, var1 * 16 + var7, var2 * 16 + var8, this.stoneNoise[var8 + var7 * 16]);
         }
      }

   }

   public Chunk provideChunk(int var1, int var2) {
      this.rand.setSeed((long)var1 * 341873128712L + (long)var2 * 132897987541L);
      ChunkPrimer var3 = new ChunkPrimer();
      this.func_180518_a(var1, var2, var3);
      this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, var1 * 16, var2 * 16, 16, 16);
      this.func_180517_a(var1, var2, var3, this.biomesForGeneration);
      if (this.field_177477_r.field_177839_r) {
         this.caveGenerator.func_175792_a(this, this.worldObj, var1, var2, var3);
      }

      if (this.field_177477_r.field_177850_z) {
         this.ravineGenerator.func_175792_a(this, this.worldObj, var1, var2, var3);
      }

      if (this.field_177477_r.field_177829_w && this.mapFeaturesEnabled) {
         this.mineshaftGenerator.func_175792_a(this, this.worldObj, var1, var2, var3);
      }

      if (this.field_177477_r.field_177831_v && this.mapFeaturesEnabled) {
         this.villageGenerator.func_175792_a(this, this.worldObj, var1, var2, var3);
      }

      if (this.field_177477_r.field_177833_u && this.mapFeaturesEnabled) {
         this.strongholdGenerator.func_175792_a(this, this.worldObj, var1, var2, var3);
      }

      if (this.field_177477_r.field_177854_x && this.mapFeaturesEnabled) {
         this.scatteredFeatureGenerator.func_175792_a(this, this.worldObj, var1, var2, var3);
      }

      if (this.field_177477_r.field_177852_y && this.mapFeaturesEnabled) {
         this.field_177474_A.func_175792_a(this, this.worldObj, var1, var2, var3);
      }

      Chunk var4 = new Chunk(this.worldObj, var3, var1, var2);
      byte[] var5 = var4.getBiomeArray();

      for(int var6 = 0; var6 < var5.length; ++var6) {
         var5[var6] = (byte)this.biomesForGeneration[var6].biomeID;
      }

      var4.generateSkylightMap();
      return var4;
   }

   public Chunk func_177459_a(BlockPos var1) {
      return this.provideChunk(var1.getX() >> 4, var1.getZ() >> 4);
   }

   public boolean unloadQueuedChunks() {
      return false;
   }

   public boolean canSave() {
      return true;
   }

   public void populate(IChunkProvider var1, int var2, int var3) {
      BlockFalling.fallInstantly = true;
      int var4 = var2 * 16;
      int var5 = var3 * 16;
      BlockPos var6 = new BlockPos(var4, 0, var5);
      BiomeGenBase var7 = this.worldObj.getBiomeGenForCoords(var6.add(16, 0, 16));
      this.rand.setSeed(this.worldObj.getSeed());
      long var8 = this.rand.nextLong() / 2L * 2L + 1L;
      long var10 = this.rand.nextLong() / 2L * 2L + 1L;
      this.rand.setSeed((long)var2 * var8 + (long)var3 * var10 ^ this.worldObj.getSeed());
      boolean var12 = false;
      ChunkCoordIntPair var13 = new ChunkCoordIntPair(var2, var3);
      if (this.field_177477_r.field_177829_w && this.mapFeaturesEnabled) {
         this.mineshaftGenerator.func_175794_a(this.worldObj, this.rand, var13);
      }

      if (this.field_177477_r.field_177831_v && this.mapFeaturesEnabled) {
         var12 = this.villageGenerator.func_175794_a(this.worldObj, this.rand, var13);
      }

      if (this.field_177477_r.field_177833_u && this.mapFeaturesEnabled) {
         this.strongholdGenerator.func_175794_a(this.worldObj, this.rand, var13);
      }

      if (this.field_177477_r.field_177854_x && this.mapFeaturesEnabled) {
         this.scatteredFeatureGenerator.func_175794_a(this.worldObj, this.rand, var13);
      }

      if (this.field_177477_r.field_177852_y && this.mapFeaturesEnabled) {
         this.field_177474_A.func_175794_a(this.worldObj, this.rand, var13);
      }

      int var14;
      int var15;
      int var16;
      if (var7 != BiomeGenBase.desert && var7 != BiomeGenBase.desertHills && this.field_177477_r.field_177781_A && !var12 && this.rand.nextInt(this.field_177477_r.field_177782_B) == 0) {
         var14 = this.rand.nextInt(16) + 8;
         var15 = this.rand.nextInt(256);
         var16 = this.rand.nextInt(16) + 8;
         (new WorldGenLakes(Blocks.water)).generate(this.worldObj, this.rand, var6.add(var14, var15, var16));
      }

      if (!var12 && this.rand.nextInt(this.field_177477_r.field_177777_D / 10) == 0 && this.field_177477_r.field_177783_C) {
         var14 = this.rand.nextInt(16) + 8;
         var15 = this.rand.nextInt(this.rand.nextInt(248) + 8);
         var16 = this.rand.nextInt(16) + 8;
         if (var15 < 63 || this.rand.nextInt(this.field_177477_r.field_177777_D / 8) == 0) {
            (new WorldGenLakes(Blocks.lava)).generate(this.worldObj, this.rand, var6.add(var14, var15, var16));
         }
      }

      if (this.field_177477_r.field_177837_s) {
         for(var14 = 0; var14 < this.field_177477_r.field_177835_t; ++var14) {
            var15 = this.rand.nextInt(16) + 8;
            var16 = this.rand.nextInt(256);
            int var17 = this.rand.nextInt(16) + 8;
            (new WorldGenDungeons()).generate(this.worldObj, this.rand, var6.add(var15, var16, var17));
         }
      }

      var7.func_180624_a(this.worldObj, this.rand, new BlockPos(var4, 0, var5));
      SpawnerAnimals.performWorldGenSpawning(this.worldObj, var7, var4 + 8, var5 + 8, 16, 16, this.rand);
      var6 = var6.add(8, 0, 8);

      for(var14 = 0; var14 < 16; ++var14) {
         for(var15 = 0; var15 < 16; ++var15) {
            BlockPos var19 = this.worldObj.func_175725_q(var6.add(var14, 0, var15));
            BlockPos var18 = var19.offsetDown();
            if (this.worldObj.func_175675_v(var18)) {
               this.worldObj.setBlockState(var18, Blocks.ice.getDefaultState(), 2);
            }

            if (this.worldObj.func_175708_f(var19, true)) {
               this.worldObj.setBlockState(var19, Blocks.snow_layer.getDefaultState(), 2);
            }
         }
      }

      BlockFalling.fallInstantly = false;
   }

   public void saveExtraData() {
   }

   public boolean chunkExists(int var1, int var2) {
      return true;
   }

   private void func_147423_a(int var1, int var2, int var3) {
      this.field_147426_g = this.noiseGen6.generateNoiseOctaves(this.field_147426_g, var1, var3, 5, 5, (double)this.field_177477_r.field_177808_e, (double)this.field_177477_r.field_177803_f, (double)this.field_177477_r.field_177804_g);
      float var4 = this.field_177477_r.field_177811_a;
      float var5 = this.field_177477_r.field_177809_b;
      this.field_147427_d = this.field_147429_l.generateNoiseOctaves(this.field_147427_d, var1, var2, var3, 5, 33, 5, (double)(var4 / this.field_177477_r.field_177825_h), (double)(var5 / this.field_177477_r.field_177827_i), (double)(var4 / this.field_177477_r.field_177821_j));
      this.field_147428_e = this.field_147431_j.generateNoiseOctaves(this.field_147428_e, var1, var2, var3, 5, 33, 5, (double)var4, (double)var5, (double)var4);
      this.field_147425_f = this.field_147432_k.generateNoiseOctaves(this.field_147425_f, var1, var2, var3, 5, 33, 5, (double)var4, (double)var5, (double)var4);
      boolean var6 = false;
      boolean var7 = false;
      int var8 = 0;
      int var9 = 0;

      for(int var10 = 0; var10 < 5; ++var10) {
         for(int var11 = 0; var11 < 5; ++var11) {
            float var12 = 0.0F;
            float var13 = 0.0F;
            float var14 = 0.0F;
            byte var15 = 2;
            BiomeGenBase var16 = this.biomesForGeneration[var10 + 2 + (var11 + 2) * 10];

            for(int var17 = -var15; var17 <= var15; ++var17) {
               for(int var18 = -var15; var18 <= var15; ++var18) {
                  BiomeGenBase var19 = this.biomesForGeneration[var10 + var17 + 2 + (var11 + var18 + 2) * 10];
                  float var20 = this.field_177477_r.field_177813_n + var19.minHeight * this.field_177477_r.field_177819_m;
                  float var21 = this.field_177477_r.field_177843_p + var19.maxHeight * this.field_177477_r.field_177815_o;
                  if (this.field_177475_o == WorldType.AMPLIFIED && var20 > 0.0F) {
                     var20 = 1.0F + var20 * 2.0F;
                     var21 = 1.0F + var21 * 4.0F;
                  }

                  float var22 = this.parabolicField[var17 + 2 + (var18 + 2) * 5] / (var20 + 2.0F);
                  if (var19.minHeight > var16.minHeight) {
                     var22 /= 2.0F;
                  }

                  var12 += var21 * var22;
                  var13 += var20 * var22;
                  var14 += var22;
               }
            }

            var12 /= var14;
            var13 /= var14;
            var12 = var12 * 0.9F + 0.1F;
            var13 = (var13 * 4.0F - 1.0F) / 8.0F;
            double var38 = this.field_147426_g[var9] / 8000.0D;
            if (var38 < 0.0D) {
               var38 = -var38 * 0.3D;
            }

            var38 = var38 * 3.0D - 2.0D;
            if (var38 < 0.0D) {
               var38 /= 2.0D;
               if (var38 < -1.0D) {
                  var38 = -1.0D;
               }

               var38 /= 1.4D;
               var38 /= 2.0D;
            } else {
               if (var38 > 1.0D) {
                  var38 = 1.0D;
               }

               var38 /= 8.0D;
            }

            ++var9;
            double var39 = (double)var13;
            double var40 = (double)var12;
            var39 += var38 * 0.2D;
            var39 = var39 * (double)this.field_177477_r.field_177823_k / 8.0D;
            double var23 = (double)this.field_177477_r.field_177823_k + var39 * 4.0D;

            for(int var25 = 0; var25 < 33; ++var25) {
               double var26 = ((double)var25 - var23) * (double)this.field_177477_r.field_177817_l * 128.0D / 256.0D / var40;
               if (var26 < 0.0D) {
                  var26 *= 4.0D;
               }

               double var28 = this.field_147428_e[var8] / (double)this.field_177477_r.field_177806_d;
               double var30 = this.field_147425_f[var8] / (double)this.field_177477_r.field_177810_c;
               double var32 = (this.field_147427_d[var8] / 10.0D + 1.0D) / 2.0D;
               double var34 = MathHelper.denormalizeClamp(var28, var30, var32) - var26;
               if (var25 > 29) {
                  double var36 = (double)((float)(var25 - 29) / 3.0F);
                  var34 = var34 * (1.0D - var36) + -10.0D * var36;
               }

               this.field_147434_q[var8] = var34;
               ++var8;
            }
         }
      }

   }
}
