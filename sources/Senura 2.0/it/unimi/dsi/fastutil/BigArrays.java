/*      */ package it.unimi.dsi.fastutil;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanArrays;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanBigArrays;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteArrays;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteBigArrays;
/*      */ import it.unimi.dsi.fastutil.chars.CharArrays;
/*      */ import it.unimi.dsi.fastutil.chars.CharBigArrays;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleArrays;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleBigArrays;
/*      */ import it.unimi.dsi.fastutil.floats.FloatArrays;
/*      */ import it.unimi.dsi.fastutil.floats.FloatBigArrays;
/*      */ import it.unimi.dsi.fastutil.ints.IntArrays;
/*      */ import it.unimi.dsi.fastutil.ints.IntBigArrays;
/*      */ import it.unimi.dsi.fastutil.longs.LongArrays;
/*      */ import it.unimi.dsi.fastutil.longs.LongBigArrays;
/*      */ import it.unimi.dsi.fastutil.longs.LongComparator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectArrays;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectBigArrays;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortArrays;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortBigArrays;
/*      */ import java.lang.reflect.Array;
/*      */ import java.util.Arrays;
/*      */ import java.util.Objects;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BigArrays
/*      */ {
/*      */   public static final int SEGMENT_SHIFT = 27;
/*      */   public static final int SEGMENT_SIZE = 134217728;
/*      */   public static final int SEGMENT_MASK = 134217727;
/*      */   private static final int SMALL = 7;
/*      */   private static final int MEDIUM = 40;
/*      */   
/*      */   public static int segment(long index) {
/*  216 */     return (int)(index >>> 27L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int displacement(long index) {
/*  227 */     return (int)(index & 0x7FFFFFFL);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long start(int segment) {
/*  237 */     return segment << 27L;
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
/*      */   public static long index(int segment, int displacement) {
/*  252 */     return start(segment) + displacement;
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
/*      */   public static void ensureFromTo(long bigArrayLength, long from, long to) {
/*  274 */     if (from < 0L) throw new ArrayIndexOutOfBoundsException("Start index (" + from + ") is negative"); 
/*  275 */     if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/*  276 */     if (to > bigArrayLength) throw new ArrayIndexOutOfBoundsException("End index (" + to + ") is greater than big-array length (" + bigArrayLength + ")");
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
/*      */   public static void ensureOffsetLength(long bigArrayLength, long offset, long length) {
/*  299 */     if (offset < 0L) throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative"); 
/*  300 */     if (length < 0L) throw new IllegalArgumentException("Length (" + length + ") is negative"); 
/*  301 */     if (offset + length > bigArrayLength) throw new ArrayIndexOutOfBoundsException("Last index (" + (offset + length) + ") is greater than big-array length (" + bigArrayLength + ")");
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
/*      */   public static void ensureLength(long bigArrayLength) {
/*  313 */     if (bigArrayLength < 0L) throw new IllegalArgumentException("Negative big-array size: " + bigArrayLength); 
/*  314 */     if (bigArrayLength >= 288230376017494016L) throw new IllegalArgumentException("Big-array size too big: " + bigArrayLength);
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
/*      */   private static void inPlaceMerge(long from, long mid, long to, LongComparator comp, BigSwapper swapper) {
/*      */     // Byte code:
/*      */     //   0: lload_0
/*      */     //   1: lload_2
/*      */     //   2: lcmp
/*      */     //   3: ifge -> 13
/*      */     //   6: lload_2
/*      */     //   7: lload #4
/*      */     //   9: lcmp
/*      */     //   10: iflt -> 14
/*      */     //   13: return
/*      */     //   14: lload #4
/*      */     //   16: lload_0
/*      */     //   17: lsub
/*      */     //   18: ldc2_w 2
/*      */     //   21: lcmp
/*      */     //   22: ifne -> 47
/*      */     //   25: aload #6
/*      */     //   27: lload_2
/*      */     //   28: lload_0
/*      */     //   29: invokeinterface compare : (JJ)I
/*      */     //   34: ifge -> 46
/*      */     //   37: aload #7
/*      */     //   39: lload_0
/*      */     //   40: lload_2
/*      */     //   41: invokeinterface swap : (JJ)V
/*      */     //   46: return
/*      */     //   47: lload_2
/*      */     //   48: lload_0
/*      */     //   49: lsub
/*      */     //   50: lload #4
/*      */     //   52: lload_2
/*      */     //   53: lsub
/*      */     //   54: lcmp
/*      */     //   55: ifle -> 84
/*      */     //   58: lload_0
/*      */     //   59: lload_2
/*      */     //   60: lload_0
/*      */     //   61: lsub
/*      */     //   62: ldc2_w 2
/*      */     //   65: ldiv
/*      */     //   66: ladd
/*      */     //   67: lstore #8
/*      */     //   69: lload_2
/*      */     //   70: lload #4
/*      */     //   72: lload #8
/*      */     //   74: aload #6
/*      */     //   76: invokestatic lowerBound : (JJJLit/unimi/dsi/fastutil/longs/LongComparator;)J
/*      */     //   79: lstore #10
/*      */     //   81: goto -> 107
/*      */     //   84: lload_2
/*      */     //   85: lload #4
/*      */     //   87: lload_2
/*      */     //   88: lsub
/*      */     //   89: ldc2_w 2
/*      */     //   92: ldiv
/*      */     //   93: ladd
/*      */     //   94: lstore #10
/*      */     //   96: lload_0
/*      */     //   97: lload_2
/*      */     //   98: lload #10
/*      */     //   100: aload #6
/*      */     //   102: invokestatic upperBound : (JJJLit/unimi/dsi/fastutil/longs/LongComparator;)J
/*      */     //   105: lstore #8
/*      */     //   107: lload #8
/*      */     //   109: lstore #12
/*      */     //   111: lload_2
/*      */     //   112: lstore #14
/*      */     //   114: lload #10
/*      */     //   116: lstore #16
/*      */     //   118: lload #14
/*      */     //   120: lload #12
/*      */     //   122: lcmp
/*      */     //   123: ifeq -> 254
/*      */     //   126: lload #14
/*      */     //   128: lload #16
/*      */     //   130: lcmp
/*      */     //   131: ifeq -> 254
/*      */     //   134: lload #12
/*      */     //   136: lstore #18
/*      */     //   138: lload #14
/*      */     //   140: lstore #20
/*      */     //   142: lload #18
/*      */     //   144: lload #20
/*      */     //   146: lconst_1
/*      */     //   147: lsub
/*      */     //   148: dup2
/*      */     //   149: lstore #20
/*      */     //   151: lcmp
/*      */     //   152: ifge -> 174
/*      */     //   155: aload #7
/*      */     //   157: lload #18
/*      */     //   159: dup2
/*      */     //   160: lconst_1
/*      */     //   161: ladd
/*      */     //   162: lstore #18
/*      */     //   164: lload #20
/*      */     //   166: invokeinterface swap : (JJ)V
/*      */     //   171: goto -> 142
/*      */     //   174: lload #14
/*      */     //   176: lstore #18
/*      */     //   178: lload #16
/*      */     //   180: lstore #20
/*      */     //   182: lload #18
/*      */     //   184: lload #20
/*      */     //   186: lconst_1
/*      */     //   187: lsub
/*      */     //   188: dup2
/*      */     //   189: lstore #20
/*      */     //   191: lcmp
/*      */     //   192: ifge -> 214
/*      */     //   195: aload #7
/*      */     //   197: lload #18
/*      */     //   199: dup2
/*      */     //   200: lconst_1
/*      */     //   201: ladd
/*      */     //   202: lstore #18
/*      */     //   204: lload #20
/*      */     //   206: invokeinterface swap : (JJ)V
/*      */     //   211: goto -> 182
/*      */     //   214: lload #12
/*      */     //   216: lstore #18
/*      */     //   218: lload #16
/*      */     //   220: lstore #20
/*      */     //   222: lload #18
/*      */     //   224: lload #20
/*      */     //   226: lconst_1
/*      */     //   227: lsub
/*      */     //   228: dup2
/*      */     //   229: lstore #20
/*      */     //   231: lcmp
/*      */     //   232: ifge -> 254
/*      */     //   235: aload #7
/*      */     //   237: lload #18
/*      */     //   239: dup2
/*      */     //   240: lconst_1
/*      */     //   241: ladd
/*      */     //   242: lstore #18
/*      */     //   244: lload #20
/*      */     //   246: invokeinterface swap : (JJ)V
/*      */     //   251: goto -> 222
/*      */     //   254: lload #8
/*      */     //   256: lload #10
/*      */     //   258: lload_2
/*      */     //   259: lsub
/*      */     //   260: ladd
/*      */     //   261: lstore_2
/*      */     //   262: lload_0
/*      */     //   263: lload #8
/*      */     //   265: lload_2
/*      */     //   266: aload #6
/*      */     //   268: aload #7
/*      */     //   270: invokestatic inPlaceMerge : (JJJLit/unimi/dsi/fastutil/longs/LongComparator;Lit/unimi/dsi/fastutil/BigSwapper;)V
/*      */     //   273: lload_2
/*      */     //   274: lload #10
/*      */     //   276: lload #4
/*      */     //   278: aload #6
/*      */     //   280: aload #7
/*      */     //   282: invokestatic inPlaceMerge : (JJJLit/unimi/dsi/fastutil/longs/LongComparator;Lit/unimi/dsi/fastutil/BigSwapper;)V
/*      */     //   285: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #326	-> 0
/*      */     //   #327	-> 14
/*      */     //   #328	-> 25
/*      */     //   #329	-> 37
/*      */     //   #331	-> 46
/*      */     //   #335	-> 47
/*      */     //   #336	-> 58
/*      */     //   #337	-> 69
/*      */     //   #339	-> 84
/*      */     //   #340	-> 96
/*      */     //   #342	-> 107
/*      */     //   #343	-> 111
/*      */     //   #344	-> 114
/*      */     //   #345	-> 118
/*      */     //   #346	-> 134
/*      */     //   #347	-> 138
/*      */     //   #348	-> 142
/*      */     //   #349	-> 155
/*      */     //   #350	-> 174
/*      */     //   #351	-> 178
/*      */     //   #352	-> 182
/*      */     //   #353	-> 195
/*      */     //   #354	-> 214
/*      */     //   #355	-> 218
/*      */     //   #356	-> 222
/*      */     //   #357	-> 235
/*      */     //   #359	-> 254
/*      */     //   #360	-> 262
/*      */     //   #361	-> 273
/*      */     //   #362	-> 285
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   69	15	8	firstCut	J
/*      */     //   81	3	10	secondCut	J
/*      */     //   138	116	18	first1	J
/*      */     //   142	112	20	last1	J
/*      */     //   0	286	0	from	J
/*      */     //   0	286	2	mid	J
/*      */     //   0	286	4	to	J
/*      */     //   0	286	6	comp	Lit/unimi/dsi/fastutil/longs/LongComparator;
/*      */     //   0	286	7	swapper	Lit/unimi/dsi/fastutil/BigSwapper;
/*      */     //   107	179	8	firstCut	J
/*      */     //   96	190	10	secondCut	J
/*      */     //   111	175	12	first2	J
/*      */     //   114	172	14	middle2	J
/*      */     //   118	168	16	last2	J
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
/*      */   private static long lowerBound(long mid, long to, long firstCut, LongComparator comp) {
/*  381 */     long len = to - mid;
/*  382 */     while (len > 0L) {
/*  383 */       long half = len / 2L;
/*  384 */       long middle = mid + half;
/*  385 */       if (comp.compare(middle, firstCut) < 0) {
/*  386 */         mid = middle + 1L;
/*  387 */         len -= half + 1L; continue;
/*      */       } 
/*  389 */       len = half;
/*      */     } 
/*      */     
/*  392 */     return mid;
/*      */   }
/*      */   
/*      */   private static long med3(long a, long b, long c, LongComparator comp) {
/*  396 */     int ab = comp.compare(a, b);
/*  397 */     int ac = comp.compare(a, c);
/*  398 */     int bc = comp.compare(b, c);
/*  399 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
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
/*      */   public static void mergeSort(long from, long to, LongComparator comp, BigSwapper swapper) {
/*  427 */     long length = to - from;
/*      */     
/*  429 */     if (length < 7L) {
/*  430 */       long i; for (i = from; i < to; i++) {
/*  431 */         long j; for (j = i; j > from && comp.compare(j - 1L, j) > 0; j--) {
/*  432 */           swapper.swap(j, j - 1L);
/*      */         }
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/*  438 */     long mid = from + to >>> 1L;
/*  439 */     mergeSort(from, mid, comp, swapper);
/*  440 */     mergeSort(mid, to, comp, swapper);
/*      */ 
/*      */     
/*  443 */     if (comp.compare(mid - 1L, mid) <= 0)
/*      */       return; 
/*  445 */     inPlaceMerge(from, mid, to, comp, swapper);
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
/*      */   public static void quickSort(long from, long to, LongComparator comp, BigSwapper swapper) {
/*  469 */     long len = to - from;
/*      */     
/*  471 */     if (len < 7L) {
/*  472 */       long i; for (i = from; i < to; i++) {
/*  473 */         long j; for (j = i; j > from && comp.compare(j - 1L, j) > 0; j--) {
/*  474 */           swapper.swap(j, j - 1L);
/*      */         }
/*      */       } 
/*      */       return;
/*      */     } 
/*  479 */     long m = from + len / 2L;
/*  480 */     if (len > 7L) {
/*  481 */       long l = from, n = to - 1L;
/*  482 */       if (len > 40L) {
/*  483 */         long s = len / 8L;
/*  484 */         l = med3(l, l + s, l + 2L * s, comp);
/*  485 */         m = med3(m - s, m, m + s, comp);
/*  486 */         n = med3(n - 2L * s, n - s, n, comp);
/*      */       } 
/*  488 */       m = med3(l, m, n, comp);
/*      */     } 
/*      */     
/*  491 */     long a = from, b = a, c = to - 1L, d = c;
/*      */     
/*      */     int comparison;
/*      */     
/*  495 */     while (b <= c && (comparison = comp.compare(b, m)) <= 0) {
/*  496 */       if (comparison == 0) {
/*  497 */         if (a == m) { m = b; }
/*  498 */         else if (b == m) { m = a; }
/*  499 */          swapper.swap(a++, b);
/*      */       } 
/*  501 */       b++;
/*      */     } 
/*  503 */     while (c >= b && (comparison = comp.compare(c, m)) >= 0) {
/*  504 */       if (comparison == 0) {
/*  505 */         if (c == m) { m = d; }
/*  506 */         else if (d == m) { m = c; }
/*  507 */          swapper.swap(c, d--);
/*      */       } 
/*  509 */       c--;
/*      */     } 
/*  511 */     if (b > c) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  518 */       long n = from + len;
/*  519 */       long s = Math.min(a - from, b - a);
/*  520 */       vecSwap(swapper, from, b - s, s);
/*  521 */       s = Math.min(d - c, n - d - 1L);
/*  522 */       vecSwap(swapper, b, n - s, s);
/*      */       
/*  524 */       if ((s = b - a) > 1L) quickSort(from, from + s, comp, swapper); 
/*  525 */       if ((s = d - c) > 1L) quickSort(n - s, n, comp, swapper);
/*      */       
/*      */       return;
/*      */     } 
/*      */     if (b == m) {
/*      */       m = d;
/*      */     } else if (c == m) {
/*      */       m = c;
/*      */     } 
/*      */     swapper.swap(b++, c--);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static long upperBound(long from, long mid, long secondCut, LongComparator comp) {
/*  545 */     long len = mid - from;
/*  546 */     while (len > 0L) {
/*  547 */       long half = len / 2L;
/*  548 */       long middle = from + half;
/*  549 */       if (comp.compare(secondCut, middle) < 0) {
/*  550 */         len = half; continue;
/*      */       } 
/*  552 */       from = middle + 1L;
/*  553 */       len -= half + 1L;
/*      */     } 
/*      */     
/*  556 */     return from;
/*      */   }
/*      */   
/*      */   private static void vecSwap(BigSwapper swapper, long from, long l, long s) {
/*  560 */     for (int i = 0; i < s; i++, from++, l++) {
/*  561 */       swapper.swap(from, l);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte get(byte[][] array, long index) {
/*  608 */     return array[segment(index)][displacement(index)];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void set(byte[][] array, long index, byte value) {
/*  617 */     array[segment(index)][displacement(index)] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(byte[][] array, long first, long second) {
/*  626 */     byte t = array[segment(first)][displacement(first)];
/*  627 */     array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
/*  628 */     array[segment(second)][displacement(second)] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void add(byte[][] array, long index, byte incr) {
/*  637 */     array[segment(index)][displacement(index)] = (byte)(array[segment(index)][displacement(index)] + incr);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mul(byte[][] array, long index, byte factor) {
/*  646 */     array[segment(index)][displacement(index)] = (byte)(array[segment(index)][displacement(index)] * factor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void incr(byte[][] array, long index) {
/*  654 */     array[segment(index)][displacement(index)] = (byte)(array[segment(index)][displacement(index)] + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void decr(byte[][] array, long index) {
/*  662 */     array[segment(index)][displacement(index)] = (byte)(array[segment(index)][displacement(index)] - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long length(byte[][] array) {
/*  670 */     int length = array.length;
/*  671 */     return (length == 0) ? 0L : (start(length - 1) + (array[length - 1]).length);
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
/*      */   public static void copy(byte[][] srcArray, long srcPos, byte[][] destArray, long destPos, long length) {
/*  683 */     if (destPos <= srcPos) {
/*  684 */       int srcSegment = segment(srcPos);
/*  685 */       int destSegment = segment(destPos);
/*  686 */       int srcDispl = displacement(srcPos);
/*  687 */       int destDispl = displacement(destPos);
/*      */       
/*  689 */       while (length > 0L) {
/*  690 */         int l = (int)Math.min(length, Math.min((srcArray[srcSegment]).length - srcDispl, (destArray[destSegment]).length - destDispl));
/*  691 */         System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
/*  692 */         if ((srcDispl += l) == 134217728) {
/*  693 */           srcDispl = 0;
/*  694 */           srcSegment++;
/*      */         } 
/*  696 */         if ((destDispl += l) == 134217728) {
/*  697 */           destDispl = 0;
/*  698 */           destSegment++;
/*      */         } 
/*  700 */         length -= l;
/*      */       } 
/*      */     } else {
/*      */       
/*  704 */       int srcSegment = segment(srcPos + length);
/*  705 */       int destSegment = segment(destPos + length);
/*  706 */       int srcDispl = displacement(srcPos + length);
/*  707 */       int destDispl = displacement(destPos + length);
/*      */       
/*  709 */       while (length > 0L) {
/*  710 */         if (srcDispl == 0) {
/*  711 */           srcDispl = 134217728;
/*  712 */           srcSegment--;
/*      */         } 
/*  714 */         if (destDispl == 0) {
/*  715 */           destDispl = 134217728;
/*  716 */           destSegment--;
/*      */         } 
/*  718 */         int l = (int)Math.min(length, Math.min(srcDispl, destDispl));
/*  719 */         System.arraycopy(srcArray[srcSegment], srcDispl - l, destArray[destSegment], destDispl - l, l);
/*  720 */         srcDispl -= l;
/*  721 */         destDispl -= l;
/*  722 */         length -= l;
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
/*      */   public static void copyFromBig(byte[][] srcArray, long srcPos, byte[] destArray, int destPos, int length) {
/*  735 */     int srcSegment = segment(srcPos);
/*  736 */     int srcDispl = displacement(srcPos);
/*      */     
/*  738 */     while (length > 0) {
/*  739 */       int l = Math.min((srcArray[srcSegment]).length - srcDispl, length);
/*  740 */       System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
/*  741 */       if ((srcDispl += l) == 134217728) {
/*  742 */         srcDispl = 0;
/*  743 */         srcSegment++;
/*      */       } 
/*  745 */       destPos += l;
/*  746 */       length -= l;
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
/*      */   public static void copyToBig(byte[] srcArray, int srcPos, byte[][] destArray, long destPos, long length) {
/*  758 */     int destSegment = segment(destPos);
/*  759 */     int destDispl = displacement(destPos);
/*      */     
/*  761 */     while (length > 0L) {
/*  762 */       int l = (int)Math.min(((destArray[destSegment]).length - destDispl), length);
/*  763 */       System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
/*  764 */       if ((destDispl += l) == 134217728) {
/*  765 */         destDispl = 0;
/*  766 */         destSegment++;
/*      */       } 
/*  768 */       srcPos += l;
/*  769 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[][] wrap(byte[] array) {
/*  780 */     if (array.length == 0) return ByteBigArrays.EMPTY_BIG_ARRAY; 
/*  781 */     if (array.length <= 134217728) return new byte[][] { array }; 
/*  782 */     byte[][] bigArray = ByteBigArrays.newBigArray(array.length);
/*  783 */     for (int i = 0; i < bigArray.length; ) { System.arraycopy(array, (int)start(i), bigArray[i], 0, (bigArray[i]).length); i++; }
/*  784 */      return bigArray;
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
/*      */   public static byte[][] ensureCapacity(byte[][] array, long length) {
/*  801 */     return ensureCapacity(array, length, length(array));
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
/*      */   public static byte[][] forceCapacity(byte[][] array, long length, long preserve) {
/*  815 */     ensureLength(length);
/*  816 */     int valid = array.length - ((array.length == 0 || (array.length > 0 && (array[array.length - 1]).length == 134217728)) ? 0 : 1);
/*  817 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  818 */     byte[][] base = Arrays.<byte[]>copyOf(array, baseLength);
/*  819 */     int residual = (int)(length & 0x7FFFFFFL);
/*  820 */     if (residual != 0) {
/*  821 */       for (int i = valid; i < baseLength - 1; ) { base[i] = new byte[134217728]; i++; }
/*  822 */        base[baseLength - 1] = new byte[residual];
/*      */     } else {
/*  824 */       for (int i = valid; i < baseLength; ) { base[i] = new byte[134217728]; i++; } 
/*  825 */     }  if (preserve - valid * 134217728L > 0L) copy(array, valid * 134217728L, base, valid * 134217728L, preserve - valid * 134217728L); 
/*  826 */     return base;
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
/*      */   public static byte[][] ensureCapacity(byte[][] array, long length, long preserve) {
/*  841 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
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
/*      */   public static byte[][] grow(byte[][] array, long length) {
/*  861 */     long oldLength = length(array);
/*  862 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
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
/*      */   public static byte[][] grow(byte[][] array, long length, long preserve) {
/*  883 */     long oldLength = length(array);
/*  884 */     return (length > oldLength) ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : array;
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
/*      */   public static byte[][] trim(byte[][] array, long length) {
/*  900 */     ensureLength(length);
/*  901 */     long oldLength = length(array);
/*  902 */     if (length >= oldLength) return array; 
/*  903 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  904 */     byte[][] base = Arrays.<byte[]>copyOf(array, baseLength);
/*  905 */     int residual = (int)(length & 0x7FFFFFFL);
/*  906 */     if (residual != 0) base[baseLength - 1] = ByteArrays.trim(base[baseLength - 1], residual); 
/*  907 */     return base;
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
/*      */   public static byte[][] setLength(byte[][] array, long length) {
/*  926 */     long oldLength = length(array);
/*  927 */     if (length == oldLength) return array; 
/*  928 */     if (length < oldLength) return trim(array, length); 
/*  929 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[][] copy(byte[][] array, long offset, long length) {
/*  939 */     ensureOffsetLength(array, offset, length);
/*      */     
/*  941 */     byte[][] a = ByteBigArrays.newBigArray(length);
/*  942 */     copy(array, offset, a, 0L, length);
/*  943 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[][] copy(byte[][] array) {
/*  951 */     byte[][] base = (byte[][])array.clone();
/*  952 */     for (int i = base.length; i-- != 0; base[i] = (byte[])array[i].clone());
/*  953 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(byte[][] array, byte value) {
/*  964 */     for (int i = array.length; i-- != 0; Arrays.fill(array[i], value));
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
/*      */   public static void fill(byte[][] array, long from, long to, byte value) {
/*  978 */     long length = length(array);
/*  979 */     ensureFromTo(length, from, to);
/*  980 */     if (length == 0L)
/*  981 */       return;  int fromSegment = segment(from);
/*  982 */     int toSegment = segment(to);
/*  983 */     int fromDispl = displacement(from);
/*  984 */     int toDispl = displacement(to);
/*  985 */     if (fromSegment == toSegment) {
/*  986 */       Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
/*      */       return;
/*      */     } 
/*  989 */     if (toDispl != 0) Arrays.fill(array[toSegment], 0, toDispl, value); 
/*  990 */     for (; --toSegment > fromSegment; Arrays.fill(array[toSegment], value));
/*  991 */     Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
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
/*      */   public static boolean equals(byte[][] a1, byte[][] a2) {
/* 1003 */     if (length(a1) != length(a2)) return false; 
/* 1004 */     int i = a1.length;
/*      */     
/* 1006 */     while (i-- != 0) {
/* 1007 */       byte[] t = a1[i];
/* 1008 */       byte[] u = a2[i];
/* 1009 */       int j = t.length;
/* 1010 */       while (j-- != 0) { if (t[j] != u[j]) return false;  }
/*      */     
/* 1012 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(byte[][] a) {
/* 1021 */     if (a == null) return "null"; 
/* 1022 */     long last = length(a) - 1L;
/* 1023 */     if (last == -1L) return "[]"; 
/* 1024 */     StringBuilder b = new StringBuilder();
/* 1025 */     b.append('['); long i;
/* 1026 */     for (i = 0L;; i++) {
/* 1027 */       b.append(String.valueOf(get(a, i)));
/* 1028 */       if (i == last) return b.append(']').toString(); 
/* 1029 */       b.append(", ");
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
/*      */   public static void ensureFromTo(byte[][] a, long from, long to) {
/* 1043 */     ensureFromTo(length(a), from, to);
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
/*      */   public static void ensureOffsetLength(byte[][] a, long offset, long length) {
/* 1056 */     ensureOffsetLength(length(a), offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureSameLength(byte[][] a, byte[][] b) {
/* 1065 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int get(int[][] array, long index) {
/* 1112 */     return array[segment(index)][displacement(index)];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void set(int[][] array, long index, int value) {
/* 1121 */     array[segment(index)][displacement(index)] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(int[][] array, long first, long second) {
/* 1130 */     int t = array[segment(first)][displacement(first)];
/* 1131 */     array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
/* 1132 */     array[segment(second)][displacement(second)] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void add(int[][] array, long index, int incr) {
/* 1141 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] + incr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mul(int[][] array, long index, int factor) {
/* 1150 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] * factor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void incr(int[][] array, long index) {
/* 1158 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] + 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void decr(int[][] array, long index) {
/* 1166 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] - 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long length(int[][] array) {
/* 1174 */     int length = array.length;
/* 1175 */     return (length == 0) ? 0L : (start(length - 1) + (array[length - 1]).length);
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
/*      */   public static void copy(int[][] srcArray, long srcPos, int[][] destArray, long destPos, long length) {
/* 1187 */     if (destPos <= srcPos) {
/* 1188 */       int srcSegment = segment(srcPos);
/* 1189 */       int destSegment = segment(destPos);
/* 1190 */       int srcDispl = displacement(srcPos);
/* 1191 */       int destDispl = displacement(destPos);
/*      */       
/* 1193 */       while (length > 0L) {
/* 1194 */         int l = (int)Math.min(length, Math.min((srcArray[srcSegment]).length - srcDispl, (destArray[destSegment]).length - destDispl));
/* 1195 */         System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
/* 1196 */         if ((srcDispl += l) == 134217728) {
/* 1197 */           srcDispl = 0;
/* 1198 */           srcSegment++;
/*      */         } 
/* 1200 */         if ((destDispl += l) == 134217728) {
/* 1201 */           destDispl = 0;
/* 1202 */           destSegment++;
/*      */         } 
/* 1204 */         length -= l;
/*      */       } 
/*      */     } else {
/*      */       
/* 1208 */       int srcSegment = segment(srcPos + length);
/* 1209 */       int destSegment = segment(destPos + length);
/* 1210 */       int srcDispl = displacement(srcPos + length);
/* 1211 */       int destDispl = displacement(destPos + length);
/*      */       
/* 1213 */       while (length > 0L) {
/* 1214 */         if (srcDispl == 0) {
/* 1215 */           srcDispl = 134217728;
/* 1216 */           srcSegment--;
/*      */         } 
/* 1218 */         if (destDispl == 0) {
/* 1219 */           destDispl = 134217728;
/* 1220 */           destSegment--;
/*      */         } 
/* 1222 */         int l = (int)Math.min(length, Math.min(srcDispl, destDispl));
/* 1223 */         System.arraycopy(srcArray[srcSegment], srcDispl - l, destArray[destSegment], destDispl - l, l);
/* 1224 */         srcDispl -= l;
/* 1225 */         destDispl -= l;
/* 1226 */         length -= l;
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
/*      */   public static void copyFromBig(int[][] srcArray, long srcPos, int[] destArray, int destPos, int length) {
/* 1239 */     int srcSegment = segment(srcPos);
/* 1240 */     int srcDispl = displacement(srcPos);
/*      */     
/* 1242 */     while (length > 0) {
/* 1243 */       int l = Math.min((srcArray[srcSegment]).length - srcDispl, length);
/* 1244 */       System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
/* 1245 */       if ((srcDispl += l) == 134217728) {
/* 1246 */         srcDispl = 0;
/* 1247 */         srcSegment++;
/*      */       } 
/* 1249 */       destPos += l;
/* 1250 */       length -= l;
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
/*      */   public static void copyToBig(int[] srcArray, int srcPos, int[][] destArray, long destPos, long length) {
/* 1262 */     int destSegment = segment(destPos);
/* 1263 */     int destDispl = displacement(destPos);
/*      */     
/* 1265 */     while (length > 0L) {
/* 1266 */       int l = (int)Math.min(((destArray[destSegment]).length - destDispl), length);
/* 1267 */       System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
/* 1268 */       if ((destDispl += l) == 134217728) {
/* 1269 */         destDispl = 0;
/* 1270 */         destSegment++;
/*      */       } 
/* 1272 */       srcPos += l;
/* 1273 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] wrap(int[] array) {
/* 1284 */     if (array.length == 0) return IntBigArrays.EMPTY_BIG_ARRAY; 
/* 1285 */     if (array.length <= 134217728) return new int[][] { array }; 
/* 1286 */     int[][] bigArray = IntBigArrays.newBigArray(array.length);
/* 1287 */     for (int i = 0; i < bigArray.length; ) { System.arraycopy(array, (int)start(i), bigArray[i], 0, (bigArray[i]).length); i++; }
/* 1288 */      return bigArray;
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
/*      */   public static int[][] ensureCapacity(int[][] array, long length) {
/* 1305 */     return ensureCapacity(array, length, length(array));
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
/*      */   public static int[][] forceCapacity(int[][] array, long length, long preserve) {
/* 1319 */     ensureLength(length);
/* 1320 */     int valid = array.length - ((array.length == 0 || (array.length > 0 && (array[array.length - 1]).length == 134217728)) ? 0 : 1);
/* 1321 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 1322 */     int[][] base = Arrays.<int[]>copyOf(array, baseLength);
/* 1323 */     int residual = (int)(length & 0x7FFFFFFL);
/* 1324 */     if (residual != 0) {
/* 1325 */       for (int i = valid; i < baseLength - 1; ) { base[i] = new int[134217728]; i++; }
/* 1326 */        base[baseLength - 1] = new int[residual];
/*      */     } else {
/* 1328 */       for (int i = valid; i < baseLength; ) { base[i] = new int[134217728]; i++; } 
/* 1329 */     }  if (preserve - valid * 134217728L > 0L) copy(array, valid * 134217728L, base, valid * 134217728L, preserve - valid * 134217728L); 
/* 1330 */     return base;
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
/*      */   public static int[][] ensureCapacity(int[][] array, long length, long preserve) {
/* 1345 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
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
/*      */   public static int[][] grow(int[][] array, long length) {
/* 1365 */     long oldLength = length(array);
/* 1366 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
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
/*      */   public static int[][] grow(int[][] array, long length, long preserve) {
/* 1387 */     long oldLength = length(array);
/* 1388 */     return (length > oldLength) ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : array;
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
/*      */   public static int[][] trim(int[][] array, long length) {
/* 1404 */     ensureLength(length);
/* 1405 */     long oldLength = length(array);
/* 1406 */     if (length >= oldLength) return array; 
/* 1407 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 1408 */     int[][] base = Arrays.<int[]>copyOf(array, baseLength);
/* 1409 */     int residual = (int)(length & 0x7FFFFFFL);
/* 1410 */     if (residual != 0) base[baseLength - 1] = IntArrays.trim(base[baseLength - 1], residual); 
/* 1411 */     return base;
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
/*      */   public static int[][] setLength(int[][] array, long length) {
/* 1430 */     long oldLength = length(array);
/* 1431 */     if (length == oldLength) return array; 
/* 1432 */     if (length < oldLength) return trim(array, length); 
/* 1433 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] copy(int[][] array, long offset, long length) {
/* 1443 */     ensureOffsetLength(array, offset, length);
/*      */     
/* 1445 */     int[][] a = IntBigArrays.newBigArray(length);
/* 1446 */     copy(array, offset, a, 0L, length);
/* 1447 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] copy(int[][] array) {
/* 1455 */     int[][] base = (int[][])array.clone();
/* 1456 */     for (int i = base.length; i-- != 0; base[i] = (int[])array[i].clone());
/* 1457 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(int[][] array, int value) {
/* 1468 */     for (int i = array.length; i-- != 0; Arrays.fill(array[i], value));
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
/*      */   public static void fill(int[][] array, long from, long to, int value) {
/* 1482 */     long length = length(array);
/* 1483 */     ensureFromTo(length, from, to);
/* 1484 */     if (length == 0L)
/* 1485 */       return;  int fromSegment = segment(from);
/* 1486 */     int toSegment = segment(to);
/* 1487 */     int fromDispl = displacement(from);
/* 1488 */     int toDispl = displacement(to);
/* 1489 */     if (fromSegment == toSegment) {
/* 1490 */       Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
/*      */       return;
/*      */     } 
/* 1493 */     if (toDispl != 0) Arrays.fill(array[toSegment], 0, toDispl, value); 
/* 1494 */     for (; --toSegment > fromSegment; Arrays.fill(array[toSegment], value));
/* 1495 */     Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
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
/*      */   public static boolean equals(int[][] a1, int[][] a2) {
/* 1507 */     if (length(a1) != length(a2)) return false; 
/* 1508 */     int i = a1.length;
/*      */     
/* 1510 */     while (i-- != 0) {
/* 1511 */       int[] t = a1[i];
/* 1512 */       int[] u = a2[i];
/* 1513 */       int j = t.length;
/* 1514 */       while (j-- != 0) { if (t[j] != u[j]) return false;  }
/*      */     
/* 1516 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(int[][] a) {
/* 1525 */     if (a == null) return "null"; 
/* 1526 */     long last = length(a) - 1L;
/* 1527 */     if (last == -1L) return "[]"; 
/* 1528 */     StringBuilder b = new StringBuilder();
/* 1529 */     b.append('['); long i;
/* 1530 */     for (i = 0L;; i++) {
/* 1531 */       b.append(String.valueOf(get(a, i)));
/* 1532 */       if (i == last) return b.append(']').toString(); 
/* 1533 */       b.append(", ");
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
/*      */   public static void ensureFromTo(int[][] a, long from, long to) {
/* 1547 */     ensureFromTo(length(a), from, to);
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
/*      */   public static void ensureOffsetLength(int[][] a, long offset, long length) {
/* 1560 */     ensureOffsetLength(length(a), offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureSameLength(int[][] a, int[][] b) {
/* 1569 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long get(long[][] array, long index) {
/* 1616 */     return array[segment(index)][displacement(index)];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void set(long[][] array, long index, long value) {
/* 1625 */     array[segment(index)][displacement(index)] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(long[][] array, long first, long second) {
/* 1634 */     long t = array[segment(first)][displacement(first)];
/* 1635 */     array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
/* 1636 */     array[segment(second)][displacement(second)] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void add(long[][] array, long index, long incr) {
/* 1645 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] + incr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mul(long[][] array, long index, long factor) {
/* 1654 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] * factor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void incr(long[][] array, long index) {
/* 1662 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] + 1L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void decr(long[][] array, long index) {
/* 1670 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] - 1L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long length(long[][] array) {
/* 1678 */     int length = array.length;
/* 1679 */     return (length == 0) ? 0L : (start(length - 1) + (array[length - 1]).length);
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
/*      */   public static void copy(long[][] srcArray, long srcPos, long[][] destArray, long destPos, long length) {
/* 1691 */     if (destPos <= srcPos) {
/* 1692 */       int srcSegment = segment(srcPos);
/* 1693 */       int destSegment = segment(destPos);
/* 1694 */       int srcDispl = displacement(srcPos);
/* 1695 */       int destDispl = displacement(destPos);
/*      */       
/* 1697 */       while (length > 0L) {
/* 1698 */         int l = (int)Math.min(length, Math.min((srcArray[srcSegment]).length - srcDispl, (destArray[destSegment]).length - destDispl));
/* 1699 */         System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
/* 1700 */         if ((srcDispl += l) == 134217728) {
/* 1701 */           srcDispl = 0;
/* 1702 */           srcSegment++;
/*      */         } 
/* 1704 */         if ((destDispl += l) == 134217728) {
/* 1705 */           destDispl = 0;
/* 1706 */           destSegment++;
/*      */         } 
/* 1708 */         length -= l;
/*      */       } 
/*      */     } else {
/*      */       
/* 1712 */       int srcSegment = segment(srcPos + length);
/* 1713 */       int destSegment = segment(destPos + length);
/* 1714 */       int srcDispl = displacement(srcPos + length);
/* 1715 */       int destDispl = displacement(destPos + length);
/*      */       
/* 1717 */       while (length > 0L) {
/* 1718 */         if (srcDispl == 0) {
/* 1719 */           srcDispl = 134217728;
/* 1720 */           srcSegment--;
/*      */         } 
/* 1722 */         if (destDispl == 0) {
/* 1723 */           destDispl = 134217728;
/* 1724 */           destSegment--;
/*      */         } 
/* 1726 */         int l = (int)Math.min(length, Math.min(srcDispl, destDispl));
/* 1727 */         System.arraycopy(srcArray[srcSegment], srcDispl - l, destArray[destSegment], destDispl - l, l);
/* 1728 */         srcDispl -= l;
/* 1729 */         destDispl -= l;
/* 1730 */         length -= l;
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
/*      */   public static void copyFromBig(long[][] srcArray, long srcPos, long[] destArray, int destPos, int length) {
/* 1743 */     int srcSegment = segment(srcPos);
/* 1744 */     int srcDispl = displacement(srcPos);
/*      */     
/* 1746 */     while (length > 0) {
/* 1747 */       int l = Math.min((srcArray[srcSegment]).length - srcDispl, length);
/* 1748 */       System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
/* 1749 */       if ((srcDispl += l) == 134217728) {
/* 1750 */         srcDispl = 0;
/* 1751 */         srcSegment++;
/*      */       } 
/* 1753 */       destPos += l;
/* 1754 */       length -= l;
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
/*      */   public static void copyToBig(long[] srcArray, int srcPos, long[][] destArray, long destPos, long length) {
/* 1766 */     int destSegment = segment(destPos);
/* 1767 */     int destDispl = displacement(destPos);
/*      */     
/* 1769 */     while (length > 0L) {
/* 1770 */       int l = (int)Math.min(((destArray[destSegment]).length - destDispl), length);
/* 1771 */       System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
/* 1772 */       if ((destDispl += l) == 134217728) {
/* 1773 */         destDispl = 0;
/* 1774 */         destSegment++;
/*      */       } 
/* 1776 */       srcPos += l;
/* 1777 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[][] wrap(long[] array) {
/* 1788 */     if (array.length == 0) return LongBigArrays.EMPTY_BIG_ARRAY; 
/* 1789 */     if (array.length <= 134217728) return new long[][] { array }; 
/* 1790 */     long[][] bigArray = LongBigArrays.newBigArray(array.length);
/* 1791 */     for (int i = 0; i < bigArray.length; ) { System.arraycopy(array, (int)start(i), bigArray[i], 0, (bigArray[i]).length); i++; }
/* 1792 */      return bigArray;
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
/*      */   public static long[][] ensureCapacity(long[][] array, long length) {
/* 1809 */     return ensureCapacity(array, length, length(array));
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
/*      */   public static long[][] forceCapacity(long[][] array, long length, long preserve) {
/* 1823 */     ensureLength(length);
/* 1824 */     int valid = array.length - ((array.length == 0 || (array.length > 0 && (array[array.length - 1]).length == 134217728)) ? 0 : 1);
/* 1825 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 1826 */     long[][] base = Arrays.<long[]>copyOf(array, baseLength);
/* 1827 */     int residual = (int)(length & 0x7FFFFFFL);
/* 1828 */     if (residual != 0) {
/* 1829 */       for (int i = valid; i < baseLength - 1; ) { base[i] = new long[134217728]; i++; }
/* 1830 */        base[baseLength - 1] = new long[residual];
/*      */     } else {
/* 1832 */       for (int i = valid; i < baseLength; ) { base[i] = new long[134217728]; i++; } 
/* 1833 */     }  if (preserve - valid * 134217728L > 0L) copy(array, valid * 134217728L, base, valid * 134217728L, preserve - valid * 134217728L); 
/* 1834 */     return base;
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
/*      */   public static long[][] ensureCapacity(long[][] array, long length, long preserve) {
/* 1849 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
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
/*      */   public static long[][] grow(long[][] array, long length) {
/* 1869 */     long oldLength = length(array);
/* 1870 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
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
/*      */   public static long[][] grow(long[][] array, long length, long preserve) {
/* 1891 */     long oldLength = length(array);
/* 1892 */     return (length > oldLength) ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : array;
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
/*      */   public static long[][] trim(long[][] array, long length) {
/* 1908 */     ensureLength(length);
/* 1909 */     long oldLength = length(array);
/* 1910 */     if (length >= oldLength) return array; 
/* 1911 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 1912 */     long[][] base = Arrays.<long[]>copyOf(array, baseLength);
/* 1913 */     int residual = (int)(length & 0x7FFFFFFL);
/* 1914 */     if (residual != 0) base[baseLength - 1] = LongArrays.trim(base[baseLength - 1], residual); 
/* 1915 */     return base;
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
/*      */   public static long[][] setLength(long[][] array, long length) {
/* 1934 */     long oldLength = length(array);
/* 1935 */     if (length == oldLength) return array; 
/* 1936 */     if (length < oldLength) return trim(array, length); 
/* 1937 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[][] copy(long[][] array, long offset, long length) {
/* 1947 */     ensureOffsetLength(array, offset, length);
/*      */     
/* 1949 */     long[][] a = LongBigArrays.newBigArray(length);
/* 1950 */     copy(array, offset, a, 0L, length);
/* 1951 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[][] copy(long[][] array) {
/* 1959 */     long[][] base = (long[][])array.clone();
/* 1960 */     for (int i = base.length; i-- != 0; base[i] = (long[])array[i].clone());
/* 1961 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(long[][] array, long value) {
/* 1972 */     for (int i = array.length; i-- != 0; Arrays.fill(array[i], value));
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
/*      */   public static void fill(long[][] array, long from, long to, long value) {
/* 1986 */     long length = length(array);
/* 1987 */     ensureFromTo(length, from, to);
/* 1988 */     if (length == 0L)
/* 1989 */       return;  int fromSegment = segment(from);
/* 1990 */     int toSegment = segment(to);
/* 1991 */     int fromDispl = displacement(from);
/* 1992 */     int toDispl = displacement(to);
/* 1993 */     if (fromSegment == toSegment) {
/* 1994 */       Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
/*      */       return;
/*      */     } 
/* 1997 */     if (toDispl != 0) Arrays.fill(array[toSegment], 0, toDispl, value); 
/* 1998 */     for (; --toSegment > fromSegment; Arrays.fill(array[toSegment], value));
/* 1999 */     Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
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
/*      */   public static boolean equals(long[][] a1, long[][] a2) {
/* 2011 */     if (length(a1) != length(a2)) return false; 
/* 2012 */     int i = a1.length;
/*      */     
/* 2014 */     while (i-- != 0) {
/* 2015 */       long[] t = a1[i];
/* 2016 */       long[] u = a2[i];
/* 2017 */       int j = t.length;
/* 2018 */       while (j-- != 0) { if (t[j] != u[j]) return false;  }
/*      */     
/* 2020 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(long[][] a) {
/* 2029 */     if (a == null) return "null"; 
/* 2030 */     long last = length(a) - 1L;
/* 2031 */     if (last == -1L) return "[]"; 
/* 2032 */     StringBuilder b = new StringBuilder();
/* 2033 */     b.append('['); long i;
/* 2034 */     for (i = 0L;; i++) {
/* 2035 */       b.append(String.valueOf(get(a, i)));
/* 2036 */       if (i == last) return b.append(']').toString(); 
/* 2037 */       b.append(", ");
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
/*      */   public static void ensureFromTo(long[][] a, long from, long to) {
/* 2051 */     ensureFromTo(length(a), from, to);
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
/*      */   public static void ensureOffsetLength(long[][] a, long offset, long length) {
/* 2064 */     ensureOffsetLength(length(a), offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureSameLength(long[][] a, long[][] b) {
/* 2073 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double get(double[][] array, long index) {
/* 2120 */     return array[segment(index)][displacement(index)];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void set(double[][] array, long index, double value) {
/* 2129 */     array[segment(index)][displacement(index)] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(double[][] array, long first, long second) {
/* 2138 */     double t = array[segment(first)][displacement(first)];
/* 2139 */     array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
/* 2140 */     array[segment(second)][displacement(second)] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void add(double[][] array, long index, double incr) {
/* 2149 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] + incr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mul(double[][] array, long index, double factor) {
/* 2158 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] * factor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void incr(double[][] array, long index) {
/* 2166 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] + 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void decr(double[][] array, long index) {
/* 2174 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] - 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long length(double[][] array) {
/* 2182 */     int length = array.length;
/* 2183 */     return (length == 0) ? 0L : (start(length - 1) + (array[length - 1]).length);
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
/*      */   public static void copy(double[][] srcArray, long srcPos, double[][] destArray, long destPos, long length) {
/* 2195 */     if (destPos <= srcPos) {
/* 2196 */       int srcSegment = segment(srcPos);
/* 2197 */       int destSegment = segment(destPos);
/* 2198 */       int srcDispl = displacement(srcPos);
/* 2199 */       int destDispl = displacement(destPos);
/*      */       
/* 2201 */       while (length > 0L) {
/* 2202 */         int l = (int)Math.min(length, Math.min((srcArray[srcSegment]).length - srcDispl, (destArray[destSegment]).length - destDispl));
/* 2203 */         System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
/* 2204 */         if ((srcDispl += l) == 134217728) {
/* 2205 */           srcDispl = 0;
/* 2206 */           srcSegment++;
/*      */         } 
/* 2208 */         if ((destDispl += l) == 134217728) {
/* 2209 */           destDispl = 0;
/* 2210 */           destSegment++;
/*      */         } 
/* 2212 */         length -= l;
/*      */       } 
/*      */     } else {
/*      */       
/* 2216 */       int srcSegment = segment(srcPos + length);
/* 2217 */       int destSegment = segment(destPos + length);
/* 2218 */       int srcDispl = displacement(srcPos + length);
/* 2219 */       int destDispl = displacement(destPos + length);
/*      */       
/* 2221 */       while (length > 0L) {
/* 2222 */         if (srcDispl == 0) {
/* 2223 */           srcDispl = 134217728;
/* 2224 */           srcSegment--;
/*      */         } 
/* 2226 */         if (destDispl == 0) {
/* 2227 */           destDispl = 134217728;
/* 2228 */           destSegment--;
/*      */         } 
/* 2230 */         int l = (int)Math.min(length, Math.min(srcDispl, destDispl));
/* 2231 */         System.arraycopy(srcArray[srcSegment], srcDispl - l, destArray[destSegment], destDispl - l, l);
/* 2232 */         srcDispl -= l;
/* 2233 */         destDispl -= l;
/* 2234 */         length -= l;
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
/*      */   public static void copyFromBig(double[][] srcArray, long srcPos, double[] destArray, int destPos, int length) {
/* 2247 */     int srcSegment = segment(srcPos);
/* 2248 */     int srcDispl = displacement(srcPos);
/*      */     
/* 2250 */     while (length > 0) {
/* 2251 */       int l = Math.min((srcArray[srcSegment]).length - srcDispl, length);
/* 2252 */       System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
/* 2253 */       if ((srcDispl += l) == 134217728) {
/* 2254 */         srcDispl = 0;
/* 2255 */         srcSegment++;
/*      */       } 
/* 2257 */       destPos += l;
/* 2258 */       length -= l;
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
/*      */   public static void copyToBig(double[] srcArray, int srcPos, double[][] destArray, long destPos, long length) {
/* 2270 */     int destSegment = segment(destPos);
/* 2271 */     int destDispl = displacement(destPos);
/*      */     
/* 2273 */     while (length > 0L) {
/* 2274 */       int l = (int)Math.min(((destArray[destSegment]).length - destDispl), length);
/* 2275 */       System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
/* 2276 */       if ((destDispl += l) == 134217728) {
/* 2277 */         destDispl = 0;
/* 2278 */         destSegment++;
/*      */       } 
/* 2280 */       srcPos += l;
/* 2281 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] wrap(double[] array) {
/* 2292 */     if (array.length == 0) return DoubleBigArrays.EMPTY_BIG_ARRAY; 
/* 2293 */     if (array.length <= 134217728) return new double[][] { array }; 
/* 2294 */     double[][] bigArray = DoubleBigArrays.newBigArray(array.length);
/* 2295 */     for (int i = 0; i < bigArray.length; ) { System.arraycopy(array, (int)start(i), bigArray[i], 0, (bigArray[i]).length); i++; }
/* 2296 */      return bigArray;
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
/*      */   public static double[][] ensureCapacity(double[][] array, long length) {
/* 2313 */     return ensureCapacity(array, length, length(array));
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
/*      */   public static double[][] forceCapacity(double[][] array, long length, long preserve) {
/* 2327 */     ensureLength(length);
/* 2328 */     int valid = array.length - ((array.length == 0 || (array.length > 0 && (array[array.length - 1]).length == 134217728)) ? 0 : 1);
/* 2329 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 2330 */     double[][] base = Arrays.<double[]>copyOf(array, baseLength);
/* 2331 */     int residual = (int)(length & 0x7FFFFFFL);
/* 2332 */     if (residual != 0) {
/* 2333 */       for (int i = valid; i < baseLength - 1; ) { base[i] = new double[134217728]; i++; }
/* 2334 */        base[baseLength - 1] = new double[residual];
/*      */     } else {
/* 2336 */       for (int i = valid; i < baseLength; ) { base[i] = new double[134217728]; i++; } 
/* 2337 */     }  if (preserve - valid * 134217728L > 0L) copy(array, valid * 134217728L, base, valid * 134217728L, preserve - valid * 134217728L); 
/* 2338 */     return base;
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
/*      */   public static double[][] ensureCapacity(double[][] array, long length, long preserve) {
/* 2353 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
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
/*      */   public static double[][] grow(double[][] array, long length) {
/* 2373 */     long oldLength = length(array);
/* 2374 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
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
/*      */   public static double[][] grow(double[][] array, long length, long preserve) {
/* 2395 */     long oldLength = length(array);
/* 2396 */     return (length > oldLength) ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : array;
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
/*      */   public static double[][] trim(double[][] array, long length) {
/* 2412 */     ensureLength(length);
/* 2413 */     long oldLength = length(array);
/* 2414 */     if (length >= oldLength) return array; 
/* 2415 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 2416 */     double[][] base = Arrays.<double[]>copyOf(array, baseLength);
/* 2417 */     int residual = (int)(length & 0x7FFFFFFL);
/* 2418 */     if (residual != 0) base[baseLength - 1] = DoubleArrays.trim(base[baseLength - 1], residual); 
/* 2419 */     return base;
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
/*      */   public static double[][] setLength(double[][] array, long length) {
/* 2438 */     long oldLength = length(array);
/* 2439 */     if (length == oldLength) return array; 
/* 2440 */     if (length < oldLength) return trim(array, length); 
/* 2441 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] copy(double[][] array, long offset, long length) {
/* 2451 */     ensureOffsetLength(array, offset, length);
/*      */     
/* 2453 */     double[][] a = DoubleBigArrays.newBigArray(length);
/* 2454 */     copy(array, offset, a, 0L, length);
/* 2455 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] copy(double[][] array) {
/* 2463 */     double[][] base = (double[][])array.clone();
/* 2464 */     for (int i = base.length; i-- != 0; base[i] = (double[])array[i].clone());
/* 2465 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(double[][] array, double value) {
/* 2476 */     for (int i = array.length; i-- != 0; Arrays.fill(array[i], value));
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
/*      */   public static void fill(double[][] array, long from, long to, double value) {
/* 2490 */     long length = length(array);
/* 2491 */     ensureFromTo(length, from, to);
/* 2492 */     if (length == 0L)
/* 2493 */       return;  int fromSegment = segment(from);
/* 2494 */     int toSegment = segment(to);
/* 2495 */     int fromDispl = displacement(from);
/* 2496 */     int toDispl = displacement(to);
/* 2497 */     if (fromSegment == toSegment) {
/* 2498 */       Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
/*      */       return;
/*      */     } 
/* 2501 */     if (toDispl != 0) Arrays.fill(array[toSegment], 0, toDispl, value); 
/* 2502 */     for (; --toSegment > fromSegment; Arrays.fill(array[toSegment], value));
/* 2503 */     Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
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
/*      */   public static boolean equals(double[][] a1, double[][] a2) {
/* 2515 */     if (length(a1) != length(a2)) return false; 
/* 2516 */     int i = a1.length;
/*      */     
/* 2518 */     while (i-- != 0) {
/* 2519 */       double[] t = a1[i];
/* 2520 */       double[] u = a2[i];
/* 2521 */       int j = t.length;
/* 2522 */       while (j-- != 0) { if (Double.doubleToLongBits(t[j]) != Double.doubleToLongBits(u[j])) return false;  }
/*      */     
/* 2524 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(double[][] a) {
/* 2533 */     if (a == null) return "null"; 
/* 2534 */     long last = length(a) - 1L;
/* 2535 */     if (last == -1L) return "[]"; 
/* 2536 */     StringBuilder b = new StringBuilder();
/* 2537 */     b.append('['); long i;
/* 2538 */     for (i = 0L;; i++) {
/* 2539 */       b.append(String.valueOf(get(a, i)));
/* 2540 */       if (i == last) return b.append(']').toString(); 
/* 2541 */       b.append(", ");
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
/*      */   public static void ensureFromTo(double[][] a, long from, long to) {
/* 2555 */     ensureFromTo(length(a), from, to);
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
/*      */   public static void ensureOffsetLength(double[][] a, long offset, long length) {
/* 2568 */     ensureOffsetLength(length(a), offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureSameLength(double[][] a, double[][] b) {
/* 2577 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean get(boolean[][] array, long index) {
/* 2624 */     return array[segment(index)][displacement(index)];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void set(boolean[][] array, long index, boolean value) {
/* 2633 */     array[segment(index)][displacement(index)] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(boolean[][] array, long first, long second) {
/* 2642 */     boolean t = array[segment(first)][displacement(first)];
/* 2643 */     array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
/* 2644 */     array[segment(second)][displacement(second)] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long length(boolean[][] array) {
/* 2652 */     int length = array.length;
/* 2653 */     return (length == 0) ? 0L : (start(length - 1) + (array[length - 1]).length);
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
/*      */   public static void copy(boolean[][] srcArray, long srcPos, boolean[][] destArray, long destPos, long length) {
/* 2665 */     if (destPos <= srcPos) {
/* 2666 */       int srcSegment = segment(srcPos);
/* 2667 */       int destSegment = segment(destPos);
/* 2668 */       int srcDispl = displacement(srcPos);
/* 2669 */       int destDispl = displacement(destPos);
/*      */       
/* 2671 */       while (length > 0L) {
/* 2672 */         int l = (int)Math.min(length, Math.min((srcArray[srcSegment]).length - srcDispl, (destArray[destSegment]).length - destDispl));
/* 2673 */         System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
/* 2674 */         if ((srcDispl += l) == 134217728) {
/* 2675 */           srcDispl = 0;
/* 2676 */           srcSegment++;
/*      */         } 
/* 2678 */         if ((destDispl += l) == 134217728) {
/* 2679 */           destDispl = 0;
/* 2680 */           destSegment++;
/*      */         } 
/* 2682 */         length -= l;
/*      */       } 
/*      */     } else {
/*      */       
/* 2686 */       int srcSegment = segment(srcPos + length);
/* 2687 */       int destSegment = segment(destPos + length);
/* 2688 */       int srcDispl = displacement(srcPos + length);
/* 2689 */       int destDispl = displacement(destPos + length);
/*      */       
/* 2691 */       while (length > 0L) {
/* 2692 */         if (srcDispl == 0) {
/* 2693 */           srcDispl = 134217728;
/* 2694 */           srcSegment--;
/*      */         } 
/* 2696 */         if (destDispl == 0) {
/* 2697 */           destDispl = 134217728;
/* 2698 */           destSegment--;
/*      */         } 
/* 2700 */         int l = (int)Math.min(length, Math.min(srcDispl, destDispl));
/* 2701 */         System.arraycopy(srcArray[srcSegment], srcDispl - l, destArray[destSegment], destDispl - l, l);
/* 2702 */         srcDispl -= l;
/* 2703 */         destDispl -= l;
/* 2704 */         length -= l;
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
/*      */   public static void copyFromBig(boolean[][] srcArray, long srcPos, boolean[] destArray, int destPos, int length) {
/* 2717 */     int srcSegment = segment(srcPos);
/* 2718 */     int srcDispl = displacement(srcPos);
/*      */     
/* 2720 */     while (length > 0) {
/* 2721 */       int l = Math.min((srcArray[srcSegment]).length - srcDispl, length);
/* 2722 */       System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
/* 2723 */       if ((srcDispl += l) == 134217728) {
/* 2724 */         srcDispl = 0;
/* 2725 */         srcSegment++;
/*      */       } 
/* 2727 */       destPos += l;
/* 2728 */       length -= l;
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
/*      */   public static void copyToBig(boolean[] srcArray, int srcPos, boolean[][] destArray, long destPos, long length) {
/* 2740 */     int destSegment = segment(destPos);
/* 2741 */     int destDispl = displacement(destPos);
/*      */     
/* 2743 */     while (length > 0L) {
/* 2744 */       int l = (int)Math.min(((destArray[destSegment]).length - destDispl), length);
/* 2745 */       System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
/* 2746 */       if ((destDispl += l) == 134217728) {
/* 2747 */         destDispl = 0;
/* 2748 */         destSegment++;
/*      */       } 
/* 2750 */       srcPos += l;
/* 2751 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[][] wrap(boolean[] array) {
/* 2762 */     if (array.length == 0) return BooleanBigArrays.EMPTY_BIG_ARRAY; 
/* 2763 */     if (array.length <= 134217728) return new boolean[][] { array }; 
/* 2764 */     boolean[][] bigArray = BooleanBigArrays.newBigArray(array.length);
/* 2765 */     for (int i = 0; i < bigArray.length; ) { System.arraycopy(array, (int)start(i), bigArray[i], 0, (bigArray[i]).length); i++; }
/* 2766 */      return bigArray;
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
/*      */   public static boolean[][] ensureCapacity(boolean[][] array, long length) {
/* 2783 */     return ensureCapacity(array, length, length(array));
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
/*      */   public static boolean[][] forceCapacity(boolean[][] array, long length, long preserve) {
/* 2797 */     ensureLength(length);
/* 2798 */     int valid = array.length - ((array.length == 0 || (array.length > 0 && (array[array.length - 1]).length == 134217728)) ? 0 : 1);
/* 2799 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 2800 */     boolean[][] base = Arrays.<boolean[]>copyOf(array, baseLength);
/* 2801 */     int residual = (int)(length & 0x7FFFFFFL);
/* 2802 */     if (residual != 0) {
/* 2803 */       for (int i = valid; i < baseLength - 1; ) { base[i] = new boolean[134217728]; i++; }
/* 2804 */        base[baseLength - 1] = new boolean[residual];
/*      */     } else {
/* 2806 */       for (int i = valid; i < baseLength; ) { base[i] = new boolean[134217728]; i++; } 
/* 2807 */     }  if (preserve - valid * 134217728L > 0L) copy(array, valid * 134217728L, base, valid * 134217728L, preserve - valid * 134217728L); 
/* 2808 */     return base;
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
/*      */   public static boolean[][] ensureCapacity(boolean[][] array, long length, long preserve) {
/* 2823 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
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
/*      */   public static boolean[][] grow(boolean[][] array, long length) {
/* 2843 */     long oldLength = length(array);
/* 2844 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
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
/*      */   public static boolean[][] grow(boolean[][] array, long length, long preserve) {
/* 2865 */     long oldLength = length(array);
/* 2866 */     return (length > oldLength) ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : array;
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
/*      */   public static boolean[][] trim(boolean[][] array, long length) {
/* 2882 */     ensureLength(length);
/* 2883 */     long oldLength = length(array);
/* 2884 */     if (length >= oldLength) return array; 
/* 2885 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 2886 */     boolean[][] base = Arrays.<boolean[]>copyOf(array, baseLength);
/* 2887 */     int residual = (int)(length & 0x7FFFFFFL);
/* 2888 */     if (residual != 0) base[baseLength - 1] = BooleanArrays.trim(base[baseLength - 1], residual); 
/* 2889 */     return base;
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
/*      */   public static boolean[][] setLength(boolean[][] array, long length) {
/* 2908 */     long oldLength = length(array);
/* 2909 */     if (length == oldLength) return array; 
/* 2910 */     if (length < oldLength) return trim(array, length); 
/* 2911 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[][] copy(boolean[][] array, long offset, long length) {
/* 2921 */     ensureOffsetLength(array, offset, length);
/*      */     
/* 2923 */     boolean[][] a = BooleanBigArrays.newBigArray(length);
/* 2924 */     copy(array, offset, a, 0L, length);
/* 2925 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[][] copy(boolean[][] array) {
/* 2933 */     boolean[][] base = (boolean[][])array.clone();
/* 2934 */     for (int i = base.length; i-- != 0; base[i] = (boolean[])array[i].clone());
/* 2935 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(boolean[][] array, boolean value) {
/* 2946 */     for (int i = array.length; i-- != 0; Arrays.fill(array[i], value));
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
/*      */   public static void fill(boolean[][] array, long from, long to, boolean value) {
/* 2960 */     long length = length(array);
/* 2961 */     ensureFromTo(length, from, to);
/* 2962 */     if (length == 0L)
/* 2963 */       return;  int fromSegment = segment(from);
/* 2964 */     int toSegment = segment(to);
/* 2965 */     int fromDispl = displacement(from);
/* 2966 */     int toDispl = displacement(to);
/* 2967 */     if (fromSegment == toSegment) {
/* 2968 */       Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
/*      */       return;
/*      */     } 
/* 2971 */     if (toDispl != 0) Arrays.fill(array[toSegment], 0, toDispl, value); 
/* 2972 */     for (; --toSegment > fromSegment; Arrays.fill(array[toSegment], value));
/* 2973 */     Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
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
/*      */   public static boolean equals(boolean[][] a1, boolean[][] a2) {
/* 2985 */     if (length(a1) != length(a2)) return false; 
/* 2986 */     int i = a1.length;
/*      */     
/* 2988 */     while (i-- != 0) {
/* 2989 */       boolean[] t = a1[i];
/* 2990 */       boolean[] u = a2[i];
/* 2991 */       int j = t.length;
/* 2992 */       while (j-- != 0) { if (t[j] != u[j]) return false;  }
/*      */     
/* 2994 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(boolean[][] a) {
/* 3003 */     if (a == null) return "null"; 
/* 3004 */     long last = length(a) - 1L;
/* 3005 */     if (last == -1L) return "[]"; 
/* 3006 */     StringBuilder b = new StringBuilder();
/* 3007 */     b.append('['); long i;
/* 3008 */     for (i = 0L;; i++) {
/* 3009 */       b.append(String.valueOf(get(a, i)));
/* 3010 */       if (i == last) return b.append(']').toString(); 
/* 3011 */       b.append(", ");
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
/*      */   public static void ensureFromTo(boolean[][] a, long from, long to) {
/* 3025 */     ensureFromTo(length(a), from, to);
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
/*      */   public static void ensureOffsetLength(boolean[][] a, long offset, long length) {
/* 3038 */     ensureOffsetLength(length(a), offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureSameLength(boolean[][] a, boolean[][] b) {
/* 3047 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short get(short[][] array, long index) {
/* 3094 */     return array[segment(index)][displacement(index)];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void set(short[][] array, long index, short value) {
/* 3103 */     array[segment(index)][displacement(index)] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(short[][] array, long first, long second) {
/* 3112 */     short t = array[segment(first)][displacement(first)];
/* 3113 */     array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
/* 3114 */     array[segment(second)][displacement(second)] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void add(short[][] array, long index, short incr) {
/* 3123 */     array[segment(index)][displacement(index)] = (short)(array[segment(index)][displacement(index)] + incr);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mul(short[][] array, long index, short factor) {
/* 3132 */     array[segment(index)][displacement(index)] = (short)(array[segment(index)][displacement(index)] * factor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void incr(short[][] array, long index) {
/* 3140 */     array[segment(index)][displacement(index)] = (short)(array[segment(index)][displacement(index)] + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void decr(short[][] array, long index) {
/* 3148 */     array[segment(index)][displacement(index)] = (short)(array[segment(index)][displacement(index)] - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long length(short[][] array) {
/* 3156 */     int length = array.length;
/* 3157 */     return (length == 0) ? 0L : (start(length - 1) + (array[length - 1]).length);
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
/*      */   public static void copy(short[][] srcArray, long srcPos, short[][] destArray, long destPos, long length) {
/* 3169 */     if (destPos <= srcPos) {
/* 3170 */       int srcSegment = segment(srcPos);
/* 3171 */       int destSegment = segment(destPos);
/* 3172 */       int srcDispl = displacement(srcPos);
/* 3173 */       int destDispl = displacement(destPos);
/*      */       
/* 3175 */       while (length > 0L) {
/* 3176 */         int l = (int)Math.min(length, Math.min((srcArray[srcSegment]).length - srcDispl, (destArray[destSegment]).length - destDispl));
/* 3177 */         System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
/* 3178 */         if ((srcDispl += l) == 134217728) {
/* 3179 */           srcDispl = 0;
/* 3180 */           srcSegment++;
/*      */         } 
/* 3182 */         if ((destDispl += l) == 134217728) {
/* 3183 */           destDispl = 0;
/* 3184 */           destSegment++;
/*      */         } 
/* 3186 */         length -= l;
/*      */       } 
/*      */     } else {
/*      */       
/* 3190 */       int srcSegment = segment(srcPos + length);
/* 3191 */       int destSegment = segment(destPos + length);
/* 3192 */       int srcDispl = displacement(srcPos + length);
/* 3193 */       int destDispl = displacement(destPos + length);
/*      */       
/* 3195 */       while (length > 0L) {
/* 3196 */         if (srcDispl == 0) {
/* 3197 */           srcDispl = 134217728;
/* 3198 */           srcSegment--;
/*      */         } 
/* 3200 */         if (destDispl == 0) {
/* 3201 */           destDispl = 134217728;
/* 3202 */           destSegment--;
/*      */         } 
/* 3204 */         int l = (int)Math.min(length, Math.min(srcDispl, destDispl));
/* 3205 */         System.arraycopy(srcArray[srcSegment], srcDispl - l, destArray[destSegment], destDispl - l, l);
/* 3206 */         srcDispl -= l;
/* 3207 */         destDispl -= l;
/* 3208 */         length -= l;
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
/*      */   public static void copyFromBig(short[][] srcArray, long srcPos, short[] destArray, int destPos, int length) {
/* 3221 */     int srcSegment = segment(srcPos);
/* 3222 */     int srcDispl = displacement(srcPos);
/*      */     
/* 3224 */     while (length > 0) {
/* 3225 */       int l = Math.min((srcArray[srcSegment]).length - srcDispl, length);
/* 3226 */       System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
/* 3227 */       if ((srcDispl += l) == 134217728) {
/* 3228 */         srcDispl = 0;
/* 3229 */         srcSegment++;
/*      */       } 
/* 3231 */       destPos += l;
/* 3232 */       length -= l;
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
/*      */   public static void copyToBig(short[] srcArray, int srcPos, short[][] destArray, long destPos, long length) {
/* 3244 */     int destSegment = segment(destPos);
/* 3245 */     int destDispl = displacement(destPos);
/*      */     
/* 3247 */     while (length > 0L) {
/* 3248 */       int l = (int)Math.min(((destArray[destSegment]).length - destDispl), length);
/* 3249 */       System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
/* 3250 */       if ((destDispl += l) == 134217728) {
/* 3251 */         destDispl = 0;
/* 3252 */         destSegment++;
/*      */       } 
/* 3254 */       srcPos += l;
/* 3255 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[][] wrap(short[] array) {
/* 3266 */     if (array.length == 0) return ShortBigArrays.EMPTY_BIG_ARRAY; 
/* 3267 */     if (array.length <= 134217728) return new short[][] { array }; 
/* 3268 */     short[][] bigArray = ShortBigArrays.newBigArray(array.length);
/* 3269 */     for (int i = 0; i < bigArray.length; ) { System.arraycopy(array, (int)start(i), bigArray[i], 0, (bigArray[i]).length); i++; }
/* 3270 */      return bigArray;
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
/*      */   public static short[][] ensureCapacity(short[][] array, long length) {
/* 3287 */     return ensureCapacity(array, length, length(array));
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
/*      */   public static short[][] forceCapacity(short[][] array, long length, long preserve) {
/* 3301 */     ensureLength(length);
/* 3302 */     int valid = array.length - ((array.length == 0 || (array.length > 0 && (array[array.length - 1]).length == 134217728)) ? 0 : 1);
/* 3303 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 3304 */     short[][] base = Arrays.<short[]>copyOf(array, baseLength);
/* 3305 */     int residual = (int)(length & 0x7FFFFFFL);
/* 3306 */     if (residual != 0) {
/* 3307 */       for (int i = valid; i < baseLength - 1; ) { base[i] = new short[134217728]; i++; }
/* 3308 */        base[baseLength - 1] = new short[residual];
/*      */     } else {
/* 3310 */       for (int i = valid; i < baseLength; ) { base[i] = new short[134217728]; i++; } 
/* 3311 */     }  if (preserve - valid * 134217728L > 0L) copy(array, valid * 134217728L, base, valid * 134217728L, preserve - valid * 134217728L); 
/* 3312 */     return base;
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
/*      */   public static short[][] ensureCapacity(short[][] array, long length, long preserve) {
/* 3327 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
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
/*      */   public static short[][] grow(short[][] array, long length) {
/* 3347 */     long oldLength = length(array);
/* 3348 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
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
/*      */   public static short[][] grow(short[][] array, long length, long preserve) {
/* 3369 */     long oldLength = length(array);
/* 3370 */     return (length > oldLength) ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : array;
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
/*      */   public static short[][] trim(short[][] array, long length) {
/* 3386 */     ensureLength(length);
/* 3387 */     long oldLength = length(array);
/* 3388 */     if (length >= oldLength) return array; 
/* 3389 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 3390 */     short[][] base = Arrays.<short[]>copyOf(array, baseLength);
/* 3391 */     int residual = (int)(length & 0x7FFFFFFL);
/* 3392 */     if (residual != 0) base[baseLength - 1] = ShortArrays.trim(base[baseLength - 1], residual); 
/* 3393 */     return base;
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
/*      */   public static short[][] setLength(short[][] array, long length) {
/* 3412 */     long oldLength = length(array);
/* 3413 */     if (length == oldLength) return array; 
/* 3414 */     if (length < oldLength) return trim(array, length); 
/* 3415 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[][] copy(short[][] array, long offset, long length) {
/* 3425 */     ensureOffsetLength(array, offset, length);
/*      */     
/* 3427 */     short[][] a = ShortBigArrays.newBigArray(length);
/* 3428 */     copy(array, offset, a, 0L, length);
/* 3429 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[][] copy(short[][] array) {
/* 3437 */     short[][] base = (short[][])array.clone();
/* 3438 */     for (int i = base.length; i-- != 0; base[i] = (short[])array[i].clone());
/* 3439 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(short[][] array, short value) {
/* 3450 */     for (int i = array.length; i-- != 0; Arrays.fill(array[i], value));
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
/*      */   public static void fill(short[][] array, long from, long to, short value) {
/* 3464 */     long length = length(array);
/* 3465 */     ensureFromTo(length, from, to);
/* 3466 */     if (length == 0L)
/* 3467 */       return;  int fromSegment = segment(from);
/* 3468 */     int toSegment = segment(to);
/* 3469 */     int fromDispl = displacement(from);
/* 3470 */     int toDispl = displacement(to);
/* 3471 */     if (fromSegment == toSegment) {
/* 3472 */       Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
/*      */       return;
/*      */     } 
/* 3475 */     if (toDispl != 0) Arrays.fill(array[toSegment], 0, toDispl, value); 
/* 3476 */     for (; --toSegment > fromSegment; Arrays.fill(array[toSegment], value));
/* 3477 */     Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
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
/*      */   public static boolean equals(short[][] a1, short[][] a2) {
/* 3489 */     if (length(a1) != length(a2)) return false; 
/* 3490 */     int i = a1.length;
/*      */     
/* 3492 */     while (i-- != 0) {
/* 3493 */       short[] t = a1[i];
/* 3494 */       short[] u = a2[i];
/* 3495 */       int j = t.length;
/* 3496 */       while (j-- != 0) { if (t[j] != u[j]) return false;  }
/*      */     
/* 3498 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(short[][] a) {
/* 3507 */     if (a == null) return "null"; 
/* 3508 */     long last = length(a) - 1L;
/* 3509 */     if (last == -1L) return "[]"; 
/* 3510 */     StringBuilder b = new StringBuilder();
/* 3511 */     b.append('['); long i;
/* 3512 */     for (i = 0L;; i++) {
/* 3513 */       b.append(String.valueOf(get(a, i)));
/* 3514 */       if (i == last) return b.append(']').toString(); 
/* 3515 */       b.append(", ");
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
/*      */   public static void ensureFromTo(short[][] a, long from, long to) {
/* 3529 */     ensureFromTo(length(a), from, to);
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
/*      */   public static void ensureOffsetLength(short[][] a, long offset, long length) {
/* 3542 */     ensureOffsetLength(length(a), offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureSameLength(short[][] a, short[][] b) {
/* 3551 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char get(char[][] array, long index) {
/* 3598 */     return array[segment(index)][displacement(index)];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void set(char[][] array, long index, char value) {
/* 3607 */     array[segment(index)][displacement(index)] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(char[][] array, long first, long second) {
/* 3616 */     char t = array[segment(first)][displacement(first)];
/* 3617 */     array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
/* 3618 */     array[segment(second)][displacement(second)] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void add(char[][] array, long index, char incr) {
/* 3627 */     array[segment(index)][displacement(index)] = (char)(array[segment(index)][displacement(index)] + incr);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mul(char[][] array, long index, char factor) {
/* 3636 */     array[segment(index)][displacement(index)] = (char)(array[segment(index)][displacement(index)] * factor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void incr(char[][] array, long index) {
/* 3644 */     array[segment(index)][displacement(index)] = (char)(array[segment(index)][displacement(index)] + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void decr(char[][] array, long index) {
/* 3652 */     array[segment(index)][displacement(index)] = (char)(array[segment(index)][displacement(index)] - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long length(char[][] array) {
/* 3660 */     int length = array.length;
/* 3661 */     return (length == 0) ? 0L : (start(length - 1) + (array[length - 1]).length);
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
/*      */   public static void copy(char[][] srcArray, long srcPos, char[][] destArray, long destPos, long length) {
/* 3673 */     if (destPos <= srcPos) {
/* 3674 */       int srcSegment = segment(srcPos);
/* 3675 */       int destSegment = segment(destPos);
/* 3676 */       int srcDispl = displacement(srcPos);
/* 3677 */       int destDispl = displacement(destPos);
/*      */       
/* 3679 */       while (length > 0L) {
/* 3680 */         int l = (int)Math.min(length, Math.min((srcArray[srcSegment]).length - srcDispl, (destArray[destSegment]).length - destDispl));
/* 3681 */         System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
/* 3682 */         if ((srcDispl += l) == 134217728) {
/* 3683 */           srcDispl = 0;
/* 3684 */           srcSegment++;
/*      */         } 
/* 3686 */         if ((destDispl += l) == 134217728) {
/* 3687 */           destDispl = 0;
/* 3688 */           destSegment++;
/*      */         } 
/* 3690 */         length -= l;
/*      */       } 
/*      */     } else {
/*      */       
/* 3694 */       int srcSegment = segment(srcPos + length);
/* 3695 */       int destSegment = segment(destPos + length);
/* 3696 */       int srcDispl = displacement(srcPos + length);
/* 3697 */       int destDispl = displacement(destPos + length);
/*      */       
/* 3699 */       while (length > 0L) {
/* 3700 */         if (srcDispl == 0) {
/* 3701 */           srcDispl = 134217728;
/* 3702 */           srcSegment--;
/*      */         } 
/* 3704 */         if (destDispl == 0) {
/* 3705 */           destDispl = 134217728;
/* 3706 */           destSegment--;
/*      */         } 
/* 3708 */         int l = (int)Math.min(length, Math.min(srcDispl, destDispl));
/* 3709 */         System.arraycopy(srcArray[srcSegment], srcDispl - l, destArray[destSegment], destDispl - l, l);
/* 3710 */         srcDispl -= l;
/* 3711 */         destDispl -= l;
/* 3712 */         length -= l;
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
/*      */   public static void copyFromBig(char[][] srcArray, long srcPos, char[] destArray, int destPos, int length) {
/* 3725 */     int srcSegment = segment(srcPos);
/* 3726 */     int srcDispl = displacement(srcPos);
/*      */     
/* 3728 */     while (length > 0) {
/* 3729 */       int l = Math.min((srcArray[srcSegment]).length - srcDispl, length);
/* 3730 */       System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
/* 3731 */       if ((srcDispl += l) == 134217728) {
/* 3732 */         srcDispl = 0;
/* 3733 */         srcSegment++;
/*      */       } 
/* 3735 */       destPos += l;
/* 3736 */       length -= l;
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
/*      */   public static void copyToBig(char[] srcArray, int srcPos, char[][] destArray, long destPos, long length) {
/* 3748 */     int destSegment = segment(destPos);
/* 3749 */     int destDispl = displacement(destPos);
/*      */     
/* 3751 */     while (length > 0L) {
/* 3752 */       int l = (int)Math.min(((destArray[destSegment]).length - destDispl), length);
/* 3753 */       System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
/* 3754 */       if ((destDispl += l) == 134217728) {
/* 3755 */         destDispl = 0;
/* 3756 */         destSegment++;
/*      */       } 
/* 3758 */       srcPos += l;
/* 3759 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[][] wrap(char[] array) {
/* 3770 */     if (array.length == 0) return CharBigArrays.EMPTY_BIG_ARRAY; 
/* 3771 */     if (array.length <= 134217728) return new char[][] { array }; 
/* 3772 */     char[][] bigArray = CharBigArrays.newBigArray(array.length);
/* 3773 */     for (int i = 0; i < bigArray.length; ) { System.arraycopy(array, (int)start(i), bigArray[i], 0, (bigArray[i]).length); i++; }
/* 3774 */      return bigArray;
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
/*      */   public static char[][] ensureCapacity(char[][] array, long length) {
/* 3791 */     return ensureCapacity(array, length, length(array));
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
/*      */   public static char[][] forceCapacity(char[][] array, long length, long preserve) {
/* 3805 */     ensureLength(length);
/* 3806 */     int valid = array.length - ((array.length == 0 || (array.length > 0 && (array[array.length - 1]).length == 134217728)) ? 0 : 1);
/* 3807 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 3808 */     char[][] base = Arrays.<char[]>copyOf(array, baseLength);
/* 3809 */     int residual = (int)(length & 0x7FFFFFFL);
/* 3810 */     if (residual != 0) {
/* 3811 */       for (int i = valid; i < baseLength - 1; ) { base[i] = new char[134217728]; i++; }
/* 3812 */        base[baseLength - 1] = new char[residual];
/*      */     } else {
/* 3814 */       for (int i = valid; i < baseLength; ) { base[i] = new char[134217728]; i++; } 
/* 3815 */     }  if (preserve - valid * 134217728L > 0L) copy(array, valid * 134217728L, base, valid * 134217728L, preserve - valid * 134217728L); 
/* 3816 */     return base;
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
/*      */   public static char[][] ensureCapacity(char[][] array, long length, long preserve) {
/* 3831 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
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
/*      */   public static char[][] grow(char[][] array, long length) {
/* 3851 */     long oldLength = length(array);
/* 3852 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
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
/*      */   public static char[][] grow(char[][] array, long length, long preserve) {
/* 3873 */     long oldLength = length(array);
/* 3874 */     return (length > oldLength) ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : array;
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
/*      */   public static char[][] trim(char[][] array, long length) {
/* 3890 */     ensureLength(length);
/* 3891 */     long oldLength = length(array);
/* 3892 */     if (length >= oldLength) return array; 
/* 3893 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 3894 */     char[][] base = Arrays.<char[]>copyOf(array, baseLength);
/* 3895 */     int residual = (int)(length & 0x7FFFFFFL);
/* 3896 */     if (residual != 0) base[baseLength - 1] = CharArrays.trim(base[baseLength - 1], residual); 
/* 3897 */     return base;
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
/*      */   public static char[][] setLength(char[][] array, long length) {
/* 3916 */     long oldLength = length(array);
/* 3917 */     if (length == oldLength) return array; 
/* 3918 */     if (length < oldLength) return trim(array, length); 
/* 3919 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[][] copy(char[][] array, long offset, long length) {
/* 3929 */     ensureOffsetLength(array, offset, length);
/*      */     
/* 3931 */     char[][] a = CharBigArrays.newBigArray(length);
/* 3932 */     copy(array, offset, a, 0L, length);
/* 3933 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[][] copy(char[][] array) {
/* 3941 */     char[][] base = (char[][])array.clone();
/* 3942 */     for (int i = base.length; i-- != 0; base[i] = (char[])array[i].clone());
/* 3943 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(char[][] array, char value) {
/* 3954 */     for (int i = array.length; i-- != 0; Arrays.fill(array[i], value));
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
/*      */   public static void fill(char[][] array, long from, long to, char value) {
/* 3968 */     long length = length(array);
/* 3969 */     ensureFromTo(length, from, to);
/* 3970 */     if (length == 0L)
/* 3971 */       return;  int fromSegment = segment(from);
/* 3972 */     int toSegment = segment(to);
/* 3973 */     int fromDispl = displacement(from);
/* 3974 */     int toDispl = displacement(to);
/* 3975 */     if (fromSegment == toSegment) {
/* 3976 */       Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
/*      */       return;
/*      */     } 
/* 3979 */     if (toDispl != 0) Arrays.fill(array[toSegment], 0, toDispl, value); 
/* 3980 */     for (; --toSegment > fromSegment; Arrays.fill(array[toSegment], value));
/* 3981 */     Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
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
/*      */   public static boolean equals(char[][] a1, char[][] a2) {
/* 3993 */     if (length(a1) != length(a2)) return false; 
/* 3994 */     int i = a1.length;
/*      */     
/* 3996 */     while (i-- != 0) {
/* 3997 */       char[] t = a1[i];
/* 3998 */       char[] u = a2[i];
/* 3999 */       int j = t.length;
/* 4000 */       while (j-- != 0) { if (t[j] != u[j]) return false;  }
/*      */     
/* 4002 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(char[][] a) {
/* 4011 */     if (a == null) return "null"; 
/* 4012 */     long last = length(a) - 1L;
/* 4013 */     if (last == -1L) return "[]"; 
/* 4014 */     StringBuilder b = new StringBuilder();
/* 4015 */     b.append('['); long i;
/* 4016 */     for (i = 0L;; i++) {
/* 4017 */       b.append(String.valueOf(get(a, i)));
/* 4018 */       if (i == last) return b.append(']').toString(); 
/* 4019 */       b.append(", ");
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
/*      */   public static void ensureFromTo(char[][] a, long from, long to) {
/* 4033 */     ensureFromTo(length(a), from, to);
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
/*      */   public static void ensureOffsetLength(char[][] a, long offset, long length) {
/* 4046 */     ensureOffsetLength(length(a), offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureSameLength(char[][] a, char[][] b) {
/* 4055 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float get(float[][] array, long index) {
/* 4102 */     return array[segment(index)][displacement(index)];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void set(float[][] array, long index, float value) {
/* 4111 */     array[segment(index)][displacement(index)] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(float[][] array, long first, long second) {
/* 4120 */     float t = array[segment(first)][displacement(first)];
/* 4121 */     array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
/* 4122 */     array[segment(second)][displacement(second)] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void add(float[][] array, long index, float incr) {
/* 4131 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] + incr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mul(float[][] array, long index, float factor) {
/* 4140 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] * factor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void incr(float[][] array, long index) {
/* 4148 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] + 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void decr(float[][] array, long index) {
/* 4156 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] - 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long length(float[][] array) {
/* 4164 */     int length = array.length;
/* 4165 */     return (length == 0) ? 0L : (start(length - 1) + (array[length - 1]).length);
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
/*      */   public static void copy(float[][] srcArray, long srcPos, float[][] destArray, long destPos, long length) {
/* 4177 */     if (destPos <= srcPos) {
/* 4178 */       int srcSegment = segment(srcPos);
/* 4179 */       int destSegment = segment(destPos);
/* 4180 */       int srcDispl = displacement(srcPos);
/* 4181 */       int destDispl = displacement(destPos);
/*      */       
/* 4183 */       while (length > 0L) {
/* 4184 */         int l = (int)Math.min(length, Math.min((srcArray[srcSegment]).length - srcDispl, (destArray[destSegment]).length - destDispl));
/* 4185 */         System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
/* 4186 */         if ((srcDispl += l) == 134217728) {
/* 4187 */           srcDispl = 0;
/* 4188 */           srcSegment++;
/*      */         } 
/* 4190 */         if ((destDispl += l) == 134217728) {
/* 4191 */           destDispl = 0;
/* 4192 */           destSegment++;
/*      */         } 
/* 4194 */         length -= l;
/*      */       } 
/*      */     } else {
/*      */       
/* 4198 */       int srcSegment = segment(srcPos + length);
/* 4199 */       int destSegment = segment(destPos + length);
/* 4200 */       int srcDispl = displacement(srcPos + length);
/* 4201 */       int destDispl = displacement(destPos + length);
/*      */       
/* 4203 */       while (length > 0L) {
/* 4204 */         if (srcDispl == 0) {
/* 4205 */           srcDispl = 134217728;
/* 4206 */           srcSegment--;
/*      */         } 
/* 4208 */         if (destDispl == 0) {
/* 4209 */           destDispl = 134217728;
/* 4210 */           destSegment--;
/*      */         } 
/* 4212 */         int l = (int)Math.min(length, Math.min(srcDispl, destDispl));
/* 4213 */         System.arraycopy(srcArray[srcSegment], srcDispl - l, destArray[destSegment], destDispl - l, l);
/* 4214 */         srcDispl -= l;
/* 4215 */         destDispl -= l;
/* 4216 */         length -= l;
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
/*      */   public static void copyFromBig(float[][] srcArray, long srcPos, float[] destArray, int destPos, int length) {
/* 4229 */     int srcSegment = segment(srcPos);
/* 4230 */     int srcDispl = displacement(srcPos);
/*      */     
/* 4232 */     while (length > 0) {
/* 4233 */       int l = Math.min((srcArray[srcSegment]).length - srcDispl, length);
/* 4234 */       System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
/* 4235 */       if ((srcDispl += l) == 134217728) {
/* 4236 */         srcDispl = 0;
/* 4237 */         srcSegment++;
/*      */       } 
/* 4239 */       destPos += l;
/* 4240 */       length -= l;
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
/*      */   public static void copyToBig(float[] srcArray, int srcPos, float[][] destArray, long destPos, long length) {
/* 4252 */     int destSegment = segment(destPos);
/* 4253 */     int destDispl = displacement(destPos);
/*      */     
/* 4255 */     while (length > 0L) {
/* 4256 */       int l = (int)Math.min(((destArray[destSegment]).length - destDispl), length);
/* 4257 */       System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
/* 4258 */       if ((destDispl += l) == 134217728) {
/* 4259 */         destDispl = 0;
/* 4260 */         destSegment++;
/*      */       } 
/* 4262 */       srcPos += l;
/* 4263 */       length -= l;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] wrap(float[] array) {
/* 4274 */     if (array.length == 0) return FloatBigArrays.EMPTY_BIG_ARRAY; 
/* 4275 */     if (array.length <= 134217728) return new float[][] { array }; 
/* 4276 */     float[][] bigArray = FloatBigArrays.newBigArray(array.length);
/* 4277 */     for (int i = 0; i < bigArray.length; ) { System.arraycopy(array, (int)start(i), bigArray[i], 0, (bigArray[i]).length); i++; }
/* 4278 */      return bigArray;
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
/*      */   public static float[][] ensureCapacity(float[][] array, long length) {
/* 4295 */     return ensureCapacity(array, length, length(array));
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
/*      */   public static float[][] forceCapacity(float[][] array, long length, long preserve) {
/* 4309 */     ensureLength(length);
/* 4310 */     int valid = array.length - ((array.length == 0 || (array.length > 0 && (array[array.length - 1]).length == 134217728)) ? 0 : 1);
/* 4311 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 4312 */     float[][] base = Arrays.<float[]>copyOf(array, baseLength);
/* 4313 */     int residual = (int)(length & 0x7FFFFFFL);
/* 4314 */     if (residual != 0) {
/* 4315 */       for (int i = valid; i < baseLength - 1; ) { base[i] = new float[134217728]; i++; }
/* 4316 */        base[baseLength - 1] = new float[residual];
/*      */     } else {
/* 4318 */       for (int i = valid; i < baseLength; ) { base[i] = new float[134217728]; i++; } 
/* 4319 */     }  if (preserve - valid * 134217728L > 0L) copy(array, valid * 134217728L, base, valid * 134217728L, preserve - valid * 134217728L); 
/* 4320 */     return base;
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
/*      */   public static float[][] ensureCapacity(float[][] array, long length, long preserve) {
/* 4335 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
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
/*      */   public static float[][] grow(float[][] array, long length) {
/* 4355 */     long oldLength = length(array);
/* 4356 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
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
/*      */   public static float[][] grow(float[][] array, long length, long preserve) {
/* 4377 */     long oldLength = length(array);
/* 4378 */     return (length > oldLength) ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : array;
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
/*      */   public static float[][] trim(float[][] array, long length) {
/* 4394 */     ensureLength(length);
/* 4395 */     long oldLength = length(array);
/* 4396 */     if (length >= oldLength) return array; 
/* 4397 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 4398 */     float[][] base = Arrays.<float[]>copyOf(array, baseLength);
/* 4399 */     int residual = (int)(length & 0x7FFFFFFL);
/* 4400 */     if (residual != 0) base[baseLength - 1] = FloatArrays.trim(base[baseLength - 1], residual); 
/* 4401 */     return base;
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
/*      */   public static float[][] setLength(float[][] array, long length) {
/* 4420 */     long oldLength = length(array);
/* 4421 */     if (length == oldLength) return array; 
/* 4422 */     if (length < oldLength) return trim(array, length); 
/* 4423 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] copy(float[][] array, long offset, long length) {
/* 4433 */     ensureOffsetLength(array, offset, length);
/*      */     
/* 4435 */     float[][] a = FloatBigArrays.newBigArray(length);
/* 4436 */     copy(array, offset, a, 0L, length);
/* 4437 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] copy(float[][] array) {
/* 4445 */     float[][] base = (float[][])array.clone();
/* 4446 */     for (int i = base.length; i-- != 0; base[i] = (float[])array[i].clone());
/* 4447 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(float[][] array, float value) {
/* 4458 */     for (int i = array.length; i-- != 0; Arrays.fill(array[i], value));
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
/*      */   public static void fill(float[][] array, long from, long to, float value) {
/* 4472 */     long length = length(array);
/* 4473 */     ensureFromTo(length, from, to);
/* 4474 */     if (length == 0L)
/* 4475 */       return;  int fromSegment = segment(from);
/* 4476 */     int toSegment = segment(to);
/* 4477 */     int fromDispl = displacement(from);
/* 4478 */     int toDispl = displacement(to);
/* 4479 */     if (fromSegment == toSegment) {
/* 4480 */       Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
/*      */       return;
/*      */     } 
/* 4483 */     if (toDispl != 0) Arrays.fill(array[toSegment], 0, toDispl, value); 
/* 4484 */     for (; --toSegment > fromSegment; Arrays.fill(array[toSegment], value));
/* 4485 */     Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
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
/*      */   public static boolean equals(float[][] a1, float[][] a2) {
/* 4497 */     if (length(a1) != length(a2)) return false; 
/* 4498 */     int i = a1.length;
/*      */     
/* 4500 */     while (i-- != 0) {
/* 4501 */       float[] t = a1[i];
/* 4502 */       float[] u = a2[i];
/* 4503 */       int j = t.length;
/* 4504 */       while (j-- != 0) { if (Float.floatToIntBits(t[j]) != Float.floatToIntBits(u[j])) return false;  }
/*      */     
/* 4506 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(float[][] a) {
/* 4515 */     if (a == null) return "null"; 
/* 4516 */     long last = length(a) - 1L;
/* 4517 */     if (last == -1L) return "[]"; 
/* 4518 */     StringBuilder b = new StringBuilder();
/* 4519 */     b.append('['); long i;
/* 4520 */     for (i = 0L;; i++) {
/* 4521 */       b.append(String.valueOf(get(a, i)));
/* 4522 */       if (i == last) return b.append(']').toString(); 
/* 4523 */       b.append(", ");
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
/*      */   public static void ensureFromTo(float[][] a, long from, long to) {
/* 4537 */     ensureFromTo(length(a), from, to);
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
/*      */   public static void ensureOffsetLength(float[][] a, long offset, long length) {
/* 4550 */     ensureOffsetLength(length(a), offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureSameLength(float[][] a, float[][] b) {
/* 4559 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K get(K[][] array, long index) {
/* 4605 */     return array[segment(index)][displacement(index)];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void set(K[][] array, long index, K value) {
/* 4614 */     array[segment(index)][displacement(index)] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void swap(K[][] array, long first, long second) {
/* 4623 */     K t = array[segment(first)][displacement(first)];
/* 4624 */     array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
/* 4625 */     array[segment(second)][displacement(second)] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> long length(K[][] array) {
/* 4633 */     int length = array.length;
/* 4634 */     return (length == 0) ? 0L : (start(length - 1) + (array[length - 1]).length);
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
/*      */   public static <K> void copy(K[][] srcArray, long srcPos, K[][] destArray, long destPos, long length) {
/* 4646 */     if (destPos <= srcPos) {
/* 4647 */       int srcSegment = segment(srcPos);
/* 4648 */       int destSegment = segment(destPos);
/* 4649 */       int srcDispl = displacement(srcPos);
/* 4650 */       int destDispl = displacement(destPos);
/*      */       
/* 4652 */       while (length > 0L) {
/* 4653 */         int l = (int)Math.min(length, Math.min((srcArray[srcSegment]).length - srcDispl, (destArray[destSegment]).length - destDispl));
/* 4654 */         System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
/* 4655 */         if ((srcDispl += l) == 134217728) {
/* 4656 */           srcDispl = 0;
/* 4657 */           srcSegment++;
/*      */         } 
/* 4659 */         if ((destDispl += l) == 134217728) {
/* 4660 */           destDispl = 0;
/* 4661 */           destSegment++;
/*      */         } 
/* 4663 */         length -= l;
/*      */       } 
/*      */     } else {
/*      */       
/* 4667 */       int srcSegment = segment(srcPos + length);
/* 4668 */       int destSegment = segment(destPos + length);
/* 4669 */       int srcDispl = displacement(srcPos + length);
/* 4670 */       int destDispl = displacement(destPos + length);
/*      */       
/* 4672 */       while (length > 0L) {
/* 4673 */         if (srcDispl == 0) {
/* 4674 */           srcDispl = 134217728;
/* 4675 */           srcSegment--;
/*      */         } 
/* 4677 */         if (destDispl == 0) {
/* 4678 */           destDispl = 134217728;
/* 4679 */           destSegment--;
/*      */         } 
/* 4681 */         int l = (int)Math.min(length, Math.min(srcDispl, destDispl));
/* 4682 */         System.arraycopy(srcArray[srcSegment], srcDispl - l, destArray[destSegment], destDispl - l, l);
/* 4683 */         srcDispl -= l;
/* 4684 */         destDispl -= l;
/* 4685 */         length -= l;
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
/*      */   public static <K> void copyFromBig(K[][] srcArray, long srcPos, K[] destArray, int destPos, int length) {
/* 4698 */     int srcSegment = segment(srcPos);
/* 4699 */     int srcDispl = displacement(srcPos);
/*      */     
/* 4701 */     while (length > 0) {
/* 4702 */       int l = Math.min((srcArray[srcSegment]).length - srcDispl, length);
/* 4703 */       System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
/* 4704 */       if ((srcDispl += l) == 134217728) {
/* 4705 */         srcDispl = 0;
/* 4706 */         srcSegment++;
/*      */       } 
/* 4708 */       destPos += l;
/* 4709 */       length -= l;
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
/*      */   public static <K> void copyToBig(K[] srcArray, int srcPos, K[][] destArray, long destPos, long length) {
/* 4721 */     int destSegment = segment(destPos);
/* 4722 */     int destDispl = displacement(destPos);
/*      */     
/* 4724 */     while (length > 0L) {
/* 4725 */       int l = (int)Math.min(((destArray[destSegment]).length - destDispl), length);
/* 4726 */       System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
/* 4727 */       if ((destDispl += l) == 134217728) {
/* 4728 */         destDispl = 0;
/* 4729 */         destSegment++;
/*      */       } 
/* 4731 */       srcPos += l;
/* 4732 */       length -= l;
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
/*      */   public static <K> K[][] wrap(K[] array) {
/* 4744 */     if (array.length == 0 && array.getClass() == Object[].class) return (K[][])ObjectBigArrays.EMPTY_BIG_ARRAY; 
/* 4745 */     if (array.length <= 134217728) {
/* 4746 */       K[][] arrayOfK = (K[][])Array.newInstance(array.getClass(), 1);
/* 4747 */       arrayOfK[0] = array;
/* 4748 */       return arrayOfK;
/*      */     } 
/* 4750 */     K[][] bigArray = (K[][])ObjectBigArrays.newBigArray(array.getClass(), array.length);
/* 4751 */     for (int i = 0; i < bigArray.length; ) { System.arraycopy(array, (int)start(i), bigArray[i], 0, (bigArray[i]).length); i++; }
/* 4752 */      return bigArray;
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
/*      */   public static <K> K[][] ensureCapacity(K[][] array, long length) {
/* 4769 */     return ensureCapacity(array, length, length(array));
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
/*      */   public static <K> K[][] forceCapacity(K[][] array, long length, long preserve) {
/* 4787 */     ensureLength(length);
/* 4788 */     int valid = array.length - ((array.length == 0 || (array.length > 0 && (array[array.length - 1]).length == 134217728)) ? 0 : 1);
/* 4789 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 4790 */     K[][] base = (K[][])Arrays.<Object[]>copyOf((Object[][])array, baseLength);
/* 4791 */     Class<?> componentType = array.getClass().getComponentType();
/* 4792 */     int residual = (int)(length & 0x7FFFFFFL);
/* 4793 */     if (residual != 0) {
/* 4794 */       for (int i = valid; i < baseLength - 1; ) { base[i] = (K[])Array.newInstance(componentType.getComponentType(), 134217728); i++; }
/* 4795 */        base[baseLength - 1] = (K[])Array.newInstance(componentType.getComponentType(), residual);
/*      */     } else {
/* 4797 */       for (int i = valid; i < baseLength; ) { base[i] = (K[])Array.newInstance(componentType.getComponentType(), 134217728); i++; } 
/* 4798 */     }  if (preserve - valid * 134217728L > 0L) copy(array, valid * 134217728L, base, valid * 134217728L, preserve - valid * 134217728L); 
/* 4799 */     return base;
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
/*      */   public static <K> K[][] ensureCapacity(K[][] array, long length, long preserve) {
/* 4818 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
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
/*      */   public static <K> K[][] grow(K[][] array, long length) {
/* 4838 */     long oldLength = length(array);
/* 4839 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
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
/*      */   public static <K> K[][] grow(K[][] array, long length, long preserve) {
/* 4860 */     long oldLength = length(array);
/* 4861 */     return (length > oldLength) ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : array;
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
/*      */   public static <K> K[][] trim(K[][] array, long length) {
/* 4877 */     ensureLength(length);
/* 4878 */     long oldLength = length(array);
/* 4879 */     if (length >= oldLength) return array; 
/* 4880 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 4881 */     K[][] base = (K[][])Arrays.<Object[]>copyOf((Object[][])array, baseLength);
/* 4882 */     int residual = (int)(length & 0x7FFFFFFL);
/* 4883 */     if (residual != 0) base[baseLength - 1] = (K[])ObjectArrays.trim((Object[])base[baseLength - 1], residual); 
/* 4884 */     return base;
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
/*      */   public static <K> K[][] setLength(K[][] array, long length) {
/* 4903 */     long oldLength = length(array);
/* 4904 */     if (length == oldLength) return array; 
/* 4905 */     if (length < oldLength) return trim(array, length); 
/* 4906 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[][] copy(K[][] array, long offset, long length) {
/* 4916 */     ensureOffsetLength(array, offset, length);
/*      */     
/* 4918 */     K[][] a = (K[][])ObjectBigArrays.newBigArray((Object[][])array, length);
/* 4919 */     copy(array, offset, a, 0L, length);
/* 4920 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[][] copy(K[][] array) {
/* 4928 */     K[][] base = (K[][])array.clone();
/* 4929 */     for (int i = base.length; i-- != 0; base[i] = (K[])array[i].clone());
/* 4930 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void fill(K[][] array, K value) {
/* 4941 */     for (int i = array.length; i-- != 0; Arrays.fill((Object[])array[i], value));
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
/*      */   public static <K> void fill(K[][] array, long from, long to, K value) {
/* 4955 */     long length = length(array);
/* 4956 */     ensureFromTo(length, from, to);
/* 4957 */     if (length == 0L)
/* 4958 */       return;  int fromSegment = segment(from);
/* 4959 */     int toSegment = segment(to);
/* 4960 */     int fromDispl = displacement(from);
/* 4961 */     int toDispl = displacement(to);
/* 4962 */     if (fromSegment == toSegment) {
/* 4963 */       Arrays.fill((Object[])array[fromSegment], fromDispl, toDispl, value);
/*      */       return;
/*      */     } 
/* 4966 */     if (toDispl != 0) Arrays.fill((Object[])array[toSegment], 0, toDispl, value); 
/* 4967 */     for (; --toSegment > fromSegment; Arrays.fill((Object[])array[toSegment], value));
/* 4968 */     Arrays.fill((Object[])array[fromSegment], fromDispl, 134217728, value);
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
/*      */   public static <K> boolean equals(K[][] a1, K[][] a2) {
/* 4980 */     if (length(a1) != length(a2)) return false; 
/* 4981 */     int i = a1.length;
/*      */     
/* 4983 */     while (i-- != 0) {
/* 4984 */       K[] t = a1[i];
/* 4985 */       K[] u = a2[i];
/* 4986 */       int j = t.length;
/* 4987 */       while (j-- != 0) { if (!Objects.equals(t[j], u[j])) return false;  }
/*      */     
/* 4989 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> String toString(K[][] a) {
/* 4998 */     if (a == null) return "null"; 
/* 4999 */     long last = length(a) - 1L;
/* 5000 */     if (last == -1L) return "[]"; 
/* 5001 */     StringBuilder b = new StringBuilder();
/* 5002 */     b.append('['); long i;
/* 5003 */     for (i = 0L;; i++) {
/* 5004 */       b.append(String.valueOf(get(a, i)));
/* 5005 */       if (i == last) return b.append(']').toString(); 
/* 5006 */       b.append(", ");
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
/*      */   public static <K> void ensureFromTo(K[][] a, long from, long to) {
/* 5020 */     ensureFromTo(length(a), from, to);
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
/*      */   public static <K> void ensureOffsetLength(K[][] a, long offset, long length) {
/* 5033 */     ensureOffsetLength(length(a), offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void ensureSameLength(K[][] a, K[][] b) {
/* 5042 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b)); 
/*      */   }
/*      */   
/*      */   public static void main(String[] arg) {
/*      */     // Byte code:
/*      */     //   0: lconst_1
/*      */     //   1: aload_0
/*      */     //   2: iconst_0
/*      */     //   3: aaload
/*      */     //   4: invokestatic parseInt : (Ljava/lang/String;)I
/*      */     //   7: lshl
/*      */     //   8: invokestatic newBigArray : (J)[[I
/*      */     //   11: astore_1
/*      */     //   12: bipush #10
/*      */     //   14: istore #10
/*      */     //   16: iload #10
/*      */     //   18: iinc #10, -1
/*      */     //   21: ifeq -> 378
/*      */     //   24: invokestatic currentTimeMillis : ()J
/*      */     //   27: lneg
/*      */     //   28: lstore #8
/*      */     //   30: lconst_0
/*      */     //   31: lstore_2
/*      */     //   32: aload_1
/*      */     //   33: invokestatic length : ([[I)J
/*      */     //   36: lstore #11
/*      */     //   38: lload #11
/*      */     //   40: dup2
/*      */     //   41: lconst_1
/*      */     //   42: lsub
/*      */     //   43: lstore #11
/*      */     //   45: lconst_0
/*      */     //   46: lcmp
/*      */     //   47: ifeq -> 66
/*      */     //   50: lload_2
/*      */     //   51: lload #11
/*      */     //   53: aload_1
/*      */     //   54: lload #11
/*      */     //   56: invokestatic get : ([[IJ)I
/*      */     //   59: i2l
/*      */     //   60: lxor
/*      */     //   61: lxor
/*      */     //   62: lstore_2
/*      */     //   63: goto -> 38
/*      */     //   66: lload_2
/*      */     //   67: lconst_0
/*      */     //   68: lcmp
/*      */     //   69: ifne -> 78
/*      */     //   72: getstatic java/lang/System.err : Ljava/io/PrintStream;
/*      */     //   75: invokevirtual println : ()V
/*      */     //   78: getstatic java/lang/System.out : Ljava/io/PrintStream;
/*      */     //   81: new java/lang/StringBuilder
/*      */     //   84: dup
/*      */     //   85: invokespecial <init> : ()V
/*      */     //   88: ldc_w 'Single loop: '
/*      */     //   91: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   94: lload #8
/*      */     //   96: invokestatic currentTimeMillis : ()J
/*      */     //   99: ladd
/*      */     //   100: invokevirtual append : (J)Ljava/lang/StringBuilder;
/*      */     //   103: ldc_w 'ms'
/*      */     //   106: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   109: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   112: invokevirtual println : (Ljava/lang/String;)V
/*      */     //   115: invokestatic currentTimeMillis : ()J
/*      */     //   118: lneg
/*      */     //   119: lstore #8
/*      */     //   121: lconst_0
/*      */     //   122: lstore #4
/*      */     //   124: aload_1
/*      */     //   125: arraylength
/*      */     //   126: istore #11
/*      */     //   128: iload #11
/*      */     //   130: iinc #11, -1
/*      */     //   133: ifeq -> 180
/*      */     //   136: aload_1
/*      */     //   137: iload #11
/*      */     //   139: aaload
/*      */     //   140: astore #12
/*      */     //   142: aload #12
/*      */     //   144: arraylength
/*      */     //   145: istore #13
/*      */     //   147: iload #13
/*      */     //   149: iinc #13, -1
/*      */     //   152: ifeq -> 177
/*      */     //   155: lload #4
/*      */     //   157: aload #12
/*      */     //   159: iload #13
/*      */     //   161: iaload
/*      */     //   162: i2l
/*      */     //   163: iload #11
/*      */     //   165: iload #13
/*      */     //   167: invokestatic index : (II)J
/*      */     //   170: lxor
/*      */     //   171: lxor
/*      */     //   172: lstore #4
/*      */     //   174: goto -> 147
/*      */     //   177: goto -> 128
/*      */     //   180: lload #4
/*      */     //   182: lconst_0
/*      */     //   183: lcmp
/*      */     //   184: ifne -> 193
/*      */     //   187: getstatic java/lang/System.err : Ljava/io/PrintStream;
/*      */     //   190: invokevirtual println : ()V
/*      */     //   193: lload_2
/*      */     //   194: lload #4
/*      */     //   196: lcmp
/*      */     //   197: ifeq -> 208
/*      */     //   200: new java/lang/AssertionError
/*      */     //   203: dup
/*      */     //   204: invokespecial <init> : ()V
/*      */     //   207: athrow
/*      */     //   208: getstatic java/lang/System.out : Ljava/io/PrintStream;
/*      */     //   211: new java/lang/StringBuilder
/*      */     //   214: dup
/*      */     //   215: invokespecial <init> : ()V
/*      */     //   218: ldc_w 'Double loop: '
/*      */     //   221: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   224: lload #8
/*      */     //   226: invokestatic currentTimeMillis : ()J
/*      */     //   229: ladd
/*      */     //   230: invokevirtual append : (J)Ljava/lang/StringBuilder;
/*      */     //   233: ldc_w 'ms'
/*      */     //   236: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   239: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   242: invokevirtual println : (Ljava/lang/String;)V
/*      */     //   245: lconst_0
/*      */     //   246: lstore #6
/*      */     //   248: aload_1
/*      */     //   249: invokestatic length : ([[I)J
/*      */     //   252: lstore #11
/*      */     //   254: aload_1
/*      */     //   255: arraylength
/*      */     //   256: istore #13
/*      */     //   258: iload #13
/*      */     //   260: iinc #13, -1
/*      */     //   263: ifeq -> 310
/*      */     //   266: aload_1
/*      */     //   267: iload #13
/*      */     //   269: aaload
/*      */     //   270: astore #14
/*      */     //   272: aload #14
/*      */     //   274: arraylength
/*      */     //   275: istore #15
/*      */     //   277: iload #15
/*      */     //   279: iinc #15, -1
/*      */     //   282: ifeq -> 307
/*      */     //   285: lload #4
/*      */     //   287: aload #14
/*      */     //   289: iload #15
/*      */     //   291: iaload
/*      */     //   292: i2l
/*      */     //   293: lload #11
/*      */     //   295: lconst_1
/*      */     //   296: lsub
/*      */     //   297: dup2
/*      */     //   298: lstore #11
/*      */     //   300: lxor
/*      */     //   301: lxor
/*      */     //   302: lstore #4
/*      */     //   304: goto -> 277
/*      */     //   307: goto -> 258
/*      */     //   310: lload #6
/*      */     //   312: lconst_0
/*      */     //   313: lcmp
/*      */     //   314: ifne -> 323
/*      */     //   317: getstatic java/lang/System.err : Ljava/io/PrintStream;
/*      */     //   320: invokevirtual println : ()V
/*      */     //   323: lload_2
/*      */     //   324: lload #6
/*      */     //   326: lcmp
/*      */     //   327: ifeq -> 338
/*      */     //   330: new java/lang/AssertionError
/*      */     //   333: dup
/*      */     //   334: invokespecial <init> : ()V
/*      */     //   337: athrow
/*      */     //   338: getstatic java/lang/System.out : Ljava/io/PrintStream;
/*      */     //   341: new java/lang/StringBuilder
/*      */     //   344: dup
/*      */     //   345: invokespecial <init> : ()V
/*      */     //   348: ldc_w 'Double loop (with additional index): '
/*      */     //   351: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   354: lload #8
/*      */     //   356: invokestatic currentTimeMillis : ()J
/*      */     //   359: ladd
/*      */     //   360: invokevirtual append : (J)Ljava/lang/StringBuilder;
/*      */     //   363: ldc_w 'ms'
/*      */     //   366: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   369: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   372: invokevirtual println : (Ljava/lang/String;)V
/*      */     //   375: goto -> 16
/*      */     //   378: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #5045	-> 0
/*      */     //   #5047	-> 12
/*      */     //   #5048	-> 24
/*      */     //   #5049	-> 30
/*      */     //   #5050	-> 32
/*      */     //   #5051	-> 50
/*      */     //   #5052	-> 66
/*      */     //   #5053	-> 78
/*      */     //   #5054	-> 115
/*      */     //   #5055	-> 121
/*      */     //   #5056	-> 124
/*      */     //   #5057	-> 136
/*      */     //   #5058	-> 142
/*      */     //   #5059	-> 155
/*      */     //   #5060	-> 177
/*      */     //   #5061	-> 180
/*      */     //   #5062	-> 193
/*      */     //   #5063	-> 208
/*      */     //   #5064	-> 245
/*      */     //   #5065	-> 248
/*      */     //   #5066	-> 254
/*      */     //   #5067	-> 266
/*      */     //   #5068	-> 272
/*      */     //   #5069	-> 285
/*      */     //   #5070	-> 307
/*      */     //   #5071	-> 310
/*      */     //   #5072	-> 323
/*      */     //   #5073	-> 338
/*      */     //   #5074	-> 375
/*      */     //   #5075	-> 378
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   38	28	11	i	J
/*      */     //   147	30	13	d	I
/*      */     //   142	35	12	t	[I
/*      */     //   128	52	11	i	I
/*      */     //   277	30	15	d	I
/*      */     //   272	35	14	t	[I
/*      */     //   258	52	13	i	I
/*      */     //   254	121	11	j	J
/*      */     //   32	346	2	x	J
/*      */     //   124	254	4	y	J
/*      */     //   248	130	6	z	J
/*      */     //   30	348	8	start	J
/*      */     //   16	362	10	k	I
/*      */     //   0	379	0	arg	[Ljava/lang/String;
/*      */     //   12	367	1	a	[[I
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\BigArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */