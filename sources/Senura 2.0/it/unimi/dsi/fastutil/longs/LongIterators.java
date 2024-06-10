/*      */ package it.unimi.dsi.fastutil.longs;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.BigArrays;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteIterator;
/*      */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortIterator;
/*      */ import java.io.Serializable;
/*      */ import java.util.Iterator;
/*      */ import java.util.ListIterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
/*      */ import java.util.function.LongPredicate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class LongIterators
/*      */ {
/*      */   public static class EmptyIterator
/*      */     implements LongListIterator, Serializable, Cloneable
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
/*      */     public long nextLong() {
/*   55 */       throw new NoSuchElementException();
/*      */     }
/*      */     
/*      */     public long previousLong() {
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
/*   79 */       return LongIterators.EMPTY_ITERATOR;
/*      */     }
/*      */     private Object readResolve() {
/*   82 */       return LongIterators.EMPTY_ITERATOR;
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
/*      */   private static class SingletonIterator implements LongListIterator { private final long element;
/*      */     private int curr;
/*      */     
/*      */     public SingletonIterator(long element) {
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
/*      */     public long nextLong() {
/*  111 */       if (!hasNext())
/*  112 */         throw new NoSuchElementException(); 
/*  113 */       this.curr = 1;
/*  114 */       return this.element;
/*      */     }
/*      */     
/*      */     public long previousLong() {
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
/*      */   public static LongListIterator singleton(long element) {
/*  140 */     return new SingletonIterator(element);
/*      */   }
/*      */   private static class ArrayIterator implements LongListIterator { private final long[] array;
/*      */     private final int offset;
/*      */     private final int length;
/*      */     private int curr;
/*      */     
/*      */     public ArrayIterator(long[] array, int offset, int length) {
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
/*      */     public long nextLong() {
/*  162 */       if (!hasNext())
/*  163 */         throw new NoSuchElementException(); 
/*  164 */       return this.array[this.offset + this.curr++];
/*      */     }
/*      */     
/*      */     public long previousLong() {
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
/*      */   public static LongListIterator wrap(long[] array, int offset, int length) {
/*  219 */     LongArrays.ensureOffsetLength(array, offset, length);
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
/*      */   public static LongListIterator wrap(long[] array) {
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
/*      */   public static int unwrap(LongIterator i, long[] array, int offset, int max) {
/*  258 */     if (max < 0)
/*  259 */       throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  260 */     if (offset < 0 || offset + max > array.length)
/*  261 */       throw new IllegalArgumentException(); 
/*  262 */     int j = max;
/*  263 */     while (j-- != 0 && i.hasNext())
/*  264 */       array[offset++] = i.nextLong(); 
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
/*      */   public static int unwrap(LongIterator i, long[] array) {
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
/*      */   public static long[] unwrap(LongIterator i, int max) {
/*  302 */     if (max < 0)
/*  303 */       throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  304 */     long[] array = new long[16];
/*  305 */     int j = 0;
/*  306 */     while (max-- != 0 && i.hasNext()) {
/*  307 */       if (j == array.length)
/*  308 */         array = LongArrays.grow(array, j + 1); 
/*  309 */       array[j++] = i.nextLong();
/*      */     } 
/*  311 */     return LongArrays.trim(array, j);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[] unwrap(LongIterator i) {
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
/*      */   public static long unwrap(LongIterator i, long[][] array, long offset, long max) {
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
/*      */     //   51: invokestatic length : ([[J)J
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
/*      */     //   98: invokeinterface nextLong : ()J
/*      */     //   103: invokestatic set : ([[JJJ)V
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
/*      */     //   0	117	0	i	Lit/unimi/dsi/fastutil/longs/LongIterator;
/*      */     //   0	117	1	array	[[J
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
/*      */   public static long unwrap(LongIterator i, long[][] array) {
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
/*      */   public static int unwrap(LongIterator i, LongCollection c, int max) {
/*  398 */     if (max < 0)
/*  399 */       throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  400 */     int j = max;
/*  401 */     while (j-- != 0 && i.hasNext())
/*  402 */       c.add(i.nextLong()); 
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
/*      */   public static long[][] unwrapBig(LongIterator i, long max) {
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
/*      */     //   41: invokestatic newBigArray : (J)[[J
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
/*      */     //   70: invokestatic length : ([[J)J
/*      */     //   73: lcmp
/*      */     //   74: ifne -> 86
/*      */     //   77: aload_3
/*      */     //   78: lload #4
/*      */     //   80: lconst_1
/*      */     //   81: ladd
/*      */     //   82: invokestatic grow : ([[JJ)[[J
/*      */     //   85: astore_3
/*      */     //   86: aload_3
/*      */     //   87: lload #4
/*      */     //   89: dup2
/*      */     //   90: lconst_1
/*      */     //   91: ladd
/*      */     //   92: lstore #4
/*      */     //   94: aload_0
/*      */     //   95: invokeinterface nextLong : ()J
/*      */     //   100: invokestatic set : ([[JJJ)V
/*      */     //   103: goto -> 48
/*      */     //   106: aload_3
/*      */     //   107: lload #4
/*      */     //   109: invokestatic trim : ([[JJ)[[J
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
/*      */     //   0	113	0	i	Lit/unimi/dsi/fastutil/longs/LongIterator;
/*      */     //   0	113	1	max	J
/*      */     //   45	68	3	array	[[J
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
/*      */   public static long[][] unwrapBig(LongIterator i) {
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
/*      */   public static long unwrap(LongIterator i, LongCollection c) {
/*  467 */     long n = 0L;
/*  468 */     while (i.hasNext()) {
/*  469 */       c.add(i.nextLong());
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
/*      */   public static int pour(LongIterator i, LongCollection s, int max) {
/*  494 */     if (max < 0)
/*  495 */       throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  496 */     int j = max;
/*  497 */     while (j-- != 0 && i.hasNext())
/*  498 */       s.add(i.nextLong()); 
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
/*      */   public static int pour(LongIterator i, LongCollection s) {
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
/*      */   public static LongList pour(LongIterator i, int max) {
/*  539 */     LongArrayList l = new LongArrayList();
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
/*      */   public static LongList pour(LongIterator i) {
/*  558 */     return pour(i, 2147483647);
/*      */   }
/*      */   private static class IteratorWrapper implements LongIterator { final Iterator<Long> i;
/*      */     
/*      */     public IteratorWrapper(Iterator<Long> i) {
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
/*      */     public long nextLong() {
/*  575 */       return ((Long)this.i.next()).longValue();
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
/*      */   public static LongIterator asLongIterator(Iterator<Long> i) {
/*  598 */     if (i instanceof LongIterator)
/*  599 */       return (LongIterator)i; 
/*  600 */     return new IteratorWrapper(i);
/*      */   }
/*      */   private static class ListIteratorWrapper implements LongListIterator { final ListIterator<Long> i;
/*      */     
/*      */     public ListIteratorWrapper(ListIterator<Long> i) {
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
/*      */     public void set(long k) {
/*  625 */       this.i.set(Long.valueOf(k));
/*      */     }
/*      */     
/*      */     public void add(long k) {
/*  629 */       this.i.add(Long.valueOf(k));
/*      */     }
/*      */     
/*      */     public void remove() {
/*  633 */       this.i.remove();
/*      */     }
/*      */     
/*      */     public long nextLong() {
/*  637 */       return ((Long)this.i.next()).longValue();
/*      */     }
/*      */     
/*      */     public long previousLong() {
/*  641 */       return ((Long)this.i.previous()).longValue();
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
/*      */   public static LongListIterator asLongIterator(ListIterator<Long> i) {
/*  664 */     if (i instanceof LongListIterator)
/*  665 */       return (LongListIterator)i; 
/*  666 */     return new ListIteratorWrapper(i);
/*      */   }
/*      */   public static boolean any(LongIterator iterator, LongPredicate predicate) {
/*  669 */     return (indexOf(iterator, predicate) != -1);
/*      */   }
/*      */   public static boolean all(LongIterator iterator, LongPredicate predicate) {
/*  672 */     Objects.requireNonNull(predicate);
/*      */     while (true) {
/*  674 */       if (!iterator.hasNext())
/*  675 */         return true; 
/*  676 */       if (!predicate.test(iterator.nextLong()))
/*  677 */         return false; 
/*      */     } 
/*      */   } public static int indexOf(LongIterator iterator, LongPredicate predicate) {
/*  680 */     Objects.requireNonNull(predicate);
/*  681 */     for (int i = 0; iterator.hasNext(); i++) {
/*  682 */       if (predicate.test(iterator.nextLong()))
/*  683 */         return i; 
/*      */     } 
/*  685 */     return -1;
/*      */   }
/*      */   
/*      */   private static class IntervalIterator implements LongBidirectionalIterator { private final long from;
/*      */     
/*      */     public IntervalIterator(long from, long to) {
/*  691 */       this.from = this.curr = from;
/*  692 */       this.to = to;
/*      */     }
/*      */     private final long to; long curr;
/*      */     public boolean hasNext() {
/*  696 */       return (this.curr < this.to);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  700 */       return (this.curr > this.from);
/*      */     }
/*      */     
/*      */     public long nextLong() {
/*  704 */       if (!hasNext())
/*  705 */         throw new NoSuchElementException(); 
/*  706 */       return this.curr++;
/*      */     }
/*      */     
/*      */     public long previousLong() {
/*  710 */       if (!hasPrevious())
/*  711 */         throw new NoSuchElementException(); 
/*  712 */       return --this.curr;
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/*  716 */       if (this.curr + n <= this.to) {
/*  717 */         this.curr += n;
/*  718 */         return n;
/*      */       } 
/*  720 */       n = (int)(this.to - this.curr);
/*  721 */       this.curr = this.to;
/*  722 */       return n;
/*      */     }
/*      */     
/*      */     public int back(int n) {
/*  726 */       if (this.curr - n >= this.from) {
/*  727 */         this.curr -= n;
/*  728 */         return n;
/*      */       } 
/*  730 */       n = (int)(this.curr - this.from);
/*  731 */       this.curr = this.from;
/*  732 */       return n;
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
/*      */ 
/*      */   
/*      */   public static LongBidirectionalIterator fromTo(long from, long to) {
/*  757 */     return new IntervalIterator(from, to);
/*      */   }
/*      */   private static class IteratorConcatenator implements LongIterator { final LongIterator[] a; int offset;
/*      */     int length;
/*  761 */     int lastOffset = -1;
/*      */     public IteratorConcatenator(LongIterator[] a, int offset, int length) {
/*  763 */       this.a = a;
/*  764 */       this.offset = offset;
/*  765 */       this.length = length;
/*  766 */       advance();
/*      */     }
/*      */     private void advance() {
/*  769 */       while (this.length != 0 && 
/*  770 */         !this.a[this.offset].hasNext()) {
/*      */         
/*  772 */         this.length--;
/*  773 */         this.offset++;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  779 */       return (this.length > 0);
/*      */     }
/*      */     
/*      */     public long nextLong() {
/*  783 */       if (!hasNext())
/*  784 */         throw new NoSuchElementException(); 
/*  785 */       long next = this.a[this.lastOffset = this.offset].nextLong();
/*  786 */       advance();
/*  787 */       return next;
/*      */     }
/*      */     
/*      */     public void remove() {
/*  791 */       if (this.lastOffset == -1)
/*  792 */         throw new IllegalStateException(); 
/*  793 */       this.a[this.lastOffset].remove();
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/*  797 */       this.lastOffset = -1;
/*  798 */       int skipped = 0;
/*  799 */       while (skipped < n && this.length != 0) {
/*  800 */         skipped += this.a[this.offset].skip(n - skipped);
/*  801 */         if (this.a[this.offset].hasNext())
/*      */           break; 
/*  803 */         this.length--;
/*  804 */         this.offset++;
/*      */       } 
/*  806 */       return skipped;
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
/*      */   public static LongIterator concat(LongIterator[] a) {
/*  821 */     return concat(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongIterator concat(LongIterator[] a, int offset, int length) {
/*  841 */     return new IteratorConcatenator(a, offset, length);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableIterator implements LongIterator { protected final LongIterator i;
/*      */     
/*      */     public UnmodifiableIterator(LongIterator i) {
/*  847 */       this.i = i;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  851 */       return this.i.hasNext();
/*      */     }
/*      */     
/*      */     public long nextLong() {
/*  855 */       return this.i.nextLong();
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongIterator unmodifiable(LongIterator i) {
/*  866 */     return new UnmodifiableIterator(i);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableBidirectionalIterator implements LongBidirectionalIterator { protected final LongBidirectionalIterator i;
/*      */     
/*      */     public UnmodifiableBidirectionalIterator(LongBidirectionalIterator i) {
/*  872 */       this.i = i;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  876 */       return this.i.hasNext();
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  880 */       return this.i.hasPrevious();
/*      */     }
/*      */     
/*      */     public long nextLong() {
/*  884 */       return this.i.nextLong();
/*      */     }
/*      */     
/*      */     public long previousLong() {
/*  888 */       return this.i.previousLong();
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
/*      */   public static LongBidirectionalIterator unmodifiable(LongBidirectionalIterator i) {
/*  901 */     return new UnmodifiableBidirectionalIterator(i);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableListIterator implements LongListIterator { protected final LongListIterator i;
/*      */     
/*      */     public UnmodifiableListIterator(LongListIterator i) {
/*  907 */       this.i = i;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  911 */       return this.i.hasNext();
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  915 */       return this.i.hasPrevious();
/*      */     }
/*      */     
/*      */     public long nextLong() {
/*  919 */       return this.i.nextLong();
/*      */     }
/*      */     
/*      */     public long previousLong() {
/*  923 */       return this.i.previousLong();
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/*  927 */       return this.i.nextIndex();
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/*  931 */       return this.i.previousIndex();
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongListIterator unmodifiable(LongListIterator i) {
/*  942 */     return new UnmodifiableListIterator(i);
/*      */   }
/*      */   
/*      */   protected static class ByteIteratorWrapper implements LongIterator { final ByteIterator iterator;
/*      */     
/*      */     public ByteIteratorWrapper(ByteIterator iterator) {
/*  948 */       this.iterator = iterator;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  952 */       return this.iterator.hasNext();
/*      */     }
/*      */     
/*      */     @Deprecated
/*      */     public Long next() {
/*  957 */       return Long.valueOf(this.iterator.nextByte());
/*      */     }
/*      */     
/*      */     public long nextLong() {
/*  961 */       return this.iterator.nextByte();
/*      */     }
/*      */     
/*      */     public void remove() {
/*  965 */       this.iterator.remove();
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/*  969 */       return this.iterator.skip(n);
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongIterator wrap(ByteIterator iterator) {
/*  980 */     return new ByteIteratorWrapper(iterator);
/*      */   }
/*      */   
/*      */   protected static class ShortIteratorWrapper implements LongIterator { final ShortIterator iterator;
/*      */     
/*      */     public ShortIteratorWrapper(ShortIterator iterator) {
/*  986 */       this.iterator = iterator;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  990 */       return this.iterator.hasNext();
/*      */     }
/*      */     
/*      */     @Deprecated
/*      */     public Long next() {
/*  995 */       return Long.valueOf(this.iterator.nextShort());
/*      */     }
/*      */     
/*      */     public long nextLong() {
/*  999 */       return this.iterator.nextShort();
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1003 */       this.iterator.remove();
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/* 1007 */       return this.iterator.skip(n);
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongIterator wrap(ShortIterator iterator) {
/* 1018 */     return new ShortIteratorWrapper(iterator);
/*      */   }
/*      */   
/*      */   protected static class IntIteratorWrapper implements LongIterator { final IntIterator iterator;
/*      */     
/*      */     public IntIteratorWrapper(IntIterator iterator) {
/* 1024 */       this.iterator = iterator;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1028 */       return this.iterator.hasNext();
/*      */     }
/*      */     
/*      */     @Deprecated
/*      */     public Long next() {
/* 1033 */       return Long.valueOf(this.iterator.nextInt());
/*      */     }
/*      */     
/*      */     public long nextLong() {
/* 1037 */       return this.iterator.nextInt();
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1041 */       this.iterator.remove();
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/* 1045 */       return this.iterator.skip(n);
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongIterator wrap(IntIterator iterator) {
/* 1056 */     return new IntIteratorWrapper(iterator);
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\LongIterators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */