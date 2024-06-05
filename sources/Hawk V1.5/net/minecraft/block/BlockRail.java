package net.minecraft.block;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockRail extends BlockRailBase {
   private static final String __OBFID = "CL_00000293";
   public static final PropertyEnum field_176565_b = PropertyEnum.create("shape", BlockRailBase.EnumRailDirection.class);

   protected BlockRail() {
      super(false);
      this.setDefaultState(this.blockState.getBaseState().withProperty(field_176565_b, BlockRailBase.EnumRailDirection.NORTH_SOUTH));
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{field_176565_b});
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(field_176565_b, BlockRailBase.EnumRailDirection.func_177016_a(var1));
   }

   public IProperty func_176560_l() {
      return field_176565_b;
   }

   public int getMetaFromState(IBlockState var1) {
      return ((BlockRailBase.EnumRailDirection)var1.getValue(field_176565_b)).func_177015_a();
   }

   protected void func_176561_b(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (var4.canProvidePower() && (new BlockRailBase.Rail(var1, var2, var3)).countAdjacentRails() == 3) {
         this.func_176564_a(var1, var2, var3, false);
      }

   }
}
