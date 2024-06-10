/*     */ package it.unimi.dsi.fastutil.floats;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Objects;
/*     */ import java.util.function.DoubleToIntFunction;
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
/*     */ public final class Float2IntFunctions
/*     */ {
/*     */   public static class EmptyFunction
/*     */     extends AbstractFloat2IntFunction
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public int get(float k) {
/*  41 */       return 0;
/*     */     }
/*     */     
/*     */     public boolean containsKey(float k) {
/*  45 */       return false;
/*     */     }
/*     */     
/*     */     public int defaultReturnValue() {
/*  49 */       return 0;
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(int defRetValue) {
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
/*  64 */       return Float2IntFunctions.EMPTY_FUNCTION;
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
/*  81 */       return Float2IntFunctions.EMPTY_FUNCTION;
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
/*     */     extends AbstractFloat2IntFunction
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final float key;
/*     */ 
/*     */     
/*     */     protected final int value;
/*     */ 
/*     */     
/*     */     protected Singleton(float key, int value) {
/* 107 */       this.key = key;
/* 108 */       this.value = value;
/*     */     }
/*     */     
/*     */     public boolean containsKey(float k) {
/* 112 */       return (Float.floatToIntBits(this.key) == Float.floatToIntBits(k));
/*     */     }
/*     */     
/*     */     public int get(float k) {
/* 116 */       return (Float.floatToIntBits(this.key) == Float.floatToIntBits(k)) ? this.value : this.defRetValue;
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
/*     */   public static Float2IntFunction singleton(float key, int value) {
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
/*     */   public static Float2IntFunction singleton(Float key, Integer value) {
/* 161 */     return new Singleton(key.floatValue(), value.intValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedFunction implements Float2IntFunction, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Float2IntFunction function;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedFunction(Float2IntFunction f, Object sync) {
/* 169 */       if (f == null)
/* 170 */         throw new NullPointerException(); 
/* 171 */       this.function = f;
/* 172 */       this.sync = sync;
/*     */     }
/*     */     protected SynchronizedFunction(Float2IntFunction f) {
/* 175 */       if (f == null)
/* 176 */         throw new NullPointerException(); 
/* 177 */       this.function = f;
/* 178 */       this.sync = this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public int applyAsInt(double operand) {
/* 188 */       synchronized (this.sync) {
/* 189 */         return this.function.applyAsInt(operand);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer apply(Float key) {
/* 200 */       synchronized (this.sync) {
/* 201 */         return (Integer)this.function.apply(key);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int size() {
/* 206 */       synchronized (this.sync) {
/* 207 */         return this.function.size();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int defaultReturnValue() {
/* 212 */       synchronized (this.sync) {
/* 213 */         return this.function.defaultReturnValue();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(int defRetValue) {
/* 218 */       synchronized (this.sync) {
/* 219 */         this.function.defaultReturnValue(defRetValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean containsKey(float k) {
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
/*     */     public int put(float k, int v) {
/* 237 */       synchronized (this.sync) {
/* 238 */         return this.function.put(k, v);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int get(float k) {
/* 243 */       synchronized (this.sync) {
/* 244 */         return this.function.get(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int remove(float k) {
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
/*     */     public Integer put(Float k, Integer v) {
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
/*     */     public Integer get(Object k) {
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
/*     */     public Integer remove(Object k) {
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
/*     */   public static Float2IntFunction synchronize(Float2IntFunction f) {
/* 331 */     return new SynchronizedFunction(f);
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
/*     */   public static Float2IntFunction synchronize(Float2IntFunction f, Object sync) {
/* 346 */     return new SynchronizedFunction(f, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableFunction extends AbstractFloat2IntFunction implements Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Float2IntFunction function;
/*     */     
/*     */     protected UnmodifiableFunction(Float2IntFunction f) {
/* 353 */       if (f == null)
/* 354 */         throw new NullPointerException(); 
/* 355 */       this.function = f;
/*     */     }
/*     */     
/*     */     public int size() {
/* 359 */       return this.function.size();
/*     */     }
/*     */     
/*     */     public int defaultReturnValue() {
/* 363 */       return this.function.defaultReturnValue();
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(int defRetValue) {
/* 367 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean containsKey(float k) {
/* 371 */       return this.function.containsKey(k);
/*     */     }
/*     */     
/*     */     public int put(float k, int v) {
/* 375 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public int get(float k) {
/* 379 */       return this.function.get(k);
/*     */     }
/*     */     
/*     */     public int remove(float k) {
/* 383 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 387 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer put(Float k, Integer v) {
/* 397 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer get(Object k) {
/* 407 */       return this.function.get(k);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer remove(Object k) {
/* 417 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 421 */       return this.function.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 425 */       return (o == this || this.function.equals(o));
/*     */     }
/*     */     
/*     */     public String toString() {
/* 429 */       return this.function.toString();
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
/*     */   public static Float2IntFunction unmodifiable(Float2IntFunction f) {
/* 442 */     return new UnmodifiableFunction(f);
/*     */   }
/*     */   
/*     */   public static class PrimitiveFunction
/*     */     implements Float2IntFunction
/*     */   {
/*     */     protected final Function<? super Float, ? extends Integer> function;
/*     */     
/*     */     protected PrimitiveFunction(Function<? super Float, ? extends Integer> function) {
/* 451 */       this.function = function;
/*     */     }
/*     */     
/*     */     public boolean containsKey(float key) {
/* 455 */       return (this.function.apply(Float.valueOf(key)) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsKey(Object key) {
/* 461 */       if (key == null)
/* 462 */         return false; 
/* 463 */       return (this.function.apply((Float)key) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public int get(float key) {
/* 468 */       Integer v = this.function.apply(Float.valueOf(key));
/* 469 */       if (v == null)
/* 470 */         return defaultReturnValue(); 
/* 471 */       return v.intValue();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer get(Object key) {
/* 477 */       if (key == null)
/* 478 */         return null; 
/* 479 */       return this.function.apply((Float)key);
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public Integer put(Float key, Integer value) {
/* 484 */       throw new UnsupportedOperationException();
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
/*     */   public static Float2IntFunction primitive(Function<? super Float, ? extends Integer> f) {
/* 510 */     Objects.requireNonNull(f);
/* 511 */     if (f instanceof Float2IntFunction)
/* 512 */       return (Float2IntFunction)f; 
/* 513 */     if (f instanceof DoubleToIntFunction) {
/* 514 */       Objects.requireNonNull((DoubleToIntFunction)f); return (DoubleToIntFunction)f::applyAsInt;
/* 515 */     }  return new PrimitiveFunction(f);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2IntFunctions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */