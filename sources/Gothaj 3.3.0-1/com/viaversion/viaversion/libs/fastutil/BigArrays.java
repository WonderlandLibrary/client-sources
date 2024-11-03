package com.viaversion.viaversion.libs.fastutil;

import com.viaversion.viaversion.libs.fastutil.booleans.BooleanArrays;
import com.viaversion.viaversion.libs.fastutil.booleans.BooleanBigArrays;
import com.viaversion.viaversion.libs.fastutil.bytes.ByteArrays;
import com.viaversion.viaversion.libs.fastutil.bytes.ByteBigArrays;
import com.viaversion.viaversion.libs.fastutil.chars.CharArrays;
import com.viaversion.viaversion.libs.fastutil.chars.CharBigArrays;
import com.viaversion.viaversion.libs.fastutil.doubles.DoubleArrays;
import com.viaversion.viaversion.libs.fastutil.doubles.DoubleBigArrays;
import com.viaversion.viaversion.libs.fastutil.floats.FloatArrays;
import com.viaversion.viaversion.libs.fastutil.floats.FloatBigArrays;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrays;
import com.viaversion.viaversion.libs.fastutil.ints.IntBigArrays;
import com.viaversion.viaversion.libs.fastutil.longs.LongArrays;
import com.viaversion.viaversion.libs.fastutil.longs.LongBigArrays;
import com.viaversion.viaversion.libs.fastutil.longs.LongComparator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrays;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectBigArrays;
import com.viaversion.viaversion.libs.fastutil.shorts.ShortArrays;
import com.viaversion.viaversion.libs.fastutil.shorts.ShortBigArrays;
import java.lang.reflect.Array;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLongArray;

public class BigArrays {
   public static final int SEGMENT_SHIFT = 27;
   public static final int SEGMENT_SIZE = 134217728;
   public static final int SEGMENT_MASK = 134217727;
   private static final int SMALL = 7;
   private static final int MEDIUM = 40;

   protected BigArrays() {
   }

   public static int segment(long index) {
      return (int)(index >>> 27);
   }

   public static int displacement(long index) {
      return (int)(index & 134217727L);
   }

   public static long start(int segment) {
      return (long)segment << 27;
   }

   public static long nearestSegmentStart(long index, long min, long max) {
      long lower = start(segment(index));
      long upper = start(segment(index) + 1);
      if (upper >= max) {
         return lower < min ? index : lower;
      } else if (lower < min) {
         return upper;
      } else {
         long mid = lower + (upper - lower >> 1);
         return index <= mid ? lower : upper;
      }
   }

   public static long index(int segment, int displacement) {
      return start(segment) + (long)displacement;
   }

   public static void ensureFromTo(long bigArrayLength, long from, long to) {
      assert bigArrayLength >= 0L;

      if (from < 0L) {
         throw new ArrayIndexOutOfBoundsException("Start index (" + from + ") is negative");
      } else if (from > to) {
         throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
      } else if (to > bigArrayLength) {
         throw new ArrayIndexOutOfBoundsException("End index (" + to + ") is greater than big-array length (" + bigArrayLength + ")");
      }
   }

   public static void ensureOffsetLength(long bigArrayLength, long offset, long length) {
      assert bigArrayLength >= 0L;

      if (offset < 0L) {
         throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative");
      } else if (length < 0L) {
         throw new IllegalArgumentException("Length (" + length + ") is negative");
      } else if (length > bigArrayLength - offset) {
         throw new ArrayIndexOutOfBoundsException(
            "Last index (" + Long.toUnsignedString(offset + length) + ") is greater than big-array length (" + bigArrayLength + ")"
         );
      }
   }

   public static void ensureLength(long bigArrayLength) {
      if (bigArrayLength < 0L) {
         throw new IllegalArgumentException("Negative big-array size: " + bigArrayLength);
      } else if (bigArrayLength >= 288230376017494016L) {
         throw new IllegalArgumentException("Big-array size too big: " + bigArrayLength);
      }
   }

   private static void inPlaceMerge(long from, long mid, long to, LongComparator comp, BigSwapper swapper) {
      if (from < mid && mid < to) {
         if (to - from == 2L) {
            if (comp.compare(mid, from) < 0) {
               swapper.swap(from, mid);
            }
         } else {
            long firstCut;
            long secondCut;
            if (mid - from > to - mid) {
               firstCut = from + (mid - from) / 2L;
               secondCut = lowerBound(mid, to, firstCut, comp);
            } else {
               secondCut = mid + (to - mid) / 2L;
               firstCut = upperBound(from, mid, secondCut, comp);
            }

            if (mid != firstCut && mid != secondCut) {
               long first1 = firstCut;
               long last1 = mid;

               while (first1 < --last1) {
                  swapper.swap(first1++, last1);
               }

               first1 = mid;
               last1 = secondCut;

               while (first1 < --last1) {
                  swapper.swap(first1++, last1);
               }

               first1 = firstCut;
               last1 = secondCut;

               while (first1 < --last1) {
                  swapper.swap(first1++, last1);
               }
            }

            mid = firstCut + (secondCut - mid);
            inPlaceMerge(from, firstCut, mid, comp, swapper);
            inPlaceMerge(mid, secondCut, to, comp, swapper);
         }
      }
   }

   private static long lowerBound(long mid, long to, long firstCut, LongComparator comp) {
      long len = to - mid;

      while (len > 0L) {
         long half = len / 2L;
         long middle = mid + half;
         if (comp.compare(middle, firstCut) < 0) {
            mid = middle + 1L;
            len -= half + 1L;
         } else {
            len = half;
         }
      }

      return mid;
   }

   private static long med3(long a, long b, long c, LongComparator comp) {
      int ab = comp.compare(a, b);
      int ac = comp.compare(a, c);
      int bc = comp.compare(b, c);
      return ab < 0 ? (bc < 0 ? b : (ac < 0 ? c : a)) : (bc > 0 ? b : (ac > 0 ? c : a));
   }

   public static void mergeSort(long from, long to, LongComparator comp, BigSwapper swapper) {
      long length = to - from;
      if (length >= 7L) {
         long mid = from + to >>> 1;
         mergeSort(from, mid, comp, swapper);
         mergeSort(mid, to, comp, swapper);
         if (comp.compare(mid - 1L, mid) > 0) {
            inPlaceMerge(from, mid, to, comp, swapper);
         }
      } else {
         for (long i = from; i < to; i++) {
            for (long j = i; j > from && comp.compare(j - 1L, j) > 0; j--) {
               swapper.swap(j, j - 1L);
            }
         }
      }
   }

   public static void quickSort(long from, long to, LongComparator comp, BigSwapper swapper) {
      long len = to - from;
      if (len < 7L) {
         for (long i = from; i < to; i++) {
            for (long j = i; j > from && comp.compare(j - 1L, j) > 0; j--) {
               swapper.swap(j, j - 1L);
            }
         }
      } else {
         long m = from + len / 2L;
         if (len > 7L) {
            long l = from;
            long n = to - 1L;
            if (len > 40L) {
               long s = len / 8L;
               l = med3(from, from + s, from + 2L * s, comp);
               m = med3(m - s, m, m + s, comp);
               n = med3(n - 2L * s, n - s, n, comp);
            }

            m = med3(l, m, n, comp);
         }

         long a = from;
         long b = from;
         long c = to - 1L;
         long d = c;

         while (true) {
            int comparison;
            for (; b > c || (comparison = comp.compare(b, m)) > 0; swapper.swap(b++, c--)) {
               for (; c >= b && (comparison = comp.compare(c, m)) >= 0; c--) {
                  if (comparison == 0) {
                     if (c == m) {
                        m = d;
                     } else if (d == m) {
                        m = c;
                     }

                     swapper.swap(c, d--);
                  }
               }

               if (b > c) {
                  long n = from + len;
                  long s = Math.min(a - from, b - a);
                  vecSwap(swapper, from, b - s, s);
                  long var29 = Math.min(d - c, n - d - 1L);
                  vecSwap(swapper, b, n - var29, var29);
                  long var30;
                  if ((var30 = b - a) > 1L) {
                     quickSort(from, from + var30, comp, swapper);
                  }

                  long var31;
                  if ((var31 = d - c) > 1L) {
                     quickSort(n - var31, n, comp, swapper);
                  }

                  return;
               }

               if (b == m) {
                  m = d;
               } else if (c == m) {
                  m = c;
               }
            }

            if (comparison == 0) {
               if (a == m) {
                  m = b;
               } else if (b == m) {
                  m = a;
               }

               swapper.swap(a++, b);
            }

            b++;
         }
      }
   }

   private static long upperBound(long from, long mid, long secondCut, LongComparator comp) {
      long len = mid - from;

      while (len > 0L) {
         long half = len / 2L;
         long middle = from + half;
         if (comp.compare(secondCut, middle) < 0) {
            len = half;
         } else {
            from = middle + 1L;
            len -= half + 1L;
         }
      }

      return from;
   }

   private static void vecSwap(BigSwapper swapper, long from, long l, long s) {
      int i = 0;

      while ((long)i < s) {
         swapper.swap(from, l);
         i++;
         from++;
         l++;
      }
   }

   public static byte get(byte[][] array, long index) {
      return array[segment(index)][displacement(index)];
   }

   public static void set(byte[][] array, long index, byte value) {
      array[segment(index)][displacement(index)] = value;
   }

   public static void swap(byte[][] array, long first, long second) {
      byte t = array[segment(first)][displacement(first)];
      array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
      array[segment(second)][displacement(second)] = t;
   }

   public static byte[][] reverse(byte[][] a) {
      long length = length(a);
      long i = length / 2L;

      while (i-- != 0L) {
         swap(a, i, length - i - 1L);
      }

      return a;
   }

   public static void add(byte[][] array, long index, byte incr) {
      array[segment(index)][displacement(index)] += incr;
   }

   public static void mul(byte[][] array, long index, byte factor) {
      array[segment(index)][displacement(index)] *= factor;
   }

   public static void incr(byte[][] array, long index) {
      array[segment(index)][displacement(index)]++;
   }

   public static void decr(byte[][] array, long index) {
      array[segment(index)][displacement(index)]--;
   }

   public static void assertBigArray(byte[][] array) {
      int l = array.length;
      if (l != 0) {
         for (int i = 0; i < l - 1; i++) {
            if (array[i].length != 134217728) {
               throw new IllegalStateException("All segments except for the last one must be of length 2^" + Integer.toString(27));
            }
         }

         if (array[l - 1].length > 134217728) {
            throw new IllegalStateException("The last segment must be of length at most 2^" + Integer.toString(27));
         } else if (array[l - 1].length == 0 && l == 1) {
            throw new IllegalStateException("The last segment must be of nonzero length");
         }
      }
   }

   public static long length(byte[][] array) {
      int length = array.length;
      return length == 0 ? 0L : start(length - 1) + (long)array[length - 1].length;
   }

   public static void copy(byte[][] srcArray, long srcPos, byte[][] destArray, long destPos, long length) {
      if (destPos <= srcPos) {
         int srcSegment = segment(srcPos);
         int destSegment = segment(destPos);
         int srcDispl = displacement(srcPos);
         int destDispl = displacement(destPos);

         while (length > 0L) {
            int l = (int)Math.min(length, (long)Math.min(srcArray[srcSegment].length - srcDispl, destArray[destSegment].length - destDispl));
            if (l == 0) {
               throw new ArrayIndexOutOfBoundsException();
            }

            System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
            if ((srcDispl += l) == 134217728) {
               srcDispl = 0;
               srcSegment++;
            }

            if ((destDispl += l) == 134217728) {
               destDispl = 0;
               destSegment++;
            }

            length -= (long)l;
         }
      } else {
         int srcSegment = segment(srcPos + length);
         int destSegment = segment(destPos + length);
         int srcDispl = displacement(srcPos + length);
         int destDispl = displacement(destPos + length);

         while (length > 0L) {
            if (srcDispl == 0) {
               srcDispl = 134217728;
               srcSegment--;
            }

            if (destDispl == 0) {
               destDispl = 134217728;
               destSegment--;
            }

            int lx = (int)Math.min(length, (long)Math.min(srcDispl, destDispl));
            if (lx == 0) {
               throw new ArrayIndexOutOfBoundsException();
            }

            System.arraycopy(srcArray[srcSegment], srcDispl - lx, destArray[destSegment], destDispl - lx, lx);
            srcDispl -= lx;
            destDispl -= lx;
            length -= (long)lx;
         }
      }
   }

   public static void copyFromBig(byte[][] srcArray, long srcPos, byte[] destArray, int destPos, int length) {
      int srcSegment = segment(srcPos);
      int srcDispl = displacement(srcPos);

      while (length > 0) {
         int l = Math.min(srcArray[srcSegment].length - srcDispl, length);
         if (l == 0) {
            throw new ArrayIndexOutOfBoundsException();
         }

         System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
         if ((srcDispl += l) == 134217728) {
            srcDispl = 0;
            srcSegment++;
         }

         destPos += l;
         length -= l;
      }
   }

   public static void copyToBig(byte[] srcArray, int srcPos, byte[][] destArray, long destPos, long length) {
      int destSegment = segment(destPos);
      int destDispl = displacement(destPos);

      while (length > 0L) {
         int l = (int)Math.min((long)(destArray[destSegment].length - destDispl), length);
         if (l == 0) {
            throw new ArrayIndexOutOfBoundsException();
         }

         System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
         if ((destDispl += l) == 134217728) {
            destDispl = 0;
            destSegment++;
         }

         srcPos += l;
         length -= (long)l;
      }
   }

   public static byte[][] wrap(byte[] array) {
      if (array.length == 0) {
         return ByteBigArrays.EMPTY_BIG_ARRAY;
      } else if (array.length <= 134217728) {
         return new byte[][]{array};
      } else {
         byte[][] bigArray = ByteBigArrays.newBigArray((long)array.length);

         for (int i = 0; i < bigArray.length; i++) {
            System.arraycopy(array, (int)start(i), bigArray[i], 0, bigArray[i].length);
         }

         return bigArray;
      }
   }

   public static byte[][] ensureCapacity(byte[][] array, long length) {
      return ensureCapacity(array, length, length(array));
   }

   public static byte[][] forceCapacity(byte[][] array, long length, long preserve) {
      ensureLength(length);
      int valid = array.length - (array.length != 0 && (array.length <= 0 || array[array.length - 1].length != 134217728) ? 1 : 0);
      int baseLength = (int)(length + 134217727L >>> 27);
      byte[][] base = java.util.Arrays.copyOf(array, baseLength);
      int residual = (int)(length & 134217727L);
      if (residual != 0) {
         for (int i = valid; i < baseLength - 1; i++) {
            base[i] = new byte[134217728];
         }

         base[baseLength - 1] = new byte[residual];
      } else {
         for (int i = valid; i < baseLength; i++) {
            base[i] = new byte[134217728];
         }
      }

      if (preserve - (long)valid * 134217728L > 0L) {
         copy(array, (long)valid * 134217728L, base, (long)valid * 134217728L, preserve - (long)valid * 134217728L);
      }

      return base;
   }

   public static byte[][] ensureCapacity(byte[][] array, long length, long preserve) {
      return length > length(array) ? forceCapacity(array, length, preserve) : array;
   }

   public static byte[][] grow(byte[][] array, long length) {
      long oldLength = length(array);
      return length > oldLength ? grow(array, length, oldLength) : array;
   }

   public static byte[][] grow(byte[][] array, long length, long preserve) {
      long oldLength = length(array);
      return length > oldLength ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1), length), preserve) : array;
   }

   public static byte[][] trim(byte[][] array, long length) {
      ensureLength(length);
      long oldLength = length(array);
      if (length >= oldLength) {
         return array;
      } else {
         int baseLength = (int)(length + 134217727L >>> 27);
         byte[][] base = java.util.Arrays.copyOf(array, baseLength);
         int residual = (int)(length & 134217727L);
         if (residual != 0) {
            base[baseLength - 1] = ByteArrays.trim(base[baseLength - 1], residual);
         }

         return base;
      }
   }

   public static byte[][] setLength(byte[][] array, long length) {
      long oldLength = length(array);
      if (length == oldLength) {
         return array;
      } else {
         return length < oldLength ? trim(array, length) : ensureCapacity(array, length);
      }
   }

   public static byte[][] copy(byte[][] array, long offset, long length) {
      ensureOffsetLength(array, offset, length);
      byte[][] a = ByteBigArrays.newBigArray(length);
      copy(array, offset, a, 0L, length);
      return a;
   }

   public static byte[][] copy(byte[][] array) {
      byte[][] base = (byte[][])array.clone();
      int i = base.length;

      while (i-- != 0) {
         base[i] = (byte[])array[i].clone();
      }

      return base;
   }

   public static void fill(byte[][] array, byte value) {
      int i = array.length;

      while (i-- != 0) {
         java.util.Arrays.fill(array[i], value);
      }
   }

   public static void fill(byte[][] array, long from, long to, byte value) {
      long length = length(array);
      ensureFromTo(length, from, to);
      if (length != 0L) {
         int fromSegment = segment(from);
         int toSegment = segment(to);
         int fromDispl = displacement(from);
         int toDispl = displacement(to);
         if (fromSegment == toSegment) {
            java.util.Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
         } else {
            if (toDispl != 0) {
               java.util.Arrays.fill(array[toSegment], 0, toDispl, value);
            }

            while (--toSegment > fromSegment) {
               java.util.Arrays.fill(array[toSegment], value);
            }

            java.util.Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
         }
      }
   }

   public static boolean equals(byte[][] a1, byte[][] a2) {
      if (length(a1) != length(a2)) {
         return false;
      } else {
         int i = a1.length;

         while (i-- != 0) {
            byte[] t = a1[i];
            byte[] u = a2[i];
            int j = t.length;

            while (j-- != 0) {
               if (t[j] != u[j]) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   public static String toString(byte[][] a) {
      if (a == null) {
         return "null";
      } else {
         long last = length(a) - 1L;
         if (last == -1L) {
            return "[]";
         } else {
            StringBuilder b = new StringBuilder();
            b.append('[');
            long i = 0L;

            while (true) {
               b.append(String.valueOf(get(a, i)));
               if (i == last) {
                  return b.append(']').toString();
               }

               b.append(", ");
               i++;
            }
         }
      }
   }

   public static void ensureFromTo(byte[][] a, long from, long to) {
      ensureFromTo(length(a), from, to);
   }

   public static void ensureOffsetLength(byte[][] a, long offset, long length) {
      ensureOffsetLength(length(a), offset, length);
   }

   public static void ensureSameLength(byte[][] a, byte[][] b) {
      if (length(a) != length(b)) {
         throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
      }
   }

   public static byte[][] shuffle(byte[][] a, long from, long to, Random random) {
      long i = to - from;

      while (i-- != 0L) {
         long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
         byte t = get(a, from + i);
         set(a, from + i, get(a, from + p));
         set(a, from + p, t);
      }

      return a;
   }

   public static byte[][] shuffle(byte[][] a, Random random) {
      long i = length(a);

      while (i-- != 0L) {
         long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
         byte t = get(a, i);
         set(a, i, get(a, p));
         set(a, p, t);
      }

      return a;
   }

   public static int get(int[][] array, long index) {
      return array[segment(index)][displacement(index)];
   }

   public static void set(int[][] array, long index, int value) {
      array[segment(index)][displacement(index)] = value;
   }

   public static long length(AtomicIntegerArray[] array) {
      int length = array.length;
      return length == 0 ? 0L : start(length - 1) + (long)array[length - 1].length();
   }

   public static int get(AtomicIntegerArray[] array, long index) {
      return array[segment(index)].get(displacement(index));
   }

   public static void set(AtomicIntegerArray[] array, long index, int value) {
      array[segment(index)].set(displacement(index), value);
   }

   public static int getAndSet(AtomicIntegerArray[] array, long index, int value) {
      return array[segment(index)].getAndSet(displacement(index), value);
   }

   public static int getAndAdd(AtomicIntegerArray[] array, long index, int value) {
      return array[segment(index)].getAndAdd(displacement(index), value);
   }

   public static int addAndGet(AtomicIntegerArray[] array, long index, int value) {
      return array[segment(index)].addAndGet(displacement(index), value);
   }

   public static int getAndIncrement(AtomicIntegerArray[] array, long index) {
      return array[segment(index)].getAndDecrement(displacement(index));
   }

   public static int incrementAndGet(AtomicIntegerArray[] array, long index) {
      return array[segment(index)].incrementAndGet(displacement(index));
   }

   public static int getAndDecrement(AtomicIntegerArray[] array, long index) {
      return array[segment(index)].getAndDecrement(displacement(index));
   }

   public static int decrementAndGet(AtomicIntegerArray[] array, long index) {
      return array[segment(index)].decrementAndGet(displacement(index));
   }

   public static boolean compareAndSet(AtomicIntegerArray[] array, long index, int expected, int value) {
      return array[segment(index)].compareAndSet(displacement(index), expected, value);
   }

   public static void swap(int[][] array, long first, long second) {
      int t = array[segment(first)][displacement(first)];
      array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
      array[segment(second)][displacement(second)] = t;
   }

   public static int[][] reverse(int[][] a) {
      long length = length(a);
      long i = length / 2L;

      while (i-- != 0L) {
         swap(a, i, length - i - 1L);
      }

      return a;
   }

   public static void add(int[][] array, long index, int incr) {
      array[segment(index)][displacement(index)] += incr;
   }

   public static void mul(int[][] array, long index, int factor) {
      array[segment(index)][displacement(index)] *= factor;
   }

   public static void incr(int[][] array, long index) {
      array[segment(index)][displacement(index)]++;
   }

   public static void decr(int[][] array, long index) {
      array[segment(index)][displacement(index)]--;
   }

   public static void assertBigArray(int[][] array) {
      int l = array.length;
      if (l != 0) {
         for (int i = 0; i < l - 1; i++) {
            if (array[i].length != 134217728) {
               throw new IllegalStateException("All segments except for the last one must be of length 2^" + Integer.toString(27));
            }
         }

         if (array[l - 1].length > 134217728) {
            throw new IllegalStateException("The last segment must be of length at most 2^" + Integer.toString(27));
         } else if (array[l - 1].length == 0 && l == 1) {
            throw new IllegalStateException("The last segment must be of nonzero length");
         }
      }
   }

   public static long length(int[][] array) {
      int length = array.length;
      return length == 0 ? 0L : start(length - 1) + (long)array[length - 1].length;
   }

   public static void copy(int[][] srcArray, long srcPos, int[][] destArray, long destPos, long length) {
      if (destPos <= srcPos) {
         int srcSegment = segment(srcPos);
         int destSegment = segment(destPos);
         int srcDispl = displacement(srcPos);
         int destDispl = displacement(destPos);

         while (length > 0L) {
            int l = (int)Math.min(length, (long)Math.min(srcArray[srcSegment].length - srcDispl, destArray[destSegment].length - destDispl));
            if (l == 0) {
               throw new ArrayIndexOutOfBoundsException();
            }

            System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
            if ((srcDispl += l) == 134217728) {
               srcDispl = 0;
               srcSegment++;
            }

            if ((destDispl += l) == 134217728) {
               destDispl = 0;
               destSegment++;
            }

            length -= (long)l;
         }
      } else {
         int srcSegment = segment(srcPos + length);
         int destSegment = segment(destPos + length);
         int srcDispl = displacement(srcPos + length);
         int destDispl = displacement(destPos + length);

         while (length > 0L) {
            if (srcDispl == 0) {
               srcDispl = 134217728;
               srcSegment--;
            }

            if (destDispl == 0) {
               destDispl = 134217728;
               destSegment--;
            }

            int lx = (int)Math.min(length, (long)Math.min(srcDispl, destDispl));
            if (lx == 0) {
               throw new ArrayIndexOutOfBoundsException();
            }

            System.arraycopy(srcArray[srcSegment], srcDispl - lx, destArray[destSegment], destDispl - lx, lx);
            srcDispl -= lx;
            destDispl -= lx;
            length -= (long)lx;
         }
      }
   }

   public static void copyFromBig(int[][] srcArray, long srcPos, int[] destArray, int destPos, int length) {
      int srcSegment = segment(srcPos);
      int srcDispl = displacement(srcPos);

      while (length > 0) {
         int l = Math.min(srcArray[srcSegment].length - srcDispl, length);
         if (l == 0) {
            throw new ArrayIndexOutOfBoundsException();
         }

         System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
         if ((srcDispl += l) == 134217728) {
            srcDispl = 0;
            srcSegment++;
         }

         destPos += l;
         length -= l;
      }
   }

   public static void copyToBig(int[] srcArray, int srcPos, int[][] destArray, long destPos, long length) {
      int destSegment = segment(destPos);
      int destDispl = displacement(destPos);

      while (length > 0L) {
         int l = (int)Math.min((long)(destArray[destSegment].length - destDispl), length);
         if (l == 0) {
            throw new ArrayIndexOutOfBoundsException();
         }

         System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
         if ((destDispl += l) == 134217728) {
            destDispl = 0;
            destSegment++;
         }

         srcPos += l;
         length -= (long)l;
      }
   }

   public static int[][] wrap(int[] array) {
      if (array.length == 0) {
         return IntBigArrays.EMPTY_BIG_ARRAY;
      } else if (array.length <= 134217728) {
         return new int[][]{array};
      } else {
         int[][] bigArray = IntBigArrays.newBigArray((long)array.length);

         for (int i = 0; i < bigArray.length; i++) {
            System.arraycopy(array, (int)start(i), bigArray[i], 0, bigArray[i].length);
         }

         return bigArray;
      }
   }

   public static int[][] ensureCapacity(int[][] array, long length) {
      return ensureCapacity(array, length, length(array));
   }

   public static int[][] forceCapacity(int[][] array, long length, long preserve) {
      ensureLength(length);
      int valid = array.length - (array.length != 0 && (array.length <= 0 || array[array.length - 1].length != 134217728) ? 1 : 0);
      int baseLength = (int)(length + 134217727L >>> 27);
      int[][] base = java.util.Arrays.copyOf(array, baseLength);
      int residual = (int)(length & 134217727L);
      if (residual != 0) {
         for (int i = valid; i < baseLength - 1; i++) {
            base[i] = new int[134217728];
         }

         base[baseLength - 1] = new int[residual];
      } else {
         for (int i = valid; i < baseLength; i++) {
            base[i] = new int[134217728];
         }
      }

      if (preserve - (long)valid * 134217728L > 0L) {
         copy(array, (long)valid * 134217728L, base, (long)valid * 134217728L, preserve - (long)valid * 134217728L);
      }

      return base;
   }

   public static int[][] ensureCapacity(int[][] array, long length, long preserve) {
      return length > length(array) ? forceCapacity(array, length, preserve) : array;
   }

   public static int[][] grow(int[][] array, long length) {
      long oldLength = length(array);
      return length > oldLength ? grow(array, length, oldLength) : array;
   }

   public static int[][] grow(int[][] array, long length, long preserve) {
      long oldLength = length(array);
      return length > oldLength ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1), length), preserve) : array;
   }

   public static int[][] trim(int[][] array, long length) {
      ensureLength(length);
      long oldLength = length(array);
      if (length >= oldLength) {
         return array;
      } else {
         int baseLength = (int)(length + 134217727L >>> 27);
         int[][] base = java.util.Arrays.copyOf(array, baseLength);
         int residual = (int)(length & 134217727L);
         if (residual != 0) {
            base[baseLength - 1] = IntArrays.trim(base[baseLength - 1], residual);
         }

         return base;
      }
   }

   public static int[][] setLength(int[][] array, long length) {
      long oldLength = length(array);
      if (length == oldLength) {
         return array;
      } else {
         return length < oldLength ? trim(array, length) : ensureCapacity(array, length);
      }
   }

   public static int[][] copy(int[][] array, long offset, long length) {
      ensureOffsetLength(array, offset, length);
      int[][] a = IntBigArrays.newBigArray(length);
      copy(array, offset, a, 0L, length);
      return a;
   }

   public static int[][] copy(int[][] array) {
      int[][] base = (int[][])array.clone();
      int i = base.length;

      while (i-- != 0) {
         base[i] = (int[])array[i].clone();
      }

      return base;
   }

   public static void fill(int[][] array, int value) {
      int i = array.length;

      while (i-- != 0) {
         java.util.Arrays.fill(array[i], value);
      }
   }

   public static void fill(int[][] array, long from, long to, int value) {
      long length = length(array);
      ensureFromTo(length, from, to);
      if (length != 0L) {
         int fromSegment = segment(from);
         int toSegment = segment(to);
         int fromDispl = displacement(from);
         int toDispl = displacement(to);
         if (fromSegment == toSegment) {
            java.util.Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
         } else {
            if (toDispl != 0) {
               java.util.Arrays.fill(array[toSegment], 0, toDispl, value);
            }

            while (--toSegment > fromSegment) {
               java.util.Arrays.fill(array[toSegment], value);
            }

            java.util.Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
         }
      }
   }

   public static boolean equals(int[][] a1, int[][] a2) {
      if (length(a1) != length(a2)) {
         return false;
      } else {
         int i = a1.length;

         while (i-- != 0) {
            int[] t = a1[i];
            int[] u = a2[i];
            int j = t.length;

            while (j-- != 0) {
               if (t[j] != u[j]) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   public static String toString(int[][] a) {
      if (a == null) {
         return "null";
      } else {
         long last = length(a) - 1L;
         if (last == -1L) {
            return "[]";
         } else {
            StringBuilder b = new StringBuilder();
            b.append('[');
            long i = 0L;

            while (true) {
               b.append(String.valueOf(get(a, i)));
               if (i == last) {
                  return b.append(']').toString();
               }

               b.append(", ");
               i++;
            }
         }
      }
   }

   public static void ensureFromTo(int[][] a, long from, long to) {
      ensureFromTo(length(a), from, to);
   }

   public static void ensureOffsetLength(int[][] a, long offset, long length) {
      ensureOffsetLength(length(a), offset, length);
   }

   public static void ensureSameLength(int[][] a, int[][] b) {
      if (length(a) != length(b)) {
         throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
      }
   }

   public static int[][] shuffle(int[][] a, long from, long to, Random random) {
      long i = to - from;

      while (i-- != 0L) {
         long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
         int t = get(a, from + i);
         set(a, from + i, get(a, from + p));
         set(a, from + p, t);
      }

      return a;
   }

   public static int[][] shuffle(int[][] a, Random random) {
      long i = length(a);

      while (i-- != 0L) {
         long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
         int t = get(a, i);
         set(a, i, get(a, p));
         set(a, p, t);
      }

      return a;
   }

   public static long get(long[][] array, long index) {
      return array[segment(index)][displacement(index)];
   }

   public static void set(long[][] array, long index, long value) {
      array[segment(index)][displacement(index)] = value;
   }

   public static long length(AtomicLongArray[] array) {
      int length = array.length;
      return length == 0 ? 0L : start(length - 1) + (long)array[length - 1].length();
   }

   public static long get(AtomicLongArray[] array, long index) {
      return array[segment(index)].get(displacement(index));
   }

   public static void set(AtomicLongArray[] array, long index, long value) {
      array[segment(index)].set(displacement(index), value);
   }

   public static long getAndSet(AtomicLongArray[] array, long index, long value) {
      return array[segment(index)].getAndSet(displacement(index), value);
   }

   public static long getAndAdd(AtomicLongArray[] array, long index, long value) {
      return array[segment(index)].getAndAdd(displacement(index), value);
   }

   public static long addAndGet(AtomicLongArray[] array, long index, long value) {
      return array[segment(index)].addAndGet(displacement(index), value);
   }

   public static long getAndIncrement(AtomicLongArray[] array, long index) {
      return array[segment(index)].getAndDecrement(displacement(index));
   }

   public static long incrementAndGet(AtomicLongArray[] array, long index) {
      return array[segment(index)].incrementAndGet(displacement(index));
   }

   public static long getAndDecrement(AtomicLongArray[] array, long index) {
      return array[segment(index)].getAndDecrement(displacement(index));
   }

   public static long decrementAndGet(AtomicLongArray[] array, long index) {
      return array[segment(index)].decrementAndGet(displacement(index));
   }

   public static boolean compareAndSet(AtomicLongArray[] array, long index, long expected, long value) {
      return array[segment(index)].compareAndSet(displacement(index), expected, value);
   }

   public static void swap(long[][] array, long first, long second) {
      long t = array[segment(first)][displacement(first)];
      array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
      array[segment(second)][displacement(second)] = t;
   }

   public static long[][] reverse(long[][] a) {
      long length = length(a);
      long i = length / 2L;

      while (i-- != 0L) {
         swap(a, i, length - i - 1L);
      }

      return a;
   }

   public static void add(long[][] array, long index, long incr) {
      array[segment(index)][displacement(index)] += incr;
   }

   public static void mul(long[][] array, long index, long factor) {
      array[segment(index)][displacement(index)] *= factor;
   }

   public static void incr(long[][] array, long index) {
      array[segment(index)][displacement(index)]++;
   }

   public static void decr(long[][] array, long index) {
      array[segment(index)][displacement(index)]--;
   }

   public static void assertBigArray(long[][] array) {
      int l = array.length;
      if (l != 0) {
         for (int i = 0; i < l - 1; i++) {
            if (array[i].length != 134217728) {
               throw new IllegalStateException("All segments except for the last one must be of length 2^" + Integer.toString(27));
            }
         }

         if (array[l - 1].length > 134217728) {
            throw new IllegalStateException("The last segment must be of length at most 2^" + Integer.toString(27));
         } else if (array[l - 1].length == 0 && l == 1) {
            throw new IllegalStateException("The last segment must be of nonzero length");
         }
      }
   }

   public static long length(long[][] array) {
      int length = array.length;
      return length == 0 ? 0L : start(length - 1) + (long)array[length - 1].length;
   }

   public static void copy(long[][] srcArray, long srcPos, long[][] destArray, long destPos, long length) {
      if (destPos <= srcPos) {
         int srcSegment = segment(srcPos);
         int destSegment = segment(destPos);
         int srcDispl = displacement(srcPos);
         int destDispl = displacement(destPos);

         while (length > 0L) {
            int l = (int)Math.min(length, (long)Math.min(srcArray[srcSegment].length - srcDispl, destArray[destSegment].length - destDispl));
            if (l == 0) {
               throw new ArrayIndexOutOfBoundsException();
            }

            System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
            if ((srcDispl += l) == 134217728) {
               srcDispl = 0;
               srcSegment++;
            }

            if ((destDispl += l) == 134217728) {
               destDispl = 0;
               destSegment++;
            }

            length -= (long)l;
         }
      } else {
         int srcSegment = segment(srcPos + length);
         int destSegment = segment(destPos + length);
         int srcDispl = displacement(srcPos + length);
         int destDispl = displacement(destPos + length);

         while (length > 0L) {
            if (srcDispl == 0) {
               srcDispl = 134217728;
               srcSegment--;
            }

            if (destDispl == 0) {
               destDispl = 134217728;
               destSegment--;
            }

            int lx = (int)Math.min(length, (long)Math.min(srcDispl, destDispl));
            if (lx == 0) {
               throw new ArrayIndexOutOfBoundsException();
            }

            System.arraycopy(srcArray[srcSegment], srcDispl - lx, destArray[destSegment], destDispl - lx, lx);
            srcDispl -= lx;
            destDispl -= lx;
            length -= (long)lx;
         }
      }
   }

   public static void copyFromBig(long[][] srcArray, long srcPos, long[] destArray, int destPos, int length) {
      int srcSegment = segment(srcPos);
      int srcDispl = displacement(srcPos);

      while (length > 0) {
         int l = Math.min(srcArray[srcSegment].length - srcDispl, length);
         if (l == 0) {
            throw new ArrayIndexOutOfBoundsException();
         }

         System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
         if ((srcDispl += l) == 134217728) {
            srcDispl = 0;
            srcSegment++;
         }

         destPos += l;
         length -= l;
      }
   }

   public static void copyToBig(long[] srcArray, int srcPos, long[][] destArray, long destPos, long length) {
      int destSegment = segment(destPos);
      int destDispl = displacement(destPos);

      while (length > 0L) {
         int l = (int)Math.min((long)(destArray[destSegment].length - destDispl), length);
         if (l == 0) {
            throw new ArrayIndexOutOfBoundsException();
         }

         System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
         if ((destDispl += l) == 134217728) {
            destDispl = 0;
            destSegment++;
         }

         srcPos += l;
         length -= (long)l;
      }
   }

   public static long[][] wrap(long[] array) {
      if (array.length == 0) {
         return LongBigArrays.EMPTY_BIG_ARRAY;
      } else if (array.length <= 134217728) {
         return new long[][]{array};
      } else {
         long[][] bigArray = LongBigArrays.newBigArray((long)array.length);

         for (int i = 0; i < bigArray.length; i++) {
            System.arraycopy(array, (int)start(i), bigArray[i], 0, bigArray[i].length);
         }

         return bigArray;
      }
   }

   public static long[][] ensureCapacity(long[][] array, long length) {
      return ensureCapacity(array, length, length(array));
   }

   public static long[][] forceCapacity(long[][] array, long length, long preserve) {
      ensureLength(length);
      int valid = array.length - (array.length != 0 && (array.length <= 0 || array[array.length - 1].length != 134217728) ? 1 : 0);
      int baseLength = (int)(length + 134217727L >>> 27);
      long[][] base = java.util.Arrays.copyOf(array, baseLength);
      int residual = (int)(length & 134217727L);
      if (residual != 0) {
         for (int i = valid; i < baseLength - 1; i++) {
            base[i] = new long[134217728];
         }

         base[baseLength - 1] = new long[residual];
      } else {
         for (int i = valid; i < baseLength; i++) {
            base[i] = new long[134217728];
         }
      }

      if (preserve - (long)valid * 134217728L > 0L) {
         copy(array, (long)valid * 134217728L, base, (long)valid * 134217728L, preserve - (long)valid * 134217728L);
      }

      return base;
   }

   public static long[][] ensureCapacity(long[][] array, long length, long preserve) {
      return length > length(array) ? forceCapacity(array, length, preserve) : array;
   }

   public static long[][] grow(long[][] array, long length) {
      long oldLength = length(array);
      return length > oldLength ? grow(array, length, oldLength) : array;
   }

   public static long[][] grow(long[][] array, long length, long preserve) {
      long oldLength = length(array);
      return length > oldLength ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1), length), preserve) : array;
   }

   public static long[][] trim(long[][] array, long length) {
      ensureLength(length);
      long oldLength = length(array);
      if (length >= oldLength) {
         return array;
      } else {
         int baseLength = (int)(length + 134217727L >>> 27);
         long[][] base = java.util.Arrays.copyOf(array, baseLength);
         int residual = (int)(length & 134217727L);
         if (residual != 0) {
            base[baseLength - 1] = LongArrays.trim(base[baseLength - 1], residual);
         }

         return base;
      }
   }

   public static long[][] setLength(long[][] array, long length) {
      long oldLength = length(array);
      if (length == oldLength) {
         return array;
      } else {
         return length < oldLength ? trim(array, length) : ensureCapacity(array, length);
      }
   }

   public static long[][] copy(long[][] array, long offset, long length) {
      ensureOffsetLength(array, offset, length);
      long[][] a = LongBigArrays.newBigArray(length);
      copy(array, offset, a, 0L, length);
      return a;
   }

   public static long[][] copy(long[][] array) {
      long[][] base = (long[][])array.clone();
      int i = base.length;

      while (i-- != 0) {
         base[i] = (long[])array[i].clone();
      }

      return base;
   }

   public static void fill(long[][] array, long value) {
      int i = array.length;

      while (i-- != 0) {
         java.util.Arrays.fill(array[i], value);
      }
   }

   public static void fill(long[][] array, long from, long to, long value) {
      long length = length(array);
      ensureFromTo(length, from, to);
      if (length != 0L) {
         int fromSegment = segment(from);
         int toSegment = segment(to);
         int fromDispl = displacement(from);
         int toDispl = displacement(to);
         if (fromSegment == toSegment) {
            java.util.Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
         } else {
            if (toDispl != 0) {
               java.util.Arrays.fill(array[toSegment], 0, toDispl, value);
            }

            while (--toSegment > fromSegment) {
               java.util.Arrays.fill(array[toSegment], value);
            }

            java.util.Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
         }
      }
   }

   public static boolean equals(long[][] a1, long[][] a2) {
      if (length(a1) != length(a2)) {
         return false;
      } else {
         int i = a1.length;

         while (i-- != 0) {
            long[] t = a1[i];
            long[] u = a2[i];
            int j = t.length;

            while (j-- != 0) {
               if (t[j] != u[j]) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   public static String toString(long[][] a) {
      if (a == null) {
         return "null";
      } else {
         long last = length(a) - 1L;
         if (last == -1L) {
            return "[]";
         } else {
            StringBuilder b = new StringBuilder();
            b.append('[');
            long i = 0L;

            while (true) {
               b.append(String.valueOf(get(a, i)));
               if (i == last) {
                  return b.append(']').toString();
               }

               b.append(", ");
               i++;
            }
         }
      }
   }

   public static void ensureFromTo(long[][] a, long from, long to) {
      ensureFromTo(length(a), from, to);
   }

   public static void ensureOffsetLength(long[][] a, long offset, long length) {
      ensureOffsetLength(length(a), offset, length);
   }

   public static void ensureSameLength(long[][] a, long[][] b) {
      if (length(a) != length(b)) {
         throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
      }
   }

   public static long[][] shuffle(long[][] a, long from, long to, Random random) {
      long i = to - from;

      while (i-- != 0L) {
         long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
         long t = get(a, from + i);
         set(a, from + i, get(a, from + p));
         set(a, from + p, t);
      }

      return a;
   }

   public static long[][] shuffle(long[][] a, Random random) {
      long i = length(a);

      while (i-- != 0L) {
         long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
         long t = get(a, i);
         set(a, i, get(a, p));
         set(a, p, t);
      }

      return a;
   }

   public static double get(double[][] array, long index) {
      return array[segment(index)][displacement(index)];
   }

   public static void set(double[][] array, long index, double value) {
      array[segment(index)][displacement(index)] = value;
   }

   public static void swap(double[][] array, long first, long second) {
      double t = array[segment(first)][displacement(first)];
      array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
      array[segment(second)][displacement(second)] = t;
   }

   public static double[][] reverse(double[][] a) {
      long length = length(a);
      long i = length / 2L;

      while (i-- != 0L) {
         swap(a, i, length - i - 1L);
      }

      return a;
   }

   public static void add(double[][] array, long index, double incr) {
      array[segment(index)][displacement(index)] += incr;
   }

   public static void mul(double[][] array, long index, double factor) {
      array[segment(index)][displacement(index)] *= factor;
   }

   public static void incr(double[][] array, long index) {
      array[segment(index)][displacement(index)]++;
   }

   public static void decr(double[][] array, long index) {
      array[segment(index)][displacement(index)]--;
   }

   public static void assertBigArray(double[][] array) {
      int l = array.length;
      if (l != 0) {
         for (int i = 0; i < l - 1; i++) {
            if (array[i].length != 134217728) {
               throw new IllegalStateException("All segments except for the last one must be of length 2^" + Integer.toString(27));
            }
         }

         if (array[l - 1].length > 134217728) {
            throw new IllegalStateException("The last segment must be of length at most 2^" + Integer.toString(27));
         } else if (array[l - 1].length == 0 && l == 1) {
            throw new IllegalStateException("The last segment must be of nonzero length");
         }
      }
   }

   public static long length(double[][] array) {
      int length = array.length;
      return length == 0 ? 0L : start(length - 1) + (long)array[length - 1].length;
   }

   public static void copy(double[][] srcArray, long srcPos, double[][] destArray, long destPos, long length) {
      if (destPos <= srcPos) {
         int srcSegment = segment(srcPos);
         int destSegment = segment(destPos);
         int srcDispl = displacement(srcPos);
         int destDispl = displacement(destPos);

         while (length > 0L) {
            int l = (int)Math.min(length, (long)Math.min(srcArray[srcSegment].length - srcDispl, destArray[destSegment].length - destDispl));
            if (l == 0) {
               throw new ArrayIndexOutOfBoundsException();
            }

            System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
            if ((srcDispl += l) == 134217728) {
               srcDispl = 0;
               srcSegment++;
            }

            if ((destDispl += l) == 134217728) {
               destDispl = 0;
               destSegment++;
            }

            length -= (long)l;
         }
      } else {
         int srcSegment = segment(srcPos + length);
         int destSegment = segment(destPos + length);
         int srcDispl = displacement(srcPos + length);
         int destDispl = displacement(destPos + length);

         while (length > 0L) {
            if (srcDispl == 0) {
               srcDispl = 134217728;
               srcSegment--;
            }

            if (destDispl == 0) {
               destDispl = 134217728;
               destSegment--;
            }

            int lx = (int)Math.min(length, (long)Math.min(srcDispl, destDispl));
            if (lx == 0) {
               throw new ArrayIndexOutOfBoundsException();
            }

            System.arraycopy(srcArray[srcSegment], srcDispl - lx, destArray[destSegment], destDispl - lx, lx);
            srcDispl -= lx;
            destDispl -= lx;
            length -= (long)lx;
         }
      }
   }

   public static void copyFromBig(double[][] srcArray, long srcPos, double[] destArray, int destPos, int length) {
      int srcSegment = segment(srcPos);
      int srcDispl = displacement(srcPos);

      while (length > 0) {
         int l = Math.min(srcArray[srcSegment].length - srcDispl, length);
         if (l == 0) {
            throw new ArrayIndexOutOfBoundsException();
         }

         System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
         if ((srcDispl += l) == 134217728) {
            srcDispl = 0;
            srcSegment++;
         }

         destPos += l;
         length -= l;
      }
   }

   public static void copyToBig(double[] srcArray, int srcPos, double[][] destArray, long destPos, long length) {
      int destSegment = segment(destPos);
      int destDispl = displacement(destPos);

      while (length > 0L) {
         int l = (int)Math.min((long)(destArray[destSegment].length - destDispl), length);
         if (l == 0) {
            throw new ArrayIndexOutOfBoundsException();
         }

         System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
         if ((destDispl += l) == 134217728) {
            destDispl = 0;
            destSegment++;
         }

         srcPos += l;
         length -= (long)l;
      }
   }

   public static double[][] wrap(double[] array) {
      if (array.length == 0) {
         return DoubleBigArrays.EMPTY_BIG_ARRAY;
      } else if (array.length <= 134217728) {
         return new double[][]{array};
      } else {
         double[][] bigArray = DoubleBigArrays.newBigArray((long)array.length);

         for (int i = 0; i < bigArray.length; i++) {
            System.arraycopy(array, (int)start(i), bigArray[i], 0, bigArray[i].length);
         }

         return bigArray;
      }
   }

   public static double[][] ensureCapacity(double[][] array, long length) {
      return ensureCapacity(array, length, length(array));
   }

   public static double[][] forceCapacity(double[][] array, long length, long preserve) {
      ensureLength(length);
      int valid = array.length - (array.length != 0 && (array.length <= 0 || array[array.length - 1].length != 134217728) ? 1 : 0);
      int baseLength = (int)(length + 134217727L >>> 27);
      double[][] base = java.util.Arrays.copyOf(array, baseLength);
      int residual = (int)(length & 134217727L);
      if (residual != 0) {
         for (int i = valid; i < baseLength - 1; i++) {
            base[i] = new double[134217728];
         }

         base[baseLength - 1] = new double[residual];
      } else {
         for (int i = valid; i < baseLength; i++) {
            base[i] = new double[134217728];
         }
      }

      if (preserve - (long)valid * 134217728L > 0L) {
         copy(array, (long)valid * 134217728L, base, (long)valid * 134217728L, preserve - (long)valid * 134217728L);
      }

      return base;
   }

   public static double[][] ensureCapacity(double[][] array, long length, long preserve) {
      return length > length(array) ? forceCapacity(array, length, preserve) : array;
   }

   public static double[][] grow(double[][] array, long length) {
      long oldLength = length(array);
      return length > oldLength ? grow(array, length, oldLength) : array;
   }

   public static double[][] grow(double[][] array, long length, long preserve) {
      long oldLength = length(array);
      return length > oldLength ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1), length), preserve) : array;
   }

   public static double[][] trim(double[][] array, long length) {
      ensureLength(length);
      long oldLength = length(array);
      if (length >= oldLength) {
         return array;
      } else {
         int baseLength = (int)(length + 134217727L >>> 27);
         double[][] base = java.util.Arrays.copyOf(array, baseLength);
         int residual = (int)(length & 134217727L);
         if (residual != 0) {
            base[baseLength - 1] = DoubleArrays.trim(base[baseLength - 1], residual);
         }

         return base;
      }
   }

   public static double[][] setLength(double[][] array, long length) {
      long oldLength = length(array);
      if (length == oldLength) {
         return array;
      } else {
         return length < oldLength ? trim(array, length) : ensureCapacity(array, length);
      }
   }

   public static double[][] copy(double[][] array, long offset, long length) {
      ensureOffsetLength(array, offset, length);
      double[][] a = DoubleBigArrays.newBigArray(length);
      copy(array, offset, a, 0L, length);
      return a;
   }

   public static double[][] copy(double[][] array) {
      double[][] base = (double[][])array.clone();
      int i = base.length;

      while (i-- != 0) {
         base[i] = (double[])array[i].clone();
      }

      return base;
   }

   public static void fill(double[][] array, double value) {
      int i = array.length;

      while (i-- != 0) {
         java.util.Arrays.fill(array[i], value);
      }
   }

   public static void fill(double[][] array, long from, long to, double value) {
      long length = length(array);
      ensureFromTo(length, from, to);
      if (length != 0L) {
         int fromSegment = segment(from);
         int toSegment = segment(to);
         int fromDispl = displacement(from);
         int toDispl = displacement(to);
         if (fromSegment == toSegment) {
            java.util.Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
         } else {
            if (toDispl != 0) {
               java.util.Arrays.fill(array[toSegment], 0, toDispl, value);
            }

            while (--toSegment > fromSegment) {
               java.util.Arrays.fill(array[toSegment], value);
            }

            java.util.Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
         }
      }
   }

   public static boolean equals(double[][] a1, double[][] a2) {
      if (length(a1) != length(a2)) {
         return false;
      } else {
         int i = a1.length;

         while (i-- != 0) {
            double[] t = a1[i];
            double[] u = a2[i];
            int j = t.length;

            while (j-- != 0) {
               if (Double.doubleToLongBits(t[j]) != Double.doubleToLongBits(u[j])) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   public static String toString(double[][] a) {
      if (a == null) {
         return "null";
      } else {
         long last = length(a) - 1L;
         if (last == -1L) {
            return "[]";
         } else {
            StringBuilder b = new StringBuilder();
            b.append('[');
            long i = 0L;

            while (true) {
               b.append(String.valueOf(get(a, i)));
               if (i == last) {
                  return b.append(']').toString();
               }

               b.append(", ");
               i++;
            }
         }
      }
   }

   public static void ensureFromTo(double[][] a, long from, long to) {
      ensureFromTo(length(a), from, to);
   }

   public static void ensureOffsetLength(double[][] a, long offset, long length) {
      ensureOffsetLength(length(a), offset, length);
   }

   public static void ensureSameLength(double[][] a, double[][] b) {
      if (length(a) != length(b)) {
         throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
      }
   }

   public static double[][] shuffle(double[][] a, long from, long to, Random random) {
      long i = to - from;

      while (i-- != 0L) {
         long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
         double t = get(a, from + i);
         set(a, from + i, get(a, from + p));
         set(a, from + p, t);
      }

      return a;
   }

   public static double[][] shuffle(double[][] a, Random random) {
      long i = length(a);

      while (i-- != 0L) {
         long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
         double t = get(a, i);
         set(a, i, get(a, p));
         set(a, p, t);
      }

      return a;
   }

   public static boolean get(boolean[][] array, long index) {
      return array[segment(index)][displacement(index)];
   }

   public static void set(boolean[][] array, long index, boolean value) {
      array[segment(index)][displacement(index)] = value;
   }

   public static void swap(boolean[][] array, long first, long second) {
      boolean t = array[segment(first)][displacement(first)];
      array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
      array[segment(second)][displacement(second)] = t;
   }

   public static boolean[][] reverse(boolean[][] a) {
      long length = length(a);
      long i = length / 2L;

      while (i-- != 0L) {
         swap(a, i, length - i - 1L);
      }

      return a;
   }

   public static void assertBigArray(boolean[][] array) {
      int l = array.length;
      if (l != 0) {
         for (int i = 0; i < l - 1; i++) {
            if (array[i].length != 134217728) {
               throw new IllegalStateException("All segments except for the last one must be of length 2^" + Integer.toString(27));
            }
         }

         if (array[l - 1].length > 134217728) {
            throw new IllegalStateException("The last segment must be of length at most 2^" + Integer.toString(27));
         } else if (array[l - 1].length == 0 && l == 1) {
            throw new IllegalStateException("The last segment must be of nonzero length");
         }
      }
   }

   public static long length(boolean[][] array) {
      int length = array.length;
      return length == 0 ? 0L : start(length - 1) + (long)array[length - 1].length;
   }

   public static void copy(boolean[][] srcArray, long srcPos, boolean[][] destArray, long destPos, long length) {
      if (destPos <= srcPos) {
         int srcSegment = segment(srcPos);
         int destSegment = segment(destPos);
         int srcDispl = displacement(srcPos);
         int destDispl = displacement(destPos);

         while (length > 0L) {
            int l = (int)Math.min(length, (long)Math.min(srcArray[srcSegment].length - srcDispl, destArray[destSegment].length - destDispl));
            if (l == 0) {
               throw new ArrayIndexOutOfBoundsException();
            }

            System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
            if ((srcDispl += l) == 134217728) {
               srcDispl = 0;
               srcSegment++;
            }

            if ((destDispl += l) == 134217728) {
               destDispl = 0;
               destSegment++;
            }

            length -= (long)l;
         }
      } else {
         int srcSegment = segment(srcPos + length);
         int destSegment = segment(destPos + length);
         int srcDispl = displacement(srcPos + length);
         int destDispl = displacement(destPos + length);

         while (length > 0L) {
            if (srcDispl == 0) {
               srcDispl = 134217728;
               srcSegment--;
            }

            if (destDispl == 0) {
               destDispl = 134217728;
               destSegment--;
            }

            int lx = (int)Math.min(length, (long)Math.min(srcDispl, destDispl));
            if (lx == 0) {
               throw new ArrayIndexOutOfBoundsException();
            }

            System.arraycopy(srcArray[srcSegment], srcDispl - lx, destArray[destSegment], destDispl - lx, lx);
            srcDispl -= lx;
            destDispl -= lx;
            length -= (long)lx;
         }
      }
   }

   public static void copyFromBig(boolean[][] srcArray, long srcPos, boolean[] destArray, int destPos, int length) {
      int srcSegment = segment(srcPos);
      int srcDispl = displacement(srcPos);

      while (length > 0) {
         int l = Math.min(srcArray[srcSegment].length - srcDispl, length);
         if (l == 0) {
            throw new ArrayIndexOutOfBoundsException();
         }

         System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
         if ((srcDispl += l) == 134217728) {
            srcDispl = 0;
            srcSegment++;
         }

         destPos += l;
         length -= l;
      }
   }

   public static void copyToBig(boolean[] srcArray, int srcPos, boolean[][] destArray, long destPos, long length) {
      int destSegment = segment(destPos);
      int destDispl = displacement(destPos);

      while (length > 0L) {
         int l = (int)Math.min((long)(destArray[destSegment].length - destDispl), length);
         if (l == 0) {
            throw new ArrayIndexOutOfBoundsException();
         }

         System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
         if ((destDispl += l) == 134217728) {
            destDispl = 0;
            destSegment++;
         }

         srcPos += l;
         length -= (long)l;
      }
   }

   public static boolean[][] wrap(boolean[] array) {
      if (array.length == 0) {
         return BooleanBigArrays.EMPTY_BIG_ARRAY;
      } else if (array.length <= 134217728) {
         return new boolean[][]{array};
      } else {
         boolean[][] bigArray = BooleanBigArrays.newBigArray((long)array.length);

         for (int i = 0; i < bigArray.length; i++) {
            System.arraycopy(array, (int)start(i), bigArray[i], 0, bigArray[i].length);
         }

         return bigArray;
      }
   }

   public static boolean[][] ensureCapacity(boolean[][] array, long length) {
      return ensureCapacity(array, length, length(array));
   }

   public static boolean[][] forceCapacity(boolean[][] array, long length, long preserve) {
      ensureLength(length);
      int valid = array.length - (array.length != 0 && (array.length <= 0 || array[array.length - 1].length != 134217728) ? 1 : 0);
      int baseLength = (int)(length + 134217727L >>> 27);
      boolean[][] base = java.util.Arrays.copyOf(array, baseLength);
      int residual = (int)(length & 134217727L);
      if (residual != 0) {
         for (int i = valid; i < baseLength - 1; i++) {
            base[i] = new boolean[134217728];
         }

         base[baseLength - 1] = new boolean[residual];
      } else {
         for (int i = valid; i < baseLength; i++) {
            base[i] = new boolean[134217728];
         }
      }

      if (preserve - (long)valid * 134217728L > 0L) {
         copy(array, (long)valid * 134217728L, base, (long)valid * 134217728L, preserve - (long)valid * 134217728L);
      }

      return base;
   }

   public static boolean[][] ensureCapacity(boolean[][] array, long length, long preserve) {
      return length > length(array) ? forceCapacity(array, length, preserve) : array;
   }

   public static boolean[][] grow(boolean[][] array, long length) {
      long oldLength = length(array);
      return length > oldLength ? grow(array, length, oldLength) : array;
   }

   public static boolean[][] grow(boolean[][] array, long length, long preserve) {
      long oldLength = length(array);
      return length > oldLength ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1), length), preserve) : array;
   }

   public static boolean[][] trim(boolean[][] array, long length) {
      ensureLength(length);
      long oldLength = length(array);
      if (length >= oldLength) {
         return array;
      } else {
         int baseLength = (int)(length + 134217727L >>> 27);
         boolean[][] base = java.util.Arrays.copyOf(array, baseLength);
         int residual = (int)(length & 134217727L);
         if (residual != 0) {
            base[baseLength - 1] = BooleanArrays.trim(base[baseLength - 1], residual);
         }

         return base;
      }
   }

   public static boolean[][] setLength(boolean[][] array, long length) {
      long oldLength = length(array);
      if (length == oldLength) {
         return array;
      } else {
         return length < oldLength ? trim(array, length) : ensureCapacity(array, length);
      }
   }

   public static boolean[][] copy(boolean[][] array, long offset, long length) {
      ensureOffsetLength(array, offset, length);
      boolean[][] a = BooleanBigArrays.newBigArray(length);
      copy(array, offset, a, 0L, length);
      return a;
   }

   public static boolean[][] copy(boolean[][] array) {
      boolean[][] base = (boolean[][])array.clone();
      int i = base.length;

      while (i-- != 0) {
         base[i] = (boolean[])array[i].clone();
      }

      return base;
   }

   public static void fill(boolean[][] array, boolean value) {
      int i = array.length;

      while (i-- != 0) {
         java.util.Arrays.fill(array[i], value);
      }
   }

   public static void fill(boolean[][] array, long from, long to, boolean value) {
      long length = length(array);
      ensureFromTo(length, from, to);
      if (length != 0L) {
         int fromSegment = segment(from);
         int toSegment = segment(to);
         int fromDispl = displacement(from);
         int toDispl = displacement(to);
         if (fromSegment == toSegment) {
            java.util.Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
         } else {
            if (toDispl != 0) {
               java.util.Arrays.fill(array[toSegment], 0, toDispl, value);
            }

            while (--toSegment > fromSegment) {
               java.util.Arrays.fill(array[toSegment], value);
            }

            java.util.Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
         }
      }
   }

   public static boolean equals(boolean[][] a1, boolean[][] a2) {
      if (length(a1) != length(a2)) {
         return false;
      } else {
         int i = a1.length;

         while (i-- != 0) {
            boolean[] t = a1[i];
            boolean[] u = a2[i];
            int j = t.length;

            while (j-- != 0) {
               if (t[j] != u[j]) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   public static String toString(boolean[][] a) {
      if (a == null) {
         return "null";
      } else {
         long last = length(a) - 1L;
         if (last == -1L) {
            return "[]";
         } else {
            StringBuilder b = new StringBuilder();
            b.append('[');
            long i = 0L;

            while (true) {
               b.append(String.valueOf(get(a, i)));
               if (i == last) {
                  return b.append(']').toString();
               }

               b.append(", ");
               i++;
            }
         }
      }
   }

   public static void ensureFromTo(boolean[][] a, long from, long to) {
      ensureFromTo(length(a), from, to);
   }

   public static void ensureOffsetLength(boolean[][] a, long offset, long length) {
      ensureOffsetLength(length(a), offset, length);
   }

   public static void ensureSameLength(boolean[][] a, boolean[][] b) {
      if (length(a) != length(b)) {
         throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
      }
   }

   public static boolean[][] shuffle(boolean[][] a, long from, long to, Random random) {
      long i = to - from;

      while (i-- != 0L) {
         long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
         boolean t = get(a, from + i);
         set(a, from + i, get(a, from + p));
         set(a, from + p, t);
      }

      return a;
   }

   public static boolean[][] shuffle(boolean[][] a, Random random) {
      long i = length(a);

      while (i-- != 0L) {
         long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
         boolean t = get(a, i);
         set(a, i, get(a, p));
         set(a, p, t);
      }

      return a;
   }

   public static short get(short[][] array, long index) {
      return array[segment(index)][displacement(index)];
   }

   public static void set(short[][] array, long index, short value) {
      array[segment(index)][displacement(index)] = value;
   }

   public static void swap(short[][] array, long first, long second) {
      short t = array[segment(first)][displacement(first)];
      array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
      array[segment(second)][displacement(second)] = t;
   }

   public static short[][] reverse(short[][] a) {
      long length = length(a);
      long i = length / 2L;

      while (i-- != 0L) {
         swap(a, i, length - i - 1L);
      }

      return a;
   }

   public static void add(short[][] array, long index, short incr) {
      array[segment(index)][displacement(index)] += incr;
   }

   public static void mul(short[][] array, long index, short factor) {
      array[segment(index)][displacement(index)] *= factor;
   }

   public static void incr(short[][] array, long index) {
      array[segment(index)][displacement(index)]++;
   }

   public static void decr(short[][] array, long index) {
      array[segment(index)][displacement(index)]--;
   }

   public static void assertBigArray(short[][] array) {
      int l = array.length;
      if (l != 0) {
         for (int i = 0; i < l - 1; i++) {
            if (array[i].length != 134217728) {
               throw new IllegalStateException("All segments except for the last one must be of length 2^" + Integer.toString(27));
            }
         }

         if (array[l - 1].length > 134217728) {
            throw new IllegalStateException("The last segment must be of length at most 2^" + Integer.toString(27));
         } else if (array[l - 1].length == 0 && l == 1) {
            throw new IllegalStateException("The last segment must be of nonzero length");
         }
      }
   }

   public static long length(short[][] array) {
      int length = array.length;
      return length == 0 ? 0L : start(length - 1) + (long)array[length - 1].length;
   }

   public static void copy(short[][] srcArray, long srcPos, short[][] destArray, long destPos, long length) {
      if (destPos <= srcPos) {
         int srcSegment = segment(srcPos);
         int destSegment = segment(destPos);
         int srcDispl = displacement(srcPos);
         int destDispl = displacement(destPos);

         while (length > 0L) {
            int l = (int)Math.min(length, (long)Math.min(srcArray[srcSegment].length - srcDispl, destArray[destSegment].length - destDispl));
            if (l == 0) {
               throw new ArrayIndexOutOfBoundsException();
            }

            System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
            if ((srcDispl += l) == 134217728) {
               srcDispl = 0;
               srcSegment++;
            }

            if ((destDispl += l) == 134217728) {
               destDispl = 0;
               destSegment++;
            }

            length -= (long)l;
         }
      } else {
         int srcSegment = segment(srcPos + length);
         int destSegment = segment(destPos + length);
         int srcDispl = displacement(srcPos + length);
         int destDispl = displacement(destPos + length);

         while (length > 0L) {
            if (srcDispl == 0) {
               srcDispl = 134217728;
               srcSegment--;
            }

            if (destDispl == 0) {
               destDispl = 134217728;
               destSegment--;
            }

            int lx = (int)Math.min(length, (long)Math.min(srcDispl, destDispl));
            if (lx == 0) {
               throw new ArrayIndexOutOfBoundsException();
            }

            System.arraycopy(srcArray[srcSegment], srcDispl - lx, destArray[destSegment], destDispl - lx, lx);
            srcDispl -= lx;
            destDispl -= lx;
            length -= (long)lx;
         }
      }
   }

   public static void copyFromBig(short[][] srcArray, long srcPos, short[] destArray, int destPos, int length) {
      int srcSegment = segment(srcPos);
      int srcDispl = displacement(srcPos);

      while (length > 0) {
         int l = Math.min(srcArray[srcSegment].length - srcDispl, length);
         if (l == 0) {
            throw new ArrayIndexOutOfBoundsException();
         }

         System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
         if ((srcDispl += l) == 134217728) {
            srcDispl = 0;
            srcSegment++;
         }

         destPos += l;
         length -= l;
      }
   }

   public static void copyToBig(short[] srcArray, int srcPos, short[][] destArray, long destPos, long length) {
      int destSegment = segment(destPos);
      int destDispl = displacement(destPos);

      while (length > 0L) {
         int l = (int)Math.min((long)(destArray[destSegment].length - destDispl), length);
         if (l == 0) {
            throw new ArrayIndexOutOfBoundsException();
         }

         System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
         if ((destDispl += l) == 134217728) {
            destDispl = 0;
            destSegment++;
         }

         srcPos += l;
         length -= (long)l;
      }
   }

   public static short[][] wrap(short[] array) {
      if (array.length == 0) {
         return ShortBigArrays.EMPTY_BIG_ARRAY;
      } else if (array.length <= 134217728) {
         return new short[][]{array};
      } else {
         short[][] bigArray = ShortBigArrays.newBigArray((long)array.length);

         for (int i = 0; i < bigArray.length; i++) {
            System.arraycopy(array, (int)start(i), bigArray[i], 0, bigArray[i].length);
         }

         return bigArray;
      }
   }

   public static short[][] ensureCapacity(short[][] array, long length) {
      return ensureCapacity(array, length, length(array));
   }

   public static short[][] forceCapacity(short[][] array, long length, long preserve) {
      ensureLength(length);
      int valid = array.length - (array.length != 0 && (array.length <= 0 || array[array.length - 1].length != 134217728) ? 1 : 0);
      int baseLength = (int)(length + 134217727L >>> 27);
      short[][] base = java.util.Arrays.copyOf(array, baseLength);
      int residual = (int)(length & 134217727L);
      if (residual != 0) {
         for (int i = valid; i < baseLength - 1; i++) {
            base[i] = new short[134217728];
         }

         base[baseLength - 1] = new short[residual];
      } else {
         for (int i = valid; i < baseLength; i++) {
            base[i] = new short[134217728];
         }
      }

      if (preserve - (long)valid * 134217728L > 0L) {
         copy(array, (long)valid * 134217728L, base, (long)valid * 134217728L, preserve - (long)valid * 134217728L);
      }

      return base;
   }

   public static short[][] ensureCapacity(short[][] array, long length, long preserve) {
      return length > length(array) ? forceCapacity(array, length, preserve) : array;
   }

   public static short[][] grow(short[][] array, long length) {
      long oldLength = length(array);
      return length > oldLength ? grow(array, length, oldLength) : array;
   }

   public static short[][] grow(short[][] array, long length, long preserve) {
      long oldLength = length(array);
      return length > oldLength ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1), length), preserve) : array;
   }

   public static short[][] trim(short[][] array, long length) {
      ensureLength(length);
      long oldLength = length(array);
      if (length >= oldLength) {
         return array;
      } else {
         int baseLength = (int)(length + 134217727L >>> 27);
         short[][] base = java.util.Arrays.copyOf(array, baseLength);
         int residual = (int)(length & 134217727L);
         if (residual != 0) {
            base[baseLength - 1] = ShortArrays.trim(base[baseLength - 1], residual);
         }

         return base;
      }
   }

   public static short[][] setLength(short[][] array, long length) {
      long oldLength = length(array);
      if (length == oldLength) {
         return array;
      } else {
         return length < oldLength ? trim(array, length) : ensureCapacity(array, length);
      }
   }

   public static short[][] copy(short[][] array, long offset, long length) {
      ensureOffsetLength(array, offset, length);
      short[][] a = ShortBigArrays.newBigArray(length);
      copy(array, offset, a, 0L, length);
      return a;
   }

   public static short[][] copy(short[][] array) {
      short[][] base = (short[][])array.clone();
      int i = base.length;

      while (i-- != 0) {
         base[i] = (short[])array[i].clone();
      }

      return base;
   }

   public static void fill(short[][] array, short value) {
      int i = array.length;

      while (i-- != 0) {
         java.util.Arrays.fill(array[i], value);
      }
   }

   public static void fill(short[][] array, long from, long to, short value) {
      long length = length(array);
      ensureFromTo(length, from, to);
      if (length != 0L) {
         int fromSegment = segment(from);
         int toSegment = segment(to);
         int fromDispl = displacement(from);
         int toDispl = displacement(to);
         if (fromSegment == toSegment) {
            java.util.Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
         } else {
            if (toDispl != 0) {
               java.util.Arrays.fill(array[toSegment], 0, toDispl, value);
            }

            while (--toSegment > fromSegment) {
               java.util.Arrays.fill(array[toSegment], value);
            }

            java.util.Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
         }
      }
   }

   public static boolean equals(short[][] a1, short[][] a2) {
      if (length(a1) != length(a2)) {
         return false;
      } else {
         int i = a1.length;

         while (i-- != 0) {
            short[] t = a1[i];
            short[] u = a2[i];
            int j = t.length;

            while (j-- != 0) {
               if (t[j] != u[j]) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   public static String toString(short[][] a) {
      if (a == null) {
         return "null";
      } else {
         long last = length(a) - 1L;
         if (last == -1L) {
            return "[]";
         } else {
            StringBuilder b = new StringBuilder();
            b.append('[');
            long i = 0L;

            while (true) {
               b.append(String.valueOf(get(a, i)));
               if (i == last) {
                  return b.append(']').toString();
               }

               b.append(", ");
               i++;
            }
         }
      }
   }

   public static void ensureFromTo(short[][] a, long from, long to) {
      ensureFromTo(length(a), from, to);
   }

   public static void ensureOffsetLength(short[][] a, long offset, long length) {
      ensureOffsetLength(length(a), offset, length);
   }

   public static void ensureSameLength(short[][] a, short[][] b) {
      if (length(a) != length(b)) {
         throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
      }
   }

   public static short[][] shuffle(short[][] a, long from, long to, Random random) {
      long i = to - from;

      while (i-- != 0L) {
         long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
         short t = get(a, from + i);
         set(a, from + i, get(a, from + p));
         set(a, from + p, t);
      }

      return a;
   }

   public static short[][] shuffle(short[][] a, Random random) {
      long i = length(a);

      while (i-- != 0L) {
         long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
         short t = get(a, i);
         set(a, i, get(a, p));
         set(a, p, t);
      }

      return a;
   }

   public static char get(char[][] array, long index) {
      return array[segment(index)][displacement(index)];
   }

   public static void set(char[][] array, long index, char value) {
      array[segment(index)][displacement(index)] = value;
   }

   public static void swap(char[][] array, long first, long second) {
      char t = array[segment(first)][displacement(first)];
      array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
      array[segment(second)][displacement(second)] = t;
   }

   public static char[][] reverse(char[][] a) {
      long length = length(a);
      long i = length / 2L;

      while (i-- != 0L) {
         swap(a, i, length - i - 1L);
      }

      return a;
   }

   public static void add(char[][] array, long index, char incr) {
      array[segment(index)][displacement(index)] += incr;
   }

   public static void mul(char[][] array, long index, char factor) {
      array[segment(index)][displacement(index)] *= factor;
   }

   public static void incr(char[][] array, long index) {
      array[segment(index)][displacement(index)]++;
   }

   public static void decr(char[][] array, long index) {
      array[segment(index)][displacement(index)]--;
   }

   public static void assertBigArray(char[][] array) {
      int l = array.length;
      if (l != 0) {
         for (int i = 0; i < l - 1; i++) {
            if (array[i].length != 134217728) {
               throw new IllegalStateException("All segments except for the last one must be of length 2^" + Integer.toString(27));
            }
         }

         if (array[l - 1].length > 134217728) {
            throw new IllegalStateException("The last segment must be of length at most 2^" + Integer.toString(27));
         } else if (array[l - 1].length == 0 && l == 1) {
            throw new IllegalStateException("The last segment must be of nonzero length");
         }
      }
   }

   public static long length(char[][] array) {
      int length = array.length;
      return length == 0 ? 0L : start(length - 1) + (long)array[length - 1].length;
   }

   public static void copy(char[][] srcArray, long srcPos, char[][] destArray, long destPos, long length) {
      if (destPos <= srcPos) {
         int srcSegment = segment(srcPos);
         int destSegment = segment(destPos);
         int srcDispl = displacement(srcPos);
         int destDispl = displacement(destPos);

         while (length > 0L) {
            int l = (int)Math.min(length, (long)Math.min(srcArray[srcSegment].length - srcDispl, destArray[destSegment].length - destDispl));
            if (l == 0) {
               throw new ArrayIndexOutOfBoundsException();
            }

            System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
            if ((srcDispl += l) == 134217728) {
               srcDispl = 0;
               srcSegment++;
            }

            if ((destDispl += l) == 134217728) {
               destDispl = 0;
               destSegment++;
            }

            length -= (long)l;
         }
      } else {
         int srcSegment = segment(srcPos + length);
         int destSegment = segment(destPos + length);
         int srcDispl = displacement(srcPos + length);
         int destDispl = displacement(destPos + length);

         while (length > 0L) {
            if (srcDispl == 0) {
               srcDispl = 134217728;
               srcSegment--;
            }

            if (destDispl == 0) {
               destDispl = 134217728;
               destSegment--;
            }

            int lx = (int)Math.min(length, (long)Math.min(srcDispl, destDispl));
            if (lx == 0) {
               throw new ArrayIndexOutOfBoundsException();
            }

            System.arraycopy(srcArray[srcSegment], srcDispl - lx, destArray[destSegment], destDispl - lx, lx);
            srcDispl -= lx;
            destDispl -= lx;
            length -= (long)lx;
         }
      }
   }

   public static void copyFromBig(char[][] srcArray, long srcPos, char[] destArray, int destPos, int length) {
      int srcSegment = segment(srcPos);
      int srcDispl = displacement(srcPos);

      while (length > 0) {
         int l = Math.min(srcArray[srcSegment].length - srcDispl, length);
         if (l == 0) {
            throw new ArrayIndexOutOfBoundsException();
         }

         System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
         if ((srcDispl += l) == 134217728) {
            srcDispl = 0;
            srcSegment++;
         }

         destPos += l;
         length -= l;
      }
   }

   public static void copyToBig(char[] srcArray, int srcPos, char[][] destArray, long destPos, long length) {
      int destSegment = segment(destPos);
      int destDispl = displacement(destPos);

      while (length > 0L) {
         int l = (int)Math.min((long)(destArray[destSegment].length - destDispl), length);
         if (l == 0) {
            throw new ArrayIndexOutOfBoundsException();
         }

         System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
         if ((destDispl += l) == 134217728) {
            destDispl = 0;
            destSegment++;
         }

         srcPos += l;
         length -= (long)l;
      }
   }

   public static char[][] wrap(char[] array) {
      if (array.length == 0) {
         return CharBigArrays.EMPTY_BIG_ARRAY;
      } else if (array.length <= 134217728) {
         return new char[][]{array};
      } else {
         char[][] bigArray = CharBigArrays.newBigArray((long)array.length);

         for (int i = 0; i < bigArray.length; i++) {
            System.arraycopy(array, (int)start(i), bigArray[i], 0, bigArray[i].length);
         }

         return bigArray;
      }
   }

   public static char[][] ensureCapacity(char[][] array, long length) {
      return ensureCapacity(array, length, length(array));
   }

   public static char[][] forceCapacity(char[][] array, long length, long preserve) {
      ensureLength(length);
      int valid = array.length - (array.length != 0 && (array.length <= 0 || array[array.length - 1].length != 134217728) ? 1 : 0);
      int baseLength = (int)(length + 134217727L >>> 27);
      char[][] base = java.util.Arrays.copyOf(array, baseLength);
      int residual = (int)(length & 134217727L);
      if (residual != 0) {
         for (int i = valid; i < baseLength - 1; i++) {
            base[i] = new char[134217728];
         }

         base[baseLength - 1] = new char[residual];
      } else {
         for (int i = valid; i < baseLength; i++) {
            base[i] = new char[134217728];
         }
      }

      if (preserve - (long)valid * 134217728L > 0L) {
         copy(array, (long)valid * 134217728L, base, (long)valid * 134217728L, preserve - (long)valid * 134217728L);
      }

      return base;
   }

   public static char[][] ensureCapacity(char[][] array, long length, long preserve) {
      return length > length(array) ? forceCapacity(array, length, preserve) : array;
   }

   public static char[][] grow(char[][] array, long length) {
      long oldLength = length(array);
      return length > oldLength ? grow(array, length, oldLength) : array;
   }

   public static char[][] grow(char[][] array, long length, long preserve) {
      long oldLength = length(array);
      return length > oldLength ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1), length), preserve) : array;
   }

   public static char[][] trim(char[][] array, long length) {
      ensureLength(length);
      long oldLength = length(array);
      if (length >= oldLength) {
         return array;
      } else {
         int baseLength = (int)(length + 134217727L >>> 27);
         char[][] base = java.util.Arrays.copyOf(array, baseLength);
         int residual = (int)(length & 134217727L);
         if (residual != 0) {
            base[baseLength - 1] = CharArrays.trim(base[baseLength - 1], residual);
         }

         return base;
      }
   }

   public static char[][] setLength(char[][] array, long length) {
      long oldLength = length(array);
      if (length == oldLength) {
         return array;
      } else {
         return length < oldLength ? trim(array, length) : ensureCapacity(array, length);
      }
   }

   public static char[][] copy(char[][] array, long offset, long length) {
      ensureOffsetLength(array, offset, length);
      char[][] a = CharBigArrays.newBigArray(length);
      copy(array, offset, a, 0L, length);
      return a;
   }

   public static char[][] copy(char[][] array) {
      char[][] base = (char[][])array.clone();
      int i = base.length;

      while (i-- != 0) {
         base[i] = (char[])array[i].clone();
      }

      return base;
   }

   public static void fill(char[][] array, char value) {
      int i = array.length;

      while (i-- != 0) {
         java.util.Arrays.fill(array[i], value);
      }
   }

   public static void fill(char[][] array, long from, long to, char value) {
      long length = length(array);
      ensureFromTo(length, from, to);
      if (length != 0L) {
         int fromSegment = segment(from);
         int toSegment = segment(to);
         int fromDispl = displacement(from);
         int toDispl = displacement(to);
         if (fromSegment == toSegment) {
            java.util.Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
         } else {
            if (toDispl != 0) {
               java.util.Arrays.fill(array[toSegment], 0, toDispl, value);
            }

            while (--toSegment > fromSegment) {
               java.util.Arrays.fill(array[toSegment], value);
            }

            java.util.Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
         }
      }
   }

   public static boolean equals(char[][] a1, char[][] a2) {
      if (length(a1) != length(a2)) {
         return false;
      } else {
         int i = a1.length;

         while (i-- != 0) {
            char[] t = a1[i];
            char[] u = a2[i];
            int j = t.length;

            while (j-- != 0) {
               if (t[j] != u[j]) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   public static String toString(char[][] a) {
      if (a == null) {
         return "null";
      } else {
         long last = length(a) - 1L;
         if (last == -1L) {
            return "[]";
         } else {
            StringBuilder b = new StringBuilder();
            b.append('[');
            long i = 0L;

            while (true) {
               b.append(String.valueOf(get(a, i)));
               if (i == last) {
                  return b.append(']').toString();
               }

               b.append(", ");
               i++;
            }
         }
      }
   }

   public static void ensureFromTo(char[][] a, long from, long to) {
      ensureFromTo(length(a), from, to);
   }

   public static void ensureOffsetLength(char[][] a, long offset, long length) {
      ensureOffsetLength(length(a), offset, length);
   }

   public static void ensureSameLength(char[][] a, char[][] b) {
      if (length(a) != length(b)) {
         throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
      }
   }

   public static char[][] shuffle(char[][] a, long from, long to, Random random) {
      long i = to - from;

      while (i-- != 0L) {
         long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
         char t = get(a, from + i);
         set(a, from + i, get(a, from + p));
         set(a, from + p, t);
      }

      return a;
   }

   public static char[][] shuffle(char[][] a, Random random) {
      long i = length(a);

      while (i-- != 0L) {
         long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
         char t = get(a, i);
         set(a, i, get(a, p));
         set(a, p, t);
      }

      return a;
   }

   public static float get(float[][] array, long index) {
      return array[segment(index)][displacement(index)];
   }

   public static void set(float[][] array, long index, float value) {
      array[segment(index)][displacement(index)] = value;
   }

   public static void swap(float[][] array, long first, long second) {
      float t = array[segment(first)][displacement(first)];
      array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
      array[segment(second)][displacement(second)] = t;
   }

   public static float[][] reverse(float[][] a) {
      long length = length(a);
      long i = length / 2L;

      while (i-- != 0L) {
         swap(a, i, length - i - 1L);
      }

      return a;
   }

   public static void add(float[][] array, long index, float incr) {
      array[segment(index)][displacement(index)] += incr;
   }

   public static void mul(float[][] array, long index, float factor) {
      array[segment(index)][displacement(index)] *= factor;
   }

   public static void incr(float[][] array, long index) {
      array[segment(index)][displacement(index)]++;
   }

   public static void decr(float[][] array, long index) {
      array[segment(index)][displacement(index)]--;
   }

   public static void assertBigArray(float[][] array) {
      int l = array.length;
      if (l != 0) {
         for (int i = 0; i < l - 1; i++) {
            if (array[i].length != 134217728) {
               throw new IllegalStateException("All segments except for the last one must be of length 2^" + Integer.toString(27));
            }
         }

         if (array[l - 1].length > 134217728) {
            throw new IllegalStateException("The last segment must be of length at most 2^" + Integer.toString(27));
         } else if (array[l - 1].length == 0 && l == 1) {
            throw new IllegalStateException("The last segment must be of nonzero length");
         }
      }
   }

   public static long length(float[][] array) {
      int length = array.length;
      return length == 0 ? 0L : start(length - 1) + (long)array[length - 1].length;
   }

   public static void copy(float[][] srcArray, long srcPos, float[][] destArray, long destPos, long length) {
      if (destPos <= srcPos) {
         int srcSegment = segment(srcPos);
         int destSegment = segment(destPos);
         int srcDispl = displacement(srcPos);
         int destDispl = displacement(destPos);

         while (length > 0L) {
            int l = (int)Math.min(length, (long)Math.min(srcArray[srcSegment].length - srcDispl, destArray[destSegment].length - destDispl));
            if (l == 0) {
               throw new ArrayIndexOutOfBoundsException();
            }

            System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
            if ((srcDispl += l) == 134217728) {
               srcDispl = 0;
               srcSegment++;
            }

            if ((destDispl += l) == 134217728) {
               destDispl = 0;
               destSegment++;
            }

            length -= (long)l;
         }
      } else {
         int srcSegment = segment(srcPos + length);
         int destSegment = segment(destPos + length);
         int srcDispl = displacement(srcPos + length);
         int destDispl = displacement(destPos + length);

         while (length > 0L) {
            if (srcDispl == 0) {
               srcDispl = 134217728;
               srcSegment--;
            }

            if (destDispl == 0) {
               destDispl = 134217728;
               destSegment--;
            }

            int lx = (int)Math.min(length, (long)Math.min(srcDispl, destDispl));
            if (lx == 0) {
               throw new ArrayIndexOutOfBoundsException();
            }

            System.arraycopy(srcArray[srcSegment], srcDispl - lx, destArray[destSegment], destDispl - lx, lx);
            srcDispl -= lx;
            destDispl -= lx;
            length -= (long)lx;
         }
      }
   }

   public static void copyFromBig(float[][] srcArray, long srcPos, float[] destArray, int destPos, int length) {
      int srcSegment = segment(srcPos);
      int srcDispl = displacement(srcPos);

      while (length > 0) {
         int l = Math.min(srcArray[srcSegment].length - srcDispl, length);
         if (l == 0) {
            throw new ArrayIndexOutOfBoundsException();
         }

         System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
         if ((srcDispl += l) == 134217728) {
            srcDispl = 0;
            srcSegment++;
         }

         destPos += l;
         length -= l;
      }
   }

   public static void copyToBig(float[] srcArray, int srcPos, float[][] destArray, long destPos, long length) {
      int destSegment = segment(destPos);
      int destDispl = displacement(destPos);

      while (length > 0L) {
         int l = (int)Math.min((long)(destArray[destSegment].length - destDispl), length);
         if (l == 0) {
            throw new ArrayIndexOutOfBoundsException();
         }

         System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
         if ((destDispl += l) == 134217728) {
            destDispl = 0;
            destSegment++;
         }

         srcPos += l;
         length -= (long)l;
      }
   }

   public static float[][] wrap(float[] array) {
      if (array.length == 0) {
         return FloatBigArrays.EMPTY_BIG_ARRAY;
      } else if (array.length <= 134217728) {
         return new float[][]{array};
      } else {
         float[][] bigArray = FloatBigArrays.newBigArray((long)array.length);

         for (int i = 0; i < bigArray.length; i++) {
            System.arraycopy(array, (int)start(i), bigArray[i], 0, bigArray[i].length);
         }

         return bigArray;
      }
   }

   public static float[][] ensureCapacity(float[][] array, long length) {
      return ensureCapacity(array, length, length(array));
   }

   public static float[][] forceCapacity(float[][] array, long length, long preserve) {
      ensureLength(length);
      int valid = array.length - (array.length != 0 && (array.length <= 0 || array[array.length - 1].length != 134217728) ? 1 : 0);
      int baseLength = (int)(length + 134217727L >>> 27);
      float[][] base = java.util.Arrays.copyOf(array, baseLength);
      int residual = (int)(length & 134217727L);
      if (residual != 0) {
         for (int i = valid; i < baseLength - 1; i++) {
            base[i] = new float[134217728];
         }

         base[baseLength - 1] = new float[residual];
      } else {
         for (int i = valid; i < baseLength; i++) {
            base[i] = new float[134217728];
         }
      }

      if (preserve - (long)valid * 134217728L > 0L) {
         copy(array, (long)valid * 134217728L, base, (long)valid * 134217728L, preserve - (long)valid * 134217728L);
      }

      return base;
   }

   public static float[][] ensureCapacity(float[][] array, long length, long preserve) {
      return length > length(array) ? forceCapacity(array, length, preserve) : array;
   }

   public static float[][] grow(float[][] array, long length) {
      long oldLength = length(array);
      return length > oldLength ? grow(array, length, oldLength) : array;
   }

   public static float[][] grow(float[][] array, long length, long preserve) {
      long oldLength = length(array);
      return length > oldLength ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1), length), preserve) : array;
   }

   public static float[][] trim(float[][] array, long length) {
      ensureLength(length);
      long oldLength = length(array);
      if (length >= oldLength) {
         return array;
      } else {
         int baseLength = (int)(length + 134217727L >>> 27);
         float[][] base = java.util.Arrays.copyOf(array, baseLength);
         int residual = (int)(length & 134217727L);
         if (residual != 0) {
            base[baseLength - 1] = FloatArrays.trim(base[baseLength - 1], residual);
         }

         return base;
      }
   }

   public static float[][] setLength(float[][] array, long length) {
      long oldLength = length(array);
      if (length == oldLength) {
         return array;
      } else {
         return length < oldLength ? trim(array, length) : ensureCapacity(array, length);
      }
   }

   public static float[][] copy(float[][] array, long offset, long length) {
      ensureOffsetLength(array, offset, length);
      float[][] a = FloatBigArrays.newBigArray(length);
      copy(array, offset, a, 0L, length);
      return a;
   }

   public static float[][] copy(float[][] array) {
      float[][] base = (float[][])array.clone();
      int i = base.length;

      while (i-- != 0) {
         base[i] = (float[])array[i].clone();
      }

      return base;
   }

   public static void fill(float[][] array, float value) {
      int i = array.length;

      while (i-- != 0) {
         java.util.Arrays.fill(array[i], value);
      }
   }

   public static void fill(float[][] array, long from, long to, float value) {
      long length = length(array);
      ensureFromTo(length, from, to);
      if (length != 0L) {
         int fromSegment = segment(from);
         int toSegment = segment(to);
         int fromDispl = displacement(from);
         int toDispl = displacement(to);
         if (fromSegment == toSegment) {
            java.util.Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
         } else {
            if (toDispl != 0) {
               java.util.Arrays.fill(array[toSegment], 0, toDispl, value);
            }

            while (--toSegment > fromSegment) {
               java.util.Arrays.fill(array[toSegment], value);
            }

            java.util.Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
         }
      }
   }

   public static boolean equals(float[][] a1, float[][] a2) {
      if (length(a1) != length(a2)) {
         return false;
      } else {
         int i = a1.length;

         while (i-- != 0) {
            float[] t = a1[i];
            float[] u = a2[i];
            int j = t.length;

            while (j-- != 0) {
               if (Float.floatToIntBits(t[j]) != Float.floatToIntBits(u[j])) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   public static String toString(float[][] a) {
      if (a == null) {
         return "null";
      } else {
         long last = length(a) - 1L;
         if (last == -1L) {
            return "[]";
         } else {
            StringBuilder b = new StringBuilder();
            b.append('[');
            long i = 0L;

            while (true) {
               b.append(String.valueOf(get(a, i)));
               if (i == last) {
                  return b.append(']').toString();
               }

               b.append(", ");
               i++;
            }
         }
      }
   }

   public static void ensureFromTo(float[][] a, long from, long to) {
      ensureFromTo(length(a), from, to);
   }

   public static void ensureOffsetLength(float[][] a, long offset, long length) {
      ensureOffsetLength(length(a), offset, length);
   }

   public static void ensureSameLength(float[][] a, float[][] b) {
      if (length(a) != length(b)) {
         throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
      }
   }

   public static float[][] shuffle(float[][] a, long from, long to, Random random) {
      long i = to - from;

      while (i-- != 0L) {
         long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
         float t = get(a, from + i);
         set(a, from + i, get(a, from + p));
         set(a, from + p, t);
      }

      return a;
   }

   public static float[][] shuffle(float[][] a, Random random) {
      long i = length(a);

      while (i-- != 0L) {
         long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
         float t = get(a, i);
         set(a, i, get(a, p));
         set(a, p, t);
      }

      return a;
   }

   public static <K> K get(K[][] array, long index) {
      return array[segment(index)][displacement(index)];
   }

   public static <K> void set(K[][] array, long index, K value) {
      array[segment(index)][displacement(index)] = value;
   }

   public static <K> void swap(K[][] array, long first, long second) {
      K t = array[segment(first)][displacement(first)];
      array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
      array[segment(second)][displacement(second)] = t;
   }

   public static <K> K[][] reverse(K[][] a) {
      long length = length(a);
      long i = length / 2L;

      while (i-- != 0L) {
         swap(a, i, length - i - 1L);
      }

      return a;
   }

   public static <K> void assertBigArray(K[][] array) {
      int l = array.length;
      if (l != 0) {
         for (int i = 0; i < l - 1; i++) {
            if (array[i].length != 134217728) {
               throw new IllegalStateException("All segments except for the last one must be of length 2^" + Integer.toString(27));
            }
         }

         if (array[l - 1].length > 134217728) {
            throw new IllegalStateException("The last segment must be of length at most 2^" + Integer.toString(27));
         } else if (array[l - 1].length == 0 && l == 1) {
            throw new IllegalStateException("The last segment must be of nonzero length");
         }
      }
   }

   public static <K> long length(K[][] array) {
      int length = array.length;
      return length == 0 ? 0L : start(length - 1) + (long)array[length - 1].length;
   }

   public static <K> void copy(K[][] srcArray, long srcPos, K[][] destArray, long destPos, long length) {
      if (destPos <= srcPos) {
         int srcSegment = segment(srcPos);
         int destSegment = segment(destPos);
         int srcDispl = displacement(srcPos);
         int destDispl = displacement(destPos);

         while (length > 0L) {
            int l = (int)Math.min(length, (long)Math.min(srcArray[srcSegment].length - srcDispl, destArray[destSegment].length - destDispl));
            if (l == 0) {
               throw new ArrayIndexOutOfBoundsException();
            }

            System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
            if ((srcDispl += l) == 134217728) {
               srcDispl = 0;
               srcSegment++;
            }

            if ((destDispl += l) == 134217728) {
               destDispl = 0;
               destSegment++;
            }

            length -= (long)l;
         }
      } else {
         int srcSegment = segment(srcPos + length);
         int destSegment = segment(destPos + length);
         int srcDispl = displacement(srcPos + length);
         int destDispl = displacement(destPos + length);

         while (length > 0L) {
            if (srcDispl == 0) {
               srcDispl = 134217728;
               srcSegment--;
            }

            if (destDispl == 0) {
               destDispl = 134217728;
               destSegment--;
            }

            int lx = (int)Math.min(length, (long)Math.min(srcDispl, destDispl));
            if (lx == 0) {
               throw new ArrayIndexOutOfBoundsException();
            }

            System.arraycopy(srcArray[srcSegment], srcDispl - lx, destArray[destSegment], destDispl - lx, lx);
            srcDispl -= lx;
            destDispl -= lx;
            length -= (long)lx;
         }
      }
   }

   public static <K> void copyFromBig(K[][] srcArray, long srcPos, K[] destArray, int destPos, int length) {
      int srcSegment = segment(srcPos);
      int srcDispl = displacement(srcPos);

      while (length > 0) {
         int l = Math.min(srcArray[srcSegment].length - srcDispl, length);
         if (l == 0) {
            throw new ArrayIndexOutOfBoundsException();
         }

         System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
         if ((srcDispl += l) == 134217728) {
            srcDispl = 0;
            srcSegment++;
         }

         destPos += l;
         length -= l;
      }
   }

   public static <K> void copyToBig(K[] srcArray, int srcPos, K[][] destArray, long destPos, long length) {
      int destSegment = segment(destPos);
      int destDispl = displacement(destPos);

      while (length > 0L) {
         int l = (int)Math.min((long)(destArray[destSegment].length - destDispl), length);
         if (l == 0) {
            throw new ArrayIndexOutOfBoundsException();
         }

         System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
         if ((destDispl += l) == 134217728) {
            destDispl = 0;
            destSegment++;
         }

         srcPos += l;
         length -= (long)l;
      }
   }

   public static <K> K[][] wrap(K[] array) {
      if (array.length == 0 && array.getClass() == Object[].class) {
         return (K[][])ObjectBigArrays.EMPTY_BIG_ARRAY;
      } else if (array.length <= 134217728) {
         K[][] bigArray = (K[][])Array.newInstance(array.getClass(), 1);
         bigArray[0] = array;
         return bigArray;
      } else {
         K[][] bigArray = (K[][])ObjectBigArrays.newBigArray(array.getClass(), (long)array.length);

         for (int i = 0; i < bigArray.length; i++) {
            System.arraycopy(array, (int)start(i), bigArray[i], 0, bigArray[i].length);
         }

         return bigArray;
      }
   }

   public static <K> K[][] ensureCapacity(K[][] array, long length) {
      return (K[][])ensureCapacity(array, length, length(array));
   }

   public static <K> K[][] forceCapacity(K[][] array, long length, long preserve) {
      ensureLength(length);
      int valid = array.length - (array.length != 0 && (array.length <= 0 || array[array.length - 1].length != 134217728) ? 1 : 0);
      int baseLength = (int)(length + 134217727L >>> 27);
      K[][] base = (K[][])java.util.Arrays.copyOf(array, baseLength);
      Class<?> componentType = array.getClass().getComponentType();
      int residual = (int)(length & 134217727L);
      if (residual != 0) {
         for (int i = valid; i < baseLength - 1; i++) {
            base[i] = (K[])Array.newInstance(componentType.getComponentType(), 134217728);
         }

         base[baseLength - 1] = (K[])Array.newInstance(componentType.getComponentType(), residual);
      } else {
         for (int i = valid; i < baseLength; i++) {
            base[i] = (K[])Array.newInstance(componentType.getComponentType(), 134217728);
         }
      }

      if (preserve - (long)valid * 134217728L > 0L) {
         copy(array, (long)valid * 134217728L, base, (long)valid * 134217728L, preserve - (long)valid * 134217728L);
      }

      return base;
   }

   public static <K> K[][] ensureCapacity(K[][] array, long length, long preserve) {
      return (K[][])(length > length(array) ? forceCapacity(array, length, preserve) : array);
   }

   public static <K> K[][] grow(K[][] array, long length) {
      long oldLength = length(array);
      return (K[][])(length > oldLength ? grow(array, length, oldLength) : array);
   }

   public static <K> K[][] grow(K[][] array, long length, long preserve) {
      long oldLength = length(array);
      return (K[][])(length > oldLength ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1), length), preserve) : array);
   }

   public static <K> K[][] trim(K[][] array, long length) {
      ensureLength(length);
      long oldLength = length(array);
      if (length >= oldLength) {
         return array;
      } else {
         int baseLength = (int)(length + 134217727L >>> 27);
         K[][] base = (K[][])java.util.Arrays.copyOf(array, baseLength);
         int residual = (int)(length & 134217727L);
         if (residual != 0) {
            base[baseLength - 1] = (K[])ObjectArrays.trim(base[baseLength - 1], residual);
         }

         return base;
      }
   }

   public static <K> K[][] setLength(K[][] array, long length) {
      long oldLength = length(array);
      if (length == oldLength) {
         return array;
      } else {
         return (K[][])(length < oldLength ? trim(array, length) : ensureCapacity(array, length));
      }
   }

   public static <K> K[][] copy(K[][] array, long offset, long length) {
      ensureOffsetLength(array, offset, length);
      K[][] a = (K[][])ObjectBigArrays.newBigArray(array, length);
      copy(array, offset, a, 0L, length);
      return a;
   }

   public static <K> K[][] copy(K[][] array) {
      K[][] base = (K[][])array.clone();
      int i = base.length;

      while (i-- != 0) {
         base[i] = (K[])array[i].clone();
      }

      return base;
   }

   public static <K> void fill(K[][] array, K value) {
      int i = array.length;

      while (i-- != 0) {
         java.util.Arrays.fill(array[i], value);
      }
   }

   public static <K> void fill(K[][] array, long from, long to, K value) {
      long length = length(array);
      ensureFromTo(length, from, to);
      if (length != 0L) {
         int fromSegment = segment(from);
         int toSegment = segment(to);
         int fromDispl = displacement(from);
         int toDispl = displacement(to);
         if (fromSegment == toSegment) {
            java.util.Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
         } else {
            if (toDispl != 0) {
               java.util.Arrays.fill(array[toSegment], 0, toDispl, value);
            }

            while (--toSegment > fromSegment) {
               java.util.Arrays.fill(array[toSegment], value);
            }

            java.util.Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
         }
      }
   }

   public static <K> boolean equals(K[][] a1, K[][] a2) {
      if (length(a1) != length(a2)) {
         return false;
      } else {
         int i = a1.length;

         while (i-- != 0) {
            K[] t = a1[i];
            K[] u = a2[i];
            int j = t.length;

            while (j-- != 0) {
               if (!Objects.equals(t[j], u[j])) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   public static <K> String toString(K[][] a) {
      if (a == null) {
         return "null";
      } else {
         long last = length(a) - 1L;
         if (last == -1L) {
            return "[]";
         } else {
            StringBuilder b = new StringBuilder();
            b.append('[');
            long i = 0L;

            while (true) {
               b.append(String.valueOf(get(a, i)));
               if (i == last) {
                  return b.append(']').toString();
               }

               b.append(", ");
               i++;
            }
         }
      }
   }

   public static <K> void ensureFromTo(K[][] a, long from, long to) {
      ensureFromTo(length(a), from, to);
   }

   public static <K> void ensureOffsetLength(K[][] a, long offset, long length) {
      ensureOffsetLength(length(a), offset, length);
   }

   public static <K> void ensureSameLength(K[][] a, K[][] b) {
      if (length(a) != length(b)) {
         throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
      }
   }

   public static <K> K[][] shuffle(K[][] a, long from, long to, Random random) {
      long i = to - from;

      while (i-- != 0L) {
         long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
         K t = get(a, from + i);
         set(a, from + i, get(a, from + p));
         set(a, from + p, t);
      }

      return a;
   }

   public static <K> K[][] shuffle(K[][] a, Random random) {
      long i = length(a);

      while (i-- != 0L) {
         long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
         K t = get(a, i);
         set(a, i, get(a, p));
         set(a, p, t);
      }

      return a;
   }

   public static void main(String[] arg) {
      int[][] a = IntBigArrays.newBigArray(1L << Integer.parseInt(arg[0]));
      int k = 10;

      while (k-- != 0) {
         long start = -System.currentTimeMillis();
         long x = 0L;
         long i = length(a);

         while (i-- != 0L) {
            x ^= i ^ (long)get(a, i);
         }

         if (x == 0L) {
            System.err.println();
         }

         System.out.println("Single loop: " + (start + System.currentTimeMillis()) + "ms");
         start = -System.currentTimeMillis();
         long y = 0L;
         int ix = a.length;

         while (ix-- != 0) {
            int[] t = a[ix];
            int d = t.length;

            while (d-- != 0) {
               y ^= (long)t[d] ^ index(ix, d);
            }
         }

         if (y == 0L) {
            System.err.println();
         }

         if (x != y) {
            throw new AssertionError();
         }

         System.out.println("Double loop: " + (start + System.currentTimeMillis()) + "ms");
         long z = 0L;
         i = length(a);
         int ixx = a.length;

         while (ixx-- != 0) {
            int[] t = a[ixx];
            int d = t.length;

            while (d-- != 0) {
               y ^= (long)t[d] ^ --i;
            }
         }

         if (z == 0L) {
            System.err.println();
         }

         if (x != z) {
            throw new AssertionError();
         }

         System.out.println("Double loop (with additional index): " + (start + System.currentTimeMillis()) + "ms");
      }
   }
}
