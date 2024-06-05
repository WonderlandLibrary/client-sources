package net.minecraft.world.biome;

import java.util.Random;
import net.nA;
import net.minecraft.block.BlockDoublePlant$EnumPlantType;
import net.minecraft.block.BlockFlower$EnumFlowerType;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenPlains extends BiomeGenBase {
   protected boolean field_150628_aC;

   protected BiomeGenPlains(int var1) {
      super(var1);
      this.setTemperatureRainfall(0.8F, 0.4F);
      this.setHeight(height_LowPlains);
      this.spawnableCreatureList.add(new nA(EntityHorse.class, 5, 2, 6));
      this.theBiomeDecorator.treesPerChunk = -999;
      this.theBiomeDecorator.flowersPerChunk = 4;
      this.theBiomeDecorator.grassPerChunk = 10;
   }

   public BlockFlower$EnumFlowerType pickRandomFlower(Random var1, BlockPos var2) {
      double var3 = GRASS_COLOR_NOISE.func_151601_a((double)var2.getX() / 200.0D, (double)var2.getZ() / 200.0D);
      if(var3 < -0.8D) {
         int var6 = var1.nextInt(4);
         switch(var6) {
         case 0:
            return BlockFlower$EnumFlowerType.ORANGE_TULIP;
         case 1:
            return BlockFlower$EnumFlowerType.RED_TULIP;
         case 2:
            return BlockFlower$EnumFlowerType.PINK_TULIP;
         case 3:
         default:
            return BlockFlower$EnumFlowerType.WHITE_TULIP;
         }
      } else if(var1.nextInt(3) > 0) {
         int var5 = var1.nextInt(3);
         return BlockFlower$EnumFlowerType.POPPY;
      } else {
         return BlockFlower$EnumFlowerType.DANDELION;
      }
   }

   public void decorate(World var1, Random var2, BlockPos var3) {
      double var4 = GRASS_COLOR_NOISE.func_151601_a((double)(var3.getX() + 8) / 200.0D, (double)(var3.getZ() + 8) / 200.0D);
      if(var4 < -0.8D) {
         this.theBiomeDecorator.flowersPerChunk = 15;
         this.theBiomeDecorator.grassPerChunk = 5;
      } else {
         this.theBiomeDecorator.flowersPerChunk = 4;
         this.theBiomeDecorator.grassPerChunk = 10;
         DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant$EnumPlantType.GRASS);

         for(int var6 = 0; var6 < 7; ++var6) {
            int var7 = var2.nextInt(16) + 8;
            int var8 = var2.nextInt(16) + 8;
            int var9 = var2.nextInt(var1.getHeight(var3.a(var7, 0, var8)).getY() + 32);
            DOUBLE_PLANT_GENERATOR.generate(var1, var2, var3.a(var7, var9, var8));
         }
      }

      if(this.field_150628_aC) {
         DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant$EnumPlantType.SUNFLOWER);

         for(int var10 = 0; var10 < 10; ++var10) {
            int var11 = var2.nextInt(16) + 8;
            int var12 = var2.nextInt(16) + 8;
            int var13 = var2.nextInt(var1.getHeight(var3.a(var11, 0, var12)).getY() + 32);
            DOUBLE_PLANT_GENERATOR.generate(var1, var2, var3.a(var11, var13, var12));
         }
      }

      super.decorate(var1, var2, var3);
   }

   protected BiomeGenBase createMutatedBiome(int var1) {
      BiomeGenPlains var2 = new BiomeGenPlains(var1);
      var2.setBiomeName("Sunflower Plains");
      var2.field_150628_aC = true;
      var2.setColor(9286496);
      var2.field_150609_ah = 14273354;
      return var2;
   }
}
