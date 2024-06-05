package net;

import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.NamedSoundRewriter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class lo {
   private static final Map a = new HashMap();

   public static String a(String var0) {
      return (String)a.get(var0);
   }

   private static void lambda$static$0(String var0, String var1) {
      String var10000 = (String)a.put(var1, var0);
   }

   static {
      NamedSoundRewriter.oldToNew.forEach(lo::lambda$static$0);
   }
}
