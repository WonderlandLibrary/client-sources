package net;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.world.World;

public class rw {
   public static Entity a(World var0, int var1, double var2, double var4, double var6) {
      return ItemMonsterPlacer.spawnCreature(var0, var1, var2, var4, var6);
   }
}
