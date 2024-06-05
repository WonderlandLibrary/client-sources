package net.minecraft.inventory;

import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

class ContainerHorseInventory$2 extends Slot {
   final EntityHorse val$horse;
   final ContainerHorseInventory this$0;

   ContainerHorseInventory$2(ContainerHorseInventory var1, IInventory var2, int var3, int var4, int var5, EntityHorse var6) {
      super(var2, var3, var4, var5);
      this.this$0 = var1;
      this.val$horse = var6;
   }

   public boolean isItemValid(ItemStack var1) {
      return super.isItemValid(var1) && this.val$horse.canWearArmor() && EntityHorse.isArmorItem(var1.getItem());
   }

   public boolean canBeHovered() {
      return this.val$horse.canWearArmor();
   }
}
