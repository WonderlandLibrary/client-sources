/*     */ package org.spongepowered.asm.lib.tree;
/*     */ 
/*     */ import java.util.ListIterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import org.spongepowered.asm.lib.MethodVisitor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InsnList
/*     */ {
/*     */   private int size;
/*     */   private AbstractInsnNode first;
/*     */   private AbstractInsnNode last;
/*     */   AbstractInsnNode[] cache;
/*     */   
/*     */   public int size() {
/*  70 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractInsnNode getFirst() {
/*  80 */     return this.first;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractInsnNode getLast() {
/*  90 */     return this.last;
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
/*     */   public AbstractInsnNode get(int index) {
/* 106 */     if (index < 0 || index >= this.size) {
/* 107 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 109 */     if (this.cache == null) {
/* 110 */       this.cache = toArray();
/*     */     }
/* 112 */     return this.cache[index];
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
/*     */   public boolean contains(AbstractInsnNode insn) {
/* 125 */     AbstractInsnNode i = this.first;
/* 126 */     while (i != null && i != insn) {
/* 127 */       i = i.next;
/*     */     }
/* 129 */     return (i != null);
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
/*     */   public int indexOf(AbstractInsnNode insn) {
/* 147 */     if (this.cache == null) {
/* 148 */       this.cache = toArray();
/*     */     }
/* 150 */     return insn.index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void accept(MethodVisitor mv) {
/* 160 */     AbstractInsnNode insn = this.first;
/* 161 */     while (insn != null) {
/* 162 */       insn.accept(mv);
/* 163 */       insn = insn.next;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ListIterator<AbstractInsnNode> iterator() {
/* 173 */     return iterator(0);
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
/*     */   public ListIterator<AbstractInsnNode> iterator(int index) {
/* 186 */     return new InsnListIterator(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractInsnNode[] toArray() {
/* 195 */     int i = 0;
/* 196 */     AbstractInsnNode elem = this.first;
/* 197 */     AbstractInsnNode[] insns = new AbstractInsnNode[this.size];
/* 198 */     while (elem != null) {
/* 199 */       insns[i] = elem;
/* 200 */       elem.index = i++;
/* 201 */       elem = elem.next;
/*     */     } 
/* 203 */     return insns;
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
/*     */   public void set(AbstractInsnNode location, AbstractInsnNode insn) {
/* 216 */     AbstractInsnNode next = location.next;
/* 217 */     insn.next = next;
/* 218 */     if (next != null) {
/* 219 */       next.prev = insn;
/*     */     } else {
/* 221 */       this.last = insn;
/*     */     } 
/* 223 */     AbstractInsnNode prev = location.prev;
/* 224 */     insn.prev = prev;
/* 225 */     if (prev != null) {
/* 226 */       prev.next = insn;
/*     */     } else {
/* 228 */       this.first = insn;
/*     */     } 
/* 230 */     if (this.cache != null) {
/* 231 */       int index = location.index;
/* 232 */       this.cache[index] = insn;
/* 233 */       insn.index = index;
/*     */     } else {
/* 235 */       insn.index = 0;
/*     */     } 
/* 237 */     location.index = -1;
/* 238 */     location.prev = null;
/* 239 */     location.next = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(AbstractInsnNode insn) {
/* 250 */     this.size++;
/* 251 */     if (this.last == null) {
/* 252 */       this.first = insn;
/* 253 */       this.last = insn;
/*     */     } else {
/* 255 */       this.last.next = insn;
/* 256 */       insn.prev = this.last;
/*     */     } 
/* 258 */     this.last = insn;
/* 259 */     this.cache = null;
/* 260 */     insn.index = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(InsnList insns) {
/* 271 */     if (insns.size == 0) {
/*     */       return;
/*     */     }
/* 274 */     this.size += insns.size;
/* 275 */     if (this.last == null) {
/* 276 */       this.first = insns.first;
/* 277 */       this.last = insns.last;
/*     */     } else {
/* 279 */       AbstractInsnNode elem = insns.first;
/* 280 */       this.last.next = elem;
/* 281 */       elem.prev = this.last;
/* 282 */       this.last = insns.last;
/*     */     } 
/* 284 */     this.cache = null;
/* 285 */     insns.removeAll(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void insert(AbstractInsnNode insn) {
/* 296 */     this.size++;
/* 297 */     if (this.first == null) {
/* 298 */       this.first = insn;
/* 299 */       this.last = insn;
/*     */     } else {
/* 301 */       this.first.prev = insn;
/* 302 */       insn.next = this.first;
/*     */     } 
/* 304 */     this.first = insn;
/* 305 */     this.cache = null;
/* 306 */     insn.index = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void insert(InsnList insns) {
/* 317 */     if (insns.size == 0) {
/*     */       return;
/*     */     }
/* 320 */     this.size += insns.size;
/* 321 */     if (this.first == null) {
/* 322 */       this.first = insns.first;
/* 323 */       this.last = insns.last;
/*     */     } else {
/* 325 */       AbstractInsnNode elem = insns.last;
/* 326 */       this.first.prev = elem;
/* 327 */       elem.next = this.first;
/* 328 */       this.first = insns.first;
/*     */     } 
/* 330 */     this.cache = null;
/* 331 */     insns.removeAll(false);
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
/*     */   public void insert(AbstractInsnNode location, AbstractInsnNode insn) {
/* 346 */     this.size++;
/* 347 */     AbstractInsnNode next = location.next;
/* 348 */     if (next == null) {
/* 349 */       this.last = insn;
/*     */     } else {
/* 351 */       next.prev = insn;
/*     */     } 
/* 353 */     location.next = insn;
/* 354 */     insn.next = next;
/* 355 */     insn.prev = location;
/* 356 */     this.cache = null;
/* 357 */     insn.index = 0;
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
/*     */   public void insert(AbstractInsnNode location, InsnList insns) {
/* 371 */     if (insns.size == 0) {
/*     */       return;
/*     */     }
/* 374 */     this.size += insns.size;
/* 375 */     AbstractInsnNode ifirst = insns.first;
/* 376 */     AbstractInsnNode ilast = insns.last;
/* 377 */     AbstractInsnNode next = location.next;
/* 378 */     if (next == null) {
/* 379 */       this.last = ilast;
/*     */     } else {
/* 381 */       next.prev = ilast;
/*     */     } 
/* 383 */     location.next = ifirst;
/* 384 */     ilast.next = next;
/* 385 */     ifirst.prev = location;
/* 386 */     this.cache = null;
/* 387 */     insns.removeAll(false);
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
/*     */   public void insertBefore(AbstractInsnNode location, AbstractInsnNode insn) {
/* 402 */     this.size++;
/* 403 */     AbstractInsnNode prev = location.prev;
/* 404 */     if (prev == null) {
/* 405 */       this.first = insn;
/*     */     } else {
/* 407 */       prev.next = insn;
/*     */     } 
/* 409 */     location.prev = insn;
/* 410 */     insn.next = location;
/* 411 */     insn.prev = prev;
/* 412 */     this.cache = null;
/* 413 */     insn.index = 0;
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
/*     */   public void insertBefore(AbstractInsnNode location, InsnList insns) {
/* 428 */     if (insns.size == 0) {
/*     */       return;
/*     */     }
/* 431 */     this.size += insns.size;
/* 432 */     AbstractInsnNode ifirst = insns.first;
/* 433 */     AbstractInsnNode ilast = insns.last;
/* 434 */     AbstractInsnNode prev = location.prev;
/* 435 */     if (prev == null) {
/* 436 */       this.first = ifirst;
/*     */     } else {
/* 438 */       prev.next = ifirst;
/*     */     } 
/* 440 */     location.prev = ilast;
/* 441 */     ilast.next = location;
/* 442 */     ifirst.prev = prev;
/* 443 */     this.cache = null;
/* 444 */     insns.removeAll(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(AbstractInsnNode insn) {
/* 454 */     this.size--;
/* 455 */     AbstractInsnNode next = insn.next;
/* 456 */     AbstractInsnNode prev = insn.prev;
/* 457 */     if (next == null) {
/* 458 */       if (prev == null) {
/* 459 */         this.first = null;
/* 460 */         this.last = null;
/*     */       } else {
/* 462 */         prev.next = null;
/* 463 */         this.last = prev;
/*     */       }
/*     */     
/* 466 */     } else if (prev == null) {
/* 467 */       this.first = next;
/* 468 */       next.prev = null;
/*     */     } else {
/* 470 */       prev.next = next;
/* 471 */       next.prev = prev;
/*     */     } 
/*     */     
/* 474 */     this.cache = null;
/* 475 */     insn.index = -1;
/* 476 */     insn.prev = null;
/* 477 */     insn.next = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void removeAll(boolean mark) {
/* 488 */     if (mark) {
/* 489 */       AbstractInsnNode insn = this.first;
/* 490 */       while (insn != null) {
/* 491 */         AbstractInsnNode next = insn.next;
/* 492 */         insn.index = -1;
/* 493 */         insn.prev = null;
/* 494 */         insn.next = null;
/* 495 */         insn = next;
/*     */       } 
/*     */     } 
/* 498 */     this.size = 0;
/* 499 */     this.first = null;
/* 500 */     this.last = null;
/* 501 */     this.cache = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 508 */     removeAll(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetLabels() {
/* 517 */     AbstractInsnNode insn = this.first;
/* 518 */     while (insn != null) {
/* 519 */       if (insn instanceof LabelNode) {
/* 520 */         ((LabelNode)insn).resetLabel();
/*     */       }
/* 522 */       insn = insn.next;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private final class InsnListIterator
/*     */     implements ListIterator
/*     */   {
/*     */     AbstractInsnNode next;
/*     */     
/*     */     AbstractInsnNode prev;
/*     */     
/*     */     AbstractInsnNode remove;
/*     */     
/*     */     InsnListIterator(int index) {
/* 537 */       if (index == InsnList.this.size()) {
/* 538 */         this.next = null;
/* 539 */         this.prev = InsnList.this.getLast();
/*     */       } else {
/* 541 */         this.next = InsnList.this.get(index);
/* 542 */         this.prev = this.next.prev;
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 547 */       return (this.next != null);
/*     */     }
/*     */     
/*     */     public Object next() {
/* 551 */       if (this.next == null) {
/* 552 */         throw new NoSuchElementException();
/*     */       }
/* 554 */       AbstractInsnNode result = this.next;
/* 555 */       this.prev = result;
/* 556 */       this.next = result.next;
/* 557 */       this.remove = result;
/* 558 */       return result;
/*     */     }
/*     */     
/*     */     public void remove() {
/* 562 */       if (this.remove != null) {
/* 563 */         if (this.remove == this.next) {
/* 564 */           this.next = this.next.next;
/*     */         } else {
/* 566 */           this.prev = this.prev.prev;
/*     */         } 
/* 568 */         InsnList.this.remove(this.remove);
/* 569 */         this.remove = null;
/*     */       } else {
/* 571 */         throw new IllegalStateException();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean hasPrevious() {
/* 576 */       return (this.prev != null);
/*     */     }
/*     */     
/*     */     public Object previous() {
/* 580 */       AbstractInsnNode result = this.prev;
/* 581 */       this.next = result;
/* 582 */       this.prev = result.prev;
/* 583 */       this.remove = result;
/* 584 */       return result;
/*     */     }
/*     */     
/*     */     public int nextIndex() {
/* 588 */       if (this.next == null) {
/* 589 */         return InsnList.this.size();
/*     */       }
/* 591 */       if (InsnList.this.cache == null) {
/* 592 */         InsnList.this.cache = InsnList.this.toArray();
/*     */       }
/* 594 */       return this.next.index;
/*     */     }
/*     */     
/*     */     public int previousIndex() {
/* 598 */       if (this.prev == null) {
/* 599 */         return -1;
/*     */       }
/* 601 */       if (InsnList.this.cache == null) {
/* 602 */         InsnList.this.cache = InsnList.this.toArray();
/*     */       }
/* 604 */       return this.prev.index;
/*     */     }
/*     */     
/*     */     public void add(Object o) {
/* 608 */       if (this.next != null) {
/* 609 */         InsnList.this.insertBefore(this.next, (AbstractInsnNode)o);
/* 610 */       } else if (this.prev != null) {
/* 611 */         InsnList.this.insert(this.prev, (AbstractInsnNode)o);
/*     */       } else {
/* 613 */         InsnList.this.add((AbstractInsnNode)o);
/*     */       } 
/* 615 */       this.prev = (AbstractInsnNode)o;
/* 616 */       this.remove = null;
/*     */     }
/*     */     
/*     */     public void set(Object o) {
/* 620 */       if (this.remove != null) {
/* 621 */         InsnList.this.set(this.remove, (AbstractInsnNode)o);
/* 622 */         if (this.remove == this.prev) {
/* 623 */           this.prev = (AbstractInsnNode)o;
/*     */         } else {
/* 625 */           this.next = (AbstractInsnNode)o;
/*     */         } 
/*     */       } else {
/* 628 */         throw new IllegalStateException();
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\tree\InsnList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */