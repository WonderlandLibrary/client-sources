package net.minecraft.block;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.util.EnumFacing$Plane;

public abstract class BlockDirectional extends Block {
   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing$Plane.HORIZONTAL);

   protected BlockDirectional(Material var1) {
      super(var1);
   }

   protected BlockDirectional(Material var1, MapColor var2) {
      super(var1, var2);
   }
}
