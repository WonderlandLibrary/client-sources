/*     */ package it.unimi.dsi.fastutil.ints;
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
/*     */ public class Int2DoubleArrayMap
/*     */   extends AbstractInt2DoubleMap
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient int[] key;
/*     */   private transient double[] value;
/*     */   private int size;
/*     */   
/*     */   public Int2DoubleArrayMap(int[] key, double[] value) {
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
/*     */   public Int2DoubleArrayMap() {
/*  67 */     this.key = IntArrays.EMPTY_ARRAY;
/*  68 */     this.value = DoubleArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Int2DoubleArrayMap(int capacity) {
/*  77 */     this.key = new int[capacity];
/*  78 */     this.value = new double[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Int2DoubleArrayMap(Int2DoubleMap m) {
/*  87 */     this(m.size());
/*  88 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Int2DoubleArrayMap(Map<? extends Integer, ? extends Double> m) {
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
/*     */   public Int2DoubleArrayMap(int[] key, double[] value, int size) {
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
/*     */     extends AbstractObjectSet<Int2DoubleMap.Entry> implements Int2DoubleMap.FastEntrySet {
/*     */     public ObjectIterator<Int2DoubleMap.Entry> iterator() {
/* 130 */       return new ObjectIterator<Int2DoubleMap.Entry>() {
/* 131 */           int curr = -1; int next = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 134 */             return (this.next < Int2DoubleArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Int2DoubleMap.Entry next() {
/* 139 */             if (!hasNext())
/* 140 */               throw new NoSuchElementException(); 
/* 141 */             return new AbstractInt2DoubleMap.BasicEntry(Int2DoubleArrayMap.this.key[this.curr = this.next], Int2DoubleArrayMap.this.value[this.next++]);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 145 */             if (this.curr == -1)
/* 146 */               throw new IllegalStateException(); 
/* 147 */             this.curr = -1;
/* 148 */             int tail = Int2DoubleArrayMap.this.size-- - this.next--;
/* 149 */             System.arraycopy(Int2DoubleArrayMap.this.key, this.next + 1, Int2DoubleArrayMap.this.key, this.next, tail);
/* 150 */             System.arraycopy(Int2DoubleArrayMap.this.value, this.next + 1, Int2DoubleArrayMap.this.value, this.next, tail);
/*     */           }
/*     */         };
/*     */     }
/*     */     private EntrySet() {}
/*     */     public ObjectIterator<Int2DoubleMap.Entry> fastIterator() {
/* 156 */       return new ObjectIterator<Int2DoubleMap.Entry>() {
/* 157 */           int next = 0; int curr = -1;
/* 158 */           final AbstractInt2DoubleMap.BasicEntry entry = new AbstractInt2DoubleMap.BasicEntry();
/*     */           
/*     */           public boolean hasNext() {
/* 161 */             return (this.next < Int2DoubleArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Int2DoubleMap.Entry next() {
/* 166 */             if (!hasNext())
/* 167 */               throw new NoSuchElementException(); 
/* 168 */             this.entry.key = Int2DoubleArrayMap.this.key[this.curr = this.next];
/* 169 */             this.entry.value = Int2DoubleArrayMap.this.value[this.next++];
/* 170 */             return this.entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 174 */             if (this.curr == -1)
/* 175 */               throw new IllegalStateException(); 
/* 176 */             this.curr = -1;
/* 177 */             int tail = Int2DoubleArrayMap.this.size-- - this.next--;
/* 178 */             System.arraycopy(Int2DoubleArrayMap.this.key, this.next + 1, Int2DoubleArrayMap.this.key, this.next, tail);
/* 179 */             System.arraycopy(Int2DoubleArrayMap.this.value, this.next + 1, Int2DoubleArrayMap.this.value, this.next, tail);
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 185 */       return Int2DoubleArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 190 */       if (!(o instanceof Map.Entry))
/* 191 */         return false; 
/* 192 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 193 */       if (e.getKey() == null || !(e.getKey() instanceof Integer))
/* 194 */         return false; 
/* 195 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/* 196 */         return false; 
/* 197 */       int k = ((Integer)e.getKey()).intValue();
/* 198 */       return (Int2DoubleArrayMap.this.containsKey(k) && 
/* 199 */         Double.doubleToLongBits(Int2DoubleArrayMap.this.get(k)) == 
/* 200 */         Double.doubleToLongBits(((Double)e.getValue()).doubleValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 205 */       if (!(o instanceof Map.Entry))
/* 206 */         return false; 
/* 207 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 208 */       if (e.getKey() == null || !(e.getKey() instanceof Integer))
/* 209 */         return false; 
/* 210 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/* 211 */         return false; 
/* 212 */       int k = ((Integer)e.getKey()).intValue();
/* 213 */       double v = ((Double)e.getValue()).doubleValue();
/* 214 */       int oldPos = Int2DoubleArrayMap.this.findKey(k);
/* 215 */       if (oldPos == -1 || 
/* 216 */         Double.doubleToLongBits(v) != Double.doubleToLongBits(Int2DoubleArrayMap.this.value[oldPos]))
/* 217 */         return false; 
/* 218 */       int tail = Int2DoubleArrayMap.this.size - oldPos - 1;
/* 219 */       System.arraycopy(Int2DoubleArrayMap.this.key, oldPos + 1, Int2DoubleArrayMap.this.key, oldPos, tail);
/* 220 */       System.arraycopy(Int2DoubleArrayMap.this.value, oldPos + 1, Int2DoubleArrayMap.this.value, oldPos, tail);
/* 221 */       Int2DoubleArrayMap.this.size--;
/* 222 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Int2DoubleMap.FastEntrySet int2DoubleEntrySet() {
/* 227 */     return new EntrySet();
/*     */   }
/*     */   private int findKey(int k) {
/* 230 */     int[] key = this.key;
/* 231 */     for (int i = this.size; i-- != 0;) {
/* 232 */       if (key[i] == k)
/* 233 */         return i; 
/* 234 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public double get(int k) {
/* 239 */     int[] key = this.key;
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
/*     */   public boolean containsKey(int k) {
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
/*     */   public double put(int k, double v) {
/* 271 */     int oldKey = findKey(k);
/* 272 */     if (oldKey != -1) {
/* 273 */       double oldValue = this.value[oldKey];
/* 274 */       this.value[oldKey] = v;
/* 275 */       return oldValue;
/*     */     } 
/* 277 */     if (this.size == this.key.length) {
/* 278 */       int[] newKey = new int[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public double remove(int k) {
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
/*     */   public IntSet keySet() {
/* 307 */     return new AbstractIntSet()
/*     */       {
/*     */         public boolean contains(int k) {
/* 310 */           return (Int2DoubleArrayMap.this.findKey(k) != -1);
/*     */         }
/*     */         
/*     */         public boolean remove(int k) {
/* 314 */           int oldPos = Int2DoubleArrayMap.this.findKey(k);
/* 315 */           if (oldPos == -1)
/* 316 */             return false; 
/* 317 */           int tail = Int2DoubleArrayMap.this.size - oldPos - 1;
/* 318 */           System.arraycopy(Int2DoubleArrayMap.this.key, oldPos + 1, Int2DoubleArrayMap.this.key, oldPos, tail);
/* 319 */           System.arraycopy(Int2DoubleArrayMap.this.value, oldPos + 1, Int2DoubleArrayMap.this.value, oldPos, tail);
/* 320 */           Int2DoubleArrayMap.this.size--;
/* 321 */           return true;
/*     */         }
/*     */         
/*     */         public IntIterator iterator() {
/* 325 */           return new IntIterator() {
/* 326 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 329 */                 return (this.pos < Int2DoubleArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public int nextInt() {
/* 334 */                 if (!hasNext())
/* 335 */                   throw new NoSuchElementException(); 
/* 336 */                 return Int2DoubleArrayMap.this.key[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 340 */                 if (this.pos == 0)
/* 341 */                   throw new IllegalStateException(); 
/* 342 */                 int tail = Int2DoubleArrayMap.this.size - this.pos;
/* 343 */                 System.arraycopy(Int2DoubleArrayMap.this.key, this.pos, Int2DoubleArrayMap.this.key, this.pos - 1, tail);
/* 344 */                 System.arraycopy(Int2DoubleArrayMap.this.value, this.pos, Int2DoubleArrayMap.this.value, this.pos - 1, tail);
/* 345 */                 Int2DoubleArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 351 */           return Int2DoubleArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 355 */           Int2DoubleArrayMap.this.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public DoubleCollection values() {
/* 361 */     return (DoubleCollection)new AbstractDoubleCollection()
/*     */       {
/*     */         public boolean contains(double v) {
/* 364 */           return Int2DoubleArrayMap.this.containsValue(v);
/*     */         }
/*     */         
/*     */         public DoubleIterator iterator() {
/* 368 */           return new DoubleIterator() {
/* 369 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 372 */                 return (this.pos < Int2DoubleArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public double nextDouble() {
/* 377 */                 if (!hasNext())
/* 378 */                   throw new NoSuchElementException(); 
/* 379 */                 return Int2DoubleArrayMap.this.value[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 383 */                 if (this.pos == 0)
/* 384 */                   throw new IllegalStateException(); 
/* 385 */                 int tail = Int2DoubleArrayMap.this.size - this.pos;
/* 386 */                 System.arraycopy(Int2DoubleArrayMap.this.key, this.pos, Int2DoubleArrayMap.this.key, this.pos - 1, tail);
/* 387 */                 System.arraycopy(Int2DoubleArrayMap.this.value, this.pos, Int2DoubleArrayMap.this.value, this.pos - 1, tail);
/* 388 */                 Int2DoubleArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 394 */           return Int2DoubleArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 398 */           Int2DoubleArrayMap.this.clear();
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
/*     */   public Int2DoubleArrayMap clone() {
/*     */     Int2DoubleArrayMap c;
/*     */     try {
/* 417 */       c = (Int2DoubleArrayMap)super.clone();
/* 418 */     } catch (CloneNotSupportedException cantHappen) {
/* 419 */       throw new InternalError();
/*     */     } 
/* 421 */     c.key = (int[])this.key.clone();
/* 422 */     c.value = (double[])this.value.clone();
/* 423 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 426 */     s.defaultWriteObject();
/* 427 */     for (int i = 0; i < this.size; i++) {
/* 428 */       s.writeInt(this.key[i]);
/* 429 */       s.writeDouble(this.value[i]);
/*     */     } 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 433 */     s.defaultReadObject();
/* 434 */     this.key = new int[this.size];
/* 435 */     this.value = new double[this.size];
/* 436 */     for (int i = 0; i < this.size; i++) {
/* 437 */       this.key[i] = s.readInt();
/* 438 */       this.value[i] = s.readDouble();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\Int2DoubleArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */