package net;

import java.util.List;
import net.B7;
import net.P5;
import net.aAD;
import net.aFv;
import net.minecraft.util.MathHelper;

public class aa8 {
   public B7 b;
   public float a;
   public String c = null;

   public aa8(B7 var1, float var2) {
      this.b = var1;
      this.a = var2;
   }

   public aa8 a(String var1) {
      this.c = var1;
      return this;
   }

   public float a(float var1) {
      int var2 = P5.c();
      float var3 = this.c != null?aFv.a(this.c):this.a;
      float var4 = 0.0F;
      if(this.b == B7.ADD) {
         var4 = var1 + var3;
      }

      if(this.b == B7.SET) {
         var4 = var3;
      }

      if(this.b == B7.SUBSTRACT) {
         var4 = var1 - var3;
      }

      if(this.b == B7.MULTIPLY) {
         var4 = var1 * var3;
      }

      if(this.b == B7.DIVIDE) {
         var4 = var1 / var3;
      }

      return var4;
   }

   public static float a(aAD var0, float var1, List var2) {
      P5.b();
      float var4 = var1;
      int var5 = 0;
      if(var5 < var2.size()) {
         var4 = ((aa8)var2.get(var5)).a(var1);
         ++var5;
      }

      if(var0 == aAD.COS) {
         var4 = MathHelper.cos(var4);
      }

      if(var0 == aAD.SIN) {
         var4 = MathHelper.sin(var4);
      }

      return var4;
   }
}
