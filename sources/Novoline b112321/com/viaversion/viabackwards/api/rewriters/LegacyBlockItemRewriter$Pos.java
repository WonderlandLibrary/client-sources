package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.rewriters.LegacyBlockItemRewriter$1;
import net.aqu;

final class LegacyBlockItemRewriter$Pos {
   private final int x;
   private final short y;
   private final int z;

   private LegacyBlockItemRewriter$Pos(int var1, int var2, int var3) {
      this.x = var1;
      this.y = (short)var2;
      this.z = var3;
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public int getZ() {
      return this.z;
   }

   public boolean equals(Object var1) {
      int var2 = aqu.d();
      if(this == var1) {
         return true;
      } else if(var1 != null && this.getClass() == var1.getClass()) {
         LegacyBlockItemRewriter$Pos var3 = (LegacyBlockItemRewriter$Pos)var1;
         return this.x != var3.x?false:(this.y != var3.y?false:this.z == var3.z);
      } else {
         return false;
      }
   }

   public int hashCode() {
      int var1 = this.x;
      var1 = 31 * var1 + this.y;
      var1 = 31 * var1 + this.z;
      return var1;
   }

   public String toString() {
      return "Pos{x=" + this.x + ", y=" + this.y + ", z=" + this.z + '}';
   }

   LegacyBlockItemRewriter$Pos(int var1, int var2, int var3, LegacyBlockItemRewriter$1 var4) {
      this(var1, var2, var3);
   }
}
