package net.minecraft.block;

import com.google.common.base.Predicate;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWallSign extends BlockSign {
   private static final String __OBFID = "CL_00002047";
   public static final PropertyDirection field_176412_a;

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{field_176412_a});
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      EnumFacing var3 = (EnumFacing)var1.getBlockState(var2).getValue(field_176412_a);
      float var4 = 0.28125F;
      float var5 = 0.78125F;
      float var6 = 0.0F;
      float var7 = 1.0F;
      float var8 = 0.125F;
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      switch(var3) {
      case NORTH:
         this.setBlockBounds(var6, var4, 1.0F - var8, var7, var5, 1.0F);
         break;
      case SOUTH:
         this.setBlockBounds(var6, var4, 0.0F, var7, var5, var8);
         break;
      case WEST:
         this.setBlockBounds(1.0F - var8, var4, var6, 1.0F, var5, var7);
         break;
      case EAST:
         this.setBlockBounds(0.0F, var4, var6, var8, var5, var7);
      }

   }

   public BlockWallSign() {
      this.setDefaultState(this.blockState.getBaseState().withProperty(field_176412_a, EnumFacing.NORTH));
   }

   public int getMetaFromState(IBlockState var1) {
      return ((EnumFacing)var1.getValue(field_176412_a)).getIndex();
   }

   public IBlockState getStateFromMeta(int var1) {
      EnumFacing var2 = EnumFacing.getFront(var1);
      if (var2.getAxis() == EnumFacing.Axis.Y) {
         var2 = EnumFacing.NORTH;
      }

      return this.getDefaultState().withProperty(field_176412_a, var2);
   }

   static {
      field_176412_a = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      EnumFacing var5 = (EnumFacing)var3.getValue(field_176412_a);
      if (!var1.getBlockState(var2.offset(var5.getOpposite())).getBlock().getMaterial().isSolid()) {
         this.dropBlockAsItem(var1, var2, var3, 0);
         var1.setBlockToAir(var2);
      }

      super.onNeighborBlockChange(var1, var2, var3, var4);
   }

   static final class SwitchEnumFacing {
      static final int[] field_177331_a = new int[EnumFacing.values().length];
      private static final String __OBFID = "CL_00002046";

      static {
         try {
            field_177331_a[EnumFacing.NORTH.ordinal()] = 1;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_177331_a[EnumFacing.SOUTH.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_177331_a[EnumFacing.WEST.ordinal()] = 3;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_177331_a[EnumFacing.EAST.ordinal()] = 4;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
