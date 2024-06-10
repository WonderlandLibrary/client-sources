/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Object2ReferenceFunctions
/*     */ {
/*     */   public static class EmptyFunction<K, V>
/*     */     extends AbstractObject2ReferenceFunction<K, V>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public V get(Object k) {
/*  44 */       return null;
/*     */     }
/*     */     
/*     */     public boolean containsKey(Object k) {
/*  48 */       return false;
/*     */     }
/*     */     
/*     */     public V defaultReturnValue() {
/*  52 */       return null;
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(V defRetValue) {
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
/*  67 */       return Object2ReferenceFunctions.EMPTY_FUNCTION;
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
/*  84 */       return Object2ReferenceFunctions.EMPTY_FUNCTION;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<K, V>
/*     */     extends AbstractObject2ReferenceFunction<K, V>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */     
/*     */     protected final K key;
/*     */ 
/*     */     
/*     */     protected final V value;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(K key, V value) {
/* 113 */       this.key = key;
/* 114 */       this.value = value;
/*     */     }
/*     */     
/*     */     public boolean containsKey(Object k) {
/* 118 */       return Objects.equals(this.key, k);
/*     */     }
/*     */     
/*     */     public V get(Object k) {
/* 122 */       return Objects.equals(this.key, k) ? this.value : this.defRetValue;
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
/*     */   public static <K, V> Object2ReferenceFunction<K, V> singleton(K key, V value) {
/* 149 */     return new Singleton<>(key, value);
/*     */   }
/*     */   
/*     */   public static class SynchronizedFunction<K, V> implements Object2ReferenceFunction<K, V>, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2ReferenceFunction<K, V> function;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedFunction(Object2ReferenceFunction<K, V> f, Object sync) {
/* 157 */       if (f == null)
/* 158 */         throw new NullPointerException(); 
/* 159 */       this.function = f;
/* 160 */       this.sync = sync;
/*     */     }
/*     */     protected SynchronizedFunction(Object2ReferenceFunction<K, V> f) {
/* 163 */       if (f == null)
/* 164 */         throw new NullPointerException(); 
/* 165 */       this.function = f;
/* 166 */       this.sync = this;
/*     */     }
/*     */     
/*     */     public V apply(K key) {
/* 170 */       synchronized (this.sync) {
/* 171 */         return (V)this.function.apply(key);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int size() {
/* 176 */       synchronized (this.sync) {
/* 177 */         return this.function.size();
/*     */       } 
/*     */     }
/*     */     
/*     */     public V defaultReturnValue() {
/* 182 */       synchronized (this.sync) {
/* 183 */         return this.function.defaultReturnValue();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(V defRetValue) {
/* 188 */       synchronized (this.sync) {
/* 189 */         this.function.defaultReturnValue(defRetValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean containsKey(Object k) {
/* 194 */       synchronized (this.sync) {
/* 195 */         return this.function.containsKey(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V put(K k, V v) {
/* 200 */       synchronized (this.sync) {
/* 201 */         return this.function.put(k, v);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V get(Object k) {
/* 206 */       synchronized (this.sync) {
/* 207 */         return this.function.get(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V remove(Object k) {
/* 212 */       synchronized (this.sync) {
/* 213 */         return this.function.remove(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void clear() {
/* 218 */       synchronized (this.sync) {
/* 219 */         this.function.clear();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 224 */       synchronized (this.sync) {
/* 225 */         return this.function.hashCode();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 230 */       if (o == this)
/* 231 */         return true; 
/* 232 */       synchronized (this.sync) {
/* 233 */         return this.function.equals(o);
/*     */       } 
/*     */     }
/*     */     
/*     */     public String toString() {
/* 238 */       synchronized (this.sync) {
/* 239 */         return this.function.toString();
/*     */       } 
/*     */     }
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 243 */       synchronized (this.sync) {
/* 244 */         s.defaultWriteObject();
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
/*     */   public static <K, V> Object2ReferenceFunction<K, V> synchronize(Object2ReferenceFunction<K, V> f) {
/* 258 */     return new SynchronizedFunction<>(f);
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
/*     */   public static <K, V> Object2ReferenceFunction<K, V> synchronize(Object2ReferenceFunction<K, V> f, Object sync) {
/* 274 */     return new SynchronizedFunction<>(f, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableFunction<K, V>
/*     */     extends AbstractObject2ReferenceFunction<K, V> implements Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2ReferenceFunction<K, V> function;
/*     */     
/*     */     protected UnmodifiableFunction(Object2ReferenceFunction<K, V> f) {
/* 283 */       if (f == null)
/* 284 */         throw new NullPointerException(); 
/* 285 */       this.function = f;
/*     */     }
/*     */     
/*     */     public int size() {
/* 289 */       return this.function.size();
/*     */     }
/*     */     
/*     */     public V defaultReturnValue() {
/* 293 */       return this.function.defaultReturnValue();
/*     */     }
/*     */     
/*     */     public void defaultReturnValue(V defRetValue) {
/* 297 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean containsKey(Object k) {
/* 301 */       return this.function.containsKey(k);
/*     */     }
/*     */     
/*     */     public V put(K k, V v) {
/* 305 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V get(Object k) {
/* 309 */       return this.function.get(k);
/*     */     }
/*     */     
/*     */     public V remove(Object k) {
/* 313 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 317 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 321 */       return this.function.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 325 */       return (o == this || this.function.equals(o));
/*     */     }
/*     */     
/*     */     public String toString() {
/* 329 */       return this.function.toString();
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
/*     */   public static <K, V> Object2ReferenceFunction<K, V> unmodifiable(Object2ReferenceFunction<K, V> f) {
/* 342 */     return new UnmodifiableFunction<>(f);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2ReferenceFunctions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */