package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockBasePressurePlate extends Block {
   protected BlockBasePressurePlate(Material var1) {
      this(var1, var1.getMaterialMapColor());
   }

   protected BlockBasePressurePlate(Material var1, MapColor var2) {
      super(var1, var2);
      this.setCreativeTab(CreativeTabs.tabRedstone);
      this.setTickRandomly(true);
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      this.setBlockBoundsBasedOnState0(var1.getBlockState(var2));
   }

   protected void setBlockBoundsBasedOnState0(IBlockState var1) {
      boolean var2 = this.getRedstoneStrength(var1) > 0;
      float var3 = 0.0625F;
      this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.03125F, 0.9375F);
   }

   public int tickRate(World var1) {
      return 20;
   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean isFullCube() {
      return false;
   }

   public boolean isPassable(IBlockAccess var1, BlockPos var2) {
      return true;
   }

   public boolean func_181623_g() {
      return true;
   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      return this.canBePlacedOn(var1, var2.down());
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if(!this.canBePlacedOn(var1, var2.down())) {
         this.dropBlockAsItem(var1, var2, var3, 0);
         var1.setBlockToAir(var2);
      }

   }

   private boolean canBePlacedOn(World var1, BlockPos var2) {
      return World.doesBlockHaveSolidTopSurface(var1, var2) || var1.getBlockState(var2).getBlock() instanceof BlockFence;
   }

   public void randomTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if(!var1.isRemote) {
         int var5 = this.getRedstoneStrength(var3);
         this.updateState(var1, var2, var3, var5);
      }

   }

   public void onEntityCollidedWithBlock(World var1, BlockPos var2, IBlockState var3, Entity var4) {
      if(!var1.isRemote) {
         int var5 = this.getRedstoneStrength(var3);
         this.updateState(var1, var2, var3, var5);
      }

   }

   protected void updateState(World var1, BlockPos var2, IBlockState var3, int var4) {
      int var5 = this.computeRedstoneStrength(var1, var2);
      boolean var6 = true;
      boolean var7 = true;
      if(var4 != var5) {
         var3 = this.setRedstoneStrength(var3, var5);
         var1.setBlockState(var2, var3, 2);
         this.updateNeighbors(var1, var2);
         var1.markBlockRangeForRenderUpdate(var2, var2);
      }

      var1.playSoundEffect((double)var2.getX() + 0.5D, (double)var2.getY() + 0.1D, (double)var2.getZ() + 0.5D, "random.click", 0.3F, 0.5F);
      var1.scheduleUpdate(var2, this, this.tickRate(var1));
   }

   protected AxisAlignedBB getSensitiveAABB(BlockPos var1) {
      float var2 = 0.125F;
      return new AxisAlignedBB((double)((float)var1.getX() + 0.125F), (double)var1.getY(), (double)((float)var1.getZ() + 0.125F), (double)((float)(var1.getX() + 1) - 0.125F), (double)var1.getY() + 0.25D, (double)((float)(var1.getZ() + 1) - 0.125F));
   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      if(this.getRedstoneStrength(var3) > 0) {
         this.updateNeighbors(var1, var2);
      }

      super.breakBlock(var1, var2, var3);
   }

   protected void updateNeighbors(World var1, BlockPos var2) {
      var1.notifyNeighborsOfStateChange(var2, this);
      var1.notifyNeighborsOfStateChange(var2.down(), this);
   }

   public int getWeakPower(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return this.getRedstoneStrength(var3);
   }

   public int getStrongPower(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return var4 == EnumFacing.UP?this.getRedstoneStrength(var3):0;
   }

   public boolean canProvidePower() {
      return true;
   }

   public void setBlockBoundsForItemRender() {
      float var1 = 0.5F;
      float var2 = 0.125F;
      float var3 = 0.5F;
      this.setBlockBounds(0.0F, 0.375F, 0.0F, 1.0F, 0.625F, 1.0F);
   }

   public int getMobilityFlag() {
      return 1;
   }

   protected abstract int computeRedstoneStrength(World var1, BlockPos var2);

   protected abstract int getRedstoneStrength(IBlockState var1);

   protected abstract IBlockState setRedstoneStrength(IBlockState var1, int var2);
}
