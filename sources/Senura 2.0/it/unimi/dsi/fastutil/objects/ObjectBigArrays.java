/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.BigArrays;
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.Array;
/*      */ import java.util.Arrays;
/*      */ import java.util.Comparator;
/*      */ import java.util.Objects;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class ObjectBigArrays
/*      */ {
/*   71 */   public static final Object[][] EMPTY_BIG_ARRAY = new Object[0][];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   80 */   public static final Object[][] DEFAULT_EMPTY_BIG_ARRAY = new Object[0][];
/*      */ 
/*      */ 
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
/*      */   public static <K> K get(K[][] array, long index) {
/*   94 */     return array[BigArrays.segment(index)][BigArrays.displacement(index)];
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
/*      */   public static <K> void set(K[][] array, long index, K value) {
/*  110 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = value;
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
/*      */   public static <K> void swap(K[][] array, long first, long second) {
/*  126 */     K t = array[BigArrays.segment(first)][BigArrays.displacement(first)];
/*  127 */     array[BigArrays.segment(first)][BigArrays.displacement(first)] = array[BigArrays.segment(second)][BigArrays.displacement(second)];
/*  128 */     array[BigArrays.segment(second)][BigArrays.displacement(second)] = t;
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
/*      */   public static <K> long length(K[][] array) {
/*  141 */     int length = array.length;
/*  142 */     return (length == 0) ? 0L : (BigArrays.start(length - 1) + (array[length - 1]).length);
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
/*      */   public static <K> void copy(K[][] srcArray, long srcPos, K[][] destArray, long destPos, long length) {
/*  165 */     if (destPos <= srcPos) {
/*  166 */       int srcSegment = BigArrays.segment(srcPos);
/*  167 */       int destSegment = BigArrays.segment(destPos);
/*  168 */       int srcDispl = BigArrays.displacement(srcPos);
/*  169 */       int destDispl = BigArrays.displacement(destPos);
/*      */       
/*  171 */       while (length > 0L) {
/*  172 */         int l = (int)Math.min(length, 
/*  173 */             Math.min((srcArray[srcSegment]).length - srcDispl, (destArray[destSegment]).length - destDispl));
/*  174 */         System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
/*  175 */         if ((srcDispl += l) == 134217728) {
/*  176 */           srcDispl = 0;
/*  177 */           srcSegment++;
/*      */         } 
/*  179 */         if ((destDispl += l) == 134217728) {
/*  180 */           destDispl = 0;
/*  181 */           destSegment++;
/*      */         } 
/*  183 */         length -= l;
/*      */       } 
/*      */     } else {
/*  186 */       int srcSegment = BigArrays.segment(srcPos + length);
/*  187 */       int destSegment = BigArrays.segment(destPos + length);
/*  188 */       int srcDispl = BigArrays.displacement(srcPos + length);
/*  189 */       int destDispl = BigArrays.displacement(destPos + length);
/*      */       
/*  191 */       while (length > 0L) {
/*  192 */         if (srcDispl == 0) {
/*  193 */           srcDispl = 134217728;
/*  194 */           srcSegment--;
/*      */         } 
/*  196 */         if (destDispl == 0) {
/*  197 */           destDispl = 134217728;
/*  198 */           destSegment--;
/*      */         } 
/*  200 */         int l = (int)Math.min(length, Math.min(srcDispl, destDispl));
/*  201 */         System.arraycopy(srcArray[srcSegment], srcDispl - l, destArray[destSegment], destDispl - l, l);
/*  202 */         srcDispl -= l;
/*  203 */         destDispl -= l;
/*  204 */         length -= l;
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
/*      */   public static <K> void copyFromBig(K[][] srcArray, long srcPos, K[] destArray, int destPos, int length) {
/*  228 */     int srcSegment = BigArrays.segment(srcPos);
/*  229 */     int srcDispl = BigArrays.displacement(srcPos);
/*      */     
/*  231 */     while (length > 0) {
/*  232 */       int l = Math.min((srcArray[srcSegment]).length - srcDispl, length);
/*  233 */       System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
/*  234 */       if ((srcDispl += l) == 134217728) {
/*  235 */         srcDispl = 0;
/*  236 */         srcSegment++;
/*      */       } 
/*  238 */       destPos += l;
/*  239 */       length -= l;
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
/*      */   public static <K> void copyToBig(K[] srcArray, int srcPos, K[][] destArray, long destPos, long length) {
/*  262 */     int destSegment = BigArrays.segment(destPos);
/*  263 */     int destDispl = BigArrays.displacement(destPos);
/*      */     
/*  265 */     while (length > 0L) {
/*  266 */       int l = (int)Math.min(((destArray[destSegment]).length - destDispl), length);
/*  267 */       System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
/*  268 */       if ((destDispl += l) == 134217728) {
/*  269 */         destDispl = 0;
/*  270 */         destSegment++;
/*      */       } 
/*  272 */       srcPos += l;
/*  273 */       length -= l;
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
/*      */   public static <K> K[][] newBigArray(K[][] prototype, long length) {
/*  292 */     return (K[][])newBigArray(prototype.getClass().getComponentType(), length);
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
/*      */   public static Object[][] newBigArray(Class<?> componentType, long length) {
/*  310 */     if (length == 0L && componentType == Object[].class)
/*  311 */       return EMPTY_BIG_ARRAY; 
/*  312 */     BigArrays.ensureLength(length);
/*  313 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  314 */     Object[][] base = (Object[][])Array.newInstance(componentType, baseLength);
/*  315 */     int residual = (int)(length & 0x7FFFFFFL);
/*  316 */     if (residual != 0) {
/*  317 */       for (int i = 0; i < baseLength - 1; i++) {
/*  318 */         base[i] = (Object[])Array.newInstance(componentType.getComponentType(), 134217728);
/*      */       }
/*  320 */       base[baseLength - 1] = (Object[])Array.newInstance(componentType.getComponentType(), residual);
/*      */     } else {
/*      */       
/*  323 */       for (int i = 0; i < baseLength; i++)
/*  324 */         base[i] = (Object[])Array.newInstance(componentType.getComponentType(), 134217728); 
/*      */     } 
/*  326 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object[][] newBigArray(long length) {
/*  336 */     if (length == 0L)
/*  337 */       return EMPTY_BIG_ARRAY; 
/*  338 */     BigArrays.ensureLength(length);
/*  339 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  340 */     Object[][] base = new Object[baseLength][];
/*  341 */     int residual = (int)(length & 0x7FFFFFFL);
/*  342 */     if (residual != 0) {
/*  343 */       for (int i = 0; i < baseLength - 1; i++)
/*  344 */         base[i] = new Object[134217728]; 
/*  345 */       base[baseLength - 1] = new Object[residual];
/*      */     } else {
/*  347 */       for (int i = 0; i < baseLength; i++)
/*  348 */         base[i] = new Object[134217728]; 
/*  349 */     }  return base;
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
/*      */   public static <K> K[][] wrap(K[] array) {
/*  367 */     if (array.length == 0 && array.getClass() == Object[].class)
/*  368 */       return (K[][])EMPTY_BIG_ARRAY; 
/*  369 */     if (array.length <= 134217728) {
/*  370 */       K[][] arrayOfK = (K[][])Array.newInstance(array.getClass(), 1);
/*  371 */       arrayOfK[0] = array;
/*  372 */       return arrayOfK;
/*      */     } 
/*  374 */     K[][] bigArray = (K[][])newBigArray(array.getClass(), array.length);
/*  375 */     for (int i = 0; i < bigArray.length; i++)
/*  376 */       System.arraycopy(array, (int)BigArrays.start(i), bigArray[i], 0, (bigArray[i]).length); 
/*  377 */     return bigArray;
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
/*      */   public static <K> K[][] ensureCapacity(K[][] array, long length) {
/*  403 */     return ensureCapacity(array, length, length(array));
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
/*      */   @Deprecated
/*      */   public static <K> K[][] forceCapacity(K[][] array, long length, long preserve) {
/*  433 */     BigArrays.ensureLength(length);
/*      */     
/*  435 */     int valid = array.length - ((array.length == 0 || (array.length > 0 && (array[array.length - 1]).length == 134217728)) ? 0 : 1);
/*  436 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  437 */     K[][] base = (K[][])Arrays.<Object[]>copyOf((Object[][])array, baseLength);
/*  438 */     Class<?> componentType = array.getClass().getComponentType();
/*  439 */     int residual = (int)(length & 0x7FFFFFFL);
/*  440 */     if (residual != 0) {
/*  441 */       for (int i = valid; i < baseLength - 1; i++)
/*  442 */         base[i] = (K[])Array.newInstance(componentType.getComponentType(), 134217728); 
/*  443 */       base[baseLength - 1] = (K[])Array.newInstance(componentType.getComponentType(), residual);
/*      */     } else {
/*      */       
/*  446 */       for (int i = valid; i < baseLength; i++)
/*  447 */         base[i] = (K[])Array.newInstance(componentType.getComponentType(), 134217728); 
/*  448 */     }  if (preserve - valid * 134217728L > 0L) {
/*  449 */       copy(array, valid * 134217728L, base, valid * 134217728L, preserve - valid * 134217728L);
/*      */     }
/*  451 */     return base;
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
/*      */   @Deprecated
/*      */   public static <K> K[][] ensureCapacity(K[][] array, long length, long preserve) {
/*  482 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
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
/*      */   public static <K> K[][] grow(K[][] array, long length) {
/*  511 */     long oldLength = length(array);
/*  512 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
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
/*      */   public static <K> K[][] grow(K[][] array, long length, long preserve) {
/*  544 */     long oldLength = length(array);
/*  545 */     return (length > oldLength) ? 
/*  546 */       ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : 
/*  547 */       array;
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
/*      */   public static <K> K[][] trim(K[][] array, long length) {
/*  569 */     BigArrays.ensureLength(length);
/*  570 */     long oldLength = length(array);
/*  571 */     if (length >= oldLength)
/*  572 */       return array; 
/*  573 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  574 */     K[][] base = (K[][])Arrays.<Object[]>copyOf((Object[][])array, baseLength);
/*  575 */     int residual = (int)(length & 0x7FFFFFFL);
/*  576 */     if (residual != 0)
/*  577 */       base[baseLength - 1] = ObjectArrays.trim(base[baseLength - 1], residual); 
/*  578 */     return base;
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
/*      */   public static <K> K[][] setLength(K[][] array, long length) {
/*  603 */     long oldLength = length(array);
/*  604 */     if (length == oldLength)
/*  605 */       return array; 
/*  606 */     if (length < oldLength)
/*  607 */       return trim(array, length); 
/*  608 */     return ensureCapacity(array, length);
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
/*      */   public static <K> K[][] copy(K[][] array, long offset, long length) {
/*  626 */     ensureOffsetLength(array, offset, length);
/*  627 */     K[][] a = newBigArray(array, length);
/*  628 */     copy(array, offset, a, 0L, length);
/*  629 */     return a;
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
/*      */   public static <K> K[][] copy(K[][] array) {
/*  642 */     K[][] base = (K[][])array.clone();
/*  643 */     for (int i = base.length; i-- != 0;)
/*  644 */       base[i] = (K[])array[i].clone(); 
/*  645 */     return base;
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
/*      */   public static <K> void fill(K[][] array, K value) {
/*  663 */     for (int i = array.length; i-- != 0;) {
/*  664 */       Arrays.fill((Object[])array[i], value);
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
/*      */   public static <K> void fill(K[][] array, long from, long to, K value) {
/*  688 */     long length = length(array);
/*  689 */     BigArrays.ensureFromTo(length, from, to);
/*  690 */     if (length == 0L)
/*      */       return; 
/*  692 */     int fromSegment = BigArrays.segment(from);
/*  693 */     int toSegment = BigArrays.segment(to);
/*  694 */     int fromDispl = BigArrays.displacement(from);
/*  695 */     int toDispl = BigArrays.displacement(to);
/*  696 */     if (fromSegment == toSegment) {
/*  697 */       Arrays.fill((Object[])array[fromSegment], fromDispl, toDispl, value);
/*      */       return;
/*      */     } 
/*  700 */     if (toDispl != 0)
/*  701 */       Arrays.fill((Object[])array[toSegment], 0, toDispl, value); 
/*  702 */     while (--toSegment > fromSegment)
/*  703 */       Arrays.fill((Object[])array[toSegment], value); 
/*  704 */     Arrays.fill((Object[])array[fromSegment], fromDispl, 134217728, value);
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
/*      */   public static <K> boolean equals(K[][] a1, K[][] a2) {
/*  724 */     if (length(a1) != length(a2))
/*  725 */       return false; 
/*  726 */     int i = a1.length;
/*      */     
/*  728 */     while (i-- != 0) {
/*  729 */       K[] t = a1[i];
/*  730 */       K[] u = a2[i];
/*  731 */       int j = t.length;
/*  732 */       while (j-- != 0) {
/*  733 */         if (!Objects.equals(t[j], u[j]))
/*  734 */           return false; 
/*      */       } 
/*  736 */     }  return true;
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
/*      */   public static <K> String toString(K[][] a) {
/*  755 */     if (a == null)
/*  756 */       return "null"; 
/*  757 */     long last = length(a) - 1L;
/*  758 */     if (last == -1L)
/*  759 */       return "[]"; 
/*  760 */     StringBuilder b = new StringBuilder();
/*  761 */     b.append('['); long i;
/*  762 */     for (i = 0L;; i++) {
/*  763 */       b.append(String.valueOf(BigArrays.get((Object[][])a, i)));
/*  764 */       if (i == last)
/*  765 */         return b.append(']').toString(); 
/*  766 */       b.append(", ");
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
/*      */   public static <K> void ensureFromTo(K[][] a, long from, long to) {
/*  792 */     BigArrays.ensureFromTo(length(a), from, to);
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
/*      */   public static <K> void ensureOffsetLength(K[][] a, long offset, long length) {
/*  816 */     BigArrays.ensureOffsetLength(length(a), offset, length);
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
/*      */   public static <K> void ensureSameLength(K[][] a, K[][] b) {
/*  832 */     if (length(a) != length(b))
/*  833 */       throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b)); 
/*      */   }
/*      */   private static final class BigArrayHashStrategy<K> implements Hash.Strategy<K[][]>, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private BigArrayHashStrategy() {}
/*      */     
/*      */     public int hashCode(K[][] o) {
/*  840 */       return Arrays.deepHashCode((Object[])o);
/*      */     }
/*      */     
/*      */     public boolean equals(K[][] a, K[][] b) {
/*  844 */       return ObjectBigArrays.equals(a, b);
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
/*  856 */   public static final Hash.Strategy HASH_STRATEGY = new BigArrayHashStrategy(); private static final int SMALL = 7;
/*      */   private static final int MEDIUM = 40;
/*      */   
/*      */   private static <K> void vecSwap(K[][] x, long a, long b, long n) {
/*  860 */     for (int i = 0; i < n; i++, a++, b++)
/*  861 */       swap(x, a, b); 
/*      */   }
/*      */   private static <K> long med3(K[][] x, long a, long b, long c, Comparator<K> comp) {
/*  864 */     int ab = comp.compare((K)BigArrays.get((Object[][])x, a), (K)BigArrays.get((Object[][])x, b));
/*  865 */     int ac = comp.compare((K)BigArrays.get((Object[][])x, a), (K)BigArrays.get((Object[][])x, c));
/*  866 */     int bc = comp.compare((K)BigArrays.get((Object[][])x, b), (K)BigArrays.get((Object[][])x, c));
/*  867 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   } private static <K> void selectionSort(K[][] a, long from, long to, Comparator<K> comp) {
/*      */     long i;
/*  870 */     for (i = from; i < to - 1L; i++) {
/*  871 */       long m = i; long j;
/*  872 */       for (j = i + 1L; j < to; j++) {
/*  873 */         if (comp.compare((K)BigArrays.get((Object[][])a, j), (K)BigArrays.get((Object[][])a, m)) < 0)
/*  874 */           m = j; 
/*  875 */       }  if (m != i) {
/*  876 */         swap(a, i, m);
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
/*      */   public static <K> void quickSort(K[][] x, long from, long to, Comparator<K> comp) {
/*  898 */     long len = to - from;
/*      */     
/*  900 */     if (len < 7L) {
/*  901 */       selectionSort(x, from, to, comp);
/*      */       
/*      */       return;
/*      */     } 
/*  905 */     long m = from + len / 2L;
/*  906 */     if (len > 7L) {
/*  907 */       long l = from;
/*  908 */       long n = to - 1L;
/*  909 */       if (len > 40L) {
/*  910 */         long s = len / 8L;
/*  911 */         l = med3(x, l, l + s, l + 2L * s, comp);
/*  912 */         m = med3(x, m - s, m, m + s, comp);
/*  913 */         n = med3(x, n - 2L * s, n - s, n, comp);
/*      */       } 
/*  915 */       m = med3(x, l, m, n, comp);
/*      */     } 
/*  917 */     K v = (K)BigArrays.get((Object[][])x, m);
/*      */     
/*  919 */     long a = from, b = a, c = to - 1L, d = c;
/*      */     
/*      */     int comparison;
/*  922 */     while (b <= c && (comparison = comp.compare((K)BigArrays.get((Object[][])x, b), v)) <= 0) {
/*  923 */       if (comparison == 0)
/*  924 */         swap(x, a++, b); 
/*  925 */       b++;
/*      */     } 
/*  927 */     while (c >= b && (comparison = comp.compare((K)BigArrays.get((Object[][])x, c), v)) >= 0) {
/*  928 */       if (comparison == 0)
/*  929 */         swap(x, c, d--); 
/*  930 */       c--;
/*      */     } 
/*  932 */     if (b > c) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  937 */       long n = to;
/*  938 */       long s = Math.min(a - from, b - a);
/*  939 */       vecSwap(x, from, b - s, s);
/*  940 */       s = Math.min(d - c, n - d - 1L);
/*  941 */       vecSwap(x, b, n - s, s);
/*      */       
/*  943 */       if ((s = b - a) > 1L)
/*  944 */         quickSort(x, from, from + s, comp); 
/*  945 */       if ((s = d - c) > 1L)
/*  946 */         quickSort(x, n - s, n, comp); 
/*      */       return;
/*      */     } 
/*      */     swap(x, b++, c--); } private static <K> long med3(K[][] x, long a, long b, long c) {
/*  950 */     int ab = ((Comparable<Object>)BigArrays.get((Object[][])x, a)).compareTo(BigArrays.get((Object[][])x, b));
/*  951 */     int ac = ((Comparable<Object>)BigArrays.get((Object[][])x, a)).compareTo(BigArrays.get((Object[][])x, c));
/*  952 */     int bc = ((Comparable<Object>)BigArrays.get((Object[][])x, b)).compareTo(BigArrays.get((Object[][])x, c));
/*  953 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static <K> void selectionSort(K[][] a, long from, long to) {
/*      */     long i;
/*  957 */     for (i = from; i < to - 1L; i++) {
/*  958 */       long m = i; long j;
/*  959 */       for (j = i + 1L; j < to; j++) {
/*  960 */         if (((Comparable<Object>)BigArrays.get((Object[][])a, j)).compareTo(BigArrays.get((Object[][])a, m)) < 0)
/*  961 */           m = j; 
/*  962 */       }  if (m != i) {
/*  963 */         swap(a, i, m);
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
/*      */   public static <K> void quickSort(K[][] x, Comparator<K> comp) {
/*  982 */     quickSort(x, 0L, length(x), comp);
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
/*      */   public static <K> void quickSort(K[][] x, long from, long to) {
/* 1002 */     long len = to - from;
/*      */     
/* 1004 */     if (len < 7L) {
/* 1005 */       selectionSort(x, from, to);
/*      */       
/*      */       return;
/*      */     } 
/* 1009 */     long m = from + len / 2L;
/* 1010 */     if (len > 7L) {
/* 1011 */       long l = from;
/* 1012 */       long n = to - 1L;
/* 1013 */       if (len > 40L) {
/* 1014 */         long s = len / 8L;
/* 1015 */         l = med3(x, l, l + s, l + 2L * s);
/* 1016 */         m = med3(x, m - s, m, m + s);
/* 1017 */         n = med3(x, n - 2L * s, n - s, n);
/*      */       } 
/* 1019 */       m = med3(x, l, m, n);
/*      */     } 
/* 1021 */     K v = (K)BigArrays.get((Object[][])x, m);
/*      */     
/* 1023 */     long a = from, b = a, c = to - 1L, d = c;
/*      */     
/*      */     int comparison;
/* 1026 */     while (b <= c && (comparison = ((Comparable<K>)BigArrays.get((Object[][])x, b)).compareTo(v)) <= 0) {
/* 1027 */       if (comparison == 0)
/* 1028 */         swap(x, a++, b); 
/* 1029 */       b++;
/*      */     } 
/* 1031 */     while (c >= b && (comparison = ((Comparable<K>)BigArrays.get((Object[][])x, c)).compareTo(v)) >= 0) {
/* 1032 */       if (comparison == 0)
/* 1033 */         swap(x, c, d--); 
/* 1034 */       c--;
/*      */     } 
/* 1036 */     if (b > c) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1041 */       long n = to;
/* 1042 */       long s = Math.min(a - from, b - a);
/* 1043 */       vecSwap(x, from, b - s, s);
/* 1044 */       s = Math.min(d - c, n - d - 1L);
/* 1045 */       vecSwap(x, b, n - s, s);
/*      */       
/* 1047 */       if ((s = b - a) > 1L)
/* 1048 */         quickSort(x, from, from + s); 
/* 1049 */       if ((s = d - c) > 1L) {
/* 1050 */         quickSort(x, n - s, n);
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
/*      */   public static <K> void quickSort(K[][] x) {
/* 1065 */     quickSort(x, 0L, length(x));
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
/*      */   public static <K> long binarySearch(K[][] a, long from, long to, K key) {
/* 1095 */     to--;
/* 1096 */     while (from <= to) {
/* 1097 */       long mid = from + to >>> 1L;
/* 1098 */       K midVal = (K)BigArrays.get((Object[][])a, mid);
/* 1099 */       int cmp = ((Comparable<K>)midVal).compareTo(key);
/* 1100 */       if (cmp < 0) {
/* 1101 */         from = mid + 1L; continue;
/* 1102 */       }  if (cmp > 0) {
/* 1103 */         to = mid - 1L; continue;
/*      */       } 
/* 1105 */       return mid;
/*      */     } 
/* 1107 */     return -(from + 1L);
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
/*      */   public static <K> long binarySearch(K[][] a, Object key) {
/* 1130 */     return binarySearch(a, 0L, length(a), (K)key);
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
/*      */   public static <K> long binarySearch(K[][] a, long from, long to, K key, Comparator<K> c) {
/* 1161 */     to--;
/* 1162 */     while (from <= to) {
/* 1163 */       long mid = from + to >>> 1L;
/* 1164 */       K midVal = (K)BigArrays.get((Object[][])a, mid);
/* 1165 */       int cmp = c.compare(midVal, key);
/* 1166 */       if (cmp < 0) {
/* 1167 */         from = mid + 1L; continue;
/* 1168 */       }  if (cmp > 0) {
/* 1169 */         to = mid - 1L; continue;
/*      */       } 
/* 1171 */       return mid;
/*      */     } 
/* 1173 */     return -(from + 1L);
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
/*      */   public static <K> long binarySearch(K[][] a, K key, Comparator<K> c) {
/* 1199 */     return binarySearch(a, 0L, length(a), key, c);
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
/*      */   public static <K> K[][] shuffle(K[][] a, long from, long to, Random random) {
/* 1216 */     for (long i = to - from; i-- != 0L; ) {
/* 1217 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 1218 */       K t = (K)BigArrays.get((Object[][])a, from + i);
/* 1219 */       BigArrays.set((Object[][])a, from + i, BigArrays.get((Object[][])a, from + p));
/* 1220 */       BigArrays.set((Object[][])a, from + p, t);
/*      */     } 
/* 1222 */     return a;
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
/*      */   public static <K> K[][] shuffle(K[][] a, Random random) {
/* 1235 */     for (long i = length(a); i-- != 0L; ) {
/* 1236 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 1237 */       K t = (K)BigArrays.get((Object[][])a, i);
/* 1238 */       BigArrays.set((Object[][])a, i, BigArrays.get((Object[][])a, p));
/* 1239 */       BigArrays.set((Object[][])a, p, t);
/*      */     } 
/* 1241 */     return a;
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ObjectBigArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */