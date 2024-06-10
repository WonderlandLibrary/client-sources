/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrays;
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntIterator;
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
/*     */ public class Object2IntArrayMap<K>
/*     */   extends AbstractObject2IntMap<K>
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient Object[] key;
/*     */   private transient int[] value;
/*     */   private int size;
/*     */   
/*     */   public Object2IntArrayMap(Object[] key, int[] value) {
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
/*     */   public Object2IntArrayMap() {
/*  67 */     this.key = ObjectArrays.EMPTY_ARRAY;
/*  68 */     this.value = IntArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2IntArrayMap(int capacity) {
/*  77 */     this.key = new Object[capacity];
/*  78 */     this.value = new int[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2IntArrayMap(Object2IntMap<K> m) {
/*  87 */     this(m.size());
/*  88 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2IntArrayMap(Map<? extends K, ? extends Integer> m) {
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
/*     */   public Object2IntArrayMap(Object[] key, int[] value, int size) {
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
/*     */     extends AbstractObjectSet<Object2IntMap.Entry<K>> implements Object2IntMap.FastEntrySet<K> {
/*     */     public ObjectIterator<Object2IntMap.Entry<K>> iterator() {
/* 130 */       return (ObjectIterator)new ObjectIterator<Object2IntMap.Entry<Object2IntMap.Entry<K>>>() {
/* 131 */           int curr = -1; int next = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 134 */             return (this.next < Object2IntArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object2IntMap.Entry<K> next() {
/* 139 */             if (!hasNext())
/* 140 */               throw new NoSuchElementException(); 
/* 141 */             return new AbstractObject2IntMap.BasicEntry<>((K)Object2IntArrayMap.this.key[this.curr = this.next], Object2IntArrayMap.this.value[this.next++]);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 145 */             if (this.curr == -1)
/* 146 */               throw new IllegalStateException(); 
/* 147 */             this.curr = -1;
/* 148 */             int tail = Object2IntArrayMap.this.size-- - this.next--;
/* 149 */             System.arraycopy(Object2IntArrayMap.this.key, this.next + 1, Object2IntArrayMap.this.key, this.next, tail);
/* 150 */             System.arraycopy(Object2IntArrayMap.this.value, this.next + 1, Object2IntArrayMap.this.value, this.next, tail);
/* 151 */             Object2IntArrayMap.this.key[Object2IntArrayMap.this.size] = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     private EntrySet() {}
/*     */     public ObjectIterator<Object2IntMap.Entry<K>> fastIterator() {
/* 157 */       return (ObjectIterator)new ObjectIterator<Object2IntMap.Entry<Object2IntMap.Entry<K>>>() {
/* 158 */           int next = 0; int curr = -1;
/* 159 */           final AbstractObject2IntMap.BasicEntry<K> entry = new AbstractObject2IntMap.BasicEntry<>();
/*     */           
/*     */           public boolean hasNext() {
/* 162 */             return (this.next < Object2IntArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object2IntMap.Entry<K> next() {
/* 167 */             if (!hasNext())
/* 168 */               throw new NoSuchElementException(); 
/* 169 */             this.entry.key = (K)Object2IntArrayMap.this.key[this.curr = this.next];
/* 170 */             this.entry.value = Object2IntArrayMap.this.value[this.next++];
/* 171 */             return this.entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 175 */             if (this.curr == -1)
/* 176 */               throw new IllegalStateException(); 
/* 177 */             this.curr = -1;
/* 178 */             int tail = Object2IntArrayMap.this.size-- - this.next--;
/* 179 */             System.arraycopy(Object2IntArrayMap.this.key, this.next + 1, Object2IntArrayMap.this.key, this.next, tail);
/* 180 */             System.arraycopy(Object2IntArrayMap.this.value, this.next + 1, Object2IntArrayMap.this.value, this.next, tail);
/* 181 */             Object2IntArrayMap.this.key[Object2IntArrayMap.this.size] = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 187 */       return Object2IntArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 192 */       if (!(o instanceof Map.Entry))
/* 193 */         return false; 
/* 194 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 195 */       if (e.getValue() == null || !(e.getValue() instanceof Integer))
/* 196 */         return false; 
/* 197 */       K k = (K)e.getKey();
/* 198 */       return (Object2IntArrayMap.this.containsKey(k) && Object2IntArrayMap.this
/* 199 */         .getInt(k) == ((Integer)e.getValue()).intValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 204 */       if (!(o instanceof Map.Entry))
/* 205 */         return false; 
/* 206 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 207 */       if (e.getValue() == null || !(e.getValue() instanceof Integer))
/* 208 */         return false; 
/* 209 */       K k = (K)e.getKey();
/* 210 */       int v = ((Integer)e.getValue()).intValue();
/* 211 */       int oldPos = Object2IntArrayMap.this.findKey(k);
/* 212 */       if (oldPos == -1 || v != Object2IntArrayMap.this.value[oldPos])
/* 213 */         return false; 
/* 214 */       int tail = Object2IntArrayMap.this.size - oldPos - 1;
/* 215 */       System.arraycopy(Object2IntArrayMap.this.key, oldPos + 1, Object2IntArrayMap.this.key, oldPos, tail);
/* 216 */       System.arraycopy(Object2IntArrayMap.this.value, oldPos + 1, Object2IntArrayMap.this.value, oldPos, tail);
/* 217 */       Object2IntArrayMap.this.size--;
/* 218 */       Object2IntArrayMap.this.key[Object2IntArrayMap.this.size] = null;
/* 219 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Object2IntMap.FastEntrySet<K> object2IntEntrySet() {
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
/*     */   public int getInt(Object k) {
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
/*     */   public boolean containsValue(int v) {
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
/*     */   public int put(K k, int v) {
/* 271 */     int oldKey = findKey(k);
/* 272 */     if (oldKey != -1) {
/* 273 */       int oldValue = this.value[oldKey];
/* 274 */       this.value[oldKey] = v;
/* 275 */       return oldValue;
/*     */     } 
/* 277 */     if (this.size == this.key.length) {
/* 278 */       Object[] newKey = new Object[(this.size == 0) ? 2 : (this.size * 2)];
/* 279 */       int[] newValue = new int[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public int removeInt(Object k) {
/* 295 */     int oldPos = findKey(k);
/* 296 */     if (oldPos == -1)
/* 297 */       return this.defRetValue; 
/* 298 */     int oldValue = this.value[oldPos];
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
/* 311 */           return (Object2IntArrayMap.this.findKey(k) != -1);
/*     */         }
/*     */         
/*     */         public boolean remove(Object k) {
/* 315 */           int oldPos = Object2IntArrayMap.this.findKey(k);
/* 316 */           if (oldPos == -1)
/* 317 */             return false; 
/* 318 */           int tail = Object2IntArrayMap.this.size - oldPos - 1;
/* 319 */           System.arraycopy(Object2IntArrayMap.this.key, oldPos + 1, Object2IntArrayMap.this.key, oldPos, tail);
/* 320 */           System.arraycopy(Object2IntArrayMap.this.value, oldPos + 1, Object2IntArrayMap.this.value, oldPos, tail);
/* 321 */           Object2IntArrayMap.this.size--;
/* 322 */           return true;
/*     */         }
/*     */         
/*     */         public ObjectIterator<K> iterator() {
/* 326 */           return new ObjectIterator<K>() {
/* 327 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 330 */                 return (this.pos < Object2IntArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public K next() {
/* 335 */                 if (!hasNext())
/* 336 */                   throw new NoSuchElementException(); 
/* 337 */                 return (K)Object2IntArrayMap.this.key[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 341 */                 if (this.pos == 0)
/* 342 */                   throw new IllegalStateException(); 
/* 343 */                 int tail = Object2IntArrayMap.this.size - this.pos;
/* 344 */                 System.arraycopy(Object2IntArrayMap.this.key, this.pos, Object2IntArrayMap.this.key, this.pos - 1, tail);
/* 345 */                 System.arraycopy(Object2IntArrayMap.this.value, this.pos, Object2IntArrayMap.this.value, this.pos - 1, tail);
/* 346 */                 Object2IntArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 352 */           return Object2IntArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 356 */           Object2IntArrayMap.this.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public IntCollection values() {
/* 362 */     return (IntCollection)new AbstractIntCollection()
/*     */       {
/*     */         public boolean contains(int v) {
/* 365 */           return Object2IntArrayMap.this.containsValue(v);
/*     */         }
/*     */         
/*     */         public IntIterator iterator() {
/* 369 */           return new IntIterator() {
/* 370 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 373 */                 return (this.pos < Object2IntArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public int nextInt() {
/* 378 */                 if (!hasNext())
/* 379 */                   throw new NoSuchElementException(); 
/* 380 */                 return Object2IntArrayMap.this.value[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 384 */                 if (this.pos == 0)
/* 385 */                   throw new IllegalStateException(); 
/* 386 */                 int tail = Object2IntArrayMap.this.size - this.pos;
/* 387 */                 System.arraycopy(Object2IntArrayMap.this.key, this.pos, Object2IntArrayMap.this.key, this.pos - 1, tail);
/* 388 */                 System.arraycopy(Object2IntArrayMap.this.value, this.pos, Object2IntArrayMap.this.value, this.pos - 1, tail);
/* 389 */                 Object2IntArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 395 */           return Object2IntArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 399 */           Object2IntArrayMap.this.clear();
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
/*     */   public Object2IntArrayMap<K> clone() {
/*     */     Object2IntArrayMap<K> c;
/*     */     try {
/* 418 */       c = (Object2IntArrayMap<K>)super.clone();
/* 419 */     } catch (CloneNotSupportedException cantHappen) {
/* 420 */       throw new InternalError();
/*     */     } 
/* 422 */     c.key = (Object[])this.key.clone();
/* 423 */     c.value = (int[])this.value.clone();
/* 424 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 427 */     s.defaultWriteObject();
/* 428 */     for (int i = 0; i < this.size; i++) {
/* 429 */       s.writeObject(this.key[i]);
/* 430 */       s.writeInt(this.value[i]);
/*     */     } 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 434 */     s.defaultReadObject();
/* 435 */     this.key = new Object[this.size];
/* 436 */     this.value = new int[this.size];
/* 437 */     for (int i = 0; i < this.size; i++) {
/* 438 */       this.key[i] = s.readObject();
/* 439 */       this.value[i] = s.readInt();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2IntArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */