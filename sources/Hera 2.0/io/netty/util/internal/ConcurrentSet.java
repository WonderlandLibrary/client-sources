/*    */ package io.netty.util.internal;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.AbstractSet;
/*    */ import java.util.Iterator;
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
/*    */ public final class ConcurrentSet<E>
/*    */   extends AbstractSet<E>
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -6761513279741915432L;
/* 33 */   private final ConcurrentMap<E, Boolean> map = PlatformDependent.newConcurrentHashMap();
/*    */ 
/*    */ 
/*    */   
/*    */   public int size() {
/* 38 */     return this.map.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean contains(Object o) {
/* 43 */     return this.map.containsKey(o);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean add(E o) {
/* 48 */     return (this.map.putIfAbsent(o, Boolean.TRUE) == null);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean remove(Object o) {
/* 53 */     return (this.map.remove(o) != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 58 */     this.map.clear();
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator<E> iterator() {
/* 63 */     return this.map.keySet().iterator();
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\internal\ConcurrentSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */