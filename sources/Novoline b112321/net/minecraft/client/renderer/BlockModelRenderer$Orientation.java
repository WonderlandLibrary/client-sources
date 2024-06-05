package net.minecraft.client.renderer;

import net.minecraft.util.EnumFacing;

public enum BlockModelRenderer$Orientation {
   DOWN("DOWN", 0, EnumFacing.DOWN, false),
   UP("UP", 1, EnumFacing.UP, false),
   NORTH("NORTH", 2, EnumFacing.NORTH, false),
   SOUTH("SOUTH", 3, EnumFacing.SOUTH, false),
   WEST("WEST", 4, EnumFacing.WEST, false),
   EAST("EAST", 5, EnumFacing.EAST, false),
   FLIP_DOWN("FLIP_DOWN", 6, EnumFacing.DOWN, true),
   FLIP_UP("FLIP_UP", 7, EnumFacing.UP, true),
   FLIP_NORTH("FLIP_NORTH", 8, EnumFacing.NORTH, true),
   FLIP_SOUTH("FLIP_SOUTH", 9, EnumFacing.SOUTH, true),
   FLIP_WEST("FLIP_WEST", 10, EnumFacing.WEST, true),
   FLIP_EAST("FLIP_EAST", 11, EnumFacing.EAST, true);

   protected final int field_178229_m;
   private static final BlockModelRenderer$Orientation[] $VALUES = new BlockModelRenderer$Orientation[]{DOWN, UP, NORTH, SOUTH, WEST, EAST, FLIP_DOWN, FLIP_UP, FLIP_NORTH, FLIP_SOUTH, FLIP_WEST, FLIP_EAST};
   private static final String c = "CL_00002513";
   private static final BlockModelRenderer$Orientation[] $VALUES$ = new BlockModelRenderer$Orientation[]{DOWN, UP, NORTH, SOUTH, WEST, EAST, FLIP_DOWN, FLIP_UP, FLIP_NORTH, FLIP_SOUTH, FLIP_WEST, FLIP_EAST};

   private BlockModelRenderer$Orientation(String var3, int var4, EnumFacing var5, boolean var6) {
      this.field_178229_m = var5.getIndex() + EnumFacing.values().length;
   }
}
