package com.viaversion.viaversion.handlers;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.handlers.ViaCodecHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelProgressivePromise;
import io.netty.channel.ChannelPromise;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.EventExecutor;
import java.net.SocketAddress;

public class ChannelHandlerContextWrapper implements ChannelHandlerContext {
   private final ChannelHandlerContext base;
   private final ViaCodecHandler handler;
   private static int b;

   public ChannelHandlerContextWrapper(ChannelHandlerContext var1, ViaCodecHandler var2) {
      int var10000 = c();
      this.base = var1;
      this.handler = var2;
      int var3 = var10000;
      if(PacketRemapper.b() == null) {
         ++var3;
         b(var3);
      }

   }

   public Channel channel() {
      return this.base.channel();
   }

   public EventExecutor executor() {
      return this.base.executor();
   }

   public String name() {
      return this.base.name();
   }

   public ChannelHandler handler() {
      return this.base.handler();
   }

   public boolean isRemoved() {
      return this.base.isRemoved();
   }

   public ChannelHandlerContext fireChannelRegistered() {
      this.base.fireChannelRegistered();
      return this;
   }

   public ChannelHandlerContext fireChannelUnregistered() {
      this.base.fireChannelUnregistered();
      return this;
   }

   public ChannelHandlerContext fireChannelActive() {
      this.base.fireChannelActive();
      return this;
   }

   public ChannelHandlerContext fireChannelInactive() {
      this.base.fireChannelInactive();
      return this;
   }

   public ChannelHandlerContext fireExceptionCaught(Throwable var1) {
      this.base.fireExceptionCaught(var1);
      return this;
   }

   public ChannelHandlerContext fireUserEventTriggered(Object var1) {
      this.base.fireUserEventTriggered(var1);
      return this;
   }

   public ChannelHandlerContext fireChannelRead(Object var1) {
      this.base.fireChannelRead(var1);
      return this;
   }

   public ChannelHandlerContext fireChannelReadComplete() {
      this.base.fireChannelReadComplete();
      return this;
   }

   public ChannelHandlerContext fireChannelWritabilityChanged() {
      this.base.fireChannelWritabilityChanged();
      return this;
   }

   public ChannelFuture bind(SocketAddress var1) {
      return this.base.bind(var1);
   }

   public ChannelFuture connect(SocketAddress var1) {
      return this.base.connect(var1);
   }

   public ChannelFuture connect(SocketAddress var1, SocketAddress var2) {
      return this.base.connect(var1, var2);
   }

   public ChannelFuture disconnect() {
      return this.base.disconnect();
   }

   public ChannelFuture close() {
      return this.base.close();
   }

   public ChannelFuture deregister() {
      return this.base.deregister();
   }

   public ChannelFuture bind(SocketAddress var1, ChannelPromise var2) {
      return this.base.bind(var1, var2);
   }

   public ChannelFuture connect(SocketAddress var1, ChannelPromise var2) {
      return this.base.connect(var1, var2);
   }

   public ChannelFuture connect(SocketAddress var1, SocketAddress var2, ChannelPromise var3) {
      return this.base.connect(var1, var2, var3);
   }

   public ChannelFuture disconnect(ChannelPromise var1) {
      return this.base.disconnect(var1);
   }

   public ChannelFuture close(ChannelPromise var1) {
      return this.base.close(var1);
   }

   public ChannelFuture deregister(ChannelPromise var1) {
      return this.base.deregister(var1);
   }

   public ChannelHandlerContext read() {
      this.base.read();
      return this;
   }

   public ChannelFuture write(Object var1) {
      int var2 = c();
      return var1 instanceof ByteBuf && this.transform((ByteBuf)var1)?this.base.newFailedFuture(new Throwable()):this.base.write(var1);
   }

   public ChannelFuture write(Object var1, ChannelPromise var2) {
      int var3 = b();
      return var1 instanceof ByteBuf && this.transform((ByteBuf)var1)?this.base.newFailedFuture(new Throwable()):this.base.write(var1, var2);
   }

   public boolean transform(ByteBuf var1) {
      try {
         this.handler.transform(var1);
         return false;
      } catch (Exception var5) {
         Exception var2 = var5;

         try {
            this.handler.exceptionCaught(this.base, var2);
         } catch (Exception var4) {
            this.base.fireExceptionCaught(var4);
         }

         return true;
      }
   }

   public ChannelHandlerContext flush() {
      this.base.flush();
      return this;
   }

   public ChannelFuture writeAndFlush(Object var1, ChannelPromise var2) {
      c();
      ChannelFuture var4 = this.write(var1, var2);
      this.flush();
      return var4;
   }

   public ChannelFuture writeAndFlush(Object var1) {
      ChannelFuture var2 = this.write(var1);
      this.flush();
      return var2;
   }

   public ChannelPipeline pipeline() {
      return this.base.pipeline();
   }

   public ByteBufAllocator alloc() {
      return this.base.alloc();
   }

   public ChannelPromise newPromise() {
      return this.base.newPromise();
   }

   public ChannelProgressivePromise newProgressivePromise() {
      return this.base.newProgressivePromise();
   }

   public ChannelFuture newSucceededFuture() {
      return this.base.newSucceededFuture();
   }

   public ChannelFuture newFailedFuture(Throwable var1) {
      return this.base.newFailedFuture(var1);
   }

   public ChannelPromise voidPromise() {
      return this.base.voidPromise();
   }

   public Attribute attr(AttributeKey var1) {
      return this.base.attr(var1);
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int c() {
      int var0 = b();
      return 70;
   }

   static {
      if(c() == 0) {
         b(124);
      }

   }
}
