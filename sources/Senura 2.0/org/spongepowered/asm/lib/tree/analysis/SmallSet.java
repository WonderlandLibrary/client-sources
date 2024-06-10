/*     */ package org.spongepowered.asm.lib.tree.analysis;
/*     */ 
/*     */ import java.util.AbstractSet;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SmallSet<E>
/*     */   extends AbstractSet<E>
/*     */   implements Iterator<E>
/*     */ {
/*     */   E e1;
/*     */   E e2;
/*     */   
/*     */   static final <T> Set<T> emptySet() {
/*  50 */     return new SmallSet<T>(null, null);
/*     */   }
/*     */   
/*     */   SmallSet(E e1, E e2) {
/*  54 */     this.e1 = e1;
/*  55 */     this.e2 = e2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<E> iterator() {
/*  64 */     return new SmallSet(this.e1, this.e2);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  69 */     return (this.e1 == null) ? 0 : ((this.e2 == null) ? 1 : 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasNext() {
/*  77 */     return (this.e1 != null);
/*     */   }
/*     */   
/*     */   public E next() {
/*  81 */     if (this.e1 == null) {
/*  82 */       throw new NoSuchElementException();
/*     */     }
/*  84 */     E e = this.e1;
/*  85 */     this.e1 = this.e2;
/*  86 */     this.e2 = null;
/*  87 */     return e;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove() {}
/*     */ 
/*     */ 
/*     */   
/*     */   Set<E> union(SmallSet<E> s) {
/*  98 */     if ((s.e1 == this.e1 && s.e2 == this.e2) || (s.e1 == this.e2 && s.e2 == this.e1)) {
/*  99 */       return this;
/*     */     }
/* 101 */     if (s.e1 == null) {
/* 102 */       return this;
/*     */     }
/* 104 */     if (this.e1 == null) {
/* 105 */       return s;
/*     */     }
/* 107 */     if (s.e2 == null) {
/* 108 */       if (this.e2 == null)
/* 109 */         return new SmallSet(this.e1, s.e1); 
/* 110 */       if (s.e1 == this.e1 || s.e1 == this.e2) {
/* 111 */         return this;
/*     */       }
/*     */     } 
/* 114 */     if (this.e2 == null)
/*     */     {
/*     */ 
/*     */       
/* 118 */       if (this.e1 == s.e1 || this.e1 == s.e2) {
/* 119 */         return s;
/*     */       }
/*     */     }
/*     */     
/* 123 */     HashSet<E> r = new HashSet<E>(4);
/* 124 */     r.add(this.e1);
/* 125 */     if (this.e2 != null) {
/* 126 */       r.add(this.e2);
/*     */     }
/* 128 */     r.add(s.e1);
/* 129 */     if (s.e2 != null) {
/* 130 */       r.add(s.e2);
/*     */     }
/* 132 */     return r;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\tree\analysis\SmallSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */