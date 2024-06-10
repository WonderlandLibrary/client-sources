/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanArrays;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
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
/*     */ public class Double2BooleanArrayMap
/*     */   extends AbstractDouble2BooleanMap
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient double[] key;
/*     */   private transient boolean[] value;
/*     */   private int size;
/*     */   
/*     */   public Double2BooleanArrayMap(double[] key, boolean[] value) {
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
/*     */   public Double2BooleanArrayMap() {
/*  67 */     this.key = DoubleArrays.EMPTY_ARRAY;
/*  68 */     this.value = BooleanArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Double2BooleanArrayMap(int capacity) {
/*  77 */     this.key = new double[capacity];
/*  78 */     this.value = new boolean[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Double2BooleanArrayMap(Double2BooleanMap m) {
/*  87 */     this(m.size());
/*  88 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Double2BooleanArrayMap(Map<? extends Double, ? extends Boolean> m) {
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
/*     */   public Double2BooleanArrayMap(double[] key, boolean[] value, int size) {
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
/*     */     extends AbstractObjectSet<Double2BooleanMap.Entry> implements Double2BooleanMap.FastEntrySet {
/*     */     public ObjectIterator<Double2BooleanMap.Entry> iterator() {
/* 130 */       return new ObjectIterator<Double2BooleanMap.Entry>() {
/* 131 */           int curr = -1; int next = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 134 */             return (this.next < Double2BooleanArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Double2BooleanMap.Entry next() {
/* 139 */             if (!hasNext())
/* 140 */               throw new NoSuchElementException(); 
/* 141 */             return new AbstractDouble2BooleanMap.BasicEntry(Double2BooleanArrayMap.this.key[this.curr = this.next], Double2BooleanArrayMap.this.value[this.next++]);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 145 */             if (this.curr == -1)
/* 146 */               throw new IllegalStateException(); 
/* 147 */             this.curr = -1;
/* 148 */             int tail = Double2BooleanArrayMap.this.size-- - this.next--;
/* 149 */             System.arraycopy(Double2BooleanArrayMap.this.key, this.next + 1, Double2BooleanArrayMap.this.key, this.next, tail);
/* 150 */             System.arraycopy(Double2BooleanArrayMap.this.value, this.next + 1, Double2BooleanArrayMap.this.value, this.next, tail);
/*     */           }
/*     */         };
/*     */     }
/*     */     private EntrySet() {}
/*     */     public ObjectIterator<Double2BooleanMap.Entry> fastIterator() {
/* 156 */       return new ObjectIterator<Double2BooleanMap.Entry>() {
/* 157 */           int next = 0; int curr = -1;
/* 158 */           final AbstractDouble2BooleanMap.BasicEntry entry = new AbstractDouble2BooleanMap.BasicEntry();
/*     */           
/*     */           public boolean hasNext() {
/* 161 */             return (this.next < Double2BooleanArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Double2BooleanMap.Entry next() {
/* 166 */             if (!hasNext())
/* 167 */               throw new NoSuchElementException(); 
/* 168 */             this.entry.key = Double2BooleanArrayMap.this.key[this.curr = this.next];
/* 169 */             this.entry.value = Double2BooleanArrayMap.this.value[this.next++];
/* 170 */             return this.entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 174 */             if (this.curr == -1)
/* 175 */               throw new IllegalStateException(); 
/* 176 */             this.curr = -1;
/* 177 */             int tail = Double2BooleanArrayMap.this.size-- - this.next--;
/* 178 */             System.arraycopy(Double2BooleanArrayMap.this.key, this.next + 1, Double2BooleanArrayMap.this.key, this.next, tail);
/* 179 */             System.arraycopy(Double2BooleanArrayMap.this.value, this.next + 1, Double2BooleanArrayMap.this.value, this.next, tail);
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 185 */       return Double2BooleanArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 190 */       if (!(o instanceof Map.Entry))
/* 191 */         return false; 
/* 192 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 193 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 194 */         return false; 
/* 195 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 196 */         return false; 
/* 197 */       double k = ((Double)e.getKey()).doubleValue();
/* 198 */       return (Double2BooleanArrayMap.this.containsKey(k) && Double2BooleanArrayMap.this
/* 199 */         .get(k) == ((Boolean)e.getValue()).booleanValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 204 */       if (!(o instanceof Map.Entry))
/* 205 */         return false; 
/* 206 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 207 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 208 */         return false; 
/* 209 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 210 */         return false; 
/* 211 */       double k = ((Double)e.getKey()).doubleValue();
/* 212 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/* 213 */       int oldPos = Double2BooleanArrayMap.this.findKey(k);
/* 214 */       if (oldPos == -1 || v != Double2BooleanArrayMap.this.value[oldPos])
/* 215 */         return false; 
/* 216 */       int tail = Double2BooleanArrayMap.this.size - oldPos - 1;
/* 217 */       System.arraycopy(Double2BooleanArrayMap.this.key, oldPos + 1, Double2BooleanArrayMap.this.key, oldPos, tail);
/*     */       
/* 219 */       System.arraycopy(Double2BooleanArrayMap.this.value, oldPos + 1, Double2BooleanArrayMap.this.value, oldPos, tail);
/*     */       
/* 221 */       Double2BooleanArrayMap.this.size--;
/* 222 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Double2BooleanMap.FastEntrySet double2BooleanEntrySet() {
/* 227 */     return new EntrySet();
/*     */   }
/*     */   private int findKey(double k) {
/* 230 */     double[] key = this.key;
/* 231 */     for (int i = this.size; i-- != 0;) {
/* 232 */       if (Double.doubleToLongBits(key[i]) == Double.doubleToLongBits(k))
/* 233 */         return i; 
/* 234 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean get(double k) {
/* 239 */     double[] key = this.key;
/* 240 */     for (int i = this.size; i-- != 0;) {
/* 241 */       if (Double.doubleToLongBits(key[i]) == Double.doubleToLongBits(k))
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
/*     */   public boolean containsKey(double k) {
/* 255 */     return (findKey(k) != -1);
/*     */   }
/*     */   
/*     */   public boolean containsValue(boolean v) {
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
/*     */   public boolean put(double k, boolean v) {
/* 271 */     int oldKey = findKey(k);
/* 272 */     if (oldKey != -1) {
/* 273 */       boolean oldValue = this.value[oldKey];
/* 274 */       this.value[oldKey] = v;
/* 275 */       return oldValue;
/*     */     } 
/* 277 */     if (this.size == this.key.length) {
/* 278 */       double[] newKey = new double[(this.size == 0) ? 2 : (this.size * 2)];
/* 279 */       boolean[] newValue = new boolean[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public boolean remove(double k) {
/* 295 */     int oldPos = findKey(k);
/* 296 */     if (oldPos == -1)
/* 297 */       return this.defRetValue; 
/* 298 */     boolean oldValue = this.value[oldPos];
/* 299 */     int tail = this.size - oldPos - 1;
/* 300 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 301 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 302 */     this.size--;
/* 303 */     return oldValue;
/*     */   }
/*     */   
/*     */   public DoubleSet keySet() {
/* 307 */     return new AbstractDoubleSet()
/*     */       {
/*     */         public boolean contains(double k) {
/* 310 */           return (Double2BooleanArrayMap.this.findKey(k) != -1);
/*     */         }
/*     */         
/*     */         public boolean remove(double k) {
/* 314 */           int oldPos = Double2BooleanArrayMap.this.findKey(k);
/* 315 */           if (oldPos == -1)
/* 316 */             return false; 
/* 317 */           int tail = Double2BooleanArrayMap.this.size - oldPos - 1;
/* 318 */           System.arraycopy(Double2BooleanArrayMap.this.key, oldPos + 1, Double2BooleanArrayMap.this.key, oldPos, tail);
/* 319 */           System.arraycopy(Double2BooleanArrayMap.this.value, oldPos + 1, Double2BooleanArrayMap.this.value, oldPos, tail);
/* 320 */           Double2BooleanArrayMap.this.size--;
/* 321 */           return true;
/*     */         }
/*     */         
/*     */         public DoubleIterator iterator() {
/* 325 */           return new DoubleIterator() {
/* 326 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 329 */                 return (this.pos < Double2BooleanArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public double nextDouble() {
/* 334 */                 if (!hasNext())
/* 335 */                   throw new NoSuchElementException(); 
/* 336 */                 return Double2BooleanArrayMap.this.key[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 340 */                 if (this.pos == 0)
/* 341 */                   throw new IllegalStateException(); 
/* 342 */                 int tail = Double2BooleanArrayMap.this.size - this.pos;
/* 343 */                 System.arraycopy(Double2BooleanArrayMap.this.key, this.pos, Double2BooleanArrayMap.this.key, this.pos - 1, tail);
/* 344 */                 System.arraycopy(Double2BooleanArrayMap.this.value, this.pos, Double2BooleanArrayMap.this.value, this.pos - 1, tail);
/* 345 */                 Double2BooleanArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 351 */           return Double2BooleanArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 355 */           Double2BooleanArrayMap.this.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public BooleanCollection values() {
/* 361 */     return (BooleanCollection)new AbstractBooleanCollection()
/*     */       {
/*     */         public boolean contains(boolean v) {
/* 364 */           return Double2BooleanArrayMap.this.containsValue(v);
/*     */         }
/*     */         
/*     */         public BooleanIterator iterator() {
/* 368 */           return new BooleanIterator() {
/* 369 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 372 */                 return (this.pos < Double2BooleanArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public boolean nextBoolean() {
/* 377 */                 if (!hasNext())
/* 378 */                   throw new NoSuchElementException(); 
/* 379 */                 return Double2BooleanArrayMap.this.value[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 383 */                 if (this.pos == 0)
/* 384 */                   throw new IllegalStateException(); 
/* 385 */                 int tail = Double2BooleanArrayMap.this.size - this.pos;
/* 386 */                 System.arraycopy(Double2BooleanArrayMap.this.key, this.pos, Double2BooleanArrayMap.this.key, this.pos - 1, tail);
/* 387 */                 System.arraycopy(Double2BooleanArrayMap.this.value, this.pos, Double2BooleanArrayMap.this.value, this.pos - 1, tail);
/* 388 */                 Double2BooleanArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 394 */           return Double2BooleanArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 398 */           Double2BooleanArrayMap.this.clear();
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
/*     */   public Double2BooleanArrayMap clone() {
/*     */     Double2BooleanArrayMap c;
/*     */     try {
/* 417 */       c = (Double2BooleanArrayMap)super.clone();
/* 418 */     } catch (CloneNotSupportedException cantHappen) {
/* 419 */       throw new InternalError();
/*     */     } 
/* 421 */     c.key = (double[])this.key.clone();
/* 422 */     c.value = (boolean[])this.value.clone();
/* 423 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 426 */     s.defaultWriteObject();
/* 427 */     for (int i = 0; i < this.size; i++) {
/* 428 */       s.writeDouble(this.key[i]);
/* 429 */       s.writeBoolean(this.value[i]);
/*     */     } 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 433 */     s.defaultReadObject();
/* 434 */     this.key = new double[this.size];
/* 435 */     this.value = new boolean[this.size];
/* 436 */     for (int i = 0; i < this.size; i++) {
/* 437 */       this.key[i] = s.readDouble();
/* 438 */       this.value[i] = s.readBoolean();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2BooleanArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */