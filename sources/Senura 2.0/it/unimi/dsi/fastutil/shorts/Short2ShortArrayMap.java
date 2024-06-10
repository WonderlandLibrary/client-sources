/*     */ package it.unimi.dsi.fastutil.shorts;
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
/*     */ public class Short2ShortArrayMap
/*     */   extends AbstractShort2ShortMap
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient short[] key;
/*     */   private transient short[] value;
/*     */   private int size;
/*     */   
/*     */   public Short2ShortArrayMap(short[] key, short[] value) {
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
/*     */   public Short2ShortArrayMap() {
/*  67 */     this.key = ShortArrays.EMPTY_ARRAY;
/*  68 */     this.value = ShortArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Short2ShortArrayMap(int capacity) {
/*  77 */     this.key = new short[capacity];
/*  78 */     this.value = new short[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Short2ShortArrayMap(Short2ShortMap m) {
/*  87 */     this(m.size());
/*  88 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Short2ShortArrayMap(Map<? extends Short, ? extends Short> m) {
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
/*     */   public Short2ShortArrayMap(short[] key, short[] value, int size) {
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
/*     */     extends AbstractObjectSet<Short2ShortMap.Entry> implements Short2ShortMap.FastEntrySet {
/*     */     public ObjectIterator<Short2ShortMap.Entry> iterator() {
/* 130 */       return new ObjectIterator<Short2ShortMap.Entry>() {
/* 131 */           int curr = -1; int next = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 134 */             return (this.next < Short2ShortArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Short2ShortMap.Entry next() {
/* 139 */             if (!hasNext())
/* 140 */               throw new NoSuchElementException(); 
/* 141 */             return new AbstractShort2ShortMap.BasicEntry(Short2ShortArrayMap.this.key[this.curr = this.next], Short2ShortArrayMap.this.value[this.next++]);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 145 */             if (this.curr == -1)
/* 146 */               throw new IllegalStateException(); 
/* 147 */             this.curr = -1;
/* 148 */             int tail = Short2ShortArrayMap.this.size-- - this.next--;
/* 149 */             System.arraycopy(Short2ShortArrayMap.this.key, this.next + 1, Short2ShortArrayMap.this.key, this.next, tail);
/* 150 */             System.arraycopy(Short2ShortArrayMap.this.value, this.next + 1, Short2ShortArrayMap.this.value, this.next, tail);
/*     */           }
/*     */         };
/*     */     }
/*     */     private EntrySet() {}
/*     */     public ObjectIterator<Short2ShortMap.Entry> fastIterator() {
/* 156 */       return new ObjectIterator<Short2ShortMap.Entry>() {
/* 157 */           int next = 0; int curr = -1;
/* 158 */           final AbstractShort2ShortMap.BasicEntry entry = new AbstractShort2ShortMap.BasicEntry();
/*     */           
/*     */           public boolean hasNext() {
/* 161 */             return (this.next < Short2ShortArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Short2ShortMap.Entry next() {
/* 166 */             if (!hasNext())
/* 167 */               throw new NoSuchElementException(); 
/* 168 */             this.entry.key = Short2ShortArrayMap.this.key[this.curr = this.next];
/* 169 */             this.entry.value = Short2ShortArrayMap.this.value[this.next++];
/* 170 */             return this.entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 174 */             if (this.curr == -1)
/* 175 */               throw new IllegalStateException(); 
/* 176 */             this.curr = -1;
/* 177 */             int tail = Short2ShortArrayMap.this.size-- - this.next--;
/* 178 */             System.arraycopy(Short2ShortArrayMap.this.key, this.next + 1, Short2ShortArrayMap.this.key, this.next, tail);
/* 179 */             System.arraycopy(Short2ShortArrayMap.this.value, this.next + 1, Short2ShortArrayMap.this.value, this.next, tail);
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 185 */       return Short2ShortArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 190 */       if (!(o instanceof Map.Entry))
/* 191 */         return false; 
/* 192 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 193 */       if (e.getKey() == null || !(e.getKey() instanceof Short))
/* 194 */         return false; 
/* 195 */       if (e.getValue() == null || !(e.getValue() instanceof Short))
/* 196 */         return false; 
/* 197 */       short k = ((Short)e.getKey()).shortValue();
/* 198 */       return (Short2ShortArrayMap.this.containsKey(k) && Short2ShortArrayMap.this
/* 199 */         .get(k) == ((Short)e.getValue()).shortValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 204 */       if (!(o instanceof Map.Entry))
/* 205 */         return false; 
/* 206 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 207 */       if (e.getKey() == null || !(e.getKey() instanceof Short))
/* 208 */         return false; 
/* 209 */       if (e.getValue() == null || !(e.getValue() instanceof Short))
/* 210 */         return false; 
/* 211 */       short k = ((Short)e.getKey()).shortValue();
/* 212 */       short v = ((Short)e.getValue()).shortValue();
/* 213 */       int oldPos = Short2ShortArrayMap.this.findKey(k);
/* 214 */       if (oldPos == -1 || v != Short2ShortArrayMap.this.value[oldPos])
/* 215 */         return false; 
/* 216 */       int tail = Short2ShortArrayMap.this.size - oldPos - 1;
/* 217 */       System.arraycopy(Short2ShortArrayMap.this.key, oldPos + 1, Short2ShortArrayMap.this.key, oldPos, tail);
/* 218 */       System.arraycopy(Short2ShortArrayMap.this.value, oldPos + 1, Short2ShortArrayMap.this.value, oldPos, tail);
/* 219 */       Short2ShortArrayMap.this.size--;
/* 220 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Short2ShortMap.FastEntrySet short2ShortEntrySet() {
/* 225 */     return new EntrySet();
/*     */   }
/*     */   private int findKey(short k) {
/* 228 */     short[] key = this.key;
/* 229 */     for (int i = this.size; i-- != 0;) {
/* 230 */       if (key[i] == k)
/* 231 */         return i; 
/* 232 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public short get(short k) {
/* 237 */     short[] key = this.key;
/* 238 */     for (int i = this.size; i-- != 0;) {
/* 239 */       if (key[i] == k)
/* 240 */         return this.value[i]; 
/* 241 */     }  return this.defRetValue;
/*     */   }
/*     */   
/*     */   public int size() {
/* 245 */     return this.size;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 249 */     this.size = 0;
/*     */   }
/*     */   
/*     */   public boolean containsKey(short k) {
/* 253 */     return (findKey(k) != -1);
/*     */   }
/*     */   
/*     */   public boolean containsValue(short v) {
/* 257 */     for (int i = this.size; i-- != 0;) {
/* 258 */       if (this.value[i] == v)
/* 259 */         return true; 
/* 260 */     }  return false;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 264 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public short put(short k, short v) {
/* 269 */     int oldKey = findKey(k);
/* 270 */     if (oldKey != -1) {
/* 271 */       short oldValue = this.value[oldKey];
/* 272 */       this.value[oldKey] = v;
/* 273 */       return oldValue;
/*     */     } 
/* 275 */     if (this.size == this.key.length) {
/* 276 */       short[] newKey = new short[(this.size == 0) ? 2 : (this.size * 2)];
/* 277 */       short[] newValue = new short[(this.size == 0) ? 2 : (this.size * 2)];
/* 278 */       for (int i = this.size; i-- != 0; ) {
/* 279 */         newKey[i] = this.key[i];
/* 280 */         newValue[i] = this.value[i];
/*     */       } 
/* 282 */       this.key = newKey;
/* 283 */       this.value = newValue;
/*     */     } 
/* 285 */     this.key[this.size] = k;
/* 286 */     this.value[this.size] = v;
/* 287 */     this.size++;
/* 288 */     return this.defRetValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public short remove(short k) {
/* 293 */     int oldPos = findKey(k);
/* 294 */     if (oldPos == -1)
/* 295 */       return this.defRetValue; 
/* 296 */     short oldValue = this.value[oldPos];
/* 297 */     int tail = this.size - oldPos - 1;
/* 298 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 299 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 300 */     this.size--;
/* 301 */     return oldValue;
/*     */   }
/*     */   
/*     */   public ShortSet keySet() {
/* 305 */     return new AbstractShortSet()
/*     */       {
/*     */         public boolean contains(short k) {
/* 308 */           return (Short2ShortArrayMap.this.findKey(k) != -1);
/*     */         }
/*     */         
/*     */         public boolean remove(short k) {
/* 312 */           int oldPos = Short2ShortArrayMap.this.findKey(k);
/* 313 */           if (oldPos == -1)
/* 314 */             return false; 
/* 315 */           int tail = Short2ShortArrayMap.this.size - oldPos - 1;
/* 316 */           System.arraycopy(Short2ShortArrayMap.this.key, oldPos + 1, Short2ShortArrayMap.this.key, oldPos, tail);
/* 317 */           System.arraycopy(Short2ShortArrayMap.this.value, oldPos + 1, Short2ShortArrayMap.this.value, oldPos, tail);
/* 318 */           Short2ShortArrayMap.this.size--;
/* 319 */           return true;
/*     */         }
/*     */         
/*     */         public ShortIterator iterator() {
/* 323 */           return new ShortIterator() {
/* 324 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 327 */                 return (this.pos < Short2ShortArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public short nextShort() {
/* 332 */                 if (!hasNext())
/* 333 */                   throw new NoSuchElementException(); 
/* 334 */                 return Short2ShortArrayMap.this.key[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 338 */                 if (this.pos == 0)
/* 339 */                   throw new IllegalStateException(); 
/* 340 */                 int tail = Short2ShortArrayMap.this.size - this.pos;
/* 341 */                 System.arraycopy(Short2ShortArrayMap.this.key, this.pos, Short2ShortArrayMap.this.key, this.pos - 1, tail);
/* 342 */                 System.arraycopy(Short2ShortArrayMap.this.value, this.pos, Short2ShortArrayMap.this.value, this.pos - 1, tail);
/* 343 */                 Short2ShortArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 349 */           return Short2ShortArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 353 */           Short2ShortArrayMap.this.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public ShortCollection values() {
/* 359 */     return new AbstractShortCollection()
/*     */       {
/*     */         public boolean contains(short v) {
/* 362 */           return Short2ShortArrayMap.this.containsValue(v);
/*     */         }
/*     */         
/*     */         public ShortIterator iterator() {
/* 366 */           return new ShortIterator() {
/* 367 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 370 */                 return (this.pos < Short2ShortArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public short nextShort() {
/* 375 */                 if (!hasNext())
/* 376 */                   throw new NoSuchElementException(); 
/* 377 */                 return Short2ShortArrayMap.this.value[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 381 */                 if (this.pos == 0)
/* 382 */                   throw new IllegalStateException(); 
/* 383 */                 int tail = Short2ShortArrayMap.this.size - this.pos;
/* 384 */                 System.arraycopy(Short2ShortArrayMap.this.key, this.pos, Short2ShortArrayMap.this.key, this.pos - 1, tail);
/* 385 */                 System.arraycopy(Short2ShortArrayMap.this.value, this.pos, Short2ShortArrayMap.this.value, this.pos - 1, tail);
/* 386 */                 Short2ShortArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 392 */           return Short2ShortArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 396 */           Short2ShortArrayMap.this.clear();
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
/*     */   public Short2ShortArrayMap clone() {
/*     */     Short2ShortArrayMap c;
/*     */     try {
/* 415 */       c = (Short2ShortArrayMap)super.clone();
/* 416 */     } catch (CloneNotSupportedException cantHappen) {
/* 417 */       throw new InternalError();
/*     */     } 
/* 419 */     c.key = (short[])this.key.clone();
/* 420 */     c.value = (short[])this.value.clone();
/* 421 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 424 */     s.defaultWriteObject();
/* 425 */     for (int i = 0; i < this.size; i++) {
/* 426 */       s.writeShort(this.key[i]);
/* 427 */       s.writeShort(this.value[i]);
/*     */     } 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 431 */     s.defaultReadObject();
/* 432 */     this.key = new short[this.size];
/* 433 */     this.value = new short[this.size];
/* 434 */     for (int i = 0; i < this.size; i++) {
/* 435 */       this.key[i] = s.readShort();
/* 436 */       this.value[i] = s.readShort();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\Short2ShortArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */