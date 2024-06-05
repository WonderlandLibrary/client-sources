package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenForest;
import net.minecraft.world.biome.BiomeGenMutated;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

class BiomeGenForest$2 extends BiomeGenMutated {
   final BiomeGenForest this$0;

   BiomeGenForest$2(BiomeGenForest var1, int var2, BiomeGenBase var3) {
      super(var2, var3);
      this.this$0 = var1;
   }

   public WorldGenAbstractTree genBigTreeChance(Random var1) {
      return var1.nextBoolean()?BiomeGenForest.field_150629_aC:BiomeGenForest.field_150630_aD;
   }
}
