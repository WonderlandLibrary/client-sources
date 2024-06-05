package cc.novoline.viaversion.utils;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;

public class Util {
   private static String b;

   public static ChannelPipeline decodeEncodePlacement(ChannelPipeline var0, String var1, String var2, ChannelHandler var3) {
      b();
      byte var6 = -1;
      switch(var1.hashCode()) {
      case 1542433860:
         if(!var1.equals("decoder")) {
            break;
         }

         var6 = 0;
      case -1607367396:
         if(var1.equals("encoder")) {
            var6 = 1;
         }
      }

      switch(var6) {
      case 0:
         if(var0.get("via-decoder") == null) {
            break;
         }

         var1 = "via-decoder";
      case 1:
         if(var0.get("via-encoder") != null) {
            var1 = "via-encoder";
         }
      }

      return var0.addBefore(var1, var2, var3);
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      b("AHhvEb");
   }
}
