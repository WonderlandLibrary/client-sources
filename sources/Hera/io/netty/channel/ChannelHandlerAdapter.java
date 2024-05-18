/*    */ package io.netty.channel;
/*    */ 
/*    */ import io.netty.util.internal.InternalThreadLocalMap;
/*    */ import java.util.Map;
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
/*    */ public abstract class ChannelHandlerAdapter
/*    */   implements ChannelHandler
/*    */ {
/*    */   boolean added;
/*    */   
/*    */   public boolean isSharable() {
/* 45 */     Class<?> clazz = getClass();
/* 46 */     Map<Class<?>, Boolean> cache = InternalThreadLocalMap.get().handlerSharableCache();
/* 47 */     Boolean sharable = cache.get(clazz);
/* 48 */     if (sharable == null) {
/* 49 */       sharable = Boolean.valueOf(clazz.isAnnotationPresent((Class)ChannelHandler.Sharable.class));
/* 50 */       cache.put(clazz, sharable);
/*    */     } 
/* 52 */     return sharable.booleanValue();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 79 */     ctx.fireExceptionCaught(cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\ChannelHandlerAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */