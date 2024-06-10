/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public final class Byte2ObjectFunctions
/*     */ {
/*     */   public static class EmptyFunction<V>
/*     */     extends AbstractByte2ObjectFunction<V>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public V get(byte k) {
/*  44 */       return null;
/*     */     }
/*     */     
/*     */     public boolean containsKey(byte k) {
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
/*  67 */       return Byte2ObjectFunctions.EMPTY_FUNCTION;
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
/*  84 */       return Byte2ObjectFunctions.EMPTY_FUNCTION;
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
/*     */     extends AbstractByte2ObjectFunction<V>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final byte key;
/*     */ 
/*     */     
/*     */     protected final V value;
/*     */ 
/*     */     
/*     */     protected Singleton(byte key, V value) {
/* 110 */       this.key = key;
/* 111 */       this.value = value;
/*     */     }
/*     */     
/*     */     public boolean containsKey(byte k) {
/* 115 */       return (this.key == k);
/*     */     }
/*     */     
/*     */     public V get(byte k) {
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
/*     */   public static <V> Byte2ObjectFunction<V> singleton(byte key, V value) {
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
/*     */   public static <V> Byte2ObjectFunction<V> singleton(Byte key, V value) {
/* 164 */     return new Singleton<>(key.byteValue(), value);
/*     */   }
/*     */   
/*     */   public static class SynchronizedFunction<V> implements Byte2ObjectFunction<V>, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Byte2ObjectFunction<V> function;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedFunction(Byte2ObjectFunction<V> f, Object sync) {
/* 172 */       if (f == null)
/* 173 */         throw new NullPointerException(); 
/* 174 */       this.function = f;
/* 175 */       this.sync = sync;
/*     */     }
/*     */     protected SynchronizedFunction(Byte2ObjectFunction<V> f) {
/* 178 */       if (f == null)
/* 179 */         throw new NullPointerException(); 
/* 180 */       this.function = f;
/* 181 */       this.sync = this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V apply(int operand) {
/* 191 */       synchronized (this.sync) {
/* 192 */         return this.function.apply(operand);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V apply(Byte key) {
/* 203 */       synchronized (this.sync) {
/* 204 */         return (V)this.function.apply(key);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int size() {
/* 209 */       synchronized (this.sync) {
/* 210 */         return this.function.size();
/*     */       } 
/*     */     }
/*     */     
/*     */     public V defaultReturnValue() {
/* 215 */       synchronized (this.sync) {
/* 216 */         return this.function.defaultReturnValue();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(V defRetValue) {
/* 221 */       synchronized (this.sync) {
/* 222 */         this.function.defaultReturnValue(defRetValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean containsKey(byte k) {
/* 227 */       synchronized (this.sync) {
/* 228 */         return this.function.containsKey(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsKey(Object k) {
/* 234 */       synchronized (this.sync) {
/* 235 */         return this.function.containsKey(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V put(byte k, V v) {
/* 240 */       synchronized (this.sync) {
/* 241 */         return this.function.put(k, v);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V get(byte k) {
/* 246 */       synchronized (this.sync) {
/* 247 */         return this.function.get(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V remove(byte k) {
/* 252 */       synchronized (this.sync) {
/* 253 */         return this.function.remove(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void clear() {
/* 258 */       synchronized (this.sync) {
/* 259 */         this.function.clear();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V put(Byte k, V v) {
/* 270 */       synchronized (this.sync) {
/* 271 */         return this.function.put(k, v);
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
/* 282 */       synchronized (this.sync) {
/* 283 */         return this.function.get(k);
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
/* 294 */       synchronized (this.sync) {
/* 295 */         return this.function.remove(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 300 */       synchronized (this.sync) {
/* 301 */         return this.function.hashCode();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 306 */       if (o == this)
/* 307 */         return true; 
/* 308 */       synchronized (this.sync) {
/* 309 */         return this.function.equals(o);
/*     */       } 
/*     */     }
/*     */     
/*     */     public String toString() {
/* 314 */       synchronized (this.sync) {
/* 315 */         return this.function.toString();
/*     */       } 
/*     */     }
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 319 */       synchronized (this.sync) {
/* 320 */         s.defaultWriteObject();
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
/*     */   public static <V> Byte2ObjectFunction<V> synchronize(Byte2ObjectFunction<V> f) {
/* 334 */     return new SynchronizedFunction<>(f);
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
/*     */   public static <V> Byte2ObjectFunction<V> synchronize(Byte2ObjectFunction<V> f, Object sync) {
/* 349 */     return new SynchronizedFunction<>(f, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableFunction<V> extends AbstractByte2ObjectFunction<V> implements Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Byte2ObjectFunction<V> function;
/*     */     
/*     */     protected UnmodifiableFunction(Byte2ObjectFunction<V> f) {
/* 356 */       if (f == null)
/* 357 */         throw new NullPointerException(); 
/* 358 */       this.function = f;
/*     */     }
/*     */     
/*     */     public int size() {
/* 362 */       return this.function.size();
/*     */     }
/*     */     
/*     */     public V defaultReturnValue() {
/* 366 */       return this.function.defaultReturnValue();
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(V defRetValue) {
/* 370 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean containsKey(byte k) {
/* 374 */       return this.function.containsKey(k);
/*     */     }
/*     */     
/*     */     public V put(byte k, V v) {
/* 378 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V get(byte k) {
/* 382 */       return this.function.get(k);
/*     */     }
/*     */     
/*     */     public V remove(byte k) {
/* 386 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 390 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V put(Byte k, V v) {
/* 400 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V get(Object k) {
/* 410 */       return this.function.get(k);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V remove(Object k) {
/* 420 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 424 */       return this.function.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 428 */       return (o == this || this.function.equals(o));
/*     */     }
/*     */     
/*     */     public String toString() {
/* 432 */       return this.function.toString();
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
/*     */   public static <V> Byte2ObjectFunction<V> unmodifiable(Byte2ObjectFunction<V> f) {
/* 445 */     return new UnmodifiableFunction<>(f);
/*     */   }
/*     */   
/*     */   public static class PrimitiveFunction<V>
/*     */     implements Byte2ObjectFunction<V>
/*     */   {
/*     */     protected final Function<? super Byte, ? extends V> function;
/*     */     
/*     */     protected PrimitiveFunction(Function<? super Byte, ? extends V> function) {
/* 454 */       this.function = function;
/*     */     }
/*     */     
/*     */     public boolean containsKey(byte key) {
/* 458 */       return (this.function.apply(Byte.valueOf(key)) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsKey(Object key) {
/* 464 */       if (key == null)
/* 465 */         return false; 
/* 466 */       return (this.function.apply((Byte)key) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public V get(byte key) {
/* 471 */       V v = this.function.apply(Byte.valueOf(key));
/* 472 */       if (v == null)
/* 473 */         return null; 
/* 474 */       return v;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V get(Object key) {
/* 480 */       if (key == null)
/* 481 */         return null; 
/* 482 */       return this.function.apply((Byte)key);
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public V put(Byte key, V value) {
/* 487 */       throw new UnsupportedOperationException();
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
/*     */   public static <V> Byte2ObjectFunction<V> primitive(Function<? super Byte, ? extends V> f) {
/* 513 */     Objects.requireNonNull(f);
/* 514 */     if (f instanceof Byte2ObjectFunction)
/* 515 */       return (Byte2ObjectFunction)f; 
/* 516 */     if (f instanceof IntFunction) {
/* 517 */       Objects.requireNonNull((IntFunction)f); return (IntFunction)f::apply;
/* 518 */     }  return new PrimitiveFunction<>(f);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\Byte2ObjectFunctions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */