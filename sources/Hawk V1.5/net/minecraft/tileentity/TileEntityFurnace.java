package net.minecraft.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

public class TileEntityFurnace extends TileEntityLockable implements ISidedInventory, IUpdatePlayerListBox {
   private static final String __OBFID = "CL_00000357";
   private int furnaceBurnTime;
   private int field_174906_k;
   private static final int[] slotsBottom = new int[]{2, 1};
   private ItemStack[] furnaceItemStacks = new ItemStack[3];
   private String furnaceCustomName;
   private static final int[] slotsTop = new int[1];
   private static final int[] slotsSides = new int[]{1};
   private int currentItemBurnTime;
   private int field_174905_l;

   public void clearInventory() {
      for(int var1 = 0; var1 < this.furnaceItemStacks.length; ++var1) {
         this.furnaceItemStacks[var1] = null;
      }

   }

   public void readFromNBT(NBTTagCompound var1) {
      super.readFromNBT(var1);
      NBTTagList var2 = var1.getTagList("Items", 10);
      this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];

      for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
         NBTTagCompound var4 = var2.getCompoundTagAt(var3);
         byte var5 = var4.getByte("Slot");
         if (var5 >= 0 && var5 < this.furnaceItemStacks.length) {
            this.furnaceItemStacks[var5] = ItemStack.loadItemStackFromNBT(var4);
         }
      }

      this.furnaceBurnTime = var1.getShort("BurnTime");
      this.field_174906_k = var1.getShort("CookTime");
      this.field_174905_l = var1.getShort("CookTimeTotal");
      this.currentItemBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);
      if (var1.hasKey("CustomName", 8)) {
         this.furnaceCustomName = var1.getString("CustomName");
      }

   }

   public void closeInventory(EntityPlayer var1) {
   }

   public static int getItemBurnTime(ItemStack var0) {
      if (var0 == null) {
         return 0;
      } else {
         Item var1 = var0.getItem();
         if (var1 instanceof ItemBlock && Block.getBlockFromItem(var1) != Blocks.air) {
            Block var2 = Block.getBlockFromItem(var1);
            if (var2 == Blocks.wooden_slab) {
               return 150;
            }

            if (var2.getMaterial() == Material.wood) {
               return 300;
            }

            if (var2 == Blocks.coal_block) {
               return 16000;
            }
         }

         return var1 instanceof ItemTool && ((ItemTool)var1).getToolMaterialName().equals("WOOD") ? 200 : (var1 instanceof ItemSword && ((ItemSword)var1).getToolMaterialName().equals("WOOD") ? 200 : (var1 instanceof ItemHoe && ((ItemHoe)var1).getMaterialName().equals("WOOD") ? 200 : (var1 == Items.stick ? 100 : (var1 == Items.coal ? 1600 : (var1 == Items.lava_bucket ? 20000 : (var1 == Item.getItemFromBlock(Blocks.sapling) ? 100 : (var1 == Items.blaze_rod ? 2400 : 0)))))));
      }
   }

   public boolean canExtractItem(int var1, ItemStack var2, EnumFacing var3) {
      if (var3 == EnumFacing.DOWN && var1 == 1) {
         Item var4 = var2.getItem();
         if (var4 != Items.water_bucket && var4 != Items.bucket) {
            return false;
         }
      }

      return true;
   }

   public void setCustomInventoryName(String var1) {
      this.furnaceCustomName = var1;
   }

   public int getField(int var1) {
      switch(var1) {
      case 0:
         return this.furnaceBurnTime;
      case 1:
         return this.currentItemBurnTime;
      case 2:
         return this.field_174906_k;
      case 3:
         return this.field_174905_l;
      default:
         return 0;
      }
   }

   public boolean isBurning() {
      return this.furnaceBurnTime > 0;
   }

   public void openInventory(EntityPlayer var1) {
   }

   public void setField(int var1, int var2) {
      switch(var1) {
      case 0:
         this.furnaceBurnTime = var2;
         break;
      case 1:
         this.currentItemBurnTime = var2;
         break;
      case 2:
         this.field_174906_k = var2;
         break;
      case 3:
         this.field_174905_l = var2;
      }

   }

   private boolean canSmelt() {
      if (this.furnaceItemStacks[0] == null) {
         return false;
      } else {
         ItemStack var1 = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks[0]);
         return var1 == null ? false : (this.furnaceItemStacks[2] == null ? true : (!this.furnaceItemStacks[2].isItemEqual(var1) ? false : (this.furnaceItemStacks[2].stackSize < this.getInventoryStackLimit() && this.furnaceItemStacks[2].stackSize < this.furnaceItemStacks[2].getMaxStackSize() ? true : this.furnaceItemStacks[2].stackSize < var1.getMaxStackSize())));
      }
   }

   public boolean isItemValidForSlot(int var1, ItemStack var2) {
      return var1 == 2 ? false : (var1 != 1 ? true : isItemFuel(var2) || SlotFurnaceFuel.func_178173_c_(var2));
   }

   public String getName() {
      return this.hasCustomName() ? this.furnaceCustomName : "container.furnace";
   }

   public boolean isUseableByPlayer(EntityPlayer var1) {
      return this.worldObj.getTileEntity(this.pos) != this ? false : var1.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
   }

   public String getGuiID() {
      return "minecraft:furnace";
   }

   public ItemStack getStackInSlot(int var1) {
      return this.furnaceItemStacks[var1];
   }

   public boolean canInsertItem(int var1, ItemStack var2, EnumFacing var3) {
      return this.isItemValidForSlot(var1, var2);
   }

   public int func_174904_a(ItemStack var1) {
      return 200;
   }

   public int getSizeInventory() {
      return this.furnaceItemStacks.length;
   }

   public int[] getSlotsForFace(EnumFacing var1) {
      return var1 == EnumFacing.DOWN ? slotsBottom : (var1 == EnumFacing.UP ? slotsTop : slotsSides);
   }

   public static boolean func_174903_a(IInventory var0) {
      return var0.getField(0) > 0;
   }

   public boolean hasCustomName() {
      return this.furnaceCustomName != null && this.furnaceCustomName.length() > 0;
   }

   public void writeToNBT(NBTTagCompound var1) {
      super.writeToNBT(var1);
      var1.setShort("BurnTime", (short)this.furnaceBurnTime);
      var1.setShort("CookTime", (short)this.field_174906_k);
      var1.setShort("CookTimeTotal", (short)this.field_174905_l);
      NBTTagList var2 = new NBTTagList();

      for(int var3 = 0; var3 < this.furnaceItemStacks.length; ++var3) {
         if (this.furnaceItemStacks[var3] != null) {
            NBTTagCompound var4 = new NBTTagCompound();
            var4.setByte("Slot", (byte)var3);
            this.furnaceItemStacks[var3].writeToNBT(var4);
            var2.appendTag(var4);
         }
      }

      var1.setTag("Items", var2);
      if (this.hasCustomName()) {
         var1.setString("CustomName", this.furnaceCustomName);
      }

   }

   public void update() {
      boolean var1 = this.isBurning();
      boolean var2 = false;
      if (this.isBurning()) {
         --this.furnaceBurnTime;
      }

      if (!this.worldObj.isRemote) {
         if (this.isBurning() || this.furnaceItemStacks[1] != null && this.furnaceItemStacks[0] != null) {
            if (!this.isBurning() && this.canSmelt()) {
               this.currentItemBurnTime = this.furnaceBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);
               if (this.isBurning()) {
                  var2 = true;
                  if (this.furnaceItemStacks[1] != null) {
                     --this.furnaceItemStacks[1].stackSize;
                     if (this.furnaceItemStacks[1].stackSize == 0) {
                        Item var3 = this.furnaceItemStacks[1].getItem().getContainerItem();
                        this.furnaceItemStacks[1] = var3 != null ? new ItemStack(var3) : null;
                     }
                  }
               }
            }

            if (this.isBurning() && this.canSmelt()) {
               ++this.field_174906_k;
               if (this.field_174906_k == this.field_174905_l) {
                  this.field_174906_k = 0;
                  this.field_174905_l = this.func_174904_a(this.furnaceItemStacks[0]);
                  this.smeltItem();
                  var2 = true;
               }
            } else {
               this.field_174906_k = 0;
            }
         } else if (!this.isBurning() && this.field_174906_k > 0) {
            this.field_174906_k = MathHelper.clamp_int(this.field_174906_k - 2, 0, this.field_174905_l);
         }

         if (var1 != this.isBurning()) {
            var2 = true;
            BlockFurnace.func_176446_a(this.isBurning(), this.worldObj, this.pos);
         }
      }

      if (var2) {
         this.markDirty();
      }

   }

   public int getInventoryStackLimit() {
      return 64;
   }

   public void smeltItem() {
      if (this.canSmelt()) {
         ItemStack var1 = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks[0]);
         if (this.furnaceItemStacks[2] == null) {
            this.furnaceItemStacks[2] = var1.copy();
         } else if (this.furnaceItemStacks[2].getItem() == var1.getItem()) {
            ++this.furnaceItemStacks[2].stackSize;
         }

         if (this.furnaceItemStacks[0].getItem() == Item.getItemFromBlock(Blocks.sponge) && this.furnaceItemStacks[0].getMetadata() == 1 && this.furnaceItemStacks[1] != null && this.furnaceItemStacks[1].getItem() == Items.bucket) {
            this.furnaceItemStacks[1] = new ItemStack(Items.water_bucket);
         }

         --this.furnaceItemStacks[0].stackSize;
         if (this.furnaceItemStacks[0].stackSize <= 0) {
            this.furnaceItemStacks[0] = null;
         }
      }

   }

   public int getFieldCount() {
      return 4;
   }

   public ItemStack getStackInSlotOnClosing(int var1) {
      if (this.furnaceItemStacks[var1] != null) {
         ItemStack var2 = this.furnaceItemStacks[var1];
         this.furnaceItemStacks[var1] = null;
         return var2;
      } else {
         return null;
      }
   }

   public ItemStack decrStackSize(int var1, int var2) {
      if (this.furnaceItemStacks[var1] != null) {
         ItemStack var3;
         if (this.furnaceItemStacks[var1].stackSize <= var2) {
            var3 = this.furnaceItemStacks[var1];
            this.furnaceItemStacks[var1] = null;
            return var3;
         } else {
            var3 = this.furnaceItemStacks[var1].splitStack(var2);
            if (this.furnaceItemStacks[var1].stackSize == 0) {
               this.furnaceItemStacks[var1] = null;
            }

            return var3;
         }
      } else {
         return null;
      }
   }

   public static boolean isItemFuel(ItemStack var0) {
      return getItemBurnTime(var0) > 0;
   }

   public void setInventorySlotContents(int var1, ItemStack var2) {
      boolean var3 = var2 != null && var2.isItemEqual(this.furnaceItemStacks[var1]) && ItemStack.areItemStackTagsEqual(var2, this.furnaceItemStacks[var1]);
      this.furnaceItemStacks[var1] = var2;
      if (var2 != null && var2.stackSize > this.getInventoryStackLimit()) {
         var2.stackSize = this.getInventoryStackLimit();
      }

      if (var1 == 0 && !var3) {
         this.field_174905_l = this.func_174904_a(var2);
         this.field_174906_k = 0;
         this.markDirty();
      }

   }

   public Container createContainer(InventoryPlayer var1, EntityPlayer var2) {
      return new ContainerFurnace(var1, this);
   }
}
