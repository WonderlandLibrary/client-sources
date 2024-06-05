package net.minecraft.inventory;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

public abstract class Container {
   private final Set dragSlots = Sets.newHashSet();
   public List inventoryItemStacks = Lists.newArrayList();
   private int dragEvent;
   private short transactionID;
   public List inventorySlots = Lists.newArrayList();
   protected List crafters = Lists.newArrayList();
   private int dragMode = -1;
   public int windowId;
   private Set playerList = Sets.newHashSet();
   private static final String __OBFID = "CL_00001730";

   public void removeCraftingFromCrafters(ICrafting var1) {
      this.crafters.remove(var1);
   }

   public static int calcRedstoneFromInventory(TileEntity var0) {
      return var0 instanceof IInventory ? calcRedstoneFromInventory((IInventory)var0) : 0;
   }

   public static boolean func_180610_a(int var0, EntityPlayer var1) {
      return var0 == 0 ? true : (var0 == 1 ? true : var0 == 2 && var1.capabilities.isCreativeMode);
   }

   public boolean canDragIntoSlot(Slot var1) {
      return true;
   }

   public ItemStack slotClick(int var1, int var2, int var3, EntityPlayer var4) {
      ItemStack var5 = null;
      InventoryPlayer var6 = var4.inventory;
      int var7;
      ItemStack var8;
      ItemStack var12;
      int var13;
      if (var3 == 5) {
         int var9 = this.dragEvent;
         this.dragEvent = getDragEvent(var2);
         if ((var9 != 1 || this.dragEvent != 2) && var9 != this.dragEvent) {
            this.resetDrag();
         } else if (var6.getItemStack() == null) {
            this.resetDrag();
         } else if (this.dragEvent == 0) {
            this.dragMode = extractDragMode(var2);
            if (func_180610_a(this.dragMode, var4)) {
               this.dragEvent = 1;
               this.dragSlots.clear();
            } else {
               this.resetDrag();
            }
         } else if (this.dragEvent == 1) {
            Slot var10 = (Slot)this.inventorySlots.get(var1);
            if (var10 != null && canAddItemToSlot(var10, var6.getItemStack(), true) && var10.isItemValid(var6.getItemStack()) && var6.getItemStack().stackSize > this.dragSlots.size() && this.canDragIntoSlot(var10)) {
               this.dragSlots.add(var10);
            }
         } else if (this.dragEvent == 2) {
            if (!this.dragSlots.isEmpty()) {
               var8 = var6.getItemStack().copy();
               var7 = var6.getItemStack().stackSize;
               Iterator var18 = this.dragSlots.iterator();

               while(var18.hasNext()) {
                  Slot var11 = (Slot)var18.next();
                  if (var11 != null && canAddItemToSlot(var11, var6.getItemStack(), true) && var11.isItemValid(var6.getItemStack()) && var6.getItemStack().stackSize >= this.dragSlots.size() && this.canDragIntoSlot(var11)) {
                     var12 = var8.copy();
                     var13 = var11.getHasStack() ? var11.getStack().stackSize : 0;
                     computeStackSize(this.dragSlots, this.dragMode, var12, var13);
                     if (var12.stackSize > var12.getMaxStackSize()) {
                        var12.stackSize = var12.getMaxStackSize();
                     }

                     if (var12.stackSize > var11.func_178170_b(var12)) {
                        var12.stackSize = var11.func_178170_b(var12);
                     }

                     var7 -= var12.stackSize - var13;
                     var11.putStack(var12);
                  }
               }

               var8.stackSize = var7;
               if (var8.stackSize <= 0) {
                  var8 = null;
               }

               var6.setItemStack(var8);
            }

            this.resetDrag();
         } else {
            this.resetDrag();
         }
      } else if (this.dragEvent != 0) {
         this.resetDrag();
      } else {
         Slot var17;
         int var19;
         ItemStack var20;
         if ((var3 == 0 || var3 == 1) && (var2 == 0 || var2 == 1)) {
            if (var1 == -999) {
               if (var6.getItemStack() != null) {
                  if (var2 == 0) {
                     var4.dropPlayerItemWithRandomChoice(var6.getItemStack(), true);
                     var6.setItemStack((ItemStack)null);
                  }

                  if (var2 == 1) {
                     var4.dropPlayerItemWithRandomChoice(var6.getItemStack().splitStack(1), true);
                     if (var6.getItemStack().stackSize == 0) {
                        var6.setItemStack((ItemStack)null);
                     }
                  }
               }
            } else if (var3 == 1) {
               if (var1 < 0) {
                  return null;
               }

               var17 = (Slot)this.inventorySlots.get(var1);
               if (var17 != null && var17.canTakeStack(var4)) {
                  var8 = this.transferStackInSlot(var4, var1);
                  if (var8 != null) {
                     Item var23 = var8.getItem();
                     var5 = var8.copy();
                     if (var17.getStack() != null && var17.getStack().getItem() == var23) {
                        this.retrySlotClick(var1, var2, true, var4);
                     }
                  }
               }
            } else {
               if (var1 < 0) {
                  return null;
               }

               var17 = (Slot)this.inventorySlots.get(var1);
               if (var17 != null) {
                  var8 = var17.getStack();
                  var12 = var6.getItemStack();
                  if (var8 != null) {
                     var5 = var8.copy();
                  }

                  if (var8 == null) {
                     if (var12 != null && var17.isItemValid(var12)) {
                        var19 = var2 == 0 ? var12.stackSize : 1;
                        if (var19 > var17.func_178170_b(var12)) {
                           var19 = var17.func_178170_b(var12);
                        }

                        if (var12.stackSize >= var19) {
                           var17.putStack(var12.splitStack(var19));
                        }

                        if (var12.stackSize == 0) {
                           var6.setItemStack((ItemStack)null);
                        }
                     }
                  } else if (var17.canTakeStack(var4)) {
                     if (var12 == null) {
                        var19 = var2 == 0 ? var8.stackSize : (var8.stackSize + 1) / 2;
                        var20 = var17.decrStackSize(var19);
                        var6.setItemStack(var20);
                        if (var8.stackSize == 0) {
                           var17.putStack((ItemStack)null);
                        }

                        var17.onPickupFromSlot(var4, var6.getItemStack());
                     } else if (var17.isItemValid(var12)) {
                        if (var8.getItem() == var12.getItem() && var8.getMetadata() == var12.getMetadata() && ItemStack.areItemStackTagsEqual(var8, var12)) {
                           var19 = var2 == 0 ? var12.stackSize : 1;
                           if (var19 > var17.func_178170_b(var12) - var8.stackSize) {
                              var19 = var17.func_178170_b(var12) - var8.stackSize;
                           }

                           if (var19 > var12.getMaxStackSize() - var8.stackSize) {
                              var19 = var12.getMaxStackSize() - var8.stackSize;
                           }

                           var12.splitStack(var19);
                           if (var12.stackSize == 0) {
                              var6.setItemStack((ItemStack)null);
                           }

                           var8.stackSize += var19;
                        } else if (var12.stackSize <= var17.func_178170_b(var12)) {
                           var17.putStack(var12);
                           var6.setItemStack(var8);
                        }
                     } else if (var8.getItem() == var12.getItem() && var12.getMaxStackSize() > 1 && (!var8.getHasSubtypes() || var8.getMetadata() == var12.getMetadata()) && ItemStack.areItemStackTagsEqual(var8, var12)) {
                        var19 = var8.stackSize;
                        if (var19 > 0 && var19 + var12.stackSize <= var12.getMaxStackSize()) {
                           var12.stackSize += var19;
                           var8 = var17.decrStackSize(var19);
                           if (var8.stackSize == 0) {
                              var17.putStack((ItemStack)null);
                           }

                           var17.onPickupFromSlot(var4, var6.getItemStack());
                        }
                     }
                  }

                  var17.onSlotChanged();
               }
            }
         } else if (var3 == 2 && var2 >= 0 && var2 < 9) {
            var17 = (Slot)this.inventorySlots.get(var1);
            if (var17.canTakeStack(var4)) {
               var8 = var6.getStackInSlot(var2);
               boolean var22 = var8 == null || var17.inventory == var6 && var17.isItemValid(var8);
               var19 = -1;
               if (!var22) {
                  var19 = var6.getFirstEmptyStack();
                  var22 |= var19 > -1;
               }

               if (var17.getHasStack() && var22) {
                  var20 = var17.getStack();
                  var6.setInventorySlotContents(var2, var20.copy());
                  if ((var17.inventory != var6 || !var17.isItemValid(var8)) && var8 != null) {
                     if (var19 > -1) {
                        var6.addItemStackToInventory(var8);
                        var17.decrStackSize(var20.stackSize);
                        var17.putStack((ItemStack)null);
                        var17.onPickupFromSlot(var4, var20);
                     }
                  } else {
                     var17.decrStackSize(var20.stackSize);
                     var17.putStack(var8);
                     var17.onPickupFromSlot(var4, var20);
                  }
               } else if (!var17.getHasStack() && var8 != null && var17.isItemValid(var8)) {
                  var6.setInventorySlotContents(var2, (ItemStack)null);
                  var17.putStack(var8);
               }
            }
         } else if (var3 == 3 && var4.capabilities.isCreativeMode && var6.getItemStack() == null && var1 >= 0) {
            var17 = (Slot)this.inventorySlots.get(var1);
            if (var17 != null && var17.getHasStack()) {
               var8 = var17.getStack().copy();
               var8.stackSize = var8.getMaxStackSize();
               var6.setItemStack(var8);
            }
         } else if (var3 == 4 && var6.getItemStack() == null && var1 >= 0) {
            var17 = (Slot)this.inventorySlots.get(var1);
            if (var17 != null && var17.getHasStack() && var17.canTakeStack(var4)) {
               var8 = var17.decrStackSize(var2 == 0 ? 1 : var17.getStack().stackSize);
               var17.onPickupFromSlot(var4, var8);
               var4.dropPlayerItemWithRandomChoice(var8, true);
            }
         } else if (var3 == 6 && var1 >= 0) {
            var17 = (Slot)this.inventorySlots.get(var1);
            var8 = var6.getItemStack();
            if (var8 != null && (var17 == null || !var17.getHasStack() || !var17.canTakeStack(var4))) {
               var7 = var2 == 0 ? 0 : this.inventorySlots.size() - 1;
               var19 = var2 == 0 ? 1 : -1;

               for(int var21 = 0; var21 < 2; ++var21) {
                  for(var13 = var7; var13 >= 0 && var13 < this.inventorySlots.size() && var8.stackSize < var8.getMaxStackSize(); var13 += var19) {
                     Slot var14 = (Slot)this.inventorySlots.get(var13);
                     if (var14.getHasStack() && canAddItemToSlot(var14, var8, true) && var14.canTakeStack(var4) && this.func_94530_a(var8, var14) && (var21 != 0 || var14.getStack().stackSize != var14.getStack().getMaxStackSize())) {
                        int var15 = Math.min(var8.getMaxStackSize() - var8.stackSize, var14.getStack().stackSize);
                        ItemStack var16 = var14.decrStackSize(var15);
                        var8.stackSize += var15;
                        if (var16.stackSize <= 0) {
                           var14.putStack((ItemStack)null);
                        }

                        var14.onPickupFromSlot(var4, var16);
                     }
                  }
               }
            }

            this.detectAndSendChanges();
         }
      }

      return var5;
   }

   protected Slot addSlotToContainer(Slot var1) {
      var1.slotNumber = this.inventorySlots.size();
      this.inventorySlots.add(var1);
      this.inventoryItemStacks.add((Object)null);
      return var1;
   }

   public boolean enchantItem(EntityPlayer var1, int var2) {
      return false;
   }

   public static int calcRedstoneFromInventory(IInventory var0) {
      if (var0 == null) {
         return 0;
      } else {
         int var1 = 0;
         float var2 = 0.0F;

         for(int var3 = 0; var3 < var0.getSizeInventory(); ++var3) {
            ItemStack var4 = var0.getStackInSlot(var3);
            if (var4 != null) {
               var2 += (float)var4.stackSize / (float)Math.min(var0.getInventoryStackLimit(), var4.getMaxStackSize());
               ++var1;
            }
         }

         var2 /= (float)var0.getSizeInventory();
         return MathHelper.floor_float(var2 * 14.0F) + (var1 > 0 ? 1 : 0);
      }
   }

   public void detectAndSendChanges() {
      for(int var1 = 0; var1 < this.inventorySlots.size(); ++var1) {
         ItemStack var2 = ((Slot)this.inventorySlots.get(var1)).getStack();
         ItemStack var3 = (ItemStack)this.inventoryItemStacks.get(var1);
         if (!ItemStack.areItemStacksEqual(var3, var2)) {
            var3 = var2 == null ? null : var2.copy();
            this.inventoryItemStacks.set(var1, var3);

            for(int var4 = 0; var4 < this.crafters.size(); ++var4) {
               ((ICrafting)this.crafters.get(var4)).sendSlotContents(this, var1, var3);
            }
         }
      }

   }

   public abstract boolean canInteractWith(EntityPlayer var1);

   public Slot getSlot(int var1) {
      return (Slot)this.inventorySlots.get(var1);
   }

   public static int func_94534_d(int var0, int var1) {
      return var0 & 3 | (var1 & 3) << 2;
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

   public void onCraftGuiOpened(ICrafting var1) {
      if (this.crafters.contains(var1)) {
         throw new IllegalArgumentException("Listener already listening");
      } else {
         this.crafters.add(var1);
         var1.updateCraftingInventory(this, this.getInventory());
         this.detectAndSendChanges();
      }
   }

   protected boolean mergeItemStack(ItemStack var1, int var2, int var3, boolean var4) {
      boolean var5 = false;
      int var6 = var2;
      if (var4) {
         var6 = var3 - 1;
      }

      Slot var7;
      ItemStack var8;
      if (var1.isStackable()) {
         while(var1.stackSize > 0 && (!var4 && var6 < var3 || var4 && var6 >= var2)) {
            var7 = (Slot)this.inventorySlots.get(var6);
            var8 = var7.getStack();
            if (var8 != null && var8.getItem() == var1.getItem() && (!var1.getHasSubtypes() || var1.getMetadata() == var8.getMetadata()) && ItemStack.areItemStackTagsEqual(var1, var8)) {
               int var9 = var8.stackSize + var1.stackSize;
               if (var9 <= var1.getMaxStackSize()) {
                  var1.stackSize = 0;
                  var8.stackSize = var9;
                  var7.onSlotChanged();
                  var5 = true;
               } else if (var8.stackSize < var1.getMaxStackSize()) {
                  var1.stackSize -= var1.getMaxStackSize() - var8.stackSize;
                  var8.stackSize = var1.getMaxStackSize();
                  var7.onSlotChanged();
                  var5 = true;
               }
            }

            if (var4) {
               --var6;
            } else {
               ++var6;
            }
         }
      }

      if (var1.stackSize > 0) {
         if (var4) {
            var6 = var3 - 1;
         } else {
            var6 = var2;
         }

         while(!var4 && var6 < var3 || var4 && var6 >= var2) {
            var7 = (Slot)this.inventorySlots.get(var6);
            var8 = var7.getStack();
            if (var8 == null) {
               var7.putStack(var1.copy());
               var7.onSlotChanged();
               var1.stackSize = 0;
               var5 = true;
               break;
            }

            if (var4) {
               --var6;
            } else {
               ++var6;
            }
         }
      }

      return var5;
   }

   public ItemStack transferStackInSlot(EntityPlayer var1, int var2) {
      Slot var3 = (Slot)this.inventorySlots.get(var2);
      return var3 != null ? var3.getStack() : null;
   }

   public void putStackInSlot(int var1, ItemStack var2) {
      this.getSlot(var1).putStack(var2);
   }

   protected void resetDrag() {
      this.dragEvent = 0;
      this.dragSlots.clear();
   }

   public void setCanCraft(EntityPlayer var1, boolean var2) {
      if (var2) {
         this.playerList.remove(var1);
      } else {
         this.playerList.add(var1);
      }

   }

   public boolean getCanCraft(EntityPlayer var1) {
      return !this.playerList.contains(var1);
   }

   public void putStacksInSlots(ItemStack[] var1) {
      for(int var2 = 0; var2 < var1.length; ++var2) {
         this.getSlot(var2).putStack(var1[var2]);
      }

   }

   public short getNextTransactionID(InventoryPlayer var1) {
      ++this.transactionID;
      return this.transactionID;
   }

   public static int extractDragMode(int var0) {
      return var0 >> 2 & 3;
   }

   public Slot getSlotFromInventory(IInventory var1, int var2) {
      for(int var3 = 0; var3 < this.inventorySlots.size(); ++var3) {
         Slot var4 = (Slot)this.inventorySlots.get(var3);
         if (var4.isHere(var1, var2)) {
            return var4;
         }
      }

      return null;
   }

   public List getInventory() {
      ArrayList var1 = Lists.newArrayList();

      for(int var2 = 0; var2 < this.inventorySlots.size(); ++var2) {
         var1.add(((Slot)this.inventorySlots.get(var2)).getStack());
      }

      return var1;
   }

   public void onCraftMatrixChanged(IInventory var1) {
      this.detectAndSendChanges();
   }

   public static boolean canAddItemToSlot(Slot var0, ItemStack var1, boolean var2) {
      boolean var3 = var0 == null || !var0.getHasStack();
      if (var0 != null && var0.getHasStack() && var1 != null && var1.isItemEqual(var0.getStack()) && ItemStack.areItemStackTagsEqual(var0.getStack(), var1)) {
         int var4 = var2 ? 0 : var1.stackSize;
         var3 |= var0.getStack().stackSize + var4 <= var1.getMaxStackSize();
      }

      return var3;
   }

   public void onContainerClosed(EntityPlayer var1) {
      InventoryPlayer var2 = var1.inventory;
      if (var2.getItemStack() != null) {
         var1.dropPlayerItemWithRandomChoice(var2.getItemStack(), false);
         var2.setItemStack((ItemStack)null);
      }

   }

   protected void retrySlotClick(int var1, int var2, boolean var3, EntityPlayer var4) {
      this.slotClick(var1, var2, 1, var4);
   }

   public static int getDragEvent(int var0) {
      return var0 & 3;
   }

   public void updateProgressBar(int var1, int var2) {
   }

   public boolean func_94530_a(ItemStack var1, Slot var2) {
      return true;
   }
}
