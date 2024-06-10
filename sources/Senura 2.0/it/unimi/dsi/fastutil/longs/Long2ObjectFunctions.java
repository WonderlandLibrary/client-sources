/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.LongFunction;
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
/*     */ public final class Long2ObjectFunctions
/*     */ {
/*     */   public static class EmptyFunction<V>
/*     */     extends AbstractLong2ObjectFunction<V>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public V get(long k) {
/*  44 */       return null;
/*     */     }
/*     */     
/*     */     public boolean containsKey(long k) {
/*  48 */       return false;
/*     */     }
/*     */     
/*     */     public V defaultReturnValue() {
/*  52 */       return null;
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(V defRetValue) {
/*  56 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public int size() {
/*  60 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {}
/*     */     
/*     */     public Object clone() {
/*  67 */       return Long2ObjectFunctions.EMPTY_FUNCTION;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/*  71 */       return 0;
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/*  75 */       if (!(o instanceof Function))
/*  76 */         return false; 
/*  77 */       return (((Function)o).size() == 0);
/*     */     }
/*     */     
/*     */     public String toString() {
/*  81 */       return "{}";
/*     */     }
/*     */     private Object readResolve() {
/*  84 */       return Long2ObjectFunctions.EMPTY_FUNCTION;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<V>
/*     */     extends AbstractLong2ObjectFunction<V>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final long key;
/*     */ 
/*     */     
/*     */     protected final V value;
/*     */ 
/*     */     
/*     */     protected Singleton(long key, V value) {
/* 110 */       this.key = key;
/* 111 */       this.value = value;
/*     */     }
/*     */     
/*     */     public boolean containsKey(long k) {
/* 115 */       return (this.key == k);
/*     */     }
/*     */     
/*     */     public V get(long k) {
/* 119 */       return (this.key == k) ? this.value : this.defRetValue;
/*     */     }
/*     */     
/*     */     public int size() {
/* 123 */       return 1;
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 127 */       return this;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> Long2ObjectFunction<V> singleton(long key, V value) {
/* 146 */     return new Singleton<>(key, value);
/*     */   }
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
/*     */   public static <V> Long2ObjectFunction<V> singleton(Long key, V value) {
/* 164 */     return new Singleton<>(key.longValue(), value);
/*     */   }
/*     */   
/*     */   public static class SynchronizedFunction<V> implements Long2ObjectFunction<V>, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Long2ObjectFunction<V> function;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedFunction(Long2ObjectFunction<V> f, Object sync) {
/* 172 */       if (f == null)
/* 173 */         throw new NullPointerException(); 
/* 174 */       this.function = f;
/* 175 */       this.sync = sync;
/*     */     }
/*     */     protected SynchronizedFunction(Long2ObjectFunction<V> f) {
/* 178 */       if (f == null)
/* 179 */         throw new NullPointerException(); 
/* 180 */       this.function = f;
/* 181 */       this.sync = this;
/*     */     }
/*     */     
/*     */     public V apply(long operand) {
/* 185 */       synchronized (this.sync) {
/* 186 */         return this.function.apply(operand);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V apply(Long key) {
/* 197 */       synchronized (this.sync) {
/* 198 */         return (V)this.function.apply(key);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int size() {
/* 203 */       synchronized (this.sync) {
/* 204 */         return this.function.size();
/*     */       } 
/*     */     }
/*     */     
/*     */     public V defaultReturnValue() {
/* 209 */       synchronized (this.sync) {
/* 210 */         return this.function.defaultReturnValue();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(V defRetValue) {
/* 215 */       synchronized (this.sync) {
/* 216 */         this.function.defaultReturnValue(defRetValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean containsKey(long k) {
/* 221 */       synchronized (this.sync) {
/* 222 */         return this.function.containsKey(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsKey(Object k) {
/* 228 */       synchronized (this.sync) {
/* 229 */         return this.function.containsKey(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V put(long k, V v) {
/* 234 */       synchronized (this.sync) {
/* 235 */         return this.function.put(k, v);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V get(long k) {
/* 240 */       synchronized (this.sync) {
/* 241 */         return this.function.get(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V remove(long k) {
/* 246 */       synchronized (this.sync) {
/* 247 */         return this.function.remove(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void clear() {
/* 252 */       synchronized (this.sync) {
/* 253 */         this.function.clear();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V put(Long k, V v) {
/* 264 */       synchronized (this.sync) {
/* 265 */         return this.function.put(k, v);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V get(Object k) {
/* 276 */       synchronized (this.sync) {
/* 277 */         return this.function.get(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V remove(Object k) {
/* 288 */       synchronized (this.sync) {
/* 289 */         return this.function.remove(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 294 */       synchronized (this.sync) {
/* 295 */         return this.function.hashCode();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 300 */       if (o == this)
/* 301 */         return true; 
/* 302 */       synchronized (this.sync) {
/* 303 */         return this.function.equals(o);
/*     */       } 
/*     */     }
/*     */     
/*     */     public String toString() {
/* 308 */       synchronized (this.sync) {
/* 309 */         return this.function.toString();
/*     */       } 
/*     */     }
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 313 */       synchronized (this.sync) {
/* 314 */         s.defaultWriteObject();
/*     */       } 
/*     */     } }
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
/*     */   public static <V> Long2ObjectFunction<V> synchronize(Long2ObjectFunction<V> f) {
/* 328 */     return new SynchronizedFunction<>(f);
/*     */   }
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
/*     */   public static <V> Long2ObjectFunction<V> synchronize(Long2ObjectFunction<V> f, Object sync) {
/* 343 */     return new SynchronizedFunction<>(f, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableFunction<V> extends AbstractLong2ObjectFunction<V> implements Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Long2ObjectFunction<V> function;
/*     */     
/*     */     protected UnmodifiableFunction(Long2ObjectFunction<V> f) {
/* 350 */       if (f == null)
/* 351 */         throw new NullPointerException(); 
/* 352 */       this.function = f;
/*     */     }
/*     */     
/*     */     public int size() {
/* 356 */       return this.function.size();
/*     */     }
/*     */     
/*     */     public V defaultReturnValue() {
/* 360 */       return this.function.defaultReturnValue();
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(V defRetValue) {
/* 364 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean containsKey(long k) {
/* 368 */       return this.function.containsKey(k);
/*     */     }
/*     */     
/*     */     public V put(long k, V v) {
/* 372 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V get(long k) {
/* 376 */       return this.function.get(k);
/*     */     }
/*     */     
/*     */     public V remove(long k) {
/* 380 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 384 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V put(Long k, V v) {
/* 394 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V get(Object k) {
/* 404 */       return this.function.get(k);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V remove(Object k) {
/* 414 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 418 */       return this.function.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 422 */       return (o == this || this.function.equals(o));
/*     */     }
/*     */     
/*     */     public String toString() {
/* 426 */       return this.function.toString();
/*     */     } }
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
/*     */   public static <V> Long2ObjectFunction<V> unmodifiable(Long2ObjectFunction<V> f) {
/* 439 */     return new UnmodifiableFunction<>(f);
/*     */   }
/*     */   
/*     */   public static class PrimitiveFunction<V>
/*     */     implements Long2ObjectFunction<V>
/*     */   {
/*     */     protected final Function<? super Long, ? extends V> function;
/*     */     
/*     */     protected PrimitiveFunction(Function<? super Long, ? extends V> function) {
/* 448 */       this.function = function;
/*     */     }
/*     */     
/*     */     public boolean containsKey(long key) {
/* 452 */       return (this.function.apply(Long.valueOf(key)) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsKey(Object key) {
/* 458 */       if (key == null)
/* 459 */         return false; 
/* 460 */       return (this.function.apply((Long)key) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public V get(long key) {
/* 465 */       V v = this.function.apply(Long.valueOf(key));
/* 466 */       if (v == null)
/* 467 */         return null; 
/* 468 */       return v;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V get(Object key) {
/* 474 */       if (key == null)
/* 475 */         return null; 
/* 476 */       return this.function.apply((Long)key);
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public V put(Long key, V value) {
/* 481 */       throw new UnsupportedOperationException();
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
/*     */   public static <V> Long2ObjectFunction<V> primitive(Function<? super Long, ? extends V> f) {
/* 507 */     Objects.requireNonNull(f);
/* 508 */     if (f instanceof Long2ObjectFunction)
/* 509 */       return (Long2ObjectFunction)f; 
/* 510 */     if (f instanceof LongFunction) {
/* 511 */       Objects.requireNonNull((LongFunction)f); return (LongFunction)f::apply;
/* 512 */     }  return new PrimitiveFunction<>(f);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\Long2ObjectFunctions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */