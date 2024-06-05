package net;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import java.util.List;
import java.util.Map;

public class a1_ {
   public static ChannelHandlerContext a(ChannelPipeline var0, String var1) {
      return var0.context(var1);
   }

   public static ChannelPipeline a(ChannelPipeline var0, Object var1) {
      return var0.fireChannelRead(var1);
   }

   public static ChannelPipeline a(ChannelPipeline var0, String var1, ChannelHandler var2) {
      return var0.addLast(var1, var2);
   }

   public static ChannelPipeline a(ChannelPipeline var0, String var1, String var2, ChannelHandler var3) {
      return var0.addBefore(var1, var2, var3);
   }

   public static ChannelHandler c(ChannelPipeline var0, String var1) {
      return var0.get(var1);
   }

   public static ChannelHandler b(ChannelPipeline var0, String var1) {
      return var0.remove(var1);
   }

   public static List b(ChannelPipeline var0) {
      return var0.names();
   }

   public static ChannelHandlerContext a(ChannelPipeline var0, ChannelHandler var1) {
      return var0.context(var1);
   }

   public static Map c(ChannelPipeline var0) {
      return var0.toMap();
   }

   public static ChannelPipeline a(ChannelPipeline var0, ChannelHandler[] var1) {
      return var0.addLast(var1);
   }

   public static ChannelHandler b(ChannelPipeline var0, String var1, String var2, ChannelHandler var3) {
      return var0.replace(var1, var2, var3);
   }

   public static ChannelHandlerContext a(ChannelPipeline var0) {
      return var0.firstContext();
   }
}
