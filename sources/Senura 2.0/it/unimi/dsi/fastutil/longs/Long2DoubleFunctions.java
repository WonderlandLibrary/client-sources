/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.LongToDoubleFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Long2DoubleFunctions
/*     */ {
/*     */   public static class EmptyFunction
/*     */     extends AbstractLong2DoubleFunction
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public double get(long k) {
/*  41 */       return 0.0D;
/*     */     }
/*     */     
/*     */     public boolean containsKey(long k) {
/*  45 */       return false;
/*     */     }
/*     */     
/*     */     public double defaultReturnValue() {
/*  49 */       return 0.0D;
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(double defRetValue) {
/*  53 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public int size() {
/*  57 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {}
/*     */     
/*     */     public Object clone() {
/*  64 */       return Long2DoubleFunctions.EMPTY_FUNCTION;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/*  68 */       return 0;
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/*  72 */       if (!(o instanceof Function))
/*  73 */         return false; 
/*  74 */       return (((Function)o).size() == 0);
/*     */     }
/*     */     
/*     */     public String toString() {
/*  78 */       return "{}";
/*     */     }
/*     */     private Object readResolve() {
/*  81 */       return Long2DoubleFunctions.EMPTY_FUNCTION;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton
/*     */     extends AbstractLong2DoubleFunction
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final long key;
/*     */ 
/*     */     
/*     */     protected final double value;
/*     */ 
/*     */     
/*     */     protected Singleton(long key, double value) {
/* 107 */       this.key = key;
/* 108 */       this.value = value;
/*     */     }
/*     */     
/*     */     public boolean containsKey(long k) {
/* 112 */       return (this.key == k);
/*     */     }
/*     */     
/*     */     public double get(long k) {
/* 116 */       return (this.key == k) ? this.value : this.defRetValue;
/*     */     }
/*     */     
/*     */     public int size() {
/* 120 */       return 1;
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 124 */       return this;
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
/*     */   public static Long2DoubleFunction singleton(long key, double value) {
/* 143 */     return new Singleton(key, value);
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
/*     */   public static Long2DoubleFunction singleton(Long key, Double value) {
/* 161 */     return new Singleton(key.longValue(), value.doubleValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedFunction implements Long2DoubleFunction, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Long2DoubleFunction function;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedFunction(Long2DoubleFunction f, Object sync) {
/* 169 */       if (f == null)
/* 170 */         throw new NullPointerException(); 
/* 171 */       this.function = f;
/* 172 */       this.sync = sync;
/*     */     }
/*     */     protected SynchronizedFunction(Long2DoubleFunction f) {
/* 175 */       if (f == null)
/* 176 */         throw new NullPointerException(); 
/* 177 */       this.function = f;
/* 178 */       this.sync = this;
/*     */     }
/*     */     
/*     */     public double applyAsDouble(long operand) {
/* 182 */       synchronized (this.sync) {
/* 183 */         return this.function.applyAsDouble(operand);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double apply(Long key) {
/* 194 */       synchronized (this.sync) {
/* 195 */         return (Double)this.function.apply(key);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int size() {
/* 200 */       synchronized (this.sync) {
/* 201 */         return this.function.size();
/*     */       } 
/*     */     }
/*     */     
/*     */     public double defaultReturnValue() {
/* 206 */       synchronized (this.sync) {
/* 207 */         return this.function.defaultReturnValue();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(double defRetValue) {
/* 212 */       synchronized (this.sync) {
/* 213 */         this.function.defaultReturnValue(defRetValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean containsKey(long k) {
/* 218 */       synchronized (this.sync) {
/* 219 */         return this.function.containsKey(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsKey(Object k) {
/* 225 */       synchronized (this.sync) {
/* 226 */         return this.function.containsKey(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public double put(long k, double v) {
/* 231 */       synchronized (this.sync) {
/* 232 */         return this.function.put(k, v);
/*     */       } 
/*     */     }
/*     */     
/*     */     public double get(long k) {
/* 237 */       synchronized (this.sync) {
/* 238 */         return this.function.get(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public double remove(long k) {
/* 243 */       synchronized (this.sync) {
/* 244 */         return this.function.remove(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void clear() {
/* 249 */       synchronized (this.sync) {
/* 250 */         this.function.clear();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double put(Long k, Double v) {
/* 261 */       synchronized (this.sync) {
/* 262 */         return this.function.put(k, v);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double get(Object k) {
/* 273 */       synchronized (this.sync) {
/* 274 */         return this.function.get(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double remove(Object k) {
/* 285 */       synchronized (this.sync) {
/* 286 */         return this.function.remove(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 291 */       synchronized (this.sync) {
/* 292 */         return this.function.hashCode();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 297 */       if (o == this)
/* 298 */         return true; 
/* 299 */       synchronized (this.sync) {
/* 300 */         return this.function.equals(o);
/*     */       } 
/*     */     }
/*     */     
/*     */     public String toString() {
/* 305 */       synchronized (this.sync) {
/* 306 */         return this.function.toString();
/*     */       } 
/*     */     }
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 310 */       synchronized (this.sync) {
/* 311 */         s.defaultWriteObject();
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
/*     */   public static Long2DoubleFunction synchronize(Long2DoubleFunction f) {
/* 325 */     return new SynchronizedFunction(f);
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
/*     */   public static Long2DoubleFunction synchronize(Long2DoubleFunction f, Object sync) {
/* 340 */     return new SynchronizedFunction(f, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableFunction extends AbstractLong2DoubleFunction implements Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Long2DoubleFunction function;
/*     */     
/*     */     protected UnmodifiableFunction(Long2DoubleFunction f) {
/* 347 */       if (f == null)
/* 348 */         throw new NullPointerException(); 
/* 349 */       this.function = f;
/*     */     }
/*     */     
/*     */     public int size() {
/* 353 */       return this.function.size();
/*     */     }
/*     */     
/*     */     public double defaultReturnValue() {
/* 357 */       return this.function.defaultReturnValue();
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(double defRetValue) {
/* 361 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean containsKey(long k) {
/* 365 */       return this.function.containsKey(k);
/*     */     }
/*     */     
/*     */     public double put(long k, double v) {
/* 369 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public double get(long k) {
/* 373 */       return this.function.get(k);
/*     */     }
/*     */     
/*     */     public double remove(long k) {
/* 377 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 381 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double put(Long k, Double v) {
/* 391 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double get(Object k) {
/* 401 */       return this.function.get(k);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double remove(Object k) {
/* 411 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 415 */       return this.function.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 419 */       return (o == this || this.function.equals(o));
/*     */     }
/*     */     
/*     */     public String toString() {
/* 423 */       return this.function.toString();
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
/*     */   public static Long2DoubleFunction unmodifiable(Long2DoubleFunction f) {
/* 436 */     return new UnmodifiableFunction(f);
/*     */   }
/*     */   
/*     */   public static class PrimitiveFunction
/*     */     implements Long2DoubleFunction
/*     */   {
/*     */     protected final Function<? super Long, ? extends Double> function;
/*     */     
/*     */     protected PrimitiveFunction(Function<? super Long, ? extends Double> function) {
/* 445 */       this.function = function;
/*     */     }
/*     */     
/*     */     public boolean containsKey(long key) {
/* 449 */       return (this.function.apply(Long.valueOf(key)) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsKey(Object key) {
/* 455 */       if (key == null)
/* 456 */         return false; 
/* 457 */       return (this.function.apply((Long)key) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public double get(long key) {
/* 462 */       Double v = this.function.apply(Long.valueOf(key));
/* 463 */       if (v == null)
/* 464 */         return defaultReturnValue(); 
/* 465 */       return v.doubleValue();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double get(Object key) {
/* 471 */       if (key == null)
/* 472 */         return null; 
/* 473 */       return this.function.apply((Long)key);
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public Double put(Long key, Double value) {
/* 478 */       throw new UnsupportedOperationException();
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
/*     */   public static Long2DoubleFunction primitive(Function<? super Long, ? extends Double> f) {
/* 504 */     Objects.requireNonNull(f);
/* 505 */     if (f instanceof Long2DoubleFunction)
/* 506 */       return (Long2DoubleFunction)f; 
/* 507 */     if (f instanceof LongToDoubleFunction) {
/* 508 */       Objects.requireNonNull((LongToDoubleFunction)f); return (LongToDoubleFunction)f::applyAsDouble;
/* 509 */     }  return new PrimitiveFunction(f);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\Long2DoubleFunctions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */