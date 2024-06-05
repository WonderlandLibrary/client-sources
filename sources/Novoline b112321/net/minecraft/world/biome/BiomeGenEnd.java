package net.minecraft.world.biome;

import net.nA;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeEndDecorator;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenEnd extends BiomeGenBase {
   public BiomeGenEnd(int var1) {
      super(var1);
      this.spawnableMonsterList.clear();
      this.spawnableCreatureList.clear();
      this.spawnableWaterCreatureList.clear();
      this.spawnableCaveCreatureList.clear();
      this.spawnableMonsterList.add(new nA(EntityEnderman.class, 10, 4, 4));
      this.topBlock = Blocks.dirt.getDefaultState();
      this.fillerBlock = Blocks.dirt.getDefaultState();
      this.theBiomeDecorator = new BiomeEndDecorator();
   }

   public int getSkyColorByTemp(float var1) {
      return 0;
   }
}
