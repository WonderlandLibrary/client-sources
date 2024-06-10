/*     */ package it.unimi.dsi.fastutil.chars;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrays;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectCollection;
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
/*     */ public class Char2ObjectArrayMap<V>
/*     */   extends AbstractChar2ObjectMap<V>
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient char[] key;
/*     */   private transient Object[] value;
/*     */   private int size;
/*     */   
/*     */   public Char2ObjectArrayMap(char[] key, Object[] value) {
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
/*     */   public Char2ObjectArrayMap() {
/*  67 */     this.key = CharArrays.EMPTY_ARRAY;
/*  68 */     this.value = ObjectArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Char2ObjectArrayMap(int capacity) {
/*  77 */     this.key = new char[capacity];
/*  78 */     this.value = new Object[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Char2ObjectArrayMap(Char2ObjectMap<V> m) {
/*  87 */     this(m.size());
/*  88 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Char2ObjectArrayMap(Map<? extends Character, ? extends V> m) {
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
/*     */   public Char2ObjectArrayMap(char[] key, Object[] value, int size) {
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
/*     */     extends AbstractObjectSet<Char2ObjectMap.Entry<V>> implements Char2ObjectMap.FastEntrySet<V> {
/*     */     public ObjectIterator<Char2ObjectMap.Entry<V>> iterator() {
/* 130 */       return new ObjectIterator<Char2ObjectMap.Entry<V>>() {
/* 131 */           int curr = -1; int next = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 134 */             return (this.next < Char2ObjectArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Char2ObjectMap.Entry<V> next() {
/* 139 */             if (!hasNext())
/* 140 */               throw new NoSuchElementException(); 
/* 141 */             return new AbstractChar2ObjectMap.BasicEntry<>(Char2ObjectArrayMap.this.key[this.curr = this.next], (V)Char2ObjectArrayMap.this.value[this.next++]);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 145 */             if (this.curr == -1)
/* 146 */               throw new IllegalStateException(); 
/* 147 */             this.curr = -1;
/* 148 */             int tail = Char2ObjectArrayMap.this.size-- - this.next--;
/* 149 */             System.arraycopy(Char2ObjectArrayMap.this.key, this.next + 1, Char2ObjectArrayMap.this.key, this.next, tail);
/* 150 */             System.arraycopy(Char2ObjectArrayMap.this.value, this.next + 1, Char2ObjectArrayMap.this.value, this.next, tail);
/* 151 */             Char2ObjectArrayMap.this.value[Char2ObjectArrayMap.this.size] = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     private EntrySet() {}
/*     */     public ObjectIterator<Char2ObjectMap.Entry<V>> fastIterator() {
/* 157 */       return new ObjectIterator<Char2ObjectMap.Entry<V>>() {
/* 158 */           int next = 0; int curr = -1;
/* 159 */           final AbstractChar2ObjectMap.BasicEntry<V> entry = new AbstractChar2ObjectMap.BasicEntry<>();
/*     */           
/*     */           public boolean hasNext() {
/* 162 */             return (this.next < Char2ObjectArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Char2ObjectMap.Entry<V> next() {
/* 167 */             if (!hasNext())
/* 168 */               throw new NoSuchElementException(); 
/* 169 */             this.entry.key = Char2ObjectArrayMap.this.key[this.curr = this.next];
/* 170 */             this.entry.value = (V)Char2ObjectArrayMap.this.value[this.next++];
/* 171 */             return this.entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 175 */             if (this.curr == -1)
/* 176 */               throw new IllegalStateException(); 
/* 177 */             this.curr = -1;
/* 178 */             int tail = Char2ObjectArrayMap.this.size-- - this.next--;
/* 179 */             System.arraycopy(Char2ObjectArrayMap.this.key, this.next + 1, Char2ObjectArrayMap.this.key, this.next, tail);
/* 180 */             System.arraycopy(Char2ObjectArrayMap.this.value, this.next + 1, Char2ObjectArrayMap.this.value, this.next, tail);
/* 181 */             Char2ObjectArrayMap.this.value[Char2ObjectArrayMap.this.size] = null;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 187 */       return Char2ObjectArrayMap.this.size;
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
/* 198 */       return (Char2ObjectArrayMap.this.containsKey(k) && 
/* 199 */         Objects.equals(Char2ObjectArrayMap.this.get(k), e.getValue()));
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
/* 211 */       int oldPos = Char2ObjectArrayMap.this.findKey(k);
/* 212 */       if (oldPos == -1 || !Objects.equals(v, Char2ObjectArrayMap.this.value[oldPos]))
/* 213 */         return false; 
/* 214 */       int tail = Char2ObjectArrayMap.this.size - oldPos - 1;
/* 215 */       System.arraycopy(Char2ObjectArrayMap.this.key, oldPos + 1, Char2ObjectArrayMap.this.key, oldPos, tail);
/* 216 */       System.arraycopy(Char2ObjectArrayMap.this.value, oldPos + 1, Char2ObjectArrayMap.this.value, oldPos, tail);
/* 217 */       Char2ObjectArrayMap.this.size--;
/* 218 */       Char2ObjectArrayMap.this.value[Char2ObjectArrayMap.this.size] = null;
/* 219 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Char2ObjectMap.FastEntrySet<V> char2ObjectEntrySet() {
/* 224 */     return new EntrySet();
/*     */   }
/*     */   private int findKey(char k) {
/* 227 */     char[] key = this.key;
/* 228 */     for (int i = this.size; i-- != 0;) {
/* 229 */       if (key[i] == k)
/* 230 */         return i; 
/* 231 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public V get(char k) {
/* 236 */     char[] key = this.key;
/* 237 */     for (int i = this.size; i-- != 0;) {
/* 238 */       if (key[i] == k)
/* 239 */         return (V)this.value[i]; 
/* 240 */     }  return this.defRetValue;
/*     */   }
/*     */   
/*     */   public int size() {
/* 244 */     return this.size;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 248 */     for (int i = this.size; i-- != 0;) {
/* 249 */       this.value[i] = null;
/*     */     }
/* 251 */     this.size = 0;
/*     */   }
/*     */   
/*     */   public boolean containsKey(char k) {
/* 255 */     return (findKey(k) != -1);
/*     */   }
/*     */   
/*     */   public boolean containsValue(Object v) {
/* 259 */     for (int i = this.size; i-- != 0;) {
/* 260 */       if (Objects.equals(this.value[i], v))
/* 261 */         return true; 
/* 262 */     }  return false;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 266 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public V put(char k, V v) {
/* 271 */     int oldKey = findKey(k);
/* 272 */     if (oldKey != -1) {
/* 273 */       V oldValue = (V)this.value[oldKey];
/* 274 */       this.value[oldKey] = v;
/* 275 */       return oldValue;
/*     */     } 
/* 277 */     if (this.size == this.key.length) {
/* 278 */       char[] newKey = new char[(this.size == 0) ? 2 : (this.size * 2)];
/* 279 */       Object[] newValue = new Object[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public V remove(char k) {
/* 295 */     int oldPos = findKey(k);
/* 296 */     if (oldPos == -1)
/* 297 */       return this.defRetValue; 
/* 298 */     V oldValue = (V)this.value[oldPos];
/* 299 */     int tail = this.size - oldPos - 1;
/* 300 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 301 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 302 */     this.size--;
/* 303 */     this.value[this.size] = null;
/* 304 */     return oldValue;
/*     */   }
/*     */   
/*     */   public CharSet keySet() {
/* 308 */     return new AbstractCharSet()
/*     */       {
/*     */         public boolean contains(char k) {
/* 311 */           return (Char2ObjectArrayMap.this.findKey(k) != -1);
/*     */         }
/*     */         
/*     */         public boolean remove(char k) {
/* 315 */           int oldPos = Char2ObjectArrayMap.this.findKey(k);
/* 316 */           if (oldPos == -1)
/* 317 */             return false; 
/* 318 */           int tail = Char2ObjectArrayMap.this.size - oldPos - 1;
/* 319 */           System.arraycopy(Char2ObjectArrayMap.this.key, oldPos + 1, Char2ObjectArrayMap.this.key, oldPos, tail);
/* 320 */           System.arraycopy(Char2ObjectArrayMap.this.value, oldPos + 1, Char2ObjectArrayMap.this.value, oldPos, tail);
/* 321 */           Char2ObjectArrayMap.this.size--;
/* 322 */           return true;
/*     */         }
/*     */         
/*     */         public CharIterator iterator() {
/* 326 */           return new CharIterator() {
/* 327 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 330 */                 return (this.pos < Char2ObjectArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public char nextChar() {
/* 335 */                 if (!hasNext())
/* 336 */                   throw new NoSuchElementException(); 
/* 337 */                 return Char2ObjectArrayMap.this.key[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 341 */                 if (this.pos == 0)
/* 342 */                   throw new IllegalStateException(); 
/* 343 */                 int tail = Char2ObjectArrayMap.this.size - this.pos;
/* 344 */                 System.arraycopy(Char2ObjectArrayMap.this.key, this.pos, Char2ObjectArrayMap.this.key, this.pos - 1, tail);
/* 345 */                 System.arraycopy(Char2ObjectArrayMap.this.value, this.pos, Char2ObjectArrayMap.this.value, this.pos - 1, tail);
/* 346 */                 Char2ObjectArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 352 */           return Char2ObjectArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 356 */           Char2ObjectArrayMap.this.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public ObjectCollection<V> values() {
/* 362 */     return (ObjectCollection<V>)new AbstractObjectCollection<V>()
/*     */       {
/*     */         public boolean contains(Object v) {
/* 365 */           return Char2ObjectArrayMap.this.containsValue(v);
/*     */         }
/*     */         
/*     */         public ObjectIterator<V> iterator() {
/* 369 */           return new ObjectIterator<V>() {
/* 370 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 373 */                 return (this.pos < Char2ObjectArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public V next() {
/* 378 */                 if (!hasNext())
/* 379 */                   throw new NoSuchElementException(); 
/* 380 */                 return (V)Char2ObjectArrayMap.this.value[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 384 */                 if (this.pos == 0)
/* 385 */                   throw new IllegalStateException(); 
/* 386 */                 int tail = Char2ObjectArrayMap.this.size - this.pos;
/* 387 */                 System.arraycopy(Char2ObjectArrayMap.this.key, this.pos, Char2ObjectArrayMap.this.key, this.pos - 1, tail);
/* 388 */                 System.arraycopy(Char2ObjectArrayMap.this.value, this.pos, Char2ObjectArrayMap.this.value, this.pos - 1, tail);
/* 389 */                 Char2ObjectArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 395 */           return Char2ObjectArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 399 */           Char2ObjectArrayMap.this.clear();
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
/*     */   public Char2ObjectArrayMap<V> clone() {
/*     */     Char2ObjectArrayMap<V> c;
/*     */     try {
/* 418 */       c = (Char2ObjectArrayMap<V>)super.clone();
/* 419 */     } catch (CloneNotSupportedException cantHappen) {
/* 420 */       throw new InternalError();
/*     */     } 
/* 422 */     c.key = (char[])this.key.clone();
/* 423 */     c.value = (Object[])this.value.clone();
/* 424 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 427 */     s.defaultWriteObject();
/* 428 */     for (int i = 0; i < this.size; i++) {
/* 429 */       s.writeChar(this.key[i]);
/* 430 */       s.writeObject(this.value[i]);
/*     */     } 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 434 */     s.defaultReadObject();
/* 435 */     this.key = new char[this.size];
/* 436 */     this.value = new Object[this.size];
/* 437 */     for (int i = 0; i < this.size; i++) {
/* 438 */       this.key[i] = s.readChar();
/* 439 */       this.value[i] = s.readObject();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\Char2ObjectArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */