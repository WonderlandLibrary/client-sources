package net.minecraft.util;

import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;

public class Rotations {
   protected final float field_179418_c;
   protected final float field_179419_a;
   protected final float field_179417_b;
   private static final String __OBFID = "CL_00002316";

   public float func_179413_d() {
      return this.field_179418_c;
   }

   public Rotations(NBTTagList var1) {
      this.field_179419_a = var1.getFloat(0);
      this.field_179417_b = var1.getFloat(1);
      this.field_179418_c = var1.getFloat(2);
   }

   public float func_179415_b() {
      return this.field_179419_a;
   }

   public Rotations(float var1, float var2, float var3) {
      this.field_179419_a = var1;
      this.field_179417_b = var2;
      this.field_179418_c = var3;
   }

   public NBTTagList func_179414_a() {
      NBTTagList var1 = new NBTTagList();
      var1.appendTag(new NBTTagFloat(this.field_179419_a));
      var1.appendTag(new NBTTagFloat(this.field_179417_b));
      var1.appendTag(new NBTTagFloat(this.field_179418_c));
      return var1;
   }

   public float func_179416_c() {
      return this.field_179417_b;
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof Rotations)) {
         return false;
      } else {
         Rotations var2 = (Rotations)var1;
         return this.field_179419_a == var2.field_179419_a && this.field_179417_b == var2.field_179417_b && this.field_179418_c == var2.field_179418_c;
      }
   }
}
