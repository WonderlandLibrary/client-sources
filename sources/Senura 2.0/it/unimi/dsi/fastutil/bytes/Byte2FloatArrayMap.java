/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public class Byte2FloatArrayMap
/*     */   extends AbstractByte2FloatMap
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient byte[] key;
/*     */   private transient float[] value;
/*     */   private int size;
/*     */   
/*     */   public Byte2FloatArrayMap(byte[] key, float[] value) {
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
/*     */   public Byte2FloatArrayMap() {
/*  67 */     this.key = ByteArrays.EMPTY_ARRAY;
/*  68 */     this.value = FloatArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Byte2FloatArrayMap(int capacity) {
/*  77 */     this.key = new byte[capacity];
/*  78 */     this.value = new float[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Byte2FloatArrayMap(Byte2FloatMap m) {
/*  87 */     this(m.size());
/*  88 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Byte2FloatArrayMap(Map<? extends Byte, ? extends Float> m) {
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
/*     */   public Byte2FloatArrayMap(byte[] key, float[] value, int size) {
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
/*     */     extends AbstractObjectSet<Byte2FloatMap.Entry> implements Byte2FloatMap.FastEntrySet {
/*     */     public ObjectIterator<Byte2FloatMap.Entry> iterator() {
/* 130 */       return new ObjectIterator<Byte2FloatMap.Entry>() {
/* 131 */           int curr = -1; int next = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 134 */             return (this.next < Byte2FloatArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Byte2FloatMap.Entry next() {
/* 139 */             if (!hasNext())
/* 140 */               throw new NoSuchElementException(); 
/* 141 */             return new AbstractByte2FloatMap.BasicEntry(Byte2FloatArrayMap.this.key[this.curr = this.next], Byte2FloatArrayMap.this.value[this.next++]);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 145 */             if (this.curr == -1)
/* 146 */               throw new IllegalStateException(); 
/* 147 */             this.curr = -1;
/* 148 */             int tail = Byte2FloatArrayMap.this.size-- - this.next--;
/* 149 */             System.arraycopy(Byte2FloatArrayMap.this.key, this.next + 1, Byte2FloatArrayMap.this.key, this.next, tail);
/* 150 */             System.arraycopy(Byte2FloatArrayMap.this.value, this.next + 1, Byte2FloatArrayMap.this.value, this.next, tail);
/*     */           }
/*     */         };
/*     */     }
/*     */     private EntrySet() {}
/*     */     public ObjectIterator<Byte2FloatMap.Entry> fastIterator() {
/* 156 */       return new ObjectIterator<Byte2FloatMap.Entry>() {
/* 157 */           int next = 0; int curr = -1;
/* 158 */           final AbstractByte2FloatMap.BasicEntry entry = new AbstractByte2FloatMap.BasicEntry();
/*     */           
/*     */           public boolean hasNext() {
/* 161 */             return (this.next < Byte2FloatArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Byte2FloatMap.Entry next() {
/* 166 */             if (!hasNext())
/* 167 */               throw new NoSuchElementException(); 
/* 168 */             this.entry.key = Byte2FloatArrayMap.this.key[this.curr = this.next];
/* 169 */             this.entry.value = Byte2FloatArrayMap.this.value[this.next++];
/* 170 */             return this.entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 174 */             if (this.curr == -1)
/* 175 */               throw new IllegalStateException(); 
/* 176 */             this.curr = -1;
/* 177 */             int tail = Byte2FloatArrayMap.this.size-- - this.next--;
/* 178 */             System.arraycopy(Byte2FloatArrayMap.this.key, this.next + 1, Byte2FloatArrayMap.this.key, this.next, tail);
/* 179 */             System.arraycopy(Byte2FloatArrayMap.this.value, this.next + 1, Byte2FloatArrayMap.this.value, this.next, tail);
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 185 */       return Byte2FloatArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 190 */       if (!(o instanceof Map.Entry))
/* 191 */         return false; 
/* 192 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 193 */       if (e.getKey() == null || !(e.getKey() instanceof Byte))
/* 194 */         return false; 
/* 195 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 196 */         return false; 
/* 197 */       byte k = ((Byte)e.getKey()).byteValue();
/* 198 */       return (Byte2FloatArrayMap.this.containsKey(k) && 
/* 199 */         Float.floatToIntBits(Byte2FloatArrayMap.this.get(k)) == 
/* 200 */         Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 205 */       if (!(o instanceof Map.Entry))
/* 206 */         return false; 
/* 207 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 208 */       if (e.getKey() == null || !(e.getKey() instanceof Byte))
/* 209 */         return false; 
/* 210 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 211 */         return false; 
/* 212 */       byte k = ((Byte)e.getKey()).byteValue();
/* 213 */       float v = ((Float)e.getValue()).floatValue();
/* 214 */       int oldPos = Byte2FloatArrayMap.this.findKey(k);
/* 215 */       if (oldPos == -1 || 
/* 216 */         Float.floatToIntBits(v) != Float.floatToIntBits(Byte2FloatArrayMap.this.value[oldPos]))
/* 217 */         return false; 
/* 218 */       int tail = Byte2FloatArrayMap.this.size - oldPos - 1;
/* 219 */       System.arraycopy(Byte2FloatArrayMap.this.key, oldPos + 1, Byte2FloatArrayMap.this.key, oldPos, tail);
/* 220 */       System.arraycopy(Byte2FloatArrayMap.this.value, oldPos + 1, Byte2FloatArrayMap.this.value, oldPos, tail);
/* 221 */       Byte2FloatArrayMap.this.size--;
/* 222 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Byte2FloatMap.FastEntrySet byte2FloatEntrySet() {
/* 227 */     return new EntrySet();
/*     */   }
/*     */   private int findKey(byte k) {
/* 230 */     byte[] key = this.key;
/* 231 */     for (int i = this.size; i-- != 0;) {
/* 232 */       if (key[i] == k)
/* 233 */         return i; 
/* 234 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public float get(byte k) {
/* 239 */     byte[] key = this.key;
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
/*     */   public boolean containsKey(byte k) {
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
/*     */   public float put(byte k, float v) {
/* 271 */     int oldKey = findKey(k);
/* 272 */     if (oldKey != -1) {
/* 273 */       float oldValue = this.value[oldKey];
/* 274 */       this.value[oldKey] = v;
/* 275 */       return oldValue;
/*     */     } 
/* 277 */     if (this.size == this.key.length) {
/* 278 */       byte[] newKey = new byte[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public float remove(byte k) {
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
/*     */   public ByteSet keySet() {
/* 307 */     return new AbstractByteSet()
/*     */       {
/*     */         public boolean contains(byte k) {
/* 310 */           return (Byte2FloatArrayMap.this.findKey(k) != -1);
/*     */         }
/*     */         
/*     */         public boolean remove(byte k) {
/* 314 */           int oldPos = Byte2FloatArrayMap.this.findKey(k);
/* 315 */           if (oldPos == -1)
/* 316 */             return false; 
/* 317 */           int tail = Byte2FloatArrayMap.this.size - oldPos - 1;
/* 318 */           System.arraycopy(Byte2FloatArrayMap.this.key, oldPos + 1, Byte2FloatArrayMap.this.key, oldPos, tail);
/* 319 */           System.arraycopy(Byte2FloatArrayMap.this.value, oldPos + 1, Byte2FloatArrayMap.this.value, oldPos, tail);
/* 320 */           Byte2FloatArrayMap.this.size--;
/* 321 */           return true;
/*     */         }
/*     */         
/*     */         public ByteIterator iterator() {
/* 325 */           return new ByteIterator() {
/* 326 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 329 */                 return (this.pos < Byte2FloatArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public byte nextByte() {
/* 334 */                 if (!hasNext())
/* 335 */                   throw new NoSuchElementException(); 
/* 336 */                 return Byte2FloatArrayMap.this.key[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 340 */                 if (this.pos == 0)
/* 341 */                   throw new IllegalStateException(); 
/* 342 */                 int tail = Byte2FloatArrayMap.this.size - this.pos;
/* 343 */                 System.arraycopy(Byte2FloatArrayMap.this.key, this.pos, Byte2FloatArrayMap.this.key, this.pos - 1, tail);
/* 344 */                 System.arraycopy(Byte2FloatArrayMap.this.value, this.pos, Byte2FloatArrayMap.this.value, this.pos - 1, tail);
/* 345 */                 Byte2FloatArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 351 */           return Byte2FloatArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 355 */           Byte2FloatArrayMap.this.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public FloatCollection values() {
/* 361 */     return (FloatCollection)new AbstractFloatCollection()
/*     */       {
/*     */         public boolean contains(float v) {
/* 364 */           return Byte2FloatArrayMap.this.containsValue(v);
/*     */         }
/*     */         
/*     */         public FloatIterator iterator() {
/* 368 */           return new FloatIterator() {
/* 369 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 372 */                 return (this.pos < Byte2FloatArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public float nextFloat() {
/* 377 */                 if (!hasNext())
/* 378 */                   throw new NoSuchElementException(); 
/* 379 */                 return Byte2FloatArrayMap.this.value[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 383 */                 if (this.pos == 0)
/* 384 */                   throw new IllegalStateException(); 
/* 385 */                 int tail = Byte2FloatArrayMap.this.size - this.pos;
/* 386 */                 System.arraycopy(Byte2FloatArrayMap.this.key, this.pos, Byte2FloatArrayMap.this.key, this.pos - 1, tail);
/* 387 */                 System.arraycopy(Byte2FloatArrayMap.this.value, this.pos, Byte2FloatArrayMap.this.value, this.pos - 1, tail);
/* 388 */                 Byte2FloatArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 394 */           return Byte2FloatArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 398 */           Byte2FloatArrayMap.this.clear();
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
/*     */   public Byte2FloatArrayMap clone() {
/*     */     Byte2FloatArrayMap c;
/*     */     try {
/* 417 */       c = (Byte2FloatArrayMap)super.clone();
/* 418 */     } catch (CloneNotSupportedException cantHappen) {
/* 419 */       throw new InternalError();
/*     */     } 
/* 421 */     c.key = (byte[])this.key.clone();
/* 422 */     c.value = (float[])this.value.clone();
/* 423 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 426 */     s.defaultWriteObject();
/* 427 */     for (int i = 0; i < this.size; i++) {
/* 428 */       s.writeByte(this.key[i]);
/* 429 */       s.writeFloat(this.value[i]);
/*     */     } 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 433 */     s.defaultReadObject();
/* 434 */     this.key = new byte[this.size];
/* 435 */     this.value = new float[this.size];
/* 436 */     for (int i = 0; i < this.size; i++) {
/* 437 */       this.key[i] = s.readByte();
/* 438 */       this.value[i] = s.readFloat();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\Byte2FloatArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */