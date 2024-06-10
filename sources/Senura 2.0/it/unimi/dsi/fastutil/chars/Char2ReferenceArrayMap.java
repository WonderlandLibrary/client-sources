/*     */ package it.unimi.dsi.fastutil.chars;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrays;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ReferenceCollection;
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
/*     */ public class Char2ReferenceArrayMap<V>
/*     */   extends AbstractChar2ReferenceMap<V>
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient char[] key;
/*     */   private transient Object[] value;
/*     */   private int size;
/*     */   
/*     */   public Char2ReferenceArrayMap(char[] key, Object[] value) {
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
/*     */   public Char2ReferenceArrayMap() {
/*  67 */     this.key = CharArrays.EMPTY_ARRAY;
/*  68 */     this.value = ObjectArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Char2ReferenceArrayMap(int capacity) {
/*  77 */     this.key = new char[capacity];
/*  78 */     this.value = new Object[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Char2ReferenceArrayMap(Char2ReferenceMap<V> m) {
/*  87 */     this(m.size());
/*  88 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Char2ReferenceArrayMap(Map<? extends Character, ? extends V> m) {
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
/*     */   public Char2ReferenceArrayMap(char[] key, Object[] value, int size) {
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
/*     */     extends AbstractObjectSet<Char2ReferenceMap.Entry<V>> implements Char2ReferenceMap.FastEntrySet<V> {
/*     */     public ObjectIterator<Char2ReferenceMap.Entry<V>> iterator() {
/* 130 */       return new ObjectIterator<Char2ReferenceMap.Entry<V>>() {
/* 131 */           int curr = -1; int next = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 134 */             return (this.next < Char2ReferenceArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Char2ReferenceMap.Entry<V> next() {
/* 139 */             if (!hasNext())
/* 140 */               throw new NoSuchElementException(); 
/* 141 */             return new AbstractChar2ReferenceMap.BasicEntry<>(Char2ReferenceArrayMap.this.key[this.curr = this.next], (V)Char2ReferenceArrayMap.this.value[this.next++]);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 145 */             if (this.curr == -1)
/* 146 */               throw new IllegalStateException(); 
/* 147 */             this.curr = -1;
/* 148 */             int tail = Char2ReferenceArrayMap.this.size-- - this.next--;
/* 149 */             System.arraycopy(Char2ReferenceArrayMap.this.key, this.next + 1, Char2ReferenceArrayMap.this.key, this.next, tail);
/* 150 */             System.arraycopy(Char2ReferenceArrayMap.this.value, this.next + 1, Char2ReferenceArrayMap.this.value, this.next, tail);
/* 151 */             Char2ReferenceArrayMap.this.value[Char2ReferenceArrayMap.this.size] = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     private EntrySet() {}
/*     */     public ObjectIterator<Char2ReferenceMap.Entry<V>> fastIterator() {
/* 157 */       return new ObjectIterator<Char2ReferenceMap.Entry<V>>() {
/* 158 */           int next = 0; int curr = -1;
/* 159 */           final AbstractChar2ReferenceMap.BasicEntry<V> entry = new AbstractChar2ReferenceMap.BasicEntry<>();
/*     */           
/*     */           public boolean hasNext() {
/* 162 */             return (this.next < Char2ReferenceArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Char2ReferenceMap.Entry<V> next() {
/* 167 */             if (!hasNext())
/* 168 */               throw new NoSuchElementException(); 
/* 169 */             this.entry.key = Char2ReferenceArrayMap.this.key[this.curr = this.next];
/* 170 */             this.entry.value = (V)Char2ReferenceArrayMap.this.value[this.next++];
/* 171 */             return this.entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 175 */             if (this.curr == -1)
/* 176 */               throw new IllegalStateException(); 
/* 177 */             this.curr = -1;
/* 178 */             int tail = Char2ReferenceArrayMap.this.size-- - this.next--;
/* 179 */             System.arraycopy(Char2ReferenceArrayMap.this.key, this.next + 1, Char2ReferenceArrayMap.this.key, this.next, tail);
/* 180 */             System.arraycopy(Char2ReferenceArrayMap.this.value, this.next + 1, Char2ReferenceArrayMap.this.value, this.next, tail);
/* 181 */             Char2ReferenceArrayMap.this.value[Char2ReferenceArrayMap.this.size] = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 187 */       return Char2ReferenceArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 192 */       if (!(o instanceof Map.Entry))
/* 193 */         return false; 
/* 194 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 195 */       if (e.getKey() == null || !(e.getKey() instanceof Character))
/* 196 */         return false; 
/* 197 */       char k = ((Character)e.getKey()).charValue();
/* 198 */       return (Char2ReferenceArrayMap.this.containsKey(k) && Char2ReferenceArrayMap.this
/* 199 */         .get(k) == e.getValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 204 */       if (!(o instanceof Map.Entry))
/* 205 */         return false; 
/* 206 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 207 */       if (e.getKey() == null || !(e.getKey() instanceof Character))
/* 208 */         return false; 
/* 209 */       char k = ((Character)e.getKey()).charValue();
/* 210 */       V v = (V)e.getValue();
/* 211 */       int oldPos = Char2ReferenceArrayMap.this.findKey(k);
/* 212 */       if (oldPos == -1 || v != Char2ReferenceArrayMap.this.value[oldPos])
/* 213 */         return false; 
/* 214 */       int tail = Char2ReferenceArrayMap.this.size - oldPos - 1;
/* 215 */       System.arraycopy(Char2ReferenceArrayMap.this.key, oldPos + 1, Char2ReferenceArrayMap.this.key, oldPos, tail);
/*     */       
/* 217 */       System.arraycopy(Char2ReferenceArrayMap.this.value, oldPos + 1, Char2ReferenceArrayMap.this.value, oldPos, tail);
/*     */       
/* 219 */       Char2ReferenceArrayMap.this.size--;
/* 220 */       Char2ReferenceArrayMap.this.value[Char2ReferenceArrayMap.this.size] = null;
/* 221 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Char2ReferenceMap.FastEntrySet<V> char2ReferenceEntrySet() {
/* 226 */     return new EntrySet();
/*     */   }
/*     */   private int findKey(char k) {
/* 229 */     char[] key = this.key;
/* 230 */     for (int i = this.size; i-- != 0;) {
/* 231 */       if (key[i] == k)
/* 232 */         return i; 
/* 233 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public V get(char k) {
/* 238 */     char[] key = this.key;
/* 239 */     for (int i = this.size; i-- != 0;) {
/* 240 */       if (key[i] == k)
/* 241 */         return (V)this.value[i]; 
/* 242 */     }  return this.defRetValue;
/*     */   }
/*     */   
/*     */   public int size() {
/* 246 */     return this.size;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 250 */     for (int i = this.size; i-- != 0;) {
/* 251 */       this.value[i] = null;
/*     */     }
/* 253 */     this.size = 0;
/*     */   }
/*     */   
/*     */   public boolean containsKey(char k) {
/* 257 */     return (findKey(k) != -1);
/*     */   }
/*     */   
/*     */   public boolean containsValue(Object v) {
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
/*     */   public V put(char k, V v) {
/* 273 */     int oldKey = findKey(k);
/* 274 */     if (oldKey != -1) {
/* 275 */       V oldValue = (V)this.value[oldKey];
/* 276 */       this.value[oldKey] = v;
/* 277 */       return oldValue;
/*     */     } 
/* 279 */     if (this.size == this.key.length) {
/* 280 */       char[] newKey = new char[(this.size == 0) ? 2 : (this.size * 2)];
/* 281 */       Object[] newValue = new Object[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public V remove(char k) {
/* 297 */     int oldPos = findKey(k);
/* 298 */     if (oldPos == -1)
/* 299 */       return this.defRetValue; 
/* 300 */     V oldValue = (V)this.value[oldPos];
/* 301 */     int tail = this.size - oldPos - 1;
/* 302 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 303 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 304 */     this.size--;
/* 305 */     this.value[this.size] = null;
/* 306 */     return oldValue;
/*     */   }
/*     */   
/*     */   public CharSet keySet() {
/* 310 */     return new AbstractCharSet()
/*     */       {
/*     */         public boolean contains(char k) {
/* 313 */           return (Char2ReferenceArrayMap.this.findKey(k) != -1);
/*     */         }
/*     */         
/*     */         public boolean remove(char k) {
/* 317 */           int oldPos = Char2ReferenceArrayMap.this.findKey(k);
/* 318 */           if (oldPos == -1)
/* 319 */             return false; 
/* 320 */           int tail = Char2ReferenceArrayMap.this.size - oldPos - 1;
/* 321 */           System.arraycopy(Char2ReferenceArrayMap.this.key, oldPos + 1, Char2ReferenceArrayMap.this.key, oldPos, tail);
/* 322 */           System.arraycopy(Char2ReferenceArrayMap.this.value, oldPos + 1, Char2ReferenceArrayMap.this.value, oldPos, tail);
/* 323 */           Char2ReferenceArrayMap.this.size--;
/* 324 */           return true;
/*     */         }
/*     */         
/*     */         public CharIterator iterator() {
/* 328 */           return new CharIterator() {
/* 329 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 332 */                 return (this.pos < Char2ReferenceArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public char nextChar() {
/* 337 */                 if (!hasNext())
/* 338 */                   throw new NoSuchElementException(); 
/* 339 */                 return Char2ReferenceArrayMap.this.key[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 343 */                 if (this.pos == 0)
/* 344 */                   throw new IllegalStateException(); 
/* 345 */                 int tail = Char2ReferenceArrayMap.this.size - this.pos;
/* 346 */                 System.arraycopy(Char2ReferenceArrayMap.this.key, this.pos, Char2ReferenceArrayMap.this.key, this.pos - 1, tail);
/* 347 */                 System.arraycopy(Char2ReferenceArrayMap.this.value, this.pos, Char2ReferenceArrayMap.this.value, this.pos - 1, tail);
/* 348 */                 Char2ReferenceArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 354 */           return Char2ReferenceArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 358 */           Char2ReferenceArrayMap.this.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public ReferenceCollection<V> values() {
/* 364 */     return (ReferenceCollection<V>)new AbstractReferenceCollection<V>()
/*     */       {
/*     */         public boolean contains(Object v) {
/* 367 */           return Char2ReferenceArrayMap.this.containsValue(v);
/*     */         }
/*     */         
/*     */         public ObjectIterator<V> iterator() {
/* 371 */           return new ObjectIterator<V>() {
/* 372 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 375 */                 return (this.pos < Char2ReferenceArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public V next() {
/* 380 */                 if (!hasNext())
/* 381 */                   throw new NoSuchElementException(); 
/* 382 */                 return (V)Char2ReferenceArrayMap.this.value[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 386 */                 if (this.pos == 0)
/* 387 */                   throw new IllegalStateException(); 
/* 388 */                 int tail = Char2ReferenceArrayMap.this.size - this.pos;
/* 389 */                 System.arraycopy(Char2ReferenceArrayMap.this.key, this.pos, Char2ReferenceArrayMap.this.key, this.pos - 1, tail);
/* 390 */                 System.arraycopy(Char2ReferenceArrayMap.this.value, this.pos, Char2ReferenceArrayMap.this.value, this.pos - 1, tail);
/* 391 */                 Char2ReferenceArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 397 */           return Char2ReferenceArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 401 */           Char2ReferenceArrayMap.this.clear();
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
/*     */   public Char2ReferenceArrayMap<V> clone() {
/*     */     Char2ReferenceArrayMap<V> c;
/*     */     try {
/* 420 */       c = (Char2ReferenceArrayMap<V>)super.clone();
/* 421 */     } catch (CloneNotSupportedException cantHappen) {
/* 422 */       throw new InternalError();
/*     */     } 
/* 424 */     c.key = (char[])this.key.clone();
/* 425 */     c.value = (Object[])this.value.clone();
/* 426 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 429 */     s.defaultWriteObject();
/* 430 */     for (int i = 0; i < this.size; i++) {
/* 431 */       s.writeChar(this.key[i]);
/* 432 */       s.writeObject(this.value[i]);
/*     */     } 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 436 */     s.defaultReadObject();
/* 437 */     this.key = new char[this.size];
/* 438 */     this.value = new Object[this.size];
/* 439 */     for (int i = 0; i < this.size; i++) {
/* 440 */       this.key[i] = s.readChar();
/* 441 */       this.value[i] = s.readObject();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\Char2ReferenceArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */