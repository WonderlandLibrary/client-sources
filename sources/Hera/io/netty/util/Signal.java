/*    */ package io.netty.util;
/*    */ 
/*    */ import io.netty.util.internal.PlatformDependent;
/*    */ import java.util.concurrent.ConcurrentMap;
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
/*    */ public final class Signal
/*    */   extends Error
/*    */ {
/*    */   private static final long serialVersionUID = -221145131122459977L;
/* 31 */   private static final ConcurrentMap<String, Boolean> map = PlatformDependent.newConcurrentHashMap();
/*    */ 
/*    */ 
/*    */   
/*    */   private final UniqueName uname;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Signal valueOf(String name) {
/* 41 */     return new Signal(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Signal(String name) {
/* 49 */     super(name);
/* 50 */     this.uname = new UniqueName(map, name, new Object[0]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void expect(Signal signal) {
/* 58 */     if (this != signal) {
/* 59 */       throw new IllegalStateException("unexpected signal: " + signal);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public Throwable initCause(Throwable cause) {
/* 65 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public Throwable fillInStackTrace() {
/* 70 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 75 */     return this.uname.name();
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\Signal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */