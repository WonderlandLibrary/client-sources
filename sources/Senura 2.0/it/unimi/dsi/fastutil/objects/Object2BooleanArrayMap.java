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
/*     */ public class Object2BooleanArrayMap<K>
/*     */   extends AbstractObject2BooleanMap<K>
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient Object[] key;
/*     */   private transient boolean[] value;
/*     */   private int size;
/*     */   
/*     */   public Object2BooleanArrayMap(Object[] key, boolean[] value) {
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
/*     */   public Object2BooleanArrayMap() {
/*  67 */     this.key = ObjectArrays.EMPTY_ARRAY;
/*  68 */     this.value = BooleanArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2BooleanArrayMap(int capacity) {
/*  77 */     this.key = new Object[capacity];
/*  78 */     this.value = new boolean[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2BooleanArrayMap(Object2BooleanMap<K> m) {
/*  87 */     this(m.size());
/*  88 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2BooleanArrayMap(Map<? extends K, ? extends Boolean> m) {
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
/*     */   public Object2BooleanArrayMap(Object[] key, boolean[] value, int size) {
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
/*     */     extends AbstractObjectSet<Object2BooleanMap.Entry<K>> implements Object2BooleanMap.FastEntrySet<K> {
/*     */     public ObjectIterator<Object2BooleanMap.Entry<K>> iterator() {
/* 130 */       return (ObjectIterator)new ObjectIterator<Object2BooleanMap.Entry<Object2BooleanMap.Entry<K>>>() {
/* 131 */           int curr = -1; int next = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 134 */             return (this.next < Object2BooleanArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object2BooleanMap.Entry<K> next() {
/* 139 */             if (!hasNext())
/* 140 */               throw new NoSuchElementException(); 
/* 141 */             return new AbstractObject2BooleanMap.BasicEntry<>((K)Object2BooleanArrayMap.this.key[this.curr = this.next], Object2BooleanArrayMap.this.value[this.next++]);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 145 */             if (this.curr == -1)
/* 146 */               throw new IllegalStateException(); 
/* 147 */             this.curr = -1;
/* 148 */             int tail = Object2BooleanArrayMap.this.size-- - this.next--;
/* 149 */             System.arraycopy(Object2BooleanArrayMap.this.key, this.next + 1, Object2BooleanArrayMap.this.key, this.next, tail);
/* 150 */             System.arraycopy(Object2BooleanArrayMap.this.value, this.next + 1, Object2BooleanArrayMap.this.value, this.next, tail);
/* 151 */             Object2BooleanArrayMap.this.key[Object2BooleanArrayMap.this.size] = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     private EntrySet() {}
/*     */     public ObjectIterator<Object2BooleanMap.Entry<K>> fastIterator() {
/* 157 */       return (ObjectIterator)new ObjectIterator<Object2BooleanMap.Entry<Object2BooleanMap.Entry<K>>>() {
/* 158 */           int next = 0; int curr = -1;
/* 159 */           final AbstractObject2BooleanMap.BasicEntry<K> entry = new AbstractObject2BooleanMap.BasicEntry<>();
/*     */           
/*     */           public boolean hasNext() {
/* 162 */             return (this.next < Object2BooleanArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object2BooleanMap.Entry<K> next() {
/* 167 */             if (!hasNext())
/* 168 */               throw new NoSuchElementException(); 
/* 169 */             this.entry.key = (K)Object2BooleanArrayMap.this.key[this.curr = this.next];
/* 170 */             this.entry.value = Object2BooleanArrayMap.this.value[this.next++];
/* 171 */             return this.entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 175 */             if (this.curr == -1)
/* 176 */               throw new IllegalStateException(); 
/* 177 */             this.curr = -1;
/* 178 */             int tail = Object2BooleanArrayMap.this.size-- - this.next--;
/* 179 */             System.arraycopy(Object2BooleanArrayMap.this.key, this.next + 1, Object2BooleanArrayMap.this.key, this.next, tail);
/* 180 */             System.arraycopy(Object2BooleanArrayMap.this.value, this.next + 1, Object2BooleanArrayMap.this.value, this.next, tail);
/* 181 */             Object2BooleanArrayMap.this.key[Object2BooleanArrayMap.this.size] = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 187 */       return Object2BooleanArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 192 */       if (!(o instanceof Map.Entry))
/* 193 */         return false; 
/* 194 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 195 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 196 */         return false; 
/* 197 */       K k = (K)e.getKey();
/* 198 */       return (Object2BooleanArrayMap.this.containsKey(k) && Object2BooleanArrayMap.this
/* 199 */         .getBoolean(k) == ((Boolean)e.getValue()).booleanValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 204 */       if (!(o instanceof Map.Entry))
/* 205 */         return false; 
/* 206 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 207 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 208 */         return false; 
/* 209 */       K k = (K)e.getKey();
/* 210 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/* 211 */       int oldPos = Object2BooleanArrayMap.this.findKey(k);
/* 212 */       if (oldPos == -1 || v != Object2BooleanArrayMap.this.value[oldPos])
/* 213 */         return false; 
/* 214 */       int tail = Object2BooleanArrayMap.this.size - oldPos - 1;
/* 215 */       System.arraycopy(Object2BooleanArrayMap.this.key, oldPos + 1, Object2BooleanArrayMap.this.key, oldPos, tail);
/*     */       
/* 217 */       System.arraycopy(Object2BooleanArrayMap.this.value, oldPos + 1, Object2BooleanArrayMap.this.value, oldPos, tail);
/*     */       
/* 219 */       Object2BooleanArrayMap.this.size--;
/* 220 */       Object2BooleanArrayMap.this.key[Object2BooleanArrayMap.this.size] = null;
/* 221 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Object2BooleanMap.FastEntrySet<K> object2BooleanEntrySet() {
/* 226 */     return new EntrySet();
/*     */   }
/*     */   private int findKey(Object k) {
/* 229 */     Object[] key = this.key;
/* 230 */     for (int i = this.size; i-- != 0;) {
/* 231 */       if (Objects.equals(key[i], k))
/* 232 */         return i; 
/* 233 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getBoolean(Object k) {
/* 238 */     Object[] key = this.key;
/* 239 */     for (int i = this.size; i-- != 0;) {
/* 240 */       if (Objects.equals(key[i], k))
/* 241 */         return this.value[i]; 
/* 242 */     }  return this.defRetValue;
/*     */   }
/*     */   
/*     */   public int size() {
/* 246 */     return this.size;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 250 */     for (int i = this.size; i-- != 0;) {
/* 251 */       this.key[i] = null;
/*     */     }
/* 253 */     this.size = 0;
/*     */   }
/*     */   
/*     */   public boolean containsKey(Object k) {
/* 257 */     return (findKey(k) != -1);
/*     */   }
/*     */   
/*     */   public boolean containsValue(boolean v) {
/* 261 */     for (int i = this.size; i-- != 0;) {
/* 262 */       if (this.value[i] == v)
/* 263 */         return true; 
/* 264 */     }  return false;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 268 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean put(K k, boolean v) {
/* 273 */     int oldKey = findKey(k);
/* 274 */     if (oldKey != -1) {
/* 275 */       boolean oldValue = this.value[oldKey];
/* 276 */       this.value[oldKey] = v;
/* 277 */       return oldValue;
/*     */     } 
/* 279 */     if (this.size == this.key.length) {
/* 280 */       Object[] newKey = new Object[(this.size == 0) ? 2 : (this.size * 2)];
/* 281 */       boolean[] newValue = new boolean[(this.size == 0) ? 2 : (this.size * 2)];
/* 282 */       for (int i = this.size; i-- != 0; ) {
/* 283 */         newKey[i] = this.key[i];
/* 284 */         newValue[i] = this.value[i];
/*     */       } 
/* 286 */       this.key = newKey;
/* 287 */       this.value = newValue;
/*     */     } 
/* 289 */     this.key[this.size] = k;
/* 290 */     this.value[this.size] = v;
/* 291 */     this.size++;
/* 292 */     return this.defRetValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeBoolean(Object k) {
/* 297 */     int oldPos = findKey(k);
/* 298 */     if (oldPos == -1)
/* 299 */       return this.defRetValue; 
/* 300 */     boolean oldValue = this.value[oldPos];
/* 301 */     int tail = this.size - oldPos - 1;
/* 302 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 303 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 304 */     this.size--;
/* 305 */     this.key[this.size] = null;
/* 306 */     return oldValue;
/*     */   }
/*     */   
/*     */   public ObjectSet<K> keySet() {
/* 310 */     return new AbstractObjectSet<K>()
/*     */       {
/*     */         public boolean contains(Object k) {
/* 313 */           return (Object2BooleanArrayMap.this.findKey(k) != -1);
/*     */         }
/*     */         
/*     */         public boolean remove(Object k) {
/* 317 */           int oldPos = Object2BooleanArrayMap.this.findKey(k);
/* 318 */           if (oldPos == -1)
/* 319 */             return false; 
/* 320 */           int tail = Object2BooleanArrayMap.this.size - oldPos - 1;
/* 321 */           System.arraycopy(Object2BooleanArrayMap.this.key, oldPos + 1, Object2BooleanArrayMap.this.key, oldPos, tail);
/* 322 */           System.arraycopy(Object2BooleanArrayMap.this.value, oldPos + 1, Object2BooleanArrayMap.this.value, oldPos, tail);
/* 323 */           Object2BooleanArrayMap.this.size--;
/* 324 */           return true;
/*     */         }
/*     */         
/*     */         public ObjectIterator<K> iterator() {
/* 328 */           return new ObjectIterator<K>() {
/* 329 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 332 */                 return (this.pos < Object2BooleanArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public K next() {
/* 337 */                 if (!hasNext())
/* 338 */                   throw new NoSuchElementException(); 
/* 339 */                 return (K)Object2BooleanArrayMap.this.key[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 343 */                 if (this.pos == 0)
/* 344 */                   throw new IllegalStateException(); 
/* 345 */                 int tail = Object2BooleanArrayMap.this.size - this.pos;
/* 346 */                 System.arraycopy(Object2BooleanArrayMap.this.key, this.pos, Object2BooleanArrayMap.this.key, this.pos - 1, tail);
/* 347 */                 System.arraycopy(Object2BooleanArrayMap.this.value, this.pos, Object2BooleanArrayMap.this.value, this.pos - 1, tail);
/* 348 */                 Object2BooleanArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 354 */           return Object2BooleanArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 358 */           Object2BooleanArrayMap.this.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public BooleanCollection values() {
/* 364 */     return (BooleanCollection)new AbstractBooleanCollection()
/*     */       {
/*     */         public boolean contains(boolean v) {
/* 367 */           return Object2BooleanArrayMap.this.containsValue(v);
/*     */         }
/*     */         
/*     */         public BooleanIterator iterator() {
/* 371 */           return new BooleanIterator() {
/* 372 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 375 */                 return (this.pos < Object2BooleanArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public boolean nextBoolean() {
/* 380 */                 if (!hasNext())
/* 381 */                   throw new NoSuchElementException(); 
/* 382 */                 return Object2BooleanArrayMap.this.value[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 386 */                 if (this.pos == 0)
/* 387 */                   throw new IllegalStateException(); 
/* 388 */                 int tail = Object2BooleanArrayMap.this.size - this.pos;
/* 389 */                 System.arraycopy(Object2BooleanArrayMap.this.key, this.pos, Object2BooleanArrayMap.this.key, this.pos - 1, tail);
/* 390 */                 System.arraycopy(Object2BooleanArrayMap.this.value, this.pos, Object2BooleanArrayMap.this.value, this.pos - 1, tail);
/* 391 */                 Object2BooleanArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 397 */           return Object2BooleanArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 401 */           Object2BooleanArrayMap.this.clear();
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
/*     */   public Object2BooleanArrayMap<K> clone() {
/*     */     Object2BooleanArrayMap<K> c;
/*     */     try {
/* 420 */       c = (Object2BooleanArrayMap<K>)super.clone();
/* 421 */     } catch (CloneNotSupportedException cantHappen) {
/* 422 */       throw new InternalError();
/*     */     } 
/* 424 */     c.key = (Object[])this.key.clone();
/* 425 */     c.value = (boolean[])this.value.clone();
/* 426 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 429 */     s.defaultWriteObject();
/* 430 */     for (int i = 0; i < this.size; i++) {
/* 431 */       s.writeObject(this.key[i]);
/* 432 */       s.writeBoolean(this.value[i]);
/*     */     } 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 436 */     s.defaultReadObject();
/* 437 */     this.key = new Object[this.size];
/* 438 */     this.value = new boolean[this.size];
/* 439 */     for (int i = 0; i < this.size; i++) {
/* 440 */       this.key[i] = s.readObject();
/* 441 */       this.value[i] = s.readBoolean();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2BooleanArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */