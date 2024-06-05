package net.minecraft.tileentity;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockHopper;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TileEntityHopper extends TileEntityLockable implements IUpdatePlayerListBox, IHopper {
   private String customName;
   private ItemStack[] inventory = new ItemStack[5];
   private int transferCooldown = -1;
   private static final String __OBFID = "CL_00000359";

   public int getSizeInventory() {
      return this.inventory.length;
   }

   public static ItemStack func_174918_a(IInventory var0, ItemStack var1, EnumFacing var2) {
      if (var0 instanceof ISidedInventory && var2 != null) {
         ISidedInventory var6 = (ISidedInventory)var0;
         int[] var7 = var6.getSlotsForFace(var2);

         for(int var5 = 0; var5 < var7.length && var1 != null && var1.stackSize > 0; ++var5) {
            var1 = func_174916_c(var0, var1, var7[var5], var2);
         }
      } else {
         int var3 = var0.getSizeInventory();

         for(int var4 = 0; var4 < var3 && var1 != null && var1.stackSize > 0; ++var4) {
            var1 = func_174916_c(var0, var1, var4, var2);
         }
      }

      if (var1 != null && var1.stackSize == 0) {
         var1 = null;
      }

      return var1;
   }

   private IInventory func_145895_l() {
      EnumFacing var1 = BlockHopper.func_176428_b(this.getBlockMetadata());
      return func_145893_b(this.getWorld(), (double)(this.pos.getX() + var1.getFrontOffsetX()), (double)(this.pos.getY() + var1.getFrontOffsetY()), (double)(this.pos.getZ() + var1.getFrontOffsetZ()));
   }

   private boolean func_145883_k() {
      IInventory var1 = this.func_145895_l();
      if (var1 == null) {
         return false;
      } else {
         EnumFacing var2 = BlockHopper.func_176428_b(this.getBlockMetadata()).getOpposite();
         if (this.func_174919_a(var1, var2)) {
            return false;
         } else {
            for(int var3 = 0; var3 < this.getSizeInventory(); ++var3) {
               if (this.getStackInSlot(var3) != null) {
                  ItemStack var4 = this.getStackInSlot(var3).copy();
                  ItemStack var5 = func_174918_a(var1, this.decrStackSize(var3, 1), var2);
                  if (var5 == null || var5.stackSize == 0) {
                     var1.markDirty();
                     return true;
                  }

                  this.setInventorySlotContents(var3, var4);
               }
            }

            return false;
         }
      }
   }

   public Container createContainer(InventoryPlayer var1, EntityPlayer var2) {
      return new ContainerHopper(var1, this, var2);
   }

   public void setCustomName(String var1) {
      this.customName = var1;
   }

   public void update() {
      if (this.worldObj != null && !this.worldObj.isRemote) {
         --this.transferCooldown;
         if (!this.isOnTransferCooldown()) {
            this.setTransferCooldown(0);
            this.func_145887_i();
         }
      }

   }

   private boolean func_152105_l() {
      ItemStack[] var1 = this.inventory;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         ItemStack var4 = var1[var3];
         if (var4 == null || var4.stackSize != var4.getMaxStackSize()) {
            return false;
         }
      }

      return true;
   }

   public int getInventoryStackLimit() {
      return 64;
   }

   public void setInventorySlotContents(int var1, ItemStack var2) {
      this.inventory[var1] = var2;
      if (var2 != null && var2.stackSize > this.getInventoryStackLimit()) {
         var2.stackSize = this.getInventoryStackLimit();
      }

   }

   private static boolean func_174920_a(IInventory var0, ItemStack var1, int var2, EnumFacing var3) {
      return !var0.isItemValidForSlot(var2, var1) ? false : !(var0 instanceof ISidedInventory) || ((ISidedInventory)var0).canInsertItem(var2, var1, var3);
   }

   public int getField(int var1) {
      return 0;
   }

   public void writeToNBT(NBTTagCompound var1) {
      super.writeToNBT(var1);
      NBTTagList var2 = new NBTTagList();

      for(int var3 = 0; var3 < this.inventory.length; ++var3) {
         if (this.inventory[var3] != null) {
            NBTTagCompound var4 = new NBTTagCompound();
            var4.setByte("Slot", (byte)var3);
            this.inventory[var3].writeToNBT(var4);
            var2.appendTag(var4);
         }
      }

      var1.setTag("Items", var2);
      var1.setInteger("TransferCooldown", this.transferCooldown);
      if (this.hasCustomName()) {
         var1.setString("CustomName", this.customName);
      }

   }

   public void setTransferCooldown(int var1) {
      this.transferCooldown = var1;
   }

   private static ItemStack func_174916_c(IInventory var0, ItemStack var1, int var2, EnumFacing var3) {
      ItemStack var4 = var0.getStackInSlot(var2);
      if (func_174920_a(var0, var1, var2, var3)) {
         boolean var5 = false;
         if (var4 == null) {
            var0.setInventorySlotContents(var2, var1);
            var1 = null;
            var5 = true;
         } else if (canCombine(var4, var1)) {
            int var6 = var1.getMaxStackSize() - var4.stackSize;
            int var7 = Math.min(var1.stackSize, var6);
            var1.stackSize -= var7;
            var4.stackSize += var7;
            var5 = var7 > 0;
         }

         if (var5) {
            if (var0 instanceof TileEntityHopper) {
               TileEntityHopper var8 = (TileEntityHopper)var0;
               if (var8.mayTransfer()) {
                  var8.setTransferCooldown(8);
               }

               var0.markDirty();
            }

            var0.markDirty();
         }
      }

      return var1;
   }

   public boolean func_145887_i() {
      if (this.worldObj != null && !this.worldObj.isRemote) {
         if (!this.isOnTransferCooldown() && BlockHopper.getActiveStateFromMetadata(this.getBlockMetadata())) {
            boolean var1 = false;
            if (!this.func_152104_k()) {
               var1 = this.func_145883_k();
            }

            if (!this.func_152105_l()) {
               var1 = func_145891_a(this) || var1;
            }

            if (var1) {
               this.setTransferCooldown(8);
               this.markDirty();
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public boolean hasCustomName() {
      return this.customName != null && this.customName.length() > 0;
   }

   public double getYPos() {
      return (double)this.pos.getY();
   }

   public ItemStack getStackInSlot(int var1) {
      return this.inventory[var1];
   }

   public void setField(int var1, int var2) {
   }

   public static EntityItem func_145897_a(World var0, double var1, double var3, double var5) {
      List var7 = var0.func_175647_a(EntityItem.class, new AxisAlignedBB(var1, var3, var5, var1 + 1.0D, var3 + 1.0D, var5 + 1.0D), IEntitySelector.selectAnything);
      return var7.size() > 0 ? (EntityItem)var7.get(0) : null;
   }

   public String getGuiID() {
      return "minecraft:hopper";
   }

   public void clearInventory() {
      for(int var1 = 0; var1 < this.inventory.length; ++var1) {
         this.inventory[var1] = null;
      }

   }

   private static boolean func_174915_a(IHopper var0, IInventory var1, int var2, EnumFacing var3) {
      ItemStack var4 = var1.getStackInSlot(var2);
      if (var4 != null && func_174921_b(var1, var4, var2, var3)) {
         ItemStack var5 = var4.copy();
         ItemStack var6 = func_174918_a(var0, var1.decrStackSize(var2, 1), (EnumFacing)null);
         if (var6 == null || var6.stackSize == 0) {
            var1.markDirty();
            return true;
         }

         var1.setInventorySlotContents(var2, var5);
      }

      return false;
   }

   public boolean mayTransfer() {
      return this.transferCooldown <= 1;
   }

   public ItemStack getStackInSlotOnClosing(int var1) {
      if (this.inventory[var1] != null) {
         ItemStack var2 = this.inventory[var1];
         this.inventory[var1] = null;
         return var2;
      } else {
         return null;
      }
   }

   public double getXPos() {
      return (double)this.pos.getX();
   }

   public boolean isUseableByPlayer(EntityPlayer var1) {
      return this.worldObj.getTileEntity(this.pos) != this ? false : var1.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
   }

   public void markDirty() {
      super.markDirty();
   }

   public static IInventory func_145893_b(World var0, double var1, double var3, double var5) {
      Object var7 = null;
      int var8 = MathHelper.floor_double(var1);
      int var9 = MathHelper.floor_double(var3);
      int var10 = MathHelper.floor_double(var5);
      BlockPos var11 = new BlockPos(var8, var9, var10);
      TileEntity var12 = var0.getTileEntity(new BlockPos(var8, var9, var10));
      if (var12 instanceof IInventory) {
         var7 = (IInventory)var12;
         if (var7 instanceof TileEntityChest) {
            Block var13 = var0.getBlockState(new BlockPos(var8, var9, var10)).getBlock();
            if (var13 instanceof BlockChest) {
               var7 = ((BlockChest)var13).getLockableContainer(var0, var11);
            }
         }
      }

      if (var7 == null) {
         List var14 = var0.func_175674_a((Entity)null, new AxisAlignedBB(var1, var3, var5, var1 + 1.0D, var3 + 1.0D, var5 + 1.0D), IEntitySelector.selectInventories);
         if (var14.size() > 0) {
            var7 = (IInventory)var14.get(var0.rand.nextInt(var14.size()));
         }
      }

      return (IInventory)var7;
   }

   public static IInventory func_145884_b(IHopper var0) {
      return func_145893_b(var0.getWorld(), var0.getXPos(), var0.getYPos() + 1.0D, var0.getZPos());
   }

   public String getName() {
      return this.hasCustomName() ? this.customName : "container.hopper";
   }

   private boolean func_174919_a(IInventory var1, EnumFacing var2) {
      if (var1 instanceof ISidedInventory) {
         ISidedInventory var7 = (ISidedInventory)var1;
         int[] var8 = var7.getSlotsForFace(var2);

         for(int var9 = 0; var9 < var8.length; ++var9) {
            ItemStack var6 = var7.getStackInSlot(var8[var9]);
            if (var6 == null || var6.stackSize != var6.getMaxStackSize()) {
               return false;
            }
         }
      } else {
         int var3 = var1.getSizeInventory();

         for(int var4 = 0; var4 < var3; ++var4) {
            ItemStack var5 = var1.getStackInSlot(var4);
            if (var5 == null || var5.stackSize != var5.getMaxStackSize()) {
               return false;
            }
         }
      }

      return true;
   }

   public boolean isOnTransferCooldown() {
      return this.transferCooldown > 0;
   }

   public int getFieldCount() {
      return 0;
   }

   private boolean func_152104_k() {
      ItemStack[] var1 = this.inventory;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         ItemStack var4 = var1[var3];
         if (var4 != null) {
            return false;
         }
      }

      return true;
   }

   public double getZPos() {
      return (double)this.pos.getZ();
   }

   public boolean isItemValidForSlot(int var1, ItemStack var2) {
      return true;
   }

   public ItemStack decrStackSize(int var1, int var2) {
      if (this.inventory[var1] != null) {
         ItemStack var3;
         if (this.inventory[var1].stackSize <= var2) {
            var3 = this.inventory[var1];
            this.inventory[var1] = null;
            return var3;
         } else {
            var3 = this.inventory[var1].splitStack(var2);
            if (this.inventory[var1].stackSize == 0) {
               this.inventory[var1] = null;
            }

            return var3;
         }
      } else {
         return null;
      }
   }

   public void openInventory(EntityPlayer var1) {
   }

   public static boolean func_145898_a(IInventory var0, EntityItem var1) {
      boolean var2 = false;
      if (var1 == null) {
         return false;
      } else {
         ItemStack var3 = var1.getEntityItem().copy();
         ItemStack var4 = func_174918_a(var0, var3, (EnumFacing)null);
         if (var4 != null && var4.stackSize != 0) {
            var1.setEntityItemStack(var4);
         } else {
            var2 = true;
            var1.setDead();
         }

         return var2;
      }
   }

   private static boolean func_174921_b(IInventory var0, ItemStack var1, int var2, EnumFacing var3) {
      return !(var0 instanceof ISidedInventory) || ((ISidedInventory)var0).canExtractItem(var2, var1, var3);
   }

   public void closeInventory(EntityPlayer var1) {
   }

   private static boolean func_174917_b(IInventory var0, EnumFacing var1) {
      if (var0 instanceof ISidedInventory) {
         ISidedInventory var2 = (ISidedInventory)var0;
         int[] var3 = var2.getSlotsForFace(var1);

         for(int var4 = 0; var4 < var3.length; ++var4) {
            if (var2.getStackInSlot(var3[var4]) != null) {
               return false;
            }
         }
      } else {
         int var5 = var0.getSizeInventory();

         for(int var6 = 0; var6 < var5; ++var6) {
            if (var0.getStackInSlot(var6) != null) {
               return false;
            }
         }
      }

      return true;
   }

   public static boolean func_145891_a(IHopper var0) {
      IInventory var1 = func_145884_b(var0);
      if (var1 != null) {
         EnumFacing var2 = EnumFacing.DOWN;
         if (func_174917_b(var1, var2)) {
            return false;
         }

         if (var1 instanceof ISidedInventory) {
            ISidedInventory var3 = (ISidedInventory)var1;
            int[] var4 = var3.getSlotsForFace(var2);

            for(int var5 = 0; var5 < var4.length; ++var5) {
               if (func_174915_a(var0, var1, var4[var5], var2)) {
                  return true;
               }
            }
         } else {
            int var7 = var1.getSizeInventory();

            for(int var8 = 0; var8 < var7; ++var8) {
               if (func_174915_a(var0, var1, var8, var2)) {
                  return true;
               }
            }
         }
      } else {
         EntityItem var6 = func_145897_a(var0.getWorld(), var0.getXPos(), var0.getYPos() + 1.0D, var0.getZPos());
         if (var6 != null) {
            return func_145898_a(var0, var6);
         }
      }

      return false;
   }

   private static boolean canCombine(ItemStack var0, ItemStack var1) {
      return var0.getItem() != var1.getItem() ? false : (var0.getMetadata() != var1.getMetadata() ? false : (var0.stackSize > var0.getMaxStackSize() ? false : ItemStack.areItemStackTagsEqual(var0, var1)));
   }

   public void readFromNBT(NBTTagCompound var1) {
      super.readFromNBT(var1);
      NBTTagList var2 = var1.getTagList("Items", 10);
      this.inventory = new ItemStack[this.getSizeInventory()];
      if (var1.hasKey("CustomName", 8)) {
         this.customName = var1.getString("CustomName");
      }

      this.transferCooldown = var1.getInteger("TransferCooldown");

      for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
         NBTTagCompound var4 = var2.getCompoundTagAt(var3);
         byte var5 = var4.getByte("Slot");
         if (var5 >= 0 && var5 < this.inventory.length) {
            this.inventory[var5] = ItemStack.loadItemStackFromNBT(var4);
         }
      }

   }
}
