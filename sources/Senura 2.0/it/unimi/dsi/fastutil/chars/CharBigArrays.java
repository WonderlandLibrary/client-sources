/*      */ package it.unimi.dsi.fastutil.chars;
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
/*      */ public final class CharBigArrays
/*      */ {
/*   63 */   public static final char[][] EMPTY_BIG_ARRAY = new char[0][];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   72 */   public static final char[][] DEFAULT_EMPTY_BIG_ARRAY = new char[0][];
/*      */ 
/*      */ 
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
/*      */   public static char get(char[][] array, long index) {
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
/*      */   public static void set(char[][] array, long index, char value) {
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
/*      */   public static void swap(char[][] array, long first, long second) {
/*  118 */     char t = array[BigArrays.segment(first)][BigArrays.displacement(first)];
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
/*      */   public static void add(char[][] array, long index, char incr) {
/*  137 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = (char)(array[BigArrays.segment(index)][BigArrays.displacement(index)] + incr);
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
/*      */   public static void mul(char[][] array, long index, char factor) {
/*  154 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = (char)(array[BigArrays.segment(index)][BigArrays.displacement(index)] * factor);
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
/*      */   public static void incr(char[][] array, long index) {
/*  168 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = (char)(array[BigArrays.segment(index)][BigArrays.displacement(index)] + 1);
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
/*      */   public static void decr(char[][] array, long index) {
/*  182 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = (char)(array[BigArrays.segment(index)][BigArrays.displacement(index)] - 1);
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
/*      */   public static long length(char[][] array) {
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
/*      */   public static void copy(char[][] srcArray, long srcPos, char[][] destArray, long destPos, long length) {
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
/*      */   public static void copyFromBig(char[][] srcArray, long srcPos, char[] destArray, int destPos, int length) {
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
/*      */   public static void copyToBig(char[] srcArray, int srcPos, char[][] destArray, long destPos, long length) {
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
/*      */   public static char[][] newBigArray(long length) {
/*  338 */     if (length == 0L)
/*  339 */       return EMPTY_BIG_ARRAY; 
/*  340 */     BigArrays.ensureLength(length);
/*  341 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  342 */     char[][] base = new char[baseLength][];
/*  343 */     int residual = (int)(length & 0x7FFFFFFL);
/*  344 */     if (residual != 0) {
/*  345 */       for (int i = 0; i < baseLength - 1; i++)
/*  346 */         base[i] = new char[134217728]; 
/*  347 */       base[baseLength - 1] = new char[residual];
/*      */     } else {
/*  349 */       for (int i = 0; i < baseLength; i++)
/*  350 */         base[i] = new char[134217728]; 
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
/*      */   public static char[][] wrap(char[] array) {
/*  368 */     if (array.length == 0)
/*  369 */       return EMPTY_BIG_ARRAY; 
/*  370 */     if (array.length <= 134217728)
/*  371 */       return new char[][] { array }; 
/*  372 */     char[][] bigArray = newBigArray(array.length);
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
/*      */   public static char[][] ensureCapacity(char[][] array, long length) {
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
/*      */   public static char[][] forceCapacity(char[][] array, long length, long preserve) {
/*  426 */     BigArrays.ensureLength(length);
/*      */     
/*  428 */     int valid = array.length - ((array.length == 0 || (array.length > 0 && (array[array.length - 1]).length == 134217728)) ? 0 : 1);
/*  429 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  430 */     char[][] base = Arrays.<char[]>copyOf(array, baseLength);
/*  431 */     int residual = (int)(length & 0x7FFFFFFL);
/*  432 */     if (residual != 0) {
/*  433 */       for (int i = valid; i < baseLength - 1; i++)
/*  434 */         base[i] = new char[134217728]; 
/*  435 */       base[baseLength - 1] = new char[residual];
/*      */     } else {
/*  437 */       for (int i = valid; i < baseLength; i++)
/*  438 */         base[i] = new char[134217728]; 
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
/*      */   public static char[][] ensureCapacity(char[][] array, long length, long preserve) {
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
/*      */   public static char[][] grow(char[][] array, long length) {
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
/*      */   public static char[][] grow(char[][] array, long length, long preserve) {
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
/*      */   public static char[][] trim(char[][] array, long length) {
/*  555 */     BigArrays.ensureLength(length);
/*  556 */     long oldLength = length(array);
/*  557 */     if (length >= oldLength)
/*  558 */       return array; 
/*  559 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  560 */     char[][] base = Arrays.<char[]>copyOf(array, baseLength);
/*  561 */     int residual = (int)(length & 0x7FFFFFFL);
/*  562 */     if (residual != 0)
/*  563 */       base[baseLength - 1] = CharArrays.trim(base[baseLength - 1], residual); 
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
/*      */   public static char[][] setLength(char[][] array, long length) {
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
/*      */   public static char[][] copy(char[][] array, long offset, long length) {
/*  612 */     ensureOffsetLength(array, offset, length);
/*  613 */     char[][] a = newBigArray(length);
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
/*      */   public static char[][] copy(char[][] array) {
/*  628 */     char[][] base = (char[][])array.clone();
/*  629 */     for (int i = base.length; i-- != 0;)
/*  630 */       base[i] = (char[])array[i].clone(); 
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
/*      */   public static void fill(char[][] array, char value) {
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
/*      */   public static void fill(char[][] array, long from, long to, char value) {
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
/*      */   public static boolean equals(char[][] a1, char[][] a2) {
/*  710 */     if (length(a1) != length(a2))
/*  711 */       return false; 
/*  712 */     int i = a1.length;
/*      */     
/*  714 */     while (i-- != 0) {
/*  715 */       char[] t = a1[i];
/*  716 */       char[] u = a2[i];
/*  717 */       int j = t.length;
/*  718 */       while (j-- != 0) {
/*  719 */         if (t[j] != u[j])
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
/*      */   public static String toString(char[][] a) {
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
/*      */   public static void ensureFromTo(char[][] a, long from, long to) {
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
/*      */   public static void ensureOffsetLength(char[][] a, long offset, long length) {
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
/*      */   public static void ensureSameLength(char[][] a, char[][] b) {
/*  818 */     if (length(a) != length(b))
/*  819 */       throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b)); 
/*      */   }
/*      */   private static final class BigArrayHashStrategy implements Hash.Strategy<char[][]>, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private BigArrayHashStrategy() {}
/*      */     
/*      */     public int hashCode(char[][] o) {
/*  826 */       return Arrays.deepHashCode((Object[])o);
/*      */     }
/*      */     
/*      */     public boolean equals(char[][] a, char[][] b) {
/*  830 */       return CharBigArrays.equals(a, b);
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
/*  842 */   public static final Hash.Strategy HASH_STRATEGY = new BigArrayHashStrategy(); private static final int DIGIT_MASK = 255; private static final int DIGITS_PER_ELEMENT = 2;
/*      */   private static final int RADIXSORT_NO_REC = 1024;
/*      */   
/*      */   private static void vecSwap(char[][] x, long a, long b, long n) {
/*  846 */     for (int i = 0; i < n; i++, a++, b++)
/*  847 */       swap(x, a, b); 
/*      */   }
/*      */   private static long med3(char[][] x, long a, long b, long c, CharComparator comp) {
/*  850 */     int ab = comp.compare(BigArrays.get(x, a), BigArrays.get(x, b));
/*  851 */     int ac = comp.compare(BigArrays.get(x, a), BigArrays.get(x, c));
/*  852 */     int bc = comp.compare(BigArrays.get(x, b), BigArrays.get(x, c));
/*  853 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   } private static void selectionSort(char[][] a, long from, long to, CharComparator comp) {
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
/*      */   public static void quickSort(char[][] x, long from, long to, CharComparator comp)
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
/*  903 */     char v = BigArrays.get(x, m);
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
/*      */     swap(x, b++, c--); } private static long med3(char[][] x, long a, long b, long c) {
/*  936 */     int ab = Character.compare(BigArrays.get(x, a), BigArrays.get(x, b));
/*  937 */     int ac = Character.compare(BigArrays.get(x, a), BigArrays.get(x, c));
/*  938 */     int bc = Character.compare(BigArrays.get(x, b), BigArrays.get(x, c));
/*  939 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void selectionSort(char[][] a, long from, long to) {
/*      */     long i;
/*  943 */     for (i = from; i < to - 1L; i++) {
/*  944 */       long m = i; long j;
/*  945 */       for (j = i + 1L; j < to; j++) {
/*  946 */         if (BigArrays.get(a, j) < BigArrays.get(a, m))
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
/*      */   public static void quickSort(char[][] x, CharComparator comp) {
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
/*      */   public static void quickSort(char[][] x, long from, long to) {
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
/* 1007 */     char v = BigArrays.get(x, m);
/*      */     
/* 1009 */     long a = from, b = a, c = to - 1L, d = c;
/*      */     
/*      */     int comparison;
/* 1012 */     while (b <= c && (comparison = Character.compare(BigArrays.get(x, b), v)) <= 0) {
/* 1013 */       if (comparison == 0)
/* 1014 */         swap(x, a++, b); 
/* 1015 */       b++;
/*      */     } 
/* 1017 */     while (c >= b && (comparison = Character.compare(BigArrays.get(x, c), v)) >= 0) {
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
/*      */   public static void quickSort(char[][] x) {
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
/*      */   public static long binarySearch(char[][] a, long from, long to, char key) {
/* 1081 */     to--;
/* 1082 */     while (from <= to) {
/* 1083 */       long mid = from + to >>> 1L;
/* 1084 */       char midVal = BigArrays.get(a, mid);
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
/*      */   public static long binarySearch(char[][] a, char key) {
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
/*      */   public static long binarySearch(char[][] a, long from, long to, char key, CharComparator c) {
/* 1146 */     to--;
/* 1147 */     while (from <= to) {
/* 1148 */       long mid = from + to >>> 1L;
/* 1149 */       char midVal = BigArrays.get(a, mid);
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
/*      */   public static long binarySearch(char[][] a, char key, CharComparator c) {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(char[][] a) {
/* 1219 */     radixSort(a, 0L, length(a));
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
/*      */   public static void radixSort(char[][] a, long from, long to) {
/* 1248 */     int maxLevel = 1;
/* 1249 */     int stackSize = 256;
/* 1250 */     long[] offsetStack = new long[256];
/* 1251 */     int offsetPos = 0;
/* 1252 */     long[] lengthStack = new long[256];
/* 1253 */     int lengthPos = 0;
/* 1254 */     int[] levelStack = new int[256];
/* 1255 */     int levelPos = 0;
/* 1256 */     offsetStack[offsetPos++] = from;
/* 1257 */     lengthStack[lengthPos++] = to - from;
/* 1258 */     levelStack[levelPos++] = 0;
/* 1259 */     long[] count = new long[256];
/* 1260 */     long[] pos = new long[256];
/* 1261 */     byte[][] digit = ByteBigArrays.newBigArray(to - from);
/* 1262 */     while (offsetPos > 0) {
/* 1263 */       long first = offsetStack[--offsetPos];
/* 1264 */       long length = lengthStack[--lengthPos];
/* 1265 */       int level = levelStack[--levelPos];
/* 1266 */       int signMask = 0;
/* 1267 */       if (length < 40L) {
/* 1268 */         selectionSort(a, first, first + length);
/*      */         continue;
/*      */       } 
/* 1271 */       int shift = (1 - level % 2) * 8;
/*      */ 
/*      */       
/*      */       long i;
/*      */       
/* 1276 */       for (i = length; i-- != 0L;)
/* 1277 */         BigArrays.set(digit, i, (byte)(BigArrays.get(a, first + i) >>> shift & 0xFF ^ 0x0)); 
/* 1278 */       for (i = length; i-- != 0L;) {
/* 1279 */         count[BigArrays.get(digit, i) & 0xFF] = count[BigArrays.get(digit, i) & 0xFF] + 1L;
/*      */       }
/* 1281 */       int lastUsed = -1;
/* 1282 */       long p = 0L;
/* 1283 */       for (int j = 0; j < 256; j++) {
/* 1284 */         if (count[j] != 0L) {
/* 1285 */           lastUsed = j;
/* 1286 */           if (level < 1 && count[j] > 1L) {
/*      */ 
/*      */             
/* 1289 */             offsetStack[offsetPos++] = p + first;
/* 1290 */             lengthStack[lengthPos++] = count[j];
/* 1291 */             levelStack[levelPos++] = level + 1;
/*      */           } 
/*      */         } 
/* 1294 */         pos[j] = p += count[j];
/*      */       } 
/*      */       
/* 1297 */       long end = length - count[lastUsed];
/* 1298 */       count[lastUsed] = 0L;
/*      */       
/* 1300 */       int c = -1;
/* 1301 */       for (long l1 = 0L; l1 < end; l1 += count[c], count[c] = 0L) {
/* 1302 */         char t = BigArrays.get(a, l1 + first);
/* 1303 */         c = BigArrays.get(digit, l1) & 0xFF; long d;
/* 1304 */         for (pos[c] = pos[c] - 1L; (d = pos[c] - 1L) > l1; ) {
/* 1305 */           char z = t;
/* 1306 */           int zz = c;
/* 1307 */           t = BigArrays.get(a, d + first);
/* 1308 */           c = BigArrays.get(digit, d) & 0xFF;
/* 1309 */           BigArrays.set(a, d + first, z);
/* 1310 */           BigArrays.set(digit, d, (byte)zz);
/*      */         } 
/* 1312 */         BigArrays.set(a, l1 + first, t);
/*      */       } 
/*      */     } 
/*      */   } private static void selectionSort(char[][] a, char[][] b, long from, long to) {
/*      */     long i;
/* 1317 */     for (i = from; i < to - 1L; i++) {
/* 1318 */       long m = i; long j;
/* 1319 */       for (j = i + 1L; j < to; j++) {
/* 1320 */         if (BigArrays.get(a, j) < BigArrays.get(a, m) || (BigArrays.get(a, j) == BigArrays.get(a, m) && 
/* 1321 */           BigArrays.get(b, j) < BigArrays.get(b, m)))
/* 1322 */           m = j; 
/* 1323 */       }  if (m != i) {
/* 1324 */         char t = BigArrays.get(a, i);
/* 1325 */         BigArrays.set(a, i, BigArrays.get(a, m));
/* 1326 */         BigArrays.set(a, m, t);
/* 1327 */         t = BigArrays.get(b, i);
/* 1328 */         BigArrays.set(b, i, BigArrays.get(b, m));
/* 1329 */         BigArrays.set(b, m, t);
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
/*      */   public static void radixSort(char[][] a, char[][] b) {
/* 1364 */     radixSort(a, b, 0L, length(a));
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
/*      */   public static void radixSort(char[][] a, char[][] b, long from, long to) {
/* 1402 */     int layers = 2;
/* 1403 */     if (length(a) != length(b))
/* 1404 */       throw new IllegalArgumentException("Array size mismatch."); 
/* 1405 */     int maxLevel = 3;
/* 1406 */     int stackSize = 766;
/* 1407 */     long[] offsetStack = new long[766];
/* 1408 */     int offsetPos = 0;
/* 1409 */     long[] lengthStack = new long[766];
/* 1410 */     int lengthPos = 0;
/* 1411 */     int[] levelStack = new int[766];
/* 1412 */     int levelPos = 0;
/* 1413 */     offsetStack[offsetPos++] = from;
/* 1414 */     lengthStack[lengthPos++] = to - from;
/* 1415 */     levelStack[levelPos++] = 0;
/* 1416 */     long[] count = new long[256];
/* 1417 */     long[] pos = new long[256];
/* 1418 */     byte[][] digit = ByteBigArrays.newBigArray(to - from);
/* 1419 */     while (offsetPos > 0) {
/* 1420 */       long first = offsetStack[--offsetPos];
/* 1421 */       long length = lengthStack[--lengthPos];
/* 1422 */       int level = levelStack[--levelPos];
/* 1423 */       int signMask = 0;
/* 1424 */       if (length < 40L) {
/* 1425 */         selectionSort(a, b, first, first + length);
/*      */         continue;
/*      */       } 
/* 1428 */       char[][] k = (level < 2) ? a : b;
/* 1429 */       int shift = (1 - level % 2) * 8;
/*      */ 
/*      */       
/*      */       long i;
/*      */       
/* 1434 */       for (i = length; i-- != 0L;)
/* 1435 */         BigArrays.set(digit, i, (byte)(BigArrays.get(k, first + i) >>> shift & 0xFF ^ 0x0)); 
/* 1436 */       for (i = length; i-- != 0L;) {
/* 1437 */         count[BigArrays.get(digit, i) & 0xFF] = count[BigArrays.get(digit, i) & 0xFF] + 1L;
/*      */       }
/* 1439 */       int lastUsed = -1;
/* 1440 */       long p = 0L;
/* 1441 */       for (int j = 0; j < 256; j++) {
/* 1442 */         if (count[j] != 0L) {
/* 1443 */           lastUsed = j;
/* 1444 */           if (level < 3 && count[j] > 1L) {
/* 1445 */             offsetStack[offsetPos++] = p + first;
/* 1446 */             lengthStack[lengthPos++] = count[j];
/* 1447 */             levelStack[levelPos++] = level + 1;
/*      */           } 
/*      */         } 
/* 1450 */         pos[j] = p += count[j];
/*      */       } 
/*      */       
/* 1453 */       long end = length - count[lastUsed];
/* 1454 */       count[lastUsed] = 0L;
/*      */       
/* 1456 */       int c = -1;
/* 1457 */       for (long l1 = 0L; l1 < end; l1 += count[c], count[c] = 0L) {
/* 1458 */         char t = BigArrays.get(a, l1 + first);
/* 1459 */         char u = BigArrays.get(b, l1 + first);
/* 1460 */         c = BigArrays.get(digit, l1) & 0xFF; long d;
/* 1461 */         for (pos[c] = pos[c] - 1L; (d = pos[c] - 1L) > l1; ) {
/* 1462 */           char z = t;
/* 1463 */           int zz = c;
/* 1464 */           t = BigArrays.get(a, d + first);
/* 1465 */           BigArrays.set(a, d + first, z);
/* 1466 */           z = u;
/* 1467 */           u = BigArrays.get(b, d + first);
/* 1468 */           BigArrays.set(b, d + first, z);
/* 1469 */           c = BigArrays.get(digit, d) & 0xFF;
/* 1470 */           BigArrays.set(digit, d, (byte)zz);
/*      */         } 
/* 1472 */         BigArrays.set(a, l1 + first, t);
/* 1473 */         BigArrays.set(b, l1 + first, u);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void insertionSortIndirect(long[][] perm, char[][] a, char[][] b, long from, long to) {
/* 1480 */     long i = from; while (true) { long t, j, u; if (++i < to)
/* 1481 */       { t = BigArrays.get(perm, i);
/* 1482 */         j = i;
/* 1483 */         u = BigArrays.get(perm, j - 1L); } else { break; }  if (BigArrays.get(
/* 1484 */           a, t) < BigArrays.get(a, u) || (BigArrays.get(a, t) == BigArrays.get(a, u) && 
/* 1485 */         BigArrays.get(b, t) < BigArrays.get(b, u)));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1492 */       BigArrays.set(perm, j, t); }
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
/*      */   public static void radixSortIndirect(long[][] perm, char[][] a, char[][] b, boolean stable) {
/* 1527 */     ensureSameLength(a, b);
/* 1528 */     radixSortIndirect(perm, a, b, 0L, BigArrays.length(a), stable);
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
/*      */   public static void radixSortIndirect(long[][] perm, char[][] a, char[][] b, long from, long to, boolean stable) {
/* 1568 */     if (to - from < 1024L) {
/* 1569 */       insertionSortIndirect(perm, a, b, from, to);
/*      */       return;
/*      */     } 
/* 1572 */     int layers = 2;
/* 1573 */     int maxLevel = 3;
/* 1574 */     int stackSize = 766;
/* 1575 */     int stackPos = 0;
/* 1576 */     long[] offsetStack = new long[766];
/* 1577 */     long[] lengthStack = new long[766];
/* 1578 */     int[] levelStack = new int[766];
/* 1579 */     offsetStack[stackPos] = from;
/* 1580 */     lengthStack[stackPos] = to - from;
/* 1581 */     levelStack[stackPos++] = 0;
/* 1582 */     long[] count = new long[256];
/* 1583 */     long[] pos = new long[256];
/*      */ 
/*      */     
/* 1586 */     long[][] support = stable ? LongBigArrays.newBigArray(BigArrays.length(perm)) : null;
/* 1587 */     while (stackPos > 0) {
/* 1588 */       long first = offsetStack[--stackPos];
/* 1589 */       long length = lengthStack[stackPos];
/* 1590 */       int level = levelStack[stackPos];
/* 1591 */       int signMask = 0;
/* 1592 */       char[][] k = (level < 2) ? a : b;
/* 1593 */       int shift = (1 - level % 2) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1598 */       for (long i = first + length; i-- != first;) {
/* 1599 */         count[BigArrays.get(k, BigArrays.get(perm, i)) >>> shift & 0xFF ^ 0x0] = count[BigArrays.get(k, BigArrays.get(perm, i)) >>> shift & 0xFF ^ 0x0] + 1L;
/*      */       }
/* 1601 */       int lastUsed = -1;
/* 1602 */       long p = stable ? 0L : first;
/* 1603 */       for (int j = 0; j < 256; j++) {
/* 1604 */         if (count[j] != 0L)
/* 1605 */           lastUsed = j; 
/* 1606 */         pos[j] = p += count[j];
/*      */       } 
/* 1608 */       if (stable) {
/* 1609 */         for (long l = first + length; l-- != first;)
/* 1610 */           BigArrays.set(support, pos[
/* 1611 */                 BigArrays.get(k, BigArrays.get(perm, l)) >>> shift & 0xFF ^ 0x0] = pos[BigArrays.get(k, BigArrays.get(perm, l)) >>> shift & 0xFF ^ 0x0] - 1L, 
/* 1612 */               BigArrays.get(perm, l)); 
/* 1613 */         BigArrays.copy(support, 0L, perm, first, length);
/* 1614 */         p = first;
/* 1615 */         for (int m = 0; m < 256; m++) {
/* 1616 */           if (level < 3 && count[m] > 1L) {
/* 1617 */             if (count[m] < 1024L) {
/* 1618 */               insertionSortIndirect(perm, a, b, p, p + count[m]);
/*      */             } else {
/* 1620 */               offsetStack[stackPos] = p;
/* 1621 */               lengthStack[stackPos] = count[m];
/* 1622 */               levelStack[stackPos++] = level + 1;
/*      */             } 
/*      */           }
/* 1625 */           p += count[m];
/*      */         } 
/* 1627 */         Arrays.fill(count, 0L); continue;
/*      */       } 
/* 1629 */       long end = first + length - count[lastUsed];
/*      */       
/* 1631 */       int c = -1;
/* 1632 */       for (long l1 = first; l1 <= end; l1 += count[c], count[c] = 0L) {
/* 1633 */         long t = BigArrays.get(perm, l1);
/* 1634 */         c = BigArrays.get(k, t) >>> shift & 0xFF ^ 0x0;
/* 1635 */         if (l1 < end) {
/* 1636 */           long d; for (pos[c] = pos[c] - 1L; (d = pos[c] - 1L) > l1; ) {
/* 1637 */             long z = t;
/* 1638 */             t = BigArrays.get(perm, d);
/* 1639 */             BigArrays.set(perm, d, z);
/* 1640 */             c = BigArrays.get(k, t) >>> shift & 0xFF ^ 0x0;
/*      */           } 
/* 1642 */           BigArrays.set(perm, l1, t);
/*      */         } 
/* 1644 */         if (level < 3 && count[c] > 1L) {
/* 1645 */           if (count[c] < 1024L) {
/* 1646 */             insertionSortIndirect(perm, a, b, l1, l1 + count[c]);
/*      */           } else {
/* 1648 */             offsetStack[stackPos] = l1;
/* 1649 */             lengthStack[stackPos] = count[c];
/* 1650 */             levelStack[stackPos++] = level + 1;
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
/*      */   public static char[][] shuffle(char[][] a, long from, long to, Random random) {
/* 1672 */     for (long i = to - from; i-- != 0L; ) {
/* 1673 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 1674 */       char t = BigArrays.get(a, from + i);
/* 1675 */       BigArrays.set(a, from + i, BigArrays.get(a, from + p));
/* 1676 */       BigArrays.set(a, from + p, t);
/*      */     } 
/* 1678 */     return a;
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
/*      */   public static char[][] shuffle(char[][] a, Random random) {
/* 1691 */     for (long i = length(a); i-- != 0L; ) {
/* 1692 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 1693 */       char t = BigArrays.get(a, i);
/* 1694 */       BigArrays.set(a, i, BigArrays.get(a, p));
/* 1695 */       BigArrays.set(a, p, t);
/*      */     } 
/* 1697 */     return a;
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\CharBigArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */