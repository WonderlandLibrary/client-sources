/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import io.netty.util.internal.InternalThreadLocalMap;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.util.Collections;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Set;
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
/*     */ public class FastThreadLocal<V>
/*     */ {
/*  46 */   private static final int variablesToRemoveIndex = InternalThreadLocalMap.nextVariableIndex();
/*     */ 
/*     */   
/*     */   private final int index;
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeAll() {
/*  54 */     InternalThreadLocalMap threadLocalMap = InternalThreadLocalMap.getIfSet();
/*  55 */     if (threadLocalMap == null) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/*  60 */       Object v = threadLocalMap.indexedVariable(variablesToRemoveIndex);
/*  61 */       if (v != null && v != InternalThreadLocalMap.UNSET) {
/*     */         
/*  63 */         Set<FastThreadLocal<?>> variablesToRemove = (Set<FastThreadLocal<?>>)v;
/*  64 */         FastThreadLocal[] arrayOfFastThreadLocal = variablesToRemove.<FastThreadLocal>toArray(new FastThreadLocal[variablesToRemove.size()]);
/*     */         
/*  66 */         for (FastThreadLocal<?> tlv : arrayOfFastThreadLocal) {
/*  67 */           tlv.remove(threadLocalMap);
/*     */         }
/*     */       } 
/*     */     } finally {
/*  71 */       InternalThreadLocalMap.remove();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int size() {
/*  79 */     InternalThreadLocalMap threadLocalMap = InternalThreadLocalMap.getIfSet();
/*  80 */     if (threadLocalMap == null) {
/*  81 */       return 0;
/*     */     }
/*  83 */     return threadLocalMap.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void destroy() {
/*  94 */     InternalThreadLocalMap.destroy();
/*     */   }
/*     */   
/*     */   private static void addToVariablesToRemove(InternalThreadLocalMap threadLocalMap, FastThreadLocal<?> variable) {
/*     */     Set<FastThreadLocal<?>> variablesToRemove;
/*  99 */     Object v = threadLocalMap.indexedVariable(variablesToRemoveIndex);
/*     */     
/* 101 */     if (v == InternalThreadLocalMap.UNSET || v == null) {
/* 102 */       variablesToRemove = Collections.newSetFromMap(new IdentityHashMap<FastThreadLocal<?>, Boolean>());
/* 103 */       threadLocalMap.setIndexedVariable(variablesToRemoveIndex, variablesToRemove);
/*     */     } else {
/* 105 */       variablesToRemove = (Set<FastThreadLocal<?>>)v;
/*     */     } 
/*     */     
/* 108 */     variablesToRemove.add(variable);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void removeFromVariablesToRemove(InternalThreadLocalMap threadLocalMap, FastThreadLocal<?> variable) {
/* 114 */     Object v = threadLocalMap.indexedVariable(variablesToRemoveIndex);
/*     */     
/* 116 */     if (v == InternalThreadLocalMap.UNSET || v == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 121 */     Set<FastThreadLocal<?>> variablesToRemove = (Set<FastThreadLocal<?>>)v;
/* 122 */     variablesToRemove.remove(variable);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FastThreadLocal() {
/* 128 */     this.index = InternalThreadLocalMap.nextVariableIndex();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final V get() {
/* 135 */     return get(InternalThreadLocalMap.get());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final V get(InternalThreadLocalMap threadLocalMap) {
/* 144 */     Object v = threadLocalMap.indexedVariable(this.index);
/* 145 */     if (v != InternalThreadLocalMap.UNSET) {
/* 146 */       return (V)v;
/*     */     }
/*     */     
/* 149 */     return initialize(threadLocalMap);
/*     */   }
/*     */   
/*     */   private V initialize(InternalThreadLocalMap threadLocalMap) {
/* 153 */     V v = null;
/*     */     try {
/* 155 */       v = initialValue();
/* 156 */     } catch (Exception e) {
/* 157 */       PlatformDependent.throwException(e);
/*     */     } 
/*     */     
/* 160 */     threadLocalMap.setIndexedVariable(this.index, v);
/* 161 */     addToVariablesToRemove(threadLocalMap, this);
/* 162 */     return v;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(V value) {
/* 169 */     if (value != InternalThreadLocalMap.UNSET) {
/* 170 */       set(InternalThreadLocalMap.get(), value);
/*     */     } else {
/* 172 */       remove();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(InternalThreadLocalMap threadLocalMap, V value) {
/* 180 */     if (value != InternalThreadLocalMap.UNSET) {
/* 181 */       if (threadLocalMap.setIndexedVariable(this.index, value)) {
/* 182 */         addToVariablesToRemove(threadLocalMap, this);
/*     */       }
/*     */     } else {
/* 185 */       remove(threadLocalMap);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isSet() {
/* 193 */     return isSet(InternalThreadLocalMap.getIfSet());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isSet(InternalThreadLocalMap threadLocalMap) {
/* 201 */     return (threadLocalMap != null && threadLocalMap.isIndexedVariableSet(this.index));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void remove() {
/* 207 */     remove(InternalThreadLocalMap.getIfSet());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void remove(InternalThreadLocalMap threadLocalMap) {
/* 217 */     if (threadLocalMap == null) {
/*     */       return;
/*     */     }
/*     */     
/* 221 */     Object v = threadLocalMap.removeIndexedVariable(this.index);
/* 222 */     removeFromVariablesToRemove(threadLocalMap, this);
/*     */     
/* 224 */     if (v != InternalThreadLocalMap.UNSET) {
/*     */       try {
/* 226 */         onRemoval((V)v);
/* 227 */       } catch (Exception e) {
/* 228 */         PlatformDependent.throwException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected V initialValue() throws Exception {
/* 237 */     return null;
/*     */   }
/*     */   
/*     */   protected void onRemoval(V value) throws Exception {}
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\concurrent\FastThreadLocal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */