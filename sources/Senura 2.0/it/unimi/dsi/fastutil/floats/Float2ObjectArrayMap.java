/*     */ package it.unimi.dsi.fastutil.floats;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrays;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectCollection;
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
/*     */ public class Float2ObjectArrayMap<V>
/*     */   extends AbstractFloat2ObjectMap<V>
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient float[] key;
/*     */   private transient Object[] value;
/*     */   private int size;
/*     */   
/*     */   public Float2ObjectArrayMap(float[] key, Object[] value) {
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
/*     */   public Float2ObjectArrayMap() {
/*  67 */     this.key = FloatArrays.EMPTY_ARRAY;
/*  68 */     this.value = ObjectArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float2ObjectArrayMap(int capacity) {
/*  77 */     this.key = new float[capacity];
/*  78 */     this.value = new Object[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float2ObjectArrayMap(Float2ObjectMap<V> m) {
/*  87 */     this(m.size());
/*  88 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float2ObjectArrayMap(Map<? extends Float, ? extends V> m) {
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
/*     */   public Float2ObjectArrayMap(float[] key, Object[] value, int size) {
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
/*     */     extends AbstractObjectSet<Float2ObjectMap.Entry<V>> implements Float2ObjectMap.FastEntrySet<V> {
/*     */     public ObjectIterator<Float2ObjectMap.Entry<V>> iterator() {
/* 130 */       return new ObjectIterator<Float2ObjectMap.Entry<V>>() {
/* 131 */           int curr = -1; int next = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 134 */             return (this.next < Float2ObjectArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Float2ObjectMap.Entry<V> next() {
/* 139 */             if (!hasNext())
/* 140 */               throw new NoSuchElementException(); 
/* 141 */             return new AbstractFloat2ObjectMap.BasicEntry<>(Float2ObjectArrayMap.this.key[this.curr = this.next], (V)Float2ObjectArrayMap.this.value[this.next++]);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 145 */             if (this.curr == -1)
/* 146 */               throw new IllegalStateException(); 
/* 147 */             this.curr = -1;
/* 148 */             int tail = Float2ObjectArrayMap.this.size-- - this.next--;
/* 149 */             System.arraycopy(Float2ObjectArrayMap.this.key, this.next + 1, Float2ObjectArrayMap.this.key, this.next, tail);
/* 150 */             System.arraycopy(Float2ObjectArrayMap.this.value, this.next + 1, Float2ObjectArrayMap.this.value, this.next, tail);
/* 151 */             Float2ObjectArrayMap.this.value[Float2ObjectArrayMap.this.size] = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     private EntrySet() {}
/*     */     public ObjectIterator<Float2ObjectMap.Entry<V>> fastIterator() {
/* 157 */       return new ObjectIterator<Float2ObjectMap.Entry<V>>() {
/* 158 */           int next = 0; int curr = -1;
/* 159 */           final AbstractFloat2ObjectMap.BasicEntry<V> entry = new AbstractFloat2ObjectMap.BasicEntry<>();
/*     */           
/*     */           public boolean hasNext() {
/* 162 */             return (this.next < Float2ObjectArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Float2ObjectMap.Entry<V> next() {
/* 167 */             if (!hasNext())
/* 168 */               throw new NoSuchElementException(); 
/* 169 */             this.entry.key = Float2ObjectArrayMap.this.key[this.curr = this.next];
/* 170 */             this.entry.value = (V)Float2ObjectArrayMap.this.value[this.next++];
/* 171 */             return this.entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 175 */             if (this.curr == -1)
/* 176 */               throw new IllegalStateException(); 
/* 177 */             this.curr = -1;
/* 178 */             int tail = Float2ObjectArrayMap.this.size-- - this.next--;
/* 179 */             System.arraycopy(Float2ObjectArrayMap.this.key, this.next + 1, Float2ObjectArrayMap.this.key, this.next, tail);
/* 180 */             System.arraycopy(Float2ObjectArrayMap.this.value, this.next + 1, Float2ObjectArrayMap.this.value, this.next, tail);
/* 181 */             Float2ObjectArrayMap.this.value[Float2ObjectArrayMap.this.size] = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 187 */       return Float2ObjectArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 192 */       if (!(o instanceof Map.Entry))
/* 193 */         return false; 
/* 194 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 195 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 196 */         return false; 
/* 197 */       float k = ((Float)e.getKey()).floatValue();
/* 198 */       return (Float2ObjectArrayMap.this.containsKey(k) && 
/* 199 */         Objects.equals(Float2ObjectArrayMap.this.get(k), e.getValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 204 */       if (!(o instanceof Map.Entry))
/* 205 */         return false; 
/* 206 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 207 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 208 */         return false; 
/* 209 */       float k = ((Float)e.getKey()).floatValue();
/* 210 */       V v = (V)e.getValue();
/* 211 */       int oldPos = Float2ObjectArrayMap.this.findKey(k);
/* 212 */       if (oldPos == -1 || !Objects.equals(v, Float2ObjectArrayMap.this.value[oldPos]))
/* 213 */         return false; 
/* 214 */       int tail = Float2ObjectArrayMap.this.size - oldPos - 1;
/* 215 */       System.arraycopy(Float2ObjectArrayMap.this.key, oldPos + 1, Float2ObjectArrayMap.this.key, oldPos, tail);
/* 216 */       System.arraycopy(Float2ObjectArrayMap.this.value, oldPos + 1, Float2ObjectArrayMap.this.value, oldPos, tail);
/*     */       
/* 218 */       Float2ObjectArrayMap.this.size--;
/* 219 */       Float2ObjectArrayMap.this.value[Float2ObjectArrayMap.this.size] = null;
/* 220 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Float2ObjectMap.FastEntrySet<V> float2ObjectEntrySet() {
/* 225 */     return new EntrySet();
/*     */   }
/*     */   private int findKey(float k) {
/* 228 */     float[] key = this.key;
/* 229 */     for (int i = this.size; i-- != 0;) {
/* 230 */       if (Float.floatToIntBits(key[i]) == Float.floatToIntBits(k))
/* 231 */         return i; 
/* 232 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public V get(float k) {
/* 237 */     float[] key = this.key;
/* 238 */     for (int i = this.size; i-- != 0;) {
/* 239 */       if (Float.floatToIntBits(key[i]) == Float.floatToIntBits(k))
/* 240 */         return (V)this.value[i]; 
/* 241 */     }  return this.defRetValue;
/*     */   }
/*     */   
/*     */   public int size() {
/* 245 */     return this.size;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 249 */     for (int i = this.size; i-- != 0;) {
/* 250 */       this.value[i] = null;
/*     */     }
/* 252 */     this.size = 0;
/*     */   }
/*     */   
/*     */   public boolean containsKey(float k) {
/* 256 */     return (findKey(k) != -1);
/*     */   }
/*     */   
/*     */   public boolean containsValue(Object v) {
/* 260 */     for (int i = this.size; i-- != 0;) {
/* 261 */       if (Objects.equals(this.value[i], v))
/* 262 */         return true; 
/* 263 */     }  return false;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 267 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public V put(float k, V v) {
/* 272 */     int oldKey = findKey(k);
/* 273 */     if (oldKey != -1) {
/* 274 */       V oldValue = (V)this.value[oldKey];
/* 275 */       this.value[oldKey] = v;
/* 276 */       return oldValue;
/*     */     } 
/* 278 */     if (this.size == this.key.length) {
/* 279 */       float[] newKey = new float[(this.size == 0) ? 2 : (this.size * 2)];
/* 280 */       Object[] newValue = new Object[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public V remove(float k) {
/* 296 */     int oldPos = findKey(k);
/* 297 */     if (oldPos == -1)
/* 298 */       return this.defRetValue; 
/* 299 */     V oldValue = (V)this.value[oldPos];
/* 300 */     int tail = this.size - oldPos - 1;
/* 301 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 302 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 303 */     this.size--;
/* 304 */     this.value[this.size] = null;
/* 305 */     return oldValue;
/*     */   }
/*     */   
/*     */   public FloatSet keySet() {
/* 309 */     return new AbstractFloatSet()
/*     */       {
/*     */         public boolean contains(float k) {
/* 312 */           return (Float2ObjectArrayMap.this.findKey(k) != -1);
/*     */         }
/*     */         
/*     */         public boolean remove(float k) {
/* 316 */           int oldPos = Float2ObjectArrayMap.this.findKey(k);
/* 317 */           if (oldPos == -1)
/* 318 */             return false; 
/* 319 */           int tail = Float2ObjectArrayMap.this.size - oldPos - 1;
/* 320 */           System.arraycopy(Float2ObjectArrayMap.this.key, oldPos + 1, Float2ObjectArrayMap.this.key, oldPos, tail);
/* 321 */           System.arraycopy(Float2ObjectArrayMap.this.value, oldPos + 1, Float2ObjectArrayMap.this.value, oldPos, tail);
/* 322 */           Float2ObjectArrayMap.this.size--;
/* 323 */           return true;
/*     */         }
/*     */         
/*     */         public FloatIterator iterator() {
/* 327 */           return new FloatIterator() {
/* 328 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 331 */                 return (this.pos < Float2ObjectArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public float nextFloat() {
/* 336 */                 if (!hasNext())
/* 337 */                   throw new NoSuchElementException(); 
/* 338 */                 return Float2ObjectArrayMap.this.key[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 342 */                 if (this.pos == 0)
/* 343 */                   throw new IllegalStateException(); 
/* 344 */                 int tail = Float2ObjectArrayMap.this.size - this.pos;
/* 345 */                 System.arraycopy(Float2ObjectArrayMap.this.key, this.pos, Float2ObjectArrayMap.this.key, this.pos - 1, tail);
/* 346 */                 System.arraycopy(Float2ObjectArrayMap.this.value, this.pos, Float2ObjectArrayMap.this.value, this.pos - 1, tail);
/* 347 */                 Float2ObjectArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 353 */           return Float2ObjectArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 357 */           Float2ObjectArrayMap.this.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public ObjectCollection<V> values() {
/* 363 */     return (ObjectCollection<V>)new AbstractObjectCollection<V>()
/*     */       {
/*     */         public boolean contains(Object v) {
/* 366 */           return Float2ObjectArrayMap.this.containsValue(v);
/*     */         }
/*     */         
/*     */         public ObjectIterator<V> iterator() {
/* 370 */           return new ObjectIterator<V>() {
/* 371 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 374 */                 return (this.pos < Float2ObjectArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public V next() {
/* 379 */                 if (!hasNext())
/* 380 */                   throw new NoSuchElementException(); 
/* 381 */                 return (V)Float2ObjectArrayMap.this.value[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 385 */                 if (this.pos == 0)
/* 386 */                   throw new IllegalStateException(); 
/* 387 */                 int tail = Float2ObjectArrayMap.this.size - this.pos;
/* 388 */                 System.arraycopy(Float2ObjectArrayMap.this.key, this.pos, Float2ObjectArrayMap.this.key, this.pos - 1, tail);
/* 389 */                 System.arraycopy(Float2ObjectArrayMap.this.value, this.pos, Float2ObjectArrayMap.this.value, this.pos - 1, tail);
/* 390 */                 Float2ObjectArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 396 */           return Float2ObjectArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 400 */           Float2ObjectArrayMap.this.clear();
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
/*     */   public Float2ObjectArrayMap<V> clone() {
/*     */     Float2ObjectArrayMap<V> c;
/*     */     try {
/* 419 */       c = (Float2ObjectArrayMap<V>)super.clone();
/* 420 */     } catch (CloneNotSupportedException cantHappen) {
/* 421 */       throw new InternalError();
/*     */     } 
/* 423 */     c.key = (float[])this.key.clone();
/* 424 */     c.value = (Object[])this.value.clone();
/* 425 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 428 */     s.defaultWriteObject();
/* 429 */     for (int i = 0; i < this.size; i++) {
/* 430 */       s.writeFloat(this.key[i]);
/* 431 */       s.writeObject(this.value[i]);
/*     */     } 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 435 */     s.defaultReadObject();
/* 436 */     this.key = new float[this.size];
/* 437 */     this.value = new Object[this.size];
/* 438 */     for (int i = 0; i < this.size; i++) {
/* 439 */       this.key[i] = s.readFloat();
/* 440 */       this.value[i] = s.readObject();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2ObjectArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */