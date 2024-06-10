/*     */ package it.unimi.dsi.fastutil.doubles;
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
/*     */ public final class DoubleSets
/*     */ {
/*     */   public static class EmptySet
/*     */     extends DoubleCollections.EmptyCollection
/*     */     implements DoubleSet, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public boolean remove(double ok) {
/*  46 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public Object clone() {
/*  50 */       return DoubleSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/*  55 */       return (o instanceof Set && ((Set)o).isEmpty());
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public boolean rem(double k) {
/*  60 */       return super.rem(k);
/*     */     }
/*     */     private Object readResolve() {
/*  63 */       return DoubleSets.EMPTY_SET;
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
/*     */     extends AbstractDoubleSet
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected final double element;
/*     */     
/*     */     protected Singleton(double element) {
/*  82 */       this.element = element;
/*     */     }
/*     */     
/*     */     public boolean contains(double k) {
/*  86 */       return (Double.doubleToLongBits(k) == Double.doubleToLongBits(this.element));
/*     */     }
/*     */     
/*     */     public boolean remove(double k) {
/*  90 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public DoubleListIterator iterator() {
/*  94 */       return DoubleIterators.singleton(this.element);
/*     */     }
/*     */     
/*     */     public int size() {
/*  98 */       return 1;
/*     */     }
/*     */     
/*     */     public boolean addAll(Collection<? extends Double> c) {
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
/*     */     public boolean addAll(DoubleCollection c) {
/* 114 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean removeAll(DoubleCollection c) {
/* 118 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean retainAll(DoubleCollection c) {
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
/*     */   public static DoubleSet singleton(double element) {
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
/*     */   public static DoubleSet singleton(Double element) {
/* 149 */     return new Singleton(element.doubleValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedSet
/*     */     extends DoubleCollections.SynchronizedCollection
/*     */     implements DoubleSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected SynchronizedSet(DoubleSet s, Object sync) {
/* 158 */       super(s, sync);
/*     */     }
/*     */     protected SynchronizedSet(DoubleSet s) {
/* 161 */       super(s);
/*     */     }
/*     */     
/*     */     public boolean remove(double k) {
/* 165 */       synchronized (this.sync) {
/* 166 */         return this.collection.rem(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public boolean rem(double k) {
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
/*     */   public static DoubleSet synchronize(DoubleSet s) {
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
/*     */   public static DoubleSet synchronize(DoubleSet s, Object sync) {
/* 199 */     return new SynchronizedSet(s, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSet
/*     */     extends DoubleCollections.UnmodifiableCollection
/*     */     implements DoubleSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected UnmodifiableSet(DoubleSet s) {
/* 208 */       super(s);
/*     */     }
/*     */     
/*     */     public boolean remove(double k) {
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
/*     */     public boolean rem(double k) {
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
/*     */   public static DoubleSet unmodifiable(DoubleSet s) {
/* 240 */     return new UnmodifiableSet(s);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\DoubleSets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */