package net.minecraft.client.util;

import java.nio.FloatBuffer;
import java.util.Comparator;

public class QuadComparator implements Comparator {
   private int field_178079_e;
   private float field_147629_c;
   private float field_147628_b;
   private FloatBuffer field_147627_d;
   private static final String __OBFID = "CL_00000958";
   private float field_147630_a;

   public int compare(Integer var1, Integer var2) {
      float var3 = this.field_147627_d.get(var1) - this.field_147630_a;
      float var4 = this.field_147627_d.get(var1 + 1) - this.field_147628_b;
      float var5 = this.field_147627_d.get(var1 + 2) - this.field_147629_c;
      float var6 = this.field_147627_d.get(var1 + this.field_178079_e) - this.field_147630_a;
      float var7 = this.field_147627_d.get(var1 + this.field_178079_e + 1) - this.field_147628_b;
      float var8 = this.field_147627_d.get(var1 + this.field_178079_e + 2) - this.field_147629_c;
      float var9 = this.field_147627_d.get(var1 + this.field_178079_e * 2) - this.field_147630_a;
      float var10 = this.field_147627_d.get(var1 + this.field_178079_e * 2 + 1) - this.field_147628_b;
      float var11 = this.field_147627_d.get(var1 + this.field_178079_e * 2 + 2) - this.field_147629_c;
      float var12 = this.field_147627_d.get(var1 + this.field_178079_e * 3) - this.field_147630_a;
      float var13 = this.field_147627_d.get(var1 + this.field_178079_e * 3 + 1) - this.field_147628_b;
      float var14 = this.field_147627_d.get(var1 + this.field_178079_e * 3 + 2) - this.field_147629_c;
      float var15 = this.field_147627_d.get(var2) - this.field_147630_a;
      float var16 = this.field_147627_d.get(var2 + 1) - this.field_147628_b;
      float var17 = this.field_147627_d.get(var2 + 2) - this.field_147629_c;
      float var18 = this.field_147627_d.get(var2 + this.field_178079_e) - this.field_147630_a;
      float var19 = this.field_147627_d.get(var2 + this.field_178079_e + 1) - this.field_147628_b;
      float var20 = this.field_147627_d.get(var2 + this.field_178079_e + 2) - this.field_147629_c;
      float var21 = this.field_147627_d.get(var2 + this.field_178079_e * 2) - this.field_147630_a;
      float var22 = this.field_147627_d.get(var2 + this.field_178079_e * 2 + 1) - this.field_147628_b;
      float var23 = this.field_147627_d.get(var2 + this.field_178079_e * 2 + 2) - this.field_147629_c;
      float var24 = this.field_147627_d.get(var2 + this.field_178079_e * 3) - this.field_147630_a;
      float var25 = this.field_147627_d.get(var2 + this.field_178079_e * 3 + 1) - this.field_147628_b;
      float var26 = this.field_147627_d.get(var2 + this.field_178079_e * 3 + 2) - this.field_147629_c;
      float var27 = (var3 + var6 + var9 + var12) * 0.25F;
      float var28 = (var4 + var7 + var10 + var13) * 0.25F;
      float var29 = (var5 + var8 + var11 + var14) * 0.25F;
      float var30 = (var15 + var18 + var21 + var24) * 0.25F;
      float var31 = (var16 + var19 + var22 + var25) * 0.25F;
      float var32 = (var17 + var20 + var23 + var26) * 0.25F;
      float var33 = var27 * var27 + var28 * var28 + var29 * var29;
      float var34 = var30 * var30 + var31 * var31 + var32 * var32;
      return Float.compare(var34, var33);
   }

   public QuadComparator(FloatBuffer var1, float var2, float var3, float var4, int var5) {
      this.field_147627_d = var1;
      this.field_147630_a = var2;
      this.field_147628_b = var3;
      this.field_147629_c = var4;
      this.field_178079_e = var5;
   }

   public int compare(Object var1, Object var2) {
      return this.compare((Integer)var1, (Integer)var2);
   }
}
