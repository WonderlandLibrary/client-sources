/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.IntFunction;
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
/*     */ public final class Int2ObjectFunctions
/*     */ {
/*     */   public static class EmptyFunction<V>
/*     */     extends AbstractInt2ObjectFunction<V>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public V get(int k) {
/*  44 */       return null;
/*     */     }
/*     */     
/*     */     public boolean containsKey(int k) {
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
/*  67 */       return Int2ObjectFunctions.EMPTY_FUNCTION;
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
/*  84 */       return Int2ObjectFunctions.EMPTY_FUNCTION;
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
/*     */     extends AbstractInt2ObjectFunction<V>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final int key;
/*     */ 
/*     */     
/*     */     protected final V value;
/*     */ 
/*     */     
/*     */     protected Singleton(int key, V value) {
/* 110 */       this.key = key;
/* 111 */       this.value = value;
/*     */     }
/*     */     
/*     */     public boolean containsKey(int k) {
/* 115 */       return (this.key == k);
/*     */     }
/*     */     
/*     */     public V get(int k) {
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
/*     */   public static <V> Int2ObjectFunction<V> singleton(int key, V value) {
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
/*     */   public static <V> Int2ObjectFunction<V> singleton(Integer key, V value) {
/* 164 */     return new Singleton<>(key.intValue(), value);
/*     */   }
/*     */   
/*     */   public static class SynchronizedFunction<V> implements Int2ObjectFunction<V>, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Int2ObjectFunction<V> function;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedFunction(Int2ObjectFunction<V> f, Object sync) {
/* 172 */       if (f == null)
/* 173 */         throw new NullPointerException(); 
/* 174 */       this.function = f;
/* 175 */       this.sync = sync;
/*     */     }
/*     */     protected SynchronizedFunction(Int2ObjectFunction<V> f) {
/* 178 */       if (f == null)
/* 179 */         throw new NullPointerException(); 
/* 180 */       this.function = f;
/* 181 */       this.sync = this;
/*     */     }
/*     */     
/*     */     public V apply(int operand) {
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
/*     */     public V apply(Integer key) {
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
/*     */     public boolean containsKey(int k) {
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
/*     */     public V put(int k, V v) {
/* 234 */       synchronized (this.sync) {
/* 235 */         return this.function.put(k, v);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V get(int k) {
/* 240 */       synchronized (this.sync) {
/* 241 */         return this.function.get(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V remove(int k) {
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
/*     */     public V put(Integer k, V v) {
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
/*     */   public static <V> Int2ObjectFunction<V> synchronize(Int2ObjectFunction<V> f) {
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
/*     */   public static <V> Int2ObjectFunction<V> synchronize(Int2ObjectFunction<V> f, Object sync) {
/* 343 */     return new SynchronizedFunction<>(f, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableFunction<V> extends AbstractInt2ObjectFunction<V> implements Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Int2ObjectFunction<V> function;
/*     */     
/*     */     protected UnmodifiableFunction(Int2ObjectFunction<V> f) {
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
/*     */     public boolean containsKey(int k) {
/* 368 */       return this.function.containsKey(k);
/*     */     }
/*     */     
/*     */     public V put(int k, V v) {
/* 372 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V get(int k) {
/* 376 */       return this.function.get(k);
/*     */     }
/*     */     
/*     */     public V remove(int k) {
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
/*     */     public V put(Integer k, V v) {
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
/*     */   public static <V> Int2ObjectFunction<V> unmodifiable(Int2ObjectFunction<V> f) {
/* 439 */     return new UnmodifiableFunction<>(f);
/*     */   }
/*     */   
/*     */   public static class PrimitiveFunction<V>
/*     */     implements Int2ObjectFunction<V>
/*     */   {
/*     */     protected final Function<? super Integer, ? extends V> function;
/*     */     
/*     */     protected PrimitiveFunction(Function<? super Integer, ? extends V> function) {
/* 448 */       this.function = function;
/*     */     }
/*     */     
/*     */     public boolean containsKey(int key) {
/* 452 */       return (this.function.apply(Integer.valueOf(key)) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsKey(Object key) {
/* 458 */       if (key == null)
/* 459 */         return false; 
/* 460 */       return (this.function.apply((Integer)key) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public V get(int key) {
/* 465 */       V v = this.function.apply(Integer.valueOf(key));
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
/* 476 */       return this.function.apply((Integer)key);
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public V put(Integer key, V value) {
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
/*     */   
/*     */   public static <V> Int2ObjectFunction<V> primitive(Function<? super Integer, ? extends V> f) {
/* 508 */     Objects.requireNonNull(f);
/* 509 */     if (f instanceof Int2ObjectFunction)
/* 510 */       return (Int2ObjectFunction)f; 
/* 511 */     if (f instanceof IntFunction) {
/* 512 */       Objects.requireNonNull((IntFunction)f); return (IntFunction)f::apply;
/* 513 */     }  return new PrimitiveFunction<>(f);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\Int2ObjectFunctions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */