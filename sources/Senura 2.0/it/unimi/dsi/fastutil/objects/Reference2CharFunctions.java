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
/*     */ public final class Reference2CharFunctions
/*     */ {
/*     */   public static class EmptyFunction<K>
/*     */     extends AbstractReference2CharFunction<K>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public char getChar(Object k) {
/*  44 */       return Character.MIN_VALUE;
/*     */     }
/*     */     
/*     */     public boolean containsKey(Object k) {
/*  48 */       return false;
/*     */     }
/*     */     
/*     */     public char defaultReturnValue() {
/*  52 */       return Character.MIN_VALUE;
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(char defRetValue) {
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
/*  67 */       return Reference2CharFunctions.EMPTY_FUNCTION;
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
/*  84 */       return Reference2CharFunctions.EMPTY_FUNCTION;
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
/*     */     extends AbstractReference2CharFunction<K>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */     
/*     */     protected final K key;
/*     */ 
/*     */     
/*     */     protected final char value;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(K key, char value) {
/* 113 */       this.key = key;
/* 114 */       this.value = value;
/*     */     }
/*     */     
/*     */     public boolean containsKey(Object k) {
/* 118 */       return (this.key == k);
/*     */     }
/*     */     
/*     */     public char getChar(Object k) {
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
/*     */   public static <K> Reference2CharFunction<K> singleton(K key, char value) {
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
/*     */   public static <K> Reference2CharFunction<K> singleton(K key, Character value) {
/* 167 */     return new Singleton<>(key, value.charValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedFunction<K> implements Reference2CharFunction<K>, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2CharFunction<K> function;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedFunction(Reference2CharFunction<K> f, Object sync) {
/* 175 */       if (f == null)
/* 176 */         throw new NullPointerException(); 
/* 177 */       this.function = f;
/* 178 */       this.sync = sync;
/*     */     }
/*     */     protected SynchronizedFunction(Reference2CharFunction<K> f) {
/* 181 */       if (f == null)
/* 182 */         throw new NullPointerException(); 
/* 183 */       this.function = f;
/* 184 */       this.sync = this;
/*     */     }
/*     */     
/*     */     public int applyAsInt(K operand) {
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
/*     */     public Character apply(K key) {
/* 200 */       synchronized (this.sync) {
/* 201 */         return (Character)this.function.apply(key);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int size() {
/* 206 */       synchronized (this.sync) {
/* 207 */         return this.function.size();
/*     */       } 
/*     */     }
/*     */     
/*     */     public char defaultReturnValue() {
/* 212 */       synchronized (this.sync) {
/* 213 */         return this.function.defaultReturnValue();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(char defRetValue) {
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
/*     */     public char put(K k, char v) {
/* 230 */       synchronized (this.sync) {
/* 231 */         return this.function.put(k, v);
/*     */       } 
/*     */     }
/*     */     
/*     */     public char getChar(Object k) {
/* 236 */       synchronized (this.sync) {
/* 237 */         return this.function.getChar(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public char removeChar(Object k) {
/* 242 */       synchronized (this.sync) {
/* 243 */         return this.function.removeChar(k);
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
/*     */     public Character put(K k, Character v) {
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
/*     */     public Character get(Object k) {
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
/*     */     public Character remove(Object k) {
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
/*     */   public static <K> Reference2CharFunction<K> synchronize(Reference2CharFunction<K> f) {
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
/*     */   public static <K> Reference2CharFunction<K> synchronize(Reference2CharFunction<K> f, Object sync) {
/* 339 */     return new SynchronizedFunction<>(f, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableFunction<K>
/*     */     extends AbstractReference2CharFunction<K> implements Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2CharFunction<K> function;
/*     */     
/*     */     protected UnmodifiableFunction(Reference2CharFunction<K> f) {
/* 348 */       if (f == null)
/* 349 */         throw new NullPointerException(); 
/* 350 */       this.function = f;
/*     */     }
/*     */     
/*     */     public int size() {
/* 354 */       return this.function.size();
/*     */     }
/*     */     
/*     */     public char defaultReturnValue() {
/* 358 */       return this.function.defaultReturnValue();
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(char defRetValue) {
/* 362 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean containsKey(Object k) {
/* 366 */       return this.function.containsKey(k);
/*     */     }
/*     */     
/*     */     public char put(K k, char v) {
/* 370 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public char getChar(Object k) {
/* 374 */       return this.function.getChar(k);
/*     */     }
/*     */     
/*     */     public char removeChar(Object k) {
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
/*     */     public Character put(K k, Character v) {
/* 392 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character get(Object k) {
/* 402 */       return this.function.get(k);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character remove(Object k) {
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
/*     */   public static <K> Reference2CharFunction<K> unmodifiable(Reference2CharFunction<K> f) {
/* 437 */     return new UnmodifiableFunction<>(f);
/*     */   }
/*     */   
/*     */   public static class PrimitiveFunction<K>
/*     */     implements Reference2CharFunction<K>
/*     */   {
/*     */     protected final Function<? super K, ? extends Character> function;
/*     */     
/*     */     protected PrimitiveFunction(Function<? super K, ? extends Character> function) {
/* 446 */       this.function = function;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(Object key) {
/* 451 */       return (this.function.apply((K)key) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public char getChar(Object key) {
/* 456 */       Character v = this.function.apply((K)key);
/* 457 */       if (v == null)
/* 458 */         return defaultReturnValue(); 
/* 459 */       return v.charValue();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character get(Object key) {
/* 465 */       return this.function.apply((K)key);
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public Character put(K key, Character value) {
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
/*     */   public static <K> Reference2CharFunction<K> primitive(Function<? super K, ? extends Character> f) {
/* 497 */     Objects.requireNonNull(f);
/* 498 */     if (f instanceof Reference2CharFunction)
/* 499 */       return (Reference2CharFunction)f; 
/* 500 */     if (f instanceof ToIntFunction) {
/* 501 */       return key -> SafeMath.safeIntToChar(((ToIntFunction<Object>)f).applyAsInt(key));
/*     */     }
/* 503 */     return new PrimitiveFunction<>(f);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2CharFunctions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */