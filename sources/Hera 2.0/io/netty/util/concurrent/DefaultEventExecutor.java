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
/*    */ final class DefaultEventExecutor
/*    */   extends SingleThreadEventExecutor
/*    */ {
/*    */   DefaultEventExecutor(DefaultEventExecutorGroup parent, ThreadFactory threadFactory) {
/* 28 */     super(parent, threadFactory, true);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void run() {
/*    */     do {
/* 34 */       Runnable task = takeTask();
/* 35 */       if (task == null)
/* 36 */         continue;  task.run();
/* 37 */       updateLastExecutionTime();
/*    */     
/*    */     }
/* 40 */     while (!confirmShutdown());
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\concurrent\DefaultEventExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */