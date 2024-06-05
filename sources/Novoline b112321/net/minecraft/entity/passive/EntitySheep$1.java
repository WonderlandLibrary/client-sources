package net.minecraft.entity.passive;

import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

class EntitySheep$1 extends Container {
   final EntitySheep this$0;

   EntitySheep$1(EntitySheep var1) {
      this.this$0 = var1;
   }

   public boolean canInteractWith(EntityPlayer var1) {
      return false;
   }
}
