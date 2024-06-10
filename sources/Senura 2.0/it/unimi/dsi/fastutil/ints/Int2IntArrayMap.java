/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
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
/*     */ public class Int2IntArrayMap
/*     */   extends AbstractInt2IntMap
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient int[] key;
/*     */   private transient int[] value;
/*     */   private int size;
/*     */   
/*     */   public Int2IntArrayMap(int[] key, int[] value) {
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
/*     */   public Int2IntArrayMap() {
/*  67 */     this.key = IntArrays.EMPTY_ARRAY;
/*  68 */     this.value = IntArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Int2IntArrayMap(int capacity) {
/*  77 */     this.key = new int[capacity];
/*  78 */     this.value = new int[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Int2IntArrayMap(Int2IntMap m) {
/*  87 */     this(m.size());
/*  88 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Int2IntArrayMap(Map<? extends Integer, ? extends Integer> m) {
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
/*     */   public Int2IntArrayMap(int[] key, int[] value, int size) {
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
/*     */     extends AbstractObjectSet<Int2IntMap.Entry> implements Int2IntMap.FastEntrySet {
/*     */     public ObjectIterator<Int2IntMap.Entry> iterator() {
/* 130 */       return new ObjectIterator<Int2IntMap.Entry>() {
/* 131 */           int curr = -1; int next = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 134 */             return (this.next < Int2IntArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Int2IntMap.Entry next() {
/* 139 */             if (!hasNext())
/* 140 */               throw new NoSuchElementException(); 
/* 141 */             return new AbstractInt2IntMap.BasicEntry(Int2IntArrayMap.this.key[this.curr = this.next], Int2IntArrayMap.this.value[this.next++]);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 145 */             if (this.curr == -1)
/* 146 */               throw new IllegalStateException(); 
/* 147 */             this.curr = -1;
/* 148 */             int tail = Int2IntArrayMap.this.size-- - this.next--;
/* 149 */             System.arraycopy(Int2IntArrayMap.this.key, this.next + 1, Int2IntArrayMap.this.key, this.next, tail);
/* 150 */             System.arraycopy(Int2IntArrayMap.this.value, this.next + 1, Int2IntArrayMap.this.value, this.next, tail);
/*     */           }
/*     */         };
/*     */     }
/*     */     private EntrySet() {}
/*     */     public ObjectIterator<Int2IntMap.Entry> fastIterator() {
/* 156 */       return new ObjectIterator<Int2IntMap.Entry>() {
/* 157 */           int next = 0; int curr = -1;
/* 158 */           final AbstractInt2IntMap.BasicEntry entry = new AbstractInt2IntMap.BasicEntry();
/*     */           
/*     */           public boolean hasNext() {
/* 161 */             return (this.next < Int2IntArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Int2IntMap.Entry next() {
/* 166 */             if (!hasNext())
/* 167 */               throw new NoSuchElementException(); 
/* 168 */             this.entry.key = Int2IntArrayMap.this.key[this.curr = this.next];
/* 169 */             this.entry.value = Int2IntArrayMap.this.value[this.next++];
/* 170 */             return this.entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 174 */             if (this.curr == -1)
/* 175 */               throw new IllegalStateException(); 
/* 176 */             this.curr = -1;
/* 177 */             int tail = Int2IntArrayMap.this.size-- - this.next--;
/* 178 */             System.arraycopy(Int2IntArrayMap.this.key, this.next + 1, Int2IntArrayMap.this.key, this.next, tail);
/* 179 */             System.arraycopy(Int2IntArrayMap.this.value, this.next + 1, Int2IntArrayMap.this.value, this.next, tail);
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 185 */       return Int2IntArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 190 */       if (!(o instanceof Map.Entry))
/* 191 */         return false; 
/* 192 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 193 */       if (e.getKey() == null || !(e.getKey() instanceof Integer))
/* 194 */         return false; 
/* 195 */       if (e.getValue() == null || !(e.getValue() instanceof Integer))
/* 196 */         return false; 
/* 197 */       int k = ((Integer)e.getKey()).intValue();
/* 198 */       return (Int2IntArrayMap.this.containsKey(k) && Int2IntArrayMap.this
/* 199 */         .get(k) == ((Integer)e.getValue()).intValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 204 */       if (!(o instanceof Map.Entry))
/* 205 */         return false; 
/* 206 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 207 */       if (e.getKey() == null || !(e.getKey() instanceof Integer))
/* 208 */         return false; 
/* 209 */       if (e.getValue() == null || !(e.getValue() instanceof Integer))
/* 210 */         return false; 
/* 211 */       int k = ((Integer)e.getKey()).intValue();
/* 212 */       int v = ((Integer)e.getValue()).intValue();
/* 213 */       int oldPos = Int2IntArrayMap.this.findKey(k);
/* 214 */       if (oldPos == -1 || v != Int2IntArrayMap.this.value[oldPos])
/* 215 */         return false; 
/* 216 */       int tail = Int2IntArrayMap.this.size - oldPos - 1;
/* 217 */       System.arraycopy(Int2IntArrayMap.this.key, oldPos + 1, Int2IntArrayMap.this.key, oldPos, tail);
/* 218 */       System.arraycopy(Int2IntArrayMap.this.value, oldPos + 1, Int2IntArrayMap.this.value, oldPos, tail);
/* 219 */       Int2IntArrayMap.this.size--;
/* 220 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Int2IntMap.FastEntrySet int2IntEntrySet() {
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
/*     */   public int get(int k) {
/* 237 */     int[] key = this.key;
/* 238 */     for (int i = this.size; i-- != 0;) {
/* 239 */       if (key[i] == k)
/* 240 */         return this.value[i]; 
/* 241 */     }  return this.defRetValue;
/*     */   }
/*     */   
/*     */   public int size() {
/* 245 */     return this.size;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 249 */     this.size = 0;
/*     */   }
/*     */   
/*     */   public boolean containsKey(int k) {
/* 253 */     return (findKey(k) != -1);
/*     */   }
/*     */   
/*     */   public boolean containsValue(int v) {
/* 257 */     for (int i = this.size; i-- != 0;) {
/* 258 */       if (this.value[i] == v)
/* 259 */         return true; 
/* 260 */     }  return false;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 264 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int put(int k, int v) {
/* 269 */     int oldKey = findKey(k);
/* 270 */     if (oldKey != -1) {
/* 271 */       int oldValue = this.value[oldKey];
/* 272 */       this.value[oldKey] = v;
/* 273 */       return oldValue;
/*     */     } 
/* 275 */     if (this.size == this.key.length) {
/* 276 */       int[] newKey = new int[(this.size == 0) ? 2 : (this.size * 2)];
/* 277 */       int[] newValue = new int[(this.size == 0) ? 2 : (this.size * 2)];
/* 278 */       for (int i = this.size; i-- != 0; ) {
/* 279 */         newKey[i] = this.key[i];
/* 280 */         newValue[i] = this.value[i];
/*     */       } 
/* 282 */       this.key = newKey;
/* 283 */       this.value = newValue;
/*     */     } 
/* 285 */     this.key[this.size] = k;
/* 286 */     this.value[this.size] = v;
/* 287 */     this.size++;
/* 288 */     return this.defRetValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public int remove(int k) {
/* 293 */     int oldPos = findKey(k);
/* 294 */     if (oldPos == -1)
/* 295 */       return this.defRetValue; 
/* 296 */     int oldValue = this.value[oldPos];
/* 297 */     int tail = this.size - oldPos - 1;
/* 298 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 299 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 300 */     this.size--;
/* 301 */     return oldValue;
/*     */   }
/*     */   
/*     */   public IntSet keySet() {
/* 305 */     return new AbstractIntSet()
/*     */       {
/*     */         public boolean contains(int k) {
/* 308 */           return (Int2IntArrayMap.this.findKey(k) != -1);
/*     */         }
/*     */         
/*     */         public boolean remove(int k) {
/* 312 */           int oldPos = Int2IntArrayMap.this.findKey(k);
/* 313 */           if (oldPos == -1)
/* 314 */             return false; 
/* 315 */           int tail = Int2IntArrayMap.this.size - oldPos - 1;
/* 316 */           System.arraycopy(Int2IntArrayMap.this.key, oldPos + 1, Int2IntArrayMap.this.key, oldPos, tail);
/* 317 */           System.arraycopy(Int2IntArrayMap.this.value, oldPos + 1, Int2IntArrayMap.this.value, oldPos, tail);
/* 318 */           Int2IntArrayMap.this.size--;
/* 319 */           return true;
/*     */         }
/*     */         
/*     */         public IntIterator iterator() {
/* 323 */           return new IntIterator() {
/* 324 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 327 */                 return (this.pos < Int2IntArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public int nextInt() {
/* 332 */                 if (!hasNext())
/* 333 */                   throw new NoSuchElementException(); 
/* 334 */                 return Int2IntArrayMap.this.key[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 338 */                 if (this.pos == 0)
/* 339 */                   throw new IllegalStateException(); 
/* 340 */                 int tail = Int2IntArrayMap.this.size - this.pos;
/* 341 */                 System.arraycopy(Int2IntArrayMap.this.key, this.pos, Int2IntArrayMap.this.key, this.pos - 1, tail);
/* 342 */                 System.arraycopy(Int2IntArrayMap.this.value, this.pos, Int2IntArrayMap.this.value, this.pos - 1, tail);
/* 343 */                 Int2IntArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 349 */           return Int2IntArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 353 */           Int2IntArrayMap.this.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public IntCollection values() {
/* 359 */     return new AbstractIntCollection()
/*     */       {
/*     */         public boolean contains(int v) {
/* 362 */           return Int2IntArrayMap.this.containsValue(v);
/*     */         }
/*     */         
/*     */         public IntIterator iterator() {
/* 366 */           return new IntIterator() {
/* 367 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 370 */                 return (this.pos < Int2IntArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public int nextInt() {
/* 375 */                 if (!hasNext())
/* 376 */                   throw new NoSuchElementException(); 
/* 377 */                 return Int2IntArrayMap.this.value[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 381 */                 if (this.pos == 0)
/* 382 */                   throw new IllegalStateException(); 
/* 383 */                 int tail = Int2IntArrayMap.this.size - this.pos;
/* 384 */                 System.arraycopy(Int2IntArrayMap.this.key, this.pos, Int2IntArrayMap.this.key, this.pos - 1, tail);
/* 385 */                 System.arraycopy(Int2IntArrayMap.this.value, this.pos, Int2IntArrayMap.this.value, this.pos - 1, tail);
/* 386 */                 Int2IntArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 392 */           return Int2IntArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 396 */           Int2IntArrayMap.this.clear();
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
/*     */   public Int2IntArrayMap clone() {
/*     */     Int2IntArrayMap c;
/*     */     try {
/* 415 */       c = (Int2IntArrayMap)super.clone();
/* 416 */     } catch (CloneNotSupportedException cantHappen) {
/* 417 */       throw new InternalError();
/*     */     } 
/* 419 */     c.key = (int[])this.key.clone();
/* 420 */     c.value = (int[])this.value.clone();
/* 421 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 424 */     s.defaultWriteObject();
/* 425 */     for (int i = 0; i < this.size; i++) {
/* 426 */       s.writeInt(this.key[i]);
/* 427 */       s.writeInt(this.value[i]);
/*     */     } 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 431 */     s.defaultReadObject();
/* 432 */     this.key = new int[this.size];
/* 433 */     this.value = new int[this.size];
/* 434 */     for (int i = 0; i < this.size; i++) {
/* 435 */       this.key[i] = s.readInt();
/* 436 */       this.value[i] = s.readInt();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\Int2IntArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */