package cc.novoline.modules.configurations.property.object;

import cc.novoline.modules.configurations.property.object.IntProperty;
import java.awt.Color;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ColorProperty extends IntProperty {
   public ColorProperty(@Nullable Integer var1) {
      super(var1);
   }

   @NotNull
   public static ColorProperty of(@Nullable Integer var0) {
      return new ColorProperty(var0);
   }

   public void set(@Nullable Integer var1) {
      this.setARGB(var1);
   }

   public void d(@Nullable Integer var1) {
      int[] var2 = IntProperty.a();
      this.value = var1;
   }

   @Nullable
   public Integer getARGB() {
      return (Integer)this.value;
   }

   public void setARGB(@Nullable Integer var1) {
      this.value = var1;
   }

   public Integer get() {
      return this.getARGB();
   }

   public int h() {
      int[] var1 = IntProperty.a();
      return this.value != null?((Integer)this.value).intValue() >>> 16 & 255:0;
   }

   public int f() {
      int[] var1 = IntProperty.a();
      return this.value != null?((Integer)this.value).intValue() >>> 8 & 255:0;
   }

   public int c() {
      int[] var1 = IntProperty.a();
      return this.value != null?((Integer)this.value).intValue() & 255:0;
   }

   public int getAlpha() {
      int[] var1 = IntProperty.a();
      return this.value != null?((Integer)this.value).intValue() >>> 24:0;
   }

   @Nullable
   public Color getAwtColor() {
      return this.value == null?null:new Color(((Integer)this.value).intValue(), true);
   }

   public float[] getHSB() {
      int[] var1 = IntProperty.a();
      if(this.value == null) {
         return new float[]{0.0F, 0.0F, 0.0F};
      } else {
         float[] var2 = new float[3];
         int var6 = Math.max(((Integer)this.value).intValue() >>> 16 & 255, ((Integer)this.value).intValue() >>> 8 & 255);
         if((((Integer)this.value).intValue() & 255) > var6) {
            var6 = ((Integer)this.value).intValue() & 255;
         }

         int var7 = Math.min(((Integer)this.value).intValue() >>> 16 & 255, ((Integer)this.value).intValue() >>> 8 & 255);
         if((((Integer)this.value).intValue() & 255) < var7) {
            var7 = ((Integer)this.value).intValue() & 255;
         }

         float var4 = (float)var6 / 255.0F;
         float var3 = var6 != 0?(float)(var6 - var7) / (float)var6:0.0F;
         if(var3 == 0.0F) {
            float var5 = 0.0F;
         }

         float var8 = (float)(var6 - (((Integer)this.value).intValue() >>> 16 & 255)) / (float)(var6 - var7);
         float var9 = (float)(var6 - (((Integer)this.value).intValue() >>> 8 & 255)) / (float)(var6 - var7);
         float var10 = (float)(var6 - (((Integer)this.value).intValue() & 255)) / (float)(var6 - var7);
         float var11 = ((((Integer)this.value).intValue() >>> 16 & 255) == var6?var10 - var9:((((Integer)this.value).intValue() >>> 8 & 255) == var6?2.0F + var8 - var10:4.0F + var9 - var8)) / 6.0F;
         if(var11 < 0.0F) {
            ++var11;
         }

         var2[0] = var11;
         var2[1] = var3;
         var2[2] = var4;
         return var2;
      }
   }
}
