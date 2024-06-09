/*    */ package io.netty.util.concurrent;
/*    */ 
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
/*    */ public class DefaultEventExecutorGroup
/*    */   extends MultithreadEventExecutorGroup
/*    */ {
/*    */   public DefaultEventExecutorGroup(int nThreads) {
/* 30 */     this(nThreads, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DefaultEventExecutorGroup(int nThreads, ThreadFactory threadFactory) {
/* 40 */     super(nThreads, threadFactory, new Object[0]);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected EventExecutor newChild(ThreadFactory threadFactory, Object... args) throws Exception {
/* 46 */     return new DefaultEventExecutor(this, threadFactory);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\concurrent\DefaultEventExecutorGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */