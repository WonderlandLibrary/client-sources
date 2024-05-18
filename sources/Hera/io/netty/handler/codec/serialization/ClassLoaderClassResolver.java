/*    */ package io.netty.handler.codec.serialization;
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
/*    */ class ClassLoaderClassResolver
/*    */   implements ClassResolver
/*    */ {
/*    */   private final ClassLoader classLoader;
/*    */   
/*    */   ClassLoaderClassResolver(ClassLoader classLoader) {
/* 23 */     this.classLoader = classLoader;
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<?> resolve(String className) throws ClassNotFoundException {
/*    */     try {
/* 29 */       return this.classLoader.loadClass(className);
/* 30 */     } catch (ClassNotFoundException ignored) {
/* 31 */       return Class.forName(className, false, this.classLoader);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\serialization\ClassLoaderClassResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */