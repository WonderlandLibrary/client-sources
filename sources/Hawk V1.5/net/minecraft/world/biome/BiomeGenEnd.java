package net.minecraft.world.biome;

import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;

public class BiomeGenEnd extends BiomeGenBase {
   private static final String __OBFID = "CL_00000187";

   public BiomeGenEnd(int var1) {
      super(var1);
      this.spawnableMonsterList.clear();
      this.spawnableCreatureList.clear();
      this.spawnableWaterCreatureList.clear();
      this.spawnableCaveCreatureList.clear();
      this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityEnderman.class, 10, 4, 4));
      this.topBlock = Blocks.dirt.getDefaultState();
      this.fillerBlock = Blocks.dirt.getDefaultState();
      this.theBiomeDecorator = new BiomeEndDecorator();
   }

   public int getSkyColorByTemp(float var1) {
      return 0;
   }
}
