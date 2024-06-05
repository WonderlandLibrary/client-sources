package net.minecraft.client.renderer;

import net.minecraft.util.EnumFacing;

enum BlockModelRenderer$VertexTranslations {
   DOWN("DOWN", 0, 0, 1, 2, 3),
   UP("UP", 1, 2, 3, 0, 1),
   NORTH("NORTH", 2, 3, 0, 1, 2),
   SOUTH("SOUTH", 3, 0, 1, 2, 3),
   WEST("WEST", 4, 3, 0, 1, 2),
   EAST("EAST", 5, 1, 2, 3, 0);

   private final int field_178191_g;
   private final int field_178200_h;
   private final int field_178201_i;
   private final int field_178198_j;
   private static final BlockModelRenderer$VertexTranslations[] field_178199_k = new BlockModelRenderer$VertexTranslations[6];
   private static final BlockModelRenderer$VertexTranslations[] $VALUES = new BlockModelRenderer$VertexTranslations[]{DOWN, UP, NORTH, SOUTH, WEST, EAST};
   private static final String f = "CL_00002514";
   private static final BlockModelRenderer$VertexTranslations[] $VALUES$ = new BlockModelRenderer$VertexTranslations[]{DOWN, UP, NORTH, SOUTH, WEST, EAST};

   private BlockModelRenderer$VertexTranslations(String var3, int var4, int var5, int var6, int var7, int var8) {
      this.field_178191_g = var5;
      this.field_178200_h = var6;
      this.field_178201_i = var7;
      this.field_178198_j = var8;
   }

   public static BlockModelRenderer$VertexTranslations getVertexTranslations(EnumFacing var0) {
      return field_178199_k[var0.getIndex()];
   }

   static int access$200(BlockModelRenderer$VertexTranslations var0) {
      return var0.field_178191_g;
   }

   static int access$300(BlockModelRenderer$VertexTranslations var0) {
      return var0.field_178200_h;
   }

   static int access$400(BlockModelRenderer$VertexTranslations var0) {
      return var0.field_178201_i;
   }

   static int access$500(BlockModelRenderer$VertexTranslations var0) {
      return var0.field_178198_j;
   }

   static {
      field_178199_k[EnumFacing.DOWN.getIndex()] = DOWN;
      field_178199_k[EnumFacing.UP.getIndex()] = UP;
      field_178199_k[EnumFacing.NORTH.getIndex()] = NORTH;
      field_178199_k[EnumFacing.SOUTH.getIndex()] = SOUTH;
      field_178199_k[EnumFacing.WEST.getIndex()] = WEST;
      field_178199_k[EnumFacing.EAST.getIndex()] = EAST;
   }
}
