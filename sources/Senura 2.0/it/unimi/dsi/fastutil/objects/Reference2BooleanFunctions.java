/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Reference2BooleanFunctions
/*     */ {
/*     */   public static class EmptyFunction<K>
/*     */     extends AbstractReference2BooleanFunction<K>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public boolean getBoolean(Object k) {
/*  44 */       return false;
/*     */     }
/*     */     
/*     */     public boolean containsKey(Object k) {
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
/*  67 */       return Reference2BooleanFunctions.EMPTY_FUNCTION;
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
/*  84 */       return Reference2BooleanFunctions.EMPTY_FUNCTION;
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
/*     */     extends AbstractReference2BooleanFunction<K>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */     
/*     */     protected final K key;
/*     */ 
/*     */     
/*     */     protected final boolean value;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(K key, boolean value) {
/* 113 */       this.key = key;
/* 114 */       this.value = value;
/*     */     }
/*     */     
/*     */     public boolean containsKey(Object k) {
/* 118 */       return (this.key == k);
/*     */     }
/*     */     
/*     */     public boolean getBoolean(Object k) {
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
/*     */   public static <K> Reference2BooleanFunction<K> singleton(K key, boolean value) {
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
/*     */   public static <K> Reference2BooleanFunction<K> singleton(K key, Boolean value) {
/* 167 */     return new Singleton<>(key, value.booleanValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedFunction<K> implements Reference2BooleanFunction<K>, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2BooleanFunction<K> function;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedFunction(Reference2BooleanFunction<K> f, Object sync) {
/* 175 */       if (f == null)
/* 176 */         throw new NullPointerException(); 
/* 177 */       this.function = f;
/* 178 */       this.sync = sync;
/*     */     }
/*     */     protected SynchronizedFunction(Reference2BooleanFunction<K> f) {
/* 181 */       if (f == null)
/* 182 */         throw new NullPointerException(); 
/* 183 */       this.function = f;
/* 184 */       this.sync = this;
/*     */     }
/*     */     
/*     */     public boolean test(K operand) {
/* 188 */       synchronized (this.sync) {
/* 189 */         return this.function.test(operand);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Boolean apply(K key) {
/* 200 */       synchronized (this.sync) {
/* 201 */         return (Boolean)this.function.apply(key);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int size() {
/* 206 */       synchronized (this.sync) {
/* 207 */         return this.function.size();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean defaultReturnValue() {
/* 212 */       synchronized (this.sync) {
/* 213 */         return this.function.defaultReturnValue();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(boolean defRetValue) {
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
/*     */     public boolean put(K k, boolean v) {
/* 230 */       synchronized (this.sync) {
/* 231 */         return this.function.put(k, v);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean getBoolean(Object k) {
/* 236 */       synchronized (this.sync) {
/* 237 */         return this.function.getBoolean(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean removeBoolean(Object k) {
/* 242 */       synchronized (this.sync) {
/* 243 */         return this.function.removeBoolean(k);
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
/*     */     public Boolean put(K k, Boolean v) {
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
/*     */     public Boolean get(Object k) {
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
/*     */     public Boolean remove(Object k) {
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
/*     */   public static <K> Reference2BooleanFunction<K> synchronize(Reference2BooleanFunction<K> f) {
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
/*     */   
/*     */   public static <K> Reference2BooleanFunction<K> synchronize(Reference2BooleanFunction<K> f, Object sync) {
/* 340 */     return new SynchronizedFunction<>(f, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableFunction<K>
/*     */     extends AbstractReference2BooleanFunction<K> implements Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2BooleanFunction<K> function;
/*     */     
/*     */     protected UnmodifiableFunction(Reference2BooleanFunction<K> f) {
/* 349 */       if (f == null)
/* 350 */         throw new NullPointerException(); 
/* 351 */       this.function = f;
/*     */     }
/*     */     
/*     */     public int size() {
/* 355 */       return this.function.size();
/*     */     }
/*     */     
/*     */     public boolean defaultReturnValue() {
/* 359 */       return this.function.defaultReturnValue();
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(boolean defRetValue) {
/* 363 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean containsKey(Object k) {
/* 367 */       return this.function.containsKey(k);
/*     */     }
/*     */     
/*     */     public boolean put(K k, boolean v) {
/* 371 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean getBoolean(Object k) {
/* 375 */       return this.function.getBoolean(k);
/*     */     }
/*     */     
/*     */     public boolean removeBoolean(Object k) {
/* 379 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 383 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Boolean put(K k, Boolean v) {
/* 393 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Boolean get(Object k) {
/* 403 */       return this.function.get(k);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Boolean remove(Object k) {
/* 413 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 417 */       return this.function.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 421 */       return (o == this || this.function.equals(o));
/*     */     }
/*     */     
/*     */     public String toString() {
/* 425 */       return this.function.toString();
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
/*     */   public static <K> Reference2BooleanFunction<K> unmodifiable(Reference2BooleanFunction<K> f) {
/* 438 */     return new UnmodifiableFunction<>(f);
/*     */   }
/*     */   
/*     */   public static class PrimitiveFunction<K>
/*     */     implements Reference2BooleanFunction<K>
/*     */   {
/*     */     protected final Function<? super K, ? extends Boolean> function;
/*     */     
/*     */     protected PrimitiveFunction(Function<? super K, ? extends Boolean> function) {
/* 447 */       this.function = function;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(Object key) {
/* 452 */       return (this.function.apply((K)key) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean getBoolean(Object key) {
/* 457 */       Boolean v = this.function.apply((K)key);
/* 458 */       if (v == null)
/* 459 */         return defaultReturnValue(); 
/* 460 */       return v.booleanValue();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Boolean get(Object key) {
/* 466 */       return this.function.apply((K)key);
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public Boolean put(K key, Boolean value) {
/* 471 */       throw new UnsupportedOperationException();
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
/*     */   public static <K> Reference2BooleanFunction<K> primitive(Function<? super K, ? extends Boolean> f) {
/* 498 */     Objects.requireNonNull(f);
/* 499 */     if (f instanceof Reference2BooleanFunction)
/* 500 */       return (Reference2BooleanFunction)f; 
/* 501 */     if (f instanceof Predicate)
/* 502 */       return key -> ((Predicate<Object>)f).test(key); 
/* 503 */     return new PrimitiveFunction<>(f);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2BooleanFunctions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */