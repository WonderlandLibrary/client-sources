package xyz.cucumber.base.utils.render;

import java.awt.Font;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class Fonts {
   private static HashMap<String[], FontUtils> fonts = new HashMap<>();

   public Fonts() {
      fonts.put(new String[]{"rb-r"}, new FontUtils(getResource(30.0F, false, new ResourceLocation(this.getLocation("Roboto-Regular")))));
      fonts.put(new String[]{"rb-m"}, new FontUtils(getResource(30.0F, false, new ResourceLocation(this.getLocation("Roboto-Medium")))));
      fonts.put(new String[]{"rb-b"}, new FontUtils(getResource(30.0F, false, new ResourceLocation(this.getLocation("Roboto-Bold")))));
      fonts.put(new String[]{"rb-b-h"}, new FontUtils(getResource(44.0F, false, new ResourceLocation(this.getLocation("Roboto-Bold")))));
      fonts.put(new String[]{"rb-r-13"}, new FontUtils(getResource(26.0F, false, new ResourceLocation(this.getLocation("Roboto-Regular")))));
      fonts.put(new String[]{"rb-m-13"}, new FontUtils(getResource(26.0F, false, new ResourceLocation(this.getLocation("Roboto-Medium")))));
      fonts.put(new String[]{"rb-b-13"}, new FontUtils(getResource(26.0F, false, new ResourceLocation(this.getLocation("Roboto-Bold")))));
      fonts.put(new String[]{"comforta"}, new FontUtils(getResource(30.0F, false, new ResourceLocation(this.getLocation("comfortaa")))));
      fonts.put(new String[]{"8-bits"}, new FontUtils(getResource(30.0F, false, new ResourceLocation(this.getLocation("EightBits")))));
      fonts.put(new String[]{"orbitron"}, new FontUtils(getResource(30.0F, false, new ResourceLocation(this.getLocation("Orbitron")))));
      fonts.put(new String[]{"volte"}, new FontUtils(getResource(30.0F, false, new ResourceLocation(this.getLocation("Volte-Semibold")))));
      fonts.put(new String[]{"minecraft"}, new FontUtils(getResource(26.0F, false, new ResourceLocation(this.getLocation("minecraft")))));
      fonts.put(new String[]{"bebas"}, new FontUtils(getResource(30.0F, false, new ResourceLocation(this.getLocation("bebas")))));
      fonts.put(new String[]{"mitr"}, new FontUtils(getResource(30.0F, false, new ResourceLocation(this.getLocation("Mitr")))));
   }

   public static FontUtils getFont(String font) {
      for (Entry<String[], FontUtils> entry : fonts.entrySet()) {
         for (String s : Arrays.asList(entry.getKey())) {
            boolean valid = s.toLowerCase().equals(font.toLowerCase());
            if (valid) {
               return entry.getValue();
            }
         }
      }

      return null;
   }

   private String getLocation(String name) {
      return "client/fonts/" + name + ".ttf";
   }

   private static Font getResource(float size, boolean bold, ResourceLocation fontFile) {
      Font font = null;

      try {
         InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(fontFile).getInputStream();
         font = Font.createFont(0, is);
         font = font.deriveFont(0, size);
      } catch (Exception var5) {
         var5.printStackTrace();
         System.out.println("Error loading font, setted to verdana.");
         font = new Font("Verdana", 0, 12);
      }

      return font;
   }
}
