/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
/*     */ import it.unimi.dsi.fastutil.floats.FloatArrays;
/*     */ import it.unimi.dsi.fastutil.floats.FloatCollection;
/*     */ import it.unimi.dsi.fastutil.floats.FloatIterator;
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
/*     */ public class Double2FloatArrayMap
/*     */   extends AbstractDouble2FloatMap
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient double[] key;
/*     */   private transient float[] value;
/*     */   private int size;
/*     */   
/*     */   public Double2FloatArrayMap(double[] key, float[] value) {
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
/*     */   public Double2FloatArrayMap() {
/*  67 */     this.key = DoubleArrays.EMPTY_ARRAY;
/*  68 */     this.value = FloatArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Double2FloatArrayMap(int capacity) {
/*  77 */     this.key = new double[capacity];
/*  78 */     this.value = new float[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Double2FloatArrayMap(Double2FloatMap m) {
/*  87 */     this(m.size());
/*  88 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Double2FloatArrayMap(Map<? extends Double, ? extends Float> m) {
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
/*     */   public Double2FloatArrayMap(double[] key, float[] value, int size) {
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
/*     */     extends AbstractObjectSet<Double2FloatMap.Entry> implements Double2FloatMap.FastEntrySet {
/*     */     public ObjectIterator<Double2FloatMap.Entry> iterator() {
/* 130 */       return new ObjectIterator<Double2FloatMap.Entry>() {
/* 131 */           int curr = -1; int next = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 134 */             return (this.next < Double2FloatArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Double2FloatMap.Entry next() {
/* 139 */             if (!hasNext())
/* 140 */               throw new NoSuchElementException(); 
/* 141 */             return new AbstractDouble2FloatMap.BasicEntry(Double2FloatArrayMap.this.key[this.curr = this.next], Double2FloatArrayMap.this.value[this.next++]);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 145 */             if (this.curr == -1)
/* 146 */               throw new IllegalStateException(); 
/* 147 */             this.curr = -1;
/* 148 */             int tail = Double2FloatArrayMap.this.size-- - this.next--;
/* 149 */             System.arraycopy(Double2FloatArrayMap.this.key, this.next + 1, Double2FloatArrayMap.this.key, this.next, tail);
/* 150 */             System.arraycopy(Double2FloatArrayMap.this.value, this.next + 1, Double2FloatArrayMap.this.value, this.next, tail);
/*     */           }
/*     */         };
/*     */     }
/*     */     private EntrySet() {}
/*     */     public ObjectIterator<Double2FloatMap.Entry> fastIterator() {
/* 156 */       return new ObjectIterator<Double2FloatMap.Entry>() {
/* 157 */           int next = 0; int curr = -1;
/* 158 */           final AbstractDouble2FloatMap.BasicEntry entry = new AbstractDouble2FloatMap.BasicEntry();
/*     */           
/*     */           public boolean hasNext() {
/* 161 */             return (this.next < Double2FloatArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Double2FloatMap.Entry next() {
/* 166 */             if (!hasNext())
/* 167 */               throw new NoSuchElementException(); 
/* 168 */             this.entry.key = Double2FloatArrayMap.this.key[this.curr = this.next];
/* 169 */             this.entry.value = Double2FloatArrayMap.this.value[this.next++];
/* 170 */             return this.entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 174 */             if (this.curr == -1)
/* 175 */               throw new IllegalStateException(); 
/* 176 */             this.curr = -1;
/* 177 */             int tail = Double2FloatArrayMap.this.size-- - this.next--;
/* 178 */             System.arraycopy(Double2FloatArrayMap.this.key, this.next + 1, Double2FloatArrayMap.this.key, this.next, tail);
/* 179 */             System.arraycopy(Double2FloatArrayMap.this.value, this.next + 1, Double2FloatArrayMap.this.value, this.next, tail);
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 185 */       return Double2FloatArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 190 */       if (!(o instanceof Map.Entry))
/* 191 */         return false; 
/* 192 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 193 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 194 */         return false; 
/* 195 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 196 */         return false; 
/* 197 */       double k = ((Double)e.getKey()).doubleValue();
/* 198 */       return (Double2FloatArrayMap.this.containsKey(k) && 
/* 199 */         Float.floatToIntBits(Double2FloatArrayMap.this.get(k)) == 
/* 200 */         Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 205 */       if (!(o instanceof Map.Entry))
/* 206 */         return false; 
/* 207 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 208 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 209 */         return false; 
/* 210 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 211 */         return false; 
/* 212 */       double k = ((Double)e.getKey()).doubleValue();
/* 213 */       float v = ((Float)e.getValue()).floatValue();
/* 214 */       int oldPos = Double2FloatArrayMap.this.findKey(k);
/* 215 */       if (oldPos == -1 || 
/* 216 */         Float.floatToIntBits(v) != Float.floatToIntBits(Double2FloatArrayMap.this.value[oldPos]))
/* 217 */         return false; 
/* 218 */       int tail = Double2FloatArrayMap.this.size - oldPos - 1;
/* 219 */       System.arraycopy(Double2FloatArrayMap.this.key, oldPos + 1, Double2FloatArrayMap.this.key, oldPos, tail);
/* 220 */       System.arraycopy(Double2FloatArrayMap.this.value, oldPos + 1, Double2FloatArrayMap.this.value, oldPos, tail);
/*     */       
/* 222 */       Double2FloatArrayMap.this.size--;
/* 223 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Double2FloatMap.FastEntrySet double2FloatEntrySet() {
/* 228 */     return new EntrySet();
/*     */   }
/*     */   private int findKey(double k) {
/* 231 */     double[] key = this.key;
/* 232 */     for (int i = this.size; i-- != 0;) {
/* 233 */       if (Double.doubleToLongBits(key[i]) == Double.doubleToLongBits(k))
/* 234 */         return i; 
/* 235 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public float get(double k) {
/* 240 */     double[] key = this.key;
/* 241 */     for (int i = this.size; i-- != 0;) {
/* 242 */       if (Double.doubleToLongBits(key[i]) == Double.doubleToLongBits(k))
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
/*     */   public boolean containsKey(double k) {
/* 256 */     return (findKey(k) != -1);
/*     */   }
/*     */   
/*     */   public boolean containsValue(float v) {
/* 260 */     for (int i = this.size; i-- != 0;) {
/* 261 */       if (Float.floatToIntBits(this.value[i]) == Float.floatToIntBits(v))
/* 262 */         return true; 
/* 263 */     }  return false;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 267 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public float put(double k, float v) {
/* 272 */     int oldKey = findKey(k);
/* 273 */     if (oldKey != -1) {
/* 274 */       float oldValue = this.value[oldKey];
/* 275 */       this.value[oldKey] = v;
/* 276 */       return oldValue;
/*     */     } 
/* 278 */     if (this.size == this.key.length) {
/* 279 */       double[] newKey = new double[(this.size == 0) ? 2 : (this.size * 2)];
/* 280 */       float[] newValue = new float[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public float remove(double k) {
/* 296 */     int oldPos = findKey(k);
/* 297 */     if (oldPos == -1)
/* 298 */       return this.defRetValue; 
/* 299 */     float oldValue = this.value[oldPos];
/* 300 */     int tail = this.size - oldPos - 1;
/* 301 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 302 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 303 */     this.size--;
/* 304 */     return oldValue;
/*     */   }
/*     */   
/*     */   public DoubleSet keySet() {
/* 308 */     return new AbstractDoubleSet()
/*     */       {
/*     */         public boolean contains(double k) {
/* 311 */           return (Double2FloatArrayMap.this.findKey(k) != -1);
/*     */         }
/*     */         
/*     */         public boolean remove(double k) {
/* 315 */           int oldPos = Double2FloatArrayMap.this.findKey(k);
/* 316 */           if (oldPos == -1)
/* 317 */             return false; 
/* 318 */           int tail = Double2FloatArrayMap.this.size - oldPos - 1;
/* 319 */           System.arraycopy(Double2FloatArrayMap.this.key, oldPos + 1, Double2FloatArrayMap.this.key, oldPos, tail);
/* 320 */           System.arraycopy(Double2FloatArrayMap.this.value, oldPos + 1, Double2FloatArrayMap.this.value, oldPos, tail);
/* 321 */           Double2FloatArrayMap.this.size--;
/* 322 */           return true;
/*     */         }
/*     */         
/*     */         public DoubleIterator iterator() {
/* 326 */           return new DoubleIterator() {
/* 327 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 330 */                 return (this.pos < Double2FloatArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public double nextDouble() {
/* 335 */                 if (!hasNext())
/* 336 */                   throw new NoSuchElementException(); 
/* 337 */                 return Double2FloatArrayMap.this.key[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 341 */                 if (this.pos == 0)
/* 342 */                   throw new IllegalStateException(); 
/* 343 */                 int tail = Double2FloatArrayMap.this.size - this.pos;
/* 344 */                 System.arraycopy(Double2FloatArrayMap.this.key, this.pos, Double2FloatArrayMap.this.key, this.pos - 1, tail);
/* 345 */                 System.arraycopy(Double2FloatArrayMap.this.value, this.pos, Double2FloatArrayMap.this.value, this.pos - 1, tail);
/* 346 */                 Double2FloatArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 352 */           return Double2FloatArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 356 */           Double2FloatArrayMap.this.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public FloatCollection values() {
/* 362 */     return (FloatCollection)new AbstractFloatCollection()
/*     */       {
/*     */         public boolean contains(float v) {
/* 365 */           return Double2FloatArrayMap.this.containsValue(v);
/*     */         }
/*     */         
/*     */         public FloatIterator iterator() {
/* 369 */           return new FloatIterator() {
/* 370 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 373 */                 return (this.pos < Double2FloatArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public float nextFloat() {
/* 378 */                 if (!hasNext())
/* 379 */                   throw new NoSuchElementException(); 
/* 380 */                 return Double2FloatArrayMap.this.value[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 384 */                 if (this.pos == 0)
/* 385 */                   throw new IllegalStateException(); 
/* 386 */                 int tail = Double2FloatArrayMap.this.size - this.pos;
/* 387 */                 System.arraycopy(Double2FloatArrayMap.this.key, this.pos, Double2FloatArrayMap.this.key, this.pos - 1, tail);
/* 388 */                 System.arraycopy(Double2FloatArrayMap.this.value, this.pos, Double2FloatArrayMap.this.value, this.pos - 1, tail);
/* 389 */                 Double2FloatArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 395 */           return Double2FloatArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 399 */           Double2FloatArrayMap.this.clear();
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
/*     */   public Double2FloatArrayMap clone() {
/*     */     Double2FloatArrayMap c;
/*     */     try {
/* 418 */       c = (Double2FloatArrayMap)super.clone();
/* 419 */     } catch (CloneNotSupportedException cantHappen) {
/* 420 */       throw new InternalError();
/*     */     } 
/* 422 */     c.key = (double[])this.key.clone();
/* 423 */     c.value = (float[])this.value.clone();
/* 424 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 427 */     s.defaultWriteObject();
/* 428 */     for (int i = 0; i < this.size; i++) {
/* 429 */       s.writeDouble(this.key[i]);
/* 430 */       s.writeFloat(this.value[i]);
/*     */     } 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 434 */     s.defaultReadObject();
/* 435 */     this.key = new double[this.size];
/* 436 */     this.value = new float[this.size];
/* 437 */     for (int i = 0; i < this.size; i++) {
/* 438 */       this.key[i] = s.readDouble();
/* 439 */       this.value[i] = s.readFloat();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2FloatArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */