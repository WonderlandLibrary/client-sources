package net.minecraft.inventory;

import java.util.Iterator;
import java.util.Map;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ContainerRepair extends Container {
   private int materialCost;
   private BlockPos field_178156_j;
   public int maximumCost;
   private final EntityPlayer thePlayer;
   private IInventory inputSlots;
   private IInventory outputSlot;
   private static final Logger logger = LogManager.getLogger();
   private static final String __OBFID = "CL_00001732";
   private String repairedItemName;
   private World theWorld;

   public ContainerRepair(InventoryPlayer var1, World var2, EntityPlayer var3) {
      this(var1, var2, BlockPos.ORIGIN, var3);
   }

   public boolean canInteractWith(EntityPlayer var1) {
      return this.theWorld.getBlockState(this.field_178156_j).getBlock() != Blocks.anvil ? false : var1.getDistanceSq((double)this.field_178156_j.getX() + 0.5D, (double)this.field_178156_j.getY() + 0.5D, (double)this.field_178156_j.getZ() + 0.5D) <= 64.0D;
   }

   public void onContainerClosed(EntityPlayer var1) {
      super.onContainerClosed(var1);
      if (!this.theWorld.isRemote) {
         for(int var2 = 0; var2 < this.inputSlots.getSizeInventory(); ++var2) {
            ItemStack var3 = this.inputSlots.getStackInSlotOnClosing(var2);
            if (var3 != null) {
               var1.dropPlayerItemWithRandomChoice(var3, false);
            }
         }
      }

   }

   public void onCraftGuiOpened(ICrafting var1) {
      super.onCraftGuiOpened(var1);
      var1.sendProgressBarUpdate(this, 0, this.maximumCost);
   }

   public void updateRepairOutput() {
      boolean var1 = false;
      boolean var2 = true;
      boolean var3 = true;
      boolean var4 = true;
      boolean var5 = true;
      boolean var6 = true;
      boolean var7 = true;
      ItemStack var8 = this.inputSlots.getStackInSlot(0);
      this.maximumCost = 1;
      int var9 = 0;
      byte var10 = 0;
      byte var11 = 0;
      if (var8 == null) {
         this.outputSlot.setInventorySlotContents(0, (ItemStack)null);
         this.maximumCost = 0;
      } else {
         ItemStack var12 = var8.copy();
         ItemStack var13 = this.inputSlots.getStackInSlot(1);
         Map var14 = EnchantmentHelper.getEnchantments(var12);
         boolean var15 = false;
         int var16 = var10 + var8.getRepairCost() + (var13 == null ? 0 : var13.getRepairCost());
         this.materialCost = 0;
         int var17;
         if (var13 != null) {
            var15 = var13.getItem() == Items.enchanted_book && Items.enchanted_book.func_92110_g(var13).tagCount() > 0;
            int var18;
            int var19;
            if (var12.isItemStackDamageable() && var12.getItem().getIsRepairable(var8, var13)) {
               var17 = Math.min(var12.getItemDamage(), var12.getMaxDamage() / 4);
               if (var17 <= 0) {
                  this.outputSlot.setInventorySlotContents(0, (ItemStack)null);
                  this.maximumCost = 0;
                  return;
               }

               for(var18 = 0; var17 > 0 && var18 < var13.stackSize; ++var18) {
                  var19 = var12.getItemDamage() - var17;
                  var12.setItemDamage(var19);
                  ++var9;
                  var17 = Math.min(var12.getItemDamage(), var12.getMaxDamage() / 4);
               }

               this.materialCost = var18;
            } else {
               if (!var15 && (var12.getItem() != var13.getItem() || !var12.isItemStackDamageable())) {
                  this.outputSlot.setInventorySlotContents(0, (ItemStack)null);
                  this.maximumCost = 0;
                  return;
               }

               int var20;
               if (var12.isItemStackDamageable() && !var15) {
                  var17 = var8.getMaxDamage() - var8.getItemDamage();
                  var18 = var13.getMaxDamage() - var13.getItemDamage();
                  var19 = var18 + var12.getMaxDamage() * 12 / 100;
                  int var21 = var17 + var19;
                  var20 = var12.getMaxDamage() - var21;
                  if (var20 < 0) {
                     var20 = 0;
                  }

                  if (var20 < var12.getMetadata()) {
                     var12.setItemDamage(var20);
                     var9 += 2;
                  }
               }

               Map var29 = EnchantmentHelper.getEnchantments(var13);
               Iterator var22 = var29.keySet().iterator();

               label190:
               while(true) {
                  Enchantment var23;
                  do {
                     if (!var22.hasNext()) {
                        break label190;
                     }

                     var19 = (Integer)var22.next();
                     var23 = Enchantment.func_180306_c(var19);
                  } while(var23 == null);

                  var20 = var14.containsKey(var19) ? (Integer)var14.get(var19) : 0;
                  int var24 = (Integer)var29.get(var19);
                  int var25;
                  if (var20 == var24) {
                     ++var24;
                     var25 = var24;
                  } else {
                     var25 = Math.max(var24, var20);
                  }

                  var24 = var25;
                  boolean var26 = var23.canApply(var8);
                  if (this.thePlayer.capabilities.isCreativeMode || var8.getItem() == Items.enchanted_book) {
                     var26 = true;
                  }

                  Iterator var27 = var14.keySet().iterator();

                  int var28;
                  while(var27.hasNext()) {
                     var28 = (Integer)var27.next();
                     if (var28 != var19 && !var23.canApplyTogether(Enchantment.func_180306_c(var28))) {
                        var26 = false;
                        ++var9;
                     }
                  }

                  if (var26) {
                     if (var25 > var23.getMaxLevel()) {
                        var24 = var23.getMaxLevel();
                     }

                     var14.put(var19, var24);
                     var28 = 0;
                     switch(var23.getWeight()) {
                     case 1:
                        var28 = 8;
                        break;
                     case 2:
                        var28 = 4;
                     case 3:
                     case 4:
                     case 6:
                     case 7:
                     case 8:
                     case 9:
                     default:
                        break;
                     case 5:
                        var28 = 2;
                        break;
                     case 10:
                        var28 = 1;
                     }

                     if (var15) {
                        var28 = Math.max(1, var28 / 2);
                     }

                     var9 += var28 * var24;
                  }
               }
            }
         }

         if (StringUtils.isBlank(this.repairedItemName)) {
            if (var8.hasDisplayName()) {
               var11 = 1;
               var9 += var11;
               var12.clearCustomName();
            }
         } else if (!this.repairedItemName.equals(var8.getDisplayName())) {
            var11 = 1;
            var9 += var11;
            var12.setStackDisplayName(this.repairedItemName);
         }

         this.maximumCost = var16 + var9;
         if (var9 <= 0) {
            var12 = null;
         }

         if (var11 == var9 && var11 > 0 && this.maximumCost >= 40) {
            this.maximumCost = 39;
         }

         if (this.maximumCost >= 40 && !this.thePlayer.capabilities.isCreativeMode) {
            var12 = null;
         }

         if (var12 != null) {
            var17 = var12.getRepairCost();
            if (var13 != null && var17 < var13.getRepairCost()) {
               var17 = var13.getRepairCost();
            }

            var17 = var17 * 2 + 1;
            var12.setRepairCost(var17);
            EnchantmentHelper.setEnchantments(var14, var12);
         }

         this.outputSlot.setInventorySlotContents(0, var12);
         this.detectAndSendChanges();
      }

   }

   public ItemStack transferStackInSlot(EntityPlayer var1, int var2) {
      ItemStack var3 = null;
      Slot var4 = (Slot)this.inventorySlots.get(var2);
      if (var4 != null && var4.getHasStack()) {
         ItemStack var5 = var4.getStack();
         var3 = var5.copy();
         if (var2 == 2) {
            if (!this.mergeItemStack(var5, 3, 39, true)) {
               return null;
            }

            var4.onSlotChange(var5, var3);
         } else if (var2 != 0 && var2 != 1) {
            if (var2 >= 3 && var2 < 39 && !this.mergeItemStack(var5, 0, 2, false)) {
               return null;
            }
         } else if (!this.mergeItemStack(var5, 3, 39, false)) {
            return null;
         }

         if (var5.stackSize == 0) {
            var4.putStack((ItemStack)null);
         } else {
            var4.onSlotChanged();
         }

         if (var5.stackSize == var3.stackSize) {
            return null;
         }

         var4.onPickupFromSlot(var1, var5);
      }

      return var3;
   }

   public ContainerRepair(InventoryPlayer var1, World var2, BlockPos var3, EntityPlayer var4) {
      this.outputSlot = new InventoryCraftResult();
      this.inputSlots = new InventoryBasic(this, "Repair", true, 2) {
         final ContainerRepair this$0;
         private static final String __OBFID = "CL_00001733";

         public void markDirty() {
            super.markDirty();
            this.this$0.onCraftMatrixChanged(this);
         }

         {
            this.this$0 = var1;
         }
      };
      this.field_178156_j = var3;
      this.theWorld = var2;
      this.thePlayer = var4;
      this.addSlotToContainer(new Slot(this.inputSlots, 0, 27, 47));
      this.addSlotToContainer(new Slot(this.inputSlots, 1, 76, 47));
      this.addSlotToContainer(new Slot(this, this.outputSlot, 2, 134, 47, var2, var3) {
         private final BlockPos val$p_i45807_3_;
         private static final String __OBFID = "CL_00001734";
         private final World val$worldIn;
         final ContainerRepair this$0;

         public void onPickupFromSlot(EntityPlayer var1, ItemStack var2) {
            if (!var1.capabilities.isCreativeMode) {
               var1.addExperienceLevel(-this.this$0.maximumCost);
            }

            ContainerRepair.access$0(this.this$0).setInventorySlotContents(0, (ItemStack)null);
            if (ContainerRepair.access$1(this.this$0) > 0) {
               ItemStack var3 = ContainerRepair.access$0(this.this$0).getStackInSlot(1);
               if (var3 != null && var3.stackSize > ContainerRepair.access$1(this.this$0)) {
                  var3.stackSize -= ContainerRepair.access$1(this.this$0);
                  ContainerRepair.access$0(this.this$0).setInventorySlotContents(1, var3);
               } else {
                  ContainerRepair.access$0(this.this$0).setInventorySlotContents(1, (ItemStack)null);
               }
            } else {
               ContainerRepair.access$0(this.this$0).setInventorySlotContents(1, (ItemStack)null);
            }

            this.this$0.maximumCost = 0;
            IBlockState var5 = this.val$worldIn.getBlockState(this.val$p_i45807_3_);
            if (!var1.capabilities.isCreativeMode && !this.val$worldIn.isRemote && var5.getBlock() == Blocks.anvil && var1.getRNG().nextFloat() < 0.12F) {
               int var4 = (Integer)var5.getValue(BlockAnvil.DAMAGE);
               ++var4;
               if (var4 > 2) {
                  this.val$worldIn.setBlockToAir(this.val$p_i45807_3_);
                  this.val$worldIn.playAuxSFX(1020, this.val$p_i45807_3_, 0);
               } else {
                  this.val$worldIn.setBlockState(this.val$p_i45807_3_, var5.withProperty(BlockAnvil.DAMAGE, var4), 2);
                  this.val$worldIn.playAuxSFX(1021, this.val$p_i45807_3_, 0);
               }
            } else if (!this.val$worldIn.isRemote) {
               this.val$worldIn.playAuxSFX(1021, this.val$p_i45807_3_, 0);
            }

         }

         public boolean canTakeStack(EntityPlayer var1) {
            return (var1.capabilities.isCreativeMode || var1.experienceLevel >= this.this$0.maximumCost) && this.this$0.maximumCost > 0 && this.getHasStack();
         }

         {
            this.this$0 = var1;
            this.val$worldIn = var6;
            this.val$p_i45807_3_ = var7;
         }

         public boolean isItemValid(ItemStack var1) {
            return false;
         }
      });

      int var5;
      for(var5 = 0; var5 < 3; ++var5) {
         for(int var6 = 0; var6 < 9; ++var6) {
            this.addSlotToContainer(new Slot(var1, var6 + var5 * 9 + 9, 8 + var6 * 18, 84 + var5 * 18));
         }
      }

      for(var5 = 0; var5 < 9; ++var5) {
         this.addSlotToContainer(new Slot(var1, var5, 8 + var5 * 18, 142));
      }

   }

   static int access$1(ContainerRepair var0) {
      return var0.materialCost;
   }

   public void updateProgressBar(int var1, int var2) {
      if (var1 == 0) {
         this.maximumCost = var2;
      }

   }

   public void updateItemName(String var1) {
      this.repairedItemName = var1;
      if (this.getSlot(2).getHasStack()) {
         ItemStack var2 = this.getSlot(2).getStack();
         if (StringUtils.isBlank(var1)) {
            var2.clearCustomName();
         } else {
            var2.setStackDisplayName(this.repairedItemName);
         }
      }

      this.updateRepairOutput();
   }

   static IInventory access$0(ContainerRepair var0) {
      return var0.inputSlots;
   }

   public void onCraftMatrixChanged(IInventory var1) {
      super.onCraftMatrixChanged(var1);
      if (var1 == this.inputSlots) {
         this.updateRepairOutput();
      }

   }
}
