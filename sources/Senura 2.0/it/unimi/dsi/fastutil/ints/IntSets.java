/*     */ package it.unimi.dsi.fastutil.ints;
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
/*     */ public final class IntSets
/*     */ {
/*     */   public static class EmptySet
/*     */     extends IntCollections.EmptyCollection
/*     */     implements IntSet, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public boolean remove(int ok) {
/*  46 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public Object clone() {
/*  50 */       return IntSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/*  55 */       return (o instanceof Set && ((Set)o).isEmpty());
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public boolean rem(int k) {
/*  60 */       return super.rem(k);
/*     */     }
/*     */     private Object readResolve() {
/*  63 */       return IntSets.EMPTY_SET;
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
/*     */     extends AbstractIntSet
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected final int element;
/*     */     
/*     */     protected Singleton(int element) {
/*  82 */       this.element = element;
/*     */     }
/*     */     
/*     */     public boolean contains(int k) {
/*  86 */       return (k == this.element);
/*     */     }
/*     */     
/*     */     public boolean remove(int k) {
/*  90 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public IntListIterator iterator() {
/*  94 */       return IntIterators.singleton(this.element);
/*     */     }
/*     */     
/*     */     public int size() {
/*  98 */       return 1;
/*     */     }
/*     */     
/*     */     public boolean addAll(Collection<? extends Integer> c) {
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
/*     */     public boolean addAll(IntCollection c) {
/* 114 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean removeAll(IntCollection c) {
/* 118 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean retainAll(IntCollection c) {
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
/*     */   public static IntSet singleton(int element) {
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
/*     */   public static IntSet singleton(Integer element) {
/* 149 */     return new Singleton(element.intValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedSet
/*     */     extends IntCollections.SynchronizedCollection
/*     */     implements IntSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected SynchronizedSet(IntSet s, Object sync) {
/* 158 */       super(s, sync);
/*     */     }
/*     */     protected SynchronizedSet(IntSet s) {
/* 161 */       super(s);
/*     */     }
/*     */     
/*     */     public boolean remove(int k) {
/* 165 */       synchronized (this.sync) {
/* 166 */         return this.collection.rem(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public boolean rem(int k) {
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
/*     */   public static IntSet synchronize(IntSet s) {
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
/*     */   public static IntSet synchronize(IntSet s, Object sync) {
/* 199 */     return new SynchronizedSet(s, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSet
/*     */     extends IntCollections.UnmodifiableCollection
/*     */     implements IntSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected UnmodifiableSet(IntSet s) {
/* 208 */       super(s);
/*     */     }
/*     */     
/*     */     public boolean remove(int k) {
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
/*     */     public boolean rem(int k) {
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
/*     */   public static IntSet unmodifiable(IntSet s) {
/* 240 */     return new UnmodifiableSet(s);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\IntSets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */