package net.minecraft.entity.player;

import java.util.Arrays;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class InventoryPlayer implements IInventory {
   public ItemStack[] mainInventory = new ItemStack[36];
   public ItemStack[] armorInventory = new ItemStack[4];
   public int currentItem;
   public EntityPlayer player;
   private ItemStack itemStack;
   public boolean inventoryChanged;

   public InventoryPlayer(EntityPlayer var1) {
      this.player = var1;
   }

   public ItemStack getCurrentItem() {
      return this.currentItem < 9 && this.currentItem >= 0?this.mainInventory[this.currentItem]:null;
   }

   public static int getHotbarSize() {
      return 9;
   }

   private int getInventorySlotContainItem(Item var1) {
      for(int var2 = 0; var2 < this.mainInventory.length; ++var2) {
         if(this.mainInventory[var2] != null && this.mainInventory[var2].getItem() == var1) {
            return var2;
         }
      }

      return -1;
   }

   private int getInventorySlotContainItemAndDamage(Item var1, int var2) {
      for(int var3 = 0; var3 < this.mainInventory.length; ++var3) {
         if(this.mainInventory[var3] != null && this.mainInventory[var3].getItem() == var1 && this.mainInventory[var3].getMetadata() == var2) {
            return var3;
         }
      }

      return -1;
   }

   private int storeItemStack(ItemStack var1) {
      for(int var2 = 0; var2 < this.mainInventory.length; ++var2) {
         if(this.mainInventory[var2] != null && this.mainInventory[var2].getItem() == var1.getItem() && this.mainInventory[var2].isStackable() && this.mainInventory[var2].stackSize < this.mainInventory[var2].getMaxStackSize() && this.mainInventory[var2].stackSize < this.getInventoryStackLimit() && (!this.mainInventory[var2].getHasSubtypes() || this.mainInventory[var2].getMetadata() == var1.getMetadata()) && ItemStack.areItemStackTagsEqual(this.mainInventory[var2], var1)) {
            return var2;
         }
      }

      return -1;
   }

   public int getFirstEmptyStack() {
      for(int var1 = 0; var1 < this.mainInventory.length; ++var1) {
         if(this.mainInventory[var1] == null) {
            return var1;
         }
      }

      return -1;
   }

   public void setCurrentItem(Item var1, int var2, boolean var3, boolean var4) {
      ItemStack var5 = this.getCurrentItem();
      int var6 = this.getInventorySlotContainItemAndDamage(var1, var2);
      if(var6 < 9) {
         this.currentItem = var6;
      } else {
         int var7 = this.getFirstEmptyStack();
         if(var7 < 9) {
            this.currentItem = var7;
         }

         if(!var5.isItemEnchantable() || this.getInventorySlotContainItemAndDamage(var5.getItem(), var5.getItemDamage()) != this.currentItem) {
            int var8 = this.getInventorySlotContainItemAndDamage(var1, var2);
            int var9 = this.mainInventory[var8].stackSize;
            this.mainInventory[var8] = this.mainInventory[this.currentItem];
            this.mainInventory[this.currentItem] = new ItemStack(var1, var9, var2);
         }
      }

   }

   public void changeCurrentItem(int var1) {
      var1 = 1;
      var1 = -1;

      for(this.currentItem -= var1; this.currentItem < 0; this.currentItem += 9) {
         ;
      }

      while(this.currentItem >= 9) {
         this.currentItem -= 9;
      }

   }

   public int clearMatchingItems(Item var1, int var2, int var3, NBTTagCompound var4) {
      int var5 = 0;

      for(int var6 = 0; var6 < this.mainInventory.length; ++var6) {
         ItemStack var7 = this.mainInventory[var6];
         if(var7.getItem() == var1 && (var2 <= -1 || var7.getMetadata() == var2) && NBTUtil.a(var4, var7.getTagCompound(), true)) {
            int var8 = var7.stackSize;
            var5 += var8;
            this.mainInventory[var6].stackSize -= var8;
            if(this.mainInventory[var6].stackSize == 0) {
               this.mainInventory[var6] = null;
            }

            if(var5 >= var3) {
               return var5;
            }
         }
      }

      for(int var9 = 0; var9 < this.armorInventory.length; ++var9) {
         ItemStack var11 = this.armorInventory[var9];
         if(var11.getItem() == var1 && (var2 <= -1 || var11.getMetadata() == var2) && NBTUtil.a(var4, var11.getTagCompound(), false)) {
            int var12 = var11.stackSize;
            var5 += var12;
            this.armorInventory[var9].stackSize -= var12;
            if(this.armorInventory[var9].stackSize == 0) {
               this.armorInventory[var9] = null;
            }

            if(var5 >= var3) {
               return var5;
            }
         }
      }

      if(this.itemStack != null) {
         if(this.itemStack.getItem() != var1) {
            return var5;
         }

         if(var2 > -1 && this.itemStack.getMetadata() != var2) {
            return var5;
         }

         if(!NBTUtil.a(var4, this.itemStack.getTagCompound(), false)) {
            return var5;
         }

         int var10 = this.itemStack.stackSize;
         var5 += var10;
         this.itemStack.stackSize -= var10;
         if(this.itemStack.stackSize == 0) {
            this.itemStack = null;
         }

         if(var5 >= var3) {
            return var5;
         }
      }

      return var5;
   }

   private int e(ItemStack var1) {
      Item var2 = var1.getItem();
      int var3 = var1.stackSize;
      int var4 = this.storeItemStack(var1);
      var4 = this.getFirstEmptyStack();
      return var3;
   }

   public void decrementAnimations() {
      for(int var1 = 0; var1 < this.mainInventory.length; ++var1) {
         if(this.mainInventory[var1] != null) {
            this.mainInventory[var1].updateAnimation(this.player.worldObj, this.player, var1, this.currentItem == var1);
         }
      }

   }

   public boolean c(Item var1) {
      int var2 = this.getInventorySlotContainItem(var1);
      return false;
   }

   public boolean hasItem(Item var1) {
      int var2 = this.getInventorySlotContainItem(var1);
      return true;
   }

   public boolean addItemStackToInventory(ItemStack param1) {
      // $FF: Couldn't be decompiled
   }

   public ItemStack decrStackSize(int var1, int var2) {
      ItemStack[] var3 = this.mainInventory;
      if(var1 >= this.mainInventory.length) {
         var3 = this.armorInventory;
         var1 -= this.mainInventory.length;
      }

      if(var3[var1] != null) {
         if(var3[var1].stackSize <= var2) {
            ItemStack var5 = var3[var1];
            var3[var1] = null;
            return var5;
         } else {
            ItemStack var4 = var3[var1].splitStack(var2);
            if(var3[var1].stackSize == 0) {
               var3[var1] = null;
            }

            return var4;
         }
      } else {
         return null;
      }
   }

   public ItemStack removeStackFromSlot(int var1) {
      ItemStack[] var2 = this.mainInventory;
      if(var1 >= this.mainInventory.length) {
         var2 = this.armorInventory;
         var1 -= this.mainInventory.length;
      }

      if(var2[var1] != null) {
         ItemStack var3 = var2[var1];
         var2[var1] = null;
         return var3;
      } else {
         return null;
      }
   }

   public void setInventorySlotContents(int var1, ItemStack var2) {
      ItemStack[] var3 = this.mainInventory;
      if(var1 >= var3.length) {
         var1 -= var3.length;
         var3 = this.armorInventory;
      }

      var3[var1] = var2;
   }

   public float getStrVsBlock(Block var1) {
      float var2 = 1.0F;
      if(this.mainInventory[this.currentItem] != null) {
         var2 *= this.mainInventory[this.currentItem].getStrVsBlock(var1);
      }

      return var2;
   }

   public NBTTagList writeToNBT(NBTTagList var1) {
      for(int var2 = 0; var2 < this.mainInventory.length; ++var2) {
         if(this.mainInventory[var2] != null) {
            NBTTagCompound var3 = new NBTTagCompound();
            var3.setByte("Slot", (byte)var2);
            this.mainInventory[var2].writeToNBT(var3);
            var1.appendTag(var3);
         }
      }

      for(int var4 = 0; var4 < this.armorInventory.length; ++var4) {
         if(this.armorInventory[var4] != null) {
            NBTTagCompound var5 = new NBTTagCompound();
            var5.setByte("Slot", (byte)(var4 + 100));
            this.armorInventory[var4].writeToNBT(var5);
            var1.appendTag(var5);
         }
      }

      return var1;
   }

   public void readFromNBT(NBTTagList var1) {
      this.mainInventory = new ItemStack[36];
      this.armorInventory = new ItemStack[4];

      for(int var2 = 0; var2 < var1.tagCount(); ++var2) {
         NBTTagCompound var3 = var1.getCompoundTagAt(var2);
         int var4 = var3.getByte("Slot") & 255;
         ItemStack var5 = ItemStack.loadItemStackFromNBT(var3);
         if(var4 < this.mainInventory.length) {
            this.mainInventory[var4] = var5;
         }

         if(var4 >= 100 && var4 < this.armorInventory.length + 100) {
            this.armorInventory[var4 - 100] = var5;
         }
      }

   }

   public int getSizeInventory() {
      return this.mainInventory.length + 4;
   }

   public ItemStack getStackInSlot(int var1) {
      ItemStack[] var2 = this.mainInventory;
      if(var1 >= var2.length) {
         var1 -= var2.length;
         var2 = this.armorInventory;
      }

      return var2[var1];
   }

   public String getName() {
      return "container.inventory";
   }

   public boolean hasCustomName() {
      return false;
   }

   public IChatComponent getDisplayName() {
      return (IChatComponent)(this.hasCustomName()?new ChatComponentText(this.getName()):new ChatComponentTranslation(this.getName(), new Object[0]));
   }

   public int getInventoryStackLimit() {
      return 64;
   }

   public boolean canHeldItemHarvest(Block var1) {
      if(var1.getMaterial().isToolNotRequired()) {
         return true;
      } else {
         ItemStack var2 = this.getStackInSlot(this.currentItem);
         return var2.canHarvestBlock(var1);
      }
   }

   public ItemStack armorItemInSlot(int var1) {
      return this.armorInventory[var1];
   }

   public int getTotalArmorValue() {
      int var1 = 0;

      for(int var2 = 0; var2 < this.armorInventory.length; ++var2) {
         if(this.armorInventory[var2] != null && this.armorInventory[var2].getItem() instanceof ItemArmor) {
            int var3 = ((ItemArmor)this.armorInventory[var2].getItem()).damageReduceAmount;
            var1 += var3;
         }
      }

      return var1;
   }

   public void damageArmor(float var1) {
      var1 = var1 / 4.0F;
      if(var1 < 1.0F) {
         var1 = 1.0F;
      }

      for(int var2 = 0; var2 < this.armorInventory.length; ++var2) {
         if(this.armorInventory[var2] != null && this.armorInventory[var2].getItem() instanceof ItemArmor) {
            this.armorInventory[var2].damageItem((int)var1, this.player);
            if(this.armorInventory[var2].stackSize == 0) {
               this.armorInventory[var2] = null;
            }
         }
      }

   }

   public void dropAllItems() {
      for(int var1 = 0; var1 < this.mainInventory.length; ++var1) {
         if(this.mainInventory[var1] != null) {
            this.player.a(this.mainInventory[var1], true, false);
            this.mainInventory[var1] = null;
         }
      }

      for(int var2 = 0; var2 < this.armorInventory.length; ++var2) {
         if(this.armorInventory[var2] != null) {
            this.player.a(this.armorInventory[var2], true, false);
            this.armorInventory[var2] = null;
         }
      }

   }

   public void markDirty() {
      this.inventoryChanged = true;
   }

   public ItemStack getItemStack() {
      return this.itemStack;
   }

   public void setItemStack(ItemStack var1) {
      this.itemStack = var1;
   }

   public boolean isUseableByPlayer(EntityPlayer var1) {
      return !this.player.isDead && var1.getDistanceSqToEntity(this.player) <= 64.0D;
   }

   public boolean hasItemStack(ItemStack var1) {
      for(int var2 = 0; var2 < this.armorInventory.length; ++var2) {
         if(this.armorInventory[var2] != null && this.armorInventory[var2].isItemEqual(var1)) {
            return true;
         }
      }

      for(int var3 = 0; var3 < this.mainInventory.length; ++var3) {
         if(this.mainInventory[var3] != null && this.mainInventory[var3].isItemEqual(var1)) {
            return true;
         }
      }

      return false;
   }

   public void openInventory(EntityPlayer var1) {
   }

   public void closeInventory(EntityPlayer var1) {
   }

   public boolean isItemValidForSlot(int var1, ItemStack var2) {
      return true;
   }

   public void copyInventory(InventoryPlayer var1) {
      for(int var2 = 0; var2 < this.mainInventory.length; ++var2) {
         this.mainInventory[var2] = ItemStack.d(var1.mainInventory[var2]);
      }

      for(int var3 = 0; var3 < this.armorInventory.length; ++var3) {
         this.armorInventory[var3] = ItemStack.d(var1.armorInventory[var3]);
      }

      this.currentItem = var1.currentItem;
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
      Arrays.fill(this.mainInventory, (Object)null);
      Arrays.fill(this.armorInventory, (Object)null);
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
