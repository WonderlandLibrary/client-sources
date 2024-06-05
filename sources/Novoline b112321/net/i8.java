package net;

import viaversion.viaversion.api.protocol.ClientboundPacketType;
import viaversion.viaversion.api.rewriters.RegistryType;
import viaversion.viaversion.api.rewriters.TagRewriter;

public class i8 {
   public static void a(TagRewriter var0, ClientboundPacketType var1) {
      var0.register(var1);
   }

   public static void a(TagRewriter var0, RegistryType var1, String var2, int[] var3) {
      var0.addTag(var1, var2, var3);
   }

   public static void a(TagRewriter var0, RegistryType var1, String var2) {
      var0.addEmptyTag(var1, var2);
   }

   public static void a(TagRewriter var0, RegistryType var1, String[] var2) {
      var0.addEmptyTags(var1, var2);
   }
}
