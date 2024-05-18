/*     */ package io.netty.util;
/*     */ 
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ReferenceCountUtil
/*     */ {
/*  27 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ReferenceCountUtil.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T retain(T msg) {
/*  35 */     if (msg instanceof ReferenceCounted) {
/*  36 */       return (T)((ReferenceCounted)msg).retain();
/*     */     }
/*  38 */     return msg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T retain(T msg, int increment) {
/*  47 */     if (msg instanceof ReferenceCounted) {
/*  48 */       return (T)((ReferenceCounted)msg).retain(increment);
/*     */     }
/*  50 */     return msg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean release(Object msg) {
/*  58 */     if (msg instanceof ReferenceCounted) {
/*  59 */       return ((ReferenceCounted)msg).release();
/*     */     }
/*  61 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean release(Object msg, int decrement) {
/*  69 */     if (msg instanceof ReferenceCounted) {
/*  70 */       return ((ReferenceCounted)msg).release(decrement);
/*     */     }
/*  72 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void safeRelease(Object msg) {
/*     */     try {
/*  84 */       release(msg);
/*  85 */     } catch (Throwable t) {
/*  86 */       logger.warn("Failed to release a message: {}", msg, t);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void safeRelease(Object msg, int decrement) {
/*     */     try {
/*  99 */       release(msg, decrement);
/* 100 */     } catch (Throwable t) {
/* 101 */       if (logger.isWarnEnabled()) {
/* 102 */         logger.warn("Failed to release a message: {} (decrement: {})", new Object[] { msg, Integer.valueOf(decrement), t });
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T releaseLater(T msg) {
/* 113 */     return releaseLater(msg, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T releaseLater(T msg, int decrement) {
/* 122 */     if (msg instanceof ReferenceCounted) {
/* 123 */       ThreadDeathWatcher.watch(Thread.currentThread(), new ReleasingTask((ReferenceCounted)msg, decrement));
/*     */     }
/* 125 */     return msg;
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class ReleasingTask
/*     */     implements Runnable
/*     */   {
/*     */     private final ReferenceCounted obj;
/*     */     
/*     */     private final int decrement;
/*     */     
/*     */     ReleasingTask(ReferenceCounted obj, int decrement) {
/* 137 */       this.obj = obj;
/* 138 */       this.decrement = decrement;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/*     */       try {
/* 144 */         if (!this.obj.release(this.decrement)) {
/* 145 */           ReferenceCountUtil.logger.warn("Non-zero refCnt: {}", this);
/*     */         } else {
/* 147 */           ReferenceCountUtil.logger.debug("Released: {}", this);
/*     */         } 
/* 149 */       } catch (Exception ex) {
/* 150 */         ReferenceCountUtil.logger.warn("Failed to release an object: {}", this.obj, ex);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 156 */       return StringUtil.simpleClassName(this.obj) + ".release(" + this.decrement + ") refCnt: " + this.obj.refCnt();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\ReferenceCountUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */