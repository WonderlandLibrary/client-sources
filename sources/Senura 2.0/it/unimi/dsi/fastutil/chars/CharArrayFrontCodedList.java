/*     */ package it.unimi.dsi.fastutil.chars;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.BigArrays;
/*     */ import it.unimi.dsi.fastutil.longs.LongArrays;
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.ListIterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.RandomAccess;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CharArrayFrontCodedList
/*     */   extends AbstractObjectList<char[]>
/*     */   implements Serializable, Cloneable, RandomAccess
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final int n;
/*     */   protected final int ratio;
/*     */   protected final char[][] array;
/*     */   protected transient long[] p;
/*     */   
/*     */   public CharArrayFrontCodedList(Iterator<char[]> arrays, int ratio) {
/* 115 */     if (ratio < 1)
/* 116 */       throw new IllegalArgumentException("Illegal ratio (" + ratio + ")"); 
/* 117 */     char[][] array = CharBigArrays.EMPTY_BIG_ARRAY;
/* 118 */     long[] p = LongArrays.EMPTY_ARRAY;
/* 119 */     char[][] a = new char[2][];
/* 120 */     long curSize = 0L;
/* 121 */     int n = 0, b = 0;
/* 122 */     while (arrays.hasNext()) {
/* 123 */       a[b] = arrays.next();
/* 124 */       int length = (a[b]).length;
/* 125 */       if (n % ratio == 0) {
/* 126 */         p = LongArrays.grow(p, n / ratio + 1);
/* 127 */         p[n / ratio] = curSize;
/* 128 */         array = BigArrays.grow(array, curSize + count(length) + length, curSize);
/* 129 */         curSize += writeInt(array, length, curSize);
/* 130 */         BigArrays.copyToBig(a[b], 0, array, curSize, length);
/* 131 */         curSize += length;
/*     */       } else {
/* 133 */         int minLength = (a[1 - b]).length;
/* 134 */         if (length < minLength)
/* 135 */           minLength = length;  int common;
/* 136 */         for (common = 0; common < minLength && 
/* 137 */           a[0][common] == a[1][common]; common++);
/*     */         
/* 139 */         length -= common;
/* 140 */         array = BigArrays.grow(array, curSize + count(length) + count(common) + length, curSize);
/* 141 */         curSize += writeInt(array, length, curSize);
/* 142 */         curSize += writeInt(array, common, curSize);
/* 143 */         BigArrays.copyToBig(a[b], common, array, curSize, length);
/* 144 */         curSize += length;
/*     */       } 
/* 146 */       b = 1 - b;
/* 147 */       n++;
/*     */     } 
/* 149 */     this.n = n;
/* 150 */     this.ratio = ratio;
/* 151 */     this.array = BigArrays.trim(array, curSize);
/* 152 */     this.p = LongArrays.trim(p, (n + ratio - 1) / ratio);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharArrayFrontCodedList(Collection<char[]> c, int ratio) {
/* 163 */     this((Iterator)c.iterator(), ratio);
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
/*     */   private static int readInt(char[][] a, long pos) {
/* 180 */     char c0 = BigArrays.get(a, pos);
/* 181 */     return (c0 < '耀') ? c0 : ((c0 & 0x7FFF) << 16 | BigArrays.get(a, pos + 1L));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int count(int length) {
/* 191 */     return (length < 32768) ? 1 : 2;
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
/*     */   private static int writeInt(char[][] a, int length, long pos) {
/* 205 */     if (length < 32768) {
/* 206 */       BigArrays.set(a, pos, (char)length);
/* 207 */       return 1;
/*     */     } 
/* 209 */     BigArrays.set(a, pos++, (char)(length >>> 16 | 0x8000));
/* 210 */     BigArrays.set(a, pos, (char)(length & 0xFFFF));
/* 211 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int ratio() {
/* 219 */     return this.ratio;
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
/*     */   private int length(int index) {
/* 233 */     char[][] array = this.array;
/* 234 */     int delta = index % this.ratio;
/* 235 */     long pos = this.p[index / this.ratio];
/* 236 */     int length = readInt(array, pos);
/* 237 */     if (delta == 0) {
/* 238 */       return length;
/*     */     }
/*     */ 
/*     */     
/* 242 */     pos += (count(length) + length);
/* 243 */     length = readInt(array, pos);
/* 244 */     int common = readInt(array, pos + count(length));
/* 245 */     for (int i = 0; i < delta - 1; i++) {
/* 246 */       pos += (count(length) + count(common) + length);
/* 247 */       length = readInt(array, pos);
/* 248 */       common = readInt(array, pos + count(length));
/*     */     } 
/* 250 */     return length + common;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int arrayLength(int index) {
/* 260 */     ensureRestrictedIndex(index);
/* 261 */     return length(index);
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
/*     */   private int extract(int index, char[] a, int offset, int length) {
/* 278 */     int delta = index % this.ratio;
/* 279 */     long startPos = this.p[index / this.ratio];
/*     */     
/*     */     long pos;
/* 282 */     int arrayLength = readInt(this.array, pos = startPos), currLen = 0;
/* 283 */     if (delta == 0) {
/* 284 */       pos = this.p[index / this.ratio] + count(arrayLength);
/* 285 */       BigArrays.copyFromBig(this.array, pos, a, offset, Math.min(length, arrayLength));
/* 286 */       return arrayLength;
/*     */     } 
/* 288 */     int common = 0;
/* 289 */     for (int i = 0; i < delta; i++) {
/* 290 */       long prevArrayPos = pos + count(arrayLength) + ((i != 0) ? count(common) : 0L);
/* 291 */       pos = prevArrayPos + arrayLength;
/* 292 */       arrayLength = readInt(this.array, pos);
/* 293 */       common = readInt(this.array, pos + count(arrayLength));
/* 294 */       int actualCommon = Math.min(common, length);
/* 295 */       if (actualCommon <= currLen) {
/* 296 */         currLen = actualCommon;
/*     */       } else {
/* 298 */         BigArrays.copyFromBig(this.array, prevArrayPos, a, currLen + offset, actualCommon - currLen);
/* 299 */         currLen = actualCommon;
/*     */       } 
/*     */     } 
/* 302 */     if (currLen < length)
/* 303 */       BigArrays.copyFromBig(this.array, pos + count(arrayLength) + count(common), a, currLen + offset, 
/* 304 */           Math.min(arrayLength, length - currLen)); 
/* 305 */     return arrayLength + common;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char[] get(int index) {
/* 314 */     return getArray(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char[] getArray(int index) {
/* 324 */     ensureRestrictedIndex(index);
/* 325 */     int length = length(index);
/* 326 */     char[] a = new char[length];
/* 327 */     extract(index, a, 0, length);
/* 328 */     return a;
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
/*     */   public int get(int index, char[] a, int offset, int length) {
/* 347 */     ensureRestrictedIndex(index);
/* 348 */     CharArrays.ensureOffsetLength(a, offset, length);
/* 349 */     int arrayLength = extract(index, a, offset, length);
/* 350 */     if (length >= arrayLength)
/* 351 */       return arrayLength; 
/* 352 */     return length - arrayLength;
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
/*     */   public int get(int index, char[] a) {
/* 367 */     return get(index, a, 0, a.length);
/*     */   }
/*     */   
/*     */   public int size() {
/* 371 */     return this.n;
/*     */   }
/*     */   
/*     */   public ObjectListIterator<char[]> listIterator(final int start) {
/* 375 */     ensureIndex(start);
/* 376 */     return new ObjectListIterator<char[]>() {
/* 377 */         char[] s = CharArrays.EMPTY_ARRAY;
/* 378 */         int i = 0;
/* 379 */         long pos = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         boolean inSync;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public boolean hasNext() {
/* 397 */           return (this.i < CharArrayFrontCodedList.this.n);
/*     */         }
/*     */         
/*     */         public boolean hasPrevious() {
/* 401 */           return (this.i > 0);
/*     */         }
/*     */         
/*     */         public int previousIndex() {
/* 405 */           return this.i - 1;
/*     */         }
/*     */         
/*     */         public int nextIndex() {
/* 409 */           return this.i;
/*     */         }
/*     */         
/*     */         public char[] next() {
/*     */           int length;
/* 414 */           if (!hasNext())
/* 415 */             throw new NoSuchElementException(); 
/* 416 */           if (this.i % CharArrayFrontCodedList.this.ratio == 0) {
/* 417 */             this.pos = CharArrayFrontCodedList.this.p[this.i / CharArrayFrontCodedList.this.ratio];
/* 418 */             length = CharArrayFrontCodedList.readInt(CharArrayFrontCodedList.this.array, this.pos);
/* 419 */             this.s = CharArrays.ensureCapacity(this.s, length, 0);
/* 420 */             BigArrays.copyFromBig(CharArrayFrontCodedList.this.array, this.pos + CharArrayFrontCodedList.count(length), this.s, 0, length);
/* 421 */             this.pos += (length + CharArrayFrontCodedList.count(length));
/* 422 */             this.inSync = true;
/*     */           }
/* 424 */           else if (this.inSync) {
/* 425 */             length = CharArrayFrontCodedList.readInt(CharArrayFrontCodedList.this.array, this.pos);
/* 426 */             int common = CharArrayFrontCodedList.readInt(CharArrayFrontCodedList.this.array, this.pos + CharArrayFrontCodedList.count(length));
/* 427 */             this.s = CharArrays.ensureCapacity(this.s, length + common, common);
/* 428 */             BigArrays.copyFromBig(CharArrayFrontCodedList.this.array, this.pos + CharArrayFrontCodedList.count(length) + CharArrayFrontCodedList.count(common), this.s, common, length);
/* 429 */             this.pos += (CharArrayFrontCodedList.count(length) + CharArrayFrontCodedList.count(common) + length);
/* 430 */             length += common;
/*     */           } else {
/* 432 */             this.s = CharArrays.ensureCapacity(this.s, length = CharArrayFrontCodedList.this.length(this.i), 0);
/* 433 */             CharArrayFrontCodedList.this.extract(this.i, this.s, 0, length);
/*     */           } 
/*     */           
/* 436 */           this.i++;
/* 437 */           return CharArrays.copy(this.s, 0, length);
/*     */         }
/*     */         
/*     */         public char[] previous() {
/* 441 */           if (!hasPrevious())
/* 442 */             throw new NoSuchElementException(); 
/* 443 */           this.inSync = false;
/* 444 */           return CharArrayFrontCodedList.this.getArray(--this.i);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharArrayFrontCodedList clone() {
/* 455 */     return this;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 459 */     StringBuffer s = new StringBuffer();
/* 460 */     s.append("[");
/* 461 */     for (int i = 0; i < this.n; i++) {
/* 462 */       if (i != 0)
/* 463 */         s.append(", "); 
/* 464 */       s.append(CharArrayList.wrap(getArray(i)).toString());
/*     */     } 
/* 466 */     s.append("]");
/* 467 */     return s.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected long[] rebuildPointerArray() {
/* 476 */     long[] p = new long[(this.n + this.ratio - 1) / this.ratio];
/* 477 */     char[][] a = this.array;
/*     */     
/* 479 */     long pos = 0L;
/* 480 */     for (int i = 0, j = 0, skip = this.ratio - 1; i < this.n; i++) {
/* 481 */       int length = readInt(a, pos);
/* 482 */       int count = count(length);
/* 483 */       if (++skip == this.ratio) {
/* 484 */         skip = 0;
/* 485 */         p[j++] = pos;
/* 486 */         pos += (count + length);
/*     */       } else {
/* 488 */         pos += (count + count(readInt(a, pos + count)) + length);
/*     */       } 
/* 490 */     }  return p;
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 493 */     s.defaultReadObject();
/*     */     
/* 495 */     this.p = rebuildPointerArray();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\CharArrayFrontCodedList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */