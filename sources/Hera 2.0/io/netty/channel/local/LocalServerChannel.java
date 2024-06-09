/*     */ package io.netty.channel.local;
/*     */ 
/*     */ import io.netty.channel.AbstractServerChannel;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.DefaultChannelConfig;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.util.concurrent.SingleThreadEventExecutor;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Queue;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LocalServerChannel
/*     */   extends AbstractServerChannel
/*     */ {
/*  36 */   private final ChannelConfig config = (ChannelConfig)new DefaultChannelConfig((Channel)this);
/*  37 */   private final Queue<Object> inboundBuffer = new ArrayDeque();
/*  38 */   private final Runnable shutdownHook = new Runnable()
/*     */     {
/*     */       public void run() {
/*  41 */         LocalServerChannel.this.unsafe().close(LocalServerChannel.this.unsafe().voidPromise());
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private volatile int state;
/*     */   private volatile LocalAddress localAddress;
/*     */   private volatile boolean acceptInProgress;
/*     */   
/*     */   public ChannelConfig config() {
/*  51 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   public LocalAddress localAddress() {
/*  56 */     return (LocalAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public LocalAddress remoteAddress() {
/*  61 */     return (LocalAddress)super.remoteAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOpen() {
/*  66 */     return (this.state < 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/*  71 */     return (this.state == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isCompatible(EventLoop loop) {
/*  76 */     return loop instanceof io.netty.channel.SingleThreadEventLoop;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress localAddress0() {
/*  81 */     return this.localAddress;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doRegister() throws Exception {
/*  86 */     ((SingleThreadEventExecutor)eventLoop()).addShutdownHook(this.shutdownHook);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/*  91 */     this.localAddress = LocalChannelRegistry.register((Channel)this, this.localAddress, localAddress);
/*  92 */     this.state = 1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/*  97 */     if (this.state <= 1) {
/*     */       
/*  99 */       if (this.localAddress != null) {
/* 100 */         LocalChannelRegistry.unregister(this.localAddress);
/* 101 */         this.localAddress = null;
/*     */       } 
/* 103 */       this.state = 2;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDeregister() throws Exception {
/* 109 */     ((SingleThreadEventExecutor)eventLoop()).removeShutdownHook(this.shutdownHook);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBeginRead() throws Exception {
/* 114 */     if (this.acceptInProgress) {
/*     */       return;
/*     */     }
/*     */     
/* 118 */     Queue<Object> inboundBuffer = this.inboundBuffer;
/* 119 */     if (inboundBuffer.isEmpty()) {
/* 120 */       this.acceptInProgress = true;
/*     */       
/*     */       return;
/*     */     } 
/* 124 */     ChannelPipeline pipeline = pipeline();
/*     */     while (true) {
/* 126 */       Object m = inboundBuffer.poll();
/* 127 */       if (m == null) {
/*     */         break;
/*     */       }
/* 130 */       pipeline.fireChannelRead(m);
/*     */     } 
/* 132 */     pipeline.fireChannelReadComplete();
/*     */   }
/*     */   
/*     */   LocalChannel serve(LocalChannel peer) {
/* 136 */     final LocalChannel child = new LocalChannel(this, peer);
/* 137 */     if (eventLoop().inEventLoop()) {
/* 138 */       serve0(child);
/*     */     } else {
/* 140 */       eventLoop().execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 143 */               LocalServerChannel.this.serve0(child);
/*     */             }
/*     */           });
/*     */     } 
/* 147 */     return child;
/*     */   }
/*     */   
/*     */   private void serve0(LocalChannel child) {
/* 151 */     this.inboundBuffer.add(child);
/* 152 */     if (this.acceptInProgress) {
/* 153 */       this.acceptInProgress = false;
/* 154 */       ChannelPipeline pipeline = pipeline();
/*     */       while (true) {
/* 156 */         Object m = this.inboundBuffer.poll();
/* 157 */         if (m == null) {
/*     */           break;
/*     */         }
/* 160 */         pipeline.fireChannelRead(m);
/*     */       } 
/* 162 */       pipeline.fireChannelReadComplete();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\local\LocalServerChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */