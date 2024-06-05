package net.minecraft.world.biome;

import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeColorHelper$ColorResolver;
import net.minecraft.world.biome.BiomeGenBase;

final class BiomeColorHelper$2 implements BiomeColorHelper$ColorResolver {
   public int getColorAtPos(BiomeGenBase var1, BlockPos var2) {
      return var1.getFoliageColorAtPos(var2);
   }
}
