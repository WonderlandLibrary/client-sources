package net;

import net.minecraft.entity.EntityLiving$SpawnPlacementType;
import net.minecraft.entity.EntitySpawnPlacementRegistry;

public class nb {
   public static EntityLiving$SpawnPlacementType a(Class var0) {
      return EntitySpawnPlacementRegistry.getPlacementForEntity(var0);
   }
}
