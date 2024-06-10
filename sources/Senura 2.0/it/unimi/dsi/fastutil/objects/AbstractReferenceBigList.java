/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.BigArrays;
/*     */ import it.unimi.dsi.fastutil.BigList;
/*     */ import it.unimi.dsi.fastutil.BigListIterator;
/*     */ import it.unimi.dsi.fastutil.Stack;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractReferenceBigList<K>
/*     */   extends AbstractReferenceCollection<K>
/*     */   implements ReferenceBigList<K>, Stack<K>
/*     */ {
/*     */   protected void ensureIndex(long index) {
/*  47 */     if (index < 0L)
/*  48 */       throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/*  49 */     if (index > size64()) {
/*  50 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than list size (" + size64() + ")");
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
/*  63 */     if (index < 0L)
/*  64 */       throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/*  65 */     if (index >= size64()) {
/*  66 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + 
/*  67 */           size64() + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(long index, K k) {
/*  77 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(K k) {
/*  88 */     add(size64(), k);
/*  89 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public K remove(long i) {
/*  99 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public K set(long index, K k) {
/* 109 */     throw new UnsupportedOperationException();
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
/*     */   public boolean addAll(long index, Collection<? extends K> c) {
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
/*     */     //   29: ifeq -> 51
/*     */     //   32: aload_0
/*     */     //   33: lload_1
/*     */     //   34: dup2
/*     */     //   35: lconst_1
/*     */     //   36: ladd
/*     */     //   37: lstore_1
/*     */     //   38: aload #4
/*     */     //   40: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   45: invokevirtual add : (JLjava/lang/Object;)V
/*     */     //   48: goto -> 22
/*     */     //   51: iload #5
/*     */     //   53: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #117	-> 0
/*     */     //   #118	-> 5
/*     */     //   #119	-> 13
/*     */     //   #120	-> 22
/*     */     //   #121	-> 32
/*     */     //   #122	-> 51
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	54	0	this	Lit/unimi/dsi/fastutil/objects/AbstractReferenceBigList;
/*     */     //   0	54	1	index	J
/*     */     //   0	54	3	c	Ljava/util/Collection;
/*     */     //   13	41	4	i	Ljava/util/Iterator;
/*     */     //   22	32	5	retVal	Z
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	54	0	this	Lit/unimi/dsi/fastutil/objects/AbstractReferenceBigList<TK;>;
/*     */     //   0	54	3	c	Ljava/util/Collection<+TK;>;
/*     */     //   13	41	4	i	Ljava/util/Iterator<+TK;>;
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
/*     */   public boolean addAll(Collection<? extends K> c) {
/* 133 */     return addAll(size64(), c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectBigListIterator<K> iterator() {
/* 143 */     return listIterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectBigListIterator<K> listIterator() {
/* 154 */     return listIterator(0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectBigListIterator<K> listIterator(final long index) {
/* 163 */     ensureIndex(index);
/* 164 */     return new ObjectBigListIterator<K>() {
/* 165 */         long pos = index; long last = -1L;
/*     */         
/*     */         public boolean hasNext() {
/* 168 */           return (this.pos < AbstractReferenceBigList.this.size64());
/*     */         }
/*     */         
/*     */         public boolean hasPrevious() {
/* 172 */           return (this.pos > 0L);
/*     */         }
/*     */         
/*     */         public K next() {
/* 176 */           if (!hasNext())
/* 177 */             throw new NoSuchElementException(); 
/* 178 */           return (K)AbstractReferenceBigList.this.get(this.last = this.pos++);
/*     */         }
/*     */         
/*     */         public K previous() {
/* 182 */           if (!hasPrevious())
/* 183 */             throw new NoSuchElementException(); 
/* 184 */           return (K)AbstractReferenceBigList.this.get(this.last = --this.pos);
/*     */         }
/*     */         
/*     */         public long nextIndex() {
/* 188 */           return this.pos;
/*     */         }
/*     */         
/*     */         public long previousIndex() {
/* 192 */           return this.pos - 1L;
/*     */         }
/*     */         
/*     */         public void add(K k) {
/* 196 */           AbstractReferenceBigList.this.add(this.pos++, k);
/* 197 */           this.last = -1L;
/*     */         }
/*     */         
/*     */         public void set(K k) {
/* 201 */           if (this.last == -1L)
/* 202 */             throw new IllegalStateException(); 
/* 203 */           AbstractReferenceBigList.this.set(this.last, k);
/*     */         }
/*     */         
/*     */         public void remove() {
/* 207 */           if (this.last == -1L)
/* 208 */             throw new IllegalStateException(); 
/* 209 */           AbstractReferenceBigList.this.remove(this.last);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 214 */           if (this.last < this.pos)
/* 215 */             this.pos--; 
/* 216 */           this.last = -1L;
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
/*     */   public boolean contains(Object k) {
/* 229 */     return (indexOf(k) >= 0L);
/*     */   }
/*     */   
/*     */   public long indexOf(Object k) {
/* 233 */     ObjectBigListIterator<K> i = listIterator();
/*     */     
/* 235 */     while (i.hasNext()) {
/* 236 */       K e = i.next();
/* 237 */       if (k == e)
/* 238 */         return i.previousIndex(); 
/*     */     } 
/* 240 */     return -1L;
/*     */   }
/*     */   
/*     */   public long lastIndexOf(Object k) {
/* 244 */     ObjectBigListIterator<K> i = listIterator(size64());
/*     */     
/* 246 */     while (i.hasPrevious()) {
/* 247 */       K e = (K)i.previous();
/* 248 */       if (k == e)
/* 249 */         return i.nextIndex(); 
/*     */     } 
/* 251 */     return -1L;
/*     */   }
/*     */   
/*     */   public void size(long size) {
/* 255 */     long i = size64();
/* 256 */     if (size > i) {
/* 257 */       while (i++ < size)
/* 258 */         add((K)null); 
/*     */     } else {
/* 260 */       while (i-- != size)
/* 261 */         remove(i); 
/*     */     } 
/*     */   }
/*     */   public ReferenceBigList<K> subList(long from, long to) {
/* 265 */     ensureIndex(from);
/* 266 */     ensureIndex(to);
/* 267 */     if (from > to)
/* 268 */       throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 269 */     return new ReferenceSubList<>(this, from, to);
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
/* 280 */     ensureIndex(to);
/* 281 */     ObjectBigListIterator<K> i = listIterator(from);
/* 282 */     long n = to - from;
/* 283 */     if (n < 0L)
/* 284 */       throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 285 */     while (n-- != 0L) {
/* 286 */       i.next();
/* 287 */       i.remove();
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
/*     */   public void addElements(long index, K[][] a, long offset, long length) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: lload_1
/*     */     //   2: invokevirtual ensureIndex : (J)V
/*     */     //   5: aload_3
/*     */     //   6: lload #4
/*     */     //   8: lload #6
/*     */     //   10: invokestatic ensureOffsetLength : ([[Ljava/lang/Object;JJ)V
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
/*     */     //   39: invokestatic get : ([[Ljava/lang/Object;J)Ljava/lang/Object;
/*     */     //   42: invokevirtual add : (JLjava/lang/Object;)V
/*     */     //   45: goto -> 13
/*     */     //   48: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #299	-> 0
/*     */     //   #300	-> 5
/*     */     //   #301	-> 13
/*     */     //   #302	-> 25
/*     */     //   #303	-> 48
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	49	0	this	Lit/unimi/dsi/fastutil/objects/AbstractReferenceBigList;
/*     */     //   0	49	1	index	J
/*     */     //   0	49	3	a	[[Ljava/lang/Object;
/*     */     //   0	49	4	offset	J
/*     */     //   0	49	6	length	J
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	49	0	this	Lit/unimi/dsi/fastutil/objects/AbstractReferenceBigList<TK;>;
/*     */     //   0	49	3	a	[[TK;
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
/*     */   public void addElements(long index, K[][] a) {
/* 313 */     addElements(index, a, 0L, BigArrays.length((Object[][])a));
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
/*     */   public void getElements(long from, Object[][] a, long offset, long length) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: lload_1
/*     */     //   2: invokevirtual listIterator : (J)Lit/unimi/dsi/fastutil/objects/ObjectBigListIterator;
/*     */     //   5: astore #8
/*     */     //   7: aload_3
/*     */     //   8: lload #4
/*     */     //   10: lload #6
/*     */     //   12: invokestatic ensureOffsetLength : ([[Ljava/lang/Object;JJ)V
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
/*     */     //   96: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   101: invokestatic set : ([[Ljava/lang/Object;JLjava/lang/Object;)V
/*     */     //   104: goto -> 74
/*     */     //   107: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #324	-> 0
/*     */     //   #325	-> 7
/*     */     //   #326	-> 15
/*     */     //   #327	-> 27
/*     */     //   #328	-> 56
/*     */     //   #329	-> 74
/*     */     //   #330	-> 86
/*     */     //   #331	-> 107
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	108	0	this	Lit/unimi/dsi/fastutil/objects/AbstractReferenceBigList;
/*     */     //   0	108	1	from	J
/*     */     //   0	108	3	a	[[Ljava/lang/Object;
/*     */     //   0	108	4	offset	J
/*     */     //   0	108	6	length	J
/*     */     //   7	101	8	i	Lit/unimi/dsi/fastutil/objects/ObjectBigListIterator;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	108	0	this	Lit/unimi/dsi/fastutil/objects/AbstractReferenceBigList<TK;>;
/*     */     //   7	101	8	i	Lit/unimi/dsi/fastutil/objects/ObjectBigListIterator<TK;>;
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
/* 339 */     removeElements(0L, size64());
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
/* 352 */     return (int)Math.min(2147483647L, size64());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 362 */     ObjectIterator<K> i = iterator();
/* 363 */     int h = 1;
/* 364 */     long s = size64();
/* 365 */     while (s-- != 0L) {
/* 366 */       K k = i.next();
/* 367 */       h = 31 * h + System.identityHashCode(k);
/*     */     } 
/* 369 */     return h;
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/* 373 */     if (o == this)
/* 374 */       return true; 
/* 375 */     if (!(o instanceof BigList))
/* 376 */       return false; 
/* 377 */     BigList<?> l = (BigList)o;
/* 378 */     long s = size64();
/* 379 */     if (s != l.size64())
/* 380 */       return false; 
/* 381 */     BigListIterator<?> i1 = listIterator(), i2 = l.listIterator();
/* 382 */     while (s-- != 0L) {
/* 383 */       if (i1.next() != i2.next())
/* 384 */         return false; 
/* 385 */     }  return true;
/*     */   }
/*     */   
/*     */   public void push(K o) {
/* 389 */     add(o);
/*     */   }
/*     */   
/*     */   public K pop() {
/* 393 */     if (isEmpty())
/* 394 */       throw new NoSuchElementException(); 
/* 395 */     return remove(size64() - 1L);
/*     */   }
/*     */   
/*     */   public K top() {
/* 399 */     if (isEmpty())
/* 400 */       throw new NoSuchElementException(); 
/* 401 */     return (K)get(size64() - 1L);
/*     */   }
/*     */   
/*     */   public K peek(int i) {
/* 405 */     return (K)get(size64() - 1L - i);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 409 */     StringBuilder s = new StringBuilder();
/* 410 */     ObjectIterator<K> i = iterator();
/* 411 */     long n = size64();
/*     */     
/* 413 */     boolean first = true;
/* 414 */     s.append("[");
/* 415 */     while (n-- != 0L) {
/* 416 */       if (first) {
/* 417 */         first = false;
/*     */       } else {
/* 419 */         s.append(", ");
/* 420 */       }  K k = i.next();
/* 421 */       if (this == k) {
/* 422 */         s.append("(this big list)"); continue;
/*     */       } 
/* 424 */       s.append(String.valueOf(k));
/*     */     } 
/* 426 */     s.append("]");
/* 427 */     return s.toString();
/*     */   }
/*     */   
/*     */   public static class ReferenceSubList<K>
/*     */     extends AbstractReferenceBigList<K>
/*     */     implements Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ReferenceBigList<K> l;
/*     */     protected final long from;
/*     */     protected long to;
/*     */     
/*     */     public ReferenceSubList(ReferenceBigList<K> l, long from, long to) {
/* 439 */       this.l = l;
/* 440 */       this.from = from;
/* 441 */       this.to = to;
/*     */     }
/*     */     private boolean assertRange() {
/* 444 */       assert this.from <= this.l.size64();
/* 445 */       assert this.to <= this.l.size64();
/* 446 */       assert this.to >= this.from;
/* 447 */       return true;
/*     */     }
/*     */     
/*     */     public boolean add(K k) {
/* 451 */       this.l.add(this.to, k);
/* 452 */       this.to++;
/* 453 */       assert assertRange();
/* 454 */       return true;
/*     */     }
/*     */     
/*     */     public void add(long index, K k) {
/* 458 */       ensureIndex(index);
/* 459 */       this.l.add(this.from + index, k);
/* 460 */       this.to++;
/* 461 */       assert assertRange();
/*     */     }
/*     */     
/*     */     public boolean addAll(long index, Collection<? extends K> c) {
/* 465 */       ensureIndex(index);
/* 466 */       this.to += c.size();
/* 467 */       return this.l.addAll(this.from + index, c);
/*     */     }
/*     */     
/*     */     public K get(long index) {
/* 471 */       ensureRestrictedIndex(index);
/* 472 */       return (K)this.l.get(this.from + index);
/*     */     }
/*     */     
/*     */     public K remove(long index) {
/* 476 */       ensureRestrictedIndex(index);
/* 477 */       this.to--;
/* 478 */       return (K)this.l.remove(this.from + index);
/*     */     }
/*     */     
/*     */     public K set(long index, K k) {
/* 482 */       ensureRestrictedIndex(index);
/* 483 */       return (K)this.l.set(this.from + index, k);
/*     */     }
/*     */     
/*     */     public long size64() {
/* 487 */       return this.to - this.from;
/*     */     }
/*     */     
/*     */     public void getElements(long from, Object[][] a, long offset, long length) {
/* 491 */       ensureIndex(from);
/* 492 */       if (from + length > size64())
/* 493 */         throw new IndexOutOfBoundsException("End index (" + from + length + ") is greater than list size (" + 
/* 494 */             size64() + ")"); 
/* 495 */       this.l.getElements(this.from + from, a, offset, length);
/*     */     }
/*     */     
/*     */     public void removeElements(long from, long to) {
/* 499 */       ensureIndex(from);
/* 500 */       ensureIndex(to);
/* 501 */       this.l.removeElements(this.from + from, this.from + to);
/* 502 */       this.to -= to - from;
/* 503 */       assert assertRange();
/*     */     }
/*     */     
/*     */     public void addElements(long index, K[][] a, long offset, long length) {
/* 507 */       ensureIndex(index);
/* 508 */       this.l.addElements(this.from + index, a, offset, length);
/* 509 */       this.to += length;
/* 510 */       assert assertRange();
/*     */     }
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator(final long index) {
/* 514 */       ensureIndex(index);
/* 515 */       return new ObjectBigListIterator<K>() {
/* 516 */           long pos = index; long last = -1L;
/*     */           
/*     */           public boolean hasNext() {
/* 519 */             return (this.pos < AbstractReferenceBigList.ReferenceSubList.this.size64());
/*     */           }
/*     */           
/*     */           public boolean hasPrevious() {
/* 523 */             return (this.pos > 0L);
/*     */           }
/*     */           
/*     */           public K next() {
/* 527 */             if (!hasNext())
/* 528 */               throw new NoSuchElementException(); 
/* 529 */             return (K)AbstractReferenceBigList.ReferenceSubList.this.l.get(AbstractReferenceBigList.ReferenceSubList.this.from + (this.last = this.pos++));
/*     */           }
/*     */           
/*     */           public K previous() {
/* 533 */             if (!hasPrevious())
/* 534 */               throw new NoSuchElementException(); 
/* 535 */             return (K)AbstractReferenceBigList.ReferenceSubList.this.l.get(AbstractReferenceBigList.ReferenceSubList.this.from + (this.last = --this.pos));
/*     */           }
/*     */           
/*     */           public long nextIndex() {
/* 539 */             return this.pos;
/*     */           }
/*     */           
/*     */           public long previousIndex() {
/* 543 */             return this.pos - 1L;
/*     */           }
/*     */           
/*     */           public void add(K k) {
/* 547 */             if (this.last == -1L)
/* 548 */               throw new IllegalStateException(); 
/* 549 */             AbstractReferenceBigList.ReferenceSubList.this.add(this.pos++, k);
/* 550 */             this.last = -1L;
/* 551 */             if (!$assertionsDisabled && !AbstractReferenceBigList.ReferenceSubList.this.assertRange()) throw new AssertionError(); 
/*     */           }
/*     */           
/*     */           public void set(K k) {
/* 555 */             if (this.last == -1L)
/* 556 */               throw new IllegalStateException(); 
/* 557 */             AbstractReferenceBigList.ReferenceSubList.this.set(this.last, k);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 561 */             if (this.last == -1L)
/* 562 */               throw new IllegalStateException(); 
/* 563 */             AbstractReferenceBigList.ReferenceSubList.this.remove(this.last);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 568 */             if (this.last < this.pos)
/* 569 */               this.pos--; 
/* 570 */             this.last = -1L;
/* 571 */             assert AbstractReferenceBigList.ReferenceSubList.this.assertRange();
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public ReferenceBigList<K> subList(long from, long to) {
/* 577 */       ensureIndex(from);
/* 578 */       ensureIndex(to);
/* 579 */       if (from > to)
/* 580 */         throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 581 */       return new ReferenceSubList(this, from, to);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\AbstractReferenceBigList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */