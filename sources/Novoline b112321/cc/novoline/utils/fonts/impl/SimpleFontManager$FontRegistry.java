package cc.novoline.utils.fonts.impl;

import cc.novoline.utils.SneakyThrowing;
import cc.novoline.utils.fonts.api.FontFamily;
import cc.novoline.utils.fonts.api.FontType;
import cc.novoline.utils.fonts.impl.SimpleFontFamily;
import cc.novoline.utils.fonts.impl.SimpleFontManager$1;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.function.Function;

final class SimpleFontManager$FontRegistry extends EnumMap {
   private SimpleFontManager$FontRegistry() {
      super(FontType.class);
   }

   private FontFamily fontFamily(FontType var1) {
      return (FontFamily)this.computeIfAbsent(var1, SimpleFontManager$FontRegistry::lambda$fontFamily$0);
   }

   private static Font readFontFromResources(FontType param0) throws IOException {
      // $FF: Couldn't be decompiled
   }

   private static Font readFont(InputStream var0) {
      byte var10000 = 0;
      InputStream var10001 = var0;

      try {
         Font var1 = Font.createFont(var10000, var10001);
         return var1;
      } catch (FontFormatException var3) {
         throw new RuntimeException("Resource does not contain the required font tables for the specified format", var3);
      } catch (IOException var4) {
         throw new RuntimeException("Couldn\'t completely read font resource", var4);
      }
   }

   private static FontFamily lambda$fontFamily$0(FontType var0, FontType var1) {
      FontType var10000 = var0;
      FontType var10001 = var0;

      try {
         return SimpleFontFamily.create(var10000, readFontFromResources(var10001));
      } catch (IOException var3) {
         throw SneakyThrowing.sneakyThrow(var3);
      }
   }

   SimpleFontManager$FontRegistry(SimpleFontManager$1 var1) {
      this();
   }

   static FontFamily access$100(SimpleFontManager$FontRegistry var0, FontType var1) {
      return var0.fontFamily(var1);
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
