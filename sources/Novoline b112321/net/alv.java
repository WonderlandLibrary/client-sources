package net;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoop;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import java.net.SocketAddress;

public class alv {
   private static String b;

   public static ChannelPipeline e(Channel var0) {
      return var0.pipeline();
   }

   public static EventLoop a(Channel var0) {
      return var0.eventLoop();
   }

   public static ChannelFuture c(Channel var0) {
      return var0.newSucceededFuture();
   }

   public static boolean i(Channel var0) {
      return var0.isOpen();
   }

   public static ChannelFuture b(Channel var0) {
      return var0.close();
   }

   public static Attribute a(Channel var0, AttributeKey var1) {
      return var0.attr(var1);
   }

   public static SocketAddress d(Channel var0) {
      return var0.remoteAddress();
   }

   public static ChannelConfig k(Channel var0) {
      return var0.config();
   }

   public static ChannelFuture a(Channel var0, Object var1) {
      return var0.writeAndFlush(var1);
   }

   public static Channel f(Channel var0) {
      return var0.flush();
   }

   public static ChannelFuture h(Channel var0) {
      return var0.closeFuture();
   }

   public static ByteBufAllocator g(Channel var0) {
      return var0.alloc();
   }

   public static ChannelFuture a(Channel var0, Throwable var1) {
      return var0.newFailedFuture(var1);
   }

   public static SocketAddress j(Channel var0) {
      return var0.localAddress();
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() == null) {
         b("Suz2Eb");
      }

   }
}
