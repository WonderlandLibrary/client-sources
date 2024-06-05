package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.List;
import net.nA;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.world.gen.structure.MapGenNetherBridge$Start;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenNetherBridge extends MapGenStructure {
   private List spawnList = Lists.newArrayList();

   public MapGenNetherBridge() {
      this.spawnList.add(new nA(EntityBlaze.class, 10, 2, 3));
      this.spawnList.add(new nA(EntityPigZombie.class, 5, 4, 4));
      this.spawnList.add(new nA(EntitySkeleton.class, 10, 4, 4));
      this.spawnList.add(new nA(EntityMagmaCube.class, 3, 4, 4));
   }

   public String getStructureName() {
      return "Fortress";
   }

   public List getSpawnList() {
      return this.spawnList;
   }

   protected boolean canSpawnStructureAtCoords(int var1, int var2) {
      int var3 = var1 >> 4;
      int var4 = var2 >> 4;
      this.rand.setSeed((long)(var3 ^ var4 << 4) ^ this.worldObj.getSeed());
      this.rand.nextInt();
      return this.rand.nextInt(3) == 0 && var1 == (var3 << 4) + 4 + this.rand.nextInt(8) && var2 == (var4 << 4) + 4 + this.rand.nextInt(8);
   }

   protected StructureStart getStructureStart(int var1, int var2) {
      return new MapGenNetherBridge$Start(this.worldObj, this.rand, var1, var2);
   }
}
