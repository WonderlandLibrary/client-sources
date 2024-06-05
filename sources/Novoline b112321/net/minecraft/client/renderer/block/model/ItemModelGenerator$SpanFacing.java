package net.minecraft.client.renderer.block.model;

import net.minecraft.util.EnumFacing;

enum ItemModelGenerator$SpanFacing {
   UP(EnumFacing.UP, 0, -1),
   DOWN(EnumFacing.DOWN, 0, 1),
   LEFT(EnumFacing.EAST, -1, 0),
   RIGHT(EnumFacing.WEST, 1, 0);

   private final EnumFacing facing;
   private final int field_178373_f;
   private final int field_178374_g;
   private static final ItemModelGenerator$SpanFacing[] $VALUES = new ItemModelGenerator$SpanFacing[]{UP, DOWN, LEFT, RIGHT};

   private ItemModelGenerator$SpanFacing(EnumFacing var3, int var4, int var5) {
      this.facing = var3;
      this.field_178373_f = var4;
      this.field_178374_g = var5;
   }

   public EnumFacing getFacing() {
      return this.facing;
   }

   public int func_178372_b() {
      return this.field_178373_f;
   }

   public int func_178371_c() {
      return this.field_178374_g;
   }

   private boolean func_178369_d() {
      return this == DOWN || this == UP;
   }

   static boolean access$000(ItemModelGenerator$SpanFacing var0) {
      return var0.func_178369_d();
   }
}
