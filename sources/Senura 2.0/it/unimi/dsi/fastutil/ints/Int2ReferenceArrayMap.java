/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrays;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ReferenceCollection;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
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
/*     */ public class Int2ReferenceArrayMap<V>
/*     */   extends AbstractInt2ReferenceMap<V>
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient int[] key;
/*     */   private transient Object[] value;
/*     */   private int size;
/*     */   
/*     */   public Int2ReferenceArrayMap(int[] key, Object[] value) {
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
/*     */   public Int2ReferenceArrayMap() {
/*  67 */     this.key = IntArrays.EMPTY_ARRAY;
/*  68 */     this.value = ObjectArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Int2ReferenceArrayMap(int capacity) {
/*  77 */     this.key = new int[capacity];
/*  78 */     this.value = new Object[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Int2ReferenceArrayMap(Int2ReferenceMap<V> m) {
/*  87 */     this(m.size());
/*  88 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Int2ReferenceArrayMap(Map<? extends Integer, ? extends V> m) {
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
/*     */   public Int2ReferenceArrayMap(int[] key, Object[] value, int size) {
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
/*     */     extends AbstractObjectSet<Int2ReferenceMap.Entry<V>> implements Int2ReferenceMap.FastEntrySet<V> {
/*     */     public ObjectIterator<Int2ReferenceMap.Entry<V>> iterator() {
/* 130 */       return new ObjectIterator<Int2ReferenceMap.Entry<V>>() {
/* 131 */           int curr = -1; int next = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 134 */             return (this.next < Int2ReferenceArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Int2ReferenceMap.Entry<V> next() {
/* 139 */             if (!hasNext())
/* 140 */               throw new NoSuchElementException(); 
/* 141 */             return new AbstractInt2ReferenceMap.BasicEntry<>(Int2ReferenceArrayMap.this.key[this.curr = this.next], (V)Int2ReferenceArrayMap.this.value[this.next++]);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 145 */             if (this.curr == -1)
/* 146 */               throw new IllegalStateException(); 
/* 147 */             this.curr = -1;
/* 148 */             int tail = Int2ReferenceArrayMap.this.size-- - this.next--;
/* 149 */             System.arraycopy(Int2ReferenceArrayMap.this.key, this.next + 1, Int2ReferenceArrayMap.this.key, this.next, tail);
/* 150 */             System.arraycopy(Int2ReferenceArrayMap.this.value, this.next + 1, Int2ReferenceArrayMap.this.value, this.next, tail);
/* 151 */             Int2ReferenceArrayMap.this.value[Int2ReferenceArrayMap.this.size] = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     private EntrySet() {}
/*     */     public ObjectIterator<Int2ReferenceMap.Entry<V>> fastIterator() {
/* 157 */       return new ObjectIterator<Int2ReferenceMap.Entry<V>>() {
/* 158 */           int next = 0; int curr = -1;
/* 159 */           final AbstractInt2ReferenceMap.BasicEntry<V> entry = new AbstractInt2ReferenceMap.BasicEntry<>();
/*     */           
/*     */           public boolean hasNext() {
/* 162 */             return (this.next < Int2ReferenceArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Int2ReferenceMap.Entry<V> next() {
/* 167 */             if (!hasNext())
/* 168 */               throw new NoSuchElementException(); 
/* 169 */             this.entry.key = Int2ReferenceArrayMap.this.key[this.curr = this.next];
/* 170 */             this.entry.value = (V)Int2ReferenceArrayMap.this.value[this.next++];
/* 171 */             return this.entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 175 */             if (this.curr == -1)
/* 176 */               throw new IllegalStateException(); 
/* 177 */             this.curr = -1;
/* 178 */             int tail = Int2ReferenceArrayMap.this.size-- - this.next--;
/* 179 */             System.arraycopy(Int2ReferenceArrayMap.this.key, this.next + 1, Int2ReferenceArrayMap.this.key, this.next, tail);
/* 180 */             System.arraycopy(Int2ReferenceArrayMap.this.value, this.next + 1, Int2ReferenceArrayMap.this.value, this.next, tail);
/* 181 */             Int2ReferenceArrayMap.this.value[Int2ReferenceArrayMap.this.size] = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 187 */       return Int2ReferenceArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 192 */       if (!(o instanceof Map.Entry))
/* 193 */         return false; 
/* 194 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 195 */       if (e.getKey() == null || !(e.getKey() instanceof Integer))
/* 196 */         return false; 
/* 197 */       int k = ((Integer)e.getKey()).intValue();
/* 198 */       return (Int2ReferenceArrayMap.this.containsKey(k) && Int2ReferenceArrayMap.this
/* 199 */         .get(k) == e.getValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 204 */       if (!(o instanceof Map.Entry))
/* 205 */         return false; 
/* 206 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 207 */       if (e.getKey() == null || !(e.getKey() instanceof Integer))
/* 208 */         return false; 
/* 209 */       int k = ((Integer)e.getKey()).intValue();
/* 210 */       V v = (V)e.getValue();
/* 211 */       int oldPos = Int2ReferenceArrayMap.this.findKey(k);
/* 212 */       if (oldPos == -1 || v != Int2ReferenceArrayMap.this.value[oldPos])
/* 213 */         return false; 
/* 214 */       int tail = Int2ReferenceArrayMap.this.size - oldPos - 1;
/* 215 */       System.arraycopy(Int2ReferenceArrayMap.this.key, oldPos + 1, Int2ReferenceArrayMap.this.key, oldPos, tail);
/* 216 */       System.arraycopy(Int2ReferenceArrayMap.this.value, oldPos + 1, Int2ReferenceArrayMap.this.value, oldPos, tail);
/*     */       
/* 218 */       Int2ReferenceArrayMap.this.size--;
/* 219 */       Int2ReferenceArrayMap.this.value[Int2ReferenceArrayMap.this.size] = null;
/* 220 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Int2ReferenceMap.FastEntrySet<V> int2ReferenceEntrySet() {
/* 225 */     return new EntrySet();
/*     */   }
/*     */   private int findKey(int k) {
/* 228 */     int[] key = this.key;
/* 229 */     for (int i = this.size; i-- != 0;) {
/* 230 */       if (key[i] == k)
/* 231 */         return i; 
/* 232 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public V get(int k) {
/* 237 */     int[] key = this.key;
/* 238 */     for (int i = this.size; i-- != 0;) {
/* 239 */       if (key[i] == k)
/* 240 */         return (V)this.value[i]; 
/* 241 */     }  return this.defRetValue;
/*     */   }
/*     */   
/*     */   public int size() {
/* 245 */     return this.size;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 249 */     for (int i = this.size; i-- != 0;) {
/* 250 */       this.value[i] = null;
/*     */     }
/* 252 */     this.size = 0;
/*     */   }
/*     */   
/*     */   public boolean containsKey(int k) {
/* 256 */     return (findKey(k) != -1);
/*     */   }
/*     */   
/*     */   public boolean containsValue(Object v) {
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
/*     */   public V put(int k, V v) {
/* 272 */     int oldKey = findKey(k);
/* 273 */     if (oldKey != -1) {
/* 274 */       V oldValue = (V)this.value[oldKey];
/* 275 */       this.value[oldKey] = v;
/* 276 */       return oldValue;
/*     */     } 
/* 278 */     if (this.size == this.key.length) {
/* 279 */       int[] newKey = new int[(this.size == 0) ? 2 : (this.size * 2)];
/* 280 */       Object[] newValue = new Object[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public V remove(int k) {
/* 296 */     int oldPos = findKey(k);
/* 297 */     if (oldPos == -1)
/* 298 */       return this.defRetValue; 
/* 299 */     V oldValue = (V)this.value[oldPos];
/* 300 */     int tail = this.size - oldPos - 1;
/* 301 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 302 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 303 */     this.size--;
/* 304 */     this.value[this.size] = null;
/* 305 */     return oldValue;
/*     */   }
/*     */   
/*     */   public IntSet keySet() {
/* 309 */     return new AbstractIntSet()
/*     */       {
/*     */         public boolean contains(int k) {
/* 312 */           return (Int2ReferenceArrayMap.this.findKey(k) != -1);
/*     */         }
/*     */         
/*     */         public boolean remove(int k) {
/* 316 */           int oldPos = Int2ReferenceArrayMap.this.findKey(k);
/* 317 */           if (oldPos == -1)
/* 318 */             return false; 
/* 319 */           int tail = Int2ReferenceArrayMap.this.size - oldPos - 1;
/* 320 */           System.arraycopy(Int2ReferenceArrayMap.this.key, oldPos + 1, Int2ReferenceArrayMap.this.key, oldPos, tail);
/* 321 */           System.arraycopy(Int2ReferenceArrayMap.this.value, oldPos + 1, Int2ReferenceArrayMap.this.value, oldPos, tail);
/* 322 */           Int2ReferenceArrayMap.this.size--;
/* 323 */           return true;
/*     */         }
/*     */         
/*     */         public IntIterator iterator() {
/* 327 */           return new IntIterator() {
/* 328 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 331 */                 return (this.pos < Int2ReferenceArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public int nextInt() {
/* 336 */                 if (!hasNext())
/* 337 */                   throw new NoSuchElementException(); 
/* 338 */                 return Int2ReferenceArrayMap.this.key[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 342 */                 if (this.pos == 0)
/* 343 */                   throw new IllegalStateException(); 
/* 344 */                 int tail = Int2ReferenceArrayMap.this.size - this.pos;
/* 345 */                 System.arraycopy(Int2ReferenceArrayMap.this.key, this.pos, Int2ReferenceArrayMap.this.key, this.pos - 1, tail);
/* 346 */                 System.arraycopy(Int2ReferenceArrayMap.this.value, this.pos, Int2ReferenceArrayMap.this.value, this.pos - 1, tail);
/* 347 */                 Int2ReferenceArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 353 */           return Int2ReferenceArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 357 */           Int2ReferenceArrayMap.this.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public ReferenceCollection<V> values() {
/* 363 */     return (ReferenceCollection<V>)new AbstractReferenceCollection<V>()
/*     */       {
/*     */         public boolean contains(Object v) {
/* 366 */           return Int2ReferenceArrayMap.this.containsValue(v);
/*     */         }
/*     */         
/*     */         public ObjectIterator<V> iterator() {
/* 370 */           return new ObjectIterator<V>() {
/* 371 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 374 */                 return (this.pos < Int2ReferenceArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public V next() {
/* 379 */                 if (!hasNext())
/* 380 */                   throw new NoSuchElementException(); 
/* 381 */                 return (V)Int2ReferenceArrayMap.this.value[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 385 */                 if (this.pos == 0)
/* 386 */                   throw new IllegalStateException(); 
/* 387 */                 int tail = Int2ReferenceArrayMap.this.size - this.pos;
/* 388 */                 System.arraycopy(Int2ReferenceArrayMap.this.key, this.pos, Int2ReferenceArrayMap.this.key, this.pos - 1, tail);
/* 389 */                 System.arraycopy(Int2ReferenceArrayMap.this.value, this.pos, Int2ReferenceArrayMap.this.value, this.pos - 1, tail);
/* 390 */                 Int2ReferenceArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 396 */           return Int2ReferenceArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 400 */           Int2ReferenceArrayMap.this.clear();
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
/*     */   public Int2ReferenceArrayMap<V> clone() {
/*     */     Int2ReferenceArrayMap<V> c;
/*     */     try {
/* 419 */       c = (Int2ReferenceArrayMap<V>)super.clone();
/* 420 */     } catch (CloneNotSupportedException cantHappen) {
/* 421 */       throw new InternalError();
/*     */     } 
/* 423 */     c.key = (int[])this.key.clone();
/* 424 */     c.value = (Object[])this.value.clone();
/* 425 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 428 */     s.defaultWriteObject();
/* 429 */     for (int i = 0; i < this.size; i++) {
/* 430 */       s.writeInt(this.key[i]);
/* 431 */       s.writeObject(this.value[i]);
/*     */     } 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 435 */     s.defaultReadObject();
/* 436 */     this.key = new int[this.size];
/* 437 */     this.value = new Object[this.size];
/* 438 */     for (int i = 0; i < this.size; i++) {
/* 439 */       this.key[i] = s.readInt();
/* 440 */       this.value[i] = s.readObject();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\Int2ReferenceArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */