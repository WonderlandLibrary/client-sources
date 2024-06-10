/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortArrays;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortCollection;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortIterator;
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
/*     */ public class Object2ShortArrayMap<K>
/*     */   extends AbstractObject2ShortMap<K>
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient Object[] key;
/*     */   private transient short[] value;
/*     */   private int size;
/*     */   
/*     */   public Object2ShortArrayMap(Object[] key, short[] value) {
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
/*     */   public Object2ShortArrayMap() {
/*  67 */     this.key = ObjectArrays.EMPTY_ARRAY;
/*  68 */     this.value = ShortArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2ShortArrayMap(int capacity) {
/*  77 */     this.key = new Object[capacity];
/*  78 */     this.value = new short[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2ShortArrayMap(Object2ShortMap<K> m) {
/*  87 */     this(m.size());
/*  88 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2ShortArrayMap(Map<? extends K, ? extends Short> m) {
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
/*     */   public Object2ShortArrayMap(Object[] key, short[] value, int size) {
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
/*     */     extends AbstractObjectSet<Object2ShortMap.Entry<K>> implements Object2ShortMap.FastEntrySet<K> {
/*     */     public ObjectIterator<Object2ShortMap.Entry<K>> iterator() {
/* 130 */       return (ObjectIterator)new ObjectIterator<Object2ShortMap.Entry<Object2ShortMap.Entry<K>>>() {
/* 131 */           int curr = -1; int next = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 134 */             return (this.next < Object2ShortArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object2ShortMap.Entry<K> next() {
/* 139 */             if (!hasNext())
/* 140 */               throw new NoSuchElementException(); 
/* 141 */             return new AbstractObject2ShortMap.BasicEntry<>((K)Object2ShortArrayMap.this.key[this.curr = this.next], Object2ShortArrayMap.this.value[this.next++]);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 145 */             if (this.curr == -1)
/* 146 */               throw new IllegalStateException(); 
/* 147 */             this.curr = -1;
/* 148 */             int tail = Object2ShortArrayMap.this.size-- - this.next--;
/* 149 */             System.arraycopy(Object2ShortArrayMap.this.key, this.next + 1, Object2ShortArrayMap.this.key, this.next, tail);
/* 150 */             System.arraycopy(Object2ShortArrayMap.this.value, this.next + 1, Object2ShortArrayMap.this.value, this.next, tail);
/* 151 */             Object2ShortArrayMap.this.key[Object2ShortArrayMap.this.size] = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     private EntrySet() {}
/*     */     public ObjectIterator<Object2ShortMap.Entry<K>> fastIterator() {
/* 157 */       return (ObjectIterator)new ObjectIterator<Object2ShortMap.Entry<Object2ShortMap.Entry<K>>>() {
/* 158 */           int next = 0; int curr = -1;
/* 159 */           final AbstractObject2ShortMap.BasicEntry<K> entry = new AbstractObject2ShortMap.BasicEntry<>();
/*     */           
/*     */           public boolean hasNext() {
/* 162 */             return (this.next < Object2ShortArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object2ShortMap.Entry<K> next() {
/* 167 */             if (!hasNext())
/* 168 */               throw new NoSuchElementException(); 
/* 169 */             this.entry.key = (K)Object2ShortArrayMap.this.key[this.curr = this.next];
/* 170 */             this.entry.value = Object2ShortArrayMap.this.value[this.next++];
/* 171 */             return this.entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 175 */             if (this.curr == -1)
/* 176 */               throw new IllegalStateException(); 
/* 177 */             this.curr = -1;
/* 178 */             int tail = Object2ShortArrayMap.this.size-- - this.next--;
/* 179 */             System.arraycopy(Object2ShortArrayMap.this.key, this.next + 1, Object2ShortArrayMap.this.key, this.next, tail);
/* 180 */             System.arraycopy(Object2ShortArrayMap.this.value, this.next + 1, Object2ShortArrayMap.this.value, this.next, tail);
/* 181 */             Object2ShortArrayMap.this.key[Object2ShortArrayMap.this.size] = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 187 */       return Object2ShortArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 192 */       if (!(o instanceof Map.Entry))
/* 193 */         return false; 
/* 194 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 195 */       if (e.getValue() == null || !(e.getValue() instanceof Short))
/* 196 */         return false; 
/* 197 */       K k = (K)e.getKey();
/* 198 */       return (Object2ShortArrayMap.this.containsKey(k) && Object2ShortArrayMap.this
/* 199 */         .getShort(k) == ((Short)e.getValue()).shortValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 204 */       if (!(o instanceof Map.Entry))
/* 205 */         return false; 
/* 206 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 207 */       if (e.getValue() == null || !(e.getValue() instanceof Short))
/* 208 */         return false; 
/* 209 */       K k = (K)e.getKey();
/* 210 */       short v = ((Short)e.getValue()).shortValue();
/* 211 */       int oldPos = Object2ShortArrayMap.this.findKey(k);
/* 212 */       if (oldPos == -1 || v != Object2ShortArrayMap.this.value[oldPos])
/* 213 */         return false; 
/* 214 */       int tail = Object2ShortArrayMap.this.size - oldPos - 1;
/* 215 */       System.arraycopy(Object2ShortArrayMap.this.key, oldPos + 1, Object2ShortArrayMap.this.key, oldPos, tail);
/* 216 */       System.arraycopy(Object2ShortArrayMap.this.value, oldPos + 1, Object2ShortArrayMap.this.value, oldPos, tail);
/*     */       
/* 218 */       Object2ShortArrayMap.this.size--;
/* 219 */       Object2ShortArrayMap.this.key[Object2ShortArrayMap.this.size] = null;
/* 220 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Object2ShortMap.FastEntrySet<K> object2ShortEntrySet() {
/* 225 */     return new EntrySet();
/*     */   }
/*     */   private int findKey(Object k) {
/* 228 */     Object[] key = this.key;
/* 229 */     for (int i = this.size; i-- != 0;) {
/* 230 */       if (Objects.equals(key[i], k))
/* 231 */         return i; 
/* 232 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public short getShort(Object k) {
/* 237 */     Object[] key = this.key;
/* 238 */     for (int i = this.size; i-- != 0;) {
/* 239 */       if (Objects.equals(key[i], k))
/* 240 */         return this.value[i]; 
/* 241 */     }  return this.defRetValue;
/*     */   }
/*     */   
/*     */   public int size() {
/* 245 */     return this.size;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 249 */     for (int i = this.size; i-- != 0;) {
/* 250 */       this.key[i] = null;
/*     */     }
/* 252 */     this.size = 0;
/*     */   }
/*     */   
/*     */   public boolean containsKey(Object k) {
/* 256 */     return (findKey(k) != -1);
/*     */   }
/*     */   
/*     */   public boolean containsValue(short v) {
/* 260 */     for (int i = this.size; i-- != 0;) {
/* 261 */       if (this.value[i] == v)
/* 262 */         return true; 
/* 263 */     }  return false;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 267 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public short put(K k, short v) {
/* 272 */     int oldKey = findKey(k);
/* 273 */     if (oldKey != -1) {
/* 274 */       short oldValue = this.value[oldKey];
/* 275 */       this.value[oldKey] = v;
/* 276 */       return oldValue;
/*     */     } 
/* 278 */     if (this.size == this.key.length) {
/* 279 */       Object[] newKey = new Object[(this.size == 0) ? 2 : (this.size * 2)];
/* 280 */       short[] newValue = new short[(this.size == 0) ? 2 : (this.size * 2)];
/* 281 */       for (int i = this.size; i-- != 0; ) {
/* 282 */         newKey[i] = this.key[i];
/* 283 */         newValue[i] = this.value[i];
/*     */       } 
/* 285 */       this.key = newKey;
/* 286 */       this.value = newValue;
/*     */     } 
/* 288 */     this.key[this.size] = k;
/* 289 */     this.value[this.size] = v;
/* 290 */     this.size++;
/* 291 */     return this.defRetValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public short removeShort(Object k) {
/* 296 */     int oldPos = findKey(k);
/* 297 */     if (oldPos == -1)
/* 298 */       return this.defRetValue; 
/* 299 */     short oldValue = this.value[oldPos];
/* 300 */     int tail = this.size - oldPos - 1;
/* 301 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 302 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 303 */     this.size--;
/* 304 */     this.key[this.size] = null;
/* 305 */     return oldValue;
/*     */   }
/*     */   
/*     */   public ObjectSet<K> keySet() {
/* 309 */     return new AbstractObjectSet<K>()
/*     */       {
/*     */         public boolean contains(Object k) {
/* 312 */           return (Object2ShortArrayMap.this.findKey(k) != -1);
/*     */         }
/*     */         
/*     */         public boolean remove(Object k) {
/* 316 */           int oldPos = Object2ShortArrayMap.this.findKey(k);
/* 317 */           if (oldPos == -1)
/* 318 */             return false; 
/* 319 */           int tail = Object2ShortArrayMap.this.size - oldPos - 1;
/* 320 */           System.arraycopy(Object2ShortArrayMap.this.key, oldPos + 1, Object2ShortArrayMap.this.key, oldPos, tail);
/* 321 */           System.arraycopy(Object2ShortArrayMap.this.value, oldPos + 1, Object2ShortArrayMap.this.value, oldPos, tail);
/* 322 */           Object2ShortArrayMap.this.size--;
/* 323 */           return true;
/*     */         }
/*     */         
/*     */         public ObjectIterator<K> iterator() {
/* 327 */           return new ObjectIterator<K>() {
/* 328 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 331 */                 return (this.pos < Object2ShortArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public K next() {
/* 336 */                 if (!hasNext())
/* 337 */                   throw new NoSuchElementException(); 
/* 338 */                 return (K)Object2ShortArrayMap.this.key[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 342 */                 if (this.pos == 0)
/* 343 */                   throw new IllegalStateException(); 
/* 344 */                 int tail = Object2ShortArrayMap.this.size - this.pos;
/* 345 */                 System.arraycopy(Object2ShortArrayMap.this.key, this.pos, Object2ShortArrayMap.this.key, this.pos - 1, tail);
/* 346 */                 System.arraycopy(Object2ShortArrayMap.this.value, this.pos, Object2ShortArrayMap.this.value, this.pos - 1, tail);
/* 347 */                 Object2ShortArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 353 */           return Object2ShortArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 357 */           Object2ShortArrayMap.this.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public ShortCollection values() {
/* 363 */     return (ShortCollection)new AbstractShortCollection()
/*     */       {
/*     */         public boolean contains(short v) {
/* 366 */           return Object2ShortArrayMap.this.containsValue(v);
/*     */         }
/*     */         
/*     */         public ShortIterator iterator() {
/* 370 */           return new ShortIterator() {
/* 371 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 374 */                 return (this.pos < Object2ShortArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public short nextShort() {
/* 379 */                 if (!hasNext())
/* 380 */                   throw new NoSuchElementException(); 
/* 381 */                 return Object2ShortArrayMap.this.value[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 385 */                 if (this.pos == 0)
/* 386 */                   throw new IllegalStateException(); 
/* 387 */                 int tail = Object2ShortArrayMap.this.size - this.pos;
/* 388 */                 System.arraycopy(Object2ShortArrayMap.this.key, this.pos, Object2ShortArrayMap.this.key, this.pos - 1, tail);
/* 389 */                 System.arraycopy(Object2ShortArrayMap.this.value, this.pos, Object2ShortArrayMap.this.value, this.pos - 1, tail);
/* 390 */                 Object2ShortArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 396 */           return Object2ShortArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 400 */           Object2ShortArrayMap.this.clear();
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
/*     */   public Object2ShortArrayMap<K> clone() {
/*     */     Object2ShortArrayMap<K> c;
/*     */     try {
/* 419 */       c = (Object2ShortArrayMap<K>)super.clone();
/* 420 */     } catch (CloneNotSupportedException cantHappen) {
/* 421 */       throw new InternalError();
/*     */     } 
/* 423 */     c.key = (Object[])this.key.clone();
/* 424 */     c.value = (short[])this.value.clone();
/* 425 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 428 */     s.defaultWriteObject();
/* 429 */     for (int i = 0; i < this.size; i++) {
/* 430 */       s.writeObject(this.key[i]);
/* 431 */       s.writeShort(this.value[i]);
/*     */     } 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 435 */     s.defaultReadObject();
/* 436 */     this.key = new Object[this.size];
/* 437 */     this.value = new short[this.size];
/* 438 */     for (int i = 0; i < this.size; i++) {
/* 439 */       this.key[i] = s.readObject();
/* 440 */       this.value[i] = s.readShort();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2ShortArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */