/*     */ package it.unimi.dsi.fastutil.floats;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Float2FloatArrayMap
/*     */   extends AbstractFloat2FloatMap
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient float[] key;
/*     */   private transient float[] value;
/*     */   private int size;
/*     */   
/*     */   public Float2FloatArrayMap(float[] key, float[] value) {
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
/*     */   public Float2FloatArrayMap() {
/*  67 */     this.key = FloatArrays.EMPTY_ARRAY;
/*  68 */     this.value = FloatArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float2FloatArrayMap(int capacity) {
/*  77 */     this.key = new float[capacity];
/*  78 */     this.value = new float[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float2FloatArrayMap(Float2FloatMap m) {
/*  87 */     this(m.size());
/*  88 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float2FloatArrayMap(Map<? extends Float, ? extends Float> m) {
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
/*     */   public Float2FloatArrayMap(float[] key, float[] value, int size) {
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
/*     */     extends AbstractObjectSet<Float2FloatMap.Entry> implements Float2FloatMap.FastEntrySet {
/*     */     public ObjectIterator<Float2FloatMap.Entry> iterator() {
/* 130 */       return new ObjectIterator<Float2FloatMap.Entry>() {
/* 131 */           int curr = -1; int next = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 134 */             return (this.next < Float2FloatArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Float2FloatMap.Entry next() {
/* 139 */             if (!hasNext())
/* 140 */               throw new NoSuchElementException(); 
/* 141 */             return new AbstractFloat2FloatMap.BasicEntry(Float2FloatArrayMap.this.key[this.curr = this.next], Float2FloatArrayMap.this.value[this.next++]);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 145 */             if (this.curr == -1)
/* 146 */               throw new IllegalStateException(); 
/* 147 */             this.curr = -1;
/* 148 */             int tail = Float2FloatArrayMap.this.size-- - this.next--;
/* 149 */             System.arraycopy(Float2FloatArrayMap.this.key, this.next + 1, Float2FloatArrayMap.this.key, this.next, tail);
/* 150 */             System.arraycopy(Float2FloatArrayMap.this.value, this.next + 1, Float2FloatArrayMap.this.value, this.next, tail);
/*     */           }
/*     */         };
/*     */     }
/*     */     private EntrySet() {}
/*     */     public ObjectIterator<Float2FloatMap.Entry> fastIterator() {
/* 156 */       return new ObjectIterator<Float2FloatMap.Entry>() {
/* 157 */           int next = 0; int curr = -1;
/* 158 */           final AbstractFloat2FloatMap.BasicEntry entry = new AbstractFloat2FloatMap.BasicEntry();
/*     */           
/*     */           public boolean hasNext() {
/* 161 */             return (this.next < Float2FloatArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Float2FloatMap.Entry next() {
/* 166 */             if (!hasNext())
/* 167 */               throw new NoSuchElementException(); 
/* 168 */             this.entry.key = Float2FloatArrayMap.this.key[this.curr = this.next];
/* 169 */             this.entry.value = Float2FloatArrayMap.this.value[this.next++];
/* 170 */             return this.entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 174 */             if (this.curr == -1)
/* 175 */               throw new IllegalStateException(); 
/* 176 */             this.curr = -1;
/* 177 */             int tail = Float2FloatArrayMap.this.size-- - this.next--;
/* 178 */             System.arraycopy(Float2FloatArrayMap.this.key, this.next + 1, Float2FloatArrayMap.this.key, this.next, tail);
/* 179 */             System.arraycopy(Float2FloatArrayMap.this.value, this.next + 1, Float2FloatArrayMap.this.value, this.next, tail);
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 185 */       return Float2FloatArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 190 */       if (!(o instanceof Map.Entry))
/* 191 */         return false; 
/* 192 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 193 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 194 */         return false; 
/* 195 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 196 */         return false; 
/* 197 */       float k = ((Float)e.getKey()).floatValue();
/* 198 */       return (Float2FloatArrayMap.this.containsKey(k) && 
/* 199 */         Float.floatToIntBits(Float2FloatArrayMap.this.get(k)) == 
/* 200 */         Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 205 */       if (!(o instanceof Map.Entry))
/* 206 */         return false; 
/* 207 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 208 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 209 */         return false; 
/* 210 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 211 */         return false; 
/* 212 */       float k = ((Float)e.getKey()).floatValue();
/* 213 */       float v = ((Float)e.getValue()).floatValue();
/* 214 */       int oldPos = Float2FloatArrayMap.this.findKey(k);
/* 215 */       if (oldPos == -1 || 
/* 216 */         Float.floatToIntBits(v) != Float.floatToIntBits(Float2FloatArrayMap.this.value[oldPos]))
/* 217 */         return false; 
/* 218 */       int tail = Float2FloatArrayMap.this.size - oldPos - 1;
/* 219 */       System.arraycopy(Float2FloatArrayMap.this.key, oldPos + 1, Float2FloatArrayMap.this.key, oldPos, tail);
/* 220 */       System.arraycopy(Float2FloatArrayMap.this.value, oldPos + 1, Float2FloatArrayMap.this.value, oldPos, tail);
/* 221 */       Float2FloatArrayMap.this.size--;
/* 222 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Float2FloatMap.FastEntrySet float2FloatEntrySet() {
/* 227 */     return new EntrySet();
/*     */   }
/*     */   private int findKey(float k) {
/* 230 */     float[] key = this.key;
/* 231 */     for (int i = this.size; i-- != 0;) {
/* 232 */       if (Float.floatToIntBits(key[i]) == Float.floatToIntBits(k))
/* 233 */         return i; 
/* 234 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public float get(float k) {
/* 239 */     float[] key = this.key;
/* 240 */     for (int i = this.size; i-- != 0;) {
/* 241 */       if (Float.floatToIntBits(key[i]) == Float.floatToIntBits(k))
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
/*     */   public boolean containsKey(float k) {
/* 255 */     return (findKey(k) != -1);
/*     */   }
/*     */   
/*     */   public boolean containsValue(float v) {
/* 259 */     for (int i = this.size; i-- != 0;) {
/* 260 */       if (Float.floatToIntBits(this.value[i]) == Float.floatToIntBits(v))
/* 261 */         return true; 
/* 262 */     }  return false;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 266 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public float put(float k, float v) {
/* 271 */     int oldKey = findKey(k);
/* 272 */     if (oldKey != -1) {
/* 273 */       float oldValue = this.value[oldKey];
/* 274 */       this.value[oldKey] = v;
/* 275 */       return oldValue;
/*     */     } 
/* 277 */     if (this.size == this.key.length) {
/* 278 */       float[] newKey = new float[(this.size == 0) ? 2 : (this.size * 2)];
/* 279 */       float[] newValue = new float[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public float remove(float k) {
/* 295 */     int oldPos = findKey(k);
/* 296 */     if (oldPos == -1)
/* 297 */       return this.defRetValue; 
/* 298 */     float oldValue = this.value[oldPos];
/* 299 */     int tail = this.size - oldPos - 1;
/* 300 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 301 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 302 */     this.size--;
/* 303 */     return oldValue;
/*     */   }
/*     */   
/*     */   public FloatSet keySet() {
/* 307 */     return new AbstractFloatSet()
/*     */       {
/*     */         public boolean contains(float k) {
/* 310 */           return (Float2FloatArrayMap.this.findKey(k) != -1);
/*     */         }
/*     */         
/*     */         public boolean remove(float k) {
/* 314 */           int oldPos = Float2FloatArrayMap.this.findKey(k);
/* 315 */           if (oldPos == -1)
/* 316 */             return false; 
/* 317 */           int tail = Float2FloatArrayMap.this.size - oldPos - 1;
/* 318 */           System.arraycopy(Float2FloatArrayMap.this.key, oldPos + 1, Float2FloatArrayMap.this.key, oldPos, tail);
/* 319 */           System.arraycopy(Float2FloatArrayMap.this.value, oldPos + 1, Float2FloatArrayMap.this.value, oldPos, tail);
/* 320 */           Float2FloatArrayMap.this.size--;
/* 321 */           return true;
/*     */         }
/*     */         
/*     */         public FloatIterator iterator() {
/* 325 */           return new FloatIterator() {
/* 326 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 329 */                 return (this.pos < Float2FloatArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public float nextFloat() {
/* 334 */                 if (!hasNext())
/* 335 */                   throw new NoSuchElementException(); 
/* 336 */                 return Float2FloatArrayMap.this.key[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 340 */                 if (this.pos == 0)
/* 341 */                   throw new IllegalStateException(); 
/* 342 */                 int tail = Float2FloatArrayMap.this.size - this.pos;
/* 343 */                 System.arraycopy(Float2FloatArrayMap.this.key, this.pos, Float2FloatArrayMap.this.key, this.pos - 1, tail);
/* 344 */                 System.arraycopy(Float2FloatArrayMap.this.value, this.pos, Float2FloatArrayMap.this.value, this.pos - 1, tail);
/* 345 */                 Float2FloatArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 351 */           return Float2FloatArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 355 */           Float2FloatArrayMap.this.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public FloatCollection values() {
/* 361 */     return new AbstractFloatCollection()
/*     */       {
/*     */         public boolean contains(float v) {
/* 364 */           return Float2FloatArrayMap.this.containsValue(v);
/*     */         }
/*     */         
/*     */         public FloatIterator iterator() {
/* 368 */           return new FloatIterator() {
/* 369 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 372 */                 return (this.pos < Float2FloatArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public float nextFloat() {
/* 377 */                 if (!hasNext())
/* 378 */                   throw new NoSuchElementException(); 
/* 379 */                 return Float2FloatArrayMap.this.value[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 383 */                 if (this.pos == 0)
/* 384 */                   throw new IllegalStateException(); 
/* 385 */                 int tail = Float2FloatArrayMap.this.size - this.pos;
/* 386 */                 System.arraycopy(Float2FloatArrayMap.this.key, this.pos, Float2FloatArrayMap.this.key, this.pos - 1, tail);
/* 387 */                 System.arraycopy(Float2FloatArrayMap.this.value, this.pos, Float2FloatArrayMap.this.value, this.pos - 1, tail);
/* 388 */                 Float2FloatArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 394 */           return Float2FloatArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 398 */           Float2FloatArrayMap.this.clear();
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
/*     */   public Float2FloatArrayMap clone() {
/*     */     Float2FloatArrayMap c;
/*     */     try {
/* 417 */       c = (Float2FloatArrayMap)super.clone();
/* 418 */     } catch (CloneNotSupportedException cantHappen) {
/* 419 */       throw new InternalError();
/*     */     } 
/* 421 */     c.key = (float[])this.key.clone();
/* 422 */     c.value = (float[])this.value.clone();
/* 423 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 426 */     s.defaultWriteObject();
/* 427 */     for (int i = 0; i < this.size; i++) {
/* 428 */       s.writeFloat(this.key[i]);
/* 429 */       s.writeFloat(this.value[i]);
/*     */     } 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 433 */     s.defaultReadObject();
/* 434 */     this.key = new float[this.size];
/* 435 */     this.value = new float[this.size];
/* 436 */     for (int i = 0; i < this.size; i++) {
/* 437 */       this.key[i] = s.readFloat();
/* 438 */       this.value[i] = s.readFloat();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2FloatArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */