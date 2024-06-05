package net.minecraft.inventory;

import java.util.List;
import java.util.Random;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerEnchantment$1;
import net.minecraft.inventory.ContainerEnchantment$2;
import net.minecraft.inventory.ContainerEnchantment$3;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ContainerEnchantment extends Container {
   public IInventory tableInventory;
   private World worldPointer;
   private BlockPos position;
   private Random rand;
   public int xpSeed;
   public int[] enchantLevels;
   public int[] field_178151_h;

   public ContainerEnchantment(InventoryPlayer var1, World var2) {
      this(var1, var2, BlockPos.ORIGIN);
   }

   public ContainerEnchantment(InventoryPlayer var1, World var2, BlockPos var3) {
      this.tableInventory = new ContainerEnchantment$1(this, "Enchant", true, 2);
      this.rand = new Random();
      this.enchantLevels = new int[3];
      this.field_178151_h = new int[]{-1, -1, -1};
      this.worldPointer = var2;
      this.position = var3;
      this.xpSeed = var1.player.getXPSeed();
      this.addSlotToContainer(new ContainerEnchantment$2(this, this.tableInventory, 0, 15, 47));
      this.addSlotToContainer(new ContainerEnchantment$3(this, this.tableInventory, 1, 35, 47));

      for(int var4 = 0; var4 < 3; ++var4) {
         for(int var5 = 0; var5 < 9; ++var5) {
            this.addSlotToContainer(new Slot(var1, var5 + var4 * 9 + 9, 8 + var5 * 18, 84 + var4 * 18));
         }
      }

      for(int var6 = 0; var6 < 9; ++var6) {
         this.addSlotToContainer(new Slot(var1, var6, 8 + var6 * 18, 142));
      }

   }

   public void onCraftGuiOpened(ICrafting var1) {
      super.onCraftGuiOpened(var1);
      var1.sendProgressBarUpdate(this, 0, this.enchantLevels[0]);
      var1.sendProgressBarUpdate(this, 1, this.enchantLevels[1]);
      var1.sendProgressBarUpdate(this, 2, this.enchantLevels[2]);
      var1.sendProgressBarUpdate(this, 3, this.xpSeed & -16);
      var1.sendProgressBarUpdate(this, 4, this.field_178151_h[0]);
      var1.sendProgressBarUpdate(this, 5, this.field_178151_h[1]);
      var1.sendProgressBarUpdate(this, 6, this.field_178151_h[2]);
   }

   public void detectAndSendChanges() {
      super.detectAndSendChanges();

      for(ICrafting var2 : this.crafters) {
         var2.sendProgressBarUpdate(this, 0, this.enchantLevels[0]);
         var2.sendProgressBarUpdate(this, 1, this.enchantLevels[1]);
         var2.sendProgressBarUpdate(this, 2, this.enchantLevels[2]);
         var2.sendProgressBarUpdate(this, 3, this.xpSeed & -16);
         var2.sendProgressBarUpdate(this, 4, this.field_178151_h[0]);
         var2.sendProgressBarUpdate(this, 5, this.field_178151_h[1]);
         var2.sendProgressBarUpdate(this, 6, this.field_178151_h[2]);
      }

   }

   public void updateProgressBar(int var1, int var2) {
      if(var1 <= 2) {
         this.enchantLevels[var1] = var2;
      } else if(var1 == 3) {
         this.xpSeed = var2;
      } else if(var1 >= 4 && var1 <= 6) {
         this.field_178151_h[var1 - 4] = var2;
      } else {
         super.updateProgressBar(var1, var2);
      }

   }

   public void onCraftMatrixChanged(IInventory var1) {
      if(var1 == this.tableInventory) {
         ItemStack var2 = var1.getStackInSlot(0);
         if(var2.isItemEnchantable()) {
            if(!this.worldPointer.isRemote) {
               int var3 = 0;

               for(int var4 = -1; var4 <= 1; ++var4) {
                  for(int var5 = -1; var5 <= 1; ++var5) {
                     if(this.worldPointer.isAirBlock(this.position.a(var5, 0, var4)) && this.worldPointer.isAirBlock(this.position.a(var5, 1, var4))) {
                        if(this.worldPointer.getBlockState(this.position.a(var5 * 2, 0, var4 * 2)).getBlock() == Blocks.bookshelf) {
                           ++var3;
                        }

                        if(this.worldPointer.getBlockState(this.position.a(var5 * 2, 1, var4 * 2)).getBlock() == Blocks.bookshelf) {
                           ++var3;
                        }

                        if(this.worldPointer.getBlockState(this.position.a(var5 * 2, 0, var4)).getBlock() == Blocks.bookshelf) {
                           ++var3;
                        }

                        if(this.worldPointer.getBlockState(this.position.a(var5 * 2, 1, var4)).getBlock() == Blocks.bookshelf) {
                           ++var3;
                        }

                        if(this.worldPointer.getBlockState(this.position.a(var5, 0, var4 * 2)).getBlock() == Blocks.bookshelf) {
                           ++var3;
                        }

                        if(this.worldPointer.getBlockState(this.position.a(var5, 1, var4 * 2)).getBlock() == Blocks.bookshelf) {
                           ++var3;
                        }
                     }
                  }
               }

               this.rand.setSeed((long)this.xpSeed);

               for(int var8 = 0; var8 < 3; ++var8) {
                  this.enchantLevels[var8] = EnchantmentHelper.calcItemStackEnchantability(this.rand, var8, var3, var2);
                  this.field_178151_h[var8] = -1;
                  if(this.enchantLevels[var8] < var8 + 1) {
                     this.enchantLevels[var8] = 0;
                  }
               }

               for(int var9 = 0; var9 < 3; ++var9) {
                  if(this.enchantLevels[var9] > 0) {
                     List var10 = this.func_178148_a(var2, var9, this.enchantLevels[var9]);
                     if(!var10.isEmpty()) {
                        EnchantmentData var6 = (EnchantmentData)var10.get(this.rand.nextInt(var10.size()));
                        this.field_178151_h[var9] = var6.enchantmentobj.effectId | var6.enchantmentLevel << 8;
                     }
                  }
               }

               this.detectAndSendChanges();
            }
         } else {
            for(int var7 = 0; var7 < 3; ++var7) {
               this.enchantLevels[var7] = 0;
               this.field_178151_h[var7] = -1;
            }
         }
      }

   }

   public boolean enchantItem(EntityPlayer var1, int var2) {
      ItemStack var3 = this.tableInventory.getStackInSlot(0);
      ItemStack var4 = this.tableInventory.getStackInSlot(1);
      int var5 = var2 + 1;
      if(var4.stackSize < var5 && !var1.abilities.isCreative()) {
         return false;
      } else if(this.enchantLevels[var2] > 0 && (var1.experienceLevel >= var5 && var1.experienceLevel >= this.enchantLevels[var2] || var1.abilities.isCreative())) {
         if(!this.worldPointer.isRemote) {
            List var6 = this.func_178148_a(var3, var2, this.enchantLevels[var2]);
            boolean var7 = var3.getItem() == Items.book;
            var1.removeExperienceLevel(var5);
            var3.setItem(Items.enchanted_book);

            for(EnchantmentData var9 : var6) {
               Items.enchanted_book.addEnchantment(var3, var9);
            }

            if(!var1.abilities.isCreative()) {
               var4.stackSize -= var5;
               if(var4.stackSize <= 0) {
                  this.tableInventory.setInventorySlotContents(1, (ItemStack)null);
               }
            }

            var1.triggerAchievement(StatList.field_181739_W);
            this.tableInventory.markDirty();
            this.xpSeed = var1.getXPSeed();
            this.onCraftMatrixChanged(this.tableInventory);
         }

         return true;
      } else {
         return false;
      }
   }

   private List func_178148_a(ItemStack var1, int var2, int var3) {
      this.rand.setSeed((long)(this.xpSeed + var2));
      List var4 = EnchantmentHelper.a(this.rand, var1, var3);
      if(var1.getItem() == Items.book && var4.size() > 1) {
         var4.remove(this.rand.nextInt(var4.size()));
      }

      return var4;
   }

   public int getLapisAmount() {
      ItemStack var1 = this.tableInventory.getStackInSlot(1);
      return 0;
   }

   public void onContainerClosed(EntityPlayer var1) {
      super.onContainerClosed(var1);
      if(!this.worldPointer.isRemote) {
         for(int var2 = 0; var2 < this.tableInventory.getSizeInventory(); ++var2) {
            ItemStack var3 = this.tableInventory.removeStackFromSlot(var2);
            var1.dropPlayerItemWithRandomChoice(var3, false);
         }
      }

   }

   public boolean canInteractWith(EntityPlayer var1) {
      return this.worldPointer.getBlockState(this.position).getBlock() == Blocks.enchanting_table && var1.getDistanceSq((double)this.position.getX() + 0.5D, (double)this.position.getY() + 0.5D, (double)this.position.getZ() + 0.5D) <= 64.0D;
   }

   public ItemStack transferStackInSlot(EntityPlayer var1, int var2) {
      ItemStack var3 = null;
      Slot var4 = (Slot)this.inventorySlots.get(var2);
      if(var4.getHasStack()) {
         ItemStack var5 = var4.getStack();
         var3 = var5.copy();
         if(!this.mergeItemStack(var5, 2, 38, true)) {
            return null;
         }

         if(var5.stackSize == 0) {
            var4.putStack((ItemStack)null);
         } else {
            var4.onSlotChanged();
         }

         if(var5.stackSize == var3.stackSize) {
            return null;
         }

         var4.onPickupFromSlot(var1, var5);
      }

      return var3;
   }
}
