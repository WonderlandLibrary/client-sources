/*     */ package it.unimi.dsi.fastutil.doubles;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Double2ReferenceArrayMap<V>
/*     */   extends AbstractDouble2ReferenceMap<V>
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient double[] key;
/*     */   private transient Object[] value;
/*     */   private int size;
/*     */   
/*     */   public Double2ReferenceArrayMap(double[] key, Object[] value) {
/*  59 */     this.key = key;
/*  60 */     this.value = value;
/*  61 */     this.size = key.length;
/*  62 */     if (key.length != value.length) {
/*  63 */       throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Double2ReferenceArrayMap() {
/*  70 */     this.key = DoubleArrays.EMPTY_ARRAY;
/*  71 */     this.value = ObjectArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Double2ReferenceArrayMap(int capacity) {
/*  80 */     this.key = new double[capacity];
/*  81 */     this.value = new Object[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Double2ReferenceArrayMap(Double2ReferenceMap<V> m) {
/*  90 */     this(m.size());
/*  91 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Double2ReferenceArrayMap(Map<? extends Double, ? extends V> m) {
/* 100 */     this(m.size());
/* 101 */     putAll(m);
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
/*     */   public Double2ReferenceArrayMap(double[] key, Object[] value, int size) {
/* 120 */     this.key = key;
/* 121 */     this.value = value;
/* 122 */     this.size = size;
/* 123 */     if (key.length != value.length) {
/* 124 */       throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/*     */     }
/* 126 */     if (size > key.length)
/* 127 */       throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")"); 
/*     */   }
/*     */   
/*     */   private final class EntrySet
/*     */     extends AbstractObjectSet<Double2ReferenceMap.Entry<V>> implements Double2ReferenceMap.FastEntrySet<V> {
/*     */     public ObjectIterator<Double2ReferenceMap.Entry<V>> iterator() {
/* 133 */       return new ObjectIterator<Double2ReferenceMap.Entry<V>>() {
/* 134 */           int curr = -1; int next = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 137 */             return (this.next < Double2ReferenceArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Double2ReferenceMap.Entry<V> next() {
/* 142 */             if (!hasNext())
/* 143 */               throw new NoSuchElementException(); 
/* 144 */             return new AbstractDouble2ReferenceMap.BasicEntry<>(Double2ReferenceArrayMap.this.key[this.curr = this.next], (V)Double2ReferenceArrayMap.this.value[this.next++]);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 148 */             if (this.curr == -1)
/* 149 */               throw new IllegalStateException(); 
/* 150 */             this.curr = -1;
/* 151 */             int tail = Double2ReferenceArrayMap.this.size-- - this.next--;
/* 152 */             System.arraycopy(Double2ReferenceArrayMap.this.key, this.next + 1, Double2ReferenceArrayMap.this.key, this.next, tail);
/* 153 */             System.arraycopy(Double2ReferenceArrayMap.this.value, this.next + 1, Double2ReferenceArrayMap.this.value, this.next, tail);
/* 154 */             Double2ReferenceArrayMap.this.value[Double2ReferenceArrayMap.this.size] = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     private EntrySet() {}
/*     */     public ObjectIterator<Double2ReferenceMap.Entry<V>> fastIterator() {
/* 160 */       return new ObjectIterator<Double2ReferenceMap.Entry<V>>() {
/* 161 */           int next = 0; int curr = -1;
/* 162 */           final AbstractDouble2ReferenceMap.BasicEntry<V> entry = new AbstractDouble2ReferenceMap.BasicEntry<>();
/*     */           
/*     */           public boolean hasNext() {
/* 165 */             return (this.next < Double2ReferenceArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Double2ReferenceMap.Entry<V> next() {
/* 170 */             if (!hasNext())
/* 171 */               throw new NoSuchElementException(); 
/* 172 */             this.entry.key = Double2ReferenceArrayMap.this.key[this.curr = this.next];
/* 173 */             this.entry.value = (V)Double2ReferenceArrayMap.this.value[this.next++];
/* 174 */             return this.entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 178 */             if (this.curr == -1)
/* 179 */               throw new IllegalStateException(); 
/* 180 */             this.curr = -1;
/* 181 */             int tail = Double2ReferenceArrayMap.this.size-- - this.next--;
/* 182 */             System.arraycopy(Double2ReferenceArrayMap.this.key, this.next + 1, Double2ReferenceArrayMap.this.key, this.next, tail);
/* 183 */             System.arraycopy(Double2ReferenceArrayMap.this.value, this.next + 1, Double2ReferenceArrayMap.this.value, this.next, tail);
/* 184 */             Double2ReferenceArrayMap.this.value[Double2ReferenceArrayMap.this.size] = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 190 */       return Double2ReferenceArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 195 */       if (!(o instanceof Map.Entry))
/* 196 */         return false; 
/* 197 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 198 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 199 */         return false; 
/* 200 */       double k = ((Double)e.getKey()).doubleValue();
/* 201 */       return (Double2ReferenceArrayMap.this.containsKey(k) && Double2ReferenceArrayMap.this
/* 202 */         .get(k) == e.getValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 207 */       if (!(o instanceof Map.Entry))
/* 208 */         return false; 
/* 209 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 210 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 211 */         return false; 
/* 212 */       double k = ((Double)e.getKey()).doubleValue();
/* 213 */       V v = (V)e.getValue();
/* 214 */       int oldPos = Double2ReferenceArrayMap.this.findKey(k);
/* 215 */       if (oldPos == -1 || v != Double2ReferenceArrayMap.this.value[oldPos])
/* 216 */         return false; 
/* 217 */       int tail = Double2ReferenceArrayMap.this.size - oldPos - 1;
/* 218 */       System.arraycopy(Double2ReferenceArrayMap.this.key, oldPos + 1, Double2ReferenceArrayMap.this.key, oldPos, tail);
/*     */       
/* 220 */       System.arraycopy(Double2ReferenceArrayMap.this.value, oldPos + 1, Double2ReferenceArrayMap.this.value, oldPos, tail);
/*     */       
/* 222 */       Double2ReferenceArrayMap.this.size--;
/* 223 */       Double2ReferenceArrayMap.this.value[Double2ReferenceArrayMap.this.size] = null;
/* 224 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Double2ReferenceMap.FastEntrySet<V> double2ReferenceEntrySet() {
/* 229 */     return new EntrySet();
/*     */   }
/*     */   private int findKey(double k) {
/* 232 */     double[] key = this.key;
/* 233 */     for (int i = this.size; i-- != 0;) {
/* 234 */       if (Double.doubleToLongBits(key[i]) == Double.doubleToLongBits(k))
/* 235 */         return i; 
/* 236 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public V get(double k) {
/* 241 */     double[] key = this.key;
/* 242 */     for (int i = this.size; i-- != 0;) {
/* 243 */       if (Double.doubleToLongBits(key[i]) == Double.doubleToLongBits(k))
/* 244 */         return (V)this.value[i]; 
/* 245 */     }  return this.defRetValue;
/*     */   }
/*     */   
/*     */   public int size() {
/* 249 */     return this.size;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 253 */     for (int i = this.size; i-- != 0;) {
/* 254 */       this.value[i] = null;
/*     */     }
/* 256 */     this.size = 0;
/*     */   }
/*     */   
/*     */   public boolean containsKey(double k) {
/* 260 */     return (findKey(k) != -1);
/*     */   }
/*     */   
/*     */   public boolean containsValue(Object v) {
/* 264 */     for (int i = this.size; i-- != 0;) {
/* 265 */       if (this.value[i] == v)
/* 266 */         return true; 
/* 267 */     }  return false;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 271 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public V put(double k, V v) {
/* 276 */     int oldKey = findKey(k);
/* 277 */     if (oldKey != -1) {
/* 278 */       V oldValue = (V)this.value[oldKey];
/* 279 */       this.value[oldKey] = v;
/* 280 */       return oldValue;
/*     */     } 
/* 282 */     if (this.size == this.key.length) {
/* 283 */       double[] newKey = new double[(this.size == 0) ? 2 : (this.size * 2)];
/* 284 */       Object[] newValue = new Object[(this.size == 0) ? 2 : (this.size * 2)];
/* 285 */       for (int i = this.size; i-- != 0; ) {
/* 286 */         newKey[i] = this.key[i];
/* 287 */         newValue[i] = this.value[i];
/*     */       } 
/* 289 */       this.key = newKey;
/* 290 */       this.value = newValue;
/*     */     } 
/* 292 */     this.key[this.size] = k;
/* 293 */     this.value[this.size] = v;
/* 294 */     this.size++;
/* 295 */     return this.defRetValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public V remove(double k) {
/* 300 */     int oldPos = findKey(k);
/* 301 */     if (oldPos == -1)
/* 302 */       return this.defRetValue; 
/* 303 */     V oldValue = (V)this.value[oldPos];
/* 304 */     int tail = this.size - oldPos - 1;
/* 305 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 306 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 307 */     this.size--;
/* 308 */     this.value[this.size] = null;
/* 309 */     return oldValue;
/*     */   }
/*     */   
/*     */   public DoubleSet keySet() {
/* 313 */     return new AbstractDoubleSet()
/*     */       {
/*     */         public boolean contains(double k) {
/* 316 */           return (Double2ReferenceArrayMap.this.findKey(k) != -1);
/*     */         }
/*     */         
/*     */         public boolean remove(double k) {
/* 320 */           int oldPos = Double2ReferenceArrayMap.this.findKey(k);
/* 321 */           if (oldPos == -1)
/* 322 */             return false; 
/* 323 */           int tail = Double2ReferenceArrayMap.this.size - oldPos - 1;
/* 324 */           System.arraycopy(Double2ReferenceArrayMap.this.key, oldPos + 1, Double2ReferenceArrayMap.this.key, oldPos, tail);
/* 325 */           System.arraycopy(Double2ReferenceArrayMap.this.value, oldPos + 1, Double2ReferenceArrayMap.this.value, oldPos, tail);
/* 326 */           Double2ReferenceArrayMap.this.size--;
/* 327 */           return true;
/*     */         }
/*     */         
/*     */         public DoubleIterator iterator() {
/* 331 */           return new DoubleIterator() {
/* 332 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 335 */                 return (this.pos < Double2ReferenceArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public double nextDouble() {
/* 340 */                 if (!hasNext())
/* 341 */                   throw new NoSuchElementException(); 
/* 342 */                 return Double2ReferenceArrayMap.this.key[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 346 */                 if (this.pos == 0)
/* 347 */                   throw new IllegalStateException(); 
/* 348 */                 int tail = Double2ReferenceArrayMap.this.size - this.pos;
/* 349 */                 System.arraycopy(Double2ReferenceArrayMap.this.key, this.pos, Double2ReferenceArrayMap.this.key, this.pos - 1, tail);
/* 350 */                 System.arraycopy(Double2ReferenceArrayMap.this.value, this.pos, Double2ReferenceArrayMap.this.value, this.pos - 1, tail);
/* 351 */                 Double2ReferenceArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 357 */           return Double2ReferenceArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 361 */           Double2ReferenceArrayMap.this.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public ReferenceCollection<V> values() {
/* 367 */     return (ReferenceCollection<V>)new AbstractReferenceCollection<V>()
/*     */       {
/*     */         public boolean contains(Object v) {
/* 370 */           return Double2ReferenceArrayMap.this.containsValue(v);
/*     */         }
/*     */         
/*     */         public ObjectIterator<V> iterator() {
/* 374 */           return new ObjectIterator<V>() {
/* 375 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 378 */                 return (this.pos < Double2ReferenceArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public V next() {
/* 383 */                 if (!hasNext())
/* 384 */                   throw new NoSuchElementException(); 
/* 385 */                 return (V)Double2ReferenceArrayMap.this.value[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 389 */                 if (this.pos == 0)
/* 390 */                   throw new IllegalStateException(); 
/* 391 */                 int tail = Double2ReferenceArrayMap.this.size - this.pos;
/* 392 */                 System.arraycopy(Double2ReferenceArrayMap.this.key, this.pos, Double2ReferenceArrayMap.this.key, this.pos - 1, tail);
/* 393 */                 System.arraycopy(Double2ReferenceArrayMap.this.value, this.pos, Double2ReferenceArrayMap.this.value, this.pos - 1, tail);
/* 394 */                 Double2ReferenceArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 400 */           return Double2ReferenceArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 404 */           Double2ReferenceArrayMap.this.clear();
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
/*     */   public Double2ReferenceArrayMap<V> clone() {
/*     */     Double2ReferenceArrayMap<V> c;
/*     */     try {
/* 423 */       c = (Double2ReferenceArrayMap<V>)super.clone();
/* 424 */     } catch (CloneNotSupportedException cantHappen) {
/* 425 */       throw new InternalError();
/*     */     } 
/* 427 */     c.key = (double[])this.key.clone();
/* 428 */     c.value = (Object[])this.value.clone();
/* 429 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 432 */     s.defaultWriteObject();
/* 433 */     for (int i = 0; i < this.size; i++) {
/* 434 */       s.writeDouble(this.key[i]);
/* 435 */       s.writeObject(this.value[i]);
/*     */     } 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 439 */     s.defaultReadObject();
/* 440 */     this.key = new double[this.size];
/* 441 */     this.value = new Object[this.size];
/* 442 */     for (int i = 0; i < this.size; i++) {
/* 443 */       this.key[i] = s.readDouble();
/* 444 */       this.value[i] = s.readObject();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2ReferenceArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */