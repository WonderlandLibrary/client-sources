package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.EnumFacing;

public abstract class BlockRotatedPillar extends Block {
   private static final String __OBFID = "CL_00000302";
   public static final PropertyEnum field_176298_M = PropertyEnum.create("axis", EnumFacing.Axis.class);

   protected BlockRotatedPillar(Material var1) {
      super(var1);
   }
}
