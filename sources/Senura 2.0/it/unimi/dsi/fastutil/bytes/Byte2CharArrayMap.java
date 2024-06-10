/*     */ package it.unimi.dsi.fastutil.bytes;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
/*     */ import it.unimi.dsi.fastutil.chars.CharArrays;
/*     */ import it.unimi.dsi.fastutil.chars.CharCollection;
/*     */ import it.unimi.dsi.fastutil.chars.CharIterator;
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
/*     */ public class Byte2CharArrayMap
/*     */   extends AbstractByte2CharMap
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient byte[] key;
/*     */   private transient char[] value;
/*     */   private int size;
/*     */   
/*     */   public Byte2CharArrayMap(byte[] key, char[] value) {
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
/*     */   public Byte2CharArrayMap() {
/*  67 */     this.key = ByteArrays.EMPTY_ARRAY;
/*  68 */     this.value = CharArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Byte2CharArrayMap(int capacity) {
/*  77 */     this.key = new byte[capacity];
/*  78 */     this.value = new char[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Byte2CharArrayMap(Byte2CharMap m) {
/*  87 */     this(m.size());
/*  88 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Byte2CharArrayMap(Map<? extends Byte, ? extends Character> m) {
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
/*     */   public Byte2CharArrayMap(byte[] key, char[] value, int size) {
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
/*     */     extends AbstractObjectSet<Byte2CharMap.Entry> implements Byte2CharMap.FastEntrySet {
/*     */     public ObjectIterator<Byte2CharMap.Entry> iterator() {
/* 130 */       return new ObjectIterator<Byte2CharMap.Entry>() {
/* 131 */           int curr = -1; int next = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 134 */             return (this.next < Byte2CharArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Byte2CharMap.Entry next() {
/* 139 */             if (!hasNext())
/* 140 */               throw new NoSuchElementException(); 
/* 141 */             return new AbstractByte2CharMap.BasicEntry(Byte2CharArrayMap.this.key[this.curr = this.next], Byte2CharArrayMap.this.value[this.next++]);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 145 */             if (this.curr == -1)
/* 146 */               throw new IllegalStateException(); 
/* 147 */             this.curr = -1;
/* 148 */             int tail = Byte2CharArrayMap.this.size-- - this.next--;
/* 149 */             System.arraycopy(Byte2CharArrayMap.this.key, this.next + 1, Byte2CharArrayMap.this.key, this.next, tail);
/* 150 */             System.arraycopy(Byte2CharArrayMap.this.value, this.next + 1, Byte2CharArrayMap.this.value, this.next, tail);
/*     */           }
/*     */         };
/*     */     }
/*     */     private EntrySet() {}
/*     */     public ObjectIterator<Byte2CharMap.Entry> fastIterator() {
/* 156 */       return new ObjectIterator<Byte2CharMap.Entry>() {
/* 157 */           int next = 0; int curr = -1;
/* 158 */           final AbstractByte2CharMap.BasicEntry entry = new AbstractByte2CharMap.BasicEntry();
/*     */           
/*     */           public boolean hasNext() {
/* 161 */             return (this.next < Byte2CharArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Byte2CharMap.Entry next() {
/* 166 */             if (!hasNext())
/* 167 */               throw new NoSuchElementException(); 
/* 168 */             this.entry.key = Byte2CharArrayMap.this.key[this.curr = this.next];
/* 169 */             this.entry.value = Byte2CharArrayMap.this.value[this.next++];
/* 170 */             return this.entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 174 */             if (this.curr == -1)
/* 175 */               throw new IllegalStateException(); 
/* 176 */             this.curr = -1;
/* 177 */             int tail = Byte2CharArrayMap.this.size-- - this.next--;
/* 178 */             System.arraycopy(Byte2CharArrayMap.this.key, this.next + 1, Byte2CharArrayMap.this.key, this.next, tail);
/* 179 */             System.arraycopy(Byte2CharArrayMap.this.value, this.next + 1, Byte2CharArrayMap.this.value, this.next, tail);
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 185 */       return Byte2CharArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 190 */       if (!(o instanceof Map.Entry))
/* 191 */         return false; 
/* 192 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 193 */       if (e.getKey() == null || !(e.getKey() instanceof Byte))
/* 194 */         return false; 
/* 195 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/* 196 */         return false; 
/* 197 */       byte k = ((Byte)e.getKey()).byteValue();
/* 198 */       return (Byte2CharArrayMap.this.containsKey(k) && Byte2CharArrayMap.this
/* 199 */         .get(k) == ((Character)e.getValue()).charValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 204 */       if (!(o instanceof Map.Entry))
/* 205 */         return false; 
/* 206 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 207 */       if (e.getKey() == null || !(e.getKey() instanceof Byte))
/* 208 */         return false; 
/* 209 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/* 210 */         return false; 
/* 211 */       byte k = ((Byte)e.getKey()).byteValue();
/* 212 */       char v = ((Character)e.getValue()).charValue();
/* 213 */       int oldPos = Byte2CharArrayMap.this.findKey(k);
/* 214 */       if (oldPos == -1 || v != Byte2CharArrayMap.this.value[oldPos])
/* 215 */         return false; 
/* 216 */       int tail = Byte2CharArrayMap.this.size - oldPos - 1;
/* 217 */       System.arraycopy(Byte2CharArrayMap.this.key, oldPos + 1, Byte2CharArrayMap.this.key, oldPos, tail);
/* 218 */       System.arraycopy(Byte2CharArrayMap.this.value, oldPos + 1, Byte2CharArrayMap.this.value, oldPos, tail);
/* 219 */       Byte2CharArrayMap.this.size--;
/* 220 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Byte2CharMap.FastEntrySet byte2CharEntrySet() {
/* 225 */     return new EntrySet();
/*     */   }
/*     */   private int findKey(byte k) {
/* 228 */     byte[] key = this.key;
/* 229 */     for (int i = this.size; i-- != 0;) {
/* 230 */       if (key[i] == k)
/* 231 */         return i; 
/* 232 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public char get(byte k) {
/* 237 */     byte[] key = this.key;
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
/*     */   public boolean containsKey(byte k) {
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
/*     */   public char put(byte k, char v) {
/* 269 */     int oldKey = findKey(k);
/* 270 */     if (oldKey != -1) {
/* 271 */       char oldValue = this.value[oldKey];
/* 272 */       this.value[oldKey] = v;
/* 273 */       return oldValue;
/*     */     } 
/* 275 */     if (this.size == this.key.length) {
/* 276 */       byte[] newKey = new byte[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public char remove(byte k) {
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
/*     */   public ByteSet keySet() {
/* 305 */     return new AbstractByteSet()
/*     */       {
/*     */         public boolean contains(byte k) {
/* 308 */           return (Byte2CharArrayMap.this.findKey(k) != -1);
/*     */         }
/*     */         
/*     */         public boolean remove(byte k) {
/* 312 */           int oldPos = Byte2CharArrayMap.this.findKey(k);
/* 313 */           if (oldPos == -1)
/* 314 */             return false; 
/* 315 */           int tail = Byte2CharArrayMap.this.size - oldPos - 1;
/* 316 */           System.arraycopy(Byte2CharArrayMap.this.key, oldPos + 1, Byte2CharArrayMap.this.key, oldPos, tail);
/* 317 */           System.arraycopy(Byte2CharArrayMap.this.value, oldPos + 1, Byte2CharArrayMap.this.value, oldPos, tail);
/* 318 */           Byte2CharArrayMap.this.size--;
/* 319 */           return true;
/*     */         }
/*     */         
/*     */         public ByteIterator iterator() {
/* 323 */           return new ByteIterator() {
/* 324 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 327 */                 return (this.pos < Byte2CharArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public byte nextByte() {
/* 332 */                 if (!hasNext())
/* 333 */                   throw new NoSuchElementException(); 
/* 334 */                 return Byte2CharArrayMap.this.key[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 338 */                 if (this.pos == 0)
/* 339 */                   throw new IllegalStateException(); 
/* 340 */                 int tail = Byte2CharArrayMap.this.size - this.pos;
/* 341 */                 System.arraycopy(Byte2CharArrayMap.this.key, this.pos, Byte2CharArrayMap.this.key, this.pos - 1, tail);
/* 342 */                 System.arraycopy(Byte2CharArrayMap.this.value, this.pos, Byte2CharArrayMap.this.value, this.pos - 1, tail);
/* 343 */                 Byte2CharArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 349 */           return Byte2CharArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 353 */           Byte2CharArrayMap.this.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public CharCollection values() {
/* 359 */     return (CharCollection)new AbstractCharCollection()
/*     */       {
/*     */         public boolean contains(char v) {
/* 362 */           return Byte2CharArrayMap.this.containsValue(v);
/*     */         }
/*     */         
/*     */         public CharIterator iterator() {
/* 366 */           return new CharIterator() {
/* 367 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 370 */                 return (this.pos < Byte2CharArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public char nextChar() {
/* 375 */                 if (!hasNext())
/* 376 */                   throw new NoSuchElementException(); 
/* 377 */                 return Byte2CharArrayMap.this.value[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 381 */                 if (this.pos == 0)
/* 382 */                   throw new IllegalStateException(); 
/* 383 */                 int tail = Byte2CharArrayMap.this.size - this.pos;
/* 384 */                 System.arraycopy(Byte2CharArrayMap.this.key, this.pos, Byte2CharArrayMap.this.key, this.pos - 1, tail);
/* 385 */                 System.arraycopy(Byte2CharArrayMap.this.value, this.pos, Byte2CharArrayMap.this.value, this.pos - 1, tail);
/* 386 */                 Byte2CharArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 392 */           return Byte2CharArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 396 */           Byte2CharArrayMap.this.clear();
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
/*     */   public Byte2CharArrayMap clone() {
/*     */     Byte2CharArrayMap c;
/*     */     try {
/* 415 */       c = (Byte2CharArrayMap)super.clone();
/* 416 */     } catch (CloneNotSupportedException cantHappen) {
/* 417 */       throw new InternalError();
/*     */     } 
/* 419 */     c.key = (byte[])this.key.clone();
/* 420 */     c.value = (char[])this.value.clone();
/* 421 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 424 */     s.defaultWriteObject();
/* 425 */     for (int i = 0; i < this.size; i++) {
/* 426 */       s.writeByte(this.key[i]);
/* 427 */       s.writeChar(this.value[i]);
/*     */     } 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 431 */     s.defaultReadObject();
/* 432 */     this.key = new byte[this.size];
/* 433 */     this.value = new char[this.size];
/* 434 */     for (int i = 0; i < this.size; i++) {
/* 435 */       this.key[i] = s.readByte();
/* 436 */       this.value[i] = s.readChar();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\Byte2CharArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */