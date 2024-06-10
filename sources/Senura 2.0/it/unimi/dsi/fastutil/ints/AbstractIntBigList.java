/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.BigArrays;
/*     */ import it.unimi.dsi.fastutil.BigList;
/*     */ import it.unimi.dsi.fastutil.BigListIterator;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractIntBigList
/*     */   extends AbstractIntCollection
/*     */   implements IntBigList, IntStack
/*     */ {
/*     */   protected void ensureIndex(long index) {
/*  43 */     if (index < 0L)
/*  44 */       throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/*  45 */     if (index > size64()) {
/*  46 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than list size (" + size64() + ")");
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
/*     */   
/*     */   protected void ensureRestrictedIndex(long index) {
/*  59 */     if (index < 0L)
/*  60 */       throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/*  61 */     if (index >= size64()) {
/*  62 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + 
/*  63 */           size64() + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(long index, int k) {
/*  73 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(int k) {
/*  84 */     add(size64(), k);
/*  85 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int removeInt(long i) {
/*  95 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int set(long index, int k) {
/* 105 */     throw new UnsupportedOperationException();
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
/*     */   public boolean addAll(long index, Collection<? extends Integer> c) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: lload_1
/*     */     //   2: invokevirtual ensureIndex : (J)V
/*     */     //   5: aload_3
/*     */     //   6: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   11: astore #4
/*     */     //   13: aload #4
/*     */     //   15: invokeinterface hasNext : ()Z
/*     */     //   20: istore #5
/*     */     //   22: aload #4
/*     */     //   24: invokeinterface hasNext : ()Z
/*     */     //   29: ifeq -> 54
/*     */     //   32: aload_0
/*     */     //   33: lload_1
/*     */     //   34: dup2
/*     */     //   35: lconst_1
/*     */     //   36: ladd
/*     */     //   37: lstore_1
/*     */     //   38: aload #4
/*     */     //   40: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   45: checkcast java/lang/Integer
/*     */     //   48: invokevirtual add : (JLjava/lang/Integer;)V
/*     */     //   51: goto -> 22
/*     */     //   54: iload #5
/*     */     //   56: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #113	-> 0
/*     */     //   #114	-> 5
/*     */     //   #115	-> 13
/*     */     //   #116	-> 22
/*     */     //   #117	-> 32
/*     */     //   #118	-> 54
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	57	0	this	Lit/unimi/dsi/fastutil/ints/AbstractIntBigList;
/*     */     //   0	57	1	index	J
/*     */     //   0	57	3	c	Ljava/util/Collection;
/*     */     //   13	44	4	i	Ljava/util/Iterator;
/*     */     //   22	35	5	retVal	Z
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	57	3	c	Ljava/util/Collection<+Ljava/lang/Integer;>;
/*     */     //   13	44	4	i	Ljava/util/Iterator<+Ljava/lang/Integer;>;
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
/*     */   public boolean addAll(Collection<? extends Integer> c) {
/* 129 */     return addAll(size64(), c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntBigListIterator iterator() {
/* 139 */     return listIterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntBigListIterator listIterator() {
/* 150 */     return listIterator(0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntBigListIterator listIterator(final long index) {
/* 159 */     ensureIndex(index);
/* 160 */     return new IntBigListIterator() {
/* 161 */         long pos = index; long last = -1L;
/*     */         
/*     */         public boolean hasNext() {
/* 164 */           return (this.pos < AbstractIntBigList.this.size64());
/*     */         }
/*     */         
/*     */         public boolean hasPrevious() {
/* 168 */           return (this.pos > 0L);
/*     */         }
/*     */         
/*     */         public int nextInt() {
/* 172 */           if (!hasNext())
/* 173 */             throw new NoSuchElementException(); 
/* 174 */           return AbstractIntBigList.this.getInt(this.last = this.pos++);
/*     */         }
/*     */         
/*     */         public int previousInt() {
/* 178 */           if (!hasPrevious())
/* 179 */             throw new NoSuchElementException(); 
/* 180 */           return AbstractIntBigList.this.getInt(this.last = --this.pos);
/*     */         }
/*     */         
/*     */         public long nextIndex() {
/* 184 */           return this.pos;
/*     */         }
/*     */         
/*     */         public long previousIndex() {
/* 188 */           return this.pos - 1L;
/*     */         }
/*     */         
/*     */         public void add(int k) {
/* 192 */           AbstractIntBigList.this.add(this.pos++, k);
/* 193 */           this.last = -1L;
/*     */         }
/*     */         
/*     */         public void set(int k) {
/* 197 */           if (this.last == -1L)
/* 198 */             throw new IllegalStateException(); 
/* 199 */           AbstractIntBigList.this.set(this.last, k);
/*     */         }
/*     */         
/*     */         public void remove() {
/* 203 */           if (this.last == -1L)
/* 204 */             throw new IllegalStateException(); 
/* 205 */           AbstractIntBigList.this.removeInt(this.last);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 210 */           if (this.last < this.pos)
/* 211 */             this.pos--; 
/* 212 */           this.last = -1L;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(int k) {
/* 225 */     return (indexOf(k) >= 0L);
/*     */   }
/*     */   
/*     */   public long indexOf(int k) {
/* 229 */     IntBigListIterator i = listIterator();
/*     */     
/* 231 */     while (i.hasNext()) {
/* 232 */       int e = i.nextInt();
/* 233 */       if (k == e)
/* 234 */         return i.previousIndex(); 
/*     */     } 
/* 236 */     return -1L;
/*     */   }
/*     */   
/*     */   public long lastIndexOf(int k) {
/* 240 */     IntBigListIterator i = listIterator(size64());
/*     */     
/* 242 */     while (i.hasPrevious()) {
/* 243 */       int e = i.previousInt();
/* 244 */       if (k == e)
/* 245 */         return i.nextIndex(); 
/*     */     } 
/* 247 */     return -1L;
/*     */   }
/*     */   
/*     */   public void size(long size) {
/* 251 */     long i = size64();
/* 252 */     if (size > i) {
/* 253 */       while (i++ < size)
/* 254 */         add(0); 
/*     */     } else {
/* 256 */       while (i-- != size)
/* 257 */         remove(i); 
/*     */     } 
/*     */   }
/*     */   public IntBigList subList(long from, long to) {
/* 261 */     ensureIndex(from);
/* 262 */     ensureIndex(to);
/* 263 */     if (from > to)
/* 264 */       throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 265 */     return new IntSubList(this, from, to);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeElements(long from, long to) {
/* 276 */     ensureIndex(to);
/* 277 */     IntBigListIterator i = listIterator(from);
/* 278 */     long n = to - from;
/* 279 */     if (n < 0L)
/* 280 */       throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 281 */     while (n-- != 0L) {
/* 282 */       i.nextInt();
/* 283 */       i.remove();
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
/*     */ 
/*     */   
/*     */   public void addElements(long index, int[][] a, long offset, long length) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: lload_1
/*     */     //   2: invokevirtual ensureIndex : (J)V
/*     */     //   5: aload_3
/*     */     //   6: lload #4
/*     */     //   8: lload #6
/*     */     //   10: invokestatic ensureOffsetLength : ([[IJJ)V
/*     */     //   13: lload #6
/*     */     //   15: dup2
/*     */     //   16: lconst_1
/*     */     //   17: lsub
/*     */     //   18: lstore #6
/*     */     //   20: lconst_0
/*     */     //   21: lcmp
/*     */     //   22: ifeq -> 48
/*     */     //   25: aload_0
/*     */     //   26: lload_1
/*     */     //   27: dup2
/*     */     //   28: lconst_1
/*     */     //   29: ladd
/*     */     //   30: lstore_1
/*     */     //   31: aload_3
/*     */     //   32: lload #4
/*     */     //   34: dup2
/*     */     //   35: lconst_1
/*     */     //   36: ladd
/*     */     //   37: lstore #4
/*     */     //   39: invokestatic get : ([[IJ)I
/*     */     //   42: invokevirtual add : (JI)V
/*     */     //   45: goto -> 13
/*     */     //   48: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #295	-> 0
/*     */     //   #296	-> 5
/*     */     //   #297	-> 13
/*     */     //   #298	-> 25
/*     */     //   #299	-> 48
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	49	0	this	Lit/unimi/dsi/fastutil/ints/AbstractIntBigList;
/*     */     //   0	49	1	index	J
/*     */     //   0	49	3	a	[[I
/*     */     //   0	49	4	offset	J
/*     */     //   0	49	6	length	J
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
/*     */   public void addElements(long index, int[][] a) {
/* 309 */     addElements(index, a, 0L, BigArrays.length(a));
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
/*     */   public void getElements(long from, int[][] a, long offset, long length) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: lload_1
/*     */     //   2: invokevirtual listIterator : (J)Lit/unimi/dsi/fastutil/ints/IntBigListIterator;
/*     */     //   5: astore #8
/*     */     //   7: aload_3
/*     */     //   8: lload #4
/*     */     //   10: lload #6
/*     */     //   12: invokestatic ensureOffsetLength : ([[IJJ)V
/*     */     //   15: lload_1
/*     */     //   16: lload #6
/*     */     //   18: ladd
/*     */     //   19: aload_0
/*     */     //   20: invokevirtual size64 : ()J
/*     */     //   23: lcmp
/*     */     //   24: ifle -> 74
/*     */     //   27: new java/lang/IndexOutOfBoundsException
/*     */     //   30: dup
/*     */     //   31: new java/lang/StringBuilder
/*     */     //   34: dup
/*     */     //   35: invokespecial <init> : ()V
/*     */     //   38: ldc 'End index ('
/*     */     //   40: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   43: lload_1
/*     */     //   44: lload #6
/*     */     //   46: ladd
/*     */     //   47: invokevirtual append : (J)Ljava/lang/StringBuilder;
/*     */     //   50: ldc ') is greater than list size ('
/*     */     //   52: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   55: aload_0
/*     */     //   56: invokevirtual size64 : ()J
/*     */     //   59: invokevirtual append : (J)Ljava/lang/StringBuilder;
/*     */     //   62: ldc ')'
/*     */     //   64: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   67: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   70: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   73: athrow
/*     */     //   74: lload #6
/*     */     //   76: dup2
/*     */     //   77: lconst_1
/*     */     //   78: lsub
/*     */     //   79: lstore #6
/*     */     //   81: lconst_0
/*     */     //   82: lcmp
/*     */     //   83: ifeq -> 107
/*     */     //   86: aload_3
/*     */     //   87: lload #4
/*     */     //   89: dup2
/*     */     //   90: lconst_1
/*     */     //   91: ladd
/*     */     //   92: lstore #4
/*     */     //   94: aload #8
/*     */     //   96: invokeinterface nextInt : ()I
/*     */     //   101: invokestatic set : ([[IJI)V
/*     */     //   104: goto -> 74
/*     */     //   107: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #320	-> 0
/*     */     //   #321	-> 7
/*     */     //   #322	-> 15
/*     */     //   #323	-> 27
/*     */     //   #324	-> 56
/*     */     //   #325	-> 74
/*     */     //   #326	-> 86
/*     */     //   #327	-> 107
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	108	0	this	Lit/unimi/dsi/fastutil/ints/AbstractIntBigList;
/*     */     //   0	108	1	from	J
/*     */     //   0	108	3	a	[[I
/*     */     //   0	108	4	offset	J
/*     */     //   0	108	6	length	J
/*     */     //   7	101	8	i	Lit/unimi/dsi/fastutil/ints/IntBigListIterator;
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
/*     */   public void clear() {
/* 335 */     removeElements(0L, size64());
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
/*     */   @Deprecated
/*     */   public int size() {
/* 348 */     return (int)Math.min(2147483647L, size64());
/*     */   }
/*     */   private boolean valEquals(Object a, Object b) {
/* 351 */     return (a == null) ? ((b == null)) : a.equals(b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 361 */     IntIterator i = iterator();
/* 362 */     int h = 1;
/* 363 */     long s = size64();
/* 364 */     while (s-- != 0L) {
/* 365 */       int k = i.nextInt();
/* 366 */       h = 31 * h + k;
/*     */     } 
/* 368 */     return h;
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/* 372 */     if (o == this)
/* 373 */       return true; 
/* 374 */     if (!(o instanceof BigList))
/* 375 */       return false; 
/* 376 */     BigList<?> l = (BigList)o;
/* 377 */     long s = size64();
/* 378 */     if (s != l.size64())
/* 379 */       return false; 
/* 380 */     if (l instanceof IntBigList) {
/* 381 */       IntBigListIterator intBigListIterator1 = listIterator(), intBigListIterator2 = ((IntBigList)l).listIterator();
/* 382 */       while (s-- != 0L) {
/* 383 */         if (intBigListIterator1.nextInt() != intBigListIterator2.nextInt())
/* 384 */           return false; 
/* 385 */       }  return true;
/*     */     } 
/* 387 */     BigListIterator<?> i1 = listIterator(), i2 = l.listIterator();
/* 388 */     while (s-- != 0L) {
/* 389 */       if (!valEquals(i1.next(), i2.next()))
/* 390 */         return false; 
/* 391 */     }  return true;
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
/*     */   public int compareTo(BigList<? extends Integer> l) {
/* 409 */     if (l == this)
/* 410 */       return 0; 
/* 411 */     if (l instanceof IntBigList) {
/* 412 */       IntBigListIterator intBigListIterator1 = listIterator(), intBigListIterator2 = ((IntBigList)l).listIterator();
/*     */ 
/*     */       
/* 415 */       while (intBigListIterator1.hasNext() && intBigListIterator2.hasNext()) {
/* 416 */         int e1 = intBigListIterator1.nextInt();
/* 417 */         int e2 = intBigListIterator2.nextInt(); int r;
/* 418 */         if ((r = Integer.compare(e1, e2)) != 0)
/* 419 */           return r; 
/*     */       } 
/* 421 */       return intBigListIterator2.hasNext() ? -1 : (intBigListIterator1.hasNext() ? 1 : 0);
/*     */     } 
/* 423 */     BigListIterator<? extends Integer> i1 = listIterator(), i2 = l.listIterator();
/*     */     
/* 425 */     while (i1.hasNext() && i2.hasNext()) {
/* 426 */       int r; if ((r = ((Comparable<Object>)i1.next()).compareTo(i2.next())) != 0)
/* 427 */         return r; 
/*     */     } 
/* 429 */     return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
/*     */   }
/*     */   
/*     */   public void push(int o) {
/* 433 */     add(o);
/*     */   }
/*     */   
/*     */   public int popInt() {
/* 437 */     if (isEmpty())
/* 438 */       throw new NoSuchElementException(); 
/* 439 */     return removeInt(size64() - 1L);
/*     */   }
/*     */   
/*     */   public int topInt() {
/* 443 */     if (isEmpty())
/* 444 */       throw new NoSuchElementException(); 
/* 445 */     return getInt(size64() - 1L);
/*     */   }
/*     */   
/*     */   public int peekInt(int i) {
/* 449 */     return getInt(size64() - 1L - i);
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
/*     */   public boolean rem(int k) {
/* 461 */     long index = indexOf(k);
/* 462 */     if (index == -1L)
/* 463 */       return false; 
/* 464 */     removeInt(index);
/* 465 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(long index, IntCollection c) {
/* 476 */     return addAll(index, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(long index, IntBigList l) {
/* 487 */     return addAll(index, l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(IntCollection c) {
/* 498 */     return addAll(size64(), c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(IntBigList l) {
/* 509 */     return addAll(size64(), l);
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
/*     */   @Deprecated
/*     */   public void add(long index, Integer ok) {
/* 522 */     add(index, ok.intValue());
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
/*     */   @Deprecated
/*     */   public Integer set(long index, Integer ok) {
/* 535 */     return Integer.valueOf(set(index, ok.intValue()));
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
/*     */   @Deprecated
/*     */   public Integer get(long index) {
/* 548 */     return Integer.valueOf(getInt(index));
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
/*     */   @Deprecated
/*     */   public long indexOf(Object ok) {
/* 561 */     return indexOf(((Integer)ok).intValue());
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
/*     */   @Deprecated
/*     */   public long lastIndexOf(Object ok) {
/* 574 */     return lastIndexOf(((Integer)ok).intValue());
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
/*     */   @Deprecated
/*     */   public Integer remove(long index) {
/* 587 */     return Integer.valueOf(removeInt(index));
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
/*     */   @Deprecated
/*     */   public void push(Integer o) {
/* 600 */     push(o.intValue());
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
/*     */   @Deprecated
/*     */   public Integer pop() {
/* 613 */     return Integer.valueOf(popInt());
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
/*     */   @Deprecated
/*     */   public Integer top() {
/* 626 */     return Integer.valueOf(topInt());
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
/*     */   @Deprecated
/*     */   public Integer peek(int i) {
/* 639 */     return Integer.valueOf(peekInt(i));
/*     */   }
/*     */   
/*     */   public String toString() {
/* 643 */     StringBuilder s = new StringBuilder();
/* 644 */     IntIterator i = iterator();
/* 645 */     long n = size64();
/*     */     
/* 647 */     boolean first = true;
/* 648 */     s.append("[");
/* 649 */     while (n-- != 0L) {
/* 650 */       if (first) {
/* 651 */         first = false;
/*     */       } else {
/* 653 */         s.append(", ");
/* 654 */       }  int k = i.nextInt();
/* 655 */       s.append(String.valueOf(k));
/*     */     } 
/* 657 */     s.append("]");
/* 658 */     return s.toString();
/*     */   }
/*     */   
/*     */   public static class IntSubList
/*     */     extends AbstractIntBigList
/*     */     implements Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final IntBigList l;
/*     */     protected final long from;
/*     */     protected long to;
/*     */     
/*     */     public IntSubList(IntBigList l, long from, long to) {
/* 670 */       this.l = l;
/* 671 */       this.from = from;
/* 672 */       this.to = to;
/*     */     }
/*     */     private boolean assertRange() {
/* 675 */       assert this.from <= this.l.size64();
/* 676 */       assert this.to <= this.l.size64();
/* 677 */       assert this.to >= this.from;
/* 678 */       return true;
/*     */     }
/*     */     
/*     */     public boolean add(int k) {
/* 682 */       this.l.add(this.to, k);
/* 683 */       this.to++;
/* 684 */       assert assertRange();
/* 685 */       return true;
/*     */     }
/*     */     
/*     */     public void add(long index, int k) {
/* 689 */       ensureIndex(index);
/* 690 */       this.l.add(this.from + index, k);
/* 691 */       this.to++;
/* 692 */       assert assertRange();
/*     */     }
/*     */     
/*     */     public boolean addAll(long index, Collection<? extends Integer> c) {
/* 696 */       ensureIndex(index);
/* 697 */       this.to += c.size();
/* 698 */       return this.l.addAll(this.from + index, c);
/*     */     }
/*     */     
/*     */     public int getInt(long index) {
/* 702 */       ensureRestrictedIndex(index);
/* 703 */       return this.l.getInt(this.from + index);
/*     */     }
/*     */     
/*     */     public int removeInt(long index) {
/* 707 */       ensureRestrictedIndex(index);
/* 708 */       this.to--;
/* 709 */       return this.l.removeInt(this.from + index);
/*     */     }
/*     */     
/*     */     public int set(long index, int k) {
/* 713 */       ensureRestrictedIndex(index);
/* 714 */       return this.l.set(this.from + index, k);
/*     */     }
/*     */     
/*     */     public long size64() {
/* 718 */       return this.to - this.from;
/*     */     }
/*     */     
/*     */     public void getElements(long from, int[][] a, long offset, long length) {
/* 722 */       ensureIndex(from);
/* 723 */       if (from + length > size64())
/* 724 */         throw new IndexOutOfBoundsException("End index (" + from + length + ") is greater than list size (" + 
/* 725 */             size64() + ")"); 
/* 726 */       this.l.getElements(this.from + from, a, offset, length);
/*     */     }
/*     */     
/*     */     public void removeElements(long from, long to) {
/* 730 */       ensureIndex(from);
/* 731 */       ensureIndex(to);
/* 732 */       this.l.removeElements(this.from + from, this.from + to);
/* 733 */       this.to -= to - from;
/* 734 */       assert assertRange();
/*     */     }
/*     */     
/*     */     public void addElements(long index, int[][] a, long offset, long length) {
/* 738 */       ensureIndex(index);
/* 739 */       this.l.addElements(this.from + index, a, offset, length);
/* 740 */       this.to += length;
/* 741 */       assert assertRange();
/*     */     }
/*     */     
/*     */     public IntBigListIterator listIterator(final long index) {
/* 745 */       ensureIndex(index);
/* 746 */       return new IntBigListIterator() {
/* 747 */           long pos = index; long last = -1L;
/*     */           
/*     */           public boolean hasNext() {
/* 750 */             return (this.pos < AbstractIntBigList.IntSubList.this.size64());
/*     */           }
/*     */           
/*     */           public boolean hasPrevious() {
/* 754 */             return (this.pos > 0L);
/*     */           }
/*     */           
/*     */           public int nextInt() {
/* 758 */             if (!hasNext())
/* 759 */               throw new NoSuchElementException(); 
/* 760 */             return AbstractIntBigList.IntSubList.this.l.getInt(AbstractIntBigList.IntSubList.this.from + (this.last = this.pos++));
/*     */           }
/*     */           
/*     */           public int previousInt() {
/* 764 */             if (!hasPrevious())
/* 765 */               throw new NoSuchElementException(); 
/* 766 */             return AbstractIntBigList.IntSubList.this.l.getInt(AbstractIntBigList.IntSubList.this.from + (this.last = --this.pos));
/*     */           }
/*     */           
/*     */           public long nextIndex() {
/* 770 */             return this.pos;
/*     */           }
/*     */           
/*     */           public long previousIndex() {
/* 774 */             return this.pos - 1L;
/*     */           }
/*     */           
/*     */           public void add(int k) {
/* 778 */             if (this.last == -1L)
/* 779 */               throw new IllegalStateException(); 
/* 780 */             AbstractIntBigList.IntSubList.this.add(this.pos++, k);
/* 781 */             this.last = -1L;
/* 782 */             if (!$assertionsDisabled && !AbstractIntBigList.IntSubList.this.assertRange()) throw new AssertionError(); 
/*     */           }
/*     */           
/*     */           public void set(int k) {
/* 786 */             if (this.last == -1L)
/* 787 */               throw new IllegalStateException(); 
/* 788 */             AbstractIntBigList.IntSubList.this.set(this.last, k);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 792 */             if (this.last == -1L)
/* 793 */               throw new IllegalStateException(); 
/* 794 */             AbstractIntBigList.IntSubList.this.removeInt(this.last);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 799 */             if (this.last < this.pos)
/* 800 */               this.pos--; 
/* 801 */             this.last = -1L;
/* 802 */             assert AbstractIntBigList.IntSubList.this.assertRange();
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public IntBigList subList(long from, long to) {
/* 808 */       ensureIndex(from);
/* 809 */       ensureIndex(to);
/* 810 */       if (from > to)
/* 811 */         throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 812 */       return new IntSubList(this, from, to);
/*     */     }
/*     */     
/*     */     public boolean rem(int k) {
/* 816 */       long index = indexOf(k);
/* 817 */       if (index == -1L)
/* 818 */         return false; 
/* 819 */       this.to--;
/* 820 */       this.l.removeInt(this.from + index);
/* 821 */       assert assertRange();
/* 822 */       return true;
/*     */     }
/*     */     
/*     */     public boolean addAll(long index, IntCollection c) {
/* 826 */       ensureIndex(index);
/* 827 */       return super.addAll(index, c);
/*     */     }
/*     */     
/*     */     public boolean addAll(long index, IntBigList l) {
/* 831 */       ensureIndex(index);
/* 832 */       return super.addAll(index, l);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\AbstractIntBigList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */