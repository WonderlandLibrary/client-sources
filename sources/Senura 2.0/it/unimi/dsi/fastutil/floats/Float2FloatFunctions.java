/*     */ package it.unimi.dsi.fastutil.floats;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Objects;
/*     */ import java.util.function.DoubleUnaryOperator;
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
/*     */ public final class Float2FloatFunctions
/*     */ {
/*     */   public static class EmptyFunction
/*     */     extends AbstractFloat2FloatFunction
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public float get(float k) {
/*  41 */       return 0.0F;
/*     */     }
/*     */     
/*     */     public boolean containsKey(float k) {
/*  45 */       return false;
/*     */     }
/*     */     
/*     */     public float defaultReturnValue() {
/*  49 */       return 0.0F;
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(float defRetValue) {
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
/*  64 */       return Float2FloatFunctions.EMPTY_FUNCTION;
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
/*  81 */       return Float2FloatFunctions.EMPTY_FUNCTION;
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
/*     */     extends AbstractFloat2FloatFunction
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final float key;
/*     */ 
/*     */     
/*     */     protected final float value;
/*     */ 
/*     */     
/*     */     protected Singleton(float key, float value) {
/* 107 */       this.key = key;
/* 108 */       this.value = value;
/*     */     }
/*     */     
/*     */     public boolean containsKey(float k) {
/* 112 */       return (Float.floatToIntBits(this.key) == Float.floatToIntBits(k));
/*     */     }
/*     */     
/*     */     public float get(float k) {
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
/*     */   public static Float2FloatFunction singleton(float key, float value) {
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
/*     */   public static Float2FloatFunction singleton(Float key, Float value) {
/* 161 */     return new Singleton(key.floatValue(), value.floatValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedFunction implements Float2FloatFunction, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Float2FloatFunction function;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedFunction(Float2FloatFunction f, Object sync) {
/* 169 */       if (f == null)
/* 170 */         throw new NullPointerException(); 
/* 171 */       this.function = f;
/* 172 */       this.sync = sync;
/*     */     }
/*     */     protected SynchronizedFunction(Float2FloatFunction f) {
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
/*     */     public double applyAsDouble(double operand) {
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
/*     */     public Float apply(Float key) {
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
/*     */     public float put(float k, float v) {
/* 237 */       synchronized (this.sync) {
/* 238 */         return this.function.put(k, v);
/*     */       } 
/*     */     }
/*     */     
/*     */     public float get(float k) {
/* 243 */       synchronized (this.sync) {
/* 244 */         return this.function.get(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public float remove(float k) {
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
/*     */     public Float put(Float k, Float v) {
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
/*     */     public Float get(Object k) {
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
/*     */     public Float remove(Object k) {
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
/*     */   public static Float2FloatFunction synchronize(Float2FloatFunction f) {
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
/*     */   public static Float2FloatFunction synchronize(Float2FloatFunction f, Object sync) {
/* 346 */     return new SynchronizedFunction(f, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableFunction extends AbstractFloat2FloatFunction implements Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Float2FloatFunction function;
/*     */     
/*     */     protected UnmodifiableFunction(Float2FloatFunction f) {
/* 353 */       if (f == null)
/* 354 */         throw new NullPointerException(); 
/* 355 */       this.function = f;
/*     */     }
/*     */     
/*     */     public int size() {
/* 359 */       return this.function.size();
/*     */     }
/*     */     
/*     */     public float defaultReturnValue() {
/* 363 */       return this.function.defaultReturnValue();
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(float defRetValue) {
/* 367 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean containsKey(float k) {
/* 371 */       return this.function.containsKey(k);
/*     */     }
/*     */     
/*     */     public float put(float k, float v) {
/* 375 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public float get(float k) {
/* 379 */       return this.function.get(k);
/*     */     }
/*     */     
/*     */     public float remove(float k) {
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
/*     */     public Float put(Float k, Float v) {
/* 397 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float get(Object k) {
/* 407 */       return this.function.get(k);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float remove(Object k) {
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
/*     */   public static Float2FloatFunction unmodifiable(Float2FloatFunction f) {
/* 442 */     return new UnmodifiableFunction(f);
/*     */   }
/*     */   
/*     */   public static class PrimitiveFunction
/*     */     implements Float2FloatFunction
/*     */   {
/*     */     protected final Function<? super Float, ? extends Float> function;
/*     */     
/*     */     protected PrimitiveFunction(Function<? super Float, ? extends Float> function) {
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
/*     */     public float get(float key) {
/* 468 */       Float v = this.function.apply(Float.valueOf(key));
/* 469 */       if (v == null)
/* 470 */         return defaultReturnValue(); 
/* 471 */       return v.floatValue();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float get(Object key) {
/* 477 */       if (key == null)
/* 478 */         return null; 
/* 479 */       return this.function.apply((Float)key);
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public Float put(Float key, Float value) {
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
/*     */   public static Float2FloatFunction primitive(Function<? super Float, ? extends Float> f) {
/* 510 */     Objects.requireNonNull(f);
/* 511 */     if (f instanceof Float2FloatFunction)
/* 512 */       return (Float2FloatFunction)f; 
/* 513 */     if (f instanceof DoubleUnaryOperator) {
/* 514 */       return key -> SafeMath.safeDoubleToFloat(((DoubleUnaryOperator)f).applyAsDouble(key));
/*     */     }
/* 516 */     return new PrimitiveFunction(f);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2FloatFunctions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */