package ru.FecuritySQ.font;


import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

public final class Fonts {
    public static GlyphPageFontRenderer mntsb;
   public static GlyphPageFontRenderer mntsb16;
   public static GlyphPageFontRenderer GREYCLIFF;
   public static GlyphPageFontRenderer mntsb40;
   public static GlyphPageFontRenderer MCR8;
   public static GlyphPageFontRenderer MCR14;
   public static GlyphPageFontRenderer dreamspace32;

   public static void initAll() {
         try {
            mntsb = GlyphPageFontRenderer.create("/assets/minecraft/FecuritySQ/fonts/mntsb.ttf" , 20, false, false, false);
            mntsb16 = GlyphPageFontRenderer.create("/assets/minecraft/FecuritySQ/fonts/mntsb.ttf" , 16, true, false, true);
            GREYCLIFF = GlyphPageFontRenderer.create("/assets/minecraft/FecuritySQ/fonts/greycliff.ttf", 16, false, false, false);
            mntsb40 = GlyphPageFontRenderer.create("/assets/minecraft/FecuritySQ/fonts/mntsb.ttf", 40, true, false, false);
            MCR8 = GlyphPageFontRenderer.create("/assets/minecraft/FecuritySQ/fonts/minecraftrus.ttf", 12, true, false, false);
            MCR14 = GlyphPageFontRenderer.create("/assets/minecraft/FecuritySQ/fonts/minecraftrus.ttf", 14, true, false, false);
            dreamspace32 = GlyphPageFontRenderer.create("/assets/minecraft/FecuritySQ/fonts/dreamspace.ttf", 32, true, false, false);
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   }