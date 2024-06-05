package net.minecraft.block;

import com.google.common.base.Predicate;
import net.minecraft.block.BlockPlanks$EnumType;

final class BlockNewLeaf$1 implements Predicate {
   public boolean apply(BlockPlanks$EnumType var1) {
      return var1.getMetadata() >= 4;
   }
}
