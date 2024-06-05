package hawk.util;

import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;

public class JColor extends Color {
   private static final long serialVersionUID = 1L;

   public JColor(int var1, int var2, int var3) {
      super(var1, var2, var3);
   }

   public JColor(JColor var1, int var2) {
      super(var1.getRed(), var1.getGreen(), var1.getBlue(), var2);
   }

   public float getBrightness() {
      return RGBtoHSB(this.getRed(), this.getGreen(), this.getBlue(), (float[])null)[2];
   }

   public float getHue() {
      return RGBtoHSB(this.getRed(), this.getGreen(), this.getBlue(), (float[])null)[0];
   }

   public void glColor() {
      GlStateManager.color((float)this.getRed() / 255.0F, (float)this.getGreen() / 255.0F, (float)this.getBlue() / 255.0F, (float)this.getAlpha() / 255.0F);
   }

   public static JColor fromHSB(float var0, float var1, float var2) {
      return new JColor(Color.getHSBColor(var0, var1, var2));
   }

   public JColor(int var1) {
      super(var1);
   }

   public float getSaturation() {
      return RGBtoHSB(this.getRed(), this.getGreen(), this.getBlue(), (float[])null)[1];
   }

   public JColor(int var1, int var2, int var3, int var4) {
      super(var1, var2, var3, var4);
   }

   public JColor(int var1, boolean var2) {
      super(var1, var2);
   }

   public JColor(Color var1) {
      super(var1.getRed(), var1.getGreen(), var1.getBlue(), var1.getAlpha());
   }
}
