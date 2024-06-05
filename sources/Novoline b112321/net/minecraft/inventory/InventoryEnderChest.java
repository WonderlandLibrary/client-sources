package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityEnderChest;

public class InventoryEnderChest extends InventoryBasic {
   private TileEntityEnderChest associatedChest;

   public InventoryEnderChest() {
      super("container.enderchest", false, 27);
   }

   public void setChestTileEntity(TileEntityEnderChest var1) {
      this.associatedChest = var1;
   }

   public void loadInventoryFromNBT(NBTTagList var1) {
      for(int var2 = 0; var2 < this.getSizeInventory(); ++var2) {
         this.setInventorySlotContents(var2, (ItemStack)null);
      }

      for(int var5 = 0; var5 < var1.tagCount(); ++var5) {
         NBTTagCompound var3 = var1.getCompoundTagAt(var5);
         int var4 = var3.getByte("Slot") & 255;
         if(var4 < this.getSizeInventory()) {
            this.setInventorySlotContents(var4, ItemStack.loadItemStackFromNBT(var3));
         }
      }

   }

   public NBTTagList saveInventoryToNBT() {
      NBTTagList var1 = new NBTTagList();

      for(int var2 = 0; var2 < this.getSizeInventory(); ++var2) {
         ItemStack var3 = this.getStackInSlot(var2);
         NBTTagCompound var4 = new NBTTagCompound();
         var4.setByte("Slot", (byte)var2);
         var3.writeToNBT(var4);
         var1.appendTag(var4);
      }

      return var1;
   }

   public boolean isUseableByPlayer(EntityPlayer var1) {
      return (this.associatedChest == null || this.associatedChest.canBeUsed(var1)) && super.isUseableByPlayer(var1);
   }

   public void openInventory(EntityPlayer var1) {
      if(this.associatedChest != null) {
         this.associatedChest.openChest();
      }

      super.openInventory(var1);
   }

   public void closeInventory(EntityPlayer var1) {
      if(this.associatedChest != null) {
         this.associatedChest.closeChest();
      }

      super.closeInventory(var1);
      this.associatedChest = null;
   }
}
