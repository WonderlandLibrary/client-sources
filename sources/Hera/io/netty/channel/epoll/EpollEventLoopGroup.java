/*    */ package io.netty.channel.epoll;
/*    */ 
/*    */ import io.netty.channel.EventLoopGroup;
/*    */ import io.netty.channel.MultithreadEventLoopGroup;
/*    */ import io.netty.util.concurrent.EventExecutor;
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
/*    */ 
/*    */ 
/*    */ public final class EpollEventLoopGroup
/*    */   extends MultithreadEventLoopGroup
/*    */ {
/*    */   public EpollEventLoopGroup() {
/* 34 */     this(0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EpollEventLoopGroup(int nThreads) {
/* 41 */     this(nThreads, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EpollEventLoopGroup(int nThreads, ThreadFactory threadFactory) {
/* 48 */     this(nThreads, threadFactory, 128);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EpollEventLoopGroup(int nThreads, ThreadFactory threadFactory, int maxEventsAtOnce) {
/* 56 */     super(nThreads, threadFactory, new Object[] { Integer.valueOf(maxEventsAtOnce) });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setIoRatio(int ioRatio) {
/* 64 */     for (EventExecutor e : children()) {
/* 65 */       ((EpollEventLoop)e).setIoRatio(ioRatio);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected EventExecutor newChild(ThreadFactory threadFactory, Object... args) throws Exception {
/* 71 */     return (EventExecutor)new EpollEventLoop((EventLoopGroup)this, threadFactory, ((Integer)args[0]).intValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\epoll\EpollEventLoopGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */