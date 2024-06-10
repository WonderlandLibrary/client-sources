/*     */ package it.unimi.dsi.fastutil.doubles;
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
/*     */ public class Double2CharArrayMap
/*     */   extends AbstractDouble2CharMap
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient double[] key;
/*     */   private transient char[] value;
/*     */   private int size;
/*     */   
/*     */   public Double2CharArrayMap(double[] key, char[] value) {
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
/*     */   public Double2CharArrayMap() {
/*  67 */     this.key = DoubleArrays.EMPTY_ARRAY;
/*  68 */     this.value = CharArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Double2CharArrayMap(int capacity) {
/*  77 */     this.key = new double[capacity];
/*  78 */     this.value = new char[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Double2CharArrayMap(Double2CharMap m) {
/*  87 */     this(m.size());
/*  88 */     putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Double2CharArrayMap(Map<? extends Double, ? extends Character> m) {
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
/*     */   public Double2CharArrayMap(double[] key, char[] value, int size) {
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
/*     */     extends AbstractObjectSet<Double2CharMap.Entry> implements Double2CharMap.FastEntrySet {
/*     */     public ObjectIterator<Double2CharMap.Entry> iterator() {
/* 130 */       return new ObjectIterator<Double2CharMap.Entry>() {
/* 131 */           int curr = -1; int next = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 134 */             return (this.next < Double2CharArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Double2CharMap.Entry next() {
/* 139 */             if (!hasNext())
/* 140 */               throw new NoSuchElementException(); 
/* 141 */             return new AbstractDouble2CharMap.BasicEntry(Double2CharArrayMap.this.key[this.curr = this.next], Double2CharArrayMap.this.value[this.next++]);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 145 */             if (this.curr == -1)
/* 146 */               throw new IllegalStateException(); 
/* 147 */             this.curr = -1;
/* 148 */             int tail = Double2CharArrayMap.this.size-- - this.next--;
/* 149 */             System.arraycopy(Double2CharArrayMap.this.key, this.next + 1, Double2CharArrayMap.this.key, this.next, tail);
/* 150 */             System.arraycopy(Double2CharArrayMap.this.value, this.next + 1, Double2CharArrayMap.this.value, this.next, tail);
/*     */           }
/*     */         };
/*     */     }
/*     */     private EntrySet() {}
/*     */     public ObjectIterator<Double2CharMap.Entry> fastIterator() {
/* 156 */       return new ObjectIterator<Double2CharMap.Entry>() {
/* 157 */           int next = 0; int curr = -1;
/* 158 */           final AbstractDouble2CharMap.BasicEntry entry = new AbstractDouble2CharMap.BasicEntry();
/*     */           
/*     */           public boolean hasNext() {
/* 161 */             return (this.next < Double2CharArrayMap.this.size);
/*     */           }
/*     */ 
/*     */           
/*     */           public Double2CharMap.Entry next() {
/* 166 */             if (!hasNext())
/* 167 */               throw new NoSuchElementException(); 
/* 168 */             this.entry.key = Double2CharArrayMap.this.key[this.curr = this.next];
/* 169 */             this.entry.value = Double2CharArrayMap.this.value[this.next++];
/* 170 */             return this.entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 174 */             if (this.curr == -1)
/* 175 */               throw new IllegalStateException(); 
/* 176 */             this.curr = -1;
/* 177 */             int tail = Double2CharArrayMap.this.size-- - this.next--;
/* 178 */             System.arraycopy(Double2CharArrayMap.this.key, this.next + 1, Double2CharArrayMap.this.key, this.next, tail);
/* 179 */             System.arraycopy(Double2CharArrayMap.this.value, this.next + 1, Double2CharArrayMap.this.value, this.next, tail);
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 185 */       return Double2CharArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 190 */       if (!(o instanceof Map.Entry))
/* 191 */         return false; 
/* 192 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 193 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 194 */         return false; 
/* 195 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/* 196 */         return false; 
/* 197 */       double k = ((Double)e.getKey()).doubleValue();
/* 198 */       return (Double2CharArrayMap.this.containsKey(k) && Double2CharArrayMap.this
/* 199 */         .get(k) == ((Character)e.getValue()).charValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 204 */       if (!(o instanceof Map.Entry))
/* 205 */         return false; 
/* 206 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 207 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 208 */         return false; 
/* 209 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/* 210 */         return false; 
/* 211 */       double k = ((Double)e.getKey()).doubleValue();
/* 212 */       char v = ((Character)e.getValue()).charValue();
/* 213 */       int oldPos = Double2CharArrayMap.this.findKey(k);
/* 214 */       if (oldPos == -1 || v != Double2CharArrayMap.this.value[oldPos])
/* 215 */         return false; 
/* 216 */       int tail = Double2CharArrayMap.this.size - oldPos - 1;
/* 217 */       System.arraycopy(Double2CharArrayMap.this.key, oldPos + 1, Double2CharArrayMap.this.key, oldPos, tail);
/* 218 */       System.arraycopy(Double2CharArrayMap.this.value, oldPos + 1, Double2CharArrayMap.this.value, oldPos, tail);
/* 219 */       Double2CharArrayMap.this.size--;
/* 220 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Double2CharMap.FastEntrySet double2CharEntrySet() {
/* 225 */     return new EntrySet();
/*     */   }
/*     */   private int findKey(double k) {
/* 228 */     double[] key = this.key;
/* 229 */     for (int i = this.size; i-- != 0;) {
/* 230 */       if (Double.doubleToLongBits(key[i]) == Double.doubleToLongBits(k))
/* 231 */         return i; 
/* 232 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public char get(double k) {
/* 237 */     double[] key = this.key;
/* 238 */     for (int i = this.size; i-- != 0;) {
/* 239 */       if (Double.doubleToLongBits(key[i]) == Double.doubleToLongBits(k))
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
/*     */   public boolean containsKey(double k) {
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
/*     */   public char put(double k, char v) {
/* 269 */     int oldKey = findKey(k);
/* 270 */     if (oldKey != -1) {
/* 271 */       char oldValue = this.value[oldKey];
/* 272 */       this.value[oldKey] = v;
/* 273 */       return oldValue;
/*     */     } 
/* 275 */     if (this.size == this.key.length) {
/* 276 */       double[] newKey = new double[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public char remove(double k) {
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
/*     */   public DoubleSet keySet() {
/* 305 */     return new AbstractDoubleSet()
/*     */       {
/*     */         public boolean contains(double k) {
/* 308 */           return (Double2CharArrayMap.this.findKey(k) != -1);
/*     */         }
/*     */         
/*     */         public boolean remove(double k) {
/* 312 */           int oldPos = Double2CharArrayMap.this.findKey(k);
/* 313 */           if (oldPos == -1)
/* 314 */             return false; 
/* 315 */           int tail = Double2CharArrayMap.this.size - oldPos - 1;
/* 316 */           System.arraycopy(Double2CharArrayMap.this.key, oldPos + 1, Double2CharArrayMap.this.key, oldPos, tail);
/* 317 */           System.arraycopy(Double2CharArrayMap.this.value, oldPos + 1, Double2CharArrayMap.this.value, oldPos, tail);
/* 318 */           Double2CharArrayMap.this.size--;
/* 319 */           return true;
/*     */         }
/*     */         
/*     */         public DoubleIterator iterator() {
/* 323 */           return new DoubleIterator() {
/* 324 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 327 */                 return (this.pos < Double2CharArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public double nextDouble() {
/* 332 */                 if (!hasNext())
/* 333 */                   throw new NoSuchElementException(); 
/* 334 */                 return Double2CharArrayMap.this.key[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 338 */                 if (this.pos == 0)
/* 339 */                   throw new IllegalStateException(); 
/* 340 */                 int tail = Double2CharArrayMap.this.size - this.pos;
/* 341 */                 System.arraycopy(Double2CharArrayMap.this.key, this.pos, Double2CharArrayMap.this.key, this.pos - 1, tail);
/* 342 */                 System.arraycopy(Double2CharArrayMap.this.value, this.pos, Double2CharArrayMap.this.value, this.pos - 1, tail);
/* 343 */                 Double2CharArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 349 */           return Double2CharArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 353 */           Double2CharArrayMap.this.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public CharCollection values() {
/* 359 */     return (CharCollection)new AbstractCharCollection()
/*     */       {
/*     */         public boolean contains(char v) {
/* 362 */           return Double2CharArrayMap.this.containsValue(v);
/*     */         }
/*     */         
/*     */         public CharIterator iterator() {
/* 366 */           return new CharIterator() {
/* 367 */               int pos = 0;
/*     */               
/*     */               public boolean hasNext() {
/* 370 */                 return (this.pos < Double2CharArrayMap.this.size);
/*     */               }
/*     */ 
/*     */               
/*     */               public char nextChar() {
/* 375 */                 if (!hasNext())
/* 376 */                   throw new NoSuchElementException(); 
/* 377 */                 return Double2CharArrayMap.this.value[this.pos++];
/*     */               }
/*     */               
/*     */               public void remove() {
/* 381 */                 if (this.pos == 0)
/* 382 */                   throw new IllegalStateException(); 
/* 383 */                 int tail = Double2CharArrayMap.this.size - this.pos;
/* 384 */                 System.arraycopy(Double2CharArrayMap.this.key, this.pos, Double2CharArrayMap.this.key, this.pos - 1, tail);
/* 385 */                 System.arraycopy(Double2CharArrayMap.this.value, this.pos, Double2CharArrayMap.this.value, this.pos - 1, tail);
/* 386 */                 Double2CharArrayMap.this.size--;
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 392 */           return Double2CharArrayMap.this.size;
/*     */         }
/*     */         
/*     */         public void clear() {
/* 396 */           Double2CharArrayMap.this.clear();
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
/*     */   public Double2CharArrayMap clone() {
/*     */     Double2CharArrayMap c;
/*     */     try {
/* 415 */       c = (Double2CharArrayMap)super.clone();
/* 416 */     } catch (CloneNotSupportedException cantHappen) {
/* 417 */       throw new InternalError();
/*     */     } 
/* 419 */     c.key = (double[])this.key.clone();
/* 420 */     c.value = (char[])this.value.clone();
/* 421 */     return c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 424 */     s.defaultWriteObject();
/* 425 */     for (int i = 0; i < this.size; i++) {
/* 426 */       s.writeDouble(this.key[i]);
/* 427 */       s.writeChar(this.value[i]);
/*     */     } 
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 431 */     s.defaultReadObject();
/* 432 */     this.key = new double[this.size];
/* 433 */     this.value = new char[this.size];
/* 434 */     for (int i = 0; i < this.size; i++) {
/* 435 */       this.key[i] = s.readDouble();
/* 436 */       this.value[i] = s.readChar();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2CharArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */