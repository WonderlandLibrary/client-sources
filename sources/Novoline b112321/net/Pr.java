package net;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.function.Function;
import net.TJ;
import net.aG6;
import net.aLF;
import net.jg;
import net.mt;

final class Pr extends EnumMap {
   private Pr() {
      super(mt.class);
   }

   private TJ a(mt var1) {
      return (TJ)this.computeIfAbsent(var1, Pr::lambda$fontFamily$0);
   }

   private static Font b(mt param0) throws IOException {
      // $FF: Couldn't be decompiled
   }

   private static Font a(InputStream var0) {
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

   private static TJ lambda$fontFamily$0(mt var0, mt var1) {
      mt var10000 = var0;
      mt var10001 = var0;

      try {
         return aG6.a(var10000, b(var10001));
      } catch (IOException var3) {
         throw aLF.a(var3);
      }
   }

   Pr(jg var1) {
      this();
   }

   static TJ a(Pr var0, mt var1) {
      return var0.a(var1);
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
