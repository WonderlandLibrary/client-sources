/*     */ package it.unimi.dsi.fastutil.shorts;
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
/*     */ public class Short2DoubleArrayMap
/*     */   extends AbstractShort2DoubleMap
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient short[] key;
/*     */   private transient double[] value;
/*     */   private int size;
/*     */   
/*     */   public Short2DoubleArrayMap(short[] key, double[] value) {
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
/*     */   public Short2DoubleArrayMap() {
/*  67 */     this.key = ShortArrays.EMPTY_ARRAY;
/*  68 */     this.value = DoubleArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Short2DoubleArrayMap(int capacity) {
/*  77 */     this.key = new short[capacity];
/*  78 */     this.value = new double[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Short2DoubleArrayMap(Short2DoubleMap m) {
/*  87 */     this(m.size());
/*  88 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Short2DoubleArrayMap(Map<? extends Short, ? extends Double> m) {
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
/*     */   public Short2DoubleArrayMap(short[] key, double[] value, int size) {
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
/*     */     extends AbstractObjectSet<Short2DoubleMap.Entry> implements Short2DoubleMap.FastEntrySet {
/*     */     public ObjectIterator<Short2DoubleMap.Entry> iterator() {
/* 130 */       return new ObjectIterator<Short2DoubleMap.Entry>() {
/* 131 */           int curr = -1; int next = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 134 */             return (this.next < Short2DoubleArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Short2DoubleMap.Entry next() {
/* 139 */             if (!hasNext())
/* 140 */               throw new NoSuchElementException(); 
/* 141 */             return new AbstractShort2DoubleMap.BasicEntry(Short2DoubleArrayMap.this.key[this.curr = this.next], Short2DoubleArrayMap.this.value[this.next++]);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 145 */             if (this.curr == -1)
/* 146 */               throw new IllegalStateException(); 
/* 147 */             this.curr = -1;
/* 148 */             int tail = Short2DoubleArrayMap.this.size-- - this.next--;
/* 149 */             System.arraycopy(Short2DoubleArrayMap.this.key, this.next + 1, Short2DoubleArrayMap.this.key, this.next, tail);
/* 150 */             System.arraycopy(Short2DoubleArrayMap.this.value, this.next + 1, Short2DoubleArrayMap.this.value, this.next, tail);
/*     */           }
/*     */         };
/*     */     }
/*     */     private EntrySet() {}
/*     */     public ObjectIterator<Short2DoubleMap.Entry> fastIterator() {
/* 156 */       return new ObjectIterator<Short2DoubleMap.Entry>() {
/* 157 */           int next = 0; int curr = -1;
/* 158 */           final AbstractShort2DoubleMap.BasicEntry entry = new AbstractShort2DoubleMap.BasicEntry();
/*     */           
/*     */           public boolean hasNext() {
/* 161 */             return (this.next < Short2DoubleArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Short2DoubleMap.Entry next() {
/* 166 */             if (!hasNext())
/* 167 */               throw new NoSuchElementException(); 
/* 168 */             this.entry.key = Short2DoubleArrayMap.this.key[this.curr = this.next];
/* 169 */             this.entry.value = Short2DoubleArrayMap.this.value[this.next++];
/* 170 */             return this.entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 174 */             if (this.curr == -1)
/* 175 */               throw new IllegalStateException(); 
/* 176 */             this.curr = -1;
/* 177 */             int tail = Short2DoubleArrayMap.this.size-- - this.next--;
/* 178 */             System.arraycopy(Short2DoubleArrayMap.this.key, this.next + 1, Short2DoubleArrayMap.this.key, this.next, tail);
/* 179 */             System.arraycopy(Short2DoubleArrayMap.this.value, this.next + 1, Short2DoubleArrayMap.this.value, this.next, tail);
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 185 */       return Short2DoubleArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 190 */       if (!(o instanceof Map.Entry))
/* 191 */         return false; 
/* 192 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 193 */       if (e.getKey() == null || !(e.getKey() instanceof Short))
/* 194 */         return false; 
/* 195 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/* 196 */         return false; 
/* 197 */       short k = ((Short)e.getKey()).shortValue();
/* 198 */       return (Short2DoubleArrayMap.this.containsKey(k) && 
/* 199 */         Double.doubleToLongBits(Short2DoubleArrayMap.this.get(k)) == 
/* 200 */         Double.doubleToLongBits(((Double)e.getValue()).doubleValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 205 */       if (!(o instanceof Map.Entry))
/* 206 */         return false; 
/* 207 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 208 */       if (e.getKey() == null || !(e.getKey() instanceof Short))
/* 209 */         return false; 
/* 210 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/* 211 */         return false; 
/* 212 */       short k = ((Short)e.getKey()).shortValue();
/* 213 */       double v = ((Double)e.getValue()).doubleValue();
/* 214 */       int oldPos = Short2DoubleArrayMap.this.findKey(k);
/* 215 */       if (oldPos == -1 || Double.doubleToLongBits(v) != 
/* 216 */         Double.doubleToLongBits(Short2DoubleArrayMap.this.value[oldPos]))
/* 217 */         return false; 
/* 218 */       int tail = Short2DoubleArrayMap.this.size - oldPos - 1;
/* 219 */       System.arraycopy(Short2DoubleArrayMap.this.key, oldPos + 1, Short2DoubleArrayMap.this.key, oldPos, tail);
/* 220 */       System.arraycopy(Short2DoubleArrayMap.this.value, oldPos + 1, Short2DoubleArrayMap.this.value, oldPos, tail);
/*     */       
/* 222 */       Short2DoubleArrayMap.this.size--;
/* 223 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Short2DoubleMap.FastEntrySet short2DoubleEntrySet() {
/* 228 */     return new EntrySet();
/*     */   }
/*     */   private int findKey(short k) {
/* 231 */     short[] key = this.key;
/* 232 */     for (int i = this.size; i-- != 0;) {
/* 233 */       if (key[i] == k)
/* 234 */         return i; 
/* 235 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public double get(short k) {
/* 240 */     short[] key = this.key;
/* 241 */     for (int i = this.size; i-- != 0;) {
/* 242 */       if (key[i] == k)
/* 243 */         return this.value[i]; 
/* 244 */     }  return this.defRetValue;
/*     */   }
/*     */   
/*     */   public int size() {
/* 248 */     return this.size;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 252 */     this.size = 0;
/*     */   }
/*     */   
/*     */   public boolean containsKey(short k) {
/* 256 */     return (findKey(k) != -1);
/*     */   }
/*     */   
/*     */   public boolean containsValue(double v) {
/* 260 */     for (int i = this.size; i-- != 0;) {
/* 261 */       if (Double.doubleToLongBits(this.value[i]) == Double.doubleToLongBits(v))
/* 262 */         return true; 
/* 263 */     }  return false;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 267 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public double put(short k, double v) {
/* 272 */     int oldKey = findKey(k);
/* 273 */     if (oldKey != -1) {
/* 274 */       double oldValue = this.value[oldKey];
/* 275 */       this.value[oldKey] = v;
/* 276 */       return oldValue;
/*     */     } 
/* 278 */     if (this.size == this.key.length) {
/* 279 */       short[] newKey = new short[(this.size == 0) ? 2 : (this.size * 2)];
/* 280 */       double[] newValue = new double[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public double remove(short k) {
/* 296 */     int oldPos = findKey(k);
/* 297 */     if (oldPos == -1)
/* 298 */       return this.defRetValue; 
/* 299 */     double oldValue = this.value[oldPos];
/* 300 */     int tail = this.size - oldPos - 1;
/* 301 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 302 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 303 */     this.size--;
/* 304 */     return oldValue;
/*     */   }
/*     */   
/*     */   public ShortSet keySet() {
/* 308 */     return new AbstractShortSet()
/*     */       {
/*     */         public boolean contains(short k) {
/* 311 */           return (Short2DoubleArrayMap.this.findKey(k) != -1);
/*     */         }
/*     */         
/*     */         public boolean remove(short k) {
/* 315 */           int oldPos = Short2DoubleArrayMap.this.findKey(k);
/* 316 */           if (oldPos == -1)
/* 317 */             return false; 
/* 318 */           int tail = Short2DoubleArrayMap.this.size - oldPos - 1;
/* 319 */           System.arraycopy(Short2DoubleArrayMap.this.key, oldPos + 1, Short2DoubleArrayMap.this.key, oldPos, tail);
/* 320 */           System.arraycopy(Short2DoubleArrayMap.this.value, oldPos + 1, Short2DoubleArrayMap.this.value, oldPos, tail);
/* 321 */           Short2DoubleArrayMap.this.size--;
/* 322 */           return true;
/*     */         }
/*     */         
/*     */         public ShortIterator iterator() {
/* 326 */           return new ShortIterator() {
/* 327 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 330 */                 return (this.pos < Short2DoubleArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public short nextShort() {
/* 335 */                 if (!hasNext())
/* 336 */                   throw new NoSuchElementException(); 
/* 337 */                 return Short2DoubleArrayMap.this.key[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 341 */                 if (this.pos == 0)
/* 342 */                   throw new IllegalStateException(); 
/* 343 */                 int tail = Short2DoubleArrayMap.this.size - this.pos;
/* 344 */                 System.arraycopy(Short2DoubleArrayMap.this.key, this.pos, Short2DoubleArrayMap.this.key, this.pos - 1, tail);
/* 345 */                 System.arraycopy(Short2DoubleArrayMap.this.value, this.pos, Short2DoubleArrayMap.this.value, this.pos - 1, tail);
/* 346 */                 Short2DoubleArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 352 */           return Short2DoubleArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 356 */           Short2DoubleArrayMap.this.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public DoubleCollection values() {
/* 362 */     return (DoubleCollection)new AbstractDoubleCollection()
/*     */       {
/*     */         public boolean contains(double v) {
/* 365 */           return Short2DoubleArrayMap.this.containsValue(v);
/*     */         }
/*     */         
/*     */         public DoubleIterator iterator() {
/* 369 */           return new DoubleIterator() {
/* 370 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 373 */                 return (this.pos < Short2DoubleArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public double nextDouble() {
/* 378 */                 if (!hasNext())
/* 379 */                   throw new NoSuchElementException(); 
/* 380 */                 return Short2DoubleArrayMap.this.value[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 384 */                 if (this.pos == 0)
/* 385 */                   throw new IllegalStateException(); 
/* 386 */                 int tail = Short2DoubleArrayMap.this.size - this.pos;
/* 387 */                 System.arraycopy(Short2DoubleArrayMap.this.key, this.pos, Short2DoubleArrayMap.this.key, this.pos - 1, tail);
/* 388 */                 System.arraycopy(Short2DoubleArrayMap.this.value, this.pos, Short2DoubleArrayMap.this.value, this.pos - 1, tail);
/* 389 */                 Short2DoubleArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 395 */           return Short2DoubleArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 399 */           Short2DoubleArrayMap.this.clear();
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
/*     */   public Short2DoubleArrayMap clone() {
/*     */     Short2DoubleArrayMap c;
/*     */     try {
/* 418 */       c = (Short2DoubleArrayMap)super.clone();
/* 419 */     } catch (CloneNotSupportedException cantHappen) {
/* 420 */       throw new InternalError();
/*     */     } 
/* 422 */     c.key = (short[])this.key.clone();
/* 423 */     c.value = (double[])this.value.clone();
/* 424 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 427 */     s.defaultWriteObject();
/* 428 */     for (int i = 0; i < this.size; i++) {
/* 429 */       s.writeShort(this.key[i]);
/* 430 */       s.writeDouble(this.value[i]);
/*     */     } 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 434 */     s.defaultReadObject();
/* 435 */     this.key = new short[this.size];
/* 436 */     this.value = new double[this.size];
/* 437 */     for (int i = 0; i < this.size; i++) {
/* 438 */       this.key[i] = s.readShort();
/* 439 */       this.value[i] = s.readDouble();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\Short2DoubleArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */