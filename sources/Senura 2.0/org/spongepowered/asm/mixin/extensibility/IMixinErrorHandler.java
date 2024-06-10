/*    */ package org.spongepowered.asm.mixin.extensibility;
/*    */ 
/*    */ import org.apache.logging.log4j.Level;
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
/*    */ public interface IMixinErrorHandler
/*    */ {
/*    */   ErrorAction onPrepareError(IMixinConfig paramIMixinConfig, Throwable paramThrowable, IMixinInfo paramIMixinInfo, ErrorAction paramErrorAction);
/*    */   
/*    */   ErrorAction onApplyError(String paramString, Throwable paramThrowable, IMixinInfo paramIMixinInfo, ErrorAction paramErrorAction);
/*    */   
/*    */   public enum ErrorAction
/*    */   {
/* 46 */     NONE((String)Level.INFO),
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 51 */     WARN((String)Level.WARN),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 58 */     ERROR((String)Level.FATAL);
/*    */ 
/*    */     
/*    */     public final Level logLevel;
/*    */ 
/*    */ 
/*    */     
/*    */     ErrorAction(Level logLevel) {
/* 66 */       this.logLevel = logLevel;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\extensibility\IMixinErrorHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */