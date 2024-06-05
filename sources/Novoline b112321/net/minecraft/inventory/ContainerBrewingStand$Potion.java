package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;

class ContainerBrewingStand$Potion extends Slot {
   private EntityPlayer player;

   public ContainerBrewingStand$Potion(EntityPlayer var1, IInventory var2, int var3, int var4, int var5) {
      super(var2, var3, var4, var5);
      this.player = var1;
   }

   public boolean isItemValid(ItemStack var1) {
      return canHoldPotion(var1);
   }

   public int getSlotStackLimit() {
      return 1;
   }

   public void onPickupFromSlot(EntityPlayer var1, ItemStack var2) {
      if(var2.getItem() == Items.potionitem && var2.getMetadata() > 0) {
         this.player.triggerAchievement(AchievementList.potion);
      }

      super.onPickupFromSlot(var1, var2);
   }

   public static boolean canHoldPotion(ItemStack var0) {
      return var0.getItem() == Items.potionitem || var0.getItem() == Items.glass_bottle;
   }
}
