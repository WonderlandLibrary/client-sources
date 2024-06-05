package net.minecraft.item;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemBlock extends Item {
   private static final String __OBFID = "CL_00001772";
   protected final Block block;

   public CreativeTabs getCreativeTab() {
      return this.block.getCreativeTabToDisplayOn();
   }

   public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      IBlockState var9 = var3.getBlockState(var4);
      Block var10 = var9.getBlock();
      if (var10 == Blocks.snow_layer && (Integer)var9.getValue(BlockSnow.LAYERS_PROP) < 1) {
         var5 = EnumFacing.UP;
      } else if (!var10.isReplaceable(var3, var4)) {
         var4 = var4.offset(var5);
      }

      if (var1.stackSize == 0) {
         return false;
      } else if (!var2.func_175151_a(var4, var5, var1)) {
         return false;
      } else if (var4.getY() == 255 && this.block.getMaterial().isSolid()) {
         return false;
      } else if (var3.canBlockBePlaced(this.block, var4, false, var5, (Entity)null, var1)) {
         int var11 = this.getMetadata(var1.getMetadata());
         IBlockState var12 = this.block.onBlockPlaced(var3, var4, var5, var6, var7, var8, var11, var2);
         if (var3.setBlockState(var4, var12, 3)) {
            var12 = var3.getBlockState(var4);
            if (var12.getBlock() == this.block) {
               setTileEntityNBT(var3, var4, var1);
               this.block.onBlockPlacedBy(var3, var4, var12, var2, var1);
            }

            var3.playSoundEffect((double)((float)var4.getX() + 0.5F), (double)((float)var4.getY() + 0.5F), (double)((float)var4.getZ() + 0.5F), this.block.stepSound.getPlaceSound(), (this.block.stepSound.getVolume() + 1.0F) / 2.0F, this.block.stepSound.getFrequency() * 0.8F);
            --var1.stackSize;
         }

         return true;
      } else {
         return false;
      }
   }

   public ItemBlock setUnlocalizedName(String var1) {
      super.setUnlocalizedName(var1);
      return this;
   }

   public String getUnlocalizedName(ItemStack var1) {
      return this.block.getUnlocalizedName();
   }

   public boolean canPlaceBlockOnSide(World var1, BlockPos var2, EnumFacing var3, EntityPlayer var4, ItemStack var5) {
      Block var6 = var1.getBlockState(var2).getBlock();
      if (var6 == Blocks.snow_layer) {
         var3 = EnumFacing.UP;
      } else if (!var6.isReplaceable(var1, var2)) {
         var2 = var2.offset(var3);
      }

      return var1.canBlockBePlaced(this.block, var2, false, var3, (Entity)null, var5);
   }

   public Block getBlock() {
      return this.block;
   }

   public ItemBlock(Block var1) {
      this.block = var1;
   }

   public Item setUnlocalizedName(String var1) {
      return this.setUnlocalizedName(var1);
   }

   public void getSubItems(Item var1, CreativeTabs var2, List var3) {
      this.block.getSubBlocks(var1, var2, var3);
   }

   public static boolean setTileEntityNBT(World var0, BlockPos var1, ItemStack var2) {
      if (var2.hasTagCompound() && var2.getTagCompound().hasKey("BlockEntityTag", 10)) {
         TileEntity var3 = var0.getTileEntity(var1);
         if (var3 != null) {
            NBTTagCompound var4 = new NBTTagCompound();
            NBTTagCompound var5 = (NBTTagCompound)var4.copy();
            var3.writeToNBT(var4);
            NBTTagCompound var6 = (NBTTagCompound)var2.getTagCompound().getTag("BlockEntityTag");
            var4.merge(var6);
            var4.setInteger("x", var1.getX());
            var4.setInteger("y", var1.getY());
            var4.setInteger("z", var1.getZ());
            if (!var4.equals(var5)) {
               var3.readFromNBT(var4);
               var3.markDirty();
               return true;
            }
         }
      }

      return false;
   }

   public String getUnlocalizedName() {
      return this.block.getUnlocalizedName();
   }
}
