package net;

import net.ary;
import net.at_;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.util.IChatComponent;

public class apv {
   public static IChatComponent b(ServerStatusResponse var0) {
      return var0.getServerDescription();
   }

   public static at_ c(ServerStatusResponse var0) {
      return var0.d();
   }

   public static ary d(ServerStatusResponse var0) {
      return var0.c();
   }

   public static String a(ServerStatusResponse var0) {
      return var0.getFavicon();
   }

   public static void a(ServerStatusResponse var0, IChatComponent var1) {
      var0.setServerDescription(var1);
   }

   public static void a(ServerStatusResponse var0, ary var1) {
      var0.a(var1);
   }

   public static void a(ServerStatusResponse var0, at_ var1) {
      var0.a(var1);
   }

   public static void a(ServerStatusResponse var0, String var1) {
      var0.setFavicon(var1);
   }
}
