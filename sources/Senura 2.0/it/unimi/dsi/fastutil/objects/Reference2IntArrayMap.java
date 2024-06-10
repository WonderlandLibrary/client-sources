/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrays;
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntIterator;
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
/*     */ public class Reference2IntArrayMap<K>
/*     */   extends AbstractReference2IntMap<K>
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient Object[] key;
/*     */   private transient int[] value;
/*     */   private int size;
/*     */   
/*     */   public Reference2IntArrayMap(Object[] key, int[] value) {
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
/*     */   public Reference2IntArrayMap() {
/*  67 */     this.key = ObjectArrays.EMPTY_ARRAY;
/*  68 */     this.value = IntArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Reference2IntArrayMap(int capacity) {
/*  77 */     this.key = new Object[capacity];
/*  78 */     this.value = new int[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Reference2IntArrayMap(Reference2IntMap<K> m) {
/*  87 */     this(m.size());
/*  88 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Reference2IntArrayMap(Map<? extends K, ? extends Integer> m) {
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
/*     */   public Reference2IntArrayMap(Object[] key, int[] value, int size) {
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
/*     */     extends AbstractObjectSet<Reference2IntMap.Entry<K>> implements Reference2IntMap.FastEntrySet<K> {
/*     */     public ObjectIterator<Reference2IntMap.Entry<K>> iterator() {
/* 130 */       return (ObjectIterator)new ObjectIterator<Reference2IntMap.Entry<Reference2IntMap.Entry<K>>>() {
/* 131 */           int curr = -1; int next = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 134 */             return (this.next < Reference2IntArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Reference2IntMap.Entry<K> next() {
/* 139 */             if (!hasNext())
/* 140 */               throw new NoSuchElementException(); 
/* 141 */             return new AbstractReference2IntMap.BasicEntry<>((K)Reference2IntArrayMap.this.key[this.curr = this.next], Reference2IntArrayMap.this.value[this.next++]);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 145 */             if (this.curr == -1)
/* 146 */               throw new IllegalStateException(); 
/* 147 */             this.curr = -1;
/* 148 */             int tail = Reference2IntArrayMap.this.size-- - this.next--;
/* 149 */             System.arraycopy(Reference2IntArrayMap.this.key, this.next + 1, Reference2IntArrayMap.this.key, this.next, tail);
/* 150 */             System.arraycopy(Reference2IntArrayMap.this.value, this.next + 1, Reference2IntArrayMap.this.value, this.next, tail);
/* 151 */             Reference2IntArrayMap.this.key[Reference2IntArrayMap.this.size] = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     private EntrySet() {}
/*     */     public ObjectIterator<Reference2IntMap.Entry<K>> fastIterator() {
/* 157 */       return (ObjectIterator)new ObjectIterator<Reference2IntMap.Entry<Reference2IntMap.Entry<K>>>() {
/* 158 */           int next = 0; int curr = -1;
/* 159 */           final AbstractReference2IntMap.BasicEntry<K> entry = new AbstractReference2IntMap.BasicEntry<>();
/*     */           
/*     */           public boolean hasNext() {
/* 162 */             return (this.next < Reference2IntArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Reference2IntMap.Entry<K> next() {
/* 167 */             if (!hasNext())
/* 168 */               throw new NoSuchElementException(); 
/* 169 */             this.entry.key = (K)Reference2IntArrayMap.this.key[this.curr = this.next];
/* 170 */             this.entry.value = Reference2IntArrayMap.this.value[this.next++];
/* 171 */             return this.entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 175 */             if (this.curr == -1)
/* 176 */               throw new IllegalStateException(); 
/* 177 */             this.curr = -1;
/* 178 */             int tail = Reference2IntArrayMap.this.size-- - this.next--;
/* 179 */             System.arraycopy(Reference2IntArrayMap.this.key, this.next + 1, Reference2IntArrayMap.this.key, this.next, tail);
/* 180 */             System.arraycopy(Reference2IntArrayMap.this.value, this.next + 1, Reference2IntArrayMap.this.value, this.next, tail);
/* 181 */             Reference2IntArrayMap.this.key[Reference2IntArrayMap.this.size] = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 187 */       return Reference2IntArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 192 */       if (!(o instanceof Map.Entry))
/* 193 */         return false; 
/* 194 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 195 */       if (e.getValue() == null || !(e.getValue() instanceof Integer))
/* 196 */         return false; 
/* 197 */       K k = (K)e.getKey();
/* 198 */       return (Reference2IntArrayMap.this.containsKey(k) && Reference2IntArrayMap.this
/* 199 */         .getInt(k) == ((Integer)e.getValue()).intValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 204 */       if (!(o instanceof Map.Entry))
/* 205 */         return false; 
/* 206 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 207 */       if (e.getValue() == null || !(e.getValue() instanceof Integer))
/* 208 */         return false; 
/* 209 */       K k = (K)e.getKey();
/* 210 */       int v = ((Integer)e.getValue()).intValue();
/* 211 */       int oldPos = Reference2IntArrayMap.this.findKey(k);
/* 212 */       if (oldPos == -1 || v != Reference2IntArrayMap.this.value[oldPos])
/* 213 */         return false; 
/* 214 */       int tail = Reference2IntArrayMap.this.size - oldPos - 1;
/* 215 */       System.arraycopy(Reference2IntArrayMap.this.key, oldPos + 1, Reference2IntArrayMap.this.key, oldPos, tail);
/* 216 */       System.arraycopy(Reference2IntArrayMap.this.value, oldPos + 1, Reference2IntArrayMap.this.value, oldPos, tail);
/*     */       
/* 218 */       Reference2IntArrayMap.this.size--;
/* 219 */       Reference2IntArrayMap.this.key[Reference2IntArrayMap.this.size] = null;
/* 220 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Reference2IntMap.FastEntrySet<K> reference2IntEntrySet() {
/* 225 */     return new EntrySet();
/*     */   }
/*     */   private int findKey(Object k) {
/* 228 */     Object[] key = this.key;
/* 229 */     for (int i = this.size; i-- != 0;) {
/* 230 */       if (key[i] == k)
/* 231 */         return i; 
/* 232 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInt(Object k) {
/* 237 */     Object[] key = this.key;
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
/* 249 */     for (int i = this.size; i-- != 0;) {
/* 250 */       this.key[i] = null;
/*     */     }
/* 252 */     this.size = 0;
/*     */   }
/*     */   
/*     */   public boolean containsKey(Object k) {
/* 256 */     return (findKey(k) != -1);
/*     */   }
/*     */   
/*     */   public boolean containsValue(int v) {
/* 260 */     for (int i = this.size; i-- != 0;) {
/* 261 */       if (this.value[i] == v)
/* 262 */         return true; 
/* 263 */     }  return false;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 267 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int put(K k, int v) {
/* 272 */     int oldKey = findKey(k);
/* 273 */     if (oldKey != -1) {
/* 274 */       int oldValue = this.value[oldKey];
/* 275 */       this.value[oldKey] = v;
/* 276 */       return oldValue;
/*     */     } 
/* 278 */     if (this.size == this.key.length) {
/* 279 */       Object[] newKey = new Object[(this.size == 0) ? 2 : (this.size * 2)];
/* 280 */       int[] newValue = new int[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public int removeInt(Object k) {
/* 296 */     int oldPos = findKey(k);
/* 297 */     if (oldPos == -1)
/* 298 */       return this.defRetValue; 
/* 299 */     int oldValue = this.value[oldPos];
/* 300 */     int tail = this.size - oldPos - 1;
/* 301 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 302 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 303 */     this.size--;
/* 304 */     this.key[this.size] = null;
/* 305 */     return oldValue;
/*     */   }
/*     */   
/*     */   public ReferenceSet<K> keySet() {
/* 309 */     return new AbstractReferenceSet<K>()
/*     */       {
/*     */         public boolean contains(Object k) {
/* 312 */           return (Reference2IntArrayMap.this.findKey(k) != -1);
/*     */         }
/*     */         
/*     */         public boolean remove(Object k) {
/* 316 */           int oldPos = Reference2IntArrayMap.this.findKey(k);
/* 317 */           if (oldPos == -1)
/* 318 */             return false; 
/* 319 */           int tail = Reference2IntArrayMap.this.size - oldPos - 1;
/* 320 */           System.arraycopy(Reference2IntArrayMap.this.key, oldPos + 1, Reference2IntArrayMap.this.key, oldPos, tail);
/* 321 */           System.arraycopy(Reference2IntArrayMap.this.value, oldPos + 1, Reference2IntArrayMap.this.value, oldPos, tail);
/* 322 */           Reference2IntArrayMap.this.size--;
/* 323 */           return true;
/*     */         }
/*     */         
/*     */         public ObjectIterator<K> iterator() {
/* 327 */           return new ObjectIterator<K>() {
/* 328 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 331 */                 return (this.pos < Reference2IntArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public K next() {
/* 336 */                 if (!hasNext())
/* 337 */                   throw new NoSuchElementException(); 
/* 338 */                 return (K)Reference2IntArrayMap.this.key[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 342 */                 if (this.pos == 0)
/* 343 */                   throw new IllegalStateException(); 
/* 344 */                 int tail = Reference2IntArrayMap.this.size - this.pos;
/* 345 */                 System.arraycopy(Reference2IntArrayMap.this.key, this.pos, Reference2IntArrayMap.this.key, this.pos - 1, tail);
/* 346 */                 System.arraycopy(Reference2IntArrayMap.this.value, this.pos, Reference2IntArrayMap.this.value, this.pos - 1, tail);
/* 347 */                 Reference2IntArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 353 */           return Reference2IntArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 357 */           Reference2IntArrayMap.this.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public IntCollection values() {
/* 363 */     return (IntCollection)new AbstractIntCollection()
/*     */       {
/*     */         public boolean contains(int v) {
/* 366 */           return Reference2IntArrayMap.this.containsValue(v);
/*     */         }
/*     */         
/*     */         public IntIterator iterator() {
/* 370 */           return new IntIterator() {
/* 371 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 374 */                 return (this.pos < Reference2IntArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public int nextInt() {
/* 379 */                 if (!hasNext())
/* 380 */                   throw new NoSuchElementException(); 
/* 381 */                 return Reference2IntArrayMap.this.value[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 385 */                 if (this.pos == 0)
/* 386 */                   throw new IllegalStateException(); 
/* 387 */                 int tail = Reference2IntArrayMap.this.size - this.pos;
/* 388 */                 System.arraycopy(Reference2IntArrayMap.this.key, this.pos, Reference2IntArrayMap.this.key, this.pos - 1, tail);
/* 389 */                 System.arraycopy(Reference2IntArrayMap.this.value, this.pos, Reference2IntArrayMap.this.value, this.pos - 1, tail);
/* 390 */                 Reference2IntArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 396 */           return Reference2IntArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 400 */           Reference2IntArrayMap.this.clear();
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
/*     */   public Reference2IntArrayMap<K> clone() {
/*     */     Reference2IntArrayMap<K> c;
/*     */     try {
/* 419 */       c = (Reference2IntArrayMap<K>)super.clone();
/* 420 */     } catch (CloneNotSupportedException cantHappen) {
/* 421 */       throw new InternalError();
/*     */     } 
/* 423 */     c.key = (Object[])this.key.clone();
/* 424 */     c.value = (int[])this.value.clone();
/* 425 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 428 */     s.defaultWriteObject();
/* 429 */     for (int i = 0; i < this.size; i++) {
/* 430 */       s.writeObject(this.key[i]);
/* 431 */       s.writeInt(this.value[i]);
/*     */     } 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 435 */     s.defaultReadObject();
/* 436 */     this.key = new Object[this.size];
/* 437 */     this.value = new int[this.size];
/* 438 */     for (int i = 0; i < this.size; i++) {
/* 439 */       this.key[i] = s.readObject();
/* 440 */       this.value[i] = s.readInt();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2IntArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */