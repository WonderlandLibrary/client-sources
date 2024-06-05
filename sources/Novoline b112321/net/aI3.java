package net;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityOwnable;

public class aI3 {
   public static String a(IEntityOwnable var0) {
      return var0.getOwnerId();
   }

   public static Entity b(IEntityOwnable var0) {
      return var0.getOwner();
   }
}
