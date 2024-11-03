package net.augustus.font.testfontbase;

import java.awt.Font;
import java.io.InputStream;
import java.util.HashMap;
import net.augustus.font.CustomFontUtil;

public class FontUtil {
   public static volatile int completed;
   public static CustomFontUtil espHotbar;
   public static CustomFontUtil esp;
   public static CustomFontUtil espTitle;
   public static CustomFontUtil verdana;
   public static CustomFontUtil arial;
   public static CustomFontUtil roboto;
   public static CustomFontUtil comfortaa;
   private static HashMap<String, InputStream> fontInputs = new HashMap<>();

   private static InputStream getInputSteam(String location) {
      InputStream is = null;
      if (!fontInputs.containsKey(location)) {
         is = FontUtil.class.getClassLoader().getResourceAsStream(location);
         fontInputs.put(location, is);
      } else {
         is = fontInputs.get(location);
      }

      return is;
   }

   private static Font getFont(String location, int size) {
      Font font = null;

      try {
         InputStream is = FontUtil.class.getClassLoader().getResourceAsStream(location);
         font = Font.createFont(0, is);
         font = font.deriveFont(0, (float)size);
      } catch (Exception var4) {
         var4.printStackTrace();
         System.err.println("Error loading font");
         font = new Font("default", 0, 10);
      }

      return font;
   }

   public static void bootstrap() {
      espHotbar = new CustomFontUtil("Esp", getFont("esp.ttf", 44));
      esp = new CustomFontUtil("Esp", getFont("esp.ttf", 16));
      verdana = new CustomFontUtil("Verdana", getFont("verdana.ttf", 16));
      arial = new CustomFontUtil("Arial", getFont("arial.ttf", 16));
      roboto = new CustomFontUtil("Roboto", getFont("roboto.ttf", 16));
      espTitle = new CustomFontUtil("Esp", getFont("esp.ttf", 60));
      comfortaa = new CustomFontUtil("Comfortaa", getFont("comfortaa.ttf", 16));
   }
}
