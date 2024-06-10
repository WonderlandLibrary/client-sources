/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
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
/*     */ public abstract class AbstractShortList
/*     */   extends AbstractShortCollection
/*     */   implements ShortList, ShortStack
/*     */ {
/*     */   protected void ensureIndex(int index) {
/*  43 */     if (index < 0)
/*  44 */       throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/*  45 */     if (index > size()) {
/*  46 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than list size (" + size() + ")");
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
/*  57 */     if (index < 0)
/*  58 */       throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/*  59 */     if (index >= size()) {
/*  60 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + 
/*  61 */           size() + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(int index, short k) {
/*  71 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(short k) {
/*  82 */     add(size(), k);
/*  83 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short removeShort(int i) {
/*  93 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short set(int index, short k) {
/* 103 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(int index, Collection<? extends Short> c) {
/* 111 */     ensureIndex(index);
/* 112 */     Iterator<? extends Short> i = c.iterator();
/* 113 */     boolean retVal = i.hasNext();
/* 114 */     while (i.hasNext())
/* 115 */       add(index++, ((Short)i.next()).shortValue()); 
/* 116 */     return retVal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends Short> c) {
/* 127 */     return addAll(size(), c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortListIterator iterator() {
/* 137 */     return listIterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortListIterator listIterator() {
/* 147 */     return listIterator(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortListIterator listIterator(final int index) {
/* 156 */     ensureIndex(index);
/* 157 */     return new ShortListIterator() {
/* 158 */         int pos = index; int last = -1;
/*     */         
/*     */         public boolean hasNext() {
/* 161 */           return (this.pos < AbstractShortList.this.size());
/*     */         }
/*     */         
/*     */         public boolean hasPrevious() {
/* 165 */           return (this.pos > 0);
/*     */         }
/*     */         
/*     */         public short nextShort() {
/* 169 */           if (!hasNext())
/* 170 */             throw new NoSuchElementException(); 
/* 171 */           return AbstractShortList.this.getShort(this.last = this.pos++);
/*     */         }
/*     */         
/*     */         public short previousShort() {
/* 175 */           if (!hasPrevious())
/* 176 */             throw new NoSuchElementException(); 
/* 177 */           return AbstractShortList.this.getShort(this.last = --this.pos);
/*     */         }
/*     */         
/*     */         public int nextIndex() {
/* 181 */           return this.pos;
/*     */         }
/*     */         
/*     */         public int previousIndex() {
/* 185 */           return this.pos - 1;
/*     */         }
/*     */         
/*     */         public void add(short k) {
/* 189 */           AbstractShortList.this.add(this.pos++, k);
/* 190 */           this.last = -1;
/*     */         }
/*     */         
/*     */         public void set(short k) {
/* 194 */           if (this.last == -1)
/* 195 */             throw new IllegalStateException(); 
/* 196 */           AbstractShortList.this.set(this.last, k);
/*     */         }
/*     */         
/*     */         public void remove() {
/* 200 */           if (this.last == -1)
/* 201 */             throw new IllegalStateException(); 
/* 202 */           AbstractShortList.this.removeShort(this.last);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 207 */           if (this.last < this.pos)
/* 208 */             this.pos--; 
/* 209 */           this.last = -1;
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
/*     */   public boolean contains(short k) {
/* 222 */     return (indexOf(k) >= 0);
/*     */   }
/*     */   
/*     */   public int indexOf(short k) {
/* 226 */     ShortListIterator i = listIterator();
/*     */     
/* 228 */     while (i.hasNext()) {
/* 229 */       short e = i.nextShort();
/* 230 */       if (k == e)
/* 231 */         return i.previousIndex(); 
/*     */     } 
/* 233 */     return -1;
/*     */   }
/*     */   
/*     */   public int lastIndexOf(short k) {
/* 237 */     ShortListIterator i = listIterator(size());
/*     */     
/* 239 */     while (i.hasPrevious()) {
/* 240 */       short e = i.previousShort();
/* 241 */       if (k == e)
/* 242 */         return i.nextIndex(); 
/*     */     } 
/* 244 */     return -1;
/*     */   }
/*     */   
/*     */   public void size(int size) {
/* 248 */     int i = size();
/* 249 */     if (size > i) {
/* 250 */       while (i++ < size)
/* 251 */         add((short)0); 
/*     */     } else {
/* 253 */       while (i-- != size)
/* 254 */         removeShort(i); 
/*     */     } 
/*     */   }
/*     */   public ShortList subList(int from, int to) {
/* 258 */     ensureIndex(from);
/* 259 */     ensureIndex(to);
/* 260 */     if (from > to)
/* 261 */       throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 262 */     return new ShortSubList(this, from, to);
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
/* 273 */     ensureIndex(to);
/* 274 */     ShortListIterator i = listIterator(from);
/* 275 */     int n = to - from;
/* 276 */     if (n < 0)
/* 277 */       throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 278 */     while (n-- != 0) {
/* 279 */       i.nextShort();
/* 280 */       i.remove();
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
/*     */   public void addElements(int index, short[] a, int offset, int length) {
/* 292 */     ensureIndex(index);
/* 293 */     if (offset < 0)
/* 294 */       throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative"); 
/* 295 */     if (offset + length > a.length) {
/* 296 */       throw new ArrayIndexOutOfBoundsException("End index (" + (offset + length) + ") is greater than array length (" + a.length + ")");
/*     */     }
/* 298 */     while (length-- != 0) {
/* 299 */       add(index++, a[offset++]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addElements(int index, short[] a) {
/* 309 */     addElements(index, a, 0, a.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getElements(int from, short[] a, int offset, int length) {
/* 320 */     ShortListIterator i = listIterator(from);
/* 321 */     if (offset < 0)
/* 322 */       throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative"); 
/* 323 */     if (offset + length > a.length) {
/* 324 */       throw new ArrayIndexOutOfBoundsException("End index (" + (offset + length) + ") is greater than array length (" + a.length + ")");
/*     */     }
/* 326 */     if (from + length > size())
/* 327 */       throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + 
/* 328 */           size() + ")"); 
/* 329 */     while (length-- != 0) {
/* 330 */       a[offset++] = i.nextShort();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 339 */     removeElements(0, size());
/*     */   }
/*     */   private boolean valEquals(Object a, Object b) {
/* 342 */     return (a == null) ? ((b == null)) : a.equals(b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 352 */     ShortIterator i = iterator();
/* 353 */     int h = 1, s = size();
/* 354 */     while (s-- != 0) {
/* 355 */       short k = i.nextShort();
/* 356 */       h = 31 * h + k;
/*     */     } 
/* 358 */     return h;
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/* 362 */     if (o == this)
/* 363 */       return true; 
/* 364 */     if (!(o instanceof List))
/* 365 */       return false; 
/* 366 */     List<?> l = (List)o;
/* 367 */     int s = size();
/* 368 */     if (s != l.size())
/* 369 */       return false; 
/* 370 */     if (l instanceof ShortList) {
/* 371 */       ShortListIterator shortListIterator1 = listIterator(), shortListIterator2 = ((ShortList)l).listIterator();
/* 372 */       while (s-- != 0) {
/* 373 */         if (shortListIterator1.nextShort() != shortListIterator2.nextShort())
/* 374 */           return false; 
/* 375 */       }  return true;
/*     */     } 
/* 377 */     ListIterator<?> i1 = listIterator(), i2 = l.listIterator();
/* 378 */     while (s-- != 0) {
/* 379 */       if (!valEquals(i1.next(), i2.next()))
/* 380 */         return false; 
/* 381 */     }  return true;
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
/*     */   public int compareTo(List<? extends Short> l) {
/* 399 */     if (l == this)
/* 400 */       return 0; 
/* 401 */     if (l instanceof ShortList) {
/* 402 */       ShortListIterator shortListIterator1 = listIterator(), shortListIterator2 = ((ShortList)l).listIterator();
/*     */ 
/*     */       
/* 405 */       while (shortListIterator1.hasNext() && shortListIterator2.hasNext()) {
/* 406 */         short e1 = shortListIterator1.nextShort();
/* 407 */         short e2 = shortListIterator2.nextShort(); int r;
/* 408 */         if ((r = Short.compare(e1, e2)) != 0)
/* 409 */           return r; 
/*     */       } 
/* 411 */       return shortListIterator2.hasNext() ? -1 : (shortListIterator1.hasNext() ? 1 : 0);
/*     */     } 
/* 413 */     ListIterator<? extends Short> i1 = listIterator(), i2 = l.listIterator();
/*     */     
/* 415 */     while (i1.hasNext() && i2.hasNext()) {
/* 416 */       int r; if ((r = ((Comparable)i1.next()).compareTo(i2.next())) != 0)
/* 417 */         return r; 
/*     */     } 
/* 419 */     return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
/*     */   }
/*     */   
/*     */   public void push(short o) {
/* 423 */     add(o);
/*     */   }
/*     */   
/*     */   public short popShort() {
/* 427 */     if (isEmpty())
/* 428 */       throw new NoSuchElementException(); 
/* 429 */     return removeShort(size() - 1);
/*     */   }
/*     */   
/*     */   public short topShort() {
/* 433 */     if (isEmpty())
/* 434 */       throw new NoSuchElementException(); 
/* 435 */     return getShort(size() - 1);
/*     */   }
/*     */   
/*     */   public short peekShort(int i) {
/* 439 */     return getShort(size() - 1 - i);
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
/*     */   public boolean rem(short k) {
/* 451 */     int index = indexOf(k);
/* 452 */     if (index == -1)
/* 453 */       return false; 
/* 454 */     removeShort(index);
/* 455 */     return true;
/*     */   }
/*     */   
/*     */   public boolean addAll(int index, ShortCollection c) {
/* 459 */     ensureIndex(index);
/* 460 */     ShortIterator i = c.iterator();
/* 461 */     boolean retVal = i.hasNext();
/* 462 */     while (i.hasNext())
/* 463 */       add(index++, i.nextShort()); 
/* 464 */     return retVal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(int index, ShortList l) {
/* 475 */     return addAll(index, l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(ShortCollection c) {
/* 486 */     return addAll(size(), c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(ShortList l) {
/* 497 */     return addAll(size(), l);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 501 */     StringBuilder s = new StringBuilder();
/* 502 */     ShortIterator i = iterator();
/* 503 */     int n = size();
/*     */     
/* 505 */     boolean first = true;
/* 506 */     s.append("[");
/* 507 */     while (n-- != 0) {
/* 508 */       if (first) {
/* 509 */         first = false;
/*     */       } else {
/* 511 */         s.append(", ");
/* 512 */       }  short k = i.nextShort();
/* 513 */       s.append(String.valueOf(k));
/*     */     } 
/* 515 */     s.append("]");
/* 516 */     return s.toString();
/*     */   }
/*     */   
/*     */   public static class ShortSubList
/*     */     extends AbstractShortList
/*     */     implements Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ShortList l;
/*     */     protected final int from;
/*     */     protected int to;
/*     */     
/*     */     public ShortSubList(ShortList l, int from, int to) {
/* 528 */       this.l = l;
/* 529 */       this.from = from;
/* 530 */       this.to = to;
/*     */     }
/*     */     private boolean assertRange() {
/* 533 */       assert this.from <= this.l.size();
/* 534 */       assert this.to <= this.l.size();
/* 535 */       assert this.to >= this.from;
/* 536 */       return true;
/*     */     }
/*     */     
/*     */     public boolean add(short k) {
/* 540 */       this.l.add(this.to, k);
/* 541 */       this.to++;
/* 542 */       assert assertRange();
/* 543 */       return true;
/*     */     }
/*     */     
/*     */     public void add(int index, short k) {
/* 547 */       ensureIndex(index);
/* 548 */       this.l.add(this.from + index, k);
/* 549 */       this.to++;
/* 550 */       assert assertRange();
/*     */     }
/*     */     
/*     */     public boolean addAll(int index, Collection<? extends Short> c) {
/* 554 */       ensureIndex(index);
/* 555 */       this.to += c.size();
/* 556 */       return this.l.addAll(this.from + index, c);
/*     */     }
/*     */     
/*     */     public short getShort(int index) {
/* 560 */       ensureRestrictedIndex(index);
/* 561 */       return this.l.getShort(this.from + index);
/*     */     }
/*     */     
/*     */     public short removeShort(int index) {
/* 565 */       ensureRestrictedIndex(index);
/* 566 */       this.to--;
/* 567 */       return this.l.removeShort(this.from + index);
/*     */     }
/*     */     
/*     */     public short set(int index, short k) {
/* 571 */       ensureRestrictedIndex(index);
/* 572 */       return this.l.set(this.from + index, k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 576 */       return this.to - this.from;
/*     */     }
/*     */     
/*     */     public void getElements(int from, short[] a, int offset, int length) {
/* 580 */       ensureIndex(from);
/* 581 */       if (from + length > size())
/* 582 */         throw new IndexOutOfBoundsException("End index (" + from + length + ") is greater than list size (" + 
/* 583 */             size() + ")"); 
/* 584 */       this.l.getElements(this.from + from, a, offset, length);
/*     */     }
/*     */     
/*     */     public void removeElements(int from, int to) {
/* 588 */       ensureIndex(from);
/* 589 */       ensureIndex(to);
/* 590 */       this.l.removeElements(this.from + from, this.from + to);
/* 591 */       this.to -= to - from;
/* 592 */       assert assertRange();
/*     */     }
/*     */     
/*     */     public void addElements(int index, short[] a, int offset, int length) {
/* 596 */       ensureIndex(index);
/* 597 */       this.l.addElements(this.from + index, a, offset, length);
/* 598 */       this.to += length;
/* 599 */       assert assertRange();
/*     */     }
/*     */     
/*     */     public ShortListIterator listIterator(final int index) {
/* 603 */       ensureIndex(index);
/* 604 */       return new ShortListIterator() {
/* 605 */           int pos = index; int last = -1;
/*     */           
/*     */           public boolean hasNext() {
/* 608 */             return (this.pos < AbstractShortList.ShortSubList.this.size());
/*     */           }
/*     */           
/*     */           public boolean hasPrevious() {
/* 612 */             return (this.pos > 0);
/*     */           }
/*     */           
/*     */           public short nextShort() {
/* 616 */             if (!hasNext())
/* 617 */               throw new NoSuchElementException(); 
/* 618 */             return AbstractShortList.ShortSubList.this.l.getShort(AbstractShortList.ShortSubList.this.from + (this.last = this.pos++));
/*     */           }
/*     */           
/*     */           public short previousShort() {
/* 622 */             if (!hasPrevious())
/* 623 */               throw new NoSuchElementException(); 
/* 624 */             return AbstractShortList.ShortSubList.this.l.getShort(AbstractShortList.ShortSubList.this.from + (this.last = --this.pos));
/*     */           }
/*     */           
/*     */           public int nextIndex() {
/* 628 */             return this.pos;
/*     */           }
/*     */           
/*     */           public int previousIndex() {
/* 632 */             return this.pos - 1;
/*     */           }
/*     */           
/*     */           public void add(short k) {
/* 636 */             if (this.last == -1)
/* 637 */               throw new IllegalStateException(); 
/* 638 */             AbstractShortList.ShortSubList.this.add(this.pos++, k);
/* 639 */             this.last = -1;
/* 640 */             assert AbstractShortList.ShortSubList.this.assertRange();
/*     */           }
/*     */           
/*     */           public void set(short k) {
/* 644 */             if (this.last == -1)
/* 645 */               throw new IllegalStateException(); 
/* 646 */             AbstractShortList.ShortSubList.this.set(this.last, k);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 650 */             if (this.last == -1)
/* 651 */               throw new IllegalStateException(); 
/* 652 */             AbstractShortList.ShortSubList.this.removeShort(this.last);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 657 */             if (this.last < this.pos)
/* 658 */               this.pos--; 
/* 659 */             this.last = -1;
/* 660 */             assert AbstractShortList.ShortSubList.this.assertRange();
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public ShortList subList(int from, int to) {
/* 666 */       ensureIndex(from);
/* 667 */       ensureIndex(to);
/* 668 */       if (from > to)
/* 669 */         throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 670 */       return new ShortSubList(this, from, to);
/*     */     }
/*     */     
/*     */     public boolean rem(short k) {
/* 674 */       int index = indexOf(k);
/* 675 */       if (index == -1)
/* 676 */         return false; 
/* 677 */       this.to--;
/* 678 */       this.l.removeShort(this.from + index);
/* 679 */       assert assertRange();
/* 680 */       return true;
/*     */     }
/*     */     
/*     */     public boolean addAll(int index, ShortCollection c) {
/* 684 */       ensureIndex(index);
/* 685 */       return super.addAll(index, c);
/*     */     }
/*     */     
/*     */     public boolean addAll(int index, ShortList l) {
/* 689 */       ensureIndex(index);
/* 690 */       return super.addAll(index, l);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\AbstractShortList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */