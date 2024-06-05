package net.minecraft.inventory;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

public abstract class Container {
   public List inventoryItemStacks = Lists.newArrayList();
   public List inventorySlots = Lists.newArrayList();
   public int windowId;
   private short transactionID;
   private int dragMode = -1;
   private int dragEvent;
   private final Set dragSlots = Sets.newHashSet();
   protected List crafters = Lists.newArrayList();
   private final Set playerList = Sets.newHashSet();

   protected Slot addSlotToContainer(Slot var1) {
      var1.slotNumber = this.inventorySlots.size();
      this.inventorySlots.add(var1);
      this.inventoryItemStacks.add((Object)null);
      return var1;
   }

   public void onCraftGuiOpened(ICrafting var1) {
      if(this.crafters.contains(var1)) {
         throw new IllegalArgumentException("Listener already listening");
      } else {
         this.crafters.add(var1);
         var1.updateCraftingInventory(this, this.getInventory());
         this.detectAndSendChanges();
      }
   }

   public void removeCraftingFromCrafters(ICrafting var1) {
      this.crafters.remove(var1);
   }

   public List getInventory() {
      ArrayList var1 = Lists.newArrayList();

      for(Slot var3 : this.inventorySlots) {
         var1.add(var3.getStack());
      }

      return var1;
   }

   public void detectAndSendChanges() {
      for(int var1 = 0; var1 < this.inventorySlots.size(); ++var1) {
         ItemStack var2 = ((Slot)this.inventorySlots.get(var1)).getStack();
         ItemStack var3 = (ItemStack)this.inventoryItemStacks.get(var1);
         if(!ItemStack.areItemStacksEqual(var3, var2)) {
            var3 = null;
            this.inventoryItemStacks.set(var1, var3);

            for(ICrafting var5 : this.crafters) {
               var5.sendSlotContents(this, var1, var3);
            }
         }
      }

   }

   public boolean enchantItem(EntityPlayer var1, int var2) {
      return false;
   }

   public Slot getSlotFromInventory(IInventory var1, int var2) {
      for(Slot var4 : this.inventorySlots) {
         if(var4.isHere(var1, var2)) {
            return var4;
         }
      }

      return null;
   }

   public Slot getSlot(int var1) {
      return (Slot)this.inventorySlots.get(var1);
   }

   public ItemStack transferStackInSlot(EntityPlayer var1, int var2) {
      Slot var3 = (Slot)this.inventorySlots.get(var2);
      return var3.getStack();
   }

   public ItemStack slotClick(int var1, int var2, int var3, EntityPlayer var4) {
      Object var5 = null;
      InventoryPlayer var6 = var4.inventory;
      if(var3 == 5) {
         int var7 = this.dragEvent;
         this.dragEvent = getDragEvent(var2);
         if((var7 != 1 || this.dragEvent != 2) && var7 != this.dragEvent) {
            this.resetDrag();
         } else if(var6.getItemStack() == null) {
            this.resetDrag();
         } else if(this.dragEvent == 0) {
            this.dragMode = extractDragMode(var2);
            if(isValidDragMode(this.dragMode, var4)) {
               this.dragEvent = 1;
               this.dragSlots.clear();
            } else {
               this.resetDrag();
            }
         } else if(this.dragEvent == 1) {
            Slot var8 = (Slot)this.inventorySlots.get(var1);
            if(canAddItemToSlot(var8, var6.getItemStack(), true) && var8.isItemValid(var6.getItemStack()) && var6.getItemStack().stackSize > this.dragSlots.size() && this.canDragIntoSlot(var8)) {
               this.dragSlots.add(var8);
            }
         } else if(this.dragEvent == 2) {
            if(!this.dragSlots.isEmpty()) {
               ItemStack var20 = var6.getItemStack().copy();
               int var9 = var6.getItemStack().stackSize;

               for(Slot var11 : this.dragSlots) {
                  if(canAddItemToSlot(var11, var6.getItemStack(), true) && var11.isItemValid(var6.getItemStack()) && var6.getItemStack().stackSize >= this.dragSlots.size() && this.canDragIntoSlot(var11)) {
                     ItemStack var12 = var20.copy();
                     int var13 = var11.getHasStack()?var11.getStack().stackSize:0;
                     computeStackSize(this.dragSlots, this.dragMode, var12, var13);
                     if(var12.stackSize > var12.getMaxStackSize()) {
                        var12.stackSize = var12.getMaxStackSize();
                     }

                     if(var12.stackSize > var11.getItemStackLimit(var12)) {
                        var12.stackSize = var11.getItemStackLimit(var12);
                     }

                     var9 -= var12.stackSize - var13;
                     var11.putStack(var12);
                  }
               }

               var20.stackSize = var9;
               if(var20.stackSize <= 0) {
                  var20 = null;
               }

               var6.setItemStack(var20);
            }

            this.resetDrag();
         } else {
            this.resetDrag();
         }
      } else if(this.dragEvent != 0) {
         this.resetDrag();
      } else if(var3 == 1 && var2 == 1) {
         if(var1 != -999) {
            if(var3 == 1) {
               return null;
            }

            return null;
         }

         if(var6.getItemStack() != null) {
            var4.dropPlayerItemWithRandomChoice(var6.getItemStack(), true);
            var6.setItemStack((ItemStack)null);
            if(var2 == 1) {
               var4.dropPlayerItemWithRandomChoice(var6.getItemStack().splitStack(1), true);
               if(var6.getItemStack().stackSize == 0) {
                  var6.setItemStack((ItemStack)null);
               }
            }
         }
      } else if(var3 == 2 && var2 < 9) {
         Slot var19 = (Slot)this.inventorySlots.get(var1);
         if(var19.canTakeStack(var4)) {
            ItemStack var24 = var6.getStackInSlot(var2);
            boolean var26 = var19.inventory == var6 && var19.isItemValid(var24);
            int var29 = -1;
            var29 = var6.getFirstEmptyStack();
            var26 = var26 | var29 > -1;
            if(var19.getHasStack()) {
               ItemStack var32 = var19.getStack();
               var6.setInventorySlotContents(var2, var32.copy());
               if(var19.inventory == var6 && var19.isItemValid(var24)) {
                  var19.decrStackSize(var32.stackSize);
                  var19.putStack(var24);
                  var19.onPickupFromSlot(var4, var32);
               } else if(var29 > -1) {
                  var6.addItemStackToInventory(var24);
                  var19.decrStackSize(var32.stackSize);
                  var19.putStack((ItemStack)null);
                  var19.onPickupFromSlot(var4, var32);
               }
            } else if(!var19.getHasStack() && var19.isItemValid(var24)) {
               var6.setInventorySlotContents(var2, (ItemStack)null);
               var19.putStack(var24);
            }
         }
      } else if(var3 == 3 && var4.abilities.isCreative() && var6.getItemStack() == null) {
         Slot var18 = (Slot)this.inventorySlots.get(var1);
         if(var18.getHasStack()) {
            ItemStack var23 = var18.getStack().copy();
            var23.stackSize = var23.getMaxStackSize();
            var6.setItemStack(var23);
         }
      } else if(var3 == 4 && var6.getItemStack() == null) {
         Slot var17 = (Slot)this.inventorySlots.get(var1);
         if(var17.getHasStack() && var17.canTakeStack(var4)) {
            ItemStack var22 = var17.decrStackSize(1);
            var17.onPickupFromSlot(var4, var22);
            var4.dropPlayerItemWithRandomChoice(var22, true);
         }
      } else if(var3 == 6) {
         Slot var16 = (Slot)this.inventorySlots.get(var1);
         ItemStack var21 = var6.getItemStack();
         if(!var16.getHasStack() || !var16.canTakeStack(var4)) {
            byte var25 = 0;
            byte var28 = 1;

            for(int var31 = 0; var31 < 2; ++var31) {
               for(int var33 = var25; var33 < this.inventorySlots.size() && var21.stackSize < var21.getMaxStackSize(); var33 += var28) {
                  Slot var34 = (Slot)this.inventorySlots.get(var33);
                  if(var34.getHasStack() && canAddItemToSlot(var34, var21, true) && var34.canTakeStack(var4) && this.canMergeSlot(var21, var34) && var34.getStack().stackSize != var34.getStack().getMaxStackSize()) {
                     int var14 = Math.min(var21.getMaxStackSize() - var21.stackSize, var34.getStack().stackSize);
                     ItemStack var15 = var34.decrStackSize(var14);
                     var21.stackSize += var14;
                     if(var15.stackSize <= 0) {
                        var34.putStack((ItemStack)null);
                     }

                     var34.onPickupFromSlot(var4, var15);
                  }
               }
            }
         }

         this.detectAndSendChanges();
      }

      return (ItemStack)var5;
   }

   public boolean canMergeSlot(ItemStack var1, Slot var2) {
      return true;
   }

   protected void retrySlotClick(int var1, int var2, boolean var3, EntityPlayer var4) {
      this.slotClick(var1, var2, 1, var4);
   }

   public void onContainerClosed(EntityPlayer var1) {
      InventoryPlayer var2 = var1.inventory;
      if(var2.getItemStack() != null) {
         var1.dropPlayerItemWithRandomChoice(var2.getItemStack(), false);
         var2.setItemStack((ItemStack)null);
      }

   }

   public void onCraftMatrixChanged(IInventory var1) {
      this.detectAndSendChanges();
   }

   public void putStackInSlot(int var1, ItemStack var2) {
      this.getSlot(var1).putStack(var2);
   }

   public void putStacksInSlots(ItemStack[] var1) {
      for(int var2 = 0; var2 < var1.length; ++var2) {
         this.getSlot(var2).putStack(var1[var2]);
      }

   }

   public void updateProgressBar(int var1, int var2) {
   }

   public short d() {
      ++this.transactionID;
      return this.transactionID;
   }

   public boolean getCanCraft(EntityPlayer var1) {
      return !this.playerList.contains(var1);
   }

   public void setCanCraft(EntityPlayer var1, boolean var2) {
      this.playerList.remove(var1);
   }

   public abstract boolean canInteractWith(EntityPlayer var1);

   protected boolean mergeItemStack(ItemStack var1, int var2, int var3, boolean var4) {
      boolean var5 = false;
      int var6 = var3 - 1;
      if(var1.isStackable()) {
         for(; var1.stackSize > 0 && (var6 < var3 || var6 >= var2); --var6) {
            Slot var7 = (Slot)this.inventorySlots.get(var6);
            ItemStack var8 = var7.getStack();
            if(var8.getItem() == var1.getItem() && (!var1.getHasSubtypes() || var1.getMetadata() == var8.getMetadata()) && ItemStack.areItemStackTagsEqual(var1, var8)) {
               int var9 = var8.stackSize + var1.stackSize;
               if(var9 <= var1.getMaxStackSize()) {
                  var1.stackSize = 0;
                  var8.stackSize = var9;
                  var7.onSlotChanged();
                  var5 = true;
               } else if(var8.stackSize < var1.getMaxStackSize()) {
                  var1.stackSize -= var1.getMaxStackSize() - var8.stackSize;
                  var8.stackSize = var1.getMaxStackSize();
                  var7.onSlotChanged();
                  var5 = true;
               }
            }
         }
      }

      if(var1.stackSize > 0) {
         var6 = var3 - 1;
         if(var6 < var3 || var6 >= var2) {
            Slot var11 = (Slot)this.inventorySlots.get(var6);
            ItemStack var12 = var11.getStack();
            var11.putStack(var1.copy());
            var11.onSlotChanged();
            var1.stackSize = 0;
            var5 = true;
         }
      }

      return var5;
   }

   public static int extractDragMode(int var0) {
      return var0 >> 2 & 3;
   }

   public static int getDragEvent(int var0) {
      return var0 & 3;
   }

   public static int func_94534_d(int var0, int var1) {
      return var0 & 3 | (var1 & 3) << 2;
   }

   public static boolean isValidDragMode(int var0, EntityPlayer var1) {
      return var0 == 1 || var0 == 2 && var1.abilities.isCreative();
   }

   protected void resetDrag() {
      this.dragEvent = 0;
      this.dragSlots.clear();
   }

   public static boolean canAddItemToSlot(Slot var0, ItemStack var1, boolean var2) {
      boolean var3 = !var0.getHasStack();
      if(var0.getHasStack() && var1.isItemEqual(var0.getStack()) && ItemStack.areItemStackTagsEqual(var0.getStack(), var1)) {
         var3 |= var0.getStack().stackSize + 0 <= var1.getMaxStackSize();
      }

      return var3;
   }

   public static void computeStackSize(Set var0, int var1, ItemStack var2, int var3) {
      switch(var1) {
      case 0:
         var2.stackSize = MathHelper.floor_float((float)var2.stackSize / (float)var0.size());
         break;
      case 1:
         var2.stackSize = 1;
         break;
      case 2:
         var2.stackSize = var2.getItem().getItemStackLimit();
      }

      var2.stackSize += var3;
   }

   public boolean canDragIntoSlot(Slot var1) {
      return true;
   }

   public static int calcRedstone(TileEntity var0) {
      return var0 instanceof IInventory?a((IInventory)var0):0;
   }

   public static int a(IInventory var0) {
      return 0;
   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}
