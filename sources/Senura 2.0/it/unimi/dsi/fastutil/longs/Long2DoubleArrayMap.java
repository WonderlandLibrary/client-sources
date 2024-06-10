/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleArrays;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleCollection;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleIterator;
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
/*     */ public class Long2DoubleArrayMap
/*     */   extends AbstractLong2DoubleMap
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient long[] key;
/*     */   private transient double[] value;
/*     */   private int size;
/*     */   
/*     */   public Long2DoubleArrayMap(long[] key, double[] value) {
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
/*     */   public Long2DoubleArrayMap() {
/*  67 */     this.key = LongArrays.EMPTY_ARRAY;
/*  68 */     this.value = DoubleArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long2DoubleArrayMap(int capacity) {
/*  77 */     this.key = new long[capacity];
/*  78 */     this.value = new double[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long2DoubleArrayMap(Long2DoubleMap m) {
/*  87 */     this(m.size());
/*  88 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long2DoubleArrayMap(Map<? extends Long, ? extends Double> m) {
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
/*     */   public Long2DoubleArrayMap(long[] key, double[] value, int size) {
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
/*     */     extends AbstractObjectSet<Long2DoubleMap.Entry> implements Long2DoubleMap.FastEntrySet {
/*     */     public ObjectIterator<Long2DoubleMap.Entry> iterator() {
/* 130 */       return new ObjectIterator<Long2DoubleMap.Entry>() {
/* 131 */           int curr = -1; int next = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 134 */             return (this.next < Long2DoubleArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Long2DoubleMap.Entry next() {
/* 139 */             if (!hasNext())
/* 140 */               throw new NoSuchElementException(); 
/* 141 */             return new AbstractLong2DoubleMap.BasicEntry(Long2DoubleArrayMap.this.key[this.curr = this.next], Long2DoubleArrayMap.this.value[this.next++]);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 145 */             if (this.curr == -1)
/* 146 */               throw new IllegalStateException(); 
/* 147 */             this.curr = -1;
/* 148 */             int tail = Long2DoubleArrayMap.this.size-- - this.next--;
/* 149 */             System.arraycopy(Long2DoubleArrayMap.this.key, this.next + 1, Long2DoubleArrayMap.this.key, this.next, tail);
/* 150 */             System.arraycopy(Long2DoubleArrayMap.this.value, this.next + 1, Long2DoubleArrayMap.this.value, this.next, tail);
/*     */           }
/*     */         };
/*     */     }
/*     */     private EntrySet() {}
/*     */     public ObjectIterator<Long2DoubleMap.Entry> fastIterator() {
/* 156 */       return new ObjectIterator<Long2DoubleMap.Entry>() {
/* 157 */           int next = 0; int curr = -1;
/* 158 */           final AbstractLong2DoubleMap.BasicEntry entry = new AbstractLong2DoubleMap.BasicEntry();
/*     */           
/*     */           public boolean hasNext() {
/* 161 */             return (this.next < Long2DoubleArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Long2DoubleMap.Entry next() {
/* 166 */             if (!hasNext())
/* 167 */               throw new NoSuchElementException(); 
/* 168 */             this.entry.key = Long2DoubleArrayMap.this.key[this.curr = this.next];
/* 169 */             this.entry.value = Long2DoubleArrayMap.this.value[this.next++];
/* 170 */             return this.entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 174 */             if (this.curr == -1)
/* 175 */               throw new IllegalStateException(); 
/* 176 */             this.curr = -1;
/* 177 */             int tail = Long2DoubleArrayMap.this.size-- - this.next--;
/* 178 */             System.arraycopy(Long2DoubleArrayMap.this.key, this.next + 1, Long2DoubleArrayMap.this.key, this.next, tail);
/* 179 */             System.arraycopy(Long2DoubleArrayMap.this.value, this.next + 1, Long2DoubleArrayMap.this.value, this.next, tail);
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 185 */       return Long2DoubleArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 190 */       if (!(o instanceof Map.Entry))
/* 191 */         return false; 
/* 192 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 193 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/* 194 */         return false; 
/* 195 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/* 196 */         return false; 
/* 197 */       long k = ((Long)e.getKey()).longValue();
/* 198 */       return (Long2DoubleArrayMap.this.containsKey(k) && 
/* 199 */         Double.doubleToLongBits(Long2DoubleArrayMap.this.get(k)) == 
/* 200 */         Double.doubleToLongBits(((Double)e.getValue()).doubleValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 205 */       if (!(o instanceof Map.Entry))
/* 206 */         return false; 
/* 207 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 208 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/* 209 */         return false; 
/* 210 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/* 211 */         return false; 
/* 212 */       long k = ((Long)e.getKey()).longValue();
/* 213 */       double v = ((Double)e.getValue()).doubleValue();
/* 214 */       int oldPos = Long2DoubleArrayMap.this.findKey(k);
/* 215 */       if (oldPos == -1 || 
/* 216 */         Double.doubleToLongBits(v) != Double.doubleToLongBits(Long2DoubleArrayMap.this.value[oldPos]))
/* 217 */         return false; 
/* 218 */       int tail = Long2DoubleArrayMap.this.size - oldPos - 1;
/* 219 */       System.arraycopy(Long2DoubleArrayMap.this.key, oldPos + 1, Long2DoubleArrayMap.this.key, oldPos, tail);
/* 220 */       System.arraycopy(Long2DoubleArrayMap.this.value, oldPos + 1, Long2DoubleArrayMap.this.value, oldPos, tail);
/* 221 */       Long2DoubleArrayMap.this.size--;
/* 222 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Long2DoubleMap.FastEntrySet long2DoubleEntrySet() {
/* 227 */     return new EntrySet();
/*     */   }
/*     */   private int findKey(long k) {
/* 230 */     long[] key = this.key;
/* 231 */     for (int i = this.size; i-- != 0;) {
/* 232 */       if (key[i] == k)
/* 233 */         return i; 
/* 234 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public double get(long k) {
/* 239 */     long[] key = this.key;
/* 240 */     for (int i = this.size; i-- != 0;) {
/* 241 */       if (key[i] == k)
/* 242 */         return this.value[i]; 
/* 243 */     }  return this.defRetValue;
/*     */   }
/*     */   
/*     */   public int size() {
/* 247 */     return this.size;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 251 */     this.size = 0;
/*     */   }
/*     */   
/*     */   public boolean containsKey(long k) {
/* 255 */     return (findKey(k) != -1);
/*     */   }
/*     */   
/*     */   public boolean containsValue(double v) {
/* 259 */     for (int i = this.size; i-- != 0;) {
/* 260 */       if (Double.doubleToLongBits(this.value[i]) == Double.doubleToLongBits(v))
/* 261 */         return true; 
/* 262 */     }  return false;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 266 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public double put(long k, double v) {
/* 271 */     int oldKey = findKey(k);
/* 272 */     if (oldKey != -1) {
/* 273 */       double oldValue = this.value[oldKey];
/* 274 */       this.value[oldKey] = v;
/* 275 */       return oldValue;
/*     */     } 
/* 277 */     if (this.size == this.key.length) {
/* 278 */       long[] newKey = new long[(this.size == 0) ? 2 : (this.size * 2)];
/* 279 */       double[] newValue = new double[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public double remove(long k) {
/* 295 */     int oldPos = findKey(k);
/* 296 */     if (oldPos == -1)
/* 297 */       return this.defRetValue; 
/* 298 */     double oldValue = this.value[oldPos];
/* 299 */     int tail = this.size - oldPos - 1;
/* 300 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 301 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 302 */     this.size--;
/* 303 */     return oldValue;
/*     */   }
/*     */   
/*     */   public LongSet keySet() {
/* 307 */     return new AbstractLongSet()
/*     */       {
/*     */         public boolean contains(long k) {
/* 310 */           return (Long2DoubleArrayMap.this.findKey(k) != -1);
/*     */         }
/*     */         
/*     */         public boolean remove(long k) {
/* 314 */           int oldPos = Long2DoubleArrayMap.this.findKey(k);
/* 315 */           if (oldPos == -1)
/* 316 */             return false; 
/* 317 */           int tail = Long2DoubleArrayMap.this.size - oldPos - 1;
/* 318 */           System.arraycopy(Long2DoubleArrayMap.this.key, oldPos + 1, Long2DoubleArrayMap.this.key, oldPos, tail);
/* 319 */           System.arraycopy(Long2DoubleArrayMap.this.value, oldPos + 1, Long2DoubleArrayMap.this.value, oldPos, tail);
/* 320 */           Long2DoubleArrayMap.this.size--;
/* 321 */           return true;
/*     */         }
/*     */         
/*     */         public LongIterator iterator() {
/* 325 */           return new LongIterator() {
/* 326 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 329 */                 return (this.pos < Long2DoubleArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public long nextLong() {
/* 334 */                 if (!hasNext())
/* 335 */                   throw new NoSuchElementException(); 
/* 336 */                 return Long2DoubleArrayMap.this.key[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 340 */                 if (this.pos == 0)
/* 341 */                   throw new IllegalStateException(); 
/* 342 */                 int tail = Long2DoubleArrayMap.this.size - this.pos;
/* 343 */                 System.arraycopy(Long2DoubleArrayMap.this.key, this.pos, Long2DoubleArrayMap.this.key, this.pos - 1, tail);
/* 344 */                 System.arraycopy(Long2DoubleArrayMap.this.value, this.pos, Long2DoubleArrayMap.this.value, this.pos - 1, tail);
/* 345 */                 Long2DoubleArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 351 */           return Long2DoubleArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 355 */           Long2DoubleArrayMap.this.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public DoubleCollection values() {
/* 361 */     return (DoubleCollection)new AbstractDoubleCollection()
/*     */       {
/*     */         public boolean contains(double v) {
/* 364 */           return Long2DoubleArrayMap.this.containsValue(v);
/*     */         }
/*     */         
/*     */         public DoubleIterator iterator() {
/* 368 */           return new DoubleIterator() {
/* 369 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 372 */                 return (this.pos < Long2DoubleArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public double nextDouble() {
/* 377 */                 if (!hasNext())
/* 378 */                   throw new NoSuchElementException(); 
/* 379 */                 return Long2DoubleArrayMap.this.value[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 383 */                 if (this.pos == 0)
/* 384 */                   throw new IllegalStateException(); 
/* 385 */                 int tail = Long2DoubleArrayMap.this.size - this.pos;
/* 386 */                 System.arraycopy(Long2DoubleArrayMap.this.key, this.pos, Long2DoubleArrayMap.this.key, this.pos - 1, tail);
/* 387 */                 System.arraycopy(Long2DoubleArrayMap.this.value, this.pos, Long2DoubleArrayMap.this.value, this.pos - 1, tail);
/* 388 */                 Long2DoubleArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 394 */           return Long2DoubleArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 398 */           Long2DoubleArrayMap.this.clear();
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
/*     */   public Long2DoubleArrayMap clone() {
/*     */     Long2DoubleArrayMap c;
/*     */     try {
/* 417 */       c = (Long2DoubleArrayMap)super.clone();
/* 418 */     } catch (CloneNotSupportedException cantHappen) {
/* 419 */       throw new InternalError();
/*     */     } 
/* 421 */     c.key = (long[])this.key.clone();
/* 422 */     c.value = (double[])this.value.clone();
/* 423 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 426 */     s.defaultWriteObject();
/* 427 */     for (int i = 0; i < this.size; i++) {
/* 428 */       s.writeLong(this.key[i]);
/* 429 */       s.writeDouble(this.value[i]);
/*     */     } 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 433 */     s.defaultReadObject();
/* 434 */     this.key = new long[this.size];
/* 435 */     this.value = new double[this.size];
/* 436 */     for (int i = 0; i < this.size; i++) {
/* 437 */       this.key[i] = s.readLong();
/* 438 */       this.value[i] = s.readDouble();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\Long2DoubleArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */