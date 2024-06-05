package net.minecraft.block;

import com.google.common.base.Predicate;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockRailPowered extends BlockRailBase {
   private static final String __OBFID = "CL_00000288";
   public static final PropertyBool field_176569_M = PropertyBool.create("powered");
   public static final PropertyEnum field_176568_b = PropertyEnum.create("shape", BlockRailBase.EnumRailDirection.class, new Predicate() {
      private static final String __OBFID = "CL_00002080";

      public boolean apply(Object var1) {
         return this.func_180133_a((BlockRailBase.EnumRailDirection)var1);
      }

      public boolean func_180133_a(BlockRailBase.EnumRailDirection var1) {
         return var1 != BlockRailBase.EnumRailDirection.NORTH_EAST && var1 != BlockRailBase.EnumRailDirection.NORTH_WEST && var1 != BlockRailBase.EnumRailDirection.SOUTH_EAST && var1 != BlockRailBase.EnumRailDirection.SOUTH_WEST;
      }
   });

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(field_176568_b, BlockRailBase.EnumRailDirection.func_177016_a(var1 & 7)).withProperty(field_176569_M, (var1 & 8) > 0);
   }

   protected void func_176561_b(World var1, BlockPos var2, IBlockState var3, Block var4) {
      boolean var5 = (Boolean)var3.getValue(field_176569_M);
      boolean var6 = var1.isBlockPowered(var2) || this.func_176566_a(var1, var2, var3, true, 0) || this.func_176566_a(var1, var2, var3, false, 0);
      if (var6 != var5) {
         var1.setBlockState(var2, var3.withProperty(field_176569_M, var6), 3);
         var1.notifyNeighborsOfStateChange(var2.offsetDown(), this);
         if (((BlockRailBase.EnumRailDirection)var3.getValue(field_176568_b)).func_177018_c()) {
            var1.notifyNeighborsOfStateChange(var2.offsetUp(), this);
         }
      }

   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{field_176568_b, field_176569_M});
   }

   protected BlockRailPowered() {
      super(true);
      this.setDefaultState(this.blockState.getBaseState().withProperty(field_176568_b, BlockRailBase.EnumRailDirection.NORTH_SOUTH).withProperty(field_176569_M, false));
   }

   protected boolean func_176566_a(World var1, BlockPos var2, IBlockState var3, boolean var4, int var5) {
      if (var5 >= 8) {
         return false;
      } else {
         int var6 = var2.getX();
         int var7 = var2.getY();
         int var8 = var2.getZ();
         boolean var9 = true;
         BlockRailBase.EnumRailDirection var10 = (BlockRailBase.EnumRailDirection)var3.getValue(field_176568_b);
         switch(var10) {
         case NORTH_SOUTH:
            if (var4) {
               ++var8;
            } else {
               --var8;
            }
            break;
         case EAST_WEST:
            if (var4) {
               --var6;
            } else {
               ++var6;
            }
            break;
         case ASCENDING_EAST:
            if (var4) {
               --var6;
            } else {
               ++var6;
               ++var7;
               var9 = false;
            }

            var10 = BlockRailBase.EnumRailDirection.EAST_WEST;
            break;
         case ASCENDING_WEST:
            if (var4) {
               --var6;
               ++var7;
               var9 = false;
            } else {
               ++var6;
            }

            var10 = BlockRailBase.EnumRailDirection.EAST_WEST;
            break;
         case ASCENDING_NORTH:
            if (var4) {
               ++var8;
            } else {
               --var8;
               ++var7;
               var9 = false;
            }

            var10 = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
            break;
         case ASCENDING_SOUTH:
            if (var4) {
               ++var8;
               ++var7;
               var9 = false;
            } else {
               --var8;
            }

            var10 = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
         }

         return this.func_176567_a(var1, new BlockPos(var6, var7, var8), var4, var5, var10) ? true : var9 && this.func_176567_a(var1, new BlockPos(var6, var7 - 1, var8), var4, var5, var10);
      }
   }

   public int getMetaFromState(IBlockState var1) {
      byte var2 = 0;
      int var3 = var2 | ((BlockRailBase.EnumRailDirection)var1.getValue(field_176568_b)).func_177015_a();
      if ((Boolean)var1.getValue(field_176569_M)) {
         var3 |= 8;
      }

      return var3;
   }

   protected boolean func_176567_a(World var1, BlockPos var2, boolean var3, int var4, BlockRailBase.EnumRailDirection var5) {
      IBlockState var6 = var1.getBlockState(var2);
      if (var6.getBlock() != this) {
         return false;
      } else {
         BlockRailBase.EnumRailDirection var7 = (BlockRailBase.EnumRailDirection)var6.getValue(field_176568_b);
         return var5 == BlockRailBase.EnumRailDirection.EAST_WEST && (var7 == BlockRailBase.EnumRailDirection.NORTH_SOUTH || var7 == BlockRailBase.EnumRailDirection.ASCENDING_NORTH || var7 == BlockRailBase.EnumRailDirection.ASCENDING_SOUTH) ? false : (var5 != BlockRailBase.EnumRailDirection.NORTH_SOUTH || var7 != BlockRailBase.EnumRailDirection.EAST_WEST && var7 != BlockRailBase.EnumRailDirection.ASCENDING_EAST && var7 != BlockRailBase.EnumRailDirection.ASCENDING_WEST ? ((Boolean)var6.getValue(field_176569_M) ? (var1.isBlockPowered(var2) ? true : this.func_176566_a(var1, var2, var6, var3, var4 + 1)) : false) : false);
      }
   }

   public IProperty func_176560_l() {
      return field_176568_b;
   }

   static final class SwitchEnumRailDirection {
      private static final String __OBFID = "CL_00002079";
      static final int[] field_180121_a = new int[BlockRailBase.EnumRailDirection.values().length];

      static {
         try {
            field_180121_a[BlockRailBase.EnumRailDirection.NORTH_SOUTH.ordinal()] = 1;
         } catch (NoSuchFieldError var6) {
         }

         try {
            field_180121_a[BlockRailBase.EnumRailDirection.EAST_WEST.ordinal()] = 2;
         } catch (NoSuchFieldError var5) {
         }

         try {
            field_180121_a[BlockRailBase.EnumRailDirection.ASCENDING_EAST.ordinal()] = 3;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_180121_a[BlockRailBase.EnumRailDirection.ASCENDING_WEST.ordinal()] = 4;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_180121_a[BlockRailBase.EnumRailDirection.ASCENDING_NORTH.ordinal()] = 5;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_180121_a[BlockRailBase.EnumRailDirection.ASCENDING_SOUTH.ordinal()] = 6;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
