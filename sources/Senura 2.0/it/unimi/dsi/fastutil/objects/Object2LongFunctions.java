/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.ToLongFunction;
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
/*     */ public final class Object2LongFunctions
/*     */ {
/*     */   public static class EmptyFunction<K>
/*     */     extends AbstractObject2LongFunction<K>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public long getLong(Object k) {
/*  44 */       return 0L;
/*     */     }
/*     */     
/*     */     public boolean containsKey(Object k) {
/*  48 */       return false;
/*     */     }
/*     */     
/*     */     public long defaultReturnValue() {
/*  52 */       return 0L;
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(long defRetValue) {
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
/*  67 */       return Object2LongFunctions.EMPTY_FUNCTION;
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
/*  84 */       return Object2LongFunctions.EMPTY_FUNCTION;
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
/*     */   public static class Singleton<K>
/*     */     extends AbstractObject2LongFunction<K>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final K key;
/*     */ 
/*     */     
/*     */     protected final long value;
/*     */ 
/*     */     
/*     */     protected Singleton(K key, long value) {
/* 110 */       this.key = key;
/* 111 */       this.value = value;
/*     */     }
/*     */     
/*     */     public boolean containsKey(Object k) {
/* 115 */       return Objects.equals(this.key, k);
/*     */     }
/*     */     
/*     */     public long getLong(Object k) {
/* 119 */       return Objects.equals(this.key, k) ? this.value : this.defRetValue;
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
/*     */   public static <K> Object2LongFunction<K> singleton(K key, long value) {
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
/*     */   public static <K> Object2LongFunction<K> singleton(K key, Long value) {
/* 164 */     return new Singleton<>(key, value.longValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedFunction<K> implements Object2LongFunction<K>, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2LongFunction<K> function;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedFunction(Object2LongFunction<K> f, Object sync) {
/* 172 */       if (f == null)
/* 173 */         throw new NullPointerException(); 
/* 174 */       this.function = f;
/* 175 */       this.sync = sync;
/*     */     }
/*     */     protected SynchronizedFunction(Object2LongFunction<K> f) {
/* 178 */       if (f == null)
/* 179 */         throw new NullPointerException(); 
/* 180 */       this.function = f;
/* 181 */       this.sync = this;
/*     */     }
/*     */     
/*     */     public long applyAsLong(K operand) {
/* 185 */       synchronized (this.sync) {
/* 186 */         return this.function.applyAsLong(operand);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long apply(K key) {
/* 197 */       synchronized (this.sync) {
/* 198 */         return (Long)this.function.apply(key);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int size() {
/* 203 */       synchronized (this.sync) {
/* 204 */         return this.function.size();
/*     */       } 
/*     */     }
/*     */     
/*     */     public long defaultReturnValue() {
/* 209 */       synchronized (this.sync) {
/* 210 */         return this.function.defaultReturnValue();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(long defRetValue) {
/* 215 */       synchronized (this.sync) {
/* 216 */         this.function.defaultReturnValue(defRetValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean containsKey(Object k) {
/* 221 */       synchronized (this.sync) {
/* 222 */         return this.function.containsKey(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public long put(K k, long v) {
/* 227 */       synchronized (this.sync) {
/* 228 */         return this.function.put(k, v);
/*     */       } 
/*     */     }
/*     */     
/*     */     public long getLong(Object k) {
/* 233 */       synchronized (this.sync) {
/* 234 */         return this.function.getLong(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public long removeLong(Object k) {
/* 239 */       synchronized (this.sync) {
/* 240 */         return this.function.removeLong(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void clear() {
/* 245 */       synchronized (this.sync) {
/* 246 */         this.function.clear();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long put(K k, Long v) {
/* 257 */       synchronized (this.sync) {
/* 258 */         return this.function.put(k, v);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long get(Object k) {
/* 269 */       synchronized (this.sync) {
/* 270 */         return this.function.get(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long remove(Object k) {
/* 281 */       synchronized (this.sync) {
/* 282 */         return this.function.remove(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 287 */       synchronized (this.sync) {
/* 288 */         return this.function.hashCode();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 293 */       if (o == this)
/* 294 */         return true; 
/* 295 */       synchronized (this.sync) {
/* 296 */         return this.function.equals(o);
/*     */       } 
/*     */     }
/*     */     
/*     */     public String toString() {
/* 301 */       synchronized (this.sync) {
/* 302 */         return this.function.toString();
/*     */       } 
/*     */     }
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 306 */       synchronized (this.sync) {
/* 307 */         s.defaultWriteObject();
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
/*     */   public static <K> Object2LongFunction<K> synchronize(Object2LongFunction<K> f) {
/* 321 */     return new SynchronizedFunction<>(f);
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
/*     */   public static <K> Object2LongFunction<K> synchronize(Object2LongFunction<K> f, Object sync) {
/* 336 */     return new SynchronizedFunction<>(f, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableFunction<K> extends AbstractObject2LongFunction<K> implements Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2LongFunction<K> function;
/*     */     
/*     */     protected UnmodifiableFunction(Object2LongFunction<K> f) {
/* 343 */       if (f == null)
/* 344 */         throw new NullPointerException(); 
/* 345 */       this.function = f;
/*     */     }
/*     */     
/*     */     public int size() {
/* 349 */       return this.function.size();
/*     */     }
/*     */     
/*     */     public long defaultReturnValue() {
/* 353 */       return this.function.defaultReturnValue();
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(long defRetValue) {
/* 357 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean containsKey(Object k) {
/* 361 */       return this.function.containsKey(k);
/*     */     }
/*     */     
/*     */     public long put(K k, long v) {
/* 365 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public long getLong(Object k) {
/* 369 */       return this.function.getLong(k);
/*     */     }
/*     */     
/*     */     public long removeLong(Object k) {
/* 373 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 377 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long put(K k, Long v) {
/* 387 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long get(Object k) {
/* 397 */       return this.function.get(k);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long remove(Object k) {
/* 407 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 411 */       return this.function.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 415 */       return (o == this || this.function.equals(o));
/*     */     }
/*     */     
/*     */     public String toString() {
/* 419 */       return this.function.toString();
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
/*     */   public static <K> Object2LongFunction<K> unmodifiable(Object2LongFunction<K> f) {
/* 432 */     return new UnmodifiableFunction<>(f);
/*     */   }
/*     */   
/*     */   public static class PrimitiveFunction<K>
/*     */     implements Object2LongFunction<K>
/*     */   {
/*     */     protected final Function<? super K, ? extends Long> function;
/*     */     
/*     */     protected PrimitiveFunction(Function<? super K, ? extends Long> function) {
/* 441 */       this.function = function;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(Object key) {
/* 446 */       return (this.function.apply((K)key) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public long getLong(Object key) {
/* 451 */       Long v = this.function.apply((K)key);
/* 452 */       if (v == null)
/* 453 */         return defaultReturnValue(); 
/* 454 */       return v.longValue();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long get(Object key) {
/* 460 */       return this.function.apply((K)key);
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public Long put(K key, Long value) {
/* 465 */       throw new UnsupportedOperationException();
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
/*     */   public static <K> Object2LongFunction<K> primitive(Function<? super K, ? extends Long> f) {
/* 491 */     Objects.requireNonNull(f);
/* 492 */     if (f instanceof Object2LongFunction)
/* 493 */       return (Object2LongFunction)f; 
/* 494 */     if (f instanceof ToLongFunction)
/* 495 */       return key -> ((ToLongFunction<Object>)f).applyAsLong(key); 
/* 496 */     return new PrimitiveFunction<>(f);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2LongFunctions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */