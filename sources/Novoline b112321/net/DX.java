package net;

import cc.novoline.viaversion.utils.Util;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;

public class DX {
   private static String[] b;

   public static ChannelPipeline a(ChannelPipeline var0, String var1, String var2, ChannelHandler var3) {
      return Util.decodeEncodePlacement(var0, var1, var2, var3);
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new String[2]);
      }

   }
}
