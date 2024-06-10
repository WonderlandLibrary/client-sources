/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.ToDoubleFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Object2FloatFunctions
/*     */ {
/*     */   public static class EmptyFunction<K>
/*     */     extends AbstractObject2FloatFunction<K>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public float getFloat(Object k) {
/*  44 */       return 0.0F;
/*     */     }
/*     */     
/*     */     public boolean containsKey(Object k) {
/*  48 */       return false;
/*     */     }
/*     */     
/*     */     public float defaultReturnValue() {
/*  52 */       return 0.0F;
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(float defRetValue) {
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
/*  67 */       return Object2FloatFunctions.EMPTY_FUNCTION;
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
/*  84 */       return Object2FloatFunctions.EMPTY_FUNCTION;
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
/*     */   public static class Singleton<K>
/*     */     extends AbstractObject2FloatFunction<K>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */     
/*     */     protected final K key;
/*     */ 
/*     */     
/*     */     protected final float value;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(K key, float value) {
/* 113 */       this.key = key;
/* 114 */       this.value = value;
/*     */     }
/*     */     
/*     */     public boolean containsKey(Object k) {
/* 118 */       return Objects.equals(this.key, k);
/*     */     }
/*     */     
/*     */     public float getFloat(Object k) {
/* 122 */       return Objects.equals(this.key, k) ? this.value : this.defRetValue;
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
/*     */   public static <K> Object2FloatFunction<K> singleton(K key, float value) {
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
/*     */   public static <K> Object2FloatFunction<K> singleton(K key, Float value) {
/* 167 */     return new Singleton<>(key, value.floatValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedFunction<K> implements Object2FloatFunction<K>, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2FloatFunction<K> function;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedFunction(Object2FloatFunction<K> f, Object sync) {
/* 175 */       if (f == null)
/* 176 */         throw new NullPointerException(); 
/* 177 */       this.function = f;
/* 178 */       this.sync = sync;
/*     */     }
/*     */     protected SynchronizedFunction(Object2FloatFunction<K> f) {
/* 181 */       if (f == null)
/* 182 */         throw new NullPointerException(); 
/* 183 */       this.function = f;
/* 184 */       this.sync = this;
/*     */     }
/*     */     
/*     */     public double applyAsDouble(K operand) {
/* 188 */       synchronized (this.sync) {
/* 189 */         return this.function.applyAsDouble(operand);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float apply(K key) {
/* 200 */       synchronized (this.sync) {
/* 201 */         return (Float)this.function.apply(key);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int size() {
/* 206 */       synchronized (this.sync) {
/* 207 */         return this.function.size();
/*     */       } 
/*     */     }
/*     */     
/*     */     public float defaultReturnValue() {
/* 212 */       synchronized (this.sync) {
/* 213 */         return this.function.defaultReturnValue();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(float defRetValue) {
/* 218 */       synchronized (this.sync) {
/* 219 */         this.function.defaultReturnValue(defRetValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean containsKey(Object k) {
/* 224 */       synchronized (this.sync) {
/* 225 */         return this.function.containsKey(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public float put(K k, float v) {
/* 230 */       synchronized (this.sync) {
/* 231 */         return this.function.put(k, v);
/*     */       } 
/*     */     }
/*     */     
/*     */     public float getFloat(Object k) {
/* 236 */       synchronized (this.sync) {
/* 237 */         return this.function.getFloat(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public float removeFloat(Object k) {
/* 242 */       synchronized (this.sync) {
/* 243 */         return this.function.removeFloat(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void clear() {
/* 248 */       synchronized (this.sync) {
/* 249 */         this.function.clear();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float put(K k, Float v) {
/* 260 */       synchronized (this.sync) {
/* 261 */         return this.function.put(k, v);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float get(Object k) {
/* 272 */       synchronized (this.sync) {
/* 273 */         return this.function.get(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float remove(Object k) {
/* 284 */       synchronized (this.sync) {
/* 285 */         return this.function.remove(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 290 */       synchronized (this.sync) {
/* 291 */         return this.function.hashCode();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 296 */       if (o == this)
/* 297 */         return true; 
/* 298 */       synchronized (this.sync) {
/* 299 */         return this.function.equals(o);
/*     */       } 
/*     */     }
/*     */     
/*     */     public String toString() {
/* 304 */       synchronized (this.sync) {
/* 305 */         return this.function.toString();
/*     */       } 
/*     */     }
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 309 */       synchronized (this.sync) {
/* 310 */         s.defaultWriteObject();
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
/*     */   public static <K> Object2FloatFunction<K> synchronize(Object2FloatFunction<K> f) {
/* 324 */     return new SynchronizedFunction<>(f);
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
/*     */   public static <K> Object2FloatFunction<K> synchronize(Object2FloatFunction<K> f, Object sync) {
/* 339 */     return new SynchronizedFunction<>(f, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableFunction<K>
/*     */     extends AbstractObject2FloatFunction<K> implements Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2FloatFunction<K> function;
/*     */     
/*     */     protected UnmodifiableFunction(Object2FloatFunction<K> f) {
/* 348 */       if (f == null)
/* 349 */         throw new NullPointerException(); 
/* 350 */       this.function = f;
/*     */     }
/*     */     
/*     */     public int size() {
/* 354 */       return this.function.size();
/*     */     }
/*     */     
/*     */     public float defaultReturnValue() {
/* 358 */       return this.function.defaultReturnValue();
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(float defRetValue) {
/* 362 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean containsKey(Object k) {
/* 366 */       return this.function.containsKey(k);
/*     */     }
/*     */     
/*     */     public float put(K k, float v) {
/* 370 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public float getFloat(Object k) {
/* 374 */       return this.function.getFloat(k);
/*     */     }
/*     */     
/*     */     public float removeFloat(Object k) {
/* 378 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 382 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float put(K k, Float v) {
/* 392 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float get(Object k) {
/* 402 */       return this.function.get(k);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float remove(Object k) {
/* 412 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 416 */       return this.function.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 420 */       return (o == this || this.function.equals(o));
/*     */     }
/*     */     
/*     */     public String toString() {
/* 424 */       return this.function.toString();
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
/*     */   public static <K> Object2FloatFunction<K> unmodifiable(Object2FloatFunction<K> f) {
/* 437 */     return new UnmodifiableFunction<>(f);
/*     */   }
/*     */   
/*     */   public static class PrimitiveFunction<K>
/*     */     implements Object2FloatFunction<K>
/*     */   {
/*     */     protected final Function<? super K, ? extends Float> function;
/*     */     
/*     */     protected PrimitiveFunction(Function<? super K, ? extends Float> function) {
/* 446 */       this.function = function;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(Object key) {
/* 451 */       return (this.function.apply((K)key) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public float getFloat(Object key) {
/* 456 */       Float v = this.function.apply((K)key);
/* 457 */       if (v == null)
/* 458 */         return defaultReturnValue(); 
/* 459 */       return v.floatValue();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float get(Object key) {
/* 465 */       return this.function.apply((K)key);
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public Float put(K key, Float value) {
/* 470 */       throw new UnsupportedOperationException();
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
/*     */   public static <K> Object2FloatFunction<K> primitive(Function<? super K, ? extends Float> f) {
/* 497 */     Objects.requireNonNull(f);
/* 498 */     if (f instanceof Object2FloatFunction)
/* 499 */       return (Object2FloatFunction)f; 
/* 500 */     if (f instanceof ToDoubleFunction) {
/* 501 */       return key -> SafeMath.safeDoubleToFloat(((ToDoubleFunction<Object>)f).applyAsDouble(key));
/*     */     }
/* 503 */     return new PrimitiveFunction<>(f);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2FloatFunctions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */