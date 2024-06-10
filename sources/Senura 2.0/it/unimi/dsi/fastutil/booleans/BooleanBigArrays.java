/*      */ package it.unimi.dsi.fastutil.booleans;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.BigArrays;
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import java.util.Random;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class BooleanBigArrays
/*      */ {
/*   62 */   public static final boolean[][] EMPTY_BIG_ARRAY = new boolean[0][];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   71 */   public static final boolean[][] DEFAULT_EMPTY_BIG_ARRAY = new boolean[0][];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static boolean get(boolean[][] array, long index) {
/*   85 */     return array[BigArrays.segment(index)][BigArrays.displacement(index)];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void set(boolean[][] array, long index, boolean value) {
/*  101 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void swap(boolean[][] array, long first, long second) {
/*  117 */     boolean t = array[BigArrays.segment(first)][BigArrays.displacement(first)];
/*  118 */     array[BigArrays.segment(first)][BigArrays.displacement(first)] = array[BigArrays.segment(second)][BigArrays.displacement(second)];
/*  119 */     array[BigArrays.segment(second)][BigArrays.displacement(second)] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static long length(boolean[][] array) {
/*  132 */     int length = array.length;
/*  133 */     return (length == 0) ? 0L : (BigArrays.start(length - 1) + (array[length - 1]).length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void copy(boolean[][] srcArray, long srcPos, boolean[][] destArray, long destPos, long length) {
/*  156 */     if (destPos <= srcPos) {
/*  157 */       int srcSegment = BigArrays.segment(srcPos);
/*  158 */       int destSegment = BigArrays.segment(destPos);
/*  159 */       int srcDispl = BigArrays.displacement(srcPos);
/*  160 */       int destDispl = BigArrays.displacement(destPos);
/*      */       
/*  162 */       while (length > 0L) {
/*  163 */         int l = (int)Math.min(length, 
/*  164 */             Math.min((srcArray[srcSegment]).length - srcDispl, (destArray[destSegment]).length - destDispl));
/*  165 */         System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
/*  166 */         if ((srcDispl += l) == 134217728) {
/*  167 */           srcDispl = 0;
/*  168 */           srcSegment++;
/*      */         } 
/*  170 */         if ((destDispl += l) == 134217728) {
/*  171 */           destDispl = 0;
/*  172 */           destSegment++;
/*      */         } 
/*  174 */         length -= l;
/*      */       } 
/*      */     } else {
/*  177 */       int srcSegment = BigArrays.segment(srcPos + length);
/*  178 */       int destSegment = BigArrays.segment(destPos + length);
/*  179 */       int srcDispl = BigArrays.displacement(srcPos + length);
/*  180 */       int destDispl = BigArrays.displacement(destPos + length);
/*      */       
/*  182 */       while (length > 0L) {
/*  183 */         if (srcDispl == 0) {
/*  184 */           srcDispl = 134217728;
/*  185 */           srcSegment--;
/*      */         } 
/*  187 */         if (destDispl == 0) {
/*  188 */           destDispl = 134217728;
/*  189 */           destSegment--;
/*      */         } 
/*  191 */         int l = (int)Math.min(length, Math.min(srcDispl, destDispl));
/*  192 */         System.arraycopy(srcArray[srcSegment], srcDispl - l, destArray[destSegment], destDispl - l, l);
/*  193 */         srcDispl -= l;
/*  194 */         destDispl -= l;
/*  195 */         length -= l;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void copyFromBig(boolean[][] srcArray, long srcPos, boolean[] destArray, int destPos, int length) {
/*  219 */     int srcSegment = BigArrays.segment(srcPos);
/*  220 */     int srcDispl = BigArrays.displacement(srcPos);
/*      */     
/*  222 */     while (length > 0) {
/*  223 */       int l = Math.min((srcArray[srcSegment]).length - srcDispl, length);
/*  224 */       System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
/*  225 */       if ((srcDispl += l) == 134217728) {
/*  226 */         srcDispl = 0;
/*  227 */         srcSegment++;
/*      */       } 
/*  229 */       destPos += l;
/*  230 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void copyToBig(boolean[] srcArray, int srcPos, boolean[][] destArray, long destPos, long length) {
/*  253 */     int destSegment = BigArrays.segment(destPos);
/*  254 */     int destDispl = BigArrays.displacement(destPos);
/*      */     
/*  256 */     while (length > 0L) {
/*  257 */       int l = (int)Math.min(((destArray[destSegment]).length - destDispl), length);
/*  258 */       System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
/*  259 */       if ((destDispl += l) == 134217728) {
/*  260 */         destDispl = 0;
/*  261 */         destSegment++;
/*      */       } 
/*  263 */       srcPos += l;
/*  264 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[][] newBigArray(long length) {
/*  275 */     if (length == 0L)
/*  276 */       return EMPTY_BIG_ARRAY; 
/*  277 */     BigArrays.ensureLength(length);
/*  278 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  279 */     boolean[][] base = new boolean[baseLength][];
/*  280 */     int residual = (int)(length & 0x7FFFFFFL);
/*  281 */     if (residual != 0) {
/*  282 */       for (int i = 0; i < baseLength - 1; i++)
/*  283 */         base[i] = new boolean[134217728]; 
/*  284 */       base[baseLength - 1] = new boolean[residual];
/*      */     } else {
/*  286 */       for (int i = 0; i < baseLength; i++)
/*  287 */         base[i] = new boolean[134217728]; 
/*  288 */     }  return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static boolean[][] wrap(boolean[] array) {
/*  305 */     if (array.length == 0)
/*  306 */       return EMPTY_BIG_ARRAY; 
/*  307 */     if (array.length <= 134217728)
/*  308 */       return new boolean[][] { array }; 
/*  309 */     boolean[][] bigArray = newBigArray(array.length);
/*  310 */     for (int i = 0; i < bigArray.length; i++)
/*  311 */       System.arraycopy(array, (int)BigArrays.start(i), bigArray[i], 0, (bigArray[i]).length); 
/*  312 */     return bigArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static boolean[][] ensureCapacity(boolean[][] array, long length) {
/*  338 */     return ensureCapacity(array, length, length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static boolean[][] forceCapacity(boolean[][] array, long length, long preserve) {
/*  363 */     BigArrays.ensureLength(length);
/*      */     
/*  365 */     int valid = array.length - ((array.length == 0 || (array.length > 0 && (array[array.length - 1]).length == 134217728)) ? 0 : 1);
/*  366 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  367 */     boolean[][] base = Arrays.<boolean[]>copyOf(array, baseLength);
/*  368 */     int residual = (int)(length & 0x7FFFFFFL);
/*  369 */     if (residual != 0) {
/*  370 */       for (int i = valid; i < baseLength - 1; i++)
/*  371 */         base[i] = new boolean[134217728]; 
/*  372 */       base[baseLength - 1] = new boolean[residual];
/*      */     } else {
/*  374 */       for (int i = valid; i < baseLength; i++)
/*  375 */         base[i] = new boolean[134217728]; 
/*  376 */     }  if (preserve - valid * 134217728L > 0L) {
/*  377 */       copy(array, valid * 134217728L, base, valid * 134217728L, preserve - valid * 134217728L);
/*      */     }
/*  379 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static boolean[][] ensureCapacity(boolean[][] array, long length, long preserve) {
/*  405 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static boolean[][] grow(boolean[][] array, long length) {
/*  434 */     long oldLength = length(array);
/*  435 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static boolean[][] grow(boolean[][] array, long length, long preserve) {
/*  467 */     long oldLength = length(array);
/*  468 */     return (length > oldLength) ? 
/*  469 */       ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : 
/*  470 */       array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static boolean[][] trim(boolean[][] array, long length) {
/*  492 */     BigArrays.ensureLength(length);
/*  493 */     long oldLength = length(array);
/*  494 */     if (length >= oldLength)
/*  495 */       return array; 
/*  496 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  497 */     boolean[][] base = Arrays.<boolean[]>copyOf(array, baseLength);
/*  498 */     int residual = (int)(length & 0x7FFFFFFL);
/*  499 */     if (residual != 0)
/*  500 */       base[baseLength - 1] = BooleanArrays.trim(base[baseLength - 1], residual); 
/*  501 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static boolean[][] setLength(boolean[][] array, long length) {
/*  526 */     long oldLength = length(array);
/*  527 */     if (length == oldLength)
/*  528 */       return array; 
/*  529 */     if (length < oldLength)
/*  530 */       return trim(array, length); 
/*  531 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static boolean[][] copy(boolean[][] array, long offset, long length) {
/*  549 */     ensureOffsetLength(array, offset, length);
/*  550 */     boolean[][] a = newBigArray(length);
/*  551 */     copy(array, offset, a, 0L, length);
/*  552 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static boolean[][] copy(boolean[][] array) {
/*  565 */     boolean[][] base = (boolean[][])array.clone();
/*  566 */     for (int i = base.length; i-- != 0;)
/*  567 */       base[i] = (boolean[])array[i].clone(); 
/*  568 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void fill(boolean[][] array, boolean value) {
/*  586 */     for (int i = array.length; i-- != 0;) {
/*  587 */       Arrays.fill(array[i], value);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void fill(boolean[][] array, long from, long to, boolean value) {
/*  611 */     long length = length(array);
/*  612 */     BigArrays.ensureFromTo(length, from, to);
/*  613 */     if (length == 0L)
/*      */       return; 
/*  615 */     int fromSegment = BigArrays.segment(from);
/*  616 */     int toSegment = BigArrays.segment(to);
/*  617 */     int fromDispl = BigArrays.displacement(from);
/*  618 */     int toDispl = BigArrays.displacement(to);
/*  619 */     if (fromSegment == toSegment) {
/*  620 */       Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
/*      */       return;
/*      */     } 
/*  623 */     if (toDispl != 0)
/*  624 */       Arrays.fill(array[toSegment], 0, toDispl, value); 
/*  625 */     while (--toSegment > fromSegment)
/*  626 */       Arrays.fill(array[toSegment], value); 
/*  627 */     Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static boolean equals(boolean[][] a1, boolean[][] a2) {
/*  647 */     if (length(a1) != length(a2))
/*  648 */       return false; 
/*  649 */     int i = a1.length;
/*      */     
/*  651 */     while (i-- != 0) {
/*  652 */       boolean[] t = a1[i];
/*  653 */       boolean[] u = a2[i];
/*  654 */       int j = t.length;
/*  655 */       while (j-- != 0) {
/*  656 */         if (t[j] != u[j])
/*  657 */           return false; 
/*      */       } 
/*  659 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static String toString(boolean[][] a) {
/*  678 */     if (a == null)
/*  679 */       return "null"; 
/*  680 */     long last = length(a) - 1L;
/*  681 */     if (last == -1L)
/*  682 */       return "[]"; 
/*  683 */     StringBuilder b = new StringBuilder();
/*  684 */     b.append('['); long i;
/*  685 */     for (i = 0L;; i++) {
/*  686 */       b.append(String.valueOf(BigArrays.get(a, i)));
/*  687 */       if (i == last)
/*  688 */         return b.append(']').toString(); 
/*  689 */       b.append(", ");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void ensureFromTo(boolean[][] a, long from, long to) {
/*  715 */     BigArrays.ensureFromTo(length(a), from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void ensureOffsetLength(boolean[][] a, long offset, long length) {
/*  739 */     BigArrays.ensureOffsetLength(length(a), offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void ensureSameLength(boolean[][] a, boolean[][] b) {
/*  755 */     if (length(a) != length(b))
/*  756 */       throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b)); 
/*      */   }
/*      */   private static final class BigArrayHashStrategy implements Hash.Strategy<boolean[][]>, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private BigArrayHashStrategy() {}
/*      */     
/*      */     public int hashCode(boolean[][] o) {
/*  763 */       return Arrays.deepHashCode((Object[])o);
/*      */     }
/*      */     
/*      */     public boolean equals(boolean[][] a, boolean[][] b) {
/*  767 */       return BooleanBigArrays.equals(a, b);
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  779 */   public static final Hash.Strategy HASH_STRATEGY = new BigArrayHashStrategy(); private static final int SMALL = 7;
/*      */   private static final int MEDIUM = 40;
/*      */   
/*      */   private static void vecSwap(boolean[][] x, long a, long b, long n) {
/*  783 */     for (int i = 0; i < n; i++, a++, b++)
/*  784 */       swap(x, a, b); 
/*      */   }
/*      */   private static long med3(boolean[][] x, long a, long b, long c, BooleanComparator comp) {
/*  787 */     int ab = comp.compare(BigArrays.get(x, a), BigArrays.get(x, b));
/*  788 */     int ac = comp.compare(BigArrays.get(x, a), BigArrays.get(x, c));
/*  789 */     int bc = comp.compare(BigArrays.get(x, b), BigArrays.get(x, c));
/*  790 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void selectionSort(boolean[][] a, long from, long to, BooleanComparator comp) {
/*      */     long i;
/*  794 */     for (i = from; i < to - 1L; i++) {
/*  795 */       long m = i; long j;
/*  796 */       for (j = i + 1L; j < to; j++) {
/*  797 */         if (comp.compare(BigArrays.get(a, j), BigArrays.get(a, m)) < 0)
/*  798 */           m = j; 
/*  799 */       }  if (m != i) {
/*  800 */         swap(a, i, m);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void quickSort(boolean[][] x, long from, long to, BooleanComparator comp) {
/*  822 */     long len = to - from;
/*      */     
/*  824 */     if (len < 7L) {
/*  825 */       selectionSort(x, from, to, comp);
/*      */       
/*      */       return;
/*      */     } 
/*  829 */     long m = from + len / 2L;
/*  830 */     if (len > 7L) {
/*  831 */       long l = from;
/*  832 */       long n = to - 1L;
/*  833 */       if (len > 40L) {
/*  834 */         long s = len / 8L;
/*  835 */         l = med3(x, l, l + s, l + 2L * s, comp);
/*  836 */         m = med3(x, m - s, m, m + s, comp);
/*  837 */         n = med3(x, n - 2L * s, n - s, n, comp);
/*      */       } 
/*  839 */       m = med3(x, l, m, n, comp);
/*      */     } 
/*  841 */     boolean v = BigArrays.get(x, m);
/*      */     
/*  843 */     long a = from, b = a, c = to - 1L, d = c;
/*      */     
/*      */     int comparison;
/*  846 */     while (b <= c && (comparison = comp.compare(BigArrays.get(x, b), v)) <= 0) {
/*  847 */       if (comparison == 0)
/*  848 */         swap(x, a++, b); 
/*  849 */       b++;
/*      */     } 
/*  851 */     while (c >= b && (comparison = comp.compare(BigArrays.get(x, c), v)) >= 0) {
/*  852 */       if (comparison == 0)
/*  853 */         swap(x, c, d--); 
/*  854 */       c--;
/*      */     } 
/*  856 */     if (b > c) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  861 */       long n = to;
/*  862 */       long s = Math.min(a - from, b - a);
/*  863 */       vecSwap(x, from, b - s, s);
/*  864 */       s = Math.min(d - c, n - d - 1L);
/*  865 */       vecSwap(x, b, n - s, s);
/*      */       
/*  867 */       if ((s = b - a) > 1L)
/*  868 */         quickSort(x, from, from + s, comp); 
/*  869 */       if ((s = d - c) > 1L)
/*  870 */         quickSort(x, n - s, n, comp); 
/*      */       return;
/*      */     } 
/*      */     swap(x, b++, c--); } private static long med3(boolean[][] x, long a, long b, long c) {
/*  874 */     int ab = Boolean.compare(BigArrays.get(x, a), BigArrays.get(x, b));
/*  875 */     int ac = Boolean.compare(BigArrays.get(x, a), BigArrays.get(x, c));
/*  876 */     int bc = Boolean.compare(BigArrays.get(x, b), BigArrays.get(x, c));
/*  877 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void selectionSort(boolean[][] a, long from, long to) {
/*      */     long i;
/*  881 */     for (i = from; i < to - 1L; i++) {
/*  882 */       long m = i; long j;
/*  883 */       for (j = i + 1L; j < to; j++) {
/*  884 */         if (!BigArrays.get(a, j) && BigArrays.get(a, m))
/*  885 */           m = j; 
/*  886 */       }  if (m != i) {
/*  887 */         swap(a, i, m);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void quickSort(boolean[][] x, BooleanComparator comp) {
/*  906 */     quickSort(x, 0L, length(x), comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void quickSort(boolean[][] x, long from, long to) {
/*  926 */     long len = to - from;
/*      */     
/*  928 */     if (len < 7L) {
/*  929 */       selectionSort(x, from, to);
/*      */       
/*      */       return;
/*      */     } 
/*  933 */     long m = from + len / 2L;
/*  934 */     if (len > 7L) {
/*  935 */       long l = from;
/*  936 */       long n = to - 1L;
/*  937 */       if (len > 40L) {
/*  938 */         long s = len / 8L;
/*  939 */         l = med3(x, l, l + s, l + 2L * s);
/*  940 */         m = med3(x, m - s, m, m + s);
/*  941 */         n = med3(x, n - 2L * s, n - s, n);
/*      */       } 
/*  943 */       m = med3(x, l, m, n);
/*      */     } 
/*  945 */     boolean v = BigArrays.get(x, m);
/*      */     
/*  947 */     long a = from, b = a, c = to - 1L, d = c;
/*      */     
/*      */     int comparison;
/*  950 */     while (b <= c && (comparison = Boolean.compare(BigArrays.get(x, b), v)) <= 0) {
/*  951 */       if (comparison == 0)
/*  952 */         swap(x, a++, b); 
/*  953 */       b++;
/*      */     } 
/*  955 */     while (c >= b && (comparison = Boolean.compare(BigArrays.get(x, c), v)) >= 0) {
/*  956 */       if (comparison == 0)
/*  957 */         swap(x, c, d--); 
/*  958 */       c--;
/*      */     } 
/*  960 */     if (b > c) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  965 */       long n = to;
/*  966 */       long s = Math.min(a - from, b - a);
/*  967 */       vecSwap(x, from, b - s, s);
/*  968 */       s = Math.min(d - c, n - d - 1L);
/*  969 */       vecSwap(x, b, n - s, s);
/*      */       
/*  971 */       if ((s = b - a) > 1L)
/*  972 */         quickSort(x, from, from + s); 
/*  973 */       if ((s = d - c) > 1L) {
/*  974 */         quickSort(x, n - s, n);
/*      */       }
/*      */       return;
/*      */     } 
/*      */     swap(x, b++, c--);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void quickSort(boolean[][] x) {
/*  989 */     quickSort(x, 0L, length(x));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[][] shuffle(boolean[][] a, long from, long to, Random random) {
/* 1006 */     for (long i = to - from; i-- != 0L; ) {
/* 1007 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 1008 */       boolean t = BigArrays.get(a, from + i);
/* 1009 */       BigArrays.set(a, from + i, BigArrays.get(a, from + p));
/* 1010 */       BigArrays.set(a, from + p, t);
/*      */     } 
/* 1012 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[][] shuffle(boolean[][] a, Random random) {
/* 1025 */     for (long i = length(a); i-- != 0L; ) {
/* 1026 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 1027 */       boolean t = BigArrays.get(a, i);
/* 1028 */       BigArrays.set(a, i, BigArrays.get(a, p));
/* 1029 */       BigArrays.set(a, p, t);
/*      */     } 
/* 1031 */     return a;
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\booleans\BooleanBigArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */