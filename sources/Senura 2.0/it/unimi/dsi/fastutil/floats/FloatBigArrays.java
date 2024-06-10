/*      */ package it.unimi.dsi.fastutil.floats;
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
/*      */ public final class FloatBigArrays
/*      */ {
/*   63 */   public static final float[][] EMPTY_BIG_ARRAY = new float[0][];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   72 */   public static final float[][] DEFAULT_EMPTY_BIG_ARRAY = new float[0][];
/*      */ 
/*      */ 
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
/*      */   public static float get(float[][] array, long index) {
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
/*      */   public static void set(float[][] array, long index, float value) {
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
/*      */   public static void swap(float[][] array, long first, long second) {
/*  118 */     float t = array[BigArrays.segment(first)][BigArrays.displacement(first)];
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
/*      */   public static void add(float[][] array, long index, float incr) {
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
/*      */   public static void mul(float[][] array, long index, float factor) {
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
/*      */   public static void incr(float[][] array, long index) {
/*  168 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = array[BigArrays.segment(index)][BigArrays.displacement(index)] + 1.0F;
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
/*      */   public static void decr(float[][] array, long index) {
/*  182 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = array[BigArrays.segment(index)][BigArrays.displacement(index)] - 1.0F;
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
/*      */   public static long length(float[][] array) {
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
/*      */   public static void copy(float[][] srcArray, long srcPos, float[][] destArray, long destPos, long length) {
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
/*      */   public static void copyFromBig(float[][] srcArray, long srcPos, float[] destArray, int destPos, int length) {
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
/*      */   public static void copyToBig(float[] srcArray, int srcPos, float[][] destArray, long destPos, long length) {
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
/*      */   public static float[][] newBigArray(long length) {
/*  338 */     if (length == 0L)
/*  339 */       return EMPTY_BIG_ARRAY; 
/*  340 */     BigArrays.ensureLength(length);
/*  341 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  342 */     float[][] base = new float[baseLength][];
/*  343 */     int residual = (int)(length & 0x7FFFFFFL);
/*  344 */     if (residual != 0) {
/*  345 */       for (int i = 0; i < baseLength - 1; i++)
/*  346 */         base[i] = new float[134217728]; 
/*  347 */       base[baseLength - 1] = new float[residual];
/*      */     } else {
/*  349 */       for (int i = 0; i < baseLength; i++)
/*  350 */         base[i] = new float[134217728]; 
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
/*      */   public static float[][] wrap(float[] array) {
/*  368 */     if (array.length == 0)
/*  369 */       return EMPTY_BIG_ARRAY; 
/*  370 */     if (array.length <= 134217728)
/*  371 */       return new float[][] { array }; 
/*  372 */     float[][] bigArray = newBigArray(array.length);
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
/*      */   public static float[][] ensureCapacity(float[][] array, long length) {
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
/*      */   public static float[][] forceCapacity(float[][] array, long length, long preserve) {
/*  426 */     BigArrays.ensureLength(length);
/*      */     
/*  428 */     int valid = array.length - ((array.length == 0 || (array.length > 0 && (array[array.length - 1]).length == 134217728)) ? 0 : 1);
/*  429 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  430 */     float[][] base = Arrays.<float[]>copyOf(array, baseLength);
/*  431 */     int residual = (int)(length & 0x7FFFFFFL);
/*  432 */     if (residual != 0) {
/*  433 */       for (int i = valid; i < baseLength - 1; i++)
/*  434 */         base[i] = new float[134217728]; 
/*  435 */       base[baseLength - 1] = new float[residual];
/*      */     } else {
/*  437 */       for (int i = valid; i < baseLength; i++)
/*  438 */         base[i] = new float[134217728]; 
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
/*      */   public static float[][] ensureCapacity(float[][] array, long length, long preserve) {
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
/*      */   public static float[][] grow(float[][] array, long length) {
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
/*      */   public static float[][] grow(float[][] array, long length, long preserve) {
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
/*      */   public static float[][] trim(float[][] array, long length) {
/*  555 */     BigArrays.ensureLength(length);
/*  556 */     long oldLength = length(array);
/*  557 */     if (length >= oldLength)
/*  558 */       return array; 
/*  559 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  560 */     float[][] base = Arrays.<float[]>copyOf(array, baseLength);
/*  561 */     int residual = (int)(length & 0x7FFFFFFL);
/*  562 */     if (residual != 0)
/*  563 */       base[baseLength - 1] = FloatArrays.trim(base[baseLength - 1], residual); 
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
/*      */   public static float[][] setLength(float[][] array, long length) {
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
/*      */   public static float[][] copy(float[][] array, long offset, long length) {
/*  612 */     ensureOffsetLength(array, offset, length);
/*  613 */     float[][] a = newBigArray(length);
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
/*      */   public static float[][] copy(float[][] array) {
/*  628 */     float[][] base = (float[][])array.clone();
/*  629 */     for (int i = base.length; i-- != 0;)
/*  630 */       base[i] = (float[])array[i].clone(); 
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
/*      */   public static void fill(float[][] array, float value) {
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
/*      */   public static void fill(float[][] array, long from, long to, float value) {
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
/*      */   public static boolean equals(float[][] a1, float[][] a2) {
/*  710 */     if (length(a1) != length(a2))
/*  711 */       return false; 
/*  712 */     int i = a1.length;
/*      */     
/*  714 */     while (i-- != 0) {
/*  715 */       float[] t = a1[i];
/*  716 */       float[] u = a2[i];
/*  717 */       int j = t.length;
/*  718 */       while (j-- != 0) {
/*  719 */         if (Float.floatToIntBits(t[j]) != Float.floatToIntBits(u[j]))
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
/*      */   public static String toString(float[][] a) {
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
/*      */   public static void ensureFromTo(float[][] a, long from, long to) {
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
/*      */   public static void ensureOffsetLength(float[][] a, long offset, long length) {
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
/*      */   public static void ensureSameLength(float[][] a, float[][] b) {
/*  818 */     if (length(a) != length(b))
/*  819 */       throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b)); 
/*      */   }
/*      */   private static final class BigArrayHashStrategy implements Hash.Strategy<float[][]>, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private BigArrayHashStrategy() {}
/*      */     
/*      */     public int hashCode(float[][] o) {
/*  826 */       return Arrays.deepHashCode((Object[])o);
/*      */     }
/*      */     
/*      */     public boolean equals(float[][] a, float[][] b) {
/*  830 */       return FloatBigArrays.equals(a, b);
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
/*  842 */   public static final Hash.Strategy HASH_STRATEGY = new BigArrayHashStrategy(); private static final int DIGIT_MASK = 255; private static final int DIGITS_PER_ELEMENT = 4;
/*      */   private static final int RADIXSORT_NO_REC = 1024;
/*      */   
/*      */   private static void vecSwap(float[][] x, long a, long b, long n) {
/*  846 */     for (int i = 0; i < n; i++, a++, b++)
/*  847 */       swap(x, a, b); 
/*      */   }
/*      */   private static long med3(float[][] x, long a, long b, long c, FloatComparator comp) {
/*  850 */     int ab = comp.compare(BigArrays.get(x, a), BigArrays.get(x, b));
/*  851 */     int ac = comp.compare(BigArrays.get(x, a), BigArrays.get(x, c));
/*  852 */     int bc = comp.compare(BigArrays.get(x, b), BigArrays.get(x, c));
/*  853 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   } private static void selectionSort(float[][] a, long from, long to, FloatComparator comp) {
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
/*      */   public static void quickSort(float[][] x, long from, long to, FloatComparator comp)
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
/*  903 */     float v = BigArrays.get(x, m);
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
/*      */     swap(x, b++, c--); } private static long med3(float[][] x, long a, long b, long c) {
/*  936 */     int ab = Float.compare(BigArrays.get(x, a), BigArrays.get(x, b));
/*  937 */     int ac = Float.compare(BigArrays.get(x, a), BigArrays.get(x, c));
/*  938 */     int bc = Float.compare(BigArrays.get(x, b), BigArrays.get(x, c));
/*  939 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void selectionSort(float[][] a, long from, long to) {
/*      */     long i;
/*  943 */     for (i = from; i < to - 1L; i++) {
/*  944 */       long m = i; long j;
/*  945 */       for (j = i + 1L; j < to; j++) {
/*  946 */         if (Float.compare(BigArrays.get(a, j), BigArrays.get(a, m)) < 0)
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
/*      */   public static void quickSort(float[][] x, FloatComparator comp) {
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
/*      */   public static void quickSort(float[][] x, long from, long to) {
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
/* 1007 */     float v = BigArrays.get(x, m);
/*      */     
/* 1009 */     long a = from, b = a, c = to - 1L, d = c;
/*      */     
/*      */     int comparison;
/* 1012 */     while (b <= c && (comparison = Float.compare(BigArrays.get(x, b), v)) <= 0) {
/* 1013 */       if (comparison == 0)
/* 1014 */         swap(x, a++, b); 
/* 1015 */       b++;
/*      */     } 
/* 1017 */     while (c >= b && (comparison = Float.compare(BigArrays.get(x, c), v)) >= 0) {
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
/*      */   public static void quickSort(float[][] x) {
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
/*      */   public static long binarySearch(float[][] a, long from, long to, float key) {
/* 1081 */     to--;
/* 1082 */     while (from <= to) {
/* 1083 */       long mid = from + to >>> 1L;
/* 1084 */       float midVal = BigArrays.get(a, mid);
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
/*      */   public static long binarySearch(float[][] a, float key) {
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
/*      */   public static long binarySearch(float[][] a, long from, long to, float key, FloatComparator c) {
/* 1146 */     to--;
/* 1147 */     while (from <= to) {
/* 1148 */       long mid = from + to >>> 1L;
/* 1149 */       float midVal = BigArrays.get(a, mid);
/* 1150 */       int cmp = c.compare(midVal, key);
/* 1151 */       if (cmp < 0) {
/* 1152 */         from = mid + 1L; continue;
/* 1153 */       }  if (cmp > 0) {
/* 1154 */         to = mid - 1L; continue;
/*      */       } 
/* 1156 */       return mid;
/*      */     } 
/* 1158 */     return -(from + 1L);
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
/*      */   public static long binarySearch(float[][] a, float key, FloatComparator c) {
/* 1184 */     return binarySearch(a, 0L, length(a), key, c);
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
/*      */   private static final int fixFloat(float f) {
/* 1197 */     int i = Float.floatToRawIntBits(f);
/* 1198 */     return (i >= 0) ? i : (i ^ Integer.MAX_VALUE);
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
/*      */   public static void radixSort(float[][] a) {
/* 1223 */     radixSort(a, 0L, length(a));
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
/*      */   public static void radixSort(float[][] a, long from, long to) {
/* 1252 */     int maxLevel = 3;
/* 1253 */     int stackSize = 766;
/* 1254 */     long[] offsetStack = new long[766];
/* 1255 */     int offsetPos = 0;
/* 1256 */     long[] lengthStack = new long[766];
/* 1257 */     int lengthPos = 0;
/* 1258 */     int[] levelStack = new int[766];
/* 1259 */     int levelPos = 0;
/* 1260 */     offsetStack[offsetPos++] = from;
/* 1261 */     lengthStack[lengthPos++] = to - from;
/* 1262 */     levelStack[levelPos++] = 0;
/* 1263 */     long[] count = new long[256];
/* 1264 */     long[] pos = new long[256];
/* 1265 */     byte[][] digit = ByteBigArrays.newBigArray(to - from);
/* 1266 */     while (offsetPos > 0) {
/* 1267 */       long first = offsetStack[--offsetPos];
/* 1268 */       long length = lengthStack[--lengthPos];
/* 1269 */       int level = levelStack[--levelPos];
/* 1270 */       int signMask = (level % 4 == 0) ? 128 : 0;
/* 1271 */       if (length < 40L) {
/* 1272 */         selectionSort(a, first, first + length);
/*      */         continue;
/*      */       } 
/* 1275 */       int shift = (3 - level % 4) * 8;
/*      */ 
/*      */       
/*      */       long i;
/*      */       
/* 1280 */       for (i = length; i-- != 0L;)
/* 1281 */         BigArrays.set(digit, i, 
/* 1282 */             (byte)(fixFloat(BigArrays.get(a, first + i)) >>> shift & 0xFF ^ signMask)); 
/* 1283 */       for (i = length; i-- != 0L;) {
/* 1284 */         count[BigArrays.get(digit, i) & 0xFF] = count[BigArrays.get(digit, i) & 0xFF] + 1L;
/*      */       }
/* 1286 */       int lastUsed = -1;
/* 1287 */       long p = 0L;
/* 1288 */       for (int j = 0; j < 256; j++) {
/* 1289 */         if (count[j] != 0L) {
/* 1290 */           lastUsed = j;
/* 1291 */           if (level < 3 && count[j] > 1L) {
/*      */ 
/*      */             
/* 1294 */             offsetStack[offsetPos++] = p + first;
/* 1295 */             lengthStack[lengthPos++] = count[j];
/* 1296 */             levelStack[levelPos++] = level + 1;
/*      */           } 
/*      */         } 
/* 1299 */         pos[j] = p += count[j];
/*      */       } 
/*      */       
/* 1302 */       long end = length - count[lastUsed];
/* 1303 */       count[lastUsed] = 0L;
/*      */       
/* 1305 */       int c = -1;
/* 1306 */       for (long l1 = 0L; l1 < end; l1 += count[c], count[c] = 0L) {
/* 1307 */         float t = BigArrays.get(a, l1 + first);
/* 1308 */         c = BigArrays.get(digit, l1) & 0xFF; long d;
/* 1309 */         for (pos[c] = pos[c] - 1L; (d = pos[c] - 1L) > l1; ) {
/* 1310 */           float z = t;
/* 1311 */           int zz = c;
/* 1312 */           t = BigArrays.get(a, d + first);
/* 1313 */           c = BigArrays.get(digit, d) & 0xFF;
/* 1314 */           BigArrays.set(a, d + first, z);
/* 1315 */           BigArrays.set(digit, d, (byte)zz);
/*      */         } 
/* 1317 */         BigArrays.set(a, l1 + first, t);
/*      */       } 
/*      */     } 
/*      */   } private static void selectionSort(float[][] a, float[][] b, long from, long to) {
/*      */     long i;
/* 1322 */     for (i = from; i < to - 1L; i++) {
/* 1323 */       long m = i; long j;
/* 1324 */       for (j = i + 1L; j < to; j++) {
/* 1325 */         if (Float.compare(BigArrays.get(a, j), BigArrays.get(a, m)) < 0 || (
/* 1326 */           Float.compare(BigArrays.get(a, j), BigArrays.get(a, m)) == 0 && 
/* 1327 */           Float.compare(BigArrays.get(b, j), BigArrays.get(b, m)) < 0))
/* 1328 */           m = j; 
/* 1329 */       }  if (m != i) {
/* 1330 */         float t = BigArrays.get(a, i);
/* 1331 */         BigArrays.set(a, i, BigArrays.get(a, m));
/* 1332 */         BigArrays.set(a, m, t);
/* 1333 */         t = BigArrays.get(b, i);
/* 1334 */         BigArrays.set(b, i, BigArrays.get(b, m));
/* 1335 */         BigArrays.set(b, m, t);
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
/*      */   public static void radixSort(float[][] a, float[][] b) {
/* 1370 */     radixSort(a, b, 0L, length(a));
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
/*      */   public static void radixSort(float[][] a, float[][] b, long from, long to) {
/* 1408 */     int layers = 2;
/* 1409 */     if (length(a) != length(b))
/* 1410 */       throw new IllegalArgumentException("Array size mismatch."); 
/* 1411 */     int maxLevel = 7;
/* 1412 */     int stackSize = 1786;
/* 1413 */     long[] offsetStack = new long[1786];
/* 1414 */     int offsetPos = 0;
/* 1415 */     long[] lengthStack = new long[1786];
/* 1416 */     int lengthPos = 0;
/* 1417 */     int[] levelStack = new int[1786];
/* 1418 */     int levelPos = 0;
/* 1419 */     offsetStack[offsetPos++] = from;
/* 1420 */     lengthStack[lengthPos++] = to - from;
/* 1421 */     levelStack[levelPos++] = 0;
/* 1422 */     long[] count = new long[256];
/* 1423 */     long[] pos = new long[256];
/* 1424 */     byte[][] digit = ByteBigArrays.newBigArray(to - from);
/* 1425 */     while (offsetPos > 0) {
/* 1426 */       long first = offsetStack[--offsetPos];
/* 1427 */       long length = lengthStack[--lengthPos];
/* 1428 */       int level = levelStack[--levelPos];
/* 1429 */       int signMask = (level % 4 == 0) ? 128 : 0;
/* 1430 */       if (length < 40L) {
/* 1431 */         selectionSort(a, b, first, first + length);
/*      */         continue;
/*      */       } 
/* 1434 */       float[][] k = (level < 4) ? a : b;
/* 1435 */       int shift = (3 - level % 4) * 8;
/*      */ 
/*      */       
/*      */       long i;
/*      */       
/* 1440 */       for (i = length; i-- != 0L;)
/* 1441 */         BigArrays.set(digit, i, 
/* 1442 */             (byte)(fixFloat(BigArrays.get(k, first + i)) >>> shift & 0xFF ^ signMask)); 
/* 1443 */       for (i = length; i-- != 0L;) {
/* 1444 */         count[BigArrays.get(digit, i) & 0xFF] = count[BigArrays.get(digit, i) & 0xFF] + 1L;
/*      */       }
/* 1446 */       int lastUsed = -1;
/* 1447 */       long p = 0L;
/* 1448 */       for (int j = 0; j < 256; j++) {
/* 1449 */         if (count[j] != 0L) {
/* 1450 */           lastUsed = j;
/* 1451 */           if (level < 7 && count[j] > 1L) {
/* 1452 */             offsetStack[offsetPos++] = p + first;
/* 1453 */             lengthStack[lengthPos++] = count[j];
/* 1454 */             levelStack[levelPos++] = level + 1;
/*      */           } 
/*      */         } 
/* 1457 */         pos[j] = p += count[j];
/*      */       } 
/*      */       
/* 1460 */       long end = length - count[lastUsed];
/* 1461 */       count[lastUsed] = 0L;
/*      */       
/* 1463 */       int c = -1;
/* 1464 */       for (long l1 = 0L; l1 < end; l1 += count[c], count[c] = 0L) {
/* 1465 */         float t = BigArrays.get(a, l1 + first);
/* 1466 */         float u = BigArrays.get(b, l1 + first);
/* 1467 */         c = BigArrays.get(digit, l1) & 0xFF; long d;
/* 1468 */         for (pos[c] = pos[c] - 1L; (d = pos[c] - 1L) > l1; ) {
/* 1469 */           float z = t;
/* 1470 */           int zz = c;
/* 1471 */           t = BigArrays.get(a, d + first);
/* 1472 */           BigArrays.set(a, d + first, z);
/* 1473 */           z = u;
/* 1474 */           u = BigArrays.get(b, d + first);
/* 1475 */           BigArrays.set(b, d + first, z);
/* 1476 */           c = BigArrays.get(digit, d) & 0xFF;
/* 1477 */           BigArrays.set(digit, d, (byte)zz);
/*      */         } 
/* 1479 */         BigArrays.set(a, l1 + first, t);
/* 1480 */         BigArrays.set(b, l1 + first, u);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void insertionSortIndirect(long[][] perm, float[][] a, float[][] b, long from, long to) {
/* 1487 */     long i = from; while (true) { long t, j, u; if (++i < to)
/* 1488 */       { t = BigArrays.get(perm, i);
/* 1489 */         j = i;
/* 1490 */         u = BigArrays.get(perm, j - 1L); } else { break; }  if (Float.compare(BigArrays.get(
/* 1491 */             a, t), BigArrays.get(a, u)) < 0 || (Float.compare(BigArrays.get(a, t), BigArrays.get(a, u)) == 0 && 
/* 1492 */         Float.compare(BigArrays.get(b, t), BigArrays.get(b, u)) < 0));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1500 */       BigArrays.set(perm, j, t); }
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
/*      */   public static void radixSortIndirect(long[][] perm, float[][] a, float[][] b, boolean stable) {
/* 1535 */     ensureSameLength(a, b);
/* 1536 */     radixSortIndirect(perm, a, b, 0L, BigArrays.length(a), stable);
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
/*      */   public static void radixSortIndirect(long[][] perm, float[][] a, float[][] b, long from, long to, boolean stable) {
/* 1576 */     if (to - from < 1024L) {
/* 1577 */       insertionSortIndirect(perm, a, b, from, to);
/*      */       return;
/*      */     } 
/* 1580 */     int layers = 2;
/* 1581 */     int maxLevel = 7;
/* 1582 */     int stackSize = 1786;
/* 1583 */     int stackPos = 0;
/* 1584 */     long[] offsetStack = new long[1786];
/* 1585 */     long[] lengthStack = new long[1786];
/* 1586 */     int[] levelStack = new int[1786];
/* 1587 */     offsetStack[stackPos] = from;
/* 1588 */     lengthStack[stackPos] = to - from;
/* 1589 */     levelStack[stackPos++] = 0;
/* 1590 */     long[] count = new long[256];
/* 1591 */     long[] pos = new long[256];
/*      */ 
/*      */     
/* 1594 */     long[][] support = stable ? LongBigArrays.newBigArray(BigArrays.length(perm)) : null;
/* 1595 */     while (stackPos > 0) {
/* 1596 */       long first = offsetStack[--stackPos];
/* 1597 */       long length = lengthStack[stackPos];
/* 1598 */       int level = levelStack[stackPos];
/* 1599 */       int signMask = (level % 4 == 0) ? 128 : 0;
/* 1600 */       float[][] k = (level < 4) ? a : b;
/* 1601 */       int shift = (3 - level % 4) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1606 */       for (long i = first + length; i-- != first;) {
/* 1607 */         count[fixFloat(BigArrays.get(k, BigArrays.get(perm, i))) >>> shift & 0xFF ^ signMask] = count[fixFloat(BigArrays.get(k, BigArrays.get(perm, i))) >>> shift & 0xFF ^ signMask] + 1L;
/*      */       }
/* 1609 */       int lastUsed = -1;
/* 1610 */       long p = stable ? 0L : first;
/* 1611 */       for (int j = 0; j < 256; j++) {
/* 1612 */         if (count[j] != 0L)
/* 1613 */           lastUsed = j; 
/* 1614 */         pos[j] = p += count[j];
/*      */       } 
/* 1616 */       if (stable) {
/* 1617 */         for (long l = first + length; l-- != first;)
/* 1618 */           BigArrays.set(support, pos[
/* 1619 */                 fixFloat(BigArrays.get(k, BigArrays.get(perm, l))) >>> shift & 0xFF ^ signMask] = pos[fixFloat(BigArrays.get(k, BigArrays.get(perm, l))) >>> shift & 0xFF ^ signMask] - 1L, 
/*      */               
/* 1621 */               BigArrays.get(perm, l)); 
/* 1622 */         BigArrays.copy(support, 0L, perm, first, length);
/* 1623 */         p = first;
/* 1624 */         for (int m = 0; m < 256; m++) {
/* 1625 */           if (level < 7 && count[m] > 1L) {
/* 1626 */             if (count[m] < 1024L) {
/* 1627 */               insertionSortIndirect(perm, a, b, p, p + count[m]);
/*      */             } else {
/* 1629 */               offsetStack[stackPos] = p;
/* 1630 */               lengthStack[stackPos] = count[m];
/* 1631 */               levelStack[stackPos++] = level + 1;
/*      */             } 
/*      */           }
/* 1634 */           p += count[m];
/*      */         } 
/* 1636 */         Arrays.fill(count, 0L); continue;
/*      */       } 
/* 1638 */       long end = first + length - count[lastUsed];
/*      */       
/* 1640 */       int c = -1;
/* 1641 */       for (long l1 = first; l1 <= end; l1 += count[c], count[c] = 0L) {
/* 1642 */         long t = BigArrays.get(perm, l1);
/* 1643 */         c = fixFloat(BigArrays.get(k, t)) >>> shift & 0xFF ^ signMask;
/* 1644 */         if (l1 < end) {
/* 1645 */           long d; for (pos[c] = pos[c] - 1L; (d = pos[c] - 1L) > l1; ) {
/* 1646 */             long z = t;
/* 1647 */             t = BigArrays.get(perm, d);
/* 1648 */             BigArrays.set(perm, d, z);
/* 1649 */             c = fixFloat(BigArrays.get(k, t)) >>> shift & 0xFF ^ signMask;
/*      */           } 
/* 1651 */           BigArrays.set(perm, l1, t);
/*      */         } 
/* 1653 */         if (level < 7 && count[c] > 1L) {
/* 1654 */           if (count[c] < 1024L) {
/* 1655 */             insertionSortIndirect(perm, a, b, l1, l1 + count[c]);
/*      */           } else {
/* 1657 */             offsetStack[stackPos] = l1;
/* 1658 */             lengthStack[stackPos] = count[c];
/* 1659 */             levelStack[stackPos++] = level + 1;
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
/*      */   public static float[][] shuffle(float[][] a, long from, long to, Random random) {
/* 1681 */     for (long i = to - from; i-- != 0L; ) {
/* 1682 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 1683 */       float t = BigArrays.get(a, from + i);
/* 1684 */       BigArrays.set(a, from + i, BigArrays.get(a, from + p));
/* 1685 */       BigArrays.set(a, from + p, t);
/*      */     } 
/* 1687 */     return a;
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
/*      */   public static float[][] shuffle(float[][] a, Random random) {
/* 1700 */     for (long i = length(a); i-- != 0L; ) {
/* 1701 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 1702 */       float t = BigArrays.get(a, i);
/* 1703 */       BigArrays.set(a, i, BigArrays.get(a, p));
/* 1704 */       BigArrays.set(a, p, t);
/*      */     } 
/* 1706 */     return a;
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\FloatBigArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */