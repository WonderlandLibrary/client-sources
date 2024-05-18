/*    */ package io.netty.channel;
/*    */ 
/*    */ import io.netty.util.concurrent.DefaultThreadFactory;
/*    */ import io.netty.util.concurrent.EventExecutor;
/*    */ import io.netty.util.concurrent.MultithreadEventExecutorGroup;
/*    */ import io.netty.util.internal.SystemPropertyUtil;
/*    */ import io.netty.util.internal.logging.InternalLogger;
/*    */ import io.netty.util.internal.logging.InternalLoggerFactory;
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
/*    */ public abstract class MultithreadEventLoopGroup
/*    */   extends MultithreadEventExecutorGroup
/*    */   implements EventLoopGroup
/*    */ {
/* 32 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(MultithreadEventLoopGroup.class);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 37 */   private static final int DEFAULT_EVENT_LOOP_THREADS = Math.max(1, SystemPropertyUtil.getInt("io.netty.eventLoopThreads", Runtime.getRuntime().availableProcessors() * 2));
/*    */   
/*    */   static {
/* 40 */     if (logger.isDebugEnabled()) {
/* 41 */       logger.debug("-Dio.netty.eventLoopThreads: {}", Integer.valueOf(DEFAULT_EVENT_LOOP_THREADS));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected MultithreadEventLoopGroup(int nThreads, ThreadFactory threadFactory, Object... args) {
/* 49 */     super((nThreads == 0) ? DEFAULT_EVENT_LOOP_THREADS : nThreads, threadFactory, args);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ThreadFactory newDefaultThreadFactory() {
/* 54 */     return (ThreadFactory)new DefaultThreadFactory(getClass(), 10);
/*    */   }
/*    */ 
/*    */   
/*    */   public EventLoop next() {
/* 59 */     return (EventLoop)super.next();
/*    */   }
/*    */ 
/*    */   
/*    */   public ChannelFuture register(Channel channel) {
/* 64 */     return next().register(channel);
/*    */   }
/*    */ 
/*    */   
/*    */   public ChannelFuture register(Channel channel, ChannelPromise promise) {
/* 69 */     return next().register(channel, promise);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\MultithreadEventLoopGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */