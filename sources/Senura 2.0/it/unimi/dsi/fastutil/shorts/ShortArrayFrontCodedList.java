/*     */ package it.unimi.dsi.fastutil.shorts;
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
/*     */ public class ShortArrayFrontCodedList
/*     */   extends AbstractObjectList<short[]>
/*     */   implements Serializable, Cloneable, RandomAccess
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final int n;
/*     */   protected final int ratio;
/*     */   protected final short[][] array;
/*     */   protected transient long[] p;
/*     */   
/*     */   public ShortArrayFrontCodedList(Iterator<short[]> arrays, int ratio) {
/* 115 */     if (ratio < 1)
/* 116 */       throw new IllegalArgumentException("Illegal ratio (" + ratio + ")"); 
/* 117 */     short[][] array = ShortBigArrays.EMPTY_BIG_ARRAY;
/* 118 */     long[] p = LongArrays.EMPTY_ARRAY;
/* 119 */     short[][] a = new short[2][];
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
/*     */   public ShortArrayFrontCodedList(Collection<short[]> c, int ratio) {
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
/*     */   private static int readInt(short[][] a, long pos) {
/* 180 */     short s0 = BigArrays.get(a, pos);
/* 181 */     return (s0 >= 0) ? s0 : (s0 << 16 | BigArrays.get(a, pos + 1L) & 0xFFFF);
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
/*     */   private static int writeInt(short[][] a, int length, long pos) {
/* 205 */     if (length < 32768) {
/* 206 */       BigArrays.set(a, pos, (short)length);
/* 207 */       return 1;
/*     */     } 
/* 209 */     BigArrays.set(a, pos++, (short)(-(length >>> 16) - 1));
/* 210 */     BigArrays.set(a, pos, (short)(length & 0xFFFF));
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
/* 233 */     short[][] array = this.array;
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
/*     */   private int extract(int index, short[] a, int offset, int length) {
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
/*     */   public short[] get(int index) {
/* 314 */     return getArray(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short[] getArray(int index) {
/* 324 */     ensureRestrictedIndex(index);
/* 325 */     int length = length(index);
/* 326 */     short[] a = new short[length];
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
/*     */   public int get(int index, short[] a, int offset, int length) {
/* 347 */     ensureRestrictedIndex(index);
/* 348 */     ShortArrays.ensureOffsetLength(a, offset, length);
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
/*     */   public int get(int index, short[] a) {
/* 367 */     return get(index, a, 0, a.length);
/*     */   }
/*     */   
/*     */   public int size() {
/* 371 */     return this.n;
/*     */   }
/*     */   
/*     */   public ObjectListIterator<short[]> listIterator(final int start) {
/* 375 */     ensureIndex(start);
/* 376 */     return new ObjectListIterator<short[]>() {
/* 377 */         short[] s = ShortArrays.EMPTY_ARRAY;
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
/* 397 */           return (this.i < ShortArrayFrontCodedList.this.n);
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
/*     */         public short[] next() {
/*     */           int length;
/* 414 */           if (!hasNext())
/* 415 */             throw new NoSuchElementException(); 
/* 416 */           if (this.i % ShortArrayFrontCodedList.this.ratio == 0) {
/* 417 */             this.pos = ShortArrayFrontCodedList.this.p[this.i / ShortArrayFrontCodedList.this.ratio];
/* 418 */             length = ShortArrayFrontCodedList.readInt(ShortArrayFrontCodedList.this.array, this.pos);
/* 419 */             this.s = ShortArrays.ensureCapacity(this.s, length, 0);
/* 420 */             BigArrays.copyFromBig(ShortArrayFrontCodedList.this.array, this.pos + ShortArrayFrontCodedList.count(length), this.s, 0, length);
/* 421 */             this.pos += (length + ShortArrayFrontCodedList.count(length));
/* 422 */             this.inSync = true;
/*     */           }
/* 424 */           else if (this.inSync) {
/* 425 */             length = ShortArrayFrontCodedList.readInt(ShortArrayFrontCodedList.this.array, this.pos);
/* 426 */             int common = ShortArrayFrontCodedList.readInt(ShortArrayFrontCodedList.this.array, this.pos + ShortArrayFrontCodedList.count(length));
/* 427 */             this.s = ShortArrays.ensureCapacity(this.s, length + common, common);
/* 428 */             BigArrays.copyFromBig(ShortArrayFrontCodedList.this.array, this.pos + ShortArrayFrontCodedList.count(length) + ShortArrayFrontCodedList.count(common), this.s, common, length);
/* 429 */             this.pos += (ShortArrayFrontCodedList.count(length) + ShortArrayFrontCodedList.count(common) + length);
/* 430 */             length += common;
/*     */           } else {
/* 432 */             this.s = ShortArrays.ensureCapacity(this.s, length = ShortArrayFrontCodedList.this.length(this.i), 0);
/* 433 */             ShortArrayFrontCodedList.this.extract(this.i, this.s, 0, length);
/*     */           } 
/*     */           
/* 436 */           this.i++;
/* 437 */           return ShortArrays.copy(this.s, 0, length);
/*     */         }
/*     */         
/*     */         public short[] previous() {
/* 441 */           if (!hasPrevious())
/* 442 */             throw new NoSuchElementException(); 
/* 443 */           this.inSync = false;
/* 444 */           return ShortArrayFrontCodedList.this.getArray(--this.i);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortArrayFrontCodedList clone() {
/* 455 */     return this;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 459 */     StringBuffer s = new StringBuffer();
/* 460 */     s.append("[");
/* 461 */     for (int i = 0; i < this.n; i++) {
/* 462 */       if (i != 0)
/* 463 */         s.append(", "); 
/* 464 */       s.append(ShortArrayList.wrap(getArray(i)).toString());
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
/* 477 */     short[][] a = this.array;
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


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\ShortArrayFrontCodedList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */