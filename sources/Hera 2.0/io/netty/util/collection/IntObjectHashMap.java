/*     */ package io.netty.util.collection;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IntObjectHashMap<V>
/*     */   implements IntObjectMap<V>, Iterable<IntObjectMap.Entry<V>>
/*     */ {
/*     */   private static final int DEFAULT_CAPACITY = 11;
/*     */   private static final float DEFAULT_LOAD_FACTOR = 0.5F;
/*  43 */   private static final Object NULL_VALUE = new Object();
/*     */   
/*     */   private int maxSize;
/*     */   
/*     */   private final float loadFactor;
/*     */   
/*     */   private int[] keys;
/*     */   
/*     */   private V[] values;
/*     */   
/*     */   private int size;
/*     */   
/*     */   public IntObjectHashMap() {
/*  56 */     this(11, 0.5F);
/*     */   }
/*     */   
/*     */   public IntObjectHashMap(int initialCapacity) {
/*  60 */     this(initialCapacity, 0.5F);
/*     */   }
/*     */   
/*     */   public IntObjectHashMap(int initialCapacity, float loadFactor) {
/*  64 */     if (initialCapacity < 1) {
/*  65 */       throw new IllegalArgumentException("initialCapacity must be >= 1");
/*     */     }
/*  67 */     if (loadFactor <= 0.0F || loadFactor > 1.0F)
/*     */     {
/*     */       
/*  70 */       throw new IllegalArgumentException("loadFactor must be > 0 and <= 1");
/*     */     }
/*     */     
/*  73 */     this.loadFactor = loadFactor;
/*     */ 
/*     */     
/*  76 */     int capacity = adjustCapacity(initialCapacity);
/*     */ 
/*     */     
/*  79 */     this.keys = new int[capacity];
/*     */     
/*  81 */     V[] temp = (V[])new Object[capacity];
/*  82 */     this.values = temp;
/*     */ 
/*     */     
/*  85 */     this.maxSize = calcMaxSize(capacity);
/*     */   }
/*     */   
/*     */   private static <T> T toExternal(T value) {
/*  89 */     return (value == NULL_VALUE) ? null : value;
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> T toInternal(T value) {
/*  94 */     return (value == null) ? (T)NULL_VALUE : value;
/*     */   }
/*     */ 
/*     */   
/*     */   public V get(int key) {
/*  99 */     int index = indexOf(key);
/* 100 */     return (index == -1) ? null : toExternal(this.values[index]);
/*     */   }
/*     */ 
/*     */   
/*     */   public V put(int key, V value) {
/* 105 */     int startIndex = hashIndex(key);
/* 106 */     int index = startIndex;
/*     */     
/*     */     do {
/* 109 */       if (this.values[index] == null) {
/*     */         
/* 111 */         this.keys[index] = key;
/* 112 */         this.values[index] = toInternal(value);
/* 113 */         growSize();
/* 114 */         return null;
/* 115 */       }  if (this.keys[index] == key)
/*     */       {
/* 117 */         V previousValue = this.values[index];
/* 118 */         this.values[index] = toInternal(value);
/* 119 */         return toExternal(previousValue);
/*     */       }
/*     */     
/*     */     }
/* 123 */     while ((index = probeNext(index)) != startIndex);
/*     */     
/* 125 */     throw new IllegalStateException("Unable to insert");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int probeNext(int index) {
/* 131 */     return (index == this.values.length - 1) ? 0 : (index + 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void putAll(IntObjectMap<V> sourceMap) {
/* 136 */     if (sourceMap instanceof IntObjectHashMap) {
/*     */       
/* 138 */       IntObjectHashMap<V> source = (IntObjectHashMap<V>)sourceMap;
/* 139 */       for (int i = 0; i < source.values.length; i++) {
/* 140 */         V sourceValue = source.values[i];
/* 141 */         if (sourceValue != null) {
/* 142 */           put(source.keys[i], sourceValue);
/*     */         }
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 149 */     for (IntObjectMap.Entry<V> entry : sourceMap.entries()) {
/* 150 */       put(entry.key(), entry.value());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public V remove(int key) {
/* 156 */     int index = indexOf(key);
/* 157 */     if (index == -1) {
/* 158 */       return null;
/*     */     }
/*     */     
/* 161 */     V prev = this.values[index];
/* 162 */     removeAt(index);
/* 163 */     return toExternal(prev);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 168 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 173 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 178 */     Arrays.fill(this.keys, 0);
/* 179 */     Arrays.fill((Object[])this.values, (Object)null);
/* 180 */     this.size = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(int key) {
/* 185 */     return (indexOf(key) >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(V value) {
/* 190 */     V v = toInternal(value);
/* 191 */     for (int i = 0; i < this.values.length; i++) {
/*     */       
/* 193 */       if (this.values[i] != null && this.values[i].equals(v)) {
/* 194 */         return true;
/*     */       }
/*     */     } 
/* 197 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterable<IntObjectMap.Entry<V>> entries() {
/* 202 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<IntObjectMap.Entry<V>> iterator() {
/* 207 */     return new IteratorImpl();
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] keys() {
/* 212 */     int[] outKeys = new int[size()];
/* 213 */     int targetIx = 0;
/* 214 */     for (int i = 0; i < this.values.length; i++) {
/* 215 */       if (this.values[i] != null) {
/* 216 */         outKeys[targetIx++] = this.keys[i];
/*     */       }
/*     */     } 
/* 219 */     return outKeys;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public V[] values(Class<V> clazz) {
/* 225 */     V[] outValues = (V[])Array.newInstance(clazz, size());
/* 226 */     int targetIx = 0;
/* 227 */     for (int i = 0; i < this.values.length; i++) {
/* 228 */       if (this.values[i] != null) {
/* 229 */         outValues[targetIx++] = this.values[i];
/*     */       }
/*     */     } 
/* 232 */     return outValues;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 240 */     int hash = this.size;
/* 241 */     for (int i = 0; i < this.keys.length; i++)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 249 */       hash ^= this.keys[i];
/*     */     }
/* 251 */     return hash;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 256 */     if (this == obj)
/* 257 */       return true; 
/* 258 */     if (!(obj instanceof IntObjectMap)) {
/* 259 */       return false;
/*     */     }
/*     */     
/* 262 */     IntObjectMap other = (IntObjectMap)obj;
/* 263 */     if (this.size != other.size()) {
/* 264 */       return false;
/*     */     }
/* 266 */     for (int i = 0; i < this.values.length; i++) {
/* 267 */       V value = this.values[i];
/* 268 */       if (value != null) {
/* 269 */         int key = this.keys[i];
/* 270 */         Object otherValue = other.get(key);
/* 271 */         if (value == NULL_VALUE) {
/* 272 */           if (otherValue != null) {
/* 273 */             return false;
/*     */           }
/* 275 */         } else if (!value.equals(otherValue)) {
/* 276 */           return false;
/*     */         } 
/*     */       } 
/*     */     } 
/* 280 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int indexOf(int key) {
/* 290 */     int startIndex = hashIndex(key);
/* 291 */     int index = startIndex;
/*     */     
/*     */     while (true) {
/* 294 */       if (this.values[index] == null)
/*     */       {
/* 296 */         return -1; } 
/* 297 */       if (key == this.keys[index]) {
/* 298 */         return index;
/*     */       }
/*     */ 
/*     */       
/* 302 */       if ((index = probeNext(index)) == startIndex) {
/* 303 */         return -1;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int hashIndex(int key) {
/* 312 */     return key % this.keys.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void growSize() {
/* 319 */     this.size++;
/*     */     
/* 321 */     if (this.size > this.maxSize) {
/*     */ 
/*     */       
/* 324 */       rehash(adjustCapacity((int)Math.min(this.keys.length * 2.0D, 2.147483639E9D)));
/* 325 */     } else if (this.size == this.keys.length) {
/*     */ 
/*     */       
/* 328 */       rehash(this.keys.length);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int adjustCapacity(int capacity) {
/* 336 */     return capacity | 0x1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void removeAt(int index) {
/* 346 */     this.size--;
/*     */ 
/*     */     
/* 349 */     this.keys[index] = 0;
/* 350 */     this.values[index] = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 357 */     int nextFree = index;
/* 358 */     for (int i = probeNext(index); this.values[i] != null; i = probeNext(i)) {
/* 359 */       int bucket = hashIndex(this.keys[i]);
/* 360 */       if ((i < bucket && (bucket <= nextFree || nextFree <= i)) || (bucket <= nextFree && nextFree <= i)) {
/*     */ 
/*     */         
/* 363 */         this.keys[nextFree] = this.keys[i];
/* 364 */         this.values[nextFree] = this.values[i];
/*     */         
/* 366 */         this.keys[i] = 0;
/* 367 */         this.values[i] = null;
/* 368 */         nextFree = i;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int calcMaxSize(int capacity) {
/* 378 */     int upperBound = capacity - 1;
/* 379 */     return Math.min(upperBound, (int)(capacity * this.loadFactor));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void rehash(int newCapacity) {
/* 388 */     int[] oldKeys = this.keys;
/* 389 */     V[] oldVals = this.values;
/*     */     
/* 391 */     this.keys = new int[newCapacity];
/*     */     
/* 393 */     V[] temp = (V[])new Object[newCapacity];
/* 394 */     this.values = temp;
/*     */     
/* 396 */     this.maxSize = calcMaxSize(newCapacity);
/*     */ 
/*     */     
/* 399 */     for (int i = 0; i < oldVals.length; i++) {
/* 400 */       V oldVal = oldVals[i];
/* 401 */       if (oldVal != null) {
/*     */ 
/*     */         
/* 404 */         int oldKey = oldKeys[i];
/* 405 */         int startIndex = hashIndex(oldKey);
/* 406 */         int index = startIndex;
/*     */         
/*     */         while (true) {
/* 409 */           if (this.values[index] == null) {
/* 410 */             this.keys[index] = oldKey;
/* 411 */             this.values[index] = toInternal(oldVal);
/*     */             
/*     */             break;
/*     */           } 
/*     */           
/* 416 */           index = probeNext(index);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private final class IteratorImpl
/*     */     implements Iterator<IntObjectMap.Entry<V>>, IntObjectMap.Entry<V>
/*     */   {
/* 426 */     private int prevIndex = -1;
/* 427 */     private int nextIndex = -1;
/* 428 */     private int entryIndex = -1;
/*     */     private void scanNext() {
/*     */       do {
/*     */       
/* 432 */       } while (++this.nextIndex != IntObjectHashMap.this.values.length && IntObjectHashMap.this.values[this.nextIndex] == null);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 440 */       if (this.nextIndex == -1) {
/* 441 */         scanNext();
/*     */       }
/* 443 */       return (this.nextIndex < IntObjectHashMap.this.keys.length);
/*     */     }
/*     */ 
/*     */     
/*     */     public IntObjectMap.Entry<V> next() {
/* 448 */       if (!hasNext()) {
/* 449 */         throw new NoSuchElementException();
/*     */       }
/*     */       
/* 452 */       this.prevIndex = this.nextIndex;
/* 453 */       scanNext();
/*     */ 
/*     */       
/* 456 */       this.entryIndex = this.prevIndex;
/* 457 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 462 */       if (this.prevIndex < 0) {
/* 463 */         throw new IllegalStateException("next must be called before each remove.");
/*     */       }
/* 465 */       IntObjectHashMap.this.removeAt(this.prevIndex);
/* 466 */       this.prevIndex = -1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int key() {
/* 474 */       return IntObjectHashMap.this.keys[this.entryIndex];
/*     */     }
/*     */ 
/*     */     
/*     */     public V value() {
/* 479 */       return (V)IntObjectHashMap.toExternal((T)IntObjectHashMap.this.values[this.entryIndex]);
/*     */     }
/*     */ 
/*     */     
/*     */     public void setValue(V value) {
/* 484 */       IntObjectHashMap.this.values[this.entryIndex] = IntObjectHashMap.toInternal((T)value);
/*     */     }
/*     */     
/*     */     private IteratorImpl() {} }
/*     */   
/*     */   public String toString() {
/* 490 */     if (this.size == 0) {
/* 491 */       return "{}";
/*     */     }
/* 493 */     StringBuilder sb = new StringBuilder(4 * this.size);
/* 494 */     for (int i = 0; i < this.values.length; i++) {
/* 495 */       V value = this.values[i];
/* 496 */       if (value != null) {
/* 497 */         sb.append((sb.length() == 0) ? "{" : ", ");
/* 498 */         sb.append(this.keys[i]).append('=').append((value == this) ? "(this Map)" : value);
/*     */       } 
/*     */     } 
/* 501 */     return sb.append('}').toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\collection\IntObjectHashMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */