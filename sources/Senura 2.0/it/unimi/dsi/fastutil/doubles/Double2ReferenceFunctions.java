/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Objects;
/*     */ import java.util.function.DoubleFunction;
/*     */ import java.util.function.Function;
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
/*     */ public final class Double2ReferenceFunctions
/*     */ {
/*     */   public static class EmptyFunction<V>
/*     */     extends AbstractDouble2ReferenceFunction<V>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public V get(double k) {
/*  44 */       return null;
/*     */     }
/*     */     
/*     */     public boolean containsKey(double k) {
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
/*  67 */       return Double2ReferenceFunctions.EMPTY_FUNCTION;
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
/*  84 */       return Double2ReferenceFunctions.EMPTY_FUNCTION;
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
/*     */   
/*     */   public static class Singleton<V>
/*     */     extends AbstractDouble2ReferenceFunction<V>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */     
/*     */     protected final double key;
/*     */ 
/*     */     
/*     */     protected final V value;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(double key, V value) {
/* 113 */       this.key = key;
/* 114 */       this.value = value;
/*     */     }
/*     */     
/*     */     public boolean containsKey(double k) {
/* 118 */       return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(k));
/*     */     }
/*     */     
/*     */     public V get(double k) {
/* 122 */       return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(k)) ? this.value : this.defRetValue;
/*     */     }
/*     */     
/*     */     public int size() {
/* 126 */       return 1;
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 130 */       return this;
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
/*     */   public static <V> Double2ReferenceFunction<V> singleton(double key, V value) {
/* 149 */     return new Singleton<>(key, value);
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
/*     */   public static <V> Double2ReferenceFunction<V> singleton(Double key, V value) {
/* 167 */     return new Singleton<>(key.doubleValue(), value);
/*     */   }
/*     */   
/*     */   public static class SynchronizedFunction<V> implements Double2ReferenceFunction<V>, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Double2ReferenceFunction<V> function;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedFunction(Double2ReferenceFunction<V> f, Object sync) {
/* 175 */       if (f == null)
/* 176 */         throw new NullPointerException(); 
/* 177 */       this.function = f;
/* 178 */       this.sync = sync;
/*     */     }
/*     */     protected SynchronizedFunction(Double2ReferenceFunction<V> f) {
/* 181 */       if (f == null)
/* 182 */         throw new NullPointerException(); 
/* 183 */       this.function = f;
/* 184 */       this.sync = this;
/*     */     }
/*     */     
/*     */     public V apply(double operand) {
/* 188 */       synchronized (this.sync) {
/* 189 */         return this.function.apply(operand);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V apply(Double key) {
/* 200 */       synchronized (this.sync) {
/* 201 */         return (V)this.function.apply(key);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int size() {
/* 206 */       synchronized (this.sync) {
/* 207 */         return this.function.size();
/*     */       } 
/*     */     }
/*     */     
/*     */     public V defaultReturnValue() {
/* 212 */       synchronized (this.sync) {
/* 213 */         return this.function.defaultReturnValue();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(V defRetValue) {
/* 218 */       synchronized (this.sync) {
/* 219 */         this.function.defaultReturnValue(defRetValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean containsKey(double k) {
/* 224 */       synchronized (this.sync) {
/* 225 */         return this.function.containsKey(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsKey(Object k) {
/* 231 */       synchronized (this.sync) {
/* 232 */         return this.function.containsKey(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V put(double k, V v) {
/* 237 */       synchronized (this.sync) {
/* 238 */         return this.function.put(k, v);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V get(double k) {
/* 243 */       synchronized (this.sync) {
/* 244 */         return this.function.get(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V remove(double k) {
/* 249 */       synchronized (this.sync) {
/* 250 */         return this.function.remove(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void clear() {
/* 255 */       synchronized (this.sync) {
/* 256 */         this.function.clear();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V put(Double k, V v) {
/* 267 */       synchronized (this.sync) {
/* 268 */         return this.function.put(k, v);
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
/* 279 */       synchronized (this.sync) {
/* 280 */         return this.function.get(k);
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
/* 291 */       synchronized (this.sync) {
/* 292 */         return this.function.remove(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 297 */       synchronized (this.sync) {
/* 298 */         return this.function.hashCode();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 303 */       if (o == this)
/* 304 */         return true; 
/* 305 */       synchronized (this.sync) {
/* 306 */         return this.function.equals(o);
/*     */       } 
/*     */     }
/*     */     
/*     */     public String toString() {
/* 311 */       synchronized (this.sync) {
/* 312 */         return this.function.toString();
/*     */       } 
/*     */     }
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 316 */       synchronized (this.sync) {
/* 317 */         s.defaultWriteObject();
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
/*     */   public static <V> Double2ReferenceFunction<V> synchronize(Double2ReferenceFunction<V> f) {
/* 331 */     return new SynchronizedFunction<>(f);
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
/*     */   public static <V> Double2ReferenceFunction<V> synchronize(Double2ReferenceFunction<V> f, Object sync) {
/* 346 */     return new SynchronizedFunction<>(f, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableFunction<V>
/*     */     extends AbstractDouble2ReferenceFunction<V> implements Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Double2ReferenceFunction<V> function;
/*     */     
/*     */     protected UnmodifiableFunction(Double2ReferenceFunction<V> f) {
/* 355 */       if (f == null)
/* 356 */         throw new NullPointerException(); 
/* 357 */       this.function = f;
/*     */     }
/*     */     
/*     */     public int size() {
/* 361 */       return this.function.size();
/*     */     }
/*     */     
/*     */     public V defaultReturnValue() {
/* 365 */       return this.function.defaultReturnValue();
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(V defRetValue) {
/* 369 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean containsKey(double k) {
/* 373 */       return this.function.containsKey(k);
/*     */     }
/*     */     
/*     */     public V put(double k, V v) {
/* 377 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V get(double k) {
/* 381 */       return this.function.get(k);
/*     */     }
/*     */     
/*     */     public V remove(double k) {
/* 385 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 389 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V put(Double k, V v) {
/* 399 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V get(Object k) {
/* 409 */       return this.function.get(k);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V remove(Object k) {
/* 419 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 423 */       return this.function.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 427 */       return (o == this || this.function.equals(o));
/*     */     }
/*     */     
/*     */     public String toString() {
/* 431 */       return this.function.toString();
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
/*     */   public static <V> Double2ReferenceFunction<V> unmodifiable(Double2ReferenceFunction<V> f) {
/* 444 */     return new UnmodifiableFunction<>(f);
/*     */   }
/*     */   
/*     */   public static class PrimitiveFunction<V>
/*     */     implements Double2ReferenceFunction<V>
/*     */   {
/*     */     protected final Function<? super Double, ? extends V> function;
/*     */     
/*     */     protected PrimitiveFunction(Function<? super Double, ? extends V> function) {
/* 453 */       this.function = function;
/*     */     }
/*     */     
/*     */     public boolean containsKey(double key) {
/* 457 */       return (this.function.apply(Double.valueOf(key)) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsKey(Object key) {
/* 463 */       if (key == null)
/* 464 */         return false; 
/* 465 */       return (this.function.apply((Double)key) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public V get(double key) {
/* 470 */       V v = this.function.apply(Double.valueOf(key));
/* 471 */       if (v == null)
/* 472 */         return null; 
/* 473 */       return v;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V get(Object key) {
/* 479 */       if (key == null)
/* 480 */         return null; 
/* 481 */       return this.function.apply((Double)key);
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public V put(Double key, V value) {
/* 486 */       throw new UnsupportedOperationException();
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
/*     */   public static <V> Double2ReferenceFunction<V> primitive(Function<? super Double, ? extends V> f) {
/* 513 */     Objects.requireNonNull(f);
/* 514 */     if (f instanceof Double2ReferenceFunction)
/* 515 */       return (Double2ReferenceFunction)f; 
/* 516 */     if (f instanceof DoubleFunction) {
/* 517 */       Objects.requireNonNull((DoubleFunction)f); return (DoubleFunction)f::apply;
/* 518 */     }  return new PrimitiveFunction<>(f);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2ReferenceFunctions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */