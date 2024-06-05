package net;

import com.google.common.base.Splitter;
import java.util.List;

public class afN {
   public static Splitter a(char var0) {
      return Splitter.on(var0);
   }

   public static Splitter a(Splitter var0, int var1) {
      return var0.limit(var1);
   }

   public static Iterable a(Splitter var0, CharSequence var1) {
      return var0.split(var1);
   }

   public static Splitter a(Splitter var0) {
      return var0.omitEmptyStrings();
   }

   public static Splitter a(String var0) {
      return Splitter.on(var0);
   }

   public static List b(Splitter var0, CharSequence var1) {
      return var0.splitToList(var1);
   }
}
