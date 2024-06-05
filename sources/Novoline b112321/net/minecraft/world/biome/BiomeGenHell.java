package net.minecraft.world.biome;

import net.nA;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenHell extends BiomeGenBase {
   public BiomeGenHell(int var1) {
      super(var1);
      this.spawnableMonsterList.clear();
      this.spawnableCreatureList.clear();
      this.spawnableWaterCreatureList.clear();
      this.spawnableCaveCreatureList.clear();
      this.spawnableMonsterList.add(new nA(EntityGhast.class, 50, 4, 4));
      this.spawnableMonsterList.add(new nA(EntityPigZombie.class, 100, 4, 4));
      this.spawnableMonsterList.add(new nA(EntityMagmaCube.class, 1, 4, 4));
   }
}
