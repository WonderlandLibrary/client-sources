/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.IntToLongFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Int2LongFunctions
/*     */ {
/*     */   public static class EmptyFunction
/*     */     extends AbstractInt2LongFunction
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public long get(int k) {
/*  41 */       return 0L;
/*     */     }
/*     */     
/*     */     public boolean containsKey(int k) {
/*  45 */       return false;
/*     */     }
/*     */     
/*     */     public long defaultReturnValue() {
/*  49 */       return 0L;
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(long defRetValue) {
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
/*  64 */       return Int2LongFunctions.EMPTY_FUNCTION;
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
/*  81 */       return Int2LongFunctions.EMPTY_FUNCTION;
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
/*     */     extends AbstractInt2LongFunction
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final int key;
/*     */ 
/*     */     
/*     */     protected final long value;
/*     */ 
/*     */     
/*     */     protected Singleton(int key, long value) {
/* 107 */       this.key = key;
/* 108 */       this.value = value;
/*     */     }
/*     */     
/*     */     public boolean containsKey(int k) {
/* 112 */       return (this.key == k);
/*     */     }
/*     */     
/*     */     public long get(int k) {
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
/*     */   public static Int2LongFunction singleton(int key, long value) {
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
/*     */   public static Int2LongFunction singleton(Integer key, Long value) {
/* 161 */     return new Singleton(key.intValue(), value.longValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedFunction implements Int2LongFunction, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Int2LongFunction function;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedFunction(Int2LongFunction f, Object sync) {
/* 169 */       if (f == null)
/* 170 */         throw new NullPointerException(); 
/* 171 */       this.function = f;
/* 172 */       this.sync = sync;
/*     */     }
/*     */     protected SynchronizedFunction(Int2LongFunction f) {
/* 175 */       if (f == null)
/* 176 */         throw new NullPointerException(); 
/* 177 */       this.function = f;
/* 178 */       this.sync = this;
/*     */     }
/*     */     
/*     */     public long applyAsLong(int operand) {
/* 182 */       synchronized (this.sync) {
/* 183 */         return this.function.applyAsLong(operand);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long apply(Integer key) {
/* 194 */       synchronized (this.sync) {
/* 195 */         return (Long)this.function.apply(key);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int size() {
/* 200 */       synchronized (this.sync) {
/* 201 */         return this.function.size();
/*     */       } 
/*     */     }
/*     */     
/*     */     public long defaultReturnValue() {
/* 206 */       synchronized (this.sync) {
/* 207 */         return this.function.defaultReturnValue();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(long defRetValue) {
/* 212 */       synchronized (this.sync) {
/* 213 */         this.function.defaultReturnValue(defRetValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean containsKey(int k) {
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
/*     */     public long put(int k, long v) {
/* 231 */       synchronized (this.sync) {
/* 232 */         return this.function.put(k, v);
/*     */       } 
/*     */     }
/*     */     
/*     */     public long get(int k) {
/* 237 */       synchronized (this.sync) {
/* 238 */         return this.function.get(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public long remove(int k) {
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
/*     */     public Long put(Integer k, Long v) {
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
/*     */     public Long get(Object k) {
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
/*     */     public Long remove(Object k) {
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
/*     */   public static Int2LongFunction synchronize(Int2LongFunction f) {
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
/*     */   public static Int2LongFunction synchronize(Int2LongFunction f, Object sync) {
/* 340 */     return new SynchronizedFunction(f, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableFunction extends AbstractInt2LongFunction implements Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Int2LongFunction function;
/*     */     
/*     */     protected UnmodifiableFunction(Int2LongFunction f) {
/* 347 */       if (f == null)
/* 348 */         throw new NullPointerException(); 
/* 349 */       this.function = f;
/*     */     }
/*     */     
/*     */     public int size() {
/* 353 */       return this.function.size();
/*     */     }
/*     */     
/*     */     public long defaultReturnValue() {
/* 357 */       return this.function.defaultReturnValue();
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(long defRetValue) {
/* 361 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean containsKey(int k) {
/* 365 */       return this.function.containsKey(k);
/*     */     }
/*     */     
/*     */     public long put(int k, long v) {
/* 369 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public long get(int k) {
/* 373 */       return this.function.get(k);
/*     */     }
/*     */     
/*     */     public long remove(int k) {
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
/*     */     public Long put(Integer k, Long v) {
/* 391 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long get(Object k) {
/* 401 */       return this.function.get(k);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long remove(Object k) {
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
/*     */   public static Int2LongFunction unmodifiable(Int2LongFunction f) {
/* 436 */     return new UnmodifiableFunction(f);
/*     */   }
/*     */   
/*     */   public static class PrimitiveFunction
/*     */     implements Int2LongFunction
/*     */   {
/*     */     protected final Function<? super Integer, ? extends Long> function;
/*     */     
/*     */     protected PrimitiveFunction(Function<? super Integer, ? extends Long> function) {
/* 445 */       this.function = function;
/*     */     }
/*     */     
/*     */     public boolean containsKey(int key) {
/* 449 */       return (this.function.apply(Integer.valueOf(key)) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsKey(Object key) {
/* 455 */       if (key == null)
/* 456 */         return false; 
/* 457 */       return (this.function.apply((Integer)key) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public long get(int key) {
/* 462 */       Long v = this.function.apply(Integer.valueOf(key));
/* 463 */       if (v == null)
/* 464 */         return defaultReturnValue(); 
/* 465 */       return v.longValue();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long get(Object key) {
/* 471 */       if (key == null)
/* 472 */         return null; 
/* 473 */       return this.function.apply((Integer)key);
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public Long put(Integer key, Long value) {
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
/*     */   public static Int2LongFunction primitive(Function<? super Integer, ? extends Long> f) {
/* 504 */     Objects.requireNonNull(f);
/* 505 */     if (f instanceof Int2LongFunction)
/* 506 */       return (Int2LongFunction)f; 
/* 507 */     if (f instanceof IntToLongFunction) {
/* 508 */       Objects.requireNonNull((IntToLongFunction)f); return (IntToLongFunction)f::applyAsLong;
/* 509 */     }  return new PrimitiveFunction(f);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\Int2LongFunctions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */