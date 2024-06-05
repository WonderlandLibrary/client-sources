package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import net.Jr;
import net.Rr;
import net.aPI;
import net.awr;

public enum ah8 implements aPI {
   HASH(new Jr("#")),
   DOUBLE_SLASH(new Jr("//")),
   SLASH_BLOCK(new Rr("/*", "*/", "*")),
   XML_STYLE(new Rr("<!--", "-->", "~"));

   private static final int c = 4096;
   private final aPI b;
   private static final ah8[] a = new ah8[]{HASH, DOUBLE_SLASH, SLASH_BLOCK, XML_STYLE};

   private ah8(aPI var3) {
      this.b = var3;
   }

   public Optional a(BufferedReader var1) throws IOException {
      return this.b.a(var1);
   }

   public Collection a(Collection var1) {
      return this.b.a(var1);
   }

   public static String a(BufferedReader var0, aPI... var1) throws IOException {
      awr.d();
      var0.mark(4096);
      int var4 = var1.length;
      byte var5 = 0;
      if(var5 < var4) {
         aPI var6 = var1[var5];
         Optional var7 = var6.a(var0);
         if(!var7.isPresent()) {
            var0.reset();
         }

         return (String)var7.get();
      } else {
         return null;
      }
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
