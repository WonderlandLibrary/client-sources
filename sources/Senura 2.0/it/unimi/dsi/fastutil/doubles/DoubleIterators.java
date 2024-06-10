/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.BigArrays;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteIterator;
/*      */ import it.unimi.dsi.fastutil.floats.FloatIterator;
/*      */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortIterator;
/*      */ import java.io.Serializable;
/*      */ import java.util.Iterator;
/*      */ import java.util.ListIterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
/*      */ import java.util.function.DoublePredicate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class DoubleIterators
/*      */ {
/*      */   public static class EmptyIterator
/*      */     implements DoubleListIterator, Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     public boolean hasNext() {
/*   47 */       return false;
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*   51 */       return false;
/*      */     }
/*      */     
/*      */     public double nextDouble() {
/*   55 */       throw new NoSuchElementException();
/*      */     }
/*      */     
/*      */     public double previousDouble() {
/*   59 */       throw new NoSuchElementException();
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/*   63 */       return 0;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/*   67 */       return -1;
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/*   71 */       return 0;
/*      */     }
/*      */     
/*      */     public int back(int n) {
/*   75 */       return 0;
/*      */     }
/*      */     
/*      */     public Object clone() {
/*   79 */       return DoubleIterators.EMPTY_ITERATOR;
/*      */     }
/*      */     private Object readResolve() {
/*   82 */       return DoubleIterators.EMPTY_ITERATOR;
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
/*   93 */   public static final EmptyIterator EMPTY_ITERATOR = new EmptyIterator();
/*      */   
/*      */   private static class SingletonIterator implements DoubleListIterator { private final double element;
/*      */     private int curr;
/*      */     
/*      */     public SingletonIterator(double element) {
/*   99 */       this.element = element;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  103 */       return (this.curr == 0);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  107 */       return (this.curr == 1);
/*      */     }
/*      */     
/*      */     public double nextDouble() {
/*  111 */       if (!hasNext())
/*  112 */         throw new NoSuchElementException(); 
/*  113 */       this.curr = 1;
/*  114 */       return this.element;
/*      */     }
/*      */     
/*      */     public double previousDouble() {
/*  118 */       if (!hasPrevious())
/*  119 */         throw new NoSuchElementException(); 
/*  120 */       this.curr = 0;
/*  121 */       return this.element;
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/*  125 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/*  129 */       return this.curr - 1;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleListIterator singleton(double element) {
/*  140 */     return new SingletonIterator(element);
/*      */   }
/*      */   private static class ArrayIterator implements DoubleListIterator { private final double[] array;
/*      */     private final int offset;
/*      */     private final int length;
/*      */     private int curr;
/*      */     
/*      */     public ArrayIterator(double[] array, int offset, int length) {
/*  148 */       this.array = array;
/*  149 */       this.offset = offset;
/*  150 */       this.length = length;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  154 */       return (this.curr < this.length);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  158 */       return (this.curr > 0);
/*      */     }
/*      */     
/*      */     public double nextDouble() {
/*  162 */       if (!hasNext())
/*  163 */         throw new NoSuchElementException(); 
/*  164 */       return this.array[this.offset + this.curr++];
/*      */     }
/*      */     
/*      */     public double previousDouble() {
/*  168 */       if (!hasPrevious())
/*  169 */         throw new NoSuchElementException(); 
/*  170 */       return this.array[this.offset + --this.curr];
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/*  174 */       if (n <= this.length - this.curr) {
/*  175 */         this.curr += n;
/*  176 */         return n;
/*      */       } 
/*  178 */       n = this.length - this.curr;
/*  179 */       this.curr = this.length;
/*  180 */       return n;
/*      */     }
/*      */     
/*      */     public int back(int n) {
/*  184 */       if (n <= this.curr) {
/*  185 */         this.curr -= n;
/*  186 */         return n;
/*      */       } 
/*  188 */       n = this.curr;
/*  189 */       this.curr = 0;
/*  190 */       return n;
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/*  194 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/*  198 */       return this.curr - 1;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleListIterator wrap(double[] array, int offset, int length) {
/*  219 */     DoubleArrays.ensureOffsetLength(array, offset, length);
/*  220 */     return new ArrayIterator(array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleListIterator wrap(double[] array) {
/*  234 */     return new ArrayIterator(array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int unwrap(DoubleIterator i, double[] array, int offset, int max) {
/*  258 */     if (max < 0)
/*  259 */       throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  260 */     if (offset < 0 || offset + max > array.length)
/*  261 */       throw new IllegalArgumentException(); 
/*  262 */     int j = max;
/*  263 */     while (j-- != 0 && i.hasNext())
/*  264 */       array[offset++] = i.nextDouble(); 
/*  265 */     return max - j - 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int unwrap(DoubleIterator i, double[] array) {
/*  282 */     return unwrap(i, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] unwrap(DoubleIterator i, int max) {
/*  302 */     if (max < 0)
/*  303 */       throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  304 */     double[] array = new double[16];
/*  305 */     int j = 0;
/*  306 */     while (max-- != 0 && i.hasNext()) {
/*  307 */       if (j == array.length)
/*  308 */         array = DoubleArrays.grow(array, j + 1); 
/*  309 */       array[j++] = i.nextDouble();
/*      */     } 
/*  311 */     return DoubleArrays.trim(array, j);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] unwrap(DoubleIterator i) {
/*  325 */     return unwrap(i, 2147483647);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long unwrap(DoubleIterator i, double[][] array, long offset, long max) {
/*      */     // Byte code:
/*      */     //   0: lload #4
/*      */     //   2: lconst_0
/*      */     //   3: lcmp
/*      */     //   4: ifge -> 40
/*      */     //   7: new java/lang/IllegalArgumentException
/*      */     //   10: dup
/*      */     //   11: new java/lang/StringBuilder
/*      */     //   14: dup
/*      */     //   15: invokespecial <init> : ()V
/*      */     //   18: ldc 'The maximum number of elements ('
/*      */     //   20: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   23: lload #4
/*      */     //   25: invokevirtual append : (J)Ljava/lang/StringBuilder;
/*      */     //   28: ldc ') is negative'
/*      */     //   30: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   33: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   36: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   39: athrow
/*      */     //   40: lload_2
/*      */     //   41: lconst_0
/*      */     //   42: lcmp
/*      */     //   43: iflt -> 58
/*      */     //   46: lload_2
/*      */     //   47: lload #4
/*      */     //   49: ladd
/*      */     //   50: aload_1
/*      */     //   51: invokestatic length : ([[D)J
/*      */     //   54: lcmp
/*      */     //   55: ifle -> 66
/*      */     //   58: new java/lang/IllegalArgumentException
/*      */     //   61: dup
/*      */     //   62: invokespecial <init> : ()V
/*      */     //   65: athrow
/*      */     //   66: lload #4
/*      */     //   68: lstore #6
/*      */     //   70: lload #6
/*      */     //   72: dup2
/*      */     //   73: lconst_1
/*      */     //   74: lsub
/*      */     //   75: lstore #6
/*      */     //   77: lconst_0
/*      */     //   78: lcmp
/*      */     //   79: ifeq -> 109
/*      */     //   82: aload_0
/*      */     //   83: invokeinterface hasNext : ()Z
/*      */     //   88: ifeq -> 109
/*      */     //   91: aload_1
/*      */     //   92: lload_2
/*      */     //   93: dup2
/*      */     //   94: lconst_1
/*      */     //   95: ladd
/*      */     //   96: lstore_2
/*      */     //   97: aload_0
/*      */     //   98: invokeinterface nextDouble : ()D
/*      */     //   103: invokestatic set : ([[DJD)V
/*      */     //   106: goto -> 70
/*      */     //   109: lload #4
/*      */     //   111: lload #6
/*      */     //   113: lsub
/*      */     //   114: lconst_1
/*      */     //   115: lsub
/*      */     //   116: lreturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #349	-> 0
/*      */     //   #350	-> 7
/*      */     //   #351	-> 40
/*      */     //   #352	-> 58
/*      */     //   #353	-> 66
/*      */     //   #354	-> 70
/*      */     //   #355	-> 91
/*      */     //   #356	-> 109
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   0	117	0	i	Lit/unimi/dsi/fastutil/doubles/DoubleIterator;
/*      */     //   0	117	1	array	[[D
/*      */     //   0	117	2	offset	J
/*      */     //   0	117	4	max	J
/*      */     //   70	47	6	j	J
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long unwrap(DoubleIterator i, double[][] array) {
/*  373 */     return unwrap(i, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int unwrap(DoubleIterator i, DoubleCollection c, int max) {
/*  398 */     if (max < 0)
/*  399 */       throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  400 */     int j = max;
/*  401 */     while (j-- != 0 && i.hasNext())
/*  402 */       c.add(i.nextDouble()); 
/*  403 */     return max - j - 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] unwrapBig(DoubleIterator i, long max) {
/*      */     // Byte code:
/*      */     //   0: lload_1
/*      */     //   1: lconst_0
/*      */     //   2: lcmp
/*      */     //   3: ifge -> 38
/*      */     //   6: new java/lang/IllegalArgumentException
/*      */     //   9: dup
/*      */     //   10: new java/lang/StringBuilder
/*      */     //   13: dup
/*      */     //   14: invokespecial <init> : ()V
/*      */     //   17: ldc 'The maximum number of elements ('
/*      */     //   19: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   22: lload_1
/*      */     //   23: invokevirtual append : (J)Ljava/lang/StringBuilder;
/*      */     //   26: ldc ') is negative'
/*      */     //   28: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   31: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   34: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   37: athrow
/*      */     //   38: ldc2_w 16
/*      */     //   41: invokestatic newBigArray : (J)[[D
/*      */     //   44: astore_3
/*      */     //   45: lconst_0
/*      */     //   46: lstore #4
/*      */     //   48: lload_1
/*      */     //   49: dup2
/*      */     //   50: lconst_1
/*      */     //   51: lsub
/*      */     //   52: lstore_1
/*      */     //   53: lconst_0
/*      */     //   54: lcmp
/*      */     //   55: ifeq -> 106
/*      */     //   58: aload_0
/*      */     //   59: invokeinterface hasNext : ()Z
/*      */     //   64: ifeq -> 106
/*      */     //   67: lload #4
/*      */     //   69: aload_3
/*      */     //   70: invokestatic length : ([[D)J
/*      */     //   73: lcmp
/*      */     //   74: ifne -> 86
/*      */     //   77: aload_3
/*      */     //   78: lload #4
/*      */     //   80: lconst_1
/*      */     //   81: ladd
/*      */     //   82: invokestatic grow : ([[DJ)[[D
/*      */     //   85: astore_3
/*      */     //   86: aload_3
/*      */     //   87: lload #4
/*      */     //   89: dup2
/*      */     //   90: lconst_1
/*      */     //   91: ladd
/*      */     //   92: lstore #4
/*      */     //   94: aload_0
/*      */     //   95: invokeinterface nextDouble : ()D
/*      */     //   100: invokestatic set : ([[DJD)V
/*      */     //   103: goto -> 48
/*      */     //   106: aload_3
/*      */     //   107: lload #4
/*      */     //   109: invokestatic trim : ([[DJ)[[D
/*      */     //   112: areturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #423	-> 0
/*      */     //   #424	-> 6
/*      */     //   #425	-> 38
/*      */     //   #426	-> 45
/*      */     //   #427	-> 48
/*      */     //   #428	-> 67
/*      */     //   #429	-> 77
/*      */     //   #430	-> 86
/*      */     //   #432	-> 106
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   0	113	0	i	Lit/unimi/dsi/fastutil/doubles/DoubleIterator;
/*      */     //   0	113	1	max	J
/*      */     //   45	68	3	array	[[D
/*      */     //   48	65	4	j	J
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] unwrapBig(DoubleIterator i) {
/*  446 */     return unwrapBig(i, Long.MAX_VALUE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long unwrap(DoubleIterator i, DoubleCollection c) {
/*  467 */     long n = 0L;
/*  468 */     while (i.hasNext()) {
/*  469 */       c.add(i.nextDouble());
/*  470 */       n++;
/*      */     } 
/*  472 */     return n;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int pour(DoubleIterator i, DoubleCollection s, int max) {
/*  494 */     if (max < 0)
/*  495 */       throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  496 */     int j = max;
/*  497 */     while (j-- != 0 && i.hasNext())
/*  498 */       s.add(i.nextDouble()); 
/*  499 */     return max - j - 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int pour(DoubleIterator i, DoubleCollection s) {
/*  518 */     return pour(i, s, 2147483647);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleList pour(DoubleIterator i, int max) {
/*  539 */     DoubleArrayList l = new DoubleArrayList();
/*  540 */     pour(i, l, max);
/*  541 */     l.trim();
/*  542 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleList pour(DoubleIterator i) {
/*  558 */     return pour(i, 2147483647);
/*      */   }
/*      */   private static class IteratorWrapper implements DoubleIterator { final Iterator<Double> i;
/*      */     
/*      */     public IteratorWrapper(Iterator<Double> i) {
/*  563 */       this.i = i;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  567 */       return this.i.hasNext();
/*      */     }
/*      */     
/*      */     public void remove() {
/*  571 */       this.i.remove();
/*      */     }
/*      */     
/*      */     public double nextDouble() {
/*  575 */       return ((Double)this.i.next()).doubleValue();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterator asDoubleIterator(Iterator<Double> i) {
/*  598 */     if (i instanceof DoubleIterator)
/*  599 */       return (DoubleIterator)i; 
/*  600 */     return new IteratorWrapper(i);
/*      */   }
/*      */   private static class ListIteratorWrapper implements DoubleListIterator { final ListIterator<Double> i;
/*      */     
/*      */     public ListIteratorWrapper(ListIterator<Double> i) {
/*  605 */       this.i = i;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  609 */       return this.i.hasNext();
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  613 */       return this.i.hasPrevious();
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/*  617 */       return this.i.nextIndex();
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/*  621 */       return this.i.previousIndex();
/*      */     }
/*      */     
/*      */     public void set(double k) {
/*  625 */       this.i.set(Double.valueOf(k));
/*      */     }
/*      */     
/*      */     public void add(double k) {
/*  629 */       this.i.add(Double.valueOf(k));
/*      */     }
/*      */     
/*      */     public void remove() {
/*  633 */       this.i.remove();
/*      */     }
/*      */     
/*      */     public double nextDouble() {
/*  637 */       return ((Double)this.i.next()).doubleValue();
/*      */     }
/*      */     
/*      */     public double previousDouble() {
/*  641 */       return ((Double)this.i.previous()).doubleValue();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleListIterator asDoubleIterator(ListIterator<Double> i) {
/*  664 */     if (i instanceof DoubleListIterator)
/*  665 */       return (DoubleListIterator)i; 
/*  666 */     return new ListIteratorWrapper(i);
/*      */   }
/*      */   public static boolean any(DoubleIterator iterator, DoublePredicate predicate) {
/*  669 */     return (indexOf(iterator, predicate) != -1);
/*      */   }
/*      */   public static boolean all(DoubleIterator iterator, DoublePredicate predicate) {
/*  672 */     Objects.requireNonNull(predicate);
/*      */     while (true) {
/*  674 */       if (!iterator.hasNext())
/*  675 */         return true; 
/*  676 */       if (!predicate.test(iterator.nextDouble()))
/*  677 */         return false; 
/*      */     } 
/*      */   } public static int indexOf(DoubleIterator iterator, DoublePredicate predicate) {
/*  680 */     Objects.requireNonNull(predicate);
/*  681 */     for (int i = 0; iterator.hasNext(); i++) {
/*  682 */       if (predicate.test(iterator.nextDouble()))
/*  683 */         return i; 
/*      */     } 
/*  685 */     return -1;
/*      */   }
/*      */   
/*      */   private static class IteratorConcatenator implements DoubleIterator { final DoubleIterator[] a;
/*  689 */     int lastOffset = -1; int offset; int length;
/*      */     public IteratorConcatenator(DoubleIterator[] a, int offset, int length) {
/*  691 */       this.a = a;
/*  692 */       this.offset = offset;
/*  693 */       this.length = length;
/*  694 */       advance();
/*      */     }
/*      */     private void advance() {
/*  697 */       while (this.length != 0 && 
/*  698 */         !this.a[this.offset].hasNext()) {
/*      */         
/*  700 */         this.length--;
/*  701 */         this.offset++;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  707 */       return (this.length > 0);
/*      */     }
/*      */     
/*      */     public double nextDouble() {
/*  711 */       if (!hasNext())
/*  712 */         throw new NoSuchElementException(); 
/*  713 */       double next = this.a[this.lastOffset = this.offset].nextDouble();
/*  714 */       advance();
/*  715 */       return next;
/*      */     }
/*      */     
/*      */     public void remove() {
/*  719 */       if (this.lastOffset == -1)
/*  720 */         throw new IllegalStateException(); 
/*  721 */       this.a[this.lastOffset].remove();
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/*  725 */       this.lastOffset = -1;
/*  726 */       int skipped = 0;
/*  727 */       while (skipped < n && this.length != 0) {
/*  728 */         skipped += this.a[this.offset].skip(n - skipped);
/*  729 */         if (this.a[this.offset].hasNext())
/*      */           break; 
/*  731 */         this.length--;
/*  732 */         this.offset++;
/*      */       } 
/*  734 */       return skipped;
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
/*      */ 
/*      */   
/*      */   public static DoubleIterator concat(DoubleIterator[] a) {
/*  749 */     return concat(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterator concat(DoubleIterator[] a, int offset, int length) {
/*  769 */     return new IteratorConcatenator(a, offset, length);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableIterator implements DoubleIterator { protected final DoubleIterator i;
/*      */     
/*      */     public UnmodifiableIterator(DoubleIterator i) {
/*  775 */       this.i = i;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  779 */       return this.i.hasNext();
/*      */     }
/*      */     
/*      */     public double nextDouble() {
/*  783 */       return this.i.nextDouble();
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterator unmodifiable(DoubleIterator i) {
/*  794 */     return new UnmodifiableIterator(i);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableBidirectionalIterator implements DoubleBidirectionalIterator { protected final DoubleBidirectionalIterator i;
/*      */     
/*      */     public UnmodifiableBidirectionalIterator(DoubleBidirectionalIterator i) {
/*  800 */       this.i = i;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  804 */       return this.i.hasNext();
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  808 */       return this.i.hasPrevious();
/*      */     }
/*      */     
/*      */     public double nextDouble() {
/*  812 */       return this.i.nextDouble();
/*      */     }
/*      */     
/*      */     public double previousDouble() {
/*  816 */       return this.i.previousDouble();
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
/*      */   public static DoubleBidirectionalIterator unmodifiable(DoubleBidirectionalIterator i) {
/*  829 */     return new UnmodifiableBidirectionalIterator(i);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableListIterator implements DoubleListIterator { protected final DoubleListIterator i;
/*      */     
/*      */     public UnmodifiableListIterator(DoubleListIterator i) {
/*  835 */       this.i = i;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  839 */       return this.i.hasNext();
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  843 */       return this.i.hasPrevious();
/*      */     }
/*      */     
/*      */     public double nextDouble() {
/*  847 */       return this.i.nextDouble();
/*      */     }
/*      */     
/*      */     public double previousDouble() {
/*  851 */       return this.i.previousDouble();
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/*  855 */       return this.i.nextIndex();
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/*  859 */       return this.i.previousIndex();
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleListIterator unmodifiable(DoubleListIterator i) {
/*  870 */     return new UnmodifiableListIterator(i);
/*      */   }
/*      */   
/*      */   protected static class ByteIteratorWrapper implements DoubleIterator { final ByteIterator iterator;
/*      */     
/*      */     public ByteIteratorWrapper(ByteIterator iterator) {
/*  876 */       this.iterator = iterator;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  880 */       return this.iterator.hasNext();
/*      */     }
/*      */     
/*      */     @Deprecated
/*      */     public Double next() {
/*  885 */       return Double.valueOf(this.iterator.nextByte());
/*      */     }
/*      */     
/*      */     public double nextDouble() {
/*  889 */       return this.iterator.nextByte();
/*      */     }
/*      */     
/*      */     public void remove() {
/*  893 */       this.iterator.remove();
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/*  897 */       return this.iterator.skip(n);
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterator wrap(ByteIterator iterator) {
/*  908 */     return new ByteIteratorWrapper(iterator);
/*      */   }
/*      */   
/*      */   protected static class ShortIteratorWrapper implements DoubleIterator { final ShortIterator iterator;
/*      */     
/*      */     public ShortIteratorWrapper(ShortIterator iterator) {
/*  914 */       this.iterator = iterator;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  918 */       return this.iterator.hasNext();
/*      */     }
/*      */     
/*      */     @Deprecated
/*      */     public Double next() {
/*  923 */       return Double.valueOf(this.iterator.nextShort());
/*      */     }
/*      */     
/*      */     public double nextDouble() {
/*  927 */       return this.iterator.nextShort();
/*      */     }
/*      */     
/*      */     public void remove() {
/*  931 */       this.iterator.remove();
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/*  935 */       return this.iterator.skip(n);
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterator wrap(ShortIterator iterator) {
/*  946 */     return new ShortIteratorWrapper(iterator);
/*      */   }
/*      */   
/*      */   protected static class IntIteratorWrapper implements DoubleIterator { final IntIterator iterator;
/*      */     
/*      */     public IntIteratorWrapper(IntIterator iterator) {
/*  952 */       this.iterator = iterator;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  956 */       return this.iterator.hasNext();
/*      */     }
/*      */     
/*      */     @Deprecated
/*      */     public Double next() {
/*  961 */       return Double.valueOf(this.iterator.nextInt());
/*      */     }
/*      */     
/*      */     public double nextDouble() {
/*  965 */       return this.iterator.nextInt();
/*      */     }
/*      */     
/*      */     public void remove() {
/*  969 */       this.iterator.remove();
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/*  973 */       return this.iterator.skip(n);
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterator wrap(IntIterator iterator) {
/*  984 */     return new IntIteratorWrapper(iterator);
/*      */   }
/*      */   
/*      */   protected static class FloatIteratorWrapper implements DoubleIterator { final FloatIterator iterator;
/*      */     
/*      */     public FloatIteratorWrapper(FloatIterator iterator) {
/*  990 */       this.iterator = iterator;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  994 */       return this.iterator.hasNext();
/*      */     }
/*      */     
/*      */     @Deprecated
/*      */     public Double next() {
/*  999 */       return Double.valueOf(this.iterator.nextFloat());
/*      */     }
/*      */     
/*      */     public double nextDouble() {
/* 1003 */       return this.iterator.nextFloat();
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1007 */       this.iterator.remove();
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/* 1011 */       return this.iterator.skip(n);
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterator wrap(FloatIterator iterator) {
/* 1022 */     return new FloatIteratorWrapper(iterator);
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\DoubleIterators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */