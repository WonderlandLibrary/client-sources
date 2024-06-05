package net.minecraft.block;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRailBase$EnumRailDirection;
import net.minecraft.block.BlockRailPowered$1;
import net.minecraft.block.BlockRailPowered$2;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockRailPowered extends BlockRailBase {
   public static final PropertyEnum SHAPE = PropertyEnum.create("shape", BlockRailBase$EnumRailDirection.class, (Predicate)(new BlockRailPowered$1()));
   public static final PropertyBool POWERED = PropertyBool.create("powered");

   protected BlockRailPowered() {
      super(true);
      this.setDefaultState(this.blockState.getBaseState().withProperty(SHAPE, BlockRailBase$EnumRailDirection.NORTH_SOUTH).withProperty(POWERED, Boolean.FALSE));
   }

   protected boolean func_176566_a(World var1, BlockPos var2, IBlockState var3, boolean var4, int var5) {
      if(var5 >= 8) {
         return false;
      } else {
         int var6 = var2.getX();
         int var7 = var2.getY();
         int var8 = var2.getZ();
         boolean var9 = true;
         BlockRailBase$EnumRailDirection var10 = (BlockRailBase$EnumRailDirection)var3.getValue(SHAPE);
         switch(BlockRailPowered$2.$SwitchMap$net$minecraft$block$BlockRailBase$EnumRailDirection[var10.ordinal()]) {
         case 1:
            ++var8;
            break;
         case 2:
            --var6;
            break;
         case 3:
            --var6;
            var10 = BlockRailBase$EnumRailDirection.EAST_WEST;
            break;
         case 4:
            --var6;
            ++var7;
            var9 = false;
            var10 = BlockRailBase$EnumRailDirection.EAST_WEST;
            break;
         case 5:
            ++var8;
            var10 = BlockRailBase$EnumRailDirection.NORTH_SOUTH;
            break;
         case 6:
            ++var8;
            ++var7;
            var9 = false;
            var10 = BlockRailBase$EnumRailDirection.NORTH_SOUTH;
         }

         return this.func_176567_a(var1, new BlockPos(var6, var7, var8), var4, var5, var10) || this.func_176567_a(var1, new BlockPos(var6, var7 - 1, var8), var4, var5, var10);
      }
   }

   protected boolean func_176567_a(World var1, BlockPos var2, boolean var3, int var4, BlockRailBase$EnumRailDirection var5) {
      IBlockState var6 = var1.getBlockState(var2);
      if(var6.getBlock() != this) {
         return false;
      } else {
         BlockRailBase$EnumRailDirection var7 = (BlockRailBase$EnumRailDirection)var6.getValue(SHAPE);
         return (var5 != BlockRailBase$EnumRailDirection.EAST_WEST || var7 != BlockRailBase$EnumRailDirection.NORTH_SOUTH && var7 != BlockRailBase$EnumRailDirection.ASCENDING_NORTH && var7 != BlockRailBase$EnumRailDirection.ASCENDING_SOUTH) && (var5 != BlockRailBase$EnumRailDirection.NORTH_SOUTH || var7 != BlockRailBase$EnumRailDirection.EAST_WEST && var7 != BlockRailBase$EnumRailDirection.ASCENDING_EAST && var7 != BlockRailBase$EnumRailDirection.ASCENDING_WEST) && ((Boolean)var6.getValue(POWERED)).booleanValue() && (var1.isBlockPowered(var2) || this.func_176566_a(var1, var2, var6, var3, var4 + 1));
      }
   }

   protected void onNeighborChangedInternal(World var1, BlockPos var2, IBlockState var3, Block var4) {
      boolean var5 = ((Boolean)var3.getValue(POWERED)).booleanValue();
      boolean var6 = var1.isBlockPowered(var2) || this.func_176566_a(var1, var2, var3, true, 0) || this.func_176566_a(var1, var2, var3, false, 0);
      if(var6 != var5) {
         var1.setBlockState(var2, var3.withProperty(POWERED, Boolean.valueOf(var6)), 3);
         var1.notifyNeighborsOfStateChange(var2.down(), this);
         if(((BlockRailBase$EnumRailDirection)var3.getValue(SHAPE)).isAscending()) {
            var1.notifyNeighborsOfStateChange(var2.up(), this);
         }
      }

   }

   public IProperty getShapeProperty() {
      return SHAPE;
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(SHAPE, BlockRailBase$EnumRailDirection.byMetadata(var1 & 7)).withProperty(POWERED, Boolean.valueOf((var1 & 8) > 0));
   }

   public int getMetaFromState(IBlockState var1) {
      int var2 = 0;
      var2 = var2 | ((BlockRailBase$EnumRailDirection)var1.getValue(SHAPE)).getMetadata();
      if(((Boolean)var1.getValue(POWERED)).booleanValue()) {
         var2 |= 8;
      }

      return var2;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{SHAPE, POWERED});
   }
}
