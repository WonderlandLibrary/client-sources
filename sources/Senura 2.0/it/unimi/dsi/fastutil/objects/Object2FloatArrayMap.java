/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
/*     */ import it.unimi.dsi.fastutil.floats.FloatArrays;
/*     */ import it.unimi.dsi.fastutil.floats.FloatCollection;
/*     */ import it.unimi.dsi.fastutil.floats.FloatIterator;
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
/*     */ public class Object2FloatArrayMap<K>
/*     */   extends AbstractObject2FloatMap<K>
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient Object[] key;
/*     */   private transient float[] value;
/*     */   private int size;
/*     */   
/*     */   public Object2FloatArrayMap(Object[] key, float[] value) {
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
/*     */   public Object2FloatArrayMap() {
/*  67 */     this.key = ObjectArrays.EMPTY_ARRAY;
/*  68 */     this.value = FloatArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2FloatArrayMap(int capacity) {
/*  77 */     this.key = new Object[capacity];
/*  78 */     this.value = new float[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2FloatArrayMap(Object2FloatMap<K> m) {
/*  87 */     this(m.size());
/*  88 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2FloatArrayMap(Map<? extends K, ? extends Float> m) {
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
/*     */   public Object2FloatArrayMap(Object[] key, float[] value, int size) {
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
/*     */     extends AbstractObjectSet<Object2FloatMap.Entry<K>> implements Object2FloatMap.FastEntrySet<K> {
/*     */     public ObjectIterator<Object2FloatMap.Entry<K>> iterator() {
/* 130 */       return (ObjectIterator)new ObjectIterator<Object2FloatMap.Entry<Object2FloatMap.Entry<K>>>() {
/* 131 */           int curr = -1; int next = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 134 */             return (this.next < Object2FloatArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object2FloatMap.Entry<K> next() {
/* 139 */             if (!hasNext())
/* 140 */               throw new NoSuchElementException(); 
/* 141 */             return new AbstractObject2FloatMap.BasicEntry<>((K)Object2FloatArrayMap.this.key[this.curr = this.next], Object2FloatArrayMap.this.value[this.next++]);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 145 */             if (this.curr == -1)
/* 146 */               throw new IllegalStateException(); 
/* 147 */             this.curr = -1;
/* 148 */             int tail = Object2FloatArrayMap.this.size-- - this.next--;
/* 149 */             System.arraycopy(Object2FloatArrayMap.this.key, this.next + 1, Object2FloatArrayMap.this.key, this.next, tail);
/* 150 */             System.arraycopy(Object2FloatArrayMap.this.value, this.next + 1, Object2FloatArrayMap.this.value, this.next, tail);
/* 151 */             Object2FloatArrayMap.this.key[Object2FloatArrayMap.this.size] = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     private EntrySet() {}
/*     */     public ObjectIterator<Object2FloatMap.Entry<K>> fastIterator() {
/* 157 */       return (ObjectIterator)new ObjectIterator<Object2FloatMap.Entry<Object2FloatMap.Entry<K>>>() {
/* 158 */           int next = 0; int curr = -1;
/* 159 */           final AbstractObject2FloatMap.BasicEntry<K> entry = new AbstractObject2FloatMap.BasicEntry<>();
/*     */           
/*     */           public boolean hasNext() {
/* 162 */             return (this.next < Object2FloatArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object2FloatMap.Entry<K> next() {
/* 167 */             if (!hasNext())
/* 168 */               throw new NoSuchElementException(); 
/* 169 */             this.entry.key = (K)Object2FloatArrayMap.this.key[this.curr = this.next];
/* 170 */             this.entry.value = Object2FloatArrayMap.this.value[this.next++];
/* 171 */             return this.entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 175 */             if (this.curr == -1)
/* 176 */               throw new IllegalStateException(); 
/* 177 */             this.curr = -1;
/* 178 */             int tail = Object2FloatArrayMap.this.size-- - this.next--;
/* 179 */             System.arraycopy(Object2FloatArrayMap.this.key, this.next + 1, Object2FloatArrayMap.this.key, this.next, tail);
/* 180 */             System.arraycopy(Object2FloatArrayMap.this.value, this.next + 1, Object2FloatArrayMap.this.value, this.next, tail);
/* 181 */             Object2FloatArrayMap.this.key[Object2FloatArrayMap.this.size] = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 187 */       return Object2FloatArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 192 */       if (!(o instanceof Map.Entry))
/* 193 */         return false; 
/* 194 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 195 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 196 */         return false; 
/* 197 */       K k = (K)e.getKey();
/* 198 */       return (Object2FloatArrayMap.this.containsKey(k) && 
/* 199 */         Float.floatToIntBits(Object2FloatArrayMap.this.getFloat(k)) == 
/* 200 */         Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 205 */       if (!(o instanceof Map.Entry))
/* 206 */         return false; 
/* 207 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 208 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 209 */         return false; 
/* 210 */       K k = (K)e.getKey();
/* 211 */       float v = ((Float)e.getValue()).floatValue();
/* 212 */       int oldPos = Object2FloatArrayMap.this.findKey(k);
/* 213 */       if (oldPos == -1 || 
/* 214 */         Float.floatToIntBits(v) != Float.floatToIntBits(Object2FloatArrayMap.this.value[oldPos]))
/* 215 */         return false; 
/* 216 */       int tail = Object2FloatArrayMap.this.size - oldPos - 1;
/* 217 */       System.arraycopy(Object2FloatArrayMap.this.key, oldPos + 1, Object2FloatArrayMap.this.key, oldPos, tail);
/* 218 */       System.arraycopy(Object2FloatArrayMap.this.value, oldPos + 1, Object2FloatArrayMap.this.value, oldPos, tail);
/*     */       
/* 220 */       Object2FloatArrayMap.this.size--;
/* 221 */       Object2FloatArrayMap.this.key[Object2FloatArrayMap.this.size] = null;
/* 222 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Object2FloatMap.FastEntrySet<K> object2FloatEntrySet() {
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
/*     */   public float getFloat(Object k) {
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
/*     */   public boolean containsValue(float v) {
/* 262 */     for (int i = this.size; i-- != 0;) {
/* 263 */       if (Float.floatToIntBits(this.value[i]) == Float.floatToIntBits(v))
/* 264 */         return true; 
/* 265 */     }  return false;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 269 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public float put(K k, float v) {
/* 274 */     int oldKey = findKey(k);
/* 275 */     if (oldKey != -1) {
/* 276 */       float oldValue = this.value[oldKey];
/* 277 */       this.value[oldKey] = v;
/* 278 */       return oldValue;
/*     */     } 
/* 280 */     if (this.size == this.key.length) {
/* 281 */       Object[] newKey = new Object[(this.size == 0) ? 2 : (this.size * 2)];
/* 282 */       float[] newValue = new float[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public float removeFloat(Object k) {
/* 298 */     int oldPos = findKey(k);
/* 299 */     if (oldPos == -1)
/* 300 */       return this.defRetValue; 
/* 301 */     float oldValue = this.value[oldPos];
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
/* 314 */           return (Object2FloatArrayMap.this.findKey(k) != -1);
/*     */         }
/*     */         
/*     */         public boolean remove(Object k) {
/* 318 */           int oldPos = Object2FloatArrayMap.this.findKey(k);
/* 319 */           if (oldPos == -1)
/* 320 */             return false; 
/* 321 */           int tail = Object2FloatArrayMap.this.size - oldPos - 1;
/* 322 */           System.arraycopy(Object2FloatArrayMap.this.key, oldPos + 1, Object2FloatArrayMap.this.key, oldPos, tail);
/* 323 */           System.arraycopy(Object2FloatArrayMap.this.value, oldPos + 1, Object2FloatArrayMap.this.value, oldPos, tail);
/* 324 */           Object2FloatArrayMap.this.size--;
/* 325 */           return true;
/*     */         }
/*     */         
/*     */         public ObjectIterator<K> iterator() {
/* 329 */           return new ObjectIterator<K>() {
/* 330 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 333 */                 return (this.pos < Object2FloatArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public K next() {
/* 338 */                 if (!hasNext())
/* 339 */                   throw new NoSuchElementException(); 
/* 340 */                 return (K)Object2FloatArrayMap.this.key[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 344 */                 if (this.pos == 0)
/* 345 */                   throw new IllegalStateException(); 
/* 346 */                 int tail = Object2FloatArrayMap.this.size - this.pos;
/* 347 */                 System.arraycopy(Object2FloatArrayMap.this.key, this.pos, Object2FloatArrayMap.this.key, this.pos - 1, tail);
/* 348 */                 System.arraycopy(Object2FloatArrayMap.this.value, this.pos, Object2FloatArrayMap.this.value, this.pos - 1, tail);
/* 349 */                 Object2FloatArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 355 */           return Object2FloatArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 359 */           Object2FloatArrayMap.this.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public FloatCollection values() {
/* 365 */     return (FloatCollection)new AbstractFloatCollection()
/*     */       {
/*     */         public boolean contains(float v) {
/* 368 */           return Object2FloatArrayMap.this.containsValue(v);
/*     */         }
/*     */         
/*     */         public FloatIterator iterator() {
/* 372 */           return new FloatIterator() {
/* 373 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 376 */                 return (this.pos < Object2FloatArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public float nextFloat() {
/* 381 */                 if (!hasNext())
/* 382 */                   throw new NoSuchElementException(); 
/* 383 */                 return Object2FloatArrayMap.this.value[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 387 */                 if (this.pos == 0)
/* 388 */                   throw new IllegalStateException(); 
/* 389 */                 int tail = Object2FloatArrayMap.this.size - this.pos;
/* 390 */                 System.arraycopy(Object2FloatArrayMap.this.key, this.pos, Object2FloatArrayMap.this.key, this.pos - 1, tail);
/* 391 */                 System.arraycopy(Object2FloatArrayMap.this.value, this.pos, Object2FloatArrayMap.this.value, this.pos - 1, tail);
/* 392 */                 Object2FloatArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 398 */           return Object2FloatArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 402 */           Object2FloatArrayMap.this.clear();
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
/*     */   public Object2FloatArrayMap<K> clone() {
/*     */     Object2FloatArrayMap<K> c;
/*     */     try {
/* 421 */       c = (Object2FloatArrayMap<K>)super.clone();
/* 422 */     } catch (CloneNotSupportedException cantHappen) {
/* 423 */       throw new InternalError();
/*     */     } 
/* 425 */     c.key = (Object[])this.key.clone();
/* 426 */     c.value = (float[])this.value.clone();
/* 427 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 430 */     s.defaultWriteObject();
/* 431 */     for (int i = 0; i < this.size; i++) {
/* 432 */       s.writeObject(this.key[i]);
/* 433 */       s.writeFloat(this.value[i]);
/*     */     } 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 437 */     s.defaultReadObject();
/* 438 */     this.key = new Object[this.size];
/* 439 */     this.value = new float[this.size];
/* 440 */     for (int i = 0; i < this.size; i++) {
/* 441 */       this.key[i] = s.readObject();
/* 442 */       this.value[i] = s.readFloat();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2FloatArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */