/*    */ package io.netty.channel;
/*    */ 
/*    */ import io.netty.util.concurrent.Future;
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
/*    */ public class ThreadPerChannelEventLoop
/*    */   extends SingleThreadEventLoop
/*    */ {
/*    */   private final ThreadPerChannelEventLoopGroup parent;
/*    */   private Channel ch;
/*    */   
/*    */   public ThreadPerChannelEventLoop(ThreadPerChannelEventLoopGroup parent) {
/* 29 */     super(parent, parent.threadFactory, true);
/* 30 */     this.parent = parent;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChannelFuture register(Channel channel, ChannelPromise promise) {
/* 35 */     return super.register(channel, promise).addListener(new ChannelFutureListener()
/*    */         {
/*    */           public void operationComplete(ChannelFuture future) throws Exception {
/* 38 */             if (future.isSuccess()) {
/* 39 */               ThreadPerChannelEventLoop.this.ch = future.channel();
/*    */             } else {
/* 41 */               ThreadPerChannelEventLoop.this.deregister();
/*    */             } 
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   protected void run() {
/*    */     while (true) {
/* 50 */       Runnable task = takeTask();
/* 51 */       if (task != null) {
/* 52 */         task.run();
/* 53 */         updateLastExecutionTime();
/*    */       } 
/*    */       
/* 56 */       Channel ch = this.ch;
/* 57 */       if (isShuttingDown()) {
/* 58 */         if (ch != null) {
/* 59 */           ch.unsafe().close(ch.unsafe().voidPromise());
/*    */         }
/* 61 */         if (confirmShutdown())
/*    */           break; 
/*    */         continue;
/*    */       } 
/* 65 */       if (ch != null)
/*    */       {
/* 67 */         if (!ch.isRegistered()) {
/* 68 */           runAllTasks();
/* 69 */           deregister();
/*    */         } 
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void deregister() {
/* 77 */     this.ch = null;
/* 78 */     this.parent.activeChildren.remove(this);
/* 79 */     this.parent.idleChildren.add(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\ThreadPerChannelEventLoop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */