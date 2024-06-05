package net.minecraft.client.renderer.block.model;

import net.minecraft.client.renderer.block.model.ItemModelGenerator$SpanFacing;

class ItemModelGenerator$Span {
   private final ItemModelGenerator$SpanFacing spanFacing;
   private int field_178387_b;
   private int field_178388_c;
   private final int field_178386_d;

   public ItemModelGenerator$Span(ItemModelGenerator$SpanFacing var1, int var2, int var3) {
      this.spanFacing = var1;
      this.field_178387_b = var2;
      this.field_178388_c = var2;
      this.field_178386_d = var3;
   }

   public void func_178382_a(int var1) {
      if(var1 < this.field_178387_b) {
         this.field_178387_b = var1;
      } else if(var1 > this.field_178388_c) {
         this.field_178388_c = var1;
      }

   }

   public ItemModelGenerator$SpanFacing func_178383_a() {
      return this.spanFacing;
   }

   public int func_178385_b() {
      return this.field_178387_b;
   }

   public int func_178384_c() {
      return this.field_178388_c;
   }

   public int func_178381_d() {
      return this.field_178386_d;
   }
}
