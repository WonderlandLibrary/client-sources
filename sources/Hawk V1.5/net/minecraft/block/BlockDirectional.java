package net.minecraft.block;

import com.google.common.base.Predicate;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.util.EnumFacing;

public abstract class BlockDirectional extends Block {
   public static final PropertyDirection AGE;
   private static final String __OBFID = "CL_00000227";

   protected BlockDirectional(Material var1) {
      super(var1);
   }

   static {
      AGE = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
   }
}
