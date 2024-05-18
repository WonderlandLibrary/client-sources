/*    */ package io.netty.util;
/*    */ 
/*    */ import java.util.Arrays;
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
/*    */ @Deprecated
/*    */ public class ResourceLeakException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 7186453858343358280L;
/*    */   private final StackTraceElement[] cachedStackTrace;
/*    */   
/*    */   public ResourceLeakException() {
/* 32 */     this.cachedStackTrace = getStackTrace();
/*    */   }
/*    */   
/*    */   public ResourceLeakException(String message) {
/* 36 */     super(message);
/* 37 */     this.cachedStackTrace = getStackTrace();
/*    */   }
/*    */   
/*    */   public ResourceLeakException(String message, Throwable cause) {
/* 41 */     super(message, cause);
/* 42 */     this.cachedStackTrace = getStackTrace();
/*    */   }
/*    */   
/*    */   public ResourceLeakException(Throwable cause) {
/* 46 */     super(cause);
/* 47 */     this.cachedStackTrace = getStackTrace();
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 52 */     StackTraceElement[] trace = this.cachedStackTrace;
/* 53 */     int hashCode = 0;
/* 54 */     for (StackTraceElement e : trace) {
/* 55 */       hashCode = hashCode * 31 + e.hashCode();
/*    */     }
/* 57 */     return hashCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 62 */     if (!(o instanceof ResourceLeakException)) {
/* 63 */       return false;
/*    */     }
/* 65 */     if (o == this) {
/* 66 */       return true;
/*    */     }
/*    */     
/* 69 */     return Arrays.equals((Object[])this.cachedStackTrace, (Object[])((ResourceLeakException)o).cachedStackTrace);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\ResourceLeakException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */