package net.minecraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBanner;
import net.minecraft.block.BlockBanner$1;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing$Axis;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBanner$BlockBannerHanging extends BlockBanner {
   public BlockBanner$BlockBannerHanging() {
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      EnumFacing var3 = (EnumFacing)var1.getBlockState(var2).getValue(FACING);
      float var4 = 0.0F;
      float var5 = 0.78125F;
      float var6 = 0.0F;
      float var7 = 1.0F;
      float var8 = 0.125F;
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      switch(BlockBanner$1.$SwitchMap$net$minecraft$util$EnumFacing[var3.ordinal()]) {
      case 1:
      default:
         this.setBlockBounds(0.0F, 0.0F, 0.875F, 1.0F, 0.78125F, 1.0F);
         break;
      case 2:
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.78125F, 0.125F);
         break;
      case 3:
         this.setBlockBounds(0.875F, 0.0F, 0.0F, 1.0F, 0.78125F, 1.0F);
         break;
      case 4:
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.125F, 0.78125F, 1.0F);
      }

   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      EnumFacing var5 = (EnumFacing)var3.getValue(FACING);
      if(!var1.getBlockState(var2.offset(var5.getOpposite())).getBlock().getMaterial().isSolid()) {
         this.dropBlockAsItem(var1, var2, var3, 0);
         var1.setBlockToAir(var2);
      }

      super.onNeighborBlockChange(var1, var2, var3, var4);
   }

   public IBlockState getStateFromMeta(int var1) {
      EnumFacing var2 = EnumFacing.getFront(var1);
      if(var2.getAxis() == EnumFacing$Axis.Y) {
         var2 = EnumFacing.NORTH;
      }

      return this.getDefaultState().withProperty(FACING, var2);
   }

   public int getMetaFromState(IBlockState var1) {
      return ((EnumFacing)var1.getValue(FACING)).getIndex();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{FACING});
   }
}
