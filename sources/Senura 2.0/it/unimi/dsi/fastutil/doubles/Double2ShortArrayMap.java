/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
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
/*     */ public class Double2ShortArrayMap
/*     */   extends AbstractDouble2ShortMap
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient double[] key;
/*     */   private transient short[] value;
/*     */   private int size;
/*     */   
/*     */   public Double2ShortArrayMap(double[] key, short[] value) {
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
/*     */   public Double2ShortArrayMap() {
/*  67 */     this.key = DoubleArrays.EMPTY_ARRAY;
/*  68 */     this.value = ShortArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Double2ShortArrayMap(int capacity) {
/*  77 */     this.key = new double[capacity];
/*  78 */     this.value = new short[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Double2ShortArrayMap(Double2ShortMap m) {
/*  87 */     this(m.size());
/*  88 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Double2ShortArrayMap(Map<? extends Double, ? extends Short> m) {
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
/*     */   public Double2ShortArrayMap(double[] key, short[] value, int size) {
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
/*     */     extends AbstractObjectSet<Double2ShortMap.Entry> implements Double2ShortMap.FastEntrySet {
/*     */     public ObjectIterator<Double2ShortMap.Entry> iterator() {
/* 130 */       return new ObjectIterator<Double2ShortMap.Entry>() {
/* 131 */           int curr = -1; int next = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 134 */             return (this.next < Double2ShortArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Double2ShortMap.Entry next() {
/* 139 */             if (!hasNext())
/* 140 */               throw new NoSuchElementException(); 
/* 141 */             return new AbstractDouble2ShortMap.BasicEntry(Double2ShortArrayMap.this.key[this.curr = this.next], Double2ShortArrayMap.this.value[this.next++]);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 145 */             if (this.curr == -1)
/* 146 */               throw new IllegalStateException(); 
/* 147 */             this.curr = -1;
/* 148 */             int tail = Double2ShortArrayMap.this.size-- - this.next--;
/* 149 */             System.arraycopy(Double2ShortArrayMap.this.key, this.next + 1, Double2ShortArrayMap.this.key, this.next, tail);
/* 150 */             System.arraycopy(Double2ShortArrayMap.this.value, this.next + 1, Double2ShortArrayMap.this.value, this.next, tail);
/*     */           }
/*     */         };
/*     */     }
/*     */     private EntrySet() {}
/*     */     public ObjectIterator<Double2ShortMap.Entry> fastIterator() {
/* 156 */       return new ObjectIterator<Double2ShortMap.Entry>() {
/* 157 */           int next = 0; int curr = -1;
/* 158 */           final AbstractDouble2ShortMap.BasicEntry entry = new AbstractDouble2ShortMap.BasicEntry();
/*     */           
/*     */           public boolean hasNext() {
/* 161 */             return (this.next < Double2ShortArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Double2ShortMap.Entry next() {
/* 166 */             if (!hasNext())
/* 167 */               throw new NoSuchElementException(); 
/* 168 */             this.entry.key = Double2ShortArrayMap.this.key[this.curr = this.next];
/* 169 */             this.entry.value = Double2ShortArrayMap.this.value[this.next++];
/* 170 */             return this.entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 174 */             if (this.curr == -1)
/* 175 */               throw new IllegalStateException(); 
/* 176 */             this.curr = -1;
/* 177 */             int tail = Double2ShortArrayMap.this.size-- - this.next--;
/* 178 */             System.arraycopy(Double2ShortArrayMap.this.key, this.next + 1, Double2ShortArrayMap.this.key, this.next, tail);
/* 179 */             System.arraycopy(Double2ShortArrayMap.this.value, this.next + 1, Double2ShortArrayMap.this.value, this.next, tail);
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 185 */       return Double2ShortArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 190 */       if (!(o instanceof Map.Entry))
/* 191 */         return false; 
/* 192 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 193 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 194 */         return false; 
/* 195 */       if (e.getValue() == null || !(e.getValue() instanceof Short))
/* 196 */         return false; 
/* 197 */       double k = ((Double)e.getKey()).doubleValue();
/* 198 */       return (Double2ShortArrayMap.this.containsKey(k) && Double2ShortArrayMap.this
/* 199 */         .get(k) == ((Short)e.getValue()).shortValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 204 */       if (!(o instanceof Map.Entry))
/* 205 */         return false; 
/* 206 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 207 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 208 */         return false; 
/* 209 */       if (e.getValue() == null || !(e.getValue() instanceof Short))
/* 210 */         return false; 
/* 211 */       double k = ((Double)e.getKey()).doubleValue();
/* 212 */       short v = ((Short)e.getValue()).shortValue();
/* 213 */       int oldPos = Double2ShortArrayMap.this.findKey(k);
/* 214 */       if (oldPos == -1 || v != Double2ShortArrayMap.this.value[oldPos])
/* 215 */         return false; 
/* 216 */       int tail = Double2ShortArrayMap.this.size - oldPos - 1;
/* 217 */       System.arraycopy(Double2ShortArrayMap.this.key, oldPos + 1, Double2ShortArrayMap.this.key, oldPos, tail);
/* 218 */       System.arraycopy(Double2ShortArrayMap.this.value, oldPos + 1, Double2ShortArrayMap.this.value, oldPos, tail);
/*     */       
/* 220 */       Double2ShortArrayMap.this.size--;
/* 221 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Double2ShortMap.FastEntrySet double2ShortEntrySet() {
/* 226 */     return new EntrySet();
/*     */   }
/*     */   private int findKey(double k) {
/* 229 */     double[] key = this.key;
/* 230 */     for (int i = this.size; i-- != 0;) {
/* 231 */       if (Double.doubleToLongBits(key[i]) == Double.doubleToLongBits(k))
/* 232 */         return i; 
/* 233 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public short get(double k) {
/* 238 */     double[] key = this.key;
/* 239 */     for (int i = this.size; i-- != 0;) {
/* 240 */       if (Double.doubleToLongBits(key[i]) == Double.doubleToLongBits(k))
/* 241 */         return this.value[i]; 
/* 242 */     }  return this.defRetValue;
/*     */   }
/*     */   
/*     */   public int size() {
/* 246 */     return this.size;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 250 */     this.size = 0;
/*     */   }
/*     */   
/*     */   public boolean containsKey(double k) {
/* 254 */     return (findKey(k) != -1);
/*     */   }
/*     */   
/*     */   public boolean containsValue(short v) {
/* 258 */     for (int i = this.size; i-- != 0;) {
/* 259 */       if (this.value[i] == v)
/* 260 */         return true; 
/* 261 */     }  return false;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 265 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public short put(double k, short v) {
/* 270 */     int oldKey = findKey(k);
/* 271 */     if (oldKey != -1) {
/* 272 */       short oldValue = this.value[oldKey];
/* 273 */       this.value[oldKey] = v;
/* 274 */       return oldValue;
/*     */     } 
/* 276 */     if (this.size == this.key.length) {
/* 277 */       double[] newKey = new double[(this.size == 0) ? 2 : (this.size * 2)];
/* 278 */       short[] newValue = new short[(this.size == 0) ? 2 : (this.size * 2)];
/* 279 */       for (int i = this.size; i-- != 0; ) {
/* 280 */         newKey[i] = this.key[i];
/* 281 */         newValue[i] = this.value[i];
/*     */       } 
/* 283 */       this.key = newKey;
/* 284 */       this.value = newValue;
/*     */     } 
/* 286 */     this.key[this.size] = k;
/* 287 */     this.value[this.size] = v;
/* 288 */     this.size++;
/* 289 */     return this.defRetValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public short remove(double k) {
/* 294 */     int oldPos = findKey(k);
/* 295 */     if (oldPos == -1)
/* 296 */       return this.defRetValue; 
/* 297 */     short oldValue = this.value[oldPos];
/* 298 */     int tail = this.size - oldPos - 1;
/* 299 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 300 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 301 */     this.size--;
/* 302 */     return oldValue;
/*     */   }
/*     */   
/*     */   public DoubleSet keySet() {
/* 306 */     return new AbstractDoubleSet()
/*     */       {
/*     */         public boolean contains(double k) {
/* 309 */           return (Double2ShortArrayMap.this.findKey(k) != -1);
/*     */         }
/*     */         
/*     */         public boolean remove(double k) {
/* 313 */           int oldPos = Double2ShortArrayMap.this.findKey(k);
/* 314 */           if (oldPos == -1)
/* 315 */             return false; 
/* 316 */           int tail = Double2ShortArrayMap.this.size - oldPos - 1;
/* 317 */           System.arraycopy(Double2ShortArrayMap.this.key, oldPos + 1, Double2ShortArrayMap.this.key, oldPos, tail);
/* 318 */           System.arraycopy(Double2ShortArrayMap.this.value, oldPos + 1, Double2ShortArrayMap.this.value, oldPos, tail);
/* 319 */           Double2ShortArrayMap.this.size--;
/* 320 */           return true;
/*     */         }
/*     */         
/*     */         public DoubleIterator iterator() {
/* 324 */           return new DoubleIterator() {
/* 325 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 328 */                 return (this.pos < Double2ShortArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public double nextDouble() {
/* 333 */                 if (!hasNext())
/* 334 */                   throw new NoSuchElementException(); 
/* 335 */                 return Double2ShortArrayMap.this.key[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 339 */                 if (this.pos == 0)
/* 340 */                   throw new IllegalStateException(); 
/* 341 */                 int tail = Double2ShortArrayMap.this.size - this.pos;
/* 342 */                 System.arraycopy(Double2ShortArrayMap.this.key, this.pos, Double2ShortArrayMap.this.key, this.pos - 1, tail);
/* 343 */                 System.arraycopy(Double2ShortArrayMap.this.value, this.pos, Double2ShortArrayMap.this.value, this.pos - 1, tail);
/* 344 */                 Double2ShortArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 350 */           return Double2ShortArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 354 */           Double2ShortArrayMap.this.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public ShortCollection values() {
/* 360 */     return (ShortCollection)new AbstractShortCollection()
/*     */       {
/*     */         public boolean contains(short v) {
/* 363 */           return Double2ShortArrayMap.this.containsValue(v);
/*     */         }
/*     */         
/*     */         public ShortIterator iterator() {
/* 367 */           return new ShortIterator() {
/* 368 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 371 */                 return (this.pos < Double2ShortArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public short nextShort() {
/* 376 */                 if (!hasNext())
/* 377 */                   throw new NoSuchElementException(); 
/* 378 */                 return Double2ShortArrayMap.this.value[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 382 */                 if (this.pos == 0)
/* 383 */                   throw new IllegalStateException(); 
/* 384 */                 int tail = Double2ShortArrayMap.this.size - this.pos;
/* 385 */                 System.arraycopy(Double2ShortArrayMap.this.key, this.pos, Double2ShortArrayMap.this.key, this.pos - 1, tail);
/* 386 */                 System.arraycopy(Double2ShortArrayMap.this.value, this.pos, Double2ShortArrayMap.this.value, this.pos - 1, tail);
/* 387 */                 Double2ShortArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 393 */           return Double2ShortArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 397 */           Double2ShortArrayMap.this.clear();
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
/*     */   public Double2ShortArrayMap clone() {
/*     */     Double2ShortArrayMap c;
/*     */     try {
/* 416 */       c = (Double2ShortArrayMap)super.clone();
/* 417 */     } catch (CloneNotSupportedException cantHappen) {
/* 418 */       throw new InternalError();
/*     */     } 
/* 420 */     c.key = (double[])this.key.clone();
/* 421 */     c.value = (short[])this.value.clone();
/* 422 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 425 */     s.defaultWriteObject();
/* 426 */     for (int i = 0; i < this.size; i++) {
/* 427 */       s.writeDouble(this.key[i]);
/* 428 */       s.writeShort(this.value[i]);
/*     */     } 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 432 */     s.defaultReadObject();
/* 433 */     this.key = new double[this.size];
/* 434 */     this.value = new short[this.size];
/* 435 */     for (int i = 0; i < this.size; i++) {
/* 436 */       this.key[i] = s.readDouble();
/* 437 */       this.value[i] = s.readShort();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2ShortArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */