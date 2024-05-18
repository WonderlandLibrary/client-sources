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
/*    */ 
/*    */ 
/*    */ public final class AttributeKey<T>
/*    */   extends UniqueName
/*    */ {
/* 32 */   private static final ConcurrentMap<String, Boolean> names = PlatformDependent.newConcurrentHashMap();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T> AttributeKey<T> valueOf(String name) {
/* 39 */     return new AttributeKey<T>(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public AttributeKey(String name) {
/* 47 */     super(names, name, new Object[0]);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\AttributeKey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */