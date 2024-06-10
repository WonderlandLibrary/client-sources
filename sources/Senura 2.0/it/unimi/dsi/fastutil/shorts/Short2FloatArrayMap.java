/*     */ package it.unimi.dsi.fastutil.shorts;
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
/*     */ public class Short2FloatArrayMap
/*     */   extends AbstractShort2FloatMap
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient short[] key;
/*     */   private transient float[] value;
/*     */   private int size;
/*     */   
/*     */   public Short2FloatArrayMap(short[] key, float[] value) {
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
/*     */   public Short2FloatArrayMap() {
/*  67 */     this.key = ShortArrays.EMPTY_ARRAY;
/*  68 */     this.value = FloatArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Short2FloatArrayMap(int capacity) {
/*  77 */     this.key = new short[capacity];
/*  78 */     this.value = new float[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Short2FloatArrayMap(Short2FloatMap m) {
/*  87 */     this(m.size());
/*  88 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Short2FloatArrayMap(Map<? extends Short, ? extends Float> m) {
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
/*     */   public Short2FloatArrayMap(short[] key, float[] value, int size) {
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
/*     */     extends AbstractObjectSet<Short2FloatMap.Entry> implements Short2FloatMap.FastEntrySet {
/*     */     public ObjectIterator<Short2FloatMap.Entry> iterator() {
/* 130 */       return new ObjectIterator<Short2FloatMap.Entry>() {
/* 131 */           int curr = -1; int next = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 134 */             return (this.next < Short2FloatArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Short2FloatMap.Entry next() {
/* 139 */             if (!hasNext())
/* 140 */               throw new NoSuchElementException(); 
/* 141 */             return new AbstractShort2FloatMap.BasicEntry(Short2FloatArrayMap.this.key[this.curr = this.next], Short2FloatArrayMap.this.value[this.next++]);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 145 */             if (this.curr == -1)
/* 146 */               throw new IllegalStateException(); 
/* 147 */             this.curr = -1;
/* 148 */             int tail = Short2FloatArrayMap.this.size-- - this.next--;
/* 149 */             System.arraycopy(Short2FloatArrayMap.this.key, this.next + 1, Short2FloatArrayMap.this.key, this.next, tail);
/* 150 */             System.arraycopy(Short2FloatArrayMap.this.value, this.next + 1, Short2FloatArrayMap.this.value, this.next, tail);
/*     */           }
/*     */         };
/*     */     }
/*     */     private EntrySet() {}
/*     */     public ObjectIterator<Short2FloatMap.Entry> fastIterator() {
/* 156 */       return new ObjectIterator<Short2FloatMap.Entry>() {
/* 157 */           int next = 0; int curr = -1;
/* 158 */           final AbstractShort2FloatMap.BasicEntry entry = new AbstractShort2FloatMap.BasicEntry();
/*     */           
/*     */           public boolean hasNext() {
/* 161 */             return (this.next < Short2FloatArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Short2FloatMap.Entry next() {
/* 166 */             if (!hasNext())
/* 167 */               throw new NoSuchElementException(); 
/* 168 */             this.entry.key = Short2FloatArrayMap.this.key[this.curr = this.next];
/* 169 */             this.entry.value = Short2FloatArrayMap.this.value[this.next++];
/* 170 */             return this.entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 174 */             if (this.curr == -1)
/* 175 */               throw new IllegalStateException(); 
/* 176 */             this.curr = -1;
/* 177 */             int tail = Short2FloatArrayMap.this.size-- - this.next--;
/* 178 */             System.arraycopy(Short2FloatArrayMap.this.key, this.next + 1, Short2FloatArrayMap.this.key, this.next, tail);
/* 179 */             System.arraycopy(Short2FloatArrayMap.this.value, this.next + 1, Short2FloatArrayMap.this.value, this.next, tail);
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 185 */       return Short2FloatArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 190 */       if (!(o instanceof Map.Entry))
/* 191 */         return false; 
/* 192 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 193 */       if (e.getKey() == null || !(e.getKey() instanceof Short))
/* 194 */         return false; 
/* 195 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 196 */         return false; 
/* 197 */       short k = ((Short)e.getKey()).shortValue();
/* 198 */       return (Short2FloatArrayMap.this.containsKey(k) && 
/* 199 */         Float.floatToIntBits(Short2FloatArrayMap.this.get(k)) == 
/* 200 */         Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 205 */       if (!(o instanceof Map.Entry))
/* 206 */         return false; 
/* 207 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 208 */       if (e.getKey() == null || !(e.getKey() instanceof Short))
/* 209 */         return false; 
/* 210 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 211 */         return false; 
/* 212 */       short k = ((Short)e.getKey()).shortValue();
/* 213 */       float v = ((Float)e.getValue()).floatValue();
/* 214 */       int oldPos = Short2FloatArrayMap.this.findKey(k);
/* 215 */       if (oldPos == -1 || 
/* 216 */         Float.floatToIntBits(v) != Float.floatToIntBits(Short2FloatArrayMap.this.value[oldPos]))
/* 217 */         return false; 
/* 218 */       int tail = Short2FloatArrayMap.this.size - oldPos - 1;
/* 219 */       System.arraycopy(Short2FloatArrayMap.this.key, oldPos + 1, Short2FloatArrayMap.this.key, oldPos, tail);
/* 220 */       System.arraycopy(Short2FloatArrayMap.this.value, oldPos + 1, Short2FloatArrayMap.this.value, oldPos, tail);
/* 221 */       Short2FloatArrayMap.this.size--;
/* 222 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Short2FloatMap.FastEntrySet short2FloatEntrySet() {
/* 227 */     return new EntrySet();
/*     */   }
/*     */   private int findKey(short k) {
/* 230 */     short[] key = this.key;
/* 231 */     for (int i = this.size; i-- != 0;) {
/* 232 */       if (key[i] == k)
/* 233 */         return i; 
/* 234 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public float get(short k) {
/* 239 */     short[] key = this.key;
/* 240 */     for (int i = this.size; i-- != 0;) {
/* 241 */       if (key[i] == k)
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
/*     */   public boolean containsKey(short k) {
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
/*     */   public float put(short k, float v) {
/* 271 */     int oldKey = findKey(k);
/* 272 */     if (oldKey != -1) {
/* 273 */       float oldValue = this.value[oldKey];
/* 274 */       this.value[oldKey] = v;
/* 275 */       return oldValue;
/*     */     } 
/* 277 */     if (this.size == this.key.length) {
/* 278 */       short[] newKey = new short[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public float remove(short k) {
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
/*     */   public ShortSet keySet() {
/* 307 */     return new AbstractShortSet()
/*     */       {
/*     */         public boolean contains(short k) {
/* 310 */           return (Short2FloatArrayMap.this.findKey(k) != -1);
/*     */         }
/*     */         
/*     */         public boolean remove(short k) {
/* 314 */           int oldPos = Short2FloatArrayMap.this.findKey(k);
/* 315 */           if (oldPos == -1)
/* 316 */             return false; 
/* 317 */           int tail = Short2FloatArrayMap.this.size - oldPos - 1;
/* 318 */           System.arraycopy(Short2FloatArrayMap.this.key, oldPos + 1, Short2FloatArrayMap.this.key, oldPos, tail);
/* 319 */           System.arraycopy(Short2FloatArrayMap.this.value, oldPos + 1, Short2FloatArrayMap.this.value, oldPos, tail);
/* 320 */           Short2FloatArrayMap.this.size--;
/* 321 */           return true;
/*     */         }
/*     */         
/*     */         public ShortIterator iterator() {
/* 325 */           return new ShortIterator() {
/* 326 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 329 */                 return (this.pos < Short2FloatArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public short nextShort() {
/* 334 */                 if (!hasNext())
/* 335 */                   throw new NoSuchElementException(); 
/* 336 */                 return Short2FloatArrayMap.this.key[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 340 */                 if (this.pos == 0)
/* 341 */                   throw new IllegalStateException(); 
/* 342 */                 int tail = Short2FloatArrayMap.this.size - this.pos;
/* 343 */                 System.arraycopy(Short2FloatArrayMap.this.key, this.pos, Short2FloatArrayMap.this.key, this.pos - 1, tail);
/* 344 */                 System.arraycopy(Short2FloatArrayMap.this.value, this.pos, Short2FloatArrayMap.this.value, this.pos - 1, tail);
/* 345 */                 Short2FloatArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 351 */           return Short2FloatArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 355 */           Short2FloatArrayMap.this.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public FloatCollection values() {
/* 361 */     return (FloatCollection)new AbstractFloatCollection()
/*     */       {
/*     */         public boolean contains(float v) {
/* 364 */           return Short2FloatArrayMap.this.containsValue(v);
/*     */         }
/*     */         
/*     */         public FloatIterator iterator() {
/* 368 */           return new FloatIterator() {
/* 369 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 372 */                 return (this.pos < Short2FloatArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public float nextFloat() {
/* 377 */                 if (!hasNext())
/* 378 */                   throw new NoSuchElementException(); 
/* 379 */                 return Short2FloatArrayMap.this.value[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 383 */                 if (this.pos == 0)
/* 384 */                   throw new IllegalStateException(); 
/* 385 */                 int tail = Short2FloatArrayMap.this.size - this.pos;
/* 386 */                 System.arraycopy(Short2FloatArrayMap.this.key, this.pos, Short2FloatArrayMap.this.key, this.pos - 1, tail);
/* 387 */                 System.arraycopy(Short2FloatArrayMap.this.value, this.pos, Short2FloatArrayMap.this.value, this.pos - 1, tail);
/* 388 */                 Short2FloatArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 394 */           return Short2FloatArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 398 */           Short2FloatArrayMap.this.clear();
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
/*     */   public Short2FloatArrayMap clone() {
/*     */     Short2FloatArrayMap c;
/*     */     try {
/* 417 */       c = (Short2FloatArrayMap)super.clone();
/* 418 */     } catch (CloneNotSupportedException cantHappen) {
/* 419 */       throw new InternalError();
/*     */     } 
/* 421 */     c.key = (short[])this.key.clone();
/* 422 */     c.value = (float[])this.value.clone();
/* 423 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 426 */     s.defaultWriteObject();
/* 427 */     for (int i = 0; i < this.size; i++) {
/* 428 */       s.writeShort(this.key[i]);
/* 429 */       s.writeFloat(this.value[i]);
/*     */     } 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 433 */     s.defaultReadObject();
/* 434 */     this.key = new short[this.size];
/* 435 */     this.value = new float[this.size];
/* 436 */     for (int i = 0; i < this.size; i++) {
/* 437 */       this.key[i] = s.readShort();
/* 438 */       this.value[i] = s.readFloat();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\Short2FloatArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */