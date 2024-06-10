/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.BigArrays;
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteBigArrays;
/*      */ import it.unimi.dsi.fastutil.longs.LongBigArrays;
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
/*      */ public final class DoubleBigArrays
/*      */ {
/*   63 */   public static final double[][] EMPTY_BIG_ARRAY = new double[0][];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   72 */   public static final double[][] DEFAULT_EMPTY_BIG_ARRAY = new double[0][];
/*      */ 
/*      */ 
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
/*      */   public static double get(double[][] array, long index) {
/*   86 */     return array[BigArrays.segment(index)][BigArrays.displacement(index)];
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
/*      */   public static void set(double[][] array, long index, double value) {
/*  102 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = value;
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
/*      */   public static void swap(double[][] array, long first, long second) {
/*  118 */     double t = array[BigArrays.segment(first)][BigArrays.displacement(first)];
/*  119 */     array[BigArrays.segment(first)][BigArrays.displacement(first)] = array[BigArrays.segment(second)][BigArrays.displacement(second)];
/*  120 */     array[BigArrays.segment(second)][BigArrays.displacement(second)] = t;
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
/*      */   public static void add(double[][] array, long index, double incr) {
/*  137 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = array[BigArrays.segment(index)][BigArrays.displacement(index)] + incr;
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
/*      */   public static void mul(double[][] array, long index, double factor) {
/*  154 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = array[BigArrays.segment(index)][BigArrays.displacement(index)] * factor;
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
/*      */   @Deprecated
/*      */   public static void incr(double[][] array, long index) {
/*  168 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = array[BigArrays.segment(index)][BigArrays.displacement(index)] + 1.0D;
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
/*      */   @Deprecated
/*      */   public static void decr(double[][] array, long index) {
/*  182 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = array[BigArrays.segment(index)][BigArrays.displacement(index)] - 1.0D;
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
/*      */   public static long length(double[][] array) {
/*  195 */     int length = array.length;
/*  196 */     return (length == 0) ? 0L : (BigArrays.start(length - 1) + (array[length - 1]).length);
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
/*      */   public static void copy(double[][] srcArray, long srcPos, double[][] destArray, long destPos, long length) {
/*  219 */     if (destPos <= srcPos) {
/*  220 */       int srcSegment = BigArrays.segment(srcPos);
/*  221 */       int destSegment = BigArrays.segment(destPos);
/*  222 */       int srcDispl = BigArrays.displacement(srcPos);
/*  223 */       int destDispl = BigArrays.displacement(destPos);
/*      */       
/*  225 */       while (length > 0L) {
/*  226 */         int l = (int)Math.min(length, 
/*  227 */             Math.min((srcArray[srcSegment]).length - srcDispl, (destArray[destSegment]).length - destDispl));
/*  228 */         System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
/*  229 */         if ((srcDispl += l) == 134217728) {
/*  230 */           srcDispl = 0;
/*  231 */           srcSegment++;
/*      */         } 
/*  233 */         if ((destDispl += l) == 134217728) {
/*  234 */           destDispl = 0;
/*  235 */           destSegment++;
/*      */         } 
/*  237 */         length -= l;
/*      */       } 
/*      */     } else {
/*  240 */       int srcSegment = BigArrays.segment(srcPos + length);
/*  241 */       int destSegment = BigArrays.segment(destPos + length);
/*  242 */       int srcDispl = BigArrays.displacement(srcPos + length);
/*  243 */       int destDispl = BigArrays.displacement(destPos + length);
/*      */       
/*  245 */       while (length > 0L) {
/*  246 */         if (srcDispl == 0) {
/*  247 */           srcDispl = 134217728;
/*  248 */           srcSegment--;
/*      */         } 
/*  250 */         if (destDispl == 0) {
/*  251 */           destDispl = 134217728;
/*  252 */           destSegment--;
/*      */         } 
/*  254 */         int l = (int)Math.min(length, Math.min(srcDispl, destDispl));
/*  255 */         System.arraycopy(srcArray[srcSegment], srcDispl - l, destArray[destSegment], destDispl - l, l);
/*  256 */         srcDispl -= l;
/*  257 */         destDispl -= l;
/*  258 */         length -= l;
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
/*      */   public static void copyFromBig(double[][] srcArray, long srcPos, double[] destArray, int destPos, int length) {
/*  282 */     int srcSegment = BigArrays.segment(srcPos);
/*  283 */     int srcDispl = BigArrays.displacement(srcPos);
/*      */     
/*  285 */     while (length > 0) {
/*  286 */       int l = Math.min((srcArray[srcSegment]).length - srcDispl, length);
/*  287 */       System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
/*  288 */       if ((srcDispl += l) == 134217728) {
/*  289 */         srcDispl = 0;
/*  290 */         srcSegment++;
/*      */       } 
/*  292 */       destPos += l;
/*  293 */       length -= l;
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
/*      */   public static void copyToBig(double[] srcArray, int srcPos, double[][] destArray, long destPos, long length) {
/*  316 */     int destSegment = BigArrays.segment(destPos);
/*  317 */     int destDispl = BigArrays.displacement(destPos);
/*      */     
/*  319 */     while (length > 0L) {
/*  320 */       int l = (int)Math.min(((destArray[destSegment]).length - destDispl), length);
/*  321 */       System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
/*  322 */       if ((destDispl += l) == 134217728) {
/*  323 */         destDispl = 0;
/*  324 */         destSegment++;
/*      */       } 
/*  326 */       srcPos += l;
/*  327 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] newBigArray(long length) {
/*  338 */     if (length == 0L)
/*  339 */       return EMPTY_BIG_ARRAY; 
/*  340 */     BigArrays.ensureLength(length);
/*  341 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  342 */     double[][] base = new double[baseLength][];
/*  343 */     int residual = (int)(length & 0x7FFFFFFL);
/*  344 */     if (residual != 0) {
/*  345 */       for (int i = 0; i < baseLength - 1; i++)
/*  346 */         base[i] = new double[134217728]; 
/*  347 */       base[baseLength - 1] = new double[residual];
/*      */     } else {
/*  349 */       for (int i = 0; i < baseLength; i++)
/*  350 */         base[i] = new double[134217728]; 
/*  351 */     }  return base;
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
/*      */   public static double[][] wrap(double[] array) {
/*  368 */     if (array.length == 0)
/*  369 */       return EMPTY_BIG_ARRAY; 
/*  370 */     if (array.length <= 134217728)
/*  371 */       return new double[][] { array }; 
/*  372 */     double[][] bigArray = newBigArray(array.length);
/*  373 */     for (int i = 0; i < bigArray.length; i++)
/*  374 */       System.arraycopy(array, (int)BigArrays.start(i), bigArray[i], 0, (bigArray[i]).length); 
/*  375 */     return bigArray;
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
/*      */   public static double[][] ensureCapacity(double[][] array, long length) {
/*  401 */     return ensureCapacity(array, length, length(array));
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
/*      */   public static double[][] forceCapacity(double[][] array, long length, long preserve) {
/*  426 */     BigArrays.ensureLength(length);
/*      */     
/*  428 */     int valid = array.length - ((array.length == 0 || (array.length > 0 && (array[array.length - 1]).length == 134217728)) ? 0 : 1);
/*  429 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  430 */     double[][] base = Arrays.<double[]>copyOf(array, baseLength);
/*  431 */     int residual = (int)(length & 0x7FFFFFFL);
/*  432 */     if (residual != 0) {
/*  433 */       for (int i = valid; i < baseLength - 1; i++)
/*  434 */         base[i] = new double[134217728]; 
/*  435 */       base[baseLength - 1] = new double[residual];
/*      */     } else {
/*  437 */       for (int i = valid; i < baseLength; i++)
/*  438 */         base[i] = new double[134217728]; 
/*  439 */     }  if (preserve - valid * 134217728L > 0L) {
/*  440 */       copy(array, valid * 134217728L, base, valid * 134217728L, preserve - valid * 134217728L);
/*      */     }
/*  442 */     return base;
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
/*      */   public static double[][] ensureCapacity(double[][] array, long length, long preserve) {
/*  468 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
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
/*      */   public static double[][] grow(double[][] array, long length) {
/*  497 */     long oldLength = length(array);
/*  498 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
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
/*      */   public static double[][] grow(double[][] array, long length, long preserve) {
/*  530 */     long oldLength = length(array);
/*  531 */     return (length > oldLength) ? 
/*  532 */       ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : 
/*  533 */       array;
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
/*      */   public static double[][] trim(double[][] array, long length) {
/*  555 */     BigArrays.ensureLength(length);
/*  556 */     long oldLength = length(array);
/*  557 */     if (length >= oldLength)
/*  558 */       return array; 
/*  559 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  560 */     double[][] base = Arrays.<double[]>copyOf(array, baseLength);
/*  561 */     int residual = (int)(length & 0x7FFFFFFL);
/*  562 */     if (residual != 0)
/*  563 */       base[baseLength - 1] = DoubleArrays.trim(base[baseLength - 1], residual); 
/*  564 */     return base;
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
/*      */   public static double[][] setLength(double[][] array, long length) {
/*  589 */     long oldLength = length(array);
/*  590 */     if (length == oldLength)
/*  591 */       return array; 
/*  592 */     if (length < oldLength)
/*  593 */       return trim(array, length); 
/*  594 */     return ensureCapacity(array, length);
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
/*      */   public static double[][] copy(double[][] array, long offset, long length) {
/*  612 */     ensureOffsetLength(array, offset, length);
/*  613 */     double[][] a = newBigArray(length);
/*  614 */     copy(array, offset, a, 0L, length);
/*  615 */     return a;
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
/*      */   public static double[][] copy(double[][] array) {
/*  628 */     double[][] base = (double[][])array.clone();
/*  629 */     for (int i = base.length; i-- != 0;)
/*  630 */       base[i] = (double[])array[i].clone(); 
/*  631 */     return base;
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
/*      */   public static void fill(double[][] array, double value) {
/*  649 */     for (int i = array.length; i-- != 0;) {
/*  650 */       Arrays.fill(array[i], value);
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
/*      */   public static void fill(double[][] array, long from, long to, double value) {
/*  674 */     long length = length(array);
/*  675 */     BigArrays.ensureFromTo(length, from, to);
/*  676 */     if (length == 0L)
/*      */       return; 
/*  678 */     int fromSegment = BigArrays.segment(from);
/*  679 */     int toSegment = BigArrays.segment(to);
/*  680 */     int fromDispl = BigArrays.displacement(from);
/*  681 */     int toDispl = BigArrays.displacement(to);
/*  682 */     if (fromSegment == toSegment) {
/*  683 */       Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
/*      */       return;
/*      */     } 
/*  686 */     if (toDispl != 0)
/*  687 */       Arrays.fill(array[toSegment], 0, toDispl, value); 
/*  688 */     while (--toSegment > fromSegment)
/*  689 */       Arrays.fill(array[toSegment], value); 
/*  690 */     Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
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
/*      */   public static boolean equals(double[][] a1, double[][] a2) {
/*  710 */     if (length(a1) != length(a2))
/*  711 */       return false; 
/*  712 */     int i = a1.length;
/*      */     
/*  714 */     while (i-- != 0) {
/*  715 */       double[] t = a1[i];
/*  716 */       double[] u = a2[i];
/*  717 */       int j = t.length;
/*  718 */       while (j-- != 0) {
/*  719 */         if (Double.doubleToLongBits(t[j]) != Double.doubleToLongBits(u[j]))
/*  720 */           return false; 
/*      */       } 
/*  722 */     }  return true;
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
/*      */   public static String toString(double[][] a) {
/*  741 */     if (a == null)
/*  742 */       return "null"; 
/*  743 */     long last = length(a) - 1L;
/*  744 */     if (last == -1L)
/*  745 */       return "[]"; 
/*  746 */     StringBuilder b = new StringBuilder();
/*  747 */     b.append('['); long i;
/*  748 */     for (i = 0L;; i++) {
/*  749 */       b.append(String.valueOf(BigArrays.get(a, i)));
/*  750 */       if (i == last)
/*  751 */         return b.append(']').toString(); 
/*  752 */       b.append(", ");
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
/*      */   public static void ensureFromTo(double[][] a, long from, long to) {
/*  778 */     BigArrays.ensureFromTo(length(a), from, to);
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
/*      */   public static void ensureOffsetLength(double[][] a, long offset, long length) {
/*  802 */     BigArrays.ensureOffsetLength(length(a), offset, length);
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
/*      */   public static void ensureSameLength(double[][] a, double[][] b) {
/*  818 */     if (length(a) != length(b))
/*  819 */       throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b)); 
/*      */   }
/*      */   private static final class BigArrayHashStrategy implements Hash.Strategy<double[][]>, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private BigArrayHashStrategy() {}
/*      */     
/*      */     public int hashCode(double[][] o) {
/*  826 */       return Arrays.deepHashCode((Object[])o);
/*      */     }
/*      */     
/*      */     public boolean equals(double[][] a, double[][] b) {
/*  830 */       return DoubleBigArrays.equals(a, b);
/*      */     } }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int SMALL = 7;
/*      */ 
/*      */   
/*      */   private static final int MEDIUM = 40;
/*      */   
/*      */   private static final int DIGIT_BITS = 8;
/*      */   
/*  842 */   public static final Hash.Strategy HASH_STRATEGY = new BigArrayHashStrategy(); private static final int DIGIT_MASK = 255; private static final int DIGITS_PER_ELEMENT = 8;
/*      */   private static final int RADIXSORT_NO_REC = 1024;
/*      */   
/*      */   private static void vecSwap(double[][] x, long a, long b, long n) {
/*  846 */     for (int i = 0; i < n; i++, a++, b++)
/*  847 */       swap(x, a, b); 
/*      */   }
/*      */   private static long med3(double[][] x, long a, long b, long c, DoubleComparator comp) {
/*  850 */     int ab = comp.compare(BigArrays.get(x, a), BigArrays.get(x, b));
/*  851 */     int ac = comp.compare(BigArrays.get(x, a), BigArrays.get(x, c));
/*  852 */     int bc = comp.compare(BigArrays.get(x, b), BigArrays.get(x, c));
/*  853 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   } private static void selectionSort(double[][] a, long from, long to, DoubleComparator comp) {
/*      */     long i;
/*  856 */     for (i = from; i < to - 1L; i++) {
/*  857 */       long m = i; long j;
/*  858 */       for (j = i + 1L; j < to; j++) {
/*  859 */         if (comp.compare(BigArrays.get(a, j), BigArrays.get(a, m)) < 0)
/*  860 */           m = j; 
/*  861 */       }  if (m != i) {
/*  862 */         swap(a, i, m);
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
/*      */   public static void quickSort(double[][] x, long from, long to, DoubleComparator comp)
/*      */   {
/*  884 */     long len = to - from;
/*      */     
/*  886 */     if (len < 7L) {
/*  887 */       selectionSort(x, from, to, comp);
/*      */       
/*      */       return;
/*      */     } 
/*  891 */     long m = from + len / 2L;
/*  892 */     if (len > 7L) {
/*  893 */       long l = from;
/*  894 */       long n = to - 1L;
/*  895 */       if (len > 40L) {
/*  896 */         long s = len / 8L;
/*  897 */         l = med3(x, l, l + s, l + 2L * s, comp);
/*  898 */         m = med3(x, m - s, m, m + s, comp);
/*  899 */         n = med3(x, n - 2L * s, n - s, n, comp);
/*      */       } 
/*  901 */       m = med3(x, l, m, n, comp);
/*      */     } 
/*  903 */     double v = BigArrays.get(x, m);
/*      */     
/*  905 */     long a = from, b = a, c = to - 1L, d = c;
/*      */     
/*      */     int comparison;
/*  908 */     while (b <= c && (comparison = comp.compare(BigArrays.get(x, b), v)) <= 0) {
/*  909 */       if (comparison == 0)
/*  910 */         swap(x, a++, b); 
/*  911 */       b++;
/*      */     } 
/*  913 */     while (c >= b && (comparison = comp.compare(BigArrays.get(x, c), v)) >= 0) {
/*  914 */       if (comparison == 0)
/*  915 */         swap(x, c, d--); 
/*  916 */       c--;
/*      */     } 
/*  918 */     if (b > c) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  923 */       long n = to;
/*  924 */       long s = Math.min(a - from, b - a);
/*  925 */       vecSwap(x, from, b - s, s);
/*  926 */       s = Math.min(d - c, n - d - 1L);
/*  927 */       vecSwap(x, b, n - s, s);
/*      */       
/*  929 */       if ((s = b - a) > 1L)
/*  930 */         quickSort(x, from, from + s, comp); 
/*  931 */       if ((s = d - c) > 1L)
/*  932 */         quickSort(x, n - s, n, comp); 
/*      */       return;
/*      */     } 
/*      */     swap(x, b++, c--); } private static long med3(double[][] x, long a, long b, long c) {
/*  936 */     int ab = Double.compare(BigArrays.get(x, a), BigArrays.get(x, b));
/*  937 */     int ac = Double.compare(BigArrays.get(x, a), BigArrays.get(x, c));
/*  938 */     int bc = Double.compare(BigArrays.get(x, b), BigArrays.get(x, c));
/*  939 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void selectionSort(double[][] a, long from, long to) {
/*      */     long i;
/*  943 */     for (i = from; i < to - 1L; i++) {
/*  944 */       long m = i; long j;
/*  945 */       for (j = i + 1L; j < to; j++) {
/*  946 */         if (Double.compare(BigArrays.get(a, j), BigArrays.get(a, m)) < 0)
/*  947 */           m = j; 
/*  948 */       }  if (m != i) {
/*  949 */         swap(a, i, m);
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
/*      */   public static void quickSort(double[][] x, DoubleComparator comp) {
/*  968 */     quickSort(x, 0L, length(x), comp);
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
/*      */   public static void quickSort(double[][] x, long from, long to) {
/*  988 */     long len = to - from;
/*      */     
/*  990 */     if (len < 7L) {
/*  991 */       selectionSort(x, from, to);
/*      */       
/*      */       return;
/*      */     } 
/*  995 */     long m = from + len / 2L;
/*  996 */     if (len > 7L) {
/*  997 */       long l = from;
/*  998 */       long n = to - 1L;
/*  999 */       if (len > 40L) {
/* 1000 */         long s = len / 8L;
/* 1001 */         l = med3(x, l, l + s, l + 2L * s);
/* 1002 */         m = med3(x, m - s, m, m + s);
/* 1003 */         n = med3(x, n - 2L * s, n - s, n);
/*      */       } 
/* 1005 */       m = med3(x, l, m, n);
/*      */     } 
/* 1007 */     double v = BigArrays.get(x, m);
/*      */     
/* 1009 */     long a = from, b = a, c = to - 1L, d = c;
/*      */     
/*      */     int comparison;
/* 1012 */     while (b <= c && (comparison = Double.compare(BigArrays.get(x, b), v)) <= 0) {
/* 1013 */       if (comparison == 0)
/* 1014 */         swap(x, a++, b); 
/* 1015 */       b++;
/*      */     } 
/* 1017 */     while (c >= b && (comparison = Double.compare(BigArrays.get(x, c), v)) >= 0) {
/* 1018 */       if (comparison == 0)
/* 1019 */         swap(x, c, d--); 
/* 1020 */       c--;
/*      */     } 
/* 1022 */     if (b > c) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1027 */       long n = to;
/* 1028 */       long s = Math.min(a - from, b - a);
/* 1029 */       vecSwap(x, from, b - s, s);
/* 1030 */       s = Math.min(d - c, n - d - 1L);
/* 1031 */       vecSwap(x, b, n - s, s);
/*      */       
/* 1033 */       if ((s = b - a) > 1L)
/* 1034 */         quickSort(x, from, from + s); 
/* 1035 */       if ((s = d - c) > 1L) {
/* 1036 */         quickSort(x, n - s, n);
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
/*      */   public static void quickSort(double[][] x) {
/* 1051 */     quickSort(x, 0L, length(x));
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
/*      */   public static long binarySearch(double[][] a, long from, long to, double key) {
/* 1081 */     to--;
/* 1082 */     while (from <= to) {
/* 1083 */       long mid = from + to >>> 1L;
/* 1084 */       double midVal = BigArrays.get(a, mid);
/* 1085 */       if (midVal < key) {
/* 1086 */         from = mid + 1L; continue;
/* 1087 */       }  if (midVal > key) {
/* 1088 */         to = mid - 1L; continue;
/*      */       } 
/* 1090 */       return mid;
/*      */     } 
/* 1092 */     return -(from + 1L);
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
/*      */   public static long binarySearch(double[][] a, double key) {
/* 1115 */     return binarySearch(a, 0L, length(a), key);
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
/*      */   
/*      */   public static long binarySearch(double[][] a, long from, long to, double key, DoubleComparator c) {
/* 1147 */     to--;
/* 1148 */     while (from <= to) {
/* 1149 */       long mid = from + to >>> 1L;
/* 1150 */       double midVal = BigArrays.get(a, mid);
/* 1151 */       int cmp = c.compare(midVal, key);
/* 1152 */       if (cmp < 0) {
/* 1153 */         from = mid + 1L; continue;
/* 1154 */       }  if (cmp > 0) {
/* 1155 */         to = mid - 1L; continue;
/*      */       } 
/* 1157 */       return mid;
/*      */     } 
/* 1159 */     return -(from + 1L);
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
/*      */   public static long binarySearch(double[][] a, double key, DoubleComparator c) {
/* 1185 */     return binarySearch(a, 0L, length(a), key, c);
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
/*      */   private static final long fixDouble(double d) {
/* 1198 */     long l = Double.doubleToRawLongBits(d);
/* 1199 */     return (l >= 0L) ? l : (l ^ Long.MAX_VALUE);
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
/*      */   public static void radixSort(double[][] a) {
/* 1224 */     radixSort(a, 0L, length(a));
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
/*      */   public static void radixSort(double[][] a, long from, long to) {
/* 1253 */     int maxLevel = 7;
/* 1254 */     int stackSize = 1786;
/* 1255 */     long[] offsetStack = new long[1786];
/* 1256 */     int offsetPos = 0;
/* 1257 */     long[] lengthStack = new long[1786];
/* 1258 */     int lengthPos = 0;
/* 1259 */     int[] levelStack = new int[1786];
/* 1260 */     int levelPos = 0;
/* 1261 */     offsetStack[offsetPos++] = from;
/* 1262 */     lengthStack[lengthPos++] = to - from;
/* 1263 */     levelStack[levelPos++] = 0;
/* 1264 */     long[] count = new long[256];
/* 1265 */     long[] pos = new long[256];
/* 1266 */     byte[][] digit = ByteBigArrays.newBigArray(to - from);
/* 1267 */     while (offsetPos > 0) {
/* 1268 */       long first = offsetStack[--offsetPos];
/* 1269 */       long length = lengthStack[--lengthPos];
/* 1270 */       int level = levelStack[--levelPos];
/* 1271 */       int signMask = (level % 8 == 0) ? 128 : 0;
/* 1272 */       if (length < 40L) {
/* 1273 */         selectionSort(a, first, first + length);
/*      */         continue;
/*      */       } 
/* 1276 */       int shift = (7 - level % 8) * 8;
/*      */ 
/*      */       
/*      */       long i;
/*      */       
/* 1281 */       for (i = length; i-- != 0L;)
/* 1282 */         BigArrays.set(digit, i, 
/* 1283 */             (byte)(int)(fixDouble(BigArrays.get(a, first + i)) >>> shift & 0xFFL ^ signMask)); 
/* 1284 */       for (i = length; i-- != 0L;) {
/* 1285 */         count[BigArrays.get(digit, i) & 0xFF] = count[BigArrays.get(digit, i) & 0xFF] + 1L;
/*      */       }
/* 1287 */       int lastUsed = -1;
/* 1288 */       long p = 0L;
/* 1289 */       for (int j = 0; j < 256; j++) {
/* 1290 */         if (count[j] != 0L) {
/* 1291 */           lastUsed = j;
/* 1292 */           if (level < 7 && count[j] > 1L) {
/*      */ 
/*      */             
/* 1295 */             offsetStack[offsetPos++] = p + first;
/* 1296 */             lengthStack[lengthPos++] = count[j];
/* 1297 */             levelStack[levelPos++] = level + 1;
/*      */           } 
/*      */         } 
/* 1300 */         pos[j] = p += count[j];
/*      */       } 
/*      */       
/* 1303 */       long end = length - count[lastUsed];
/* 1304 */       count[lastUsed] = 0L;
/*      */       
/* 1306 */       int c = -1;
/* 1307 */       for (long l1 = 0L; l1 < end; l1 += count[c], count[c] = 0L) {
/* 1308 */         double t = BigArrays.get(a, l1 + first);
/* 1309 */         c = BigArrays.get(digit, l1) & 0xFF; long d;
/* 1310 */         for (pos[c] = pos[c] - 1L; (d = pos[c] - 1L) > l1; ) {
/* 1311 */           double z = t;
/* 1312 */           int zz = c;
/* 1313 */           t = BigArrays.get(a, d + first);
/* 1314 */           c = BigArrays.get(digit, d) & 0xFF;
/* 1315 */           BigArrays.set(a, d + first, z);
/* 1316 */           BigArrays.set(digit, d, (byte)zz);
/*      */         } 
/* 1318 */         BigArrays.set(a, l1 + first, t);
/*      */       } 
/*      */     } 
/*      */   } private static void selectionSort(double[][] a, double[][] b, long from, long to) {
/*      */     long i;
/* 1323 */     for (i = from; i < to - 1L; i++) {
/* 1324 */       long m = i; long j;
/* 1325 */       for (j = i + 1L; j < to; j++) {
/* 1326 */         if (Double.compare(BigArrays.get(a, j), BigArrays.get(a, m)) < 0 || (
/* 1327 */           Double.compare(BigArrays.get(a, j), BigArrays.get(a, m)) == 0 && 
/* 1328 */           Double.compare(BigArrays.get(b, j), BigArrays.get(b, m)) < 0))
/* 1329 */           m = j; 
/* 1330 */       }  if (m != i) {
/* 1331 */         double t = BigArrays.get(a, i);
/* 1332 */         BigArrays.set(a, i, BigArrays.get(a, m));
/* 1333 */         BigArrays.set(a, m, t);
/* 1334 */         t = BigArrays.get(b, i);
/* 1335 */         BigArrays.set(b, i, BigArrays.get(b, m));
/* 1336 */         BigArrays.set(b, m, t);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(double[][] a, double[][] b) {
/* 1371 */     radixSort(a, b, 0L, length(a));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(double[][] a, double[][] b, long from, long to) {
/* 1409 */     int layers = 2;
/* 1410 */     if (length(a) != length(b))
/* 1411 */       throw new IllegalArgumentException("Array size mismatch."); 
/* 1412 */     int maxLevel = 15;
/* 1413 */     int stackSize = 3826;
/* 1414 */     long[] offsetStack = new long[3826];
/* 1415 */     int offsetPos = 0;
/* 1416 */     long[] lengthStack = new long[3826];
/* 1417 */     int lengthPos = 0;
/* 1418 */     int[] levelStack = new int[3826];
/* 1419 */     int levelPos = 0;
/* 1420 */     offsetStack[offsetPos++] = from;
/* 1421 */     lengthStack[lengthPos++] = to - from;
/* 1422 */     levelStack[levelPos++] = 0;
/* 1423 */     long[] count = new long[256];
/* 1424 */     long[] pos = new long[256];
/* 1425 */     byte[][] digit = ByteBigArrays.newBigArray(to - from);
/* 1426 */     while (offsetPos > 0) {
/* 1427 */       long first = offsetStack[--offsetPos];
/* 1428 */       long length = lengthStack[--lengthPos];
/* 1429 */       int level = levelStack[--levelPos];
/* 1430 */       int signMask = (level % 8 == 0) ? 128 : 0;
/* 1431 */       if (length < 40L) {
/* 1432 */         selectionSort(a, b, first, first + length);
/*      */         continue;
/*      */       } 
/* 1435 */       double[][] k = (level < 8) ? a : b;
/* 1436 */       int shift = (7 - level % 8) * 8;
/*      */ 
/*      */       
/*      */       long i;
/*      */       
/* 1441 */       for (i = length; i-- != 0L;)
/* 1442 */         BigArrays.set(digit, i, 
/* 1443 */             (byte)(int)(fixDouble(BigArrays.get(k, first + i)) >>> shift & 0xFFL ^ signMask)); 
/* 1444 */       for (i = length; i-- != 0L;) {
/* 1445 */         count[BigArrays.get(digit, i) & 0xFF] = count[BigArrays.get(digit, i) & 0xFF] + 1L;
/*      */       }
/* 1447 */       int lastUsed = -1;
/* 1448 */       long p = 0L;
/* 1449 */       for (int j = 0; j < 256; j++) {
/* 1450 */         if (count[j] != 0L) {
/* 1451 */           lastUsed = j;
/* 1452 */           if (level < 15 && count[j] > 1L) {
/* 1453 */             offsetStack[offsetPos++] = p + first;
/* 1454 */             lengthStack[lengthPos++] = count[j];
/* 1455 */             levelStack[levelPos++] = level + 1;
/*      */           } 
/*      */         } 
/* 1458 */         pos[j] = p += count[j];
/*      */       } 
/*      */       
/* 1461 */       long end = length - count[lastUsed];
/* 1462 */       count[lastUsed] = 0L;
/*      */       
/* 1464 */       int c = -1;
/* 1465 */       for (long l1 = 0L; l1 < end; l1 += count[c], count[c] = 0L) {
/* 1466 */         double t = BigArrays.get(a, l1 + first);
/* 1467 */         double u = BigArrays.get(b, l1 + first);
/* 1468 */         c = BigArrays.get(digit, l1) & 0xFF; long d;
/* 1469 */         for (pos[c] = pos[c] - 1L; (d = pos[c] - 1L) > l1; ) {
/* 1470 */           double z = t;
/* 1471 */           int zz = c;
/* 1472 */           t = BigArrays.get(a, d + first);
/* 1473 */           BigArrays.set(a, d + first, z);
/* 1474 */           z = u;
/* 1475 */           u = BigArrays.get(b, d + first);
/* 1476 */           BigArrays.set(b, d + first, z);
/* 1477 */           c = BigArrays.get(digit, d) & 0xFF;
/* 1478 */           BigArrays.set(digit, d, (byte)zz);
/*      */         } 
/* 1480 */         BigArrays.set(a, l1 + first, t);
/* 1481 */         BigArrays.set(b, l1 + first, u);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void insertionSortIndirect(long[][] perm, double[][] a, double[][] b, long from, long to) {
/* 1488 */     long i = from; while (true) { long t, j, u; if (++i < to)
/* 1489 */       { t = BigArrays.get(perm, i);
/* 1490 */         j = i;
/* 1491 */         u = BigArrays.get(perm, j - 1L); } else { break; }  if (Double.compare(BigArrays.get(
/* 1492 */             a, t), BigArrays.get(a, u)) < 0 || (Double.compare(BigArrays.get(a, t), BigArrays.get(a, u)) == 0 && 
/* 1493 */         Double.compare(BigArrays.get(b, t), BigArrays.get(b, u)) < 0));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1501 */       BigArrays.set(perm, j, t); }
/*      */   
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
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSortIndirect(long[][] perm, double[][] a, double[][] b, boolean stable) {
/* 1536 */     ensureSameLength(a, b);
/* 1537 */     radixSortIndirect(perm, a, b, 0L, BigArrays.length(a), stable);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSortIndirect(long[][] perm, double[][] a, double[][] b, long from, long to, boolean stable) {
/* 1577 */     if (to - from < 1024L) {
/* 1578 */       insertionSortIndirect(perm, a, b, from, to);
/*      */       return;
/*      */     } 
/* 1581 */     int layers = 2;
/* 1582 */     int maxLevel = 15;
/* 1583 */     int stackSize = 3826;
/* 1584 */     int stackPos = 0;
/* 1585 */     long[] offsetStack = new long[3826];
/* 1586 */     long[] lengthStack = new long[3826];
/* 1587 */     int[] levelStack = new int[3826];
/* 1588 */     offsetStack[stackPos] = from;
/* 1589 */     lengthStack[stackPos] = to - from;
/* 1590 */     levelStack[stackPos++] = 0;
/* 1591 */     long[] count = new long[256];
/* 1592 */     long[] pos = new long[256];
/*      */ 
/*      */     
/* 1595 */     long[][] support = stable ? LongBigArrays.newBigArray(BigArrays.length(perm)) : null;
/* 1596 */     while (stackPos > 0) {
/* 1597 */       long first = offsetStack[--stackPos];
/* 1598 */       long length = lengthStack[stackPos];
/* 1599 */       int level = levelStack[stackPos];
/* 1600 */       int signMask = (level % 8 == 0) ? 128 : 0;
/* 1601 */       double[][] k = (level < 8) ? a : b;
/* 1602 */       int shift = (7 - level % 8) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1607 */       for (long i = first + length; i-- != first;) {
/* 1608 */         count[(int)(fixDouble(BigArrays.get(k, BigArrays.get(perm, i))) >>> shift & 0xFFL ^ signMask)] = count[(int)(fixDouble(BigArrays.get(k, BigArrays.get(perm, i))) >>> shift & 0xFFL ^ signMask)] + 1L;
/*      */       }
/* 1610 */       int lastUsed = -1;
/* 1611 */       long p = stable ? 0L : first;
/* 1612 */       for (int j = 0; j < 256; j++) {
/* 1613 */         if (count[j] != 0L)
/* 1614 */           lastUsed = j; 
/* 1615 */         pos[j] = p += count[j];
/*      */       } 
/* 1617 */       if (stable) {
/* 1618 */         for (long l = first + length; l-- != first;)
/* 1619 */           BigArrays.set(support, pos[
/* 1620 */                 (int)(fixDouble(BigArrays.get(k, BigArrays.get(perm, l))) >>> shift & 0xFFL ^ signMask)] = pos[(int)(fixDouble(BigArrays.get(k, BigArrays.get(perm, l))) >>> shift & 0xFFL ^ signMask)] - 1L, 
/*      */               
/* 1622 */               BigArrays.get(perm, l)); 
/* 1623 */         BigArrays.copy(support, 0L, perm, first, length);
/* 1624 */         p = first;
/* 1625 */         for (int m = 0; m < 256; m++) {
/* 1626 */           if (level < 15 && count[m] > 1L) {
/* 1627 */             if (count[m] < 1024L) {
/* 1628 */               insertionSortIndirect(perm, a, b, p, p + count[m]);
/*      */             } else {
/* 1630 */               offsetStack[stackPos] = p;
/* 1631 */               lengthStack[stackPos] = count[m];
/* 1632 */               levelStack[stackPos++] = level + 1;
/*      */             } 
/*      */           }
/* 1635 */           p += count[m];
/*      */         } 
/* 1637 */         Arrays.fill(count, 0L); continue;
/*      */       } 
/* 1639 */       long end = first + length - count[lastUsed];
/*      */       
/* 1641 */       int c = -1;
/* 1642 */       for (long l1 = first; l1 <= end; l1 += count[c], count[c] = 0L) {
/* 1643 */         long t = BigArrays.get(perm, l1);
/* 1644 */         c = (int)(fixDouble(BigArrays.get(k, t)) >>> shift & 0xFFL ^ signMask);
/* 1645 */         if (l1 < end) {
/* 1646 */           long d; for (pos[c] = pos[c] - 1L; (d = pos[c] - 1L) > l1; ) {
/* 1647 */             long z = t;
/* 1648 */             t = BigArrays.get(perm, d);
/* 1649 */             BigArrays.set(perm, d, z);
/* 1650 */             c = (int)(fixDouble(BigArrays.get(k, t)) >>> shift & 0xFFL ^ signMask);
/*      */           } 
/* 1652 */           BigArrays.set(perm, l1, t);
/*      */         } 
/* 1654 */         if (level < 15 && count[c] > 1L) {
/* 1655 */           if (count[c] < 1024L) {
/* 1656 */             insertionSortIndirect(perm, a, b, l1, l1 + count[c]);
/*      */           } else {
/* 1658 */             offsetStack[stackPos] = l1;
/* 1659 */             lengthStack[stackPos] = count[c];
/* 1660 */             levelStack[stackPos++] = level + 1;
/*      */           } 
/*      */         }
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
/*      */   public static double[][] shuffle(double[][] a, long from, long to, Random random) {
/* 1682 */     for (long i = to - from; i-- != 0L; ) {
/* 1683 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 1684 */       double t = BigArrays.get(a, from + i);
/* 1685 */       BigArrays.set(a, from + i, BigArrays.get(a, from + p));
/* 1686 */       BigArrays.set(a, from + p, t);
/*      */     } 
/* 1688 */     return a;
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
/*      */   public static double[][] shuffle(double[][] a, Random random) {
/* 1701 */     for (long i = length(a); i-- != 0L; ) {
/* 1702 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 1703 */       double t = BigArrays.get(a, i);
/* 1704 */       BigArrays.set(a, i, BigArrays.get(a, p));
/* 1705 */       BigArrays.set(a, p, t);
/*      */     } 
/* 1707 */     return a;
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\DoubleBigArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */