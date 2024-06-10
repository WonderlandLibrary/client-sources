/*     */ package it.unimi.dsi.fastutil.chars;
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
/*     */ public final class Char2ReferenceFunctions
/*     */ {
/*     */   public static class EmptyFunction<V>
/*     */     extends AbstractChar2ReferenceFunction<V>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public V get(char k) {
/*  44 */       return null;
/*     */     }
/*     */     
/*     */     public boolean containsKey(char k) {
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
/*  67 */       return Char2ReferenceFunctions.EMPTY_FUNCTION;
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
/*  84 */       return Char2ReferenceFunctions.EMPTY_FUNCTION;
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
/*     */     extends AbstractChar2ReferenceFunction<V>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */     
/*     */     protected final char key;
/*     */ 
/*     */     
/*     */     protected final V value;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(char key, V value) {
/* 113 */       this.key = key;
/* 114 */       this.value = value;
/*     */     }
/*     */     
/*     */     public boolean containsKey(char k) {
/* 118 */       return (this.key == k);
/*     */     }
/*     */     
/*     */     public V get(char k) {
/* 122 */       return (this.key == k) ? this.value : this.defRetValue;
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
/*     */   public static <V> Char2ReferenceFunction<V> singleton(char key, V value) {
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
/*     */   public static <V> Char2ReferenceFunction<V> singleton(Character key, V value) {
/* 167 */     return new Singleton<>(key.charValue(), value);
/*     */   }
/*     */   
/*     */   public static class SynchronizedFunction<V> implements Char2ReferenceFunction<V>, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Char2ReferenceFunction<V> function;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedFunction(Char2ReferenceFunction<V> f, Object sync) {
/* 175 */       if (f == null)
/* 176 */         throw new NullPointerException(); 
/* 177 */       this.function = f;
/* 178 */       this.sync = sync;
/*     */     }
/*     */     protected SynchronizedFunction(Char2ReferenceFunction<V> f) {
/* 181 */       if (f == null)
/* 182 */         throw new NullPointerException(); 
/* 183 */       this.function = f;
/* 184 */       this.sync = this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V apply(int operand) {
/* 194 */       synchronized (this.sync) {
/* 195 */         return this.function.apply(operand);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V apply(Character key) {
/* 206 */       synchronized (this.sync) {
/* 207 */         return (V)this.function.apply(key);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int size() {
/* 212 */       synchronized (this.sync) {
/* 213 */         return this.function.size();
/*     */       } 
/*     */     }
/*     */     
/*     */     public V defaultReturnValue() {
/* 218 */       synchronized (this.sync) {
/* 219 */         return this.function.defaultReturnValue();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(V defRetValue) {
/* 224 */       synchronized (this.sync) {
/* 225 */         this.function.defaultReturnValue(defRetValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean containsKey(char k) {
/* 230 */       synchronized (this.sync) {
/* 231 */         return this.function.containsKey(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsKey(Object k) {
/* 237 */       synchronized (this.sync) {
/* 238 */         return this.function.containsKey(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V put(char k, V v) {
/* 243 */       synchronized (this.sync) {
/* 244 */         return this.function.put(k, v);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V get(char k) {
/* 249 */       synchronized (this.sync) {
/* 250 */         return this.function.get(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V remove(char k) {
/* 255 */       synchronized (this.sync) {
/* 256 */         return this.function.remove(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void clear() {
/* 261 */       synchronized (this.sync) {
/* 262 */         this.function.clear();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V put(Character k, V v) {
/* 273 */       synchronized (this.sync) {
/* 274 */         return this.function.put(k, v);
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
/* 285 */       synchronized (this.sync) {
/* 286 */         return this.function.get(k);
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
/* 297 */       synchronized (this.sync) {
/* 298 */         return this.function.remove(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 303 */       synchronized (this.sync) {
/* 304 */         return this.function.hashCode();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 309 */       if (o == this)
/* 310 */         return true; 
/* 311 */       synchronized (this.sync) {
/* 312 */         return this.function.equals(o);
/*     */       } 
/*     */     }
/*     */     
/*     */     public String toString() {
/* 317 */       synchronized (this.sync) {
/* 318 */         return this.function.toString();
/*     */       } 
/*     */     }
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 322 */       synchronized (this.sync) {
/* 323 */         s.defaultWriteObject();
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
/*     */   public static <V> Char2ReferenceFunction<V> synchronize(Char2ReferenceFunction<V> f) {
/* 337 */     return new SynchronizedFunction<>(f);
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
/*     */   public static <V> Char2ReferenceFunction<V> synchronize(Char2ReferenceFunction<V> f, Object sync) {
/* 352 */     return new SynchronizedFunction<>(f, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableFunction<V>
/*     */     extends AbstractChar2ReferenceFunction<V> implements Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Char2ReferenceFunction<V> function;
/*     */     
/*     */     protected UnmodifiableFunction(Char2ReferenceFunction<V> f) {
/* 361 */       if (f == null)
/* 362 */         throw new NullPointerException(); 
/* 363 */       this.function = f;
/*     */     }
/*     */     
/*     */     public int size() {
/* 367 */       return this.function.size();
/*     */     }
/*     */     
/*     */     public V defaultReturnValue() {
/* 371 */       return this.function.defaultReturnValue();
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(V defRetValue) {
/* 375 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean containsKey(char k) {
/* 379 */       return this.function.containsKey(k);
/*     */     }
/*     */     
/*     */     public V put(char k, V v) {
/* 383 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V get(char k) {
/* 387 */       return this.function.get(k);
/*     */     }
/*     */     
/*     */     public V remove(char k) {
/* 391 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 395 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V put(Character k, V v) {
/* 405 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V get(Object k) {
/* 415 */       return this.function.get(k);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V remove(Object k) {
/* 425 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 429 */       return this.function.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 433 */       return (o == this || this.function.equals(o));
/*     */     }
/*     */     
/*     */     public String toString() {
/* 437 */       return this.function.toString();
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
/*     */   public static <V> Char2ReferenceFunction<V> unmodifiable(Char2ReferenceFunction<V> f) {
/* 450 */     return new UnmodifiableFunction<>(f);
/*     */   }
/*     */   
/*     */   public static class PrimitiveFunction<V>
/*     */     implements Char2ReferenceFunction<V>
/*     */   {
/*     */     protected final Function<? super Character, ? extends V> function;
/*     */     
/*     */     protected PrimitiveFunction(Function<? super Character, ? extends V> function) {
/* 459 */       this.function = function;
/*     */     }
/*     */     
/*     */     public boolean containsKey(char key) {
/* 463 */       return (this.function.apply(Character.valueOf(key)) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsKey(Object key) {
/* 469 */       if (key == null)
/* 470 */         return false; 
/* 471 */       return (this.function.apply((Character)key) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public V get(char key) {
/* 476 */       V v = this.function.apply(Character.valueOf(key));
/* 477 */       if (v == null)
/* 478 */         return null; 
/* 479 */       return v;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V get(Object key) {
/* 485 */       if (key == null)
/* 486 */         return null; 
/* 487 */       return this.function.apply((Character)key);
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public V put(Character key, V value) {
/* 492 */       throw new UnsupportedOperationException();
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
/*     */   public static <V> Char2ReferenceFunction<V> primitive(Function<? super Character, ? extends V> f) {
/* 519 */     Objects.requireNonNull(f);
/* 520 */     if (f instanceof Char2ReferenceFunction)
/* 521 */       return (Char2ReferenceFunction)f; 
/* 522 */     if (f instanceof IntFunction) {
/* 523 */       Objects.requireNonNull((IntFunction)f); return (IntFunction)f::apply;
/* 524 */     }  return new PrimitiveFunction<>(f);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\Char2ReferenceFunctions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */