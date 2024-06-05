package net.minecraft.block;

import com.google.common.base.Predicate;
import net.minecraft.block.BlockRailBase$EnumRailDirection;

final class BlockRailDetector$1 implements Predicate {
   public boolean apply(BlockRailBase$EnumRailDirection var1) {
      return var1 != BlockRailBase$EnumRailDirection.NORTH_EAST && var1 != BlockRailBase$EnumRailDirection.NORTH_WEST && var1 != BlockRailBase$EnumRailDirection.SOUTH_EAST && var1 != BlockRailBase$EnumRailDirection.SOUTH_WEST;
   }
}
