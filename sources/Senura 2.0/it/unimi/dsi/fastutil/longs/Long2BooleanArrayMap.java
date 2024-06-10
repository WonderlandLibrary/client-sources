/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanArrays;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
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
/*     */ public class Long2BooleanArrayMap
/*     */   extends AbstractLong2BooleanMap
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient long[] key;
/*     */   private transient boolean[] value;
/*     */   private int size;
/*     */   
/*     */   public Long2BooleanArrayMap(long[] key, boolean[] value) {
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
/*     */   public Long2BooleanArrayMap() {
/*  67 */     this.key = LongArrays.EMPTY_ARRAY;
/*  68 */     this.value = BooleanArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long2BooleanArrayMap(int capacity) {
/*  77 */     this.key = new long[capacity];
/*  78 */     this.value = new boolean[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long2BooleanArrayMap(Long2BooleanMap m) {
/*  87 */     this(m.size());
/*  88 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long2BooleanArrayMap(Map<? extends Long, ? extends Boolean> m) {
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
/*     */   public Long2BooleanArrayMap(long[] key, boolean[] value, int size) {
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
/*     */     extends AbstractObjectSet<Long2BooleanMap.Entry> implements Long2BooleanMap.FastEntrySet {
/*     */     public ObjectIterator<Long2BooleanMap.Entry> iterator() {
/* 130 */       return new ObjectIterator<Long2BooleanMap.Entry>() {
/* 131 */           int curr = -1; int next = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 134 */             return (this.next < Long2BooleanArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Long2BooleanMap.Entry next() {
/* 139 */             if (!hasNext())
/* 140 */               throw new NoSuchElementException(); 
/* 141 */             return new AbstractLong2BooleanMap.BasicEntry(Long2BooleanArrayMap.this.key[this.curr = this.next], Long2BooleanArrayMap.this.value[this.next++]);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 145 */             if (this.curr == -1)
/* 146 */               throw new IllegalStateException(); 
/* 147 */             this.curr = -1;
/* 148 */             int tail = Long2BooleanArrayMap.this.size-- - this.next--;
/* 149 */             System.arraycopy(Long2BooleanArrayMap.this.key, this.next + 1, Long2BooleanArrayMap.this.key, this.next, tail);
/* 150 */             System.arraycopy(Long2BooleanArrayMap.this.value, this.next + 1, Long2BooleanArrayMap.this.value, this.next, tail);
/*     */           }
/*     */         };
/*     */     }
/*     */     private EntrySet() {}
/*     */     public ObjectIterator<Long2BooleanMap.Entry> fastIterator() {
/* 156 */       return new ObjectIterator<Long2BooleanMap.Entry>() {
/* 157 */           int next = 0; int curr = -1;
/* 158 */           final AbstractLong2BooleanMap.BasicEntry entry = new AbstractLong2BooleanMap.BasicEntry();
/*     */           
/*     */           public boolean hasNext() {
/* 161 */             return (this.next < Long2BooleanArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Long2BooleanMap.Entry next() {
/* 166 */             if (!hasNext())
/* 167 */               throw new NoSuchElementException(); 
/* 168 */             this.entry.key = Long2BooleanArrayMap.this.key[this.curr = this.next];
/* 169 */             this.entry.value = Long2BooleanArrayMap.this.value[this.next++];
/* 170 */             return this.entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 174 */             if (this.curr == -1)
/* 175 */               throw new IllegalStateException(); 
/* 176 */             this.curr = -1;
/* 177 */             int tail = Long2BooleanArrayMap.this.size-- - this.next--;
/* 178 */             System.arraycopy(Long2BooleanArrayMap.this.key, this.next + 1, Long2BooleanArrayMap.this.key, this.next, tail);
/* 179 */             System.arraycopy(Long2BooleanArrayMap.this.value, this.next + 1, Long2BooleanArrayMap.this.value, this.next, tail);
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 185 */       return Long2BooleanArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 190 */       if (!(o instanceof Map.Entry))
/* 191 */         return false; 
/* 192 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 193 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/* 194 */         return false; 
/* 195 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 196 */         return false; 
/* 197 */       long k = ((Long)e.getKey()).longValue();
/* 198 */       return (Long2BooleanArrayMap.this.containsKey(k) && Long2BooleanArrayMap.this
/* 199 */         .get(k) == ((Boolean)e.getValue()).booleanValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 204 */       if (!(o instanceof Map.Entry))
/* 205 */         return false; 
/* 206 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 207 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/* 208 */         return false; 
/* 209 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 210 */         return false; 
/* 211 */       long k = ((Long)e.getKey()).longValue();
/* 212 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/* 213 */       int oldPos = Long2BooleanArrayMap.this.findKey(k);
/* 214 */       if (oldPos == -1 || v != Long2BooleanArrayMap.this.value[oldPos])
/* 215 */         return false; 
/* 216 */       int tail = Long2BooleanArrayMap.this.size - oldPos - 1;
/* 217 */       System.arraycopy(Long2BooleanArrayMap.this.key, oldPos + 1, Long2BooleanArrayMap.this.key, oldPos, tail);
/* 218 */       System.arraycopy(Long2BooleanArrayMap.this.value, oldPos + 1, Long2BooleanArrayMap.this.value, oldPos, tail);
/*     */       
/* 220 */       Long2BooleanArrayMap.this.size--;
/* 221 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Long2BooleanMap.FastEntrySet long2BooleanEntrySet() {
/* 226 */     return new EntrySet();
/*     */   }
/*     */   private int findKey(long k) {
/* 229 */     long[] key = this.key;
/* 230 */     for (int i = this.size; i-- != 0;) {
/* 231 */       if (key[i] == k)
/* 232 */         return i; 
/* 233 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean get(long k) {
/* 238 */     long[] key = this.key;
/* 239 */     for (int i = this.size; i-- != 0;) {
/* 240 */       if (key[i] == k)
/* 241 */         return this.value[i]; 
/* 242 */     }  return this.defRetValue;
/*     */   }
/*     */   
/*     */   public int size() {
/* 246 */     return this.size;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 250 */     this.size = 0;
/*     */   }
/*     */   
/*     */   public boolean containsKey(long k) {
/* 254 */     return (findKey(k) != -1);
/*     */   }
/*     */   
/*     */   public boolean containsValue(boolean v) {
/* 258 */     for (int i = this.size; i-- != 0;) {
/* 259 */       if (this.value[i] == v)
/* 260 */         return true; 
/* 261 */     }  return false;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 265 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean put(long k, boolean v) {
/* 270 */     int oldKey = findKey(k);
/* 271 */     if (oldKey != -1) {
/* 272 */       boolean oldValue = this.value[oldKey];
/* 273 */       this.value[oldKey] = v;
/* 274 */       return oldValue;
/*     */     } 
/* 276 */     if (this.size == this.key.length) {
/* 277 */       long[] newKey = new long[(this.size == 0) ? 2 : (this.size * 2)];
/* 278 */       boolean[] newValue = new boolean[(this.size == 0) ? 2 : (this.size * 2)];
/* 279 */       for (int i = this.size; i-- != 0; ) {
/* 280 */         newKey[i] = this.key[i];
/* 281 */         newValue[i] = this.value[i];
/*     */       } 
/* 283 */       this.key = newKey;
/* 284 */       this.value = newValue;
/*     */     } 
/* 286 */     this.key[this.size] = k;
/* 287 */     this.value[this.size] = v;
/* 288 */     this.size++;
/* 289 */     return this.defRetValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(long k) {
/* 294 */     int oldPos = findKey(k);
/* 295 */     if (oldPos == -1)
/* 296 */       return this.defRetValue; 
/* 297 */     boolean oldValue = this.value[oldPos];
/* 298 */     int tail = this.size - oldPos - 1;
/* 299 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 300 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 301 */     this.size--;
/* 302 */     return oldValue;
/*     */   }
/*     */   
/*     */   public LongSet keySet() {
/* 306 */     return new AbstractLongSet()
/*     */       {
/*     */         public boolean contains(long k) {
/* 309 */           return (Long2BooleanArrayMap.this.findKey(k) != -1);
/*     */         }
/*     */         
/*     */         public boolean remove(long k) {
/* 313 */           int oldPos = Long2BooleanArrayMap.this.findKey(k);
/* 314 */           if (oldPos == -1)
/* 315 */             return false; 
/* 316 */           int tail = Long2BooleanArrayMap.this.size - oldPos - 1;
/* 317 */           System.arraycopy(Long2BooleanArrayMap.this.key, oldPos + 1, Long2BooleanArrayMap.this.key, oldPos, tail);
/* 318 */           System.arraycopy(Long2BooleanArrayMap.this.value, oldPos + 1, Long2BooleanArrayMap.this.value, oldPos, tail);
/* 319 */           Long2BooleanArrayMap.this.size--;
/* 320 */           return true;
/*     */         }
/*     */         
/*     */         public LongIterator iterator() {
/* 324 */           return new LongIterator() {
/* 325 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 328 */                 return (this.pos < Long2BooleanArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public long nextLong() {
/* 333 */                 if (!hasNext())
/* 334 */                   throw new NoSuchElementException(); 
/* 335 */                 return Long2BooleanArrayMap.this.key[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 339 */                 if (this.pos == 0)
/* 340 */                   throw new IllegalStateException(); 
/* 341 */                 int tail = Long2BooleanArrayMap.this.size - this.pos;
/* 342 */                 System.arraycopy(Long2BooleanArrayMap.this.key, this.pos, Long2BooleanArrayMap.this.key, this.pos - 1, tail);
/* 343 */                 System.arraycopy(Long2BooleanArrayMap.this.value, this.pos, Long2BooleanArrayMap.this.value, this.pos - 1, tail);
/* 344 */                 Long2BooleanArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 350 */           return Long2BooleanArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 354 */           Long2BooleanArrayMap.this.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public BooleanCollection values() {
/* 360 */     return (BooleanCollection)new AbstractBooleanCollection()
/*     */       {
/*     */         public boolean contains(boolean v) {
/* 363 */           return Long2BooleanArrayMap.this.containsValue(v);
/*     */         }
/*     */         
/*     */         public BooleanIterator iterator() {
/* 367 */           return new BooleanIterator() {
/* 368 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 371 */                 return (this.pos < Long2BooleanArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public boolean nextBoolean() {
/* 376 */                 if (!hasNext())
/* 377 */                   throw new NoSuchElementException(); 
/* 378 */                 return Long2BooleanArrayMap.this.value[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 382 */                 if (this.pos == 0)
/* 383 */                   throw new IllegalStateException(); 
/* 384 */                 int tail = Long2BooleanArrayMap.this.size - this.pos;
/* 385 */                 System.arraycopy(Long2BooleanArrayMap.this.key, this.pos, Long2BooleanArrayMap.this.key, this.pos - 1, tail);
/* 386 */                 System.arraycopy(Long2BooleanArrayMap.this.value, this.pos, Long2BooleanArrayMap.this.value, this.pos - 1, tail);
/* 387 */                 Long2BooleanArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 393 */           return Long2BooleanArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 397 */           Long2BooleanArrayMap.this.clear();
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
/*     */   public Long2BooleanArrayMap clone() {
/*     */     Long2BooleanArrayMap c;
/*     */     try {
/* 416 */       c = (Long2BooleanArrayMap)super.clone();
/* 417 */     } catch (CloneNotSupportedException cantHappen) {
/* 418 */       throw new InternalError();
/*     */     } 
/* 420 */     c.key = (long[])this.key.clone();
/* 421 */     c.value = (boolean[])this.value.clone();
/* 422 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 425 */     s.defaultWriteObject();
/* 426 */     for (int i = 0; i < this.size; i++) {
/* 427 */       s.writeLong(this.key[i]);
/* 428 */       s.writeBoolean(this.value[i]);
/*     */     } 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 432 */     s.defaultReadObject();
/* 433 */     this.key = new long[this.size];
/* 434 */     this.value = new boolean[this.size];
/* 435 */     for (int i = 0; i < this.size; i++) {
/* 436 */       this.key[i] = s.readLong();
/* 437 */       this.value[i] = s.readBoolean();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\Long2BooleanArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */