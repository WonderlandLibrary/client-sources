package net.minecraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase$EnumRailDirection;
import net.minecraft.block.BlockRailBase$Rail;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockRailBase extends Block {
   protected final boolean isPowered;

   public static boolean isRailBlock(World var0, BlockPos var1) {
      return isRailBlock(var0.getBlockState(var1));
   }

   public static boolean isRailBlock(IBlockState var0) {
      Block var1 = var0.getBlock();
      return var1 == Blocks.rail || var1 == Blocks.golden_rail || var1 == Blocks.detector_rail || var1 == Blocks.activator_rail;
   }

   protected BlockRailBase(boolean var1) {
      super(Material.circuits);
      this.isPowered = var1;
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
      this.setCreativeTab(CreativeTabs.tabTransport);
   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public MovingObjectPosition collisionRayTrace(World var1, BlockPos var2, Vec3 var3, Vec3 var4) {
      this.setBlockBoundsBasedOnState(var1, var2);
      return super.collisionRayTrace(var1, var2, var3, var4);
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      IBlockState var3 = var1.getBlockState(var2);
      BlockRailBase$EnumRailDirection var4 = var3.getBlock() == this?(BlockRailBase$EnumRailDirection)var3.getValue(this.getShapeProperty()):null;
      if(var4.isAscending()) {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
      } else {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
      }

   }

   public boolean isFullCube() {
      return false;
   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      return World.doesBlockHaveSolidTopSurface(var1, var2.down());
   }

   public void onBlockAdded(World var1, BlockPos var2, IBlockState var3) {
      if(!var1.isRemote) {
         var3 = this.func_176564_a(var1, var2, var3, true);
         if(this.isPowered) {
            this.onNeighborBlockChange(var1, var2, var3, this);
         }
      }

   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if(!var1.isRemote) {
         BlockRailBase$EnumRailDirection var5 = (BlockRailBase$EnumRailDirection)var3.getValue(this.getShapeProperty());
         boolean var6 = false;
         if(!World.doesBlockHaveSolidTopSurface(var1, var2.down())) {
            var6 = true;
         }

         if(var5 == BlockRailBase$EnumRailDirection.ASCENDING_EAST && !World.doesBlockHaveSolidTopSurface(var1, var2.east())) {
            var6 = true;
         } else if(var5 == BlockRailBase$EnumRailDirection.ASCENDING_WEST && !World.doesBlockHaveSolidTopSurface(var1, var2.west())) {
            var6 = true;
         } else if(var5 == BlockRailBase$EnumRailDirection.ASCENDING_NORTH && !World.doesBlockHaveSolidTopSurface(var1, var2.north())) {
            var6 = true;
         } else if(var5 == BlockRailBase$EnumRailDirection.ASCENDING_SOUTH && !World.doesBlockHaveSolidTopSurface(var1, var2.south())) {
            var6 = true;
         }

         this.dropBlockAsItem(var1, var2, var3, 0);
         var1.setBlockToAir(var2);
      }

   }

   protected void onNeighborChangedInternal(World var1, BlockPos var2, IBlockState var3, Block var4) {
   }

   protected IBlockState func_176564_a(World var1, BlockPos var2, IBlockState var3, boolean var4) {
      return var1.isRemote?var3:(new BlockRailBase$Rail(this, var1, var2, var3)).func_180364_a(var1.isBlockPowered(var2), var4).getBlockState();
   }

   public int getMobilityFlag() {
      return 0;
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      super.breakBlock(var1, var2, var3);
      if(((BlockRailBase$EnumRailDirection)var3.getValue(this.getShapeProperty())).isAscending()) {
         var1.notifyNeighborsOfStateChange(var2.up(), this);
      }

      if(this.isPowered) {
         var1.notifyNeighborsOfStateChange(var2, this);
         var1.notifyNeighborsOfStateChange(var2.down(), this);
      }

   }

   public abstract IProperty getShapeProperty();
}
