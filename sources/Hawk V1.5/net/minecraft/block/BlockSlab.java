package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockSlab extends Block {
   private static final String __OBFID = "CL_00000253";
   public static final PropertyEnum HALF_PROP = PropertyEnum.create("half", BlockSlab.EnumBlockHalf.class);

   public boolean shouldSideBeRendered(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      if (this.isDouble()) {
         return super.shouldSideBeRendered(var1, var2, var3);
      } else if (var3 != EnumFacing.UP && var3 != EnumFacing.DOWN && !super.shouldSideBeRendered(var1, var2, var3)) {
         return false;
      } else {
         BlockPos var4 = var2.offset(var3.getOpposite());
         IBlockState var5 = var1.getBlockState(var2);
         IBlockState var6 = var1.getBlockState(var4);
         boolean var7 = func_150003_a(var5.getBlock()) && var5.getValue(HALF_PROP) == BlockSlab.EnumBlockHalf.TOP;
         boolean var8 = func_150003_a(var6.getBlock()) && var6.getValue(HALF_PROP) == BlockSlab.EnumBlockHalf.TOP;
         return var8 ? (var3 == EnumFacing.DOWN ? true : (var3 == EnumFacing.UP && super.shouldSideBeRendered(var1, var2, var3) ? true : !func_150003_a(var5.getBlock()) || !var7)) : (var3 == EnumFacing.UP ? true : (var3 == EnumFacing.DOWN && super.shouldSideBeRendered(var1, var2, var3) ? true : !func_150003_a(var5.getBlock()) || var7));
      }
   }

   public abstract Object func_176553_a(ItemStack var1);

   protected boolean canSilkHarvest() {
      return false;
   }

   public abstract IProperty func_176551_l();

   public void addCollisionBoxesToList(World var1, BlockPos var2, IBlockState var3, AxisAlignedBB var4, List var5, Entity var6) {
      this.setBlockBoundsBasedOnState(var1, var2);
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      IBlockState var9 = super.onBlockPlaced(var1, var2, var3, var4, var5, var6, var7, var8).withProperty(HALF_PROP, BlockSlab.EnumBlockHalf.BOTTOM);
      return this.isDouble() ? var9 : (var3 == EnumFacing.DOWN || var3 != EnumFacing.UP && !((double)var5 <= 0.5D) ? var9.withProperty(HALF_PROP, BlockSlab.EnumBlockHalf.TOP) : var9);
   }

   public BlockSlab(Material var1) {
      super(var1);
      if (this.isDouble()) {
         this.fullBlock = true;
      } else {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
      }

      this.setLightOpacity(255);
   }

   public void setBlockBoundsForItemRender() {
      if (this.isDouble()) {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      } else {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
      }

   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      if (this.isDouble()) {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      } else {
         IBlockState var3 = var1.getBlockState(var2);
         if (var3.getBlock() == this) {
            if (var3.getValue(HALF_PROP) == BlockSlab.EnumBlockHalf.TOP) {
               this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
            } else {
               this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
            }
         }
      }

   }

   protected static boolean func_150003_a(Block var0) {
      return var0 == Blocks.stone_slab || var0 == Blocks.wooden_slab || var0 == Blocks.stone_slab2;
   }

   public boolean isOpaqueCube() {
      return this.isDouble();
   }

   public abstract String getFullSlabName(int var1);

   public boolean isFullCube() {
      return this.isDouble();
   }

   public abstract boolean isDouble();

   public int quantityDropped(Random var1) {
      return this.isDouble() ? 2 : 1;
   }

   public int getDamageValue(World var1, BlockPos var2) {
      return super.getDamageValue(var1, var2) & 7;
   }

   public static enum EnumBlockHalf implements IStringSerializable {
      private static final BlockSlab.EnumBlockHalf[] $VALUES = new BlockSlab.EnumBlockHalf[]{TOP, BOTTOM};
      private static final BlockSlab.EnumBlockHalf[] ENUM$VALUES = new BlockSlab.EnumBlockHalf[]{TOP, BOTTOM};
      TOP("TOP", 0, "top"),
      BOTTOM("BOTTOM", 1, "bottom");

      private static final String __OBFID = "CL_00002109";
      private final String halfName;

      public String getName() {
         return this.halfName;
      }

      public String toString() {
         return this.halfName;
      }

      private EnumBlockHalf(String var3, int var4, String var5) {
         this.halfName = var5;
      }
   }
}
