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
/*     */ 
/*     */ 
/*     */ public class Object2DoubleArrayMap<K>
/*     */   extends AbstractObject2DoubleMap<K>
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient Object[] key;
/*     */   private transient double[] value;
/*     */   private int size;
/*     */   
/*     */   public Object2DoubleArrayMap(Object[] key, double[] value) {
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
/*     */   public Object2DoubleArrayMap() {
/*  67 */     this.key = ObjectArrays.EMPTY_ARRAY;
/*  68 */     this.value = DoubleArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2DoubleArrayMap(int capacity) {
/*  77 */     this.key = new Object[capacity];
/*  78 */     this.value = new double[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2DoubleArrayMap(Object2DoubleMap<K> m) {
/*  87 */     this(m.size());
/*  88 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2DoubleArrayMap(Map<? extends K, ? extends Double> m) {
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
/*     */   public Object2DoubleArrayMap(Object[] key, double[] value, int size) {
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
/*     */     extends AbstractObjectSet<Object2DoubleMap.Entry<K>> implements Object2DoubleMap.FastEntrySet<K> {
/*     */     public ObjectIterator<Object2DoubleMap.Entry<K>> iterator() {
/* 130 */       return (ObjectIterator)new ObjectIterator<Object2DoubleMap.Entry<Object2DoubleMap.Entry<K>>>() {
/* 131 */           int curr = -1; int next = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 134 */             return (this.next < Object2DoubleArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object2DoubleMap.Entry<K> next() {
/* 139 */             if (!hasNext())
/* 140 */               throw new NoSuchElementException(); 
/* 141 */             return new AbstractObject2DoubleMap.BasicEntry<>((K)Object2DoubleArrayMap.this.key[this.curr = this.next], Object2DoubleArrayMap.this.value[this.next++]);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 145 */             if (this.curr == -1)
/* 146 */               throw new IllegalStateException(); 
/* 147 */             this.curr = -1;
/* 148 */             int tail = Object2DoubleArrayMap.this.size-- - this.next--;
/* 149 */             System.arraycopy(Object2DoubleArrayMap.this.key, this.next + 1, Object2DoubleArrayMap.this.key, this.next, tail);
/* 150 */             System.arraycopy(Object2DoubleArrayMap.this.value, this.next + 1, Object2DoubleArrayMap.this.value, this.next, tail);
/* 151 */             Object2DoubleArrayMap.this.key[Object2DoubleArrayMap.this.size] = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     private EntrySet() {}
/*     */     public ObjectIterator<Object2DoubleMap.Entry<K>> fastIterator() {
/* 157 */       return (ObjectIterator)new ObjectIterator<Object2DoubleMap.Entry<Object2DoubleMap.Entry<K>>>() {
/* 158 */           int next = 0; int curr = -1;
/* 159 */           final AbstractObject2DoubleMap.BasicEntry<K> entry = new AbstractObject2DoubleMap.BasicEntry<>();
/*     */           
/*     */           public boolean hasNext() {
/* 162 */             return (this.next < Object2DoubleArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object2DoubleMap.Entry<K> next() {
/* 167 */             if (!hasNext())
/* 168 */               throw new NoSuchElementException(); 
/* 169 */             this.entry.key = (K)Object2DoubleArrayMap.this.key[this.curr = this.next];
/* 170 */             this.entry.value = Object2DoubleArrayMap.this.value[this.next++];
/* 171 */             return this.entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 175 */             if (this.curr == -1)
/* 176 */               throw new IllegalStateException(); 
/* 177 */             this.curr = -1;
/* 178 */             int tail = Object2DoubleArrayMap.this.size-- - this.next--;
/* 179 */             System.arraycopy(Object2DoubleArrayMap.this.key, this.next + 1, Object2DoubleArrayMap.this.key, this.next, tail);
/* 180 */             System.arraycopy(Object2DoubleArrayMap.this.value, this.next + 1, Object2DoubleArrayMap.this.value, this.next, tail);
/* 181 */             Object2DoubleArrayMap.this.key[Object2DoubleArrayMap.this.size] = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 187 */       return Object2DoubleArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 192 */       if (!(o instanceof Map.Entry))
/* 193 */         return false; 
/* 194 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 195 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/* 196 */         return false; 
/* 197 */       K k = (K)e.getKey();
/* 198 */       return (Object2DoubleArrayMap.this.containsKey(k) && 
/* 199 */         Double.doubleToLongBits(Object2DoubleArrayMap.this.getDouble(k)) == 
/* 200 */         Double.doubleToLongBits(((Double)e.getValue()).doubleValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 205 */       if (!(o instanceof Map.Entry))
/* 206 */         return false; 
/* 207 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 208 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/* 209 */         return false; 
/* 210 */       K k = (K)e.getKey();
/* 211 */       double v = ((Double)e.getValue()).doubleValue();
/* 212 */       int oldPos = Object2DoubleArrayMap.this.findKey(k);
/* 213 */       if (oldPos == -1 || Double.doubleToLongBits(v) != 
/* 214 */         Double.doubleToLongBits(Object2DoubleArrayMap.this.value[oldPos]))
/* 215 */         return false; 
/* 216 */       int tail = Object2DoubleArrayMap.this.size - oldPos - 1;
/* 217 */       System.arraycopy(Object2DoubleArrayMap.this.key, oldPos + 1, Object2DoubleArrayMap.this.key, oldPos, tail);
/* 218 */       System.arraycopy(Object2DoubleArrayMap.this.value, oldPos + 1, Object2DoubleArrayMap.this.value, oldPos, tail);
/*     */       
/* 220 */       Object2DoubleArrayMap.this.size--;
/* 221 */       Object2DoubleArrayMap.this.key[Object2DoubleArrayMap.this.size] = null;
/* 222 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Object2DoubleMap.FastEntrySet<K> object2DoubleEntrySet() {
/* 227 */     return new EntrySet();
/*     */   }
/*     */   private int findKey(Object k) {
/* 230 */     Object[] key = this.key;
/* 231 */     for (int i = this.size; i-- != 0;) {
/* 232 */       if (Objects.equals(key[i], k))
/* 233 */         return i; 
/* 234 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDouble(Object k) {
/* 239 */     Object[] key = this.key;
/* 240 */     for (int i = this.size; i-- != 0;) {
/* 241 */       if (Objects.equals(key[i], k))
/* 242 */         return this.value[i]; 
/* 243 */     }  return this.defRetValue;
/*     */   }
/*     */   
/*     */   public int size() {
/* 247 */     return this.size;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 251 */     for (int i = this.size; i-- != 0;) {
/* 252 */       this.key[i] = null;
/*     */     }
/* 254 */     this.size = 0;
/*     */   }
/*     */   
/*     */   public boolean containsKey(Object k) {
/* 258 */     return (findKey(k) != -1);
/*     */   }
/*     */   
/*     */   public boolean containsValue(double v) {
/* 262 */     for (int i = this.size; i-- != 0;) {
/* 263 */       if (Double.doubleToLongBits(this.value[i]) == Double.doubleToLongBits(v))
/* 264 */         return true; 
/* 265 */     }  return false;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 269 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public double put(K k, double v) {
/* 274 */     int oldKey = findKey(k);
/* 275 */     if (oldKey != -1) {
/* 276 */       double oldValue = this.value[oldKey];
/* 277 */       this.value[oldKey] = v;
/* 278 */       return oldValue;
/*     */     } 
/* 280 */     if (this.size == this.key.length) {
/* 281 */       Object[] newKey = new Object[(this.size == 0) ? 2 : (this.size * 2)];
/* 282 */       double[] newValue = new double[(this.size == 0) ? 2 : (this.size * 2)];
/* 283 */       for (int i = this.size; i-- != 0; ) {
/* 284 */         newKey[i] = this.key[i];
/* 285 */         newValue[i] = this.value[i];
/*     */       } 
/* 287 */       this.key = newKey;
/* 288 */       this.value = newValue;
/*     */     } 
/* 290 */     this.key[this.size] = k;
/* 291 */     this.value[this.size] = v;
/* 292 */     this.size++;
/* 293 */     return this.defRetValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public double removeDouble(Object k) {
/* 298 */     int oldPos = findKey(k);
/* 299 */     if (oldPos == -1)
/* 300 */       return this.defRetValue; 
/* 301 */     double oldValue = this.value[oldPos];
/* 302 */     int tail = this.size - oldPos - 1;
/* 303 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 304 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 305 */     this.size--;
/* 306 */     this.key[this.size] = null;
/* 307 */     return oldValue;
/*     */   }
/*     */   
/*     */   public ObjectSet<K> keySet() {
/* 311 */     return new AbstractObjectSet<K>()
/*     */       {
/*     */         public boolean contains(Object k) {
/* 314 */           return (Object2DoubleArrayMap.this.findKey(k) != -1);
/*     */         }
/*     */         
/*     */         public boolean remove(Object k) {
/* 318 */           int oldPos = Object2DoubleArrayMap.this.findKey(k);
/* 319 */           if (oldPos == -1)
/* 320 */             return false; 
/* 321 */           int tail = Object2DoubleArrayMap.this.size - oldPos - 1;
/* 322 */           System.arraycopy(Object2DoubleArrayMap.this.key, oldPos + 1, Object2DoubleArrayMap.this.key, oldPos, tail);
/* 323 */           System.arraycopy(Object2DoubleArrayMap.this.value, oldPos + 1, Object2DoubleArrayMap.this.value, oldPos, tail);
/* 324 */           Object2DoubleArrayMap.this.size--;
/* 325 */           return true;
/*     */         }
/*     */         
/*     */         public ObjectIterator<K> iterator() {
/* 329 */           return new ObjectIterator<K>() {
/* 330 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 333 */                 return (this.pos < Object2DoubleArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public K next() {
/* 338 */                 if (!hasNext())
/* 339 */                   throw new NoSuchElementException(); 
/* 340 */                 return (K)Object2DoubleArrayMap.this.key[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 344 */                 if (this.pos == 0)
/* 345 */                   throw new IllegalStateException(); 
/* 346 */                 int tail = Object2DoubleArrayMap.this.size - this.pos;
/* 347 */                 System.arraycopy(Object2DoubleArrayMap.this.key, this.pos, Object2DoubleArrayMap.this.key, this.pos - 1, tail);
/* 348 */                 System.arraycopy(Object2DoubleArrayMap.this.value, this.pos, Object2DoubleArrayMap.this.value, this.pos - 1, tail);
/* 349 */                 Object2DoubleArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 355 */           return Object2DoubleArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 359 */           Object2DoubleArrayMap.this.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public DoubleCollection values() {
/* 365 */     return (DoubleCollection)new AbstractDoubleCollection()
/*     */       {
/*     */         public boolean contains(double v) {
/* 368 */           return Object2DoubleArrayMap.this.containsValue(v);
/*     */         }
/*     */         
/*     */         public DoubleIterator iterator() {
/* 372 */           return new DoubleIterator() {
/* 373 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 376 */                 return (this.pos < Object2DoubleArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public double nextDouble() {
/* 381 */                 if (!hasNext())
/* 382 */                   throw new NoSuchElementException(); 
/* 383 */                 return Object2DoubleArrayMap.this.value[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 387 */                 if (this.pos == 0)
/* 388 */                   throw new IllegalStateException(); 
/* 389 */                 int tail = Object2DoubleArrayMap.this.size - this.pos;
/* 390 */                 System.arraycopy(Object2DoubleArrayMap.this.key, this.pos, Object2DoubleArrayMap.this.key, this.pos - 1, tail);
/* 391 */                 System.arraycopy(Object2DoubleArrayMap.this.value, this.pos, Object2DoubleArrayMap.this.value, this.pos - 1, tail);
/* 392 */                 Object2DoubleArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 398 */           return Object2DoubleArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 402 */           Object2DoubleArrayMap.this.clear();
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
/*     */   public Object2DoubleArrayMap<K> clone() {
/*     */     Object2DoubleArrayMap<K> c;
/*     */     try {
/* 421 */       c = (Object2DoubleArrayMap<K>)super.clone();
/* 422 */     } catch (CloneNotSupportedException cantHappen) {
/* 423 */       throw new InternalError();
/*     */     } 
/* 425 */     c.key = (Object[])this.key.clone();
/* 426 */     c.value = (double[])this.value.clone();
/* 427 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 430 */     s.defaultWriteObject();
/* 431 */     for (int i = 0; i < this.size; i++) {
/* 432 */       s.writeObject(this.key[i]);
/* 433 */       s.writeDouble(this.value[i]);
/*     */     } 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 437 */     s.defaultReadObject();
/* 438 */     this.key = new Object[this.size];
/* 439 */     this.value = new double[this.size];
/* 440 */     for (int i = 0; i < this.size; i++) {
/* 441 */       this.key[i] = s.readObject();
/* 442 */       this.value[i] = s.readDouble();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2DoubleArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */