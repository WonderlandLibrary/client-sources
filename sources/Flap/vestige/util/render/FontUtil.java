package vestige.util.render;

import java.util.function.Supplier;
import net.minecraft.client.gui.FontRenderer;
import vestige.Flap;
import vestige.font.VestigeFontRenderer;
import vestige.setting.impl.ModeSetting;
import vestige.util.IMinecraft;

public class FontUtil implements IMinecraft {
   private static FontRenderer mcFont;
   private static VestigeFontRenderer productSans;
   private static VestigeFontRenderer comfortaa;

   public static ModeSetting getFontSetting() {
      return new ModeSetting("Font", "Minecraft", new String[]{"Minecraft", "Product sans", "Comfortaa"});
   }

   public static ModeSetting getFontSetting(Supplier<Boolean> visibility) {
      return new ModeSetting("Font", visibility, "Minecraft", new String[]{"Minecraft", "Product sans", "Comfortaa"});
   }

   public static void initFonts() {
      mcFont = mc.fontRendererObj;
      productSans = Flap.instance.getFontManager().getProductSans();
      comfortaa = Flap.instance.getFontManager().getComfortaa();
   }

   public static void drawString(String font, String text, float x, float y, int color) {
      byte var6 = -1;
      switch(font.hashCode()) {
      case -1595926131:
         if (font.equals("Minecraft")) {
            var6 = 0;
         }
         break;
      case 317902540:
         if (font.equals("Comfortaa")) {
            var6 = 2;
         }
         break;
      case 1120662852:
         if (font.equals("Product sans")) {
            var6 = 1;
         }
      }

      switch(var6) {
      case 0:
         mcFont.drawString(text, x, y, color);
         break;
      case 1:
         productSans.drawString(text, x, y, color);
         break;
      case 2:
         comfortaa.drawString(text, x, y, color);
      }

   }

   public static void drawStringWithShadow(String font, String text, float x, float y, int color) {
      byte var6 = -1;
      switch(font.hashCode()) {
      case -1595926131:
         if (font.equals("Minecraft")) {
            var6 = 0;
         }
         break;
      case 317902540:
         if (font.equals("Comfortaa")) {
            var6 = 2;
         }
         break;
      case 1120662852:
         if (font.equals("Product sans")) {
            var6 = 1;
         }
      }

      switch(var6) {
      case 0:
         mcFont.drawStringWithShadow(text, x, y, color);
         break;
      case 1:
         productSans.drawStringWithShadow(text, x, y, color);
         break;
      case 2:
         comfortaa.drawStringWithShadow(text, x, y, color);
      }

   }

   public static double getStringWidth(String font, String s) {
      byte var3 = -1;
      switch(font.hashCode()) {
      case -1595926131:
         if (font.equals("Minecraft")) {
            var3 = 2;
         }
         break;
      case 317902540:
         if (font.equals("Comfortaa")) {
            var3 = 1;
         }
         break;
      case 1120662852:
         if (font.equals("Product sans")) {
            var3 = 0;
         }
      }

      switch(var3) {
      case 0:
         return productSans.getStringWidth(s);
      case 1:
         return comfortaa.getStringWidth(s);
      case 2:
      default:
         return (double)mc.fontRendererObj.getStringWidth(s);
      }
   }

   public static int getFontHeight(String font) {
      byte var2 = -1;
      switch(font.hashCode()) {
      case -1595926131:
         if (font.equals("Minecraft")) {
            var2 = 2;
         }
         break;
      case 317902540:
         if (font.equals("Comfortaa")) {
            var2 = 1;
         }
         break;
      case 1120662852:
         if (font.equals("Product sans")) {
            var2 = 0;
         }
      }

      switch(var2) {
      case 0:
         return productSans.getHeight();
      case 1:
         return comfortaa.getHeight();
      case 2:
      default:
         return mc.fontRendererObj.FONT_HEIGHT;
      }
   }
}
