/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrays;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectCollection;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
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
/*     */ public class Int2ObjectArrayMap<V>
/*     */   extends AbstractInt2ObjectMap<V>
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient int[] key;
/*     */   private transient Object[] value;
/*     */   private int size;
/*     */   
/*     */   public Int2ObjectArrayMap(int[] key, Object[] value) {
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
/*     */   public Int2ObjectArrayMap() {
/*  67 */     this.key = IntArrays.EMPTY_ARRAY;
/*  68 */     this.value = ObjectArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Int2ObjectArrayMap(int capacity) {
/*  77 */     this.key = new int[capacity];
/*  78 */     this.value = new Object[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Int2ObjectArrayMap(Int2ObjectMap<V> m) {
/*  87 */     this(m.size());
/*  88 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Int2ObjectArrayMap(Map<? extends Integer, ? extends V> m) {
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
/*     */   public Int2ObjectArrayMap(int[] key, Object[] value, int size) {
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
/*     */     extends AbstractObjectSet<Int2ObjectMap.Entry<V>> implements Int2ObjectMap.FastEntrySet<V> {
/*     */     public ObjectIterator<Int2ObjectMap.Entry<V>> iterator() {
/* 130 */       return new ObjectIterator<Int2ObjectMap.Entry<V>>() {
/* 131 */           int curr = -1; int next = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 134 */             return (this.next < Int2ObjectArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Int2ObjectMap.Entry<V> next() {
/* 139 */             if (!hasNext())
/* 140 */               throw new NoSuchElementException(); 
/* 141 */             return new AbstractInt2ObjectMap.BasicEntry<>(Int2ObjectArrayMap.this.key[this.curr = this.next], (V)Int2ObjectArrayMap.this.value[this.next++]);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 145 */             if (this.curr == -1)
/* 146 */               throw new IllegalStateException(); 
/* 147 */             this.curr = -1;
/* 148 */             int tail = Int2ObjectArrayMap.this.size-- - this.next--;
/* 149 */             System.arraycopy(Int2ObjectArrayMap.this.key, this.next + 1, Int2ObjectArrayMap.this.key, this.next, tail);
/* 150 */             System.arraycopy(Int2ObjectArrayMap.this.value, this.next + 1, Int2ObjectArrayMap.this.value, this.next, tail);
/* 151 */             Int2ObjectArrayMap.this.value[Int2ObjectArrayMap.this.size] = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     private EntrySet() {}
/*     */     public ObjectIterator<Int2ObjectMap.Entry<V>> fastIterator() {
/* 157 */       return new ObjectIterator<Int2ObjectMap.Entry<V>>() {
/* 158 */           int next = 0; int curr = -1;
/* 159 */           final AbstractInt2ObjectMap.BasicEntry<V> entry = new AbstractInt2ObjectMap.BasicEntry<>();
/*     */           
/*     */           public boolean hasNext() {
/* 162 */             return (this.next < Int2ObjectArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Int2ObjectMap.Entry<V> next() {
/* 167 */             if (!hasNext())
/* 168 */               throw new NoSuchElementException(); 
/* 169 */             this.entry.key = Int2ObjectArrayMap.this.key[this.curr = this.next];
/* 170 */             this.entry.value = (V)Int2ObjectArrayMap.this.value[this.next++];
/* 171 */             return this.entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 175 */             if (this.curr == -1)
/* 176 */               throw new IllegalStateException(); 
/* 177 */             this.curr = -1;
/* 178 */             int tail = Int2ObjectArrayMap.this.size-- - this.next--;
/* 179 */             System.arraycopy(Int2ObjectArrayMap.this.key, this.next + 1, Int2ObjectArrayMap.this.key, this.next, tail);
/* 180 */             System.arraycopy(Int2ObjectArrayMap.this.value, this.next + 1, Int2ObjectArrayMap.this.value, this.next, tail);
/* 181 */             Int2ObjectArrayMap.this.value[Int2ObjectArrayMap.this.size] = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 187 */       return Int2ObjectArrayMap.this.size;
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
/* 198 */       return (Int2ObjectArrayMap.this.containsKey(k) && 
/* 199 */         Objects.equals(Int2ObjectArrayMap.this.get(k), e.getValue()));
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
/* 211 */       int oldPos = Int2ObjectArrayMap.this.findKey(k);
/* 212 */       if (oldPos == -1 || !Objects.equals(v, Int2ObjectArrayMap.this.value[oldPos]))
/* 213 */         return false; 
/* 214 */       int tail = Int2ObjectArrayMap.this.size - oldPos - 1;
/* 215 */       System.arraycopy(Int2ObjectArrayMap.this.key, oldPos + 1, Int2ObjectArrayMap.this.key, oldPos, tail);
/* 216 */       System.arraycopy(Int2ObjectArrayMap.this.value, oldPos + 1, Int2ObjectArrayMap.this.value, oldPos, tail);
/* 217 */       Int2ObjectArrayMap.this.size--;
/* 218 */       Int2ObjectArrayMap.this.value[Int2ObjectArrayMap.this.size] = null;
/* 219 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Int2ObjectMap.FastEntrySet<V> int2ObjectEntrySet() {
/* 224 */     return new EntrySet();
/*     */   }
/*     */   private int findKey(int k) {
/* 227 */     int[] key = this.key;
/* 228 */     for (int i = this.size; i-- != 0;) {
/* 229 */       if (key[i] == k)
/* 230 */         return i; 
/* 231 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public V get(int k) {
/* 236 */     int[] key = this.key;
/* 237 */     for (int i = this.size; i-- != 0;) {
/* 238 */       if (key[i] == k)
/* 239 */         return (V)this.value[i]; 
/* 240 */     }  return this.defRetValue;
/*     */   }
/*     */   
/*     */   public int size() {
/* 244 */     return this.size;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 248 */     for (int i = this.size; i-- != 0;) {
/* 249 */       this.value[i] = null;
/*     */     }
/* 251 */     this.size = 0;
/*     */   }
/*     */   
/*     */   public boolean containsKey(int k) {
/* 255 */     return (findKey(k) != -1);
/*     */   }
/*     */   
/*     */   public boolean containsValue(Object v) {
/* 259 */     for (int i = this.size; i-- != 0;) {
/* 260 */       if (Objects.equals(this.value[i], v))
/* 261 */         return true; 
/* 262 */     }  return false;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 266 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public V put(int k, V v) {
/* 271 */     int oldKey = findKey(k);
/* 272 */     if (oldKey != -1) {
/* 273 */       V oldValue = (V)this.value[oldKey];
/* 274 */       this.value[oldKey] = v;
/* 275 */       return oldValue;
/*     */     } 
/* 277 */     if (this.size == this.key.length) {
/* 278 */       int[] newKey = new int[(this.size == 0) ? 2 : (this.size * 2)];
/* 279 */       Object[] newValue = new Object[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public V remove(int k) {
/* 295 */     int oldPos = findKey(k);
/* 296 */     if (oldPos == -1)
/* 297 */       return this.defRetValue; 
/* 298 */     V oldValue = (V)this.value[oldPos];
/* 299 */     int tail = this.size - oldPos - 1;
/* 300 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 301 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 302 */     this.size--;
/* 303 */     this.value[this.size] = null;
/* 304 */     return oldValue;
/*     */   }
/*     */   
/*     */   public IntSet keySet() {
/* 308 */     return new AbstractIntSet()
/*     */       {
/*     */         public boolean contains(int k) {
/* 311 */           return (Int2ObjectArrayMap.this.findKey(k) != -1);
/*     */         }
/*     */         
/*     */         public boolean remove(int k) {
/* 315 */           int oldPos = Int2ObjectArrayMap.this.findKey(k);
/* 316 */           if (oldPos == -1)
/* 317 */             return false; 
/* 318 */           int tail = Int2ObjectArrayMap.this.size - oldPos - 1;
/* 319 */           System.arraycopy(Int2ObjectArrayMap.this.key, oldPos + 1, Int2ObjectArrayMap.this.key, oldPos, tail);
/* 320 */           System.arraycopy(Int2ObjectArrayMap.this.value, oldPos + 1, Int2ObjectArrayMap.this.value, oldPos, tail);
/* 321 */           Int2ObjectArrayMap.this.size--;
/* 322 */           return true;
/*     */         }
/*     */         
/*     */         public IntIterator iterator() {
/* 326 */           return new IntIterator() {
/* 327 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 330 */                 return (this.pos < Int2ObjectArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public int nextInt() {
/* 335 */                 if (!hasNext())
/* 336 */                   throw new NoSuchElementException(); 
/* 337 */                 return Int2ObjectArrayMap.this.key[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 341 */                 if (this.pos == 0)
/* 342 */                   throw new IllegalStateException(); 
/* 343 */                 int tail = Int2ObjectArrayMap.this.size - this.pos;
/* 344 */                 System.arraycopy(Int2ObjectArrayMap.this.key, this.pos, Int2ObjectArrayMap.this.key, this.pos - 1, tail);
/* 345 */                 System.arraycopy(Int2ObjectArrayMap.this.value, this.pos, Int2ObjectArrayMap.this.value, this.pos - 1, tail);
/* 346 */                 Int2ObjectArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 352 */           return Int2ObjectArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 356 */           Int2ObjectArrayMap.this.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public ObjectCollection<V> values() {
/* 362 */     return (ObjectCollection<V>)new AbstractObjectCollection<V>()
/*     */       {
/*     */         public boolean contains(Object v) {
/* 365 */           return Int2ObjectArrayMap.this.containsValue(v);
/*     */         }
/*     */         
/*     */         public ObjectIterator<V> iterator() {
/* 369 */           return new ObjectIterator<V>() {
/* 370 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 373 */                 return (this.pos < Int2ObjectArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public V next() {
/* 378 */                 if (!hasNext())
/* 379 */                   throw new NoSuchElementException(); 
/* 380 */                 return (V)Int2ObjectArrayMap.this.value[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 384 */                 if (this.pos == 0)
/* 385 */                   throw new IllegalStateException(); 
/* 386 */                 int tail = Int2ObjectArrayMap.this.size - this.pos;
/* 387 */                 System.arraycopy(Int2ObjectArrayMap.this.key, this.pos, Int2ObjectArrayMap.this.key, this.pos - 1, tail);
/* 388 */                 System.arraycopy(Int2ObjectArrayMap.this.value, this.pos, Int2ObjectArrayMap.this.value, this.pos - 1, tail);
/* 389 */                 Int2ObjectArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 395 */           return Int2ObjectArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 399 */           Int2ObjectArrayMap.this.clear();
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
/*     */   public Int2ObjectArrayMap<V> clone() {
/*     */     Int2ObjectArrayMap<V> c;
/*     */     try {
/* 418 */       c = (Int2ObjectArrayMap<V>)super.clone();
/* 419 */     } catch (CloneNotSupportedException cantHappen) {
/* 420 */       throw new InternalError();
/*     */     } 
/* 422 */     c.key = (int[])this.key.clone();
/* 423 */     c.value = (Object[])this.value.clone();
/* 424 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 427 */     s.defaultWriteObject();
/* 428 */     for (int i = 0; i < this.size; i++) {
/* 429 */       s.writeInt(this.key[i]);
/* 430 */       s.writeObject(this.value[i]);
/*     */     } 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 434 */     s.defaultReadObject();
/* 435 */     this.key = new int[this.size];
/* 436 */     this.value = new Object[this.size];
/* 437 */     for (int i = 0; i < this.size; i++) {
/* 438 */       this.key[i] = s.readInt();
/* 439 */       this.value[i] = s.readObject();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\Int2ObjectArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */