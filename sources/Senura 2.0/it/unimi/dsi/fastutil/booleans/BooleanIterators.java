/*     */ package it.unimi.dsi.fastutil.booleans;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.BigArrays;
/*     */ import java.io.Serializable;
/*     */ import java.util.Iterator;
/*     */ import java.util.ListIterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Predicate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BooleanIterators
/*     */ {
/*     */   public static class EmptyIterator
/*     */     implements BooleanListIterator, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public boolean hasNext() {
/*  47 */       return false;
/*     */     }
/*     */     
/*     */     public boolean hasPrevious() {
/*  51 */       return false;
/*     */     }
/*     */     
/*     */     public boolean nextBoolean() {
/*  55 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public boolean previousBoolean() {
/*  59 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public int nextIndex() {
/*  63 */       return 0;
/*     */     }
/*     */     
/*     */     public int previousIndex() {
/*  67 */       return -1;
/*     */     }
/*     */     
/*     */     public int skip(int n) {
/*  71 */       return 0;
/*     */     }
/*     */     
/*     */     public int back(int n) {
/*  75 */       return 0;
/*     */     }
/*     */     
/*     */     public Object clone() {
/*  79 */       return BooleanIterators.EMPTY_ITERATOR;
/*     */     }
/*     */     private Object readResolve() {
/*  82 */       return BooleanIterators.EMPTY_ITERATOR;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   public static final EmptyIterator EMPTY_ITERATOR = new EmptyIterator();
/*     */   
/*     */   private static class SingletonIterator implements BooleanListIterator { private final boolean element;
/*     */     private int curr;
/*     */     
/*     */     public SingletonIterator(boolean element) {
/*  99 */       this.element = element;
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 103 */       return (this.curr == 0);
/*     */     }
/*     */     
/*     */     public boolean hasPrevious() {
/* 107 */       return (this.curr == 1);
/*     */     }
/*     */     
/*     */     public boolean nextBoolean() {
/* 111 */       if (!hasNext())
/* 112 */         throw new NoSuchElementException(); 
/* 113 */       this.curr = 1;
/* 114 */       return this.element;
/*     */     }
/*     */     
/*     */     public boolean previousBoolean() {
/* 118 */       if (!hasPrevious())
/* 119 */         throw new NoSuchElementException(); 
/* 120 */       this.curr = 0;
/* 121 */       return this.element;
/*     */     }
/*     */     
/*     */     public int nextIndex() {
/* 125 */       return this.curr;
/*     */     }
/*     */     
/*     */     public int previousIndex() {
/* 129 */       return this.curr - 1;
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BooleanListIterator singleton(boolean element) {
/* 140 */     return new SingletonIterator(element);
/*     */   }
/*     */   private static class ArrayIterator implements BooleanListIterator { private final boolean[] array;
/*     */     private final int offset;
/*     */     private final int length;
/*     */     private int curr;
/*     */     
/*     */     public ArrayIterator(boolean[] array, int offset, int length) {
/* 148 */       this.array = array;
/* 149 */       this.offset = offset;
/* 150 */       this.length = length;
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 154 */       return (this.curr < this.length);
/*     */     }
/*     */     
/*     */     public boolean hasPrevious() {
/* 158 */       return (this.curr > 0);
/*     */     }
/*     */     
/*     */     public boolean nextBoolean() {
/* 162 */       if (!hasNext())
/* 163 */         throw new NoSuchElementException(); 
/* 164 */       return this.array[this.offset + this.curr++];
/*     */     }
/*     */     
/*     */     public boolean previousBoolean() {
/* 168 */       if (!hasPrevious())
/* 169 */         throw new NoSuchElementException(); 
/* 170 */       return this.array[this.offset + --this.curr];
/*     */     }
/*     */     
/*     */     public int skip(int n) {
/* 174 */       if (n <= this.length - this.curr) {
/* 175 */         this.curr += n;
/* 176 */         return n;
/*     */       } 
/* 178 */       n = this.length - this.curr;
/* 179 */       this.curr = this.length;
/* 180 */       return n;
/*     */     }
/*     */     
/*     */     public int back(int n) {
/* 184 */       if (n <= this.curr) {
/* 185 */         this.curr -= n;
/* 186 */         return n;
/*     */       } 
/* 188 */       n = this.curr;
/* 189 */       this.curr = 0;
/* 190 */       return n;
/*     */     }
/*     */     
/*     */     public int nextIndex() {
/* 194 */       return this.curr;
/*     */     }
/*     */     
/*     */     public int previousIndex() {
/* 198 */       return this.curr - 1;
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BooleanListIterator wrap(boolean[] array, int offset, int length) {
/* 219 */     BooleanArrays.ensureOffsetLength(array, offset, length);
/* 220 */     return new ArrayIterator(array, offset, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BooleanListIterator wrap(boolean[] array) {
/* 234 */     return new ArrayIterator(array, 0, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int unwrap(BooleanIterator i, boolean[] array, int offset, int max) {
/* 258 */     if (max < 0)
/* 259 */       throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/* 260 */     if (offset < 0 || offset + max > array.length)
/* 261 */       throw new IllegalArgumentException(); 
/* 262 */     int j = max;
/* 263 */     while (j-- != 0 && i.hasNext())
/* 264 */       array[offset++] = i.nextBoolean(); 
/* 265 */     return max - j - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int unwrap(BooleanIterator i, boolean[] array) {
/* 282 */     return unwrap(i, array, 0, array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean[] unwrap(BooleanIterator i, int max) {
/* 302 */     if (max < 0)
/* 303 */       throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/* 304 */     boolean[] array = new boolean[16];
/* 305 */     int j = 0;
/* 306 */     while (max-- != 0 && i.hasNext()) {
/* 307 */       if (j == array.length)
/* 308 */         array = BooleanArrays.grow(array, j + 1); 
/* 309 */       array[j++] = i.nextBoolean();
/*     */     } 
/* 311 */     return BooleanArrays.trim(array, j);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean[] unwrap(BooleanIterator i) {
/* 325 */     return unwrap(i, 2147483647);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long unwrap(BooleanIterator i, boolean[][] array, long offset, long max) {
/*     */     // Byte code:
/*     */     //   0: lload #4
/*     */     //   2: lconst_0
/*     */     //   3: lcmp
/*     */     //   4: ifge -> 40
/*     */     //   7: new java/lang/IllegalArgumentException
/*     */     //   10: dup
/*     */     //   11: new java/lang/StringBuilder
/*     */     //   14: dup
/*     */     //   15: invokespecial <init> : ()V
/*     */     //   18: ldc 'The maximum number of elements ('
/*     */     //   20: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   23: lload #4
/*     */     //   25: invokevirtual append : (J)Ljava/lang/StringBuilder;
/*     */     //   28: ldc ') is negative'
/*     */     //   30: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   33: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   36: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   39: athrow
/*     */     //   40: lload_2
/*     */     //   41: lconst_0
/*     */     //   42: lcmp
/*     */     //   43: iflt -> 58
/*     */     //   46: lload_2
/*     */     //   47: lload #4
/*     */     //   49: ladd
/*     */     //   50: aload_1
/*     */     //   51: invokestatic length : ([[Z)J
/*     */     //   54: lcmp
/*     */     //   55: ifle -> 66
/*     */     //   58: new java/lang/IllegalArgumentException
/*     */     //   61: dup
/*     */     //   62: invokespecial <init> : ()V
/*     */     //   65: athrow
/*     */     //   66: lload #4
/*     */     //   68: lstore #6
/*     */     //   70: lload #6
/*     */     //   72: dup2
/*     */     //   73: lconst_1
/*     */     //   74: lsub
/*     */     //   75: lstore #6
/*     */     //   77: lconst_0
/*     */     //   78: lcmp
/*     */     //   79: ifeq -> 109
/*     */     //   82: aload_0
/*     */     //   83: invokeinterface hasNext : ()Z
/*     */     //   88: ifeq -> 109
/*     */     //   91: aload_1
/*     */     //   92: lload_2
/*     */     //   93: dup2
/*     */     //   94: lconst_1
/*     */     //   95: ladd
/*     */     //   96: lstore_2
/*     */     //   97: aload_0
/*     */     //   98: invokeinterface nextBoolean : ()Z
/*     */     //   103: invokestatic set : ([[ZJZ)V
/*     */     //   106: goto -> 70
/*     */     //   109: lload #4
/*     */     //   111: lload #6
/*     */     //   113: lsub
/*     */     //   114: lconst_1
/*     */     //   115: lsub
/*     */     //   116: lreturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #349	-> 0
/*     */     //   #350	-> 7
/*     */     //   #351	-> 40
/*     */     //   #352	-> 58
/*     */     //   #353	-> 66
/*     */     //   #354	-> 70
/*     */     //   #355	-> 91
/*     */     //   #356	-> 109
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	117	0	i	Lit/unimi/dsi/fastutil/booleans/BooleanIterator;
/*     */     //   0	117	1	array	[[Z
/*     */     //   0	117	2	offset	J
/*     */     //   0	117	4	max	J
/*     */     //   70	47	6	j	J
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long unwrap(BooleanIterator i, boolean[][] array) {
/* 373 */     return unwrap(i, array, 0L, BigArrays.length(array));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int unwrap(BooleanIterator i, BooleanCollection c, int max) {
/* 398 */     if (max < 0)
/* 399 */       throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/* 400 */     int j = max;
/* 401 */     while (j-- != 0 && i.hasNext())
/* 402 */       c.add(i.nextBoolean()); 
/* 403 */     return max - j - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean[][] unwrapBig(BooleanIterator i, long max) {
/*     */     // Byte code:
/*     */     //   0: lload_1
/*     */     //   1: lconst_0
/*     */     //   2: lcmp
/*     */     //   3: ifge -> 38
/*     */     //   6: new java/lang/IllegalArgumentException
/*     */     //   9: dup
/*     */     //   10: new java/lang/StringBuilder
/*     */     //   13: dup
/*     */     //   14: invokespecial <init> : ()V
/*     */     //   17: ldc 'The maximum number of elements ('
/*     */     //   19: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   22: lload_1
/*     */     //   23: invokevirtual append : (J)Ljava/lang/StringBuilder;
/*     */     //   26: ldc ') is negative'
/*     */     //   28: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   31: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   34: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   37: athrow
/*     */     //   38: ldc2_w 16
/*     */     //   41: invokestatic newBigArray : (J)[[Z
/*     */     //   44: astore_3
/*     */     //   45: lconst_0
/*     */     //   46: lstore #4
/*     */     //   48: lload_1
/*     */     //   49: dup2
/*     */     //   50: lconst_1
/*     */     //   51: lsub
/*     */     //   52: lstore_1
/*     */     //   53: lconst_0
/*     */     //   54: lcmp
/*     */     //   55: ifeq -> 106
/*     */     //   58: aload_0
/*     */     //   59: invokeinterface hasNext : ()Z
/*     */     //   64: ifeq -> 106
/*     */     //   67: lload #4
/*     */     //   69: aload_3
/*     */     //   70: invokestatic length : ([[Z)J
/*     */     //   73: lcmp
/*     */     //   74: ifne -> 86
/*     */     //   77: aload_3
/*     */     //   78: lload #4
/*     */     //   80: lconst_1
/*     */     //   81: ladd
/*     */     //   82: invokestatic grow : ([[ZJ)[[Z
/*     */     //   85: astore_3
/*     */     //   86: aload_3
/*     */     //   87: lload #4
/*     */     //   89: dup2
/*     */     //   90: lconst_1
/*     */     //   91: ladd
/*     */     //   92: lstore #4
/*     */     //   94: aload_0
/*     */     //   95: invokeinterface nextBoolean : ()Z
/*     */     //   100: invokestatic set : ([[ZJZ)V
/*     */     //   103: goto -> 48
/*     */     //   106: aload_3
/*     */     //   107: lload #4
/*     */     //   109: invokestatic trim : ([[ZJ)[[Z
/*     */     //   112: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #423	-> 0
/*     */     //   #424	-> 6
/*     */     //   #425	-> 38
/*     */     //   #426	-> 45
/*     */     //   #427	-> 48
/*     */     //   #428	-> 67
/*     */     //   #429	-> 77
/*     */     //   #430	-> 86
/*     */     //   #432	-> 106
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	113	0	i	Lit/unimi/dsi/fastutil/booleans/BooleanIterator;
/*     */     //   0	113	1	max	J
/*     */     //   45	68	3	array	[[Z
/*     */     //   48	65	4	j	J
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean[][] unwrapBig(BooleanIterator i) {
/* 446 */     return unwrapBig(i, Long.MAX_VALUE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long unwrap(BooleanIterator i, BooleanCollection c) {
/* 467 */     long n = 0L;
/* 468 */     while (i.hasNext()) {
/* 469 */       c.add(i.nextBoolean());
/* 470 */       n++;
/*     */     } 
/* 472 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int pour(BooleanIterator i, BooleanCollection s, int max) {
/* 494 */     if (max < 0)
/* 495 */       throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/* 496 */     int j = max;
/* 497 */     while (j-- != 0 && i.hasNext())
/* 498 */       s.add(i.nextBoolean()); 
/* 499 */     return max - j - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int pour(BooleanIterator i, BooleanCollection s) {
/* 518 */     return pour(i, s, 2147483647);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BooleanList pour(BooleanIterator i, int max) {
/* 539 */     BooleanArrayList l = new BooleanArrayList();
/* 540 */     pour(i, l, max);
/* 541 */     l.trim();
/* 542 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BooleanList pour(BooleanIterator i) {
/* 558 */     return pour(i, 2147483647);
/*     */   }
/*     */   private static class IteratorWrapper implements BooleanIterator { final Iterator<Boolean> i;
/*     */     
/*     */     public IteratorWrapper(Iterator<Boolean> i) {
/* 563 */       this.i = i;
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 567 */       return this.i.hasNext();
/*     */     }
/*     */     
/*     */     public void remove() {
/* 571 */       this.i.remove();
/*     */     }
/*     */     
/*     */     public boolean nextBoolean() {
/* 575 */       return ((Boolean)this.i.next()).booleanValue();
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BooleanIterator asBooleanIterator(Iterator<Boolean> i) {
/* 598 */     if (i instanceof BooleanIterator)
/* 599 */       return (BooleanIterator)i; 
/* 600 */     return new IteratorWrapper(i);
/*     */   }
/*     */   private static class ListIteratorWrapper implements BooleanListIterator { final ListIterator<Boolean> i;
/*     */     
/*     */     public ListIteratorWrapper(ListIterator<Boolean> i) {
/* 605 */       this.i = i;
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 609 */       return this.i.hasNext();
/*     */     }
/*     */     
/*     */     public boolean hasPrevious() {
/* 613 */       return this.i.hasPrevious();
/*     */     }
/*     */     
/*     */     public int nextIndex() {
/* 617 */       return this.i.nextIndex();
/*     */     }
/*     */     
/*     */     public int previousIndex() {
/* 621 */       return this.i.previousIndex();
/*     */     }
/*     */     
/*     */     public void set(boolean k) {
/* 625 */       this.i.set(Boolean.valueOf(k));
/*     */     }
/*     */     
/*     */     public void add(boolean k) {
/* 629 */       this.i.add(Boolean.valueOf(k));
/*     */     }
/*     */     
/*     */     public void remove() {
/* 633 */       this.i.remove();
/*     */     }
/*     */     
/*     */     public boolean nextBoolean() {
/* 637 */       return ((Boolean)this.i.next()).booleanValue();
/*     */     }
/*     */     
/*     */     public boolean previousBoolean() {
/* 641 */       return ((Boolean)this.i.previous()).booleanValue();
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BooleanListIterator asBooleanIterator(ListIterator<Boolean> i) {
/* 664 */     if (i instanceof BooleanListIterator)
/* 665 */       return (BooleanListIterator)i; 
/* 666 */     return new ListIteratorWrapper(i);
/*     */   }
/*     */   
/*     */   public static boolean any(BooleanIterator iterator, Predicate<? super Boolean> predicate) {
/* 670 */     return (indexOf(iterator, predicate) != -1);
/*     */   }
/*     */   
/*     */   public static boolean all(BooleanIterator iterator, Predicate<? super Boolean> predicate) {
/* 674 */     Objects.requireNonNull(predicate);
/*     */     while (true) {
/* 676 */       if (!iterator.hasNext())
/* 677 */         return true; 
/* 678 */       if (!predicate.test(Boolean.valueOf(iterator.nextBoolean())))
/* 679 */         return false; 
/*     */     } 
/*     */   }
/*     */   public static int indexOf(BooleanIterator iterator, Predicate<? super Boolean> predicate) {
/* 683 */     Objects.requireNonNull(predicate);
/* 684 */     for (int i = 0; iterator.hasNext(); i++) {
/* 685 */       if (predicate.test(Boolean.valueOf(iterator.nextBoolean())))
/* 686 */         return i; 
/*     */     } 
/* 688 */     return -1;
/*     */   }
/*     */   
/*     */   private static class IteratorConcatenator implements BooleanIterator { final BooleanIterator[] a;
/* 692 */     int lastOffset = -1; int offset; int length;
/*     */     public IteratorConcatenator(BooleanIterator[] a, int offset, int length) {
/* 694 */       this.a = a;
/* 695 */       this.offset = offset;
/* 696 */       this.length = length;
/* 697 */       advance();
/*     */     }
/*     */     private void advance() {
/* 700 */       while (this.length != 0 && 
/* 701 */         !this.a[this.offset].hasNext()) {
/*     */         
/* 703 */         this.length--;
/* 704 */         this.offset++;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 710 */       return (this.length > 0);
/*     */     }
/*     */     
/*     */     public boolean nextBoolean() {
/* 714 */       if (!hasNext())
/* 715 */         throw new NoSuchElementException(); 
/* 716 */       boolean next = this.a[this.lastOffset = this.offset].nextBoolean();
/* 717 */       advance();
/* 718 */       return next;
/*     */     }
/*     */     
/*     */     public void remove() {
/* 722 */       if (this.lastOffset == -1)
/* 723 */         throw new IllegalStateException(); 
/* 724 */       this.a[this.lastOffset].remove();
/*     */     }
/*     */     
/*     */     public int skip(int n) {
/* 728 */       this.lastOffset = -1;
/* 729 */       int skipped = 0;
/* 730 */       while (skipped < n && this.length != 0) {
/* 731 */         skipped += this.a[this.offset].skip(n - skipped);
/* 732 */         if (this.a[this.offset].hasNext())
/*     */           break; 
/* 734 */         this.length--;
/* 735 */         this.offset++;
/*     */       } 
/* 737 */       return skipped;
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BooleanIterator concat(BooleanIterator[] a) {
/* 752 */     return concat(a, 0, a.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BooleanIterator concat(BooleanIterator[] a, int offset, int length) {
/* 772 */     return new IteratorConcatenator(a, offset, length);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableIterator implements BooleanIterator { protected final BooleanIterator i;
/*     */     
/*     */     public UnmodifiableIterator(BooleanIterator i) {
/* 778 */       this.i = i;
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 782 */       return this.i.hasNext();
/*     */     }
/*     */     
/*     */     public boolean nextBoolean() {
/* 786 */       return this.i.nextBoolean();
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BooleanIterator unmodifiable(BooleanIterator i) {
/* 797 */     return new UnmodifiableIterator(i);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableBidirectionalIterator implements BooleanBidirectionalIterator { protected final BooleanBidirectionalIterator i;
/*     */     
/*     */     public UnmodifiableBidirectionalIterator(BooleanBidirectionalIterator i) {
/* 803 */       this.i = i;
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 807 */       return this.i.hasNext();
/*     */     }
/*     */     
/*     */     public boolean hasPrevious() {
/* 811 */       return this.i.hasPrevious();
/*     */     }
/*     */     
/*     */     public boolean nextBoolean() {
/* 815 */       return this.i.nextBoolean();
/*     */     }
/*     */     
/*     */     public boolean previousBoolean() {
/* 819 */       return this.i.previousBoolean();
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BooleanBidirectionalIterator unmodifiable(BooleanBidirectionalIterator i) {
/* 832 */     return new UnmodifiableBidirectionalIterator(i);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableListIterator implements BooleanListIterator { protected final BooleanListIterator i;
/*     */     
/*     */     public UnmodifiableListIterator(BooleanListIterator i) {
/* 838 */       this.i = i;
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 842 */       return this.i.hasNext();
/*     */     }
/*     */     
/*     */     public boolean hasPrevious() {
/* 846 */       return this.i.hasPrevious();
/*     */     }
/*     */     
/*     */     public boolean nextBoolean() {
/* 850 */       return this.i.nextBoolean();
/*     */     }
/*     */     
/*     */     public boolean previousBoolean() {
/* 854 */       return this.i.previousBoolean();
/*     */     }
/*     */     
/*     */     public int nextIndex() {
/* 858 */       return this.i.nextIndex();
/*     */     }
/*     */     
/*     */     public int previousIndex() {
/* 862 */       return this.i.previousIndex();
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BooleanListIterator unmodifiable(BooleanListIterator i) {
/* 873 */     return new UnmodifiableListIterator(i);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\booleans\BooleanIterators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */