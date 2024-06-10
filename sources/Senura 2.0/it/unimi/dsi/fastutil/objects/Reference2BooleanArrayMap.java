/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanArrays;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
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
/*     */ 
/*     */ 
/*     */ public class Reference2BooleanArrayMap<K>
/*     */   extends AbstractReference2BooleanMap<K>
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient Object[] key;
/*     */   private transient boolean[] value;
/*     */   private int size;
/*     */   
/*     */   public Reference2BooleanArrayMap(Object[] key, boolean[] value) {
/*  59 */     this.key = key;
/*  60 */     this.value = value;
/*  61 */     this.size = key.length;
/*  62 */     if (key.length != value.length) {
/*  63 */       throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Reference2BooleanArrayMap() {
/*  70 */     this.key = ObjectArrays.EMPTY_ARRAY;
/*  71 */     this.value = BooleanArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Reference2BooleanArrayMap(int capacity) {
/*  80 */     this.key = new Object[capacity];
/*  81 */     this.value = new boolean[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Reference2BooleanArrayMap(Reference2BooleanMap<K> m) {
/*  90 */     this(m.size());
/*  91 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Reference2BooleanArrayMap(Map<? extends K, ? extends Boolean> m) {
/* 100 */     this(m.size());
/* 101 */     putAll(m);
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
/*     */   public Reference2BooleanArrayMap(Object[] key, boolean[] value, int size) {
/* 120 */     this.key = key;
/* 121 */     this.value = value;
/* 122 */     this.size = size;
/* 123 */     if (key.length != value.length) {
/* 124 */       throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/*     */     }
/* 126 */     if (size > key.length)
/* 127 */       throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")"); 
/*     */   }
/*     */   
/*     */   private final class EntrySet
/*     */     extends AbstractObjectSet<Reference2BooleanMap.Entry<K>> implements Reference2BooleanMap.FastEntrySet<K> {
/*     */     public ObjectIterator<Reference2BooleanMap.Entry<K>> iterator() {
/* 133 */       return (ObjectIterator)new ObjectIterator<Reference2BooleanMap.Entry<Reference2BooleanMap.Entry<K>>>() {
/* 134 */           int curr = -1; int next = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 137 */             return (this.next < Reference2BooleanArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Reference2BooleanMap.Entry<K> next() {
/* 142 */             if (!hasNext())
/* 143 */               throw new NoSuchElementException(); 
/* 144 */             return new AbstractReference2BooleanMap.BasicEntry<>((K)Reference2BooleanArrayMap.this.key[this.curr = this.next], Reference2BooleanArrayMap.this.value[this.next++]);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 148 */             if (this.curr == -1)
/* 149 */               throw new IllegalStateException(); 
/* 150 */             this.curr = -1;
/* 151 */             int tail = Reference2BooleanArrayMap.this.size-- - this.next--;
/* 152 */             System.arraycopy(Reference2BooleanArrayMap.this.key, this.next + 1, Reference2BooleanArrayMap.this.key, this.next, tail);
/* 153 */             System.arraycopy(Reference2BooleanArrayMap.this.value, this.next + 1, Reference2BooleanArrayMap.this.value, this.next, tail);
/* 154 */             Reference2BooleanArrayMap.this.key[Reference2BooleanArrayMap.this.size] = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     private EntrySet() {}
/*     */     public ObjectIterator<Reference2BooleanMap.Entry<K>> fastIterator() {
/* 160 */       return (ObjectIterator)new ObjectIterator<Reference2BooleanMap.Entry<Reference2BooleanMap.Entry<K>>>() {
/* 161 */           int next = 0; int curr = -1;
/* 162 */           final AbstractReference2BooleanMap.BasicEntry<K> entry = new AbstractReference2BooleanMap.BasicEntry<>();
/*     */           
/*     */           public boolean hasNext() {
/* 165 */             return (this.next < Reference2BooleanArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Reference2BooleanMap.Entry<K> next() {
/* 170 */             if (!hasNext())
/* 171 */               throw new NoSuchElementException(); 
/* 172 */             this.entry.key = (K)Reference2BooleanArrayMap.this.key[this.curr = this.next];
/* 173 */             this.entry.value = Reference2BooleanArrayMap.this.value[this.next++];
/* 174 */             return this.entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 178 */             if (this.curr == -1)
/* 179 */               throw new IllegalStateException(); 
/* 180 */             this.curr = -1;
/* 181 */             int tail = Reference2BooleanArrayMap.this.size-- - this.next--;
/* 182 */             System.arraycopy(Reference2BooleanArrayMap.this.key, this.next + 1, Reference2BooleanArrayMap.this.key, this.next, tail);
/* 183 */             System.arraycopy(Reference2BooleanArrayMap.this.value, this.next + 1, Reference2BooleanArrayMap.this.value, this.next, tail);
/* 184 */             Reference2BooleanArrayMap.this.key[Reference2BooleanArrayMap.this.size] = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 190 */       return Reference2BooleanArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 195 */       if (!(o instanceof Map.Entry))
/* 196 */         return false; 
/* 197 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 198 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 199 */         return false; 
/* 200 */       K k = (K)e.getKey();
/* 201 */       return (Reference2BooleanArrayMap.this.containsKey(k) && Reference2BooleanArrayMap.this
/* 202 */         .getBoolean(k) == ((Boolean)e.getValue()).booleanValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 207 */       if (!(o instanceof Map.Entry))
/* 208 */         return false; 
/* 209 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 210 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 211 */         return false; 
/* 212 */       K k = (K)e.getKey();
/* 213 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/* 214 */       int oldPos = Reference2BooleanArrayMap.this.findKey(k);
/* 215 */       if (oldPos == -1 || v != Reference2BooleanArrayMap.this.value[oldPos])
/* 216 */         return false; 
/* 217 */       int tail = Reference2BooleanArrayMap.this.size - oldPos - 1;
/* 218 */       System.arraycopy(Reference2BooleanArrayMap.this.key, oldPos + 1, Reference2BooleanArrayMap.this.key, oldPos, tail);
/*     */       
/* 220 */       System.arraycopy(Reference2BooleanArrayMap.this.value, oldPos + 1, Reference2BooleanArrayMap.this.value, oldPos, tail);
/*     */       
/* 222 */       Reference2BooleanArrayMap.this.size--;
/* 223 */       Reference2BooleanArrayMap.this.key[Reference2BooleanArrayMap.this.size] = null;
/* 224 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Reference2BooleanMap.FastEntrySet<K> reference2BooleanEntrySet() {
/* 229 */     return new EntrySet();
/*     */   }
/*     */   private int findKey(Object k) {
/* 232 */     Object[] key = this.key;
/* 233 */     for (int i = this.size; i-- != 0;) {
/* 234 */       if (key[i] == k)
/* 235 */         return i; 
/* 236 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getBoolean(Object k) {
/* 241 */     Object[] key = this.key;
/* 242 */     for (int i = this.size; i-- != 0;) {
/* 243 */       if (key[i] == k)
/* 244 */         return this.value[i]; 
/* 245 */     }  return this.defRetValue;
/*     */   }
/*     */   
/*     */   public int size() {
/* 249 */     return this.size;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 253 */     for (int i = this.size; i-- != 0;) {
/* 254 */       this.key[i] = null;
/*     */     }
/* 256 */     this.size = 0;
/*     */   }
/*     */   
/*     */   public boolean containsKey(Object k) {
/* 260 */     return (findKey(k) != -1);
/*     */   }
/*     */   
/*     */   public boolean containsValue(boolean v) {
/* 264 */     for (int i = this.size; i-- != 0;) {
/* 265 */       if (this.value[i] == v)
/* 266 */         return true; 
/* 267 */     }  return false;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 271 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean put(K k, boolean v) {
/* 276 */     int oldKey = findKey(k);
/* 277 */     if (oldKey != -1) {
/* 278 */       boolean oldValue = this.value[oldKey];
/* 279 */       this.value[oldKey] = v;
/* 280 */       return oldValue;
/*     */     } 
/* 282 */     if (this.size == this.key.length) {
/* 283 */       Object[] newKey = new Object[(this.size == 0) ? 2 : (this.size * 2)];
/* 284 */       boolean[] newValue = new boolean[(this.size == 0) ? 2 : (this.size * 2)];
/* 285 */       for (int i = this.size; i-- != 0; ) {
/* 286 */         newKey[i] = this.key[i];
/* 287 */         newValue[i] = this.value[i];
/*     */       } 
/* 289 */       this.key = newKey;
/* 290 */       this.value = newValue;
/*     */     } 
/* 292 */     this.key[this.size] = k;
/* 293 */     this.value[this.size] = v;
/* 294 */     this.size++;
/* 295 */     return this.defRetValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeBoolean(Object k) {
/* 300 */     int oldPos = findKey(k);
/* 301 */     if (oldPos == -1)
/* 302 */       return this.defRetValue; 
/* 303 */     boolean oldValue = this.value[oldPos];
/* 304 */     int tail = this.size - oldPos - 1;
/* 305 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 306 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 307 */     this.size--;
/* 308 */     this.key[this.size] = null;
/* 309 */     return oldValue;
/*     */   }
/*     */   
/*     */   public ReferenceSet<K> keySet() {
/* 313 */     return new AbstractReferenceSet<K>()
/*     */       {
/*     */         public boolean contains(Object k) {
/* 316 */           return (Reference2BooleanArrayMap.this.findKey(k) != -1);
/*     */         }
/*     */         
/*     */         public boolean remove(Object k) {
/* 320 */           int oldPos = Reference2BooleanArrayMap.this.findKey(k);
/* 321 */           if (oldPos == -1)
/* 322 */             return false; 
/* 323 */           int tail = Reference2BooleanArrayMap.this.size - oldPos - 1;
/* 324 */           System.arraycopy(Reference2BooleanArrayMap.this.key, oldPos + 1, Reference2BooleanArrayMap.this.key, oldPos, tail);
/* 325 */           System.arraycopy(Reference2BooleanArrayMap.this.value, oldPos + 1, Reference2BooleanArrayMap.this.value, oldPos, tail);
/* 326 */           Reference2BooleanArrayMap.this.size--;
/* 327 */           return true;
/*     */         }
/*     */         
/*     */         public ObjectIterator<K> iterator() {
/* 331 */           return new ObjectIterator<K>() {
/* 332 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 335 */                 return (this.pos < Reference2BooleanArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public K next() {
/* 340 */                 if (!hasNext())
/* 341 */                   throw new NoSuchElementException(); 
/* 342 */                 return (K)Reference2BooleanArrayMap.this.key[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 346 */                 if (this.pos == 0)
/* 347 */                   throw new IllegalStateException(); 
/* 348 */                 int tail = Reference2BooleanArrayMap.this.size - this.pos;
/* 349 */                 System.arraycopy(Reference2BooleanArrayMap.this.key, this.pos, Reference2BooleanArrayMap.this.key, this.pos - 1, tail);
/* 350 */                 System.arraycopy(Reference2BooleanArrayMap.this.value, this.pos, Reference2BooleanArrayMap.this.value, this.pos - 1, tail);
/* 351 */                 Reference2BooleanArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 357 */           return Reference2BooleanArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 361 */           Reference2BooleanArrayMap.this.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public BooleanCollection values() {
/* 367 */     return (BooleanCollection)new AbstractBooleanCollection()
/*     */       {
/*     */         public boolean contains(boolean v) {
/* 370 */           return Reference2BooleanArrayMap.this.containsValue(v);
/*     */         }
/*     */         
/*     */         public BooleanIterator iterator() {
/* 374 */           return new BooleanIterator() {
/* 375 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 378 */                 return (this.pos < Reference2BooleanArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public boolean nextBoolean() {
/* 383 */                 if (!hasNext())
/* 384 */                   throw new NoSuchElementException(); 
/* 385 */                 return Reference2BooleanArrayMap.this.value[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 389 */                 if (this.pos == 0)
/* 390 */                   throw new IllegalStateException(); 
/* 391 */                 int tail = Reference2BooleanArrayMap.this.size - this.pos;
/* 392 */                 System.arraycopy(Reference2BooleanArrayMap.this.key, this.pos, Reference2BooleanArrayMap.this.key, this.pos - 1, tail);
/* 393 */                 System.arraycopy(Reference2BooleanArrayMap.this.value, this.pos, Reference2BooleanArrayMap.this.value, this.pos - 1, tail);
/* 394 */                 Reference2BooleanArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 400 */           return Reference2BooleanArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 404 */           Reference2BooleanArrayMap.this.clear();
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
/*     */   public Reference2BooleanArrayMap<K> clone() {
/*     */     Reference2BooleanArrayMap<K> c;
/*     */     try {
/* 423 */       c = (Reference2BooleanArrayMap<K>)super.clone();
/* 424 */     } catch (CloneNotSupportedException cantHappen) {
/* 425 */       throw new InternalError();
/*     */     } 
/* 427 */     c.key = (Object[])this.key.clone();
/* 428 */     c.value = (boolean[])this.value.clone();
/* 429 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 432 */     s.defaultWriteObject();
/* 433 */     for (int i = 0; i < this.size; i++) {
/* 434 */       s.writeObject(this.key[i]);
/* 435 */       s.writeBoolean(this.value[i]);
/*     */     } 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 439 */     s.defaultReadObject();
/* 440 */     this.key = new Object[this.size];
/* 441 */     this.value = new boolean[this.size];
/* 442 */     for (int i = 0; i < this.size; i++) {
/* 443 */       this.key[i] = s.readObject();
/* 444 */       this.value[i] = s.readBoolean();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2BooleanArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */