/*      */ package it.unimi.dsi.fastutil.ints;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.BigArrays;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteIterator;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortIterator;
/*      */ import java.io.Serializable;
/*      */ import java.util.Iterator;
/*      */ import java.util.ListIterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
/*      */ import java.util.function.IntPredicate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class IntIterators
/*      */ {
/*      */   public static class EmptyIterator
/*      */     implements IntListIterator, Serializable, Cloneable
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
/*      */     public int nextInt() {
/*   55 */       throw new NoSuchElementException();
/*      */     }
/*      */     
/*      */     public int previousInt() {
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
/*   79 */       return IntIterators.EMPTY_ITERATOR;
/*      */     }
/*      */     private Object readResolve() {
/*   82 */       return IntIterators.EMPTY_ITERATOR;
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
/*      */   private static class SingletonIterator implements IntListIterator { private final int element;
/*      */     private int curr;
/*      */     
/*      */     public SingletonIterator(int element) {
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
/*      */     public int nextInt() {
/*  111 */       if (!hasNext())
/*  112 */         throw new NoSuchElementException(); 
/*  113 */       this.curr = 1;
/*  114 */       return this.element;
/*      */     }
/*      */     
/*      */     public int previousInt() {
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
/*      */   public static IntListIterator singleton(int element) {
/*  140 */     return new SingletonIterator(element);
/*      */   }
/*      */   private static class ArrayIterator implements IntListIterator { private final int[] array;
/*      */     private final int offset;
/*      */     private final int length;
/*      */     private int curr;
/*      */     
/*      */     public ArrayIterator(int[] array, int offset, int length) {
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
/*      */     public int nextInt() {
/*  162 */       if (!hasNext())
/*  163 */         throw new NoSuchElementException(); 
/*  164 */       return this.array[this.offset + this.curr++];
/*      */     }
/*      */     
/*      */     public int previousInt() {
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
/*      */   public static IntListIterator wrap(int[] array, int offset, int length) {
/*  219 */     IntArrays.ensureOffsetLength(array, offset, length);
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
/*      */   public static IntListIterator wrap(int[] array) {
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
/*      */   public static int unwrap(IntIterator i, int[] array, int offset, int max) {
/*  258 */     if (max < 0)
/*  259 */       throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  260 */     if (offset < 0 || offset + max > array.length)
/*  261 */       throw new IllegalArgumentException(); 
/*  262 */     int j = max;
/*  263 */     while (j-- != 0 && i.hasNext())
/*  264 */       array[offset++] = i.nextInt(); 
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
/*      */   public static int unwrap(IntIterator i, int[] array) {
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
/*      */   public static int[] unwrap(IntIterator i, int max) {
/*  302 */     if (max < 0)
/*  303 */       throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  304 */     int[] array = new int[16];
/*  305 */     int j = 0;
/*  306 */     while (max-- != 0 && i.hasNext()) {
/*  307 */       if (j == array.length)
/*  308 */         array = IntArrays.grow(array, j + 1); 
/*  309 */       array[j++] = i.nextInt();
/*      */     } 
/*  311 */     return IntArrays.trim(array, j);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] unwrap(IntIterator i) {
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
/*      */   public static long unwrap(IntIterator i, int[][] array, long offset, long max) {
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
/*      */     //   51: invokestatic length : ([[I)J
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
/*      */     //   98: invokeinterface nextInt : ()I
/*      */     //   103: invokestatic set : ([[IJI)V
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
/*      */     //   0	117	0	i	Lit/unimi/dsi/fastutil/ints/IntIterator;
/*      */     //   0	117	1	array	[[I
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
/*      */   public static long unwrap(IntIterator i, int[][] array) {
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
/*      */   public static int unwrap(IntIterator i, IntCollection c, int max) {
/*  398 */     if (max < 0)
/*  399 */       throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  400 */     int j = max;
/*  401 */     while (j-- != 0 && i.hasNext())
/*  402 */       c.add(i.nextInt()); 
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
/*      */   public static int[][] unwrapBig(IntIterator i, long max) {
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
/*      */     //   41: invokestatic newBigArray : (J)[[I
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
/*      */     //   70: invokestatic length : ([[I)J
/*      */     //   73: lcmp
/*      */     //   74: ifne -> 86
/*      */     //   77: aload_3
/*      */     //   78: lload #4
/*      */     //   80: lconst_1
/*      */     //   81: ladd
/*      */     //   82: invokestatic grow : ([[IJ)[[I
/*      */     //   85: astore_3
/*      */     //   86: aload_3
/*      */     //   87: lload #4
/*      */     //   89: dup2
/*      */     //   90: lconst_1
/*      */     //   91: ladd
/*      */     //   92: lstore #4
/*      */     //   94: aload_0
/*      */     //   95: invokeinterface nextInt : ()I
/*      */     //   100: invokestatic set : ([[IJI)V
/*      */     //   103: goto -> 48
/*      */     //   106: aload_3
/*      */     //   107: lload #4
/*      */     //   109: invokestatic trim : ([[IJ)[[I
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
/*      */     //   0	113	0	i	Lit/unimi/dsi/fastutil/ints/IntIterator;
/*      */     //   0	113	1	max	J
/*      */     //   45	68	3	array	[[I
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
/*      */   public static int[][] unwrapBig(IntIterator i) {
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
/*      */   public static long unwrap(IntIterator i, IntCollection c) {
/*  467 */     long n = 0L;
/*  468 */     while (i.hasNext()) {
/*  469 */       c.add(i.nextInt());
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
/*      */   public static int pour(IntIterator i, IntCollection s, int max) {
/*  494 */     if (max < 0)
/*  495 */       throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  496 */     int j = max;
/*  497 */     while (j-- != 0 && i.hasNext())
/*  498 */       s.add(i.nextInt()); 
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
/*      */   public static int pour(IntIterator i, IntCollection s) {
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
/*      */   public static IntList pour(IntIterator i, int max) {
/*  539 */     IntArrayList l = new IntArrayList();
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
/*      */   public static IntList pour(IntIterator i) {
/*  558 */     return pour(i, 2147483647);
/*      */   }
/*      */   private static class IteratorWrapper implements IntIterator { final Iterator<Integer> i;
/*      */     
/*      */     public IteratorWrapper(Iterator<Integer> i) {
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
/*      */     public int nextInt() {
/*  575 */       return ((Integer)this.i.next()).intValue();
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
/*      */   public static IntIterator asIntIterator(Iterator<Integer> i) {
/*  598 */     if (i instanceof IntIterator)
/*  599 */       return (IntIterator)i; 
/*  600 */     return new IteratorWrapper(i);
/*      */   }
/*      */   private static class ListIteratorWrapper implements IntListIterator { final ListIterator<Integer> i;
/*      */     
/*      */     public ListIteratorWrapper(ListIterator<Integer> i) {
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
/*      */     public void set(int k) {
/*  625 */       this.i.set(Integer.valueOf(k));
/*      */     }
/*      */     
/*      */     public void add(int k) {
/*  629 */       this.i.add(Integer.valueOf(k));
/*      */     }
/*      */     
/*      */     public void remove() {
/*  633 */       this.i.remove();
/*      */     }
/*      */     
/*      */     public int nextInt() {
/*  637 */       return ((Integer)this.i.next()).intValue();
/*      */     }
/*      */     
/*      */     public int previousInt() {
/*  641 */       return ((Integer)this.i.previous()).intValue();
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
/*      */   public static IntListIterator asIntIterator(ListIterator<Integer> i) {
/*  664 */     if (i instanceof IntListIterator)
/*  665 */       return (IntListIterator)i; 
/*  666 */     return new ListIteratorWrapper(i);
/*      */   }
/*      */   public static boolean any(IntIterator iterator, IntPredicate predicate) {
/*  669 */     return (indexOf(iterator, predicate) != -1);
/*      */   }
/*      */   public static boolean all(IntIterator iterator, IntPredicate predicate) {
/*  672 */     Objects.requireNonNull(predicate);
/*      */     while (true) {
/*  674 */       if (!iterator.hasNext())
/*  675 */         return true; 
/*  676 */       if (!predicate.test(iterator.nextInt()))
/*  677 */         return false; 
/*      */     } 
/*      */   } public static int indexOf(IntIterator iterator, IntPredicate predicate) {
/*  680 */     Objects.requireNonNull(predicate);
/*  681 */     for (int i = 0; iterator.hasNext(); i++) {
/*  682 */       if (predicate.test(iterator.nextInt()))
/*  683 */         return i; 
/*      */     } 
/*  685 */     return -1;
/*      */   }
/*      */   
/*      */   private static class IntervalIterator implements IntListIterator { private final int from;
/*      */     
/*      */     public IntervalIterator(int from, int to) {
/*  691 */       this.from = this.curr = from;
/*  692 */       this.to = to;
/*      */     }
/*      */     private final int to; int curr;
/*      */     public boolean hasNext() {
/*  696 */       return (this.curr < this.to);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  700 */       return (this.curr > this.from);
/*      */     }
/*      */     
/*      */     public int nextInt() {
/*  704 */       if (!hasNext())
/*  705 */         throw new NoSuchElementException(); 
/*  706 */       return this.curr++;
/*      */     }
/*      */     
/*      */     public int previousInt() {
/*  710 */       if (!hasPrevious())
/*  711 */         throw new NoSuchElementException(); 
/*  712 */       return --this.curr;
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/*  716 */       return this.curr - this.from;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/*  720 */       return this.curr - this.from - 1;
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/*  724 */       if (this.curr + n <= this.to) {
/*  725 */         this.curr += n;
/*  726 */         return n;
/*      */       } 
/*  728 */       n = this.to - this.curr;
/*  729 */       this.curr = this.to;
/*  730 */       return n;
/*      */     }
/*      */     
/*      */     public int back(int n) {
/*  734 */       if (this.curr - n >= this.from) {
/*  735 */         this.curr -= n;
/*  736 */         return n;
/*      */       } 
/*  738 */       n = this.curr - this.from;
/*  739 */       this.curr = this.from;
/*  740 */       return n;
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
/*      */   public static IntListIterator fromTo(int from, int to) {
/*  758 */     return new IntervalIterator(from, to);
/*      */   }
/*      */   private static class IteratorConcatenator implements IntIterator { final IntIterator[] a; int offset;
/*      */     int length;
/*  762 */     int lastOffset = -1;
/*      */     public IteratorConcatenator(IntIterator[] a, int offset, int length) {
/*  764 */       this.a = a;
/*  765 */       this.offset = offset;
/*  766 */       this.length = length;
/*  767 */       advance();
/*      */     }
/*      */     private void advance() {
/*  770 */       while (this.length != 0 && 
/*  771 */         !this.a[this.offset].hasNext()) {
/*      */         
/*  773 */         this.length--;
/*  774 */         this.offset++;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  780 */       return (this.length > 0);
/*      */     }
/*      */     
/*      */     public int nextInt() {
/*  784 */       if (!hasNext())
/*  785 */         throw new NoSuchElementException(); 
/*  786 */       int next = this.a[this.lastOffset = this.offset].nextInt();
/*  787 */       advance();
/*  788 */       return next;
/*      */     }
/*      */     
/*      */     public void remove() {
/*  792 */       if (this.lastOffset == -1)
/*  793 */         throw new IllegalStateException(); 
/*  794 */       this.a[this.lastOffset].remove();
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/*  798 */       this.lastOffset = -1;
/*  799 */       int skipped = 0;
/*  800 */       while (skipped < n && this.length != 0) {
/*  801 */         skipped += this.a[this.offset].skip(n - skipped);
/*  802 */         if (this.a[this.offset].hasNext())
/*      */           break; 
/*  804 */         this.length--;
/*  805 */         this.offset++;
/*      */       } 
/*  807 */       return skipped;
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
/*      */   public static IntIterator concat(IntIterator[] a) {
/*  822 */     return concat(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntIterator concat(IntIterator[] a, int offset, int length) {
/*  842 */     return new IteratorConcatenator(a, offset, length);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableIterator implements IntIterator { protected final IntIterator i;
/*      */     
/*      */     public UnmodifiableIterator(IntIterator i) {
/*  848 */       this.i = i;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  852 */       return this.i.hasNext();
/*      */     }
/*      */     
/*      */     public int nextInt() {
/*  856 */       return this.i.nextInt();
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntIterator unmodifiable(IntIterator i) {
/*  867 */     return new UnmodifiableIterator(i);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableBidirectionalIterator implements IntBidirectionalIterator { protected final IntBidirectionalIterator i;
/*      */     
/*      */     public UnmodifiableBidirectionalIterator(IntBidirectionalIterator i) {
/*  873 */       this.i = i;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  877 */       return this.i.hasNext();
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  881 */       return this.i.hasPrevious();
/*      */     }
/*      */     
/*      */     public int nextInt() {
/*  885 */       return this.i.nextInt();
/*      */     }
/*      */     
/*      */     public int previousInt() {
/*  889 */       return this.i.previousInt();
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
/*      */   public static IntBidirectionalIterator unmodifiable(IntBidirectionalIterator i) {
/*  902 */     return new UnmodifiableBidirectionalIterator(i);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableListIterator implements IntListIterator { protected final IntListIterator i;
/*      */     
/*      */     public UnmodifiableListIterator(IntListIterator i) {
/*  908 */       this.i = i;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  912 */       return this.i.hasNext();
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  916 */       return this.i.hasPrevious();
/*      */     }
/*      */     
/*      */     public int nextInt() {
/*  920 */       return this.i.nextInt();
/*      */     }
/*      */     
/*      */     public int previousInt() {
/*  924 */       return this.i.previousInt();
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/*  928 */       return this.i.nextIndex();
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/*  932 */       return this.i.previousIndex();
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntListIterator unmodifiable(IntListIterator i) {
/*  943 */     return new UnmodifiableListIterator(i);
/*      */   }
/*      */   
/*      */   protected static class ByteIteratorWrapper implements IntIterator { final ByteIterator iterator;
/*      */     
/*      */     public ByteIteratorWrapper(ByteIterator iterator) {
/*  949 */       this.iterator = iterator;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  953 */       return this.iterator.hasNext();
/*      */     }
/*      */     
/*      */     @Deprecated
/*      */     public Integer next() {
/*  958 */       return Integer.valueOf(this.iterator.nextByte());
/*      */     }
/*      */     
/*      */     public int nextInt() {
/*  962 */       return this.iterator.nextByte();
/*      */     }
/*      */     
/*      */     public void remove() {
/*  966 */       this.iterator.remove();
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/*  970 */       return this.iterator.skip(n);
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntIterator wrap(ByteIterator iterator) {
/*  981 */     return new ByteIteratorWrapper(iterator);
/*      */   }
/*      */   
/*      */   protected static class ShortIteratorWrapper implements IntIterator { final ShortIterator iterator;
/*      */     
/*      */     public ShortIteratorWrapper(ShortIterator iterator) {
/*  987 */       this.iterator = iterator;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  991 */       return this.iterator.hasNext();
/*      */     }
/*      */     
/*      */     @Deprecated
/*      */     public Integer next() {
/*  996 */       return Integer.valueOf(this.iterator.nextShort());
/*      */     }
/*      */     
/*      */     public int nextInt() {
/* 1000 */       return this.iterator.nextShort();
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1004 */       this.iterator.remove();
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/* 1008 */       return this.iterator.skip(n);
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntIterator wrap(ShortIterator iterator) {
/* 1019 */     return new ShortIteratorWrapper(iterator);
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\IntIterators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */