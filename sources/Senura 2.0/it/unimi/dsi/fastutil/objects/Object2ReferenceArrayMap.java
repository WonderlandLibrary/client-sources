/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Object2ReferenceArrayMap<K, V>
/*     */   extends AbstractObject2ReferenceMap<K, V>
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient Object[] key;
/*     */   private transient Object[] value;
/*     */   private int size;
/*     */   
/*     */   public Object2ReferenceArrayMap(Object[] key, Object[] value) {
/*  59 */     this.key = key;
/*  60 */     this.value = value;
/*  61 */     this.size = key.length;
/*  62 */     if (key.length != value.length) {
/*  63 */       throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2ReferenceArrayMap() {
/*  70 */     this.key = ObjectArrays.EMPTY_ARRAY;
/*  71 */     this.value = ObjectArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2ReferenceArrayMap(int capacity) {
/*  80 */     this.key = new Object[capacity];
/*  81 */     this.value = new Object[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2ReferenceArrayMap(Object2ReferenceMap<K, V> m) {
/*  90 */     this(m.size());
/*  91 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2ReferenceArrayMap(Map<? extends K, ? extends V> m) {
/* 100 */     this(m.size());
/* 101 */     putAll(m);
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
/*     */   public Object2ReferenceArrayMap(Object[] key, Object[] value, int size) {
/* 120 */     this.key = key;
/* 121 */     this.value = value;
/* 122 */     this.size = size;
/* 123 */     if (key.length != value.length) {
/* 124 */       throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/*     */     }
/* 126 */     if (size > key.length)
/* 127 */       throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")"); 
/*     */   }
/*     */   
/*     */   private final class EntrySet
/*     */     extends AbstractObjectSet<Object2ReferenceMap.Entry<K, V>> implements Object2ReferenceMap.FastEntrySet<K, V> {
/*     */     private EntrySet() {}
/*     */     
/*     */     public ObjectIterator<Object2ReferenceMap.Entry<K, V>> iterator() {
/* 135 */       return (ObjectIterator)new ObjectIterator<Object2ReferenceMap.Entry<Object2ReferenceMap.Entry<K, V>, V>>() {
/* 136 */           int curr = -1; int next = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 139 */             return (this.next < Object2ReferenceArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object2ReferenceMap.Entry<K, V> next() {
/* 144 */             if (!hasNext())
/* 145 */               throw new NoSuchElementException(); 
/* 146 */             return new AbstractObject2ReferenceMap.BasicEntry<>((K)Object2ReferenceArrayMap.this.key[this.curr = this.next], (V)Object2ReferenceArrayMap.this.value[this.next++]);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 150 */             if (this.curr == -1)
/* 151 */               throw new IllegalStateException(); 
/* 152 */             this.curr = -1;
/* 153 */             int tail = Object2ReferenceArrayMap.this.size-- - this.next--;
/* 154 */             System.arraycopy(Object2ReferenceArrayMap.this.key, this.next + 1, Object2ReferenceArrayMap.this.key, this.next, tail);
/* 155 */             System.arraycopy(Object2ReferenceArrayMap.this.value, this.next + 1, Object2ReferenceArrayMap.this.value, this.next, tail);
/* 156 */             Object2ReferenceArrayMap.this.key[Object2ReferenceArrayMap.this.size] = null;
/* 157 */             Object2ReferenceArrayMap.this.value[Object2ReferenceArrayMap.this.size] = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public ObjectIterator<Object2ReferenceMap.Entry<K, V>> fastIterator() {
/* 163 */       return (ObjectIterator)new ObjectIterator<Object2ReferenceMap.Entry<Object2ReferenceMap.Entry<K, V>, V>>() {
/* 164 */           int next = 0; int curr = -1;
/* 165 */           final AbstractObject2ReferenceMap.BasicEntry<K, V> entry = new AbstractObject2ReferenceMap.BasicEntry<>();
/*     */           
/*     */           public boolean hasNext() {
/* 168 */             return (this.next < Object2ReferenceArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object2ReferenceMap.Entry<K, V> next() {
/* 173 */             if (!hasNext())
/* 174 */               throw new NoSuchElementException(); 
/* 175 */             this.entry.key = (K)Object2ReferenceArrayMap.this.key[this.curr = this.next];
/* 176 */             this.entry.value = (V)Object2ReferenceArrayMap.this.value[this.next++];
/* 177 */             return this.entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 181 */             if (this.curr == -1)
/* 182 */               throw new IllegalStateException(); 
/* 183 */             this.curr = -1;
/* 184 */             int tail = Object2ReferenceArrayMap.this.size-- - this.next--;
/* 185 */             System.arraycopy(Object2ReferenceArrayMap.this.key, this.next + 1, Object2ReferenceArrayMap.this.key, this.next, tail);
/* 186 */             System.arraycopy(Object2ReferenceArrayMap.this.value, this.next + 1, Object2ReferenceArrayMap.this.value, this.next, tail);
/* 187 */             Object2ReferenceArrayMap.this.key[Object2ReferenceArrayMap.this.size] = null;
/* 188 */             Object2ReferenceArrayMap.this.value[Object2ReferenceArrayMap.this.size] = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 194 */       return Object2ReferenceArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 199 */       if (!(o instanceof Map.Entry))
/* 200 */         return false; 
/* 201 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 202 */       K k = (K)e.getKey();
/* 203 */       return (Object2ReferenceArrayMap.this.containsKey(k) && Object2ReferenceArrayMap.this
/* 204 */         .get(k) == e.getValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 209 */       if (!(o instanceof Map.Entry))
/* 210 */         return false; 
/* 211 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 212 */       K k = (K)e.getKey();
/* 213 */       V v = (V)e.getValue();
/* 214 */       int oldPos = Object2ReferenceArrayMap.this.findKey(k);
/* 215 */       if (oldPos == -1 || v != Object2ReferenceArrayMap.this.value[oldPos])
/* 216 */         return false; 
/* 217 */       int tail = Object2ReferenceArrayMap.this.size - oldPos - 1;
/* 218 */       System.arraycopy(Object2ReferenceArrayMap.this.key, oldPos + 1, Object2ReferenceArrayMap.this.key, oldPos, tail);
/*     */       
/* 220 */       System.arraycopy(Object2ReferenceArrayMap.this.value, oldPos + 1, Object2ReferenceArrayMap.this.value, oldPos, tail);
/*     */       
/* 222 */       Object2ReferenceArrayMap.this.size--;
/* 223 */       Object2ReferenceArrayMap.this.key[Object2ReferenceArrayMap.this.size] = null;
/* 224 */       Object2ReferenceArrayMap.this.value[Object2ReferenceArrayMap.this.size] = null;
/* 225 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Object2ReferenceMap.FastEntrySet<K, V> object2ReferenceEntrySet() {
/* 230 */     return new EntrySet();
/*     */   }
/*     */   private int findKey(Object k) {
/* 233 */     Object[] key = this.key;
/* 234 */     for (int i = this.size; i-- != 0;) {
/* 235 */       if (Objects.equals(key[i], k))
/* 236 */         return i; 
/* 237 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public V get(Object k) {
/* 242 */     Object[] key = this.key;
/* 243 */     for (int i = this.size; i-- != 0;) {
/* 244 */       if (Objects.equals(key[i], k))
/* 245 */         return (V)this.value[i]; 
/* 246 */     }  return this.defRetValue;
/*     */   }
/*     */   
/*     */   public int size() {
/* 250 */     return this.size;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 254 */     for (int i = this.size; i-- != 0; ) {
/* 255 */       this.key[i] = null;
/* 256 */       this.value[i] = null;
/*     */     } 
/* 258 */     this.size = 0;
/*     */   }
/*     */   
/*     */   public boolean containsKey(Object k) {
/* 262 */     return (findKey(k) != -1);
/*     */   }
/*     */   
/*     */   public boolean containsValue(Object v) {
/* 266 */     for (int i = this.size; i-- != 0;) {
/* 267 */       if (this.value[i] == v)
/* 268 */         return true; 
/* 269 */     }  return false;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 273 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public V put(K k, V v) {
/* 278 */     int oldKey = findKey(k);
/* 279 */     if (oldKey != -1) {
/* 280 */       V oldValue = (V)this.value[oldKey];
/* 281 */       this.value[oldKey] = v;
/* 282 */       return oldValue;
/*     */     } 
/* 284 */     if (this.size == this.key.length) {
/* 285 */       Object[] newKey = new Object[(this.size == 0) ? 2 : (this.size * 2)];
/* 286 */       Object[] newValue = new Object[(this.size == 0) ? 2 : (this.size * 2)];
/* 287 */       for (int i = this.size; i-- != 0; ) {
/* 288 */         newKey[i] = this.key[i];
/* 289 */         newValue[i] = this.value[i];
/*     */       } 
/* 291 */       this.key = newKey;
/* 292 */       this.value = newValue;
/*     */     } 
/* 294 */     this.key[this.size] = k;
/* 295 */     this.value[this.size] = v;
/* 296 */     this.size++;
/* 297 */     return this.defRetValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public V remove(Object k) {
/* 302 */     int oldPos = findKey(k);
/* 303 */     if (oldPos == -1)
/* 304 */       return this.defRetValue; 
/* 305 */     V oldValue = (V)this.value[oldPos];
/* 306 */     int tail = this.size - oldPos - 1;
/* 307 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 308 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 309 */     this.size--;
/* 310 */     this.key[this.size] = null;
/* 311 */     this.value[this.size] = null;
/* 312 */     return oldValue;
/*     */   }
/*     */   
/*     */   public ObjectSet<K> keySet() {
/* 316 */     return new AbstractObjectSet<K>()
/*     */       {
/*     */         public boolean contains(Object k) {
/* 319 */           return (Object2ReferenceArrayMap.this.findKey(k) != -1);
/*     */         }
/*     */         
/*     */         public boolean remove(Object k) {
/* 323 */           int oldPos = Object2ReferenceArrayMap.this.findKey(k);
/* 324 */           if (oldPos == -1)
/* 325 */             return false; 
/* 326 */           int tail = Object2ReferenceArrayMap.this.size - oldPos - 1;
/* 327 */           System.arraycopy(Object2ReferenceArrayMap.this.key, oldPos + 1, Object2ReferenceArrayMap.this.key, oldPos, tail);
/* 328 */           System.arraycopy(Object2ReferenceArrayMap.this.value, oldPos + 1, Object2ReferenceArrayMap.this.value, oldPos, tail);
/* 329 */           Object2ReferenceArrayMap.this.size--;
/* 330 */           return true;
/*     */         }
/*     */         
/*     */         public ObjectIterator<K> iterator() {
/* 334 */           return new ObjectIterator<K>() {
/* 335 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 338 */                 return (this.pos < Object2ReferenceArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public K next() {
/* 343 */                 if (!hasNext())
/* 344 */                   throw new NoSuchElementException(); 
/* 345 */                 return (K)Object2ReferenceArrayMap.this.key[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 349 */                 if (this.pos == 0)
/* 350 */                   throw new IllegalStateException(); 
/* 351 */                 int tail = Object2ReferenceArrayMap.this.size - this.pos;
/* 352 */                 System.arraycopy(Object2ReferenceArrayMap.this.key, this.pos, Object2ReferenceArrayMap.this.key, this.pos - 1, tail);
/* 353 */                 System.arraycopy(Object2ReferenceArrayMap.this.value, this.pos, Object2ReferenceArrayMap.this.value, this.pos - 1, tail);
/* 354 */                 Object2ReferenceArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 360 */           return Object2ReferenceArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 364 */           Object2ReferenceArrayMap.this.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public ReferenceCollection<V> values() {
/* 370 */     return new AbstractReferenceCollection<V>()
/*     */       {
/*     */         public boolean contains(Object v) {
/* 373 */           return Object2ReferenceArrayMap.this.containsValue(v);
/*     */         }
/*     */         
/*     */         public ObjectIterator<V> iterator() {
/* 377 */           return new ObjectIterator<V>() {
/* 378 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 381 */                 return (this.pos < Object2ReferenceArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public V next() {
/* 386 */                 if (!hasNext())
/* 387 */                   throw new NoSuchElementException(); 
/* 388 */                 return (V)Object2ReferenceArrayMap.this.value[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 392 */                 if (this.pos == 0)
/* 393 */                   throw new IllegalStateException(); 
/* 394 */                 int tail = Object2ReferenceArrayMap.this.size - this.pos;
/* 395 */                 System.arraycopy(Object2ReferenceArrayMap.this.key, this.pos, Object2ReferenceArrayMap.this.key, this.pos - 1, tail);
/* 396 */                 System.arraycopy(Object2ReferenceArrayMap.this.value, this.pos, Object2ReferenceArrayMap.this.value, this.pos - 1, tail);
/* 397 */                 Object2ReferenceArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 403 */           return Object2ReferenceArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 407 */           Object2ReferenceArrayMap.this.clear();
/*     */         }
/*     */       };
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
/*     */   public Object2ReferenceArrayMap<K, V> clone() {
/*     */     Object2ReferenceArrayMap<K, V> c;
/*     */     try {
/* 426 */       c = (Object2ReferenceArrayMap<K, V>)super.clone();
/* 427 */     } catch (CloneNotSupportedException cantHappen) {
/* 428 */       throw new InternalError();
/*     */     } 
/* 430 */     c.key = (Object[])this.key.clone();
/* 431 */     c.value = (Object[])this.value.clone();
/* 432 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 435 */     s.defaultWriteObject();
/* 436 */     for (int i = 0; i < this.size; i++) {
/* 437 */       s.writeObject(this.key[i]);
/* 438 */       s.writeObject(this.value[i]);
/*     */     } 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 442 */     s.defaultReadObject();
/* 443 */     this.key = new Object[this.size];
/* 444 */     this.value = new Object[this.size];
/* 445 */     for (int i = 0; i < this.size; i++) {
/* 446 */       this.key[i] = s.readObject();
/* 447 */       this.value[i] = s.readObject();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2ReferenceArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */