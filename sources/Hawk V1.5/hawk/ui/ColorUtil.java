package hawk.ui;

import java.awt.Color;

public class ColorUtil {
   public static int getRainbow(float var0, float var1, float var2) {
      float var3 = (float)(System.currentTimeMillis() % (long)((int)(var0 * 1000.0F))) / (var0 * 1000.0F);
      int var4 = Color.HSBtoRGB(var3, var1, var2);
      return var4;
   }

   public static int getRainbow(float var0, float var1, float var2, long var3) {
      float var5 = (float)((System.currentTimeMillis() + var3) % (long)((int)(var0 * 1000.0F))) / (var0 * 1000.0F);
      int var6 = Color.HSBtoRGB(var5, var1, var2);
      return var6;
   }
}
