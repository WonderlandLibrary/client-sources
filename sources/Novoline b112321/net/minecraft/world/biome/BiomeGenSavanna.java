package net.minecraft.world.biome;

import java.util.Random;
import net.nA;
import net.minecraft.block.BlockDoublePlant$EnumPlantType;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenSavanna$Mutated;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenSavannaTree;

public class BiomeGenSavanna extends BiomeGenBase {
   private static final WorldGenSavannaTree field_150627_aC = new WorldGenSavannaTree(false);

   protected BiomeGenSavanna(int var1) {
      super(var1);
      this.spawnableCreatureList.add(new nA(EntityHorse.class, 1, 2, 6));
      this.theBiomeDecorator.treesPerChunk = 1;
      this.theBiomeDecorator.flowersPerChunk = 4;
      this.theBiomeDecorator.grassPerChunk = 20;
   }

   public WorldGenAbstractTree genBigTreeChance(Random var1) {
      return (WorldGenAbstractTree)(var1.nextInt(5) > 0?field_150627_aC:this.worldGeneratorTrees);
   }

   protected BiomeGenBase createMutatedBiome(int var1) {
      BiomeGenSavanna$Mutated var2 = new BiomeGenSavanna$Mutated(var1, this);
      var2.temperature = (this.temperature + 1.0F) * 0.5F;
      var2.minHeight = this.minHeight * 0.5F + 0.3F;
      var2.maxHeight = this.maxHeight * 0.5F + 1.2F;
      return var2;
   }

   public void decorate(World var1, Random var2, BlockPos var3) {
      DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant$EnumPlantType.GRASS);

      for(int var4 = 0; var4 < 7; ++var4) {
         int var5 = var2.nextInt(16) + 8;
         int var6 = var2.nextInt(16) + 8;
         int var7 = var2.nextInt(var1.getHeight(var3.a(var5, 0, var6)).getY() + 32);
         DOUBLE_PLANT_GENERATOR.generate(var1, var2, var3.a(var5, var7, var6));
      }

      super.decorate(var1, var2, var3);
   }
}
