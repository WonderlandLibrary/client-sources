/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleArrays;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleCollection;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleIterator;
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
/*     */ 
/*     */ 
/*     */ public class Reference2DoubleArrayMap<K>
/*     */   extends AbstractReference2DoubleMap<K>
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient Object[] key;
/*     */   private transient double[] value;
/*     */   private int size;
/*     */   
/*     */   public Reference2DoubleArrayMap(Object[] key, double[] value) {
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
/*     */   public Reference2DoubleArrayMap() {
/*  70 */     this.key = ObjectArrays.EMPTY_ARRAY;
/*  71 */     this.value = DoubleArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Reference2DoubleArrayMap(int capacity) {
/*  80 */     this.key = new Object[capacity];
/*  81 */     this.value = new double[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Reference2DoubleArrayMap(Reference2DoubleMap<K> m) {
/*  90 */     this(m.size());
/*  91 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Reference2DoubleArrayMap(Map<? extends K, ? extends Double> m) {
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
/*     */   public Reference2DoubleArrayMap(Object[] key, double[] value, int size) {
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
/*     */     extends AbstractObjectSet<Reference2DoubleMap.Entry<K>> implements Reference2DoubleMap.FastEntrySet<K> {
/*     */     public ObjectIterator<Reference2DoubleMap.Entry<K>> iterator() {
/* 133 */       return (ObjectIterator)new ObjectIterator<Reference2DoubleMap.Entry<Reference2DoubleMap.Entry<K>>>() {
/* 134 */           int curr = -1; int next = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 137 */             return (this.next < Reference2DoubleArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Reference2DoubleMap.Entry<K> next() {
/* 142 */             if (!hasNext())
/* 143 */               throw new NoSuchElementException(); 
/* 144 */             return new AbstractReference2DoubleMap.BasicEntry<>((K)Reference2DoubleArrayMap.this.key[this.curr = this.next], Reference2DoubleArrayMap.this.value[this.next++]);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 148 */             if (this.curr == -1)
/* 149 */               throw new IllegalStateException(); 
/* 150 */             this.curr = -1;
/* 151 */             int tail = Reference2DoubleArrayMap.this.size-- - this.next--;
/* 152 */             System.arraycopy(Reference2DoubleArrayMap.this.key, this.next + 1, Reference2DoubleArrayMap.this.key, this.next, tail);
/* 153 */             System.arraycopy(Reference2DoubleArrayMap.this.value, this.next + 1, Reference2DoubleArrayMap.this.value, this.next, tail);
/* 154 */             Reference2DoubleArrayMap.this.key[Reference2DoubleArrayMap.this.size] = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     private EntrySet() {}
/*     */     public ObjectIterator<Reference2DoubleMap.Entry<K>> fastIterator() {
/* 160 */       return (ObjectIterator)new ObjectIterator<Reference2DoubleMap.Entry<Reference2DoubleMap.Entry<K>>>() {
/* 161 */           int next = 0; int curr = -1;
/* 162 */           final AbstractReference2DoubleMap.BasicEntry<K> entry = new AbstractReference2DoubleMap.BasicEntry<>();
/*     */           
/*     */           public boolean hasNext() {
/* 165 */             return (this.next < Reference2DoubleArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Reference2DoubleMap.Entry<K> next() {
/* 170 */             if (!hasNext())
/* 171 */               throw new NoSuchElementException(); 
/* 172 */             this.entry.key = (K)Reference2DoubleArrayMap.this.key[this.curr = this.next];
/* 173 */             this.entry.value = Reference2DoubleArrayMap.this.value[this.next++];
/* 174 */             return this.entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 178 */             if (this.curr == -1)
/* 179 */               throw new IllegalStateException(); 
/* 180 */             this.curr = -1;
/* 181 */             int tail = Reference2DoubleArrayMap.this.size-- - this.next--;
/* 182 */             System.arraycopy(Reference2DoubleArrayMap.this.key, this.next + 1, Reference2DoubleArrayMap.this.key, this.next, tail);
/* 183 */             System.arraycopy(Reference2DoubleArrayMap.this.value, this.next + 1, Reference2DoubleArrayMap.this.value, this.next, tail);
/* 184 */             Reference2DoubleArrayMap.this.key[Reference2DoubleArrayMap.this.size] = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 190 */       return Reference2DoubleArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 195 */       if (!(o instanceof Map.Entry))
/* 196 */         return false; 
/* 197 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 198 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/* 199 */         return false; 
/* 200 */       K k = (K)e.getKey();
/* 201 */       return (Reference2DoubleArrayMap.this.containsKey(k) && 
/* 202 */         Double.doubleToLongBits(Reference2DoubleArrayMap.this.getDouble(k)) == 
/* 203 */         Double.doubleToLongBits(((Double)e.getValue()).doubleValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 208 */       if (!(o instanceof Map.Entry))
/* 209 */         return false; 
/* 210 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 211 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/* 212 */         return false; 
/* 213 */       K k = (K)e.getKey();
/* 214 */       double v = ((Double)e.getValue()).doubleValue();
/* 215 */       int oldPos = Reference2DoubleArrayMap.this.findKey(k);
/* 216 */       if (oldPos == -1 || Double.doubleToLongBits(v) != 
/* 217 */         Double.doubleToLongBits(Reference2DoubleArrayMap.this.value[oldPos]))
/* 218 */         return false; 
/* 219 */       int tail = Reference2DoubleArrayMap.this.size - oldPos - 1;
/* 220 */       System.arraycopy(Reference2DoubleArrayMap.this.key, oldPos + 1, Reference2DoubleArrayMap.this.key, oldPos, tail);
/*     */       
/* 222 */       System.arraycopy(Reference2DoubleArrayMap.this.value, oldPos + 1, Reference2DoubleArrayMap.this.value, oldPos, tail);
/*     */       
/* 224 */       Reference2DoubleArrayMap.this.size--;
/* 225 */       Reference2DoubleArrayMap.this.key[Reference2DoubleArrayMap.this.size] = null;
/* 226 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Reference2DoubleMap.FastEntrySet<K> reference2DoubleEntrySet() {
/* 231 */     return new EntrySet();
/*     */   }
/*     */   private int findKey(Object k) {
/* 234 */     Object[] key = this.key;
/* 235 */     for (int i = this.size; i-- != 0;) {
/* 236 */       if (key[i] == k)
/* 237 */         return i; 
/* 238 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDouble(Object k) {
/* 243 */     Object[] key = this.key;
/* 244 */     for (int i = this.size; i-- != 0;) {
/* 245 */       if (key[i] == k)
/* 246 */         return this.value[i]; 
/* 247 */     }  return this.defRetValue;
/*     */   }
/*     */   
/*     */   public int size() {
/* 251 */     return this.size;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 255 */     for (int i = this.size; i-- != 0;) {
/* 256 */       this.key[i] = null;
/*     */     }
/* 258 */     this.size = 0;
/*     */   }
/*     */   
/*     */   public boolean containsKey(Object k) {
/* 262 */     return (findKey(k) != -1);
/*     */   }
/*     */   
/*     */   public boolean containsValue(double v) {
/* 266 */     for (int i = this.size; i-- != 0;) {
/* 267 */       if (Double.doubleToLongBits(this.value[i]) == Double.doubleToLongBits(v))
/* 268 */         return true; 
/* 269 */     }  return false;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 273 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public double put(K k, double v) {
/* 278 */     int oldKey = findKey(k);
/* 279 */     if (oldKey != -1) {
/* 280 */       double oldValue = this.value[oldKey];
/* 281 */       this.value[oldKey] = v;
/* 282 */       return oldValue;
/*     */     } 
/* 284 */     if (this.size == this.key.length) {
/* 285 */       Object[] newKey = new Object[(this.size == 0) ? 2 : (this.size * 2)];
/* 286 */       double[] newValue = new double[(this.size == 0) ? 2 : (this.size * 2)];
/* 287 */       for (int i = this.size; i-- != 0; ) {
/* 288 */         newKey[i] = this.key[i];
/* 289 */         newValue[i] = this.value[i];
/*     */       } 
/* 291 */       this.key = newKey;
/* 292 */       this.value = newValue;
/*     */     } 
/* 294 */     this.key[this.size] = k;
/* 295 */     this.value[this.size] = v;
/* 296 */     this.size++;
/* 297 */     return this.defRetValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public double removeDouble(Object k) {
/* 302 */     int oldPos = findKey(k);
/* 303 */     if (oldPos == -1)
/* 304 */       return this.defRetValue; 
/* 305 */     double oldValue = this.value[oldPos];
/* 306 */     int tail = this.size - oldPos - 1;
/* 307 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 308 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 309 */     this.size--;
/* 310 */     this.key[this.size] = null;
/* 311 */     return oldValue;
/*     */   }
/*     */   
/*     */   public ReferenceSet<K> keySet() {
/* 315 */     return new AbstractReferenceSet<K>()
/*     */       {
/*     */         public boolean contains(Object k) {
/* 318 */           return (Reference2DoubleArrayMap.this.findKey(k) != -1);
/*     */         }
/*     */         
/*     */         public boolean remove(Object k) {
/* 322 */           int oldPos = Reference2DoubleArrayMap.this.findKey(k);
/* 323 */           if (oldPos == -1)
/* 324 */             return false; 
/* 325 */           int tail = Reference2DoubleArrayMap.this.size - oldPos - 1;
/* 326 */           System.arraycopy(Reference2DoubleArrayMap.this.key, oldPos + 1, Reference2DoubleArrayMap.this.key, oldPos, tail);
/* 327 */           System.arraycopy(Reference2DoubleArrayMap.this.value, oldPos + 1, Reference2DoubleArrayMap.this.value, oldPos, tail);
/* 328 */           Reference2DoubleArrayMap.this.size--;
/* 329 */           return true;
/*     */         }
/*     */         
/*     */         public ObjectIterator<K> iterator() {
/* 333 */           return new ObjectIterator<K>() {
/* 334 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 337 */                 return (this.pos < Reference2DoubleArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public K next() {
/* 342 */                 if (!hasNext())
/* 343 */                   throw new NoSuchElementException(); 
/* 344 */                 return (K)Reference2DoubleArrayMap.this.key[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 348 */                 if (this.pos == 0)
/* 349 */                   throw new IllegalStateException(); 
/* 350 */                 int tail = Reference2DoubleArrayMap.this.size - this.pos;
/* 351 */                 System.arraycopy(Reference2DoubleArrayMap.this.key, this.pos, Reference2DoubleArrayMap.this.key, this.pos - 1, tail);
/* 352 */                 System.arraycopy(Reference2DoubleArrayMap.this.value, this.pos, Reference2DoubleArrayMap.this.value, this.pos - 1, tail);
/* 353 */                 Reference2DoubleArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 359 */           return Reference2DoubleArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 363 */           Reference2DoubleArrayMap.this.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public DoubleCollection values() {
/* 369 */     return (DoubleCollection)new AbstractDoubleCollection()
/*     */       {
/*     */         public boolean contains(double v) {
/* 372 */           return Reference2DoubleArrayMap.this.containsValue(v);
/*     */         }
/*     */         
/*     */         public DoubleIterator iterator() {
/* 376 */           return new DoubleIterator() {
/* 377 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 380 */                 return (this.pos < Reference2DoubleArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public double nextDouble() {
/* 385 */                 if (!hasNext())
/* 386 */                   throw new NoSuchElementException(); 
/* 387 */                 return Reference2DoubleArrayMap.this.value[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 391 */                 if (this.pos == 0)
/* 392 */                   throw new IllegalStateException(); 
/* 393 */                 int tail = Reference2DoubleArrayMap.this.size - this.pos;
/* 394 */                 System.arraycopy(Reference2DoubleArrayMap.this.key, this.pos, Reference2DoubleArrayMap.this.key, this.pos - 1, tail);
/* 395 */                 System.arraycopy(Reference2DoubleArrayMap.this.value, this.pos, Reference2DoubleArrayMap.this.value, this.pos - 1, tail);
/* 396 */                 Reference2DoubleArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 402 */           return Reference2DoubleArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 406 */           Reference2DoubleArrayMap.this.clear();
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
/*     */   public Reference2DoubleArrayMap<K> clone() {
/*     */     Reference2DoubleArrayMap<K> c;
/*     */     try {
/* 425 */       c = (Reference2DoubleArrayMap<K>)super.clone();
/* 426 */     } catch (CloneNotSupportedException cantHappen) {
/* 427 */       throw new InternalError();
/*     */     } 
/* 429 */     c.key = (Object[])this.key.clone();
/* 430 */     c.value = (double[])this.value.clone();
/* 431 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 434 */     s.defaultWriteObject();
/* 435 */     for (int i = 0; i < this.size; i++) {
/* 436 */       s.writeObject(this.key[i]);
/* 437 */       s.writeDouble(this.value[i]);
/*     */     } 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 441 */     s.defaultReadObject();
/* 442 */     this.key = new Object[this.size];
/* 443 */     this.value = new double[this.size];
/* 444 */     for (int i = 0; i < this.size; i++) {
/* 445 */       this.key[i] = s.readObject();
/* 446 */       this.value[i] = s.readDouble();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2DoubleArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */