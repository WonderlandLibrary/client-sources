package net.minecraft.inventory;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.village.MerchantRecipe;

public class SlotMerchantResult extends Slot {
   private final IMerchant theMerchant;
   private int field_75231_g;
   private EntityPlayer thePlayer;
   private final InventoryMerchant theMerchantInventory;
   private static final String __OBFID = "CL_00001758";

   public boolean isItemValid(ItemStack var1) {
      return false;
   }

   protected void onCrafting(ItemStack var1) {
      var1.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.field_75231_g);
      this.field_75231_g = 0;
   }

   public void onPickupFromSlot(EntityPlayer var1, ItemStack var2) {
      this.onCrafting(var2);
      MerchantRecipe var3 = this.theMerchantInventory.getCurrentRecipe();
      if (var3 != null) {
         ItemStack var4 = this.theMerchantInventory.getStackInSlot(0);
         ItemStack var5 = this.theMerchantInventory.getStackInSlot(1);
         if (this.doTrade(var3, var4, var5) || this.doTrade(var3, var5, var4)) {
            this.theMerchant.useRecipe(var3);
            var1.triggerAchievement(StatList.timesTradedWithVillagerStat);
            if (var4 != null && var4.stackSize <= 0) {
               var4 = null;
            }

            if (var5 != null && var5.stackSize <= 0) {
               var5 = null;
            }

            this.theMerchantInventory.setInventorySlotContents(0, var4);
            this.theMerchantInventory.setInventorySlotContents(1, var5);
         }
      }

   }

   public SlotMerchantResult(EntityPlayer var1, IMerchant var2, InventoryMerchant var3, int var4, int var5, int var6) {
      super(var3, var4, var5, var6);
      this.thePlayer = var1;
      this.theMerchant = var2;
      this.theMerchantInventory = var3;
   }

   public ItemStack decrStackSize(int var1) {
      if (this.getHasStack()) {
         this.field_75231_g += Math.min(var1, this.getStack().stackSize);
      }

      return super.decrStackSize(var1);
   }

   protected void onCrafting(ItemStack var1, int var2) {
      this.field_75231_g += var2;
      this.onCrafting(var1);
   }

   private boolean doTrade(MerchantRecipe var1, ItemStack var2, ItemStack var3) {
      ItemStack var4 = var1.getItemToBuy();
      ItemStack var5 = var1.getSecondItemToBuy();
      if (var2 != null && var2.getItem() == var4.getItem()) {
         if (var5 != null && var3 != null && var5.getItem() == var3.getItem()) {
            var2.stackSize -= var4.stackSize;
            var3.stackSize -= var5.stackSize;
            return true;
         }

         if (var5 == null && var3 == null) {
            var2.stackSize -= var4.stackSize;
            return true;
         }
      }

      return false;
   }
}
