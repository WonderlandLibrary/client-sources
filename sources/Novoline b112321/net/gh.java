package net;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.GenericFutureListener;

public class gh {
   public static ChannelFuture a(ChannelFuture var0, GenericFutureListener[] var1) {
      return var0.addListeners(var1);
   }

   public static ChannelFuture a(ChannelFuture var0, GenericFutureListener var1) {
      return var0.addListener(var1);
   }

   public static ChannelFuture a(ChannelFuture var0) {
      return var0.awaitUninterruptibly();
   }

   public static ChannelFuture c(ChannelFuture var0) {
      return var0.syncUninterruptibly();
   }

   public static Channel d(ChannelFuture var0) {
      return var0.channel();
   }

   public static ChannelFuture b(ChannelFuture var0) {
      return var0.sync();
   }
}
