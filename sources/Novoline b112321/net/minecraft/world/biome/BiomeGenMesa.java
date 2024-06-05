package net.minecraft.world.biome;

import java.util.Arrays;
import java.util.Random;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDirt$DirtType;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSand$EnumType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeGenMesa extends BiomeGenBase {
   private IBlockState[] field_150621_aC;
   private long field_150622_aD;
   private NoiseGeneratorPerlin field_150623_aE;
   private NoiseGeneratorPerlin field_150624_aF;
   private NoiseGeneratorPerlin field_150625_aG;
   private boolean field_150626_aH;
   private boolean field_150620_aI;

   public BiomeGenMesa(int var1, boolean var2, boolean var3) {
      super(var1);
      this.field_150626_aH = var2;
      this.field_150620_aI = var3;
      this.setDisableRain();
      this.setTemperatureRainfall(2.0F, 0.0F);
      this.spawnableCreatureList.clear();
      this.topBlock = Blocks.sand.getDefaultState().withProperty(BlockSand.VARIANT, BlockSand$EnumType.RED_SAND);
      this.fillerBlock = Blocks.stained_hardened_clay.getDefaultState();
      this.theBiomeDecorator.treesPerChunk = -999;
      this.theBiomeDecorator.deadBushPerChunk = 20;
      this.theBiomeDecorator.reedsPerChunk = 3;
      this.theBiomeDecorator.cactiPerChunk = 5;
      this.theBiomeDecorator.flowersPerChunk = 0;
      this.spawnableCreatureList.clear();
      this.theBiomeDecorator.treesPerChunk = 5;
   }

   public WorldGenAbstractTree genBigTreeChance(Random var1) {
      return this.worldGeneratorTrees;
   }

   public int getFoliageColorAtPos(BlockPos var1) {
      return 10387789;
   }

   public int getGrassColorAtPos(BlockPos var1) {
      return 9470285;
   }

   public void decorate(World var1, Random var2, BlockPos var3) {
      super.decorate(var1, var2, var3);
   }

   public void genTerrainBlocks(World var1, Random var2, ChunkPrimer var3, int var4, int var5, double var6) {
      if(this.field_150621_aC == null || this.field_150622_aD != var1.getSeed()) {
         this.func_150619_a(var1.getSeed());
      }

      if(this.field_150623_aE == null || this.field_150624_aF == null || this.field_150622_aD != var1.getSeed()) {
         Random var8 = new Random(this.field_150622_aD);
         this.field_150623_aE = new NoiseGeneratorPerlin(var8, 4);
         this.field_150624_aF = new NoiseGeneratorPerlin(var8, 1);
      }

      this.field_150622_aD = var1.getSeed();
      double var22 = 0.0D;
      if(this.field_150626_aH) {
         int var10 = (var4 & -16) + (var5 & 15);
         int var11 = (var5 & -16) + (var4 & 15);
         double var12 = Math.min(Math.abs(var6), this.field_150623_aE.func_151601_a((double)var10 * 0.25D, (double)var11 * 0.25D));
         if(var12 > 0.0D) {
            double var14 = 0.001953125D;
            double var16 = Math.abs(this.field_150624_aF.func_151601_a((double)var10 * var14, (double)var11 * var14));
            var22 = var12 * var12 * 2.5D;
            double var18 = Math.ceil(var16 * 50.0D) + 14.0D;
            if(var22 > var18) {
               var22 = var18;
            }

            var22 = var22 + 64.0D;
         }
      }

      int var24 = var4 & 15;
      int var25 = var5 & 15;
      int var26 = var1.func_181545_F();
      IBlockState var13 = Blocks.stained_hardened_clay.getDefaultState();
      IBlockState var29 = this.fillerBlock;
      int var15 = (int)(var6 / 3.0D + 3.0D + var2.nextDouble() * 0.25D);
      boolean var31 = (double)MathHelper.cos(var6 / 3.0D * 3.141592653589793D) > 0.0D;
      int var17 = -1;
      boolean var32 = false;
      int var19 = 255;

      while(true) {
         if(var3.getBlockState(var25, var19, var24).getBlock().getMaterial() == Material.air && var19 < (int)var22) {
            var3.setBlockState(var25, var19, var24, Blocks.stone.getDefaultState());
         }

         if(var19 <= var2.nextInt(5)) {
            var3.setBlockState(var25, var19, var24, Blocks.bedrock.getDefaultState());
         } else {
            IBlockState var20 = var3.getBlockState(var25, var19, var24);
            if(var20.getBlock().getMaterial() == Material.air) {
               var17 = -1;
            } else if(var20.getBlock() == Blocks.stone) {
               if(var17 == -1) {
                  var32 = false;
                  var13 = null;
                  var29 = Blocks.stone.getDefaultState();
                  if(var19 < var26 && var13.getBlock().getMaterial() == Material.air) {
                     var13 = Blocks.water.getDefaultState();
                  }

                  var17 = var15 + Math.max(0, var19 - var26);
                  if(var19 < var26 - 1) {
                     var3.setBlockState(var25, var19, var24, var29);
                     if(var29.getBlock() == Blocks.stained_hardened_clay) {
                        var3.setBlockState(var25, var19, var24, var29.getBlock().getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.ORANGE));
                     }
                  } else if(this.field_150620_aI && var19 > 86 + var15 * 2) {
                     var3.setBlockState(var25, var19, var24, Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt$DirtType.COARSE_DIRT));
                  } else if(var19 <= var26 + 3 + var15) {
                     var3.setBlockState(var25, var19, var24, this.topBlock);
                     var32 = true;
                  } else {
                     IBlockState var21;
                     if(var19 >= 64 && var19 <= 127) {
                        var21 = Blocks.hardened_clay.getDefaultState();
                     } else {
                        var21 = Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.ORANGE);
                     }

                     var3.setBlockState(var25, var19, var24, var21);
                  }
               } else {
                  --var17;
                  var3.setBlockState(var25, var19, var24, Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.ORANGE));
               }
            }
         }

         --var19;
      }
   }

   private void func_150619_a(long var1) {
      this.field_150621_aC = new IBlockState[64];
      Arrays.fill(this.field_150621_aC, Blocks.hardened_clay.getDefaultState());
      Random var3 = new Random(var1);
      this.field_150625_aG = new NoiseGeneratorPerlin(var3, 1);

      for(int var12 = 0; var12 < 64; ++var12) {
         var12 += var3.nextInt(5) + 1;
         if(var12 < 64) {
            this.field_150621_aC[var12] = Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.ORANGE);
         }
      }

      int var13 = var3.nextInt(4) + 2;

      for(int var5 = 0; var5 < var13; ++var5) {
         int var6 = var3.nextInt(3) + 1;
         int var7 = var3.nextInt(64);

         for(int var8 = 0; var7 + var8 < 64 && var8 < var6; ++var8) {
            this.field_150621_aC[var7 + var8] = Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.YELLOW);
         }
      }

      int var14 = var3.nextInt(4) + 2;

      for(int var15 = 0; var15 < var14; ++var15) {
         int var17 = var3.nextInt(3) + 2;
         int var20 = var3.nextInt(64);

         for(int var9 = 0; var20 + var9 < 64 && var9 < var17; ++var9) {
            this.field_150621_aC[var20 + var9] = Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.BROWN);
         }
      }

      int var16 = var3.nextInt(4) + 2;

      for(int var18 = 0; var18 < var16; ++var18) {
         int var21 = var3.nextInt(3) + 1;
         int var23 = var3.nextInt(64);

         for(int var10 = 0; var23 + var10 < 64 && var10 < var21; ++var10) {
            this.field_150621_aC[var23 + var10] = Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.RED);
         }
      }

      int var19 = var3.nextInt(3) + 3;
      int var22 = 0;

      for(int var24 = 0; var24 < var19; ++var24) {
         byte var25 = 1;
         var22 += var3.nextInt(16) + 4;

         for(int var11 = 0; var22 + var11 < 64 && var11 < var25; ++var11) {
            this.field_150621_aC[var22 + var11] = Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.WHITE);
            if(var22 + var11 > 1 && var3.nextBoolean()) {
               this.field_150621_aC[var22 + var11 - 1] = Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.SILVER);
            }

            if(var22 + var11 < 63 && var3.nextBoolean()) {
               this.field_150621_aC[var22 + var11 + 1] = Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.SILVER);
            }
         }
      }

   }

   private IBlockState func_180629_a(int var1, int var2, int var3) {
      int var4 = (int)Math.round(this.field_150625_aG.func_151601_a((double)var1 * 1.0D / 512.0D, (double)var1 * 1.0D / 512.0D) * 2.0D);
      return this.field_150621_aC[(var2 + var4 + 64) % 64];
   }

   protected BiomeGenBase createMutatedBiome(int var1) {
      boolean var2 = this.biomeID == BiomeGenBase.mesa.biomeID;
      BiomeGenMesa var3 = new BiomeGenMesa(var1, var2, this.field_150620_aI);
      var3.setHeight(height_LowHills);
      var3.setBiomeName(this.biomeName + " M");
      var3.func_150557_a(this.color, true);
      return var3;
   }
}
