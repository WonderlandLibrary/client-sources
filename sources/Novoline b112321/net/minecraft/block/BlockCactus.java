package net.minecraft.block;

import java.util.Random;
import net.iV;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing$Plane;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

public class BlockCactus extends Block {
   public static final iV P = iV.a("age", 0, 15);

   protected BlockCactus() {
      super(Material.cactus);
      this.setDefaultState(this.blockState.getBaseState().withProperty(P, Integer.valueOf(0)));
      this.setTickRandomly(true);
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      BlockPos var5 = var2.up();
      if(var1.isAirBlock(var5)) {
         int var6;
         for(var6 = 1; var1.getBlockState(var2.down(var6)).getBlock() == this; ++var6) {
            ;
         }

         if(var6 < 3) {
            int var7 = ((Integer)var3.getValue(P)).intValue();
            if(var7 == 15) {
               var1.setBlockState(var5, this.getDefaultState());
               IBlockState var8 = var3.withProperty(P, Integer.valueOf(0));
               var1.setBlockState(var2, var8, 4);
               this.onNeighborBlockChange(var1, var5, var8, this);
            } else {
               var1.setBlockState(var2, var3.withProperty(P, Integer.valueOf(var7 + 1)), 4);
            }
         }
      }

   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      float var4 = 0.0625F;
      return new AxisAlignedBB((double)((float)var2.getX() + 0.0625F), (double)var2.getY(), (double)((float)var2.getZ() + 0.0625F), (double)((float)(var2.getX() + 1) - 0.0625F), (double)((float)(var2.getY() + 1) - 0.0625F), (double)((float)(var2.getZ() + 1) - 0.0625F));
   }

   public AxisAlignedBB getSelectedBoundingBox(World var1, BlockPos var2) {
      float var3 = 0.0625F;
      return new AxisAlignedBB((double)((float)var2.getX() + 0.0625F), (double)var2.getY(), (double)((float)var2.getZ() + 0.0625F), (double)((float)(var2.getX() + 1) - 0.0625F), (double)(var2.getY() + 1), (double)((float)(var2.getZ() + 1) - 0.0625F));
   }

   public boolean isFullCube() {
      return false;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      return super.canPlaceBlockAt(var1, var2) && this.canBlockStay(var1, var2);
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if(!this.canBlockStay(var1, var2)) {
         var1.destroyBlock(var2, true);
      }

   }

   public boolean canBlockStay(World var1, BlockPos var2) {
      for(Object var4 : EnumFacing$Plane.HORIZONTAL) {
         if(var1.getBlockState(var2.offset((EnumFacing)var4)).getBlock().getMaterial().isSolid()) {
            return false;
         }
      }

      Block var5 = var1.getBlockState(var2.down()).getBlock();
      return var5 == Blocks.cactus || var5 == Blocks.sand;
   }

   public void onEntityCollidedWithBlock(World var1, BlockPos var2, IBlockState var3, Entity var4) {
      var4.attackEntityFrom(DamageSource.cactus, 1.0F);
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(P, Integer.valueOf(var1));
   }

   public int getMetaFromState(IBlockState var1) {
      return ((Integer)var1.getValue(P)).intValue();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{P});
   }
}
