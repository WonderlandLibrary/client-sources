/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Stack;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractReferenceList<K>
/*     */   extends AbstractReferenceCollection<K>
/*     */   implements ReferenceList<K>, Stack<K>
/*     */ {
/*     */   protected void ensureIndex(int index) {
/*  47 */     if (index < 0)
/*  48 */       throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/*  49 */     if (index > size()) {
/*  50 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than list size (" + size() + ")");
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
/*  61 */     if (index < 0)
/*  62 */       throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/*  63 */     if (index >= size()) {
/*  64 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + 
/*  65 */           size() + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(int index, K k) {
/*  75 */     throw new UnsupportedOperationException();
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
/*  86 */     add(size(), k);
/*  87 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public K remove(int i) {
/*  97 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public K set(int index, K k) {
/* 107 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(int index, Collection<? extends K> c) {
/* 115 */     ensureIndex(index);
/* 116 */     Iterator<? extends K> i = c.iterator();
/* 117 */     boolean retVal = i.hasNext();
/* 118 */     while (i.hasNext())
/* 119 */       add(index++, i.next()); 
/* 120 */     return retVal;
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
/* 131 */     return addAll(size(), c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectListIterator<K> iterator() {
/* 141 */     return listIterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectListIterator<K> listIterator() {
/* 151 */     return listIterator(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectListIterator<K> listIterator(final int index) {
/* 160 */     ensureIndex(index);
/* 161 */     return new ObjectListIterator<K>() {
/* 162 */         int pos = index; int last = -1;
/*     */         
/*     */         public boolean hasNext() {
/* 165 */           return (this.pos < AbstractReferenceList.this.size());
/*     */         }
/*     */         
/*     */         public boolean hasPrevious() {
/* 169 */           return (this.pos > 0);
/*     */         }
/*     */         
/*     */         public K next() {
/* 173 */           if (!hasNext())
/* 174 */             throw new NoSuchElementException(); 
/* 175 */           return AbstractReferenceList.this.get(this.last = this.pos++);
/*     */         }
/*     */         
/*     */         public K previous() {
/* 179 */           if (!hasPrevious())
/* 180 */             throw new NoSuchElementException(); 
/* 181 */           return AbstractReferenceList.this.get(this.last = --this.pos);
/*     */         }
/*     */         
/*     */         public int nextIndex() {
/* 185 */           return this.pos;
/*     */         }
/*     */         
/*     */         public int previousIndex() {
/* 189 */           return this.pos - 1;
/*     */         }
/*     */         
/*     */         public void add(K k) {
/* 193 */           AbstractReferenceList.this.add(this.pos++, k);
/* 194 */           this.last = -1;
/*     */         }
/*     */         
/*     */         public void set(K k) {
/* 198 */           if (this.last == -1)
/* 199 */             throw new IllegalStateException(); 
/* 200 */           AbstractReferenceList.this.set(this.last, k);
/*     */         }
/*     */         
/*     */         public void remove() {
/* 204 */           if (this.last == -1)
/* 205 */             throw new IllegalStateException(); 
/* 206 */           AbstractReferenceList.this.remove(this.last);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 211 */           if (this.last < this.pos)
/* 212 */             this.pos--; 
/* 213 */           this.last = -1;
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
/* 226 */     return (indexOf(k) >= 0);
/*     */   }
/*     */   
/*     */   public int indexOf(Object k) {
/* 230 */     ObjectListIterator<K> i = listIterator();
/*     */     
/* 232 */     while (i.hasNext()) {
/* 233 */       K e = i.next();
/* 234 */       if (k == e)
/* 235 */         return i.previousIndex(); 
/*     */     } 
/* 237 */     return -1;
/*     */   }
/*     */   
/*     */   public int lastIndexOf(Object k) {
/* 241 */     ObjectListIterator<K> i = listIterator(size());
/*     */     
/* 243 */     while (i.hasPrevious()) {
/* 244 */       K e = i.previous();
/* 245 */       if (k == e)
/* 246 */         return i.nextIndex(); 
/*     */     } 
/* 248 */     return -1;
/*     */   }
/*     */   
/*     */   public void size(int size) {
/* 252 */     int i = size();
/* 253 */     if (size > i) {
/* 254 */       while (i++ < size)
/* 255 */         add((K)null); 
/*     */     } else {
/* 257 */       while (i-- != size)
/* 258 */         remove(i); 
/*     */     } 
/*     */   }
/*     */   public ReferenceList<K> subList(int from, int to) {
/* 262 */     ensureIndex(from);
/* 263 */     ensureIndex(to);
/* 264 */     if (from > to)
/* 265 */       throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 266 */     return new ReferenceSubList<>(this, from, to);
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
/* 277 */     ensureIndex(to);
/* 278 */     ObjectListIterator<K> i = listIterator(from);
/* 279 */     int n = to - from;
/* 280 */     if (n < 0)
/* 281 */       throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 282 */     while (n-- != 0) {
/* 283 */       i.next();
/* 284 */       i.remove();
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
/* 296 */     ensureIndex(index);
/* 297 */     if (offset < 0)
/* 298 */       throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative"); 
/* 299 */     if (offset + length > a.length) {
/* 300 */       throw new ArrayIndexOutOfBoundsException("End index (" + (offset + length) + ") is greater than array length (" + a.length + ")");
/*     */     }
/* 302 */     while (length-- != 0) {
/* 303 */       add(index++, a[offset++]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addElements(int index, K[] a) {
/* 313 */     addElements(index, a, 0, a.length);
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
/* 324 */     ObjectListIterator<K> i = listIterator(from);
/* 325 */     if (offset < 0)
/* 326 */       throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative"); 
/* 327 */     if (offset + length > a.length) {
/* 328 */       throw new ArrayIndexOutOfBoundsException("End index (" + (offset + length) + ") is greater than array length (" + a.length + ")");
/*     */     }
/* 330 */     if (from + length > size())
/* 331 */       throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + 
/* 332 */           size() + ")"); 
/* 333 */     while (length-- != 0) {
/* 334 */       a[offset++] = i.next();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 343 */     removeElements(0, size());
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
/* 357 */       h = 31 * h + System.identityHashCode(k);
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
/* 373 */       if (i1.next() != i2.next())
/* 374 */         return false; 
/* 375 */     }  return true;
/*     */   }
/*     */   
/*     */   public void push(K o) {
/* 379 */     add(o);
/*     */   }
/*     */   
/*     */   public K pop() {
/* 383 */     if (isEmpty())
/* 384 */       throw new NoSuchElementException(); 
/* 385 */     return remove(size() - 1);
/*     */   }
/*     */   
/*     */   public K top() {
/* 389 */     if (isEmpty())
/* 390 */       throw new NoSuchElementException(); 
/* 391 */     return get(size() - 1);
/*     */   }
/*     */   
/*     */   public K peek(int i) {
/* 395 */     return get(size() - 1 - i);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 399 */     StringBuilder s = new StringBuilder();
/* 400 */     ObjectIterator<K> i = iterator();
/* 401 */     int n = size();
/*     */     
/* 403 */     boolean first = true;
/* 404 */     s.append("[");
/* 405 */     while (n-- != 0) {
/* 406 */       if (first) {
/* 407 */         first = false;
/*     */       } else {
/* 409 */         s.append(", ");
/* 410 */       }  K k = i.next();
/* 411 */       if (this == k) {
/* 412 */         s.append("(this list)"); continue;
/*     */       } 
/* 414 */       s.append(String.valueOf(k));
/*     */     } 
/* 416 */     s.append("]");
/* 417 */     return s.toString();
/*     */   }
/*     */   
/*     */   public static class ReferenceSubList<K>
/*     */     extends AbstractReferenceList<K>
/*     */     implements Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ReferenceList<K> l;
/*     */     protected final int from;
/*     */     protected int to;
/*     */     
/*     */     public ReferenceSubList(ReferenceList<K> l, int from, int to) {
/* 429 */       this.l = l;
/* 430 */       this.from = from;
/* 431 */       this.to = to;
/*     */     }
/*     */     private boolean assertRange() {
/* 434 */       assert this.from <= this.l.size();
/* 435 */       assert this.to <= this.l.size();
/* 436 */       assert this.to >= this.from;
/* 437 */       return true;
/*     */     }
/*     */     
/*     */     public boolean add(K k) {
/* 441 */       this.l.add(this.to, k);
/* 442 */       this.to++;
/* 443 */       assert assertRange();
/* 444 */       return true;
/*     */     }
/*     */     
/*     */     public void add(int index, K k) {
/* 448 */       ensureIndex(index);
/* 449 */       this.l.add(this.from + index, k);
/* 450 */       this.to++;
/* 451 */       assert assertRange();
/*     */     }
/*     */     
/*     */     public boolean addAll(int index, Collection<? extends K> c) {
/* 455 */       ensureIndex(index);
/* 456 */       this.to += c.size();
/* 457 */       return this.l.addAll(this.from + index, c);
/*     */     }
/*     */     
/*     */     public K get(int index) {
/* 461 */       ensureRestrictedIndex(index);
/* 462 */       return this.l.get(this.from + index);
/*     */     }
/*     */     
/*     */     public K remove(int index) {
/* 466 */       ensureRestrictedIndex(index);
/* 467 */       this.to--;
/* 468 */       return this.l.remove(this.from + index);
/*     */     }
/*     */     
/*     */     public K set(int index, K k) {
/* 472 */       ensureRestrictedIndex(index);
/* 473 */       return this.l.set(this.from + index, k);
/*     */     }
/*     */     
/*     */     public int size() {
/* 477 */       return this.to - this.from;
/*     */     }
/*     */     
/*     */     public void getElements(int from, Object[] a, int offset, int length) {
/* 481 */       ensureIndex(from);
/* 482 */       if (from + length > size())
/* 483 */         throw new IndexOutOfBoundsException("End index (" + from + length + ") is greater than list size (" + 
/* 484 */             size() + ")"); 
/* 485 */       this.l.getElements(this.from + from, a, offset, length);
/*     */     }
/*     */     
/*     */     public void removeElements(int from, int to) {
/* 489 */       ensureIndex(from);
/* 490 */       ensureIndex(to);
/* 491 */       this.l.removeElements(this.from + from, this.from + to);
/* 492 */       this.to -= to - from;
/* 493 */       assert assertRange();
/*     */     }
/*     */     
/*     */     public void addElements(int index, K[] a, int offset, int length) {
/* 497 */       ensureIndex(index);
/* 498 */       this.l.addElements(this.from + index, a, offset, length);
/* 499 */       this.to += length;
/* 500 */       assert assertRange();
/*     */     }
/*     */     
/*     */     public ObjectListIterator<K> listIterator(final int index) {
/* 504 */       ensureIndex(index);
/* 505 */       return new ObjectListIterator<K>() {
/* 506 */           int pos = index; int last = -1;
/*     */           
/*     */           public boolean hasNext() {
/* 509 */             return (this.pos < AbstractReferenceList.ReferenceSubList.this.size());
/*     */           }
/*     */           
/*     */           public boolean hasPrevious() {
/* 513 */             return (this.pos > 0);
/*     */           }
/*     */           
/*     */           public K next() {
/* 517 */             if (!hasNext())
/* 518 */               throw new NoSuchElementException(); 
/* 519 */             return AbstractReferenceList.ReferenceSubList.this.l.get(AbstractReferenceList.ReferenceSubList.this.from + (this.last = this.pos++));
/*     */           }
/*     */           
/*     */           public K previous() {
/* 523 */             if (!hasPrevious())
/* 524 */               throw new NoSuchElementException(); 
/* 525 */             return AbstractReferenceList.ReferenceSubList.this.l.get(AbstractReferenceList.ReferenceSubList.this.from + (this.last = --this.pos));
/*     */           }
/*     */           
/*     */           public int nextIndex() {
/* 529 */             return this.pos;
/*     */           }
/*     */           
/*     */           public int previousIndex() {
/* 533 */             return this.pos - 1;
/*     */           }
/*     */           
/*     */           public void add(K k) {
/* 537 */             if (this.last == -1)
/* 538 */               throw new IllegalStateException(); 
/* 539 */             AbstractReferenceList.ReferenceSubList.this.add(this.pos++, k);
/* 540 */             this.last = -1;
/* 541 */             assert AbstractReferenceList.ReferenceSubList.this.assertRange();
/*     */           }
/*     */           
/*     */           public void set(K k) {
/* 545 */             if (this.last == -1)
/* 546 */               throw new IllegalStateException(); 
/* 547 */             AbstractReferenceList.ReferenceSubList.this.set(this.last, k);
/*     */           }
/*     */           
/*     */           public void remove() {
/* 551 */             if (this.last == -1)
/* 552 */               throw new IllegalStateException(); 
/* 553 */             AbstractReferenceList.ReferenceSubList.this.remove(this.last);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 558 */             if (this.last < this.pos)
/* 559 */               this.pos--; 
/* 560 */             this.last = -1;
/* 561 */             assert AbstractReferenceList.ReferenceSubList.this.assertRange();
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public ReferenceList<K> subList(int from, int to) {
/* 567 */       ensureIndex(from);
/* 568 */       ensureIndex(to);
/* 569 */       if (from > to)
/* 570 */         throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 571 */       return new ReferenceSubList(this, from, to);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\AbstractReferenceList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */