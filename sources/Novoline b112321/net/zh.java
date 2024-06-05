package net;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.IInteractionObject;

public class zh {
   public static String a(IInteractionObject var0) {
      return var0.getGuiID();
   }

   public static Container a(IInteractionObject var0, InventoryPlayer var1, EntityPlayer var2) {
      return var0.createContainer(var1, var2);
   }
}
