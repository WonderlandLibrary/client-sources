/*     */ package it.unimi.dsi.fastutil.chars;
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
/*     */ public class Char2CharArrayMap
/*     */   extends AbstractChar2CharMap
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient char[] key;
/*     */   private transient char[] value;
/*     */   private int size;
/*     */   
/*     */   public Char2CharArrayMap(char[] key, char[] value) {
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
/*     */   public Char2CharArrayMap() {
/*  67 */     this.key = CharArrays.EMPTY_ARRAY;
/*  68 */     this.value = CharArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Char2CharArrayMap(int capacity) {
/*  77 */     this.key = new char[capacity];
/*  78 */     this.value = new char[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Char2CharArrayMap(Char2CharMap m) {
/*  87 */     this(m.size());
/*  88 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Char2CharArrayMap(Map<? extends Character, ? extends Character> m) {
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
/*     */   public Char2CharArrayMap(char[] key, char[] value, int size) {
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
/*     */     extends AbstractObjectSet<Char2CharMap.Entry> implements Char2CharMap.FastEntrySet {
/*     */     public ObjectIterator<Char2CharMap.Entry> iterator() {
/* 130 */       return new ObjectIterator<Char2CharMap.Entry>() {
/* 131 */           int curr = -1; int next = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 134 */             return (this.next < Char2CharArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Char2CharMap.Entry next() {
/* 139 */             if (!hasNext())
/* 140 */               throw new NoSuchElementException(); 
/* 141 */             return new AbstractChar2CharMap.BasicEntry(Char2CharArrayMap.this.key[this.curr = this.next], Char2CharArrayMap.this.value[this.next++]);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 145 */             if (this.curr == -1)
/* 146 */               throw new IllegalStateException(); 
/* 147 */             this.curr = -1;
/* 148 */             int tail = Char2CharArrayMap.this.size-- - this.next--;
/* 149 */             System.arraycopy(Char2CharArrayMap.this.key, this.next + 1, Char2CharArrayMap.this.key, this.next, tail);
/* 150 */             System.arraycopy(Char2CharArrayMap.this.value, this.next + 1, Char2CharArrayMap.this.value, this.next, tail);
/*     */           }
/*     */         };
/*     */     }
/*     */     private EntrySet() {}
/*     */     public ObjectIterator<Char2CharMap.Entry> fastIterator() {
/* 156 */       return new ObjectIterator<Char2CharMap.Entry>() {
/* 157 */           int next = 0; int curr = -1;
/* 158 */           final AbstractChar2CharMap.BasicEntry entry = new AbstractChar2CharMap.BasicEntry();
/*     */           
/*     */           public boolean hasNext() {
/* 161 */             return (this.next < Char2CharArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Char2CharMap.Entry next() {
/* 166 */             if (!hasNext())
/* 167 */               throw new NoSuchElementException(); 
/* 168 */             this.entry.key = Char2CharArrayMap.this.key[this.curr = this.next];
/* 169 */             this.entry.value = Char2CharArrayMap.this.value[this.next++];
/* 170 */             return this.entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 174 */             if (this.curr == -1)
/* 175 */               throw new IllegalStateException(); 
/* 176 */             this.curr = -1;
/* 177 */             int tail = Char2CharArrayMap.this.size-- - this.next--;
/* 178 */             System.arraycopy(Char2CharArrayMap.this.key, this.next + 1, Char2CharArrayMap.this.key, this.next, tail);
/* 179 */             System.arraycopy(Char2CharArrayMap.this.value, this.next + 1, Char2CharArrayMap.this.value, this.next, tail);
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 185 */       return Char2CharArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 190 */       if (!(o instanceof Map.Entry))
/* 191 */         return false; 
/* 192 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 193 */       if (e.getKey() == null || !(e.getKey() instanceof Character))
/* 194 */         return false; 
/* 195 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/* 196 */         return false; 
/* 197 */       char k = ((Character)e.getKey()).charValue();
/* 198 */       return (Char2CharArrayMap.this.containsKey(k) && Char2CharArrayMap.this
/* 199 */         .get(k) == ((Character)e.getValue()).charValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 204 */       if (!(o instanceof Map.Entry))
/* 205 */         return false; 
/* 206 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 207 */       if (e.getKey() == null || !(e.getKey() instanceof Character))
/* 208 */         return false; 
/* 209 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/* 210 */         return false; 
/* 211 */       char k = ((Character)e.getKey()).charValue();
/* 212 */       char v = ((Character)e.getValue()).charValue();
/* 213 */       int oldPos = Char2CharArrayMap.this.findKey(k);
/* 214 */       if (oldPos == -1 || v != Char2CharArrayMap.this.value[oldPos])
/* 215 */         return false; 
/* 216 */       int tail = Char2CharArrayMap.this.size - oldPos - 1;
/* 217 */       System.arraycopy(Char2CharArrayMap.this.key, oldPos + 1, Char2CharArrayMap.this.key, oldPos, tail);
/* 218 */       System.arraycopy(Char2CharArrayMap.this.value, oldPos + 1, Char2CharArrayMap.this.value, oldPos, tail);
/* 219 */       Char2CharArrayMap.this.size--;
/* 220 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Char2CharMap.FastEntrySet char2CharEntrySet() {
/* 225 */     return new EntrySet();
/*     */   }
/*     */   private int findKey(char k) {
/* 228 */     char[] key = this.key;
/* 229 */     for (int i = this.size; i-- != 0;) {
/* 230 */       if (key[i] == k)
/* 231 */         return i; 
/* 232 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public char get(char k) {
/* 237 */     char[] key = this.key;
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
/*     */   public boolean containsKey(char k) {
/* 253 */     return (findKey(k) != -1);
/*     */   }
/*     */   
/*     */   public boolean containsValue(char v) {
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
/*     */   public char put(char k, char v) {
/* 269 */     int oldKey = findKey(k);
/* 270 */     if (oldKey != -1) {
/* 271 */       char oldValue = this.value[oldKey];
/* 272 */       this.value[oldKey] = v;
/* 273 */       return oldValue;
/*     */     } 
/* 275 */     if (this.size == this.key.length) {
/* 276 */       char[] newKey = new char[(this.size == 0) ? 2 : (this.size * 2)];
/* 277 */       char[] newValue = new char[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public char remove(char k) {
/* 293 */     int oldPos = findKey(k);
/* 294 */     if (oldPos == -1)
/* 295 */       return this.defRetValue; 
/* 296 */     char oldValue = this.value[oldPos];
/* 297 */     int tail = this.size - oldPos - 1;
/* 298 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 299 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 300 */     this.size--;
/* 301 */     return oldValue;
/*     */   }
/*     */   
/*     */   public CharSet keySet() {
/* 305 */     return new AbstractCharSet()
/*     */       {
/*     */         public boolean contains(char k) {
/* 308 */           return (Char2CharArrayMap.this.findKey(k) != -1);
/*     */         }
/*     */         
/*     */         public boolean remove(char k) {
/* 312 */           int oldPos = Char2CharArrayMap.this.findKey(k);
/* 313 */           if (oldPos == -1)
/* 314 */             return false; 
/* 315 */           int tail = Char2CharArrayMap.this.size - oldPos - 1;
/* 316 */           System.arraycopy(Char2CharArrayMap.this.key, oldPos + 1, Char2CharArrayMap.this.key, oldPos, tail);
/* 317 */           System.arraycopy(Char2CharArrayMap.this.value, oldPos + 1, Char2CharArrayMap.this.value, oldPos, tail);
/* 318 */           Char2CharArrayMap.this.size--;
/* 319 */           return true;
/*     */         }
/*     */         
/*     */         public CharIterator iterator() {
/* 323 */           return new CharIterator() {
/* 324 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 327 */                 return (this.pos < Char2CharArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public char nextChar() {
/* 332 */                 if (!hasNext())
/* 333 */                   throw new NoSuchElementException(); 
/* 334 */                 return Char2CharArrayMap.this.key[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 338 */                 if (this.pos == 0)
/* 339 */                   throw new IllegalStateException(); 
/* 340 */                 int tail = Char2CharArrayMap.this.size - this.pos;
/* 341 */                 System.arraycopy(Char2CharArrayMap.this.key, this.pos, Char2CharArrayMap.this.key, this.pos - 1, tail);
/* 342 */                 System.arraycopy(Char2CharArrayMap.this.value, this.pos, Char2CharArrayMap.this.value, this.pos - 1, tail);
/* 343 */                 Char2CharArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 349 */           return Char2CharArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 353 */           Char2CharArrayMap.this.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public CharCollection values() {
/* 359 */     return new AbstractCharCollection()
/*     */       {
/*     */         public boolean contains(char v) {
/* 362 */           return Char2CharArrayMap.this.containsValue(v);
/*     */         }
/*     */         
/*     */         public CharIterator iterator() {
/* 366 */           return new CharIterator() {
/* 367 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 370 */                 return (this.pos < Char2CharArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public char nextChar() {
/* 375 */                 if (!hasNext())
/* 376 */                   throw new NoSuchElementException(); 
/* 377 */                 return Char2CharArrayMap.this.value[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 381 */                 if (this.pos == 0)
/* 382 */                   throw new IllegalStateException(); 
/* 383 */                 int tail = Char2CharArrayMap.this.size - this.pos;
/* 384 */                 System.arraycopy(Char2CharArrayMap.this.key, this.pos, Char2CharArrayMap.this.key, this.pos - 1, tail);
/* 385 */                 System.arraycopy(Char2CharArrayMap.this.value, this.pos, Char2CharArrayMap.this.value, this.pos - 1, tail);
/* 386 */                 Char2CharArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 392 */           return Char2CharArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 396 */           Char2CharArrayMap.this.clear();
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
/*     */   public Char2CharArrayMap clone() {
/*     */     Char2CharArrayMap c;
/*     */     try {
/* 415 */       c = (Char2CharArrayMap)super.clone();
/* 416 */     } catch (CloneNotSupportedException cantHappen) {
/* 417 */       throw new InternalError();
/*     */     } 
/* 419 */     c.key = (char[])this.key.clone();
/* 420 */     c.value = (char[])this.value.clone();
/* 421 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 424 */     s.defaultWriteObject();
/* 425 */     for (int i = 0; i < this.size; i++) {
/* 426 */       s.writeChar(this.key[i]);
/* 427 */       s.writeChar(this.value[i]);
/*     */     } 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 431 */     s.defaultReadObject();
/* 432 */     this.key = new char[this.size];
/* 433 */     this.value = new char[this.size];
/* 434 */     for (int i = 0; i < this.size; i++) {
/* 435 */       this.key[i] = s.readChar();
/* 436 */       this.value[i] = s.readChar();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\Char2CharArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */