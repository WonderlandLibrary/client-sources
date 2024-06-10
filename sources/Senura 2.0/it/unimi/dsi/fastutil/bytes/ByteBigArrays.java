/*      */ package it.unimi.dsi.fastutil.bytes;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.BigArrays;
/*      */ import it.unimi.dsi.fastutil.Hash;
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
/*      */ public final class ByteBigArrays
/*      */ {
/*   62 */   public static final byte[][] EMPTY_BIG_ARRAY = new byte[0][];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   71 */   public static final byte[][] DEFAULT_EMPTY_BIG_ARRAY = new byte[0][];
/*      */ 
/*      */ 
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
/*      */   public static byte get(byte[][] array, long index) {
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
/*      */   public static void set(byte[][] array, long index, byte value) {
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
/*      */   public static void swap(byte[][] array, long first, long second) {
/*  117 */     byte t = array[BigArrays.segment(first)][BigArrays.displacement(first)];
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void add(byte[][] array, long index, byte incr) {
/*  136 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = (byte)(array[BigArrays.segment(index)][BigArrays.displacement(index)] + incr);
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
/*      */   public static void mul(byte[][] array, long index, byte factor) {
/*  153 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = (byte)(array[BigArrays.segment(index)][BigArrays.displacement(index)] * factor);
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
/*      */   public static void incr(byte[][] array, long index) {
/*  167 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = (byte)(array[BigArrays.segment(index)][BigArrays.displacement(index)] + 1);
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
/*      */   public static void decr(byte[][] array, long index) {
/*  181 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = (byte)(array[BigArrays.segment(index)][BigArrays.displacement(index)] - 1);
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
/*      */   public static long length(byte[][] array) {
/*  194 */     int length = array.length;
/*  195 */     return (length == 0) ? 0L : (BigArrays.start(length - 1) + (array[length - 1]).length);
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
/*      */   public static void copy(byte[][] srcArray, long srcPos, byte[][] destArray, long destPos, long length) {
/*  218 */     if (destPos <= srcPos) {
/*  219 */       int srcSegment = BigArrays.segment(srcPos);
/*  220 */       int destSegment = BigArrays.segment(destPos);
/*  221 */       int srcDispl = BigArrays.displacement(srcPos);
/*  222 */       int destDispl = BigArrays.displacement(destPos);
/*      */       
/*  224 */       while (length > 0L) {
/*  225 */         int l = (int)Math.min(length, 
/*  226 */             Math.min((srcArray[srcSegment]).length - srcDispl, (destArray[destSegment]).length - destDispl));
/*  227 */         System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
/*  228 */         if ((srcDispl += l) == 134217728) {
/*  229 */           srcDispl = 0;
/*  230 */           srcSegment++;
/*      */         } 
/*  232 */         if ((destDispl += l) == 134217728) {
/*  233 */           destDispl = 0;
/*  234 */           destSegment++;
/*      */         } 
/*  236 */         length -= l;
/*      */       } 
/*      */     } else {
/*  239 */       int srcSegment = BigArrays.segment(srcPos + length);
/*  240 */       int destSegment = BigArrays.segment(destPos + length);
/*  241 */       int srcDispl = BigArrays.displacement(srcPos + length);
/*  242 */       int destDispl = BigArrays.displacement(destPos + length);
/*      */       
/*  244 */       while (length > 0L) {
/*  245 */         if (srcDispl == 0) {
/*  246 */           srcDispl = 134217728;
/*  247 */           srcSegment--;
/*      */         } 
/*  249 */         if (destDispl == 0) {
/*  250 */           destDispl = 134217728;
/*  251 */           destSegment--;
/*      */         } 
/*  253 */         int l = (int)Math.min(length, Math.min(srcDispl, destDispl));
/*  254 */         System.arraycopy(srcArray[srcSegment], srcDispl - l, destArray[destSegment], destDispl - l, l);
/*  255 */         srcDispl -= l;
/*  256 */         destDispl -= l;
/*  257 */         length -= l;
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
/*      */   public static void copyFromBig(byte[][] srcArray, long srcPos, byte[] destArray, int destPos, int length) {
/*  281 */     int srcSegment = BigArrays.segment(srcPos);
/*  282 */     int srcDispl = BigArrays.displacement(srcPos);
/*      */     
/*  284 */     while (length > 0) {
/*  285 */       int l = Math.min((srcArray[srcSegment]).length - srcDispl, length);
/*  286 */       System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
/*  287 */       if ((srcDispl += l) == 134217728) {
/*  288 */         srcDispl = 0;
/*  289 */         srcSegment++;
/*      */       } 
/*  291 */       destPos += l;
/*  292 */       length -= l;
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
/*      */   public static void copyToBig(byte[] srcArray, int srcPos, byte[][] destArray, long destPos, long length) {
/*  315 */     int destSegment = BigArrays.segment(destPos);
/*  316 */     int destDispl = BigArrays.displacement(destPos);
/*      */     
/*  318 */     while (length > 0L) {
/*  319 */       int l = (int)Math.min(((destArray[destSegment]).length - destDispl), length);
/*  320 */       System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
/*  321 */       if ((destDispl += l) == 134217728) {
/*  322 */         destDispl = 0;
/*  323 */         destSegment++;
/*      */       } 
/*  325 */       srcPos += l;
/*  326 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[][] newBigArray(long length) {
/*  337 */     if (length == 0L)
/*  338 */       return EMPTY_BIG_ARRAY; 
/*  339 */     BigArrays.ensureLength(length);
/*  340 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  341 */     byte[][] base = new byte[baseLength][];
/*  342 */     int residual = (int)(length & 0x7FFFFFFL);
/*  343 */     if (residual != 0) {
/*  344 */       for (int i = 0; i < baseLength - 1; i++)
/*  345 */         base[i] = new byte[134217728]; 
/*  346 */       base[baseLength - 1] = new byte[residual];
/*      */     } else {
/*  348 */       for (int i = 0; i < baseLength; i++)
/*  349 */         base[i] = new byte[134217728]; 
/*  350 */     }  return base;
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
/*      */   public static byte[][] wrap(byte[] array) {
/*  367 */     if (array.length == 0)
/*  368 */       return EMPTY_BIG_ARRAY; 
/*  369 */     if (array.length <= 134217728)
/*  370 */       return new byte[][] { array }; 
/*  371 */     byte[][] bigArray = newBigArray(array.length);
/*  372 */     for (int i = 0; i < bigArray.length; i++)
/*  373 */       System.arraycopy(array, (int)BigArrays.start(i), bigArray[i], 0, (bigArray[i]).length); 
/*  374 */     return bigArray;
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
/*      */   public static byte[][] ensureCapacity(byte[][] array, long length) {
/*  400 */     return ensureCapacity(array, length, length(array));
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
/*      */   public static byte[][] forceCapacity(byte[][] array, long length, long preserve) {
/*  425 */     BigArrays.ensureLength(length);
/*      */     
/*  427 */     int valid = array.length - ((array.length == 0 || (array.length > 0 && (array[array.length - 1]).length == 134217728)) ? 0 : 1);
/*  428 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  429 */     byte[][] base = Arrays.<byte[]>copyOf(array, baseLength);
/*  430 */     int residual = (int)(length & 0x7FFFFFFL);
/*  431 */     if (residual != 0) {
/*  432 */       for (int i = valid; i < baseLength - 1; i++)
/*  433 */         base[i] = new byte[134217728]; 
/*  434 */       base[baseLength - 1] = new byte[residual];
/*      */     } else {
/*  436 */       for (int i = valid; i < baseLength; i++)
/*  437 */         base[i] = new byte[134217728]; 
/*  438 */     }  if (preserve - valid * 134217728L > 0L) {
/*  439 */       copy(array, valid * 134217728L, base, valid * 134217728L, preserve - valid * 134217728L);
/*      */     }
/*  441 */     return base;
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
/*      */   public static byte[][] ensureCapacity(byte[][] array, long length, long preserve) {
/*  467 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
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
/*      */   public static byte[][] grow(byte[][] array, long length) {
/*  496 */     long oldLength = length(array);
/*  497 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
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
/*      */   public static byte[][] grow(byte[][] array, long length, long preserve) {
/*  529 */     long oldLength = length(array);
/*  530 */     return (length > oldLength) ? 
/*  531 */       ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : 
/*  532 */       array;
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
/*      */   public static byte[][] trim(byte[][] array, long length) {
/*  554 */     BigArrays.ensureLength(length);
/*  555 */     long oldLength = length(array);
/*  556 */     if (length >= oldLength)
/*  557 */       return array; 
/*  558 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  559 */     byte[][] base = Arrays.<byte[]>copyOf(array, baseLength);
/*  560 */     int residual = (int)(length & 0x7FFFFFFL);
/*  561 */     if (residual != 0)
/*  562 */       base[baseLength - 1] = ByteArrays.trim(base[baseLength - 1], residual); 
/*  563 */     return base;
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
/*      */   public static byte[][] setLength(byte[][] array, long length) {
/*  588 */     long oldLength = length(array);
/*  589 */     if (length == oldLength)
/*  590 */       return array; 
/*  591 */     if (length < oldLength)
/*  592 */       return trim(array, length); 
/*  593 */     return ensureCapacity(array, length);
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
/*      */   public static byte[][] copy(byte[][] array, long offset, long length) {
/*  611 */     ensureOffsetLength(array, offset, length);
/*  612 */     byte[][] a = newBigArray(length);
/*  613 */     copy(array, offset, a, 0L, length);
/*  614 */     return a;
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
/*      */   public static byte[][] copy(byte[][] array) {
/*  627 */     byte[][] base = (byte[][])array.clone();
/*  628 */     for (int i = base.length; i-- != 0;)
/*  629 */       base[i] = (byte[])array[i].clone(); 
/*  630 */     return base;
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
/*      */   public static void fill(byte[][] array, byte value) {
/*  648 */     for (int i = array.length; i-- != 0;) {
/*  649 */       Arrays.fill(array[i], value);
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
/*      */   public static void fill(byte[][] array, long from, long to, byte value) {
/*  673 */     long length = length(array);
/*  674 */     BigArrays.ensureFromTo(length, from, to);
/*  675 */     if (length == 0L)
/*      */       return; 
/*  677 */     int fromSegment = BigArrays.segment(from);
/*  678 */     int toSegment = BigArrays.segment(to);
/*  679 */     int fromDispl = BigArrays.displacement(from);
/*  680 */     int toDispl = BigArrays.displacement(to);
/*  681 */     if (fromSegment == toSegment) {
/*  682 */       Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
/*      */       return;
/*      */     } 
/*  685 */     if (toDispl != 0)
/*  686 */       Arrays.fill(array[toSegment], 0, toDispl, value); 
/*  687 */     while (--toSegment > fromSegment)
/*  688 */       Arrays.fill(array[toSegment], value); 
/*  689 */     Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
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
/*      */   public static boolean equals(byte[][] a1, byte[][] a2) {
/*  709 */     if (length(a1) != length(a2))
/*  710 */       return false; 
/*  711 */     int i = a1.length;
/*      */     
/*  713 */     while (i-- != 0) {
/*  714 */       byte[] t = a1[i];
/*  715 */       byte[] u = a2[i];
/*  716 */       int j = t.length;
/*  717 */       while (j-- != 0) {
/*  718 */         if (t[j] != u[j])
/*  719 */           return false; 
/*      */       } 
/*  721 */     }  return true;
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
/*      */   public static String toString(byte[][] a) {
/*  740 */     if (a == null)
/*  741 */       return "null"; 
/*  742 */     long last = length(a) - 1L;
/*  743 */     if (last == -1L)
/*  744 */       return "[]"; 
/*  745 */     StringBuilder b = new StringBuilder();
/*  746 */     b.append('['); long i;
/*  747 */     for (i = 0L;; i++) {
/*  748 */       b.append(String.valueOf(BigArrays.get(a, i)));
/*  749 */       if (i == last)
/*  750 */         return b.append(']').toString(); 
/*  751 */       b.append(", ");
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
/*      */   public static void ensureFromTo(byte[][] a, long from, long to) {
/*  777 */     BigArrays.ensureFromTo(length(a), from, to);
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
/*      */   public static void ensureOffsetLength(byte[][] a, long offset, long length) {
/*  801 */     BigArrays.ensureOffsetLength(length(a), offset, length);
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
/*      */   public static void ensureSameLength(byte[][] a, byte[][] b) {
/*  817 */     if (length(a) != length(b))
/*  818 */       throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b)); 
/*      */   }
/*      */   private static final class BigArrayHashStrategy implements Hash.Strategy<byte[][]>, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private BigArrayHashStrategy() {}
/*      */     
/*      */     public int hashCode(byte[][] o) {
/*  825 */       return Arrays.deepHashCode((Object[])o);
/*      */     }
/*      */     
/*      */     public boolean equals(byte[][] a, byte[][] b) {
/*  829 */       return ByteBigArrays.equals(a, b);
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
/*  841 */   public static final Hash.Strategy HASH_STRATEGY = new BigArrayHashStrategy(); private static final int DIGIT_MASK = 255; private static final int DIGITS_PER_ELEMENT = 1;
/*      */   private static final int RADIXSORT_NO_REC = 1024;
/*      */   
/*      */   private static void vecSwap(byte[][] x, long a, long b, long n) {
/*  845 */     for (int i = 0; i < n; i++, a++, b++)
/*  846 */       swap(x, a, b); 
/*      */   }
/*      */   private static long med3(byte[][] x, long a, long b, long c, ByteComparator comp) {
/*  849 */     int ab = comp.compare(BigArrays.get(x, a), BigArrays.get(x, b));
/*  850 */     int ac = comp.compare(BigArrays.get(x, a), BigArrays.get(x, c));
/*  851 */     int bc = comp.compare(BigArrays.get(x, b), BigArrays.get(x, c));
/*  852 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   } private static void selectionSort(byte[][] a, long from, long to, ByteComparator comp) {
/*      */     long i;
/*  855 */     for (i = from; i < to - 1L; i++) {
/*  856 */       long m = i; long j;
/*  857 */       for (j = i + 1L; j < to; j++) {
/*  858 */         if (comp.compare(BigArrays.get(a, j), BigArrays.get(a, m)) < 0)
/*  859 */           m = j; 
/*  860 */       }  if (m != i) {
/*  861 */         swap(a, i, m);
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
/*      */   public static void quickSort(byte[][] x, long from, long to, ByteComparator comp)
/*      */   {
/*  883 */     long len = to - from;
/*      */     
/*  885 */     if (len < 7L) {
/*  886 */       selectionSort(x, from, to, comp);
/*      */       
/*      */       return;
/*      */     } 
/*  890 */     long m = from + len / 2L;
/*  891 */     if (len > 7L) {
/*  892 */       long l = from;
/*  893 */       long n = to - 1L;
/*  894 */       if (len > 40L) {
/*  895 */         long s = len / 8L;
/*  896 */         l = med3(x, l, l + s, l + 2L * s, comp);
/*  897 */         m = med3(x, m - s, m, m + s, comp);
/*  898 */         n = med3(x, n - 2L * s, n - s, n, comp);
/*      */       } 
/*  900 */       m = med3(x, l, m, n, comp);
/*      */     } 
/*  902 */     byte v = BigArrays.get(x, m);
/*      */     
/*  904 */     long a = from, b = a, c = to - 1L, d = c;
/*      */     
/*      */     int comparison;
/*  907 */     while (b <= c && (comparison = comp.compare(BigArrays.get(x, b), v)) <= 0) {
/*  908 */       if (comparison == 0)
/*  909 */         swap(x, a++, b); 
/*  910 */       b++;
/*      */     } 
/*  912 */     while (c >= b && (comparison = comp.compare(BigArrays.get(x, c), v)) >= 0) {
/*  913 */       if (comparison == 0)
/*  914 */         swap(x, c, d--); 
/*  915 */       c--;
/*      */     } 
/*  917 */     if (b > c) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  922 */       long n = to;
/*  923 */       long s = Math.min(a - from, b - a);
/*  924 */       vecSwap(x, from, b - s, s);
/*  925 */       s = Math.min(d - c, n - d - 1L);
/*  926 */       vecSwap(x, b, n - s, s);
/*      */       
/*  928 */       if ((s = b - a) > 1L)
/*  929 */         quickSort(x, from, from + s, comp); 
/*  930 */       if ((s = d - c) > 1L)
/*  931 */         quickSort(x, n - s, n, comp); 
/*      */       return;
/*      */     } 
/*      */     swap(x, b++, c--); } private static long med3(byte[][] x, long a, long b, long c) {
/*  935 */     int ab = Byte.compare(BigArrays.get(x, a), BigArrays.get(x, b));
/*  936 */     int ac = Byte.compare(BigArrays.get(x, a), BigArrays.get(x, c));
/*  937 */     int bc = Byte.compare(BigArrays.get(x, b), BigArrays.get(x, c));
/*  938 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void selectionSort(byte[][] a, long from, long to) {
/*      */     long i;
/*  942 */     for (i = from; i < to - 1L; i++) {
/*  943 */       long m = i; long j;
/*  944 */       for (j = i + 1L; j < to; j++) {
/*  945 */         if (BigArrays.get(a, j) < BigArrays.get(a, m))
/*  946 */           m = j; 
/*  947 */       }  if (m != i) {
/*  948 */         swap(a, i, m);
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
/*      */   public static void quickSort(byte[][] x, ByteComparator comp) {
/*  967 */     quickSort(x, 0L, length(x), comp);
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
/*      */   public static void quickSort(byte[][] x, long from, long to) {
/*  987 */     long len = to - from;
/*      */     
/*  989 */     if (len < 7L) {
/*  990 */       selectionSort(x, from, to);
/*      */       
/*      */       return;
/*      */     } 
/*  994 */     long m = from + len / 2L;
/*  995 */     if (len > 7L) {
/*  996 */       long l = from;
/*  997 */       long n = to - 1L;
/*  998 */       if (len > 40L) {
/*  999 */         long s = len / 8L;
/* 1000 */         l = med3(x, l, l + s, l + 2L * s);
/* 1001 */         m = med3(x, m - s, m, m + s);
/* 1002 */         n = med3(x, n - 2L * s, n - s, n);
/*      */       } 
/* 1004 */       m = med3(x, l, m, n);
/*      */     } 
/* 1006 */     byte v = BigArrays.get(x, m);
/*      */     
/* 1008 */     long a = from, b = a, c = to - 1L, d = c;
/*      */     
/*      */     int comparison;
/* 1011 */     while (b <= c && (comparison = Byte.compare(BigArrays.get(x, b), v)) <= 0) {
/* 1012 */       if (comparison == 0)
/* 1013 */         swap(x, a++, b); 
/* 1014 */       b++;
/*      */     } 
/* 1016 */     while (c >= b && (comparison = Byte.compare(BigArrays.get(x, c), v)) >= 0) {
/* 1017 */       if (comparison == 0)
/* 1018 */         swap(x, c, d--); 
/* 1019 */       c--;
/*      */     } 
/* 1021 */     if (b > c) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1026 */       long n = to;
/* 1027 */       long s = Math.min(a - from, b - a);
/* 1028 */       vecSwap(x, from, b - s, s);
/* 1029 */       s = Math.min(d - c, n - d - 1L);
/* 1030 */       vecSwap(x, b, n - s, s);
/*      */       
/* 1032 */       if ((s = b - a) > 1L)
/* 1033 */         quickSort(x, from, from + s); 
/* 1034 */       if ((s = d - c) > 1L) {
/* 1035 */         quickSort(x, n - s, n);
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
/*      */   public static void quickSort(byte[][] x) {
/* 1050 */     quickSort(x, 0L, length(x));
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
/*      */   public static long binarySearch(byte[][] a, long from, long to, byte key) {
/* 1080 */     to--;
/* 1081 */     while (from <= to) {
/* 1082 */       long mid = from + to >>> 1L;
/* 1083 */       byte midVal = BigArrays.get(a, mid);
/* 1084 */       if (midVal < key) {
/* 1085 */         from = mid + 1L; continue;
/* 1086 */       }  if (midVal > key) {
/* 1087 */         to = mid - 1L; continue;
/*      */       } 
/* 1089 */       return mid;
/*      */     } 
/* 1091 */     return -(from + 1L);
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
/*      */   public static long binarySearch(byte[][] a, byte key) {
/* 1114 */     return binarySearch(a, 0L, length(a), key);
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
/*      */   public static long binarySearch(byte[][] a, long from, long to, byte key, ByteComparator c) {
/* 1145 */     to--;
/* 1146 */     while (from <= to) {
/* 1147 */       long mid = from + to >>> 1L;
/* 1148 */       byte midVal = BigArrays.get(a, mid);
/* 1149 */       int cmp = c.compare(midVal, key);
/* 1150 */       if (cmp < 0) {
/* 1151 */         from = mid + 1L; continue;
/* 1152 */       }  if (cmp > 0) {
/* 1153 */         to = mid - 1L; continue;
/*      */       } 
/* 1155 */       return mid;
/*      */     } 
/* 1157 */     return -(from + 1L);
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
/*      */   public static long binarySearch(byte[][] a, byte key, ByteComparator c) {
/* 1183 */     return binarySearch(a, 0L, length(a), key, c);
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
/*      */   public static void radixSort(byte[][] a) {
/* 1218 */     radixSort(a, 0L, length(a));
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
/*      */   public static void radixSort(byte[][] a, long from, long to) {
/* 1247 */     int maxLevel = 0;
/* 1248 */     int stackSize = 1;
/* 1249 */     long[] offsetStack = new long[1];
/* 1250 */     int offsetPos = 0;
/* 1251 */     long[] lengthStack = new long[1];
/* 1252 */     int lengthPos = 0;
/* 1253 */     int[] levelStack = new int[1];
/* 1254 */     int levelPos = 0;
/* 1255 */     offsetStack[offsetPos++] = from;
/* 1256 */     lengthStack[lengthPos++] = to - from;
/* 1257 */     levelStack[levelPos++] = 0;
/* 1258 */     long[] count = new long[256];
/* 1259 */     long[] pos = new long[256];
/* 1260 */     byte[][] digit = newBigArray(to - from);
/* 1261 */     while (offsetPos > 0) {
/* 1262 */       long first = offsetStack[--offsetPos];
/* 1263 */       long length = lengthStack[--lengthPos];
/* 1264 */       int level = levelStack[--levelPos];
/* 1265 */       int signMask = (level % 1 == 0) ? 128 : 0;
/* 1266 */       if (length < 40L) {
/* 1267 */         selectionSort(a, first, first + length);
/*      */         continue;
/*      */       } 
/* 1270 */       int shift = (0 - level % 1) * 8;
/*      */ 
/*      */       
/*      */       long i;
/*      */       
/* 1275 */       for (i = length; i-- != 0L;)
/* 1276 */         BigArrays.set(digit, i, (byte)(BigArrays.get(a, first + i) >>> shift & 0xFF ^ signMask)); 
/* 1277 */       for (i = length; i-- != 0L;) {
/* 1278 */         count[BigArrays.get(digit, i) & 0xFF] = count[BigArrays.get(digit, i) & 0xFF] + 1L;
/*      */       }
/* 1280 */       int lastUsed = -1;
/* 1281 */       long p = 0L;
/* 1282 */       for (int j = 0; j < 256; j++) {
/* 1283 */         if (count[j] != 0L) {
/* 1284 */           lastUsed = j;
/* 1285 */           if (level < 0 && count[j] > 1L) {
/*      */ 
/*      */             
/* 1288 */             offsetStack[offsetPos++] = p + first;
/* 1289 */             lengthStack[lengthPos++] = count[j];
/* 1290 */             levelStack[levelPos++] = level + 1;
/*      */           } 
/*      */         } 
/* 1293 */         pos[j] = p += count[j];
/*      */       } 
/*      */       
/* 1296 */       long end = length - count[lastUsed];
/* 1297 */       count[lastUsed] = 0L;
/*      */       
/* 1299 */       int c = -1;
/* 1300 */       for (long l1 = 0L; l1 < end; l1 += count[c], count[c] = 0L) {
/* 1301 */         byte t = BigArrays.get(a, l1 + first);
/* 1302 */         c = BigArrays.get(digit, l1) & 0xFF; long d;
/* 1303 */         for (pos[c] = pos[c] - 1L; (d = pos[c] - 1L) > l1; ) {
/* 1304 */           byte z = t;
/* 1305 */           int zz = c;
/* 1306 */           t = BigArrays.get(a, d + first);
/* 1307 */           c = BigArrays.get(digit, d) & 0xFF;
/* 1308 */           BigArrays.set(a, d + first, z);
/* 1309 */           BigArrays.set(digit, d, (byte)zz);
/*      */         } 
/* 1311 */         BigArrays.set(a, l1 + first, t);
/*      */       } 
/*      */     } 
/*      */   } private static void selectionSort(byte[][] a, byte[][] b, long from, long to) {
/*      */     long i;
/* 1316 */     for (i = from; i < to - 1L; i++) {
/* 1317 */       long m = i; long j;
/* 1318 */       for (j = i + 1L; j < to; j++) {
/* 1319 */         if (BigArrays.get(a, j) < BigArrays.get(a, m) || (BigArrays.get(a, j) == BigArrays.get(a, m) && 
/* 1320 */           BigArrays.get(b, j) < BigArrays.get(b, m)))
/* 1321 */           m = j; 
/* 1322 */       }  if (m != i) {
/* 1323 */         byte t = BigArrays.get(a, i);
/* 1324 */         BigArrays.set(a, i, BigArrays.get(a, m));
/* 1325 */         BigArrays.set(a, m, t);
/* 1326 */         t = BigArrays.get(b, i);
/* 1327 */         BigArrays.set(b, i, BigArrays.get(b, m));
/* 1328 */         BigArrays.set(b, m, t);
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
/*      */   public static void radixSort(byte[][] a, byte[][] b) {
/* 1363 */     radixSort(a, b, 0L, length(a));
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
/*      */   public static void radixSort(byte[][] a, byte[][] b, long from, long to) {
/* 1401 */     int layers = 2;
/* 1402 */     if (length(a) != length(b))
/* 1403 */       throw new IllegalArgumentException("Array size mismatch."); 
/* 1404 */     int maxLevel = 1;
/* 1405 */     int stackSize = 256;
/* 1406 */     long[] offsetStack = new long[256];
/* 1407 */     int offsetPos = 0;
/* 1408 */     long[] lengthStack = new long[256];
/* 1409 */     int lengthPos = 0;
/* 1410 */     int[] levelStack = new int[256];
/* 1411 */     int levelPos = 0;
/* 1412 */     offsetStack[offsetPos++] = from;
/* 1413 */     lengthStack[lengthPos++] = to - from;
/* 1414 */     levelStack[levelPos++] = 0;
/* 1415 */     long[] count = new long[256];
/* 1416 */     long[] pos = new long[256];
/* 1417 */     byte[][] digit = newBigArray(to - from);
/* 1418 */     while (offsetPos > 0) {
/* 1419 */       long first = offsetStack[--offsetPos];
/* 1420 */       long length = lengthStack[--lengthPos];
/* 1421 */       int level = levelStack[--levelPos];
/* 1422 */       int signMask = (level % 1 == 0) ? 128 : 0;
/* 1423 */       if (length < 40L) {
/* 1424 */         selectionSort(a, b, first, first + length);
/*      */         continue;
/*      */       } 
/* 1427 */       byte[][] k = (level < 1) ? a : b;
/* 1428 */       int shift = (0 - level % 1) * 8;
/*      */ 
/*      */       
/*      */       long i;
/*      */       
/* 1433 */       for (i = length; i-- != 0L;)
/* 1434 */         BigArrays.set(digit, i, (byte)(BigArrays.get(k, first + i) >>> shift & 0xFF ^ signMask)); 
/* 1435 */       for (i = length; i-- != 0L;) {
/* 1436 */         count[BigArrays.get(digit, i) & 0xFF] = count[BigArrays.get(digit, i) & 0xFF] + 1L;
/*      */       }
/* 1438 */       int lastUsed = -1;
/* 1439 */       long p = 0L;
/* 1440 */       for (int j = 0; j < 256; j++) {
/* 1441 */         if (count[j] != 0L) {
/* 1442 */           lastUsed = j;
/* 1443 */           if (level < 1 && count[j] > 1L) {
/* 1444 */             offsetStack[offsetPos++] = p + first;
/* 1445 */             lengthStack[lengthPos++] = count[j];
/* 1446 */             levelStack[levelPos++] = level + 1;
/*      */           } 
/*      */         } 
/* 1449 */         pos[j] = p += count[j];
/*      */       } 
/*      */       
/* 1452 */       long end = length - count[lastUsed];
/* 1453 */       count[lastUsed] = 0L;
/*      */       
/* 1455 */       int c = -1;
/* 1456 */       for (long l1 = 0L; l1 < end; l1 += count[c], count[c] = 0L) {
/* 1457 */         byte t = BigArrays.get(a, l1 + first);
/* 1458 */         byte u = BigArrays.get(b, l1 + first);
/* 1459 */         c = BigArrays.get(digit, l1) & 0xFF; long d;
/* 1460 */         for (pos[c] = pos[c] - 1L; (d = pos[c] - 1L) > l1; ) {
/* 1461 */           byte z = t;
/* 1462 */           int zz = c;
/* 1463 */           t = BigArrays.get(a, d + first);
/* 1464 */           BigArrays.set(a, d + first, z);
/* 1465 */           z = u;
/* 1466 */           u = BigArrays.get(b, d + first);
/* 1467 */           BigArrays.set(b, d + first, z);
/* 1468 */           c = BigArrays.get(digit, d) & 0xFF;
/* 1469 */           BigArrays.set(digit, d, (byte)zz);
/*      */         } 
/* 1471 */         BigArrays.set(a, l1 + first, t);
/* 1472 */         BigArrays.set(b, l1 + first, u);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void insertionSortIndirect(long[][] perm, byte[][] a, byte[][] b, long from, long to) {
/* 1479 */     long i = from; while (true) { long t, j, u; if (++i < to)
/* 1480 */       { t = BigArrays.get(perm, i);
/* 1481 */         j = i;
/* 1482 */         u = BigArrays.get(perm, j - 1L); } else { break; }  if (BigArrays.get(
/* 1483 */           a, t) < BigArrays.get(a, u) || (BigArrays.get(a, t) == BigArrays.get(a, u) && 
/* 1484 */         BigArrays.get(b, t) < BigArrays.get(b, u)));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1491 */       BigArrays.set(perm, j, t); }
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
/*      */   public static void radixSortIndirect(long[][] perm, byte[][] a, byte[][] b, boolean stable) {
/* 1526 */     ensureSameLength(a, b);
/* 1527 */     radixSortIndirect(perm, a, b, 0L, BigArrays.length(a), stable);
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
/*      */   public static void radixSortIndirect(long[][] perm, byte[][] a, byte[][] b, long from, long to, boolean stable) {
/* 1567 */     if (to - from < 1024L) {
/* 1568 */       insertionSortIndirect(perm, a, b, from, to);
/*      */       return;
/*      */     } 
/* 1571 */     int layers = 2;
/* 1572 */     int maxLevel = 1;
/* 1573 */     int stackSize = 256;
/* 1574 */     int stackPos = 0;
/* 1575 */     long[] offsetStack = new long[256];
/* 1576 */     long[] lengthStack = new long[256];
/* 1577 */     int[] levelStack = new int[256];
/* 1578 */     offsetStack[stackPos] = from;
/* 1579 */     lengthStack[stackPos] = to - from;
/* 1580 */     levelStack[stackPos++] = 0;
/* 1581 */     long[] count = new long[256];
/* 1582 */     long[] pos = new long[256];
/*      */ 
/*      */     
/* 1585 */     long[][] support = stable ? LongBigArrays.newBigArray(BigArrays.length(perm)) : null;
/* 1586 */     while (stackPos > 0) {
/* 1587 */       long first = offsetStack[--stackPos];
/* 1588 */       long length = lengthStack[stackPos];
/* 1589 */       int level = levelStack[stackPos];
/* 1590 */       int signMask = (level % 1 == 0) ? 128 : 0;
/* 1591 */       byte[][] k = (level < 1) ? a : b;
/* 1592 */       int shift = (0 - level % 1) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1597 */       for (long i = first + length; i-- != first;) {
/* 1598 */         count[BigArrays.get(k, BigArrays.get(perm, i)) >>> shift & 0xFF ^ signMask] = count[BigArrays.get(k, BigArrays.get(perm, i)) >>> shift & 0xFF ^ signMask] + 1L;
/*      */       }
/* 1600 */       int lastUsed = -1;
/* 1601 */       long p = stable ? 0L : first;
/* 1602 */       for (int j = 0; j < 256; j++) {
/* 1603 */         if (count[j] != 0L)
/* 1604 */           lastUsed = j; 
/* 1605 */         pos[j] = p += count[j];
/*      */       } 
/* 1607 */       if (stable) {
/* 1608 */         for (long l = first + length; l-- != first;)
/* 1609 */           BigArrays.set(support, pos[
/* 1610 */                 BigArrays.get(k, BigArrays.get(perm, l)) >>> shift & 0xFF ^ signMask] = pos[BigArrays.get(k, BigArrays.get(perm, l)) >>> shift & 0xFF ^ signMask] - 1L, 
/* 1611 */               BigArrays.get(perm, l)); 
/* 1612 */         BigArrays.copy(support, 0L, perm, first, length);
/* 1613 */         p = first;
/* 1614 */         for (int m = 0; m < 256; m++) {
/* 1615 */           if (level < 1 && count[m] > 1L) {
/* 1616 */             if (count[m] < 1024L) {
/* 1617 */               insertionSortIndirect(perm, a, b, p, p + count[m]);
/*      */             } else {
/* 1619 */               offsetStack[stackPos] = p;
/* 1620 */               lengthStack[stackPos] = count[m];
/* 1621 */               levelStack[stackPos++] = level + 1;
/*      */             } 
/*      */           }
/* 1624 */           p += count[m];
/*      */         } 
/* 1626 */         Arrays.fill(count, 0L); continue;
/*      */       } 
/* 1628 */       long end = first + length - count[lastUsed];
/*      */       
/* 1630 */       int c = -1;
/* 1631 */       for (long l1 = first; l1 <= end; l1 += count[c], count[c] = 0L) {
/* 1632 */         long t = BigArrays.get(perm, l1);
/* 1633 */         c = BigArrays.get(k, t) >>> shift & 0xFF ^ signMask;
/* 1634 */         if (l1 < end) {
/* 1635 */           long d; for (pos[c] = pos[c] - 1L; (d = pos[c] - 1L) > l1; ) {
/* 1636 */             long z = t;
/* 1637 */             t = BigArrays.get(perm, d);
/* 1638 */             BigArrays.set(perm, d, z);
/* 1639 */             c = BigArrays.get(k, t) >>> shift & 0xFF ^ signMask;
/*      */           } 
/* 1641 */           BigArrays.set(perm, l1, t);
/*      */         } 
/* 1643 */         if (level < 1 && count[c] > 1L) {
/* 1644 */           if (count[c] < 1024L) {
/* 1645 */             insertionSortIndirect(perm, a, b, l1, l1 + count[c]);
/*      */           } else {
/* 1647 */             offsetStack[stackPos] = l1;
/* 1648 */             lengthStack[stackPos] = count[c];
/* 1649 */             levelStack[stackPos++] = level + 1;
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
/*      */   public static byte[][] shuffle(byte[][] a, long from, long to, Random random) {
/* 1671 */     for (long i = to - from; i-- != 0L; ) {
/* 1672 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 1673 */       byte t = BigArrays.get(a, from + i);
/* 1674 */       BigArrays.set(a, from + i, BigArrays.get(a, from + p));
/* 1675 */       BigArrays.set(a, from + p, t);
/*      */     } 
/* 1677 */     return a;
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
/*      */   public static byte[][] shuffle(byte[][] a, Random random) {
/* 1690 */     for (long i = length(a); i-- != 0L; ) {
/* 1691 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 1692 */       byte t = BigArrays.get(a, i);
/* 1693 */       BigArrays.set(a, i, BigArrays.get(a, p));
/* 1694 */       BigArrays.set(a, p, t);
/*      */     } 
/* 1696 */     return a;
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\ByteBigArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */