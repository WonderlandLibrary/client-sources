package com.viaversion.viaversion.handlers;

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

   public ChannelHandlerContextWrapper(ChannelHandlerContext base, ViaCodecHandler handler) {
      this.base = base;
      this.handler = handler;
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

   public ChannelHandlerContext fireExceptionCaught(Throwable throwable) {
      this.base.fireExceptionCaught(throwable);
      return this;
   }

   public ChannelHandlerContext fireUserEventTriggered(Object o) {
      this.base.fireUserEventTriggered(o);
      return this;
   }

   public ChannelHandlerContext fireChannelRead(Object o) {
      this.base.fireChannelRead(o);
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

   public ChannelFuture bind(SocketAddress socketAddress) {
      return this.base.bind(socketAddress);
   }

   public ChannelFuture connect(SocketAddress socketAddress) {
      return this.base.connect(socketAddress);
   }

   public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress1) {
      return this.base.connect(socketAddress, socketAddress1);
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

   public ChannelFuture bind(SocketAddress socketAddress, ChannelPromise channelPromise) {
      return this.base.bind(socketAddress, channelPromise);
   }

   public ChannelFuture connect(SocketAddress socketAddress, ChannelPromise channelPromise) {
      return this.base.connect(socketAddress, channelPromise);
   }

   public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress1, ChannelPromise channelPromise) {
      return this.base.connect(socketAddress, socketAddress1, channelPromise);
   }

   public ChannelFuture disconnect(ChannelPromise channelPromise) {
      return this.base.disconnect(channelPromise);
   }

   public ChannelFuture close(ChannelPromise channelPromise) {
      return this.base.close(channelPromise);
   }

   public ChannelFuture deregister(ChannelPromise channelPromise) {
      return this.base.deregister(channelPromise);
   }

   public ChannelHandlerContext read() {
      this.base.read();
      return this;
   }

   public ChannelFuture write(Object o) {
      return o instanceof ByteBuf && this.transform((ByteBuf)o) ? this.base.newFailedFuture(new Throwable()) : this.base.write(o);
   }

   public ChannelFuture write(Object o, ChannelPromise channelPromise) {
      return o instanceof ByteBuf && this.transform((ByteBuf)o) ? this.base.newFailedFuture(new Throwable()) : this.base.write(o, channelPromise);
   }

   public boolean transform(ByteBuf buf) {
      try {
         this.handler.transform(buf);
         return false;
      } catch (Exception var5) {
         Exception e = var5;

         try {
            this.handler.exceptionCaught(this.base, e);
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

   public ChannelFuture writeAndFlush(Object o, ChannelPromise channelPromise) {
      ChannelFuture future = this.write(o, channelPromise);
      this.flush();
      return future;
   }

   public ChannelFuture writeAndFlush(Object o) {
      ChannelFuture future = this.write(o);
      this.flush();
      return future;
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

   public ChannelFuture newFailedFuture(Throwable throwable) {
      return this.base.newFailedFuture(throwable);
   }

   public ChannelPromise voidPromise() {
      return this.base.voidPromise();
   }

   public <T> Attribute<T> attr(AttributeKey<T> attributeKey) {
      return this.base.attr(attributeKey);
   }
}
