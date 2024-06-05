package net.minecraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRailBase$EnumRailDirection;
import net.minecraft.block.BlockRailBase$Rail;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockRail extends BlockRailBase {
   public static final PropertyEnum SHAPE = PropertyEnum.create("shape", BlockRailBase$EnumRailDirection.class);

   protected BlockRail() {
      super(false);
      this.setDefaultState(this.blockState.getBaseState().withProperty(SHAPE, BlockRailBase$EnumRailDirection.NORTH_SOUTH));
   }

   protected void onNeighborChangedInternal(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if(var4.canProvidePower() && (new BlockRailBase$Rail(this, var1, var2, var3)).countAdjacentRails() == 3) {
         this.func_176564_a(var1, var2, var3, false);
      }

   }

   public IProperty getShapeProperty() {
      return SHAPE;
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(SHAPE, BlockRailBase$EnumRailDirection.byMetadata(var1));
   }

   public int getMetaFromState(IBlockState var1) {
      return ((BlockRailBase$EnumRailDirection)var1.getValue(SHAPE)).getMetadata();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{SHAPE});
   }
}
