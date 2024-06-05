package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenForest;
import net.minecraft.world.biome.BiomeGenMutated;

class BiomeGenForest$1 extends BiomeGenMutated {
   final BiomeGenForest this$0;

   BiomeGenForest$1(BiomeGenForest var1, int var2, BiomeGenBase var3) {
      super(var2, var3);
      this.this$0 = var1;
   }

   public void decorate(World var1, Random var2, BlockPos var3) {
      this.baseBiome.decorate(var1, var2, var3);
   }
}
