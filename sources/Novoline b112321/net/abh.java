package net;

import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.Slot;

public class abh {
   public static void a(ContainerRepair var0, String var1) {
      var0.updateItemName(var1);
   }

   public static Slot a(ContainerRepair var0, int var1) {
      return var0.getSlot(var1);
   }
}
