/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.BigArrays;
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
/*     */ 
/*     */ public class LongArrayFrontCodedList
/*     */   extends AbstractObjectList<long[]>
/*     */   implements Serializable, Cloneable, RandomAccess
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final int n;
/*     */   protected final int ratio;
/*     */   protected final long[][] array;
/*     */   protected transient long[] p;
/*     */   
/*     */   public LongArrayFrontCodedList(Iterator<long[]> arrays, int ratio) {
/* 115 */     if (ratio < 1)
/* 116 */       throw new IllegalArgumentException("Illegal ratio (" + ratio + ")"); 
/* 117 */     long[][] array = LongBigArrays.EMPTY_BIG_ARRAY;
/* 118 */     long[] p = LongArrays.EMPTY_ARRAY;
/* 119 */     long[][] a = new long[2][];
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
/*     */   public LongArrayFrontCodedList(Collection<long[]> c, int ratio) {
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
/*     */   private static int readInt(long[][] a, long pos) {
/* 180 */     return (int)BigArrays.get(a, pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int count(int length) {
/* 190 */     return 1;
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
/*     */   private static int writeInt(long[][] a, int length, long pos) {
/* 204 */     BigArrays.set(a, pos, length);
/* 205 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int ratio() {
/* 213 */     return this.ratio;
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
/* 227 */     long[][] array = this.array;
/* 228 */     int delta = index % this.ratio;
/* 229 */     long pos = this.p[index / this.ratio];
/* 230 */     int length = readInt(array, pos);
/* 231 */     if (delta == 0) {
/* 232 */       return length;
/*     */     }
/*     */ 
/*     */     
/* 236 */     pos += (count(length) + length);
/* 237 */     length = readInt(array, pos);
/* 238 */     int common = readInt(array, pos + count(length));
/* 239 */     for (int i = 0; i < delta - 1; i++) {
/* 240 */       pos += (count(length) + count(common) + length);
/* 241 */       length = readInt(array, pos);
/* 242 */       common = readInt(array, pos + count(length));
/*     */     } 
/* 244 */     return length + common;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int arrayLength(int index) {
/* 254 */     ensureRestrictedIndex(index);
/* 255 */     return length(index);
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
/*     */   private int extract(int index, long[] a, int offset, int length) {
/* 272 */     int delta = index % this.ratio;
/* 273 */     long startPos = this.p[index / this.ratio];
/*     */     
/*     */     long pos;
/* 276 */     int arrayLength = readInt(this.array, pos = startPos), currLen = 0;
/* 277 */     if (delta == 0) {
/* 278 */       pos = this.p[index / this.ratio] + count(arrayLength);
/* 279 */       BigArrays.copyFromBig(this.array, pos, a, offset, Math.min(length, arrayLength));
/* 280 */       return arrayLength;
/*     */     } 
/* 282 */     int common = 0;
/* 283 */     for (int i = 0; i < delta; i++) {
/* 284 */       long prevArrayPos = pos + count(arrayLength) + ((i != 0) ? count(common) : 0L);
/* 285 */       pos = prevArrayPos + arrayLength;
/* 286 */       arrayLength = readInt(this.array, pos);
/* 287 */       common = readInt(this.array, pos + count(arrayLength));
/* 288 */       int actualCommon = Math.min(common, length);
/* 289 */       if (actualCommon <= currLen) {
/* 290 */         currLen = actualCommon;
/*     */       } else {
/* 292 */         BigArrays.copyFromBig(this.array, prevArrayPos, a, currLen + offset, actualCommon - currLen);
/* 293 */         currLen = actualCommon;
/*     */       } 
/*     */     } 
/* 296 */     if (currLen < length)
/* 297 */       BigArrays.copyFromBig(this.array, pos + count(arrayLength) + count(common), a, currLen + offset, 
/* 298 */           Math.min(arrayLength, length - currLen)); 
/* 299 */     return arrayLength + common;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long[] get(int index) {
/* 308 */     return getArray(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long[] getArray(int index) {
/* 318 */     ensureRestrictedIndex(index);
/* 319 */     int length = length(index);
/* 320 */     long[] a = new long[length];
/* 321 */     extract(index, a, 0, length);
/* 322 */     return a;
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
/*     */   public int get(int index, long[] a, int offset, int length) {
/* 341 */     ensureRestrictedIndex(index);
/* 342 */     LongArrays.ensureOffsetLength(a, offset, length);
/* 343 */     int arrayLength = extract(index, a, offset, length);
/* 344 */     if (length >= arrayLength)
/* 345 */       return arrayLength; 
/* 346 */     return length - arrayLength;
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
/*     */   public int get(int index, long[] a) {
/* 361 */     return get(index, a, 0, a.length);
/*     */   }
/*     */   
/*     */   public int size() {
/* 365 */     return this.n;
/*     */   }
/*     */   
/*     */   public ObjectListIterator<long[]> listIterator(final int start) {
/* 369 */     ensureIndex(start);
/* 370 */     return new ObjectListIterator<long[]>() {
/* 371 */         long[] s = LongArrays.EMPTY_ARRAY;
/* 372 */         int i = 0;
/* 373 */         long pos = 0L;
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
/* 391 */           return (this.i < LongArrayFrontCodedList.this.n);
/*     */         }
/*     */         
/*     */         public boolean hasPrevious() {
/* 395 */           return (this.i > 0);
/*     */         }
/*     */         
/*     */         public int previousIndex() {
/* 399 */           return this.i - 1;
/*     */         }
/*     */         
/*     */         public int nextIndex() {
/* 403 */           return this.i;
/*     */         }
/*     */         
/*     */         public long[] next() {
/*     */           int length;
/* 408 */           if (!hasNext())
/* 409 */             throw new NoSuchElementException(); 
/* 410 */           if (this.i % LongArrayFrontCodedList.this.ratio == 0) {
/* 411 */             this.pos = LongArrayFrontCodedList.this.p[this.i / LongArrayFrontCodedList.this.ratio];
/* 412 */             length = LongArrayFrontCodedList.readInt(LongArrayFrontCodedList.this.array, this.pos);
/* 413 */             this.s = LongArrays.ensureCapacity(this.s, length, 0);
/* 414 */             BigArrays.copyFromBig(LongArrayFrontCodedList.this.array, this.pos + LongArrayFrontCodedList.count(length), this.s, 0, length);
/* 415 */             this.pos += (length + LongArrayFrontCodedList.count(length));
/* 416 */             this.inSync = true;
/*     */           }
/* 418 */           else if (this.inSync) {
/* 419 */             length = LongArrayFrontCodedList.readInt(LongArrayFrontCodedList.this.array, this.pos);
/* 420 */             int common = LongArrayFrontCodedList.readInt(LongArrayFrontCodedList.this.array, this.pos + LongArrayFrontCodedList.count(length));
/* 421 */             this.s = LongArrays.ensureCapacity(this.s, length + common, common);
/* 422 */             BigArrays.copyFromBig(LongArrayFrontCodedList.this.array, this.pos + LongArrayFrontCodedList.count(length) + LongArrayFrontCodedList.count(common), this.s, common, length);
/* 423 */             this.pos += (LongArrayFrontCodedList.count(length) + LongArrayFrontCodedList.count(common) + length);
/* 424 */             length += common;
/*     */           } else {
/* 426 */             this.s = LongArrays.ensureCapacity(this.s, length = LongArrayFrontCodedList.this.length(this.i), 0);
/* 427 */             LongArrayFrontCodedList.this.extract(this.i, this.s, 0, length);
/*     */           } 
/*     */           
/* 430 */           this.i++;
/* 431 */           return LongArrays.copy(this.s, 0, length);
/*     */         }
/*     */         
/*     */         public long[] previous() {
/* 435 */           if (!hasPrevious())
/* 436 */             throw new NoSuchElementException(); 
/* 437 */           this.inSync = false;
/* 438 */           return LongArrayFrontCodedList.this.getArray(--this.i);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongArrayFrontCodedList clone() {
/* 449 */     return this;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 453 */     StringBuffer s = new StringBuffer();
/* 454 */     s.append("[");
/* 455 */     for (int i = 0; i < this.n; i++) {
/* 456 */       if (i != 0)
/* 457 */         s.append(", "); 
/* 458 */       s.append(LongArrayList.wrap(getArray(i)).toString());
/*     */     } 
/* 460 */     s.append("]");
/* 461 */     return s.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected long[] rebuildPointerArray() {
/* 470 */     long[] p = new long[(this.n + this.ratio - 1) / this.ratio];
/* 471 */     long[][] a = this.array;
/*     */     
/* 473 */     long pos = 0L;
/* 474 */     for (int i = 0, j = 0, skip = this.ratio - 1; i < this.n; i++) {
/* 475 */       int length = readInt(a, pos);
/* 476 */       int count = count(length);
/* 477 */       if (++skip == this.ratio) {
/* 478 */         skip = 0;
/* 479 */         p[j++] = pos;
/* 480 */         pos += (count + length);
/*     */       } else {
/* 482 */         pos += (count + count(readInt(a, pos + count)) + length);
/*     */       } 
/* 484 */     }  return p;
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 487 */     s.defaultReadObject();
/*     */     
/* 489 */     this.p = rebuildPointerArray();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\LongArrayFrontCodedList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */