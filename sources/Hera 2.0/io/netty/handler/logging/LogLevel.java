/*    */ package io.netty.handler.logging;
/*    */ 
/*    */ import io.netty.util.internal.logging.InternalLogLevel;
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
/*    */ public enum LogLevel
/*    */ {
/* 21 */   TRACE(InternalLogLevel.TRACE),
/* 22 */   DEBUG(InternalLogLevel.DEBUG),
/* 23 */   INFO(InternalLogLevel.INFO),
/* 24 */   WARN(InternalLogLevel.WARN),
/* 25 */   ERROR(InternalLogLevel.ERROR);
/*    */   
/*    */   private final InternalLogLevel internalLevel;
/*    */   
/*    */   LogLevel(InternalLogLevel internalLevel) {
/* 30 */     this.internalLevel = internalLevel;
/*    */   }
/*    */   
/*    */   InternalLogLevel toInternalLevel() {
/* 34 */     return this.internalLevel;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\logging\LogLevel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */