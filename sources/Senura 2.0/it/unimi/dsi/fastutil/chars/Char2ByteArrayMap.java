/*     */ package it.unimi.dsi.fastutil.chars;
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
/*     */ public class Char2ByteArrayMap
/*     */   extends AbstractChar2ByteMap
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient char[] key;
/*     */   private transient byte[] value;
/*     */   private int size;
/*     */   
/*     */   public Char2ByteArrayMap(char[] key, byte[] value) {
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
/*     */   public Char2ByteArrayMap() {
/*  67 */     this.key = CharArrays.EMPTY_ARRAY;
/*  68 */     this.value = ByteArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Char2ByteArrayMap(int capacity) {
/*  77 */     this.key = new char[capacity];
/*  78 */     this.value = new byte[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Char2ByteArrayMap(Char2ByteMap m) {
/*  87 */     this(m.size());
/*  88 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Char2ByteArrayMap(Map<? extends Character, ? extends Byte> m) {
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
/*     */   public Char2ByteArrayMap(char[] key, byte[] value, int size) {
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
/*     */     extends AbstractObjectSet<Char2ByteMap.Entry> implements Char2ByteMap.FastEntrySet {
/*     */     public ObjectIterator<Char2ByteMap.Entry> iterator() {
/* 130 */       return new ObjectIterator<Char2ByteMap.Entry>() {
/* 131 */           int curr = -1; int next = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 134 */             return (this.next < Char2ByteArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Char2ByteMap.Entry next() {
/* 139 */             if (!hasNext())
/* 140 */               throw new NoSuchElementException(); 
/* 141 */             return new AbstractChar2ByteMap.BasicEntry(Char2ByteArrayMap.this.key[this.curr = this.next], Char2ByteArrayMap.this.value[this.next++]);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 145 */             if (this.curr == -1)
/* 146 */               throw new IllegalStateException(); 
/* 147 */             this.curr = -1;
/* 148 */             int tail = Char2ByteArrayMap.this.size-- - this.next--;
/* 149 */             System.arraycopy(Char2ByteArrayMap.this.key, this.next + 1, Char2ByteArrayMap.this.key, this.next, tail);
/* 150 */             System.arraycopy(Char2ByteArrayMap.this.value, this.next + 1, Char2ByteArrayMap.this.value, this.next, tail);
/*     */           }
/*     */         };
/*     */     }
/*     */     private EntrySet() {}
/*     */     public ObjectIterator<Char2ByteMap.Entry> fastIterator() {
/* 156 */       return new ObjectIterator<Char2ByteMap.Entry>() {
/* 157 */           int next = 0; int curr = -1;
/* 158 */           final AbstractChar2ByteMap.BasicEntry entry = new AbstractChar2ByteMap.BasicEntry();
/*     */           
/*     */           public boolean hasNext() {
/* 161 */             return (this.next < Char2ByteArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Char2ByteMap.Entry next() {
/* 166 */             if (!hasNext())
/* 167 */               throw new NoSuchElementException(); 
/* 168 */             this.entry.key = Char2ByteArrayMap.this.key[this.curr = this.next];
/* 169 */             this.entry.value = Char2ByteArrayMap.this.value[this.next++];
/* 170 */             return this.entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 174 */             if (this.curr == -1)
/* 175 */               throw new IllegalStateException(); 
/* 176 */             this.curr = -1;
/* 177 */             int tail = Char2ByteArrayMap.this.size-- - this.next--;
/* 178 */             System.arraycopy(Char2ByteArrayMap.this.key, this.next + 1, Char2ByteArrayMap.this.key, this.next, tail);
/* 179 */             System.arraycopy(Char2ByteArrayMap.this.value, this.next + 1, Char2ByteArrayMap.this.value, this.next, tail);
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 185 */       return Char2ByteArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 190 */       if (!(o instanceof Map.Entry))
/* 191 */         return false; 
/* 192 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 193 */       if (e.getKey() == null || !(e.getKey() instanceof Character))
/* 194 */         return false; 
/* 195 */       if (e.getValue() == null || !(e.getValue() instanceof Byte))
/* 196 */         return false; 
/* 197 */       char k = ((Character)e.getKey()).charValue();
/* 198 */       return (Char2ByteArrayMap.this.containsKey(k) && Char2ByteArrayMap.this
/* 199 */         .get(k) == ((Byte)e.getValue()).byteValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 204 */       if (!(o instanceof Map.Entry))
/* 205 */         return false; 
/* 206 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 207 */       if (e.getKey() == null || !(e.getKey() instanceof Character))
/* 208 */         return false; 
/* 209 */       if (e.getValue() == null || !(e.getValue() instanceof Byte))
/* 210 */         return false; 
/* 211 */       char k = ((Character)e.getKey()).charValue();
/* 212 */       byte v = ((Byte)e.getValue()).byteValue();
/* 213 */       int oldPos = Char2ByteArrayMap.this.findKey(k);
/* 214 */       if (oldPos == -1 || v != Char2ByteArrayMap.this.value[oldPos])
/* 215 */         return false; 
/* 216 */       int tail = Char2ByteArrayMap.this.size - oldPos - 1;
/* 217 */       System.arraycopy(Char2ByteArrayMap.this.key, oldPos + 1, Char2ByteArrayMap.this.key, oldPos, tail);
/* 218 */       System.arraycopy(Char2ByteArrayMap.this.value, oldPos + 1, Char2ByteArrayMap.this.value, oldPos, tail);
/* 219 */       Char2ByteArrayMap.this.size--;
/* 220 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Char2ByteMap.FastEntrySet char2ByteEntrySet() {
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
/*     */   public byte get(char k) {
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
/*     */   public byte put(char k, byte v) {
/* 269 */     int oldKey = findKey(k);
/* 270 */     if (oldKey != -1) {
/* 271 */       byte oldValue = this.value[oldKey];
/* 272 */       this.value[oldKey] = v;
/* 273 */       return oldValue;
/*     */     } 
/* 275 */     if (this.size == this.key.length) {
/* 276 */       char[] newKey = new char[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public byte remove(char k) {
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
/*     */   public CharSet keySet() {
/* 305 */     return new AbstractCharSet()
/*     */       {
/*     */         public boolean contains(char k) {
/* 308 */           return (Char2ByteArrayMap.this.findKey(k) != -1);
/*     */         }
/*     */         
/*     */         public boolean remove(char k) {
/* 312 */           int oldPos = Char2ByteArrayMap.this.findKey(k);
/* 313 */           if (oldPos == -1)
/* 314 */             return false; 
/* 315 */           int tail = Char2ByteArrayMap.this.size - oldPos - 1;
/* 316 */           System.arraycopy(Char2ByteArrayMap.this.key, oldPos + 1, Char2ByteArrayMap.this.key, oldPos, tail);
/* 317 */           System.arraycopy(Char2ByteArrayMap.this.value, oldPos + 1, Char2ByteArrayMap.this.value, oldPos, tail);
/* 318 */           Char2ByteArrayMap.this.size--;
/* 319 */           return true;
/*     */         }
/*     */         
/*     */         public CharIterator iterator() {
/* 323 */           return new CharIterator() {
/* 324 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 327 */                 return (this.pos < Char2ByteArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public char nextChar() {
/* 332 */                 if (!hasNext())
/* 333 */                   throw new NoSuchElementException(); 
/* 334 */                 return Char2ByteArrayMap.this.key[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 338 */                 if (this.pos == 0)
/* 339 */                   throw new IllegalStateException(); 
/* 340 */                 int tail = Char2ByteArrayMap.this.size - this.pos;
/* 341 */                 System.arraycopy(Char2ByteArrayMap.this.key, this.pos, Char2ByteArrayMap.this.key, this.pos - 1, tail);
/* 342 */                 System.arraycopy(Char2ByteArrayMap.this.value, this.pos, Char2ByteArrayMap.this.value, this.pos - 1, tail);
/* 343 */                 Char2ByteArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 349 */           return Char2ByteArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 353 */           Char2ByteArrayMap.this.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public ByteCollection values() {
/* 359 */     return (ByteCollection)new AbstractByteCollection()
/*     */       {
/*     */         public boolean contains(byte v) {
/* 362 */           return Char2ByteArrayMap.this.containsValue(v);
/*     */         }
/*     */         
/*     */         public ByteIterator iterator() {
/* 366 */           return new ByteIterator() {
/* 367 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 370 */                 return (this.pos < Char2ByteArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public byte nextByte() {
/* 375 */                 if (!hasNext())
/* 376 */                   throw new NoSuchElementException(); 
/* 377 */                 return Char2ByteArrayMap.this.value[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 381 */                 if (this.pos == 0)
/* 382 */                   throw new IllegalStateException(); 
/* 383 */                 int tail = Char2ByteArrayMap.this.size - this.pos;
/* 384 */                 System.arraycopy(Char2ByteArrayMap.this.key, this.pos, Char2ByteArrayMap.this.key, this.pos - 1, tail);
/* 385 */                 System.arraycopy(Char2ByteArrayMap.this.value, this.pos, Char2ByteArrayMap.this.value, this.pos - 1, tail);
/* 386 */                 Char2ByteArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 392 */           return Char2ByteArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 396 */           Char2ByteArrayMap.this.clear();
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
/*     */   public Char2ByteArrayMap clone() {
/*     */     Char2ByteArrayMap c;
/*     */     try {
/* 415 */       c = (Char2ByteArrayMap)super.clone();
/* 416 */     } catch (CloneNotSupportedException cantHappen) {
/* 417 */       throw new InternalError();
/*     */     } 
/* 419 */     c.key = (char[])this.key.clone();
/* 420 */     c.value = (byte[])this.value.clone();
/* 421 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 424 */     s.defaultWriteObject();
/* 425 */     for (int i = 0; i < this.size; i++) {
/* 426 */       s.writeChar(this.key[i]);
/* 427 */       s.writeByte(this.value[i]);
/*     */     } 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 431 */     s.defaultReadObject();
/* 432 */     this.key = new char[this.size];
/* 433 */     this.value = new byte[this.size];
/* 434 */     for (int i = 0; i < this.size; i++) {
/* 435 */       this.key[i] = s.readChar();
/* 436 */       this.value[i] = s.readByte();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\Char2ByteArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */