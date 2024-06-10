/*     */ package it.unimi.dsi.fastutil.chars;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
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
/*     */ public final class CharSets
/*     */ {
/*     */   public static class EmptySet
/*     */     extends CharCollections.EmptyCollection
/*     */     implements CharSet, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public boolean remove(char ok) {
/*  46 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public Object clone() {
/*  50 */       return CharSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/*  55 */       return (o instanceof Set && ((Set)o).isEmpty());
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public boolean rem(char k) {
/*  60 */       return super.rem(k);
/*     */     }
/*     */     private Object readResolve() {
/*  63 */       return CharSets.EMPTY_SET;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   public static final EmptySet EMPTY_SET = new EmptySet();
/*     */ 
/*     */   
/*     */   public static class Singleton
/*     */     extends AbstractCharSet
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected final char element;
/*     */     
/*     */     protected Singleton(char element) {
/*  82 */       this.element = element;
/*     */     }
/*     */     
/*     */     public boolean contains(char k) {
/*  86 */       return (k == this.element);
/*     */     }
/*     */     
/*     */     public boolean remove(char k) {
/*  90 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public CharListIterator iterator() {
/*  94 */       return CharIterators.singleton(this.element);
/*     */     }
/*     */     
/*     */     public int size() {
/*  98 */       return 1;
/*     */     }
/*     */     
/*     */     public boolean addAll(Collection<? extends Character> c) {
/* 102 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 106 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 110 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean addAll(CharCollection c) {
/* 114 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean removeAll(CharCollection c) {
/* 118 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean retainAll(CharCollection c) {
/* 122 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 126 */       return this;
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
/*     */   public static CharSet singleton(char element) {
/* 138 */     return new Singleton(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CharSet singleton(Character element) {
/* 149 */     return new Singleton(element.charValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedSet
/*     */     extends CharCollections.SynchronizedCollection
/*     */     implements CharSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected SynchronizedSet(CharSet s, Object sync) {
/* 158 */       super(s, sync);
/*     */     }
/*     */     protected SynchronizedSet(CharSet s) {
/* 161 */       super(s);
/*     */     }
/*     */     
/*     */     public boolean remove(char k) {
/* 165 */       synchronized (this.sync) {
/* 166 */         return this.collection.rem(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public boolean rem(char k) {
/* 172 */       return super.rem(k);
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
/*     */   public static CharSet synchronize(CharSet s) {
/* 185 */     return new SynchronizedSet(s);
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
/*     */   public static CharSet synchronize(CharSet s, Object sync) {
/* 199 */     return new SynchronizedSet(s, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSet
/*     */     extends CharCollections.UnmodifiableCollection
/*     */     implements CharSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected UnmodifiableSet(CharSet s) {
/* 208 */       super(s);
/*     */     }
/*     */     
/*     */     public boolean remove(char k) {
/* 212 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 216 */       if (o == this)
/* 217 */         return true; 
/* 218 */       return this.collection.equals(o);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 222 */       return this.collection.hashCode();
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public boolean rem(char k) {
/* 227 */       return super.rem(k);
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
/*     */   public static CharSet unmodifiable(CharSet s) {
/* 240 */     return new UnmodifiableSet(s);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\CharSets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */