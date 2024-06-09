/*    */ package io.netty.util.internal;
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
/*    */ public abstract class OneTimeTask
/*    */   extends MpscLinkedQueueNode<Runnable>
/*    */   implements Runnable
/*    */ {
/*    */   public Runnable value() {
/* 30 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\internal\OneTimeTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */