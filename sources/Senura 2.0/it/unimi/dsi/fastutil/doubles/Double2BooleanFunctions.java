/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Objects;
/*     */ import java.util.function.DoublePredicate;
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
/*     */ public final class Double2BooleanFunctions
/*     */ {
/*     */   public static class EmptyFunction
/*     */     extends AbstractDouble2BooleanFunction
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public boolean get(double k) {
/*  44 */       return false;
/*     */     }
/*     */     
/*     */     public boolean containsKey(double k) {
/*  48 */       return false;
/*     */     }
/*     */     
/*     */     public boolean defaultReturnValue() {
/*  52 */       return false;
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(boolean defRetValue) {
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
/*  67 */       return Double2BooleanFunctions.EMPTY_FUNCTION;
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
/*  84 */       return Double2BooleanFunctions.EMPTY_FUNCTION;
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
/*     */   public static class Singleton
/*     */     extends AbstractDouble2BooleanFunction
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final double key;
/*     */ 
/*     */     
/*     */     protected final boolean value;
/*     */ 
/*     */     
/*     */     protected Singleton(double key, boolean value) {
/* 110 */       this.key = key;
/* 111 */       this.value = value;
/*     */     }
/*     */     
/*     */     public boolean containsKey(double k) {
/* 115 */       return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(k));
/*     */     }
/*     */     
/*     */     public boolean get(double k) {
/* 119 */       return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(k)) ? this.value : this.defRetValue;
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
/*     */   public static Double2BooleanFunction singleton(double key, boolean value) {
/* 146 */     return new Singleton(key, value);
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
/*     */   public static Double2BooleanFunction singleton(Double key, Boolean value) {
/* 164 */     return new Singleton(key.doubleValue(), value.booleanValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedFunction implements Double2BooleanFunction, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Double2BooleanFunction function;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedFunction(Double2BooleanFunction f, Object sync) {
/* 172 */       if (f == null)
/* 173 */         throw new NullPointerException(); 
/* 174 */       this.function = f;
/* 175 */       this.sync = sync;
/*     */     }
/*     */     protected SynchronizedFunction(Double2BooleanFunction f) {
/* 178 */       if (f == null)
/* 179 */         throw new NullPointerException(); 
/* 180 */       this.function = f;
/* 181 */       this.sync = this;
/*     */     }
/*     */     
/*     */     public boolean test(double operand) {
/* 185 */       synchronized (this.sync) {
/* 186 */         return this.function.test(operand);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Boolean apply(Double key) {
/* 197 */       synchronized (this.sync) {
/* 198 */         return (Boolean)this.function.apply(key);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int size() {
/* 203 */       synchronized (this.sync) {
/* 204 */         return this.function.size();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean defaultReturnValue() {
/* 209 */       synchronized (this.sync) {
/* 210 */         return this.function.defaultReturnValue();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(boolean defRetValue) {
/* 215 */       synchronized (this.sync) {
/* 216 */         this.function.defaultReturnValue(defRetValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean containsKey(double k) {
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
/*     */     public boolean put(double k, boolean v) {
/* 234 */       synchronized (this.sync) {
/* 235 */         return this.function.put(k, v);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean get(double k) {
/* 240 */       synchronized (this.sync) {
/* 241 */         return this.function.get(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean remove(double k) {
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
/*     */     public Boolean put(Double k, Boolean v) {
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
/*     */     public Boolean get(Object k) {
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
/*     */     public Boolean remove(Object k) {
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
/*     */   public static Double2BooleanFunction synchronize(Double2BooleanFunction f) {
/* 328 */     return new SynchronizedFunction(f);
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
/*     */   public static Double2BooleanFunction synchronize(Double2BooleanFunction f, Object sync) {
/* 343 */     return new SynchronizedFunction(f, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableFunction extends AbstractDouble2BooleanFunction implements Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Double2BooleanFunction function;
/*     */     
/*     */     protected UnmodifiableFunction(Double2BooleanFunction f) {
/* 350 */       if (f == null)
/* 351 */         throw new NullPointerException(); 
/* 352 */       this.function = f;
/*     */     }
/*     */     
/*     */     public int size() {
/* 356 */       return this.function.size();
/*     */     }
/*     */     
/*     */     public boolean defaultReturnValue() {
/* 360 */       return this.function.defaultReturnValue();
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(boolean defRetValue) {
/* 364 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean containsKey(double k) {
/* 368 */       return this.function.containsKey(k);
/*     */     }
/*     */     
/*     */     public boolean put(double k, boolean v) {
/* 372 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean get(double k) {
/* 376 */       return this.function.get(k);
/*     */     }
/*     */     
/*     */     public boolean remove(double k) {
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
/*     */     public Boolean put(Double k, Boolean v) {
/* 394 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Boolean get(Object k) {
/* 404 */       return this.function.get(k);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Boolean remove(Object k) {
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
/*     */   public static Double2BooleanFunction unmodifiable(Double2BooleanFunction f) {
/* 439 */     return new UnmodifiableFunction(f);
/*     */   }
/*     */   
/*     */   public static class PrimitiveFunction
/*     */     implements Double2BooleanFunction
/*     */   {
/*     */     protected final Function<? super Double, ? extends Boolean> function;
/*     */     
/*     */     protected PrimitiveFunction(Function<? super Double, ? extends Boolean> function) {
/* 448 */       this.function = function;
/*     */     }
/*     */     
/*     */     public boolean containsKey(double key) {
/* 452 */       return (this.function.apply(Double.valueOf(key)) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsKey(Object key) {
/* 458 */       if (key == null)
/* 459 */         return false; 
/* 460 */       return (this.function.apply((Double)key) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean get(double key) {
/* 465 */       Boolean v = this.function.apply(Double.valueOf(key));
/* 466 */       if (v == null)
/* 467 */         return defaultReturnValue(); 
/* 468 */       return v.booleanValue();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Boolean get(Object key) {
/* 474 */       if (key == null)
/* 475 */         return null; 
/* 476 */       return this.function.apply((Double)key);
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public Boolean put(Double key, Boolean value) {
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
/*     */   public static Double2BooleanFunction primitive(Function<? super Double, ? extends Boolean> f) {
/* 508 */     Objects.requireNonNull(f);
/* 509 */     if (f instanceof Double2BooleanFunction)
/* 510 */       return (Double2BooleanFunction)f; 
/* 511 */     if (f instanceof DoublePredicate) {
/* 512 */       Objects.requireNonNull((DoublePredicate)f); return (DoublePredicate)f::test;
/* 513 */     }  return new PrimitiveFunction(f);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2BooleanFunctions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */