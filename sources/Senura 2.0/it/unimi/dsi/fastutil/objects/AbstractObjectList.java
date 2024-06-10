/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Stack;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
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
/*     */ public abstract class AbstractObjectList<K>
/*     */   extends AbstractObjectCollection<K>
/*     */   implements ObjectList<K>, Stack<K>
/*     */ {
/*     */   protected void ensureIndex(int index) {
/*  44 */     if (index < 0)
/*  45 */       throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/*  46 */     if (index > size()) {
/*  47 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than list size (" + size() + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void ensureRestrictedIndex(int index) {
/*  58 */     if (index < 0)
/*  59 */       throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/*  60 */     if (index >= size()) {
/*  61 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + 
/*  62 */           size() + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(int index, K k) {
/*  72 */     throw new UnsupportedOperationException();
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
/*  83 */     add(size(), k);
/*  84 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public K remove(int i) {
/*  94 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public K set(int index, K k) {
/* 104 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(int index, Collection<? extends K> c) {
/* 112 */     ensureIndex(index);
/* 113 */     Iterator<? extends K> i = c.iterator();
/* 114 */     boolean retVal = i.hasNext();
/* 115 */     while (i.hasNext())
/* 116 */       add(index++, i.next()); 
/* 117 */     return retVal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends K> c) {
/* 128 */     return addAll(size(), c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectListIterator<K> iterator() {
/* 138 */     return listIterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectListIterator<K> listIterator() {
/* 148 */     return listIterator(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectListIterator<K> listIterator(final int index) {
/* 157 */     ensureIndex(index);
/* 158 */     return new ObjectListIterator<K>() {
/* 159 */         int pos = index; int last = -1;
/*     */         
/*     */         public boolean hasNext() {
/* 162 */           return (this.pos < AbstractObjectList.this.size());
/*     */         }
/*     */         
/*     */         public boolean hasPrevious() {
/* 166 */           return (this.pos > 0);
/*     */         }
/*     */         
/*     */         public K next() {
/* 170 */           if (!hasNext())
/* 171 */             throw new NoSuchElementException(); 
/* 172 */           return AbstractObjectList.this.get(this.last = this.pos++);
/*     */         }
/*     */         
/*     */         public K previous() {
/* 176 */           if (!hasPrevious())
/* 177 */             throw new NoSuchElementException(); 
/* 178 */           return AbstractObjectList.this.get(this.last = --this.pos);
/*     */         }
/*     */         
/*     */         public int nextIndex() {
/* 182 */           return this.pos;
/*     */         }
/*     */         
/*     */         public int previousIndex() {
/* 186 */           return this.pos - 1;
/*     */         }
/*     */         
/*     */         public void add(K k) {
/* 190 */           AbstractObjectList.this.add(this.pos++, k);
/* 191 */           this.last = -1;
/*     */         }
/*     */         
/*     */         public void set(K k) {
/* 195 */           if (this.last == -1)
/* 196 */             throw new IllegalStateException(); 
/* 197 */           AbstractObjectList.this.set(this.last, k);
/*     */         }
/*     */         
/*     */         public void remove() {
/* 201 */           if (this.last == -1)
/* 202 */             throw new IllegalStateException(); 
/* 203 */           AbstractObjectList.this.remove(this.last);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 208 */           if (this.last < this.pos)
/* 209 */             this.pos--; 
/* 210 */           this.last = -1;
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
/* 223 */     return (indexOf(k) >= 0);
/*     */   }
/*     */   
/*     */   public int indexOf(Object k) {
/* 227 */     ObjectListIterator<K> i = listIterator();
/*     */     
/* 229 */     while (i.hasNext()) {
/* 230 */       K e = i.next();
/* 231 */       if (Objects.equals(k, e))
/* 232 */         return i.previousIndex(); 
/*     */     } 
/* 234 */     return -1;
/*     */   }
/*     */   
/*     */   public int lastIndexOf(Object k) {
/* 238 */     ObjectListIterator<K> i = listIterator(size());
/*     */     
/* 240 */     while (i.hasPrevious()) {
/* 241 */       K e = i.previous();
/* 242 */       if (Objects.equals(k, e))
/* 243 */         return i.nextIndex(); 
/*     */     } 
/* 245 */     return -1;
/*     */   }
/*     */   
/*     */   public void size(int size) {
/* 249 */     int i = size();
/* 250 */     if (size > i) {
/* 251 */       while (i++ < size)
/* 252 */         add((K)null); 
/*     */     } else {
/* 254 */       while (i-- != size)
/* 255 */         remove(i); 
/*     */     } 
/*     */   }
/*     */   public ObjectList<K> subList(int from, int to) {
/* 259 */     ensureIndex(from);
/* 260 */     ensureIndex(to);
/* 261 */     if (from > to)
/* 262 */       throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 263 */     return new ObjectSubList<>(this, from, to);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeElements(int from, int to) {
/* 274 */     ensureIndex(to);
/* 275 */     ObjectListIterator<K> i = listIterator(from);
/* 276 */     int n = to - from;
/* 277 */     if (n < 0)
/* 278 */       throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 279 */     while (n-- != 0) {
/* 280 */       i.next();
/* 281 */       i.remove();
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
/*     */   public void addElements(int index, K[] a, int offset, int length) {
/* 293 */     ensureIndex(index);
/* 294 */     if (offset < 0)
/* 295 */       throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative"); 
/* 296 */     if (offset + length > a.length) {
/* 297 */       throw new ArrayIndexOutOfBoundsException("End index (" + (offset + length) + ") is greater than array length (" + a.length + ")");
/*     */     }
/* 299 */     while (length-- != 0) {
/* 300 */       add(index++, a[offset++]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addElements(int index, K[] a) {
/* 310 */     addElements(index, a, 0, a.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getElements(int from, Object[] a, int offset, int length) {
/* 321 */     ObjectListIterator<K> i = listIterator(from);
/* 322 */     if (offset < 0)
/* 323 */       throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative"); 
/* 324 */     if (offset + length > a.length) {
/* 325 */       throw new ArrayIndexOutOfBoundsException("End index (" + (offset + length) + ") is greater than array length (" + a.length + ")");
/*     */     }
/* 327 */     if (from + length > size())
/* 328 */       throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + 
/* 329 */           size() + ")"); 
/* 330 */     while (length-- != 0) {
/* 331 */       a[offset++] = i.next();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 340 */     removeElements(0, size());
/*     */   }
/*     */   private boolean valEquals(Object a, Object b) {
/* 343 */     return (a == null) ? ((b == null)) : a.equals(b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 353 */     ObjectIterator<K> i = iterator();
/* 354 */     int h = 1, s = size();
/* 355 */     while (s-- != 0) {
/* 356 */       K k = i.next();
/* 357 */       h = 31 * h + ((k == null) ? 0 : k.hashCode());
/*     */     } 
/* 359 */     return h;
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/* 363 */     if (o == this)
/* 364 */       return true; 
/* 365 */     if (!(o instanceof List))
/* 366 */       return false; 
/* 367 */     List<?> l = (List)o;
/* 368 */     int s = size();
/* 369 */     if (s != l.size())
/* 370 */       return false; 
/* 371 */     ListIterator<?> i1 = listIterator(), i2 = l.listIterator();
/* 372 */     while (s-- != 0) {
/* 373 */       if (!valEquals(i1.next(), i2.next()))
/* 374 */         return false; 
/* 375 */     }  return true;
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
/*     */   public int compareTo(List<? extends K> l) {
/* 393 */     if (l == this)
/* 394 */       return 0; 
/* 395 */     if (l instanceof ObjectList) {
/* 396 */       ObjectListIterator<K> objectListIterator1 = listIterator(), objectListIterator2 = ((ObjectList)l).listIterator();
/*     */ 
/*     */       
/* 399 */       while (objectListIterator1.hasNext() && objectListIterator2.hasNext()) {
/* 400 */         K e1 = objectListIterator1.next();
/* 401 */         K e2 = objectListIterator2.next(); int r;
/* 402 */         if ((r = ((Comparable<K>)e1).compareTo(e2)) != 0)
/* 403 */           return r; 
/*     */       } 
/* 405 */       return objectListIterator2.hasNext() ? -1 : (objectListIterator1.hasNext() ? 1 : 0);
/*     */     } 
/* 407 */     ListIterator<? extends K> i1 = listIterator(), i2 = l.listIterator();
/*     */     
/* 409 */     while (i1.hasNext() && i2.hasNext()) {
/* 410 */       int r; if ((r = ((Comparable)i1.next()).compareTo(i2.next())) != 0)
/* 411 */         return r; 
/*     */     } 
/* 413 */     return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
/*     */   }
/*     */   
/*     */   public void push(K o) {
/* 417 */     add(o);
/*     */   }
/*     */   
/*     */   public K pop() {
/* 421 */     if (isEmpty())
/* 422 */       throw new NoSuchElementException(); 
/* 423 */     return remove(size() - 1);
/*     */   }
/*     */   
/*     */   public K top() {
/* 427 */     if (isEmpty())
/* 428 */       throw new NoSuchElementException(); 
/* 429 */     return get(size() - 1);
/*     */   }
/*     */   
/*     */   public K peek(int i) {
/* 433 */     return get(size() - 1 - i);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 437 */     StringBuilder s = new StringBuilder();
/* 438 */     ObjectIterator<K> i = iterator();
/* 439 */     int n = size();
/*     */     
/* 441 */     boolean first = true;
/* 442 */     s.append("[");
/* 443 */     while (n-- != 0) {
/* 444 */       if (first) {
/* 445 */         first = false;
/*     */       } else {
/* 447 */         s.append(", ");
/* 448 */       }  K k = i.next();
/* 449 */       if (this == k) {
/* 450 */         s.append("(this list)"); continue;
/*     */       } 
/* 452 */       s.append(String.valueOf(k));
/*     */     } 
/* 454 */     s.append("]");
/* 455 */     return s.toString();
/*     */   }
/*     */   
/*     */   public static class ObjectSubList<K>
/*     */     extends AbstractObjectList<K>
/*     */     implements Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ObjectList<K> l;
/*     */     protected final int from;
/*     */     protected int to;
/*     */     
/*     */     public ObjectSubList(ObjectList<K> l, int from, int to) {
/* 467 */       this.l = l;
/* 468 */       this.from = from;
/* 469 */       this.to = to;
/*     */     }
/*     */     private boolean assertRange() {
/* 472 */       assert this.from <= this.l.size();
/* 473 */       assert this.to <= this.l.size();
/* 474 */       assert this.to >= this.from;
/* 475 */       return true;
/*     */     }
/*     */     
/*     */     public boolean add(K k) {
/* 479 */       this.l.add(this.to, k);
/* 480 */       this.to++;
/* 481 */       assert assertRange();
/* 482 */       return true;
/*     */     }
/*     */     
/*     */     public void add(int index, K k) {
/* 486 */       ensureIndex(index);
/* 487 */       this.l.add(this.from + index, k);
/* 488 */       this.to++;
/* 489 */       assert assertRange();
/*     */     }
/*     */     
/*     */     public boolean addAll(int index, Collection<? extends K> c) {
/* 493 */       ensureIndex(index);
/* 494 */       this.to += c.size();
/* 495 */       return this.l.addAll(this.from + index, c);
/*     */     }
/*     */     
/*     */     public K get(int index) {
/* 499 */       ensureRestrictedIndex(index);
/* 500 */       return this.l.get(this.from + index);
/*     */     }
/*     */     
/*     */     public K remove(int index) {
/* 504 */       ensureRestrictedIndex(index);
/* 505 */       this.to--;
/* 506 */       return this.l.remove(this.from + index);
/*     */     }
/*     */     
/*     */     public K set(int index, K k) {
/* 510 */       ensureRestrictedIndex(index);
/* 511 */       return this.l.set(this.from + index, k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 515 */       return this.to - this.from;
/*     */     }
/*     */     
/*     */     public void getElements(int from, Object[] a, int offset, int length) {
/* 519 */       ensureIndex(from);
/* 520 */       if (from + length > size())
/* 521 */         throw new IndexOutOfBoundsException("End index (" + from + length + ") is greater than list size (" + 
/* 522 */             size() + ")"); 
/* 523 */       this.l.getElements(this.from + from, a, offset, length);
/*     */     }
/*     */     
/*     */     public void removeElements(int from, int to) {
/* 527 */       ensureIndex(from);
/* 528 */       ensureIndex(to);
/* 529 */       this.l.removeElements(this.from + from, this.from + to);
/* 530 */       this.to -= to - from;
/* 531 */       assert assertRange();
/*     */     }
/*     */     
/*     */     public void addElements(int index, K[] a, int offset, int length) {
/* 535 */       ensureIndex(index);
/* 536 */       this.l.addElements(this.from + index, a, offset, length);
/* 537 */       this.to += length;
/* 538 */       assert assertRange();
/*     */     }
/*     */     
/*     */     public ObjectListIterator<K> listIterator(final int index) {
/* 542 */       ensureIndex(index);
/* 543 */       return new ObjectListIterator<K>() {
/* 544 */           int pos = index; int last = -1;
/*     */           
/*     */           public boolean hasNext() {
/* 547 */             return (this.pos < AbstractObjectList.ObjectSubList.this.size());
/*     */           }
/*     */           
/*     */           public boolean hasPrevious() {
/* 551 */             return (this.pos > 0);
/*     */           }
/*     */           
/*     */           public K next() {
/* 555 */             if (!hasNext())
/* 556 */               throw new NoSuchElementException(); 
/* 557 */             return AbstractObjectList.ObjectSubList.this.l.get(AbstractObjectList.ObjectSubList.this.from + (this.last = this.pos++));
/*     */           }
/*     */           
/*     */           public K previous() {
/* 561 */             if (!hasPrevious())
/* 562 */               throw new NoSuchElementException(); 
/* 563 */             return AbstractObjectList.ObjectSubList.this.l.get(AbstractObjectList.ObjectSubList.this.from + (this.last = --this.pos));
/*     */           }
/*     */           
/*     */           public int nextIndex() {
/* 567 */             return this.pos;
/*     */           }
/*     */           
/*     */           public int previousIndex() {
/* 571 */             return this.pos - 1;
/*     */           }
/*     */           
/*     */           public void add(K k) {
/* 575 */             if (this.last == -1)
/* 576 */               throw new IllegalStateException(); 
/* 577 */             AbstractObjectList.ObjectSubList.this.add(this.pos++, k);
/* 578 */             this.last = -1;
/* 579 */             assert AbstractObjectList.ObjectSubList.this.assertRange();
/*     */           }
/*     */           
/*     */           public void set(K k) {
/* 583 */             if (this.last == -1)
/* 584 */               throw new IllegalStateException(); 
/* 585 */             AbstractObjectList.ObjectSubList.this.set(this.last, k);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 589 */             if (this.last == -1)
/* 590 */               throw new IllegalStateException(); 
/* 591 */             AbstractObjectList.ObjectSubList.this.remove(this.last);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 596 */             if (this.last < this.pos)
/* 597 */               this.pos--; 
/* 598 */             this.last = -1;
/* 599 */             assert AbstractObjectList.ObjectSubList.this.assertRange();
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public ObjectList<K> subList(int from, int to) {
/* 605 */       ensureIndex(from);
/* 606 */       ensureIndex(to);
/* 607 */       if (from > to)
/* 608 */         throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 609 */       return new ObjectSubList(this, from, to);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\AbstractObjectList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */