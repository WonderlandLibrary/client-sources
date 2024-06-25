package cc.slack.utils.font;

import cc.slack.utils.other.PrintUtil;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class Fonts {
   public static final MCFontRenderer IconFont = new MCFontRenderer(fontFromTTF("guiicons.ttf", 24.0F), true, true);
   public static final MCFontRenderer IconFontBig = new MCFontRenderer(fontFromTTF("guiicons.ttf", 38.0F), true, true);
   public static final MCFontRenderer SFBold30 = new MCFontRenderer(fontFromTTF("sfsemibold.ttf", 30.0F), true, true);
   public static final MCFontRenderer SFBold18 = new MCFontRenderer(fontFromTTF("sfsemibold.ttf", 18.0F), true, true);
   public static final MCFontRenderer SFReg18 = new MCFontRenderer(fontFromTTF("sfregular.ttf", 18.0F), true, true);
   public static final MCFontRenderer SFReg24 = new MCFontRenderer(fontFromTTF("sfregular.ttf", 24.0F), true, true);
   public static final MCFontRenderer SFReg45 = new MCFontRenderer(fontFromTTF("sfregular.ttf", 45.0F), true, true);
   public static final MCFontRenderer axi24 = new MCFontRenderer(fontFromTTF("axi.ttf", 24.0F), true, false);
   public static final MCFontRenderer axi12 = new MCFontRenderer(fontFromTTF("axi.ttf", 12.0F), true, false);
   public static final MCFontRenderer axi16 = new MCFontRenderer(fontFromTTF("axi.ttf", 16.0F), true, false);
   public static final MCFontRenderer axi18 = new MCFontRenderer(fontFromTTF("axi.ttf", 18.0F), true, false);
   public static final MCFontRenderer axi45 = new MCFontRenderer(fontFromTTF("axi.ttf", 45.0F), true, false);
   public static final MCFontRenderer apple18 = new MCFontRenderer(fontFromTTF("apple.ttf", 18.0F), true, false);
   public static final MCFontRenderer apple20 = new MCFontRenderer(fontFromTTF("apple.ttf", 20.0F), true, false);
   public static final MCFontRenderer apple24 = new MCFontRenderer(fontFromTTF("apple.ttf", 24.0F), true, false);
   public static final MCFontRenderer Arial18 = new MCFontRenderer(new Font("Arial", 0, 18), true, true);
   public static final MCFontRenderer Arial45 = new MCFontRenderer(new Font("Arial", 0, 45), true, false);
   public static final MCFontRenderer Arial65 = new MCFontRenderer(new Font("Arial", 0, 65), true, false);
   public static final MCFontRenderer Checkmark = new MCFontRenderer(fontFromTTF("checkmark.ttf", 24.0F), true, false);

   private static Font fontFromTTF(String fileName, float fontSize) {
      PrintUtil.print("Initializing Font: " + fileName + " | Size: " + fontSize);
      Font output = null;

      try {
         output = Font.createFont(0, Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("slack/fonts/" + fileName)).getInputStream());
         output = output.deriveFont(fontSize);
      } catch (IOException | FontFormatException var4) {
      }

      return output;
   }
}
