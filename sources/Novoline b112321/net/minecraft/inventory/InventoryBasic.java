package net.minecraft.inventory;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInvBasic;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class InventoryBasic implements IInventory {
   private String inventoryTitle;
   private int slotsCount;
   private ItemStack[] inventoryContents;
   private List field_70480_d;
   private boolean hasCustomName;

   public InventoryBasic(String var1, boolean var2, int var3) {
      this.inventoryTitle = var1;
      this.hasCustomName = var2;
      this.slotsCount = var3;
      this.inventoryContents = new ItemStack[var3];
   }

   public InventoryBasic(IChatComponent var1, int var2) {
      this(var1.getUnformattedText(), true, var2);
   }

   public void func_110134_a(IInvBasic var1) {
      if(this.field_70480_d == null) {
         this.field_70480_d = Lists.newArrayList();
      }

      this.field_70480_d.add(var1);
   }

   public void func_110132_b(IInvBasic var1) {
      this.field_70480_d.remove(var1);
   }

   public ItemStack getStackInSlot(int var1) {
      return var1 < this.inventoryContents.length?this.inventoryContents[var1]:null;
   }

   public ItemStack decrStackSize(int var1, int var2) {
      if(this.inventoryContents[var1] != null) {
         if(this.inventoryContents[var1].stackSize <= var2) {
            ItemStack var4 = this.inventoryContents[var1];
            this.inventoryContents[var1] = null;
            this.markDirty();
            return var4;
         } else {
            ItemStack var3 = this.inventoryContents[var1].splitStack(var2);
            if(this.inventoryContents[var1].stackSize == 0) {
               this.inventoryContents[var1] = null;
            }

            this.markDirty();
            return var3;
         }
      } else {
         return null;
      }
   }

   public ItemStack a(ItemStack var1) {
      ItemStack var2 = var1.copy();
      byte var3 = 0;
      if(var3 < this.slotsCount) {
         ItemStack var4 = this.getStackInSlot(var3);
         this.setInventorySlotContents(var3, var2);
         this.markDirty();
         return null;
      } else {
         if(var2.stackSize != var1.stackSize) {
            this.markDirty();
         }

         return var2;
      }
   }

   public ItemStack removeStackFromSlot(int var1) {
      if(this.inventoryContents[var1] != null) {
         ItemStack var2 = this.inventoryContents[var1];
         this.inventoryContents[var1] = null;
         return var2;
      } else {
         return null;
      }
   }

   public void setInventorySlotContents(int var1, ItemStack var2) {
      this.inventoryContents[var1] = var2;
      if(var2.stackSize > this.getInventoryStackLimit()) {
         var2.stackSize = this.getInventoryStackLimit();
      }

      this.markDirty();
   }

   public int getSizeInventory() {
      return this.slotsCount;
   }

   public String getName() {
      return this.inventoryTitle;
   }

   public boolean hasCustomName() {
      return this.hasCustomName;
   }

   public void setCustomName(String var1) {
      this.hasCustomName = true;
      this.inventoryTitle = var1;
   }

   public IChatComponent getDisplayName() {
      return (IChatComponent)(this.hasCustomName()?new ChatComponentText(this.getName()):new ChatComponentTranslation(this.getName(), new Object[0]));
   }

   public int getInventoryStackLimit() {
      return 64;
   }

   public void markDirty() {
      if(this.field_70480_d != null) {
         for(IInvBasic var2 : this.field_70480_d) {
            var2.onInventoryChanged(this);
         }
      }

   }

   public boolean isUseableByPlayer(EntityPlayer var1) {
      return true;
   }

   public void openInventory(EntityPlayer var1) {
   }

   public void closeInventory(EntityPlayer var1) {
   }

   public boolean isItemValidForSlot(int var1, ItemStack var2) {
      return true;
   }

   public int getField(int var1) {
      return 0;
   }

   public void setField(int var1, int var2) {
   }

   public int getFieldCount() {
      return 0;
   }

   public void clear() {
      for(int var1 = 0; var1 < this.inventoryContents.length; ++var1) {
         this.inventoryContents[var1] = null;
      }

   }
}
