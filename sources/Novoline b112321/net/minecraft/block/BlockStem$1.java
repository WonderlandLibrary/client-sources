package net.minecraft.block;

import com.google.common.base.Predicate;
import net.minecraft.util.EnumFacing;

final class BlockStem$1 implements Predicate {
   public boolean apply(EnumFacing var1) {
      return var1 != EnumFacing.DOWN;
   }
}
