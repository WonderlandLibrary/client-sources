package net.minecraft.world.biome;

import java.util.Random;
import net.nA;
import net.minecraft.block.BlockDoublePlant$EnumPlantType;
import net.minecraft.block.BlockFlower$EnumFlowerType;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase$Height;
import net.minecraft.world.biome.BiomeGenForest$1;
import net.minecraft.world.biome.BiomeGenForest$2;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenCanopyTree;
import net.minecraft.world.gen.feature.WorldGenForest;

public class BiomeGenForest extends BiomeGenBase {
   private int field_150632_aF;
   protected static final WorldGenForest field_150629_aC = new WorldGenForest(false, true);
   protected static final WorldGenForest field_150630_aD = new WorldGenForest(false, false);
   protected static final WorldGenCanopyTree field_150631_aE = new WorldGenCanopyTree(false);

   public BiomeGenForest(int var1, int var2) {
      super(var1);
      this.field_150632_aF = var2;
      this.theBiomeDecorator.treesPerChunk = 10;
      this.theBiomeDecorator.grassPerChunk = 2;
      if(this.field_150632_aF == 1) {
         this.theBiomeDecorator.treesPerChunk = 6;
         this.theBiomeDecorator.flowersPerChunk = 100;
         this.theBiomeDecorator.grassPerChunk = 1;
      }

      this.setFillerBlockMetadata(5159473);
      this.setTemperatureRainfall(0.7F, 0.8F);
      if(this.field_150632_aF == 2) {
         this.field_150609_ah = 353825;
         this.color = 3175492;
         this.setTemperatureRainfall(0.6F, 0.6F);
      }

      if(this.field_150632_aF == 0) {
         this.spawnableCreatureList.add(new nA(EntityWolf.class, 5, 4, 4));
      }

      if(this.field_150632_aF == 3) {
         this.theBiomeDecorator.treesPerChunk = -999;
      }

   }

   protected BiomeGenBase func_150557_a(int var1, boolean var2) {
      if(this.field_150632_aF == 2) {
         this.field_150609_ah = 353825;
         this.color = var1;
         this.field_150609_ah = (this.field_150609_ah & 16711422) >> 1;
         return this;
      } else {
         return super.func_150557_a(var1, var2);
      }
   }

   public WorldGenAbstractTree genBigTreeChance(Random var1) {
      return (WorldGenAbstractTree)(this.field_150632_aF == 3 && var1.nextInt(3) > 0?field_150631_aE:(this.field_150632_aF != 2 && var1.nextInt(5) != 0?this.worldGeneratorTrees:field_150630_aD));
   }

   public BlockFlower$EnumFlowerType pickRandomFlower(Random var1, BlockPos var2) {
      if(this.field_150632_aF == 1) {
         double var3 = MathHelper.clamp_double((1.0D + GRASS_COLOR_NOISE.func_151601_a((double)var2.getX() / 48.0D, (double)var2.getZ() / 48.0D)) / 2.0D, 0.0D, 0.9999D);
         BlockFlower$EnumFlowerType var5 = BlockFlower$EnumFlowerType.values()[(int)(var3 * (double)BlockFlower$EnumFlowerType.values().length)];
         return var5 == BlockFlower$EnumFlowerType.BLUE_ORCHID?BlockFlower$EnumFlowerType.POPPY:var5;
      } else {
         return super.pickRandomFlower(var1, var2);
      }
   }

   public void decorate(World var1, Random var2, BlockPos var3) {
      if(this.field_150632_aF == 3) {
         for(int var4 = 0; var4 < 4; ++var4) {
            for(int var5 = 0; var5 < 4; ++var5) {
               int var6 = var4 * 4 + 1 + 8 + var2.nextInt(3);
               int var7 = var5 * 4 + 1 + 8 + var2.nextInt(3);
               BlockPos var8 = var1.getHeight(var3.a(var6, 0, var7));
               if(var2.nextInt(20) == 0) {
                  WorldGenBigMushroom var9 = new WorldGenBigMushroom();
                  var9.generate(var1, var2, var8);
               } else {
                  WorldGenAbstractTree var16 = this.genBigTreeChance(var2);
                  var16.func_175904_e();
                  if(var16.generate(var1, var2, var8)) {
                     var16.func_180711_a(var1, var2, var8);
                  }
               }
            }
         }
      }

      int var11 = var2.nextInt(5) - 3;
      if(this.field_150632_aF == 1) {
         var11 += 2;
      }

      for(int var12 = 0; var12 < var11; ++var12) {
         int var13 = var2.nextInt(3);
         DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant$EnumPlantType.SYRINGA);

         for(int var14 = 0; var14 < 5; ++var14) {
            int var15 = var2.nextInt(16) + 8;
            int var17 = var2.nextInt(16) + 8;
            int var10 = var2.nextInt(var1.getHeight(var3.a(var15, 0, var17)).getY() + 32);
            if(DOUBLE_PLANT_GENERATOR.generate(var1, var2, new BlockPos(var3.getX() + var15, var10, var3.getZ() + var17))) {
               break;
            }
         }
      }

      super.decorate(var1, var2, var3);
   }

   public int getGrassColorAtPos(BlockPos var1) {
      int var2 = super.getGrassColorAtPos(var1);
      return this.field_150632_aF == 3?(var2 & 16711422) + 2634762 >> 1:var2;
   }

   protected BiomeGenBase createMutatedBiome(int var1) {
      if(this.biomeID == BiomeGenBase.forest.biomeID) {
         BiomeGenForest var2 = new BiomeGenForest(var1, 1);
         var2.setHeight(new BiomeGenBase$Height(this.minHeight, this.maxHeight + 0.2F));
         var2.setBiomeName("Flower Forest");
         var2.func_150557_a(6976549, true);
         var2.setFillerBlockMetadata(8233509);
         return var2;
      } else {
         return (BiomeGenBase)(this.biomeID != BiomeGenBase.birchForest.biomeID && this.biomeID != BiomeGenBase.birchForestHills.biomeID?new BiomeGenForest$1(this, var1, this):new BiomeGenForest$2(this, var1, this));
      }
   }
}
