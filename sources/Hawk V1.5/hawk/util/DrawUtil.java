package hawk.util;

import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

public class DrawUtil extends GuiScreen {
   public static DrawUtil instance = new DrawUtil();

   public static void setColor(int var0) {
      float var1 = (float)(var0 >> 24 & 255) / 255.0F;
      float var2 = (float)(var0 >> 16 & 255) / 255.0F;
      float var3 = (float)(var0 >> 8 & 255) / 255.0F;
      float var4 = (float)(var0 & 255) / 255.0F;
      GL11.glColor4f(var2, var3, var4, var1);
   }

   public static void drawBorderedRoundedRect(float var0, float var1, float var2, float var3, float var4, int var5, int var6) {
      drawRoundedRect((double)var0, (double)var1, (double)var2, (double)var3, (double)var4, var5);
      drawRoundedRect((double)(var0 + 0.5F), (double)(var1 + 0.5F), (double)(var2 - 0.5F), (double)(var3 - 0.5F), (double)var4, var6);
   }

   public static void drawBorderedRoundedRect(float var0, float var1, float var2, float var3, float var4, float var5, int var6, int var7) {
      drawRoundedRect((double)var0, (double)var1, (double)var2, (double)var3, (double)var4, var6);
      drawRoundedRect((double)(var0 + var5), (double)(var1 + var5), (double)(var2 - var5), (double)(var3 - var5), (double)var4, var7);
   }

   public static void drawRoundedRect(double var0, double var2, double var4, double var6, double var8, int var10) {
      GL11.glPushAttrib(0);
      GL11.glScaled(0.5D, 0.5D, 0.5D);
      var0 *= 2.0D;
      var2 *= 2.0D;
      var4 *= 2.0D;
      var6 *= 2.0D;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      setColor(var10);
      GL11.glEnable(2848);
      GL11.glBegin(9);

      int var11;
      for(var11 = 0; var11 <= 90; var11 += 3) {
         GL11.glVertex2d(var0 + var8 + Math.sin((double)var11 * 3.141592653589793D / 180.0D) * var8 * -1.0D, var2 + var8 + Math.cos((double)var11 * 3.141592653589793D / 180.0D) * var8 * -1.0D);
      }

      for(var11 = 90; var11 <= 180; var11 += 3) {
         GL11.glVertex2d(var0 + var8 + Math.sin((double)var11 * 3.141592653589793D / 180.0D) * var8 * -1.0D, var6 - var8 + Math.cos((double)var11 * 3.141592653589793D / 180.0D) * var8 * -1.0D);
      }

      for(var11 = 0; var11 <= 90; var11 += 3) {
         GL11.glVertex2d(var4 - var8 + Math.sin((double)var11 * 3.141592653589793D / 180.0D) * var8, var6 - var8 + Math.cos((double)var11 * 3.141592653589793D / 180.0D) * var8);
      }

      for(var11 = 90; var11 <= 180; var11 += 3) {
         GL11.glVertex2d(var4 - var8 + Math.sin((double)var11 * 3.141592653589793D / 180.0D) * var8, var2 + var8 + Math.cos((double)var11 * 3.141592653589793D / 180.0D) * var8);
      }

      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      GL11.glScaled(2.0D, 2.0D, 2.0D);
      GL11.glPopAttrib();
   }
}
