/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.channel.socket.ServerSocketChannel;
/*     */ import io.netty.channel.socket.ServerSocketChannelConfig;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class EpollServerSocketChannel
/*     */   extends AbstractEpollChannel
/*     */   implements ServerSocketChannel
/*     */ {
/*     */   private final EpollServerSocketChannelConfig config;
/*     */   private volatile InetSocketAddress local;
/*     */   
/*     */   public EpollServerSocketChannel() {
/*  37 */     super(Native.socketStreamFd(), 4);
/*  38 */     this.config = new EpollServerSocketChannelConfig(this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isCompatible(EventLoop loop) {
/*  43 */     return loop instanceof EpollEventLoop;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/*  48 */     InetSocketAddress addr = (InetSocketAddress)localAddress;
/*  49 */     checkResolvable(addr);
/*  50 */     Native.bind(this.fd, addr.getAddress(), addr.getPort());
/*  51 */     this.local = Native.localAddress(this.fd);
/*  52 */     Native.listen(this.fd, this.config.getBacklog());
/*  53 */     this.active = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerSocketChannelConfig config() {
/*  58 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   protected InetSocketAddress localAddress0() {
/*  63 */     return this.local;
/*     */   }
/*     */ 
/*     */   
/*     */   protected InetSocketAddress remoteAddress0() {
/*  68 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractEpollChannel.AbstractEpollUnsafe newUnsafe() {
/*  73 */     return new EpollServerSocketUnsafe();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/*  78 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object filterOutboundMessage(Object msg) throws Exception {
/*  83 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   final class EpollServerSocketUnsafe
/*     */     extends AbstractEpollChannel.AbstractEpollUnsafe
/*     */   {
/*     */     public void connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
/*  91 */       channelPromise.setFailure(new UnsupportedOperationException());
/*     */     }
/*     */ 
/*     */     
/*     */     void epollInReady() {
/*  96 */       assert EpollServerSocketChannel.this.eventLoop().inEventLoop();
/*  97 */       ChannelPipeline pipeline = EpollServerSocketChannel.this.pipeline();
/*  98 */       Throwable exception = null;
/*     */       try {
/*     */         while (true) {
/*     */           try {
/* 102 */             int socketFd = Native.accept(EpollServerSocketChannel.this.fd);
/* 103 */             if (socketFd == -1) {
/*     */               break;
/*     */             }
/*     */             
/*     */             try {
/* 108 */               this.readPending = false;
/* 109 */               pipeline.fireChannelRead(new EpollSocketChannel((Channel)EpollServerSocketChannel.this, socketFd));
/* 110 */             } catch (Throwable t) {
/*     */               
/* 112 */               pipeline.fireChannelReadComplete();
/* 113 */               pipeline.fireExceptionCaught(t);
/*     */             }
/*     */           
/* 116 */           } catch (Throwable t) {
/* 117 */             exception = t; break;
/*     */           } 
/* 119 */         }  pipeline.fireChannelReadComplete();
/*     */         
/* 121 */         if (exception != null) {
/* 122 */           pipeline.fireExceptionCaught(exception);
/*     */ 
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */       finally {
/*     */ 
/*     */         
/* 131 */         if (!EpollServerSocketChannel.this.config.isAutoRead() && !this.readPending)
/* 132 */           clearEpollIn0(); 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\epoll\EpollServerSocketChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */