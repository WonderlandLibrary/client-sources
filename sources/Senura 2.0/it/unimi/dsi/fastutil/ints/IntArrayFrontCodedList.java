/*     */ package it.unimi.dsi.fastutil.ints;
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
/*     */ public class IntArrayFrontCodedList
/*     */   extends AbstractObjectList<int[]>
/*     */   implements Serializable, Cloneable, RandomAccess
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final int n;
/*     */   protected final int ratio;
/*     */   protected final int[][] array;
/*     */   protected transient long[] p;
/*     */   
/*     */   public IntArrayFrontCodedList(Iterator<int[]> arrays, int ratio) {
/* 111 */     if (ratio < 1)
/* 112 */       throw new IllegalArgumentException("Illegal ratio (" + ratio + ")"); 
/* 113 */     int[][] array = IntBigArrays.EMPTY_BIG_ARRAY;
/* 114 */     long[] p = LongArrays.EMPTY_ARRAY;
/* 115 */     int[][] a = new int[2][];
/* 116 */     long curSize = 0L;
/* 117 */     int n = 0, b = 0;
/* 118 */     while (arrays.hasNext()) {
/* 119 */       a[b] = arrays.next();
/* 120 */       int length = (a[b]).length;
/* 121 */       if (n % ratio == 0) {
/* 122 */         p = LongArrays.grow(p, n / ratio + 1);
/* 123 */         p[n / ratio] = curSize;
/* 124 */         array = BigArrays.grow(array, curSize + count(length) + length, curSize);
/* 125 */         curSize += writeInt(array, length, curSize);
/* 126 */         BigArrays.copyToBig(a[b], 0, array, curSize, length);
/* 127 */         curSize += length;
/*     */       } else {
/* 129 */         int minLength = (a[1 - b]).length;
/* 130 */         if (length < minLength)
/* 131 */           minLength = length;  int common;
/* 132 */         for (common = 0; common < minLength && 
/* 133 */           a[0][common] == a[1][common]; common++);
/*     */         
/* 135 */         length -= common;
/* 136 */         array = BigArrays.grow(array, curSize + count(length) + count(common) + length, curSize);
/* 137 */         curSize += writeInt(array, length, curSize);
/* 138 */         curSize += writeInt(array, common, curSize);
/* 139 */         BigArrays.copyToBig(a[b], common, array, curSize, length);
/* 140 */         curSize += length;
/*     */       } 
/* 142 */       b = 1 - b;
/* 143 */       n++;
/*     */     } 
/* 145 */     this.n = n;
/* 146 */     this.ratio = ratio;
/* 147 */     this.array = BigArrays.trim(array, curSize);
/* 148 */     this.p = LongArrays.trim(p, (n + ratio - 1) / ratio);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntArrayFrontCodedList(Collection<int[]> c, int ratio) {
/* 159 */     this((Iterator)c.iterator(), ratio);
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
/*     */   private static int readInt(int[][] a, long pos) {
/* 176 */     return BigArrays.get(a, pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int count(int length) {
/* 186 */     return 1;
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
/*     */   private static int writeInt(int[][] a, int length, long pos) {
/* 200 */     BigArrays.set(a, pos, length);
/* 201 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int ratio() {
/* 209 */     return this.ratio;
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
/* 223 */     int[][] array = this.array;
/* 224 */     int delta = index % this.ratio;
/* 225 */     long pos = this.p[index / this.ratio];
/* 226 */     int length = readInt(array, pos);
/* 227 */     if (delta == 0) {
/* 228 */       return length;
/*     */     }
/*     */ 
/*     */     
/* 232 */     pos += (count(length) + length);
/* 233 */     length = readInt(array, pos);
/* 234 */     int common = readInt(array, pos + count(length));
/* 235 */     for (int i = 0; i < delta - 1; i++) {
/* 236 */       pos += (count(length) + count(common) + length);
/* 237 */       length = readInt(array, pos);
/* 238 */       common = readInt(array, pos + count(length));
/*     */     } 
/* 240 */     return length + common;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int arrayLength(int index) {
/* 250 */     ensureRestrictedIndex(index);
/* 251 */     return length(index);
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
/*     */   private int extract(int index, int[] a, int offset, int length) {
/* 268 */     int delta = index % this.ratio;
/* 269 */     long startPos = this.p[index / this.ratio];
/*     */     
/*     */     long pos;
/* 272 */     int arrayLength = readInt(this.array, pos = startPos), currLen = 0;
/* 273 */     if (delta == 0) {
/* 274 */       pos = this.p[index / this.ratio] + count(arrayLength);
/* 275 */       BigArrays.copyFromBig(this.array, pos, a, offset, Math.min(length, arrayLength));
/* 276 */       return arrayLength;
/*     */     } 
/* 278 */     int common = 0;
/* 279 */     for (int i = 0; i < delta; i++) {
/* 280 */       long prevArrayPos = pos + count(arrayLength) + ((i != 0) ? count(common) : 0L);
/* 281 */       pos = prevArrayPos + arrayLength;
/* 282 */       arrayLength = readInt(this.array, pos);
/* 283 */       common = readInt(this.array, pos + count(arrayLength));
/* 284 */       int actualCommon = Math.min(common, length);
/* 285 */       if (actualCommon <= currLen) {
/* 286 */         currLen = actualCommon;
/*     */       } else {
/* 288 */         BigArrays.copyFromBig(this.array, prevArrayPos, a, currLen + offset, actualCommon - currLen);
/* 289 */         currLen = actualCommon;
/*     */       } 
/*     */     } 
/* 292 */     if (currLen < length)
/* 293 */       BigArrays.copyFromBig(this.array, pos + count(arrayLength) + count(common), a, currLen + offset, 
/* 294 */           Math.min(arrayLength, length - currLen)); 
/* 295 */     return arrayLength + common;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] get(int index) {
/* 304 */     return getArray(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getArray(int index) {
/* 314 */     ensureRestrictedIndex(index);
/* 315 */     int length = length(index);
/* 316 */     int[] a = new int[length];
/* 317 */     extract(index, a, 0, length);
/* 318 */     return a;
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
/*     */   public int get(int index, int[] a, int offset, int length) {
/* 337 */     ensureRestrictedIndex(index);
/* 338 */     IntArrays.ensureOffsetLength(a, offset, length);
/* 339 */     int arrayLength = extract(index, a, offset, length);
/* 340 */     if (length >= arrayLength)
/* 341 */       return arrayLength; 
/* 342 */     return length - arrayLength;
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
/*     */   public int get(int index, int[] a) {
/* 357 */     return get(index, a, 0, a.length);
/*     */   }
/*     */   
/*     */   public int size() {
/* 361 */     return this.n;
/*     */   }
/*     */   
/*     */   public ObjectListIterator<int[]> listIterator(final int start) {
/* 365 */     ensureIndex(start);
/* 366 */     return new ObjectListIterator<int[]>() {
/* 367 */         int[] s = IntArrays.EMPTY_ARRAY;
/* 368 */         int i = 0;
/* 369 */         long pos = 0L;
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
/* 387 */           return (this.i < IntArrayFrontCodedList.this.n);
/*     */         }
/*     */         
/*     */         public boolean hasPrevious() {
/* 391 */           return (this.i > 0);
/*     */         }
/*     */         
/*     */         public int previousIndex() {
/* 395 */           return this.i - 1;
/*     */         }
/*     */         
/*     */         public int nextIndex() {
/* 399 */           return this.i;
/*     */         }
/*     */         
/*     */         public int[] next() {
/*     */           int length;
/* 404 */           if (!hasNext())
/* 405 */             throw new NoSuchElementException(); 
/* 406 */           if (this.i % IntArrayFrontCodedList.this.ratio == 0) {
/* 407 */             this.pos = IntArrayFrontCodedList.this.p[this.i / IntArrayFrontCodedList.this.ratio];
/* 408 */             length = IntArrayFrontCodedList.readInt(IntArrayFrontCodedList.this.array, this.pos);
/* 409 */             this.s = IntArrays.ensureCapacity(this.s, length, 0);
/* 410 */             BigArrays.copyFromBig(IntArrayFrontCodedList.this.array, this.pos + IntArrayFrontCodedList.count(length), this.s, 0, length);
/* 411 */             this.pos += (length + IntArrayFrontCodedList.count(length));
/* 412 */             this.inSync = true;
/*     */           }
/* 414 */           else if (this.inSync) {
/* 415 */             length = IntArrayFrontCodedList.readInt(IntArrayFrontCodedList.this.array, this.pos);
/* 416 */             int common = IntArrayFrontCodedList.readInt(IntArrayFrontCodedList.this.array, this.pos + IntArrayFrontCodedList.count(length));
/* 417 */             this.s = IntArrays.ensureCapacity(this.s, length + common, common);
/* 418 */             BigArrays.copyFromBig(IntArrayFrontCodedList.this.array, this.pos + IntArrayFrontCodedList.count(length) + IntArrayFrontCodedList.count(common), this.s, common, length);
/* 419 */             this.pos += (IntArrayFrontCodedList.count(length) + IntArrayFrontCodedList.count(common) + length);
/* 420 */             length += common;
/*     */           } else {
/* 422 */             this.s = IntArrays.ensureCapacity(this.s, length = IntArrayFrontCodedList.this.length(this.i), 0);
/* 423 */             IntArrayFrontCodedList.this.extract(this.i, this.s, 0, length);
/*     */           } 
/*     */           
/* 426 */           this.i++;
/* 427 */           return IntArrays.copy(this.s, 0, length);
/*     */         }
/*     */         
/*     */         public int[] previous() {
/* 431 */           if (!hasPrevious())
/* 432 */             throw new NoSuchElementException(); 
/* 433 */           this.inSync = false;
/* 434 */           return IntArrayFrontCodedList.this.getArray(--this.i);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntArrayFrontCodedList clone() {
/* 445 */     return this;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 449 */     StringBuffer s = new StringBuffer();
/* 450 */     s.append("[");
/* 451 */     for (int i = 0; i < this.n; i++) {
/* 452 */       if (i != 0)
/* 453 */         s.append(", "); 
/* 454 */       s.append(IntArrayList.wrap(getArray(i)).toString());
/*     */     } 
/* 456 */     s.append("]");
/* 457 */     return s.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected long[] rebuildPointerArray() {
/* 466 */     long[] p = new long[(this.n + this.ratio - 1) / this.ratio];
/* 467 */     int[][] a = this.array;
/*     */     
/* 469 */     long pos = 0L;
/* 470 */     for (int i = 0, j = 0, skip = this.ratio - 1; i < this.n; i++) {
/* 471 */       int length = readInt(a, pos);
/* 472 */       int count = count(length);
/* 473 */       if (++skip == this.ratio) {
/* 474 */         skip = 0;
/* 475 */         p[j++] = pos;
/* 476 */         pos += (count + length);
/*     */       } else {
/* 478 */         pos += (count + count(readInt(a, pos + count)) + length);
/*     */       } 
/* 480 */     }  return p;
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 483 */     s.defaultReadObject();
/*     */     
/* 485 */     this.p = rebuildPointerArray();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\IntArrayFrontCodedList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */