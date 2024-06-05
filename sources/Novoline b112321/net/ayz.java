package net;

import com.google.gson.JsonElement;
import viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import viaversion.viaversion.api.protocol.ClientboundPacketType;

public class ayz {
   private static String b;

   public static JsonElement a(TranslatableRewriter var0, String var1) {
      return var0.a(var1);
   }

   public static void a(TranslatableRewriter var0, JsonElement var1) {
      var0.processText(var1);
   }

   public static void e(TranslatableRewriter var0, ClientboundPacketType var1) {
      var0.b(var1);
   }

   public static void f(TranslatableRewriter var0, ClientboundPacketType var1) {
      var0.registerChatMessage(var1);
   }

   public static void b(TranslatableRewriter var0, ClientboundPacketType var1) {
      var0.c(var1);
   }

   public static void g(TranslatableRewriter var0, ClientboundPacketType var1) {
      var0.registerDisconnect(var1);
   }

   public static void h(TranslatableRewriter var0, ClientboundPacketType var1) {
      var0.registerTabList(var1);
   }

   public static void d(TranslatableRewriter var0, ClientboundPacketType var1) {
      var0.d(var1);
   }

   public static void a(TranslatableRewriter var0) {
      var0.registerPing();
   }

   public static void a() {
      TranslatableRewriter.loadTranslatables();
   }

   public static void c(TranslatableRewriter var0, ClientboundPacketType var1) {
      var0.registerOpenWindow(var1);
   }

   public static void a(TranslatableRewriter var0, ClientboundPacketType var1) {
      var0.registerLegacyOpenWindow(var1);
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() == null) {
         b("DNB1w");
      }

   }
}
