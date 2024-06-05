package net;

import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.util.ResourceLocation;

public class qj {
   private final ResourceLocation d;
   private final ModelRotation a;
   private final boolean c;
   private final int b;

   public qj(ResourceLocation var1, ModelRotation var2, boolean var3, int var4) {
      this.d = var1;
      this.a = var2;
      this.c = var3;
      this.b = var4;
   }

   public ResourceLocation c() {
      return this.d;
   }

   public ModelRotation d() {
      return this.a;
   }

   public boolean a() {
      return this.c;
   }

   public int b() {
      return this.b;
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof qj)) {
         return false;
      } else {
         qj var2 = (qj)var1;
         return this.d.equals(var2.d) && this.a == var2.a && this.c == var2.c;
      }
   }

   public int hashCode() {
      int var1 = this.d.hashCode();
      var1 = 31 * var1 + (this.a != null?this.a.hashCode():0);
      var1 = 31 * var1 + (this.c?1:0);
      return var1;
   }
}
