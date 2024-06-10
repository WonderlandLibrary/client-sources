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
/*     */ public class Object2ObjectArrayMap<K, V>
/*     */   extends AbstractObject2ObjectMap<K, V>
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient Object[] key;
/*     */   private transient Object[] value;
/*     */   private int size;
/*     */   
/*     */   public Object2ObjectArrayMap(Object[] key, Object[] value) {
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
/*     */   public Object2ObjectArrayMap() {
/*  70 */     this.key = ObjectArrays.EMPTY_ARRAY;
/*  71 */     this.value = ObjectArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2ObjectArrayMap(int capacity) {
/*  80 */     this.key = new Object[capacity];
/*  81 */     this.value = new Object[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2ObjectArrayMap(Object2ObjectMap<K, V> m) {
/*  90 */     this(m.size());
/*  91 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2ObjectArrayMap(Map<? extends K, ? extends V> m) {
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
/*     */   public Object2ObjectArrayMap(Object[] key, Object[] value, int size) {
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
/*     */     extends AbstractObjectSet<Object2ObjectMap.Entry<K, V>> implements Object2ObjectMap.FastEntrySet<K, V> {
/*     */     public ObjectIterator<Object2ObjectMap.Entry<K, V>> iterator() {
/* 133 */       return (ObjectIterator)new ObjectIterator<Object2ObjectMap.Entry<Object2ObjectMap.Entry<K, V>, V>>() {
/* 134 */           int curr = -1; int next = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 137 */             return (this.next < Object2ObjectArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object2ObjectMap.Entry<K, V> next() {
/* 142 */             if (!hasNext())
/* 143 */               throw new NoSuchElementException(); 
/* 144 */             return new AbstractObject2ObjectMap.BasicEntry<>((K)Object2ObjectArrayMap.this.key[this.curr = this.next], (V)Object2ObjectArrayMap.this.value[this.next++]);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 148 */             if (this.curr == -1)
/* 149 */               throw new IllegalStateException(); 
/* 150 */             this.curr = -1;
/* 151 */             int tail = Object2ObjectArrayMap.this.size-- - this.next--;
/* 152 */             System.arraycopy(Object2ObjectArrayMap.this.key, this.next + 1, Object2ObjectArrayMap.this.key, this.next, tail);
/* 153 */             System.arraycopy(Object2ObjectArrayMap.this.value, this.next + 1, Object2ObjectArrayMap.this.value, this.next, tail);
/* 154 */             Object2ObjectArrayMap.this.key[Object2ObjectArrayMap.this.size] = null;
/* 155 */             Object2ObjectArrayMap.this.value[Object2ObjectArrayMap.this.size] = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     private EntrySet() {}
/*     */     public ObjectIterator<Object2ObjectMap.Entry<K, V>> fastIterator() {
/* 161 */       return (ObjectIterator)new ObjectIterator<Object2ObjectMap.Entry<Object2ObjectMap.Entry<K, V>, V>>() {
/* 162 */           int next = 0; int curr = -1;
/* 163 */           final AbstractObject2ObjectMap.BasicEntry<K, V> entry = new AbstractObject2ObjectMap.BasicEntry<>();
/*     */           
/*     */           public boolean hasNext() {
/* 166 */             return (this.next < Object2ObjectArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object2ObjectMap.Entry<K, V> next() {
/* 171 */             if (!hasNext())
/* 172 */               throw new NoSuchElementException(); 
/* 173 */             this.entry.key = (K)Object2ObjectArrayMap.this.key[this.curr = this.next];
/* 174 */             this.entry.value = (V)Object2ObjectArrayMap.this.value[this.next++];
/* 175 */             return this.entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 179 */             if (this.curr == -1)
/* 180 */               throw new IllegalStateException(); 
/* 181 */             this.curr = -1;
/* 182 */             int tail = Object2ObjectArrayMap.this.size-- - this.next--;
/* 183 */             System.arraycopy(Object2ObjectArrayMap.this.key, this.next + 1, Object2ObjectArrayMap.this.key, this.next, tail);
/* 184 */             System.arraycopy(Object2ObjectArrayMap.this.value, this.next + 1, Object2ObjectArrayMap.this.value, this.next, tail);
/* 185 */             Object2ObjectArrayMap.this.key[Object2ObjectArrayMap.this.size] = null;
/* 186 */             Object2ObjectArrayMap.this.value[Object2ObjectArrayMap.this.size] = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 192 */       return Object2ObjectArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 197 */       if (!(o instanceof Map.Entry))
/* 198 */         return false; 
/* 199 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 200 */       K k = (K)e.getKey();
/* 201 */       return (Object2ObjectArrayMap.this.containsKey(k) && 
/* 202 */         Objects.equals(Object2ObjectArrayMap.this.get(k), e.getValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 207 */       if (!(o instanceof Map.Entry))
/* 208 */         return false; 
/* 209 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 210 */       K k = (K)e.getKey();
/* 211 */       V v = (V)e.getValue();
/* 212 */       int oldPos = Object2ObjectArrayMap.this.findKey(k);
/* 213 */       if (oldPos == -1 || !Objects.equals(v, Object2ObjectArrayMap.this.value[oldPos]))
/* 214 */         return false; 
/* 215 */       int tail = Object2ObjectArrayMap.this.size - oldPos - 1;
/* 216 */       System.arraycopy(Object2ObjectArrayMap.this.key, oldPos + 1, Object2ObjectArrayMap.this.key, oldPos, tail);
/* 217 */       System.arraycopy(Object2ObjectArrayMap.this.value, oldPos + 1, Object2ObjectArrayMap.this.value, oldPos, tail);
/*     */       
/* 219 */       Object2ObjectArrayMap.this.size--;
/* 220 */       Object2ObjectArrayMap.this.key[Object2ObjectArrayMap.this.size] = null;
/* 221 */       Object2ObjectArrayMap.this.value[Object2ObjectArrayMap.this.size] = null;
/* 222 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Object2ObjectMap.FastEntrySet<K, V> object2ObjectEntrySet() {
/* 227 */     return new EntrySet();
/*     */   }
/*     */   private int findKey(Object k) {
/* 230 */     Object[] key = this.key;
/* 231 */     for (int i = this.size; i-- != 0;) {
/* 232 */       if (Objects.equals(key[i], k))
/* 233 */         return i; 
/* 234 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public V get(Object k) {
/* 239 */     Object[] key = this.key;
/* 240 */     for (int i = this.size; i-- != 0;) {
/* 241 */       if (Objects.equals(key[i], k))
/* 242 */         return (V)this.value[i]; 
/* 243 */     }  return this.defRetValue;
/*     */   }
/*     */   
/*     */   public int size() {
/* 247 */     return this.size;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 251 */     for (int i = this.size; i-- != 0; ) {
/* 252 */       this.key[i] = null;
/* 253 */       this.value[i] = null;
/*     */     } 
/* 255 */     this.size = 0;
/*     */   }
/*     */   
/*     */   public boolean containsKey(Object k) {
/* 259 */     return (findKey(k) != -1);
/*     */   }
/*     */   
/*     */   public boolean containsValue(Object v) {
/* 263 */     for (int i = this.size; i-- != 0;) {
/* 264 */       if (Objects.equals(this.value[i], v))
/* 265 */         return true; 
/* 266 */     }  return false;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 270 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public V put(K k, V v) {
/* 275 */     int oldKey = findKey(k);
/* 276 */     if (oldKey != -1) {
/* 277 */       V oldValue = (V)this.value[oldKey];
/* 278 */       this.value[oldKey] = v;
/* 279 */       return oldValue;
/*     */     } 
/* 281 */     if (this.size == this.key.length) {
/* 282 */       Object[] newKey = new Object[(this.size == 0) ? 2 : (this.size * 2)];
/* 283 */       Object[] newValue = new Object[(this.size == 0) ? 2 : (this.size * 2)];
/* 284 */       for (int i = this.size; i-- != 0; ) {
/* 285 */         newKey[i] = this.key[i];
/* 286 */         newValue[i] = this.value[i];
/*     */       } 
/* 288 */       this.key = newKey;
/* 289 */       this.value = newValue;
/*     */     } 
/* 291 */     this.key[this.size] = k;
/* 292 */     this.value[this.size] = v;
/* 293 */     this.size++;
/* 294 */     return this.defRetValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public V remove(Object k) {
/* 299 */     int oldPos = findKey(k);
/* 300 */     if (oldPos == -1)
/* 301 */       return this.defRetValue; 
/* 302 */     V oldValue = (V)this.value[oldPos];
/* 303 */     int tail = this.size - oldPos - 1;
/* 304 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 305 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 306 */     this.size--;
/* 307 */     this.key[this.size] = null;
/* 308 */     this.value[this.size] = null;
/* 309 */     return oldValue;
/*     */   }
/*     */   
/*     */   public ObjectSet<K> keySet() {
/* 313 */     return new AbstractObjectSet<K>()
/*     */       {
/*     */         public boolean contains(Object k) {
/* 316 */           return (Object2ObjectArrayMap.this.findKey(k) != -1);
/*     */         }
/*     */         
/*     */         public boolean remove(Object k) {
/* 320 */           int oldPos = Object2ObjectArrayMap.this.findKey(k);
/* 321 */           if (oldPos == -1)
/* 322 */             return false; 
/* 323 */           int tail = Object2ObjectArrayMap.this.size - oldPos - 1;
/* 324 */           System.arraycopy(Object2ObjectArrayMap.this.key, oldPos + 1, Object2ObjectArrayMap.this.key, oldPos, tail);
/* 325 */           System.arraycopy(Object2ObjectArrayMap.this.value, oldPos + 1, Object2ObjectArrayMap.this.value, oldPos, tail);
/* 326 */           Object2ObjectArrayMap.this.size--;
/* 327 */           return true;
/*     */         }
/*     */         
/*     */         public ObjectIterator<K> iterator() {
/* 331 */           return new ObjectIterator<K>() {
/* 332 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 335 */                 return (this.pos < Object2ObjectArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public K next() {
/* 340 */                 if (!hasNext())
/* 341 */                   throw new NoSuchElementException(); 
/* 342 */                 return (K)Object2ObjectArrayMap.this.key[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 346 */                 if (this.pos == 0)
/* 347 */                   throw new IllegalStateException(); 
/* 348 */                 int tail = Object2ObjectArrayMap.this.size - this.pos;
/* 349 */                 System.arraycopy(Object2ObjectArrayMap.this.key, this.pos, Object2ObjectArrayMap.this.key, this.pos - 1, tail);
/* 350 */                 System.arraycopy(Object2ObjectArrayMap.this.value, this.pos, Object2ObjectArrayMap.this.value, this.pos - 1, tail);
/* 351 */                 Object2ObjectArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 357 */           return Object2ObjectArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 361 */           Object2ObjectArrayMap.this.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public ObjectCollection<V> values() {
/* 367 */     return new AbstractObjectCollection<V>()
/*     */       {
/*     */         public boolean contains(Object v) {
/* 370 */           return Object2ObjectArrayMap.this.containsValue(v);
/*     */         }
/*     */         
/*     */         public ObjectIterator<V> iterator() {
/* 374 */           return new ObjectIterator<V>() {
/* 375 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 378 */                 return (this.pos < Object2ObjectArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public V next() {
/* 383 */                 if (!hasNext())
/* 384 */                   throw new NoSuchElementException(); 
/* 385 */                 return (V)Object2ObjectArrayMap.this.value[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 389 */                 if (this.pos == 0)
/* 390 */                   throw new IllegalStateException(); 
/* 391 */                 int tail = Object2ObjectArrayMap.this.size - this.pos;
/* 392 */                 System.arraycopy(Object2ObjectArrayMap.this.key, this.pos, Object2ObjectArrayMap.this.key, this.pos - 1, tail);
/* 393 */                 System.arraycopy(Object2ObjectArrayMap.this.value, this.pos, Object2ObjectArrayMap.this.value, this.pos - 1, tail);
/* 394 */                 Object2ObjectArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 400 */           return Object2ObjectArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 404 */           Object2ObjectArrayMap.this.clear();
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
/*     */   public Object2ObjectArrayMap<K, V> clone() {
/*     */     Object2ObjectArrayMap<K, V> c;
/*     */     try {
/* 423 */       c = (Object2ObjectArrayMap<K, V>)super.clone();
/* 424 */     } catch (CloneNotSupportedException cantHappen) {
/* 425 */       throw new InternalError();
/*     */     } 
/* 427 */     c.key = (Object[])this.key.clone();
/* 428 */     c.value = (Object[])this.value.clone();
/* 429 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 432 */     s.defaultWriteObject();
/* 433 */     for (int i = 0; i < this.size; i++) {
/* 434 */       s.writeObject(this.key[i]);
/* 435 */       s.writeObject(this.value[i]);
/*     */     } 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 439 */     s.defaultReadObject();
/* 440 */     this.key = new Object[this.size];
/* 441 */     this.value = new Object[this.size];
/* 442 */     for (int i = 0; i < this.size; i++) {
/* 443 */       this.key[i] = s.readObject();
/* 444 */       this.value[i] = s.readObject();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2ObjectArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */