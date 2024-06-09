/*    */ package io.netty.channel;
/*    */ 
/*    */ import io.netty.util.concurrent.EventExecutor;
/*    */ import io.netty.util.concurrent.EventExecutorGroup;
/*    */ import io.netty.util.concurrent.SingleThreadEventExecutor;
/*    */ import java.util.concurrent.ThreadFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SingleThreadEventLoop
/*    */   extends SingleThreadEventExecutor
/*    */   implements EventLoop
/*    */ {
/*    */   protected SingleThreadEventLoop(EventLoopGroup parent, ThreadFactory threadFactory, boolean addTaskWakesUp) {
/* 33 */     super(parent, threadFactory, addTaskWakesUp);
/*    */   }
/*    */ 
/*    */   
/*    */   public EventLoopGroup parent() {
/* 38 */     return (EventLoopGroup)super.parent();
/*    */   }
/*    */ 
/*    */   
/*    */   public EventLoop next() {
/* 43 */     return (EventLoop)super.next();
/*    */   }
/*    */ 
/*    */   
/*    */   public ChannelFuture register(Channel channel) {
/* 48 */     return register(channel, new DefaultChannelPromise(channel, this));
/*    */   }
/*    */ 
/*    */   
/*    */   public ChannelFuture register(Channel channel, ChannelPromise promise) {
/* 53 */     if (channel == null) {
/* 54 */       throw new NullPointerException("channel");
/*    */     }
/* 56 */     if (promise == null) {
/* 57 */       throw new NullPointerException("promise");
/*    */     }
/*    */     
/* 60 */     channel.unsafe().register(this, promise);
/* 61 */     return promise;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean wakesUpForTask(Runnable task) {
/* 66 */     return !(task instanceof NonWakeupRunnable);
/*    */   }
/*    */   
/*    */   static interface NonWakeupRunnable extends Runnable {}
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\SingleThreadEventLoop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */