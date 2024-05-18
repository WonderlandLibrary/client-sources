/*    */ package io.netty.util.internal.logging;
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
/*    */ public abstract class InternalLoggerFactory
/*    */ {
/* 34 */   private static volatile InternalLoggerFactory defaultFactory = newDefaultFactory(InternalLoggerFactory.class.getName());
/*    */ 
/*    */ 
/*    */   
/*    */   private static InternalLoggerFactory newDefaultFactory(String name) {
/*    */     InternalLoggerFactory internalLoggerFactory;
/*    */     try {
/* 41 */       internalLoggerFactory = new Slf4JLoggerFactory(true);
/* 42 */       internalLoggerFactory.newInstance(name).debug("Using SLF4J as the default logging framework");
/* 43 */     } catch (Throwable t1) {
/*    */       try {
/* 45 */         internalLoggerFactory = new Log4JLoggerFactory();
/* 46 */         internalLoggerFactory.newInstance(name).debug("Using Log4J as the default logging framework");
/* 47 */       } catch (Throwable t2) {
/* 48 */         internalLoggerFactory = new JdkLoggerFactory();
/* 49 */         internalLoggerFactory.newInstance(name).debug("Using java.util.logging as the default logging framework");
/*    */       } 
/*    */     } 
/* 52 */     return internalLoggerFactory;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static InternalLoggerFactory getDefaultFactory() {
/* 60 */     return defaultFactory;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setDefaultFactory(InternalLoggerFactory defaultFactory) {
/* 67 */     if (defaultFactory == null) {
/* 68 */       throw new NullPointerException("defaultFactory");
/*    */     }
/* 70 */     InternalLoggerFactory.defaultFactory = defaultFactory;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static InternalLogger getInstance(Class<?> clazz) {
/* 77 */     return getInstance(clazz.getName());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static InternalLogger getInstance(String name) {
/* 84 */     return getDefaultFactory().newInstance(name);
/*    */   }
/*    */   
/*    */   protected abstract InternalLogger newInstance(String paramString);
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\internal\logging\InternalLoggerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */