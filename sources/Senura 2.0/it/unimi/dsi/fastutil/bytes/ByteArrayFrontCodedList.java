/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public class ByteArrayFrontCodedList
/*     */   extends AbstractObjectList<byte[]>
/*     */   implements Serializable, Cloneable, RandomAccess
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final int n;
/*     */   protected final int ratio;
/*     */   protected final byte[][] array;
/*     */   protected transient long[] p;
/*     */   
/*     */   public ByteArrayFrontCodedList(Iterator<byte[]> arrays, int ratio) {
/* 115 */     if (ratio < 1)
/* 116 */       throw new IllegalArgumentException("Illegal ratio (" + ratio + ")"); 
/* 117 */     byte[][] array = ByteBigArrays.EMPTY_BIG_ARRAY;
/* 118 */     long[] p = LongArrays.EMPTY_ARRAY;
/* 119 */     byte[][] a = new byte[2][];
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
/*     */   public ByteArrayFrontCodedList(Collection<byte[]> c, int ratio) {
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
/*     */   private static int readInt(byte[][] a, long pos) {
/* 180 */     byte b0 = BigArrays.get(a, pos);
/* 181 */     if (b0 >= 0)
/* 182 */       return b0; 
/* 183 */     byte b1 = BigArrays.get(a, pos + 1L);
/* 184 */     if (b1 >= 0)
/* 185 */       return -b0 - 1 << 7 | b1; 
/* 186 */     byte b2 = BigArrays.get(a, pos + 2L);
/* 187 */     if (b2 >= 0)
/* 188 */       return -b0 - 1 << 14 | -b1 - 1 << 7 | b2; 
/* 189 */     byte b3 = BigArrays.get(a, pos + 3L);
/* 190 */     if (b3 >= 0)
/* 191 */       return -b0 - 1 << 21 | -b1 - 1 << 14 | -b2 - 1 << 7 | b3; 
/* 192 */     return -b0 - 1 << 28 | -b1 - 1 << 21 | -b2 - 1 << 14 | -b3 - 1 << 7 | BigArrays.get(a, pos + 4L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int count(int length) {
/* 202 */     if (length < 128)
/* 203 */       return 1; 
/* 204 */     if (length < 16384)
/* 205 */       return 2; 
/* 206 */     if (length < 2097152)
/* 207 */       return 3; 
/* 208 */     if (length < 268435456)
/* 209 */       return 4; 
/* 210 */     return 5;
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
/*     */   private static int writeInt(byte[][] a, int length, long pos) {
/* 224 */     int count = count(length);
/* 225 */     BigArrays.set(a, pos + count - 1L, (byte)(length & 0x7F));
/* 226 */     if (count != 1) {
/* 227 */       int i = count - 1;
/* 228 */       while (i-- != 0) {
/* 229 */         length >>>= 7;
/* 230 */         BigArrays.set(a, pos + i, (byte)(-(length & 0x7F) - 1));
/*     */       } 
/*     */     } 
/* 233 */     return count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int ratio() {
/* 241 */     return this.ratio;
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
/* 255 */     byte[][] array = this.array;
/* 256 */     int delta = index % this.ratio;
/* 257 */     long pos = this.p[index / this.ratio];
/* 258 */     int length = readInt(array, pos);
/* 259 */     if (delta == 0) {
/* 260 */       return length;
/*     */     }
/*     */ 
/*     */     
/* 264 */     pos += (count(length) + length);
/* 265 */     length = readInt(array, pos);
/* 266 */     int common = readInt(array, pos + count(length));
/* 267 */     for (int i = 0; i < delta - 1; i++) {
/* 268 */       pos += (count(length) + count(common) + length);
/* 269 */       length = readInt(array, pos);
/* 270 */       common = readInt(array, pos + count(length));
/*     */     } 
/* 272 */     return length + common;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int arrayLength(int index) {
/* 282 */     ensureRestrictedIndex(index);
/* 283 */     return length(index);
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
/*     */   private int extract(int index, byte[] a, int offset, int length) {
/* 300 */     int delta = index % this.ratio;
/* 301 */     long startPos = this.p[index / this.ratio];
/*     */     
/*     */     long pos;
/* 304 */     int arrayLength = readInt(this.array, pos = startPos), currLen = 0;
/* 305 */     if (delta == 0) {
/* 306 */       pos = this.p[index / this.ratio] + count(arrayLength);
/* 307 */       BigArrays.copyFromBig(this.array, pos, a, offset, Math.min(length, arrayLength));
/* 308 */       return arrayLength;
/*     */     } 
/* 310 */     int common = 0;
/* 311 */     for (int i = 0; i < delta; i++) {
/* 312 */       long prevArrayPos = pos + count(arrayLength) + ((i != 0) ? count(common) : 0L);
/* 313 */       pos = prevArrayPos + arrayLength;
/* 314 */       arrayLength = readInt(this.array, pos);
/* 315 */       common = readInt(this.array, pos + count(arrayLength));
/* 316 */       int actualCommon = Math.min(common, length);
/* 317 */       if (actualCommon <= currLen) {
/* 318 */         currLen = actualCommon;
/*     */       } else {
/* 320 */         BigArrays.copyFromBig(this.array, prevArrayPos, a, currLen + offset, actualCommon - currLen);
/* 321 */         currLen = actualCommon;
/*     */       } 
/*     */     } 
/* 324 */     if (currLen < length)
/* 325 */       BigArrays.copyFromBig(this.array, pos + count(arrayLength) + count(common), a, currLen + offset, 
/* 326 */           Math.min(arrayLength, length - currLen)); 
/* 327 */     return arrayLength + common;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] get(int index) {
/* 336 */     return getArray(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getArray(int index) {
/* 346 */     ensureRestrictedIndex(index);
/* 347 */     int length = length(index);
/* 348 */     byte[] a = new byte[length];
/* 349 */     extract(index, a, 0, length);
/* 350 */     return a;
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
/*     */   public int get(int index, byte[] a, int offset, int length) {
/* 369 */     ensureRestrictedIndex(index);
/* 370 */     ByteArrays.ensureOffsetLength(a, offset, length);
/* 371 */     int arrayLength = extract(index, a, offset, length);
/* 372 */     if (length >= arrayLength)
/* 373 */       return arrayLength; 
/* 374 */     return length - arrayLength;
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
/*     */   public int get(int index, byte[] a) {
/* 389 */     return get(index, a, 0, a.length);
/*     */   }
/*     */   
/*     */   public int size() {
/* 393 */     return this.n;
/*     */   }
/*     */   
/*     */   public ObjectListIterator<byte[]> listIterator(final int start) {
/* 397 */     ensureIndex(start);
/* 398 */     return new ObjectListIterator<byte[]>() {
/* 399 */         byte[] s = ByteArrays.EMPTY_ARRAY;
/* 400 */         int i = 0;
/* 401 */         long pos = 0L;
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
/* 419 */           return (this.i < ByteArrayFrontCodedList.this.n);
/*     */         }
/*     */         
/*     */         public boolean hasPrevious() {
/* 423 */           return (this.i > 0);
/*     */         }
/*     */         
/*     */         public int previousIndex() {
/* 427 */           return this.i - 1;
/*     */         }
/*     */         
/*     */         public int nextIndex() {
/* 431 */           return this.i;
/*     */         }
/*     */         
/*     */         public byte[] next() {
/*     */           int length;
/* 436 */           if (!hasNext())
/* 437 */             throw new NoSuchElementException(); 
/* 438 */           if (this.i % ByteArrayFrontCodedList.this.ratio == 0) {
/* 439 */             this.pos = ByteArrayFrontCodedList.this.p[this.i / ByteArrayFrontCodedList.this.ratio];
/* 440 */             length = ByteArrayFrontCodedList.readInt(ByteArrayFrontCodedList.this.array, this.pos);
/* 441 */             this.s = ByteArrays.ensureCapacity(this.s, length, 0);
/* 442 */             BigArrays.copyFromBig(ByteArrayFrontCodedList.this.array, this.pos + ByteArrayFrontCodedList.count(length), this.s, 0, length);
/* 443 */             this.pos += (length + ByteArrayFrontCodedList.count(length));
/* 444 */             this.inSync = true;
/*     */           }
/* 446 */           else if (this.inSync) {
/* 447 */             length = ByteArrayFrontCodedList.readInt(ByteArrayFrontCodedList.this.array, this.pos);
/* 448 */             int common = ByteArrayFrontCodedList.readInt(ByteArrayFrontCodedList.this.array, this.pos + ByteArrayFrontCodedList.count(length));
/* 449 */             this.s = ByteArrays.ensureCapacity(this.s, length + common, common);
/* 450 */             BigArrays.copyFromBig(ByteArrayFrontCodedList.this.array, this.pos + ByteArrayFrontCodedList.count(length) + ByteArrayFrontCodedList.count(common), this.s, common, length);
/* 451 */             this.pos += (ByteArrayFrontCodedList.count(length) + ByteArrayFrontCodedList.count(common) + length);
/* 452 */             length += common;
/*     */           } else {
/* 454 */             this.s = ByteArrays.ensureCapacity(this.s, length = ByteArrayFrontCodedList.this.length(this.i), 0);
/* 455 */             ByteArrayFrontCodedList.this.extract(this.i, this.s, 0, length);
/*     */           } 
/*     */           
/* 458 */           this.i++;
/* 459 */           return ByteArrays.copy(this.s, 0, length);
/*     */         }
/*     */         
/*     */         public byte[] previous() {
/* 463 */           if (!hasPrevious())
/* 464 */             throw new NoSuchElementException(); 
/* 465 */           this.inSync = false;
/* 466 */           return ByteArrayFrontCodedList.this.getArray(--this.i);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteArrayFrontCodedList clone() {
/* 477 */     return this;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 481 */     StringBuffer s = new StringBuffer();
/* 482 */     s.append("[");
/* 483 */     for (int i = 0; i < this.n; i++) {
/* 484 */       if (i != 0)
/* 485 */         s.append(", "); 
/* 486 */       s.append(ByteArrayList.wrap(getArray(i)).toString());
/*     */     } 
/* 488 */     s.append("]");
/* 489 */     return s.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected long[] rebuildPointerArray() {
/* 498 */     long[] p = new long[(this.n + this.ratio - 1) / this.ratio];
/* 499 */     byte[][] a = this.array;
/*     */     
/* 501 */     long pos = 0L;
/* 502 */     for (int i = 0, j = 0, skip = this.ratio - 1; i < this.n; i++) {
/* 503 */       int length = readInt(a, pos);
/* 504 */       int count = count(length);
/* 505 */       if (++skip == this.ratio) {
/* 506 */         skip = 0;
/* 507 */         p[j++] = pos;
/* 508 */         pos += (count + length);
/*     */       } else {
/* 510 */         pos += (count + count(readInt(a, pos + count)) + length);
/*     */       } 
/* 512 */     }  return p;
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 515 */     s.defaultReadObject();
/*     */     
/* 517 */     this.p = rebuildPointerArray();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\ByteArrayFrontCodedList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */