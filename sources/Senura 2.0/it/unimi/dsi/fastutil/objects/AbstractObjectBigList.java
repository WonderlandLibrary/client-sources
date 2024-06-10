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
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractObjectBigList<K>
/*     */   extends AbstractObjectCollection<K>
/*     */   implements ObjectBigList<K>, Stack<K>
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
/*     */     //   0	54	0	this	Lit/unimi/dsi/fastutil/objects/AbstractObjectBigList;
/*     */     //   0	54	1	index	J
/*     */     //   0	54	3	c	Ljava/util/Collection;
/*     */     //   13	41	4	i	Ljava/util/Iterator;
/*     */     //   22	32	5	retVal	Z
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	54	0	this	Lit/unimi/dsi/fastutil/objects/AbstractObjectBigList<TK;>;
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
/* 168 */           return (this.pos < AbstractObjectBigList.this.size64());
/*     */         }
/*     */         
/*     */         public boolean hasPrevious() {
/* 172 */           return (this.pos > 0L);
/*     */         }
/*     */         
/*     */         public K next() {
/* 176 */           if (!hasNext())
/* 177 */             throw new NoSuchElementException(); 
/* 178 */           return (K)AbstractObjectBigList.this.get(this.last = this.pos++);
/*     */         }
/*     */         
/*     */         public K previous() {
/* 182 */           if (!hasPrevious())
/* 183 */             throw new NoSuchElementException(); 
/* 184 */           return (K)AbstractObjectBigList.this.get(this.last = --this.pos);
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
/* 196 */           AbstractObjectBigList.this.add(this.pos++, k);
/* 197 */           this.last = -1L;
/*     */         }
/*     */         
/*     */         public void set(K k) {
/* 201 */           if (this.last == -1L)
/* 202 */             throw new IllegalStateException(); 
/* 203 */           AbstractObjectBigList.this.set(this.last, k);
/*     */         }
/*     */         
/*     */         public void remove() {
/* 207 */           if (this.last == -1L)
/* 208 */             throw new IllegalStateException(); 
/* 209 */           AbstractObjectBigList.this.remove(this.last);
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
/* 237 */       if (Objects.equals(k, e))
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
/* 248 */       if (Objects.equals(k, e))
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
/*     */   public ObjectBigList<K> subList(long from, long to) {
/* 265 */     ensureIndex(from);
/* 266 */     ensureIndex(to);
/* 267 */     if (from > to)
/* 268 */       throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 269 */     return new ObjectSubList<>(this, from, to);
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
/*     */     //   0	49	0	this	Lit/unimi/dsi/fastutil/objects/AbstractObjectBigList;
/*     */     //   0	49	1	index	J
/*     */     //   0	49	3	a	[[Ljava/lang/Object;
/*     */     //   0	49	4	offset	J
/*     */     //   0	49	6	length	J
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	49	0	this	Lit/unimi/dsi/fastutil/objects/AbstractObjectBigList<TK;>;
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
/*     */     //   0	108	0	this	Lit/unimi/dsi/fastutil/objects/AbstractObjectBigList;
/*     */     //   0	108	1	from	J
/*     */     //   0	108	3	a	[[Ljava/lang/Object;
/*     */     //   0	108	4	offset	J
/*     */     //   0	108	6	length	J
/*     */     //   7	101	8	i	Lit/unimi/dsi/fastutil/objects/ObjectBigListIterator;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	108	0	this	Lit/unimi/dsi/fastutil/objects/AbstractObjectBigList<TK;>;
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
/*     */   private boolean valEquals(Object a, Object b) {
/* 355 */     return (a == null) ? ((b == null)) : a.equals(b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 365 */     ObjectIterator<K> i = iterator();
/* 366 */     int h = 1;
/* 367 */     long s = size64();
/* 368 */     while (s-- != 0L) {
/* 369 */       K k = i.next();
/* 370 */       h = 31 * h + ((k == null) ? 0 : k.hashCode());
/*     */     } 
/* 372 */     return h;
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/* 376 */     if (o == this)
/* 377 */       return true; 
/* 378 */     if (!(o instanceof BigList))
/* 379 */       return false; 
/* 380 */     BigList<?> l = (BigList)o;
/* 381 */     long s = size64();
/* 382 */     if (s != l.size64())
/* 383 */       return false; 
/* 384 */     BigListIterator<?> i1 = listIterator(), i2 = l.listIterator();
/* 385 */     while (s-- != 0L) {
/* 386 */       if (!valEquals(i1.next(), i2.next()))
/* 387 */         return false; 
/* 388 */     }  return true;
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
/*     */   public int compareTo(BigList<? extends K> l) {
/* 406 */     if (l == this)
/* 407 */       return 0; 
/* 408 */     if (l instanceof ObjectBigList) {
/* 409 */       ObjectBigListIterator<K> objectBigListIterator1 = listIterator(), objectBigListIterator2 = ((ObjectBigList)l).listIterator();
/*     */ 
/*     */       
/* 412 */       while (objectBigListIterator1.hasNext() && objectBigListIterator2.hasNext()) {
/* 413 */         K e1 = objectBigListIterator1.next();
/* 414 */         K e2 = objectBigListIterator2.next(); int r;
/* 415 */         if ((r = ((Comparable<K>)e1).compareTo(e2)) != 0)
/* 416 */           return r; 
/*     */       } 
/* 418 */       return objectBigListIterator2.hasNext() ? -1 : (objectBigListIterator1.hasNext() ? 1 : 0);
/*     */     } 
/* 420 */     BigListIterator<? extends K> i1 = listIterator(), i2 = l.listIterator();
/*     */     
/* 422 */     while (i1.hasNext() && i2.hasNext()) {
/* 423 */       int r; if ((r = ((Comparable<Object>)i1.next()).compareTo(i2.next())) != 0)
/* 424 */         return r; 
/*     */     } 
/* 426 */     return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
/*     */   }
/*     */   
/*     */   public void push(K o) {
/* 430 */     add(o);
/*     */   }
/*     */   
/*     */   public K pop() {
/* 434 */     if (isEmpty())
/* 435 */       throw new NoSuchElementException(); 
/* 436 */     return remove(size64() - 1L);
/*     */   }
/*     */   
/*     */   public K top() {
/* 440 */     if (isEmpty())
/* 441 */       throw new NoSuchElementException(); 
/* 442 */     return (K)get(size64() - 1L);
/*     */   }
/*     */   
/*     */   public K peek(int i) {
/* 446 */     return (K)get(size64() - 1L - i);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 450 */     StringBuilder s = new StringBuilder();
/* 451 */     ObjectIterator<K> i = iterator();
/* 452 */     long n = size64();
/*     */     
/* 454 */     boolean first = true;
/* 455 */     s.append("[");
/* 456 */     while (n-- != 0L) {
/* 457 */       if (first) {
/* 458 */         first = false;
/*     */       } else {
/* 460 */         s.append(", ");
/* 461 */       }  K k = i.next();
/* 462 */       if (this == k) {
/* 463 */         s.append("(this big list)"); continue;
/*     */       } 
/* 465 */       s.append(String.valueOf(k));
/*     */     } 
/* 467 */     s.append("]");
/* 468 */     return s.toString();
/*     */   }
/*     */   
/*     */   public static class ObjectSubList<K>
/*     */     extends AbstractObjectBigList<K>
/*     */     implements Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ObjectBigList<K> l;
/*     */     protected final long from;
/*     */     protected long to;
/*     */     
/*     */     public ObjectSubList(ObjectBigList<K> l, long from, long to) {
/* 480 */       this.l = l;
/* 481 */       this.from = from;
/* 482 */       this.to = to;
/*     */     }
/*     */     private boolean assertRange() {
/* 485 */       assert this.from <= this.l.size64();
/* 486 */       assert this.to <= this.l.size64();
/* 487 */       assert this.to >= this.from;
/* 488 */       return true;
/*     */     }
/*     */     
/*     */     public boolean add(K k) {
/* 492 */       this.l.add(this.to, k);
/* 493 */       this.to++;
/* 494 */       assert assertRange();
/* 495 */       return true;
/*     */     }
/*     */     
/*     */     public void add(long index, K k) {
/* 499 */       ensureIndex(index);
/* 500 */       this.l.add(this.from + index, k);
/* 501 */       this.to++;
/* 502 */       assert assertRange();
/*     */     }
/*     */     
/*     */     public boolean addAll(long index, Collection<? extends K> c) {
/* 506 */       ensureIndex(index);
/* 507 */       this.to += c.size();
/* 508 */       return this.l.addAll(this.from + index, c);
/*     */     }
/*     */     
/*     */     public K get(long index) {
/* 512 */       ensureRestrictedIndex(index);
/* 513 */       return (K)this.l.get(this.from + index);
/*     */     }
/*     */     
/*     */     public K remove(long index) {
/* 517 */       ensureRestrictedIndex(index);
/* 518 */       this.to--;
/* 519 */       return (K)this.l.remove(this.from + index);
/*     */     }
/*     */     
/*     */     public K set(long index, K k) {
/* 523 */       ensureRestrictedIndex(index);
/* 524 */       return (K)this.l.set(this.from + index, k);
/*     */     }
/*     */     
/*     */     public long size64() {
/* 528 */       return this.to - this.from;
/*     */     }
/*     */     
/*     */     public void getElements(long from, Object[][] a, long offset, long length) {
/* 532 */       ensureIndex(from);
/* 533 */       if (from + length > size64())
/* 534 */         throw new IndexOutOfBoundsException("End index (" + from + length + ") is greater than list size (" + 
/* 535 */             size64() + ")"); 
/* 536 */       this.l.getElements(this.from + from, a, offset, length);
/*     */     }
/*     */     
/*     */     public void removeElements(long from, long to) {
/* 540 */       ensureIndex(from);
/* 541 */       ensureIndex(to);
/* 542 */       this.l.removeElements(this.from + from, this.from + to);
/* 543 */       this.to -= to - from;
/* 544 */       assert assertRange();
/*     */     }
/*     */     
/*     */     public void addElements(long index, K[][] a, long offset, long length) {
/* 548 */       ensureIndex(index);
/* 549 */       this.l.addElements(this.from + index, a, offset, length);
/* 550 */       this.to += length;
/* 551 */       assert assertRange();
/*     */     }
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator(final long index) {
/* 555 */       ensureIndex(index);
/* 556 */       return new ObjectBigListIterator<K>() {
/* 557 */           long pos = index; long last = -1L;
/*     */           
/*     */           public boolean hasNext() {
/* 560 */             return (this.pos < AbstractObjectBigList.ObjectSubList.this.size64());
/*     */           }
/*     */           
/*     */           public boolean hasPrevious() {
/* 564 */             return (this.pos > 0L);
/*     */           }
/*     */           
/*     */           public K next() {
/* 568 */             if (!hasNext())
/* 569 */               throw new NoSuchElementException(); 
/* 570 */             return (K)AbstractObjectBigList.ObjectSubList.this.l.get(AbstractObjectBigList.ObjectSubList.this.from + (this.last = this.pos++));
/*     */           }
/*     */           
/*     */           public K previous() {
/* 574 */             if (!hasPrevious())
/* 575 */               throw new NoSuchElementException(); 
/* 576 */             return (K)AbstractObjectBigList.ObjectSubList.this.l.get(AbstractObjectBigList.ObjectSubList.this.from + (this.last = --this.pos));
/*     */           }
/*     */           
/*     */           public long nextIndex() {
/* 580 */             return this.pos;
/*     */           }
/*     */           
/*     */           public long previousIndex() {
/* 584 */             return this.pos - 1L;
/*     */           }
/*     */           
/*     */           public void add(K k) {
/* 588 */             if (this.last == -1L)
/* 589 */               throw new IllegalStateException(); 
/* 590 */             AbstractObjectBigList.ObjectSubList.this.add(this.pos++, k);
/* 591 */             this.last = -1L;
/* 592 */             if (!$assertionsDisabled && !AbstractObjectBigList.ObjectSubList.this.assertRange()) throw new AssertionError(); 
/*     */           }
/*     */           
/*     */           public void set(K k) {
/* 596 */             if (this.last == -1L)
/* 597 */               throw new IllegalStateException(); 
/* 598 */             AbstractObjectBigList.ObjectSubList.this.set(this.last, k);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 602 */             if (this.last == -1L)
/* 603 */               throw new IllegalStateException(); 
/* 604 */             AbstractObjectBigList.ObjectSubList.this.remove(this.last);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 609 */             if (this.last < this.pos)
/* 610 */               this.pos--; 
/* 611 */             this.last = -1L;
/* 612 */             assert AbstractObjectBigList.ObjectSubList.this.assertRange();
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public ObjectBigList<K> subList(long from, long to) {
/* 618 */       ensureIndex(from);
/* 619 */       ensureIndex(to);
/* 620 */       if (from > to)
/* 621 */         throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 622 */       return new ObjectSubList(this, from, to);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\AbstractObjectBigList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */