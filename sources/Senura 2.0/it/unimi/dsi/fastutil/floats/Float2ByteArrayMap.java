/*     */ package it.unimi.dsi.fastutil.floats;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteArrays;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteCollection;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteIterator;
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
/*     */ public class Float2ByteArrayMap
/*     */   extends AbstractFloat2ByteMap
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient float[] key;
/*     */   private transient byte[] value;
/*     */   private int size;
/*     */   
/*     */   public Float2ByteArrayMap(float[] key, byte[] value) {
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
/*     */   public Float2ByteArrayMap() {
/*  67 */     this.key = FloatArrays.EMPTY_ARRAY;
/*  68 */     this.value = ByteArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float2ByteArrayMap(int capacity) {
/*  77 */     this.key = new float[capacity];
/*  78 */     this.value = new byte[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float2ByteArrayMap(Float2ByteMap m) {
/*  87 */     this(m.size());
/*  88 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float2ByteArrayMap(Map<? extends Float, ? extends Byte> m) {
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
/*     */   public Float2ByteArrayMap(float[] key, byte[] value, int size) {
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
/*     */     extends AbstractObjectSet<Float2ByteMap.Entry> implements Float2ByteMap.FastEntrySet {
/*     */     public ObjectIterator<Float2ByteMap.Entry> iterator() {
/* 130 */       return new ObjectIterator<Float2ByteMap.Entry>() {
/* 131 */           int curr = -1; int next = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 134 */             return (this.next < Float2ByteArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Float2ByteMap.Entry next() {
/* 139 */             if (!hasNext())
/* 140 */               throw new NoSuchElementException(); 
/* 141 */             return new AbstractFloat2ByteMap.BasicEntry(Float2ByteArrayMap.this.key[this.curr = this.next], Float2ByteArrayMap.this.value[this.next++]);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 145 */             if (this.curr == -1)
/* 146 */               throw new IllegalStateException(); 
/* 147 */             this.curr = -1;
/* 148 */             int tail = Float2ByteArrayMap.this.size-- - this.next--;
/* 149 */             System.arraycopy(Float2ByteArrayMap.this.key, this.next + 1, Float2ByteArrayMap.this.key, this.next, tail);
/* 150 */             System.arraycopy(Float2ByteArrayMap.this.value, this.next + 1, Float2ByteArrayMap.this.value, this.next, tail);
/*     */           }
/*     */         };
/*     */     }
/*     */     private EntrySet() {}
/*     */     public ObjectIterator<Float2ByteMap.Entry> fastIterator() {
/* 156 */       return new ObjectIterator<Float2ByteMap.Entry>() {
/* 157 */           int next = 0; int curr = -1;
/* 158 */           final AbstractFloat2ByteMap.BasicEntry entry = new AbstractFloat2ByteMap.BasicEntry();
/*     */           
/*     */           public boolean hasNext() {
/* 161 */             return (this.next < Float2ByteArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Float2ByteMap.Entry next() {
/* 166 */             if (!hasNext())
/* 167 */               throw new NoSuchElementException(); 
/* 168 */             this.entry.key = Float2ByteArrayMap.this.key[this.curr = this.next];
/* 169 */             this.entry.value = Float2ByteArrayMap.this.value[this.next++];
/* 170 */             return this.entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 174 */             if (this.curr == -1)
/* 175 */               throw new IllegalStateException(); 
/* 176 */             this.curr = -1;
/* 177 */             int tail = Float2ByteArrayMap.this.size-- - this.next--;
/* 178 */             System.arraycopy(Float2ByteArrayMap.this.key, this.next + 1, Float2ByteArrayMap.this.key, this.next, tail);
/* 179 */             System.arraycopy(Float2ByteArrayMap.this.value, this.next + 1, Float2ByteArrayMap.this.value, this.next, tail);
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 185 */       return Float2ByteArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 190 */       if (!(o instanceof Map.Entry))
/* 191 */         return false; 
/* 192 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 193 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 194 */         return false; 
/* 195 */       if (e.getValue() == null || !(e.getValue() instanceof Byte))
/* 196 */         return false; 
/* 197 */       float k = ((Float)e.getKey()).floatValue();
/* 198 */       return (Float2ByteArrayMap.this.containsKey(k) && Float2ByteArrayMap.this
/* 199 */         .get(k) == ((Byte)e.getValue()).byteValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 204 */       if (!(o instanceof Map.Entry))
/* 205 */         return false; 
/* 206 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 207 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 208 */         return false; 
/* 209 */       if (e.getValue() == null || !(e.getValue() instanceof Byte))
/* 210 */         return false; 
/* 211 */       float k = ((Float)e.getKey()).floatValue();
/* 212 */       byte v = ((Byte)e.getValue()).byteValue();
/* 213 */       int oldPos = Float2ByteArrayMap.this.findKey(k);
/* 214 */       if (oldPos == -1 || v != Float2ByteArrayMap.this.value[oldPos])
/* 215 */         return false; 
/* 216 */       int tail = Float2ByteArrayMap.this.size - oldPos - 1;
/* 217 */       System.arraycopy(Float2ByteArrayMap.this.key, oldPos + 1, Float2ByteArrayMap.this.key, oldPos, tail);
/* 218 */       System.arraycopy(Float2ByteArrayMap.this.value, oldPos + 1, Float2ByteArrayMap.this.value, oldPos, tail);
/* 219 */       Float2ByteArrayMap.this.size--;
/* 220 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Float2ByteMap.FastEntrySet float2ByteEntrySet() {
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
/*     */   public byte get(float k) {
/* 237 */     float[] key = this.key;
/* 238 */     for (int i = this.size; i-- != 0;) {
/* 239 */       if (Float.floatToIntBits(key[i]) == Float.floatToIntBits(k))
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
/*     */   public boolean containsKey(float k) {
/* 253 */     return (findKey(k) != -1);
/*     */   }
/*     */   
/*     */   public boolean containsValue(byte v) {
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
/*     */   public byte put(float k, byte v) {
/* 269 */     int oldKey = findKey(k);
/* 270 */     if (oldKey != -1) {
/* 271 */       byte oldValue = this.value[oldKey];
/* 272 */       this.value[oldKey] = v;
/* 273 */       return oldValue;
/*     */     } 
/* 275 */     if (this.size == this.key.length) {
/* 276 */       float[] newKey = new float[(this.size == 0) ? 2 : (this.size * 2)];
/* 277 */       byte[] newValue = new byte[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public byte remove(float k) {
/* 293 */     int oldPos = findKey(k);
/* 294 */     if (oldPos == -1)
/* 295 */       return this.defRetValue; 
/* 296 */     byte oldValue = this.value[oldPos];
/* 297 */     int tail = this.size - oldPos - 1;
/* 298 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 299 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 300 */     this.size--;
/* 301 */     return oldValue;
/*     */   }
/*     */   
/*     */   public FloatSet keySet() {
/* 305 */     return new AbstractFloatSet()
/*     */       {
/*     */         public boolean contains(float k) {
/* 308 */           return (Float2ByteArrayMap.this.findKey(k) != -1);
/*     */         }
/*     */         
/*     */         public boolean remove(float k) {
/* 312 */           int oldPos = Float2ByteArrayMap.this.findKey(k);
/* 313 */           if (oldPos == -1)
/* 314 */             return false; 
/* 315 */           int tail = Float2ByteArrayMap.this.size - oldPos - 1;
/* 316 */           System.arraycopy(Float2ByteArrayMap.this.key, oldPos + 1, Float2ByteArrayMap.this.key, oldPos, tail);
/* 317 */           System.arraycopy(Float2ByteArrayMap.this.value, oldPos + 1, Float2ByteArrayMap.this.value, oldPos, tail);
/* 318 */           Float2ByteArrayMap.this.size--;
/* 319 */           return true;
/*     */         }
/*     */         
/*     */         public FloatIterator iterator() {
/* 323 */           return new FloatIterator() {
/* 324 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 327 */                 return (this.pos < Float2ByteArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public float nextFloat() {
/* 332 */                 if (!hasNext())
/* 333 */                   throw new NoSuchElementException(); 
/* 334 */                 return Float2ByteArrayMap.this.key[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 338 */                 if (this.pos == 0)
/* 339 */                   throw new IllegalStateException(); 
/* 340 */                 int tail = Float2ByteArrayMap.this.size - this.pos;
/* 341 */                 System.arraycopy(Float2ByteArrayMap.this.key, this.pos, Float2ByteArrayMap.this.key, this.pos - 1, tail);
/* 342 */                 System.arraycopy(Float2ByteArrayMap.this.value, this.pos, Float2ByteArrayMap.this.value, this.pos - 1, tail);
/* 343 */                 Float2ByteArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 349 */           return Float2ByteArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 353 */           Float2ByteArrayMap.this.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public ByteCollection values() {
/* 359 */     return (ByteCollection)new AbstractByteCollection()
/*     */       {
/*     */         public boolean contains(byte v) {
/* 362 */           return Float2ByteArrayMap.this.containsValue(v);
/*     */         }
/*     */         
/*     */         public ByteIterator iterator() {
/* 366 */           return new ByteIterator() {
/* 367 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 370 */                 return (this.pos < Float2ByteArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public byte nextByte() {
/* 375 */                 if (!hasNext())
/* 376 */                   throw new NoSuchElementException(); 
/* 377 */                 return Float2ByteArrayMap.this.value[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 381 */                 if (this.pos == 0)
/* 382 */                   throw new IllegalStateException(); 
/* 383 */                 int tail = Float2ByteArrayMap.this.size - this.pos;
/* 384 */                 System.arraycopy(Float2ByteArrayMap.this.key, this.pos, Float2ByteArrayMap.this.key, this.pos - 1, tail);
/* 385 */                 System.arraycopy(Float2ByteArrayMap.this.value, this.pos, Float2ByteArrayMap.this.value, this.pos - 1, tail);
/* 386 */                 Float2ByteArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 392 */           return Float2ByteArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 396 */           Float2ByteArrayMap.this.clear();
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
/*     */   public Float2ByteArrayMap clone() {
/*     */     Float2ByteArrayMap c;
/*     */     try {
/* 415 */       c = (Float2ByteArrayMap)super.clone();
/* 416 */     } catch (CloneNotSupportedException cantHappen) {
/* 417 */       throw new InternalError();
/*     */     } 
/* 419 */     c.key = (float[])this.key.clone();
/* 420 */     c.value = (byte[])this.value.clone();
/* 421 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 424 */     s.defaultWriteObject();
/* 425 */     for (int i = 0; i < this.size; i++) {
/* 426 */       s.writeFloat(this.key[i]);
/* 427 */       s.writeByte(this.value[i]);
/*     */     } 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 431 */     s.defaultReadObject();
/* 432 */     this.key = new float[this.size];
/* 433 */     this.value = new byte[this.size];
/* 434 */     for (int i = 0; i < this.size; i++) {
/* 435 */       this.key[i] = s.readFloat();
/* 436 */       this.value[i] = s.readByte();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2ByteArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */