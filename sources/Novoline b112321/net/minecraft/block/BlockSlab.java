package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab$EnumBlockHalf;
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
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockSlab extends Block {
   public static final PropertyEnum HALF = PropertyEnum.create("half", BlockSlab$EnumBlockHalf.class);

   public BlockSlab(Material var1) {
      super(var1);
      if(this.isDouble()) {
         this.fullBlock = true;
      } else {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
      }

      this.setLightOpacity(255);
   }

   protected boolean canSilkHarvest() {
      return false;
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      if(this.isDouble()) {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      } else {
         IBlockState var3 = var1.getBlockState(var2);
         if(var3.getBlock() == this) {
            if(var3.getValue(HALF) == BlockSlab$EnumBlockHalf.TOP) {
               this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
            } else {
               this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
            }
         }
      }

   }

   public void setBlockBoundsForItemRender() {
      if(this.isDouble()) {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      } else {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
      }

   }

   public void addCollisionBoxesToList(World var1, BlockPos var2, IBlockState var3, AxisAlignedBB var4, List var5, Entity var6) {
      this.setBlockBoundsBasedOnState(var1, var2);
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
   }

   public boolean isOpaqueCube() {
      return this.isDouble();
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      IBlockState var9 = super.onBlockPlaced(var1, var2, var3, var4, var5, var6, var7, var8).withProperty(HALF, BlockSlab$EnumBlockHalf.BOTTOM);
      return this.isDouble()?var9:(var3 == EnumFacing.DOWN || var3 != EnumFacing.UP && (double)var5 > 0.5D?var9.withProperty(HALF, BlockSlab$EnumBlockHalf.TOP):var9);
   }

   public int quantityDropped(Random var1) {
      return this.isDouble()?2:1;
   }

   public boolean isFullCube() {
      return this.isDouble();
   }

   public boolean shouldSideBeRendered(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      if(this.isDouble()) {
         return super.shouldSideBeRendered(var1, var2, var3);
      } else if(var3 != EnumFacing.UP && var3 != EnumFacing.DOWN && !super.shouldSideBeRendered(var1, var2, var3)) {
         return false;
      } else {
         BlockPos var4 = var2.offset(var3.getOpposite());
         IBlockState var5 = var1.getBlockState(var2);
         IBlockState var6 = var1.getBlockState(var4);
         if(isSlab(var5.getBlock()) && var5.getValue(HALF) == BlockSlab$EnumBlockHalf.TOP) {
            boolean var9 = true;
         } else {
            boolean var10000 = false;
         }

         if(isSlab(var6.getBlock()) && var6.getValue(HALF) == BlockSlab$EnumBlockHalf.TOP) {
            boolean var11 = true;
         } else {
            boolean var10 = false;
         }

         if(var3 != EnumFacing.DOWN && (var3 != EnumFacing.UP || !super.shouldSideBeRendered(var1, var2, var3)) && isSlab(var5.getBlock())) {
            ;
         }

         return true;
      }
   }

   protected static boolean isSlab(Block var0) {
      return var0 == Blocks.stone_slab || var0 == Blocks.wooden_slab || var0 == Blocks.stone_slab2;
   }

   public abstract String getUnlocalizedName(int var1);

   public int getDamageValue(World var1, BlockPos var2) {
      return super.getDamageValue(var1, var2) & 7;
   }

   public abstract boolean isDouble();

   public abstract IProperty getVariantProperty();

   public abstract Object getVariant(ItemStack var1);
}
