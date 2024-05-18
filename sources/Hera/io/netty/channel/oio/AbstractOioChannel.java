/*     */ package io.netty.channel.oio;
/*     */ 
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.EventLoop;
/*     */ import java.net.ConnectException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractOioChannel
/*     */   extends AbstractChannel
/*     */ {
/*     */   protected static final int SO_TIMEOUT = 1000;
/*     */   private volatile boolean readPending;
/*     */   
/*  36 */   private final Runnable readTask = new Runnable()
/*     */     {
/*     */       public void run() {
/*  39 */         if (!AbstractOioChannel.this.isReadPending() && !AbstractOioChannel.this.config().isAutoRead()) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/*  44 */         AbstractOioChannel.this.setReadPending(false);
/*  45 */         AbstractOioChannel.this.doRead();
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractOioChannel(Channel parent) {
/*  53 */     super(parent);
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractChannel.AbstractUnsafe newUnsafe() {
/*  58 */     return new DefaultOioUnsafe();
/*     */   }
/*     */   private final class DefaultOioUnsafe extends AbstractChannel.AbstractUnsafe { private DefaultOioUnsafe() {
/*  61 */       super(AbstractOioChannel.this);
/*     */     }
/*     */ 
/*     */     
/*     */     public void connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/*  66 */       if (!promise.setUncancellable() || !ensureOpen(promise)) {
/*     */         return;
/*     */       }
/*     */       
/*     */       try {
/*  71 */         boolean wasActive = AbstractOioChannel.this.isActive();
/*  72 */         AbstractOioChannel.this.doConnect(remoteAddress, localAddress);
/*  73 */         safeSetSuccess(promise);
/*  74 */         if (!wasActive && AbstractOioChannel.this.isActive()) {
/*  75 */           AbstractOioChannel.this.pipeline().fireChannelActive();
/*     */         }
/*  77 */       } catch (Throwable t) {
/*  78 */         if (t instanceof ConnectException) {
/*  79 */           Throwable newT = new ConnectException(t.getMessage() + ": " + remoteAddress);
/*  80 */           newT.setStackTrace(t.getStackTrace());
/*  81 */           t = newT;
/*     */         } 
/*  83 */         safeSetFailure(promise, t);
/*  84 */         closeIfClosed();
/*     */       } 
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isCompatible(EventLoop loop) {
/*  91 */     return loop instanceof io.netty.channel.ThreadPerChannelEventLoop;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void doConnect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2) throws Exception;
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doBeginRead() throws Exception {
/* 102 */     if (isReadPending()) {
/*     */       return;
/*     */     }
/*     */     
/* 106 */     setReadPending(true);
/* 107 */     eventLoop().execute(this.readTask);
/*     */   }
/*     */   
/*     */   protected abstract void doRead();
/*     */   
/*     */   protected boolean isReadPending() {
/* 113 */     return this.readPending;
/*     */   }
/*     */   
/*     */   protected void setReadPending(boolean readPending) {
/* 117 */     this.readPending = readPending;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\oio\AbstractOioChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */