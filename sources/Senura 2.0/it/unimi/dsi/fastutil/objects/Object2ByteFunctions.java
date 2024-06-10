/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.ToIntFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Object2ByteFunctions
/*     */ {
/*     */   public static class EmptyFunction<K>
/*     */     extends AbstractObject2ByteFunction<K>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public byte getByte(Object k) {
/*  44 */       return 0;
/*     */     }
/*     */     
/*     */     public boolean containsKey(Object k) {
/*  48 */       return false;
/*     */     }
/*     */     
/*     */     public byte defaultReturnValue() {
/*  52 */       return 0;
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(byte defRetValue) {
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
/*  67 */       return Object2ByteFunctions.EMPTY_FUNCTION;
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
/*  84 */       return Object2ByteFunctions.EMPTY_FUNCTION;
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
/*     */   public static class Singleton<K>
/*     */     extends AbstractObject2ByteFunction<K>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final K key;
/*     */ 
/*     */     
/*     */     protected final byte value;
/*     */ 
/*     */     
/*     */     protected Singleton(K key, byte value) {
/* 110 */       this.key = key;
/* 111 */       this.value = value;
/*     */     }
/*     */     
/*     */     public boolean containsKey(Object k) {
/* 115 */       return Objects.equals(this.key, k);
/*     */     }
/*     */     
/*     */     public byte getByte(Object k) {
/* 119 */       return Objects.equals(this.key, k) ? this.value : this.defRetValue;
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
/*     */   public static <K> Object2ByteFunction<K> singleton(K key, byte value) {
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
/*     */   public static <K> Object2ByteFunction<K> singleton(K key, Byte value) {
/* 164 */     return new Singleton<>(key, value.byteValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedFunction<K> implements Object2ByteFunction<K>, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2ByteFunction<K> function;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedFunction(Object2ByteFunction<K> f, Object sync) {
/* 172 */       if (f == null)
/* 173 */         throw new NullPointerException(); 
/* 174 */       this.function = f;
/* 175 */       this.sync = sync;
/*     */     }
/*     */     protected SynchronizedFunction(Object2ByteFunction<K> f) {
/* 178 */       if (f == null)
/* 179 */         throw new NullPointerException(); 
/* 180 */       this.function = f;
/* 181 */       this.sync = this;
/*     */     }
/*     */     
/*     */     public int applyAsInt(K operand) {
/* 185 */       synchronized (this.sync) {
/* 186 */         return this.function.applyAsInt(operand);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte apply(K key) {
/* 197 */       synchronized (this.sync) {
/* 198 */         return (Byte)this.function.apply(key);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int size() {
/* 203 */       synchronized (this.sync) {
/* 204 */         return this.function.size();
/*     */       } 
/*     */     }
/*     */     
/*     */     public byte defaultReturnValue() {
/* 209 */       synchronized (this.sync) {
/* 210 */         return this.function.defaultReturnValue();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(byte defRetValue) {
/* 215 */       synchronized (this.sync) {
/* 216 */         this.function.defaultReturnValue(defRetValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean containsKey(Object k) {
/* 221 */       synchronized (this.sync) {
/* 222 */         return this.function.containsKey(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public byte put(K k, byte v) {
/* 227 */       synchronized (this.sync) {
/* 228 */         return this.function.put(k, v);
/*     */       } 
/*     */     }
/*     */     
/*     */     public byte getByte(Object k) {
/* 233 */       synchronized (this.sync) {
/* 234 */         return this.function.getByte(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public byte removeByte(Object k) {
/* 239 */       synchronized (this.sync) {
/* 240 */         return this.function.removeByte(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void clear() {
/* 245 */       synchronized (this.sync) {
/* 246 */         this.function.clear();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte put(K k, Byte v) {
/* 257 */       synchronized (this.sync) {
/* 258 */         return this.function.put(k, v);
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
/* 269 */       synchronized (this.sync) {
/* 270 */         return this.function.get(k);
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
/* 281 */       synchronized (this.sync) {
/* 282 */         return this.function.remove(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 287 */       synchronized (this.sync) {
/* 288 */         return this.function.hashCode();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 293 */       if (o == this)
/* 294 */         return true; 
/* 295 */       synchronized (this.sync) {
/* 296 */         return this.function.equals(o);
/*     */       } 
/*     */     }
/*     */     
/*     */     public String toString() {
/* 301 */       synchronized (this.sync) {
/* 302 */         return this.function.toString();
/*     */       } 
/*     */     }
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 306 */       synchronized (this.sync) {
/* 307 */         s.defaultWriteObject();
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
/*     */   public static <K> Object2ByteFunction<K> synchronize(Object2ByteFunction<K> f) {
/* 321 */     return new SynchronizedFunction<>(f);
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
/*     */   public static <K> Object2ByteFunction<K> synchronize(Object2ByteFunction<K> f, Object sync) {
/* 336 */     return new SynchronizedFunction<>(f, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableFunction<K> extends AbstractObject2ByteFunction<K> implements Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2ByteFunction<K> function;
/*     */     
/*     */     protected UnmodifiableFunction(Object2ByteFunction<K> f) {
/* 343 */       if (f == null)
/* 344 */         throw new NullPointerException(); 
/* 345 */       this.function = f;
/*     */     }
/*     */     
/*     */     public int size() {
/* 349 */       return this.function.size();
/*     */     }
/*     */     
/*     */     public byte defaultReturnValue() {
/* 353 */       return this.function.defaultReturnValue();
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(byte defRetValue) {
/* 357 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean containsKey(Object k) {
/* 361 */       return this.function.containsKey(k);
/*     */     }
/*     */     
/*     */     public byte put(K k, byte v) {
/* 365 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public byte getByte(Object k) {
/* 369 */       return this.function.getByte(k);
/*     */     }
/*     */     
/*     */     public byte removeByte(Object k) {
/* 373 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 377 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte put(K k, Byte v) {
/* 387 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte get(Object k) {
/* 397 */       return this.function.get(k);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte remove(Object k) {
/* 407 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 411 */       return this.function.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 415 */       return (o == this || this.function.equals(o));
/*     */     }
/*     */     
/*     */     public String toString() {
/* 419 */       return this.function.toString();
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
/*     */   public static <K> Object2ByteFunction<K> unmodifiable(Object2ByteFunction<K> f) {
/* 432 */     return new UnmodifiableFunction<>(f);
/*     */   }
/*     */   
/*     */   public static class PrimitiveFunction<K>
/*     */     implements Object2ByteFunction<K>
/*     */   {
/*     */     protected final Function<? super K, ? extends Byte> function;
/*     */     
/*     */     protected PrimitiveFunction(Function<? super K, ? extends Byte> function) {
/* 441 */       this.function = function;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(Object key) {
/* 446 */       return (this.function.apply((K)key) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public byte getByte(Object key) {
/* 451 */       Byte v = this.function.apply((K)key);
/* 452 */       if (v == null)
/* 453 */         return defaultReturnValue(); 
/* 454 */       return v.byteValue();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte get(Object key) {
/* 460 */       return this.function.apply((K)key);
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public Byte put(K key, Byte value) {
/* 465 */       throw new UnsupportedOperationException();
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
/*     */   public static <K> Object2ByteFunction<K> primitive(Function<? super K, ? extends Byte> f) {
/* 491 */     Objects.requireNonNull(f);
/* 492 */     if (f instanceof Object2ByteFunction)
/* 493 */       return (Object2ByteFunction)f; 
/* 494 */     if (f instanceof ToIntFunction) {
/* 495 */       return key -> SafeMath.safeIntToByte(((ToIntFunction<Object>)f).applyAsInt(key));
/*     */     }
/* 497 */     return new PrimitiveFunction<>(f);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2ByteFunctions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */