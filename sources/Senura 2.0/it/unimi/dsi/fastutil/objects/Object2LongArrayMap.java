/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
/*     */ import it.unimi.dsi.fastutil.longs.LongArrays;
/*     */ import it.unimi.dsi.fastutil.longs.LongCollection;
/*     */ import it.unimi.dsi.fastutil.longs.LongIterator;
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
/*     */ public class Object2LongArrayMap<K>
/*     */   extends AbstractObject2LongMap<K>
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient Object[] key;
/*     */   private transient long[] value;
/*     */   private int size;
/*     */   
/*     */   public Object2LongArrayMap(Object[] key, long[] value) {
/*  56 */     this.key = key;
/*  57 */     this.value = value;
/*  58 */     this.size = key.length;
/*  59 */     if (key.length != value.length) {
/*  60 */       throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2LongArrayMap() {
/*  67 */     this.key = ObjectArrays.EMPTY_ARRAY;
/*  68 */     this.value = LongArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2LongArrayMap(int capacity) {
/*  77 */     this.key = new Object[capacity];
/*  78 */     this.value = new long[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2LongArrayMap(Object2LongMap<K> m) {
/*  87 */     this(m.size());
/*  88 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2LongArrayMap(Map<? extends K, ? extends Long> m) {
/*  97 */     this(m.size());
/*  98 */     putAll(m);
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
/*     */   public Object2LongArrayMap(Object[] key, long[] value, int size) {
/* 117 */     this.key = key;
/* 118 */     this.value = value;
/* 119 */     this.size = size;
/* 120 */     if (key.length != value.length) {
/* 121 */       throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/*     */     }
/* 123 */     if (size > key.length)
/* 124 */       throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")"); 
/*     */   }
/*     */   
/*     */   private final class EntrySet
/*     */     extends AbstractObjectSet<Object2LongMap.Entry<K>> implements Object2LongMap.FastEntrySet<K> {
/*     */     public ObjectIterator<Object2LongMap.Entry<K>> iterator() {
/* 130 */       return (ObjectIterator)new ObjectIterator<Object2LongMap.Entry<Object2LongMap.Entry<K>>>() {
/* 131 */           int curr = -1; int next = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 134 */             return (this.next < Object2LongArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object2LongMap.Entry<K> next() {
/* 139 */             if (!hasNext())
/* 140 */               throw new NoSuchElementException(); 
/* 141 */             return new AbstractObject2LongMap.BasicEntry<>((K)Object2LongArrayMap.this.key[this.curr = this.next], Object2LongArrayMap.this.value[this.next++]);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 145 */             if (this.curr == -1)
/* 146 */               throw new IllegalStateException(); 
/* 147 */             this.curr = -1;
/* 148 */             int tail = Object2LongArrayMap.this.size-- - this.next--;
/* 149 */             System.arraycopy(Object2LongArrayMap.this.key, this.next + 1, Object2LongArrayMap.this.key, this.next, tail);
/* 150 */             System.arraycopy(Object2LongArrayMap.this.value, this.next + 1, Object2LongArrayMap.this.value, this.next, tail);
/* 151 */             Object2LongArrayMap.this.key[Object2LongArrayMap.this.size] = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     private EntrySet() {}
/*     */     public ObjectIterator<Object2LongMap.Entry<K>> fastIterator() {
/* 157 */       return (ObjectIterator)new ObjectIterator<Object2LongMap.Entry<Object2LongMap.Entry<K>>>() {
/* 158 */           int next = 0; int curr = -1;
/* 159 */           final AbstractObject2LongMap.BasicEntry<K> entry = new AbstractObject2LongMap.BasicEntry<>();
/*     */           
/*     */           public boolean hasNext() {
/* 162 */             return (this.next < Object2LongArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object2LongMap.Entry<K> next() {
/* 167 */             if (!hasNext())
/* 168 */               throw new NoSuchElementException(); 
/* 169 */             this.entry.key = (K)Object2LongArrayMap.this.key[this.curr = this.next];
/* 170 */             this.entry.value = Object2LongArrayMap.this.value[this.next++];
/* 171 */             return this.entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 175 */             if (this.curr == -1)
/* 176 */               throw new IllegalStateException(); 
/* 177 */             this.curr = -1;
/* 178 */             int tail = Object2LongArrayMap.this.size-- - this.next--;
/* 179 */             System.arraycopy(Object2LongArrayMap.this.key, this.next + 1, Object2LongArrayMap.this.key, this.next, tail);
/* 180 */             System.arraycopy(Object2LongArrayMap.this.value, this.next + 1, Object2LongArrayMap.this.value, this.next, tail);
/* 181 */             Object2LongArrayMap.this.key[Object2LongArrayMap.this.size] = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 187 */       return Object2LongArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 192 */       if (!(o instanceof Map.Entry))
/* 193 */         return false; 
/* 194 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 195 */       if (e.getValue() == null || !(e.getValue() instanceof Long))
/* 196 */         return false; 
/* 197 */       K k = (K)e.getKey();
/* 198 */       return (Object2LongArrayMap.this.containsKey(k) && Object2LongArrayMap.this
/* 199 */         .getLong(k) == ((Long)e.getValue()).longValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 204 */       if (!(o instanceof Map.Entry))
/* 205 */         return false; 
/* 206 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 207 */       if (e.getValue() == null || !(e.getValue() instanceof Long))
/* 208 */         return false; 
/* 209 */       K k = (K)e.getKey();
/* 210 */       long v = ((Long)e.getValue()).longValue();
/* 211 */       int oldPos = Object2LongArrayMap.this.findKey(k);
/* 212 */       if (oldPos == -1 || v != Object2LongArrayMap.this.value[oldPos])
/* 213 */         return false; 
/* 214 */       int tail = Object2LongArrayMap.this.size - oldPos - 1;
/* 215 */       System.arraycopy(Object2LongArrayMap.this.key, oldPos + 1, Object2LongArrayMap.this.key, oldPos, tail);
/* 216 */       System.arraycopy(Object2LongArrayMap.this.value, oldPos + 1, Object2LongArrayMap.this.value, oldPos, tail);
/* 217 */       Object2LongArrayMap.this.size--;
/* 218 */       Object2LongArrayMap.this.key[Object2LongArrayMap.this.size] = null;
/* 219 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Object2LongMap.FastEntrySet<K> object2LongEntrySet() {
/* 224 */     return new EntrySet();
/*     */   }
/*     */   private int findKey(Object k) {
/* 227 */     Object[] key = this.key;
/* 228 */     for (int i = this.size; i-- != 0;) {
/* 229 */       if (Objects.equals(key[i], k))
/* 230 */         return i; 
/* 231 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLong(Object k) {
/* 236 */     Object[] key = this.key;
/* 237 */     for (int i = this.size; i-- != 0;) {
/* 238 */       if (Objects.equals(key[i], k))
/* 239 */         return this.value[i]; 
/* 240 */     }  return this.defRetValue;
/*     */   }
/*     */   
/*     */   public int size() {
/* 244 */     return this.size;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 248 */     for (int i = this.size; i-- != 0;) {
/* 249 */       this.key[i] = null;
/*     */     }
/* 251 */     this.size = 0;
/*     */   }
/*     */   
/*     */   public boolean containsKey(Object k) {
/* 255 */     return (findKey(k) != -1);
/*     */   }
/*     */   
/*     */   public boolean containsValue(long v) {
/* 259 */     for (int i = this.size; i-- != 0;) {
/* 260 */       if (this.value[i] == v)
/* 261 */         return true; 
/* 262 */     }  return false;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 266 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public long put(K k, long v) {
/* 271 */     int oldKey = findKey(k);
/* 272 */     if (oldKey != -1) {
/* 273 */       long oldValue = this.value[oldKey];
/* 274 */       this.value[oldKey] = v;
/* 275 */       return oldValue;
/*     */     } 
/* 277 */     if (this.size == this.key.length) {
/* 278 */       Object[] newKey = new Object[(this.size == 0) ? 2 : (this.size * 2)];
/* 279 */       long[] newValue = new long[(this.size == 0) ? 2 : (this.size * 2)];
/* 280 */       for (int i = this.size; i-- != 0; ) {
/* 281 */         newKey[i] = this.key[i];
/* 282 */         newValue[i] = this.value[i];
/*     */       } 
/* 284 */       this.key = newKey;
/* 285 */       this.value = newValue;
/*     */     } 
/* 287 */     this.key[this.size] = k;
/* 288 */     this.value[this.size] = v;
/* 289 */     this.size++;
/* 290 */     return this.defRetValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public long removeLong(Object k) {
/* 295 */     int oldPos = findKey(k);
/* 296 */     if (oldPos == -1)
/* 297 */       return this.defRetValue; 
/* 298 */     long oldValue = this.value[oldPos];
/* 299 */     int tail = this.size - oldPos - 1;
/* 300 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 301 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 302 */     this.size--;
/* 303 */     this.key[this.size] = null;
/* 304 */     return oldValue;
/*     */   }
/*     */   
/*     */   public ObjectSet<K> keySet() {
/* 308 */     return new AbstractObjectSet<K>()
/*     */       {
/*     */         public boolean contains(Object k) {
/* 311 */           return (Object2LongArrayMap.this.findKey(k) != -1);
/*     */         }
/*     */         
/*     */         public boolean remove(Object k) {
/* 315 */           int oldPos = Object2LongArrayMap.this.findKey(k);
/* 316 */           if (oldPos == -1)
/* 317 */             return false; 
/* 318 */           int tail = Object2LongArrayMap.this.size - oldPos - 1;
/* 319 */           System.arraycopy(Object2LongArrayMap.this.key, oldPos + 1, Object2LongArrayMap.this.key, oldPos, tail);
/* 320 */           System.arraycopy(Object2LongArrayMap.this.value, oldPos + 1, Object2LongArrayMap.this.value, oldPos, tail);
/* 321 */           Object2LongArrayMap.this.size--;
/* 322 */           return true;
/*     */         }
/*     */         
/*     */         public ObjectIterator<K> iterator() {
/* 326 */           return new ObjectIterator<K>() {
/* 327 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 330 */                 return (this.pos < Object2LongArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public K next() {
/* 335 */                 if (!hasNext())
/* 336 */                   throw new NoSuchElementException(); 
/* 337 */                 return (K)Object2LongArrayMap.this.key[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 341 */                 if (this.pos == 0)
/* 342 */                   throw new IllegalStateException(); 
/* 343 */                 int tail = Object2LongArrayMap.this.size - this.pos;
/* 344 */                 System.arraycopy(Object2LongArrayMap.this.key, this.pos, Object2LongArrayMap.this.key, this.pos - 1, tail);
/* 345 */                 System.arraycopy(Object2LongArrayMap.this.value, this.pos, Object2LongArrayMap.this.value, this.pos - 1, tail);
/* 346 */                 Object2LongArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 352 */           return Object2LongArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 356 */           Object2LongArrayMap.this.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public LongCollection values() {
/* 362 */     return (LongCollection)new AbstractLongCollection()
/*     */       {
/*     */         public boolean contains(long v) {
/* 365 */           return Object2LongArrayMap.this.containsValue(v);
/*     */         }
/*     */         
/*     */         public LongIterator iterator() {
/* 369 */           return new LongIterator() {
/* 370 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 373 */                 return (this.pos < Object2LongArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public long nextLong() {
/* 378 */                 if (!hasNext())
/* 379 */                   throw new NoSuchElementException(); 
/* 380 */                 return Object2LongArrayMap.this.value[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 384 */                 if (this.pos == 0)
/* 385 */                   throw new IllegalStateException(); 
/* 386 */                 int tail = Object2LongArrayMap.this.size - this.pos;
/* 387 */                 System.arraycopy(Object2LongArrayMap.this.key, this.pos, Object2LongArrayMap.this.key, this.pos - 1, tail);
/* 388 */                 System.arraycopy(Object2LongArrayMap.this.value, this.pos, Object2LongArrayMap.this.value, this.pos - 1, tail);
/* 389 */                 Object2LongArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 395 */           return Object2LongArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 399 */           Object2LongArrayMap.this.clear();
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
/*     */   public Object2LongArrayMap<K> clone() {
/*     */     Object2LongArrayMap<K> c;
/*     */     try {
/* 418 */       c = (Object2LongArrayMap<K>)super.clone();
/* 419 */     } catch (CloneNotSupportedException cantHappen) {
/* 420 */       throw new InternalError();
/*     */     } 
/* 422 */     c.key = (Object[])this.key.clone();
/* 423 */     c.value = (long[])this.value.clone();
/* 424 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 427 */     s.defaultWriteObject();
/* 428 */     for (int i = 0; i < this.size; i++) {
/* 429 */       s.writeObject(this.key[i]);
/* 430 */       s.writeLong(this.value[i]);
/*     */     } 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 434 */     s.defaultReadObject();
/* 435 */     this.key = new Object[this.size];
/* 436 */     this.value = new long[this.size];
/* 437 */     for (int i = 0; i < this.size; i++) {
/* 438 */       this.key[i] = s.readObject();
/* 439 */       this.value[i] = s.readLong();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2LongArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */