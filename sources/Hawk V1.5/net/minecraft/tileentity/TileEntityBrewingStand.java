package net.minecraft.tileentity;

import java.util.Arrays;
import java.util.List;
import net.minecraft.block.BlockBrewingStand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBrewingStand;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionHelper;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.EnumFacing;

public class TileEntityBrewingStand extends TileEntityLockable implements ISidedInventory, IUpdatePlayerListBox {
   private static final String __OBFID = "CL_00000345";
   private Item ingredientID;
   private ItemStack[] brewingItemStacks = new ItemStack[4];
   private static final int[] inputSlots = new int[]{3};
   private int brewTime;
   private boolean[] filledSlots;
   private String field_145942_n;
   private static final int[] outputSlots = new int[]{0, 1, 2};

   public void writeToNBT(NBTTagCompound var1) {
      super.writeToNBT(var1);
      var1.setShort("BrewTime", (short)this.brewTime);
      NBTTagList var2 = new NBTTagList();

      for(int var3 = 0; var3 < this.brewingItemStacks.length; ++var3) {
         if (this.brewingItemStacks[var3] != null) {
            NBTTagCompound var4 = new NBTTagCompound();
            var4.setByte("Slot", (byte)var3);
            this.brewingItemStacks[var3].writeToNBT(var4);
            var2.appendTag(var4);
         }
      }

      var1.setTag("Items", var2);
      if (this.hasCustomName()) {
         var1.setString("CustomName", this.field_145942_n);
      }

   }

   public void closeInventory(EntityPlayer var1) {
   }

   public ItemStack getStackInSlotOnClosing(int var1) {
      if (var1 >= 0 && var1 < this.brewingItemStacks.length) {
         ItemStack var2 = this.brewingItemStacks[var1];
         this.brewingItemStacks[var1] = null;
         return var2;
      } else {
         return null;
      }
   }

   public void setField(int var1, int var2) {
      switch(var1) {
      case 0:
         this.brewTime = var2;
      default:
      }
   }

   public void clearInventory() {
      for(int var1 = 0; var1 < this.brewingItemStacks.length; ++var1) {
         this.brewingItemStacks[var1] = null;
      }

   }

   private void brewPotions() {
      if (this.canBrew()) {
         ItemStack var1 = this.brewingItemStacks[3];

         for(int var2 = 0; var2 < 3; ++var2) {
            if (this.brewingItemStacks[var2] != null && this.brewingItemStacks[var2].getItem() == Items.potionitem) {
               int var3 = this.brewingItemStacks[var2].getMetadata();
               int var4 = this.func_145936_c(var3, var1);
               List var5 = Items.potionitem.getEffects(var3);
               List var6 = Items.potionitem.getEffects(var4);
               if ((var3 <= 0 || var5 != var6) && (var5 == null || !var5.equals(var6) && var6 != null)) {
                  if (var3 != var4) {
                     this.brewingItemStacks[var2].setItemDamage(var4);
                  }
               } else if (!ItemPotion.isSplash(var3) && ItemPotion.isSplash(var4)) {
                  this.brewingItemStacks[var2].setItemDamage(var4);
               }
            }
         }

         if (var1.getItem().hasContainerItem()) {
            this.brewingItemStacks[3] = new ItemStack(var1.getItem().getContainerItem());
         } else {
            --this.brewingItemStacks[3].stackSize;
            if (this.brewingItemStacks[3].stackSize <= 0) {
               this.brewingItemStacks[3] = null;
            }
         }
      }

   }

   public int getInventoryStackLimit() {
      return 64;
   }

   public ItemStack getStackInSlot(int var1) {
      return var1 >= 0 && var1 < this.brewingItemStacks.length ? this.brewingItemStacks[var1] : null;
   }

   public boolean canExtractItem(int var1, ItemStack var2, EnumFacing var3) {
      return true;
   }

   public void readFromNBT(NBTTagCompound var1) {
      super.readFromNBT(var1);
      NBTTagList var2 = var1.getTagList("Items", 10);
      this.brewingItemStacks = new ItemStack[this.getSizeInventory()];

      for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
         NBTTagCompound var4 = var2.getCompoundTagAt(var3);
         byte var5 = var4.getByte("Slot");
         if (var5 >= 0 && var5 < this.brewingItemStacks.length) {
            this.brewingItemStacks[var5] = ItemStack.loadItemStackFromNBT(var4);
         }
      }

      this.brewTime = var1.getShort("BrewTime");
      if (var1.hasKey("CustomName", 8)) {
         this.field_145942_n = var1.getString("CustomName");
      }

   }

   public int getFieldCount() {
      return 1;
   }

   private int func_145936_c(int var1, ItemStack var2) {
      return var2 == null ? var1 : (var2.getItem().isPotionIngredient(var2) ? PotionHelper.applyIngredient(var1, var2.getItem().getPotionEffect(var2)) : var1);
   }

   public String getName() {
      return this.hasCustomName() ? this.field_145942_n : "container.brewing";
   }

   public boolean canInsertItem(int var1, ItemStack var2, EnumFacing var3) {
      return this.isItemValidForSlot(var1, var2);
   }

   public int getSizeInventory() {
      return this.brewingItemStacks.length;
   }

   public Container createContainer(InventoryPlayer var1, EntityPlayer var2) {
      return new ContainerBrewingStand(var1, this);
   }

   public void func_145937_a(String var1) {
      this.field_145942_n = var1;
   }

   public void openInventory(EntityPlayer var1) {
   }

   public int getField(int var1) {
      switch(var1) {
      case 0:
         return this.brewTime;
      default:
         return 0;
      }
   }

   private boolean canBrew() {
      if (this.brewingItemStacks[3] != null && this.brewingItemStacks[3].stackSize > 0) {
         ItemStack var1 = this.brewingItemStacks[3];
         if (!var1.getItem().isPotionIngredient(var1)) {
            return false;
         } else {
            boolean var2 = false;

            for(int var3 = 0; var3 < 3; ++var3) {
               if (this.brewingItemStacks[var3] != null && this.brewingItemStacks[var3].getItem() == Items.potionitem) {
                  int var4 = this.brewingItemStacks[var3].getMetadata();
                  int var5 = this.func_145936_c(var4, var1);
                  if (!ItemPotion.isSplash(var4) && ItemPotion.isSplash(var5)) {
                     var2 = true;
                     break;
                  }

                  List var6 = Items.potionitem.getEffects(var4);
                  List var7 = Items.potionitem.getEffects(var5);
                  if ((var4 <= 0 || var6 != var7) && (var6 == null || !var6.equals(var7) && var7 != null) && var4 != var5) {
                     var2 = true;
                     break;
                  }
               }
            }

            return var2;
         }
      } else {
         return false;
      }
   }

   public void setInventorySlotContents(int var1, ItemStack var2) {
      if (var1 >= 0 && var1 < this.brewingItemStacks.length) {
         this.brewingItemStacks[var1] = var2;
      }

   }

   public boolean hasCustomName() {
      return this.field_145942_n != null && this.field_145942_n.length() > 0;
   }

   public boolean isUseableByPlayer(EntityPlayer var1) {
      return this.worldObj.getTileEntity(this.pos) != this ? false : var1.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
   }

   public ItemStack decrStackSize(int var1, int var2) {
      if (var1 >= 0 && var1 < this.brewingItemStacks.length) {
         ItemStack var3 = this.brewingItemStacks[var1];
         this.brewingItemStacks[var1] = null;
         return var3;
      } else {
         return null;
      }
   }

   public boolean isItemValidForSlot(int var1, ItemStack var2) {
      return var1 == 3 ? var2.getItem().isPotionIngredient(var2) : var2.getItem() == Items.potionitem || var2.getItem() == Items.glass_bottle;
   }

   public String getGuiID() {
      return "minecraft:brewing_stand";
   }

   public void update() {
      if (this.brewTime > 0) {
         --this.brewTime;
         if (this.brewTime == 0) {
            this.brewPotions();
            this.markDirty();
         } else if (!this.canBrew()) {
            this.brewTime = 0;
            this.markDirty();
         } else if (this.ingredientID != this.brewingItemStacks[3].getItem()) {
            this.brewTime = 0;
            this.markDirty();
         }
      } else if (this.canBrew()) {
         this.brewTime = 400;
         this.ingredientID = this.brewingItemStacks[3].getItem();
      }

      if (!this.worldObj.isRemote) {
         boolean[] var1 = this.func_174902_m();
         if (!Arrays.equals(var1, this.filledSlots)) {
            this.filledSlots = var1;
            IBlockState var2 = this.worldObj.getBlockState(this.getPos());
            if (!(var2.getBlock() instanceof BlockBrewingStand)) {
               return;
            }

            for(int var3 = 0; var3 < BlockBrewingStand.BOTTLE_PROPS.length; ++var3) {
               var2 = var2.withProperty(BlockBrewingStand.BOTTLE_PROPS[var3], var1[var3]);
            }

            this.worldObj.setBlockState(this.pos, var2, 2);
         }
      }

   }

   public int[] getSlotsForFace(EnumFacing var1) {
      return var1 == EnumFacing.UP ? inputSlots : outputSlots;
   }

   public boolean[] func_174902_m() {
      boolean[] var1 = new boolean[3];

      for(int var2 = 0; var2 < 3; ++var2) {
         if (this.brewingItemStacks[var2] != null) {
            var1[var2] = true;
         }
      }

      return var1;
   }
}
