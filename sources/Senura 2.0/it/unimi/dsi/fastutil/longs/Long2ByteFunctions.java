/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.LongToIntFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Long2ByteFunctions
/*     */ {
/*     */   public static class EmptyFunction
/*     */     extends AbstractLong2ByteFunction
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public byte get(long k) {
/*  41 */       return 0;
/*     */     }
/*     */     
/*     */     public boolean containsKey(long k) {
/*  45 */       return false;
/*     */     }
/*     */     
/*     */     public byte defaultReturnValue() {
/*  49 */       return 0;
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(byte defRetValue) {
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
/*  64 */       return Long2ByteFunctions.EMPTY_FUNCTION;
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
/*  81 */       return Long2ByteFunctions.EMPTY_FUNCTION;
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
/*     */     extends AbstractLong2ByteFunction
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final long key;
/*     */ 
/*     */     
/*     */     protected final byte value;
/*     */ 
/*     */     
/*     */     protected Singleton(long key, byte value) {
/* 107 */       this.key = key;
/* 108 */       this.value = value;
/*     */     }
/*     */     
/*     */     public boolean containsKey(long k) {
/* 112 */       return (this.key == k);
/*     */     }
/*     */     
/*     */     public byte get(long k) {
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
/*     */   public static Long2ByteFunction singleton(long key, byte value) {
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
/*     */   public static Long2ByteFunction singleton(Long key, Byte value) {
/* 161 */     return new Singleton(key.longValue(), value.byteValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedFunction implements Long2ByteFunction, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Long2ByteFunction function;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedFunction(Long2ByteFunction f, Object sync) {
/* 169 */       if (f == null)
/* 170 */         throw new NullPointerException(); 
/* 171 */       this.function = f;
/* 172 */       this.sync = sync;
/*     */     }
/*     */     protected SynchronizedFunction(Long2ByteFunction f) {
/* 175 */       if (f == null)
/* 176 */         throw new NullPointerException(); 
/* 177 */       this.function = f;
/* 178 */       this.sync = this;
/*     */     }
/*     */     
/*     */     public int applyAsInt(long operand) {
/* 182 */       synchronized (this.sync) {
/* 183 */         return this.function.applyAsInt(operand);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte apply(Long key) {
/* 194 */       synchronized (this.sync) {
/* 195 */         return (Byte)this.function.apply(key);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int size() {
/* 200 */       synchronized (this.sync) {
/* 201 */         return this.function.size();
/*     */       } 
/*     */     }
/*     */     
/*     */     public byte defaultReturnValue() {
/* 206 */       synchronized (this.sync) {
/* 207 */         return this.function.defaultReturnValue();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(byte defRetValue) {
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
/*     */     public byte put(long k, byte v) {
/* 231 */       synchronized (this.sync) {
/* 232 */         return this.function.put(k, v);
/*     */       } 
/*     */     }
/*     */     
/*     */     public byte get(long k) {
/* 237 */       synchronized (this.sync) {
/* 238 */         return this.function.get(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public byte remove(long k) {
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
/*     */     public Byte put(Long k, Byte v) {
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
/*     */     public Byte get(Object k) {
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
/*     */     public Byte remove(Object k) {
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
/*     */   public static Long2ByteFunction synchronize(Long2ByteFunction f) {
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
/*     */   public static Long2ByteFunction synchronize(Long2ByteFunction f, Object sync) {
/* 340 */     return new SynchronizedFunction(f, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableFunction extends AbstractLong2ByteFunction implements Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Long2ByteFunction function;
/*     */     
/*     */     protected UnmodifiableFunction(Long2ByteFunction f) {
/* 347 */       if (f == null)
/* 348 */         throw new NullPointerException(); 
/* 349 */       this.function = f;
/*     */     }
/*     */     
/*     */     public int size() {
/* 353 */       return this.function.size();
/*     */     }
/*     */     
/*     */     public byte defaultReturnValue() {
/* 357 */       return this.function.defaultReturnValue();
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(byte defRetValue) {
/* 361 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean containsKey(long k) {
/* 365 */       return this.function.containsKey(k);
/*     */     }
/*     */     
/*     */     public byte put(long k, byte v) {
/* 369 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public byte get(long k) {
/* 373 */       return this.function.get(k);
/*     */     }
/*     */     
/*     */     public byte remove(long k) {
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
/*     */     public Byte put(Long k, Byte v) {
/* 391 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte get(Object k) {
/* 401 */       return this.function.get(k);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte remove(Object k) {
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
/*     */   public static Long2ByteFunction unmodifiable(Long2ByteFunction f) {
/* 436 */     return new UnmodifiableFunction(f);
/*     */   }
/*     */   
/*     */   public static class PrimitiveFunction
/*     */     implements Long2ByteFunction
/*     */   {
/*     */     protected final Function<? super Long, ? extends Byte> function;
/*     */     
/*     */     protected PrimitiveFunction(Function<? super Long, ? extends Byte> function) {
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
/*     */     public byte get(long key) {
/* 462 */       Byte v = this.function.apply(Long.valueOf(key));
/* 463 */       if (v == null)
/* 464 */         return defaultReturnValue(); 
/* 465 */       return v.byteValue();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte get(Object key) {
/* 471 */       if (key == null)
/* 472 */         return null; 
/* 473 */       return this.function.apply((Long)key);
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public Byte put(Long key, Byte value) {
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
/*     */   public static Long2ByteFunction primitive(Function<? super Long, ? extends Byte> f) {
/* 504 */     Objects.requireNonNull(f);
/* 505 */     if (f instanceof Long2ByteFunction)
/* 506 */       return (Long2ByteFunction)f; 
/* 507 */     if (f instanceof LongToIntFunction) {
/* 508 */       return key -> SafeMath.safeIntToByte(((LongToIntFunction)f).applyAsInt(key));
/*     */     }
/* 510 */     return new PrimitiveFunction(f);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\Long2ByteFunctions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */