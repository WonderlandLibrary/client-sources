/*     */ package it.unimi.dsi.fastutil.chars;
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
/*     */ public final class Char2LongFunctions
/*     */ {
/*     */   public static class EmptyFunction
/*     */     extends AbstractChar2LongFunction
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public long get(char k) {
/*  41 */       return 0L;
/*     */     }
/*     */     
/*     */     public boolean containsKey(char k) {
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
/*  64 */       return Char2LongFunctions.EMPTY_FUNCTION;
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
/*  81 */       return Char2LongFunctions.EMPTY_FUNCTION;
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
/*     */     extends AbstractChar2LongFunction
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final char key;
/*     */ 
/*     */     
/*     */     protected final long value;
/*     */ 
/*     */     
/*     */     protected Singleton(char key, long value) {
/* 107 */       this.key = key;
/* 108 */       this.value = value;
/*     */     }
/*     */     
/*     */     public boolean containsKey(char k) {
/* 112 */       return (this.key == k);
/*     */     }
/*     */     
/*     */     public long get(char k) {
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
/*     */   public static Char2LongFunction singleton(char key, long value) {
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
/*     */   public static Char2LongFunction singleton(Character key, Long value) {
/* 161 */     return new Singleton(key.charValue(), value.longValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedFunction implements Char2LongFunction, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Char2LongFunction function;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedFunction(Char2LongFunction f, Object sync) {
/* 169 */       if (f == null)
/* 170 */         throw new NullPointerException(); 
/* 171 */       this.function = f;
/* 172 */       this.sync = sync;
/*     */     }
/*     */     protected SynchronizedFunction(Char2LongFunction f) {
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
/*     */     public long applyAsLong(int operand) {
/* 188 */       synchronized (this.sync) {
/* 189 */         return this.function.applyAsLong(operand);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long apply(Character key) {
/* 200 */       synchronized (this.sync) {
/* 201 */         return (Long)this.function.apply(key);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int size() {
/* 206 */       synchronized (this.sync) {
/* 207 */         return this.function.size();
/*     */       } 
/*     */     }
/*     */     
/*     */     public long defaultReturnValue() {
/* 212 */       synchronized (this.sync) {
/* 213 */         return this.function.defaultReturnValue();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(long defRetValue) {
/* 218 */       synchronized (this.sync) {
/* 219 */         this.function.defaultReturnValue(defRetValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean containsKey(char k) {
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
/*     */     public long put(char k, long v) {
/* 237 */       synchronized (this.sync) {
/* 238 */         return this.function.put(k, v);
/*     */       } 
/*     */     }
/*     */     
/*     */     public long get(char k) {
/* 243 */       synchronized (this.sync) {
/* 244 */         return this.function.get(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public long remove(char k) {
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
/*     */     public Long put(Character k, Long v) {
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
/*     */     public Long get(Object k) {
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
/*     */     public Long remove(Object k) {
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
/*     */   public static Char2LongFunction synchronize(Char2LongFunction f) {
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
/*     */   public static Char2LongFunction synchronize(Char2LongFunction f, Object sync) {
/* 346 */     return new SynchronizedFunction(f, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableFunction extends AbstractChar2LongFunction implements Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Char2LongFunction function;
/*     */     
/*     */     protected UnmodifiableFunction(Char2LongFunction f) {
/* 353 */       if (f == null)
/* 354 */         throw new NullPointerException(); 
/* 355 */       this.function = f;
/*     */     }
/*     */     
/*     */     public int size() {
/* 359 */       return this.function.size();
/*     */     }
/*     */     
/*     */     public long defaultReturnValue() {
/* 363 */       return this.function.defaultReturnValue();
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(long defRetValue) {
/* 367 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean containsKey(char k) {
/* 371 */       return this.function.containsKey(k);
/*     */     }
/*     */     
/*     */     public long put(char k, long v) {
/* 375 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public long get(char k) {
/* 379 */       return this.function.get(k);
/*     */     }
/*     */     
/*     */     public long remove(char k) {
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
/*     */     public Long put(Character k, Long v) {
/* 397 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long get(Object k) {
/* 407 */       return this.function.get(k);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long remove(Object k) {
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
/*     */   public static Char2LongFunction unmodifiable(Char2LongFunction f) {
/* 442 */     return new UnmodifiableFunction(f);
/*     */   }
/*     */   
/*     */   public static class PrimitiveFunction
/*     */     implements Char2LongFunction
/*     */   {
/*     */     protected final Function<? super Character, ? extends Long> function;
/*     */     
/*     */     protected PrimitiveFunction(Function<? super Character, ? extends Long> function) {
/* 451 */       this.function = function;
/*     */     }
/*     */     
/*     */     public boolean containsKey(char key) {
/* 455 */       return (this.function.apply(Character.valueOf(key)) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsKey(Object key) {
/* 461 */       if (key == null)
/* 462 */         return false; 
/* 463 */       return (this.function.apply((Character)key) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public long get(char key) {
/* 468 */       Long v = this.function.apply(Character.valueOf(key));
/* 469 */       if (v == null)
/* 470 */         return defaultReturnValue(); 
/* 471 */       return v.longValue();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long get(Object key) {
/* 477 */       if (key == null)
/* 478 */         return null; 
/* 479 */       return this.function.apply((Character)key);
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public Long put(Character key, Long value) {
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
/*     */   public static Char2LongFunction primitive(Function<? super Character, ? extends Long> f) {
/* 510 */     Objects.requireNonNull(f);
/* 511 */     if (f instanceof Char2LongFunction)
/* 512 */       return (Char2LongFunction)f; 
/* 513 */     if (f instanceof IntToLongFunction) {
/* 514 */       Objects.requireNonNull((IntToLongFunction)f); return (IntToLongFunction)f::applyAsLong;
/* 515 */     }  return new PrimitiveFunction(f);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\Char2LongFunctions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */