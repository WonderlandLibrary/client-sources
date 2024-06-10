/*     */ package it.unimi.dsi.fastutil.objects;
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
/*     */ public final class ObjectIterators
/*     */ {
/*     */   public static class EmptyIterator<K>
/*     */     implements ObjectListIterator<K>, Serializable, Cloneable
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
/*     */     public K next() {
/*  55 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public K previous() {
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
/*  79 */       return ObjectIterators.EMPTY_ITERATOR;
/*     */     }
/*     */     private Object readResolve() {
/*  82 */       return ObjectIterators.EMPTY_ITERATOR;
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
/*     */   public static <K> ObjectIterator<K> emptyIterator() {
/* 108 */     return EMPTY_ITERATOR;
/*     */   }
/*     */   
/*     */   private static class SingletonIterator<K> implements ObjectListIterator<K> { private final K element;
/*     */     private int curr;
/*     */     
/*     */     public SingletonIterator(K element) {
/* 115 */       this.element = element;
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 119 */       return (this.curr == 0);
/*     */     }
/*     */     
/*     */     public boolean hasPrevious() {
/* 123 */       return (this.curr == 1);
/*     */     }
/*     */     
/*     */     public K next() {
/* 127 */       if (!hasNext())
/* 128 */         throw new NoSuchElementException(); 
/* 129 */       this.curr = 1;
/* 130 */       return this.element;
/*     */     }
/*     */     
/*     */     public K previous() {
/* 134 */       if (!hasPrevious())
/* 135 */         throw new NoSuchElementException(); 
/* 136 */       this.curr = 0;
/* 137 */       return this.element;
/*     */     }
/*     */     
/*     */     public int nextIndex() {
/* 141 */       return this.curr;
/*     */     }
/*     */     
/*     */     public int previousIndex() {
/* 145 */       return this.curr - 1;
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ObjectListIterator<K> singleton(K element) {
/* 156 */     return new SingletonIterator<>(element);
/*     */   }
/*     */   private static class ArrayIterator<K> implements ObjectListIterator<K> { private final K[] array;
/*     */     private final int offset;
/*     */     private final int length;
/*     */     private int curr;
/*     */     
/*     */     public ArrayIterator(K[] array, int offset, int length) {
/* 164 */       this.array = array;
/* 165 */       this.offset = offset;
/* 166 */       this.length = length;
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 170 */       return (this.curr < this.length);
/*     */     }
/*     */     
/*     */     public boolean hasPrevious() {
/* 174 */       return (this.curr > 0);
/*     */     }
/*     */     
/*     */     public K next() {
/* 178 */       if (!hasNext())
/* 179 */         throw new NoSuchElementException(); 
/* 180 */       return this.array[this.offset + this.curr++];
/*     */     }
/*     */     
/*     */     public K previous() {
/* 184 */       if (!hasPrevious())
/* 185 */         throw new NoSuchElementException(); 
/* 186 */       return this.array[this.offset + --this.curr];
/*     */     }
/*     */     
/*     */     public int skip(int n) {
/* 190 */       if (n <= this.length - this.curr) {
/* 191 */         this.curr += n;
/* 192 */         return n;
/*     */       } 
/* 194 */       n = this.length - this.curr;
/* 195 */       this.curr = this.length;
/* 196 */       return n;
/*     */     }
/*     */     
/*     */     public int back(int n) {
/* 200 */       if (n <= this.curr) {
/* 201 */         this.curr -= n;
/* 202 */         return n;
/*     */       } 
/* 204 */       n = this.curr;
/* 205 */       this.curr = 0;
/* 206 */       return n;
/*     */     }
/*     */     
/*     */     public int nextIndex() {
/* 210 */       return this.curr;
/*     */     }
/*     */     
/*     */     public int previousIndex() {
/* 214 */       return this.curr - 1;
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
/*     */   public static <K> ObjectListIterator<K> wrap(K[] array, int offset, int length) {
/* 235 */     ObjectArrays.ensureOffsetLength(array, offset, length);
/* 236 */     return new ArrayIterator<>(array, offset, length);
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
/*     */   public static <K> ObjectListIterator<K> wrap(K[] array) {
/* 250 */     return new ArrayIterator<>(array, 0, array.length);
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
/*     */   public static <K> int unwrap(Iterator<? extends K> i, K[] array, int offset, int max) {
/* 274 */     if (max < 0)
/* 275 */       throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/* 276 */     if (offset < 0 || offset + max > array.length)
/* 277 */       throw new IllegalArgumentException(); 
/* 278 */     int j = max;
/* 279 */     while (j-- != 0 && i.hasNext())
/* 280 */       array[offset++] = i.next(); 
/* 281 */     return max - j - 1;
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
/*     */   public static <K> int unwrap(Iterator<? extends K> i, K[] array) {
/* 298 */     return unwrap(i, array, 0, array.length);
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
/*     */   public static <K> K[] unwrap(Iterator<? extends K> i, int max) {
/* 318 */     if (max < 0)
/* 319 */       throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/* 320 */     K[] array = (K[])new Object[16];
/* 321 */     int j = 0;
/* 322 */     while (max-- != 0 && i.hasNext()) {
/* 323 */       if (j == array.length)
/* 324 */         array = ObjectArrays.grow(array, j + 1); 
/* 325 */       array[j++] = i.next();
/*     */     } 
/* 327 */     return ObjectArrays.trim(array, j);
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
/*     */   public static <K> K[] unwrap(Iterator<? extends K> i) {
/* 341 */     return unwrap(i, 2147483647);
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
/*     */   public static <K> long unwrap(Iterator<? extends K> i, K[][] array, long offset, long max) {
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
/*     */     //   51: invokestatic length : ([[Ljava/lang/Object;)J
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
/*     */     //   98: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   103: invokestatic set : ([[Ljava/lang/Object;JLjava/lang/Object;)V
/*     */     //   106: goto -> 70
/*     */     //   109: lload #4
/*     */     //   111: lload #6
/*     */     //   113: lsub
/*     */     //   114: lconst_1
/*     */     //   115: lsub
/*     */     //   116: lreturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #365	-> 0
/*     */     //   #366	-> 7
/*     */     //   #367	-> 40
/*     */     //   #368	-> 58
/*     */     //   #369	-> 66
/*     */     //   #370	-> 70
/*     */     //   #371	-> 91
/*     */     //   #372	-> 109
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	117	0	i	Ljava/util/Iterator;
/*     */     //   0	117	1	array	[[Ljava/lang/Object;
/*     */     //   0	117	2	offset	J
/*     */     //   0	117	4	max	J
/*     */     //   70	47	6	j	J
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	117	0	i	Ljava/util/Iterator<+TK;>;
/*     */     //   0	117	1	array	[[TK;
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
/*     */   public static <K> long unwrap(Iterator<? extends K> i, K[][] array) {
/* 389 */     return unwrap(i, array, 0L, BigArrays.length((Object[][])array));
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
/*     */   public static <K> int unwrap(Iterator<K> i, ObjectCollection<? super K> c, int max) {
/* 414 */     if (max < 0)
/* 415 */       throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/* 416 */     int j = max;
/* 417 */     while (j-- != 0 && i.hasNext())
/* 418 */       c.add(i.next()); 
/* 419 */     return max - j - 1;
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
/*     */   public static <K> K[][] unwrapBig(Iterator<? extends K> i, long max) {
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
/*     */     //   41: invokestatic newBigArray : (J)[[Ljava/lang/Object;
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
/*     */     //   70: invokestatic length : ([[Ljava/lang/Object;)J
/*     */     //   73: lcmp
/*     */     //   74: ifne -> 86
/*     */     //   77: aload_3
/*     */     //   78: lload #4
/*     */     //   80: lconst_1
/*     */     //   81: ladd
/*     */     //   82: invokestatic grow : ([[Ljava/lang/Object;J)[[Ljava/lang/Object;
/*     */     //   85: astore_3
/*     */     //   86: aload_3
/*     */     //   87: lload #4
/*     */     //   89: dup2
/*     */     //   90: lconst_1
/*     */     //   91: ladd
/*     */     //   92: lstore #4
/*     */     //   94: aload_0
/*     */     //   95: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   100: invokestatic set : ([[Ljava/lang/Object;JLjava/lang/Object;)V
/*     */     //   103: goto -> 48
/*     */     //   106: aload_3
/*     */     //   107: lload #4
/*     */     //   109: invokestatic trim : ([[Ljava/lang/Object;J)[[Ljava/lang/Object;
/*     */     //   112: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #439	-> 0
/*     */     //   #440	-> 6
/*     */     //   #441	-> 38
/*     */     //   #442	-> 45
/*     */     //   #443	-> 48
/*     */     //   #444	-> 67
/*     */     //   #445	-> 77
/*     */     //   #446	-> 86
/*     */     //   #448	-> 106
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	113	0	i	Ljava/util/Iterator;
/*     */     //   0	113	1	max	J
/*     */     //   45	68	3	array	[[Ljava/lang/Object;
/*     */     //   48	65	4	j	J
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	113	0	i	Ljava/util/Iterator<+TK;>;
/*     */     //   45	68	3	array	[[TK;
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
/*     */   public static <K> K[][] unwrapBig(Iterator<? extends K> i) {
/* 462 */     return unwrapBig(i, Long.MAX_VALUE);
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
/*     */   public static <K> long unwrap(Iterator<K> i, ObjectCollection<? super K> c) {
/* 483 */     long n = 0L;
/* 484 */     while (i.hasNext()) {
/* 485 */       c.add(i.next());
/* 486 */       n++;
/*     */     } 
/* 488 */     return n;
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
/*     */   public static <K> int pour(Iterator<K> i, ObjectCollection<? super K> s, int max) {
/* 510 */     if (max < 0)
/* 511 */       throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/* 512 */     int j = max;
/* 513 */     while (j-- != 0 && i.hasNext())
/* 514 */       s.add(i.next()); 
/* 515 */     return max - j - 1;
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
/*     */   public static <K> int pour(Iterator<K> i, ObjectCollection<? super K> s) {
/* 534 */     return pour(i, s, 2147483647);
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
/*     */   public static <K> ObjectList<K> pour(Iterator<K> i, int max) {
/* 555 */     ObjectArrayList<K> l = new ObjectArrayList<>();
/* 556 */     pour(i, l, max);
/* 557 */     l.trim();
/* 558 */     return l;
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
/*     */   public static <K> ObjectList<K> pour(Iterator<K> i) {
/* 574 */     return pour(i, 2147483647);
/*     */   }
/*     */   private static class IteratorWrapper<K> implements ObjectIterator<K> { final Iterator<K> i;
/*     */     
/*     */     public IteratorWrapper(Iterator<K> i) {
/* 579 */       this.i = i;
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 583 */       return this.i.hasNext();
/*     */     }
/*     */     
/*     */     public void remove() {
/* 587 */       this.i.remove();
/*     */     }
/*     */     
/*     */     public K next() {
/* 591 */       return this.i.next();
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
/*     */   public static <K> ObjectIterator<K> asObjectIterator(Iterator<K> i) {
/* 613 */     if (i instanceof ObjectIterator)
/* 614 */       return (ObjectIterator<K>)i; 
/* 615 */     return new IteratorWrapper<>(i);
/*     */   }
/*     */   private static class ListIteratorWrapper<K> implements ObjectListIterator<K> { final ListIterator<K> i;
/*     */     
/*     */     public ListIteratorWrapper(ListIterator<K> i) {
/* 620 */       this.i = i;
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 624 */       return this.i.hasNext();
/*     */     }
/*     */     
/*     */     public boolean hasPrevious() {
/* 628 */       return this.i.hasPrevious();
/*     */     }
/*     */     
/*     */     public int nextIndex() {
/* 632 */       return this.i.nextIndex();
/*     */     }
/*     */     
/*     */     public int previousIndex() {
/* 636 */       return this.i.previousIndex();
/*     */     }
/*     */     
/*     */     public void set(K k) {
/* 640 */       this.i.set(k);
/*     */     }
/*     */     
/*     */     public void add(K k) {
/* 644 */       this.i.add(k);
/*     */     }
/*     */     
/*     */     public void remove() {
/* 648 */       this.i.remove();
/*     */     }
/*     */     
/*     */     public K next() {
/* 652 */       return this.i.next();
/*     */     }
/*     */     
/*     */     public K previous() {
/* 656 */       return this.i.previous();
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
/*     */   public static <K> ObjectListIterator<K> asObjectIterator(ListIterator<K> i) {
/* 678 */     if (i instanceof ObjectListIterator)
/* 679 */       return (ObjectListIterator<K>)i; 
/* 680 */     return new ListIteratorWrapper<>(i);
/*     */   }
/*     */   
/*     */   public static <K> boolean any(ObjectIterator<K> iterator, Predicate<? super K> predicate) {
/* 684 */     return (indexOf(iterator, predicate) != -1);
/*     */   }
/*     */   
/*     */   public static <K> boolean all(ObjectIterator<K> iterator, Predicate<? super K> predicate) {
/* 688 */     Objects.requireNonNull(predicate);
/*     */     while (true) {
/* 690 */       if (!iterator.hasNext())
/* 691 */         return true; 
/* 692 */       if (!predicate.test(iterator.next()))
/* 693 */         return false; 
/*     */     } 
/*     */   }
/*     */   public static <K> int indexOf(ObjectIterator<K> iterator, Predicate<? super K> predicate) {
/* 697 */     Objects.requireNonNull(predicate);
/* 698 */     for (int i = 0; iterator.hasNext(); i++) {
/* 699 */       if (predicate.test(iterator.next()))
/* 700 */         return i; 
/*     */     } 
/* 702 */     return -1;
/*     */   }
/*     */   
/*     */   private static class IteratorConcatenator<K> implements ObjectIterator<K> { final ObjectIterator<? extends K>[] a;
/* 706 */     int lastOffset = -1; int offset; int length;
/*     */     public IteratorConcatenator(ObjectIterator<? extends K>[] a, int offset, int length) {
/* 708 */       this.a = a;
/* 709 */       this.offset = offset;
/* 710 */       this.length = length;
/* 711 */       advance();
/*     */     }
/*     */     private void advance() {
/* 714 */       while (this.length != 0 && 
/* 715 */         !this.a[this.offset].hasNext()) {
/*     */         
/* 717 */         this.length--;
/* 718 */         this.offset++;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 724 */       return (this.length > 0);
/*     */     }
/*     */     
/*     */     public K next() {
/* 728 */       if (!hasNext())
/* 729 */         throw new NoSuchElementException(); 
/* 730 */       K next = this.a[this.lastOffset = this.offset].next();
/* 731 */       advance();
/* 732 */       return next;
/*     */     }
/*     */     
/*     */     public void remove() {
/* 736 */       if (this.lastOffset == -1)
/* 737 */         throw new IllegalStateException(); 
/* 738 */       this.a[this.lastOffset].remove();
/*     */     }
/*     */     
/*     */     public int skip(int n) {
/* 742 */       this.lastOffset = -1;
/* 743 */       int skipped = 0;
/* 744 */       while (skipped < n && this.length != 0) {
/* 745 */         skipped += this.a[this.offset].skip(n - skipped);
/* 746 */         if (this.a[this.offset].hasNext())
/*     */           break; 
/* 748 */         this.length--;
/* 749 */         this.offset++;
/*     */       } 
/* 751 */       return skipped;
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
/*     */   public static <K> ObjectIterator<K> concat(ObjectIterator<? extends K>[] a) {
/* 766 */     return concat(a, 0, a.length);
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
/*     */   public static <K> ObjectIterator<K> concat(ObjectIterator<? extends K>[] a, int offset, int length) {
/* 787 */     return new IteratorConcatenator<>(a, offset, length);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableIterator<K> implements ObjectIterator<K> { protected final ObjectIterator<K> i;
/*     */     
/*     */     public UnmodifiableIterator(ObjectIterator<K> i) {
/* 793 */       this.i = i;
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 797 */       return this.i.hasNext();
/*     */     }
/*     */     
/*     */     public K next() {
/* 801 */       return this.i.next();
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ObjectIterator<K> unmodifiable(ObjectIterator<K> i) {
/* 812 */     return new UnmodifiableIterator<>(i);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableBidirectionalIterator<K> implements ObjectBidirectionalIterator<K> { protected final ObjectBidirectionalIterator<K> i;
/*     */     
/*     */     public UnmodifiableBidirectionalIterator(ObjectBidirectionalIterator<K> i) {
/* 818 */       this.i = i;
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 822 */       return this.i.hasNext();
/*     */     }
/*     */     
/*     */     public boolean hasPrevious() {
/* 826 */       return this.i.hasPrevious();
/*     */     }
/*     */     
/*     */     public K next() {
/* 830 */       return this.i.next();
/*     */     }
/*     */     
/*     */     public K previous() {
/* 834 */       return (K)this.i.previous();
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
/*     */   public static <K> ObjectBidirectionalIterator<K> unmodifiable(ObjectBidirectionalIterator<K> i) {
/* 847 */     return new UnmodifiableBidirectionalIterator<>(i);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableListIterator<K> implements ObjectListIterator<K> { protected final ObjectListIterator<K> i;
/*     */     
/*     */     public UnmodifiableListIterator(ObjectListIterator<K> i) {
/* 853 */       this.i = i;
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 857 */       return this.i.hasNext();
/*     */     }
/*     */     
/*     */     public boolean hasPrevious() {
/* 861 */       return this.i.hasPrevious();
/*     */     }
/*     */     
/*     */     public K next() {
/* 865 */       return this.i.next();
/*     */     }
/*     */     
/*     */     public K previous() {
/* 869 */       return this.i.previous();
/*     */     }
/*     */     
/*     */     public int nextIndex() {
/* 873 */       return this.i.nextIndex();
/*     */     }
/*     */     
/*     */     public int previousIndex() {
/* 877 */       return this.i.previousIndex();
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ObjectListIterator<K> unmodifiable(ObjectListIterator<K> i) {
/* 888 */     return new UnmodifiableListIterator<>(i);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ObjectIterators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */